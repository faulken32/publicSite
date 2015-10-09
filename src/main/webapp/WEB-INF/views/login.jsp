<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="row border-top">
    <div class="col-md-2"></div>
    <div class="col-md-8">
      
        <div class="panel panel-default">
            <div class="panel-heading">Connecter vous</div>
            <div class="panel-body">
                <form id="login" method="post">

                    <div class="form-group">
                        <label for="pass">Email</label>
                        <input type="email" class="form-control" id="pass" name="name" placeholder="" required>
                    </div>
                    <div class="form-group">
                        <label for="pass">Mot de pass</label>
                        <input type="password" class="form-control" id="pass" name="pass" placeholder="" required>
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