var photoApp=angular.module("photoApp",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection'])
.service("photoService", function($http)
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
var promise= $http.post("../photo/search",this.searchBean)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
};
this.insert = function() {
var promise= $http.put("../photo/",this.selectedEntity)
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
var promise= $http.post("../photo/",this.selectedEntity)
.then( function(response) {
return response.data;
})
.catch(function() { 
alert("error");
});
return promise; 
}
this.del = function() {
var url="../photo/selectedEntity.photoId";
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
.controller("photoController",function($scope,$http,photoService,seedQueryService,mountainService)
{
$scope.searchBean=photoService.searchBean;
$scope.entityList=photoService.entityList;
$scope.selectedEntity=photoService.selectedEntity;
$scope.childrenList=photoService.childrenList; 
$scope.reset = function()
{
photoService.resetSearchBean();
photoService.setSelectedEntity(null);
photoService.selectedEntity.show=false;
photoService.setEntityList(null); 
seedQueryService.selectedEntity.show=false;mountainService.selectedEntity.show=false;}
$scope.showEntityDetail= function(index)
{
photoService.indexSelected=index;
photoService.setSelectedEntity($scope.entityList[index]);
photoService.selectedEntity.show=true;
};
$scope.addNew= function()
{
photoService.setSelectedEntity(null);
photoService.selectedEntity.show=true;
seedQueryService.selectedEntity.show=false;mountainService.selectedEntity.show=false;};
		
$scope.search=function()
{
photoService.selectedEntity.show=false;
photoService.search().then(function(data) { 
photoService.setEntityList(data);
});
};
$scope.insert=function()
{
if (!$scope.photoDetailForm.$valid) return; 
photoService.insert().then(function(data) { 
$scope.search();
});
};
$scope.update=function()
{
if (!$scope.photoDetailForm.$valid) return; 
seedQueryService.selectedEntity.show=false;mountainService.selectedEntity.show=false;photoService.update().then(function(data) { 
$scope.search();
});
};
$scope.del=function()
{
photoService.del().then(function(data) { 
$scope.search();
});
};$scope.showSeedQueryDetail= function(index)
{
if (index!=null)
seedQueryService.setSelectedEntity(photoService.selectedEntity.seedQueryList[index]);
else 
seedQueryService.setSelectedEntity(photoService.selectedEntity.seedQuery); 
seedQueryService.selectedEntity.show=true;
};
$scope.init=function()
{
$http
.post("../seedQuery/search",
{})
.success(
function(entityList) {
photoService.childrenList.seedQueryList=entityList;
}).error(function() {
alert("error");
});
}; 
$scope.init();
$scope.photoGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
columnDefs: [
{ name: 'photoId'},
{ name: 'url'},
{ name: 'social'},
{ name: 'date', cellFilter: "date:'dd-MM-yyyy'"},
{ name: 'status'},
{ name: 'socialId'},
{ name: 'relatedPost'},
{ name: 'seedQuery.seedQueryId', displayName: 'seedQuery'} 
]
,data: photoService.entityList
 };
$scope.photoGridOptions.onRegisterApi = function(gridApi){
gridApi.selection.on.rowSelectionChanged($scope,function(row){
seedQueryService.selectedEntity.show=false;mountainService.selectedEntity.show=false;photoService
.setSelectedEntity(row.entity);
photoService.selectedEntity.show = true;
});
  };
})
.service("seedQueryService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,photoList: []};
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
var promise= $http.post("../seedQuery/search",this.searchBean)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
};
this.insert = function() {
var promise= $http.put("../seedQuery/",this.selectedEntity)
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
var promise= $http.post("../seedQuery/",this.selectedEntity)
.then( function(response) {
return response.data;
})
.catch(function() { 
alert("error");
});
return promise; 
}
this.del = function() {
var url="../seedQuery/selectedEntity.seedQueryId";
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
.controller("seedQueryController",function($scope,$http,seedQueryService,mountainService,photoService)
{
$scope.searchBean=seedQueryService.searchBean;
$scope.entityList=seedQueryService.entityList;
$scope.selectedEntity=seedQueryService.selectedEntity;
$scope.childrenList=seedQueryService.childrenList; 
$scope.reset = function()
{
seedQueryService.resetSearchBean();
seedQueryService.setSelectedEntity(null);
seedQueryService.selectedEntity.show=false;
seedQueryService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
photoService.update().then(function(data) {
photoService.setSelectedEntity(data);
if (toDo != null)
toDo();
});
};
$scope.showEntityDetail= function(index)
{
seedQueryService.indexSelected=index;
seedQueryService.setSelectedEntity($scope.entityList[index]);
seedQueryService.selectedEntity.show=true;
};
$scope.addNew= function()
{
seedQueryService.setSelectedEntity(null);
seedQueryService.selectedEntity.show=true;
};
		
$scope.search=function()
{
seedQueryService.selectedEntity.show=false;
seedQueryService.searchBean.photoList=[];
seedQueryService.searchBean.photoList.push(seedQueryService.searchBean.photo);
delete seedQueryService.searchBean.photo; 
seedQueryService.search().then(function(data) { 
seedQueryService.setEntityList(data);
});
};
$scope.insert=function()
{
if (!$scope.seedQueryDetailForm.$valid) return; 
seedQueryService.selectedEntity.show=false;

photoService.selectedEntity.seedQuery=seedQueryService.selectedEntity;

$scope.updateParent();

};
$scope.update=function()
{
if (!$scope.seedQueryDetailForm.$valid) return; 
seedQueryService.selectedEntity.show=false;

photoService.selectedEntity.seedQuery=seedQueryService.selectedEntity;

$scope.updateParent();
};
$scope.del=function()
{
seedQueryService.selectedEntity.show=false;
photoService.selectedEntity.seedQuery=null;seedQueryService.setSelectedEntity(null);
$scope.updateParent();
};$scope.showMountainDetail= function(index)
{
if (index!=null)
mountainService.setSelectedEntity(seedQueryService.selectedEntity.mountainList[index]);
else 
mountainService.setSelectedEntity(seedQueryService.selectedEntity.mountain); 
mountainService.selectedEntity.show=true;
};
$scope.showPhotoDetail= function(index)
{
if (index!=null)
photoService.setSelectedEntity(seedQueryService.selectedEntity.photoList[index]);
else 
photoService.setSelectedEntity(seedQueryService.selectedEntity.photo); 
photoService.selectedEntity.show=true;
};
$scope.init=function()
{
$http
.post("../mountain/search",
{})
.success(
function(entityList) {
seedQueryService.childrenList.mountainList=entityList;
}).error(function() {
alert("error");
});
$http
.post("../photo/search",
{})
.success(
function(entityList) {
seedQueryService.childrenList.photoList=entityList;
}).error(function() {
alert("error");
});
}; 
$scope.photoListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
columnDefs: [
{ name: 'photoId'},
{ name: 'url'},
{ name: 'social'},
{ name: 'date', cellFilter: "date:'dd-MM-yyyy'"},
{ name: 'status'},
{ name: 'socialId'},
{ name: 'relatedPost'} 
]
,data: $scope.selectedEntity.photoList
 };
$scope.photoListGridOptions.onRegisterApi = function(gridApi){
gridApi.selection.on.rowSelectionChanged($scope,function(row){
photoService
.setSelectedEntity(row.entity);
photoService.selectedEntity.show = true;
});
  };
})
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
var promise= $http.post("../mountain/search",this.searchBean)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
};
this.insert = function() {
var promise= $http.put("../mountain/",this.selectedEntity)
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
var promise= $http.post("../mountain/",this.selectedEntity)
.then( function(response) {
return response.data;
})
.catch(function() { 
alert("error");
});
return promise; 
}
this.del = function() {
var url="../mountain/selectedEntity.mountainId";
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
.controller("mountainController",function($scope,$http,mountainService,seedQueryService,photoService)
{
$scope.searchBean=mountainService.searchBean;
$scope.entityList=mountainService.entityList;
$scope.selectedEntity=mountainService.selectedEntity;
$scope.childrenList=mountainService.childrenList; 
$scope.reset = function()
{
mountainService.resetSearchBean();
mountainService.setSelectedEntity(null);
mountainService.selectedEntity.show=false;
mountainService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
seedQueryService.update().then(function(data) {
seedQueryService.setSelectedEntity(data);
if (toDo != null)
toDo();
});
};
$scope.showEntityDetail= function(index)
{
mountainService.indexSelected=index;
mountainService.setSelectedEntity($scope.entityList[index]);
mountainService.selectedEntity.show=true;
};
$scope.addNew= function()
{
mountainService.setSelectedEntity(null);
mountainService.selectedEntity.show=true;
};
		
$scope.search=function()
{
mountainService.selectedEntity.show=false;
mountainService.searchBean.seedQueryList=[];
mountainService.searchBean.seedQueryList.push(mountainService.searchBean.seedQuery);
delete mountainService.searchBean.seedQuery; 
mountainService.search().then(function(data) { 
mountainService.setEntityList(data);
});
};
$scope.insert=function()
{
if (!$scope.mountainDetailForm.$valid) return; 
mountainService.selectedEntity.show=false;

seedQueryService.selectedEntity.mountain=mountainService.selectedEntity;

$scope.updateParent();

};
$scope.update=function()
{
if (!$scope.mountainDetailForm.$valid) return; 
mountainService.selectedEntity.show=false;

seedQueryService.selectedEntity.mountain=mountainService.selectedEntity;

$scope.updateParent();
};
$scope.del=function()
{
mountainService.selectedEntity.show=false;
seedQueryService.selectedEntity.mountain=null;mountainService.setSelectedEntity(null);
$scope.updateParent();
};$scope.showSeedQueryDetail= function(index)
{
if (index!=null)
seedQueryService.setSelectedEntity(mountainService.selectedEntity.seedQueryList[index]);
else 
seedQueryService.setSelectedEntity(mountainService.selectedEntity.seedQuery); 
seedQueryService.selectedEntity.show=true;
};
$scope.init=function()
{
$http
.post("../seedQuery/search",
{})
.success(
function(entityList) {
mountainService.childrenList.seedQueryList=entityList;
}).error(function() {
alert("error");
});
}; 
$scope.seedQueryListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
columnDefs: [
{ name: 'seedQueryId'},
{ name: 'seedKeyword'},
{ name: 'status'} 
]
,data: $scope.selectedEntity.seedQueryList
 };
$scope.seedQueryListGridOptions.onRegisterApi = function(gridApi){
gridApi.selection.on.rowSelectionChanged($scope,function(row){
seedQueryService
.setSelectedEntity(row.entity);
seedQueryService.selectedEntity.show = true;
});
  };
})
;