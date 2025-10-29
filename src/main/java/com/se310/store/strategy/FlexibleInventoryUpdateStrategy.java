package com.se310.store.strategy;

import com.se310.store.model.StoreException;

/**
 * Flexible implementation of the Inventory
 * This strategy allows temporary over-capacity for inventory updates
 * Part of the Strategy Pattern implementation
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2025-09-25
 */
public class FlexibleInventoryUpdateStrategy implements InventoryUpdateStrategy{

    //TODO: Implement Strategy Pattern allowing 20% overcapacity for flexible inventory
    
    @Override
    public int applyUpdate(int capacity, int currentCount, int delta) throws StoreException {
        long proposed = (long) currentCount + delta;
        int max = (int) Math.floor(capacity * 1.2);
        if (proposed < 0 || proposed > max) {
            throw new StoreException("Update Inventory", "Count must be between 0 and " + max + " (20% over capacity allowed)");
        }
        return (int) proposed;
    }
}