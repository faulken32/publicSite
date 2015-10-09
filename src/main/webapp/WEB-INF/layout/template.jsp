<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="tilesx" uri="http://tiles.apache.org/tags-tiles-extras" %>




<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

        <title><tiles:insertAttribute name="title"/> </title>
      
        <script src=" <c:url value="/resources/js/jquery.min.js" />"></script>           
        <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" type="text/css"/>       
        <link rel="stylesheet" href=" <c:url value="/resources/css/main.css" />" type="text/css"/>        
        <link rel="stylesheet" href="<c:url value="/resources/js/jquery-ui/jquery-ui.min.css" />" type="text/css"/>
        <script src=" <c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script> 
         <script src=" <c:url value="/resources/js/jquery-ui/jquery-ui.js" />"></script> 
    </head>

    <body class="back">
        <tiles:insertAttribute name="header"/>
        <tiles:insertAttribute name="nav"/>

        <div class="container-fluid">
            <tiles:insertAttribute name="body" />
        </div>

    </body>
</html>

