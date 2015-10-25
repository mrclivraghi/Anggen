var photoApp=angular.module("photoApp",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])
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
var promise= $http.post("../photo/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
this.setSelectedEntity(null);
var promise= $http.get("../photo/"+entity.photoId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../photo/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../photo/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../photo/"+this.selectedEntity.photoId;
var promise= $http["delete"](url);
return promise; 
}
 this.initSeedQueryList= function()
{
var promise= $http
.post("../seedQuery/search",
{});
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
seedQueryService.selectedEntity.show=false;mountainService.selectedEntity.show=false;}
$scope.addNew= function()
{
photoService.setSelectedEntity(null);
photoService.setEntityList(null);
photoService.selectedEntity.show=true;
seedQueryService.selectedEntity.show=false;mountainService.selectedEntity.show=false;$('#photoTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
photoService.selectedEntity.show=false;
photoService.search().then(function successCallback(response) {
photoService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.photoDetailForm.$valid) return; 
photoService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.photoDetailForm.$valid) return; 
seedQueryService.selectedEntity.show=false;mountainService.selectedEntity.show=false;photoService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.del=function()
{
nullService.selectedEntity.photo=null;
photoService.del().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.trueFalseValues=[true,false];
$scope.showSeedQueryDetail= function(index)
{
if (index!=null)
{
seedQueryService.searchOne(photoService.selectedEntity.seedQueryList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
seedQueryService.setSelectedEntity(response.data[0]);
seedQueryService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (photoService.selectedEntity.seedQuery==null || photoService.selectedEntity.seedQuery==undefined)
{
seedQueryService.setSelectedEntity(null); 
seedQueryService.selectedEntity.show=true; 
}
else
seedQueryService.searchOne(photoService.selectedEntity.seedQuery).then(
function successCallback(response) {
seedQueryService.setSelectedEntity(response.data[0]);
seedQueryService.selectedEntity.show=true;
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
photoService.initSeedQueryList().then(function successCallback(response) {
photoService.childrenList.seedQueryList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
$scope.photoGridOptions = {
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
{ name: 'relatedPost'},
{ name: 'seedQuery.seedQueryId', displayName: 'seedQuery'} 
]
,data: photoService.entityList
 };
$scope.photoGridOptions.onRegisterApi = function(gridApi){
gridApi.selection.on.rowSelectionChanged($scope,function(row){
seedQueryService.selectedEntity.show=false;mountainService.selectedEntity.show=false;if (row.isSelected)
{
photoService.setSelectedEntity(row.entity);
$('#photoTabs li:eq(0) a').tab('show');
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
this.setSelectedEntity(null);
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
 this.initPhotoList= function()
{
var promise= $http
.post("../photo/search",
{});
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
}
$scope.updateParent = function(toDo)
{
photoService.update().then(function successCallback(response) {
photoService.setSelectedEntity(response);
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
seedQueryService.setSelectedEntity(null);
seedQueryService.setEntityList(null);
seedQueryService.selectedEntity.show=true;
$('#seedQueryTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
seedQueryService.selectedEntity.show=false;
seedQueryService.searchBean.photoList=[];
seedQueryService.searchBean.photoList.push(seedQueryService.searchBean.photo);
delete seedQueryService.searchBean.photo; 
seedQueryService.search().then(function successCallback(response) {
seedQueryService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.seedQueryDetailForm.$valid) return; 
seedQueryService.selectedEntity.show=false;

seedQueryService.selectedEntity.show=false;
seedQueryService.selectedEntity.photo={};
seedQueryService.selectedEntity.photo.photoId=photoService.selectedEntity.photoId;
seedQueryService.insert().then(function successCallBack(response) { 
photoService.selectedEntity.seedQuery=response.data;
photoService.initSeedQueryList().then(function(response) {
photoService.childrenList.seedQueryList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.seedQueryDetailForm.$valid) return; 
seedQueryService.selectedEntity.show=false;

photoService.selectedEntity.seedQuery=seedQueryService.selectedEntity;

seedQueryService.update().then(function successCallback(response){
seedQueryService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
seedQueryService.selectedEntity.show=false;
photoService.selectedEntity.seedQuery=null;
seedQueryService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
photoService.selectedEntity.seedQuery=null;
$scope.updateParent();
seedQueryService.del().then(function successCallback(response) { 
seedQueryService.setSelectedEntity(null);
photoService.initSeedQueryList().then(function(response) {
photoService.childrenList.seedQueryList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
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
if (seedQueryService.selectedEntity.mountain==null || seedQueryService.selectedEntity.mountain==undefined)
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
$scope.showPhotoDetail= function(index)
{
if (index!=null)
{
photoService.searchOne(seedQueryService.selectedEntity.photoList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
photoService.setSelectedEntity(response.data[0]);
photoService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (seedQueryService.selectedEntity.photo==null || seedQueryService.selectedEntity.photo==undefined)
{
photoService.setSelectedEntity(null); 
photoService.selectedEntity.show=true; 
}
else
photoService.searchOne(seedQueryService.selectedEntity.photo).then(
function successCallback(response) {
photoService.setSelectedEntity(response.data[0]);
photoService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#photoTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
seedQueryService.initMountainList().then(function successCallback(response) {
seedQueryService.childrenList.mountainList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
seedQueryService.initPhotoList().then(function successCallback(response) {
seedQueryService.childrenList.photoList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
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
photoService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
photoService.setSelectedEntity(response.data[0]);
});
$('#photoTabs li:eq(0) a').tab('show');
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
var promise= $http.post("../mountain/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
this.setSelectedEntity(null);
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
.post("../seedQuery/search",
{});
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
seedQueryService.update().then(function successCallback(response) {
seedQueryService.setSelectedEntity(response);
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
mountainService.setSelectedEntity(null);
mountainService.setEntityList(null);
mountainService.selectedEntity.show=true;
$('#mountainTabs li:eq(0) a').tab('show');
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
mountainService.selectedEntity.show=false;

mountainService.selectedEntity.show=false;
mountainService.selectedEntity.seedQuery={};
mountainService.selectedEntity.seedQuery.seedQueryId=seedQueryService.selectedEntity.seedQueryId;
mountainService.insert().then(function successCallBack(response) { 
seedQueryService.selectedEntity.mountain=response.data;
seedQueryService.initMountainList().then(function(response) {
seedQueryService.childrenList.mountainList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.mountainDetailForm.$valid) return; 
mountainService.selectedEntity.show=false;

seedQueryService.selectedEntity.mountain=mountainService.selectedEntity;

mountainService.update().then(function successCallback(response){
mountainService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
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
mountainService.del().then(function successCallback(response) { 
mountainService.setSelectedEntity(null);
seedQueryService.initMountainList().then(function(response) {
seedQueryService.childrenList.mountainList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
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
seedQueryService.setSelectedEntity(response.data[0]);
seedQueryService.selectedEntity.show=true;
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
seedQueryService.setSelectedEntity(null); 
seedQueryService.selectedEntity.show=true; 
}
else
seedQueryService.searchOne(mountainService.selectedEntity.seedQuery).then(
function successCallback(response) {
seedQueryService.setSelectedEntity(response.data[0]);
seedQueryService.selectedEntity.show=true;
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
seedQueryService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
seedQueryService.setSelectedEntity(response.data[0]);
});
$('#seedQueryTabs li:eq(0) a').tab('show');
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
;