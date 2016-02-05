<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

graph view 

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/1.0.2/Chart.min.js" ></script>

<%--<c:forEach items="${graph}" var="g">

    <p>${g.key}</p>
    <p>${g.value}</p>

</c:forEach>--%>

<canvas id="myChart" width="400" height="400"></canvas>

<script>
    Chart.defaults.global.responsive = true;
    var ctx = document.getElementById("myChart").getContext("2d");
    var data = {
        labels:[<c:forEach items="${graph}" var="g">"${g.key}",</c:forEach>],
        datasets: [
            {
                label: "My First dataset",
                fillColor: "rgba(220,220,220,0.5)",
                strokeColor: "rgba(220,220,220,0.8)",
                highlightFill: "rgba(220,220,220,0.75)",
                highlightStroke: "rgba(220,220,220,1)",
                data: [<c:forEach items="${graph}" var="g">"${g.value}",</c:forEach>]
            }]
    };
    
    var options = null;
    var myBarChart = new Chart(ctx).Bar(data, options);

</script>
