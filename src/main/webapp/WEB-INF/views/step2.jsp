<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid">
    <div class="row border-top">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <div class="panel panel-default">
                <div class="panel-heading">Ajoutez une nouvelle expérience profésionnelle</div>
                <div class="panel-body">
                    <form id="addexp" method="post" action="<c:url value="/register/step2/${exp.id}"/>">
                        <input type="hidden" name="update" value="${update}" />
                        <div class="form-group">
                            <label for="compagny">
                                Compagnie
                            </label>
                            <input  class="form-control"  id="compagny" name="compagny" type="text" value="${exp.compagny}" required/>  
                        </div>
                        <div class="form-group">
                            <label for="title">
                                Titre
                            </label>
                            <input  class="form-control"  id="title" name="title" type="text" value="${exp.title}" required/>  
                        </div> 
                        <div  class="form-group">
                            <label for="start">
                                Début
                            </label>
                            <input  class="form-control"  id="start" name="start" type="text" placeholder="yyyy-mm-dd" value="${exp.start}" required/>  
                        </div> 
                        <div  class="form-group">
                            <label for="end">
                                Fin
                            </label>
                            <input  class="form-control"  id="end" name="end" type="text"  value="${exp.end}" placeholder="yyyy-mm-dd"  required/>  
                        </div>
                        <div class="form-group">
                            <label for="expContend">
                                Contenue
                            </label>


                            <textarea  id="editor1" class="form-control" name="expContend" form="addexp" row="10">
                                ${exp.expContend}
                            </textarea>
                        </div>
                        <div class="form-group">
                            <label> Technologie séparé par des virgules</label>
                            <input class="form-control" type="text" name="technoListblock" value="${techno}" required/>   
                        </div>

                        <input  class="btn btn-default"  type="submit" value="Valider" />
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
        $("#start").datepicker({
            dateFormat: "yy-mm-dd"
           
        });


        $("#end").datepicker({
            dateFormat: "yy-mm-dd"
        });


        $("#addexp").validate({
            rules: {
                // no quoting necessary
                start: {required: true,
                    dateISO: true,
                    dateFormat: "yy-mm-dd"
                },
                end: {required: true,
                    dateISO: true,
                    dateFormat: "yy-mm-dd"
                }

            }
        });

    });
</script>