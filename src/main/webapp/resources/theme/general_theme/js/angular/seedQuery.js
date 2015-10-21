var seedQueryApp=angular.module("seedQueryApp",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])
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
var promise= $http.post("../seedQuery/search",this.searchBean)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
};
this.searchOne=function(entity) {
this.setSelectedEntity(null);
var promise= $http.post("../seedQuery/search",entity)
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
 this.initMountainList= function()
{
var promise= $http
.post("../mountain/search",
{})
.then(
function(response) {
return response.data;
}).catch(function() {
alert("error");
});
return promise;
};
 this.initPhotoList= function()
{
var promise= $http
.post("../photo/search",
{})
.then(
function(response) {
return response.data;
}).catch(function() {
alert("error");
});
return promise;
};
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
mountainService.selectedEntity.show=false;photoService.selectedEntity.show=false;}
$scope.addNew= function()
{
seedQueryService.setSelectedEntity(null);
seedQueryService.setEntityList(null);
seedQueryService.selectedEntity.show=true;
mountainService.selectedEntity.show=false;photoService.selectedEntity.show=false;};
		
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
seedQueryService.insert().then(function(data) { 
$scope.search();
});
};
$scope.update=function()
{
if (!$scope.seedQueryDetailForm.$valid) return; 
mountainService.selectedEntity.show=false;photoService.selectedEntity.show=false;seedQueryService.update().then(function(data) { 
$scope.search();
});
};
$scope.del=function()
{
nullService.selectedEntity.seedQuery=null;
seedQueryService.del().then(function(data) { 
$scope.search();
});
};$scope.trueFalseValues=[true,false];$scope.showMountainDetail= function(index)
{
if (index!=null)
{
mountainService.searchOne(seedQueryService.selectedEntity.mountainList[index]).then(function(data) { 
console.log(data[0]);
mountainService.setSelectedEntity(data[0]);
mountainService.selectedEntity.show=true;
});
}
else 
{
if (seedQueryService.selectedEntity.mountain==null || seedQueryService.selectedEntity.mountain==undefined)
mountainService.setSelectedEntity(null); 
else
mountainService.searchOne(seedQueryService.selectedEntity.mountain).then(function(data) { 
console.log(data[0]);
mountainService.setSelectedEntity(data[0]);
});
mountainService.selectedEntity.show=true;
}
};
$scope.showPhotoDetail= function(index)
{
if (index!=null)
{
photoService.searchOne(seedQueryService.selectedEntity.photoList[index]).then(function(data) { 
console.log(data[0]);
photoService.setSelectedEntity(data[0]);
photoService.selectedEntity.show=true;
});
}
else 
{
if (seedQueryService.selectedEntity.photo==null || seedQueryService.selectedEntity.photo==undefined)
photoService.setSelectedEntity(null); 
else
photoService.searchOne(seedQueryService.selectedEntity.photo).then(function(data) { 
console.log(data[0]);
photoService.setSelectedEntity(data[0]);
});
photoService.selectedEntity.show=true;
}
};
$scope.init=function()
{
seedQueryService.initMountainList().then(function(data) {
seedQueryService.childrenList.mountainList=data;
});
seedQueryService.initPhotoList().then(function(data) {
seedQueryService.childrenList.photoList=data;
});
}; 
$scope.init();
$scope.seedQueryGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'seedQueryId'},
{ name: 'seedKeyword'},
{ name: 'status'},
{ name: 'mountain.mountainId', displayName: 'mountain'} 
]
,data: seedQueryService.entityList
 };
