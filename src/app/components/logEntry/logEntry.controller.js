(function() { 

angular
.module("serverTestApp")
.controller("LogEntryController",LogEntryController);
/** @ngInject */
function LogEntryController($scope,$http,$rootScope,$log,UtilityService ,logEntryService, SecurityService, MainService )
{
var vm=this;
vm.activeTab=1;
vm.searchBean=logEntryService.searchBean;
vm.entityList=logEntryService.entityList;
vm.selectedEntity=logEntryService.selectedEntity;
vm.logTypePreparedData={};
vm.logTypePreparedData.entityList=["INFO","DEBUG","WARNING","ERROR" ];
vm.operationTypePreparedData={};
vm.operationTypePreparedData.entityList=["CREATE_ENTITY","UPDATE_ENTITY","DELETE_ENTITY","SEARCH_ENTITY","LOGIN_SUCCESS","LOGIN_FAILED","VIEW_METRICS","SECURITY_VIOLATION_ATTEMPT" ];
function reset()
{
logEntryService.resetSearchBean();
vm.searchBean=logEntryService.searchBean;
logEntryService.setSelectedEntity(null);
logEntryService.selectedEntity.show=false;
logEntryService.setEntityList(null); 
if (logEntryService.isParent()) 
{
}
}
function addNew()
{
$rootScope.openNode.logEntry=true;
logEntryService.setSelectedEntity(null);
logEntryService.setEntityList(null);
logEntryService.selectedEntity.show=true;
if (logEntryService.isParent()) 
{
}
//angular.element('#logEntryTabs li:eq(0) a').tab('show');
}
		
function search()
{
logEntryService.selectedEntity.show=false;
delete $rootScope.openNode.logEntry;
UtilityService.removeObjectFromList($rootScope.parentServices,logEntryService);
logEntryService.search().then(function successCallback(response) {
logEntryService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
function insert()
{
if (!vm.logEntryDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
logEntryService.insert().then(function successCallback(response) { 
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
logEntryService.selectedEntity.show=false;
logEntryService.insert().then(function successCallBack(response) { 
$log.debug(response);
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removelogEntry(logEntryService.selectedEntity);
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
if (!vm.logEntryDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
logEntryService.update().then(function successCallback(response) { 
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
logEntryService.selectedEntity.show=false;

logEntryService.update().then(function successCallback(response){
logEntryService.setSelectedEntity(response.data);
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
logEntryService.selectedEntity.show=false;
delete $rootScope.openNode.logEntry;
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeLogEntry(logEntryService.selectedEntity);
UtilityService.removeObjectFromList($rootScope.parentServices,logEntryService);
logEntryService.setSelectedEntity(null);
}
function del()
{
$rootScope.parentServices.pop();
logEntryService.del().then(function successCallback(response) { 
$log.debug(response);
logEntryService.setSelectedEntity(null);
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
logEntryService.loadFile(file,field).then(function successCallback(response) {
logEntryService.setSelectedEntity(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
vm.trueFalseValues=['',true,false];
function downloadList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("logEntry.xls",?) FROM ?',[mystyle,vm.entityList]);
}
vm.logEntryGridOptions={};
UtilityService.cloneObject(logEntryService.gridOptions,vm.logEntryGridOptions);
vm.logEntryGridOptions.data=logEntryService.entityList;
vm.initChildrenList = function () { 
}
vm.logEntryGridOptions.onRegisterApi = function(gridApi){
vm.logEntryGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
vm.activeTab=1;
logEntryService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.logEntry=true;
$rootScope.parentServices.push(logEntryService);
logEntryService.setSelectedEntity(response.data[0]);
});
//angular.element('#logEntryTabs li:eq(0) a').tab('show');
}
else 
logEntryService.setSelectedEntity(null);
delete $rootScope.openNode.logEntry;
UtilityService.removeObjectFromList($rootScope.parentServices,logEntryService);
logEntryService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
}
function closeEntityDetail(){ 
logEntryService.setSelectedEntity(null);
logEntryService.selectedEntity.show=false;
delete $rootScope.openNode.logEntry;
UtilityService.removeObjectFromList($rootScope.parentServices,logEntryService);
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
