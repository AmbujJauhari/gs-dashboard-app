<html ng-app="ui.bootstrap.demo">
<head>
    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular-animate.js"></script>
    <script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.3.3.js"></script>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://rawgit.com/esvit/ng-table/master/dist/ng-table.min.js"></script>
    <script src="resources/js/main.js"></script>
    <script src="resources/js/CarouselController.js"></script>
    <script src="resources/js/gsenv.js"></script>
</head>
<body>

<style type="text/css">
    body {
        background: url(/resources/images/nyc.jpg) center top no-repeat;
    }
</style>
<div ng-controller="CarouselDemoCtrl" class="container-fluid">
    <h1 class="text-center text-primary">Welcome to ananta-gs dashboard</h1>
    <div style="height: 305px; width: 450px; margin:auto">
        <uib-carousel active="active" interval="myInterval" no-wrap="noWrapSlides">
            <uib-slide ng-repeat="slide in slides track by slide.id" index="slide.id">
                <img ng-src="/resources/images/cloud-home.jpg" style="margin:auto;">
                <div class="carousel-caption">
                    <a href="#" ng-click='sendEnvName(slide.text)'>
                        <h3 class="carousel-caption text-primary center-block" >
                            {{slide.text}}</h3>
                    </a>
                </div>
            </uib-slide>
        </uib-carousel>
    </div>
</div>
</body>
</html>