$scope.seedQueryGridOptions.onRegisterApi = function(gridApi){
gridApi.selection.on.rowSelectionChanged($scope,function(row){
mountainService.selectedEntity.show=false;photoService.selectedEntity.show=false;if (row.isSelected)
{
seedQueryService.setSelectedEntity(row.entity);
}
else 
seedQueryService.setSelectedEntity(null);
seedQueryService.selectedEntity.show = row.isSelected;
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
{
photoService.searchOne(row.entity).then(function(data) { 
console.log(data);
photoService.setSelectedEntity(data[0]);
});
}
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
$scope.downloadMountainList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("mountain.xls",?) FROM ?',[mystyle,$scope.selectedEntity.mountainList]);
};
$scope.downloadPhotoList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("photo.xls",?) FROM ?',[mystyle,$scope.selectedEntity.photoList]);
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
this.searchOne=function(entity) {
this.setSelectedEntity(null);
var promise= $http.post("../mountain/search",entity)
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
 this.initSeedQueryList= function()
{
var promise= $http
.post("../seedQuery/search",
{})
.then(
function(response) {
return response.data;
}).catch(function() {
alert("error");
});
return promise;
};
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
mountainService.setSelectedEntity(null);
mountainService.setEntityList(null);
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

mountainService.selectedEntity.show=false;
mountainService.selectedEntity.seedQuery={};
mountainService.selectedEntity.seedQuery.seedQueryId=seedQueryService.selectedEntity.seedQueryId;
mountainService.insert().then(function(data) { 
seedQueryService.selectedEntity.mountain=data;
seedQueryService.initMountainList().then(function(data) {
seedQueryService.childrenList.mountainList=data;
});
});
};
$scope.update=function()
{
if (!$scope.mountainDetailForm.$valid) return; 
mountainService.selectedEntity.show=false;

seedQueryService.selectedEntity.mountain=mountainService.selectedEntity;

mountainService.update().then(function(data){
mountainService.setSelectedEntity(data);
});
};
$scope.remove= function()
{
mountainService.selectedEntity.show=false;
seedQueryService.selectedEntity.mountain=null;
mountainService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
seedQueryService.selectedEntity.mountain=null;
$scope.updateParent();
mountainService.del().then(function(data) { 
mountainService.setSelectedEntity(null);
seedQueryService.initMountainList().then(function(data) {
seedQueryService.childrenList.mountainList=data;
});
});
};$scope.trueFalseValues=[true,false];$scope.showSeedQueryDetail= function(index)
{
if (index!=null)
{
seedQueryService.searchOne(mountainService.selectedEntity.seedQueryList[index]).then(function(data) { 
console.log(data[0]);
seedQueryService.setSelectedEntity(data[0]);
seedQueryService.selectedEntity.show=true;
});
}
else 
{
if (mountainService.selectedEntity.seedQuery==null || mountainService.selectedEntity.seedQuery==undefined)
seedQueryService.setSelectedEntity(null); 
else
seedQueryService.searchOne(mountainService.selectedEntity.seedQuery).then(function(data) { 
console.log(data[0]);
seedQueryService.setSelectedEntity(data[0]);
});
seedQueryService.selectedEntity.show=true;
}
};
$scope.init=function()
{
mountainService.initSeedQueryList().then(function(data) {
mountainService.childrenList.seedQueryList=data;
});
}; 
$scope.init();
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
{
seedQueryService.searchOne(row.entity).then(function(data) { 
console.log(data);
seedQueryService.setSelectedEntity(data[0]);
});
}
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
$scope.downloadSeedQueryList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("seedQuery.xls",?) FROM ?',[mystyle,$scope.selectedEntity.seedQueryList]);
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
this.searchOne=function(entity) {
this.setSelectedEntity(null);
var promise= $http.post("../photo/search",entity)
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
 this.initSeedQueryList= function()
{
var promise= $http
.post("../seedQuery/search",
{})
.then(
function(response) {
return response.data;
}).catch(function() {
alert("error");
});
return promise;
};
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

photoService.selectedEntity.show=false;
photoService.selectedEntity.seedQuery={};
photoService.selectedEntity.seedQuery.seedQueryId=seedQueryService.selectedEntity.seedQueryId;
photoService.insert().then(function(data) { 
seedQueryService.selectedEntity.photoList.push(data);
});
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

photoService.update().then(function(data){
photoService.setSelectedEntity(data);
});
};
$scope.remove= function()
{
photoService.selectedEntity.show=false;
for (i=0; i<seedQueryService.selectedEntity.photoList.length; i++)
{
if (seedQueryService.selectedEntity.photoList[i].photoId==photoService.selectedEntity.photoId)
seedQueryService.selectedEntity.photoList.splice(i,1);
}
photoService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<seedQueryService.selectedEntity.photoList.length; i++)
{
if (seedQueryService.selectedEntity.photoList[i].photoId==photoService.selectedEntity.photoId)
seedQueryService.selectedEntity.photoList.splice(i,1);
}
$scope.updateParent();
photoService.del().then(function(data) { 
photoService.setSelectedEntity(null);
seedQueryService.initPhotoList().then(function(data) {
seedQueryService.childrenList.photoList=data;
});
});
};$scope.trueFalseValues=[true,false];$scope.showSeedQueryDetail= function(index)
{
if (index!=null)
{
seedQueryService.searchOne(photoService.selectedEntity.seedQueryList[index]).then(function(data) { 
console.log(data[0]);
seedQueryService.setSelectedEntity(data[0]);
seedQueryService.selectedEntity.show=true;
});
}
else 
{
if (photoService.selectedEntity.seedQuery==null || photoService.selectedEntity.seedQuery==undefined)
seedQueryService.setSelectedEntity(null); 
else
seedQueryService.searchOne(photoService.selectedEntity.seedQuery).then(function(data) { 
console.log(data[0]);
seedQueryService.setSelectedEntity(data[0]);
});
seedQueryService.selectedEntity.show=true;
}
};
$scope.init=function()
{
photoService.initSeedQueryList().then(function(data) {
photoService.childrenList.seedQueryList=data;
});
}; 
$scope.init();
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("photo.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadSeedQueryList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("seedQuery.xls",?) FROM ?',[mystyle,$scope.selectedEntity.seedQueryList]);
};
})
;