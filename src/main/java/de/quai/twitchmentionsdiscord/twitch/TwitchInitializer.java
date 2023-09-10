package de.quai.twitchmentionsdiscord.twitch;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.helix.domain.OutboundFollow;
import com.github.twitch4j.helix.domain.OutboundFollowing;
import com.github.twitch4j.helix.domain.User;
import com.github.twitch4j.helix.domain.UserList;
import com.github.twitch4j.pubsub.TwitchPubSubConnectionPool;
import com.github.twitch4j.util.PaginationUtil;
import de.quai.twitchmentionsdiscord.config.TwitchProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@RequiredArgsConstructor
@Component
public class TwitchInitializer {


    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchClientConfiguration.class);


    private final TwitchClient twitchClient;

    private final TwitchChat twitchChat;

    private final OAuth2Credential credential;

    private final TwitchProperties twitchProperties;

    private final TwitchPubSubConnectionPool twitchPubSubConnectionPool;



    /**
     * Load all Followers from TwitchAPI
     */
    @Scheduled(fixedDelay = 300000, initialDelay = 300000)
    private void loadFollowerFromTwitchUser() {

        LOGGER.info("START - Initiale Follower");

        // Get Login name from Token
        String loginName = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(credential).map(OAuth2Credential::getUserName).orElse(null);

        UserList helixUser = twitchClient.getHelix().getUsers(null, null, Collections.singletonList(loginName)).execute();

        // Followed Channels in Liste laden
        List<OutboundFollow> followList = fetchFollower(helixUser.getUsers().get(0).getId());


        // Aktuelle Channels
        Collection<String> joinedChannels = twitchChat.getChannels();


        //  Join Channel if new channel
        for (OutboundFollow follow : followList) {
            LOGGER.debug(" ID of Followed: " + follow.getBroadcasterId() + " Name of Followed: " + follow.getBroadcasterName() + " - Followed At: " + follow.getFollowedAt());
            if (!twitchChat.isChannelJoined(follow.getBroadcasterLogin())) {
                LOGGER.info("Joining Channel {}", follow.getBroadcasterLogin());
                twitchChat.joinChannel(follow.getBroadcasterLogin());
            }
        }


        // Check if we are in this channel yes --> leave it
        joinedChannels.forEach(channel -> {
            if (!containsChannel(followList, channel) && !containsChannelInFile(twitchProperties.getChannels(), channel) && !channel.equalsIgnoreCase(loginName)) {
                LOGGER.info("Leaving Channel {}", channel);
                twitchChat.leaveChannel(channel);
            }
        });

        LOGGER.info("END - Initiale Follower");
    }

    /**
     * Load Followers from application.yml
     */
    @Scheduled(fixedDelay = 300000, initialDelay = 300000)
    private void loadFollowerFromFile() {
        LOGGER.info("START - Initiale Follower from File");

        // Get Login name from Token
        String loginName = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(credential).map(OAuth2Credential::getUserName).orElse(null);


        for (String channels : twitchProperties.getChannels()) {
            if (!twitchChat.isChannelJoined(channels)) {
                LOGGER.info("Joining Channel {}", channels);
                twitchChat.joinChannel(channels);
            }
        }

        LOGGER.info("END - Initiale Follower from File");
    }

    /**
     * Init Method
     */
    @PostConstruct
    public void init() {
        LOGGER.info("START - init");
        loadFollowerFromTwitchUser();
        loadFollowerFromFile();
        LOGGER.info("END - init");
    }

    /**
     * Check if a channelname is in follower list
     *
     * @param list
     * @param searchChannel
     * @return Boolean
     */
    public boolean containsChannel(final List<OutboundFollow> list, final String searchChannel) {
        return list.stream().filter(o -> o.getBroadcasterLogin().toLowerCase().equalsIgnoreCase(searchChannel)).findFirst().isPresent();
    }

    /**
     * Check if a channelname is in list of strings
     *
     * @param list
     * @param searchChannel
     * @return Boolean
     */
    public boolean containsChannelString(final List<String> list, final String searchChannel) {
        return list.stream().filter(o -> o.toLowerCase().equalsIgnoreCase(searchChannel)).findFirst().isPresent();
    }

    /**
     * Check if a channelname is in follower list file
     *
     * @param list
     * @param searchChannel
     * @return Boolean
     */
    public boolean containsChannelInFile(List<String> list, String searchChannel) {
        return list.stream().filter(o -> o.toLowerCase().equalsIgnoreCase(searchChannel)).findFirst().isPresent();
    }

    /**
     * Returns all Follower of a given userid
     *
     * @param userId
     * @return List with Followers
     */
    public List<OutboundFollow> fetchFollower(String userId) {
        if (!userId.isEmpty()) {
            List<OutboundFollow> followers = PaginationUtil.getPaginated(
                    cursor -> {
                        try {
                            return twitchClient.getHelix().getFollowedChannels(null, userId, null, null, cursor).execute();
                        } catch (Exception e) {
                            LOGGER.error(e.getMessage());
                            e.printStackTrace();
                            return null;
                        }
                    },
                    OutboundFollowing::getFollows,
                    call -> call.getPagination() != null ? call.getPagination().getCursor() : null
            );
            return followers;
        }
        // Return a empty List when no Names are provided
        return new ArrayList<>();
    }

}
