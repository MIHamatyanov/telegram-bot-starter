package ru.kestar.telegrambotstarter.handler;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
public class TelegramHandlersRegisterer {
    private final List<UpdateHandler> updateHandlers;
    private final TelegramRequestDispatcher dispatcher;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        updateHandlers.forEach(handler -> {
            handler.getSupportedCommands().forEach(command ->
                dispatcher.registerCommandHandler(command, handler)
            );
            handler.getSupportedCallbacks().forEach(callback ->
                dispatcher.registerCallbackHandler(callback, handler)
            );
        });
    }
}
