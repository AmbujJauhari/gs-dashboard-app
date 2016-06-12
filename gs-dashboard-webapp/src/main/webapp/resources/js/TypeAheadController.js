angular.module('ui.bootstrap.demo').controller('documentNameTypeAheadController', function ($scope, $http) {
    $scope.selected = undefined;
    getDocumentNames($scope, $http)
});

function getDocumentNames($scope, $http) {
    $http.get('http://localhost:8080/query/getAllDocumentTypesForSpace.html?envName=Grid-A').success(function (data) {
        $scope.states = data
    });
}
