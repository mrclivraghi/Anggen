var ambulatorioApp=angular.module("ambulatorioApp",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])
.service("ambulatorioService", function($http)
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
var promise= $http.post("../ambulatorio/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
this.setSelectedEntity(null);
var promise= $http.get("../ambulatorio/"+entity.ambulatorioId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../ambulatorio/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../ambulatorio/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../ambulatorio/"+this.selectedEntity.ambulatorioId;
var promise= $http["delete"](url);
return promise; 
}
})
.controller("ambulatorioController",function($scope,$http,ambulatorioService)
{
$scope.searchBean=ambulatorioService.searchBean;
$scope.entityList=ambulatorioService.entityList;
$scope.selectedEntity=ambulatorioService.selectedEntity;
$scope.childrenList=ambulatorioService.childrenList; 
$scope.reset = function()
{
ambulatorioService.resetSearchBean();
$scope.searchBean=ambulatorioService.searchBean;ambulatorioService.setSelectedEntity(null);
ambulatorioService.selectedEntity.show=false;
ambulatorioService.setEntityList(null); 
}
$scope.addNew= function()
{
ambulatorioService.setSelectedEntity(null);
ambulatorioService.setEntityList(null);
ambulatorioService.selectedEntity.show=true;
$('#ambulatorioTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
ambulatorioService.selectedEntity.show=false;
ambulatorioService.search().then(function successCallback(response) {
ambulatorioService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.ambulatorioDetailForm.$valid) return; 
ambulatorioService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.ambulatorioDetailForm.$valid) return; 
ambulatorioService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.del=function()
{
nullService.selectedEntity.ambulatorio=null;
ambulatorioService.del().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.trueFalseValues=[true,false];
$scope.init=function()
{
}; 
$scope.init();
$scope.ambulatorioGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'ambulatorioId'},
{ name: 'nome'},
{ name: 'indirizzo'} 
]
,data: ambulatorioService.entityList
 };
$scope.ambulatorioGridOptions.onRegisterApi = function(gridApi){
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
ambulatorioService.setSelectedEntity(row.entity);
$('#ambulatorioTabs li:eq(0) a').tab('show');
}
else 
ambulatorioService.setSelectedEntity(null);
ambulatorioService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("ambulatorio.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
})
;