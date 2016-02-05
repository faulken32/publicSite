<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div div="row">
    <div class="col-md-1"></div>
    <div class="col-md-10 well">
        <h2>Ajouter une annonce</h2>
        <br />
        <br />
        <form id="addDesc" method="post">
            <input name="id" type="hidden" value="${jobs.id}"/>
         
            <input name="adminValidate" type="hidden" 
                   <c:choose>
                       <c:when test="${jobs ==null || jobs.adminValidate == null || jobs.adminValidate == ''}">
                           value="false"/>                          
                       </c:when>                       
                       <c:otherwise>
                           value="${jobs.adminValidate}"/>
                       </c:otherwise>
                   </c:choose>                                      
            <label for="publish">
                Publier sur le site ?
            </label>
            <p> 
                
                <input  class="checkbox"  name="publish" type="checkbox" id="publish"
                        <c:if test="${jobs.publish}">
                            checked="true"
                        </c:if>
                        />  
            </p>
            <div class="form-group">
                <label for="compagny">
                    Titre
                </label>
                <input  class="form-control"  name="title" type="text" value="${jobs.title}" required/>  
            </div>                    
            <div class="form-group">
                <label for="desc">
                    Contenue
                </label>
                <textarea id="editor1" class="form-control" name="desc" form="addDesc" row="10">${jobs.desc}</textarea>
            </div>

            <h2>Ajouter un critere de recherche</h2>

            <div class="form-group ">
                <label>département de travail</label>
                <input  class="form-control"  name="dep" type="text" value="${jobs.dep}" required/>  
            </div>

            <div class="form-group ">
                <label>Nom du profile recherché</label>
                <input  class="form-control"  name="profileName" type="text" value="${jobs.profileName}" required/>  
            </div>
            <div class="form-group ">
                <label>Type de profile</label>
                <input  class="form-control"  name="profiType" type="text" value="${jobs.profiType}" required/>
            </div>
            <div class="form-group">
                <label>Nombre d'année d'exprérience minimum</label>
                <input  class="form-control"  name="expTotalMin" type="number" value="${jobs.expTotalMin}" required/>
            </div>
            <div class="form-group">
                <label>Nombre d'année d'exprérience max</label>
                <input  class="form-control"  name="expTotalMax" type="number" value="${jobs.expTotalMax}" required/>
            </div>
            <input  class="btn btn-default"  type="submit" value="Ok"/>
            <br />
            <br />
        </form>
        <div>
            <p>
                <c:if test="${jobs.id != null}">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target=".fade">
                        Ajouter un critrere de recherche
                    </button>
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target=".change">
                        Modidiez un critrere de recherche
                    </button>
                </c:if>
            </p>
        </div>
        <div class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Ajouter un critrere de recherche</h4>
                    </div>

                    <form id="criteria" method="post" action="<c:url value="/recrutor/step2/criteria/updateAdd/${jobs.id}/${jobs.partialsClients.id}"  />">
                        <div class="modal-body">
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group col-md-12">
                                            <label>Technologie recherché</label>
                                            <input  class="form-control"  name="technoName" type="text" value="" required/>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label>Nombre d'année</label>
                                            <input  class="form-control"  name="expDurationStart" type="number" value="" placeholder="1 to 6" required=""/>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label>Nombre d'année</label>
                                            <input  class="form-control"  name="expDurationEnd" type="number" value="" placeholder="1 to 6" required=""/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>

                            <input class="btn btn-default" type="submit" value="OK"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <table class="table table-striped">
            <tr>
                <th>Nom</th>
                <th>Debut intervale</th>
                <th>Fin intervale</th>         
            </tr>
            <c:forEach items="${jobs.technoCriterias}" var="c">                      
                <tr>
                    <td>${c.technoName}</td>
                    <td>${c.expDurationStart}</td>
                    <td>${c.expDurationEnd}</td>
                </tr>            
            </c:forEach>   
        </table>

        <!--form de modification-->                      
        <div class="modal change">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Modifiez les criteres</h4>
                    </div>

                    <form id="criteria" method="post" action="<c:url value="/recrutor/step2/criteria/techno/update/${jobs.id}/${jobs.partialsClients.id}"  />">
                        <div class="modal-body">
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-md-12">

                                        <c:forEach items="${jobs.technoCriterias}" var="c" varStatus="gridRow" begin="0" step="1"> 


                                            <div class="row">

                                                <div class="form-group col-md-4">
                                                    <label>Technologie recherché</label>
                                                    <input  class="form-control"  name="technoName-${gridRow.index}" type="text" value="${c.technoName}" required/>
                                                </div>
                                                <div class="form-group col-md-4">
                                                    <label>Nombre d'année</label>
                                                    <input  class="form-control"  name="expDurationStart-${gridRow.index}" type="number" value="${c.expDurationStart}" placeholder="1 to 6"/>
                                                </div>
                                                <div class="form-group col-md-4">
                                                    <label>Nombre d'année</label>
                                                    <input  class="form-control"  name="expDurationEnd-${gridRow.index}" type="number" value="${c.expDurationEnd}" placeholder="1 to 6"/>
                                                </div>
                                            </div>

                                        </c:forEach>   
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
                            <input class="btn btn-default" type="submit" value="OK"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>                   
    </div>   
    <div class="col-md-1"></div>
</div>

<script src="<c:url value="/resources/ckeditor/ckeditor.js"/>"></script>
<script>

    $(document).ready(function () {
        CKEDITOR.replace('editor1');
        $('#criteria').submit(function () {
        });

    });
</script>