package com.se310.store.proxy;

import com.se310.store.model.Aisle;
import com.se310.store.model.AisleLocation;
import com.se310.store.model.Basket;
import com.se310.store.model.Customer;
import com.se310.store.model.Device;
import com.se310.store.model.Inventory;
import com.se310.store.model.Store;
import com.se310.store.model.StoreException;

/**
 * Proxy Pattern implementation for the StoreService
 * The Proxy Pattern provides a surrogate or placeholder for another object to control access to it
 * This implementation adds access control before delegating to the real StoreService
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2025-09-25
 */
public class StoreServiceProxy {

    //TODO: Implement Proxy Pattern allowing command execution only with a valid token
    public interface StoreService {
        // Aisles
        Aisle addAisle(Store store, String aisleNumber, String name, String description, AisleLocation loc)
        throws StoreException;
        
        Aisle getAisle(Store store, String aisleNumber) throws StoreException;

        // Inventory
        void addInventory(Store store, Inventory inventory) throws StoreException;

        // Customers
        void addCustomer(Store store, Customer customer) throws StoreException;
        Customer getCustomer(Store store, String customerId) throws StoreException;
        void removeCustomer(Store store, String customerId) throws StoreException;

        // Devices
        void addDevice(Store store, Device device) throws StoreException;

        // Baskets
        void addBasket(Store store, Basket basket) throws StoreException;   
    }

    public static class RealStoreService implements StoreService {
        @Override
        public Aisle addAisle(Store store, String aisleNumber, String name, String description, AisleLocation loc)
            throws StoreException {
        // Delegate to the domain object so duplicate checks & invariants run
        return store.addAisle(aisleNumber, name, description, loc);
        }

        @Override
        public Aisle getAisle(Store store, String aisleNumber) throws StoreException {
        return store.getAisle(aisleNumber);
        }

        @Override
        public void addInventory(Store store, Inventory inventory) throws StoreException {
        store.addInventory(inventory);
        }

        @Override
        public void addCustomer(Store store, Customer customer) throws StoreException {
        store.addCustomer(customer);
        }

        @Override
        public Customer getCustomer(Store store, String customerId) {
        return store.getCustomer(customerId);
        }

        @Override
        public void removeCustomer(Store store, String customerId) {
        Customer c = store.getCustomer(customerId);
        if (c != null) store.removeCustomer(c);
        }

        @Override
        public void addDevice(Store store, Device device) throws StoreException {
        store.addDevice(device);
        }

        @Override
        public void addBasket(Store store, Basket basket) throws StoreException {
        store.addBasket(basket);
        }
    }
    
}
