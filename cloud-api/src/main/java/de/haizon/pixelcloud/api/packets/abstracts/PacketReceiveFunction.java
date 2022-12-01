package de.haizon.pixelcloud.api.packets.abstracts;

import de.haizon.pixelcloud.api.packets.Packet;

/**
 * JavaDoc this file!
 * Created: 24.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public abstract class PacketReceiveFunction {

    private final String id;

    public PacketReceiveFunction(String id) {
        this.id = id;
    }

    public abstract void received(Packet packet);

    public String getId() {
        return id;
    }
}
