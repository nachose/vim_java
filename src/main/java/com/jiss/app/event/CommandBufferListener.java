package com.jiss.app.event;


import com.google.common.eventbus.Subscribe;

public class CommandBufferListener {
    @Subscribe
    public void handleEnterCommandMode(EnterCommandModeEvent event) {
        // Clear the command buffer here
    }
}