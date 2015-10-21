<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid">
    <div class="row border-top">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <div class="panel panel-default">
                <div class="panel-heading">Ajoutez une nouvelle formation</div>
                <div class="panel-body">
                    <form id="addexp" method="post">
                    

                        <div class="form-group">
                            <label for="school">
                                Ecole
                            </label>
                            <input  class="form-control"  id="school" name="school" type="text" value="${school.school}"/>  
                        </div>
                        <div class="form-group">
                            <label for="title">
                                Titre
                            </label>
                            <input  class="form-control"  id="title" name="title" type="text" value="${school.title}"/>  
                        </div> 
                        <div  class="form-group">
                            <label for="start">
                                Année
                            </label>
                            <input  class="form-control"  id="start" name="start" type="text" value="${school.start}"/>  
                        </div>                       
                        <div class="form-group">
                            <label for="sContend">
                                Contenue
                            </label>
                            <textarea id="editor1" class="form-control" name="sContend" form="addexp" row="10">${school.sContend}</textarea>
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
        $("#start").datepicker({
            dateFormat: "yy-mm-dd",
           
        });


     

        CKEDITOR.replace('editor1');
        $("#addexp").validate({
            rules: {
                // no quoting necessary
                start: {required: true,
                    dateISO: true,
                    dateFormat: "yyyy-dd-mm"
                }
            }
        });

    });




</script>