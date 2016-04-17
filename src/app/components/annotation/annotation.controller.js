(function() { 

angular
.module("serverTestApp")
.controller("AnnotationController",AnnotationController);
/** @ngInject */
function AnnotationController($scope,$http,$rootScope,$log,UtilityService ,annotationService, SecurityService, MainService ,annotationAttributeService,fieldService,enumFieldService,relationshipService)
{
$scope.searchBean=annotationService.searchBean;
$scope.entityList=annotationService.entityList;
$scope.selectedEntity=annotationService.selectedEntity;
$scope.hidden=annotationService.hidden;
$scope.annotationAttributePreparedData=annotationAttributeService.preparedData;
$scope.fieldPreparedData=fieldService.preparedData;
$scope.enumFieldPreparedData=enumFieldService.preparedData;
$scope.relationshipPreparedData=relationshipService.preparedData;
$scope.annotationTypePreparedData={};$scope.annotationTypePreparedData.entityList=["PRIMARY_KEY","NOT_NULL","NOT_BLANK","DESCRIPTION_FIELD","BETWEEN_FILTER","EXCEL_EXPORT","FILTER_FIELD","IGNORE_SEARCH","IGNORE_UPDATE","IGNORE_TABLE_LIST","SIZE","PASSWORD","PRIORITY","EMBEDDED","CACHE",];
$scope.reset = function()
{
annotationService.resetSearchBean();
$scope.searchBean=annotationService.searchBean;annotationService.setSelectedEntity(null);
annotationService.selectedEntity.show=false;
annotationService.setEntityList(null); 
if (annotationService.isParent()) 
{
annotationAttributeService.selectedEntity.show=false;
delete $rootScope.openNode.annotationAttribute;
fieldService.selectedEntity.show=false;
delete $rootScope.openNode.field;
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
relationshipService.selectedEntity.show=false;
delete $rootScope.openNode.relationship;
}
}
$scope.addNew= function()
{
$rootScope.openNode.annotation=true;
annotationService.setSelectedEntity(null);
annotationService.setEntityList(null);
annotationService.selectedEntity.show=true;
if (annotationService.isParent()) 
{
annotationAttributeService.selectedEntity.show=false;
delete $rootScope.openNode.annotationAttribute;
fieldService.selectedEntity.show=false;
delete $rootScope.openNode.field;
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
relationshipService.selectedEntity.show=false;
delete $rootScope.openNode.relationship;
}
angular.element('#annotationTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
annotationService.searchBean.annotationAttributeList=[];
annotationService.searchBean.annotationAttributeList.push(annotationService.searchBean.annotationAttribute);
delete annotationService.searchBean.annotationAttribute; 
annotationService.search().then(function successCallback(response) {
annotationService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
};
$scope.insert=function()
{
if (!$scope.annotationDetailForm.$valid) return; 
if (annotationService.isParent()) 
{
annotationService.insert().then(function successCallback(response) { 
$log.debug(response);
$scope.search();
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
else 
{
annotationService.selectedEntity.show=false;
annotationService.insert().then(function successCallBack(response) { 
$log.debug(response);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
};
$scope.update=function()
{
if (!$scope.annotationDetailForm.$valid) return; 
if (annotationService.isParent()) 
{
annotationAttributeService.selectedEntity.show=false;
delete $rootScope.openNode.annotationAttribute;
fieldService.selectedEntity.show=false;
delete $rootScope.openNode.field;
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
relationshipService.selectedEntity.show=false;
delete $rootScope.openNode.relationship;
annotationService.update().then(function successCallback(response) { 
$log.debug(response);
$scope.search();
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
else 
{
annotationService.selectedEntity.show=false;

annotationService.update().then(function successCallback(response){
annotationService.setSelectedEntity(response.data);
updateParentEntities();
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
};
$scope.remove= function()
{
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
annotationService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!annotationService.isParent()) 
$scope.updateParent();
annotationService.del().then(function successCallback(response) { 
$log.debug(response);
if (annotationService.isParent()) 
{
$scope.search();
} else { 
annotationService.setSelectedEntity(null);
}
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.annotationAttributeGridApi!=undefined && $scope.annotationAttributeGridApi!=null)
 $scope.annotationAttributeGridApi.core.handleWindowResize(); 
if ($scope.fieldGridApi!=undefined && $scope.fieldGridApi!=null)
 $scope.fieldGridApi.core.handleWindowResize(); 
if ($scope.enumFieldGridApi!=undefined && $scope.enumFieldGridApi!=null)
 $scope.enumFieldGridApi.core.handleWindowResize(); 
if ($scope.relationshipGridApi!=undefined && $scope.relationshipGridApi!=null)
 $scope.relationshipGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
annotationService.loadFile(file,field).then(function successCallback(response) {
annotationService.setSelectedEntity(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
$scope.trueFalseValues=['',true,false];
$scope.showAnnotationAttributeDetail= function(index)
{
if (index!=null)
{
annotationAttributeService.searchOne(annotationService.selectedEntity.annotationAttributeList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
annotationAttributeService.setSelectedEntity(response.data[0]);
annotationAttributeService.selectedEntity.show=true;
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
else 
{
if (annotationService.selectedEntity.annotationAttribute==null || annotationService.selectedEntity.annotationAttribute==undefined)
{
annotationAttributeService.setSelectedEntity(null); 
annotationAttributeService.selectedEntity.show=true; 
$rootScope.openNode.annotationAttribute=true;
}
else
annotationAttributeService.searchOne(annotationService.selectedEntity.annotationAttribute).then(
function successCallback(response) {
annotationAttributeService.setSelectedEntity(response.data[0]);
annotationAttributeService.selectedEntity.show=true;
$rootScope.openNode.annotationAttribute=true;
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#annotationAttributeTabs li:eq(0) a').tab('show');
};
$scope.showFieldDetail= function(index)
{
if (index!=null)
{
fieldService.searchOne(annotationService.selectedEntity.fieldList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
else 
{
if (annotationService.selectedEntity.field==null || annotationService.selectedEntity.field==undefined)
{
fieldService.setSelectedEntity(null); 
fieldService.selectedEntity.show=true; 
$rootScope.openNode.field=true;
}
else
fieldService.searchOne(annotationService.selectedEntity.field).then(
function successCallback(response) {
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
$rootScope.openNode.field=true;
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#fieldTabs li:eq(0) a').tab('show');
};
$scope.showEnumFieldDetail= function(index)
{
if (index!=null)
{
enumFieldService.searchOne(annotationService.selectedEntity.enumFieldList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
enumFieldService.setSelectedEntity(response.data[0]);
enumFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
else 
{
if (annotationService.selectedEntity.enumField==null || annotationService.selectedEntity.enumField==undefined)
{
enumFieldService.setSelectedEntity(null); 
enumFieldService.selectedEntity.show=true; 
$rootScope.openNode.enumField=true;
}
else
enumFieldService.searchOne(annotationService.selectedEntity.enumField).then(
function successCallback(response) {
enumFieldService.setSelectedEntity(response.data[0]);
enumFieldService.selectedEntity.show=true;
$rootScope.openNode.enumField=true;
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#enumFieldTabs li:eq(0) a').tab('show');
};
$scope.showRelationshipDetail= function(index)
{
if (index!=null)
{
relationshipService.searchOne(annotationService.selectedEntity.relationshipList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
relationshipService.setSelectedEntity(response.data[0]);
relationshipService.selectedEntity.show=true;
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
else 
{
if (annotationService.selectedEntity.relationship==null || annotationService.selectedEntity.relationship==undefined)
{
relationshipService.setSelectedEntity(null); 
relationshipService.selectedEntity.show=true; 
$rootScope.openNode.relationship=true;
}
else
relationshipService.searchOne(annotationService.selectedEntity.relationship).then(
function successCallback(response) {
relationshipService.setSelectedEntity(response.data[0]);
relationshipService.selectedEntity.show=true;
$rootScope.openNode.relationship=true;
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#relationshipTabs li:eq(0) a').tab('show');
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("annotation.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedAnnotationAttribute= function() {
annotationService.selectedEntity.annotationAttributeList.push(annotationService.selectedEntity.annotationAttribute);
}
$scope.downloadAnnotationAttributeList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("annotationAttribute.xls",?) FROM ?',[mystyle,$scope.selectedEntity.annotationAttributeList]);
};
$scope.downloadFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("field.xls",?) FROM ?',[mystyle,$scope.selectedEntity.fieldList]);
};
$scope.downloadEnumFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("enumField.xls",?) FROM ?',[mystyle,$scope.selectedEntity.enumFieldList]);
};
$scope.downloadRelationshipList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("relationship.xls",?) FROM ?',[mystyle,$scope.selectedEntity.relationshipList]);
};
$scope.annotationGridOptions={};
UtilityService.cloneObject(annotationService.gridOptions,$scope.annotationGridOptions);
$scope.annotationGridOptions.data=annotationService.entityList;
$scope.initChildrenList = function () { 
}
$scope.annotationGridOptions.onRegisterApi = function(gridApi){
$scope.annotationGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
annotationService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.annotation=true;
annotationService.setSelectedEntity(response.data[0]);
});
angular.element('#annotationTabs li:eq(0) a').tab('show');
}
else 
annotationService.setSelectedEntity(null);
delete $rootScope.openNode.annotation;
annotationService.selectedEntity.show = row.isSelected;
});
  };
$scope.annotationAttributeGridOptions={};
UtilityService.cloneObject(annotationAttributeService.gridOptions,$scope.annotationAttributeGridOptions);
$scope.annotationAttributeGridOptions.data=$scope.selectedEntity.annotationAttributeList;
$scope.initChildrenList = function () { 
}
$scope.annotationAttributeGridOptions.onRegisterApi = function(gridApi){
$scope.annotationAttributeGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
annotationAttributeService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.annotationAttribute=true;
annotationAttributeService.setSelectedEntity(response.data[0]);
});
angular.element('#annotationAttributeTabs li:eq(0) a').tab('show');
}
else 
annotationAttributeService.setSelectedEntity(null);
delete $rootScope.openNode.annotationAttribute;
annotationAttributeService.selectedEntity.show = row.isSelected;
});
  };
$scope.fieldGridOptions={};
UtilityService.cloneObject(fieldService.gridOptions,$scope.fieldGridOptions);
$scope.fieldGridOptions.data=$scope.selectedEntity.fieldList;
$scope.initChildrenList = function () { 
}
$scope.fieldGridOptions.onRegisterApi = function(gridApi){
$scope.fieldGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
fieldService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.field=true;
fieldService.setSelectedEntity(response.data[0]);
});
angular.element('#fieldTabs li:eq(0) a').tab('show');
}
else 
fieldService.setSelectedEntity(null);
delete $rootScope.openNode.field;
fieldService.selectedEntity.show = row.isSelected;
});
  };
$scope.enumFieldGridOptions={};
UtilityService.cloneObject(enumFieldService.gridOptions,$scope.enumFieldGridOptions);
$scope.enumFieldGridOptions.data=$scope.selectedEntity.enumFieldList;
$scope.initChildrenList = function () { 
}
$scope.enumFieldGridOptions.onRegisterApi = function(gridApi){
$scope.enumFieldGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
enumFieldService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.enumField=true;
enumFieldService.setSelectedEntity(response.data[0]);
});
angular.element('#enumFieldTabs li:eq(0) a').tab('show');
}
else 
enumFieldService.setSelectedEntity(null);
delete $rootScope.openNode.enumField;
enumFieldService.selectedEntity.show = row.isSelected;
});
  };
$scope.relationshipGridOptions={};
UtilityService.cloneObject(relationshipService.gridOptions,$scope.relationshipGridOptions);
$scope.relationshipGridOptions.data=$scope.selectedEntity.relationshipList;
$scope.initChildrenList = function () { 
}
$scope.relationshipGridOptions.onRegisterApi = function(gridApi){
$scope.relationshipGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
relationshipService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.relationship=true;
relationshipService.setSelectedEntity(response.data[0]);
});
angular.element('#relationshipTabs li:eq(0) a').tab('show');
}
else 
relationshipService.setSelectedEntity(null);
delete $rootScope.openNode.relationship;
relationshipService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
annotationAttributeService.initAnnotationList().then(function(response) {
annotationService.preparedData.entityList=response.data;
});

if (annotationAttributeService.selectedEntity.annotationAttributeId!=undefined) annotationAttributeService.searchOne(annotationAttributeService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
annotationAttributeService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
fieldService.initAnnotationList().then(function(response) {
annotationService.preparedData.entityList=response.data;
});

if (fieldService.selectedEntity.fieldId!=undefined) fieldService.searchOne(fieldService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
fieldService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
enumFieldService.initAnnotationList().then(function(response) {
annotationService.preparedData.entityList=response.data;
});

if (enumFieldService.selectedEntity.enumFieldId!=undefined) enumFieldService.searchOne(enumFieldService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
enumFieldService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
relationshipService.initAnnotationList().then(function(response) {
annotationService.preparedData.entityList=response.data;
});

if (relationshipService.selectedEntity.relationshipId!=undefined) relationshipService.searchOne(relationshipService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
relationshipService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
$scope.closeEntityDetail = function(){ 
annotationService.setSelectedEntity(null);
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
}
}
})();
