package de.haizon.pixelcloud.master.backend.handler;

import de.haizon.pixelcloud.api.event.IEvent;
import de.haizon.pixelcloud.api.event.IEventHandler;
import de.haizon.pixelcloud.master.CloudMaster;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaDoc this file!
 * Created: 04.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class EventHandler implements IEventHandler {

    private final List<IEvent> events;

    public EventHandler() {
        this.events = new ArrayList<>();
    }

    @Override
    public void registerEvent(IEvent event) {
        this.events.add(event);
    }

    @Override
    public void call(IEvent event) {
        CloudMaster.getInstance().callEvent(event);
    }

    @Override
    public List<IEvent> getEvents() {
        return this.events;
    }
}
