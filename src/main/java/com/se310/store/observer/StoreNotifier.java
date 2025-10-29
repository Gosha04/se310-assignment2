package com.se310.store.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Concrete Observer that sends notifications to store management.
 * Implements Singleton pattern to ensure single notification instance per store.
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2025-09-25
 */
public class StoreNotifier implements Observable{

    //TODO: Implement Store Notifier that prints out device events to console
    public static StoreNotifier instance;
    private StoreNotifier () {};

    private static final Logger LOGGER = Logger.getLogger(StoreNotifier.class.getName());
    private String lastEventType;

    // protected final List<Observer> observers = Device.getObservers();

    public static StoreNotifier getInstance() {
        if (instance == null) {
            synchronized (StoreNotifier.class) {
                if (instance == null) {
                    instance = new StoreNotifier();
                }
            }
        }
        return instance;
    }

    @Override
    public void attach(Observer observer, List<Observer> observers) {
        Objects.requireNonNull(observer, "observer");
        if (!observers.contains(observer)) {
            observers.add(observer);
            LOGGER.info(() -> "Observer attached: " + observer.getClass().getSimpleName()
                    + " (Total observers: " + observers.size() + ")");
        } else {
            LOGGER.warning(() -> "Observer already attached: " + observer.getClass().getSimpleName());
        }
    }

    @Override
    public void detach(Observer observer, List<Observer> observers) {
        if (observers.remove(observer)) {
            LOGGER.info(() -> "Observer detached: " + observer.getClass().getSimpleName()
             + " (Total observers: " + observers.size() + ")");
        } else {
            LOGGER.warning(() -> "Attempted to detach unregistered observer: "
             + observer.getClass().getSimpleName());
            }
        }

    @Override
    public void notifyObs(boolean isAlert, List<Observer> observers) {
        LOGGER.info(() -> String.format("Notifying %d observer(s) of event: %s",
         observers.size(), lastEventType));
        
        if (isAlert) {
            for (Observer obs : observers) {
                if (!(obs instanceof DeviceStatistics)) {
                    obs.onStoreUpdate(lastEventType);
                }
            }
        }
        for (Observer obs : observers) {
            if (!(obs instanceof AlertMonitor)) {
                obs.onStoreUpdate(lastEventType);
                System.out.println("[StoreNotifier] " + lastEventType);
            }
        }
    }

    public void killAllObs(String type, List<Observer> observers) {
        List<Observer> targets = new ArrayList<>();
        switch (type) {
            case "all":
                observers.forEach(o -> this.detach(o, observers));
                LOGGER.info("All observers killed.");
                break;
            case "eventLog":
                for (Observer o : observers) {
                    if (o instanceof EventLogger) {
                        targets.add(o);
                    }
                }
                observers.forEach(o -> this.detach(o, observers));
                LOGGER.info(() -> "EventLogger observers killed: " + targets.size());
            case "alertMonitor":
                for (Observer o : observers) {
                    if (o instanceof AlertMonitor) {
                        targets.add(o);
                    }
                }
                observers.forEach(o -> this.detach(o, observers));
                LOGGER.info(() -> "AlertMonitor observers killed: " + targets.size());
            case "deviceStat":
                for (Observer o : observers) {
                    if (o instanceof DeviceStatistics) {
                        targets.add(o);
                    }
                }
                observers.forEach(o -> this.detach(o, observers));
                LOGGER.info(() -> "AlertMonitor observers killed: " + targets.size());
            default:
                LOGGER.warning(() -> "Unknown killAllObs type: " + type);
        }
    }
    
    public void publishEvent(String event, List<Observer> observers) {
        this.lastEventType = event;
        notifyObs(false, observers);
    }

    public void publishAlert(String event, List<Observer> observers) {
        this.lastEventType = event;
        notifyObs(true, observers);
    }
}