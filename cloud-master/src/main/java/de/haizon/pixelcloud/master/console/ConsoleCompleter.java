package de.haizon.pixelcloud.master.console;

import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaDoc this file!
 * Created: 02.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class ConsoleCompleter implements Completer {

    private List<String> suggestions;

    @Override
    public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {

        if(suggestions == null){
            return;
        }

        if(suggestions.isEmpty()) {
            return;
        }
        candidates.addAll(getCurrentSuggestions());

    }

    public List<Candidate> getCurrentSuggestions(){
        List<Candidate> candidates = new ArrayList<>();
        for(String string : suggestions){
            candidates.add(new Candidate(string));
        }
        return candidates;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

}
