(function() { 

angular
.module("serverTestApp")
.controller("GenerationRunController",GenerationRunController);
/** @ngInject */
function GenerationRunController($scope,$http,$rootScope,$log,UtilityService ,generationRunService, SecurityService, MainService ,projectService)
{
var vm=this;
vm.activeTab=1;
vm.searchBean=generationRunService.searchBean;
vm.entityList=generationRunService.entityList;
vm.selectedEntity=generationRunService.selectedEntity;
vm.projectPreparedData=projectService.preparedData;
function reset()
{
generationRunService.resetSearchBean();
vm.searchBean=generationRunService.searchBean;
generationRunService.setSelectedEntity(null);
generationRunService.selectedEntity.show=false;
generationRunService.setEntityList(null); 
if (generationRunService.isParent()) 
{
projectService.selectedEntity.show=false;
delete $rootScope.openNode.project;
UtilityService.removeObjectFromList($rootScope.parentServices,projectService);
}
}
function addNew()
{
$rootScope.openNode.generationRun=true;
generationRunService.setSelectedEntity(null);
generationRunService.setEntityList(null);
generationRunService.selectedEntity.show=true;
if (generationRunService.isParent()) 
{
projectService.selectedEntity.show=false;
delete $rootScope.openNode.project;
UtilityService.removeObjectFromList($rootScope.parentServices,projectService);
}
//angular.element('#generationRunTabs li:eq(0) a').tab('show');
}
		
function search()
{
generationRunService.selectedEntity.show=false;
delete $rootScope.openNode.generationRun;
UtilityService.removeObjectFromList($rootScope.parentServices,generationRunService);
generationRunService.search().then(function successCallback(response) {
generationRunService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
function insert()
{
if (!vm.generationRunDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
generationRunService.insert().then(function successCallback(response) { 
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
generationRunService.selectedEntity.show=false;
generationRunService.insert().then(function successCallBack(response) { 
$log.debug(response);
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removegenerationRun(generationRunService.selectedEntity);
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
if (!vm.generationRunDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
projectService.selectedEntity.show=false;
delete $rootScope.openNode.project;
UtilityService.removeObjectFromList($rootScope.parentServices,projectService);
generationRunService.update().then(function successCallback(response) { 
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
generationRunService.selectedEntity.show=false;

generationRunService.update().then(function successCallback(response){
generationRunService.setSelectedEntity(response.data);
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
generationRunService.selectedEntity.show=false;
delete $rootScope.openNode.generationRun;
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeGenerationRun(generationRunService.selectedEntity);
UtilityService.removeObjectFromList($rootScope.parentServices,generationRunService);
generationRunService.setSelectedEntity(null);
}
function del()
{
$rootScope.parentServices.pop();
generationRunService.del().then(function successCallback(response) { 
$log.debug(response);
generationRunService.setSelectedEntity(null);
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
function loadFile(file,field)
{
generationRunService.loadFile(file,field).then(function successCallback(response) {
generationRunService.setSelectedEntity(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
vm.trueFalseValues=['',true,false];
 function showProjectDetail(index)
{
if (index!=null)
{
projectService.searchOne(generationRunService.selectedEntity.projectList[index]).then(
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
if (generationRunService.selectedEntity.project==null || generationRunService.selectedEntity.project==undefined)
{
projectService.setSelectedEntity(null); 
projectService.selectedEntity.show=true; 
$rootScope.openNode.project=true;
}
else
projectService.searchOne(generationRunService.selectedEntity.project).then(
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
//angular.element('#projectTabs li:eq(0) a').tab('show');
}
vm.showProjectDetail=showProjectDetail;
function downloadList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("generationRun.xls",?) FROM ?',[mystyle,vm.entityList]);
}
function downloadProjectList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("project.xls",?) FROM ?',[mystyle,vm.selectedEntity.projectList]);
}
vm.downloadProjectList=downloadProjectList;
vm.generationRunGridOptions={};
UtilityService.cloneObject(generationRunService.gridOptions,vm.generationRunGridOptions);
vm.generationRunGridOptions.data=generationRunService.entityList;
vm.initChildrenList = function () { 
}
vm.generationRunGridOptions.onRegisterApi = function(gridApi){
vm.generationRunGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
vm.activeTab=1;
generationRunService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.generationRun=true;
$rootScope.parentServices.push(generationRunService);
generationRunService.setSelectedEntity(response.data[0]);
});
//angular.element('#generationRunTabs li:eq(0) a').tab('show');
}
else 
generationRunService.setSelectedEntity(null);
delete $rootScope.openNode.generationRun;
UtilityService.removeObjectFromList($rootScope.parentServices,generationRunService);
generationRunService.selectedEntity.show = row.isSelected;
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
vm.activeTab=1;
projectService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.project=true;
$rootScope.parentServices.push(projectService);
projectService.setSelectedEntity(response.data[0]);
});
//angular.element('#projectTabs li:eq(0) a').tab('show');
}
else 
projectService.setSelectedEntity(null);
delete $rootScope.openNode.project;
UtilityService.removeObjectFromList($rootScope.parentServices,projectService);
projectService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
projectService.initGenerationRunList().then(function(response) {
generationRunService.preparedData.entityList=response.data;
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
generationRunService.setSelectedEntity(null);
generationRunService.selectedEntity.show=false;
delete $rootScope.openNode.generationRun;
UtilityService.removeObjectFromList($rootScope.parentServices,generationRunService);
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
