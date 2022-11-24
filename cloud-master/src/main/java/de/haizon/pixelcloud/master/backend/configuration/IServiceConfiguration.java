package de.haizon.pixelcloud.master.backend.configuration;

import de.haizon.pixelcloud.api.services.ICloudService;

import java.io.File;

/**
 * JavaDoc this file!
 * Created: 23.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public interface IServiceConfiguration {

    /**
     * Configure the cloud service files
     * @param cloudService
     * @param file
     */
    void configure(ICloudService cloudService, File file);

}
