package ru.kestar.telegrambotstarter.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "telegram.bot")
public class TelegramBotProperties {
    private boolean enabled;
    private String token;
    private String username;
}
