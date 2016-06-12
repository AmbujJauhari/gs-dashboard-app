<!doctype html>
<html ng-app="ui.bootstrap.demo">
<head>
    <title>Hello AngularJS</title>
    <head>
        <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular.js"></script>
        <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular-animate.js"></script>
        <script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.3.3.js"></script>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://rawgit.com/esvit/ng-table/master/dist/ng-table.min.css">
        <script src="https://rawgit.com/esvit/ng-table/master/dist/ng-table.min.js"></script>
    </head>
    <script type="text/css">
        .app-modal-window .modal-dialog {
            width: 500px;
        }
    </script>

    <script type="text/javascript">
        angular.module('ui.bootstrap.demo', ['ngAnimate', 'ui.bootstrap', 'ngTable']);
        angular.module('ui.bootstrap.demo').controller('TypeaheadCtrl', function ($scope, $http) {
            $scope.selected = undefined;
            Hello($scope, $http)
        });

        function Hello($scope, $http) {
            $http.get('http://localhost:8080/query/getAllDocumentTypesForSpace.html?envName=Grid-A').success(function (data) {
                $scope.states = data
            });
        }

        angular.module('ui.bootstrap.demo').controller('queryController', function ($scope, $http, $filter, ngTableParams,
                                                                                    $uibModal, $timeout, $log, $window) {
            $scope.submit = function () {
                var queryFormData = {
                    "gridName": 'Grid-A',
                    "dataType": $scope.selected,
                    "criteria": $scope.queryCriteria
                };
                $http.post('http://localhost:8080/query/getAllObjectsFromSpaceForTypeName.html', queryFormData)
                        .success(function (data, status, headers, config) {
                            $scope.columns = data.fieldNames;
                            $scope.spaceIdFieldName = data.spaceIdFieldName
                            $scope.documentDetailedData = data.dataPerField;
                            $scope.tableParams = new ngTableParams({
                                page: 1,            // show first page
                                count: 10,          // count per page
                                filter: {
                                    name: 'M'       // initial filter
                                }
                            }, {
                                total: $scope.documentDetailedData.length, // length of data
                                getData: function ($defer, params) {
                                    $scope.paginatedDocumentDetailedData =
                                            $scope.documentDetailedData.slice((params.page() - 1) * params.count(), params.page() * params.count());
                                    $defer.resolve($scope.paginatedDocumentDetailedData);
                                }
                            });
                        });
            };


            $scope.open = function (parameter1) {

                var modalInstance = $uibModal.open({
                    templateUrl: 'myModalContent.html',
                    controller: ModalInstanceCtrl,
                    size: 'lg',
                    resolve: {
                        items: function () {
                            return "loadind data..." + parameter1;
                        }
                    }
                });

                modalInstance.opened.then(function () {
                    $scope.loadData(modalInstance);
                }, function () {
                    $log.info('Modal dismissed at: ' + new Date());
                });

                $scope.loadData = function (aModalInstance) {
                    $timeout(function () {
                        console.log($scope.selected);
                        aModalInstance.setItems($scope.selected, parameter1, $scope.spaceIdFieldName);
                    }, 3000);
                };

            };

            var ModalInstanceCtrl = function ($scope, $uibModalInstance, items) {
                $scope.items = items;
                var datTypeDetails;
                var spaceIdName;
                $uibModalInstance.setItems = function (dataType, parameter1, spaceIdVarName) {
                    spaceIdName = spaceIdVarName;
                    datTypeDetails = dataType;
                    $http.get("http://localhost:8080/query/getDetailedDataFromSpaceForTypeNameWithSpaceId.html",
                            {params: {"gridName": 'Grid-A', "dataType": dataType, "spaceId": parameter1}})
                            .success(function (data) {
                                var editableMap = [];
                                for (var i in data) {
                                    editableMap.push(
                                            {
                                                key: i,
                                                value: data[i],
                                                disabled: true
                                            }
                                    )
                                }
                                console.log(editableMap)
                                $scope.items = editableMap;
                            });
                };

                $scope.ok = function () {
                    for (var i in $scope.items) {
                        $scope.items[i].disabled = false;
                    }
                    $scope.updateEnabled = true;
                };

                $scope.save = function () {
                    console.log($scope.items);
                    var dataForUpdating = {
                        dataTypeName : datTypeDetails,
                        detailedDataEntry : $scope.items,
                        spaceIdName : spaceIdName
                    };
                    $http.post("http://localhost:8080/query/updateDataInSpaceForTypeForSpaceId", dataForUpdating);
                    $scope.updateEnabled = false;
                   $uibModalInstance.close('close');
                };

                $scope.cancel = function () {
                    $uibModalInstance.dismiss('cancel');
                };
            };
        });


    </script>
