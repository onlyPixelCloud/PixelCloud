package de.haizon.pixelcloud.master.backend.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * JavaDoc this file!
 * Created: 04.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class PixelModuleDescription {

    private final String name, main;
    public static final List<PixelModuleDescription> descriptions = new ArrayList<>();

    public PixelModuleDescription(Object name, Object main) {
        this.name = String.valueOf(name);
        this.main = String.valueOf(main);

        descriptions.add(this);
    }

    public String getName() {
        return name;
    }

    public String getMain() {
        return main;
    }

}
