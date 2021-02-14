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
  </table>
    <script>
        function showPriceHistory(productId) {
            var popup = document.getElementById("myPopup" + productId);
            popup.classList.toggle("show");
        }
    </script>
</tags:master>