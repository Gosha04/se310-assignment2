package com.se310.store.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Concrete Observer that monitors for critical events and alerts
 *
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2025-09-25
 */
public class AlertMonitor {
    private static final Logger loggy = Logger.getLogger(AlertMonitor.class.getName());
    //TODO: Implement Alert Monitor that prints out critical events to the console
    public class AlertObservable implements Observable{
        protected final List<Observer> observers = new ArrayList<>();
        protected String lastEventType;

        @Override
        public void attach(Observer observer) {
            Objects.requireNonNull(observer, "alert monitor");
            if (!observers.contains(observer)) {
                observers.add(observer); 
                loggy.info(() -> "Alert Monitor attached: " + observer.getClass().getSimpleName()
                    + " (Total Alert Monitors: " + observers.size() + ")");
            } else {
                loggy.warning(() -> "Alert Monitor already attached: " + observer.getClass().getSimpleName());
            }
        }

        @Override
        public void notifyObs() {
            loggy.info(() -> String.format("Notifying %d alert monitor(s) of event: %s",
                observers.size(), lastEventType));
            for (Observer obs : observers) {
                try {
                    obs.onStoreUpdate(lastEventType);
                } catch (Exception e) {
                    loggy.severe("Error notifying alert monitor " + obs.getClass().getSimpleName()
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
                loggy.info(() -> "Alert Monitor detached: " + observer.getClass().getSimpleName()
                              + " (Total Alert Monitors: " + observers.size() + ")");
            } else {
                loggy.warning(() -> "Attempted to detach unregistered alert monitor: "
                                  + observer.getClass().getSimpleName());
            }
        }

        public void killAllObs() {
            observers.forEach(this::detach);
            loggy.info("All alert monitors killed.");
        }

        public void publish(String eventType) {
            this.lastEventType = eventType;
            notifyObs();
        }
    }

    public class AlertObserver implements Observer {
       @Override
        public void onStoreUpdate(String eventType) {
            System.out.println("[CRITICAL] " + eventType);
        }
    }
}