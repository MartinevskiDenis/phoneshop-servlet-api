package com.es.phoneshop.service.search;

import com.es.phoneshop.dao.product.ArrayListProductDao;
import com.es.phoneshop.dao.product.ProductDao;
import com.es.phoneshop.model.enums.SearchType;
import com.es.phoneshop.model.product.Product;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class DefaultSearchService implements SearchService {
    private ProductDao productDao;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private DefaultSearchService() {
        productDao = ArrayListProductDao.getInstance();
    }

    private static class SingletonInstance {
        private static final DefaultSearchService INSTANCE = new DefaultSearchService();
    }

    public static DefaultSearchService getInstance() {
        return DefaultSearchService.SingletonInstance.INSTANCE;
    }

    @Override
    public List<Product> findProducts(String query, SearchType searchType, BigDecimal minPrice, BigDecimal maxPrice) {
        readWriteLock.readLock().lock();
        try {
            return productDao.getProducts().stream()
                    .filter(product -> product.getPrice() != null)
                    .filter(product -> product.getStock() > 0)
                    .filter(product -> checkDescriptionMatch(product.getDescription(), query, searchType))
                    .filter(product -> (minPrice == null) || (minPrice.compareTo(product.getPrice()) <= 0))
                    .filter(product -> (maxPrice == null) || (maxPrice.compareTo(product.getPrice()) >= 0))
                    .collect(Collectors.toList());
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    private boolean checkDescriptionMatch(String description, String query, SearchType searchType) {
        if (StringUtils.isBlank(query)) {
            return true;
        }
        if (searchType == SearchType.ALL_WORDS) {
            return Arrays.stream(query.toLowerCase().split(" "))
                    .allMatch(word -> !word.isEmpty() && description.toLowerCase().contains(word));
        } else {
            return Arrays.stream(query.toLowerCase().split(" ")).anyMatch(word -> !word.isEmpty() && description.toLowerCase().contains(word));
        }
    }
}
