package com.jiss.app.event;

public interface EventListener<T extends Event> {
    void onEvent(T event);
}
