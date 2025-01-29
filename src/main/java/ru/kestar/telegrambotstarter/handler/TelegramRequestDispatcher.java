package ru.kestar.telegrambotstarter.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kestar.telegrambotstarter.context.CallbackData;
import ru.kestar.telegrambotstarter.context.CallbackDataParser;
import ru.kestar.telegrambotstarter.context.TelegramActionContext;
import ru.kestar.telegrambotstarter.exception.UnknownBotCommandException;
import ru.kestar.telegrambotstarter.exception.handler.ErrorHandler;

@Slf4j
@RequiredArgsConstructor
public class TelegramRequestDispatcher {
    private final ErrorHandler errorHandler;
    private final CallbackDataParser callbackDataParser;

    private final Map<String, UpdateHandler> commandHandlers = new HashMap<>();
    private final Map<String, UpdateHandler> callbackHandlers = new HashMap<>();

    public void registerCommandHandler(String command, UpdateHandler handler) {
        commandHandlers.put(command, handler);
    }

    public void registerCallbackHandler(String action, UpdateHandler handler) {
        callbackHandlers.put(action, handler);
    }

    public Optional<BotApiMethod<?>> dispatch(TelegramActionContext context) {
        try {
            final Update update = context.getUpdate();
            if (update.hasMessage()) {
                return dispatchCommand(context);
            }
            if (update.hasCallbackQuery()) {
                return dispatchCallback(context);
            }

            throw new UnknownBotCommandException();
        } catch (Exception e) {
            return errorHandler.handle(context, e);
        }
    }

    private Optional<BotApiMethod<?>> dispatchCommand(TelegramActionContext context) {
        final Message message = context.getUpdate().getMessage();
        if (message.isCommand()) {
            final String command = message.getText().split(" ")[0];
            if (command != null && commandHandlers.containsKey(command)) {
                return commandHandlers.get(command).handle(context);
            }
        }
        throw new UnknownBotCommandException();
    }

    private Optional<BotApiMethod<?>> dispatchCallback(TelegramActionContext context) {
        final CallbackQuery callbackQuery = context.getUpdate().getCallbackQuery();
        final CallbackData callbackData = callbackDataParser.fromString(callbackQuery.getData());
        context.setCallbackData(callbackData);
        context.setCallbackMessageId(callbackQuery.getMessage().getMessageId());

        if (callbackData != null && callbackData.getAction() != null && callbackHandlers.containsKey(callbackData.getAction())) {
            return callbackHandlers.get(callbackData.getAction()).handle(context);
        }
        throw new UnknownBotCommandException();
    }
}
