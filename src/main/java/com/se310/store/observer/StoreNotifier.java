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

    protected final List<Observer> observers = new ArrayList<>();

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
    public void attach(Observer observer) {
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
    public void detach(Observer observer) {
        if (observers.remove(observer)) {
            LOGGER.info(() -> "Observer detached: " + observer.getClass().getSimpleName()
             + " (Total observers: " + observers.size() + ")");
        } else {
            LOGGER.warning(() -> "Attempted to detach unregistered observer: "
             + observer.getClass().getSimpleName());
            }
        }

    @Override
    public void notifyObs() {
        LOGGER.info(() -> String.format("Notifying %d observer(s) of event: %s",
         observers.size(), lastEventType));
        for (Observer obs : observers) {
            try {
                obs.onStoreUpdate(lastEventType);
            } catch (Exception e) {
                LOGGER.severe("Error notifying observers " + obs.getClass().getSimpleName()
                 + ": " + e.getMessage());
            }
        }
    }

    public void killAllObs(String type) {
        List<Observer> targets = new ArrayList<>();
        switch (type) {
            case "all":
                observers.forEach(this::detach);
                LOGGER.info("All observers killed.");
                break;
            case "eventLog":
                for (Observer o : observers) {
                    if (o instanceof EventLogger) {
                        targets.add(o);
                    }
                }
                targets.forEach(this::detach);
                LOGGER.info(() -> "EventLogger observers killed: " + targets.size());
            case "alertMonitor":
                for (Observer o : observers) {
                    if (o instanceof AlertMonitor) {
                        targets.add(o);
                    }
                }
                targets.forEach(this::detach);
                LOGGER.info(() -> "AlertMonitor observers killed: " + targets.size());
            case "deviceStat":
                for (Observer o : observers) {
                    if (o instanceof DeviceStatistics) {
                        targets.add(o);
                    }
                }
                targets.forEach(this::detach);
                LOGGER.info(() -> "AlertMonitor observers killed: " + targets.size());
            default:
                LOGGER.warning(() -> "Unknown killAllObs type: " + type);
        }
    }
    
    public void publish(String eventType) {
        this.lastEventType = eventType;
        notifyObs();
    }

    public int getObsCount() {
        return observers.size();
    }

    public EventLogger createEventLogger() {
        return new EventLogger();
    }
    
}