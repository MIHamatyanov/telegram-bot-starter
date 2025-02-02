package ru.kestar.telegrambotstarter.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallbackData {

    private String action;

    private Integer messageId;

    private String data;

    public CallbackData(String action) {
        this.action = action;
    }

    public CallbackData(String action, String data) {
        this(action);
        this.data = data;
    }
}
