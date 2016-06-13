/**
 * Created by Aj on 13-06-2016.
 */
angular.module('ui.bootstrap.demo').factory('dataShare', function ($window) {
    var KEY = 'GS.SelectedENV';
    var sendEnvDetails = function (data) {
        var mydata = $window.sessionStorage.getItem(KEY);
        mydata = data;
        $window.sessionStorage.setItem(KEY, JSON.stringify(mydata));
    };
    var getData = function () {
        var mydata = $window.sessionStorage.getItem(KEY);
        if (mydata) {
            mydata = JSON.parse(mydata);
        }
        return mydata || [];
    };
    return {
        sendEnvDetails: sendEnvDetails,
        getData: getData
    };
});
