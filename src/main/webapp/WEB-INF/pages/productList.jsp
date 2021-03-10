<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
  <p>
    Welcome to Expert-Soft training!
  </p>
  <h3><a href="${pageContext.servletContext.contextPath}/search">Advanced search</a></h3>
  <form>
    <c:if test="${not empty param.field and not empty param.order}">
      <input type="hidden" name="field" value="${param.field}">
      <input type="hidden" name="order" value="${param.order}">
    </c:if>
    <input type="text" placeholder="Find products" name="query" value="${param.query}">
    <button type="submit">Search</button>
  </form>
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>
          Description
          <tags:sortLink field="description" order="ascending"/>
          <tags:sortLink field="description" order="descending"/>
        </td>
        <td class="price">
          Price
          <tags:sortLink field="price" order="ascending"/>
          <tags:sortLink field="price" order="descending"/>
        </td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="${product.imageUrl}">
        </td>
        <td><a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a></td>
        <td class="price">
          <div class="popup" onclick="showPriceHistory(${product.id})">
            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            <span class="popuptext" id="myPopup${product.id}">
              <h2>Price history</h2>
              <h3>${product.description}</h3>
              <table class="noBorder" style="color: #fff;" align="center">
                <thead>
                  <tr>
                      <td><b>Start date</b></td>
                      <td><b>Price</b></td>
                  </tr>
                </thead>
                <c:forEach var="priceHistory" items="${product.priceHistories}">
                  <tr>
                    <td><fmt:formatDate value="${priceHistory.date}"/></td>
                    <td class="price"><fmt:formatNumber value="${priceHistory.price}" type="currency" currencySymbol="${product.currency.symbol}"/></td>
                  </tr>
                </c:forEach>
              </table>
            </span>
          </div>
        </td>
      </tr>
    </c:forEach>
  </table>
  <script>
    function showPriceHistory(productId) {
      var popup = document.getElementById("myPopup" + productId);
      popup.classList.toggle("show");
    }
  </script>
</tags:master>