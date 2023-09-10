package de.quai.twitchmentionsdiscord.events.twitch;

import com.github.twitch4j.chat.events.channel.ChannelJoinFailureEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ChannelJoinFailureListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelJoinFailureListener.class);

    @EventListener
    public void handleChannelJoinFailureEvent(@NotNull ChannelJoinFailureEvent event) {
        LOGGER.error("Error Joining Channel {}, Reason {}", event.getChannelName(),event.getReason().name());
    }
}
