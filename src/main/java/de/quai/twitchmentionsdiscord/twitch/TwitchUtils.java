package de.quai.twitchmentionsdiscord.twitch;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TwitchUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchUtils.class);

    @Autowired
    private OAuth2Credential credential;

    /**
     * Gets Username from Token
     *
     * @return Username from loggedin Token
     */
    public String getLoginName() {
        String loginName = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(credential).map(OAuth2Credential::getUserName).orElse(null);
        return loginName;
    }

    /**
     * Gets UserId from Token
     *
     * @return UserId from loggedin Token
     */
    public String getUserId() {
        String userId = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(credential).map(OAuth2Credential::getUserId).orElse(null);
        return userId;
    }


}
