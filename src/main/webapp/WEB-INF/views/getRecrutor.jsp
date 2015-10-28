<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <br>
    <br>


    <div class="col-md-1"></div>
    <div class="col-md-10 ">
        <div class="backgrondTrans">
            <div class="row">
                <div class="col-md-3"><h4>Données personelles</h4></div>
                <div class="col-md-9 control">

                    <a role="button" class="pull-right gray"  role="button" title="Modifier vos données" href="<c:url value="/recrutor/step1"/>" >
                        <i class="glyphicon glyphicon-pencil">  </i>
                    </a>
                    <a role="button" class="pull-right gray"  role="button" title="changer de mot de passe" href="<c:url value="/recrutor/changePass" />" >
                        <i class="glyphicon glyphicon-lock"></i>
                    </a>
                </div>


            </div>
            <div>   
                <div class="row gray">
                    <div class="col-md-3">
                        <h4> ${client.name} </h4>
                    </div>
                    <div class="col-md-9">


                    </div>
                </div>

                <div class="row gray">
                    <div class="col-md-3">
                        <abbr> ${client.email} </abbr>
                    </div>
                    <div class="col-md-9">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 gray">
                        <abbr> ${client.phone}</abbr>
                        <br/>
                        <br/>
                    </div>
                    <div class="col-md-9">
                    </div>

                </div>
            </div>
        </div>
        <div class="backgrondTrans">

            <h4>Vos annonces</h4>
            <div class="control">
                <a  role="button" data-toggle="collapse" href="#collapseEXP" aria-expanded="false" aria-controls="collapseExample">
                    <i class="glyphicon glyphicon-menu-down"></i>
                </a>
                <a role="button" title="Ajoutez une annonces" href="<c:url value="/recrutor/step2"/>">
                    <i class="glyphicon glyphicon-plus-sign"></i>
                </a>
            </div>
            <div class="row collapse"  id="collapseEXP">
                <div class="col-md-12 marginTop gray">
                    <c:if test="${all.size() == 0}">
                        Vous n'avez pas d'annonces de renseignées. 
                        <a href="<c:url value="/recrutor/step2"/>">Ajouter</a>
                    </c:if>
                    <c:forEach items="${all}" var="exp">
                        <div class="col-md-10 backgrondTrans center-block gray" id="blockFor${exp.id}">
                            <div class="control">
                                <a role="button" href="#" id="${exp.id}" >
                                    <i class="glyphicon glyphicon-remove exp-remove"></i>
                                </a>
                                <a  role="button"  href="<c:url value="/recrutor/step2/${exp.id}"/>">
                                    <i class="glyphicon glyphicon-pencil"></i>
                                </a>
                                <a  role="button" data-toggle="collapse" href="#c${exp.id}" aria-expanded="false" aria-controls="c${exp.id}">
                                    <i class="glyphicon glyphicon-menu-down"></i>
                                </a>
                            </div>
                            <div><h4>
                                    ${exp.title} ${exp.lastUpdateDate} <c:if test="${exp.publish}"> Publier </c:if>
                                </h4>
                            </div>
                            <div id="c${exp.id}" class="collapse">
                                <div>
                                    <p>  ${exp.profileName}   </p>  
                                </div>
                                <div>
                                    <p>  ${exp.profiType}   </p>  
                                </div>
                                <div>
                                    Département <kbd class="grayKbd"> ${exp.dep}</kbd> 
                                    entre <kbd class="grayKbd">${exp.expTotalMin}</kbd> 
                                    et <kbd class="grayKbd">${exp.expTotalMax}</kbd>
                                    années d'exprériences
                                </div>
                                <div>
                                    <div class="form-group">
                                        ${exp.desc}
                                    </div>
                                </div>
                               
                            </div>
                        </div>         
                    </c:forEach>       



                </div>   
            </div>
        </div>
        <div class="col-md-1"></div>



    </div>
</div>
</div> 


<script type="text/javascript">

//.css("background-color", "red!important")
    $(document).ready(function () {
//            //            $("#kibana").load(function () { 
//            //                 $("#kibana").contents().find("body").html("ttttrt");
//            //            } );
//
//
////
//        $("#modif").click(function () {
//            document.location = '<c:url value="/register/step1/${candidat.id}/true"/>';
//        });
//
//        $('.exp-remove').click(function () {
//
//            var parentId = $(this).parent().attr('id');
//            var r = confirm("Sure !");
//            if (r !== true) {
//                return  false;
//            } else {
////              
//                $.ajax({
//                    url: "<c:url value="/register/del/"/>" + $(this).parent().attr('id')
//                }).always(function (data) {
////                    console.log(data.responseText)
////                    console.log(parentId);
//                    $('#blockFor' + parentId).remove();
//
//
//                });
//            }
//        });
//
//       
    });

</script>



