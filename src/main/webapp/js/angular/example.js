var exampleApp=angular.module("exampleApp",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])
.service("exampleService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
for (i=0; i<entityList.length; i++)
this.entityList.push(entityList[i]);
};
this.searchBean = 		new Object();
this.resetSearchBean= function()
{
this.searchBean={};
};
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
} else 
this.emptyList(this.selectedEntity[val]);
} else {
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
} else {
this.selectedEntity[val] = entity[val];
}
}
}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../example/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../example/"+entity.exampleId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../example/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../example/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../example/"+this.selectedEntity.exampleId;
var promise= $http["delete"](url);
return promise; 
}
})
.controller("exampleController",function($scope,$http,exampleService)
{
//null
$scope.searchBean=exampleService.searchBean;
$scope.entityList=exampleService.entityList;
$scope.selectedEntity=exampleService.selectedEntity;
$scope.childrenList=exampleService.childrenList; 
$scope.reset = function()
{
exampleService.resetSearchBean();
$scope.searchBean=exampleService.searchBean;exampleService.setSelectedEntity(null);
exampleService.selectedEntity.show=false;
exampleService.setEntityList(null); 
}
$scope.addNew= function()
{
exampleService.setSelectedEntity(null);
exampleService.setEntityList(null);
exampleService.selectedEntity.show=true;
$('#exampleTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
exampleService.selectedEntity.show=false;
exampleService.search().then(function successCallback(response) {
exampleService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.exampleDetailForm.$valid) return; 
exampleService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.exampleDetailForm.$valid) return; 
exampleService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.del=function()
{
nullService.selectedEntity.example=null;
exampleService.del().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
};
$scope.trueFalseValues=[true,false];
$scope.init=function()
{
exampleService.childrenList.sexList=["MALE","FEMALE",];
}; 
$scope.init();
$scope.exampleGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'exampleId'},
{ name: 'name'},
{ name: 'birthDate', cellFilter: "date:'dd-MM-yyyy'"},
{ name: 'birthTime', cellFilter: "date:'HH:mm'"},
{ name: 'sex'} 
]
,data: exampleService.entityList
 };
$scope.exampleGridOptions.onRegisterApi = function(gridApi){
$scope.exampleGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
exampleService.setSelectedEntity(row.entity);
$('#exampleTabs li:eq(0) a').tab('show');
}
else 
exampleService.setSelectedEntity(null);
exampleService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT exampleId,name,eta INTO XLSXML("example.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
})
;