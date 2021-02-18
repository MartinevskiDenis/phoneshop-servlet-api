package com.es.phoneshop.dao.product;

import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);

    List<Product> getProducts();

    void save(Product product);

    void delete(Long id);
}
