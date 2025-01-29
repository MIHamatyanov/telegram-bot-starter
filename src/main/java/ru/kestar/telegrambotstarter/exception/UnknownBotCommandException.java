package ru.kestar.telegrambotstarter.exception;

public class UnknownBotCommandException extends RuntimeException {

    public UnknownBotCommandException() {
        super("Unknown command");
    }

}
