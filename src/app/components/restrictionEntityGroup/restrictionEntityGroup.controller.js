(function() { 

angular
.module("serverTestApp")
.controller("RestrictionEntityGroupController",RestrictionEntityGroupController);
/** @ngInject */
function RestrictionEntityGroupController($scope,$http,$rootScope,$log,UtilityService ,restrictionEntityGroupService, SecurityService, MainService ,roleService,entityGroupService)
{
var vm=this;
vm.searchBean=restrictionEntityGroupService.searchBean;
vm.entityList=restrictionEntityGroupService.entityList;
vm.selectedEntity=restrictionEntityGroupService.selectedEntity;
vm.rolePreparedData=roleService.preparedData;
vm.entityGroupPreparedData=entityGroupService.preparedData;
function reset()
{
restrictionEntityGroupService.resetSearchBean();
vm.searchBean=restrictionEntityGroupService.searchBean;
restrictionEntityGroupService.setSelectedEntity(null);
restrictionEntityGroupService.selectedEntity.show=false;
restrictionEntityGroupService.setEntityList(null); 
if (restrictionEntityGroupService.isParent()) 
{
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
entityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.entityGroup;
}
}
function addNew()
{
$rootScope.openNode.restrictionEntityGroup=true;
restrictionEntityGroupService.setSelectedEntity(null);
restrictionEntityGroupService.setEntityList(null);
restrictionEntityGroupService.selectedEntity.show=true;
if (restrictionEntityGroupService.isParent()) 
{
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
entityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.entityGroup;
}
angular.element('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
}
		
function search()
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
}
function insert()
{
if (!$scope.restrictionEntityGroupDetailForm.$valid) return; 
if (restrictionEntityGroupService.isParent()) 
{
restrictionEntityGroupService.insert().then(function successCallback(response) { 
$log.debug(response);
vm.search();
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
}
function update()
{
if (!$scope.restrictionEntityGroupDetailForm.$valid) return; 
if (restrictionEntityGroupService.isParent()) 
{
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
entityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.entityGroup;
restrictionEntityGroupService.update().then(function successCallback(response) { 
$log.debug(response);
vm.search();
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
}
function remove()
{
restrictionEntityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntityGroup;
restrictionEntityGroupService.setSelectedEntity(null);
}
function del()
{
restrictionEntityGroupService.del().then(function successCallback(response) { 
$log.debug(response);
if (restrictionEntityGroupService.isParent()) 
{
vm.search();
} else { 
restrictionEntityGroupService.setSelectedEntity(null);
}
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
function refreshTableDetail() 
{
if ($scope.roleGridApi!=undefined && $scope.roleGridApi!=null)
 $scope.roleGridApi.core.handleWindowResize(); 
if ($scope.entityGroupGridApi!=undefined && $scope.entityGroupGridApi!=null)
 $scope.entityGroupGridApi.core.handleWindowResize(); 
}
vm.refreshTableDetail=refreshTableDetail;
function loadFile(file,field)
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
vm.trueFalseValues=['',true,false];
 function showRoleDetail(index)
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
}
vm.showRoleDetail=showRoleDetail;
 function showEntityGroupDetail(index)
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
}
vm.showEntityGroupDetail=showEntityGroupDetail;
function downloadList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("restrictionEntityGroup.xls",?) FROM ?',[mystyle,vm.entityList]);
}
function downloadRoleList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,vm.selectedEntity.roleList]);
}
vm.downloadRoleList=downloadRoleList;
function downloadEntityGroupList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("entityGroup.xls",?) FROM ?',[mystyle,vm.selectedEntity.entityGroupList]);
}
vm.downloadEntityGroupList=downloadEntityGroupList;
vm.restrictionEntityGroupGridOptions={};
UtilityService.cloneObject(restrictionEntityGroupService.gridOptions,vm.restrictionEntityGroupGridOptions);
vm.restrictionEntityGroupGridOptions.data=restrictionEntityGroupService.entityList;
vm.initChildrenList = function () { 
}
vm.restrictionEntityGroupGridOptions.onRegisterApi = function(gridApi){
vm.restrictionEntityGroupGridApi = gridApi;
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
vm.roleGridOptions={};
UtilityService.cloneObject(roleService.gridOptions,vm.roleGridOptions);
vm.roleGridOptions.data=vm.selectedEntity.roleList;
vm.initChildrenList = function () { 
}
vm.roleGridOptions.onRegisterApi = function(gridApi){
vm.roleGridApi = gridApi;
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
vm.entityGroupGridOptions={};
UtilityService.cloneObject(entityGroupService.gridOptions,vm.entityGroupGridOptions);
vm.entityGroupGridOptions.data=vm.selectedEntity.entityGroupList;
vm.initChildrenList = function () { 
}
vm.entityGroupGridOptions.onRegisterApi = function(gridApi){
vm.entityGroupGridApi = gridApi;
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
function closeEntityDetail(){ 
restrictionEntityGroupService.setSelectedEntity(null);
restrictionEntityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntityGroup;
}
vm.reset=reset;
vm.addNew=addNew;
vm.insert=insert;
vm.update=update;
vm.search=search;
vm.remove=remove;
vm.del=del;
vm.loadFile=loadFile;
vm.downloadList=downloadList;
vm.closeEntityDetail=closeEntityDetail;
}
})();
