package ru.kestar.telegrambotstarter.config;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.kestar.telegrambotstarter.bot.TelegramBot;
import ru.kestar.telegrambotstarter.config.properties.TelegramBotProperties;
import ru.kestar.telegrambotstarter.context.CallbackDataParser;
import ru.kestar.telegrambotstarter.exception.handler.DefaultErrorHandler;
import ru.kestar.telegrambotstarter.exception.handler.ErrorHandler;
import ru.kestar.telegrambotstarter.handler.DefaultMessageHandler;
import ru.kestar.telegrambotstarter.handler.TelegramHandlersRegisterer;
import ru.kestar.telegrambotstarter.handler.TelegramRequestDispatcher;
import ru.kestar.telegrambotstarter.handler.UpdateHandler;

@Slf4j
@AutoConfiguration
@ConditionalOnProperty(name = "telegram.bot.enabled", havingValue = "true")
@EnableConfigurationProperties(TelegramBotProperties.class)
public class TelegramBotAutoconfiguration {

    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        return new TelegramBotsApi(DefaultBotSession.class);
    }

    @Bean
    public TelegramRequestDispatcher telegramRequestDispatcher(ErrorHandler errorHandler,
                                                               CallbackDataParser callbackDataParser,
                                                               DefaultMessageHandler defaultMessageHandler) {
        return new TelegramRequestDispatcher(errorHandler, callbackDataParser, defaultMessageHandler);
    }

    @Bean
    public TelegramHandlersRegisterer telegramHandlersRegisterer(TelegramRequestDispatcher requestDispatcher,
                                                                 List<UpdateHandler> handlers) {
        return new TelegramHandlersRegisterer(handlers, requestDispatcher);
    }

    @Bean
    @ConditionalOnMissingBean
    public TelegramBot telegramBot(TelegramBotProperties properties,
                                   TelegramRequestDispatcher requestDispatcher,
                                   TelegramBotsApi telegramBotsApi) {
        return new TelegramBot(properties, requestDispatcher, telegramBotsApi);
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorHandler errorHandler() {
        return new DefaultErrorHandler();
    }

    @Bean
    public CallbackDataParser callbackDataParser() {
        return new CallbackDataParser();
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultMessageHandler defaultMessageHandler() {
        return new DefaultMessageHandler();
    }
}
