<!doctype html>
<html >
  <head>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-touch.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-animate.js"></script>
    <script src="http://ui-grid.info/docs/grunt-scripts/csv.js"></script>
    <script src="http://ui-grid.info/docs/grunt-scripts/pdfmake.js"></script>
    <script src="http://ui-grid.info/docs/grunt-scripts/vfs_fonts.js"></script>
    <script src="../resources/general_theme/js/ui-grid.js"></script>
    <link rel="stylesheet" href="../resources/general_theme/css/ui-grid.css" type="text/css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	
  </head>
    <body ng-app="app">
   <script>
 var app = angular.module('app', ['ngTouch', 'ui.grid', 'ui.grid.selection','ui.grid.pagination']);

app.controller('MainCtrl', ['$scope', '$http', '$log', '$timeout', 'uiGridConstants', function ($scope, $http, $log, $timeout, uiGridConstants) {
  $scope.gridOptions = {
    enableRowSelection: true,
    enableSelectAll: true,
    selectionRowHeaderWidth: 35,
    rowHeight: 35,
    showGridFooter:true,
	paginationPageSizes: [25, 50, 75],
    paginationPageSize: 25
  };

  $scope.gridOptions.columnDefs = [
    { name: 'id' },
    { name: 'name'},
    { name: 'age', displayName: 'Age (not focusable)', allowCellFocus : false },
    { name: 'address.city' }
  ];

  $scope.gridOptions.multiSelect = true;

  $http.get('http://ui-grid.info/data/500_complex.json')
    .success(function(data) {
      $scope.gridOptions.data = data;

/*      $timeout(function() {
        if($scope.gridApi.selection.selectRow){
          $scope.gridApi.selection.selectRow($scope.gridOptions.data[0]);
        }
      });*/
    });

	
	
    $scope.info = {};

    $scope.toggleMultiSelect = function() {
      $scope.gridApi.selection.setMultiSelect(!$scope.gridApi.grid.options.multiSelect);
    };

    $scope.toggleModifierKeysToMultiSelect = function() {
      $scope.gridApi.selection.setModifierKeysToMultiSelect(!$scope.gridApi.grid.options.modifierKeysToMultiSelect);
    };

    $scope.selectAll = function() {
      $scope.gridApi.selection.selectAllRows();
    };

    $scope.clearAll = function() {
      $scope.gridApi.selection.clearSelectedRows();
    };

    $scope.toggleRow1 = function() {
      $scope.gridApi.selection.toggleRowSelection($scope.gridOptions.data[0]);
    };

    $scope.toggleFullRowSelection = function() {
      $scope.gridOptions.enableFullRowSelection = !$scope.gridOptions.enableFullRowSelection;
      $scope.gridApi.core.notifyDataChange( uiGridConstants.dataChange.OPTIONS);
    };

    $scope.setSelectable = function() {
      $scope.gridApi.selection.clearSelectedRows();

      $scope.gridOptions.isRowSelectable = function(row){
        if(row.entity.age > 30){
          return false;
        } else {
          return true;
        }
      };
      $scope.gridApi.core.notifyDataChange(uiGridConstants.dataChange.OPTIONS);

      $scope.gridOptions.data[0].age = 31;
      $scope.gridApi.core.notifyDataChange(uiGridConstants.dataChange.EDIT);
    };

    $scope.gridOptions.onRegisterApi = function(gridApi){
      //set gridApi on scope
      $scope.gridApi = gridApi;
      gridApi.selection.on.rowSelectionChanged($scope,function(row){
        var msg = 'row selected ' + row.isSelected;
        $log.log(msg);
      });

      gridApi.selection.on.rowSelectionChangedBatch($scope,function(rows){
        var msg = 'rows changed ' + rows.length;
        $log.log(msg);
      });
    };
}]);


 
 </script>
<form>ciao sono una form</form>

<div ng-controller="MainCtrl" >
  <button type="button" class="btn btn-success" ng-click="toggleMultiSelect()">Toggle multiSelect</button>  <strong>MultiSelect:</strong> <span ng-bind="gridApi.grid.options.multiSelect"></span>
  <button type="button" class="btn btn-success" ng-click="toggleRow1()">Toggle Row 0</button>
  <br/>
  <br/>
  <button type="button" class="btn btn-success" ng-click="toggleModifierKeysToMultiSelect()">Toggle modifierKeysToMultiSelect</button>  <strong>ModifierKeysToMultiSelect:</strong> <span ng-bind="gridApi.grid.options.modifierKeysToMultiSelect"> </span>
  <br/>
  <br/>
  <button type="button" class="btn btn-success" ng-disabled="!gridApi.grid.options.multiSelect" ng-click="selectAll()">Select All</button>
  <button type="button" class="btn btn-success" ng-click="clearAll()">Clear All</button>
  <button type="button" class="btn btn-success" ng-click="setSelectable()">Set Selectable</button>
  <button type="button" class="btn btn-success" ng-click="toggleFullRowSelection()">Toggle full row selection</button>
  <br/>

  <div ui-grid="gridOptions" ui-grid-selection ui-grid-pagination class="grid"></div>
</div>



   
  </body>
</html>