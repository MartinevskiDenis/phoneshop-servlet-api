package com.es.phoneshop.web;

import com.es.phoneshop.model.enums.SearchType;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.search.DefaultSearchService;
import com.es.phoneshop.service.search.SearchService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SearchPageServlet extends HttpServlet {
    private SearchService searchService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        searchService = DefaultSearchService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter(Constants.SEARCH_QUERY_PARAMETER_NAME);
        String searchTypeParameter = request.getParameter(Constants.SEARCH_TYPE_PARAMETER_NAME);
        SearchType searchType = Optional.ofNullable(searchTypeParameter)
                .map(String::toUpperCase)
                .map(SearchType::valueOf)
                .orElse(null);
        String minPriceParameter = request.getParameter(Constants.SEARCH_MIN_PRICE_PARAMETER_NAME);
        boolean flag = true;
        BigDecimal minPrice = null;
        if (!StringUtils.isBlank(minPriceParameter)) {
            try {
                minPrice = BigDecimal.valueOf(Long.parseLong(minPriceParameter));
            } catch (NumberFormatException e) {
                request.setAttribute(Constants.ERROR_MIN_PRICE_PARAMETER_NAME, Constants.ERROR_NOT_NUMBER_TEXT);
                flag = false;
            }
        }
        String maxPriceParameter = request.getParameter(Constants.SEARCH_MAX_PRICE_PARAMETER_NAME);
        BigDecimal maxPrice = null;
        if (!StringUtils.isBlank(maxPriceParameter)) {
            try {
                maxPrice = BigDecimal.valueOf(Long.parseLong(maxPriceParameter));
            } catch (NumberFormatException e) {
                request.setAttribute(Constants.ERROR_MAX_PRICE_PARAMETER_NAME, Constants.ERROR_NOT_NUMBER_TEXT);
                flag = false;
            }
        }
        if ((searchQuery == null) && (searchTypeParameter == null) && (minPriceParameter == null) && (maxPriceParameter == null)) {
            flag = false;
        }
        List<Product> products = new ArrayList<>();
        if (flag) {
            products = searchService.findProducts(searchQuery, searchType, minPrice, maxPrice);
        }
        request.setAttribute(Constants.SEARCH_PRODUCTS_ATTRIBUTE_NAME, products);
        request.getRequestDispatcher(Constants.SEARCH_PAGE_PATH).forward(request, response);
    }
}
