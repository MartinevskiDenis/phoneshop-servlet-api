<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="field" required="true" %>
<%@ attribute name="order" required="true" %>

<a href="?query=${param.query}&field=${field}&order=${order}" style="text-decoration: none">
  <c:choose>
    <c:when test="${order eq 'ascending'}">
      ${order eq param.order and field eq param.field ? "&#11015;" : "&darr;"}
    </c:when>
    <c:when test="${order eq 'descending'}">
      ${order eq param.order and field eq param.field ? "&#11014;" : "&uarr;"}
    </c:when>
  </c:choose>
</a>