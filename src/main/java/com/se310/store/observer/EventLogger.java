package com.se310.store.observer;

import java.util.logging.Logger;

/**
 * Concrete Observer that logs device events to console
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2025-09-25
 */
public class EventLogger implements Observer{
    //TODO: Implement Event Logger to log device events to console
    private static final Logger LOGGER = Logger.getLogger(EventLogger.class.getName());

    public static EventLogger instance;
    private EventLogger () {};
    
    @Override
    public void onStoreUpdate(String event) {
        LOGGER.info("[Event] " + event);
    }

    public static EventLogger getInstance() {
        if (instance == null) {
            synchronized (EventLogger.class) {
                if (instance == null) {
                    instance = new EventLogger();
                }
            }
        }
        return instance;
    }
}
