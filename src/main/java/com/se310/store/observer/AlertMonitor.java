package com.se310.store.observer;

/**
 * Concrete Observer that monitors for critical events and alerts
 *
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2025-09-25
 */
public class AlertMonitor implements Observer {
    @Override
    public void onStoreUpdate(String eventType) {
        System.out.println("[CRITICAL] " + eventType);
    }
}