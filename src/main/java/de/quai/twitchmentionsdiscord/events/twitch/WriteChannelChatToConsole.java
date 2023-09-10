package de.quai.twitchmentionsdiscord.events.twitch;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WriteChannelChatToConsole {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriteChannelChatToConsole.class);

    /**
     * Subscribe to the ChannelMessage Event and write the output to the console
     */
    @EventListener
    public void onChannelMessage(@NotNull ChannelMessageEvent event) {
        if (LOGGER.isDebugEnabled()) {
            String message = String.format(
                    "Channel [%s] - User [%s] - Message [%s]%n",
                    event.getChannel().getName(),
                    event.getUser().getName(),
                    event.getMessage());

            LOGGER.debug(message);
        }
    }

}
