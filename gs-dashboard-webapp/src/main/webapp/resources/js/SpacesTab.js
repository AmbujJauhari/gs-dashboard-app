/**
 * Created by Aj on 19-06-2016.
 */
angular.module('ui.bootstrap.demo').controller('tabbedSpaceController',function ($scope, $http, $routeParams) {
    var gridName = $routeParams.gridName;

    $http.get('/query/getListOfAllSpacesForGrid.html',
        {params: {"gridName": gridName}})
        .success(function (data) {
            console.log(data)
            $scope.spaces = data;
            $scope.gridName = gridName;
        });
});
