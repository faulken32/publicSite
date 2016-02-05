<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${nofooter == true}">

    <footer class="footerAbsolute hidden-xs">	
            <div class="container-fluid"> 
                <div class="row">
                    <div class="col-sm-4 ">                   
                        {CereBROS} est un moteur de recherche dédié au offres d'emplois dans le milieu de L'informatique et de l'IT.
                    </div>   
                    <div class="col-sm-4 ">              
                        <span>{Cerebros} est basé sur les compétences et propose une recherche avancé pour les offres d'emplois
                            et les candidats. 
                        </span>
                    </div>
                    <div class="col-sm-4 ">              
                        <span>Testez-le! Il est complètement gratuit! </span>
                    </div>
                </div>
            </div>

    </footer>
</c:if>