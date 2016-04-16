(function() { 

angular
.module("serverTestApp")
.controller("RestrictionFieldController",RestrictionFieldController);
/** @ngInject */
function RestrictionFieldController($scope,$http,$rootScope ,restrictionFieldService, SecurityService, MainService ,fieldService,entityService,restrictionEntityService,roleService,userService,restrictionEntityGroupService,entityGroupService,projectService,enumEntityService,enumValueService,tabService,enumFieldService,annotationService,annotationAttributeService,relationshipService)
{
$scope.searchBean=restrictionFieldService.searchBean;
$scope.entityList=restrictionFieldService.entityList;
$scope.selectedEntity=restrictionFieldService.selectedEntity;
$scope.hidden=restrictionFieldService.hidden;
$scope.fieldPreparedData=fieldService.preparedData;
$scope.rolePreparedData=roleService.preparedData;
$scope.reset = function()
{
restrictionFieldService.resetSearchBean();
$scope.searchBean=restrictionFieldService.searchBean;restrictionFieldService.setSelectedEntity(null);
restrictionFieldService.selectedEntity.show=false;
restrictionFieldService.setEntityList(null); 
if (restrictionFieldService.isParent()) 
{
fieldService.selectedEntity.show=false;
delete $rootScope.openNode.field;
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
}
}
$scope.addNew= function()
{
$rootScope.openNode.restrictionField=true;
restrictionFieldService.setSelectedEntity(null);
restrictionFieldService.setEntityList(null);
restrictionFieldService.selectedEntity.show=true;
if (restrictionFieldService.isParent()) 
{
fieldService.selectedEntity.show=false;
delete $rootScope.openNode.field;
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
}
$('#restrictionFieldTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
restrictionFieldService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionField;
restrictionFieldService.search().then(function successCallback(response) {
restrictionFieldService.setEntityList(response.data);
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
};
$scope.insert=function()
{
if (!$scope.restrictionFieldDetailForm.$valid) return; 
if (restrictionFieldService.isParent()) 
{
restrictionFieldService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
else 
{
restrictionFieldService.selectedEntity.show=false;
restrictionFieldService.insert().then(function successCallBack(response) { 
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
};
$scope.update=function()
{
if (!$scope.restrictionFieldDetailForm.$valid) return; 
if (restrictionFieldService.isParent()) 
{
fieldService.selectedEntity.show=false;
delete $rootScope.openNode.field;
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
restrictionFieldService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
else 
{
restrictionFieldService.selectedEntity.show=false;

restrictionFieldService.update().then(function successCallback(response){
restrictionFieldService.setSelectedEntity(response.data);
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
restrictionFieldService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionField;
restrictionFieldService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!restrictionFieldService.isParent()) 
$scope.updateParent();
restrictionFieldService.del().then(function successCallback(response) { 
if (restrictionFieldService.isParent()) 
{
$scope.search();
} else { 
restrictionFieldService.setSelectedEntity(null);
}
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.fieldGridApi!=undefined && $scope.fieldGridApi!=null)
 $scope.fieldGridApi.core.handleWindowResize(); 
if ($scope.roleGridApi!=undefined && $scope.roleGridApi!=null)
 $scope.roleGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
restrictionFieldService.loadFile(file,field).then(function successCallback(response) {
restrictionFieldService.setSelectedEntity(response.data);
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
return; 
});
}
$scope.trueFalseValues=['',true,false];
$scope.showFieldDetail= function(index)
{
if (index!=null)
{
fieldService.searchOne(restrictionFieldService.selectedEntity.fieldList[index]).then(
function successCallback(response) {
console.log("INDEX!=NULLLLLLLLLLLL");
console.log(response);
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
else 
{
if (restrictionFieldService.selectedEntity.field==null || restrictionFieldService.selectedEntity.field==undefined)
{
fieldService.setSelectedEntity(null); 
fieldService.selectedEntity.show=true; 
$rootScope.openNode.field=true;
}
else
fieldService.searchOne(restrictionFieldService.selectedEntity.field).then(
function successCallback(response) {
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
$rootScope.openNode.field=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
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
console.log("INDEX!=NULLLLLLLLLLLL");
console.log(response);
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
if (restrictionFieldService.selectedEntity.role==null || restrictionFieldService.selectedEntity.role==undefined)
{
roleService.setSelectedEntity(null); 
roleService.selectedEntity.show=true; 
$rootScope.openNode.role=true;
}
else
roleService.searchOne(restrictionFieldService.selectedEntity.role).then(
function successCallback(response) {
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
$rootScope.openNode.role=true;
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
$scope.restrictionFieldGridOptions={};
cloneObject(restrictionFieldService.gridOptions,$scope.restrictionFieldGridOptions);
$scope.restrictionFieldGridOptions.data=restrictionFieldService.entityList;
$scope.initChildrenList = function () { 
}
$scope.restrictionFieldGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionFieldGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionFieldService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
$rootScope.openNode.restrictionField=true;
restrictionFieldService.setSelectedEntity(response.data[0]);
});
$('#restrictionFieldTabs li:eq(0) a').tab('show');
}
else 
restrictionFieldService.setSelectedEntity(null);
delete $rootScope.openNode.restrictionField;
restrictionFieldService.selectedEntity.show = row.isSelected;
});
  };
$scope.fieldGridOptions={};
cloneObject(fieldService.gridOptions,$scope.fieldGridOptions);
$scope.fieldGridOptions.data=$scope.selectedEntity.fieldList;
$scope.initChildrenList = function () { 
}
$scope.fieldGridOptions.onRegisterApi = function(gridApi){
$scope.fieldGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
fieldService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
$rootScope.openNode.field=true;
fieldService.setSelectedEntity(response.data[0]);
});
$('#fieldTabs li:eq(0) a').tab('show');
}
else 
fieldService.setSelectedEntity(null);
delete $rootScope.openNode.field;
fieldService.selectedEntity.show = row.isSelected;
});
  };
$scope.roleGridOptions={};
cloneObject(roleService.gridOptions,$scope.roleGridOptions);
$scope.roleGridOptions.data=$scope.selectedEntity.roleList;
$scope.initChildrenList = function () { 
}
$scope.roleGridOptions.onRegisterApi = function(gridApi){
$scope.roleGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
roleService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
$rootScope.openNode.role=true;
roleService.setSelectedEntity(response.data[0]);
});
$('#roleTabs li:eq(0) a').tab('show');
}
else 
roleService.setSelectedEntity(null);
delete $rootScope.openNode.role;
roleService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
roleService.initRestrictionFieldList().then(function(response) {
restrictionFieldService.preparedData.entityList=response.data;
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
fieldService.initRestrictionFieldList().then(function(response) {
restrictionFieldService.preparedData.entityList=response.data;
});

if (fieldService.selectedEntity.fieldId!=undefined) fieldService.searchOne(fieldService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
fieldService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
$scope.closeEntityDetail = function(){ 
restrictionFieldService.setSelectedEntity(null);
restrictionFieldService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionField;
}
}
})();
