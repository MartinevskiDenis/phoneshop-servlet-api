package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

public class ProductPriceHistoryPageServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productService = new ProductService(ArrayListProductDao.getInstance());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pagePath;
        String productId = request.getPathInfo().substring(1);
        try {
            request.setAttribute(Constants.PRODUCT_ATTRIBUTE_NAME, productService.getProduct(Long.parseLong(productId)));
            pagePath = Constants.PRODUCT_PRICE_HISTORY_PAGE_PATH;
        } catch (NoSuchElementException e) {
            request.setAttribute(Constants.PRODUCT_ID_ATTRIBUTE_NAME, productId);
            pagePath = Constants.PRODUCT_NOT_FOUND_PAGE_PATH;
        } catch (NumberFormatException | NullPointerException e) {
            pagePath = Constants.ERROR_PAGE_PATH;
        }
        request.getRequestDispatcher(pagePath).forward(request, response);
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
