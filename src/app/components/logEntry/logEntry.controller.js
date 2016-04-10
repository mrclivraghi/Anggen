(function() { 

angular
.module("serverTestApp")
.controller("LogEntryController",LogEntryController);
/** @ngInject */
function LogEntryController($scope,$http,$rootScope ,logEntryService, SecurityService, MainService )
{
$scope.searchBean=logEntryService.searchBean;
$scope.entityList=logEntryService.entityList;
$scope.selectedEntity=logEntryService.selectedEntity;
$scope.childrenList=logEntryService.childrenList; 
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
logEntryService.setSelectedEntity(null);
logEntryService.setEntityList(null);
logEntryService.selectedEntity.show=true;
if (logEntryService.isParent()) 
{
}
$('#logEntryTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
logEntryService.selectedEntity.show=false;
logEntryService.search().then(function successCallback(response) {
logEntryService.setEntityList(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.insert=function()
{
if (!$scope.logEntryDetailForm.$valid) return; 
if (logEntryService.isParent()) 
{
logEntryService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
}
else 
{
logEntryService.selectedEntity.show=false;
logEntryService.insert().then(function successCallBack(response) { 
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
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
$scope.search();
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
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
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
}
};
$scope.remove= function()
{
logEntryService.selectedEntity.show=false;
logEntryService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!logEntryService.isParent()) 
$scope.updateParent();
logEntryService.del().then(function successCallback(response) { 
if (logEntryService.isParent()) 
{
$scope.search();
} else { 
logEntryService.setSelectedEntity(null);
}
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
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
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
return; 
});
}
$scope.trueFalseValues=[true,false];
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("logEntry.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.logEntryGridOptions={};
cloneObject(logEntryService.gridOptions,$scope.logEntryGridOptions);
$scope.logEntryGridOptions.data=logEntryService.entityList;
$scope.logEntryGridOptions.onRegisterApi = function(gridApi){
$scope.logEntryGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
logEntryService.childrenList.logTypeList=["INFO","DEBUG","WARNING","ERROR",];
logEntryService.childrenList.operationTypeList=["CREATE_ENTITY","UPDATE_ENTITY","DELETE_ENTITY","SEARCH_ENTITY","LOGIN_SUCCESS","LOGIN_FAILED","VIEW_METRICS","SECURITY_VIOLATION_ATTEMPT",];
logEntryService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
logEntryService.setSelectedEntity(response.data[0]);
});
$('#logEntryTabs li:eq(0) a').tab('show');
}
else 
logEntryService.setSelectedEntity(null);
logEntryService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
}
$scope.closeEntityDetail = function(){ 
logEntryService.setSelectedEntity(null);
logEntryService.selectedEntity.show=false;
}}
})();
