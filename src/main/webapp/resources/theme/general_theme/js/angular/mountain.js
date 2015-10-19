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
var url="../mountain/"+this.selectedEntity.mountainId;
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
$scope.searchBean=mountainService.searchBean;mountainService.setSelectedEntity(null);
mountainService.selectedEntity.show=false;
mountainService.setEntityList(null); 
seedQueryService.selectedEntity.show=false;photoService.selectedEntity.show=false;}
$scope.addNew= function()
{
mountainService.setSelectedEntity(null);
mountainService.setEntityList(null);
mountainService.selectedEntity.show=true;
seedQueryService.selectedEntity.show=false;photoService.selectedEntity.show=false;};
		
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
mountainService.insert().then(function(data) { 
$scope.search();
});
};
$scope.update=function()
{
if (!$scope.mountainDetailForm.$valid) return; 
seedQueryService.selectedEntity.show=false;photoService.selectedEntity.show=false;mountainService.update().then(function(data) { 
$scope.search();
});
};
$scope.del=function()
{
mountainService.del().then(function(data) { 
$scope.search();
});
};$scope.trueFalseValues=[true,false];$scope.showSeedQueryDetail= function(index)
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
{ name: 'name'},
{ name: 'height'} 
]
,data: mountainService.entityList
 };
$scope.mountainGridOptions.onRegisterApi = function(gridApi){
gridApi.selection.on.rowSelectionChanged($scope,function(row){
seedQueryService.selectedEntity.show=false;photoService.selectedEntity.show=false;if (row.isSelected)
mountainService.setSelectedEntity(row.entity);
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
{ name: 'seedKeyword'},
{ name: 'status'} 
]
,data: $scope.selectedEntity.seedQueryList
 };
$scope.seedQueryListGridOptions.onRegisterApi = function(gridApi){
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
seedQueryService.setSelectedEntity(row.entity);
else 
seedQueryService.setSelectedEntity(null);
seedQueryService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("mountain.xls",?) FROM ?',[mystyle,$scope.entityList]);
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
var url="../seedQuery/"+this.selectedEntity.seedQueryId;
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
$scope.searchBean=seedQueryService.searchBean;seedQueryService.setSelectedEntity(null);
seedQueryService.selectedEntity.show=false;
seedQueryService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
mountainService.update().then(function(data) {
mountainService.setSelectedEntity(data);
if (toDo != null)
toDo();
});
};
$scope.addNew= function()
{
seedQueryService.setSelectedEntity(null);
seedQueryService.setEntityList(null);
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

mountainService.selectedEntity.seedQueryList.push(seedQueryService.selectedEntity);

$scope.updateParent();

};
$scope.update=function()
{
if (!$scope.seedQueryDetailForm.$valid) return; 
seedQueryService.selectedEntity.show=false;

for (i=0; i<mountainService.selectedEntity.seedQueryList.length; i++)

{

if (mountainService.selectedEntity.seedQueryList[i].seedQueryId==seedQueryService.selectedEntity.seedQueryId)

mountainService.selectedEntity.seedQueryList.splice(i,1);

}

mountainService.selectedEntity.seedQueryList.push(seedQueryService.selectedEntity);

$scope.updateParent();
};
$scope.del=function()
{
seedQueryService.selectedEntity.show=false;
for (i=0; i<mountainService.selectedEntity.seedQueryList.length; i++)
{
if (mountainService.selectedEntity.seedQueryList[i].seedQueryId==seedQueryService.selectedEntity.seedQueryId)
mountainService.selectedEntity.seedQueryList.splice(i,1);
}
seedQueryService.setSelectedEntity(null);
$scope.updateParent();
};$scope.trueFalseValues=[true,false];$scope.showMountainDetail= function(index)
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
enableGridMenu: true,
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
if (row.isSelected)
photoService.setSelectedEntity(row.entity);
else 
photoService.setSelectedEntity(null);
photoService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("seedQuery.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
})
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
var url="../photo/"+this.selectedEntity.photoId;
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
$scope.searchBean=photoService.searchBean;photoService.setSelectedEntity(null);
photoService.selectedEntity.show=false;
photoService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
seedQueryService.update().then(function(data) {
seedQueryService.setSelectedEntity(data);
if (toDo != null)
toDo();
});
};
$scope.addNew= function()
{
photoService.setSelectedEntity(null);
photoService.setEntityList(null);
photoService.selectedEntity.show=true;
};
		
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
photoService.selectedEntity.show=false;

seedQueryService.selectedEntity.photoList.push(photoService.selectedEntity);

$scope.updateParent();

};
$scope.update=function()
{
if (!$scope.photoDetailForm.$valid) return; 
photoService.selectedEntity.show=false;

for (i=0; i<seedQueryService.selectedEntity.photoList.length; i++)

{

if (seedQueryService.selectedEntity.photoList[i].photoId==photoService.selectedEntity.photoId)

seedQueryService.selectedEntity.photoList.splice(i,1);

}

seedQueryService.selectedEntity.photoList.push(photoService.selectedEntity);

$scope.updateParent();
};
$scope.del=function()
{
photoService.selectedEntity.show=false;
for (i=0; i<seedQueryService.selectedEntity.photoList.length; i++)
{
if (seedQueryService.selectedEntity.photoList[i].photoId==photoService.selectedEntity.photoId)
seedQueryService.selectedEntity.photoList.splice(i,1);
}
photoService.setSelectedEntity(null);
$scope.updateParent();
};$scope.trueFalseValues=[true,false];$scope.showSeedQueryDetail= function(index)
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
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("photo.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
})
;