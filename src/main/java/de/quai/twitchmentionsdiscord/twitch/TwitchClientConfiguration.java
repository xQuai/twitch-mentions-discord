package de.quai.twitchmentionsdiscord.twitch;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.philippheuer.events4j.spring.SpringEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.TwitchChatBuilder;
import com.github.twitch4j.pubsub.TwitchPubSubConnectionPool;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class TwitchClientConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchClientConfiguration.class);

    @Autowired
    private SpringEventHandler springEventHandler;

    @Value("${api.twitch_client_id}")
    private String twitch_client_id;
    @Value("${api.twitch_client_secret}")
    private String twitch_client_secret;
    @Value("${credentials.irc}")
    private String irc_token;


    @Bean
    public TwitchClient getTwitchClient() {

        TwitchClientBuilder clientBuilder = TwitchClientBuilder.builder();

        //region TwitchClient
        final TwitchClient twitchClient = clientBuilder
                .withClientId(twitch_client_id)
                .withClientSecret(twitch_client_secret)
                .withDefaultAuthToken(getCredentials())
                .withDefaultEventHandler(SimpleEventHandler.class)
                .withEnableHelix(true)
                /*
                 * Chat Module
                 * Joins irc and triggers all chat based events (viewer join/leave/sub/bits/gifted subs/...)
                 */
                .withChatAccount(getCredentials())
                .withEnableChat(false)
                /*
                 * PubSub Module
                 */
                .withEnablePubSub(false)
                /*
                 * Build the TwitchClient Instance
                 */
                .build();

        // Spring Eventhandler registrieren
        twitchClient.getEventManager().registerEventHandler(springEventHandler);

        //endregion

        return twitchClient;
    }

    @Bean
    public OAuth2Credential getCredentials() {
        return new OAuth2Credential("twitch", irc_token);
    }

    @Bean
    public TwitchPubSubConnectionPool getTwitchPubSubConnectionPool() {
        return TwitchPubSubConnectionPool.builder()
                /*
                 * Max Connections
                 */
                .maxSubscriptionsPerConnection(50)
                .eventManager(getTwitchClient().getEventManager())
                .build();
    }

    @Bean
    public TwitchChat getTwitchChat() {
        TwitchChatBuilder chatBuilder = TwitchChatBuilder.builder();

        final TwitchChat twitchChat = chatBuilder
                .withClientId(twitch_client_id)
                .withClientSecret(twitch_client_secret)
                .withRemoveChannelOnJoinFailure(true)
                .withDefaultEventHandler(SimpleEventHandler.class)
                .withChatAccount(getCredentials())
                .build();

        // Spring Eventhandler registrieren
        twitchChat.getEventManager().registerEventHandler(springEventHandler);

        return twitchChat;

    }

}
