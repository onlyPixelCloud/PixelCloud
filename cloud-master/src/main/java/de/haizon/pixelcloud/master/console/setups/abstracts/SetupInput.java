package de.haizon.pixelcloud.master.console.setups.abstracts;

import java.util.List;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public abstract class SetupInput {

    private final String question;

    public SetupInput(String question) {
        this.question = question;
    }

    public abstract List<String> getSuggestions();

    public abstract boolean handle(String input);

    public String getQuestion() {
        return question;
    }

}
