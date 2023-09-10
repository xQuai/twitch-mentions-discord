package de.quai.twitchmentionsdiscord.model.discord;

public class DiscordThumbnail {
    private String url;

    DiscordThumbnail(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
