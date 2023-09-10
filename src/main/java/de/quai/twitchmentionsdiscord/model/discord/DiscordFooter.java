package de.quai.twitchmentionsdiscord.model.discord;

public class DiscordFooter {
    private String text;
    private String iconUrl;

    DiscordFooter(String text, String iconUrl) {
        this.text = text;
        this.iconUrl = iconUrl;
    }

    public String getText() {
        return text;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}