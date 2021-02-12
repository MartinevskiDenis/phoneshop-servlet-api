package com.es.phoneshop.model.product;

import com.es.phoneshop.web.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;

public class TestProductConstants {
    public static final int CNT_TEST_PRODUCTS = 3;
    public static final String TEST_PRODUCT_CODE = "test-product";
    public static final String TEST_PRODUCT_DESCRIPTION = "Test Product";
    public static final BigDecimal TEST_PRODUCT_PRICE = new BigDecimal(100);
    public static final Currency TEST_PRODUCT_CURRENCY = Currency.getInstance("USD");
    public static final int TEST_PRODUCT_STOCK = 10;
    public static final String TEST_PRODUCT_IMAGE_URL = "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg";
    public static final int NEW_STOCK = 10;
    public static final Long ID_PRODUCT_TO_UPDATE = 0L;
    public static final Long ID_PRODUCT_TO_DELETE = 1L;
    public static final Long ID_PRODUCT_TO_FIND = 2L;
    public static final Long ID_PRODUCT_NOT_FIND = -1L;
    public static final BigDecimal TEST_PRODUCT_PREVIOUS_PRICE = new BigDecimal(200);
    public static final Date TEST_PRODUCT_PREVIOUS_DATE = new Date(2020 - Constants.YEAR_SHIFT, 10, 18);
    public static final ArrayList<Product> TEST_PRODUCTS = new ArrayList<>();
    public static final ArrayList<String> TEST_PRODUCTS_AVAILABLE = new ArrayList<>();
    public static final String QUERY = "Samsung";
    public static final ArrayList<String> TEST_PRODUCTS_QUERY_ASC_DESCRIPTION = new ArrayList<>();
    public static final ArrayList<String> TEST_PRODUCTS_QUERY_DESC_PRICE = new ArrayList<>();

    static {
        TEST_PRODUCTS.add(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), TEST_PRODUCT_CURRENCY, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg",
                new ArrayList<PriceHistory>(Arrays.asList(new PriceHistory(new BigDecimal(150), new Date(2020 - Constants.YEAR_SHIFT, 10, 18))))));
        TEST_PRODUCTS.add(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), TEST_PRODUCT_CURRENCY, 1, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg",
                new ArrayList<PriceHistory>(Arrays.asList(new PriceHistory(new BigDecimal(250), new Date(2020 - Constants.YEAR_SHIFT, 12, 18)), new PriceHistory(new BigDecimal(300), new Date(2020 - Constants.YEAR_SHIFT, 10, 18))))));
        TEST_PRODUCTS.add(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), TEST_PRODUCT_CURRENCY, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg",
                null));
        TEST_PRODUCTS.add(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), TEST_PRODUCT_CURRENCY, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg",
                null));
        TEST_PRODUCTS.add(new Product("palmp", "Palm Pixi", null, TEST_PRODUCT_CURRENCY, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg",
                null));
        TEST_PRODUCTS.add(new Product("simc61", "Siemens C61", null, TEST_PRODUCT_CURRENCY, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg",
                null));
        TEST_PRODUCTS.add(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), TEST_PRODUCT_CURRENCY, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg",
                null));

        TEST_PRODUCTS_AVAILABLE.add("Samsung Galaxy S");
        TEST_PRODUCTS_AVAILABLE.add("Samsung Galaxy S II");
        TEST_PRODUCTS_AVAILABLE.add("Samsung Galaxy S III");
        TEST_PRODUCTS_AVAILABLE.add("Apple iPhone 6");
        TEST_PRODUCTS_AVAILABLE.sort(Comparator.naturalOrder());

        TEST_PRODUCTS_QUERY_ASC_DESCRIPTION.add("Samsung Galaxy S");
        TEST_PRODUCTS_QUERY_ASC_DESCRIPTION.add("Samsung Galaxy S II");
        TEST_PRODUCTS_QUERY_ASC_DESCRIPTION.add("Samsung Galaxy S III");

        TEST_PRODUCTS_QUERY_DESC_PRICE.add("Samsung Galaxy S III");
        TEST_PRODUCTS_QUERY_DESC_PRICE.add("Samsung Galaxy S II");
        TEST_PRODUCTS_QUERY_DESC_PRICE.add("Samsung Galaxy S");
    }
}
