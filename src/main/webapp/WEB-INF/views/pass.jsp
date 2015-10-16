<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="row border-top marginTop">
    <div class="col-md-2"></div>
    <div class="col-md-8">

        <div class="panel backgrondTrans">
            <div class="panel-heading">Modification du mot de passe</div>
            <div class="panel-body">

                <c:if test="${errorOld}">
                    <div class="alert alert-danger" aria-hidden="false" role="alert">Le mot de pass rentré ne correspond pas à votre ancien mot de passe
                    <!--<button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>-->
                    </div>
                </c:if>
                
                 <c:if test="${errorPass}">
                    <div class="alert alert-danger" aria-hidden="false" role="alert">Les 2 mots de pass ne correspondent pas 
                    <!--<button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>-->
                    </div>
                </c:if>
                
                
                  <c:if test="${succes}">
                    <div class="alert alert-success" aria-hidden="false" role="alert">Modification enregistré 
                    <!--<button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>-->
                    </div>
                </c:if>
                
               
               
                <form id="login" method="post">

                    <div class="form-group">
                        <input type="password" class="form-control inputTrans" id="pass" name="oldPass" placeholder="Ancien mot de pass" required>
                    </div>
                    <div class="form-group">

                        <input type="password" class="form-control inputTrans" id="pass" name="pass" placeholder="Nouveau mot de passe" required>

                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control inputTrans" id="pass" name="pass2" placeholder="Retaper votre nouveau  mot de passe" required>
                    </div>




                    <input type="submit" class="btn btn-default" />
                </form>
            </div>
        </div>
    </div>
    <div class="col-md-2"></div>
</div>

<script type="text/javascript" src=" <c:url  value="/resources/js/jquery-validation/dist/jquery.validate.min.js" />"></script>
<script type="text/javascript">

    $(document).ready(function () {
    });
</script>