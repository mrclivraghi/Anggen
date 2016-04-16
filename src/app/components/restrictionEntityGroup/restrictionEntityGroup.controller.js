(function() { 

angular
.module("serverTestApp")
.controller("RestrictionEntityGroupController",RestrictionEntityGroupController);
/** @ngInject */
function RestrictionEntityGroupController($scope,$http,$rootScope ,restrictionEntityGroupService, SecurityService, MainService ,entityGroupService,entityService,restrictionEntityService,roleService,restrictionFieldService,fieldService,tabService,enumFieldService,enumEntityService,projectService,enumValueService,annotationService,annotationAttributeService,relationshipService,userService)
{
$scope.searchBean=restrictionEntityGroupService.searchBean;
$scope.entityList=restrictionEntityGroupService.entityList;
$scope.selectedEntity=restrictionEntityGroupService.selectedEntity;
$scope.hidden=restrictionEntityGroupService.hidden;
$scope.childrenList=restrictionEntityGroupService.childrenList; 
$scope.reset = function()
{
restrictionEntityGroupService.resetSearchBean();
$scope.searchBean=restrictionEntityGroupService.searchBean;restrictionEntityGroupService.setSelectedEntity(null);
restrictionEntityGroupService.selectedEntity.show=false;
restrictionEntityGroupService.setEntityList(null); 
if (restrictionEntityGroupService.isParent()) 
{
entityGroupService.selectedEntity.show=false;
roleService.selectedEntity.show=false;
}
}
$scope.addNew= function()
{
restrictionEntityGroupService.setSelectedEntity(null);
restrictionEntityGroupService.setEntityList(null);
restrictionEntityGroupService.selectedEntity.show=true;
if (restrictionEntityGroupService.isParent()) 
{
entityGroupService.selectedEntity.show=false;
roleService.selectedEntity.show=false;
}
$('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
restrictionEntityGroupService.selectedEntity.show=false;
restrictionEntityGroupService.search().then(function successCallback(response) {
restrictionEntityGroupService.setEntityList(response.data);
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
};
$scope.insert=function()
{
if (!$scope.restrictionEntityGroupDetailForm.$valid) return; 
if (restrictionEntityGroupService.isParent()) 
{
restrictionEntityGroupService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
else 
{
restrictionEntityGroupService.selectedEntity.show=false;
restrictionEntityGroupService.insert().then(function successCallBack(response) { 
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
};
$scope.update=function()
{
if (!$scope.restrictionEntityGroupDetailForm.$valid) return; 
if (restrictionEntityGroupService.isParent()) 
{
entityGroupService.selectedEntity.show=false;
roleService.selectedEntity.show=false;
restrictionEntityGroupService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
else 
{
restrictionEntityGroupService.selectedEntity.show=false;

restrictionEntityGroupService.update().then(function successCallback(response){
restrictionEntityGroupService.setSelectedEntity(response.data);
updateParentEntities();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
};
$scope.remove= function()
{
restrictionEntityGroupService.selectedEntity.show=false;
restrictionEntityGroupService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!restrictionEntityGroupService.isParent()) 
$scope.updateParent();
restrictionEntityGroupService.del().then(function successCallback(response) { 
if (restrictionEntityGroupService.isParent()) 
{
$scope.search();
} else { 
restrictionEntityGroupService.setSelectedEntity(null);
}
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.entityGroupGridApi!=undefined && $scope.entityGroupGridApi!=null)
 $scope.entityGroupGridApi.core.handleWindowResize(); 
if ($scope.roleGridApi!=undefined && $scope.roleGridApi!=null)
 $scope.roleGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
restrictionEntityGroupService.loadFile(file,field).then(function successCallback(response) {
restrictionEntityGroupService.setSelectedEntity(response.data);
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
return; 
});
}
$scope.trueFalseValues=[true,false];
$scope.showEntityGroupDetail= function(index)
{
if (index!=null)
{
entityGroupService.searchOne(restrictionEntityGroupService.selectedEntity.entityGroupList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
entityGroupService.childrenList.securityTypeList=["BLOCK_WITH_RESTRICTION","ACCESS_WITH_PERMISSION",];
entityGroupService.setSelectedEntity(response.data[0]);
entityGroupService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
else 
{
if (restrictionEntityGroupService.selectedEntity.entityGroup==null || restrictionEntityGroupService.selectedEntity.entityGroup==undefined)
{
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
entityGroupService.childrenList.securityTypeList=["BLOCK_WITH_RESTRICTION","ACCESS_WITH_PERMISSION",];
entityGroupService.setSelectedEntity(null); 
entityGroupService.selectedEntity.show=true; 
}
else
entityGroupService.searchOne(restrictionEntityGroupService.selectedEntity.entityGroup).then(
function successCallback(response) {
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
entityGroupService.childrenList.securityTypeList=["BLOCK_WITH_RESTRICTION","ACCESS_WITH_PERMISSION",];
entityGroupService.setSelectedEntity(response.data[0]);
entityGroupService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
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
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
else 
{
if (restrictionEntityGroupService.selectedEntity.role==null || restrictionEntityGroupService.selectedEntity.role==undefined)
{
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
roleService.setSelectedEntity(null); 
roleService.selectedEntity.show=true; 
}
else
roleService.searchOne(restrictionEntityGroupService.selectedEntity.role).then(
function successCallback(response) {
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
$('#roleTabs li:eq(0) a').tab('show');
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
$scope.restrictionEntityGroupGridOptions={};
cloneObject(restrictionEntityGroupService.gridOptions,$scope.restrictionEntityGroupGridOptions);
$scope.restrictionEntityGroupGridOptions.data=restrictionEntityGroupService.entityList;
$scope.initChildrenList = function () { 
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entityGroup.canSearch)
restrictionEntityGroupService.initEntityGroupList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
restrictionEntityGroupService.initRoleList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.roleList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
$scope.restrictionEntityGroupGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionEntityGroupGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
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
$scope.entityGroupGridOptions={};
cloneObject(entityGroupService.gridOptions,$scope.entityGroupGridOptions);
$scope.entityGroupGridOptions.data=$scope.selectedEntity.entityGroupList;
$scope.initChildrenList = function () { 
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
entityGroupService.childrenList.securityTypeList=["BLOCK_WITH_RESTRICTION","ACCESS_WITH_PERMISSION",];
}
$scope.entityGroupGridOptions.onRegisterApi = function(gridApi){
$scope.entityGroupGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
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
$scope.roleGridOptions={};
cloneObject(roleService.gridOptions,$scope.roleGridOptions);
$scope.roleGridOptions.data=$scope.selectedEntity.roleList;
$scope.initChildrenList = function () { 
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.user.canSearch)
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntityGroup.canSearch)
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntity.canSearch)
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
$scope.roleGridOptions.onRegisterApi = function(gridApi){
$scope.roleGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
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
function updateParentEntities() { 
roleService.initRestrictionEntityGroupList().then(function(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
});

if (roleService.selectedEntity.roleId!=undefined) roleService.searchOne(roleService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
roleService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
entityGroupService.initRestrictionEntityGroupList().then(function(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
});

if (entityGroupService.selectedEntity.entityGroupId!=undefined) entityGroupService.searchOne(entityGroupService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
entityGroupService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
$scope.closeEntityDetail = function(){ 
restrictionEntityGroupService.setSelectedEntity(null);
restrictionEntityGroupService.selectedEntity.show=false;
}
}
})();
