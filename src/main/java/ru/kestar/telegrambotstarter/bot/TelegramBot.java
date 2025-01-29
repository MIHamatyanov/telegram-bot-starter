package ru.kestar.telegrambotstarter.bot;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kestar.telegrambotstarter.config.properties.TelegramBotProperties;
import ru.kestar.telegrambotstarter.context.TelegramActionContext;
import ru.kestar.telegrambotstarter.handler.TelegramRequestDispatcher;

@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final TelegramBotProperties botProperties;
    private final TelegramRequestDispatcher requestDispatcher;
    private final TelegramBotsApi botsApi;

    public TelegramBot(TelegramBotProperties botProperties,
                       TelegramRequestDispatcher requestDispatcher,
                       TelegramBotsApi botsApi) {
        super(botProperties.getToken());

        this.botProperties = botProperties;
        this.requestDispatcher = requestDispatcher;
        this.botsApi = botsApi;
    }

    @PostConstruct
    public void init() throws TelegramApiException {
        botsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return botProperties.getUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {
        final TelegramActionContext context = new TelegramActionContext(update);
        requestDispatcher.dispatch(context)
            .ifPresent(this::safeExecute);
    }

    public void safeExecute(BotApiMethod<?> method) {
        try {
            execute(method);
        } catch (Exception e) {
            log.error("Error occurred while sending telegram response", e);
        }
    }
}
