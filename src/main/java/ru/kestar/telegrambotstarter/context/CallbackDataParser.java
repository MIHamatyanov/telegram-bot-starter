package ru.kestar.telegrambotstarter.context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.kestar.telegrambotstarter.exception.CallbackDataParseException;

@Slf4j
@RequiredArgsConstructor
public class CallbackDataParser {
    private final ObjectMapper objectMapper;

    public String toString(CallbackData callbackData) {
        try {
            return objectMapper.writeValueAsString(callbackData);
        } catch (JsonProcessingException e) {
            log.error("Error occurred while creating callback data", e);
            throw new CallbackDataParseException(e.getMessage());
        }
    }

    public CallbackData fromString(String callbackData) {
        try {
            return objectMapper.readValue(callbackData, CallbackData.class);
        } catch (Exception e) {
            log.error("Error while parse callback data", e);
            throw new CallbackDataParseException(e.getMessage());
        }
    }
}
