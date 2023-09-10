package de.quai.twitchmentionsdiscord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TwitchMentionsDiscordApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitchMentionsDiscordApplication.class, args);
    }

}
