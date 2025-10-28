package com.se310.store.strategy;

import com.se310.store.model.StoreException;

public interface InventoryUpdateStrategy {
    
    int applyUpdate(int capacity, int currentCount, int delta) throws StoreException;
    
}
