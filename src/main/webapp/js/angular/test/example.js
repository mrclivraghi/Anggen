var exampleApp=angular.module("exampleApp",['ngFileUpload','ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])
.service("securityService",function($http)
{
this.restrictionList;
this.init= function() {
var promise= $http.get("../authentication/");
return promise; 
};
})
.run(function($rootScope,securityService,exampleService, securityService ,placeService){
securityService.init().then(function successCallback(response) {
securityService.restrictionList=response.data;
$rootScope.restrictionList=response.data;
console.log($rootScope.restrictionList);
if (securityService.restrictionList.place==undefined || securityService.restrictionList.place.canSearch)
exampleService.initPlaceList().then(function successCallback(response) {
exampleService.childrenList.placeList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
exampleService.childrenList.exampleTypeList=["TYPE1",];
});
})
.service("exampleService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,placeList: []};
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
this.loadFile= function(file,field){
var formData = new FormData();
if (file!=null)
formData.append('file',file);
var promise= $http.post("../example/"+this.selectedEntity.exampleId+"/load"+field+"/",formData,{
 headers: {'Content-Type': undefined}
});
return promise; 
}
 this.initPlaceList= function()
{
var promise= $http
.post("../place/search",
{});
return promise;
};
})
.controller("exampleController",function($scope,$http,exampleService, securityService ,placeService)
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
placeService.selectedEntity.show=false;}
$scope.addNew= function()
{
exampleService.setSelectedEntity(null);
exampleService.setEntityList(null);
exampleService.selectedEntity.show=true;
placeService.selectedEntity.show=false;$('#exampleTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
exampleService.selectedEntity.show=false;
exampleService.searchBean.placeList=[];
exampleService.searchBean.placeList.push(exampleService.searchBean.place);
delete exampleService.searchBean.place; 
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
placeService.selectedEntity.show=false;exampleService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.del=function()
{
exampleService.del().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.placeGridApi!=undefined && $scope.placeGridApi!=null)
 $scope.placeGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
exampleService.loadFile(file,field).then(function successCallback(response) {
exampleService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
return; 
});
}
$scope.trueFalseValues=[true,false];
$scope.showPlaceDetail= function(index)
{
if (index!=null)
{
placeService.searchOne(exampleService.selectedEntity.placeList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.example==undefined || securityService.restrictionList.example.canSearch)
placeService.initExampleList().then(function successCallback(response) {
placeService.childrenList.exampleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
placeService.setSelectedEntity(response.data[0]);
placeService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (exampleService.selectedEntity.place==null || exampleService.selectedEntity.place==undefined)
{
if (securityService.restrictionList.example==undefined || securityService.restrictionList.example.canSearch)
placeService.initExampleList().then(function successCallback(response) {
placeService.childrenList.exampleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
placeService.setSelectedEntity(null); 
placeService.selectedEntity.show=true; 
}
else
placeService.searchOne(exampleService.selectedEntity.place).then(
function successCallback(response) {
if (securityService.restrictionList.example==undefined || securityService.restrictionList.example.canSearch)
placeService.initExampleList().then(function successCallback(response) {
placeService.childrenList.exampleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
placeService.setSelectedEntity(response.data[0]);
placeService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#placeTabs li:eq(0) a').tab('show');
};
$scope.exampleGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'entityId'},
{ name: 'male'},
{ name: 'age'},
{ name: 'exampleDate', cellFilter: "date:'dd-MM-yyyy'"},
{ name: 'exampleId'},
{ name: 'exampleFile'},
{ name: 'file2'} 
]
,data: exampleService.entityList
 };
$scope.exampleGridOptions.onRegisterApi = function(gridApi){
$scope.exampleGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
placeService.selectedEntity.show=false;if (row.isSelected)
{
exampleService.setSelectedEntity(row.entity);
$('#exampleTabs li:eq(0) a').tab('show');
}
else 
exampleService.setSelectedEntity(null);
exampleService.selectedEntity.show = row.isSelected;
});
  };
$scope.placeListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'entityId'},
{ name: 'placeId'},
{ name: 'placeName'} 
]
,data: $scope.selectedEntity.placeList
 };
$scope.placeListGridOptions.onRegisterApi = function(gridApi){
$scope.placeGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
placeService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
placeService.setSelectedEntity(response.data[0]);
});
$('#placeTabs li:eq(0) a').tab('show');
}
else 
placeService.setSelectedEntity(null);
placeService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("example.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedPlace= function() {
exampleService.selectedEntity.placeList.push(exampleService.selectedEntity.place);
}
$scope.downloadPlaceList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("place.xls",?) FROM ?',[mystyle,$scope.selectedEntity.placeList]);
};
})
.service("placeService", function($http)
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
var promise= $http.post("../place/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../place/"+entity.placeId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../place/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../place/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../place/"+this.selectedEntity.placeId;
var promise= $http["delete"](url);
return promise; 
}
this.loadFile= function(file,field){
var formData = new FormData();
if (file!=null)
formData.append('file',file);
var promise= $http.post("../place/"+this.selectedEntity.placeId+"/load"+field+"/",formData,{
 headers: {'Content-Type': undefined}
});
return promise; 
}
 this.initExampleList= function()
{
var promise= $http
.post("../example/search",
{});
return promise;
};
})
.controller("placeController",function($scope,$http,placeService, securityService ,exampleService)
{
//example
$scope.searchBean=placeService.searchBean;
$scope.entityList=placeService.entityList;
$scope.selectedEntity=placeService.selectedEntity;
$scope.childrenList=placeService.childrenList; 
$scope.reset = function()
{
placeService.resetSearchBean();
$scope.searchBean=placeService.searchBean;placeService.setSelectedEntity(null);
placeService.selectedEntity.show=false;
placeService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
exampleService.update().then(function successCallback(response) {
exampleService.setSelectedEntity(response);
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
placeService.setSelectedEntity(null);
placeService.setEntityList(null);
placeService.selectedEntity.show=true;
$('#placeTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
placeService.selectedEntity.show=false;
placeService.search().then(function successCallback(response) {
placeService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.placeDetailForm.$valid) return; 
placeService.selectedEntity.show=false;
placeService.selectedEntity.exampleList.push(exampleService.selectedEntity);
placeService.insert().then(function successCallBack(response) { 
exampleService.selectedEntity.placeList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.placeDetailForm.$valid) return; 
placeService.selectedEntity.show=false;

for (i=0; i<exampleService.selectedEntity.placeList.length; i++)

{

if (exampleService.selectedEntity.placeList[i].placeId==placeService.selectedEntity.placeId)

exampleService.selectedEntity.placeList.splice(i,1);

}

exampleService.selectedEntity.placeList.push(placeService.selectedEntity);

placeService.update().then(function successCallback(response){
placeService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
placeService.selectedEntity.show=false;
for (i=0; i<exampleService.selectedEntity.placeList.length; i++)
{
if (exampleService.selectedEntity.placeList[i].placeId==placeService.selectedEntity.placeId)
exampleService.selectedEntity.placeList.splice(i,1);
}
placeService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<exampleService.selectedEntity.placeList.length; i++)
{
if (exampleService.selectedEntity.placeList[i].placeId==placeService.selectedEntity.placeId)
exampleService.selectedEntity.placeList.splice(i,1);
}
$scope.updateParent();
placeService.del().then(function successCallback(response) { 
placeService.setSelectedEntity(null);
exampleService.initPlaceList().then(function(response) {
exampleService.childrenList.placeList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.exampleGridApi!=undefined && $scope.exampleGridApi!=null)
 $scope.exampleGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
placeService.loadFile(file,field).then(function successCallback(response) {
placeService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
return; 
});
}
$scope.trueFalseValues=[true,false];
$scope.showExampleDetail= function(index)
{
if (index!=null)
{
exampleService.searchOne(placeService.selectedEntity.exampleList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.place==undefined || securityService.restrictionList.place.canSearch)
exampleService.initPlaceList().then(function successCallback(response) {
exampleService.childrenList.placeList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
exampleService.childrenList.exampleTypeList=["TYPE1",];
exampleService.setSelectedEntity(response.data[0]);
exampleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (placeService.selectedEntity.example==null || placeService.selectedEntity.example==undefined)
{
if (securityService.restrictionList.place==undefined || securityService.restrictionList.place.canSearch)
exampleService.initPlaceList().then(function successCallback(response) {
exampleService.childrenList.placeList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
exampleService.childrenList.exampleTypeList=["TYPE1",];
exampleService.setSelectedEntity(null); 
exampleService.selectedEntity.show=true; 
}
else
exampleService.searchOne(placeService.selectedEntity.example).then(
function successCallback(response) {
if (securityService.restrictionList.place==undefined || securityService.restrictionList.place.canSearch)
exampleService.initPlaceList().then(function successCallback(response) {
exampleService.childrenList.placeList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
exampleService.childrenList.exampleTypeList=["TYPE1",];
exampleService.setSelectedEntity(response.data[0]);
exampleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#exampleTabs li:eq(0) a').tab('show');
};
$scope.exampleListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'entityId'},
{ name: 'male'},
{ name: 'age'},
{ name: 'exampleDate', cellFilter: "date:'dd-MM-yyyy'"},
{ name: 'exampleId'},
{ name: 'exampleFile'},
{ name: 'file2'} 
]
,data: $scope.selectedEntity.exampleList
 };
$scope.exampleListGridOptions.onRegisterApi = function(gridApi){
$scope.exampleGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
exampleService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
exampleService.setSelectedEntity(response.data[0]);
});
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
alasql('SELECT * INTO XLSXML("place.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadExampleList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("example.xls",?) FROM ?',[mystyle,$scope.selectedEntity.exampleList]);
};
})
;