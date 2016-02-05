<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid">
    <div class="row border-top">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <div class="panel panel-default">
                <div class="panel-heading">${name}</div>
                <div class="panel-body">



                    <form id="step1" action="<c:url value="/register/step1" />"  method="post">
                        <input type="hidden" name="update" value="${update}" />
                        <input type="hidden" name="enterDate" value="${candidat.enterDate}" />
                        <input type="hidden" name="candidatId" value="${candidat.id}" />
                        <div class="form-group">

                            <label for="status">Statut</label>

                            <select name="status" class="form-control">
                                <c:forEach items="${status}" var="status">
                                    <option value="${status.key}"
                                            <c:if test="${status.key == candidat.status}">
                                                selected="selected"
                                            </c:if>
                                            >${status.value}</option>
                                </c:forEach>
                            </select>

                        </div>

                        <div class="form-group">
                            <label for="email">Nom</label>
                            <input type="text" class="form-control" id="email" name="name" placeholder="" value="${candidat.name}" required>
                        </div>
                        <div class="form-group">
                            <label for="pass">Prénom</label>
                            <input type="text" class="form-control" id="pass" name="surname" placeholder="" value="${candidat.surname}" required>
                        </div>
                        <div class="form-group">
                            <label for="pass">Téléphone</label>
                            <input type="text" class="form-control" id="pass" name="phone" placeholder="" value="${candidat.phone}" required>
                        </div>
                        <div class="form-group">
                            <label for="pass">Email</label>
                            <input type="email" class="form-control" id="pass" name="email" value="${candidat.email}" placeholder="" required>
                        </div>
                        <div class="form-group">
                            <label for="pass">Préavis en mois</label>
                            <input class="form-control" type="number" name="preavis" name="preavis" value="${candidat.preavis}" required/> 
                        </div>
                        <div class="containerM">
                            <c:if test="${erroM}">
                                <div class="alert alert-danger">pas de mobilité</div>
                            </c:if>
                            <div class="form-group">
                                <label for="pass">Mobilité (le ou les numéros de département dans lesquelles vous désirez travailler)</label> =>
                                <div class="glyphicon glyphicon-plus" id="add"></div>
                                <c:forEach items="${candidat.mobilite}" var="m">                                   
                                    <input class="form-control" name="mobilite" type="number" value="${m}" placeholder="Entrer un numéro de département"/>
                                    <br>
                                    <br>
                                </c:forEach>

                            </div>
                        </div>
                        <div class="containerL">
                            <c:if test="${erroL}">
                                <div class="alert alert-danger"> pas de langues sélectionnée</div>

                            </c:if>
                            <label>Langues</label>  
                            <div class="glyphicon glyphicon-plus" id="addL"></div>
                            <div class="form-group">
                                <c:forEach items="${candidat.language}" var="l">
                                    <input class="form-control" name="language" type="text" value="${l}"/>
                                </c:forEach>

                            </div>
                        </div>
                        <div class="form-group">
                            <label for="cvContends">
                                Copier coller votre Cv (utiliser coller depuis word le cas échéant) 
                                <br>
                                <br>
                                <p class="alert alert-danger">ATTENTION PAS DE NOM, ADRESSE, DE NUMERO DE TELEPHONE DANS VOTRE CV</p>
                            </label>

                            <textarea  id="editor1" class="form-control" name="cvContends" form="step1" row="30">${candidat.cvContends}</textarea>

                        </div>
                        <input type="submit" class="btn btn-default" />
                    </form>
                </div>
            </div>
        </div>
        <div class="col-md-2"></div>
    </div>

</div>


<script type="text/javascript" src=" <c:url  value="/resources/js/jquery-validation/dist/jquery.validate.min.js" />"></script>
<script type="text/javascript" src=" <c:url  value="/resources/ckeditor/ckeditor.js" />"></script>
<script type="text/javascript">

    $(document).ready(function () {

        CKEDITOR.replace('editor1');
//                $('.load').transition({opacity: 1}, 1500);

        $('#step1').validate();

        $('#add').click(function () {
            $('.containerM').append('<input class="form-control" type="text" name="mobilite" /><br><br>');
        });
        $('#addL').click(function () {
            $('.containerL').append('<input class="form-control" type="text" name="language" /><br><br>');
        });

    });
</script>