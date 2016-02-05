
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<c:choose>
    <c:when test="${msg !=null}">
        <p>${msg}</p>
    </c:when>
    <c:otherwise><p>Désolé je ne connais pas cette page</p></c:otherwise>
    
</c:choose>


