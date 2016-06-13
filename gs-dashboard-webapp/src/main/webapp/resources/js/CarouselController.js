/**
 * Created by Aj on 14-06-2016.
 */
angular.module('ui.bootstrap.demo').controller('CarouselDemoCtrl', function ($scope, $http, dataShare) {
    $scope.myInterval = 5000;
    $scope.noWrapSlides = false;
    $scope.active = 0;
    var slides = $scope.slides = [];
    var currIndex = 0;

    $scope.sendEnvName = function(data) {
        dataShare.sendEnvDetails(data);

        window.location.href = "query/queryboard.html";
    }

    $scope.addSlide = function (envName) {
        slides.push({
            text: envName,
            id: currIndex++
        });
    };

    $http.get("http://localhost:8080/getEnvList")
        .success(function (data) {
            for (var i in data) {
                $scope.addSlide(data[i].envName);
            }
        });

});
