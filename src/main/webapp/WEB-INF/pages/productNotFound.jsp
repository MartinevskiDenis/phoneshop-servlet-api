<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="productId" type="java.lang.String" scope="request"/>
<tags:master pageTitle="Product not found">
    <p><b>Product with id "${productId}" not found</b></p>
</tags:master>