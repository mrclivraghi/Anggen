(function() { 

angular
.module("serverTestApp")
.controller("ProjectController",ProjectController);
/** @ngInject */
function ProjectController($scope,$http,$rootScope,$log,UtilityService ,projectService, SecurityService, MainService ,entityGroupService,enumEntityService)
{
var vm=this;
vm.searchBean=projectService.searchBean;
vm.entityList=projectService.entityList;
vm.selectedEntity=projectService.selectedEntity;
vm.entityGroupPreparedData=entityGroupService.preparedData;
vm.enumEntityPreparedData=enumEntityService.preparedData;
function reset()
{
projectService.resetSearchBean();
vm.searchBean=projectService.searchBean;
projectService.setSelectedEntity(null);
projectService.selectedEntity.show=false;
projectService.setEntityList(null); 
if (projectService.isParent()) 
{
entityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.entityGroup;
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
}
}
function addNew()
{
$rootScope.openNode.project=true;
projectService.setSelectedEntity(null);
projectService.setEntityList(null);
projectService.selectedEntity.show=true;
if (projectService.isParent()) 
{
entityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.entityGroup;
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
}
angular.element('#projectTabs li:eq(0) a').tab('show');
}
		
function search()
{
projectService.selectedEntity.show=false;
delete $rootScope.openNode.project;
projectService.searchBean.entityGroupList=[];
projectService.searchBean.entityGroupList.push(projectService.searchBean.entityGroup);
delete projectService.searchBean.entityGroup; 
projectService.searchBean.enumEntityList=[];
projectService.searchBean.enumEntityList.push(projectService.searchBean.enumEntity);
delete projectService.searchBean.enumEntity; 
projectService.search().then(function successCallback(response) {
projectService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
function insert()
{
if (!$scope.projectDetailForm.$valid) return; 
if (projectService.isParent()) 
{
projectService.insert().then(function successCallback(response) { 
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
projectService.selectedEntity.show=false;
projectService.insert().then(function successCallBack(response) { 
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
if (!$scope.projectDetailForm.$valid) return; 
if (projectService.isParent()) 
{
entityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.entityGroup;
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
projectService.update().then(function successCallback(response) { 
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
projectService.selectedEntity.show=false;

projectService.update().then(function successCallback(response){
projectService.setSelectedEntity(response.data);
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
projectService.selectedEntity.show=false;
delete $rootScope.openNode.project;
projectService.setSelectedEntity(null);
}
function del()
{
projectService.del().then(function successCallback(response) { 
$log.debug(response);
if (projectService.isParent()) 
{
vm.search();
} else { 
projectService.setSelectedEntity(null);
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
if ($scope.entityGroupGridApi!=undefined && $scope.entityGroupGridApi!=null)
 $scope.entityGroupGridApi.core.handleWindowResize(); 
if ($scope.enumEntityGridApi!=undefined && $scope.enumEntityGridApi!=null)
 $scope.enumEntityGridApi.core.handleWindowResize(); 
}
vm.refreshTableDetail=refreshTableDetail;
function loadFile(file,field)
{
projectService.loadFile(file,field).then(function successCallback(response) {
projectService.setSelectedEntity(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
vm.trueFalseValues=['',true,false];
 function showEntityGroupDetail(index)
{
if (index!=null)
{
entityGroupService.searchOne(projectService.selectedEntity.entityGroupList[index]).then(
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
if (projectService.selectedEntity.entityGroup==null || projectService.selectedEntity.entityGroup==undefined)
{
entityGroupService.setSelectedEntity(null); 
entityGroupService.selectedEntity.show=true; 
$rootScope.openNode.entityGroup=true;
}
else
entityGroupService.searchOne(projectService.selectedEntity.entityGroup).then(
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
 function showEnumEntityDetail(index)
{
if (index!=null)
{
enumEntityService.searchOne(projectService.selectedEntity.enumEntityList[index]).then(
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
if (projectService.selectedEntity.enumEntity==null || projectService.selectedEntity.enumEntity==undefined)
{
enumEntityService.setSelectedEntity(null); 
enumEntityService.selectedEntity.show=true; 
$rootScope.openNode.enumEntity=true;
}
else
enumEntityService.searchOne(projectService.selectedEntity.enumEntity).then(
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
}
vm.showEnumEntityDetail=showEnumEntityDetail;
function downloadList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("project.xls",?) FROM ?',[mystyle,vm.entityList]);
}
function saveLinkedEntityGroup() {
projectService.selectedEntity.entityGroupList.push(projectService.selectedEntity.entityGroup);
}
vm.saveLinkedEntityGroup=saveLinkedEntityGroup;
function downloadEntityGroupList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("entityGroup.xls",?) FROM ?',[mystyle,vm.selectedEntity.entityGroupList]);
}
vm.downloadEntityGroupList=downloadEntityGroupList;
function saveLinkedEnumEntity() {
projectService.selectedEntity.enumEntityList.push(projectService.selectedEntity.enumEntity);
}
vm.saveLinkedEnumEntity=saveLinkedEnumEntity;
function downloadEnumEntityList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("enumEntity.xls",?) FROM ?',[mystyle,vm.selectedEntity.enumEntityList]);
}
vm.downloadEnumEntityList=downloadEnumEntityList;
vm.projectGridOptions={};
UtilityService.cloneObject(projectService.gridOptions,vm.projectGridOptions);
vm.projectGridOptions.data=projectService.entityList;
vm.initChildrenList = function () { 
}
vm.projectGridOptions.onRegisterApi = function(gridApi){
vm.projectGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
projectService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.project=true;
projectService.setSelectedEntity(response.data[0]);
});
angular.element('#projectTabs li:eq(0) a').tab('show');
}
else 
projectService.setSelectedEntity(null);
delete $rootScope.openNode.project;
projectService.selectedEntity.show = row.isSelected;
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
vm.enumEntityGridOptions={};
UtilityService.cloneObject(enumEntityService.gridOptions,vm.enumEntityGridOptions);
vm.enumEntityGridOptions.data=vm.selectedEntity.enumEntityList;
vm.initChildrenList = function () { 
}
vm.enumEntityGridOptions.onRegisterApi = function(gridApi){
vm.enumEntityGridApi = gridApi;
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
entityGroupService.initProjectList().then(function(response) {
projectService.preparedData.entityList=response.data;
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
enumEntityService.initProjectList().then(function(response) {
projectService.preparedData.entityList=response.data;
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
function closeEntityDetail(){ 
projectService.setSelectedEntity(null);
projectService.selectedEntity.show=false;
delete $rootScope.openNode.project;
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
