package com.se310.store.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import com.se310.store.model.Store;
import com.se310.store.observer.ObserverFull.Observer;

/**
 * Observable interface for the Observer Pattern
 * Devices implement this interface to allow observers to subscribe to their state changes
 *
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2025-09-25
 */
public class ObservableFull {
    public interface Observable {

        //TODO: Define Observer registration
        //TODO: Define Observer removal
        //TODO: Define Observer notification
        void detach(Observer observer);
        void notifyObs();
        void attach(Observer observer);
    }

    public abstract static class StoreObservable implements Observable {

        protected final List<Observer> observers = new ArrayList<>();
        protected Store lastAffectedStore;
        protected String lastEventType;

        @Override
        public void attach(Observer observer) {
            Objects.requireNonNull(observer);

            // Credit: Prof Sundukovskiy attach function
            if (!observers.contains(observer)) {
                Logger.getLogger(getClass().getName()).info(
                        "Observer attached: " + observer.getClass().getSimpleName() +
                                " (Total observers: " + observers.size() + ")"
                );
            } else {
                Logger.getLogger(getClass().getName()).warning(
                        "Observer already attached: " + observer.getClass().getSimpleName()
                );
            }
        }

        @Override
        public void notifyObs() {
            // Credit: Prof Sundukovskiy, was edited to work with this class, not sure how else to do
            Logger.getLogger(getClass().getName()).info(
                String.format("Notifying %d observer(s) of event: %s", observers.size(), lastEventType)
            );

            for (Observer obs : observers) {
                try {
                    obs.onStoreUpdate(lastAffectedStore, lastEventType);
                } catch (Exception e) {
                    Logger.getLogger(getClass().getName()).severe(
                        "Error notifying observer " + obs.getClass().getSimpleName() + ": " + e.getMessage()
                    );
                }
            }
        }

         public int getObsCount() {
            return observers.size();
        }

        @Override
        public void detach(Observer observer) {
            // Credit: Prof Sundukovskiy attach function
            if (observers.remove(observer)) {
                Logger.getLogger(getClass().getName()).info(
                        "Observer detached: " + observer.getClass().getSimpleName() +
                                " (Total observers: " + observers.size() + ")"
                );
            } else {
                Logger.getLogger(getClass().getName()).warning(
                        "Attempted to detach unregistered observer: " +
                                observer.getClass().getSimpleName()
                );
            }
        }

        public void killAllObs() {
            observers.forEach(this::detach);
        }
    }
}