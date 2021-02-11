package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ArrayListProductDao implements ProductDao {
    private final List<Product> products;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private long nextId = 0L;

    private ArrayListProductDao() {
        products = new ArrayList<>();
    }

    private static class SingletonInstance {
        private static final ArrayListProductDao INSTANCE = new ArrayListProductDao();
    }

    public static ArrayListProductDao getInstance() {
        return SingletonInstance.INSTANCE;
    }

    @Override
    public List<Product> getProducts() {
        readWriteLock.readLock().lock();
        try {
            return products;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public Product getProduct(Long id) throws NullPointerException, NoSuchElementException {
        if (id == null) {
            throw new NullPointerException();
        }
        readWriteLock.readLock().lock();
        try {
            return products.stream()
                    .filter(product -> id.equals(product.getId()))
                    .findAny()
                    .orElseThrow(NoSuchElementException::new);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public void save(Product product) throws NullPointerException {
        if (product == null) {
            throw new NullPointerException();
        }
        readWriteLock.writeLock().lock();
        try {
            if (product.getId() != null) {
                delete(product.getId());
            } else {
                product.setId(nextId++);
            }
            products.add(product);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void delete(Long id) throws NullPointerException {
        if (id == null) {
            throw new NullPointerException();
        }
        readWriteLock.writeLock().lock();
        try {
            products.removeIf(product -> id.equals(product.getId()));
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
