var enumEntityApp=angular.module("enumEntityApp",['ngFileUpload','ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])
.service("securityService",function($http)
{
this.restrictionList;
this.init= function() {
var promise= $http.get("../authentication/");
return promise; 
};
})
.run(function($rootScope,securityService,enumEntityService, securityService ,projectService,entityGroupService,enumValueService){
securityService.init().then(function successCallback(response) {
securityService.restrictionList=response.data;
$rootScope.restrictionList=response.data;
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
});
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
.controller("enumEntityController",function($scope,$http,enumEntityService, securityService ,projectService,entityGroupService,enumValueService)
{
//null
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
projectService.selectedEntity.show=false;entityGroupService.selectedEntity.show=false;enumValueService.selectedEntity.show=false;}
$scope.addNew= function()
{
enumEntityService.setSelectedEntity(null);
enumEntityService.setEntityList(null);
enumEntityService.selectedEntity.show=true;
projectService.selectedEntity.show=false;entityGroupService.selectedEntity.show=false;enumValueService.selectedEntity.show=false;$('#enumEntityTabs li:eq(0) a').tab('show');
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
enumEntityService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.update=function()
{
if (!$scope.enumEntityDetailForm.$valid) return; 
projectService.selectedEntity.show=false;entityGroupService.selectedEntity.show=false;enumValueService.selectedEntity.show=false;enumEntityService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.del=function()
{
enumEntityService.del().then(function successCallback(response) { 
$scope.search();
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
$scope.enumEntityGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'name'},
{ name: 'enumEntityId'},
{ name: 'project.projectId', displayName: 'project'} 
]
,data: enumEntityService.entityList
 };
$scope.enumEntityGridOptions.onRegisterApi = function(gridApi){
$scope.enumEntityGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
projectService.selectedEntity.show=false;entityGroupService.selectedEntity.show=false;enumValueService.selectedEntity.show=false;if (row.isSelected)
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
.controller("enumValueController",function($scope,$http,enumEntityService, securityService ,projectService,entityGroupService,enumValueService)
{
//enumEntity
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
enumEntityService.update().then(function successCallback(response) {
enumEntityService.setSelectedEntity(response);
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
enumValueService.selectedEntity.enumEntityList.push(enumEntityService.selectedEntity);
enumValueService.insert().then(function successCallBack(response) { 
enumEntityService.selectedEntity.enumValueList.push(response.data);
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

for (i=0; i<enumEntityService.selectedEntity.enumValueList.length; i++)

{

if (enumEntityService.selectedEntity.enumValueList[i].enumValueId==enumValueService.selectedEntity.enumValueId)

enumEntityService.selectedEntity.enumValueList.splice(i,1);

}

enumEntityService.selectedEntity.enumValueList.push(enumValueService.selectedEntity);

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
for (i=0; i<enumEntityService.selectedEntity.enumValueList.length; i++)
{
if (enumEntityService.selectedEntity.enumValueList[i].enumValueId==enumValueService.selectedEntity.enumValueId)
enumEntityService.selectedEntity.enumValueList.splice(i,1);
}
enumValueService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<enumEntityService.selectedEntity.enumValueList.length; i++)
{
if (enumEntityService.selectedEntity.enumValueList[i].enumValueId==enumValueService.selectedEntity.enumValueId)
enumEntityService.selectedEntity.enumValueList.splice(i,1);
}
$scope.updateParent();
enumValueService.del().then(function successCallback(response) { 
enumValueService.setSelectedEntity(null);
enumEntityService.initEnumValueList().then(function(response) {
enumEntityService.childrenList.enumValueList=response.data;
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
.controller("entityGroupController",function($scope,$http,enumEntityService, securityService ,projectService,entityGroupService,enumValueService)
{
//enumEntity
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
enumEntityService.update().then(function successCallback(response) {
enumEntityService.setSelectedEntity(response);
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
entityGroupService.selectedEntity.enumEntityList.push(enumEntityService.selectedEntity);
entityGroupService.insert().then(function successCallBack(response) { 
enumEntityService.selectedEntity.entityGroupList.push(response.data);
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

for (i=0; i<enumEntityService.selectedEntity.entityGroupList.length; i++)

{

if (enumEntityService.selectedEntity.entityGroupList[i].entityGroupId==entityGroupService.selectedEntity.entityGroupId)

enumEntityService.selectedEntity.entityGroupList.splice(i,1);

}

enumEntityService.selectedEntity.entityGroupList.push(entityGroupService.selectedEntity);

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
for (i=0; i<enumEntityService.selectedEntity.entityGroupList.length; i++)
{
if (enumEntityService.selectedEntity.entityGroupList[i].entityGroupId==entityGroupService.selectedEntity.entityGroupId)
enumEntityService.selectedEntity.entityGroupList.splice(i,1);
}
entityGroupService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<enumEntityService.selectedEntity.entityGroupList.length; i++)
{
if (enumEntityService.selectedEntity.entityGroupList[i].entityGroupId==entityGroupService.selectedEntity.entityGroupId)
enumEntityService.selectedEntity.entityGroupList.splice(i,1);
}
$scope.updateParent();
entityGroupService.del().then(function successCallback(response) { 
entityGroupService.setSelectedEntity(null);
enumEntityService.initEntityGroupList().then(function(response) {
enumEntityService.childrenList.entityGroupList=response.data;
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
.controller("projectController",function($scope,$http,enumEntityService, securityService ,projectService,entityGroupService,enumValueService)
{
//enumEntity
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
}
$scope.updateParent = function(toDo)
{
enumEntityService.update().then(function successCallback(response) {
enumEntityService.setSelectedEntity(response);
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
projectService.setSelectedEntity(null);
projectService.setEntityList(null);
projectService.selectedEntity.show=true;
$('#projectTabs li:eq(0) a').tab('show');
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
projectService.selectedEntity.show=false;
projectService.selectedEntity.enumEntity={};
projectService.selectedEntity.enumEntity.enumEntityId=enumEntityService.selectedEntity.enumEntityId;
projectService.insert().then(function successCallBack(response) { 
enumEntityService.selectedEntity.project=response.data;
enumEntityService.initProjectList().then(function(response) {
enumEntityService.childrenList.projectList=response.data;
});
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.update=function()
{
if (!$scope.projectDetailForm.$valid) return; 
projectService.selectedEntity.show=false;

enumEntityService.selectedEntity.project=projectService.selectedEntity;

projectService.update().then(function successCallback(response){
projectService.setSelectedEntity(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.remove= function()
{
projectService.selectedEntity.show=false;
enumEntityService.selectedEntity.project=null;
projectService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
enumEntityService.selectedEntity.project=null;
$scope.updateParent();
projectService.del().then(function successCallback(response) { 
projectService.setSelectedEntity(null);
enumEntityService.initProjectList().then(function(response) {
enumEntityService.childrenList.projectList=response.data;
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
;