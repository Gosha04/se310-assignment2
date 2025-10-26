package com.se310.store.factory;

import java.util.Date;

import com.se310.store.model.Customer;
import com.se310.store.model.CustomerType;

/**
 * Factory Pattern implementation for creating Customer objects
 *
 * @author  Joshua Vaysman
 * @version 1.0
 * @since   2025-10-22
 */
public class CustomerFactory {
    //TODO: Implement Customer Factory for creating guests and registered customers

    public static Customer createRegistered(String firstName, String lastName, // not strictly necessary but helps doc users
        String email, String accountAddress) {
    return createCustomer(null, firstName, lastName, CustomerType.registered, email, accountAddress);
    }
 
    public static Customer createGuest(String firstName, String lastName) { // not strictly necessary but helps doc users
        return createCustomer(null, firstName, lastName, CustomerType.guest, null, null);
    }

    public static Customer createCustomer (String id, String firstName, String lastName, CustomerType type, String email,
        String accountAddress) {
        
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("first name must not be blank");
        } else if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("last name must not be blank");
        }

        Customer customer = new Customer(id, firstName, lastName, type , email, accountAddress);
        customer.setLastSeen(new Date());
        return customer;
    }  
}



