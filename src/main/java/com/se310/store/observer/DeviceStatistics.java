package com.se310.store.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Concrete Observer that tracks statistics about device events
 *
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2025-09-25
 */
public class DeviceStatistics {
    private static final Logger loggy = Logger.getLogger(AlertMonitor.class.getName());
    //TODO: Implement Device Statistics stores and prints out event and command counts

    public class DeviceStatObservable implements Observable{
        protected final List<Observer> observers = new ArrayList<>();
        protected String lastEventType;

        @Override
        public void attach(Observer observer) {
            Objects.requireNonNull(observer, "device stat tracker");
                if (!observers.contains(observer)) {
                    observers.add(observer); 
                    loggy.info(() -> "device stat tracker attached: " + observer.getClass().getSimpleName()
                        + " (Total Device Stat Trackers: " + observers.size() + ")");
                } else {
                    loggy.warning(() -> "Device Stat Tracker already attached: " + observer.getClass().getSimpleName());
                }
        }
        
        @Override
        public void notifyObs() {
            loggy.info(() -> String.format("Notifying %d device stat tracker(s) of event: %s",
            observers.size(), lastEventType));
            for (Observer obs : observers) {
                try {
                obs.onStoreUpdate(lastEventType);
                } catch (Exception e) {
                    loggy.severe("Error notifying device stat tracker " + obs.getClass().getSimpleName()
                    + ": " + e.getMessage());
                }
            }
        }

        @Override
        public void detach(Observer observer) {
            if (observers.remove(observer)) {
                loggy.info(() -> "Device Stat Tracker detached: " + observer.getClass().getSimpleName()
                    + " (Total Device Stat Tracker: " + observers.size() + ")");
            } else {
                loggy.warning(() -> "Attempted to detach unregistered device stat tracker: "
                    + observer.getClass().getSimpleName());
            }
        }

        public void publish (String eventType) {
            this.lastEventType = eventType;
            notifyObs();
        }

        public int getObsCount() {
            return observers.size();
        }

        public void killAllObs() {
            observers.forEach(this::detach);
            System.out.println("All device stat trackers killed");
        }
    }
    
    public class DeviceStatObserver implements Observer {
        protected String deviceName;

        public synchronized void setDeviceName(String name) {
            if (this.deviceName != null) {
                throw new IllegalStateException("deviceName already set");
            }
            this.deviceName = Objects.requireNonNull(name).trim();
        }

        @Override
        public void onStoreUpdate(String eventType) {
            System.out.printf("[%s Count] " + eventType, deviceName);
        }
    }
}