</head>

<body>
<script type="text/html">
    <ul class="dropdown-menu" role="listbox">
        <li ng-repeat="match in matches track by $index"/>
    </ul>
</script>

<div class='container-fluid typeahead-demo' ng-controller="TypeaheadCtrl">
    <h1>Query Board</h1>

    <form class="form-inline" role="form" ng-controller="queryController" ng-submit="submit()">
        <div class="form-group">
            <input type="text" placeholder="Docum ent / Pojo name" ng-model="selected"
                   uib-typeahead="state for state in states | filter:$viewValue | limitTo:8"
                   class="form-control">
        </div>
        <div class="form-group col-md-offset-1">
            <input type="text" placeholder="search criteria" class="form-control" ng-model="queryCriteria">
        </div>

        <button type="submit" class="btn btn-default col-md-offset-1">Submit</button>
        <h4>You submitted below data through post:{{documentsData}}</h4>
        <div>

            Columns:

            <table ng-table="tableParams" show-filter="true" class="table">
                <thead>
                <tr>
                    <th ng-repeat="column in columns"
                        class="text-center sortable" ng-class="{
                    'sort-asc': tableParams.isSortBy(column, 'asc'),
                    'sort-desc': tableParams.isSortBy(column, 'desc')
                  }"
                        ng-click="tableParams.sorting(column, tableParams.isSortBy(column, 'asc') ? 'desc' : 'asc')">
                        {{column}}
                    </th>
                </tr>
                </thead>
                <tbody>
                <a href="#">
                    <tr ng-repeat="user in paginatedDocumentDetailedData">
                        <td ng-repeat="column in columns">
                            <div class="animate-switch" ng-if="column == spaceIdFieldName">
                                <a href="" ng-click="open(user[column])"> {{user[column]}} </a></div>
                            <div class="animate-switch" ng-if="column != spaceIdFieldName">{{user[column]}}</div>
                        </td>
                        <script type="text/ng-template" id="myModalContent.html">
                            <div class="modal-header">
                                <h3>Detailed Query view for {{}} spaceId {{}}</h3>
                            </div>
                            <div class="modal-body">
                                <%--ForId: <b>{{items}}</b>--%>
                                <table ng-table="tableParams" show-filter="true" class="table">
                                    <thead>
                                    <tr>
                                        <th>Key</th>
                                        <th>Value</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr ng-repeat="x in items">
                                        <td>{{ x.key }}</td>
                                        <td>
                                            <div ng-show="x.disabled">{{x.value}}</div>
                                            <div ng-hide="x.disabled"><input type="text" ng-model="x.value" /></div>

                                            <%--<input type="text"--%>
                                                   <%--ng-model="x.key" value={{x.value}} ng-disabled="x.disabled"/></td>--%>
                                    </tr>
                                </table>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-primary" ng-hide="updateEnabled" ng-click="ok()">Edit</button>
                                <button class="btn btn-primary" ng-show="updateEnabled" ng-click="save()">Save</button>
                                <button class="btn btn-warning" ng-click="cancel()">Cancel</button>
                            </div>
                        </script>
                    </tr>
                </a>
                </tbody>
            </table>
        </div>
    </form>
</div>
</body>
</html>