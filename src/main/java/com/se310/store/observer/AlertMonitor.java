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
    public static AlertMonitor instance;
    private AlertMonitor () {};

    @Override
    public void onStoreUpdate(String event) {
        System.out.println("[CRITICAL] " + event);
    }

    public static AlertMonitor getInstance() {
        if (instance == null) {
            synchronized (AlertMonitor.class) {
                if (instance == null) {
                    instance = new AlertMonitor();
                }
            }
        }
        return instance;
    }
}