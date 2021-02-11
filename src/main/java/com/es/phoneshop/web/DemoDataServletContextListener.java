package com.es.phoneshop.web;

import com.es.phoneshop.model.product.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;

public class DemoDataServletContextListener implements ServletContextListener {
    private final ProductService productService;

    public DemoDataServletContextListener() {
        this.productService = new ProductService(ArrayListProductDao.getInstance());
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        boolean insertDemoData = Boolean.parseBoolean(servletContextEvent.getServletContext().getInitParameter(Constants.INSERT_DEMO_DATA_PARAMETER_NAME));
        if (insertDemoData) {
            getSampleProducts().forEach(productService::save);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    private ArrayList<Product> getSampleProducts() {
        ArrayList<Product> products = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        products.add(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg",
                new ArrayList<PriceHistory>(Arrays.asList(new PriceHistory(new BigDecimal(150), new Date(2020 - Constants.YEAR_SHIFT, 10, 18))))));
        products.add(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 1, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg",
                new ArrayList<PriceHistory>(Arrays.asList(new PriceHistory(new BigDecimal(250), new Date(2020 - Constants.YEAR_SHIFT, 12, 18)), new PriceHistory(new BigDecimal(300), new Date(2020 - Constants.YEAR_SHIFT, 10, 18))))));
        products.add(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg",
                null));
        products.add(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg",
                null));
        products.add(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg",
                null));
        products.add(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg",
                null));
        products.add(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg",
                null));
        products.add(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg",
                null));
        products.add(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg",
                null));
        products.add(new Product("palmp", "Palm Pixi", null, usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg",
                null));
        products.add(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg",
                null));
        products.add(new Product("simc61", "Siemens C61", null, usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg",
                null));
        products.add(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg",
                null));
        return products;
    }
}
