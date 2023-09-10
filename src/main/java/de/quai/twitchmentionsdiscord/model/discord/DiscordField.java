package de.quai.twitchmentionsdiscord.model.discord;

public class DiscordField {
    private String name;
    private String value;
    private boolean inline;

    DiscordField(String name, String value, boolean inline) {
        this.name = name;
        this.value = value;
        this.inline = inline;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public boolean isInline() {
        return inline;
    }
}
