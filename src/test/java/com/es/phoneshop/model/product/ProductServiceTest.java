package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Mock
    private ProductDao productDao;
    private ProductService productService;

    @Before
    public void setup() {
        productService = new ProductService(productDao);
        when(productDao.getProduct(any())).thenReturn(new Product());
        doNothing().when(productDao).delete(any());
        doNothing().when(productDao).save(any());
        when(productDao.getProducts()).thenReturn(TestProductConstants.TEST_PRODUCTS);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenFindIdIsNull() {
        productService.getProduct(null);
    }

    @Test
    public void shouldCallDaoGetProduct() {
        Product product = productService.getProduct(TestProductConstants.ID_PRODUCT_TO_FIND);

        verify(productDao).getProduct(TestProductConstants.ID_PRODUCT_TO_FIND);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenDeleteIdIsNull() {
        productService.delete(null);
    }

    @Test
    public void shouldCallDaoDelete() {
        productService.delete(TestProductConstants.ID_PRODUCT_TO_DELETE);

        verify(productDao).delete(TestProductConstants.ID_PRODUCT_TO_DELETE);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenSaveNullProduct() {
        productService.save(null);
    }

    @Test
    public void shouldCallDaoSave() {
        Product product = new Product(TestProductConstants.TEST_PRODUCT_CODE, TestProductConstants.TEST_PRODUCT_DESCRIPTION, TestProductConstants.TEST_PRODUCT_PRICE,
                TestProductConstants.TEST_PRODUCT_CURRENCY, TestProductConstants.TEST_PRODUCT_STOCK, TestProductConstants.TEST_PRODUCT_IMAGE_URL, null);

        productService.save(product);

        verify(productDao).save(product);
    }

    @Test
    public void shouldDoNothingIfPriceIsNull() {
        Product product = new Product(TestProductConstants.TEST_PRODUCT_CODE, TestProductConstants.TEST_PRODUCT_DESCRIPTION, null,
                TestProductConstants.TEST_PRODUCT_CURRENCY, TestProductConstants.TEST_PRODUCT_STOCK, TestProductConstants.TEST_PRODUCT_IMAGE_URL, null);

        productService.save(product);

        assertNull(product.getPriceHistories());
    }

    @Test
    public void shouldCreatePriceHistory() {
        Product product = new Product(TestProductConstants.TEST_PRODUCT_CODE, TestProductConstants.TEST_PRODUCT_DESCRIPTION, TestProductConstants.TEST_PRODUCT_PRICE,
                TestProductConstants.TEST_PRODUCT_CURRENCY, TestProductConstants.TEST_PRODUCT_STOCK, TestProductConstants.TEST_PRODUCT_IMAGE_URL, null);

        productService.save(product);

        assertNotNull(product.getPriceHistories());
        assertEquals(1, product.getPriceHistories().size());
        assertEquals(TestProductConstants.TEST_PRODUCT_PRICE, product.getPriceHistories().get(0).getPrice());
    }

    @Test
    public void shouldUpdatePriceHistory() {
        Product product = new Product(TestProductConstants.TEST_PRODUCT_CODE, TestProductConstants.TEST_PRODUCT_DESCRIPTION, TestProductConstants.TEST_PRODUCT_PRICE,
                TestProductConstants.TEST_PRODUCT_CURRENCY, TestProductConstants.TEST_PRODUCT_STOCK, TestProductConstants.TEST_PRODUCT_IMAGE_URL,
                new ArrayList<>(Arrays.asList(new PriceHistory(TestProductConstants.TEST_PRODUCT_PREVIOUS_PRICE, TestProductConstants.TEST_PRODUCT_PREVIOUS_DATE))));

        productService.save(product);

        assertNotNull(product.getPriceHistories());
        assertEquals(2, product.getPriceHistories().size());
        assertEquals(TestProductConstants.TEST_PRODUCT_PREVIOUS_PRICE, product.getPriceHistories().get(1).getPrice());
        assertEquals(TestProductConstants.TEST_PRODUCT_PREVIOUS_DATE, product.getPriceHistories().get(1).getDate());
        assertEquals(TestProductConstants.TEST_PRODUCT_PRICE, product.getPriceHistories().get(0).getPrice());
        assertNotEquals(TestProductConstants.TEST_PRODUCT_PREVIOUS_DATE, product.getPriceHistories().get(0).getDate());
    }

    @Test
    public void shouldFindAllAvailableProducts() {
        ArrayList<String> descriptions = new ArrayList<>();

        ArrayList<Product> products = (ArrayList<Product>) productService.findProducts(null, null, null);
        products.forEach(p -> descriptions.add(p.getDescription()));
        descriptions.sort(Comparator.naturalOrder());

        assertArrayEquals(TestProductConstants.TEST_PRODUCTS_AVAILABLE.toArray(), descriptions.toArray());
    }

    @Test
    public void shouldFindProductsByDescription() {
        ArrayList<String> descriptions = new ArrayList<>();

        ArrayList<Product> products = (ArrayList<Product>) productService.findProducts(TestProductConstants.QUERY, null, null);
        products.forEach(p -> descriptions.add(p.getDescription()));
        descriptions.sort(Comparator.naturalOrder());

        assertArrayEquals(TestProductConstants.TEST_PRODUCTS_QUERY_ASC_DESCRIPTION.toArray(), descriptions.toArray());
    }

    @Test
    public void shouldFindProductsByDescriptionAndSortAscByDescription() {
        ArrayList<String> descriptions = new ArrayList<>();

        ArrayList<Product> products = (ArrayList<Product>) productService.findProducts(TestProductConstants.QUERY, SortField.DESCRIPTION, SortOrder.ASCENDING);
        products.forEach(p -> descriptions.add(p.getDescription()));

        assertArrayEquals(TestProductConstants.TEST_PRODUCTS_QUERY_ASC_DESCRIPTION.toArray(), descriptions.toArray());
    }

    @Test
    public void shouldFindProductsByDescriptionAndSortDescByPrice() {
        ArrayList<String> descriptions = new ArrayList<>();

        ArrayList<Product> products = (ArrayList<Product>) productService.findProducts(TestProductConstants.QUERY, SortField.PRICE, SortOrder.DESCENDING);
        products.forEach(p -> descriptions.add(p.getDescription()));

        assertArrayEquals(TestProductConstants.TEST_PRODUCTS_QUERY_DESC_PRICE.toArray(), descriptions.toArray());
    }
}