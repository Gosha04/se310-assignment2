package com.se310.store.proxy;

import com.se310.store.model.Aisle;
import com.se310.store.model.AisleLocation;
import com.se310.store.model.Basket;
import com.se310.store.model.Customer;
import com.se310.store.model.CustomerType;
import com.se310.store.model.Device;
import com.se310.store.model.Inventory;
import com.se310.store.model.InventoryType;
import com.se310.store.model.Product;
import com.se310.store.model.Shelf;
import com.se310.store.model.ShelfLevel;
import com.se310.store.model.Store;
import com.se310.store.model.StoreException;
import com.se310.store.model.Temperature;
import com.se310.store.singleton.StoreService;
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

    private final StoreService realService = StoreService.getInstance();

    private void validateToken(String token) throws StoreException {
        if (token == null || !token.equals("admin")) {
            throw new StoreException("Access Denied", "Invalid or missing authentication token");
        }
    }
        // Store operations
    public Store provisionStore(String storeId, String name, String address, String token) throws StoreException {
        validateToken(token);
        return realService.provisionStore(storeId, name, address, token);
    }

    public Store showStore(String storeId, String token) throws StoreException {
        validateToken(token);
        return realService.showStore(storeId, token);
    }

    // Aisle operations
    public Aisle provisionAisle(String storeId, String aisleNumber, String name,
                                String description, AisleLocation location, String token) throws StoreException {
        validateToken(token);
        return realService.provisionAisle(storeId, aisleNumber, name, description, location, token);
    }

    public Aisle showAisle(String storeId, String aisleNumber, String token) throws StoreException {
        validateToken(token);
        return realService.showAisle(storeId, aisleNumber, token);
    }

    // Shelf operations
    public Shelf provisionShelf(String storeId, String aisleNumber, String shelfId, String name,
                                ShelfLevel level, String description, Temperature temperature, String token)
            throws StoreException {
        validateToken(token);
        return realService.provisionShelf(storeId, aisleNumber, shelfId, name, level, description, temperature, token);
    }

    public Shelf showShelf(String storeId, String aisleNumber, String shelfId, String token) throws StoreException {
        validateToken(token);
        return realService.showShelf(storeId, aisleNumber, shelfId, token);
    }

    // Inventory operations
    public Inventory provisionInventory(String inventoryId, String storeId, String aisle, String shelfId,
                                        int capacity, int count, String productId, InventoryType type, String token)
            throws StoreException {
        validateToken(token);
        return realService.provisionInventory(inventoryId, storeId, aisle, shelfId, capacity, count, productId, type, token);
    }

    public Inventory showInventory(String inventoryId, String token) throws StoreException {
        validateToken(token);
        return realService.showInventory(inventoryId, token);
    }

    public Inventory updateInventory(String inventoryId, int count, String token) throws StoreException {
        validateToken(token);
        return realService.updateInventory(inventoryId, count, token);
    }

    // Product operations
    public Product provisionProduct(String id, String name, String description, String size,
                                    String category, double price, Temperature temperature, String token)
            throws StoreException {
        validateToken(token);
        return realService.provisionProduct(id, name, description, size, category, price, temperature, token);
    }

    public Product showProduct(String productId, String token) throws StoreException {
        validateToken(token);
        return realService.showProduct(productId, token);
    }

    // Customer operations
    public Customer provisionCustomer(String id, String firstName, String lastName, CustomerType type,
                                      String email, String accountAddress, String token) throws StoreException {
        validateToken(token);
        return realService.provisionCustomer(id, firstName, lastName, type, email, accountAddress, token);
    }

    public Customer showCustomer(String id, String token) throws StoreException {
        validateToken(token);
        return realService.showCustomer(id, token);
    }

    // Basket operations
    public Basket getCustomerBasket(String customerId, String token) throws StoreException {
            validateToken(token);
            return realService.getCustomerBasket(customerId, token);
        }

        public Basket provisionBasket(String basketId, String token) throws StoreException {
            validateToken(token);
            return realService.provisionBasket(basketId, token);
        }

        public Basket assignCustomerBasket(String customerId, String basketId, String token) throws StoreException {
            validateToken(token);
            return realService.assignCustomerBasket(customerId, basketId, token);
        }

        public Basket addBasketProduct(String basketId, String productId, int count, String token) throws StoreException {
            validateToken(token);
            return realService.addBasketProduct(basketId, productId, count, token);
        }

        public Basket removeBasketProduct(String basketId, String productId, int count, String token) throws StoreException {
            validateToken(token);
            return realService.removeBasketProduct(basketId, productId, count, token);
        }

        public Basket clearBasket(String basketId, String token) throws StoreException {
            validateToken(token);
            return realService.clearBasket(basketId, token);
        }

        public Basket showBasket(String basketId, String token) throws StoreException {
            validateToken(token);
            return realService.showBasket(basketId, token);
        }

    // Device operations
    public Device provisionDevice(String deviceId, String name, String deviceType,
                                  String storeId, String aisleNumber, String token) throws StoreException {
        validateToken(token);
        return realService.provisionDevice(deviceId, name, deviceType, storeId, aisleNumber, token);
    }

    public Device showDevice(String deviceId, String token) throws StoreException {
        validateToken(token);
        return realService.showDevice(deviceId, token);
    }

    // Events / Commands
    public void raiseEvent(String deviceId, String event, String token) throws StoreException {
        validateToken(token);
        realService.raiseEvent(deviceId, event, token);
    }

    public void issueCommand(String deviceId, String command, String token) throws StoreException {
        validateToken(token);
        realService.issueCommand(deviceId, command, token);
    }
    
}
