<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${nofooter == true}">

    <footer class= <c:choose>
                <c:when test="${normalFooter == true}">
                    "footerAbsolute"
                </c:when>
                <c:otherwise>
                    "footerAbsolute"
                </c:otherwise>
            </c:choose>   
	>	


            <div class="container-fluid"> 
                <div class="row">
                    <div class="col-md-4 footer-widget">                   
                        {CereBROS} est un moteur de rechere d�di� au offres d'emplois dans le milieu de L'informatique et de l'IT.
                    </div>   
                    <div class="col-md-4 footer-widget">              
                        <span>{Cerebros} est bas� sur les comp�tances et propose une recherche avanc� pour les offres d'emplois
                            et les candidats. 
                        </span>
                    </div>
                    <div class="col-md-4 footer-widget">              
                        <span>Test� le il est completement gratuit !</span>
                    </div>
                </div>
            </div>

    </footer>
</c:if>