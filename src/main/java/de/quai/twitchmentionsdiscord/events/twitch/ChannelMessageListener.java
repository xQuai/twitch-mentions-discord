package de.quai.twitchmentionsdiscord.events.twitch;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.helix.domain.UserList;
import de.quai.twitchmentionsdiscord.discord.DiscordWebhook;
import de.quai.twitchmentionsdiscord.config.DiscordProperties;
import de.quai.twitchmentionsdiscord.config.TwitchProperties;
import de.quai.twitchmentionsdiscord.model.discord.DiscordEmbedObject;
import de.quai.twitchmentionsdiscord.utils.RegexCheck;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ChannelMessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelMessageListener.class);

    @Autowired
    private TwitchProperties twitchProperties;

    @Autowired
    private DiscordProperties discordProperties;

    @Autowired
    private TwitchClient twitchClient;

    private final RegexCheck regexCheck;

    @EventListener
    public void handleChannelMessageEvent(@NotNull ChannelMessageEvent event) {
        LOGGER.debug("Received ChannelMessageEvent {}  {}", event.getUser().getName(), event);

        if (twitchProperties.getMentions()) {

            boolean isMentioned = regexCheck.checkForHighlight(event.getMessage());

            try {
                if (isMentioned) {

                    LOGGER.info("Mention gefunden in folgender Nachricht von " + event.getUser().getName());
                    LOGGER.info(event.getMessage());

                    UserList userList = twitchClient.getHelix().getUsers(null, Arrays.asList(event.getChannel().getId()), null).execute();

                    DiscordWebhook webhook = new DiscordWebhook(discordProperties.getWebhook());

                    if (!userList.getUsers().isEmpty()) {
                        webhook.setAvatarUrl(userList.getUsers().get(0).getProfileImageUrl());
                    }

                    webhook.setUsername("Lachesis");
                    webhook.setTts(false);
                    webhook.addEmbed(
                            new DiscordEmbedObject()
                                    .setTimestamp(event.getFiredAtInstant().toString())
                                    .setColor(Color.RED)
                                    .addField("Channel", StringEscapeUtils.escapeJava(event.getChannel().getName()), false)
                                    .addField("User", StringEscapeUtils.escapeJava(event.getUser().getName()), false)
                                    .addField("Message", StringEscapeUtils.escapeJava(event.getMessage()), false)
                                    .setUrl("https://twitch.tv/" + StringEscapeUtils.escapeJava(event.getChannel().getName())));
                    webhook.execute(); //Handle exception
                }
            } catch (IOException e) {
                LOGGER.error("Fehler beim Ausführen des Discord Webhooks: ");
                e.printStackTrace();
            }
        }
    }
}

