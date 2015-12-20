var projectApp=angular.module("projectApp",['ngFileUpload','ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])
.service("securityService",function($http)
{
this.restrictionList;
this.init= function() {
var promise= $http.get("../authentication/");
return promise; 
};
})
.run(function($rootScope,securityService,projectService, securityService ,enumEntityService,enumValueService,entityGroupService,restrictionEntityGroupService,entityService){
securityService.init().then(function successCallback(response) {
securityService.restrictionList=response.data;
$rootScope.restrictionList=response.data;
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
projectService.initEnumEntityList().then(function successCallback(response) {
projectService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
projectService.initEntityGroupList().then(function successCallback(response) {
projectService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
});
})
.service("projectService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,enumEntityList: [],entityGroupList: []};
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
this.loadFile= function(file,field){
var formData = new FormData();
if (file!=null)
formData.append('file',file);
var promise= $http.post("../project/"+this.selectedEntity.projectId+"/load"+field+"/",formData,{
 headers: {'Content-Type': undefined}
});
return promise; 
}
 this.initEnumEntityList= function()
{
var promise= $http
.post("../enumEntity/search",
{});
return promise;
};
 this.initEntityGroupList= function()
{
var promise= $http
.post("../entityGroup/search",
{});
return promise;
};
})
.controller("projectController",function($scope,$http,projectService, securityService ,enumEntityService,enumValueService,entityGroupService,restrictionEntityGroupService,entityService)
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
enumEntityService.selectedEntity.show=false;enumValueService.selectedEntity.show=false;entityGroupService.selectedEntity.show=false;restrictionEntityGroupService.selectedEntity.show=false;entityService.selectedEntity.show=false;}
$scope.addNew= function()
{
projectService.setSelectedEntity(null);
projectService.setEntityList(null);
projectService.selectedEntity.show=true;
enumEntityService.selectedEntity.show=false;enumValueService.selectedEntity.show=false;entityGroupService.selectedEntity.show=false;restrictionEntityGroupService.selectedEntity.show=false;entityService.selectedEntity.show=false;$('#projectTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
projectService.selectedEntity.show=false;
projectService.searchBean.enumEntityList=[];
projectService.searchBean.enumEntityList.push(projectService.searchBean.enumEntity);
delete projectService.searchBean.enumEntity; 
projectService.searchBean.entityGroupList=[];
projectService.searchBean.entityGroupList.push(projectService.searchBean.entityGroup);
delete projectService.searchBean.entityGroup; 
projectService.search().then(function successCallback(response) {
projectService.setEntityList(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.insert=function()
{
if (!$scope.projectDetailForm.$valid) return; 
projectService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.update=function()
{
if (!$scope.projectDetailForm.$valid) return; 
enumEntityService.selectedEntity.show=false;enumValueService.selectedEntity.show=false;entityGroupService.selectedEntity.show=false;restrictionEntityGroupService.selectedEntity.show=false;entityService.selectedEntity.show=false;projectService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.del=function()
{
projectService.del().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.enumEntityGridApi!=undefined && $scope.enumEntityGridApi!=null)
 $scope.enumEntityGridApi.core.handleWindowResize(); 
if ($scope.entityGroupGridApi!=undefined && $scope.entityGroupGridApi!=null)
 $scope.entityGroupGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
projectService.loadFile(file,field).then(function successCallback(response) {
projectService.setSelectedEntity(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
return; 
});
}
$scope.trueFalseValues=[true,false];
$scope.showEnumEntityDetail= function(index)
{
if (index!=null)
{
enumEntityService.searchOne(projectService.selectedEntity.enumEntityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
enumEntityService.initProjectList().then(function successCallback(response) {
enumEntityService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.enumValue==undefined || securityService.restrictionList.enumValue.canSearch)
enumEntityService.initEnumValueList().then(function successCallback(response) {
enumEntityService.childrenList.enumValueList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
enumEntityService.setSelectedEntity(response.data[0]);
enumEntityService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (projectService.selectedEntity.enumEntity==null || projectService.selectedEntity.enumEntity==undefined)
{
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
enumEntityService.initProjectList().then(function successCallback(response) {
enumEntityService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.enumValue==undefined || securityService.restrictionList.enumValue.canSearch)
enumEntityService.initEnumValueList().then(function successCallback(response) {
enumEntityService.childrenList.enumValueList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
enumEntityService.setSelectedEntity(null); 
enumEntityService.selectedEntity.show=true; 
}
else
enumEntityService.searchOne(projectService.selectedEntity.enumEntity).then(
function successCallback(response) {
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
enumEntityService.initProjectList().then(function successCallback(response) {
enumEntityService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.enumValue==undefined || securityService.restrictionList.enumValue.canSearch)
enumEntityService.initEnumValueList().then(function successCallback(response) {
enumEntityService.childrenList.enumValueList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
enumEntityService.setSelectedEntity(response.data[0]);
enumEntityService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#enumEntityTabs li:eq(0) a').tab('show');
};
$scope.showEntityGroupDetail= function(index)
{
if (index!=null)
{
entityGroupService.searchOne(projectService.selectedEntity.entityGroupList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
entityGroupService.setSelectedEntity(response.data[0]);
entityGroupService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (projectService.selectedEntity.entityGroup==null || projectService.selectedEntity.entityGroup==undefined)
{
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
entityGroupService.setSelectedEntity(null); 
entityGroupService.selectedEntity.show=true; 
}
else
entityGroupService.searchOne(projectService.selectedEntity.entityGroup).then(
function successCallback(response) {
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
entityGroupService.setSelectedEntity(response.data[0]);
entityGroupService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#entityGroupTabs li:eq(0) a').tab('show');
};
$scope.projectGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'projectId'},
{ name: 'name'} 
]
,data: projectService.entityList
 };
$scope.projectGridOptions.onRegisterApi = function(gridApi){
$scope.projectGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
enumEntityService.selectedEntity.show=false;enumValueService.selectedEntity.show=false;entityGroupService.selectedEntity.show=false;restrictionEntityGroupService.selectedEntity.show=false;entityService.selectedEntity.show=false;if (row.isSelected)
{
projectService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
projectService.setSelectedEntity(response.data[0]);
});
$('#projectTabs li:eq(0) a').tab('show');
}
else 
projectService.setSelectedEntity(null);
projectService.selectedEntity.show = row.isSelected;
});
  };
$scope.enumEntityListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'name'},
{ name: 'enumEntityId'} 
]
,data: $scope.selectedEntity.enumEntityList
 };
$scope.enumEntityListGridOptions.onRegisterApi = function(gridApi){
$scope.enumEntityGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
enumEntityService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
enumEntityService.setSelectedEntity(response.data[0]);
});
$('#enumEntityTabs li:eq(0) a').tab('show');
}
else 
enumEntityService.setSelectedEntity(null);
enumEntityService.selectedEntity.show = row.isSelected;
});
  };
$scope.entityGroupListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'name'},
{ name: 'entityGroupId'},
{ name: 'entityId'} 
]
,data: $scope.selectedEntity.entityGroupList
 };
$scope.entityGroupListGridOptions.onRegisterApi = function(gridApi){
$scope.entityGroupGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
entityGroupService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
entityGroupService.setSelectedEntity(response.data[0]);
});
$('#entityGroupTabs li:eq(0) a').tab('show');
}
else 
entityGroupService.setSelectedEntity(null);
entityGroupService.selectedEntity.show = row.isSelected;
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
$scope.saveLinkedEnumEntity= function() {
projectService.selectedEntity.enumEntityList.push(projectService.selectedEntity.enumEntity);
}
$scope.downloadEnumEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumEntity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.enumEntityList]);
};
$scope.saveLinkedEntityGroup= function() {
projectService.selectedEntity.entityGroupList.push(projectService.selectedEntity.entityGroup);
}
$scope.downloadEntityGroupList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entityGroup.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityGroupList]);
};
})
.service("enumValueService", function($http)
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
var promise= $http.post("../enumValue/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../enumValue/"+entity.enumValueId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../enumValue/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../enumValue/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../enumValue/"+this.selectedEntity.enumValueId;
var promise= $http["delete"](url);
return promise; 
}
this.loadFile= function(file,field){
var formData = new FormData();
if (file!=null)
formData.append('file',file);
var promise= $http.post("../enumValue/"+this.selectedEntity.enumValueId+"/load"+field+"/",formData,{
 headers: {'Content-Type': undefined}
});
return promise; 
}
 this.initEnumEntityList= function()
{
var promise= $http
.post("../enumEntity/search",
{});
return promise;
};
})
.controller("enumValueController",function($scope,$http,projectService, securityService ,enumEntityService,enumValueService,entityGroupService,restrictionEntityGroupService,entityService)
{
//project
$scope.searchBean=enumValueService.searchBean;
$scope.entityList=enumValueService.entityList;
$scope.selectedEntity=enumValueService.selectedEntity;
$scope.childrenList=enumValueService.childrenList; 
$scope.reset = function()
{
enumValueService.resetSearchBean();
$scope.searchBean=enumValueService.searchBean;enumValueService.setSelectedEntity(null);
enumValueService.selectedEntity.show=false;
enumValueService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
projectService.update().then(function successCallback(response) {
projectService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
}
);
};
$scope.addNew= function()
{
enumValueService.setSelectedEntity(null);
enumValueService.setEntityList(null);
enumValueService.selectedEntity.show=true;
$('#enumValueTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
enumValueService.selectedEntity.show=false;
enumValueService.search().then(function successCallback(response) {
enumValueService.setEntityList(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.insert=function()
{
if (!$scope.enumValueDetailForm.$valid) return; 
enumValueService.selectedEntity.show=false;
enumValueService.selectedEntity.projectList.push(projectService.selectedEntity);
enumValueService.insert().then(function successCallBack(response) { 
projectService.selectedEntity.enumValueList.push(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.update=function()
{
if (!$scope.enumValueDetailForm.$valid) return; 
enumValueService.selectedEntity.show=false;

for (i=0; i<projectService.selectedEntity.enumValueList.length; i++)

{

if (projectService.selectedEntity.enumValueList[i].enumValueId==enumValueService.selectedEntity.enumValueId)

projectService.selectedEntity.enumValueList.splice(i,1);

}

projectService.selectedEntity.enumValueList.push(enumValueService.selectedEntity);

enumValueService.update().then(function successCallback(response){
enumValueService.setSelectedEntity(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.remove= function()
{
enumValueService.selectedEntity.show=false;
for (i=0; i<projectService.selectedEntity.enumValueList.length; i++)
{
if (projectService.selectedEntity.enumValueList[i].enumValueId==enumValueService.selectedEntity.enumValueId)
projectService.selectedEntity.enumValueList.splice(i,1);
}
enumValueService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<projectService.selectedEntity.enumValueList.length; i++)
{
if (projectService.selectedEntity.enumValueList[i].enumValueId==enumValueService.selectedEntity.enumValueId)
projectService.selectedEntity.enumValueList.splice(i,1);
}
$scope.updateParent();
enumValueService.del().then(function successCallback(response) { 
enumValueService.setSelectedEntity(null);
projectService.initEnumValueList().then(function(response) {
projectService.childrenList.enumValueList=response.data;
});
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.enumEntityGridApi!=undefined && $scope.enumEntityGridApi!=null)
 $scope.enumEntityGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
enumValueService.loadFile(file,field).then(function successCallback(response) {
enumValueService.setSelectedEntity(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
return; 
});
}
$scope.trueFalseValues=[true,false];
$scope.showEnumEntityDetail= function(index)
{
if (index!=null)
{
enumEntityService.searchOne(enumValueService.selectedEntity.enumEntityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
enumEntityService.initProjectList().then(function successCallback(response) {
enumEntityService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.enumValue==undefined || securityService.restrictionList.enumValue.canSearch)
enumEntityService.initEnumValueList().then(function successCallback(response) {
enumEntityService.childrenList.enumValueList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
enumEntityService.setSelectedEntity(response.data[0]);
enumEntityService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (enumValueService.selectedEntity.enumEntity==null || enumValueService.selectedEntity.enumEntity==undefined)
{
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
enumEntityService.initProjectList().then(function successCallback(response) {
enumEntityService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.enumValue==undefined || securityService.restrictionList.enumValue.canSearch)
enumEntityService.initEnumValueList().then(function successCallback(response) {
enumEntityService.childrenList.enumValueList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
enumEntityService.setSelectedEntity(null); 
enumEntityService.selectedEntity.show=true; 
}
else
enumEntityService.searchOne(enumValueService.selectedEntity.enumEntity).then(
function successCallback(response) {
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
enumEntityService.initProjectList().then(function successCallback(response) {
enumEntityService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.enumValue==undefined || securityService.restrictionList.enumValue.canSearch)
enumEntityService.initEnumValueList().then(function successCallback(response) {
enumEntityService.childrenList.enumValueList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
enumEntityService.setSelectedEntity(response.data[0]);
enumEntityService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#enumEntityTabs li:eq(0) a').tab('show');
};
$scope.enumEntityGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'name'},
{ name: 'enumEntityId'} 
]
,data: $scope.selectedEntity.enumEntityList
 };
$scope.enumEntityGridOptions.onRegisterApi = function(gridApi){
$scope.enumEntityGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
enumEntityService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
enumEntityService.setSelectedEntity(response.data[0]);
});
$('#enumEntityTabs li:eq(0) a').tab('show');
}
else 
enumEntityService.setSelectedEntity(null);
enumEntityService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumValue.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadEnumEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumEntity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.enumEntityList]);
};
})
.service("enumEntityService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,enumValueList: []};
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
var promise= $http.post("../enumEntity/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../enumEntity/"+entity.enumEntityId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../enumEntity/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../enumEntity/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../enumEntity/"+this.selectedEntity.enumEntityId;
var promise= $http["delete"](url);
return promise; 
}
this.loadFile= function(file,field){
var formData = new FormData();
if (file!=null)
formData.append('file',file);
var promise= $http.post("../enumEntity/"+this.selectedEntity.enumEntityId+"/load"+field+"/",formData,{
 headers: {'Content-Type': undefined}
});
return promise; 
}
 this.initProjectList= function()
{
var promise= $http
.post("../project/search",
{});
return promise;
};
 this.initEnumValueList= function()
{
var promise= $http
.post("../enumValue/search",
{});
return promise;
};
})
.controller("enumEntityController",function($scope,$http,projectService, securityService ,enumEntityService,enumValueService,entityGroupService,restrictionEntityGroupService,entityService)
{
//project
$scope.searchBean=enumEntityService.searchBean;
$scope.entityList=enumEntityService.entityList;
$scope.selectedEntity=enumEntityService.selectedEntity;
$scope.childrenList=enumEntityService.childrenList; 
$scope.reset = function()
{
enumEntityService.resetSearchBean();
$scope.searchBean=enumEntityService.searchBean;enumEntityService.setSelectedEntity(null);
enumEntityService.selectedEntity.show=false;
enumEntityService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
projectService.update().then(function successCallback(response) {
projectService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
}
);
};
$scope.addNew= function()
{
enumEntityService.setSelectedEntity(null);
enumEntityService.setEntityList(null);
enumEntityService.selectedEntity.show=true;
$('#enumEntityTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
enumEntityService.selectedEntity.show=false;
enumEntityService.searchBean.enumValueList=[];
enumEntityService.searchBean.enumValueList.push(enumEntityService.searchBean.enumValue);
delete enumEntityService.searchBean.enumValue; 
enumEntityService.search().then(function successCallback(response) {
enumEntityService.setEntityList(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.insert=function()
{
if (!$scope.enumEntityDetailForm.$valid) return; 
enumEntityService.selectedEntity.show=false;
enumEntityService.selectedEntity.projectList.push(projectService.selectedEntity);
enumEntityService.insert().then(function successCallBack(response) { 
projectService.selectedEntity.enumEntityList.push(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.update=function()
{
if (!$scope.enumEntityDetailForm.$valid) return; 
enumEntityService.selectedEntity.show=false;

for (i=0; i<projectService.selectedEntity.enumEntityList.length; i++)

{

if (projectService.selectedEntity.enumEntityList[i].enumEntityId==enumEntityService.selectedEntity.enumEntityId)

projectService.selectedEntity.enumEntityList.splice(i,1);

}

projectService.selectedEntity.enumEntityList.push(enumEntityService.selectedEntity);

enumEntityService.update().then(function successCallback(response){
enumEntityService.setSelectedEntity(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.remove= function()
{
enumEntityService.selectedEntity.show=false;
for (i=0; i<projectService.selectedEntity.enumEntityList.length; i++)
{
if (projectService.selectedEntity.enumEntityList[i].enumEntityId==enumEntityService.selectedEntity.enumEntityId)
projectService.selectedEntity.enumEntityList.splice(i,1);
}
enumEntityService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<projectService.selectedEntity.enumEntityList.length; i++)
{
if (projectService.selectedEntity.enumEntityList[i].enumEntityId==enumEntityService.selectedEntity.enumEntityId)
projectService.selectedEntity.enumEntityList.splice(i,1);
}
$scope.updateParent();
enumEntityService.del().then(function successCallback(response) { 
enumEntityService.setSelectedEntity(null);
projectService.initEnumEntityList().then(function(response) {
projectService.childrenList.enumEntityList=response.data;
});
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.projectGridApi!=undefined && $scope.projectGridApi!=null)
 $scope.projectGridApi.core.handleWindowResize(); 
if ($scope.enumValueGridApi!=undefined && $scope.enumValueGridApi!=null)
 $scope.enumValueGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
enumEntityService.loadFile(file,field).then(function successCallback(response) {
enumEntityService.setSelectedEntity(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
return; 
});
}
$scope.trueFalseValues=[true,false];
$scope.showProjectDetail= function(index)
{
if (index!=null)
{
projectService.searchOne(enumEntityService.selectedEntity.projectList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
projectService.initEnumEntityList().then(function successCallback(response) {
projectService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
projectService.initEntityGroupList().then(function successCallback(response) {
projectService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
projectService.setSelectedEntity(response.data[0]);
projectService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (enumEntityService.selectedEntity.project==null || enumEntityService.selectedEntity.project==undefined)
{
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
projectService.initEnumEntityList().then(function successCallback(response) {
projectService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
projectService.initEntityGroupList().then(function successCallback(response) {
projectService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
projectService.setSelectedEntity(null); 
projectService.selectedEntity.show=true; 
}
else
projectService.searchOne(enumEntityService.selectedEntity.project).then(
function successCallback(response) {
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
projectService.initEnumEntityList().then(function successCallback(response) {
projectService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
projectService.initEntityGroupList().then(function successCallback(response) {
projectService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
projectService.setSelectedEntity(response.data[0]);
projectService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#projectTabs li:eq(0) a').tab('show');
};
$scope.showEnumValueDetail= function(index)
{
if (index!=null)
{
enumValueService.searchOne(enumEntityService.selectedEntity.enumValueList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
enumValueService.initEnumEntityList().then(function successCallback(response) {
enumValueService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
enumValueService.setSelectedEntity(response.data[0]);
enumValueService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (enumEntityService.selectedEntity.enumValue==null || enumEntityService.selectedEntity.enumValue==undefined)
{
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
enumValueService.initEnumEntityList().then(function successCallback(response) {
enumValueService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
enumValueService.setSelectedEntity(null); 
enumValueService.selectedEntity.show=true; 
}
else
enumValueService.searchOne(enumEntityService.selectedEntity.enumValue).then(
function successCallback(response) {
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
enumValueService.initEnumEntityList().then(function successCallback(response) {
enumValueService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
enumValueService.setSelectedEntity(response.data[0]);
enumValueService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#enumValueTabs li:eq(0) a').tab('show');
};
$scope.projectGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'projectId'},
{ name: 'name'} 
]
,data: $scope.selectedEntity.projectList
 };
$scope.projectGridOptions.onRegisterApi = function(gridApi){
$scope.projectGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
projectService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
projectService.setSelectedEntity(response.data[0]);
});
$('#projectTabs li:eq(0) a').tab('show');
}
else 
projectService.setSelectedEntity(null);
projectService.selectedEntity.show = row.isSelected;
});
  };
$scope.enumValueListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'value'},
{ name: 'enumValueId'},
{ name: 'name'} 
]
,data: $scope.selectedEntity.enumValueList
 };
$scope.enumValueListGridOptions.onRegisterApi = function(gridApi){
$scope.enumValueGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
enumValueService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
enumValueService.setSelectedEntity(response.data[0]);
});
$('#enumValueTabs li:eq(0) a').tab('show');
}
else 
enumValueService.setSelectedEntity(null);
enumValueService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumEntity.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadProjectList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("project.xls",?) FROM ?',[mystyle,$scope.selectedEntity.projectList]);
};
$scope.saveLinkedEnumValue= function() {
enumEntityService.selectedEntity.enumValueList.push(enumEntityService.selectedEntity.enumValue);
}
$scope.downloadEnumValueList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumValue.xls",?) FROM ?',[mystyle,$scope.selectedEntity.enumValueList]);
};
})
.service("entityGroupService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,restrictionEntityGroupList: [],entityList: []};
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
var promise= $http.post("../entityGroup/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../entityGroup/"+entity.entityGroupId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../entityGroup/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../entityGroup/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../entityGroup/"+this.selectedEntity.entityGroupId;
var promise= $http["delete"](url);
return promise; 
}
this.loadFile= function(file,field){
var formData = new FormData();
if (file!=null)
formData.append('file',file);
var promise= $http.post("../entityGroup/"+this.selectedEntity.entityGroupId+"/load"+field+"/",formData,{
 headers: {'Content-Type': undefined}
});
return promise; 
}
 this.initProjectList= function()
{
var promise= $http
.post("../project/search",
{});
return promise;
};
 this.initRestrictionEntityGroupList= function()
{
var promise= $http
.post("../restrictionEntityGroup/search",
{});
return promise;
};
 this.initEntityList= function()
{
var promise= $http
.post("../entity/search",
{});
return promise;
};
})
.controller("entityGroupController",function($scope,$http,projectService, securityService ,enumEntityService,enumValueService,entityGroupService,restrictionEntityGroupService,entityService)
{
//project
$scope.searchBean=entityGroupService.searchBean;
$scope.entityList=entityGroupService.entityList;
$scope.selectedEntity=entityGroupService.selectedEntity;
$scope.childrenList=entityGroupService.childrenList; 
$scope.reset = function()
{
entityGroupService.resetSearchBean();
$scope.searchBean=entityGroupService.searchBean;entityGroupService.setSelectedEntity(null);
entityGroupService.selectedEntity.show=false;
entityGroupService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
projectService.update().then(function successCallback(response) {
projectService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
}
);
};
$scope.addNew= function()
{
entityGroupService.setSelectedEntity(null);
entityGroupService.setEntityList(null);
entityGroupService.selectedEntity.show=true;
$('#entityGroupTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
entityGroupService.selectedEntity.show=false;
entityGroupService.searchBean.restrictionEntityGroupList=[];
entityGroupService.searchBean.restrictionEntityGroupList.push(entityGroupService.searchBean.restrictionEntityGroup);
delete entityGroupService.searchBean.restrictionEntityGroup; 
entityGroupService.searchBean.entityList=[];
entityGroupService.searchBean.entityList.push(entityGroupService.searchBean.entity);
delete entityGroupService.searchBean.entity; 
entityGroupService.search().then(function successCallback(response) {
entityGroupService.setEntityList(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.insert=function()
{
if (!$scope.entityGroupDetailForm.$valid) return; 
entityGroupService.selectedEntity.show=false;
entityGroupService.selectedEntity.projectList.push(projectService.selectedEntity);
entityGroupService.insert().then(function successCallBack(response) { 
projectService.selectedEntity.entityGroupList.push(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.update=function()
{
if (!$scope.entityGroupDetailForm.$valid) return; 
entityGroupService.selectedEntity.show=false;

for (i=0; i<projectService.selectedEntity.entityGroupList.length; i++)

{

if (projectService.selectedEntity.entityGroupList[i].entityGroupId==entityGroupService.selectedEntity.entityGroupId)

projectService.selectedEntity.entityGroupList.splice(i,1);

}

projectService.selectedEntity.entityGroupList.push(entityGroupService.selectedEntity);

entityGroupService.update().then(function successCallback(response){
entityGroupService.setSelectedEntity(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.remove= function()
{
entityGroupService.selectedEntity.show=false;
for (i=0; i<projectService.selectedEntity.entityGroupList.length; i++)
{
if (projectService.selectedEntity.entityGroupList[i].entityGroupId==entityGroupService.selectedEntity.entityGroupId)
projectService.selectedEntity.entityGroupList.splice(i,1);
}
entityGroupService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<projectService.selectedEntity.entityGroupList.length; i++)
{
if (projectService.selectedEntity.entityGroupList[i].entityGroupId==entityGroupService.selectedEntity.entityGroupId)
projectService.selectedEntity.entityGroupList.splice(i,1);
}
$scope.updateParent();
entityGroupService.del().then(function successCallback(response) { 
entityGroupService.setSelectedEntity(null);
projectService.initEntityGroupList().then(function(response) {
projectService.childrenList.entityGroupList=response.data;
});
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.projectGridApi!=undefined && $scope.projectGridApi!=null)
 $scope.projectGridApi.core.handleWindowResize(); 
if ($scope.restrictionEntityGroupGridApi!=undefined && $scope.restrictionEntityGroupGridApi!=null)
 $scope.restrictionEntityGroupGridApi.core.handleWindowResize(); 
if ($scope.entityGridApi!=undefined && $scope.entityGridApi!=null)
 $scope.entityGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
entityGroupService.loadFile(file,field).then(function successCallback(response) {
entityGroupService.setSelectedEntity(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
return; 
});
}
$scope.trueFalseValues=[true,false];
$scope.showProjectDetail= function(index)
{
if (index!=null)
{
projectService.searchOne(entityGroupService.selectedEntity.projectList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
projectService.initEnumEntityList().then(function successCallback(response) {
projectService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
projectService.initEntityGroupList().then(function successCallback(response) {
projectService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
projectService.setSelectedEntity(response.data[0]);
projectService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (entityGroupService.selectedEntity.project==null || entityGroupService.selectedEntity.project==undefined)
{
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
projectService.initEnumEntityList().then(function successCallback(response) {
projectService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
projectService.initEntityGroupList().then(function successCallback(response) {
projectService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
projectService.setSelectedEntity(null); 
projectService.selectedEntity.show=true; 
}
else
projectService.searchOne(entityGroupService.selectedEntity.project).then(
function successCallback(response) {
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
projectService.initEnumEntityList().then(function successCallback(response) {
projectService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
projectService.initEntityGroupList().then(function successCallback(response) {
projectService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
projectService.setSelectedEntity(response.data[0]);
projectService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#projectTabs li:eq(0) a').tab('show');
};
$scope.showRestrictionEntityGroupDetail= function(index)
{
if (index!=null)
{
restrictionEntityGroupService.searchOne(entityGroupService.selectedEntity.restrictionEntityGroupList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionEntityGroupService.initRoleList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
restrictionEntityGroupService.initEntityGroupList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
restrictionEntityGroupService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (entityGroupService.selectedEntity.restrictionEntityGroup==null || entityGroupService.selectedEntity.restrictionEntityGroup==undefined)
{
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionEntityGroupService.initRoleList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
restrictionEntityGroupService.initEntityGroupList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
restrictionEntityGroupService.setSelectedEntity(null); 
restrictionEntityGroupService.selectedEntity.show=true; 
}
else
restrictionEntityGroupService.searchOne(entityGroupService.selectedEntity.restrictionEntityGroup).then(
function successCallback(response) {
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionEntityGroupService.initRoleList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
restrictionEntityGroupService.initEntityGroupList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
restrictionEntityGroupService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
};
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(entityGroupService.selectedEntity.entityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
entityService.childrenList.securityTypeList=["BLOCK_WITH_RESTRICTION","ACCESS_WITH_PERMISSION",];
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (entityGroupService.selectedEntity.entity==null || entityGroupService.selectedEntity.entity==undefined)
{
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
entityService.childrenList.securityTypeList=["BLOCK_WITH_RESTRICTION","ACCESS_WITH_PERMISSION",];
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
}
else
entityService.searchOne(entityGroupService.selectedEntity.entity).then(
function successCallback(response) {
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
entityService.childrenList.securityTypeList=["BLOCK_WITH_RESTRICTION","ACCESS_WITH_PERMISSION",];
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#entityTabs li:eq(0) a').tab('show');
};
$scope.projectGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'projectId'},
{ name: 'name'} 
]
,data: $scope.selectedEntity.projectList
 };
$scope.projectGridOptions.onRegisterApi = function(gridApi){
$scope.projectGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
projectService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
projectService.setSelectedEntity(response.data[0]);
});
$('#projectTabs li:eq(0) a').tab('show');
}
else 
projectService.setSelectedEntity(null);
projectService.selectedEntity.show = row.isSelected;
});
  };
$scope.restrictionEntityGroupListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'canCreate'},
{ name: 'canDelete'},
{ name: 'canUpdate'},
{ name: 'restrictionEntityGroupId'},
{ name: 'canSearch'} 
]
,data: $scope.selectedEntity.restrictionEntityGroupList
 };
$scope.restrictionEntityGroupListGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionEntityGroupGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionEntityGroupService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
});
$('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
}
else 
restrictionEntityGroupService.setSelectedEntity(null);
restrictionEntityGroupService.selectedEntity.show = row.isSelected;
});
  };
$scope.entityListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'name'},
{ name: 'entityId'},
{ name: 'descendantMaxLevel'} 
]
,data: $scope.selectedEntity.entityList
 };
$scope.entityListGridOptions.onRegisterApi = function(gridApi){
$scope.entityGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
entityService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
entityService.setSelectedEntity(response.data[0]);
});
$('#entityTabs li:eq(0) a').tab('show');
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
alasql('SELECT * INTO XLSXML("entityGroup.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadProjectList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("project.xls",?) FROM ?',[mystyle,$scope.selectedEntity.projectList]);
};
$scope.saveLinkedRestrictionEntityGroup= function() {
entityGroupService.selectedEntity.restrictionEntityGroupList.push(entityGroupService.selectedEntity.restrictionEntityGroup);
}
$scope.downloadRestrictionEntityGroupList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionEntityGroup.xls",?) FROM ?',[mystyle,$scope.selectedEntity.restrictionEntityGroupList]);
};
$scope.saveLinkedEntity= function() {
entityGroupService.selectedEntity.entityList.push(entityGroupService.selectedEntity.entity);
}
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityList]);
};
})
.service("restrictionEntityGroupService", function($http)
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
var promise= $http.post("../restrictionEntityGroup/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../restrictionEntityGroup/"+entity.restrictionEntityGroupId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../restrictionEntityGroup/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../restrictionEntityGroup/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../restrictionEntityGroup/"+this.selectedEntity.restrictionEntityGroupId;
var promise= $http["delete"](url);
return promise; 
}
this.loadFile= function(file,field){
var formData = new FormData();
if (file!=null)
formData.append('file',file);
var promise= $http.post("../restrictionEntityGroup/"+this.selectedEntity.restrictionEntityGroupId+"/load"+field+"/",formData,{
 headers: {'Content-Type': undefined}
});
return promise; 
}
 this.initRoleList= function()
{
var promise= $http
.post("../role/search",
{});
return promise;
};
 this.initEntityGroupList= function()
{
var promise= $http
.post("../entityGroup/search",
{});
return promise;
};
})
.controller("restrictionEntityGroupController",function($scope,$http,projectService, securityService ,enumEntityService,enumValueService,entityGroupService,restrictionEntityGroupService,entityService)
{
//project
$scope.searchBean=restrictionEntityGroupService.searchBean;
$scope.entityList=restrictionEntityGroupService.entityList;
$scope.selectedEntity=restrictionEntityGroupService.selectedEntity;
$scope.childrenList=restrictionEntityGroupService.childrenList; 
$scope.reset = function()
{
restrictionEntityGroupService.resetSearchBean();
$scope.searchBean=restrictionEntityGroupService.searchBean;restrictionEntityGroupService.setSelectedEntity(null);
restrictionEntityGroupService.selectedEntity.show=false;
restrictionEntityGroupService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
projectService.update().then(function successCallback(response) {
projectService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
}
);
};
$scope.addNew= function()
{
restrictionEntityGroupService.setSelectedEntity(null);
restrictionEntityGroupService.setEntityList(null);
restrictionEntityGroupService.selectedEntity.show=true;
$('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
restrictionEntityGroupService.selectedEntity.show=false;
restrictionEntityGroupService.search().then(function successCallback(response) {
restrictionEntityGroupService.setEntityList(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.insert=function()
{
if (!$scope.restrictionEntityGroupDetailForm.$valid) return; 
restrictionEntityGroupService.selectedEntity.show=false;
restrictionEntityGroupService.selectedEntity.projectList.push(projectService.selectedEntity);
restrictionEntityGroupService.insert().then(function successCallBack(response) { 
projectService.selectedEntity.restrictionEntityGroupList.push(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.update=function()
{
if (!$scope.restrictionEntityGroupDetailForm.$valid) return; 
restrictionEntityGroupService.selectedEntity.show=false;

for (i=0; i<projectService.selectedEntity.restrictionEntityGroupList.length; i++)

{

if (projectService.selectedEntity.restrictionEntityGroupList[i].restrictionEntityGroupId==restrictionEntityGroupService.selectedEntity.restrictionEntityGroupId)

projectService.selectedEntity.restrictionEntityGroupList.splice(i,1);

}

projectService.selectedEntity.restrictionEntityGroupList.push(restrictionEntityGroupService.selectedEntity);

restrictionEntityGroupService.update().then(function successCallback(response){
restrictionEntityGroupService.setSelectedEntity(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.remove= function()
{
restrictionEntityGroupService.selectedEntity.show=false;
for (i=0; i<projectService.selectedEntity.restrictionEntityGroupList.length; i++)
{
if (projectService.selectedEntity.restrictionEntityGroupList[i].restrictionEntityGroupId==restrictionEntityGroupService.selectedEntity.restrictionEntityGroupId)
projectService.selectedEntity.restrictionEntityGroupList.splice(i,1);
}
restrictionEntityGroupService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<projectService.selectedEntity.restrictionEntityGroupList.length; i++)
{
if (projectService.selectedEntity.restrictionEntityGroupList[i].restrictionEntityGroupId==restrictionEntityGroupService.selectedEntity.restrictionEntityGroupId)
projectService.selectedEntity.restrictionEntityGroupList.splice(i,1);
}
$scope.updateParent();
restrictionEntityGroupService.del().then(function successCallback(response) { 
restrictionEntityGroupService.setSelectedEntity(null);
projectService.initRestrictionEntityGroupList().then(function(response) {
projectService.childrenList.restrictionEntityGroupList=response.data;
});
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.roleGridApi!=undefined && $scope.roleGridApi!=null)
 $scope.roleGridApi.core.handleWindowResize(); 
if ($scope.entityGroupGridApi!=undefined && $scope.entityGroupGridApi!=null)
 $scope.entityGroupGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
restrictionEntityGroupService.loadFile(file,field).then(function successCallback(response) {
restrictionEntityGroupService.setSelectedEntity(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
return; 
});
}
$scope.trueFalseValues=[true,false];
$scope.showRoleDetail= function(index)
{
if (index!=null)
{
roleService.searchOne(restrictionEntityGroupService.selectedEntity.roleList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.user==undefined || securityService.restrictionList.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (restrictionEntityGroupService.selectedEntity.role==null || restrictionEntityGroupService.selectedEntity.role==undefined)
{
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.user==undefined || securityService.restrictionList.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
roleService.setSelectedEntity(null); 
roleService.selectedEntity.show=true; 
}
else
roleService.searchOne(restrictionEntityGroupService.selectedEntity.role).then(
function successCallback(response) {
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.user==undefined || securityService.restrictionList.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#roleTabs li:eq(0) a').tab('show');
};
$scope.showEntityGroupDetail= function(index)
{
if (index!=null)
{
entityGroupService.searchOne(restrictionEntityGroupService.selectedEntity.entityGroupList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
entityGroupService.setSelectedEntity(response.data[0]);
entityGroupService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (restrictionEntityGroupService.selectedEntity.entityGroup==null || restrictionEntityGroupService.selectedEntity.entityGroup==undefined)
{
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
entityGroupService.setSelectedEntity(null); 
entityGroupService.selectedEntity.show=true; 
}
else
entityGroupService.searchOne(restrictionEntityGroupService.selectedEntity.entityGroup).then(
function successCallback(response) {
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
entityGroupService.setSelectedEntity(response.data[0]);
entityGroupService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#entityGroupTabs li:eq(0) a').tab('show');
};
$scope.roleGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'role'},
{ name: 'roleId'} 
]
,data: $scope.selectedEntity.roleList
 };
$scope.roleGridOptions.onRegisterApi = function(gridApi){
$scope.roleGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
roleService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
roleService.setSelectedEntity(response.data[0]);
});
$('#roleTabs li:eq(0) a').tab('show');
}
else 
roleService.setSelectedEntity(null);
roleService.selectedEntity.show = row.isSelected;
});
  };
$scope.entityGroupGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'name'},
{ name: 'entityGroupId'},
{ name: 'entityId'} 
]
,data: $scope.selectedEntity.entityGroupList
 };
$scope.entityGroupGridOptions.onRegisterApi = function(gridApi){
$scope.entityGroupGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
entityGroupService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
entityGroupService.setSelectedEntity(response.data[0]);
});
$('#entityGroupTabs li:eq(0) a').tab('show');
}
else 
entityGroupService.setSelectedEntity(null);
entityGroupService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionEntityGroup.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadRoleList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,$scope.selectedEntity.roleList]);
};
$scope.downloadEntityGroupList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entityGroup.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityGroupList]);
};
})
.service("entityService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,fieldList: [],restrictionEntityList: [],tabList: [],enumFieldList: [],relationshipList: []};
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
var promise= $http.post("../entity/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../entity/"+entity.entityId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../entity/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../entity/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../entity/"+this.selectedEntity.entityId;
var promise= $http["delete"](url);
return promise; 
}
this.loadFile= function(file,field){
var formData = new FormData();
if (file!=null)
formData.append('file',file);
var promise= $http.post("../entity/"+this.selectedEntity.entityId+"/load"+field+"/",formData,{
 headers: {'Content-Type': undefined}
});
return promise; 
}
 this.initFieldList= function()
{
var promise= $http
.post("../field/search",
{});
return promise;
};
 this.initEntityGroupList= function()
{
var promise= $http
.post("../entityGroup/search",
{});
return promise;
};
 this.initRestrictionEntityList= function()
{
var promise= $http
.post("../restrictionEntity/search",
{});
return promise;
};
 this.initTabList= function()
{
var promise= $http
.post("../tab/search",
{});
return promise;
};
 this.initEnumFieldList= function()
{
var promise= $http
.post("../enumField/search",
{});
return promise;
};
 this.initRelationshipList= function()
{
var promise= $http
.post("../relationship/search",
{});
return promise;
};
})
.controller("entityController",function($scope,$http,projectService, securityService ,enumEntityService,enumValueService,entityGroupService,restrictionEntityGroupService,entityService)
{
//project
$scope.searchBean=entityService.searchBean;
$scope.entityList=entityService.entityList;
$scope.selectedEntity=entityService.selectedEntity;
$scope.childrenList=entityService.childrenList; 
$scope.reset = function()
{
entityService.resetSearchBean();
$scope.searchBean=entityService.searchBean;entityService.setSelectedEntity(null);
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
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
}
);
};
$scope.addNew= function()
{
entityService.setSelectedEntity(null);
entityService.setEntityList(null);
entityService.selectedEntity.show=true;
$('#entityTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
entityService.selectedEntity.show=false;
entityService.searchBean.fieldList=[];
entityService.searchBean.fieldList.push(entityService.searchBean.field);
delete entityService.searchBean.field; 
entityService.searchBean.restrictionEntityList=[];
entityService.searchBean.restrictionEntityList.push(entityService.searchBean.restrictionEntity);
delete entityService.searchBean.restrictionEntity; 
entityService.searchBean.tabList=[];
entityService.searchBean.tabList.push(entityService.searchBean.tab);
delete entityService.searchBean.tab; 
entityService.searchBean.enumFieldList=[];
entityService.searchBean.enumFieldList.push(entityService.searchBean.enumField);
delete entityService.searchBean.enumField; 
entityService.searchBean.relationshipList=[];
entityService.searchBean.relationshipList.push(entityService.searchBean.relationship);
delete entityService.searchBean.relationship; 
entityService.search().then(function successCallback(response) {
entityService.setEntityList(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.insert=function()
{
if (!$scope.entityDetailForm.$valid) return; 
entityService.selectedEntity.show=false;
entityService.selectedEntity.projectList.push(projectService.selectedEntity);
entityService.insert().then(function successCallBack(response) { 
projectService.selectedEntity.entityList.push(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.update=function()
{
if (!$scope.entityDetailForm.$valid) return; 
entityService.selectedEntity.show=false;

for (i=0; i<projectService.selectedEntity.entityList.length; i++)

{

if (projectService.selectedEntity.entityList[i].entityId==entityService.selectedEntity.entityId)

projectService.selectedEntity.entityList.splice(i,1);

}

projectService.selectedEntity.entityList.push(entityService.selectedEntity);

entityService.update().then(function successCallback(response){
entityService.setSelectedEntity(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.remove= function()
{
entityService.selectedEntity.show=false;
for (i=0; i<projectService.selectedEntity.entityList.length; i++)
{
if (projectService.selectedEntity.entityList[i].entityId==entityService.selectedEntity.entityId)
projectService.selectedEntity.entityList.splice(i,1);
}
entityService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<projectService.selectedEntity.entityList.length; i++)
{
if (projectService.selectedEntity.entityList[i].entityId==entityService.selectedEntity.entityId)
projectService.selectedEntity.entityList.splice(i,1);
}
$scope.updateParent();
entityService.del().then(function successCallback(response) { 
entityService.setSelectedEntity(null);
projectService.initEntityList().then(function(response) {
projectService.childrenList.entityList=response.data;
});
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.fieldGridApi!=undefined && $scope.fieldGridApi!=null)
 $scope.fieldGridApi.core.handleWindowResize(); 
if ($scope.entityGroupGridApi!=undefined && $scope.entityGroupGridApi!=null)
 $scope.entityGroupGridApi.core.handleWindowResize(); 
if ($scope.restrictionEntityGridApi!=undefined && $scope.restrictionEntityGridApi!=null)
 $scope.restrictionEntityGridApi.core.handleWindowResize(); 
if ($scope.tabGridApi!=undefined && $scope.tabGridApi!=null)
 $scope.tabGridApi.core.handleWindowResize(); 
if ($scope.enumFieldGridApi!=undefined && $scope.enumFieldGridApi!=null)
 $scope.enumFieldGridApi.core.handleWindowResize(); 
if ($scope.relationshipGridApi!=undefined && $scope.relationshipGridApi!=null)
 $scope.relationshipGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
entityService.loadFile(file,field).then(function successCallback(response) {
entityService.setSelectedEntity(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
return; 
});
}
$scope.trueFalseValues=[true,false];
$scope.showFieldDetail= function(index)
{
if (index!=null)
{
fieldService.searchOne(entityService.selectedEntity.fieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
fieldService.initTabList().then(function successCallback(response) {
fieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
fieldService.initRestrictionFieldList().then(function successCallback(response) {
fieldService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
fieldService.initAnnotationList().then(function successCallback(response) {
fieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
fieldService.initEntityList().then(function successCallback(response) {
fieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG","FILE","PHOTO",];
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.field==null || entityService.selectedEntity.field==undefined)
{
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
fieldService.initTabList().then(function successCallback(response) {
fieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
fieldService.initRestrictionFieldList().then(function successCallback(response) {
fieldService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
fieldService.initAnnotationList().then(function successCallback(response) {
fieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
fieldService.initEntityList().then(function successCallback(response) {
fieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG","FILE","PHOTO",];
fieldService.setSelectedEntity(null); 
fieldService.selectedEntity.show=true; 
}
else
fieldService.searchOne(entityService.selectedEntity.field).then(
function successCallback(response) {
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
fieldService.initTabList().then(function successCallback(response) {
fieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
fieldService.initRestrictionFieldList().then(function successCallback(response) {
fieldService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
fieldService.initAnnotationList().then(function successCallback(response) {
fieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
fieldService.initEntityList().then(function successCallback(response) {
fieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG","FILE","PHOTO",];
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#fieldTabs li:eq(0) a').tab('show');
};
$scope.showEntityGroupDetail= function(index)
{
if (index!=null)
{
entityGroupService.searchOne(entityService.selectedEntity.entityGroupList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
entityGroupService.setSelectedEntity(response.data[0]);
entityGroupService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.entityGroup==null || entityService.selectedEntity.entityGroup==undefined)
{
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
entityGroupService.setSelectedEntity(null); 
entityGroupService.selectedEntity.show=true; 
}
else
entityGroupService.searchOne(entityService.selectedEntity.entityGroup).then(
function successCallback(response) {
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
entityGroupService.setSelectedEntity(response.data[0]);
entityGroupService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#entityGroupTabs li:eq(0) a').tab('show');
};
$scope.showRestrictionEntityDetail= function(index)
{
if (index!=null)
{
restrictionEntityService.searchOne(entityService.selectedEntity.restrictionEntityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionEntityService.initRoleList().then(function successCallback(response) {
restrictionEntityService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
restrictionEntityService.initEntityList().then(function successCallback(response) {
restrictionEntityService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
restrictionEntityService.setSelectedEntity(response.data[0]);
restrictionEntityService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.restrictionEntity==null || entityService.selectedEntity.restrictionEntity==undefined)
{
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionEntityService.initRoleList().then(function successCallback(response) {
restrictionEntityService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
restrictionEntityService.initEntityList().then(function successCallback(response) {
restrictionEntityService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
restrictionEntityService.setSelectedEntity(null); 
restrictionEntityService.selectedEntity.show=true; 
}
else
restrictionEntityService.searchOne(entityService.selectedEntity.restrictionEntity).then(
function successCallback(response) {
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionEntityService.initRoleList().then(function successCallback(response) {
restrictionEntityService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
restrictionEntityService.initEntityList().then(function successCallback(response) {
restrictionEntityService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
restrictionEntityService.setSelectedEntity(response.data[0]);
restrictionEntityService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#restrictionEntityTabs li:eq(0) a').tab('show');
};
$scope.showTabDetail= function(index)
{
if (index!=null)
{
tabService.searchOne(entityService.selectedEntity.tabList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.tab==null || entityService.selectedEntity.tab==undefined)
{
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
tabService.setSelectedEntity(null); 
tabService.selectedEntity.show=true; 
}
else
tabService.searchOne(entityService.selectedEntity.tab).then(
function successCallback(response) {
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#tabTabs li:eq(0) a').tab('show');
};
$scope.showEnumFieldDetail= function(index)
{
if (index!=null)
{
enumFieldService.searchOne(entityService.selectedEntity.enumFieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
enumFieldService.initTabList().then(function successCallback(response) {
enumFieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
enumFieldService.initAnnotationList().then(function successCallback(response) {
enumFieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
enumFieldService.initEntityList().then(function successCallback(response) {
enumFieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
enumFieldService.initEnumEntityList().then(function successCallback(response) {
enumFieldService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
enumFieldService.setSelectedEntity(response.data[0]);
enumFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.enumField==null || entityService.selectedEntity.enumField==undefined)
{
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
enumFieldService.initTabList().then(function successCallback(response) {
enumFieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
enumFieldService.initAnnotationList().then(function successCallback(response) {
enumFieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
enumFieldService.initEntityList().then(function successCallback(response) {
enumFieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
enumFieldService.initEnumEntityList().then(function successCallback(response) {
enumFieldService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
enumFieldService.setSelectedEntity(null); 
enumFieldService.selectedEntity.show=true; 
}
else
enumFieldService.searchOne(entityService.selectedEntity.enumField).then(
function successCallback(response) {
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
enumFieldService.initTabList().then(function successCallback(response) {
enumFieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
enumFieldService.initAnnotationList().then(function successCallback(response) {
enumFieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
enumFieldService.initEntityList().then(function successCallback(response) {
enumFieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
enumFieldService.initEnumEntityList().then(function successCallback(response) {
enumFieldService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
enumFieldService.setSelectedEntity(response.data[0]);
enumFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#enumFieldTabs li:eq(0) a').tab('show');
};
$scope.showRelationshipDetail= function(index)
{
if (index!=null)
{
relationshipService.searchOne(entityService.selectedEntity.relationshipList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
relationshipService.initEntityList().then(function successCallback(response) {
relationshipService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
relationshipService.initEntityList().then(function successCallback(response) {
relationshipService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
relationshipService.initAnnotationList().then(function successCallback(response) {
relationshipService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
relationshipService.initTabList().then(function successCallback(response) {
relationshipService.childrenList.tabList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
relationshipService.childrenList.relationshipTypeList=["ONE_TO_ONE","ONE_TO_MANY","MANY_TO_ONE","MANY_TO_MANY","MANY_TO_MANY_BACK",];
relationshipService.setSelectedEntity(response.data[0]);
relationshipService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.relationship==null || entityService.selectedEntity.relationship==undefined)
{
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
relationshipService.initEntityList().then(function successCallback(response) {
relationshipService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
relationshipService.initEntityList().then(function successCallback(response) {
relationshipService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
relationshipService.initAnnotationList().then(function successCallback(response) {
relationshipService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
relationshipService.initTabList().then(function successCallback(response) {
relationshipService.childrenList.tabList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
relationshipService.childrenList.relationshipTypeList=["ONE_TO_ONE","ONE_TO_MANY","MANY_TO_ONE","MANY_TO_MANY","MANY_TO_MANY_BACK",];
relationshipService.setSelectedEntity(null); 
relationshipService.selectedEntity.show=true; 
}
else
relationshipService.searchOne(entityService.selectedEntity.relationship).then(
function successCallback(response) {
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
relationshipService.initEntityList().then(function successCallback(response) {
relationshipService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
relationshipService.initEntityList().then(function successCallback(response) {
relationshipService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
relationshipService.initAnnotationList().then(function successCallback(response) {
relationshipService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
relationshipService.initTabList().then(function successCallback(response) {
relationshipService.childrenList.tabList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
relationshipService.childrenList.relationshipTypeList=["ONE_TO_ONE","ONE_TO_MANY","MANY_TO_ONE","MANY_TO_MANY","MANY_TO_MANY_BACK",];
relationshipService.setSelectedEntity(response.data[0]);
relationshipService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#relationshipTabs li:eq(0) a').tab('show');
};
$scope.fieldListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'name'},
{ name: 'priority'},
{ name: 'fieldId'} 
]
,data: $scope.selectedEntity.fieldList
 };
$scope.fieldListGridOptions.onRegisterApi = function(gridApi){
$scope.fieldGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
fieldService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
fieldService.setSelectedEntity(response.data[0]);
});
$('#fieldTabs li:eq(0) a').tab('show');
}
else 
fieldService.setSelectedEntity(null);
fieldService.selectedEntity.show = row.isSelected;
});
  };
$scope.entityGroupGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'name'},
{ name: 'entityGroupId'},
{ name: 'entityId'} 
]
,data: $scope.selectedEntity.entityGroupList
 };
$scope.entityGroupGridOptions.onRegisterApi = function(gridApi){
$scope.entityGroupGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
entityGroupService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
entityGroupService.setSelectedEntity(response.data[0]);
});
$('#entityGroupTabs li:eq(0) a').tab('show');
}
else 
entityGroupService.setSelectedEntity(null);
entityGroupService.selectedEntity.show = row.isSelected;
});
  };
$scope.restrictionEntityListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'canCreate'},
{ name: 'canSearch'},
{ name: 'canUpdate'},
{ name: 'canDelete'},
{ name: 'restrictionEntityId'} 
]
,data: $scope.selectedEntity.restrictionEntityList
 };
$scope.restrictionEntityListGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionEntityGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionEntityService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
restrictionEntityService.setSelectedEntity(response.data[0]);
});
$('#restrictionEntityTabs li:eq(0) a').tab('show');
}
else 
restrictionEntityService.setSelectedEntity(null);
restrictionEntityService.selectedEntity.show = row.isSelected;
});
  };
$scope.tabListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'tabId'},
{ name: 'name'} 
]
,data: $scope.selectedEntity.tabList
 };
$scope.tabListGridOptions.onRegisterApi = function(gridApi){
$scope.tabGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
tabService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
tabService.setSelectedEntity(response.data[0]);
});
$('#tabTabs li:eq(0) a').tab('show');
}
else 
tabService.setSelectedEntity(null);
tabService.selectedEntity.show = row.isSelected;
});
  };
$scope.enumFieldListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'name'},
{ name: 'priority'},
{ name: 'enumFieldId'} 
]
,data: $scope.selectedEntity.enumFieldList
 };
$scope.enumFieldListGridOptions.onRegisterApi = function(gridApi){
$scope.enumFieldGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
enumFieldService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
enumFieldService.setSelectedEntity(response.data[0]);
});
$('#enumFieldTabs li:eq(0) a').tab('show');
}
else 
enumFieldService.setSelectedEntity(null);
enumFieldService.selectedEntity.show = row.isSelected;
});
  };
$scope.relationshipListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'relationshipId'},
{ name: 'priority'},
{ name: 'name'} 
]
,data: $scope.selectedEntity.relationshipList
 };
$scope.relationshipListGridOptions.onRegisterApi = function(gridApi){
$scope.relationshipGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
relationshipService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
relationshipService.setSelectedEntity(response.data[0]);
});
$('#relationshipTabs li:eq(0) a').tab('show');
}
else 
relationshipService.setSelectedEntity(null);
relationshipService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedField= function() {
entityService.selectedEntity.fieldList.push(entityService.selectedEntity.field);
}
$scope.downloadFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("field.xls",?) FROM ?',[mystyle,$scope.selectedEntity.fieldList]);
};
$scope.downloadEntityGroupList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entityGroup.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityGroupList]);
};
$scope.saveLinkedRestrictionEntity= function() {
entityService.selectedEntity.restrictionEntityList.push(entityService.selectedEntity.restrictionEntity);
}
$scope.downloadRestrictionEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionEntity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.restrictionEntityList]);
};
$scope.saveLinkedTab= function() {
entityService.selectedEntity.tabList.push(entityService.selectedEntity.tab);
}
$scope.downloadTabList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("tab.xls",?) FROM ?',[mystyle,$scope.selectedEntity.tabList]);
};
$scope.saveLinkedEnumField= function() {
entityService.selectedEntity.enumFieldList.push(entityService.selectedEntity.enumField);
}
$scope.downloadEnumFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumField.xls",?) FROM ?',[mystyle,$scope.selectedEntity.enumFieldList]);
};
$scope.saveLinkedRelationship= function() {
entityService.selectedEntity.relationshipList.push(entityService.selectedEntity.relationship);
}
$scope.downloadRelationshipList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("relationship.xls",?) FROM ?',[mystyle,$scope.selectedEntity.relationshipList]);
};
})
;