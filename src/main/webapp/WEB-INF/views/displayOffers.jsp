<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="home">
    
    <c:if test="${offers != null}">
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">                             
                    <div class="well">
                        <p>Société  : ${offers.partialsClients.name}</p>
                        <p>Titre de l'offre  : ${offers.title}</p>                      
                        <p>Nom du profil  : ${offers.profileName}</p>

                        <p>${offers.desc}</p>
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