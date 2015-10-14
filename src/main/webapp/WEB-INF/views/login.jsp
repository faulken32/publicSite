<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="row border-top marginTop">
    <div class="col-md-2"></div>
    <div class="col-md-8">
      
        <div class="panel backgrondTrans">
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
            </div>
        </div>
    </div>
    <div class="col-md-2"></div>
</div>

<script type="text/javascript" src=" <c:url  value="/resources/js/jquery-validation/dist/jquery.validate.min.js" />"></script>
<script type="text/javascript">

    $(document).ready(function () {


//                $('.load').transition({opacity: 1}, 1500);

//        $('#step1').validate();


    });
</script>