package ru.kestar.telegrambotstarter.handler;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.kestar.telegrambotstarter.context.TelegramActionContext;

public interface UpdateHandler {

    Optional<BotApiMethod<?>> handle(TelegramActionContext context);

    default List<String> getSupportedCommands() {
        return Collections.emptyList();
    }

    default List<String> getSupportedCallbacks() {
        return Collections.emptyList();
    }
}
