package com.se310.store.observer;

/**
 * Concrete Observer that tracks statistics about device events
 *
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2025-09-25
 */
public class DeviceStatistics implements Observer {
    //TODO: Implement Device Statistics stores and prints out event and command counts
    private String deviceName;

    public void setDeviceName(String devName) {
        this.deviceName = devName;
    }

    @Override
    public void onStoreUpdate(String eventType) {
        System.out.printf("[%s Count] " + eventType, deviceName);
    }
}