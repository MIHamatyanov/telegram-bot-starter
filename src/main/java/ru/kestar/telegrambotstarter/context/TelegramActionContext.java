package ru.kestar.telegrambotstarter.context;

import java.util.Optional;
import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Data
public class TelegramActionContext {
    private Update update;
    private CallbackData callbackData;

    public TelegramActionContext(Update update) {
        this.update = update;
    }

    public String getChatId() {
        Message message = null;
        if (update.hasCallbackQuery()) {
            message = update.getCallbackQuery().getMessage();
        } else if (update.hasMessage()) {
            message = update.getMessage();
        }

        return Optional.ofNullable(message)
            .map(Message::getChatId)
            .map(Object::toString)
            .orElse("");
    }

    public User getUser() {
        User user = null;
        if (update.hasCallbackQuery()) {
            user = update.getCallbackQuery().getFrom();
        } else if (update.hasMessage()) {
            user = update.getMessage().getFrom();
        }
        return user;
    }
}
