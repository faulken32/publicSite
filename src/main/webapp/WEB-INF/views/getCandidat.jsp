<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<div class="row">
    <br>
    <br>

    <div class="col-md-3"> <div class="panel panel-default">
            <div class="panel-heading">Navigation</div>
            <div class="panel-body">   
                <div id="data">
                    menu
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-8 backgrondTrans">

        <div class="">
            <div class="">Données personelles <span id="modif" class="glyphicon glyphicon-pencil"></span></div>
            <div class="">   
                <div class="row gray">
                    <div class="col-md-3">
                        <h4> ${candidat.surname} ${candidat.name}</h4>
                    </div>
                    <div class="col-md-9">
                        <h4>
                            <span class="label label-default">

                                ${candidat.status} 
                            </span> 
                        </h4>
                        <c:if test="${candidat.status == 'pas en recherche'}" >
                            <span>Attention vous n'apparaîtrai pas dans les résultats de recherche</span>
                        </c:if>

                    </div>
                </div>

                <div class="row gray">
                    <div class="col-md-3">
                        ${candidat.email}
                    </div>
                    <div class="col-md-9">

                    </div>

                </div>

                <div class="row">
                    <div class="col-md-3">

                    </div>
                    <div class="col-md-9">

                    </div>

                </div>

                <div class="row">
                    <div class="col-md-3 gray">
                        ${candidat.phone}
                    </div>
                    <div class="col-md-9">

                    </div>

                </div>
                <div class="row">
                    <div class="col-md-3">
                        <c:forEach items="${candidat.mobilite}" var="m">

                            <kbd>${m}</kbd>
                        </c:forEach>

                    </div>
                    <div class="col-md-9">
                    </div>

                </div>

                <div class="row">
                    <div class="col-md-3 gray">
                        <c:forEach items="${candidat.language}" var="l">
                            ${l}                          
                        </c:forEach>
                    </div>
                    <div class="col-md-9">

                    </div>

                </div>

                <div class="row marginTop center-block">

                    <div class="col-md-12">


                        ${candidat.cvContends}
                    </div>

                </div>

            </div>
        </div>
        <div class="row">
            Exp 
        </div>            
    </div>

    <div class="col-md-1"></div>



</div>


<script type="text/javascript">

    $("#modif").click(function () {
        document.location = '<c:url value="/register/step1/${candidat.id}/true"/>';
//            $('#data').each(function () {
//
////                console.log($(this).text());
//            });

    });
</script>
