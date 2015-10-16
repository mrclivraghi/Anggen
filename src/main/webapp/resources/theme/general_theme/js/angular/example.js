var exampleApp=angular.module("exampleApp",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date'])
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
}
} else {
this.selectedEntity[val] = entity[val];
}
	}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../example/search",this.searchBean)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
};
this.insert = function() {
var promise= $http.put("../example/",this.selectedEntity)
.then( function(response) 
{
return response.data;
})
.catch(function() 
{ 
alert("error");
});
return promise; 
};
this.update = function() {
var promise= $http.post("../example/",this.selectedEntity)
.then( function(response) {
return response.data;
})
.catch(function() { 
alert("error");
});
return promise; 
}
this.del = function() {
var url="../example/selectedEntity.exampleId";
var promise= $http["delete"](url)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
}
})
.controller("exampleController",function($scope,$http,exampleService)
{
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
};
		
$scope.search=function()
{
exampleService.selectedEntity.show=false;
exampleService.search().then(function(data) { 
exampleService.setEntityList(data);
});
};
$scope.insert=function()
{
if (!$scope.exampleDetailForm.$valid) return; 
exampleService.insert().then(function(data) { 
$scope.search();
});
};
$scope.update=function()
{
if (!$scope.exampleDetailForm.$valid) return; 
exampleService.update().then(function(data) { 
$scope.search();
});
};
$scope.del=function()
{
exampleService.del().then(function(data) { 
$scope.search();
});
};$scope.init=function()
{
}; 
$scope.init();
$scope.exampleGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
columnDefs: [
{ name: 'exampleId'},
{ name: 'name'},
{ name: 'eta'},
{ name: 'male'},
{ name: 'birthDate', cellFilter: "date:'dd-MM-yyyy'"},
{ name: 'birthTime', cellFilter: "date:'dd-MM-yyyy'"} 
]
,data: exampleService.entityList
 };
$scope.exampleGridOptions.onRegisterApi = function(gridApi){
gridApi.selection.on.rowSelectionChanged($scope,function(row){
exampleService
.setSelectedEntity(row.entity);
exampleService.selectedEntity.show = true;
});
  };
})
;