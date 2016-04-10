(function() { 

angular
.module("serverTestApp")
.controller("ProjectController",ProjectController);
/** @ngInject */
function ProjectController($scope,$http,$rootScope ,projectService, SecurityService, MainService ,enumEntityService,enumValueService,entityGroupService,restrictionEntityGroupService,roleService,restrictionEntityService,entityService,tabService,fieldService,restrictionFieldService,annotationService,annotationAttributeService,enumFieldService,relationshipService,userService)
{
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
if (projectService.isParent()) 
{
enumEntityService.selectedEntity.show=false;
entityGroupService.selectedEntity.show=false;
}
}
$scope.addNew= function()
{
projectService.setSelectedEntity(null);
projectService.setEntityList(null);
projectService.selectedEntity.show=true;
if (projectService.isParent()) 
{
enumEntityService.selectedEntity.show=false;
entityGroupService.selectedEntity.show=false;
}
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
if (projectService.isParent()) 
{
projectService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
}
else 
{
projectService.selectedEntity.show=false;
projectService.insert().then(function successCallBack(response) { 
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
}
};
$scope.update=function()
{
if (!$scope.projectDetailForm.$valid) return; 
if (projectService.isParent()) 
{
enumEntityService.selectedEntity.show=false;
entityGroupService.selectedEntity.show=false;
projectService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
}
else 
{
projectService.selectedEntity.show=false;

projectService.update().then(function successCallback(response){
projectService.setSelectedEntity(response.data);
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
projectService.selectedEntity.show=false;
projectService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!projectService.isParent()) 
$scope.updateParent();
projectService.del().then(function successCallback(response) { 
if (projectService.isParent()) 
{
$scope.search();
} else { 
projectService.setSelectedEntity(null);
}
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
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.project.canSearch)
enumEntityService.initProjectList().then(function successCallback(response) {
enumEntityService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.enumValue.canSearch)
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
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.project.canSearch)
enumEntityService.initProjectList().then(function successCallback(response) {
enumEntityService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.enumValue.canSearch)
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
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.project.canSearch)
enumEntityService.initProjectList().then(function successCallback(response) {
enumEntityService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.enumValue.canSearch)
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
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
entityGroupService.childrenList.securityTypeList=["BLOCK_WITH_RESTRICTION","ACCESS_WITH_PERMISSION",];
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
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
entityGroupService.childrenList.securityTypeList=["BLOCK_WITH_RESTRICTION","ACCESS_WITH_PERMISSION",];
entityGroupService.setSelectedEntity(null); 
entityGroupService.selectedEntity.show=true; 
}
else
entityGroupService.searchOne(projectService.selectedEntity.entityGroup).then(
function successCallback(response) {
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
entityGroupService.childrenList.securityTypeList=["BLOCK_WITH_RESTRICTION","ACCESS_WITH_PERMISSION",];
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
$scope.projectGridOptions={};
cloneObject(projectService.gridOptions,$scope.projectGridOptions);
$scope.projectGridOptions.data=projectService.entityList;
$scope.projectGridOptions.onRegisterApi = function(gridApi){
$scope.projectGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.enumEntity.canSearch)
projectService.initEnumEntityList().then(function successCallback(response) {
projectService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entityGroup.canSearch)
projectService.initEntityGroupList().then(function successCallback(response) {
projectService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
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
$scope.enumEntityGridOptions={};
cloneObject(enumEntityService.gridOptions,$scope.enumEntityGridOptions);
$scope.enumEntityGridOptions.data=$scope.selectedEntity.enumEntityList;
$scope.enumEntityGridOptions.onRegisterApi = function(gridApi){
$scope.enumEntityGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.project.canSearch)
enumEntityService.initProjectList().then(function successCallback(response) {
enumEntityService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.enumValue.canSearch)
enumEntityService.initEnumValueList().then(function successCallback(response) {
enumEntityService.childrenList.enumValueList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
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
$scope.entityGroupGridOptions={};
cloneObject(entityGroupService.gridOptions,$scope.entityGroupGridOptions);
$scope.entityGroupGridOptions.data=$scope.selectedEntity.entityGroupList;
$scope.entityGroupGridOptions.onRegisterApi = function(gridApi){
$scope.entityGroupGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntityGroup.canSearch)
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entity.canSearch)
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.project.canSearch)
entityGroupService.initProjectList().then(function successCallback(response) {
entityGroupService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
entityGroupService.childrenList.securityTypeList=["BLOCK_WITH_RESTRICTION","ACCESS_WITH_PERMISSION",];
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
function updateParentEntities() { 
enumEntityService.initProjectList().then(function(response) {
enumEntityService.childrenList.projectList=response.data;
});

if (enumEntityService.selectedEntity.enumEntityId!=undefined) enumEntityService.searchOne(enumEntityService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
enumEntityService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
entityGroupService.initProjectList().then(function(response) {
entityGroupService.childrenList.projectList=response.data;
});

if (entityGroupService.selectedEntity.entityGroupId!=undefined) entityGroupService.searchOne(entityGroupService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
entityGroupService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$scope.closeEntityDetail = function(){ 
projectService.setSelectedEntity(null);
projectService.selectedEntity.show=false;
}}
})();
