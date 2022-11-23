package de.haizon.pixelcloud.api.console;

/**
 * JavaDoc this file!
 * Created: 02.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public enum LogType {

    DEBUG("DEBUG", Color.BLUE),
    CONSOLE("INFO", Color.MAGENTA),
    ERROR("ERROR", Color.RED),
    WARNING("WARN", Color.YELLOW),
    SETUP("SETUP", Color.CYAN),
    SUCCESS("SUCCESS", Color.GREEN);

    private String display;
    private Color colors;

    LogType(String display, Color colors) {
        this.display = display;
        this.colors = colors;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Color getColor() {
        return colors;
    }

    public void setColors(Color colors) {
        this.colors = colors;
    }

}
