/**
 * Created by Aj on 14-06-2016.
 */
angular.module('dashboardApp').controller('CarouselDemoCtrl', function ($scope, $http, dataShare, $uibModal) {
    $scope.myInterval = 5000;
    $scope.noWrapSlides = false;
    $scope.active = 0;
    $scope.errorShow = false;
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
            var envs = data.spaceLookUpDetailsList;
            for (var i in envs) {
                $scope.addSlide(envs[i].envName);
            }
            
            
            if(data.exceptions) {
                $scope.errorShow = true;
                $scope.exceptions = data.exceptions;
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

    $scope.toggle = function () {
        $scope.checked = !$scope.checked;
    }

});

angular.module('dashboardApp').controller('ErrModalInstanceCtrl', function ($scope, $uibModalInstance, error) {
    $scope.errormessage = error.errorMessage;
    $scope.stacktrace = error.stackTrace;

    $scope.ok = function () {
        $uibModalInstance.close('closed');
    };
});
