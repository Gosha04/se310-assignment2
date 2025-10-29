package com.se310.store.observer;

import java.util.List;

/**
 * Observable interface for the Observer Pattern
 * Devices implement this interface to allow observers to subscribe to their state changes
 *
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2025-09-25
 */
public interface Observable {
    //TODO: Define Observer registration
    //TODO: Define Observer removal
    //TODO: Define Observer notification
    void detach(Observer observer, List<Observer> observers);
    void notifyObs(boolean isAlert, List<Observer> observers);
    void attach(Observer observer, List<Observer> observers);
}