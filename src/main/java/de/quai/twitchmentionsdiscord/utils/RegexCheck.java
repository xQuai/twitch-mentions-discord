package de.quai.twitchmentionsdiscord.utils;

import de.quai.twitchmentionsdiscord.config.MentionsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;


@Component
@RequiredArgsConstructor
public class RegexCheck {


    private final MentionsProperties words;

    public boolean checkForHighlight(String inputString) {

        StringBuilder regexp = new StringBuilder();

        int i = words.getWords().length - 1;
        for (String word : words.getWords()) {
            if (i-- == 0) {
                regexp.append("(.*\\b").append(word).append("\\b.*)");
            } else {
                regexp.append("(.*\\b").append(word).append("\\b.*)|");
            }
        }

        Pattern pattern = Pattern.compile(regexp.toString(), Pattern.CASE_INSENSITIVE);

        return pattern.matcher(inputString).find();
    }
}
