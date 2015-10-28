<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>






<nav role="navigation" class="navbar navbar-default navbar-static-top">
    <div class="container-fluid">
        <ul class="nav navbar-nav navbar-right">
            <c:choose>
                <c:when test="${page == 'home'}">
                    <li>
                        <a href="<c:url value="/home/recrutor" />">RECRUTEUR</a> 
                    </li>
                    <li>
                        <a href="<c:url value="/home/candidat" />">CANDIDAT</a> 
                    </li>

                </c:when>
                <c:otherwise>
                    <li>
                        <a class="glyphicon glyphicon-off" href="<c:url value="/logout" />"></a>
                    </li>
                    <li>
                        <c:choose >
                            <c:when test="${authType == 'client'}">
                                <a class="glyphicon glyphicon-user" href="<c:url value="/recrutor/info" />"></a>
                            </c:when>
                            <c:otherwise>
                                <a class="glyphicon glyphicon-user" href="<c:url value="/candidat" />"></a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>

    </div>
</nav>