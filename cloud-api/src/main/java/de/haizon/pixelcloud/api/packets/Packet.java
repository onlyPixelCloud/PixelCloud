package de.haizon.pixelcloud.api.packets;

import de.haizon.pixelcloud.api.group.ICloudGroup;
import de.haizon.pixelcloud.api.services.ICloudService;

/**
 * JavaDoc this file!
 * Created: 24.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class Packet {

    public String id;
    public Object content;
    public ICloudGroup cloudGroup;
    public ICloudService cloudService;

}
