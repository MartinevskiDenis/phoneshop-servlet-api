package com.es.phoneshop.service.product;

import com.es.phoneshop.model.enums.SortField;
import com.es.phoneshop.model.enums.SortOrder;
import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface ProductService {
    Product getProduct(Long id);

    void save(Product product);

    void delete(Long id);

    List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder);
}
