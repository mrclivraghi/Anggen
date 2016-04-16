(function() { 

angular
.module("serverTestApp")
.controller("FieldController",FieldController);
/** @ngInject */
function FieldController($scope,$http,$rootScope ,fieldService, SecurityService, MainService ,entityService,restrictionEntityService,roleService,restrictionFieldService,userService,restrictionEntityGroupService,entityGroupService,projectService,enumEntityService,enumValueService,tabService,enumFieldService,annotationService,annotationAttributeService,relationshipService)
{
$scope.searchBean=fieldService.searchBean;
$scope.entityList=fieldService.entityList;
$scope.selectedEntity=fieldService.selectedEntity;
$scope.hidden=fieldService.hidden;
$scope.entityPreparedData=entityService.preparedData;
$scope.tabPreparedData=tabService.preparedData;
$scope.annotationPreparedData=annotationService.preparedData;
$scope.restrictionFieldPreparedData=restrictionFieldService.preparedData;
$scope.fieldTypePreparedData={};$scope.fieldTypePreparedData.entityList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG","FILE",];
$scope.reset = function()
{
fieldService.resetSearchBean();
$scope.searchBean=fieldService.searchBean;fieldService.setSelectedEntity(null);
fieldService.selectedEntity.show=false;
fieldService.setEntityList(null); 
if (fieldService.isParent()) 
{
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
tabService.selectedEntity.show=false;
delete $rootScope.openNode.tab;
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
restrictionFieldService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionField;
}
}
$scope.addNew= function()
{
$rootScope.openNode.field=true;
fieldService.setSelectedEntity(null);
fieldService.setEntityList(null);
fieldService.selectedEntity.show=true;
if (fieldService.isParent()) 
{
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
tabService.selectedEntity.show=false;
delete $rootScope.openNode.tab;
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
restrictionFieldService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionField;
}
$('#fieldTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
fieldService.selectedEntity.show=false;
delete $rootScope.openNode.field;
fieldService.searchBean.annotationList=[];
fieldService.searchBean.annotationList.push(fieldService.searchBean.annotation);
delete fieldService.searchBean.annotation; 
fieldService.searchBean.restrictionFieldList=[];
fieldService.searchBean.restrictionFieldList.push(fieldService.searchBean.restrictionField);
delete fieldService.searchBean.restrictionField; 
fieldService.search().then(function successCallback(response) {
fieldService.setEntityList(response.data);
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
};
$scope.insert=function()
{
if (!$scope.fieldDetailForm.$valid) return; 
if (fieldService.isParent()) 
{
fieldService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
else 
{
fieldService.selectedEntity.show=false;
fieldService.insert().then(function successCallBack(response) { 
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
};
$scope.update=function()
{
if (!$scope.fieldDetailForm.$valid) return; 
if (fieldService.isParent()) 
{
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
tabService.selectedEntity.show=false;
delete $rootScope.openNode.tab;
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
restrictionFieldService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionField;
fieldService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
else 
{
fieldService.selectedEntity.show=false;

fieldService.update().then(function successCallback(response){
fieldService.setSelectedEntity(response.data);
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
fieldService.selectedEntity.show=false;
delete $rootScope.openNode.field;
fieldService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!fieldService.isParent()) 
$scope.updateParent();
fieldService.del().then(function successCallback(response) { 
if (fieldService.isParent()) 
{
$scope.search();
} else { 
fieldService.setSelectedEntity(null);
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
if ($scope.tabGridApi!=undefined && $scope.tabGridApi!=null)
 $scope.tabGridApi.core.handleWindowResize(); 
if ($scope.annotationGridApi!=undefined && $scope.annotationGridApi!=null)
 $scope.annotationGridApi.core.handleWindowResize(); 
if ($scope.restrictionFieldGridApi!=undefined && $scope.restrictionFieldGridApi!=null)
 $scope.restrictionFieldGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
fieldService.loadFile(file,field).then(function successCallback(response) {
fieldService.setSelectedEntity(response.data);
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
entityService.searchOne(fieldService.selectedEntity.entityList[index]).then(
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
if (fieldService.selectedEntity.entity==null || fieldService.selectedEntity.entity==undefined)
{
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
$rootScope.openNode.entity=true;
}
else
entityService.searchOne(fieldService.selectedEntity.entity).then(
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
$scope.showTabDetail= function(index)
{
if (index!=null)
{
tabService.searchOne(fieldService.selectedEntity.tabList[index]).then(
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
if (fieldService.selectedEntity.tab==null || fieldService.selectedEntity.tab==undefined)
{
tabService.setSelectedEntity(null); 
tabService.selectedEntity.show=true; 
$rootScope.openNode.tab=true;
}
else
tabService.searchOne(fieldService.selectedEntity.tab).then(
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
annotationService.searchOne(fieldService.selectedEntity.annotationList[index]).then(
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
if (fieldService.selectedEntity.annotation==null || fieldService.selectedEntity.annotation==undefined)
{
annotationService.setSelectedEntity(null); 
annotationService.selectedEntity.show=true; 
$rootScope.openNode.annotation=true;
}
else
annotationService.searchOne(fieldService.selectedEntity.annotation).then(
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
$scope.showRestrictionFieldDetail= function(index)
{
if (index!=null)
{
restrictionFieldService.searchOne(fieldService.selectedEntity.restrictionFieldList[index]).then(
function successCallback(response) {
console.log("INDEX!=NULLLLLLLLLLLL");
console.log(response);
restrictionFieldService.setSelectedEntity(response.data[0]);
restrictionFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
else 
{
if (fieldService.selectedEntity.restrictionField==null || fieldService.selectedEntity.restrictionField==undefined)
{
restrictionFieldService.setSelectedEntity(null); 
restrictionFieldService.selectedEntity.show=true; 
$rootScope.openNode.restrictionField=true;
}
else
restrictionFieldService.searchOne(fieldService.selectedEntity.restrictionField).then(
function successCallback(response) {
restrictionFieldService.setSelectedEntity(response.data[0]);
restrictionFieldService.selectedEntity.show=true;
$rootScope.openNode.restrictionField=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
$('#restrictionFieldTabs li:eq(0) a').tab('show');
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
$scope.downloadTabList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("tab.xls",?) FROM ?',[mystyle,$scope.selectedEntity.tabList]);
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
$scope.fieldGridOptions={};
cloneObject(fieldService.gridOptions,$scope.fieldGridOptions);
$scope.fieldGridOptions.data=fieldService.entityList;
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
$scope.restrictionFieldGridOptions={};
cloneObject(restrictionFieldService.gridOptions,$scope.restrictionFieldGridOptions);
$scope.restrictionFieldGridOptions.data=$scope.selectedEntity.restrictionFieldList;
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
function updateParentEntities() { 
restrictionFieldService.initFieldList().then(function(response) {
fieldService.preparedData.entityList=response.data;
});

if (restrictionFieldService.selectedEntity.restrictionFieldId!=undefined) restrictionFieldService.searchOne(restrictionFieldService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
restrictionFieldService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
entityService.initFieldList().then(function(response) {
fieldService.preparedData.entityList=response.data;
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
tabService.initFieldList().then(function(response) {
fieldService.preparedData.entityList=response.data;
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
annotationService.initFieldList().then(function(response) {
fieldService.preparedData.entityList=response.data;
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
fieldService.setSelectedEntity(null);
fieldService.selectedEntity.show=false;
delete $rootScope.openNode.field;
}
}
})();
