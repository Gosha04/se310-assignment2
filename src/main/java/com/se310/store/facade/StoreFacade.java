package com.se310.store.facade;

import com.se310.store.model.*;
import com.se310.store.proxy.StoreServiceProxy;

/**
 * Facade Pattern implementation for the store subsystem
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2025-09-25
 */
public class StoreFacade {

    //TODO: Implement Facade Pattern to hiding complexities of the store, product and customer creation
    private final StoreServiceProxy service = new StoreServiceProxy();
    
    public Store defineStore(String storeId, String name, String address, String token) throws StoreException {
        return service.provisionStore(storeId, name, address, token);
    }

    public Customer defineCustomer(String id, String first, String last, CustomerType type,
                                   String email, String account, String token) throws StoreException {
        // Delegate to proxy for provisioning (includes token validation)
        return service.provisionCustomer(id, first, last, type, email, account, token);
    }

    public Product defineProduct (String storeId, String name, String desc, String size, String category, Double price,
     Temperature temp, String token) throws StoreException{
        return service.provisionProduct(storeId, name, desc, size, category, price, temp, token);
    }

    public Aisle defineAisle(String storeId, String aisleNumber, String name, String description,
                             AisleLocation location, String token) throws StoreException {
        return service.provisionAisle(storeId, aisleNumber, name, description, location, token);
    }

    public Shelf defineShelf(String storeId, String aisleNumber, String shelfId, String name, ShelfLevel level,
                             String description, Temperature temperature, String token) throws StoreException {
        return service.provisionShelf(storeId, aisleNumber, shelfId, name, level, description, temperature, token);
    }

    public Inventory defineInventory(String inventoryId, String storeId, String aisle, String shelfId,
                                     int capacity, int count, String productId, InventoryType type, String token)
            throws StoreException {
        return service.provisionInventory(inventoryId, storeId, aisle, shelfId, capacity, count, productId, type, token);
    }

    public Basket ensureCustomerBasket(String customerId, String basketId, String token) throws StoreException {
        try {
            return service.getCustomerBasket(customerId, token);
        } catch (StoreException e) {
            Basket b = service.provisionBasket(basketId, token);
            return service.assignCustomerBasket(customerId, b.getId(), token);
        }
    }

    public Basket addItemToCustomerBasket(String customerId, String basketId, String productId, int count, String token)
            throws StoreException {
        Basket b = ensureCustomerBasket(customerId, basketId, token);
        return service.addBasketProduct(b.getId(), productId, count, token);
    }

}
