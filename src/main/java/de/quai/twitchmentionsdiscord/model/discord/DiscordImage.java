package de.quai.twitchmentionsdiscord.model.discord;

public class DiscordImage {
    private String url;

    DiscordImage(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
