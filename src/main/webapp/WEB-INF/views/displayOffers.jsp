<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="home">

    <c:if test="${offers != null}">
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">                             
                <div class="well">
                    <c:if test="${noAuth == true}">vous devez être connecté pour postuler</c:if>
                    <p>Société  : ${offers.partialsClients.name}</p>
                    <p>Titre de l'offre  : ${offers.title}</p>                      
                    <p>Nom du profil  : ${offers.profileName}</p>

                    <p>${offers.desc}</p>
                    <c:if test="${noAuth == true}">
                        <p class="error">Vous devez être connecté pour postuler
                        </p>

                        <a class="btn btn-primary" href="<c:url value="/login"/>">Se connecter</a>

                    </c:if>  

                    <a  class="btn btn-primary" href="<c:url value="/offers/apply/${offers.id}"/>">Postuler</a>
                    <c:if test="${alreadyApply == true}">
                        <br/>
                        <br/>
                        <div class="alert alert-danger alert-dismissable"> Il semblerai que vous avec déjà postulé à cette offre
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        </div>  

                    </c:if>
                </div>               
            </div>
            <div class="col-md-2"></div>
        </div>
    </c:if>
</div>

<script type="text/javascript">

    $(document).ready(function () {



    });
</script>