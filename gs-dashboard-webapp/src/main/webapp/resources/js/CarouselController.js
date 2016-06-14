/**
 * Created by Aj on 14-06-2016.
 */
angular.module('ui.bootstrap.demo').controller('CarouselDemoCtrl', function ($scope, $http, dataShare, $uibModal) {
    $scope.myInterval = 5000;
    $scope.noWrapSlides = false;
    $scope.active = 0;
    var slides = $scope.slides = [];
    var currIndex = 0;

    $scope.sendEnvName = function (data) {
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
        })
        .error(function (errordata) {
            $uibModal.open({
                templateUrl: 'error/ErrorModal.html',
                controller: 'ErrModalInstanceCtrl',
                resolve: {
                    error: function () {
                        console.log('error=' + errordata.errorMessage)
                        return errordata;
                    }
                }
            });


        });

});

angular.module('ui.bootstrap.demo').controller('ErrModalInstanceCtrl', function ($scope, $uibModalInstance, error) {
    $scope.errormessage = error.errorMessage;
    $scope.stacktrace = error.stackTrace;

    $scope.ok = function () {
        $uibModalInstance.close('closed');
    };
});
