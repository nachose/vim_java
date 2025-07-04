package com.jiss.app.event;

// EventBus.java
import java.util.*;

public class EventBus {
    private final Map<Class<? extends Event>, List<EventListener<? extends Event>>> listeners = new HashMap<>();

    public <T extends Event> void register(Class<T> eventType, EventListener<T> listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public <T extends Event> void post(T event) {
        List<EventListener<? extends Event>> eventListeners = listeners.get(event.getClass());
        if (eventListeners != null) {
            for (EventListener listener : eventListeners) {
                ((EventListener<T>) listener).onEvent(event);
            }
        }
    }
}
