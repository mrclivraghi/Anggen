(function() { 

angular
.module("serverTestApp")
.controller("LogEntryController",LogEntryController);
/** @ngInject */
function LogEntryController($scope,$http,$rootScope,$log,UtilityService ,logEntryService, SecurityService, MainService )
{
$scope.searchBean=logEntryService.searchBean;
$scope.entityList=logEntryService.entityList;
$scope.selectedEntity=logEntryService.selectedEntity;
$scope.hidden=logEntryService.hidden;
$scope.logTypePreparedData={};$scope.logTypePreparedData.entityList=["INFO","DEBUG","WARNING","ERROR",];
$scope.operationTypePreparedData={};$scope.operationTypePreparedData.entityList=["CREATE_ENTITY","UPDATE_ENTITY","DELETE_ENTITY","SEARCH_ENTITY","LOGIN_SUCCESS","LOGIN_FAILED","VIEW_METRICS","SECURITY_VIOLATION_ATTEMPT",];
$scope.reset = function()
{
logEntryService.resetSearchBean();
$scope.searchBean=logEntryService.searchBean;logEntryService.setSelectedEntity(null);
logEntryService.selectedEntity.show=false;
logEntryService.setEntityList(null); 
if (logEntryService.isParent()) 
{
}
}
$scope.addNew= function()
{
$rootScope.openNode.logEntry=true;
logEntryService.setSelectedEntity(null);
logEntryService.setEntityList(null);
logEntryService.selectedEntity.show=true;
if (logEntryService.isParent()) 
{
}
angular.element('#logEntryTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
logEntryService.selectedEntity.show=false;
delete $rootScope.openNode.logEntry;
logEntryService.search().then(function successCallback(response) {
logEntryService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
};
$scope.insert=function()
{
if (!$scope.logEntryDetailForm.$valid) return; 
if (logEntryService.isParent()) 
{
logEntryService.insert().then(function successCallback(response) { 
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
logEntryService.selectedEntity.show=false;
logEntryService.insert().then(function successCallBack(response) { 
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
if (!$scope.logEntryDetailForm.$valid) return; 
if (logEntryService.isParent()) 
{
logEntryService.update().then(function successCallback(response) { 
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
};
$scope.remove= function()
{
logEntryService.selectedEntity.show=false;
delete $rootScope.openNode.logEntry;
logEntryService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!logEntryService.isParent()) 
$scope.updateParent();
logEntryService.del().then(function successCallback(response) { 
$log.debug(response);
if (logEntryService.isParent()) 
{
$scope.search();
} else { 
logEntryService.setSelectedEntity(null);
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
};
$scope.loadFile = function(file,field)
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
$scope.trueFalseValues=['',true,false];
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("logEntry.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.logEntryGridOptions={};
UtilityService.cloneObject(logEntryService.gridOptions,$scope.logEntryGridOptions);
$scope.logEntryGridOptions.data=logEntryService.entityList;
$scope.initChildrenList = function () { 
}
$scope.logEntryGridOptions.onRegisterApi = function(gridApi){
$scope.logEntryGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
logEntryService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.logEntry=true;
logEntryService.setSelectedEntity(response.data[0]);
});
angular.element('#logEntryTabs li:eq(0) a').tab('show');
}
else 
logEntryService.setSelectedEntity(null);
delete $rootScope.openNode.logEntry;
logEntryService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
}
$scope.closeEntityDetail = function(){ 
logEntryService.setSelectedEntity(null);
logEntryService.selectedEntity.show=false;
delete $rootScope.openNode.logEntry;
}
}
})();
