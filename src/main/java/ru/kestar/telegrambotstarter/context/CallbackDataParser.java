package ru.kestar.telegrambotstarter.context;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.kestar.telegrambotstarter.exception.CallbackDataParseException;

@Slf4j
@RequiredArgsConstructor
public class CallbackDataParser {
    private static final String DELIMITER = ";";

    public String toString(CallbackData callbackData) {
        if (callbackData.getData() != null) {
            return callbackData.getAction() + DELIMITER + callbackData.getData();
        }
        return callbackData.getAction();
    }

    public CallbackData fromString(String callbackData) {
        final String[] dataParts = callbackData.split(DELIMITER);
        if (dataParts.length == 1) {
            return new CallbackData(dataParts[0]);
        } else if (dataParts.length > 1) {
            String data = callbackData.substring(callbackData.indexOf(DELIMITER) + 1);
            return new CallbackData(dataParts[0], data);
        } else {
            log.error("Invalid callback data format");
            throw new CallbackDataParseException("Invalid callback data format");
        }
    }
}
