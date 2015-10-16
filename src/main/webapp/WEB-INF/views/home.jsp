<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="row  marginTop">
    <div class="col-md-4"></div>
    <div class="col-md-4">

        <div class="backgrondTrans big">
            <div id="home" class="row">
                <div class="col-md-3"> </div>
                <div class="col-md-3">
                    <a role="button" title="Connectez vous" href="<c:url value="/candidat"/>"><i class="glyphicon glyphicon glyphicon-off"></i></a>
                </div>
                <div class="col-md-3">
                    <a role="button" title="créé un compte" href="<c:url value="/signin"/>"><i class="glyphicon glyphicon glyphicon-user"></i></a>
                </div>
                <div class="col-md-3"></div>














            </div>
        </div>
    </div>
    <div class="col-md-4"></div>
</div>

<script type="text/javascript" src=" <c:url  value="/resources/js/jquery-validation/dist/jquery.validate.min.js" />"></script>
<script type="text/javascript">

    $(document).ready(function () {


//                $('.load').transition({opacity: 1}, 1500);

//        $('#step1').validate();


    });
</script>