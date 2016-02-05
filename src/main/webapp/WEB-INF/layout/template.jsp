<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="tilesx" uri="http://tiles.apache.org/tags-tiles-extras" %>




<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

        <title><tiles:insertAttribute name="title"/> </title>
        <meta name="description" content="<tiles:insertAttribute name="desc"/>">
        <script src=" <c:url value="/resources/js/jquery.min.js" />"></script>           
        <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" type="text/css"/>       
        <link rel="stylesheet" href=" <c:url value="/resources/css/main.css" />" type="text/css"/>        
        <link rel="stylesheet" href="<c:url value="/resources/js/jquery-ui/jquery-ui.min.css" />" type="text/css"/>
        <script src=" <c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script> 
         <script src=" <c:url value="/resources/js/jquery-ui/jquery-ui.min.js" />"></script> 
         <link href='https://fonts.googleapis.com/css?family=Patua+One' rel='stylesheet' type='text/css'>
         <meta name="google-site-verification" content="wNPA1pCP2DjAxeHrIR7f_JssbYd83g3j9TGGctIBAlk" />
    </head>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-73122288-1', 'auto');
  ga('send', 'pageview');

</script>
    <body class="${mainClass}">
        <tiles:insertAttribute name="header"/>
        <tiles:insertAttribute name="nav"/>

        <div class="container-fluid">
            <tiles:insertAttribute name="body" />
            
        </div>
         <tiles:insertAttribute name="footer" />
    </body>
</html>

