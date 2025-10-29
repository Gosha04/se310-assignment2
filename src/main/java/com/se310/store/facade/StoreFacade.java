package com.se310.store.facade;

import com.se310.store.factory.CustomerFactory;
import com.se310.store.factory.ProductFactory;
import com.se310.store.factory.ProductFactory.ProductType;
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

    public Product defineProduct(String typeStr, String id, String name, String description, String size,
                             String category, double basePrice, Temperature temperature, String token)
        throws StoreException {
    
    ProductFactory.ProductType type;
    try {
        type = ProductFactory.ProductType.valueOf(typeStr.toUpperCase());
    } catch (IllegalArgumentException e) {
        throw new StoreException("Define Product", "Invalid product type: " + typeStr);
    }

   
    Product product = ProductFactory.createProduct(type, id, name, description, size, category, basePrice, temperature);

    
    return service.provisionProduct(product.getId(), product.getName(), product.getDescription(),
            product.getSize(), product.getCategory(), product.getPrice(), product.getTemperature(), token);
    }

    public Customer defineCustomer(String id, String first, String last, String typeStr,
                               String email, String account, String token) throws StoreException {
    
    CustomerType type;
    try {
        type = CustomerType.valueOf(typeStr.toLowerCase());
    } catch (IllegalArgumentException e) {
        throw new StoreException("Define Customer", "Invalid customer type: " + typeStr);
    }

    
    Customer c = CustomerFactory.createCustomer(id, first, last, type, email, account);

    
    return service.provisionCustomer(
            c.getId(),
            c.getFirstName(),
            c.getLastName(),
            type,
            c.getEmail(),
            c.getAccountAddress(),
            token
    );
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
