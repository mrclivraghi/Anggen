(function() { 

angular
.module("serverTestApp")
.controller("RestrictionEntityController",RestrictionEntityController);
/** @ngInject */
function RestrictionEntityController($scope,$http,$rootScope,$log,UtilityService ,restrictionEntityService, SecurityService, MainService ,entityService,roleService)
{
$scope.searchBean=restrictionEntityService.searchBean;
$scope.entityList=restrictionEntityService.entityList;
$scope.selectedEntity=restrictionEntityService.selectedEntity;
$scope.hidden=restrictionEntityService.hidden;
$scope.entityPreparedData=entityService.preparedData;
$scope.rolePreparedData=roleService.preparedData;
$scope.reset = function()
{
restrictionEntityService.resetSearchBean();
$scope.searchBean=restrictionEntityService.searchBean;restrictionEntityService.setSelectedEntity(null);
restrictionEntityService.selectedEntity.show=false;
restrictionEntityService.setEntityList(null); 
if (restrictionEntityService.isParent()) 
{
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
}
}
$scope.addNew= function()
{
$rootScope.openNode.restrictionEntity=true;
restrictionEntityService.setSelectedEntity(null);
restrictionEntityService.setEntityList(null);
restrictionEntityService.selectedEntity.show=true;
if (restrictionEntityService.isParent()) 
{
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
}
angular.element('#restrictionEntityTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
restrictionEntityService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntity;
restrictionEntityService.search().then(function successCallback(response) {
restrictionEntityService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
};
$scope.insert=function()
{
if (!$scope.restrictionEntityDetailForm.$valid) return; 
if (restrictionEntityService.isParent()) 
{
restrictionEntityService.insert().then(function successCallback(response) { 
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
restrictionEntityService.selectedEntity.show=false;
restrictionEntityService.insert().then(function successCallBack(response) { 
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
if (!$scope.restrictionEntityDetailForm.$valid) return; 
if (restrictionEntityService.isParent()) 
{
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
restrictionEntityService.update().then(function successCallback(response) { 
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
restrictionEntityService.selectedEntity.show=false;

restrictionEntityService.update().then(function successCallback(response){
restrictionEntityService.setSelectedEntity(response.data);
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
restrictionEntityService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntity;
restrictionEntityService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!restrictionEntityService.isParent()) 
$scope.updateParent();
restrictionEntityService.del().then(function successCallback(response) { 
$log.debug(response);
if (restrictionEntityService.isParent()) 
{
$scope.search();
} else { 
restrictionEntityService.setSelectedEntity(null);
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
if ($scope.entityGridApi!=undefined && $scope.entityGridApi!=null)
 $scope.entityGridApi.core.handleWindowResize(); 
if ($scope.roleGridApi!=undefined && $scope.roleGridApi!=null)
 $scope.roleGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
restrictionEntityService.loadFile(file,field).then(function successCallback(response) {
restrictionEntityService.setSelectedEntity(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
$scope.trueFalseValues=['',true,false];
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(restrictionEntityService.selectedEntity.entityList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
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
if (restrictionEntityService.selectedEntity.entity==null || restrictionEntityService.selectedEntity.entity==undefined)
{
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
$rootScope.openNode.entity=true;
}
else
entityService.searchOne(restrictionEntityService.selectedEntity.entity).then(
function successCallback(response) {
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
$rootScope.openNode.entity=true;
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#entityTabs li:eq(0) a').tab('show');
};
$scope.showRoleDetail= function(index)
{
if (index!=null)
{
roleService.searchOne(restrictionEntityService.selectedEntity.roleList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
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
if (restrictionEntityService.selectedEntity.role==null || restrictionEntityService.selectedEntity.role==undefined)
{
roleService.setSelectedEntity(null); 
roleService.selectedEntity.show=true; 
$rootScope.openNode.role=true;
}
else
roleService.searchOne(restrictionEntityService.selectedEntity.role).then(
function successCallback(response) {
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
$rootScope.openNode.role=true;
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#roleTabs li:eq(0) a').tab('show');
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("restrictionEntity.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityList]);
};
$scope.downloadRoleList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,$scope.selectedEntity.roleList]);
};
$scope.restrictionEntityGridOptions={};
UtilityService.cloneObject(restrictionEntityService.gridOptions,$scope.restrictionEntityGridOptions);
$scope.restrictionEntityGridOptions.data=restrictionEntityService.entityList;
$scope.initChildrenList = function () { 
}
$scope.restrictionEntityGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionEntityGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionEntityService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.restrictionEntity=true;
restrictionEntityService.setSelectedEntity(response.data[0]);
});
angular.element('#restrictionEntityTabs li:eq(0) a').tab('show');
}
else 
restrictionEntityService.setSelectedEntity(null);
delete $rootScope.openNode.restrictionEntity;
restrictionEntityService.selectedEntity.show = row.isSelected;
});
  };
$scope.entityGridOptions={};
UtilityService.cloneObject(entityService.gridOptions,$scope.entityGridOptions);
$scope.entityGridOptions.data=$scope.selectedEntity.entityList;
$scope.initChildrenList = function () { 
}
$scope.entityGridOptions.onRegisterApi = function(gridApi){
$scope.entityGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
entityService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.entity=true;
entityService.setSelectedEntity(response.data[0]);
});
angular.element('#entityTabs li:eq(0) a').tab('show');
}
else 
entityService.setSelectedEntity(null);
delete $rootScope.openNode.entity;
entityService.selectedEntity.show = row.isSelected;
});
  };
$scope.roleGridOptions={};
UtilityService.cloneObject(roleService.gridOptions,$scope.roleGridOptions);
$scope.roleGridOptions.data=$scope.selectedEntity.roleList;
$scope.initChildrenList = function () { 
}
$scope.roleGridOptions.onRegisterApi = function(gridApi){
$scope.roleGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
roleService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.role=true;
roleService.setSelectedEntity(response.data[0]);
});
angular.element('#roleTabs li:eq(0) a').tab('show');
}
else 
roleService.setSelectedEntity(null);
delete $rootScope.openNode.role;
roleService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
roleService.initRestrictionEntityList().then(function(response) {
restrictionEntityService.preparedData.entityList=response.data;
});

if (roleService.selectedEntity.roleId!=undefined) roleService.searchOne(roleService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
roleService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
entityService.initRestrictionEntityList().then(function(response) {
restrictionEntityService.preparedData.entityList=response.data;
});

if (entityService.selectedEntity.entityId!=undefined) entityService.searchOne(entityService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
entityService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
$scope.closeEntityDetail = function(){ 
restrictionEntityService.setSelectedEntity(null);
restrictionEntityService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntity;
}
}
})();
