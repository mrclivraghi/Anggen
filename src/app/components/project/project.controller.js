(function() { 

angular
.module("serverTestApp")
.controller("ProjectController",ProjectController);
/** @ngInject */
function ProjectController($scope,$http,$rootScope,$log,UtilityService ,projectService, SecurityService, MainService ,generationRunService,relationshipService,enumEntityService)
{
var vm=this;
vm.activeTab=1;
vm.searchBean=projectService.searchBean;
vm.entityList=projectService.entityList;
vm.selectedEntity=projectService.selectedEntity;
vm.generationRunPreparedData=generationRunService.preparedData;
vm.relationshipPreparedData=relationshipService.preparedData;
vm.enumEntityPreparedData=enumEntityService.preparedData;
vm.generationTypePreparedData={};
vm.generationTypePreparedData.entityList=["SHOW_INCLUDE","HIDE_IGNORE" ];
function reset()
{
projectService.resetSearchBean();
vm.searchBean=projectService.searchBean;
projectService.setSelectedEntity(null);
projectService.selectedEntity.show=false;
projectService.setEntityList(null); 
if (projectService.isParent()) 
{
generationRunService.selectedEntity.show=false;
delete $rootScope.openNode.generationRun;
UtilityService.removeObjectFromList($rootScope.parentServices,generationRunService);
relationshipService.selectedEntity.show=false;
delete $rootScope.openNode.relationship;
UtilityService.removeObjectFromList($rootScope.parentServices,relationshipService);
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,enumEntityService);
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
generationRunService.selectedEntity.show=false;
delete $rootScope.openNode.generationRun;
UtilityService.removeObjectFromList($rootScope.parentServices,generationRunService);
relationshipService.selectedEntity.show=false;
delete $rootScope.openNode.relationship;
UtilityService.removeObjectFromList($rootScope.parentServices,relationshipService);
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,enumEntityService);
}
angular.element('#projectTabs li:eq(0) a').tab('show');
}
		
function search()
{
projectService.selectedEntity.show=false;
delete $rootScope.openNode.project;
UtilityService.removeObjectFromList($rootScope.parentServices,projectService);
projectService.searchBean.generationRunList=[];
projectService.searchBean.generationRunList.push(projectService.searchBean.generationRun);
delete projectService.searchBean.generationRun; 
projectService.searchBean.relationshipList=[];
projectService.searchBean.relationshipList.push(projectService.searchBean.relationship);
delete projectService.searchBean.relationship; 
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
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
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
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeproject(projectService.selectedEntity);
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
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
generationRunService.selectedEntity.show=false;
delete $rootScope.openNode.generationRun;
UtilityService.removeObjectFromList($rootScope.parentServices,generationRunService);
relationshipService.selectedEntity.show=false;
delete $rootScope.openNode.relationship;
UtilityService.removeObjectFromList($rootScope.parentServices,relationshipService);
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,enumEntityService);
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
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeProject(projectService.selectedEntity);
UtilityService.removeObjectFromList($rootScope.parentServices,projectService);
projectService.setSelectedEntity(null);
}
function del()
{
$rootScope.parentServices.pop();
projectService.del().then(function successCallback(response) { 
$log.debug(response);
projectService.setSelectedEntity(null);
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
if ($scope.generationRunGridApi!=undefined && $scope.generationRunGridApi!=null)
 $scope.generationRunGridApi.core.handleWindowResize(); 
if ($scope.relationshipGridApi!=undefined && $scope.relationshipGridApi!=null)
 $scope.relationshipGridApi.core.handleWindowResize(); 
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
 function showGenerationRunDetail(index)
{
if (index!=null)
{
generationRunService.searchOne(projectService.selectedEntity.generationRunList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
generationRunService.setSelectedEntity(response.data[0]);
generationRunService.selectedEntity.show=true;
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
if (projectService.selectedEntity.generationRun==null || projectService.selectedEntity.generationRun==undefined)
{
generationRunService.setSelectedEntity(null); 
generationRunService.selectedEntity.show=true; 
$rootScope.openNode.generationRun=true;
}
else
generationRunService.searchOne(projectService.selectedEntity.generationRun).then(
function successCallback(response) {
generationRunService.setSelectedEntity(response.data[0]);
generationRunService.selectedEntity.show=true;
$rootScope.openNode.generationRun=true;
$rootScope.parentServices.push(generationRunService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#generationRunTabs li:eq(0) a').tab('show');
}
vm.showGenerationRunDetail=showGenerationRunDetail;
 function showRelationshipDetail(index)
{
if (index!=null)
{
relationshipService.searchOne(projectService.selectedEntity.relationshipList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
relationshipService.setSelectedEntity(response.data[0]);
relationshipService.selectedEntity.show=true;
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
if (projectService.selectedEntity.relationship==null || projectService.selectedEntity.relationship==undefined)
{
relationshipService.setSelectedEntity(null); 
relationshipService.selectedEntity.show=true; 
$rootScope.openNode.relationship=true;
}
else
relationshipService.searchOne(projectService.selectedEntity.relationship).then(
function successCallback(response) {
relationshipService.setSelectedEntity(response.data[0]);
relationshipService.selectedEntity.show=true;
$rootScope.openNode.relationship=true;
$rootScope.parentServices.push(relationshipService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#relationshipTabs li:eq(0) a').tab('show');
}
vm.showRelationshipDetail=showRelationshipDetail;
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
$rootScope.parentServices.push(enumEntityService);
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
function saveLinkedGenerationRun() {
projectService.selectedEntity.generationRunList.push(projectService.selectedEntity.generationRun);
}
vm.saveLinkedGenerationRun=saveLinkedGenerationRun;
function downloadGenerationRunList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("generationRun.xls",?) FROM ?',[mystyle,vm.selectedEntity.generationRunList]);
}
vm.downloadGenerationRunList=downloadGenerationRunList;
function saveLinkedRelationship() {
projectService.selectedEntity.relationshipList.push(projectService.selectedEntity.relationship);
}
vm.saveLinkedRelationship=saveLinkedRelationship;
function downloadRelationshipList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("relationship.xls",?) FROM ?',[mystyle,vm.selectedEntity.relationshipList]);
}
vm.downloadRelationshipList=downloadRelationshipList;
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
vm.generationRunGridOptions={};
UtilityService.cloneObject(generationRunService.gridOptions,vm.generationRunGridOptions);
vm.generationRunGridOptions.data=vm.selectedEntity.generationRunList;
vm.initChildrenList = function () { 
}
vm.generationRunGridOptions.onRegisterApi = function(gridApi){
vm.generationRunGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
generationRunService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.generationRun=true;
$rootScope.parentServices.push(generationRunService);
generationRunService.setSelectedEntity(response.data[0]);
});
angular.element('#generationRunTabs li:eq(0) a').tab('show');
}
else 
generationRunService.setSelectedEntity(null);
delete $rootScope.openNode.generationRun;
UtilityService.removeObjectFromList($rootScope.parentServices,generationRunService);
generationRunService.selectedEntity.show = row.isSelected;
});
  };
vm.relationshipGridOptions={};
UtilityService.cloneObject(relationshipService.gridOptions,vm.relationshipGridOptions);
vm.relationshipGridOptions.data=vm.selectedEntity.relationshipList;
vm.initChildrenList = function () { 
}
vm.relationshipGridOptions.onRegisterApi = function(gridApi){
vm.relationshipGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
relationshipService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.relationship=true;
$rootScope.parentServices.push(relationshipService);
relationshipService.setSelectedEntity(response.data[0]);
});
angular.element('#relationshipTabs li:eq(0) a').tab('show');
}
else 
relationshipService.setSelectedEntity(null);
delete $rootScope.openNode.relationship;
UtilityService.removeObjectFromList($rootScope.parentServices,relationshipService);
relationshipService.selectedEntity.show = row.isSelected;
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
$rootScope.parentServices.push(enumEntityService);
enumEntityService.setSelectedEntity(response.data[0]);
});
angular.element('#enumEntityTabs li:eq(0) a').tab('show');
}
else 
enumEntityService.setSelectedEntity(null);
delete $rootScope.openNode.enumEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,enumEntityService);
enumEntityService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
entityService.initProjectList().then(function(response) {
projectService.preparedData.entityList=response.data;
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
generationRunService.initProjectList().then(function(response) {
projectService.preparedData.entityList=response.data;
});

if (generationRunService.selectedEntity.generationRunId!=undefined) generationRunService.searchOne(generationRunService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
generationRunService.setSelectedEntity(response.data[0]);
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
UtilityService.removeObjectFromList($rootScope.parentServices,projectService);
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
