var projectApp=angular.module("projectApp",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])
.service("securityService",function($http)
{
this.restrictionList;
this.init= function() {
var promise= $http.get("../authentication/");
return promise; 
};
})
.run(function($rootScope,securityService,projectService, securityService ,EntityGroupTestService){
securityService.init().then(function successCallback(response) {
securityService.restrictionList=response.data;
$rootScope.restrictionList=response.data;
console.log($rootScope.restrictionList);
if (securityService.restrictionList.EntityGroupTest==undefined || securityService.restrictionList.EntityGroupTest.canSearch)
projectService.initEntityGroupTestList().then(function successCallback(response) {
projectService.childrenList.entityGroupTestList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
});
})
.service("projectService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,EntityGroupTestList: []};
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
var promise= $http.post("../project/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../project/"+entity.projectId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../project/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../project/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../project/"+this.selectedEntity.projectId;
var promise= $http["delete"](url);
return promise; 
}
 this.initEntityGroupTestList= function()
{
var promise= $http
.post("..//search",
{});
return promise;
};
})
.controller("projectController",function($scope,$http,projectService, securityService ,EntityGroupTestService)
{
//null
$scope.searchBean=projectService.searchBean;
$scope.entityList=projectService.entityList;
$scope.selectedEntity=projectService.selectedEntity;
$scope.childrenList=projectService.childrenList; 
$scope.reset = function()
{
projectService.resetSearchBean();
$scope.searchBean=projectService.searchBean;projectService.setSelectedEntity(null);
projectService.selectedEntity.show=false;
projectService.setEntityList(null); 
EntityGroupTestService.selectedEntity.show=false;}
$scope.addNew= function()
{
projectService.setSelectedEntity(null);
projectService.setEntityList(null);
projectService.selectedEntity.show=true;
EntityGroupTestService.selectedEntity.show=false;$('#projectTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
projectService.selectedEntity.show=false;
projectService.searchBean.EntityGroupTestList=[];
projectService.searchBean.EntityGroupTestList.push(projectService.searchBean.EntityGroupTest);
delete projectService.searchBean.EntityGroupTest; 
projectService.search().then(function successCallback(response) {
projectService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.projectDetailForm.$valid) return; 
projectService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.projectDetailForm.$valid) return; 
EntityGroupTestService.selectedEntity.show=false;projectService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.del=function()
{
projectService.del().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.trueFalseValues=[true,false];
$scope.showEntityGroupTestDetail= function(index)
{
if (index!=null)
{
EntityGroupTestService.searchOne(projectService.selectedEntity.EntityGroupTestList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
Service.setSelectedEntity(response.data[0]);
Service.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (projectService.selectedEntity.EntityGroupTest==null || projectService.selectedEntity.EntityGroupTest==undefined)
{
Service.setSelectedEntity(null); 
Service.selectedEntity.show=true; 
}
else
Service.searchOne(projectService.selectedEntity.EntityGroupTest).then(
function successCallback(response) {
Service.setSelectedEntity(response.data[0]);
Service.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#EntityGroupTestTabs li:eq(0) a').tab('show');
};
$scope.projectGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'name'},
{ name: 'projectId'} 
]
,data: projectService.entityList
 };
$scope.projectGridOptions.onRegisterApi = function(gridApi){
$scope.projectGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
EntityGroupTestService.selectedEntity.show=false;if (row.isSelected)
{
projectService.setSelectedEntity(row.entity);
$('#projectTabs li:eq(0) a').tab('show');
}
else 
projectService.setSelectedEntity(null);
projectService.selectedEntity.show = row.isSelected;
});
  };
$scope.entityGroupTestListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'entityGroupTestId'} 
]
,data: $scope.selectedEntity.entityGroupTestList
 };
$scope.entityGroupTestListGridOptions.onRegisterApi = function(gridApi){
$scope.entityGroupTestGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
entityService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
entityService.setSelectedEntity(response.data[0]);
});
$('#entityGroupTestTabs li:eq(0) a').tab('show');
}
else 
entityService.setSelectedEntity(null);
entityService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("project.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedEntityGroupTest= function() {
projectService.selectedEntity.EntityGroupTestList.push(projectService.selectedEntity.EntityGroupTest);
}
$scope.downloadEntityGroupTestList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("EntityGroupTest.xls",?) FROM ?',[mystyle,$scope.selectedEntity.EntityGroupTestList]);
};
})
.service("entityGroupTestService", function($http)
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
var promise= $http.post("../entityGroupTest/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../entityGroupTest/"+entity.entityGroupTestId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../entityGroupTest/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../entityGroupTest/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../entityGroupTest/"+this.selectedEntity.entityGroupTestId;
var promise= $http["delete"](url);
return promise; 
}
})
.controller("entityGroupTestController",function($scope,$http,entityGroupTestService, securityService )
{
//project
$scope.searchBean=entityService.searchBean;
$scope.entityList=entityService.entityList;
$scope.selectedEntity=entityService.selectedEntity;
$scope.childrenList=entityService.childrenList; 
$scope.reset = function()
{
entityService.resetSearchBean();
$scope.searchBean=entityGroupTestService.searchBean;entityService.setSelectedEntity(null);
entityService.selectedEntity.show=false;
entityService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
projectService.update().then(function successCallback(response) {
projectService.setSelectedEntity(response);
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
entityService.setSelectedEntity(null);
entityService.setEntityList(null);
entityService.selectedEntity.show=true;
$('#entityGroupTestTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
entityService.selectedEntity.show=false;
entityService.search().then(function successCallback(response) {
entityService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.entityGroupTestDetailForm.$valid) return; 
entityService.selectedEntity.show=false;
entityService.selectedEntity.projectList.push(projectService.selectedEntity);
entityService.insert().then(function successCallBack(response) { 
projectService.selectedEntity.entityGroupTestList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.entityGroupTestDetailForm.$valid) return; 
entityService.selectedEntity.show=false;

for (i=0; i<projectService.selectedEntity.entityGroupTestList.length; i++)

{

if (projectService.selectedEntity.entityGroupTestList[i].entityGroupTestId==entityGroupTestService.selectedEntity.entityGroupTestId)

projectService.selectedEntity.entityGroupTestList.splice(i,1);

}

projectService.selectedEntity.entityGroupTestList.push(entityGroupTestService.selectedEntity);

entityService.update().then(function successCallback(response){
entityService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
entityService.selectedEntity.show=false;
for (i=0; i<projectService.selectedEntity.entityGroupTestList.length; i++)
{
if (projectService.selectedEntity.entityGroupTestList[i].entityGroupTestId==entityGroupTestService.selectedEntity.entityGroupTestId)
projectService.selectedEntity.entityGroupTestList.splice(i,1);
}
entityService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<projectService.selectedEntity.entityGroupTestList.length; i++)
{
if (projectService.selectedEntity.entityGroupTestList[i].entityGroupTestId==entityGroupTestService.selectedEntity.entityGroupTestId)
projectService.selectedEntity.entityGroupTestList.splice(i,1);
}
$scope.updateParent();
entityService.del().then(function successCallback(response) { 
entityService.setSelectedEntity(null);
projectService.initEntityGroupTestList().then(function(response) {
projectService.childrenList.entityGroupTestList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.trueFalseValues=[true,false];
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entityGroupTest.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
})
;