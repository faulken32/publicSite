<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="row border-top marginTop">
    <div class="col-md-2"></div>
    <div class="col-md-8">
        <div class="panel backgrondTrans">
            <c:if test="${param.error !=null}">
                <div class="alert alert-danger alert-dismissable">Couple Utilisateur/mot de pass invalide! Avez-vous créer un compte ?
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                </div>    
            </c:if>
            <div class="panel-heading">Connecter vous</div>
            <div class="panel-body">
                <form id="login" method="post">
                    <div class="form-group">                      
                        <input type="email" class="form-control inputTrans" id="pass" name="name" placeholder="Email" required>
                    </div>
                    <div class="form-group">                      
                        <input type="password" class="form-control inputTrans" id="pass" name="pass" placeholder="Mot de pass" required>
                    </div>                    
                    <input type="submit" class="btn btn-default" />
                </form>
                <br>
                <div>
                    <p> 
                        <a class="btn btn-default"  href="">
                            Mot de passe oublié ?
                        </a>
                        <a class="btn btn-default" href="<c:url value="/signin"/>">
                            Créé un compte candidat !
                        </a>
                        <a class="btn btn-default" href="<c:url value="/signin/recrutor"/>">
                            Créé un compte recruteur !

                        </a>

                    </p>
                </div>

            </div>
        </div>
    </div>
    <div class="col-md-2"></div>
</div>

<script type="text/javascript" src=" <c:url  value="/resources/js/jquery-validation/dist/jquery.validate.min.js" />"></script>
<script type="text/javascript">

    $(document).ready(function () {


        $("#login").validate({
            rules: {
                // no quoting necessary
                pass: {
                    required: true,
                }
            }
        });


    });
</script>