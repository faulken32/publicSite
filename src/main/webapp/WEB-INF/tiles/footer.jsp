<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${nofooter == true}">

    <footer class="footerAbsolute hidden-xs">	
            <div class="container-fluid"> 
                <div class="row">
                    <div class="col-sm-4 ">                   
                        {CereBROS} est un moteur de recherche d�di� au offres d'emplois dans le milieu de L'informatique et de l'IT.
                    </div>   
                    <div class="col-sm-4 ">              
                        <span>{Cerebros} est bas� sur les comp�tences et propose une recherche avanc� pour les offres d'emplois
                            et les candidats. 
                        </span>
                    </div>
                    <div class="col-sm-4 ">              
                        <span>Testez-le! Il est compl�tement gratuit! </span>
                    </div>
                </div>
            </div>

    </footer>
</c:if>