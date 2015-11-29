var restrictionEntityApp=angular.module("restrictionEntityApp",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])
.service("securityService",function($http)
{
this.restrictionList;
this.init= function() {
var promise= $http.get("../authentication/");
return promise; 
};
})
.run(function($rootScope,securityService,restrictionEntityService, securityService ,roleService,entityService,userService,restrictionFieldService,restrictionEntityGroupService,fieldService,relationshipService,enumFieldService,tabService,entityGroupService){
securityService.init().then(function successCallback(response) {
securityService.restrictionList=response.data;
$rootScope.restrictionList=response.data;
console.log($rootScope.restrictionList);
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionEntityService.initRoleList().then(function successCallback(response) {
restrictionEntityService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
restrictionEntityService.initEntityList().then(function successCallback(response) {
restrictionEntityService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
});
})
.service("restrictionEntityService", function($http)
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
var promise= $http.post("../restrictionEntity/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../restrictionEntity/"+entity.restrictionEntityId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../restrictionEntity/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../restrictionEntity/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../restrictionEntity/"+this.selectedEntity.restrictionEntityId;
var promise= $http["delete"](url);
return promise; 
}
 this.initRoleList= function()
{
var promise= $http
.post("../role/search",
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
.controller("restrictionEntityController",function($scope,$http,restrictionEntityService, securityService ,roleService,entityService,userService,restrictionFieldService,restrictionEntityGroupService,fieldService,relationshipService,enumFieldService,tabService,entityGroupService)
{
//null
$scope.searchBean=restrictionService.searchBean;
$scope.entityList=restrictionService.entityList;
$scope.selectedEntity=restrictionService.selectedEntity;
$scope.childrenList=restrictionService.childrenList; 
$scope.reset = function()
{
restrictionService.resetSearchBean();
$scope.searchBean=restrictionEntityService.searchBean;restrictionService.setSelectedEntity(null);
restrictionService.selectedEntity.show=false;
restrictionService.setEntityList(null); 
roleService.selectedEntity.show=false;entityService.selectedEntity.show=false;userService.selectedEntity.show=false;restrictionFieldService.selectedEntity.show=false;restrictionEntityGroupService.selectedEntity.show=false;fieldService.selectedEntity.show=false;relationshipService.selectedEntity.show=false;enumFieldService.selectedEntity.show=false;tabService.selectedEntity.show=false;entityGroupService.selectedEntity.show=false;}
$scope.addNew= function()
{
restrictionService.setSelectedEntity(null);
restrictionService.setEntityList(null);
restrictionService.selectedEntity.show=true;
roleService.selectedEntity.show=false;entityService.selectedEntity.show=false;userService.selectedEntity.show=false;restrictionFieldService.selectedEntity.show=false;restrictionEntityGroupService.selectedEntity.show=false;fieldService.selectedEntity.show=false;relationshipService.selectedEntity.show=false;enumFieldService.selectedEntity.show=false;tabService.selectedEntity.show=false;entityGroupService.selectedEntity.show=false;$('#restrictionEntityTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
restrictionService.selectedEntity.show=false;
restrictionService.search().then(function successCallback(response) {
restrictionService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.restrictionEntityDetailForm.$valid) return; 
restrictionService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.restrictionEntityDetailForm.$valid) return; 
roleService.selectedEntity.show=false;entityService.selectedEntity.show=false;userService.selectedEntity.show=false;restrictionFieldService.selectedEntity.show=false;restrictionEntityGroupService.selectedEntity.show=false;fieldService.selectedEntity.show=false;relationshipService.selectedEntity.show=false;enumFieldService.selectedEntity.show=false;tabService.selectedEntity.show=false;entityGroupService.selectedEntity.show=false;restrictionService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.del=function()
{
restrictionService.del().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.roleGridApi!=undefined && $scope.roleGridApi!=null)
 $scope.roleGridApi.core.handleWindowResize(); 
if ($scope.entityGridApi!=undefined && $scope.entityGridApi!=null)
 $scope.entityGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showRoleDetail= function(index)
{
if (index!=null)
{
roleService.searchOne(restrictionEntityService.selectedEntity.roleList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.user==undefined || securityService.restrictionList.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (restrictionService.selectedEntity.role==null || restrictionEntityService.selectedEntity.role==undefined)
{
if (securityService.restrictionList.user==undefined || securityService.restrictionList.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
roleService.setSelectedEntity(null); 
roleService.selectedEntity.show=true; 
}
else
roleService.searchOne(restrictionEntityService.selectedEntity.role).then(
function successCallback(response) {
if (securityService.restrictionList.user==undefined || securityService.restrictionList.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#roleTabs li:eq(0) a').tab('show');
};
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(restrictionEntityService.selectedEntity.entityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (restrictionService.selectedEntity.entity==null || restrictionEntityService.selectedEntity.entity==undefined)
{
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
}
else
entityService.searchOne(restrictionEntityService.selectedEntity.entity).then(
function successCallback(response) {
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#entityTabs li:eq(0) a').tab('show');
};
$scope.restrictionEntityGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'restrictionEntityId'},
{ name: 'canCreate'},
{ name: 'canUpdate'},
{ name: 'canSearch'},
{ name: 'canDelete'},
{ name: 'role.roleId', displayName: 'role'},
{ name: 'entity.entityId', displayName: 'entity'} 
]
,data: restrictionService.entityList
 };
$scope.restrictionEntityGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionEntityGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
roleService.selectedEntity.show=false;entityService.selectedEntity.show=false;userService.selectedEntity.show=false;restrictionFieldService.selectedEntity.show=false;restrictionEntityGroupService.selectedEntity.show=false;fieldService.selectedEntity.show=false;relationshipService.selectedEntity.show=false;enumFieldService.selectedEntity.show=false;tabService.selectedEntity.show=false;entityGroupService.selectedEntity.show=false;if (row.isSelected)
{
restrictionService.setSelectedEntity(row.entity);
$('#restrictionEntityTabs li:eq(0) a').tab('show');
}
else 
restrictionService.setSelectedEntity(null);
restrictionService.selectedEntity.show = row.isSelected;
});
  };
$scope.roleListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'roleId'},
{ name: 'role'} 
]
,data: $scope.selectedEntity.roleList
 };
$scope.roleListGridOptions.onRegisterApi = function(gridApi){
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
$scope.entityListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'entityId'},
{ name: 'name'} 
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
alasql('SELECT * INTO XLSXML("restrictionEntity.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadRoleList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,$scope.selectedEntity.roleList]);
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityList]);
};
})
.service("roleService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,userList: [],restrictionEntityList: [],restrictionFieldList: [],restrictionEntityGroupList: []};
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
var promise= $http.post("../role/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../role/"+entity.roleId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../role/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../role/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../role/"+this.selectedEntity.roleId;
var promise= $http["delete"](url);
return promise; 
}
 this.initUserList= function()
{
var promise= $http
.post("../user/search",
{});
return promise;
};
 this.initRestrictionEntityList= function()
{
var promise= $http
.post("../restriction/search",
{});
return promise;
};
 this.initRestrictionFieldList= function()
{
var promise= $http
.post("../restriction/search",
{});
return promise;
};
 this.initRestrictionEntityGroupList= function()
{
var promise= $http
.post("../restriction/search",
{});
return promise;
};
})
.controller("roleController",function($scope,$http,roleService, securityService ,userService,restrictionEntityService,restrictionFieldService,restrictionEntityGroupService,entityService,fieldService,entityGroupService)
{
//restrictionEntity
$scope.searchBean=roleService.searchBean;
$scope.entityList=roleService.entityList;
$scope.selectedEntity=roleService.selectedEntity;
$scope.childrenList=roleService.childrenList; 
$scope.reset = function()
{
roleService.resetSearchBean();
$scope.searchBean=roleService.searchBean;roleService.setSelectedEntity(null);
roleService.selectedEntity.show=false;
roleService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
restrictionService.update().then(function successCallback(response) {
restrictionService.setSelectedEntity(response);
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
roleService.setSelectedEntity(null);
roleService.setEntityList(null);
roleService.selectedEntity.show=true;
$('#roleTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
roleService.selectedEntity.show=false;
roleService.searchBean.userList=[];
roleService.searchBean.userList.push(roleService.searchBean.user);
delete roleService.searchBean.user; 
roleService.searchBean.restrictionEntityList=[];
roleService.searchBean.restrictionEntityList.push(roleService.searchBean.restrictionEntity);
delete roleService.searchBean.restrictionEntity; 
roleService.searchBean.restrictionFieldList=[];
roleService.searchBean.restrictionFieldList.push(roleService.searchBean.restrictionField);
delete roleService.searchBean.restrictionField; 
roleService.searchBean.restrictionEntityGroupList=[];
roleService.searchBean.restrictionEntityGroupList.push(roleService.searchBean.restrictionEntityGroup);
delete roleService.searchBean.restrictionEntityGroup; 
roleService.search().then(function successCallback(response) {
roleService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.roleDetailForm.$valid) return; 
roleService.selectedEntity.show=false;
roleService.selectedEntity.restrictionEntityList.push(restrictionEntityService.selectedEntity);
roleService.insert().then(function successCallBack(response) { 
restrictionService.selectedEntity.roleList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.roleDetailForm.$valid) return; 
roleService.selectedEntity.show=false;

for (i=0; i<restrictionService.selectedEntity.roleList.length; i++)

{

if (restrictionEntityService.selectedEntity.roleList[i].roleId==roleService.selectedEntity.roleId)

restrictionEntityService.selectedEntity.roleList.splice(i,1);

}

restrictionEntityService.selectedEntity.roleList.push(roleService.selectedEntity);

roleService.update().then(function successCallback(response){
roleService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
roleService.selectedEntity.show=false;
for (i=0; i<restrictionEntityService.selectedEntity.roleList.length; i++)
{
if (restrictionEntityService.selectedEntity.roleList[i].roleId==roleService.selectedEntity.roleId)
restrictionEntityService.selectedEntity.roleList.splice(i,1);
}
roleService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<restrictionEntityService.selectedEntity.roleList.length; i++)
{
if (restrictionEntityService.selectedEntity.roleList[i].roleId==roleService.selectedEntity.roleId)
restrictionEntityService.selectedEntity.roleList.splice(i,1);
}
$scope.updateParent();
roleService.del().then(function successCallback(response) { 
roleService.setSelectedEntity(null);
restrictionEntityService.initRoleList().then(function(response) {
restrictionEntityService.childrenList.roleList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.userGridApi!=undefined && $scope.userGridApi!=null)
 $scope.userGridApi.core.handleWindowResize(); 
if ($scope.restrictionEntityGridApi!=undefined && $scope.restrictionEntityGridApi!=null)
 $scope.restrictionEntityGridApi.core.handleWindowResize(); 
if ($scope.restrictionFieldGridApi!=undefined && $scope.restrictionFieldGridApi!=null)
 $scope.restrictionFieldGridApi.core.handleWindowResize(); 
if ($scope.restrictionEntityGroupGridApi!=undefined && $scope.restrictionEntityGroupGridApi!=null)
 $scope.restrictionEntityGroupGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showUserDetail= function(index)
{
if (index!=null)
{
userService.searchOne(roleService.selectedEntity.userList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
userService.initRoleList().then(function successCallback(response) {
userService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
userService.setSelectedEntity(response.data[0]);
userService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (roleService.selectedEntity.user==null || roleService.selectedEntity.user==undefined)
{
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
userService.initRoleList().then(function successCallback(response) {
userService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
userService.setSelectedEntity(null); 
userService.selectedEntity.show=true; 
}
else
userService.searchOne(roleService.selectedEntity.user).then(
function successCallback(response) {
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
userService.initRoleList().then(function successCallback(response) {
userService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
userService.setSelectedEntity(response.data[0]);
userService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#userTabs li:eq(0) a').tab('show');
};
$scope.showRestrictionEntityDetail= function(index)
{
if (index!=null)
{
restrictionEntityService.searchOne(roleService.selectedEntity.restrictionEntityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionEntityService.initRoleList().then(function successCallback(response) {
restrictionEntityService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
restrictionEntityService.initEntityList().then(function successCallback(response) {
restrictionEntityService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionService.setSelectedEntity(response.data[0]);
restrictionService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (roleService.selectedEntity.restrictionEntity==null || roleService.selectedEntity.restrictionEntity==undefined)
{
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionEntityService.initRoleList().then(function successCallback(response) {
restrictionEntityService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
restrictionEntityService.initEntityList().then(function successCallback(response) {
restrictionEntityService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionService.setSelectedEntity(null); 
restrictionService.selectedEntity.show=true; 
}
else
restrictionService.searchOne(roleService.selectedEntity.restrictionEntity).then(
function successCallback(response) {
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionEntityService.initRoleList().then(function successCallback(response) {
restrictionEntityService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
restrictionEntityService.initEntityList().then(function successCallback(response) {
restrictionEntityService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionService.setSelectedEntity(response.data[0]);
restrictionService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#restrictionEntityTabs li:eq(0) a').tab('show');
};
$scope.showRestrictionFieldDetail= function(index)
{
if (index!=null)
{
restrictionFieldService.searchOne(roleService.selectedEntity.restrictionFieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
restrictionFieldService.initFieldList().then(function successCallback(response) {
restrictionFieldService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionFieldService.initRoleList().then(function successCallback(response) {
restrictionFieldService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionService.setSelectedEntity(response.data[0]);
restrictionService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (roleService.selectedEntity.restrictionField==null || roleService.selectedEntity.restrictionField==undefined)
{
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
restrictionFieldService.initFieldList().then(function successCallback(response) {
restrictionFieldService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionFieldService.initRoleList().then(function successCallback(response) {
restrictionFieldService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionService.setSelectedEntity(null); 
restrictionService.selectedEntity.show=true; 
}
else
restrictionService.searchOne(roleService.selectedEntity.restrictionField).then(
function successCallback(response) {
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
restrictionFieldService.initFieldList().then(function successCallback(response) {
restrictionFieldService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionFieldService.initRoleList().then(function successCallback(response) {
restrictionFieldService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionService.setSelectedEntity(response.data[0]);
restrictionService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#restrictionFieldTabs li:eq(0) a').tab('show');
};
$scope.showRestrictionEntityGroupDetail= function(index)
{
if (index!=null)
{
restrictionEntityGroupService.searchOne(roleService.selectedEntity.restrictionEntityGroupList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
restrictionEntityGroupService.initEntityGroupList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionEntityGroupService.initRoleList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionService.setSelectedEntity(response.data[0]);
restrictionService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (roleService.selectedEntity.restrictionEntityGroup==null || roleService.selectedEntity.restrictionEntityGroup==undefined)
{
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
restrictionEntityGroupService.initEntityGroupList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionEntityGroupService.initRoleList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionService.setSelectedEntity(null); 
restrictionService.selectedEntity.show=true; 
}
else
restrictionService.searchOne(roleService.selectedEntity.restrictionEntityGroup).then(
function successCallback(response) {
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
restrictionEntityGroupService.initEntityGroupList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionEntityGroupService.initRoleList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionService.setSelectedEntity(response.data[0]);
restrictionService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
};
$scope.userListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'userId'},
{ name: 'username'},
{ name: 'password'},
{ name: 'enabled'} 
]
,data: $scope.selectedEntity.userList
 };
$scope.userListGridOptions.onRegisterApi = function(gridApi){
$scope.userGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
userService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
userService.setSelectedEntity(response.data[0]);
});
$('#userTabs li:eq(0) a').tab('show');
}
else 
userService.setSelectedEntity(null);
userService.selectedEntity.show = row.isSelected;
});
  };
$scope.restrictionEntityListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'restrictionEntityId'},
{ name: 'canCreate'},
{ name: 'canUpdate'},
{ name: 'canSearch'},
{ name: 'canDelete'} 
]
,data: $scope.selectedEntity.restrictionEntityList
 };
$scope.restrictionEntityListGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionEntityGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
restrictionService.setSelectedEntity(response.data[0]);
});
$('#restrictionEntityTabs li:eq(0) a').tab('show');
}
else 
restrictionService.setSelectedEntity(null);
restrictionService.selectedEntity.show = row.isSelected;
});
  };
$scope.restrictionFieldListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'restrictionFieldId'} 
]
,data: $scope.selectedEntity.restrictionFieldList
 };
$scope.restrictionFieldListGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionFieldGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
restrictionService.setSelectedEntity(response.data[0]);
});
$('#restrictionFieldTabs li:eq(0) a').tab('show');
}
else 
restrictionService.setSelectedEntity(null);
restrictionService.selectedEntity.show = row.isSelected;
});
  };
$scope.restrictionEntityGroupListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'restrictionEntityGroupId'},
{ name: 'canCreate'},
{ name: 'canUpdate'},
{ name: 'canSearch'},
{ name: 'canDelete'} 
]
,data: $scope.selectedEntity.restrictionEntityGroupList
 };
$scope.restrictionEntityGroupListGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionEntityGroupGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
restrictionService.setSelectedEntity(response.data[0]);
});
$('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
}
else 
restrictionService.setSelectedEntity(null);
restrictionService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedUser= function() {
roleService.selectedEntity.userList.push(roleService.selectedEntity.user);
}
$scope.downloadUserList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("user.xls",?) FROM ?',[mystyle,$scope.selectedEntity.userList]);
};
$scope.saveLinkedRestrictionEntity= function() {
roleService.selectedEntity.restrictionEntityList.push(roleService.selectedEntity.restrictionEntity);
}
$scope.downloadRestrictionEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionEntity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.restrictionEntityList]);
};
$scope.saveLinkedRestrictionField= function() {
roleService.selectedEntity.restrictionFieldList.push(roleService.selectedEntity.restrictionField);
}
$scope.downloadRestrictionFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionField.xls",?) FROM ?',[mystyle,$scope.selectedEntity.restrictionFieldList]);
};
$scope.saveLinkedRestrictionEntityGroup= function() {
roleService.selectedEntity.restrictionEntityGroupList.push(roleService.selectedEntity.restrictionEntityGroup);
}
$scope.downloadRestrictionEntityGroupList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionEntityGroup.xls",?) FROM ?',[mystyle,$scope.selectedEntity.restrictionEntityGroupList]);
};
})
.service("entityService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,fieldList: [],relationshipList: [],enumFieldList: [],tabList: [],restrictionEntityList: []};
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
 this.initFieldList= function()
{
var promise= $http
.post("../field/search",
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
 this.initEnumFieldList= function()
{
var promise= $http
.post("../enum/search",
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
 this.initRestrictionEntityList= function()
{
var promise= $http
.post("../restriction/search",
{});
return promise;
};
 this.initEntityGroupList= function()
{
var promise= $http
.post("../entity/search",
{});
return promise;
};
})
.controller("entityController",function($scope,$http,entityService, securityService ,fieldService,relationshipService,enumFieldService,tabService,restrictionEntityService,entityGroupService,annotationService,restrictionFieldService,tabService,annotationService,tabService,enumValueService,annotationService,tabService,roleService,restrictionEntityGroupService,projectService)
{
//restrictionEntity
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
restrictionService.update().then(function successCallback(response) {
restrictionService.setSelectedEntity(response);
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
$('#entityTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
entityService.selectedEntity.show=false;
entityService.searchBean.fieldList=[];
entityService.searchBean.fieldList.push(entityService.searchBean.field);
delete entityService.searchBean.field; 
entityService.searchBean.relationshipList=[];
entityService.searchBean.relationshipList.push(entityService.searchBean.relationship);
delete entityService.searchBean.relationship; 
entityService.searchBean.enumFieldList=[];
entityService.searchBean.enumFieldList.push(entityService.searchBean.enumField);
delete entityService.searchBean.enumField; 
entityService.searchBean.tabList=[];
entityService.searchBean.tabList.push(entityService.searchBean.tab);
delete entityService.searchBean.tab; 
entityService.searchBean.restrictionEntityList=[];
entityService.searchBean.restrictionEntityList.push(entityService.searchBean.restrictionEntity);
delete entityService.searchBean.restrictionEntity; 
entityService.search().then(function successCallback(response) {
entityService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.entityDetailForm.$valid) return; 
entityService.selectedEntity.show=false;
entityService.selectedEntity.restrictionEntityList.push(restrictionEntityService.selectedEntity);
entityService.insert().then(function successCallBack(response) { 
restrictionService.selectedEntity.entityList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.entityDetailForm.$valid) return; 
entityService.selectedEntity.show=false;

for (i=0; i<restrictionService.selectedEntity.entityList.length; i++)

{

if (restrictionEntityService.selectedEntity.entityList[i].entityId==entityService.selectedEntity.entityId)

restrictionEntityService.selectedEntity.entityList.splice(i,1);

}

restrictionEntityService.selectedEntity.entityList.push(entityService.selectedEntity);

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
for (i=0; i<restrictionEntityService.selectedEntity.entityList.length; i++)
{
if (restrictionEntityService.selectedEntity.entityList[i].entityId==entityService.selectedEntity.entityId)
restrictionEntityService.selectedEntity.entityList.splice(i,1);
}
entityService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<restrictionEntityService.selectedEntity.entityList.length; i++)
{
if (restrictionEntityService.selectedEntity.entityList[i].entityId==entityService.selectedEntity.entityId)
restrictionEntityService.selectedEntity.entityList.splice(i,1);
}
$scope.updateParent();
entityService.del().then(function successCallback(response) { 
entityService.setSelectedEntity(null);
restrictionEntityService.initEntityList().then(function(response) {
restrictionEntityService.childrenList.entityList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.fieldGridApi!=undefined && $scope.fieldGridApi!=null)
 $scope.fieldGridApi.core.handleWindowResize(); 
if ($scope.relationshipGridApi!=undefined && $scope.relationshipGridApi!=null)
 $scope.relationshipGridApi.core.handleWindowResize(); 
if ($scope.enumFieldGridApi!=undefined && $scope.enumFieldGridApi!=null)
 $scope.enumFieldGridApi.core.handleWindowResize(); 
if ($scope.tabGridApi!=undefined && $scope.tabGridApi!=null)
 $scope.tabGridApi.core.handleWindowResize(); 
if ($scope.restrictionEntityGridApi!=undefined && $scope.restrictionEntityGridApi!=null)
 $scope.restrictionEntityGridApi.core.handleWindowResize(); 
if ($scope.entityGroupGridApi!=undefined && $scope.entityGroupGridApi!=null)
 $scope.entityGroupGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showFieldDetail= function(index)
{
if (index!=null)
{
fieldService.searchOne(entityService.selectedEntity.fieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
fieldService.initEntityList().then(function successCallback(response) {
fieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
fieldService.initAnnotationList().then(function successCallback(response) {
fieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
fieldService.initRestrictionFieldList().then(function successCallback(response) {
fieldService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
fieldService.initTabList().then(function successCallback(response) {
fieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG",];
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.field==null || entityService.selectedEntity.field==undefined)
{
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
fieldService.initEntityList().then(function successCallback(response) {
fieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
fieldService.initAnnotationList().then(function successCallback(response) {
fieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
fieldService.initRestrictionFieldList().then(function successCallback(response) {
fieldService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
fieldService.initTabList().then(function successCallback(response) {
fieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG",];
fieldService.setSelectedEntity(null); 
fieldService.selectedEntity.show=true; 
}
else
fieldService.searchOne(entityService.selectedEntity.field).then(
function successCallback(response) {
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
fieldService.initEntityList().then(function successCallback(response) {
fieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
fieldService.initAnnotationList().then(function successCallback(response) {
fieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
fieldService.initRestrictionFieldList().then(function successCallback(response) {
fieldService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
fieldService.initTabList().then(function successCallback(response) {
fieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG",];
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#fieldTabs li:eq(0) a').tab('show');
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
alert("error");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
relationshipService.initEntityList().then(function successCallback(response) {
relationshipService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
relationshipService.initAnnotationList().then(function successCallback(response) {
relationshipService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
relationshipService.initTabList().then(function successCallback(response) {
relationshipService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
relationshipService.childrenList.relationshipTypeList=["ONE_TO_ONE","ONE_TO_MANY","MANY_TO_ONE","MANY_TO_MANY","MANY_TO_MANY_BACK",];
relationshipService.setSelectedEntity(response.data[0]);
relationshipService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
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
alert("error");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
relationshipService.initEntityList().then(function successCallback(response) {
relationshipService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
relationshipService.initAnnotationList().then(function successCallback(response) {
relationshipService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
relationshipService.initTabList().then(function successCallback(response) {
relationshipService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
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
alert("error");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
relationshipService.initEntityList().then(function successCallback(response) {
relationshipService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
relationshipService.initAnnotationList().then(function successCallback(response) {
relationshipService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
relationshipService.initTabList().then(function successCallback(response) {
relationshipService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
relationshipService.childrenList.relationshipTypeList=["ONE_TO_ONE","ONE_TO_MANY","MANY_TO_ONE","MANY_TO_MANY","MANY_TO_MANY_BACK",];
relationshipService.setSelectedEntity(response.data[0]);
relationshipService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#relationshipTabs li:eq(0) a').tab('show');
};
$scope.showEnumFieldDetail= function(index)
{
if (index!=null)
{
enumFieldService.searchOne(entityService.selectedEntity.enumFieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.enumValue==undefined || securityService.restrictionList.enumValue.canSearch)
enumFieldService.initEnumValueList().then(function successCallback(response) {
enumFieldService.childrenList.enumValueList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
enumFieldService.initEntityList().then(function successCallback(response) {
enumFieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
enumFieldService.initAnnotationList().then(function successCallback(response) {
enumFieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
enumFieldService.initTabList().then(function successCallback(response) {
enumFieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
enumService.setSelectedEntity(response.data[0]);
enumService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.enumField==null || entityService.selectedEntity.enumField==undefined)
{
if (securityService.restrictionList.enumValue==undefined || securityService.restrictionList.enumValue.canSearch)
enumFieldService.initEnumValueList().then(function successCallback(response) {
enumFieldService.childrenList.enumValueList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
enumFieldService.initEntityList().then(function successCallback(response) {
enumFieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
enumFieldService.initAnnotationList().then(function successCallback(response) {
enumFieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
enumFieldService.initTabList().then(function successCallback(response) {
enumFieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
enumService.setSelectedEntity(null); 
enumService.selectedEntity.show=true; 
}
else
enumService.searchOne(entityService.selectedEntity.enumField).then(
function successCallback(response) {
if (securityService.restrictionList.enumValue==undefined || securityService.restrictionList.enumValue.canSearch)
enumFieldService.initEnumValueList().then(function successCallback(response) {
enumFieldService.childrenList.enumValueList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
enumFieldService.initEntityList().then(function successCallback(response) {
enumFieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
enumFieldService.initAnnotationList().then(function successCallback(response) {
enumFieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
enumFieldService.initTabList().then(function successCallback(response) {
enumFieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
enumService.setSelectedEntity(response.data[0]);
enumService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#enumFieldTabs li:eq(0) a').tab('show');
};
$scope.showTabDetail= function(index)
{
if (index!=null)
{
tabService.searchOne(entityService.selectedEntity.tabList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.tab==null || entityService.selectedEntity.tab==undefined)
{
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
tabService.setSelectedEntity(null); 
tabService.selectedEntity.show=true; 
}
else
tabService.searchOne(entityService.selectedEntity.tab).then(
function successCallback(response) {
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#tabTabs li:eq(0) a').tab('show');
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
alert("error");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
restrictionEntityService.initEntityList().then(function successCallback(response) {
restrictionEntityService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionService.setSelectedEntity(response.data[0]);
restrictionService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
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
alert("error");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
restrictionEntityService.initEntityList().then(function successCallback(response) {
restrictionEntityService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionService.setSelectedEntity(null); 
restrictionService.selectedEntity.show=true; 
}
else
restrictionService.searchOne(entityService.selectedEntity.restrictionEntity).then(
function successCallback(response) {
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionEntityService.initRoleList().then(function successCallback(response) {
restrictionEntityService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
restrictionEntityService.initEntityList().then(function successCallback(response) {
restrictionEntityService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionService.setSelectedEntity(response.data[0]);
restrictionService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#restrictionEntityTabs li:eq(0) a').tab('show');
};
$scope.showEntityGroupDetail= function(index)
{
if (index!=null)
{
entityGroupService.searchOne(entityService.selectedEntity.entityGroupList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.entityGroup==null || entityService.selectedEntity.entityGroup==undefined)
{
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
}
else
entityService.searchOne(entityService.selectedEntity.entityGroup).then(
function successCallback(response) {
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#entityGroupTabs li:eq(0) a').tab('show');
};
$scope.fieldListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'fieldId'},
{ name: 'name'} 
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
$scope.relationshipListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'relationshipId'},
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
$scope.enumFieldListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'enumFieldId'},
{ name: 'name'} 
]
,data: $scope.selectedEntity.enumFieldList
 };
$scope.enumFieldListGridOptions.onRegisterApi = function(gridApi){
$scope.enumFieldGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
enumService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
enumService.setSelectedEntity(response.data[0]);
});
$('#enumFieldTabs li:eq(0) a').tab('show');
}
else 
enumService.setSelectedEntity(null);
enumService.selectedEntity.show = row.isSelected;
});
  };
$scope.tabListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
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
$scope.restrictionEntityListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'restrictionEntityId'},
{ name: 'canCreate'},
{ name: 'canUpdate'},
{ name: 'canSearch'},
{ name: 'canDelete'} 
]
,data: $scope.selectedEntity.restrictionEntityList
 };
$scope.restrictionEntityListGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionEntityGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
restrictionService.setSelectedEntity(response.data[0]);
});
$('#restrictionEntityTabs li:eq(0) a').tab('show');
}
else 
restrictionService.setSelectedEntity(null);
restrictionService.selectedEntity.show = row.isSelected;
});
  };
$scope.entityGroupListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'entityId'},
{ name: 'entityGroupId'},
{ name: 'name'} 
]
,data: $scope.selectedEntity.entityGroupList
 };
$scope.entityGroupListGridOptions.onRegisterApi = function(gridApi){
$scope.entityGroupGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
entityService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
entityService.setSelectedEntity(response.data[0]);
});
$('#entityGroupTabs li:eq(0) a').tab('show');
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
$scope.downloadEntityGroupList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entityGroup.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityGroupList]);
};
})
.service("userService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,roleList: []};
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
var promise= $http.post("../user/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../user/"+entity.userId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../user/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../user/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../user/"+this.selectedEntity.userId;
var promise= $http["delete"](url);
return promise; 
}
 this.initRoleList= function()
{
var promise= $http
.post("../role/search",
{});
return promise;
};
})
.controller("userController",function($scope,$http,userService, securityService ,roleService,restrictionEntityService,restrictionFieldService,restrictionEntityGroupService)
{
//restrictionEntity
$scope.searchBean=userService.searchBean;
$scope.entityList=userService.entityList;
$scope.selectedEntity=userService.selectedEntity;
$scope.childrenList=userService.childrenList; 
$scope.reset = function()
{
userService.resetSearchBean();
$scope.searchBean=userService.searchBean;userService.setSelectedEntity(null);
userService.selectedEntity.show=false;
userService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
restrictionService.update().then(function successCallback(response) {
restrictionService.setSelectedEntity(response);
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
userService.setSelectedEntity(null);
userService.setEntityList(null);
userService.selectedEntity.show=true;
$('#userTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
userService.selectedEntity.show=false;
userService.searchBean.roleList=[];
userService.searchBean.roleList.push(userService.searchBean.role);
delete userService.searchBean.role; 
userService.search().then(function successCallback(response) {
userService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.userDetailForm.$valid) return; 
userService.selectedEntity.show=false;
userService.selectedEntity.restrictionEntityList.push(restrictionEntityService.selectedEntity);
userService.insert().then(function successCallBack(response) { 
restrictionService.selectedEntity.userList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.userDetailForm.$valid) return; 
userService.selectedEntity.show=false;

for (i=0; i<restrictionService.selectedEntity.userList.length; i++)

{

if (restrictionEntityService.selectedEntity.userList[i].userId==userService.selectedEntity.userId)

restrictionEntityService.selectedEntity.userList.splice(i,1);

}

restrictionEntityService.selectedEntity.userList.push(userService.selectedEntity);

userService.update().then(function successCallback(response){
userService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
userService.selectedEntity.show=false;
for (i=0; i<restrictionEntityService.selectedEntity.userList.length; i++)
{
if (restrictionEntityService.selectedEntity.userList[i].userId==userService.selectedEntity.userId)
restrictionEntityService.selectedEntity.userList.splice(i,1);
}
userService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<restrictionEntityService.selectedEntity.userList.length; i++)
{
if (restrictionEntityService.selectedEntity.userList[i].userId==userService.selectedEntity.userId)
restrictionEntityService.selectedEntity.userList.splice(i,1);
}
$scope.updateParent();
userService.del().then(function successCallback(response) { 
userService.setSelectedEntity(null);
restrictionEntityService.initUserList().then(function(response) {
restrictionEntityService.childrenList.userList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.roleGridApi!=undefined && $scope.roleGridApi!=null)
 $scope.roleGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showRoleDetail= function(index)
{
if (index!=null)
{
roleService.searchOne(userService.selectedEntity.roleList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.user==undefined || securityService.restrictionList.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (userService.selectedEntity.role==null || userService.selectedEntity.role==undefined)
{
if (securityService.restrictionList.user==undefined || securityService.restrictionList.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
roleService.setSelectedEntity(null); 
roleService.selectedEntity.show=true; 
}
else
roleService.searchOne(userService.selectedEntity.role).then(
function successCallback(response) {
if (securityService.restrictionList.user==undefined || securityService.restrictionList.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#roleTabs li:eq(0) a').tab('show');
};
$scope.roleListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'roleId'},
{ name: 'role'} 
]
,data: $scope.selectedEntity.roleList
 };
$scope.roleListGridOptions.onRegisterApi = function(gridApi){
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
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("user.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedRole= function() {
userService.selectedEntity.roleList.push(userService.selectedEntity.role);
}
$scope.downloadRoleList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,$scope.selectedEntity.roleList]);
};
})
.service("restrictionFieldService", function($http)
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
var promise= $http.post("../restrictionField/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../restrictionField/"+entity.restrictionFieldId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../restrictionField/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../restrictionField/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../restrictionField/"+this.selectedEntity.restrictionFieldId;
var promise= $http["delete"](url);
return promise; 
}
 this.initFieldList= function()
{
var promise= $http
.post("../field/search",
{});
return promise;
};
 this.initRoleList= function()
{
var promise= $http
.post("../role/search",
{});
return promise;
};
})
.controller("restrictionFieldController",function($scope,$http,restrictionFieldService, securityService ,fieldService,roleService,entityService,annotationService,tabService,userService,restrictionEntityService,restrictionEntityGroupService)
{
//restrictionEntity
$scope.searchBean=restrictionService.searchBean;
$scope.entityList=restrictionService.entityList;
$scope.selectedEntity=restrictionService.selectedEntity;
$scope.childrenList=restrictionService.childrenList; 
$scope.reset = function()
{
restrictionService.resetSearchBean();
$scope.searchBean=restrictionFieldService.searchBean;restrictionService.setSelectedEntity(null);
restrictionService.selectedEntity.show=false;
restrictionService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
restrictionService.update().then(function successCallback(response) {
restrictionService.setSelectedEntity(response);
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
restrictionService.setSelectedEntity(null);
restrictionService.setEntityList(null);
restrictionService.selectedEntity.show=true;
$('#restrictionFieldTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
restrictionService.selectedEntity.show=false;
restrictionService.search().then(function successCallback(response) {
restrictionService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.restrictionFieldDetailForm.$valid) return; 
restrictionService.selectedEntity.show=false;
restrictionService.selectedEntity.restrictionEntityList.push(restrictionEntityService.selectedEntity);
restrictionService.insert().then(function successCallBack(response) { 
restrictionService.selectedEntity.restrictionFieldList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.restrictionFieldDetailForm.$valid) return; 
restrictionService.selectedEntity.show=false;

for (i=0; i<restrictionService.selectedEntity.restrictionFieldList.length; i++)

{

if (restrictionEntityService.selectedEntity.restrictionFieldList[i].restrictionFieldId==restrictionFieldService.selectedEntity.restrictionFieldId)

restrictionEntityService.selectedEntity.restrictionFieldList.splice(i,1);

}

restrictionEntityService.selectedEntity.restrictionFieldList.push(restrictionFieldService.selectedEntity);

restrictionService.update().then(function successCallback(response){
restrictionService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
restrictionService.selectedEntity.show=false;
for (i=0; i<restrictionEntityService.selectedEntity.restrictionFieldList.length; i++)
{
if (restrictionEntityService.selectedEntity.restrictionFieldList[i].restrictionFieldId==restrictionFieldService.selectedEntity.restrictionFieldId)
restrictionEntityService.selectedEntity.restrictionFieldList.splice(i,1);
}
restrictionService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<restrictionEntityService.selectedEntity.restrictionFieldList.length; i++)
{
if (restrictionEntityService.selectedEntity.restrictionFieldList[i].restrictionFieldId==restrictionFieldService.selectedEntity.restrictionFieldId)
restrictionEntityService.selectedEntity.restrictionFieldList.splice(i,1);
}
$scope.updateParent();
restrictionService.del().then(function successCallback(response) { 
restrictionService.setSelectedEntity(null);
restrictionEntityService.initRestrictionFieldList().then(function(response) {
restrictionEntityService.childrenList.restrictionFieldList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.fieldGridApi!=undefined && $scope.fieldGridApi!=null)
 $scope.fieldGridApi.core.handleWindowResize(); 
if ($scope.roleGridApi!=undefined && $scope.roleGridApi!=null)
 $scope.roleGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showFieldDetail= function(index)
{
if (index!=null)
{
fieldService.searchOne(restrictionFieldService.selectedEntity.fieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
fieldService.initEntityList().then(function successCallback(response) {
fieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
fieldService.initAnnotationList().then(function successCallback(response) {
fieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
fieldService.initRestrictionFieldList().then(function successCallback(response) {
fieldService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
fieldService.initTabList().then(function successCallback(response) {
fieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG",];
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (restrictionService.selectedEntity.field==null || restrictionFieldService.selectedEntity.field==undefined)
{
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
fieldService.initEntityList().then(function successCallback(response) {
fieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
fieldService.initAnnotationList().then(function successCallback(response) {
fieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
fieldService.initRestrictionFieldList().then(function successCallback(response) {
fieldService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
fieldService.initTabList().then(function successCallback(response) {
fieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG",];
fieldService.setSelectedEntity(null); 
fieldService.selectedEntity.show=true; 
}
else
fieldService.searchOne(restrictionFieldService.selectedEntity.field).then(
function successCallback(response) {
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
fieldService.initEntityList().then(function successCallback(response) {
fieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
fieldService.initAnnotationList().then(function successCallback(response) {
fieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
fieldService.initRestrictionFieldList().then(function successCallback(response) {
fieldService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
fieldService.initTabList().then(function successCallback(response) {
fieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG",];
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#fieldTabs li:eq(0) a').tab('show');
};
$scope.showRoleDetail= function(index)
{
if (index!=null)
{
roleService.searchOne(restrictionFieldService.selectedEntity.roleList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.user==undefined || securityService.restrictionList.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (restrictionService.selectedEntity.role==null || restrictionFieldService.selectedEntity.role==undefined)
{
if (securityService.restrictionList.user==undefined || securityService.restrictionList.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
roleService.setSelectedEntity(null); 
roleService.selectedEntity.show=true; 
}
else
roleService.searchOne(restrictionFieldService.selectedEntity.role).then(
function successCallback(response) {
if (securityService.restrictionList.user==undefined || securityService.restrictionList.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#roleTabs li:eq(0) a').tab('show');
};
$scope.fieldListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'fieldId'},
{ name: 'name'} 
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
$scope.roleListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'roleId'},
{ name: 'role'} 
]
,data: $scope.selectedEntity.roleList
 };
$scope.roleListGridOptions.onRegisterApi = function(gridApi){
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
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionField.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("field.xls",?) FROM ?',[mystyle,$scope.selectedEntity.fieldList]);
};
$scope.downloadRoleList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,$scope.selectedEntity.roleList]);
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
 this.initEntityGroupList= function()
{
var promise= $http
.post("../entity/search",
{});
return promise;
};
 this.initRoleList= function()
{
var promise= $http
.post("../role/search",
{});
return promise;
};
})
.controller("restrictionEntityGroupController",function($scope,$http,restrictionEntityGroupService, securityService ,entityGroupService,roleService,entityService,projectService,userService,restrictionEntityService,restrictionFieldService)
{
//restrictionEntity
$scope.searchBean=restrictionService.searchBean;
$scope.entityList=restrictionService.entityList;
$scope.selectedEntity=restrictionService.selectedEntity;
$scope.childrenList=restrictionService.childrenList; 
$scope.reset = function()
{
restrictionService.resetSearchBean();
$scope.searchBean=restrictionEntityGroupService.searchBean;restrictionService.setSelectedEntity(null);
restrictionService.selectedEntity.show=false;
restrictionService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
restrictionService.update().then(function successCallback(response) {
restrictionService.setSelectedEntity(response);
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
restrictionService.setSelectedEntity(null);
restrictionService.setEntityList(null);
restrictionService.selectedEntity.show=true;
$('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
restrictionService.selectedEntity.show=false;
restrictionService.search().then(function successCallback(response) {
restrictionService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.restrictionEntityGroupDetailForm.$valid) return; 
restrictionService.selectedEntity.show=false;
restrictionService.selectedEntity.restrictionEntityList.push(restrictionEntityService.selectedEntity);
restrictionService.insert().then(function successCallBack(response) { 
restrictionService.selectedEntity.restrictionEntityGroupList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.restrictionEntityGroupDetailForm.$valid) return; 
restrictionService.selectedEntity.show=false;

for (i=0; i<restrictionService.selectedEntity.restrictionEntityGroupList.length; i++)

{

if (restrictionEntityService.selectedEntity.restrictionEntityGroupList[i].restrictionEntityGroupId==restrictionEntityGroupService.selectedEntity.restrictionEntityGroupId)

restrictionEntityService.selectedEntity.restrictionEntityGroupList.splice(i,1);

}

restrictionEntityService.selectedEntity.restrictionEntityGroupList.push(restrictionEntityGroupService.selectedEntity);

restrictionService.update().then(function successCallback(response){
restrictionService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
restrictionService.selectedEntity.show=false;
for (i=0; i<restrictionEntityService.selectedEntity.restrictionEntityGroupList.length; i++)
{
if (restrictionEntityService.selectedEntity.restrictionEntityGroupList[i].restrictionEntityGroupId==restrictionEntityGroupService.selectedEntity.restrictionEntityGroupId)
restrictionEntityService.selectedEntity.restrictionEntityGroupList.splice(i,1);
}
restrictionService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<restrictionEntityService.selectedEntity.restrictionEntityGroupList.length; i++)
{
if (restrictionEntityService.selectedEntity.restrictionEntityGroupList[i].restrictionEntityGroupId==restrictionEntityGroupService.selectedEntity.restrictionEntityGroupId)
restrictionEntityService.selectedEntity.restrictionEntityGroupList.splice(i,1);
}
$scope.updateParent();
restrictionService.del().then(function successCallback(response) { 
restrictionService.setSelectedEntity(null);
restrictionEntityService.initRestrictionEntityGroupList().then(function(response) {
restrictionEntityService.childrenList.restrictionEntityGroupList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.entityGroupGridApi!=undefined && $scope.entityGroupGridApi!=null)
 $scope.entityGroupGridApi.core.handleWindowResize(); 
if ($scope.roleGridApi!=undefined && $scope.roleGridApi!=null)
 $scope.roleGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showEntityGroupDetail= function(index)
{
if (index!=null)
{
entityGroupService.searchOne(restrictionEntityGroupService.selectedEntity.entityGroupList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (restrictionService.selectedEntity.entityGroup==null || restrictionEntityGroupService.selectedEntity.entityGroup==undefined)
{
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
}
else
entityService.searchOne(restrictionEntityGroupService.selectedEntity.entityGroup).then(
function successCallback(response) {
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#entityGroupTabs li:eq(0) a').tab('show');
};
$scope.showRoleDetail= function(index)
{
if (index!=null)
{
roleService.searchOne(restrictionEntityGroupService.selectedEntity.roleList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.user==undefined || securityService.restrictionList.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (restrictionService.selectedEntity.role==null || restrictionEntityGroupService.selectedEntity.role==undefined)
{
if (securityService.restrictionList.user==undefined || securityService.restrictionList.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
roleService.setSelectedEntity(null); 
roleService.selectedEntity.show=true; 
}
else
roleService.searchOne(restrictionEntityGroupService.selectedEntity.role).then(
function successCallback(response) {
if (securityService.restrictionList.user==undefined || securityService.restrictionList.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntityGroup==undefined || securityService.restrictionList.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#roleTabs li:eq(0) a').tab('show');
};
$scope.entityGroupListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'entityId'},
{ name: 'entityGroupId'},
{ name: 'name'} 
]
,data: $scope.selectedEntity.entityGroupList
 };
$scope.entityGroupListGridOptions.onRegisterApi = function(gridApi){
$scope.entityGroupGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
entityService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
entityService.setSelectedEntity(response.data[0]);
});
$('#entityGroupTabs li:eq(0) a').tab('show');
}
else 
entityService.setSelectedEntity(null);
entityService.selectedEntity.show = row.isSelected;
});
  };
$scope.roleListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'roleId'},
{ name: 'role'} 
]
,data: $scope.selectedEntity.roleList
 };
$scope.roleListGridOptions.onRegisterApi = function(gridApi){
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
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionEntityGroup.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadEntityGroupList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entityGroup.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityGroupList]);
};
$scope.downloadRoleList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,$scope.selectedEntity.roleList]);
};
})
.service("fieldService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,annotationList: [],restrictionFieldList: []};
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
var promise= $http.post("../field/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../field/"+entity.fieldId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../field/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../field/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../field/"+this.selectedEntity.fieldId;
var promise= $http["delete"](url);
return promise; 
}
 this.initEntityList= function()
{
var promise= $http
.post("../entity/search",
{});
return promise;
};
 this.initAnnotationList= function()
{
var promise= $http
.post("../annotation/search",
{});
return promise;
};
 this.initRestrictionFieldList= function()
{
var promise= $http
.post("../restriction/search",
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
})
.controller("fieldController",function($scope,$http,fieldService, securityService ,entityService,annotationService,restrictionFieldService,tabService,relationshipService,enumFieldService,tabService,restrictionEntityService,entityGroupService,relationshipService,enumFieldService,annotationAttributeService,roleService,relationshipService,enumFieldService)
{
//restrictionEntity
$scope.searchBean=fieldService.searchBean;
$scope.entityList=fieldService.entityList;
$scope.selectedEntity=fieldService.selectedEntity;
$scope.childrenList=fieldService.childrenList; 
$scope.reset = function()
{
fieldService.resetSearchBean();
$scope.searchBean=fieldService.searchBean;fieldService.setSelectedEntity(null);
fieldService.selectedEntity.show=false;
fieldService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
restrictionService.update().then(function successCallback(response) {
restrictionService.setSelectedEntity(response);
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
fieldService.setSelectedEntity(null);
fieldService.setEntityList(null);
fieldService.selectedEntity.show=true;
$('#fieldTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
fieldService.selectedEntity.show=false;
fieldService.searchBean.annotationList=[];
fieldService.searchBean.annotationList.push(fieldService.searchBean.annotation);
delete fieldService.searchBean.annotation; 
fieldService.searchBean.restrictionFieldList=[];
fieldService.searchBean.restrictionFieldList.push(fieldService.searchBean.restrictionField);
delete fieldService.searchBean.restrictionField; 
fieldService.search().then(function successCallback(response) {
fieldService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.fieldDetailForm.$valid) return; 
fieldService.selectedEntity.show=false;
fieldService.selectedEntity.restrictionEntityList.push(restrictionEntityService.selectedEntity);
fieldService.insert().then(function successCallBack(response) { 
restrictionService.selectedEntity.fieldList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.fieldDetailForm.$valid) return; 
fieldService.selectedEntity.show=false;

for (i=0; i<restrictionService.selectedEntity.fieldList.length; i++)

{

if (restrictionEntityService.selectedEntity.fieldList[i].fieldId==fieldService.selectedEntity.fieldId)

restrictionEntityService.selectedEntity.fieldList.splice(i,1);

}

restrictionEntityService.selectedEntity.fieldList.push(fieldService.selectedEntity);

fieldService.update().then(function successCallback(response){
fieldService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
fieldService.selectedEntity.show=false;
for (i=0; i<restrictionEntityService.selectedEntity.fieldList.length; i++)
{
if (restrictionEntityService.selectedEntity.fieldList[i].fieldId==fieldService.selectedEntity.fieldId)
restrictionEntityService.selectedEntity.fieldList.splice(i,1);
}
fieldService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<restrictionEntityService.selectedEntity.fieldList.length; i++)
{
if (restrictionEntityService.selectedEntity.fieldList[i].fieldId==fieldService.selectedEntity.fieldId)
restrictionEntityService.selectedEntity.fieldList.splice(i,1);
}
$scope.updateParent();
fieldService.del().then(function successCallback(response) { 
fieldService.setSelectedEntity(null);
restrictionEntityService.initFieldList().then(function(response) {
restrictionEntityService.childrenList.fieldList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.entityGridApi!=undefined && $scope.entityGridApi!=null)
 $scope.entityGridApi.core.handleWindowResize(); 
if ($scope.annotationGridApi!=undefined && $scope.annotationGridApi!=null)
 $scope.annotationGridApi.core.handleWindowResize(); 
if ($scope.restrictionFieldGridApi!=undefined && $scope.restrictionFieldGridApi!=null)
 $scope.restrictionFieldGridApi.core.handleWindowResize(); 
if ($scope.tabGridApi!=undefined && $scope.tabGridApi!=null)
 $scope.tabGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(fieldService.selectedEntity.entityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (fieldService.selectedEntity.entity==null || fieldService.selectedEntity.entity==undefined)
{
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
}
else
entityService.searchOne(fieldService.selectedEntity.entity).then(
function successCallback(response) {
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#entityTabs li:eq(0) a').tab('show');
};
$scope.showAnnotationDetail= function(index)
{
if (index!=null)
{
annotationService.searchOne(fieldService.selectedEntity.annotationList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
annotationService.initFieldList().then(function successCallback(response) {
annotationService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
annotationService.initRelationshipList().then(function successCallback(response) {
annotationService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
annotationService.initEnumFieldList().then(function successCallback(response) {
annotationService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotationAttribute==undefined || securityService.restrictionList.annotationAttribute.canSearch)
annotationService.initAnnotationAttributeList().then(function successCallback(response) {
annotationService.childrenList.annotationAttributeList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
annotationService.childrenList.annotationTypeList=["PRIMARY_KEY","NOT_NULL","NOT_BLANK","DESCRIPTION_FIELD","BETWEEN_FILTER","EXCEL_EXPORT","FILTER_FIELD","IGNORE_SEARCH","IGNORE_UPDATE","IGNORE_TABLE_LIST","SIZE",];
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (fieldService.selectedEntity.annotation==null || fieldService.selectedEntity.annotation==undefined)
{
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
annotationService.initFieldList().then(function successCallback(response) {
annotationService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
annotationService.initRelationshipList().then(function successCallback(response) {
annotationService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
annotationService.initEnumFieldList().then(function successCallback(response) {
annotationService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotationAttribute==undefined || securityService.restrictionList.annotationAttribute.canSearch)
annotationService.initAnnotationAttributeList().then(function successCallback(response) {
annotationService.childrenList.annotationAttributeList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
annotationService.childrenList.annotationTypeList=["PRIMARY_KEY","NOT_NULL","NOT_BLANK","DESCRIPTION_FIELD","BETWEEN_FILTER","EXCEL_EXPORT","FILTER_FIELD","IGNORE_SEARCH","IGNORE_UPDATE","IGNORE_TABLE_LIST","SIZE",];
annotationService.setSelectedEntity(null); 
annotationService.selectedEntity.show=true; 
}
else
annotationService.searchOne(fieldService.selectedEntity.annotation).then(
function successCallback(response) {
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
annotationService.initFieldList().then(function successCallback(response) {
annotationService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
annotationService.initRelationshipList().then(function successCallback(response) {
annotationService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
annotationService.initEnumFieldList().then(function successCallback(response) {
annotationService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotationAttribute==undefined || securityService.restrictionList.annotationAttribute.canSearch)
annotationService.initAnnotationAttributeList().then(function successCallback(response) {
annotationService.childrenList.annotationAttributeList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
annotationService.childrenList.annotationTypeList=["PRIMARY_KEY","NOT_NULL","NOT_BLANK","DESCRIPTION_FIELD","BETWEEN_FILTER","EXCEL_EXPORT","FILTER_FIELD","IGNORE_SEARCH","IGNORE_UPDATE","IGNORE_TABLE_LIST","SIZE",];
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#annotationTabs li:eq(0) a').tab('show');
};
$scope.showRestrictionFieldDetail= function(index)
{
if (index!=null)
{
restrictionFieldService.searchOne(fieldService.selectedEntity.restrictionFieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
restrictionFieldService.initFieldList().then(function successCallback(response) {
restrictionFieldService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionFieldService.initRoleList().then(function successCallback(response) {
restrictionFieldService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionService.setSelectedEntity(response.data[0]);
restrictionService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (fieldService.selectedEntity.restrictionField==null || fieldService.selectedEntity.restrictionField==undefined)
{
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
restrictionFieldService.initFieldList().then(function successCallback(response) {
restrictionFieldService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionFieldService.initRoleList().then(function successCallback(response) {
restrictionFieldService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionService.setSelectedEntity(null); 
restrictionService.selectedEntity.show=true; 
}
else
restrictionService.searchOne(fieldService.selectedEntity.restrictionField).then(
function successCallback(response) {
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
restrictionFieldService.initFieldList().then(function successCallback(response) {
restrictionFieldService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionFieldService.initRoleList().then(function successCallback(response) {
restrictionFieldService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionService.setSelectedEntity(response.data[0]);
restrictionService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#restrictionFieldTabs li:eq(0) a').tab('show');
};
$scope.showTabDetail= function(index)
{
if (index!=null)
{
tabService.searchOne(fieldService.selectedEntity.tabList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (fieldService.selectedEntity.tab==null || fieldService.selectedEntity.tab==undefined)
{
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
tabService.setSelectedEntity(null); 
tabService.selectedEntity.show=true; 
}
else
tabService.searchOne(fieldService.selectedEntity.tab).then(
function successCallback(response) {
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#tabTabs li:eq(0) a').tab('show');
};
$scope.entityListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'entityId'},
{ name: 'name'} 
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
$scope.annotationListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'annotationId'} 
]
,data: $scope.selectedEntity.annotationList
 };
$scope.annotationListGridOptions.onRegisterApi = function(gridApi){
$scope.annotationGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
annotationService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
annotationService.setSelectedEntity(response.data[0]);
});
$('#annotationTabs li:eq(0) a').tab('show');
}
else 
annotationService.setSelectedEntity(null);
annotationService.selectedEntity.show = row.isSelected;
});
  };
$scope.restrictionFieldListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'restrictionFieldId'} 
]
,data: $scope.selectedEntity.restrictionFieldList
 };
$scope.restrictionFieldListGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionFieldGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
restrictionService.setSelectedEntity(response.data[0]);
});
$('#restrictionFieldTabs li:eq(0) a').tab('show');
}
else 
restrictionService.setSelectedEntity(null);
restrictionService.selectedEntity.show = row.isSelected;
});
  };
$scope.tabListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
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
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("field.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityList]);
};
$scope.saveLinkedAnnotation= function() {
fieldService.selectedEntity.annotationList.push(fieldService.selectedEntity.annotation);
}
$scope.downloadAnnotationList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("annotation.xls",?) FROM ?',[mystyle,$scope.selectedEntity.annotationList]);
};
$scope.saveLinkedRestrictionField= function() {
fieldService.selectedEntity.restrictionFieldList.push(fieldService.selectedEntity.restrictionField);
}
$scope.downloadRestrictionFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionField.xls",?) FROM ?',[mystyle,$scope.selectedEntity.restrictionFieldList]);
};
$scope.downloadTabList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("tab.xls",?) FROM ?',[mystyle,$scope.selectedEntity.tabList]);
};
})
.service("relationshipService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,annotationList: []};
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
var promise= $http.post("../relationship/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../relationship/"+entity.relationshipId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../relationship/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../relationship/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../relationship/"+this.selectedEntity.relationshipId;
var promise= $http["delete"](url);
return promise; 
}
 this.initEntityList= function()
{
var promise= $http
.post("../entity/search",
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
 this.initAnnotationList= function()
{
var promise= $http
.post("../annotation/search",
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
})
.controller("relationshipController",function($scope,$http,relationshipService, securityService ,entityService,entityService,annotationService,tabService,fieldService,enumFieldService,tabService,restrictionEntityService,entityGroupService,fieldService,enumFieldService,annotationAttributeService,fieldService,enumFieldService)
{
//restrictionEntity
$scope.searchBean=relationshipService.searchBean;
$scope.entityList=relationshipService.entityList;
$scope.selectedEntity=relationshipService.selectedEntity;
$scope.childrenList=relationshipService.childrenList; 
$scope.reset = function()
{
relationshipService.resetSearchBean();
$scope.searchBean=relationshipService.searchBean;relationshipService.setSelectedEntity(null);
relationshipService.selectedEntity.show=false;
relationshipService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
restrictionService.update().then(function successCallback(response) {
restrictionService.setSelectedEntity(response);
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
relationshipService.setSelectedEntity(null);
relationshipService.setEntityList(null);
relationshipService.selectedEntity.show=true;
$('#relationshipTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
relationshipService.selectedEntity.show=false;
relationshipService.searchBean.annotationList=[];
relationshipService.searchBean.annotationList.push(relationshipService.searchBean.annotation);
delete relationshipService.searchBean.annotation; 
relationshipService.search().then(function successCallback(response) {
relationshipService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.relationshipDetailForm.$valid) return; 
relationshipService.selectedEntity.show=false;
relationshipService.selectedEntity.restrictionEntityList.push(restrictionEntityService.selectedEntity);
relationshipService.insert().then(function successCallBack(response) { 
restrictionService.selectedEntity.relationshipList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.relationshipDetailForm.$valid) return; 
relationshipService.selectedEntity.show=false;

for (i=0; i<restrictionService.selectedEntity.relationshipList.length; i++)

{

if (restrictionEntityService.selectedEntity.relationshipList[i].relationshipId==relationshipService.selectedEntity.relationshipId)

restrictionEntityService.selectedEntity.relationshipList.splice(i,1);

}

restrictionEntityService.selectedEntity.relationshipList.push(relationshipService.selectedEntity);

relationshipService.update().then(function successCallback(response){
relationshipService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
relationshipService.selectedEntity.show=false;
for (i=0; i<restrictionEntityService.selectedEntity.relationshipList.length; i++)
{
if (restrictionEntityService.selectedEntity.relationshipList[i].relationshipId==relationshipService.selectedEntity.relationshipId)
restrictionEntityService.selectedEntity.relationshipList.splice(i,1);
}
relationshipService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<restrictionEntityService.selectedEntity.relationshipList.length; i++)
{
if (restrictionEntityService.selectedEntity.relationshipList[i].relationshipId==relationshipService.selectedEntity.relationshipId)
restrictionEntityService.selectedEntity.relationshipList.splice(i,1);
}
$scope.updateParent();
relationshipService.del().then(function successCallback(response) { 
relationshipService.setSelectedEntity(null);
restrictionEntityService.initRelationshipList().then(function(response) {
restrictionEntityService.childrenList.relationshipList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.entityGridApi!=undefined && $scope.entityGridApi!=null)
 $scope.entityGridApi.core.handleWindowResize(); 
if ($scope.entityGridApi!=undefined && $scope.entityGridApi!=null)
 $scope.entityGridApi.core.handleWindowResize(); 
if ($scope.annotationGridApi!=undefined && $scope.annotationGridApi!=null)
 $scope.annotationGridApi.core.handleWindowResize(); 
if ($scope.tabGridApi!=undefined && $scope.tabGridApi!=null)
 $scope.tabGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(relationshipService.selectedEntity.entityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (relationshipService.selectedEntity.entity==null || relationshipService.selectedEntity.entity==undefined)
{
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
}
else
entityService.searchOne(relationshipService.selectedEntity.entity).then(
function successCallback(response) {
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#entityTabs li:eq(0) a').tab('show');
};
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(relationshipService.selectedEntity.entityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (relationshipService.selectedEntity.entity==null || relationshipService.selectedEntity.entity==undefined)
{
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
}
else
entityService.searchOne(relationshipService.selectedEntity.entity).then(
function successCallback(response) {
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#entityTabs li:eq(0) a').tab('show');
};
$scope.showAnnotationDetail= function(index)
{
if (index!=null)
{
annotationService.searchOne(relationshipService.selectedEntity.annotationList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
annotationService.initFieldList().then(function successCallback(response) {
annotationService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
annotationService.initRelationshipList().then(function successCallback(response) {
annotationService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
annotationService.initEnumFieldList().then(function successCallback(response) {
annotationService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotationAttribute==undefined || securityService.restrictionList.annotationAttribute.canSearch)
annotationService.initAnnotationAttributeList().then(function successCallback(response) {
annotationService.childrenList.annotationAttributeList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
annotationService.childrenList.annotationTypeList=["PRIMARY_KEY","NOT_NULL","NOT_BLANK","DESCRIPTION_FIELD","BETWEEN_FILTER","EXCEL_EXPORT","FILTER_FIELD","IGNORE_SEARCH","IGNORE_UPDATE","IGNORE_TABLE_LIST","SIZE",];
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (relationshipService.selectedEntity.annotation==null || relationshipService.selectedEntity.annotation==undefined)
{
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
annotationService.initFieldList().then(function successCallback(response) {
annotationService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
annotationService.initRelationshipList().then(function successCallback(response) {
annotationService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
annotationService.initEnumFieldList().then(function successCallback(response) {
annotationService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotationAttribute==undefined || securityService.restrictionList.annotationAttribute.canSearch)
annotationService.initAnnotationAttributeList().then(function successCallback(response) {
annotationService.childrenList.annotationAttributeList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
annotationService.childrenList.annotationTypeList=["PRIMARY_KEY","NOT_NULL","NOT_BLANK","DESCRIPTION_FIELD","BETWEEN_FILTER","EXCEL_EXPORT","FILTER_FIELD","IGNORE_SEARCH","IGNORE_UPDATE","IGNORE_TABLE_LIST","SIZE",];
annotationService.setSelectedEntity(null); 
annotationService.selectedEntity.show=true; 
}
else
annotationService.searchOne(relationshipService.selectedEntity.annotation).then(
function successCallback(response) {
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
annotationService.initFieldList().then(function successCallback(response) {
annotationService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
annotationService.initRelationshipList().then(function successCallback(response) {
annotationService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
annotationService.initEnumFieldList().then(function successCallback(response) {
annotationService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotationAttribute==undefined || securityService.restrictionList.annotationAttribute.canSearch)
annotationService.initAnnotationAttributeList().then(function successCallback(response) {
annotationService.childrenList.annotationAttributeList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
annotationService.childrenList.annotationTypeList=["PRIMARY_KEY","NOT_NULL","NOT_BLANK","DESCRIPTION_FIELD","BETWEEN_FILTER","EXCEL_EXPORT","FILTER_FIELD","IGNORE_SEARCH","IGNORE_UPDATE","IGNORE_TABLE_LIST","SIZE",];
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#annotationTabs li:eq(0) a').tab('show');
};
$scope.showTabDetail= function(index)
{
if (index!=null)
{
tabService.searchOne(relationshipService.selectedEntity.tabList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (relationshipService.selectedEntity.tab==null || relationshipService.selectedEntity.tab==undefined)
{
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
tabService.setSelectedEntity(null); 
tabService.selectedEntity.show=true; 
}
else
tabService.searchOne(relationshipService.selectedEntity.tab).then(
function successCallback(response) {
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#tabTabs li:eq(0) a').tab('show');
};
$scope.entityListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'entityId'},
{ name: 'name'} 
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
$scope.entityListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'entityId'},
{ name: 'name'} 
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
$scope.annotationListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'annotationId'} 
]
,data: $scope.selectedEntity.annotationList
 };
$scope.annotationListGridOptions.onRegisterApi = function(gridApi){
$scope.annotationGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
annotationService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
annotationService.setSelectedEntity(response.data[0]);
});
$('#annotationTabs li:eq(0) a').tab('show');
}
else 
annotationService.setSelectedEntity(null);
annotationService.selectedEntity.show = row.isSelected;
});
  };
$scope.tabListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
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
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("relationship.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityList]);
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityList]);
};
$scope.saveLinkedAnnotation= function() {
relationshipService.selectedEntity.annotationList.push(relationshipService.selectedEntity.annotation);
}
$scope.downloadAnnotationList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("annotation.xls",?) FROM ?',[mystyle,$scope.selectedEntity.annotationList]);
};
$scope.downloadTabList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("tab.xls",?) FROM ?',[mystyle,$scope.selectedEntity.tabList]);
};
})
.service("enumFieldService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,enumValueList: [],annotationList: []};
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
var promise= $http.post("../enumField/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../enumField/"+entity.enumFieldId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../enumField/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../enumField/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../enumField/"+this.selectedEntity.enumFieldId;
var promise= $http["delete"](url);
return promise; 
}
 this.initEnumValueList= function()
{
var promise= $http
.post("../enum/search",
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
 this.initAnnotationList= function()
{
var promise= $http
.post("../annotation/search",
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
})
.controller("enumFieldController",function($scope,$http,enumFieldService, securityService ,enumValueService,entityService,annotationService,tabService,fieldService,relationshipService,tabService,restrictionEntityService,entityGroupService,fieldService,relationshipService,annotationAttributeService,fieldService,relationshipService)
{
//restrictionEntity
$scope.searchBean=enumService.searchBean;
$scope.entityList=enumService.entityList;
$scope.selectedEntity=enumService.selectedEntity;
$scope.childrenList=enumService.childrenList; 
$scope.reset = function()
{
enumService.resetSearchBean();
$scope.searchBean=enumFieldService.searchBean;enumService.setSelectedEntity(null);
enumService.selectedEntity.show=false;
enumService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
restrictionService.update().then(function successCallback(response) {
restrictionService.setSelectedEntity(response);
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
enumService.setSelectedEntity(null);
enumService.setEntityList(null);
enumService.selectedEntity.show=true;
$('#enumFieldTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
enumService.selectedEntity.show=false;
enumService.searchBean.enumValueList=[];
enumService.searchBean.enumValueList.push(enumFieldService.searchBean.enumValue);
delete enumService.searchBean.enumValue; 
enumService.searchBean.annotationList=[];
enumService.searchBean.annotationList.push(enumFieldService.searchBean.annotation);
delete enumService.searchBean.annotation; 
enumService.search().then(function successCallback(response) {
enumService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.enumFieldDetailForm.$valid) return; 
enumService.selectedEntity.show=false;
enumService.selectedEntity.restrictionEntityList.push(restrictionEntityService.selectedEntity);
enumService.insert().then(function successCallBack(response) { 
restrictionService.selectedEntity.enumFieldList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.enumFieldDetailForm.$valid) return; 
enumService.selectedEntity.show=false;

for (i=0; i<restrictionService.selectedEntity.enumFieldList.length; i++)

{

if (restrictionEntityService.selectedEntity.enumFieldList[i].enumFieldId==enumFieldService.selectedEntity.enumFieldId)

restrictionEntityService.selectedEntity.enumFieldList.splice(i,1);

}

restrictionEntityService.selectedEntity.enumFieldList.push(enumFieldService.selectedEntity);

enumService.update().then(function successCallback(response){
enumService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
enumService.selectedEntity.show=false;
for (i=0; i<restrictionEntityService.selectedEntity.enumFieldList.length; i++)
{
if (restrictionEntityService.selectedEntity.enumFieldList[i].enumFieldId==enumFieldService.selectedEntity.enumFieldId)
restrictionEntityService.selectedEntity.enumFieldList.splice(i,1);
}
enumService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<restrictionEntityService.selectedEntity.enumFieldList.length; i++)
{
if (restrictionEntityService.selectedEntity.enumFieldList[i].enumFieldId==enumFieldService.selectedEntity.enumFieldId)
restrictionEntityService.selectedEntity.enumFieldList.splice(i,1);
}
$scope.updateParent();
enumService.del().then(function successCallback(response) { 
enumService.setSelectedEntity(null);
restrictionEntityService.initEnumFieldList().then(function(response) {
restrictionEntityService.childrenList.enumFieldList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.enumValueGridApi!=undefined && $scope.enumValueGridApi!=null)
 $scope.enumValueGridApi.core.handleWindowResize(); 
if ($scope.entityGridApi!=undefined && $scope.entityGridApi!=null)
 $scope.entityGridApi.core.handleWindowResize(); 
if ($scope.annotationGridApi!=undefined && $scope.annotationGridApi!=null)
 $scope.annotationGridApi.core.handleWindowResize(); 
if ($scope.tabGridApi!=undefined && $scope.tabGridApi!=null)
 $scope.tabGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showEnumValueDetail= function(index)
{
if (index!=null)
{
enumValueService.searchOne(enumFieldService.selectedEntity.enumValueList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
enumValueService.initEnumFieldList().then(function successCallback(response) {
enumValueService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
enumService.setSelectedEntity(response.data[0]);
enumService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (enumService.selectedEntity.enumValue==null || enumFieldService.selectedEntity.enumValue==undefined)
{
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
enumValueService.initEnumFieldList().then(function successCallback(response) {
enumValueService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
enumService.setSelectedEntity(null); 
enumService.selectedEntity.show=true; 
}
else
enumService.searchOne(enumFieldService.selectedEntity.enumValue).then(
function successCallback(response) {
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
enumValueService.initEnumFieldList().then(function successCallback(response) {
enumValueService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
enumService.setSelectedEntity(response.data[0]);
enumService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#enumValueTabs li:eq(0) a').tab('show');
};
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(enumFieldService.selectedEntity.entityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (enumService.selectedEntity.entity==null || enumFieldService.selectedEntity.entity==undefined)
{
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
}
else
entityService.searchOne(enumFieldService.selectedEntity.entity).then(
function successCallback(response) {
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#entityTabs li:eq(0) a').tab('show');
};
$scope.showAnnotationDetail= function(index)
{
if (index!=null)
{
annotationService.searchOne(enumFieldService.selectedEntity.annotationList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
annotationService.initFieldList().then(function successCallback(response) {
annotationService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
annotationService.initRelationshipList().then(function successCallback(response) {
annotationService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
annotationService.initEnumFieldList().then(function successCallback(response) {
annotationService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotationAttribute==undefined || securityService.restrictionList.annotationAttribute.canSearch)
annotationService.initAnnotationAttributeList().then(function successCallback(response) {
annotationService.childrenList.annotationAttributeList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
annotationService.childrenList.annotationTypeList=["PRIMARY_KEY","NOT_NULL","NOT_BLANK","DESCRIPTION_FIELD","BETWEEN_FILTER","EXCEL_EXPORT","FILTER_FIELD","IGNORE_SEARCH","IGNORE_UPDATE","IGNORE_TABLE_LIST","SIZE",];
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (enumService.selectedEntity.annotation==null || enumFieldService.selectedEntity.annotation==undefined)
{
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
annotationService.initFieldList().then(function successCallback(response) {
annotationService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
annotationService.initRelationshipList().then(function successCallback(response) {
annotationService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
annotationService.initEnumFieldList().then(function successCallback(response) {
annotationService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotationAttribute==undefined || securityService.restrictionList.annotationAttribute.canSearch)
annotationService.initAnnotationAttributeList().then(function successCallback(response) {
annotationService.childrenList.annotationAttributeList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
annotationService.childrenList.annotationTypeList=["PRIMARY_KEY","NOT_NULL","NOT_BLANK","DESCRIPTION_FIELD","BETWEEN_FILTER","EXCEL_EXPORT","FILTER_FIELD","IGNORE_SEARCH","IGNORE_UPDATE","IGNORE_TABLE_LIST","SIZE",];
annotationService.setSelectedEntity(null); 
annotationService.selectedEntity.show=true; 
}
else
annotationService.searchOne(enumFieldService.selectedEntity.annotation).then(
function successCallback(response) {
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
annotationService.initFieldList().then(function successCallback(response) {
annotationService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
annotationService.initRelationshipList().then(function successCallback(response) {
annotationService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
annotationService.initEnumFieldList().then(function successCallback(response) {
annotationService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotationAttribute==undefined || securityService.restrictionList.annotationAttribute.canSearch)
annotationService.initAnnotationAttributeList().then(function successCallback(response) {
annotationService.childrenList.annotationAttributeList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
annotationService.childrenList.annotationTypeList=["PRIMARY_KEY","NOT_NULL","NOT_BLANK","DESCRIPTION_FIELD","BETWEEN_FILTER","EXCEL_EXPORT","FILTER_FIELD","IGNORE_SEARCH","IGNORE_UPDATE","IGNORE_TABLE_LIST","SIZE",];
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#annotationTabs li:eq(0) a').tab('show');
};
$scope.showTabDetail= function(index)
{
if (index!=null)
{
tabService.searchOne(enumFieldService.selectedEntity.tabList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (enumService.selectedEntity.tab==null || enumFieldService.selectedEntity.tab==undefined)
{
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
tabService.setSelectedEntity(null); 
tabService.selectedEntity.show=true; 
}
else
tabService.searchOne(enumFieldService.selectedEntity.tab).then(
function successCallback(response) {
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#tabTabs li:eq(0) a').tab('show');
};
$scope.enumValueListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'enumValueId'},
{ name: 'value'},
{ name: 'name'} 
]
,data: $scope.selectedEntity.enumValueList
 };
$scope.enumValueListGridOptions.onRegisterApi = function(gridApi){
$scope.enumValueGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
enumService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
enumService.setSelectedEntity(response.data[0]);
});
$('#enumValueTabs li:eq(0) a').tab('show');
}
else 
enumService.setSelectedEntity(null);
enumService.selectedEntity.show = row.isSelected;
});
  };
$scope.entityListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'entityId'},
{ name: 'name'} 
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
$scope.annotationListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'annotationId'} 
]
,data: $scope.selectedEntity.annotationList
 };
$scope.annotationListGridOptions.onRegisterApi = function(gridApi){
$scope.annotationGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
annotationService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
annotationService.setSelectedEntity(response.data[0]);
});
$('#annotationTabs li:eq(0) a').tab('show');
}
else 
annotationService.setSelectedEntity(null);
annotationService.selectedEntity.show = row.isSelected;
});
  };
$scope.tabListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
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
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumField.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedEnumValue= function() {
enumService.selectedEntity.enumValueList.push(enumService.selectedEntity.enumValue);
}
$scope.downloadEnumValueList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumValue.xls",?) FROM ?',[mystyle,$scope.selectedEntity.enumValueList]);
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityList]);
};
$scope.saveLinkedAnnotation= function() {
enumService.selectedEntity.annotationList.push(enumService.selectedEntity.annotation);
}
$scope.downloadAnnotationList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("annotation.xls",?) FROM ?',[mystyle,$scope.selectedEntity.annotationList]);
};
$scope.downloadTabList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("tab.xls",?) FROM ?',[mystyle,$scope.selectedEntity.tabList]);
};
})
.service("tabService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,fieldList: [],relationshipList: [],enumFieldList: []};
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
var promise= $http.post("../tab/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../tab/"+entity.tabId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../tab/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../tab/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../tab/"+this.selectedEntity.tabId;
var promise= $http["delete"](url);
return promise; 
}
 this.initEntityList= function()
{
var promise= $http
.post("../entity/search",
{});
return promise;
};
 this.initFieldList= function()
{
var promise= $http
.post("../field/search",
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
 this.initEnumFieldList= function()
{
var promise= $http
.post("../enum/search",
{});
return promise;
};
})
.controller("tabController",function($scope,$http,tabService, securityService ,entityService,fieldService,relationshipService,enumFieldService,fieldService,relationshipService,enumFieldService,restrictionEntityService,entityGroupService,annotationService,restrictionFieldService,annotationService,enumValueService,annotationService)
{
//restrictionEntity
$scope.searchBean=tabService.searchBean;
$scope.entityList=tabService.entityList;
$scope.selectedEntity=tabService.selectedEntity;
$scope.childrenList=tabService.childrenList; 
$scope.reset = function()
{
tabService.resetSearchBean();
$scope.searchBean=tabService.searchBean;tabService.setSelectedEntity(null);
tabService.selectedEntity.show=false;
tabService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
restrictionService.update().then(function successCallback(response) {
restrictionService.setSelectedEntity(response);
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
tabService.setSelectedEntity(null);
tabService.setEntityList(null);
tabService.selectedEntity.show=true;
$('#tabTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
tabService.selectedEntity.show=false;
tabService.searchBean.fieldList=[];
tabService.searchBean.fieldList.push(tabService.searchBean.field);
delete tabService.searchBean.field; 
tabService.searchBean.relationshipList=[];
tabService.searchBean.relationshipList.push(tabService.searchBean.relationship);
delete tabService.searchBean.relationship; 
tabService.searchBean.enumFieldList=[];
tabService.searchBean.enumFieldList.push(tabService.searchBean.enumField);
delete tabService.searchBean.enumField; 
tabService.search().then(function successCallback(response) {
tabService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.tabDetailForm.$valid) return; 
tabService.selectedEntity.show=false;
tabService.selectedEntity.restrictionEntityList.push(restrictionEntityService.selectedEntity);
tabService.insert().then(function successCallBack(response) { 
restrictionService.selectedEntity.tabList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.tabDetailForm.$valid) return; 
tabService.selectedEntity.show=false;

for (i=0; i<restrictionService.selectedEntity.tabList.length; i++)

{

if (restrictionEntityService.selectedEntity.tabList[i].tabId==tabService.selectedEntity.tabId)

restrictionEntityService.selectedEntity.tabList.splice(i,1);

}

restrictionEntityService.selectedEntity.tabList.push(tabService.selectedEntity);

tabService.update().then(function successCallback(response){
tabService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
tabService.selectedEntity.show=false;
for (i=0; i<restrictionEntityService.selectedEntity.tabList.length; i++)
{
if (restrictionEntityService.selectedEntity.tabList[i].tabId==tabService.selectedEntity.tabId)
restrictionEntityService.selectedEntity.tabList.splice(i,1);
}
tabService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<restrictionEntityService.selectedEntity.tabList.length; i++)
{
if (restrictionEntityService.selectedEntity.tabList[i].tabId==tabService.selectedEntity.tabId)
restrictionEntityService.selectedEntity.tabList.splice(i,1);
}
$scope.updateParent();
tabService.del().then(function successCallback(response) { 
tabService.setSelectedEntity(null);
restrictionEntityService.initTabList().then(function(response) {
restrictionEntityService.childrenList.tabList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.entityGridApi!=undefined && $scope.entityGridApi!=null)
 $scope.entityGridApi.core.handleWindowResize(); 
if ($scope.fieldGridApi!=undefined && $scope.fieldGridApi!=null)
 $scope.fieldGridApi.core.handleWindowResize(); 
if ($scope.relationshipGridApi!=undefined && $scope.relationshipGridApi!=null)
 $scope.relationshipGridApi.core.handleWindowResize(); 
if ($scope.enumFieldGridApi!=undefined && $scope.enumFieldGridApi!=null)
 $scope.enumFieldGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(tabService.selectedEntity.entityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (tabService.selectedEntity.entity==null || tabService.selectedEntity.entity==undefined)
{
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
}
else
entityService.searchOne(tabService.selectedEntity.entity).then(
function successCallback(response) {
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#entityTabs li:eq(0) a').tab('show');
};
$scope.showFieldDetail= function(index)
{
if (index!=null)
{
fieldService.searchOne(tabService.selectedEntity.fieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
fieldService.initEntityList().then(function successCallback(response) {
fieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
fieldService.initAnnotationList().then(function successCallback(response) {
fieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
fieldService.initRestrictionFieldList().then(function successCallback(response) {
fieldService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
fieldService.initTabList().then(function successCallback(response) {
fieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG",];
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (tabService.selectedEntity.field==null || tabService.selectedEntity.field==undefined)
{
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
fieldService.initEntityList().then(function successCallback(response) {
fieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
fieldService.initAnnotationList().then(function successCallback(response) {
fieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
fieldService.initRestrictionFieldList().then(function successCallback(response) {
fieldService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
fieldService.initTabList().then(function successCallback(response) {
fieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG",];
fieldService.setSelectedEntity(null); 
fieldService.selectedEntity.show=true; 
}
else
fieldService.searchOne(tabService.selectedEntity.field).then(
function successCallback(response) {
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
fieldService.initEntityList().then(function successCallback(response) {
fieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
fieldService.initAnnotationList().then(function successCallback(response) {
fieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionField==undefined || securityService.restrictionList.restrictionField.canSearch)
fieldService.initRestrictionFieldList().then(function successCallback(response) {
fieldService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
fieldService.initTabList().then(function successCallback(response) {
fieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG",];
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#fieldTabs li:eq(0) a').tab('show');
};
$scope.showRelationshipDetail= function(index)
{
if (index!=null)
{
relationshipService.searchOne(tabService.selectedEntity.relationshipList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
relationshipService.initEntityList().then(function successCallback(response) {
relationshipService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
relationshipService.initEntityList().then(function successCallback(response) {
relationshipService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
relationshipService.initAnnotationList().then(function successCallback(response) {
relationshipService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
relationshipService.initTabList().then(function successCallback(response) {
relationshipService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
relationshipService.childrenList.relationshipTypeList=["ONE_TO_ONE","ONE_TO_MANY","MANY_TO_ONE","MANY_TO_MANY","MANY_TO_MANY_BACK",];
relationshipService.setSelectedEntity(response.data[0]);
relationshipService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (tabService.selectedEntity.relationship==null || tabService.selectedEntity.relationship==undefined)
{
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
relationshipService.initEntityList().then(function successCallback(response) {
relationshipService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
relationshipService.initEntityList().then(function successCallback(response) {
relationshipService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
relationshipService.initAnnotationList().then(function successCallback(response) {
relationshipService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
relationshipService.initTabList().then(function successCallback(response) {
relationshipService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
relationshipService.childrenList.relationshipTypeList=["ONE_TO_ONE","ONE_TO_MANY","MANY_TO_ONE","MANY_TO_MANY","MANY_TO_MANY_BACK",];
relationshipService.setSelectedEntity(null); 
relationshipService.selectedEntity.show=true; 
}
else
relationshipService.searchOne(tabService.selectedEntity.relationship).then(
function successCallback(response) {
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
relationshipService.initEntityList().then(function successCallback(response) {
relationshipService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
relationshipService.initEntityList().then(function successCallback(response) {
relationshipService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
relationshipService.initAnnotationList().then(function successCallback(response) {
relationshipService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
relationshipService.initTabList().then(function successCallback(response) {
relationshipService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
relationshipService.childrenList.relationshipTypeList=["ONE_TO_ONE","ONE_TO_MANY","MANY_TO_ONE","MANY_TO_MANY","MANY_TO_MANY_BACK",];
relationshipService.setSelectedEntity(response.data[0]);
relationshipService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#relationshipTabs li:eq(0) a').tab('show');
};
$scope.showEnumFieldDetail= function(index)
{
if (index!=null)
{
enumFieldService.searchOne(tabService.selectedEntity.enumFieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.enumValue==undefined || securityService.restrictionList.enumValue.canSearch)
enumFieldService.initEnumValueList().then(function successCallback(response) {
enumFieldService.childrenList.enumValueList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
enumFieldService.initEntityList().then(function successCallback(response) {
enumFieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
enumFieldService.initAnnotationList().then(function successCallback(response) {
enumFieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
enumFieldService.initTabList().then(function successCallback(response) {
enumFieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
enumService.setSelectedEntity(response.data[0]);
enumService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (tabService.selectedEntity.enumField==null || tabService.selectedEntity.enumField==undefined)
{
if (securityService.restrictionList.enumValue==undefined || securityService.restrictionList.enumValue.canSearch)
enumFieldService.initEnumValueList().then(function successCallback(response) {
enumFieldService.childrenList.enumValueList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
enumFieldService.initEntityList().then(function successCallback(response) {
enumFieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
enumFieldService.initAnnotationList().then(function successCallback(response) {
enumFieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
enumFieldService.initTabList().then(function successCallback(response) {
enumFieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
enumService.setSelectedEntity(null); 
enumService.selectedEntity.show=true; 
}
else
enumService.searchOne(tabService.selectedEntity.enumField).then(
function successCallback(response) {
if (securityService.restrictionList.enumValue==undefined || securityService.restrictionList.enumValue.canSearch)
enumFieldService.initEnumValueList().then(function successCallback(response) {
enumFieldService.childrenList.enumValueList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entity==undefined || securityService.restrictionList.entity.canSearch)
enumFieldService.initEntityList().then(function successCallback(response) {
enumFieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.annotation==undefined || securityService.restrictionList.annotation.canSearch)
enumFieldService.initAnnotationList().then(function successCallback(response) {
enumFieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
enumFieldService.initTabList().then(function successCallback(response) {
enumFieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
enumService.setSelectedEntity(response.data[0]);
enumService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#enumFieldTabs li:eq(0) a').tab('show');
};
$scope.entityListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'entityId'},
{ name: 'name'} 
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
$scope.fieldListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'fieldId'},
{ name: 'name'} 
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
$scope.relationshipListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'relationshipId'},
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
$scope.enumFieldListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'enumFieldId'},
{ name: 'name'} 
]
,data: $scope.selectedEntity.enumFieldList
 };
$scope.enumFieldListGridOptions.onRegisterApi = function(gridApi){
$scope.enumFieldGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
enumService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
enumService.setSelectedEntity(response.data[0]);
});
$('#enumFieldTabs li:eq(0) a').tab('show');
}
else 
enumService.setSelectedEntity(null);
enumService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("tab.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityList]);
};
$scope.saveLinkedField= function() {
tabService.selectedEntity.fieldList.push(tabService.selectedEntity.field);
}
$scope.downloadFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("field.xls",?) FROM ?',[mystyle,$scope.selectedEntity.fieldList]);
};
$scope.saveLinkedRelationship= function() {
tabService.selectedEntity.relationshipList.push(tabService.selectedEntity.relationship);
}
$scope.downloadRelationshipList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("relationship.xls",?) FROM ?',[mystyle,$scope.selectedEntity.relationshipList]);
};
$scope.saveLinkedEnumField= function() {
tabService.selectedEntity.enumFieldList.push(tabService.selectedEntity.enumField);
}
$scope.downloadEnumFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumField.xls",?) FROM ?',[mystyle,$scope.selectedEntity.enumFieldList]);
};
})
.service("entityGroupService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,entityList: [],restrictionEntityGroupList: []};
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
 this.initEntityList= function()
{
var promise= $http
.post("../entity/search",
{});
return promise;
};
 this.initRestrictionEntityGroupList= function()
{
var promise= $http
.post("../restriction/search",
{});
return promise;
};
 this.initProjectList= function()
{
var promise= $http
.post("../project/search",
{});
return promise;
};
})
.controller("entityGroupController",function($scope,$http,entityGroupService, securityService ,entityService,restrictionEntityGroupService,projectService,fieldService,relationshipService,enumFieldService,tabService,restrictionEntityService,roleService)
{
//restrictionEntity
$scope.searchBean=entityService.searchBean;
$scope.entityList=entityService.entityList;
$scope.selectedEntity=entityService.selectedEntity;
$scope.childrenList=entityService.childrenList; 
$scope.reset = function()
{
entityService.resetSearchBean();
$scope.searchBean=entityGroupService.searchBean;entityService.setSelectedEntity(null);
entityService.selectedEntity.show=false;
entityService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
restrictionService.update().then(function successCallback(response) {
restrictionService.setSelectedEntity(response);
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
$('#entityGroupTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
entityService.selectedEntity.show=false;
entityService.searchBean.entityList=[];
entityService.searchBean.entityList.push(entityGroupService.searchBean.entity);
delete entityService.searchBean.entity; 
entityService.searchBean.restrictionEntityGroupList=[];
entityService.searchBean.restrictionEntityGroupList.push(entityGroupService.searchBean.restrictionEntityGroup);
delete entityService.searchBean.restrictionEntityGroup; 
entityService.search().then(function successCallback(response) {
entityService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.entityGroupDetailForm.$valid) return; 
entityService.selectedEntity.show=false;
entityService.selectedEntity.restrictionEntityList.push(restrictionEntityService.selectedEntity);
entityService.insert().then(function successCallBack(response) { 
restrictionService.selectedEntity.entityGroupList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.entityGroupDetailForm.$valid) return; 
entityService.selectedEntity.show=false;

for (i=0; i<restrictionService.selectedEntity.entityGroupList.length; i++)

{

if (restrictionEntityService.selectedEntity.entityGroupList[i].entityGroupId==entityGroupService.selectedEntity.entityGroupId)

restrictionEntityService.selectedEntity.entityGroupList.splice(i,1);

}

restrictionEntityService.selectedEntity.entityGroupList.push(entityGroupService.selectedEntity);

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
for (i=0; i<restrictionEntityService.selectedEntity.entityGroupList.length; i++)
{
if (restrictionEntityService.selectedEntity.entityGroupList[i].entityGroupId==entityGroupService.selectedEntity.entityGroupId)
restrictionEntityService.selectedEntity.entityGroupList.splice(i,1);
}
entityService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<restrictionEntityService.selectedEntity.entityGroupList.length; i++)
{
if (restrictionEntityService.selectedEntity.entityGroupList[i].entityGroupId==entityGroupService.selectedEntity.entityGroupId)
restrictionEntityService.selectedEntity.entityGroupList.splice(i,1);
}
$scope.updateParent();
entityService.del().then(function successCallback(response) { 
entityService.setSelectedEntity(null);
restrictionEntityService.initEntityGroupList().then(function(response) {
restrictionEntityService.childrenList.entityGroupList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.entityGridApi!=undefined && $scope.entityGridApi!=null)
 $scope.entityGridApi.core.handleWindowResize(); 
if ($scope.restrictionEntityGroupGridApi!=undefined && $scope.restrictionEntityGroupGridApi!=null)
 $scope.restrictionEntityGroupGridApi.core.handleWindowResize(); 
if ($scope.projectGridApi!=undefined && $scope.projectGridApi!=null)
 $scope.projectGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
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
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.entity==null || entityGroupService.selectedEntity.entity==undefined)
{
if (securityService.restrictionList.field==undefined || securityService.restrictionList.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
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
alert("error");
return; 
});
if (securityService.restrictionList.relationship==undefined || securityService.restrictionList.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.enumField==undefined || securityService.restrictionList.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.tab==undefined || securityService.restrictionList.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.restrictionEntity==undefined || securityService.restrictionList.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#entityTabs li:eq(0) a').tab('show');
};
$scope.showRestrictionEntityGroupDetail= function(index)
{
if (index!=null)
{
restrictionEntityGroupService.searchOne(entityGroupService.selectedEntity.restrictionEntityGroupList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
restrictionEntityGroupService.initEntityGroupList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionEntityGroupService.initRoleList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionService.setSelectedEntity(response.data[0]);
restrictionService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.restrictionEntityGroup==null || entityGroupService.selectedEntity.restrictionEntityGroup==undefined)
{
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
restrictionEntityGroupService.initEntityGroupList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionEntityGroupService.initRoleList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionService.setSelectedEntity(null); 
restrictionService.selectedEntity.show=true; 
}
else
restrictionService.searchOne(entityGroupService.selectedEntity.restrictionEntityGroup).then(
function successCallback(response) {
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
restrictionEntityGroupService.initEntityGroupList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
if (securityService.restrictionList.role==undefined || securityService.restrictionList.role.canSearch)
restrictionEntityGroupService.initRoleList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionService.setSelectedEntity(response.data[0]);
restrictionService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
};
$scope.showProjectDetail= function(index)
{
if (index!=null)
{
projectService.searchOne(entityGroupService.selectedEntity.projectList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
projectService.initEntityGroupList().then(function successCallback(response) {
projectService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
projectService.setSelectedEntity(response.data[0]);
projectService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.project==null || entityGroupService.selectedEntity.project==undefined)
{
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
projectService.initEntityGroupList().then(function successCallback(response) {
projectService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
projectService.setSelectedEntity(null); 
projectService.selectedEntity.show=true; 
}
else
projectService.searchOne(entityGroupService.selectedEntity.project).then(
function successCallback(response) {
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
projectService.initEntityGroupList().then(function successCallback(response) {
projectService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
projectService.setSelectedEntity(response.data[0]);
projectService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#projectTabs li:eq(0) a').tab('show');
};
$scope.entityListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'entityId'},
{ name: 'name'} 
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
$scope.restrictionEntityGroupListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'restrictionEntityGroupId'},
{ name: 'canCreate'},
{ name: 'canUpdate'},
{ name: 'canSearch'},
{ name: 'canDelete'} 
]
,data: $scope.selectedEntity.restrictionEntityGroupList
 };
$scope.restrictionEntityGroupListGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionEntityGroupGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
restrictionService.setSelectedEntity(response.data[0]);
});
$('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
}
else 
restrictionService.setSelectedEntity(null);
restrictionService.selectedEntity.show = row.isSelected;
});
  };
$scope.projectListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'entityId'},
{ name: 'projectId'},
{ name: 'name'} 
]
,data: $scope.selectedEntity.projectList
 };
$scope.projectListGridOptions.onRegisterApi = function(gridApi){
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
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entityGroup.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedEntity= function() {
entityService.selectedEntity.entityList.push(entityService.selectedEntity.entity);
}
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityList]);
};
$scope.saveLinkedRestrictionEntityGroup= function() {
entityService.selectedEntity.restrictionEntityGroupList.push(entityService.selectedEntity.restrictionEntityGroup);
}
$scope.downloadRestrictionEntityGroupList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionEntityGroup.xls",?) FROM ?',[mystyle,$scope.selectedEntity.restrictionEntityGroupList]);
};
$scope.downloadProjectList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("project.xls",?) FROM ?',[mystyle,$scope.selectedEntity.projectList]);
};
})
;