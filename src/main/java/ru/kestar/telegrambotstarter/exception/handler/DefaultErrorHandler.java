package ru.kestar.telegrambotstarter.exception.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.kestar.telegrambotstarter.context.TelegramActionContext;

@Slf4j
public class DefaultErrorHandler implements ErrorHandler {

    private final Map<Class<? extends Exception>, BiFunction<TelegramActionContext, Exception, Optional<BotApiMethod<?>>>>
        exceptionHandlers = new HashMap<>();

    public <E extends Exception> void registerHandler(Class<E> exceptionClass, BiFunction<TelegramActionContext, E, Optional<BotApiMethod<?>>> handler) {
        exceptionHandlers.put(exceptionClass,
            (context, ex) -> handler.apply(context, exceptionClass.cast(ex))
        );
    }

    @Override
    public Optional<BotApiMethod<?>> handle(TelegramActionContext context, Exception e) {
        Class<?> exceptionClass = e.getClass();
        while (exceptionClass != null) {
            if (exceptionHandlers.containsKey(exceptionClass)) {
                return exceptionHandlers.get(exceptionClass).apply(context, e);
            }
            exceptionClass = exceptionClass.getSuperclass();
        }

        log.error("Unhandled exception: {}", e.getMessage(), e);
        return Optional.empty();
    }
}
