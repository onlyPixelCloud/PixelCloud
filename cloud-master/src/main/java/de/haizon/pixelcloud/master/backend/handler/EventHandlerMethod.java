package de.haizon.pixelcloud.master.backend.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * JavaDoc this file!
 * Created: 04.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class EventHandlerMethod {

    private final Object listener;
    private final Method method;

    public EventHandlerMethod(Object listener, Method method) {
        this.listener = listener;
        this.method = method;
    }

    public void invoke(Object event) throws InvocationTargetException, IllegalAccessException {
        method.invoke(listener, event);
    }

}
