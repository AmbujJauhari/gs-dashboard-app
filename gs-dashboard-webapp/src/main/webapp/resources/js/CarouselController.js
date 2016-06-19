/**
 * Created by Aj on 14-06-2016.
 */
angular.module('ui.bootstrap.demo').controller('CarouselDemoCtrl', function ($scope, $http, $uibModal, $location) {
    $scope.myInterval = 5000;
    $scope.noWrapSlides = false;
    $scope.active = 0;
    $scope.showError = false;
    var slides = $scope.slides = [];
    var currIndex = 0;

    $scope.addSlide = function (envName) {
        slides.push({
            text: envName,
            id: currIndex++
        });
    };

    $http.get("http://localhost:8080/getEnvList")
        .success(function (data) {
            var envs = data.gridNames;
            for (var i in envs) {
                $scope.addSlide(envs[i]);
            }
            
            if (data.exceptions) {
                // the array is defined and has at least one element
                console.log('inside if'+data.exceptions[0])

                $scope.showError = true;
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
        alert("toggle clicked");
        $scope.checked = !$scope.checked;
    }
    
    $scope.sendEnvName = function (gridName) {
        alert(gridName)
        $location.path("/queryBoard/"+gridName);
    }

});

angular.module('ui.bootstrap.demo').controller('ErrModalInstanceCtrl', function ($scope, $uibModalInstance, error) {
    $scope.errormessage = error.errorMessage;
    $scope.stacktrace = error.stackTrace;

    $scope.ok = function () {
        $uibModalInstance.close('closed');
    };
});
