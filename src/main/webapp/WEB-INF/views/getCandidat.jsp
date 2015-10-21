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

                    <a role="button" class="pull-right gray"  role="button" title="Modifier vos données" href="<c:url value="/register/step1/${candidat.id}/true"/>" >
                        <i class="glyphicon glyphicon-pencil">  </i>
                    </a>
                    <a role="button" class="pull-right gray"  role="button" title="changer de mot de passe" href="<c:url value="/candidat/changePass" />" >
                        <i class="glyphicon glyphicon-lock"></i>
                    </a>
                </div>


            </div>
            <div>   
                <div class="row gray">
                    <div class="col-md-3">
                        <h4> ${candidat.surname} ${candidat.name}</h4>
                    </div>
                    <div class="col-md-9">
                        <h4>
                            <span class="label label-default">
                                ${candidat.status} 
                            </span> 
                            <small> &nbsp; &nbsp;         ${candidat.preavis} mois de préavis</small>
                        </h4>
                        <c:if test="${candidat.status == 'pas en recherche'}" >
                            <span>Attention vous n'apparaîtrai pas dans les résultats de recherche</span>
                        </c:if>

                    </div>
                </div>

                <div class="row gray">
                    <div class="col-md-3">
                        <abbr> ${candidat.email} </abbr>
                    </div>
                    <div class="col-md-9">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 gray">
                        <abbr> ${candidat.phone}</abbr>
                        <br/>
                        <br/>
                    </div>
                    <div class="col-md-9">

                    </div>

                </div>
                <div class="row">
                    <div class="col-md-3">
                        <p>
                            <c:forEach items="${candidat.mobilite}" var="m">

                                <kbd class="grayKbd">${m}</kbd>
                            </c:forEach>
                        </p>
                    </div>
                    <div class="col-md-9">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 gray">
                        <p>
                            <c:forEach items="${candidat.language}" var="l">
                                ${l}                          
                            </c:forEach>
                        </p>
                    </div>
                    <div class="col-md-9">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 gray">                    


                    </div>
                    <div class="col-md-9">

                    </div>
                </div>
            </div>
        </div>

        <!--                <div class="backgrondTrans">
                            <div class="">
                                <h4> Graphiques  <span  class="glyphicon glyphicon-pencil"></span></h4>
                            </div>
                            <div>   
                                <div class="row gray">
                                    <iframe id="kibana" src="http://localhost:5601/#/visualize/create?embed&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'*')),vis:(aggs:!((id:'1',params:(field:duration),schema:metric,type:sum),(id:'2',params:(field:tecnoList,order:desc,orderBy:'1',size:5),schema:segment,type:terms)),listeners:(),params:(addLegend:!f,addTimeMarker:!f,addTooltip:!f,defaultYExtents:!f,mode:stacked,scale:linear,setYExtents:!f,shareYAxis:!t,times:!(),yAxis:()),type:histogram))&indexPattern=cvdb2&type=histogram&_g=()" height="600" width="800" style="border:none;"></iframe>
                                </div>
                            </div>
                        </div>-->
        <div class="backgrondTrans">

            <h4>Votre CV</h4>
            <div class="control">
                <a  role="button" data-toggle="collapse" href="#collapseCV" aria-expanded="false" aria-controls="collapseExample">
                    <i class="glyphicon glyphicon-menu-down"></i>
                </a>




            </div>

            <div class="row collapse"  id="collapseCV">
                <div class="col-md-12 marginTop">
                    ${candidat.cvContends}
                </div>
            </div>


        </div> 

        <div class="backgrondTrans">

            <h4>Vos Expériences</h4>
            <div class="control">
                <a  role="button" data-toggle="collapse" href="#collapseEXP" aria-expanded="false" aria-controls="collapseExample">
                    <i class="glyphicon glyphicon-menu-down"></i>
                </a>
                <a role="button" title="Ajoutez une expérience" href="<c:url value="/register/step2"/>">
                    <i class="glyphicon glyphicon-plus-sign"></i>
                </a>
            </div>

            <div class="row collapse"  id="collapseEXP">
                <div class="col-md-12 marginTop gray">
                    <c:if test="${noExp}">
                        Vous n'avez pas d'expériences de renseignées. 
                        <a href="<c:url value="/register/step2"/>">Ajouter</a>
                    </c:if>
                    <c:forEach items="${exp}" var="exp">
                        <div class="col-md-10 backgrondTrans center-block gray" id="blockFor${exp.id}">
                            <div class="control">
                                <a role="button" href="#" id="${exp.id}" >
                                    <i class="glyphicon glyphicon-remove exp-remove"></i>
                                </a>
                                <a  role="button"  href="<c:url value="/register/step2/${exp.id}/true"/>">
                                    <i class="glyphicon glyphicon-pencil"></i>
                                </a>
                                <a  role="button" data-toggle="collapse" href="#c${exp.id}" aria-expanded="false" aria-controls="c${exp.id}">
                                    <i class="glyphicon glyphicon-menu-down"></i>
                                </a>
                            </div>
                            <div>
                                <h4>  ${exp.compagny} <small> ${exp.title} </small> </h4>
                            </div>
                            <div id="c${exp.id}" class="collapse">
                                <div>
                                    <p>Du ${exp.start}  au  ${exp.end}   ${exp.duration} </p>  
                                </div>
                                <div>
                                    <div class="form-group">
                                        ${exp.expContend}
                                    </div>
                                </div>
                                <div>
                                    <c:forEach items="${exp.tecnoList}" var="techno">
                                        <kbd>${techno}</kbd>
                                    </c:forEach>   
                                </div>
                            </div>
                        </div>
                    </c:forEach>       


                </div>            
            </div>
        </div>
        <div class="backgrondTrans">

            <h4>Vos Formations</h4>
            <div class="control">
                <a  role="button" data-toggle="collapse" href="#collapseSchool" aria-expanded="false" aria-controls="collapseExample">
                    <i class="glyphicon glyphicon-menu-down"></i>
                </a>
                <a role="button" title="Ajoutez une expérience" href="<c:url value="/register/step3/"/>">
                    <i class="glyphicon glyphicon-plus-sign"></i>
                </a>
            </div>

            <div class="row collapse"  id="collapseSchool">
                <div class="col-md-12 marginTop gray">
                    <c:if test="${noSchool}">
                        Vous n'avez pas de formation renseignées. 
                        <a href="<c:url value="/register/step3"/>">Ajouter</a>
                    </c:if>
                    <c:forEach items="${school}" var="school">
                        <div class="col-md-10 backgrondTrans center-block gray" id="blockFor${school.id}">
                            <div class="control"> 
                                <a role="button" href="#" id="${school.id}" >
                                    <i class="glyphicon glyphicon-remove school-remove"></i>
                                </a>
                                <a  role="button"  href="<c:url value="/register/step3/${school.id}"/>">
                                    <i class="glyphicon glyphicon-pencil"></i>
                                </a>
                                <a  role="button" data-toggle="collapse" href="#c${school.id}" aria-expanded="false" aria-controls="${school.id}">
                                    <i class="glyphicon glyphicon-menu-down"></i>
                                </a>
                            </div>

                            <div>
                                <p>
                                <h4> ${school.school}</h4>
                                </p>
                            </div>
                            <div id="c${school.id}" class="collapse">
                                <div>
                                    <p>
                                        ${school.start}
                                    </p>
                                </div>
                                <div>
                                    <p>
                                        ${school.title}
                                    </p>
                                </div>
                                <div>
                                    ${school.sContend}
                                </div>
                            </div>
                        </div>
                    </div>

                </c:forEach>      
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
//
        $("#modif").click(function () {
            document.location = '<c:url value="/register/step1/${candidat.id}/true"/>';
        });

        $('.exp-remove').click(function () {

            var parentId = $(this).parent().attr('id');
            var r = confirm("Sure !");
            if (r !== true) {
                return  false;
            } else {
//              
                $.ajax({
                    url: "<c:url value="/register/del/"/>" + $(this).parent().attr('id')
                }).always(function (data) {
//                    console.log(data.responseText)
//                    console.log(parentId);
                    $('#blockFor' + parentId).remove();


                });
            }
        });

        $('.school-remove').click(function () {

            var parentId = $(this).parent().attr('id');
            var r = confirm("Sure !");
            if (r !== true) {
                return  false;
            } else {
//              
                $.ajax({
                    url: "<c:url value="/register/del/school/"/>" + $(this).parent().attr('id')
                }).always(function (data) {
//                    console.log(data.responseText)
//                    console.log(parentId);
                    $('#blockFor' + parentId).remove();


                });
            }
        });
    });

</script>



