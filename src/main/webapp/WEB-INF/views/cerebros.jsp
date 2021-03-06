<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="home minHeight">
    <div class="row ">
        <div class="col-md-12">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <h1 class="font">CereBros Le moteur de recherche de l'IT</h1>
                <form method="post">
                    <div class="col-md-6 col-xs-6">
                        <input class="form-control" type="text" name="text" placeholder="mots cl�s" value="${text}" required=""/>
                    </div>
                    <div class="col-md-4 col-xs-6">
                        <input class="form-control " type="text" name="departement" value="${departement}" placeholder="num�ro de d�partement ex 06" required=""/>
                    </div>
                    <div class="col-md-2 col-xs-2">
                        <input class="btn btn-default btn-primary  col-md-12" type="submit" value="Rechercher"/>
                    </div>
                    
                </form>
            </div>
            <div class="col-md-2"></div>
        </div>
    </div>
    <div class="row marginTop">
        <div class="col-md-2"></div>
        <div class="col-md-8">

            <c:choose>
                <c:when test="${noRes != true}">


                    <c:forEach items="${res}" var="res">
                        <div class="blog-teaser">
                            <p><strong>${res.partialsClients.name}</strong></p>
                            <p><strong>${res.title}</strong> </p>                      
                            <p>${res.profileName}</p>
                            <p>
                                <a class="btn btn-default btn-primary" href="<c:url value="/offers/${res.id}" />">Voir l'offre</a>
                            </p>

                        </div>
                    </c:forEach>



                </c:when>
                <c:otherwise>
                    <div class="blog-teaser">
                        <p>Pas de r�sultat !!!</p>
                    </div>

                </c:otherwise>

            </c:choose>
        </div>
        <div class="col-md-2"></div>

    </div>

</div>
