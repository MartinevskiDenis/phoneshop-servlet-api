package com.es.phoneshop.service.search;

import com.es.phoneshop.model.enums.SearchType;
import com.es.phoneshop.model.product.Product;

import java.math.BigDecimal;
import java.util.List;

public interface SearchService {

    List<Product> findProducts(String query, SearchType searchType, BigDecimal minPrice, BigDecimal maxPrice);
}
