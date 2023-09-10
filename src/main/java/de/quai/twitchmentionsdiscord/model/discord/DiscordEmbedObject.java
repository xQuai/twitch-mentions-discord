package de.quai.twitchmentionsdiscord.model.discord;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DiscordEmbedObject {
    private String title;
    private String description;
    private String url;
    private Color color;

    private DiscordFooter footer;
    private DiscordThumbnail thumbnail;
    private DiscordImage image;
    private DiscordAuthor author;

    private String timestamp;

    private List<DiscordField> fields = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public DiscordEmbedObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DiscordEmbedObject setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public DiscordEmbedObject setUrl(String url) {
        this.url = url;
        return this;
    }

    public Color getColor() {
        return color;
    }

    public DiscordEmbedObject setColor(Color color) {
        this.color = color;
        return this;
    }

    public DiscordFooter getFooter() {
        return footer;
    }

    public DiscordThumbnail getThumbnail() {
        return thumbnail;
    }

    public DiscordEmbedObject setThumbnail(String url) {
        this.thumbnail = new DiscordThumbnail(url);
        return this;
    }

    public DiscordImage getImage() {
        return image;
    }

    public DiscordEmbedObject setImage(String url) {
        this.image = new DiscordImage(url);
        return this;
    }

    public DiscordAuthor getAuthor() {
        return author;
    }

    public List<DiscordField> getFields() {
        return fields;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public DiscordEmbedObject setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public DiscordEmbedObject setFooter(String text, String icon) {
        this.footer = new DiscordFooter(text, icon);
        return this;
    }

    public DiscordEmbedObject setAuthor(String name, String url, String icon) {
        this.author = new DiscordAuthor(name, url, icon);
        return this;
    }

    public DiscordEmbedObject addField(String name, String value, boolean inline) {
        this.fields.add(new DiscordField(name, value, inline));
        return this;
    }
}