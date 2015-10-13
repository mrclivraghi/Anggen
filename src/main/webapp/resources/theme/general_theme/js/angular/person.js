var personApp=angular.module("personApp",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date'])
.service("personService", function($http)
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
this.searchBean.orderId="";
this.searchBean.name="";
this.searchBean.timeslotDate="";
};
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& typeof entity[val] == "object") {
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
if (val.toLowerCase().indexOf("date") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(
date.getFullYear(), date
.getMonth(), date
.getDate());
} else {
this.selectedEntity[val] = entity[val];
}
}
	}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../person/search",this.searchBean)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
};
this.insert = function() {
var promise= $http.put("../person/",this.selectedEntity)
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
var promise= $http.post("../person/",this.selectedEntity)
.then( function(response) {
return response.data;
})
.catch(function() { 
alert("error");
});
return promise; 
}
this.del = function() {
var url="../person/selectedEntity.personId";
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
.controller("personController",function($scope,$http,personService)
{
$scope.searchBean=personService.searchBean;
$scope.entityList=personService.entityList;
$scope.selectedEntity=personService.selectedEntity;
$scope.childrenList=personService.childrenList; 
$scope.reset = function()
{
personService.resetSearchBean();
personService.setSelectedEntity(null);
personService.selectedEntity.show=false;
personService.setEntityList(null); 
}
$scope.addNew= function()
{
personService.setSelectedEntity(null);
personService.selectedEntity.show=true;
};
		
$scope.search=function()
{
personService.selectedEntity.show=false;
personService.search().then(function(data) { 
personService.setEntityList(data);
});
};
$scope.insert=function()
{
if (!$scope.personDetailForm.$valid) return; 
personService.insert().then(function(data) { 
$scope.search();
});
};
$scope.update=function()
{
if (!$scope.personDetailForm.$valid) return; 
personService.update().then(function(data) { 
$scope.search();
});
};
$scope.del=function()
{
personService.del().then(function(data) { 
$scope.search();
});
};$scope.init=function()
{
}; 
$scope.init();
$scope.personGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
columnDefs: [
{ name: 'personId'},
{ name: 'firstName'},
{ name: 'lastName'},
{ name: 'birthDate', cellFilter: "date:'dd-MM-yyyy'"} 
]
,data: personService.entityList
 };
$scope.personGridOptions.onRegisterApi = function(gridApi){
gridApi.selection.on.rowSelectionChanged($scope,function(row){
personService
.setSelectedEntity(row.entity);
personService.selectedEntity.show = true;
});
  };
})
;