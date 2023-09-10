package de.quai.twitchmentionsdiscord.model.discord;

public class DiscordAuthor {
    private String name;
    private String url;
    private String iconUrl;

    DiscordAuthor(String name, String url, String iconUrl) {
        this.name = name;
        this.url = url;
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}
