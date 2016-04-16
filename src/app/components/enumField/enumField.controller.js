(function() { 

angular
.module("serverTestApp")
.controller("EnumFieldController",EnumFieldController);
/** @ngInject */
function EnumFieldController($scope,$http,$rootScope ,enumFieldService, SecurityService, MainService ,entityService,restrictionEntityService,roleService,restrictionFieldService,fieldService,tabService,relationshipService,annotationService,annotationAttributeService,userService,restrictionEntityGroupService,entityGroupService,projectService,enumEntityService,enumValueService)
{
$scope.searchBean=enumFieldService.searchBean;
$scope.entityList=enumFieldService.entityList;
$scope.selectedEntity=enumFieldService.selectedEntity;
$scope.hidden=enumFieldService.hidden;
$scope.entityPreparedData=entityService.preparedData;
$scope.enumEntityPreparedData=enumEntityService.preparedData;
$scope.tabPreparedData=tabService.preparedData;
$scope.annotationPreparedData=annotationService.preparedData;
$scope.reset = function()
{
enumFieldService.resetSearchBean();
$scope.searchBean=enumFieldService.searchBean;enumFieldService.setSelectedEntity(null);
enumFieldService.selectedEntity.show=false;
enumFieldService.setEntityList(null); 
if (enumFieldService.isParent()) 
{
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
tabService.selectedEntity.show=false;
delete $rootScope.openNode.tab;
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
}
}
$scope.addNew= function()
{
$rootScope.openNode.enumField=true;
enumFieldService.setSelectedEntity(null);
enumFieldService.setEntityList(null);
enumFieldService.selectedEntity.show=true;
if (enumFieldService.isParent()) 
{
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
tabService.selectedEntity.show=false;
delete $rootScope.openNode.tab;
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
}
$('#enumFieldTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
enumFieldService.searchBean.annotationList=[];
enumFieldService.searchBean.annotationList.push(enumFieldService.searchBean.annotation);
delete enumFieldService.searchBean.annotation; 
enumFieldService.search().then(function successCallback(response) {
enumFieldService.setEntityList(response.data);
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
};
$scope.insert=function()
{
if (!$scope.enumFieldDetailForm.$valid) return; 
if (enumFieldService.isParent()) 
{
enumFieldService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
else 
{
enumFieldService.selectedEntity.show=false;
enumFieldService.insert().then(function successCallBack(response) { 
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
};
$scope.update=function()
{
if (!$scope.enumFieldDetailForm.$valid) return; 
if (enumFieldService.isParent()) 
{
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
tabService.selectedEntity.show=false;
delete $rootScope.openNode.tab;
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
enumFieldService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
else 
{
enumFieldService.selectedEntity.show=false;

enumFieldService.update().then(function successCallback(response){
enumFieldService.setSelectedEntity(response.data);
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
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
enumFieldService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!enumFieldService.isParent()) 
$scope.updateParent();
enumFieldService.del().then(function successCallback(response) { 
if (enumFieldService.isParent()) 
{
$scope.search();
} else { 
enumFieldService.setSelectedEntity(null);
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
if ($scope.enumEntityGridApi!=undefined && $scope.enumEntityGridApi!=null)
 $scope.enumEntityGridApi.core.handleWindowResize(); 
if ($scope.tabGridApi!=undefined && $scope.tabGridApi!=null)
 $scope.tabGridApi.core.handleWindowResize(); 
if ($scope.annotationGridApi!=undefined && $scope.annotationGridApi!=null)
 $scope.annotationGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
enumFieldService.loadFile(file,field).then(function successCallback(response) {
enumFieldService.setSelectedEntity(response.data);
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
return; 
});
}
$scope.trueFalseValues=[true,false];
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(enumFieldService.selectedEntity.entityList[index]).then(
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
if (enumFieldService.selectedEntity.entity==null || enumFieldService.selectedEntity.entity==undefined)
{
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
$rootScope.openNode.entity=true;
}
else
entityService.searchOne(enumFieldService.selectedEntity.entity).then(
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
$scope.showEnumEntityDetail= function(index)
{
if (index!=null)
{
enumEntityService.searchOne(enumFieldService.selectedEntity.enumEntityList[index]).then(
function successCallback(response) {
console.log("INDEX!=NULLLLLLLLLLLL");
console.log(response);
enumEntityService.setSelectedEntity(response.data[0]);
enumEntityService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
else 
{
if (enumFieldService.selectedEntity.enumEntity==null || enumFieldService.selectedEntity.enumEntity==undefined)
{
enumEntityService.setSelectedEntity(null); 
enumEntityService.selectedEntity.show=true; 
$rootScope.openNode.enumEntity=true;
}
else
enumEntityService.searchOne(enumFieldService.selectedEntity.enumEntity).then(
function successCallback(response) {
enumEntityService.setSelectedEntity(response.data[0]);
enumEntityService.selectedEntity.show=true;
$rootScope.openNode.enumEntity=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
$('#enumEntityTabs li:eq(0) a').tab('show');
};
$scope.showTabDetail= function(index)
{
if (index!=null)
{
tabService.searchOne(enumFieldService.selectedEntity.tabList[index]).then(
function successCallback(response) {
console.log("INDEX!=NULLLLLLLLLLLL");
console.log(response);
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
else 
{
if (enumFieldService.selectedEntity.tab==null || enumFieldService.selectedEntity.tab==undefined)
{
tabService.setSelectedEntity(null); 
tabService.selectedEntity.show=true; 
$rootScope.openNode.tab=true;
}
else
tabService.searchOne(enumFieldService.selectedEntity.tab).then(
function successCallback(response) {
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
$rootScope.openNode.tab=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
$('#tabTabs li:eq(0) a').tab('show');
};
$scope.showAnnotationDetail= function(index)
{
if (index!=null)
{
annotationService.searchOne(enumFieldService.selectedEntity.annotationList[index]).then(
function successCallback(response) {
console.log("INDEX!=NULLLLLLLLLLLL");
console.log(response);
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
else 
{
if (enumFieldService.selectedEntity.annotation==null || enumFieldService.selectedEntity.annotation==undefined)
{
annotationService.setSelectedEntity(null); 
annotationService.selectedEntity.show=true; 
$rootScope.openNode.annotation=true;
}
else
annotationService.searchOne(enumFieldService.selectedEntity.annotation).then(
function successCallback(response) {
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
$rootScope.openNode.annotation=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
$('#annotationTabs li:eq(0) a').tab('show');
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumField.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityList]);
};
$scope.downloadEnumEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumEntity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.enumEntityList]);
};
$scope.downloadTabList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("tab.xls",?) FROM ?',[mystyle,$scope.selectedEntity.tabList]);
};
$scope.saveLinkedAnnotation= function() {
enumFieldService.selectedEntity.annotationList.push(enumFieldService.selectedEntity.annotation);
}
$scope.downloadAnnotationList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("annotation.xls",?) FROM ?',[mystyle,$scope.selectedEntity.annotationList]);
};
$scope.enumFieldGridOptions={};
cloneObject(enumFieldService.gridOptions,$scope.enumFieldGridOptions);
$scope.enumFieldGridOptions.data=enumFieldService.entityList;
$scope.initChildrenList = function () { 
}
$scope.enumFieldGridOptions.onRegisterApi = function(gridApi){
$scope.enumFieldGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
enumFieldService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
$rootScope.openNode.enumField=true;
enumFieldService.setSelectedEntity(response.data[0]);
});
$('#enumFieldTabs li:eq(0) a').tab('show');
}
else 
enumFieldService.setSelectedEntity(null);
delete $rootScope.openNode.enumField;
enumFieldService.selectedEntity.show = row.isSelected;
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
$scope.enumEntityGridOptions={};
cloneObject(enumEntityService.gridOptions,$scope.enumEntityGridOptions);
$scope.enumEntityGridOptions.data=$scope.selectedEntity.enumEntityList;
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
$scope.tabGridOptions={};
cloneObject(tabService.gridOptions,$scope.tabGridOptions);
$scope.tabGridOptions.data=$scope.selectedEntity.tabList;
$scope.initChildrenList = function () { 
}
$scope.tabGridOptions.onRegisterApi = function(gridApi){
$scope.tabGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
tabService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
$rootScope.openNode.tab=true;
tabService.setSelectedEntity(response.data[0]);
});
$('#tabTabs li:eq(0) a').tab('show');
}
else 
tabService.setSelectedEntity(null);
delete $rootScope.openNode.tab;
tabService.selectedEntity.show = row.isSelected;
});
  };
$scope.annotationGridOptions={};
cloneObject(annotationService.gridOptions,$scope.annotationGridOptions);
$scope.annotationGridOptions.data=$scope.selectedEntity.annotationList;
$scope.initChildrenList = function () { 
}
$scope.annotationGridOptions.onRegisterApi = function(gridApi){
$scope.annotationGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
annotationService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
$rootScope.openNode.annotation=true;
annotationService.setSelectedEntity(response.data[0]);
});
$('#annotationTabs li:eq(0) a').tab('show');
}
else 
annotationService.setSelectedEntity(null);
delete $rootScope.openNode.annotation;
annotationService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
entityService.initEnumFieldList().then(function(response) {
enumFieldService.preparedData.entityList=response.data;
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
tabService.initEnumFieldList().then(function(response) {
enumFieldService.preparedData.entityList=response.data;
});

if (tabService.selectedEntity.tabId!=undefined) tabService.searchOne(tabService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
tabService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
annotationService.initEnumFieldList().then(function(response) {
enumFieldService.preparedData.entityList=response.data;
});

if (annotationService.selectedEntity.annotationId!=undefined) annotationService.searchOne(annotationService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
annotationService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
$scope.closeEntityDetail = function(){ 
enumFieldService.setSelectedEntity(null);
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
}
}
})();
