package com.example.buysell.services;

import com.example.buysell.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
//    private List<Product> products = new ArrayList<>();
    public List<Product> getProductsList() {
        return products;
    }

    public Product getProductById(Long id) {
        for (Product product: products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public void saveProduct(Product product) {
        product.setId(++ID);
        products.add(product);
    }

    public void deleteProduct(Long id) {
        products.removeIf(product -> product.getId().equals(id));
    }
}
