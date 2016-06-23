/**
 * Created by Aj on 12-06-2016.
 */
angular.module('ui.bootstrap.demo', ['pageslide-directive', 'ngAnimate', 'ui.bootstrap', 'ngTable', 'ngRoute', 'JSONedit']);

angular.module('ui.bootstrap.demo').config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common["X-Requested-With"];
    $httpProvider.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
    $httpProvider.defaults.headers.common["Accept"] = "application/json";
    $httpProvider.defaults.headers.common["content-type"] = "application/json";
    $httpProvider.defaults.headers.common['X-User-Agent'] = "MyClient" ;
}
]);