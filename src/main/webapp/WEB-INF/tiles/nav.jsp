<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>






<nav role="navigation" class="navbar navbar-default navbar-static-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="<c:url value="/"/>">{Cerebros beta}</a>
        <c:if test="${page == 'candidat'}">
            <form class="navbar-form navbar-left" role="search" action="<c:url value="/"/>" method="POST">
                <div class="form-group">
                    <input type="text" name="text" class="form-control" placeholder="mot clés">
                </div>
                   <div class="form-group">
                       <input type="text" name="departement" class="form-control" placeholder="Département">
                </div>
                <button type="submit" class="btn btn-default">Ok</button>
            </form>
        </c:if>
        <ul class="nav navbar-nav navbar-right">
            <c:choose>
                <c:when test="${page == 'home'}">
                    <li>
                        <a href="<c:url value="/home/recrutor" />"><strong>RECRUTEUR</strong></a> 
                    </li>
                    <li>
                        <a href="<c:url value="/home/candidat" />"><strong>CANDIDAT</strong></a> 
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