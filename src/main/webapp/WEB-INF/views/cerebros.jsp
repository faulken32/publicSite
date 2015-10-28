<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="home">
    <div class="row marginTop">
        <div class="col-md-12 backgrondTrans">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <form method="post">
                    <div class="col-md-6">
                        <input class="form-control " type="text" name="text" placeholder="mots clé" required=""/>
                    </div>
                    <div class="col-md-4">
                        <input class="form-control " type="text" name="departement" placeholder="numéro de département ex 06" required=""/>
                    </div>
                    <input class="btn btn-default" type="submit" value="Rechercher"/>
                </form>
            </div>
            <div class="col-md-2"></div>
        </div>
    </div>
    <c:if test="${noRes != true}">
        <div class="row marginTop backgrondTransWhite">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <div>Résultat de votre recherche</div>
                <c:forEach items="${res}" var="res">
                    <div class="well">
                        <p>${res.partialsClients.name}</p>
                        <p>${res.title}</p>                      
                        <p>${res.profileName}</p>
                    </div>
                </c:forEach>
            </div>
            <div class="col-md-2"></div>

        </div>
    </c:if>
</div>

<script type="text/javascript">

    $(document).ready(function () {



    });
</script>