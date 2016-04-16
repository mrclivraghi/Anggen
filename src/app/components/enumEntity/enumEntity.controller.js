(function() { 

angular
.module("serverTestApp")
.controller("EnumEntityController",EnumEntityController);
/** @ngInject */
function EnumEntityController($scope,$http,$rootScope ,enumEntityService, SecurityService, MainService ,projectService,enumValueService)
{
$scope.searchBean=enumEntityService.searchBean;
$scope.entityList=enumEntityService.entityList;
$scope.selectedEntity=enumEntityService.selectedEntity;
$scope.hidden=enumEntityService.hidden;
$scope.projectPreparedData=projectService.preparedData;
$scope.enumValuePreparedData=enumValueService.preparedData;
$scope.reset = function()
{
enumEntityService.resetSearchBean();
$scope.searchBean=enumEntityService.searchBean;enumEntityService.setSelectedEntity(null);
enumEntityService.selectedEntity.show=false;
enumEntityService.setEntityList(null); 
if (enumEntityService.isParent()) 
{
projectService.selectedEntity.show=false;
delete $rootScope.openNode.project;
enumValueService.selectedEntity.show=false;
delete $rootScope.openNode.enumValue;
}
}
$scope.addNew= function()
{
$rootScope.openNode.enumEntity=true;
enumEntityService.setSelectedEntity(null);
enumEntityService.setEntityList(null);
enumEntityService.selectedEntity.show=true;
if (enumEntityService.isParent()) 
{
projectService.selectedEntity.show=false;
delete $rootScope.openNode.project;
enumValueService.selectedEntity.show=false;
delete $rootScope.openNode.enumValue;
}
$('#enumEntityTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
enumEntityService.searchBean.enumValueList=[];
enumEntityService.searchBean.enumValueList.push(enumEntityService.searchBean.enumValue);
delete enumEntityService.searchBean.enumValue; 
enumEntityService.search().then(function successCallback(response) {
enumEntityService.setEntityList(response.data);
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
};
$scope.insert=function()
{
if (!$scope.enumEntityDetailForm.$valid) return; 
if (enumEntityService.isParent()) 
{
enumEntityService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
else 
{
enumEntityService.selectedEntity.show=false;
enumEntityService.insert().then(function successCallBack(response) { 
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
};
$scope.update=function()
{
if (!$scope.enumEntityDetailForm.$valid) return; 
if (enumEntityService.isParent()) 
{
projectService.selectedEntity.show=false;
delete $rootScope.openNode.project;
enumValueService.selectedEntity.show=false;
delete $rootScope.openNode.enumValue;
enumEntityService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
else 
{
enumEntityService.selectedEntity.show=false;

enumEntityService.update().then(function successCallback(response){
enumEntityService.setSelectedEntity(response.data);
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
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
enumEntityService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!enumEntityService.isParent()) 
$scope.updateParent();
enumEntityService.del().then(function successCallback(response) { 
if (enumEntityService.isParent()) 
{
$scope.search();
} else { 
enumEntityService.setSelectedEntity(null);
}
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
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
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
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
if (enumEntityService.selectedEntity.project==null || enumEntityService.selectedEntity.project==undefined)
{
projectService.setSelectedEntity(null); 
projectService.selectedEntity.show=true; 
$rootScope.openNode.project=true;
}
else
projectService.searchOne(enumEntityService.selectedEntity.project).then(
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
$scope.showEnumValueDetail= function(index)
{
if (index!=null)
{
enumValueService.searchOne(enumEntityService.selectedEntity.enumValueList[index]).then(
function successCallback(response) {
console.log("INDEX!=NULLLLLLLLLLLL");
console.log(response);
enumValueService.setSelectedEntity(response.data[0]);
enumValueService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
else 
{
if (enumEntityService.selectedEntity.enumValue==null || enumEntityService.selectedEntity.enumValue==undefined)
{
enumValueService.setSelectedEntity(null); 
enumValueService.selectedEntity.show=true; 
$rootScope.openNode.enumValue=true;
}
else
enumValueService.searchOne(enumEntityService.selectedEntity.enumValue).then(
function successCallback(response) {
enumValueService.setSelectedEntity(response.data[0]);
enumValueService.selectedEntity.show=true;
$rootScope.openNode.enumValue=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
$('#enumValueTabs li:eq(0) a').tab('show');
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
$scope.enumEntityGridOptions={};
cloneObject(enumEntityService.gridOptions,$scope.enumEntityGridOptions);
$scope.enumEntityGridOptions.data=enumEntityService.entityList;
$scope.initChildrenList = function () { 
}
$scope.enumEntityGridOptions.onRegisterApi = function(gridApi){
$scope.enumEntityGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
enumEntityService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
$rootScope.openNode.enumEntity=true;
enumEntityService.setSelectedEntity(response.data[0]);
});
$('#enumEntityTabs li:eq(0) a').tab('show');
}
else 
enumEntityService.setSelectedEntity(null);
delete $rootScope.openNode.enumEntity;
enumEntityService.selectedEntity.show = row.isSelected;
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
$scope.enumValueGridOptions={};
cloneObject(enumValueService.gridOptions,$scope.enumValueGridOptions);
$scope.enumValueGridOptions.data=$scope.selectedEntity.enumValueList;
$scope.initChildrenList = function () { 
}
$scope.enumValueGridOptions.onRegisterApi = function(gridApi){
$scope.enumValueGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
enumValueService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
$rootScope.openNode.enumValue=true;
enumValueService.setSelectedEntity(response.data[0]);
});
$('#enumValueTabs li:eq(0) a').tab('show');
}
else 
enumValueService.setSelectedEntity(null);
delete $rootScope.openNode.enumValue;
enumValueService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
projectService.initEnumEntityList().then(function(response) {
enumEntityService.preparedData.entityList=response.data;
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
enumValueService.initEnumEntityList().then(function(response) {
enumEntityService.preparedData.entityList=response.data;
});

if (enumValueService.selectedEntity.enumValueId!=undefined) enumValueService.searchOne(enumValueService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
enumValueService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
enumFieldService.initEnumEntityList().then(function(response) {
enumEntityService.preparedData.entityList=response.data;
});

if (enumFieldService.selectedEntity.enumFieldId!=undefined) enumFieldService.searchOne(enumFieldService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
enumFieldService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
$scope.closeEntityDetail = function(){ 
enumEntityService.setSelectedEntity(null);
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
}
}
})();
