package ru.kestar.telegrambotstarter.context;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallbackData {

    @JsonProperty("action")
    private String action;

    @JsonProperty("data")
    private Map<String, String> data;
}
