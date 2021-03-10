<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <h2>Advanced search</h2>
    <form>
        <p>
            Description
            <input type="text" name="searchQuery" value="${param.searchQuery}">
            <select name="searchType">
                <option value="all_words">
                    all words
                </option>
                <option value="any_word" <c:if test="${param.searchType == 'any_word'}">selected</c:if> >
                    any word
                </option>
            </select>
        </p>
        <p>
            Min price
            <input type="text" name="minPrice" value="${param.minPrice}">
            <c:if test="${not empty errorMinPrice}">
                <p style="color: red;">
                        ${errorMinPrice}
                </p>
            </c:if>
        </p>
        <p>
            Max price
            <input type="text" name="maxPrice" value="${param.maxPrice}">
            <c:if test="${not empty errorMaxPrice}">
                <p style="color: red;">
                        ${errorMaxPrice}
                </p>
            </c:if>
        </p>
        <button type="submit">Search</button>
    </form>
    <c:if test="${not empty products}">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>
                    Description
                </td>
                <td class="price">
                    Price
                </td>
            </tr>
            </thead>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>
                        <img class="product-tile" src="${product.imageUrl}">
                    </td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
                    </td>
                    <td class="price">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</tags:master>