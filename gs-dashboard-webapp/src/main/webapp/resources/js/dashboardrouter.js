angular.module('ui.bootstrap.demo').config(['$routeProvider', function($routeProvider) {
    $routeProvider.

    when('/welcome', {
        templateUrl: 'welcome.html',
        controller: 'CarouselDemoCtrl'
    }).

    when('/queryBoard/:gridName', {
        templateUrl: 'query/queryboard.html',
        controller: 'documentNameTypeAheadController',
    }).

    otherwise({
        redirectTo: '/welcome'
    });
}]);



