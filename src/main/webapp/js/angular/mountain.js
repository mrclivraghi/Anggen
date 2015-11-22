var mountainApp=angular.module("mountainApp",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])
.service("mountainService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,seedQueryList: []};
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
var promise= $http.post("../mountain/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../mountain/"+entity.mountainId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../mountain/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../mountain/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../mountain/"+this.selectedEntity.mountainId;
var promise= $http["delete"](url);
return promise; 
}
 this.initSeedQueryList= function()
{
var promise= $http
.post("../seed/search",
{});
return promise;
};
})
.controller("mountainController",function($scope,$http,mountainService,seedQueryService)
{
//null
$scope.searchBean=mountainService.searchBean;
$scope.entityList=mountainService.entityList;
$scope.selectedEntity=mountainService.selectedEntity;
$scope.childrenList=mountainService.childrenList; 
$scope.reset = function()
{
mountainService.resetSearchBean();
$scope.searchBean=mountainService.searchBean;mountainService.setSelectedEntity(null);
mountainService.selectedEntity.show=false;
mountainService.setEntityList(null); 
seedQueryService.selectedEntity.show=false;}
$scope.addNew= function()
{
mountainService.setSelectedEntity(null);
mountainService.setEntityList(null);
mountainService.selectedEntity.show=true;
seedQueryService.selectedEntity.show=false;$('#mountainTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
mountainService.selectedEntity.show=false;
mountainService.searchBean.seedQueryList=[];
mountainService.searchBean.seedQueryList.push(mountainService.searchBean.seedQuery);
delete mountainService.searchBean.seedQuery; 
mountainService.search().then(function successCallback(response) {
mountainService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.mountainDetailForm.$valid) return; 
mountainService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.mountainDetailForm.$valid) return; 
seedQueryService.selectedEntity.show=false;mountainService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.del=function()
{
nullService.selectedEntity.mountain=null;
mountainService.del().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDefault= function() 
{
};
$scope.trueFalseValues=[true,false];
$scope.showSeedQueryDetail= function(index)
{
if (index!=null)
{
seedQueryService.searchOne(mountainService.selectedEntity.seedQueryList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
seedService.setSelectedEntity(response.data[0]);
seedService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (mountainService.selectedEntity.seedQuery==null || mountainService.selectedEntity.seedQuery==undefined)
{
seedService.setSelectedEntity(null); 
seedService.selectedEntity.show=true; 
}
else
seedService.searchOne(mountainService.selectedEntity.seedQuery).then(
function successCallback(response) {
seedService.setSelectedEntity(response.data[0]);
seedService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#seedQueryTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
mountainService.initSeedQueryList().then(function successCallback(response) {
mountainService.childrenList.seedQueryList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
$scope.mountainGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'mountainId'},
{ name: 'name'} 
]
,data: mountainService.entityList
 };
$scope.mountainGridOptions.onRegisterApi = function(gridApi){
$scope.mountainGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
seedQueryService.selectedEntity.show=false;if (row.isSelected)
{
mountainService.setSelectedEntity(row.entity);
$('#mountainTabs li:eq(0) a').tab('show');
}
else 
mountainService.setSelectedEntity(null);
mountainService.selectedEntity.show = row.isSelected;
});
  };
$scope.seedQueryListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'seedQueryId'},
{ name: 'name'} 
]
,data: $scope.selectedEntity.seedQueryList
 };
$scope.seedQueryListGridOptions.onRegisterApi = function(gridApi){
$scope.seedQueryGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
seedService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
seedService.setSelectedEntity(response.data[0]);
});
$('#seedQueryTabs li:eq(0) a').tab('show');
}
else 
seedService.setSelectedEntity(null);
seedService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT mountainId,name INTO XLSXML("mountain.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedSeedQuery= function() {
mountainService.selectedEntity.seedQueryList.push(mountainService.selectedEntity.seedQuery);
}
$scope.downloadSeedQueryList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT seedQuery,seedQuery INTO XLSXML("seedQuery.xls",?) FROM ?',[mystyle,$scope.selectedEntity.seedQueryList]);
};
})
.service("seedQueryService", function($http)
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
var promise= $http.post("../seedQuery/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../seedQuery/"+entity.seedQueryId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../seedQuery/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../seedQuery/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../seedQuery/"+this.selectedEntity.seedQueryId;
var promise= $http["delete"](url);
return promise; 
}
 this.initMountainList= function()
{
var promise= $http
.post("../mountain/search",
{});
return promise;
};
})
.controller("seedQueryController",function($scope,$http,seedQueryService,mountainService)
{
//mountain
$scope.searchBean=seedService.searchBean;
$scope.entityList=seedService.entityList;
$scope.selectedEntity=seedService.selectedEntity;
$scope.childrenList=seedService.childrenList; 
$scope.reset = function()
{
seedService.resetSearchBean();
$scope.searchBean=seedQueryService.searchBean;seedService.setSelectedEntity(null);
seedService.selectedEntity.show=false;
seedService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
mountainService.update().then(function successCallback(response) {
mountainService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
alert("error");
return; 
}
);
};
$scope.addNew= function()
{
seedService.setSelectedEntity(null);
seedService.setEntityList(null);
seedService.selectedEntity.show=true;
$('#seedQueryTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
seedService.selectedEntity.show=false;
seedService.search().then(function successCallback(response) {
seedService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.seedQueryDetailForm.$valid) return; 
seedService.selectedEntity.show=false;
seedService.selectedEntity.mountainList.push(mountainService.selectedEntity);
seedService.insert().then(function successCallBack(response) { 
mountainService.selectedEntity.seedQueryList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.seedQueryDetailForm.$valid) return; 
seedService.selectedEntity.show=false;

for (i=0; i<mountainService.selectedEntity.seedQueryList.length; i++)

{

if (mountainService.selectedEntity.seedQueryList[i].seedQueryId==seedQueryService.selectedEntity.seedQueryId)

mountainService.selectedEntity.seedQueryList.splice(i,1);

}

mountainService.selectedEntity.seedQueryList.push(seedQueryService.selectedEntity);

seedService.update().then(function successCallback(response){
seedService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
seedService.selectedEntity.show=false;
for (i=0; i<mountainService.selectedEntity.seedQueryList.length; i++)
{
if (mountainService.selectedEntity.seedQueryList[i].seedQueryId==seedQueryService.selectedEntity.seedQueryId)
mountainService.selectedEntity.seedQueryList.splice(i,1);
}
seedService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<mountainService.selectedEntity.seedQueryList.length; i++)
{
if (mountainService.selectedEntity.seedQueryList[i].seedQueryId==seedQueryService.selectedEntity.seedQueryId)
mountainService.selectedEntity.seedQueryList.splice(i,1);
}
$scope.updateParent();
seedService.del().then(function successCallback(response) { 
seedService.setSelectedEntity(null);
mountainService.initSeedQueryList().then(function(response) {
mountainService.childrenList.seedQueryList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDefault= function() 
{
};
$scope.trueFalseValues=[true,false];
$scope.showMountainDetail= function(index)
{
if (index!=null)
{
mountainService.searchOne(seedQueryService.selectedEntity.mountainList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
mountainService.setSelectedEntity(response.data[0]);
mountainService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (seedService.selectedEntity.mountain==null || seedQueryService.selectedEntity.mountain==undefined)
{
mountainService.setSelectedEntity(null); 
mountainService.selectedEntity.show=true; 
}
else
mountainService.searchOne(seedQueryService.selectedEntity.mountain).then(
function successCallback(response) {
mountainService.setSelectedEntity(response.data[0]);
mountainService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#mountainTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
seedService.initMountainList().then(function successCallback(response) {
seedService.childrenList.mountainList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
$scope.mountainListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'mountainId'},
{ name: 'name'} 
]
,data: $scope.selectedEntity.mountainList
 };
$scope.mountainListGridOptions.onRegisterApi = function(gridApi){
$scope.mountainGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
mountainService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
mountainService.setSelectedEntity(response.data[0]);
});
$('#mountainTabs li:eq(0) a').tab('show');
}
else 
mountainService.setSelectedEntity(null);
mountainService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT seedQueryId,name INTO XLSXML("seedQuery.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadMountainList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT mountain,mountain INTO XLSXML("mountain.xls",?) FROM ?',[mystyle,$scope.selectedEntity.mountainList]);
};
})
;