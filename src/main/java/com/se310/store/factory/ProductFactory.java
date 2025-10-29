package com.se310.store.factory;

import com.se310.store.model.Product;
import com.se310.store.model.Temperature;

/**
 * Factory Pattern implementation for creating Product objects
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2025-09-25
 */

public class ProductFactory {

    public enum ProductType {
        STANDARD,
        DISCOUNTED,
        PREMIUM
    }

    public static Product createProduct(String id, String name, String description, String size, String category,
     double basePrice, Temperature temperature) {
        ProductType type;

        //argument validation
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("Product id cannot be null or blank");
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Product name cannot be null or blank");
        if (category == null || category.isBlank())
            throw new IllegalArgumentException("Product category cannot be null or blank");
        if (basePrice < 0)
            throw new IllegalArgumentException("Base price cannot be negative");
        if (temperature == null)
            throw new IllegalArgumentException("Temperature must be specified");


        switch (category) {
            case "Men_accessories":
                type = ProductType.PREMIUM;
                break;
            case "Food":
                type = ProductType.DISCOUNTED;
                break;
            default:
                type = ProductType.STANDARD;
                break;
        }
            
        switch (type) {
            case STANDARD:
                // Return standard product
                return new Product(id, name, description, size, category, basePrice, temperature);

            case DISCOUNTED:
                // Return discounted product (10% off)
                return new Product(id, name + " (Discounted)", description, size, category, basePrice * 0.9, temperature);

            case PREMIUM:
                // Return premium product (20% markup)
                return new Product(id, name + " (Premium)", description, size, category, basePrice * 1.2, temperature);

            default:
                throw new IllegalArgumentException("Unknown product type: " + type);
        }
    }
}

