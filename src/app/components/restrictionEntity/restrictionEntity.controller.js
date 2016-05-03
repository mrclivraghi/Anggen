(function() { 

angular
.module("serverTest")
.controller("RestrictionEntityController",RestrictionEntityController);
/** @ngInject */
function RestrictionEntityController($scope,$http,$rootScope,$log,UtilityService ,restrictionEntityService, SecurityService, MainService ,roleService,entityService)
{
var vm=this;
vm.activeTab=1;
vm.searchBean=restrictionEntityService.searchBean;
vm.entityList=restrictionEntityService.entityList;
vm.selectedEntity=restrictionEntityService.selectedEntity;
vm.rolePreparedData=roleService.preparedData;
vm.entityPreparedData=entityService.preparedData;
function reset()
{
restrictionEntityService.resetSearchBean();
vm.searchBean=restrictionEntityService.searchBean;
restrictionEntityService.setSelectedEntity(null);
restrictionEntityService.selectedEntity.show=false;
restrictionEntityService.setEntityList(null); 
if (restrictionEntityService.isParent()) 
{
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
UtilityService.removeObjectFromList($rootScope.parentServices,roleService);
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
}
}
function addNew()
{
$rootScope.openNode.restrictionEntity=true;
restrictionEntityService.setSelectedEntity(null);
restrictionEntityService.setEntityList(null);
restrictionEntityService.selectedEntity.show=true;
if (restrictionEntityService.isParent()) 
{
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
UtilityService.removeObjectFromList($rootScope.parentServices,roleService);
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
}
angular.element('#restrictionEntityTabs li:eq(0) a').tab('show');
}
		
function search()
{
restrictionEntityService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityService);
restrictionEntityService.search().then(function successCallback(response) {
restrictionEntityService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
function insert()
{
if (!$scope.restrictionEntityDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
restrictionEntityService.insert().then(function successCallback(response) { 
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
restrictionEntityService.selectedEntity.show=false;
restrictionEntityService.insert().then(function successCallBack(response) { 
$log.debug(response);
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removerestrictionEntity(restrictionEntityService.selectedEntity);
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
if (!$scope.restrictionEntityDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
UtilityService.removeObjectFromList($rootScope.parentServices,roleService);
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
restrictionEntityService.update().then(function successCallback(response) { 
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
}
function remove()
{
restrictionEntityService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntity;
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeRestrictionEntity(restrictionEntityService.selectedEntity);
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityService);
restrictionEntityService.setSelectedEntity(null);
}
function del()
{
$rootScope.parentServices.pop();
restrictionEntityService.del().then(function successCallback(response) { 
$log.debug(response);
restrictionEntityService.setSelectedEntity(null);
if ($rootScope.parentServices.length==0) 
{
vm.search();
} else { 
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
if ($scope.entityGridApi!=undefined && $scope.entityGridApi!=null)
 $scope.entityGridApi.core.handleWindowResize(); 
}
vm.refreshTableDetail=refreshTableDetail;
function loadFile(file,field)
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
vm.trueFalseValues=['',true,false];
 function showRoleDetail(index)
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
$rootScope.parentServices.push(roleService);
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
 function showEntityDetail(index)
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
$rootScope.parentServices.push(entityService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#entityTabs li:eq(0) a').tab('show');
}
vm.showEntityDetail=showEntityDetail;
function downloadList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("restrictionEntity.xls",?) FROM ?',[mystyle,vm.entityList]);
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
function downloadEntityList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,vm.selectedEntity.entityList]);
}
vm.downloadEntityList=downloadEntityList;
vm.restrictionEntityGridOptions={};
UtilityService.cloneObject(restrictionEntityService.gridOptions,vm.restrictionEntityGridOptions);
vm.restrictionEntityGridOptions.data=restrictionEntityService.entityList;
vm.initChildrenList = function () { 
}
vm.restrictionEntityGridOptions.onRegisterApi = function(gridApi){
vm.restrictionEntityGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionEntityService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.restrictionEntity=true;
$rootScope.parentServices.push(restrictionEntityService);
restrictionEntityService.setSelectedEntity(response.data[0]);
});
angular.element('#restrictionEntityTabs li:eq(0) a').tab('show');
}
else 
restrictionEntityService.setSelectedEntity(null);
delete $rootScope.openNode.restrictionEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityService);
restrictionEntityService.selectedEntity.show = row.isSelected;
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
$rootScope.parentServices.push(roleService);
roleService.setSelectedEntity(response.data[0]);
});
angular.element('#roleTabs li:eq(0) a').tab('show');
}
else 
roleService.setSelectedEntity(null);
delete $rootScope.openNode.role;
UtilityService.removeObjectFromList($rootScope.parentServices,roleService);
roleService.selectedEntity.show = row.isSelected;
});
  };
vm.entityGridOptions={};
UtilityService.cloneObject(entityService.gridOptions,vm.entityGridOptions);
vm.entityGridOptions.data=vm.selectedEntity.entityList;
vm.initChildrenList = function () { 
}
vm.entityGridOptions.onRegisterApi = function(gridApi){
vm.entityGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
entityService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.entity=true;
$rootScope.parentServices.push(entityService);
entityService.setSelectedEntity(response.data[0]);
});
angular.element('#entityTabs li:eq(0) a').tab('show');
}
else 
entityService.setSelectedEntity(null);
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
entityService.selectedEntity.show = row.isSelected;
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
function closeEntityDetail(){ 
restrictionEntityService.setSelectedEntity(null);
restrictionEntityService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityService);
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
