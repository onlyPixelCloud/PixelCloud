package de.haizon.pixelcloud.api.logger;

/**
 * JavaDoc this file!
 * Created: 02.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public interface ICloudLogger {

    void info(String message);

    void severe(String message);

    void warning(String message);

    void success(String message);

}
