<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid">
    <div class="row border-top">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <div class="panel panel-default">
                <div class="panel-heading">Ajoutez une nouvelle expérience profésionnelle</div>
                <div class="panel-body">
                    <form id="addexp" method="post">

                       
                        <div class="form-group">
                            <label for="compagny">
                                Compagnie
                            </label>
                            <input  class="form-control"  id="compagny" name="compagny" type="text" value="" required/>  
                        </div>
                        <div class="form-group">
                            <label for="title">
                                Titre
                            </label>
                            <input  class="form-control"  id="title" name="title" type="text" value="" required/>  
                        </div> 
                        <div  class="form-group">
                            <label for="start">
                                Début
                            </label>
                            <input  class="form-control"  id="start" name="start" type="text" value="yyyy-mm-dd" required/>  
                        </div> 
                        <div  class="form-group">
                            <label for="end">
                                Fin
                            </label>
                            <input  class="form-control"  id="end" name="end" type="text" value="yyyy-mm-dd" required/>  
                        </div>
                        <div class="form-group">
                            <label for="expContend">
                                Contenue
                            </label>


                            <textarea  id="editor1" class="form-control" name="expContend" form="addexp" row="10"></textarea>
                        </div>
                        <div class="form-group">
                            <label> Technologie séparé par des virgules</label>
                            <input class="form-control" type="text" name="technoListblock" required/>
                        </div>

                        <input  class="btn btn-default"  type="submit" value="Ajouter" />
                        <a  class="btn btn-default" href="<c:url value="/register/step3/${candidatId}"/>"  >Etape suivante</a>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-md-2"></div>
    </div>

    <!--            <div class="row row2">
                    <div class="col-md-12 footer">
                        <p>
                            SARL Infinity web 809 840 119
                            CopyRight Infinity web 2015 tous Droit réservés</p>
                    </div>
                </div>-->
</div>


<script type="text/javascript" src=" <c:url  value="/resources/js/jquery-validation/dist/jquery.validate.min.js" />"></script>
<script type="text/javascript" src=" <c:url  value="/resources/ckeditor/ckeditor.js" />"></script>
<script type="text/javascript">

    $(document).ready(function () {
        
        
        CKEDITOR.replace('editor1');
        $("#start").datepicker({
            
            dateFormat: "yy-mm-dd",
            regional: "fr" 
        });
        
        
         $("#end").datepicker({
           
            dateFormat: "yy-mm-dd"
        });

    });
</script>