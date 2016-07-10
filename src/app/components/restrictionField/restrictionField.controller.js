(function() { 

angular
.module("serverTestApp")
.controller("RestrictionFieldController",RestrictionFieldController);
/** @ngInject */
function RestrictionFieldController($scope,$http,$rootScope,$log,UtilityService ,restrictionFieldService, SecurityService, MainService ,enumFieldService,generationRunService)
{
var vm=this;
vm.activeTab=1;
vm.searchBean=restrictionFieldService.searchBean;
vm.entityList=restrictionFieldService.entityList;
vm.selectedEntity=restrictionFieldService.selectedEntity;
vm.enumFieldPreparedData=enumFieldService.preparedData;
vm.generationRunPreparedData=generationRunService.preparedData;
function reset()
{
restrictionFieldService.resetSearchBean();
vm.searchBean=restrictionFieldService.searchBean;
restrictionFieldService.setSelectedEntity(null);
restrictionFieldService.selectedEntity.show=false;
restrictionFieldService.setEntityList(null); 
if (restrictionFieldService.isParent()) 
{
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
generationRunService.selectedEntity.show=false;
delete $rootScope.openNode.generationRun;
UtilityService.removeObjectFromList($rootScope.parentServices,generationRunService);
}
}
function addNew()
{
$rootScope.openNode.restrictionField=true;
restrictionFieldService.setSelectedEntity(null);
restrictionFieldService.setEntityList(null);
restrictionFieldService.selectedEntity.show=true;
if (restrictionFieldService.isParent()) 
{
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
generationRunService.selectedEntity.show=false;
delete $rootScope.openNode.generationRun;
UtilityService.removeObjectFromList($rootScope.parentServices,generationRunService);
}
angular.element('#restrictionFieldTabs li:eq(0) a').tab('show');
}
		
function search()
{
restrictionFieldService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionField;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionFieldService);
restrictionFieldService.search().then(function successCallback(response) {
restrictionFieldService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
function insert()
{
if (!$scope.restrictionFieldDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
restrictionFieldService.insert().then(function successCallback(response) { 
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
restrictionFieldService.selectedEntity.show=false;
restrictionFieldService.insert().then(function successCallBack(response) { 
$log.debug(response);
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removerestrictionField(restrictionFieldService.selectedEntity);
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
if (!$scope.restrictionFieldDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
generationRunService.selectedEntity.show=false;
delete $rootScope.openNode.generationRun;
UtilityService.removeObjectFromList($rootScope.parentServices,generationRunService);
restrictionFieldService.update().then(function successCallback(response) { 
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
restrictionFieldService.selectedEntity.show=false;

restrictionFieldService.update().then(function successCallback(response){
restrictionFieldService.setSelectedEntity(response.data);
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
restrictionFieldService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionField;
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeRestrictionField(restrictionFieldService.selectedEntity);
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionFieldService);
restrictionFieldService.setSelectedEntity(null);
}
function del()
{
$rootScope.parentServices.pop();
restrictionFieldService.del().then(function successCallback(response) { 
$log.debug(response);
restrictionFieldService.setSelectedEntity(null);
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
if ($scope.enumFieldGridApi!=undefined && $scope.enumFieldGridApi!=null)
 $scope.enumFieldGridApi.core.handleWindowResize(); 
if ($scope.generationRunGridApi!=undefined && $scope.generationRunGridApi!=null)
 $scope.generationRunGridApi.core.handleWindowResize(); 
}
vm.refreshTableDetail=refreshTableDetail;
function loadFile(file,field)
{
restrictionFieldService.loadFile(file,field).then(function successCallback(response) {
restrictionFieldService.setSelectedEntity(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
vm.trueFalseValues=['',true,false];
 function showEnumFieldDetail(index)
{
if (index!=null)
{
enumFieldService.searchOne(restrictionFieldService.selectedEntity.enumFieldList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
enumFieldService.setSelectedEntity(response.data[0]);
enumFieldService.selectedEntity.show=true;
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
if (restrictionFieldService.selectedEntity.enumField==null || restrictionFieldService.selectedEntity.enumField==undefined)
{
enumFieldService.setSelectedEntity(null); 
enumFieldService.selectedEntity.show=true; 
$rootScope.openNode.enumField=true;
}
else
enumFieldService.searchOne(restrictionFieldService.selectedEntity.enumField).then(
function successCallback(response) {
enumFieldService.setSelectedEntity(response.data[0]);
enumFieldService.selectedEntity.show=true;
$rootScope.openNode.enumField=true;
$rootScope.parentServices.push(enumFieldService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#enumFieldTabs li:eq(0) a').tab('show');
}
vm.showEnumFieldDetail=showEnumFieldDetail;
 function showGenerationRunDetail(index)
{
if (index!=null)
{
generationRunService.searchOne(restrictionFieldService.selectedEntity.generationRunList[index]).then(
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
if (restrictionFieldService.selectedEntity.generationRun==null || restrictionFieldService.selectedEntity.generationRun==undefined)
{
generationRunService.setSelectedEntity(null); 
generationRunService.selectedEntity.show=true; 
$rootScope.openNode.generationRun=true;
}
else
generationRunService.searchOne(restrictionFieldService.selectedEntity.generationRun).then(
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
function downloadList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("restrictionField.xls",?) FROM ?',[mystyle,vm.entityList]);
}
function downloadEnumFieldList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("enumField.xls",?) FROM ?',[mystyle,vm.selectedEntity.enumFieldList]);
}
vm.downloadEnumFieldList=downloadEnumFieldList;
function downloadGenerationRunList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("generationRun.xls",?) FROM ?',[mystyle,vm.selectedEntity.generationRunList]);
}
vm.downloadGenerationRunList=downloadGenerationRunList;
vm.restrictionFieldGridOptions={};
UtilityService.cloneObject(restrictionFieldService.gridOptions,vm.restrictionFieldGridOptions);
vm.restrictionFieldGridOptions.data=restrictionFieldService.entityList;
vm.initChildrenList = function () { 
}
vm.restrictionFieldGridOptions.onRegisterApi = function(gridApi){
vm.restrictionFieldGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionFieldService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.restrictionField=true;
$rootScope.parentServices.push(restrictionFieldService);
restrictionFieldService.setSelectedEntity(response.data[0]);
});
angular.element('#restrictionFieldTabs li:eq(0) a').tab('show');
}
else 
restrictionFieldService.setSelectedEntity(null);
delete $rootScope.openNode.restrictionField;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionFieldService);
restrictionFieldService.selectedEntity.show = row.isSelected;
});
  };
vm.enumFieldGridOptions={};
UtilityService.cloneObject(enumFieldService.gridOptions,vm.enumFieldGridOptions);
vm.enumFieldGridOptions.data=vm.selectedEntity.enumFieldList;
vm.initChildrenList = function () { 
}
vm.enumFieldGridOptions.onRegisterApi = function(gridApi){
vm.enumFieldGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
enumFieldService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.enumField=true;
$rootScope.parentServices.push(enumFieldService);
enumFieldService.setSelectedEntity(response.data[0]);
});
angular.element('#enumFieldTabs li:eq(0) a').tab('show');
}
else 
enumFieldService.setSelectedEntity(null);
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
enumFieldService.selectedEntity.show = row.isSelected;
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
function updateParentEntities() { 
}
function closeEntityDetail(){ 
restrictionFieldService.setSelectedEntity(null);
restrictionFieldService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionField;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionFieldService);
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
