(function() { 

angular
.module("serverTestApp")
.controller("RoleController",RoleController);
/** @ngInject */
function RoleController($scope,$http,$rootScope ,roleService, SecurityService, MainService ,restrictionEntityService,entityService,tabService,fieldService,restrictionFieldService,annotationService,annotationAttributeService,enumFieldService,enumEntityService,projectService,entityGroupService,restrictionEntityGroupService,enumValueService,relationshipService,userService)
{
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
if (roleService.isParent()) 
{
restrictionEntityService.selectedEntity.show=false;
restrictionFieldService.selectedEntity.show=false;
userService.selectedEntity.show=false;
restrictionEntityGroupService.selectedEntity.show=false;
}
}
$scope.addNew= function()
{
roleService.setSelectedEntity(null);
roleService.setEntityList(null);
roleService.selectedEntity.show=true;
if (roleService.isParent()) 
{
restrictionEntityService.selectedEntity.show=false;
restrictionFieldService.selectedEntity.show=false;
userService.selectedEntity.show=false;
restrictionEntityGroupService.selectedEntity.show=false;
}
$('#roleTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
roleService.selectedEntity.show=false;
roleService.searchBean.restrictionEntityList=[];
roleService.searchBean.restrictionEntityList.push(roleService.searchBean.restrictionEntity);
delete roleService.searchBean.restrictionEntity; 
roleService.searchBean.restrictionFieldList=[];
roleService.searchBean.restrictionFieldList.push(roleService.searchBean.restrictionField);
delete roleService.searchBean.restrictionField; 
roleService.searchBean.userList=[];
roleService.searchBean.userList.push(roleService.searchBean.user);
delete roleService.searchBean.user; 
roleService.searchBean.restrictionEntityGroupList=[];
roleService.searchBean.restrictionEntityGroupList.push(roleService.searchBean.restrictionEntityGroup);
delete roleService.searchBean.restrictionEntityGroup; 
roleService.search().then(function successCallback(response) {
roleService.setEntityList(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.insert=function()
{
if (!$scope.roleDetailForm.$valid) return; 
if (roleService.isParent()) 
{
roleService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
}
else 
{
roleService.selectedEntity.show=false;
roleService.insert().then(function successCallBack(response) { 
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
}
};
$scope.update=function()
{
if (!$scope.roleDetailForm.$valid) return; 
if (roleService.isParent()) 
{
restrictionEntityService.selectedEntity.show=false;
restrictionFieldService.selectedEntity.show=false;
userService.selectedEntity.show=false;
restrictionEntityGroupService.selectedEntity.show=false;
roleService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
}
else 
{
roleService.selectedEntity.show=false;

roleService.update().then(function successCallback(response){
roleService.setSelectedEntity(response.data);
updateParentEntities();
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
}
};
$scope.remove= function()
{
roleService.selectedEntity.show=false;
roleService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!roleService.isParent()) 
$scope.updateParent();
roleService.del().then(function successCallback(response) { 
if (roleService.isParent()) 
{
$scope.search();
} else { 
roleService.setSelectedEntity(null);
}
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.restrictionEntityGridApi!=undefined && $scope.restrictionEntityGridApi!=null)
 $scope.restrictionEntityGridApi.core.handleWindowResize(); 
if ($scope.restrictionFieldGridApi!=undefined && $scope.restrictionFieldGridApi!=null)
 $scope.restrictionFieldGridApi.core.handleWindowResize(); 
if ($scope.userGridApi!=undefined && $scope.userGridApi!=null)
 $scope.userGridApi.core.handleWindowResize(); 
if ($scope.restrictionEntityGroupGridApi!=undefined && $scope.restrictionEntityGroupGridApi!=null)
 $scope.restrictionEntityGroupGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
roleService.loadFile(file,field).then(function successCallback(response) {
roleService.setSelectedEntity(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
return; 
});
}
$scope.trueFalseValues=[true,false];
$scope.showRestrictionEntityDetail= function(index)
{
if (index!=null)
{
restrictionEntityService.searchOne(roleService.selectedEntity.restrictionEntityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
restrictionEntityService.initRoleList().then(function successCallback(response) {
restrictionEntityService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entity.canSearch)
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
if (roleService.selectedEntity.restrictionEntity==null || roleService.selectedEntity.restrictionEntity==undefined)
{
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
restrictionEntityService.initRoleList().then(function successCallback(response) {
restrictionEntityService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entity.canSearch)
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
restrictionEntityService.searchOne(roleService.selectedEntity.restrictionEntity).then(
function successCallback(response) {
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
restrictionEntityService.initRoleList().then(function successCallback(response) {
restrictionEntityService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entity.canSearch)
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
$scope.showRestrictionFieldDetail= function(index)
{
if (index!=null)
{
restrictionFieldService.searchOne(roleService.selectedEntity.restrictionFieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
restrictionFieldService.initRoleList().then(function successCallback(response) {
restrictionFieldService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
restrictionFieldService.initFieldList().then(function successCallback(response) {
restrictionFieldService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
restrictionFieldService.setSelectedEntity(response.data[0]);
restrictionFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (roleService.selectedEntity.restrictionField==null || roleService.selectedEntity.restrictionField==undefined)
{
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
restrictionFieldService.initRoleList().then(function successCallback(response) {
restrictionFieldService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
restrictionFieldService.initFieldList().then(function successCallback(response) {
restrictionFieldService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
restrictionFieldService.setSelectedEntity(null); 
restrictionFieldService.selectedEntity.show=true; 
}
else
restrictionFieldService.searchOne(roleService.selectedEntity.restrictionField).then(
function successCallback(response) {
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
restrictionFieldService.initRoleList().then(function successCallback(response) {
restrictionFieldService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
restrictionFieldService.initFieldList().then(function successCallback(response) {
restrictionFieldService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
restrictionFieldService.setSelectedEntity(response.data[0]);
restrictionFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#restrictionFieldTabs li:eq(0) a').tab('show');
};
$scope.showUserDetail= function(index)
{
if (index!=null)
{
userService.searchOne(roleService.selectedEntity.userList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
userService.initRoleList().then(function successCallback(response) {
userService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
userService.setSelectedEntity(response.data[0]);
userService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (roleService.selectedEntity.user==null || roleService.selectedEntity.user==undefined)
{
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
userService.initRoleList().then(function successCallback(response) {
userService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
userService.setSelectedEntity(null); 
userService.selectedEntity.show=true; 
}
else
userService.searchOne(roleService.selectedEntity.user).then(
function successCallback(response) {
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
userService.initRoleList().then(function successCallback(response) {
userService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
userService.setSelectedEntity(response.data[0]);
userService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#userTabs li:eq(0) a').tab('show');
};
$scope.showRestrictionEntityGroupDetail= function(index)
{
if (index!=null)
{
restrictionEntityGroupService.searchOne(roleService.selectedEntity.restrictionEntityGroupList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
restrictionEntityGroupService.initRoleList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entityGroup.canSearch)
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
if (roleService.selectedEntity.restrictionEntityGroup==null || roleService.selectedEntity.restrictionEntityGroup==undefined)
{
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
restrictionEntityGroupService.initRoleList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entityGroup.canSearch)
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
restrictionEntityGroupService.searchOne(roleService.selectedEntity.restrictionEntityGroup).then(
function successCallback(response) {
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
restrictionEntityGroupService.initRoleList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entityGroup.canSearch)
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
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,$scope.entityList]);
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
$scope.roleGridOptions={};
cloneObject(roleService.gridOptions,$scope.roleGridOptions);
$scope.roleGridOptions.data=roleService.entityList;
$scope.roleGridOptions.onRegisterApi = function(gridApi){
$scope.roleGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
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
$scope.restrictionEntityGridOptions={};
cloneObject(restrictionEntityService.gridOptions,$scope.restrictionEntityGridOptions);
$scope.restrictionEntityGridOptions.data=$scope.selectedEntity.restrictionEntityList;
$scope.restrictionEntityGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionEntityGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
restrictionEntityService.initRoleList().then(function successCallback(response) {
restrictionEntityService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entity.canSearch)
restrictionEntityService.initEntityList().then(function successCallback(response) {
restrictionEntityService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
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
$scope.restrictionFieldGridOptions={};
cloneObject(restrictionFieldService.gridOptions,$scope.restrictionFieldGridOptions);
$scope.restrictionFieldGridOptions.data=$scope.selectedEntity.restrictionFieldList;
$scope.restrictionFieldGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionFieldGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
restrictionFieldService.initRoleList().then(function successCallback(response) {
restrictionFieldService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
restrictionFieldService.initFieldList().then(function successCallback(response) {
restrictionFieldService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
restrictionFieldService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
restrictionFieldService.setSelectedEntity(response.data[0]);
});
$('#restrictionFieldTabs li:eq(0) a').tab('show');
}
else 
restrictionFieldService.setSelectedEntity(null);
restrictionFieldService.selectedEntity.show = row.isSelected;
});
  };
$scope.userGridOptions={};
cloneObject(userService.gridOptions,$scope.userGridOptions);
$scope.userGridOptions.data=$scope.selectedEntity.userList;
$scope.userGridOptions.onRegisterApi = function(gridApi){
$scope.userGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
userService.initRoleList().then(function successCallback(response) {
userService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
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
$scope.restrictionEntityGroupGridOptions={};
cloneObject(restrictionEntityGroupService.gridOptions,$scope.restrictionEntityGroupGridOptions);
$scope.restrictionEntityGroupGridOptions.data=$scope.selectedEntity.restrictionEntityGroupList;
$scope.restrictionEntityGroupGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionEntityGroupGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
restrictionEntityGroupService.initRoleList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entityGroup.canSearch)
restrictionEntityGroupService.initEntityGroupList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
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
function updateParentEntities() { 
restrictionEntityService.initRoleList().then(function(response) {
restrictionEntityService.childrenList.roleList=response.data;
});

if (restrictionEntityService.selectedEntity.restrictionEntityId!=undefined) restrictionEntityService.searchOne(restrictionEntityService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
restrictionEntityService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
restrictionFieldService.initRoleList().then(function(response) {
restrictionFieldService.childrenList.roleList=response.data;
});

if (restrictionFieldService.selectedEntity.restrictionFieldId!=undefined) restrictionFieldService.searchOne(restrictionFieldService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
restrictionFieldService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
userService.initRoleList().then(function(response) {
userService.childrenList.roleList=response.data;
});

if (userService.selectedEntity.userId!=undefined) userService.searchOne(userService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
userService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
restrictionEntityGroupService.initRoleList().then(function(response) {
restrictionEntityGroupService.childrenList.roleList=response.data;
});

if (restrictionEntityGroupService.selectedEntity.restrictionEntityGroupId!=undefined) restrictionEntityGroupService.searchOne(restrictionEntityGroupService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$scope.closeEntityDetail = function(){ 
roleService.setSelectedEntity(null);
roleService.selectedEntity.show=false;
}}
})();
