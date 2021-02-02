package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class ArrayListProductDaoTest {
    private static final String TEST_PRODUCT_CODE = "test-product1";
    private static final String TEST_PRODUCT_DESCRIPTION = "Test Product 1";
    private static final BigDecimal TEST_PRODUCT_PRICE = new BigDecimal(100);
    private static final Currency TEST_PRODUCT_CURRENCY = Currency.getInstance("USD");
    private static final int TEST_PRODUCT_STOCK = 100;
    private static final String TEST_PRODUCT_IMAGE_URL = "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg";
    private static final int NEW_STOCK = 10;
    private static final Long ID_PRODUCT_TO_UPDATE = 0L;
    private static final Long ID_PRODUCT_TO_DELETE = 1L;
    private static final Long ID_PRODUCT_TO_FIND = 2L;
    private static final Long ID_PRODUCT_NOT_FIND = -1L;
    private static final int CNT_UNSUITABLE_PRODUCTS = 3;
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenSaveNullProduct() {
        productDao.save(null);
    }

    @Test
    public void shouldSaveNewProduct() {
        Product product = new Product(TEST_PRODUCT_CODE, TEST_PRODUCT_DESCRIPTION, TEST_PRODUCT_PRICE, TEST_PRODUCT_CURRENCY, TEST_PRODUCT_STOCK, TEST_PRODUCT_IMAGE_URL);

        productDao.save(product);

        assertNotNull(product.getId());
        assertTrue(productDao.getProducts().contains(product));
    }

    @Test
    public void shouldUpdateProduct() {
        Product product = productDao.getProducts().stream()
                .filter(p -> ID_PRODUCT_TO_UPDATE.equals(p.getId()))
                .findAny()
                .get();

        product.setStock(NEW_STOCK);
        productDao.save(product);

        assertEquals(ID_PRODUCT_TO_UPDATE, product.getId());
        Product updatedProduct = productDao.getProducts().stream()
                .filter(p -> ID_PRODUCT_TO_UPDATE.equals(p.getId()))
                .findAny()
                .get();
        assertEquals(NEW_STOCK, updatedProduct.getStock());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenDeleteIdIsNull() {
        productDao.delete(null);
    }

    @Test
    public void shouldDeleteProduct() {
        productDao.delete(ID_PRODUCT_TO_DELETE);

        List<Product> products = productDao.getProducts().stream()
                .filter(p -> ID_PRODUCT_TO_DELETE.equals(p.getId()))
                .collect(Collectors.toList());
        assertTrue(products.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenFindIdIsNull() {
        productDao.getProduct(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionWhenNoProduct() {
        productDao.getProduct(ID_PRODUCT_NOT_FIND);
    }

    @Test
    public void shouldFindProduct() {
        Product product = productDao.getProduct(ID_PRODUCT_TO_FIND);

        assertEquals(ID_PRODUCT_TO_FIND, product.getId());
    }

    @Test
    public void shouldFindProducts() {
        List<Product> allProducts = productDao.getProducts();

        List<Product> products = productDao.findProducts();

        assertEquals((allProducts.size() - CNT_UNSUITABLE_PRODUCTS), products.size());
    }
}