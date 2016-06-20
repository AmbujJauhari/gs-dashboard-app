angular.module('ui.bootstrap.demo').controller('documentNameTypeAheadController', function ($scope, $http, $filter, ngTableParams,
                                                                                            $uibModal, $timeout, $log) {


    $scope.selected = undefined;
    var gridName = $scope.gridName;
    var spaceName = $scope.space;
    $scope.saveXls = false;
    $scope.showProgressBar = false;
    console.log(gridName);
    console.log(spaceName);
    getDocumentNames($scope, $http, gridName, spaceName)


    var dataToBeSaved;


    $scope.submit = function () {
        $scope.showProgressBar = true;
        
        alert("bhow bhow")
        var queryForm = {
            "gridName": gridName,
            "dataType": $scope.selectedDocumentTypeName,
            "criteria": $scope.queryCriteria,
            "spaceName": spaceName
        };

        $http.post('/query/getDataFromSpaceForType.html', queryForm)
            .success(function (data) {
                $scope.showProgressBar = false;
                dataToBeSaved = data.dataPerField;
                $scope.saveXls = true;
                $scope.headerNames = data.fieldNames;
                $scope.spaceIdFieldName = data.spaceIdFieldName
                $scope.detailedTypeData = data.dataPerField;
                $scope.tableParams = new ngTableParams({
                    page: 1,
                    count: 10,
                    filter: {
                        name: 'M'
                    }
                }, {
                    total: $scope.detailedTypeData.length,
                    getData: function ($defer, params) {
                        $scope.paginatedDetailedDataType =
                            $scope.detailedTypeData.slice((params.page() - 1) * params.count(), params.page() * params.count());
                        $defer.resolve($scope.paginatedDetailedDataType);
                    }
                });
            });
    };

    $scope.exportData = function () {
        alert("water water everywhere")
        console.log("water")
        alasql('SELECT * INTO XLSX("' + $scope.selectedDocumentTypeName + '.xlsx",{headers:true}) FROM ?', [dataToBeSaved]);
    };


    $scope.open = function (parameter1) {
        $scope.showModalLoad = true;
        var modalInstance = $uibModal.open({
            templateUrl: 'DetailedQueryView.html',
            controller: ModalInstanceCtrl,
            size: 'lg',
            resolve: {
                detailedObjectProperties: function () {
                    return "loading data..." + parameter1;
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
                console.log($scope.selectedDocumentTypeName);
                $scope.detailedTypeName = $scope.selectedDocumentTypeName
                $scope.detailedSpaceId = parameter1
                aModalInstance.setDetailedObjectProperties($scope.selectedDocumentTypeName, parameter1, $scope.spaceIdFieldName);
            }, 3000);
        };

    };

    var ModalInstanceCtrl = function ($scope, $uibModalInstance, detailedObjectProperties) {
        $scope.detailedObjectProperties = detailedObjectProperties;
        var dataTypeDetails;
        var spaceIdName;
        $uibModalInstance.setDetailedObjectProperties = function (dataType, parameter1, spaceIdVarName) {
            spaceIdName = spaceIdVarName;
            dataTypeDetails = dataType;
            $http.get("/query/getDataFromSpaceForTypeForSpaceId.html",
                {params: {"gridName": gridName, "spaceName": spaceName, "dataType": dataType, "spaceId": parameter1}})
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
                    $scope.detailedObjectProperties = editableMap;
                });
        };

        $scope.edit = function () {
            for (var i in $scope.detailedObjectProperties) {
                $scope.detailedObjectProperties[i].disabled = false;
            }
            $scope.updateEnabled = true;
        };

        $scope.save = function () {
            console.log($scope.detailedObjectProperties);
            var detailedObjectDataForUpdating = {
                gridName: gridName,
                spaceName: spaceName,
                dataTypeName: dataTypeDetails,
                detailedDataEntry: $scope.detailedObjectProperties,
                spaceIdName: spaceIdName
            };
            $http.post("/query/updateDataInSpaceForTypeForSpaceId", detailedObjectDataForUpdating);
            $scope.updateEnabled = false;
            $uibModalInstance.close('close');
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };


    };
});

function getDocumentNames($scope, $http, gridName, spaceName) {
    $http.get('/query/getAllDocumentTypesForSpace.html',
        {params: {"gridName": gridName, "spaceName": spaceName}})
        .success(function (data) {
            $scope.states = data
        });
}

