(function() { 

angular
.module("serverTestApp")
.controller("EnumValueController",EnumValueController);
/** @ngInject */
function EnumValueController($scope,$http,$rootScope,$log,UtilityService ,enumValueService, SecurityService, MainService ,enumEntityService)
{
$scope.searchBean=enumValueService.searchBean;
$scope.entityList=enumValueService.entityList;
$scope.selectedEntity=enumValueService.selectedEntity;
$scope.hidden=enumValueService.hidden;
$scope.enumEntityPreparedData=enumEntityService.preparedData;
$scope.reset = function()
{
enumValueService.resetSearchBean();
$scope.searchBean=enumValueService.searchBean;enumValueService.setSelectedEntity(null);
enumValueService.selectedEntity.show=false;
enumValueService.setEntityList(null); 
if (enumValueService.isParent()) 
{
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
}
}
$scope.addNew= function()
{
$rootScope.openNode.enumValue=true;
enumValueService.setSelectedEntity(null);
enumValueService.setEntityList(null);
enumValueService.selectedEntity.show=true;
if (enumValueService.isParent()) 
{
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
}
angular.element('#enumValueTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
enumValueService.selectedEntity.show=false;
delete $rootScope.openNode.enumValue;
enumValueService.search().then(function successCallback(response) {
enumValueService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
};
$scope.insert=function()
{
if (!$scope.enumValueDetailForm.$valid) return; 
if (enumValueService.isParent()) 
{
enumValueService.insert().then(function successCallback(response) { 
$log.debug(response);
$scope.search();
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
else 
{
enumValueService.selectedEntity.show=false;
enumValueService.insert().then(function successCallBack(response) { 
$log.debug(response);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
};
$scope.update=function()
{
if (!$scope.enumValueDetailForm.$valid) return; 
if (enumValueService.isParent()) 
{
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
enumValueService.update().then(function successCallback(response) { 
$log.debug(response);
$scope.search();
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
else 
{
enumValueService.selectedEntity.show=false;

enumValueService.update().then(function successCallback(response){
enumValueService.setSelectedEntity(response.data);
updateParentEntities();
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
};
$scope.remove= function()
{
enumValueService.selectedEntity.show=false;
delete $rootScope.openNode.enumValue;
enumValueService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!enumValueService.isParent()) 
$scope.updateParent();
enumValueService.del().then(function successCallback(response) { 
$log.debug(response);
if (enumValueService.isParent()) 
{
$scope.search();
} else { 
enumValueService.setSelectedEntity(null);
}
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.enumEntityGridApi!=undefined && $scope.enumEntityGridApi!=null)
 $scope.enumEntityGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
enumValueService.loadFile(file,field).then(function successCallback(response) {
enumValueService.setSelectedEntity(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
$scope.trueFalseValues=['',true,false];
$scope.showEnumEntityDetail= function(index)
{
if (index!=null)
{
enumEntityService.searchOne(enumValueService.selectedEntity.enumEntityList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
enumEntityService.setSelectedEntity(response.data[0]);
enumEntityService.selectedEntity.show=true;
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
else 
{
if (enumValueService.selectedEntity.enumEntity==null || enumValueService.selectedEntity.enumEntity==undefined)
{
enumEntityService.setSelectedEntity(null); 
enumEntityService.selectedEntity.show=true; 
$rootScope.openNode.enumEntity=true;
}
else
enumEntityService.searchOne(enumValueService.selectedEntity.enumEntity).then(
function successCallback(response) {
enumEntityService.setSelectedEntity(response.data[0]);
enumEntityService.selectedEntity.show=true;
$rootScope.openNode.enumEntity=true;
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#enumEntityTabs li:eq(0) a').tab('show');
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("enumValue.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadEnumEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("enumEntity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.enumEntityList]);
};
$scope.enumValueGridOptions={};
UtilityService.cloneObject(enumValueService.gridOptions,$scope.enumValueGridOptions);
$scope.enumValueGridOptions.data=enumValueService.entityList;
$scope.initChildrenList = function () { 
}
$scope.enumValueGridOptions.onRegisterApi = function(gridApi){
$scope.enumValueGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
enumValueService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.enumValue=true;
enumValueService.setSelectedEntity(response.data[0]);
});
angular.element('#enumValueTabs li:eq(0) a').tab('show');
}
else 
enumValueService.setSelectedEntity(null);
delete $rootScope.openNode.enumValue;
enumValueService.selectedEntity.show = row.isSelected;
});
  };
$scope.enumEntityGridOptions={};
UtilityService.cloneObject(enumEntityService.gridOptions,$scope.enumEntityGridOptions);
$scope.enumEntityGridOptions.data=$scope.selectedEntity.enumEntityList;
$scope.initChildrenList = function () { 
}
$scope.enumEntityGridOptions.onRegisterApi = function(gridApi){
$scope.enumEntityGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
enumEntityService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.enumEntity=true;
enumEntityService.setSelectedEntity(response.data[0]);
});
angular.element('#enumEntityTabs li:eq(0) a').tab('show');
}
else 
enumEntityService.setSelectedEntity(null);
delete $rootScope.openNode.enumEntity;
enumEntityService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
enumEntityService.initEnumValueList().then(function(response) {
enumValueService.preparedData.entityList=response.data;
});

if (enumEntityService.selectedEntity.enumEntityId!=undefined) enumEntityService.searchOne(enumEntityService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
enumEntityService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
$scope.closeEntityDetail = function(){ 
enumValueService.setSelectedEntity(null);
enumValueService.selectedEntity.show=false;
delete $rootScope.openNode.enumValue;
}
}
})();
