(function() { 

angular
.module("serverTest")
.controller("EntityGroupController",EntityGroupController);
/** @ngInject */
function EntityGroupController($scope,$http,$rootScope,$log,UtilityService ,entityGroupService, SecurityService, MainService ,restrictionEntityGroupService,entityService,projectService)
{
var vm=this;
vm.activeTab=1;
vm.searchBean=entityGroupService.searchBean;
vm.entityList=entityGroupService.entityList;
vm.selectedEntity=entityGroupService.selectedEntity;
vm.restrictionEntityGroupPreparedData=restrictionEntityGroupService.preparedData;
vm.entityPreparedData=entityService.preparedData;
vm.projectPreparedData=projectService.preparedData;
vm.securityTypePreparedData={};
vm.securityTypePreparedData.entityList=["BLOCK_WITH_RESTRICTION","ACCESS_WITH_PERMISSION" ];
function reset()
{
entityGroupService.resetSearchBean();
vm.searchBean=entityGroupService.searchBean;
entityGroupService.setSelectedEntity(null);
entityGroupService.selectedEntity.show=false;
entityGroupService.setEntityList(null); 
if (entityGroupService.isParent()) 
{
restrictionEntityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntityGroup;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityGroupService);
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
projectService.selectedEntity.show=false;
delete $rootScope.openNode.project;
UtilityService.removeObjectFromList($rootScope.parentServices,projectService);
}
}
function addNew()
{
$rootScope.openNode.entityGroup=true;
entityGroupService.setSelectedEntity(null);
entityGroupService.setEntityList(null);
entityGroupService.selectedEntity.show=true;
if (entityGroupService.isParent()) 
{
restrictionEntityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntityGroup;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityGroupService);
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
projectService.selectedEntity.show=false;
delete $rootScope.openNode.project;
UtilityService.removeObjectFromList($rootScope.parentServices,projectService);
}
angular.element('#entityGroupTabs li:eq(0) a').tab('show');
}
		
function search()
{
entityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.entityGroup;
UtilityService.removeObjectFromList($rootScope.parentServices,entityGroupService);
entityGroupService.searchBean.restrictionEntityGroupList=[];
entityGroupService.searchBean.restrictionEntityGroupList.push(entityGroupService.searchBean.restrictionEntityGroup);
delete entityGroupService.searchBean.restrictionEntityGroup; 
entityGroupService.searchBean.entityList=[];
entityGroupService.searchBean.entityList.push(entityGroupService.searchBean.entity);
delete entityGroupService.searchBean.entity; 
entityGroupService.search().then(function successCallback(response) {
entityGroupService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
function insert()
{
if (!$scope.entityGroupDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
entityGroupService.insert().then(function successCallback(response) { 
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
entityGroupService.selectedEntity.show=false;
entityGroupService.insert().then(function successCallBack(response) { 
$log.debug(response);
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeentityGroup(entityGroupService.selectedEntity);
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
if (!$scope.entityGroupDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
restrictionEntityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntityGroup;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityGroupService);
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
projectService.selectedEntity.show=false;
delete $rootScope.openNode.project;
UtilityService.removeObjectFromList($rootScope.parentServices,projectService);
entityGroupService.update().then(function successCallback(response) { 
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
entityGroupService.selectedEntity.show=false;

entityGroupService.update().then(function successCallback(response){
entityGroupService.setSelectedEntity(response.data);
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
entityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.entityGroup;
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeEntityGroup(entityGroupService.selectedEntity);
UtilityService.removeObjectFromList($rootScope.parentServices,entityGroupService);
entityGroupService.setSelectedEntity(null);
}
function del()
{
$rootScope.parentServices.pop();
entityGroupService.del().then(function successCallback(response) { 
$log.debug(response);
entityGroupService.setSelectedEntity(null);
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
if ($scope.restrictionEntityGroupGridApi!=undefined && $scope.restrictionEntityGroupGridApi!=null)
 $scope.restrictionEntityGroupGridApi.core.handleWindowResize(); 
if ($scope.entityGridApi!=undefined && $scope.entityGridApi!=null)
 $scope.entityGridApi.core.handleWindowResize(); 
if ($scope.projectGridApi!=undefined && $scope.projectGridApi!=null)
 $scope.projectGridApi.core.handleWindowResize(); 
}
vm.refreshTableDetail=refreshTableDetail;
function loadFile(file,field)
{
entityGroupService.loadFile(file,field).then(function successCallback(response) {
entityGroupService.setSelectedEntity(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
vm.trueFalseValues=['',true,false];
 function showRestrictionEntityGroupDetail(index)
{
if (index!=null)
{
restrictionEntityGroupService.searchOne(entityGroupService.selectedEntity.restrictionEntityGroupList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
restrictionEntityGroupService.selectedEntity.show=true;
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
if (entityGroupService.selectedEntity.restrictionEntityGroup==null || entityGroupService.selectedEntity.restrictionEntityGroup==undefined)
{
restrictionEntityGroupService.setSelectedEntity(null); 
restrictionEntityGroupService.selectedEntity.show=true; 
$rootScope.openNode.restrictionEntityGroup=true;
}
else
restrictionEntityGroupService.searchOne(entityGroupService.selectedEntity.restrictionEntityGroup).then(
function successCallback(response) {
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
restrictionEntityGroupService.selectedEntity.show=true;
$rootScope.openNode.restrictionEntityGroup=true;
$rootScope.parentServices.push(restrictionEntityGroupService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
}
vm.showRestrictionEntityGroupDetail=showRestrictionEntityGroupDetail;
 function showEntityDetail(index)
{
if (index!=null)
{
entityService.searchOne(entityGroupService.selectedEntity.entityList[index]).then(
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
if (entityGroupService.selectedEntity.entity==null || entityGroupService.selectedEntity.entity==undefined)
{
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
$rootScope.openNode.entity=true;
}
else
entityService.searchOne(entityGroupService.selectedEntity.entity).then(
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
 function showProjectDetail(index)
{
if (index!=null)
{
projectService.searchOne(entityGroupService.selectedEntity.projectList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
projectService.setSelectedEntity(response.data[0]);
projectService.selectedEntity.show=true;
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
if (entityGroupService.selectedEntity.project==null || entityGroupService.selectedEntity.project==undefined)
{
projectService.setSelectedEntity(null); 
projectService.selectedEntity.show=true; 
$rootScope.openNode.project=true;
}
else
projectService.searchOne(entityGroupService.selectedEntity.project).then(
function successCallback(response) {
projectService.setSelectedEntity(response.data[0]);
projectService.selectedEntity.show=true;
$rootScope.openNode.project=true;
$rootScope.parentServices.push(projectService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#projectTabs li:eq(0) a').tab('show');
}
vm.showProjectDetail=showProjectDetail;
function downloadList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("entityGroup.xls",?) FROM ?',[mystyle,vm.entityList]);
}
function saveLinkedRestrictionEntityGroup() {
entityGroupService.selectedEntity.restrictionEntityGroupList.push(entityGroupService.selectedEntity.restrictionEntityGroup);
}
vm.saveLinkedRestrictionEntityGroup=saveLinkedRestrictionEntityGroup;
function downloadRestrictionEntityGroupList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("restrictionEntityGroup.xls",?) FROM ?',[mystyle,vm.selectedEntity.restrictionEntityGroupList]);
}
vm.downloadRestrictionEntityGroupList=downloadRestrictionEntityGroupList;
function saveLinkedEntity() {
entityGroupService.selectedEntity.entityList.push(entityGroupService.selectedEntity.entity);
}
vm.saveLinkedEntity=saveLinkedEntity;
function downloadEntityList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,vm.selectedEntity.entityList]);
}
vm.downloadEntityList=downloadEntityList;
function downloadProjectList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("project.xls",?) FROM ?',[mystyle,vm.selectedEntity.projectList]);
}
vm.downloadProjectList=downloadProjectList;
vm.entityGroupGridOptions={};
UtilityService.cloneObject(entityGroupService.gridOptions,vm.entityGroupGridOptions);
vm.entityGroupGridOptions.data=entityGroupService.entityList;
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
$rootScope.parentServices.push(entityGroupService);
entityGroupService.setSelectedEntity(response.data[0]);
});
angular.element('#entityGroupTabs li:eq(0) a').tab('show');
}
else 
entityGroupService.setSelectedEntity(null);
delete $rootScope.openNode.entityGroup;
UtilityService.removeObjectFromList($rootScope.parentServices,entityGroupService);
entityGroupService.selectedEntity.show = row.isSelected;
});
  };
vm.restrictionEntityGroupGridOptions={};
UtilityService.cloneObject(restrictionEntityGroupService.gridOptions,vm.restrictionEntityGroupGridOptions);
vm.restrictionEntityGroupGridOptions.data=vm.selectedEntity.restrictionEntityGroupList;
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
$rootScope.parentServices.push(restrictionEntityGroupService);
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
});
angular.element('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
}
else 
restrictionEntityGroupService.setSelectedEntity(null);
delete $rootScope.openNode.restrictionEntityGroup;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityGroupService);
restrictionEntityGroupService.selectedEntity.show = row.isSelected;
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
vm.projectGridOptions={};
UtilityService.cloneObject(projectService.gridOptions,vm.projectGridOptions);
vm.projectGridOptions.data=vm.selectedEntity.projectList;
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
$rootScope.parentServices.push(projectService);
projectService.setSelectedEntity(response.data[0]);
});
angular.element('#projectTabs li:eq(0) a').tab('show');
}
else 
projectService.setSelectedEntity(null);
delete $rootScope.openNode.project;
UtilityService.removeObjectFromList($rootScope.parentServices,projectService);
projectService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
restrictionEntityGroupService.initEntityGroupList().then(function(response) {
entityGroupService.preparedData.entityList=response.data;
});

if (restrictionEntityGroupService.selectedEntity.restrictionEntityGroupId!=undefined) restrictionEntityGroupService.searchOne(restrictionEntityGroupService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
entityService.initEntityGroupList().then(function(response) {
entityGroupService.preparedData.entityList=response.data;
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
projectService.initEntityGroupList().then(function(response) {
entityGroupService.preparedData.entityList=response.data;
});

if (projectService.selectedEntity.projectId!=undefined) projectService.searchOne(projectService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
projectService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
function closeEntityDetail(){ 
entityGroupService.setSelectedEntity(null);
entityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.entityGroup;
UtilityService.removeObjectFromList($rootScope.parentServices,entityGroupService);
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
