(function() { 

angular
.module("serverTestApp")
.controller("EntityGroupController",EntityGroupController);
/** @ngInject */
function EntityGroupController($scope,$http,$rootScope ,entityGroupService, SecurityService, MainService ,entityService,restrictionEntityService,roleService,restrictionFieldService,fieldService,tabService,enumFieldService,enumEntityService,projectService,enumValueService,annotationService,annotationAttributeService,relationshipService,userService,restrictionEntityGroupService)
{
$scope.searchBean=entityGroupService.searchBean;
$scope.entityList=entityGroupService.entityList;
$scope.selectedEntity=entityGroupService.selectedEntity;
$scope.hidden=entityGroupService.hidden;
$scope.entityPreparedData=entityService.preparedData;
$scope.projectPreparedData=projectService.preparedData;
$scope.restrictionEntityGroupPreparedData=restrictionEntityGroupService.preparedData;
$scope.securityTypePreparedData={};$scope.securityTypePreparedData.entityList=["BLOCK_WITH_RESTRICTION","ACCESS_WITH_PERMISSION",];
$scope.reset = function()
{
entityGroupService.resetSearchBean();
$scope.searchBean=entityGroupService.searchBean;entityGroupService.setSelectedEntity(null);
entityGroupService.selectedEntity.show=false;
entityGroupService.setEntityList(null); 
if (entityGroupService.isParent()) 
{
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
projectService.selectedEntity.show=false;
delete $rootScope.openNode.project;
restrictionEntityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntityGroup;
}
}
$scope.addNew= function()
{
$rootScope.openNode.entityGroup=true;
entityGroupService.setSelectedEntity(null);
entityGroupService.setEntityList(null);
entityGroupService.selectedEntity.show=true;
if (entityGroupService.isParent()) 
{
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
projectService.selectedEntity.show=false;
delete $rootScope.openNode.project;
restrictionEntityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntityGroup;
}
$('#entityGroupTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
entityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.entityGroup;
entityGroupService.searchBean.entityList=[];
entityGroupService.searchBean.entityList.push(entityGroupService.searchBean.entity);
delete entityGroupService.searchBean.entity; 
entityGroupService.searchBean.restrictionEntityGroupList=[];
entityGroupService.searchBean.restrictionEntityGroupList.push(entityGroupService.searchBean.restrictionEntityGroup);
delete entityGroupService.searchBean.restrictionEntityGroup; 
entityGroupService.search().then(function successCallback(response) {
entityGroupService.setEntityList(response.data);
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
};
$scope.insert=function()
{
if (!$scope.entityGroupDetailForm.$valid) return; 
if (entityGroupService.isParent()) 
{
entityGroupService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
else 
{
entityGroupService.selectedEntity.show=false;
entityGroupService.insert().then(function successCallBack(response) { 
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
};
$scope.update=function()
{
if (!$scope.entityGroupDetailForm.$valid) return; 
if (entityGroupService.isParent()) 
{
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
projectService.selectedEntity.show=false;
delete $rootScope.openNode.project;
restrictionEntityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntityGroup;
entityGroupService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
else 
{
entityGroupService.selectedEntity.show=false;

entityGroupService.update().then(function successCallback(response){
entityGroupService.setSelectedEntity(response.data);
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
entityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.entityGroup;
entityGroupService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!entityGroupService.isParent()) 
$scope.updateParent();
entityGroupService.del().then(function successCallback(response) { 
if (entityGroupService.isParent()) 
{
$scope.search();
} else { 
entityGroupService.setSelectedEntity(null);
}
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.entityGridApi!=undefined && $scope.entityGridApi!=null)
 $scope.entityGridApi.core.handleWindowResize(); 
if ($scope.projectGridApi!=undefined && $scope.projectGridApi!=null)
 $scope.projectGridApi.core.handleWindowResize(); 
if ($scope.restrictionEntityGroupGridApi!=undefined && $scope.restrictionEntityGroupGridApi!=null)
 $scope.restrictionEntityGroupGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
entityGroupService.loadFile(file,field).then(function successCallback(response) {
entityGroupService.setSelectedEntity(response.data);
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
return; 
});
}
$scope.trueFalseValues=['',true,false];
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(entityGroupService.selectedEntity.entityList[index]).then(
function successCallback(response) {
console.log("INDEX!=NULLLLLLLLLLLL");
console.log(response);
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
else 
{
if (entityGroupService.selectedEntity.entity==null || entityGroupService.selectedEntity.entity==undefined)
{
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
$rootScope.openNode.entity=true;
}
else
entityService.searchOne(entityGroupService.selectedEntity.entity).then(
function successCallback(response) {
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
$rootScope.openNode.entity=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
$('#entityTabs li:eq(0) a').tab('show');
};
$scope.showProjectDetail= function(index)
{
if (index!=null)
{
projectService.searchOne(entityGroupService.selectedEntity.projectList[index]).then(
function successCallback(response) {
console.log("INDEX!=NULLLLLLLLLLLL");
console.log(response);
projectService.setSelectedEntity(response.data[0]);
projectService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
else 
{
if (entityGroupService.selectedEntity.project==null || entityGroupService.selectedEntity.project==undefined)
{
projectService.setSelectedEntity(null); 
projectService.selectedEntity.show=true; 
$rootScope.openNode.project=true;
}
else
projectService.searchOne(entityGroupService.selectedEntity.project).then(
function successCallback(response) {
projectService.setSelectedEntity(response.data[0]);
projectService.selectedEntity.show=true;
$rootScope.openNode.project=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
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
console.log("INDEX!=NULLLLLLLLLLLL");
console.log(response);
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
restrictionEntityGroupService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
else 
{
if (entityGroupService.selectedEntity.restrictionEntityGroup==null || entityGroupService.selectedEntity.restrictionEntityGroup==undefined)
{
restrictionEntityGroupService.setSelectedEntity(null); 
restrictionEntityGroupService.selectedEntity.show=true; 
$rootScope.openNode.restrictionEntityGroup=true;
}
else
restrictionEntityGroupService.searchOne(entityGroupService.selectedEntity.restrictionEntityGroup).then(
function successCallback(response) {
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
restrictionEntityGroupService.selectedEntity.show=true;
$rootScope.openNode.restrictionEntityGroup=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
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
alasql('SELECT * INTO XLSXML("entityGroup.xls",?) FROM ?',[mystyle,$scope.entityList]);
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
$scope.entityGroupGridOptions={};
cloneObject(entityGroupService.gridOptions,$scope.entityGroupGridOptions);
$scope.entityGroupGridOptions.data=entityGroupService.entityList;
$scope.initChildrenList = function () { 
}
$scope.entityGroupGridOptions.onRegisterApi = function(gridApi){
$scope.entityGroupGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
entityGroupService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
$rootScope.openNode.entityGroup=true;
entityGroupService.setSelectedEntity(response.data[0]);
});
$('#entityGroupTabs li:eq(0) a').tab('show');
}
else 
entityGroupService.setSelectedEntity(null);
delete $rootScope.openNode.entityGroup;
entityGroupService.selectedEntity.show = row.isSelected;
});
  };
$scope.entityGridOptions={};
cloneObject(entityService.gridOptions,$scope.entityGridOptions);
$scope.entityGridOptions.data=$scope.selectedEntity.entityList;
$scope.initChildrenList = function () { 
}
$scope.entityGridOptions.onRegisterApi = function(gridApi){
$scope.entityGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
entityService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
$rootScope.openNode.entity=true;
entityService.setSelectedEntity(response.data[0]);
});
$('#entityTabs li:eq(0) a').tab('show');
}
else 
entityService.setSelectedEntity(null);
delete $rootScope.openNode.entity;
entityService.selectedEntity.show = row.isSelected;
});
  };
$scope.projectGridOptions={};
cloneObject(projectService.gridOptions,$scope.projectGridOptions);
$scope.projectGridOptions.data=$scope.selectedEntity.projectList;
$scope.initChildrenList = function () { 
}
$scope.projectGridOptions.onRegisterApi = function(gridApi){
$scope.projectGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
projectService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
$rootScope.openNode.project=true;
projectService.setSelectedEntity(response.data[0]);
});
$('#projectTabs li:eq(0) a').tab('show');
}
else 
projectService.setSelectedEntity(null);
delete $rootScope.openNode.project;
projectService.selectedEntity.show = row.isSelected;
});
  };
$scope.restrictionEntityGroupGridOptions={};
cloneObject(restrictionEntityGroupService.gridOptions,$scope.restrictionEntityGroupGridOptions);
$scope.restrictionEntityGroupGridOptions.data=$scope.selectedEntity.restrictionEntityGroupList;
$scope.initChildrenList = function () { 
}
$scope.restrictionEntityGroupGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionEntityGroupGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionEntityGroupService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
$rootScope.openNode.restrictionEntityGroup=true;
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
});
$('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
}
else 
restrictionEntityGroupService.setSelectedEntity(null);
delete $rootScope.openNode.restrictionEntityGroup;
restrictionEntityGroupService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
restrictionEntityGroupService.initEntityGroupList().then(function(response) {
entityGroupService.preparedData.entityList=response.data;
});

if (restrictionEntityGroupService.selectedEntity.restrictionEntityGroupId!=undefined) restrictionEntityGroupService.searchOne(restrictionEntityGroupService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
entityService.initEntityGroupList().then(function(response) {
entityGroupService.preparedData.entityList=response.data;
});

if (entityService.selectedEntity.entityId!=undefined) entityService.searchOne(entityService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
entityService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
projectService.initEntityGroupList().then(function(response) {
entityGroupService.preparedData.entityList=response.data;
});

if (projectService.selectedEntity.projectId!=undefined) projectService.searchOne(projectService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
projectService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
$scope.closeEntityDetail = function(){ 
entityGroupService.setSelectedEntity(null);
entityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.entityGroup;
}
}
})();
