<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="home minHeight">
    <div class="row ">
        <div class="col-md-12">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <h1 class="font">CereBros Le moteur de recherche de l'IT</h1>
                <form method="post">
                    <div class="col-md-6">
                        <input class="form-control" type="text" name="text" placeholder="mots clés" value="${text}" required=""/>
                    </div>
                    <div class="col-md-4">
                        <input class="form-control " type="text" name="departement" value="${departement}" placeholder="numéro de département ex 06" required=""/>
                    </div>
                    <input class="btn btn-default btn-primary" type="submit" value="Rechercher"/>
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
                            <p>${res.partialsClients.name}</p>
                            <p>${res.title}</p>                      
                            <p>${res.profileName}</p>
                            <p>
                                <a class="btn btn-default btn-primary" href="<c:url value="/offers/${res.id}" />">Voir l'offre</a>
                            </p>

                        </div>
                    </c:forEach>
                </div>


            </c:when>
            <c:otherwise>
                <div class="blog-teaser">
                    <p>Pas de résultat !!!</p>
                </div>

            </c:otherwise>

        </c:choose>
        <div class="col-md-2"></div>

    </div>

</div>
 <div class="row marginBottom"></div>
<script type="text/javascript">

    $(document).ready(function () {



    });
</script>