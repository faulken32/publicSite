<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<nav role="navigation" class="navbar navbar-default">
    <div class="container-fluid">
        <ul class="nav navbar-nav navbar-right">
            <li>
                <a class="glyphicon glyphicon-off" href="<c:url value="/logout" />"></a>
            </li>
             <li>
                <a class="glyphicon glyphicon-user" href="<c:url value="/candidat" />"></a>
            </li>
        </ul>

    </div>
</nav>