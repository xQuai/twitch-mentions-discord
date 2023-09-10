package de.quai.twitchmentionsdiscord.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import top.code2life.config.DynamicConfig;

import javax.validation.constraints.*;
import java.util.List;

@Data
@DynamicConfig
@Configuration
@ConfigurationProperties(prefix = "twitch")
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TwitchProperties {

    private String username;

    @Pattern(regexp = "^[a-z]{2}(-[A-Z]{2})?$")
    private String language;

    private Boolean mentions;

    private List<String> channels;

    private List<String> ids;
}
