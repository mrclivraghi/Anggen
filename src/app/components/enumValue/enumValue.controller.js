(function() { 

angular
.module("serverTestApp")
.controller("EnumValueController",EnumValueController);
/** @ngInject */
function EnumValueController($scope,$http ,enumValueService, SecurityService, MainService ,enumEntityService,projectService,entityGroupService,relationshipService,entityService,tabService,fieldService,annotationService,annotationAttributeService,enumFieldService,restrictionFieldService,roleService)
{
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
if (enumValueService.isParent()) 
{
enumEntityService.selectedEntity.show=false;
}
}
$scope.addNew= function()
{
enumValueService.setSelectedEntity(null);
enumValueService.setEntityList(null);
enumValueService.selectedEntity.show=true;
if (enumValueService.isParent()) 
{
enumEntityService.selectedEntity.show=false;
}
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
if (enumValueService.isParent()) 
{
enumValueService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
}
else 
{
enumValueService.selectedEntity.show=false;
enumValueService.insert().then(function successCallBack(response) { 
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
}
};
$scope.update=function()
{
if (!$scope.enumValueDetailForm.$valid) return; 
if (enumValueService.isParent()) 
{
enumEntityService.selectedEntity.show=false;
enumValueService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
}
else 
{
enumValueService.selectedEntity.show=false;

enumValueService.update().then(function successCallback(response){
enumValueService.setSelectedEntity(response.data);
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
enumValueService.selectedEntity.show=false;
enumValueService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!enumValueService.isParent()) 
$scope.updateParent();
enumValueService.del().then(function successCallback(response) { 
if (enumValueService.isParent()) 
{
$scope.search();
} else { 
enumValueService.setSelectedEntity(null);
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
if (SecurityService.restrictionList.project==undefined || SecurityService.restrictionList.project.canSearch)
enumEntityService.initProjectList().then(function successCallback(response) {
enumEntityService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.enumValue==undefined || SecurityService.restrictionList.enumValue.canSearch)
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
if (SecurityService.restrictionList.project==undefined || SecurityService.restrictionList.project.canSearch)
enumEntityService.initProjectList().then(function successCallback(response) {
enumEntityService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.enumValue==undefined || SecurityService.restrictionList.enumValue.canSearch)
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
if (SecurityService.restrictionList.project==undefined || SecurityService.restrictionList.project.canSearch)
enumEntityService.initProjectList().then(function successCallback(response) {
enumEntityService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.enumValue==undefined || SecurityService.restrictionList.enumValue.canSearch)
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
$scope.enumValueGridOptions={};
cloneObject(enumValueService.gridOptions,$scope.enumValueGridOptions);
$scope.enumValueGridOptions.data=enumValueService.entityList;
$scope.enumValueGridOptions.onRegisterApi = function(gridApi){
$scope.enumValueGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
if (SecurityService.restrictionList.enumEntity==undefined || SecurityService.restrictionList.enumEntity.canSearch)
enumValueService.initEnumEntityList().then(function successCallback(response) {
enumValueService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
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
$scope.enumEntityGridOptions={};
cloneObject(enumEntityService.gridOptions,$scope.enumEntityGridOptions);
$scope.enumEntityGridOptions.data=$scope.selectedEntity.enumEntityList;
$scope.enumEntityGridOptions.onRegisterApi = function(gridApi){
$scope.enumEntityGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
if (SecurityService.restrictionList.project==undefined || SecurityService.restrictionList.project.canSearch)
enumEntityService.initProjectList().then(function successCallback(response) {
enumEntityService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.enumValue==undefined || SecurityService.restrictionList.enumValue.canSearch)
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
function updateParentEntities() { 
enumEntityService.initEnumValueList().then(function(response) {
enumEntityService.childrenList.enumValueList=response.data;
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
}
$scope.closeEntityDetail = function(){ 
enumValueService.setSelectedEntity(null);
enumValueService.selectedEntity.show=false;
}}
})();
