package com.es.phoneshop.model.product;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class ArrayListProductDaoTest {
    private ArrayListProductDao productDao;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        ArrayList<Product> products = new ArrayList<>();
        for (int i = 0; i < TestProductConstants.CNT_TEST_PRODUCTS; i++) {
            products.add(new Product((long) i, TestProductConstants.TEST_PRODUCT_CODE + i, TestProductConstants.TEST_PRODUCT_DESCRIPTION + i, TestProductConstants.TEST_PRODUCT_PRICE,
                    TestProductConstants.TEST_PRODUCT_CURRENCY, TestProductConstants.TEST_PRODUCT_STOCK, TestProductConstants.TEST_PRODUCT_IMAGE_URL, null));
        }
        productDao.setProducts(products, TestProductConstants.CNT_TEST_PRODUCTS);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenSaveNullProduct() {
        productDao.save(null);
    }

    @Test
    public void shouldSaveNewProduct() {
        Product product = new Product(TestProductConstants.TEST_PRODUCT_CODE, TestProductConstants.TEST_PRODUCT_DESCRIPTION, TestProductConstants.TEST_PRODUCT_PRICE,
                TestProductConstants.TEST_PRODUCT_CURRENCY, TestProductConstants.TEST_PRODUCT_STOCK, TestProductConstants.TEST_PRODUCT_IMAGE_URL, null);

        productDao.save(product);

        assertNotNull(product.getId());
        assertTrue(productDao.getProducts().contains(product));

        productDao.getProducts().removeIf(p -> product.getCode().equals(p.getCode()));
    }

    @Test
    public void shouldUpdateProduct() {
        Product product = productDao.getProducts().stream()
                .filter(p -> TestProductConstants.ID_PRODUCT_TO_UPDATE.equals(p.getId()))
                .findAny()
                .get();

        product.setStock(TestProductConstants.NEW_STOCK);
        productDao.save(product);

        assertEquals(TestProductConstants.ID_PRODUCT_TO_UPDATE, product.getId());
        Product updatedProduct = productDao.getProducts().stream()
                .filter(p -> TestProductConstants.ID_PRODUCT_TO_UPDATE.equals(p.getId()))
                .findAny()
                .get();
        assertEquals(TestProductConstants.NEW_STOCK, updatedProduct.getStock());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenDeleteIdIsNull() {
        productDao.delete(null);
    }

    @Test
    public void shouldDeleteProduct() {
        productDao.delete(TestProductConstants.ID_PRODUCT_TO_DELETE);

        List<Product> products = productDao.getProducts().stream()
                .filter(p -> TestProductConstants.ID_PRODUCT_TO_DELETE.equals(p.getId()))
                .collect(Collectors.toList());
        assertTrue(products.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenFindIdIsNull() {
        productDao.getProduct(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionWhenNoProduct() {
        productDao.getProduct(TestProductConstants.ID_PRODUCT_NOT_FIND);
    }

    @Test
    public void shouldFindProduct() {
        Product product = productDao.getProduct(TestProductConstants.ID_PRODUCT_TO_FIND);

        assertEquals(TestProductConstants.ID_PRODUCT_TO_FIND, product.getId());
    }

    @After
    public void clear() {
        productDao.getProducts().removeIf(p -> p.getId() < TestProductConstants.CNT_TEST_PRODUCTS);
    }
}