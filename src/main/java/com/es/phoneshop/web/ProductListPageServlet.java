package com.es.phoneshop.web;

import com.es.phoneshop.model.product.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ProductListPageServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productService = new ProductService(ArrayListProductDao.getInstance());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter(Constants.QUERY_PARAMETER_NAME);
        String sortFieldParameter = request.getParameter(Constants.SORT_FIELD_PARAMETER_NAME);
        SortField sortField = Optional.ofNullable(sortFieldParameter).map(String::toUpperCase).map(SortField::valueOf).orElse(null);
        String sortOrderParameter = request.getParameter(Constants.SORT_ORDER_PARAMETER_NAME);
        SortOrder sortOrder = Optional.ofNullable(sortOrderParameter).map(String::toUpperCase).map(SortOrder::valueOf).orElse(null);
        request.setAttribute(Constants.PRODUCTS_ATTRIBUTE_NAME, productService.findProducts(query, sortField, sortOrder));
        request.getRequestDispatcher(Constants.PRODUCTS_PAGE_PATH).forward(request, response);
    }
}
