package de.quai.twitchmentionsdiscord.config;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Configuration
@ConfigurationProperties(prefix = "discord")
@Validated
public class DiscordProperties {

    @NotNull
    @URL
    private String webhook;
}
