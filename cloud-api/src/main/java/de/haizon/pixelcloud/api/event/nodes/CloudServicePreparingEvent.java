package de.haizon.pixelcloud.api.event.nodes;

import de.haizon.pixelcloud.api.event.IEvent;
import de.haizon.pixelcloud.api.services.ICloudService;

/**
 * JavaDoc this file!
 * Created: 04.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudServicePreparingEvent extends IEvent {

    private final ICloudService cloudService;

    public CloudServicePreparingEvent(ICloudService cloudService) {
        this.cloudService = cloudService;
    }

    public ICloudService getCloudService() {
        return cloudService;
    }

}
