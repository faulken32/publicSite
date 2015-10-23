<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="row border-top">
    <div class="col-md-2"></div>
    <div class="col-md-8">
        <c:if test="${error == 'error'}" >
            <div class="alert alert-warning alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                Apparament vous avez déja un compte
            </div>
        </c:if>
        <div class="panel panel-default">
            <div class="panel-heading">Administratives</div>
            <div class="panel-body">
                <form id="step1"  method="post">
                    <div class="form-group">
                        <label for="pass">Email</label>
                        <input type="email" class="form-control" id="emailLogin" name="name" placeholder="" required>
                    </div>
                    <div class="form-group">
                        <label for="pass">Mot de pass</label>
                        <input type="password" class="form-control" id="pass" name="pass" placeholder="">
                    </div>
                    <div class="form-group">
                        <label for="pass2">Rétaper votre mot de pass</label>
                        <input type="password" class="form-control" id="pass2" name="pass2" placeholder="">
                    </div>
                    <input type="submit" class="btn btn-default" />
                </form>
            </div>
        </div>
    </div>
    <div class="col-md-2"></div>
</div>

<script type="text/javascript" src=" <c:url  value="/resources/js/jquery-validation/dist/jquery.validate.min.js" />"></script>
<script type="text/javascript" src=" <c:url  value="/resources/js/jquery-validation/dist/additional-methods.min.js" />"></script>
<script type="text/javascript">

    $(document).ready(function () {

        $('#step1').validate({
            rules: {
               
                pass: {
                    required: true,
                    minlength: 1
                },
                pass2: {                                   
                    equalTo: "#pass"
                }
            }
        
        });

    });
</script>