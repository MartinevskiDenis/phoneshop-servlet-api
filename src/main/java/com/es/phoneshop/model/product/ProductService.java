package com.es.phoneshop.model.product;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Date;
import java.util.stream.Collectors;

public class ProductService {
    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductService() {
    }

    public Product getProduct(Long id) throws NullPointerException {
        if ((productDao == null) || (id == null))
            throw new NullPointerException();
        return productDao.getProduct(id);
    }

    public List<Product> getProducts() {
        if (productDao == null)
            throw new NullPointerException();
        return productDao.getProducts();
    }

    public void save(Product product) throws NullPointerException {
        if ((productDao == null) || (product == null))
            throw new NullPointerException();
        updateProductPriceHistory(product);
        productDao.save(product);
    }

    public void delete(Long id) throws NullPointerException {
        if ((productDao == null) || (id == null))
            throw new NullPointerException();
        productDao.delete(id);
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

    public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) throws NullPointerException {
        if (productDao == null)
            throw new NullPointerException();
        return productDao.getProducts().stream()
                .filter(product -> product.getPrice() != null)
                .filter(product -> product.getStock() > 0)
                .filter(product -> StringUtils.isBlank(query) || getNumberOfCoincidences(product.getDescription(), query) > 0)
                .sorted(Comparator.comparing(product -> getNumberOfCoincidences(product.getDescription(), query), Comparator.reverseOrder()))
                .sorted(getProductComparator(sortField, sortOrder))
                .collect(Collectors.toList());
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
}
