package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    private Currency usd;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
        usd = Currency.getInstance("USD");
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenSaveNullProduct() {
        productDao.save(null);
    }

    @Test
    public void shouldSaveNewProduct() {
        Product product = new Product("test-product1", "Test Product 1", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product);

        assertNotNull(product.getId());
        assertTrue(productDao.getProducts().contains(product));
    }

    @Test
    public void shouldUpdateProduct() {
        int newStock = 10;
        Long productId = 0L;
        Product product = productDao.getProducts().stream()
                .filter(p -> productId.equals(p.getId()))
                .findAny()
                .get();

        product.setStock(newStock);
        productDao.save(product);

        assertEquals(productId, product.getId());
        Product updatedProduct = productDao.getProducts().stream()
                .filter(p -> productId.equals(p.getId()))
                .findAny()
                .get();
        assertEquals(newStock, updatedProduct.getStock());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenDeleteIdIsNull() {
        productDao.delete(null);
    }

    @Test
    public void shouldDeleteProduct() {
        Long id = 1L;

        productDao.delete(id);

        List<Product> products = productDao.getProducts().stream()
                .filter(p -> id.equals(p.getId()))
                .collect(Collectors.toList());
        assertTrue(products.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenFindIdIsNull() {
        productDao.getProduct(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionWhenNoProduct() {
        productDao.getProduct(-1L);
    }

    @Test
    public void shouldFindProduct() {
        Long id = 2L;

        Product product = productDao.getProduct(id);

        assertEquals(id, product.getId());
    }

    @Test
    public void shouldFindProducts() {
        List<Product> allProducts = productDao.getProducts();

        List<Product> products = productDao.findProducts();

        assertEquals((allProducts.size() - 3), products.size());
    }
}