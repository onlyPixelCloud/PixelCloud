package de.haizon.pixelcloud.api.event;

import java.util.List;

/**
 * JavaDoc this file!
 * Created: 04.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public interface IEventHandler {


    /**
     * Register a event
     * @param event
     */
    void registerEvent(IEvent event);

    /**
     * Call a event
     * @param event
     */
    void call(IEvent event);

    /**
     * Get all events
     * @return
     */
    List<IEvent> getEvents();

}
