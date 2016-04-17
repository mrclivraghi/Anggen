(function() { 

angular
.module("serverTestApp")
.controller("RestrictionEntityGroupController",RestrictionEntityGroupController);
/** @ngInject */
function RestrictionEntityGroupController($scope,$http,$rootScope,$log,UtilityService ,restrictionEntityGroupService, SecurityService, MainService ,entityGroupService,roleService)
{
$scope.searchBean=restrictionEntityGroupService.searchBean;
$scope.entityList=restrictionEntityGroupService.entityList;
$scope.selectedEntity=restrictionEntityGroupService.selectedEntity;
$scope.hidden=restrictionEntityGroupService.hidden;
$scope.entityGroupPreparedData=entityGroupService.preparedData;
$scope.rolePreparedData=roleService.preparedData;
$scope.reset = function()
{
restrictionEntityGroupService.resetSearchBean();
$scope.searchBean=restrictionEntityGroupService.searchBean;restrictionEntityGroupService.setSelectedEntity(null);
restrictionEntityGroupService.selectedEntity.show=false;
restrictionEntityGroupService.setEntityList(null); 
if (restrictionEntityGroupService.isParent()) 
{
entityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.entityGroup;
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
}
}
$scope.addNew= function()
{
$rootScope.openNode.restrictionEntityGroup=true;
restrictionEntityGroupService.setSelectedEntity(null);
restrictionEntityGroupService.setEntityList(null);
restrictionEntityGroupService.selectedEntity.show=true;
if (restrictionEntityGroupService.isParent()) 
{
entityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.entityGroup;
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
}
angular.element('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
restrictionEntityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntityGroup;
restrictionEntityGroupService.search().then(function successCallback(response) {
restrictionEntityGroupService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
};
$scope.insert=function()
{
if (!$scope.restrictionEntityGroupDetailForm.$valid) return; 
if (restrictionEntityGroupService.isParent()) 
{
restrictionEntityGroupService.insert().then(function successCallback(response) { 
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
restrictionEntityGroupService.selectedEntity.show=false;
restrictionEntityGroupService.insert().then(function successCallBack(response) { 
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
if (!$scope.restrictionEntityGroupDetailForm.$valid) return; 
if (restrictionEntityGroupService.isParent()) 
{
entityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.entityGroup;
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
restrictionEntityGroupService.update().then(function successCallback(response) { 
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
restrictionEntityGroupService.selectedEntity.show=false;

restrictionEntityGroupService.update().then(function successCallback(response){
restrictionEntityGroupService.setSelectedEntity(response.data);
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
restrictionEntityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntityGroup;
restrictionEntityGroupService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!restrictionEntityGroupService.isParent()) 
$scope.updateParent();
restrictionEntityGroupService.del().then(function successCallback(response) { 
$log.debug(response);
if (restrictionEntityGroupService.isParent()) 
{
$scope.search();
} else { 
restrictionEntityGroupService.setSelectedEntity(null);
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
if ($scope.entityGroupGridApi!=undefined && $scope.entityGroupGridApi!=null)
 $scope.entityGroupGridApi.core.handleWindowResize(); 
if ($scope.roleGridApi!=undefined && $scope.roleGridApi!=null)
 $scope.roleGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
restrictionEntityGroupService.loadFile(file,field).then(function successCallback(response) {
restrictionEntityGroupService.setSelectedEntity(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
$scope.trueFalseValues=['',true,false];
$scope.showEntityGroupDetail= function(index)
{
if (index!=null)
{
entityGroupService.searchOne(restrictionEntityGroupService.selectedEntity.entityGroupList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
entityGroupService.setSelectedEntity(response.data[0]);
entityGroupService.selectedEntity.show=true;
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
if (restrictionEntityGroupService.selectedEntity.entityGroup==null || restrictionEntityGroupService.selectedEntity.entityGroup==undefined)
{
entityGroupService.setSelectedEntity(null); 
entityGroupService.selectedEntity.show=true; 
$rootScope.openNode.entityGroup=true;
}
else
entityGroupService.searchOne(restrictionEntityGroupService.selectedEntity.entityGroup).then(
function successCallback(response) {
entityGroupService.setSelectedEntity(response.data[0]);
entityGroupService.selectedEntity.show=true;
$rootScope.openNode.entityGroup=true;
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#entityGroupTabs li:eq(0) a').tab('show');
};
$scope.showRoleDetail= function(index)
{
if (index!=null)
{
roleService.searchOne(restrictionEntityGroupService.selectedEntity.roleList[index]).then(
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
if (restrictionEntityGroupService.selectedEntity.role==null || restrictionEntityGroupService.selectedEntity.role==undefined)
{
roleService.setSelectedEntity(null); 
roleService.selectedEntity.show=true; 
$rootScope.openNode.role=true;
}
else
roleService.searchOne(restrictionEntityGroupService.selectedEntity.role).then(
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
UtilityService.alasql('SELECT * INTO XLSXML("restrictionEntityGroup.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadEntityGroupList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("entityGroup.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityGroupList]);
};
$scope.downloadRoleList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,$scope.selectedEntity.roleList]);
};
$scope.restrictionEntityGroupGridOptions={};
UtilityService.cloneObject(restrictionEntityGroupService.gridOptions,$scope.restrictionEntityGroupGridOptions);
$scope.restrictionEntityGroupGridOptions.data=restrictionEntityGroupService.entityList;
$scope.initChildrenList = function () { 
}
$scope.restrictionEntityGroupGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionEntityGroupGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionEntityGroupService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.restrictionEntityGroup=true;
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
});
angular.element('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
}
else 
restrictionEntityGroupService.setSelectedEntity(null);
delete $rootScope.openNode.restrictionEntityGroup;
restrictionEntityGroupService.selectedEntity.show = row.isSelected;
});
  };
$scope.entityGroupGridOptions={};
UtilityService.cloneObject(entityGroupService.gridOptions,$scope.entityGroupGridOptions);
$scope.entityGroupGridOptions.data=$scope.selectedEntity.entityGroupList;
$scope.initChildrenList = function () { 
}
$scope.entityGroupGridOptions.onRegisterApi = function(gridApi){
$scope.entityGroupGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
entityGroupService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.entityGroup=true;
entityGroupService.setSelectedEntity(response.data[0]);
});
angular.element('#entityGroupTabs li:eq(0) a').tab('show');
}
else 
entityGroupService.setSelectedEntity(null);
delete $rootScope.openNode.entityGroup;
entityGroupService.selectedEntity.show = row.isSelected;
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
roleService.initRestrictionEntityGroupList().then(function(response) {
restrictionEntityGroupService.preparedData.entityList=response.data;
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
entityGroupService.initRestrictionEntityGroupList().then(function(response) {
restrictionEntityGroupService.preparedData.entityList=response.data;
});

if (entityGroupService.selectedEntity.entityGroupId!=undefined) entityGroupService.searchOne(entityGroupService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
entityGroupService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
$scope.closeEntityDetail = function(){ 
restrictionEntityGroupService.setSelectedEntity(null);
restrictionEntityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntityGroup;
}
}
})();
