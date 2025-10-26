package com.se310.store.observer;

import java.util.Observer;

import com.se310.store.model.Store;

/**
 * Observer interface for the Observer Pattern
 * Observers are notified when Devices (Observables) change state
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2025-09-25
 */

public class ObserverFull {

    public interface Observer {
        //TODO: Define Observed device changes state
        void onStoreUpdate(Store store, String eventType);
    }

    public abstract static class StoreObserver implements Observer{
        private Store store;
        private String eventString;

        public StoreObserver() {
        }    

        @Override 
        public void onStoreUpdate(Store store, String eventString) {
            this.store = store;
            this.eventString = eventString;
        }
    }
}