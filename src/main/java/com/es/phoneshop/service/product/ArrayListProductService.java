package com.es.phoneshop.service.product;

import com.es.phoneshop.dao.product.ArrayListProductDao;
import com.es.phoneshop.dao.product.ProductDao;
import com.es.phoneshop.model.enums.SortField;
import com.es.phoneshop.model.enums.SortOrder;
import com.es.phoneshop.model.product.PriceHistory;
import com.es.phoneshop.model.product.Product;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Date;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductService implements ProductService {
    private ProductDao productDao;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private ArrayListProductService() {
        productDao = ArrayListProductDao.getInstance();
    }

    private static class SingletonInstance {
        private static final ArrayListProductService INSTANCE = new ArrayListProductService();
    }

    public static ArrayListProductService getInstance() {
        return ArrayListProductService.SingletonInstance.INSTANCE;
    }

    @Override
    public Product getProduct(Long id) throws NullPointerException {
        if (id == null) {
            throw new NullPointerException();
        }
        readWriteLock.readLock().lock();
        try {
            return productDao.getProduct(id);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public void save(Product product) throws NullPointerException {
        if (product == null)
            throw new NullPointerException();
        readWriteLock.writeLock().lock();
        try {
            updateProductPriceHistory(product);
            productDao.save(product);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void delete(Long id) throws NullPointerException {
        if (id == null)
            throw new NullPointerException();
        readWriteLock.writeLock().lock();
        try {
            productDao.delete(id);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) throws NullPointerException {
        readWriteLock.readLock().lock();
        try {
            return productDao.getProducts().stream()
                    .filter(product -> product.getPrice() != null)
                    .filter(product -> product.getStock() > 0)
                    .filter(product -> StringUtils.isBlank(query) || getNumberOfCoincidences(product.getDescription(), query) > 0)
                    .sorted(Comparator.comparing(product -> getNumberOfCoincidences(product.getDescription(), query), Comparator.reverseOrder()))
                    .sorted(getProductComparator(sortField, sortOrder))
                    .collect(Collectors.toList());
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    private long getNumberOfCoincidences(String description, String query) {
        if (StringUtils.isBlank(description) || StringUtils.isBlank(query))
            return 0L;
        return Arrays.stream(query.split(" "))
                .filter(word -> !word.isEmpty() && description.toLowerCase().contains(word.toLowerCase()))
                .count();
    }

    private Comparator<Product> getProductComparator(SortField sortField, SortOrder sortOrder) {
        if ((sortField == null) || (sortOrder == null)) {
            return (p1, p2) -> 0;
        }
        Comparator<Product> comparator = Comparator.comparing(product -> {
            if (sortField == SortField.DESCRIPTION) {
                return (Comparable) product.getDescription();
            } else {
                return (Comparable) product.getPrice();
            }
        });
        if (sortOrder == SortOrder.DESCENDING) {
            comparator = comparator.reversed();
        }
        return comparator;
    }

    private void updateProductPriceHistory(Product product) throws NullPointerException {
        if (product == null) {
            throw new NullPointerException();
        }
        BigDecimal price = product.getPrice();
        if (price == null) {
            return;
        }
        List<PriceHistory> priceHistories = product.getPriceHistories();
        if (priceHistories == null) {
            priceHistories = new ArrayList<>(Arrays.asList(new PriceHistory(price, new Date())));
            product.setPriceHistories(priceHistories);
        } else {
            if (priceHistories.isEmpty() || !priceHistories.get(priceHistories.size() - 1).getPrice().equals(price)) {
                priceHistories.add(0, new PriceHistory(price, new Date()));
            }
        }
    }

    void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
