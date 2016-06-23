'use strict';

angular.module('JSONedit', ['ui.sortable'])
.directive('json', ["$compile", function($compile) {
  return {
    restrict: 'E',
    scope: {
      child: '=',
      type: '@',
      defaultCollapsed: '='
    },
    link: function(scope, element, attributes) {
        var stringName = "Text";
        var objectName = "Object";
        var arrayName = "Array";
        var refName = "Reference";
        var boolName = "Boolean";
        var numberName = "Number";

        scope.valueTypes = [stringName, objectName, arrayName, refName, boolName, numberName];
        scope.sortableOptions = {
            axis: 'y'
        };
        if (scope.$parent.defaultCollapsed === undefined) {
            scope.collapsed = false;
        } else {
            scope.collapsed = scope.defaultCollapsed;
        }
        if (scope.collapsed) {
            scope.chevron = "glyphicon-chevron-right";
        } else {
            scope.chevron = "glyphicon-chevron-down";
        }
        

        //////
        // Helper functions
        //////

        var getType = function(obj) {
            var type = Object.prototype.toString.call(obj);
            if (type === "[object Object]") {
                return "Object";
            } else if(type === "[object Array]"){
                return "Array";
            } else {
                return "Literal";
            }
        };
        
        scope.getType = function(obj) {
            return getType(obj);
        };
        scope.toggleCollapse = function() {
            if (scope.collapsed) {
                scope.collapsed = false;
                scope.chevron = "glyphicon-chevron-down";
            } else {
                scope.collapsed = true;
                scope.chevron = "glyphicon-chevron-right";
            }
        };
        
        //////
        // Template Generation
        //////

        // Note:
        // sometimes having a different ng-model and then saving it on ng-change
        // into the object or array is necessary for all updates to work
        
        // recursion
        var switchTemplate = 
            '<span ng-switch on="getType(val)" >'
                + '<json ng-switch-when="Object" child="val" type="object" default-collapsed="defaultCollapsed"></json>'
                + '<json ng-switch-when="Array" child="val" type="array" default-collapsed="defaultCollapsed"></json>'
                
                
                + '<span ng-switch-default class="jsonLiteral">' +
            '<<textarea type="text" ng-model="val" '
                    + 'placeholder="Empty" ng-model-onblur ng-change="child[key] = val" disabled/>'
                + '</span>'
            + '</span>';
        
        // display either "plus button" or "key-value inputs"
        var addItemTemplate =
        '<div ng-switch on="showAddKey" class="block" >'
            
            
        + '</div>';

        // start template
        if (scope.type == "object"){
            var template = '<i ng-click="toggleCollapse()" class="glyphicon" ng-class="chevron"></i>'
            + '<div class="jsonContents" ng-hide="collapsed">'
                // repeat
                + '<span class="block" ng-hide="key.indexOf(\'_\') == 0" ng-repeat="(key, val) in child">'
                    // object key
                    + '<span class="jsonObjectKey">'
                        + '<input class="keyinput" type="text" ng-model="newkey" ng-init="newkey=key" '
                            + ' disabled/>'
                    + '</span>'
                    // object value
                    + '<span class="jsonObjectValue">' + switchTemplate + '</span>'
                + '</span>'
                // repeat end
                + addItemTemplate
            + '</div>';
        } else if (scope.type == "array") {
            var template = '<i ng-click="toggleCollapse()" class="glyphicon"'
            + 'ng-class="chevron"></i>'
            + '<div class="jsonContents" ng-hide="collapsed">'
                + '<ol class="arrayOl" ui-sortable="sortableOptions" ng-model="child">'
                    // repeat
                    + '<li class="arrayItem" ng-repeat="(key, val) in child track by $index">'
                        + '<span>' + switchTemplate + '</span>'
                    + '</li>'
                    // repeat end
                + '</ol>'
                + addItemTemplate
            + '</div>';
        } else {
            console.error("scope.type was "+ scope.type);
        }

        var newElement = angular.element(template);
        $compile(newElement)(scope);
        element.replaceWith ( newElement );
    }
  };
}]);
