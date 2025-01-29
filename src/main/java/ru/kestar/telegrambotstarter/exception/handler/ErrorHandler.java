package ru.kestar.telegrambotstarter.exception.handler;

import java.util.Optional;
import java.util.function.BiFunction;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.kestar.telegrambotstarter.context.TelegramActionContext;

public interface ErrorHandler {

    Optional<BotApiMethod<?>> handle(TelegramActionContext context, Exception e);

    <E extends Exception> void registerHandler(Class<E> exceptionClass,
                                               BiFunction<TelegramActionContext, E, Optional<BotApiMethod<?>>> handler);
}
