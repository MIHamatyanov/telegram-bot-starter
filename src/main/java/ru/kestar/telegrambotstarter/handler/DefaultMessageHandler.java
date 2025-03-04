package ru.kestar.telegrambotstarter.handler;

import java.util.Optional;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.kestar.telegrambotstarter.context.TelegramActionContext;
import ru.kestar.telegrambotstarter.exception.UnknownBotCommandException;

public class DefaultMessageHandler implements UpdateHandler {

    @Override
    public Optional<BotApiMethod<?>> handle(TelegramActionContext context) {
        throw new UnknownBotCommandException();
    }
}
