<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="row">
    <div class="col-md-2"></div>
    <div class="col-md-8 backgrondTrans">

        <form id="comments" method="post">
            <input type="hidden" name="id" value="${client.id}" /> 
            <div class="form-group">
                Nom du client
                <input  class="form-control" type="text" name="name" value="${client.name}"/>            
            </div>

            <div class="form-group">
                Email
                <input  class="form-control" type="email" name="email"value="${client.email}"/>            
            </div>
            <div class="form-group">
                Téléphone
                <input  class="form-control" type="text" name="phone" value="${client.phone}"/>            
            </div>
            <input class="btn btn-default" type="submit" value="Valider" />
        </form>
    </div>
    <div class="col-md-2"></div>
</div>