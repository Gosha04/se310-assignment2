package com.se310.store.model;

import java.util.ArrayList;
import java.util.List;

import com.se310.store.observer.AlertMonitor;
import com.se310.store.observer.DeviceStatistics;
import com.se310.store.observer.EventLogger;
import com.se310.store.observer.Observer;
import com.se310.store.observer.StoreNotifier;

/**
     * Abstract class representing Device in the Store
     * Implements Observable to allow observers to monitor device state changes
     *
     * @author  Sergey L. Sundukovskiy
     * @version 1.0
     * @since   2025-09-25
     */
    public abstract class Device {
        private StoreNotifier storeNotifier = StoreNotifier.getInstance();
        //TODO: Implement Observable interface
        //TODO: Implement Observer storage
        //TODO: Implement Observer registration
        //TODO: Implement Observer removal
        //TODO: Implement Observer notification

        private String id;
        private String name;
        private StoreLocation storeLocation;
        private String type;
        private final List<Observer> observers;

        /**
         * Constructor for the Device class
         * @param id
         * @param name
         * @param storeLocation
         * @param type
         */
        public Device(String id, String name, StoreLocation storeLocation, String type) {
            this.id = id;
            this.name = name;
            this.storeLocation = storeLocation;
            this.type = type;
            this.observers = new ArrayList<>();
            storeNotifier.attach(AlertMonitor.getInstance(), observers);
            storeNotifier.attach(EventLogger.getInstance(), observers);
            storeNotifier.attach(new DeviceStatistics(), observers);
            
        }

        public void addObserver (Observer observer) {
            storeNotifier.attach(observer, observers);
            observers.add(observer);
        }

        public void killAllObs (String type) {
            storeNotifier.killAllObs(type, observers);
            observers.removeAll(observers);
        }

        public void removeObserver (Observer observer) {
            storeNotifier.detach(observer, observers);
            observers.remove(observer);
        }

        public List<Observer> getObservers() {
            return observers;
        }

        /**
         * Getter method for the Device id
         * @return
         */
        public String getId() {
            return id;
        }

        /**
         * Setter method for the Device id
         * @param id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Getter method for the Device name
         * @return
         */
        public String getName() {
            return name;
        }

        /**
         * Setter method for the Device name
         * @param name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Getter method for the Store location
         * @return
         */
        public StoreLocation getStoreLocation() {
            return storeLocation;
        }

        /**
         * Setter method for the Store location
         * @param storeLocation
         */
        public void setStoreLocation(StoreLocation storeLocation) {
            this.storeLocation = storeLocation;
        }

        /**
         * Getter method for the Device type
         * @return
         */
        public String getType() {
            return type;
        }

        /**
         * Setter method for the Device type
         * @param type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * Abstract method that gets implemented by Sensor or Appliance devices
         * @param event
         */
        public abstract void processEvent(String event);

        @Override
        public String toString() {
            return "Device{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", storeLocation=" + storeLocation +
                    ", type='" + type + '\'' +
                    '}';
        }

        protected void emitEvent(String msg) {
            storeNotifier.publishEvent(msg, observers);
        }

        protected void emitAlert(String msg) {
            storeNotifier.publishAlert(msg, observers);
        }

        // private String enrich(String raw) {
        //     return String.format("device=%s name=%s type=%s loc=%s :: %s",
        //         getId(), getName(), getType(), getStoreLocation(), raw);
        // }
    }
