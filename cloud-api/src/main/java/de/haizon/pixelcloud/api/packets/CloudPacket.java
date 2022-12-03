package de.haizon.pixelcloud.api.packets;

/**
 * JavaDoc this file!
 * Created: 03.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudPacket<T> {

    private final String id;
    private final T content;

    public CloudPacket(String id, T content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public T getContent() {
        return content;
    }
}
