package de.quai.twitchmentionsdiscord.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import top.code2life.config.DynamicConfig;


@Data
@DynamicConfig
@Configuration
@ConfigurationProperties(prefix = "mentions")
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MentionsProperties {

    private String[] words;
}
