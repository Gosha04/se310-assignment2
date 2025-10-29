package com.se310.store.strategy;

import com.se310.store.model.StoreException;

/**
 * Standard implementation of the Inventory
 * This strategy performs a standard inventory update, checking bounds before updating
 * Part of the Strategy Pattern implementation
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2025-09-25
 */
public class StandardInventoryUpdateStrategy implements InventoryUpdateStrategy{

    //TODO: Implement Strategy Pattern making sure inventory stays in the acceptable bounds for standard products

    @Override
    public int applyUpdate(int capacity, int currentCount, int delta) throws StoreException {
        long proposed = (long) currentCount + delta;
        if (proposed < 0 || proposed > capacity) {
            throw new StoreException("Update Inventory", "Count must be between 0 and capacity (" + capacity + ")");
        }
        return (int) proposed;
    }

}