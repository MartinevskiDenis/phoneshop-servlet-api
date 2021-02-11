<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Details">
  <p><b>${product.description}</b></p>
  <table>
      <tr>
        <td>Image</td>
        <td><img class="product-tile" src="${product.imageUrl}"></td>
      </tr>
      <tr>
        <td>Code</td>
        <td>${product.code}</td>
      </tr>
      <tr>
        <td>Stock</td>
        <td>${product.stock}</td>
      </tr>
      <tr>
        <td>Price</td>
          <td><a href="${pageContext.servletContext.contextPath}/pricehistory/${product.id}"><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/></a></td>
      </tr>
  </table>
</tags:master>