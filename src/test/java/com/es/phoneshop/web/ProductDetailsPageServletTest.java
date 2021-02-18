package com.es.phoneshop.web;

import com.es.phoneshop.constants.TestServletConstants;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.product.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig config;
    @Mock
    private Product product;
    @Mock
    private ProductService service;

    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup() throws ServletException {
        servlet.init(config);
        servlet.setProductService(service);
        when(service.getProduct(anyLong())).thenReturn(product);
        when(service.getProduct(eq(TestServletConstants.PRODUCT_NOT_FOUND_ID))).thenThrow(NoSuchElementException.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/" + TestServletConstants.PRODUCT_ID);

        servlet.doGet(request, response);

        verify(request).setAttribute(eq(TestServletConstants.PRODUCT_ATTRIBUTE), eq(product));
        verify(request).getRequestDispatcher(eq(TestServletConstants.PRODUCT_DETAILS_PAGE_PATH));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void shouldSwitchToProductNotFoundPage() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/" + TestServletConstants.PRODUCT_NOT_FOUND_ID);

        servlet.doGet(request, response);

        verify(request).setAttribute(eq(TestServletConstants.PRODUCT_ID_ATTRIBUTE), eq(TestServletConstants.PRODUCT_NOT_FOUND_ID.toString()));
        verify(request).getRequestDispatcher(eq(TestServletConstants.PRODUCT_NOT_FOUND_PAGE_PATH));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void shouldSwitchToErrorPage() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/" + TestServletConstants.INVALID_PRODUCT_ID);

        servlet.doGet(request, response);

        verify(request).getRequestDispatcher(eq(TestServletConstants.ERROR_PAGE_PATH));
        verify(requestDispatcher).forward(request, response);
    }
}