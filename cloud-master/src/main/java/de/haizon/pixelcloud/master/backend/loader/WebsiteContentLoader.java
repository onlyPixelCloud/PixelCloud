package de.haizon.pixelcloud.master.backend.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class WebsiteContentLoader {

    public String loadContent(String urlString) throws IOException {
        var url = new URL(urlString);
        var reader = new BufferedReader(new InputStreamReader(url.openStream()));
        var line = new StringBuilder();
        reader.lines().forEach(line::append);
        reader.close();
        return line.toString();
    }

}
