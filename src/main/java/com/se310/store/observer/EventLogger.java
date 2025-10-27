package com.se310.store.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Concrete Observer that logs device events to console
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2025-09-25
 */
public class EventLogger {
    //TODO: Implement Event Logger to log device events to console
    private static final Logger loggy = Logger.getLogger(AlertMonitor.class.getName());
        public class EventObservable implements Observable{
            protected final List<Observer> observers = new ArrayList<>();
            protected String lastEventType;

            @Override
            public void attach(Observer observer) {
                Objects.requireNonNull(observer, "event logger");
                if (!observers.contains(observer)) {
                    observers.add(observer); 
                    loggy.info(() -> "Event Logger attached: " + observer.getClass().getSimpleName()
                        + " (Total event loggers: " + observers.size() + ")");
                } else {
                    loggy.warning(() -> "Event Logger already attached: " + observer.getClass().getSimpleName());
                }
            }

            @Override
            public void notifyObs() {
                loggy.info(() -> String.format("Notifying %d event logger(s) of event: %s",
                    observers.size(), lastEventType));
                for (Observer obs : observers) {
                    try {
                        obs.onStoreUpdate(lastEventType);
                    } catch (Exception e) {
                        loggy.severe("Error notifying event loggers " + obs.getClass().getSimpleName()
                            + ": " + e.getMessage());
                    }
                }
            }

            public int getObsCount() {
                return observers.size();
            }

            @Override
            public void detach(Observer observer) {
                if (observers.remove(observer)) {
                    loggy.info(() -> "Event Logger detached: " + observer.getClass().getSimpleName()
                                + " (Total Event Loggers: " + observers.size() + ")");
                } else {
                    loggy.warning(() -> "Attempted to detach unregistered event logger: "
                                    + observer.getClass().getSimpleName());
                }
            }

            public void killAllObs() {
                observers.forEach(this::detach);
                loggy.info("All event loggers killed.");
            }

            public void publish(String eventType) {
                this.lastEventType = eventType;
                notifyObs();
            }
        }

        public class AlertObserver implements Observer {
        @Override
            public void onStoreUpdate(String eventType) {
                System.out.println("[Event] " + eventType);
            }
        }
    }