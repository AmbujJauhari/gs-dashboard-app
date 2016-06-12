<html>
<head>
    <title>Ananta GS Dashboard</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

</head>
<style type="text/css">
    .carousel {
        background-color: rgba(0, 0, 0, 0.02);
        margin-top: 3%;
        margin-left: 25%;
        height: 20%;
        width: 50%;
    }

    .carousel-caption {
        color: #843534;
        position: static;
    }

    body {
        background: url(/resources/images/nyc.jpg) center top no-repeat;
    }
</style>
<body>
<div class="container-fluid">
    <h1 class="text-center text-primary">Welcome to ananta-gs dashboard</h1>
    <div id="myCarousel" class="carousel slide" data-ride="carousel">
        <c:if test="${not empty gsEnvList}">

            <ol class="carousel-indicators">
                <c:forEach begin="0" end="${fn:length(gsEnvList)-1}" varStatus="loop">
                    <c:choose>
                        <c:when test="${loop.index == '0'}">
                            <li data-target="#myCarousel" data-slide-to="${loop.index}" class="active"></li>
                        </c:when>
                        <c:otherwise>
                            <li data-target="#myCarousel" data-slide-to="${loop.index}"></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

            </ol>
            <div class="carousel-inner">

                <c:forEach var="gsEnv" items="${gsEnvList}" varStatus="spaceIndex">

                    <c:choose>
                        <c:when test="${spaceIndex.index=='0'}">
                            <div class="item active">
                                <a href="query/queryboard.html?envName=${gsEnv.envName}">
                                    <h3 class="carousel-caption text-primary center-block">${gsEnv.envName}</h3>
                                </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="item">
                                <a href="query/queryboard.jsp?envName=${gsEnv.envName}">
                                    <h3 class="carousel-caption text-primary center-block">${gsEnv.envName}</h3>
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>
            </div>
        </c:if>
        <a class="carousel-control left" href="#myCarousel" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left"></span>
        </a>
        <a class="carousel-control right" href="#myCarousel" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right"></span>
        </a>
        <span class="help-block "><h5>Choose an environment from carousel</h5></span>
    </div>
</div>
</body>
</html>