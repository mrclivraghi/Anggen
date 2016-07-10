(function() { 

angular
.module("serverTestApp")
.controller("AnnotationController",AnnotationController);
/** @ngInject */
function AnnotationController($scope,$http,$rootScope,$log,UtilityService ,annotationService, SecurityService, MainService ,annotationAttributeService,generationRunService,enumFieldService,relationshipService)
{
var vm=this;
vm.activeTab=1;
vm.searchBean=annotationService.searchBean;
vm.entityList=annotationService.entityList;
vm.selectedEntity=annotationService.selectedEntity;
vm.annotationAttributePreparedData=annotationAttributeService.preparedData;
vm.generationRunPreparedData=generationRunService.preparedData;
vm.enumFieldPreparedData=enumFieldService.preparedData;
vm.relationshipPreparedData=relationshipService.preparedData;
vm.annotationTypePreparedData={};
vm.annotationTypePreparedData.entityList=["PRIMARY_KEY","NOT_NULL","NOT_BLANK","DESCRIPTION_FIELD","BETWEEN_FILTER","EXCEL_EXPORT","FILTER_FIELD","IGNORE_SEARCH","IGNORE_UPDATE","IGNORE_TABLE_LIST","SIZE","PASSWORD","PRIORITY","EMBEDDED","CACHE","INCLUDE_SEARCH","INCLUDE_TABLE_LIST","INCLUDE_UPDATE" ];
function reset()
{
annotationService.resetSearchBean();
vm.searchBean=annotationService.searchBean;
annotationService.setSelectedEntity(null);
annotationService.selectedEntity.show=false;
annotationService.setEntityList(null); 
if (annotationService.isParent()) 
{
annotationAttributeService.selectedEntity.show=false;
delete $rootScope.openNode.annotationAttribute;
UtilityService.removeObjectFromList($rootScope.parentServices,annotationAttributeService);
generationRunService.selectedEntity.show=false;
delete $rootScope.openNode.generationRun;
UtilityService.removeObjectFromList($rootScope.parentServices,generationRunService);
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
relationshipService.selectedEntity.show=false;
delete $rootScope.openNode.relationship;
UtilityService.removeObjectFromList($rootScope.parentServices,relationshipService);
}
}
function addNew()
{
$rootScope.openNode.annotation=true;
annotationService.setSelectedEntity(null);
annotationService.setEntityList(null);
annotationService.selectedEntity.show=true;
if (annotationService.isParent()) 
{
annotationAttributeService.selectedEntity.show=false;
delete $rootScope.openNode.annotationAttribute;
UtilityService.removeObjectFromList($rootScope.parentServices,annotationAttributeService);
generationRunService.selectedEntity.show=false;
delete $rootScope.openNode.generationRun;
UtilityService.removeObjectFromList($rootScope.parentServices,generationRunService);
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
relationshipService.selectedEntity.show=false;
delete $rootScope.openNode.relationship;
UtilityService.removeObjectFromList($rootScope.parentServices,relationshipService);
}
angular.element('#annotationTabs li:eq(0) a').tab('show');
}
		
function search()
{
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
UtilityService.removeObjectFromList($rootScope.parentServices,annotationService);
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
}
function insert()
{
if (!$scope.annotationDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
annotationService.insert().then(function successCallback(response) { 
$log.debug(response);
vm.search();
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
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeannotation(annotationService.selectedEntity);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
}
function update()
{
if (!$scope.annotationDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
annotationAttributeService.selectedEntity.show=false;
delete $rootScope.openNode.annotationAttribute;
UtilityService.removeObjectFromList($rootScope.parentServices,annotationAttributeService);
generationRunService.selectedEntity.show=false;
delete $rootScope.openNode.generationRun;
UtilityService.removeObjectFromList($rootScope.parentServices,generationRunService);
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
relationshipService.selectedEntity.show=false;
delete $rootScope.openNode.relationship;
UtilityService.removeObjectFromList($rootScope.parentServices,relationshipService);
annotationService.update().then(function successCallback(response) { 
$log.debug(response);
vm.search();
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
}
function remove()
{
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeAnnotation(annotationService.selectedEntity);
UtilityService.removeObjectFromList($rootScope.parentServices,annotationService);
annotationService.setSelectedEntity(null);
}
function del()
{
$rootScope.parentServices.pop();
annotationService.del().then(function successCallback(response) { 
$log.debug(response);
annotationService.setSelectedEntity(null);
if ($rootScope.parentServices.length==0) 
{
vm.search();
} else { 
}
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
function refreshTableDetail() 
{
if ($scope.annotationAttributeGridApi!=undefined && $scope.annotationAttributeGridApi!=null)
 $scope.annotationAttributeGridApi.core.handleWindowResize(); 
if ($scope.generationRunGridApi!=undefined && $scope.generationRunGridApi!=null)
 $scope.generationRunGridApi.core.handleWindowResize(); 
if ($scope.enumFieldGridApi!=undefined && $scope.enumFieldGridApi!=null)
 $scope.enumFieldGridApi.core.handleWindowResize(); 
if ($scope.relationshipGridApi!=undefined && $scope.relationshipGridApi!=null)
 $scope.relationshipGridApi.core.handleWindowResize(); 
}
vm.refreshTableDetail=refreshTableDetail;
function loadFile(file,field)
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
vm.trueFalseValues=['',true,false];
 function showAnnotationAttributeDetail(index)
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
$rootScope.parentServices.push(annotationAttributeService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#annotationAttributeTabs li:eq(0) a').tab('show');
}
vm.showAnnotationAttributeDetail=showAnnotationAttributeDetail;
 function showGenerationRunDetail(index)
{
if (index!=null)
{
generationRunService.searchOne(annotationService.selectedEntity.generationRunList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
generationRunService.setSelectedEntity(response.data[0]);
generationRunService.selectedEntity.show=true;
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
if (annotationService.selectedEntity.generationRun==null || annotationService.selectedEntity.generationRun==undefined)
{
generationRunService.setSelectedEntity(null); 
generationRunService.selectedEntity.show=true; 
$rootScope.openNode.generationRun=true;
}
else
generationRunService.searchOne(annotationService.selectedEntity.generationRun).then(
function successCallback(response) {
generationRunService.setSelectedEntity(response.data[0]);
generationRunService.selectedEntity.show=true;
$rootScope.openNode.generationRun=true;
$rootScope.parentServices.push(generationRunService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#generationRunTabs li:eq(0) a').tab('show');
}
vm.showGenerationRunDetail=showGenerationRunDetail;
 function showEnumFieldDetail(index)
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
$rootScope.parentServices.push(enumFieldService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#enumFieldTabs li:eq(0) a').tab('show');
}
vm.showEnumFieldDetail=showEnumFieldDetail;
 function showRelationshipDetail(index)
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
$rootScope.parentServices.push(relationshipService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#relationshipTabs li:eq(0) a').tab('show');
}
vm.showRelationshipDetail=showRelationshipDetail;
function downloadList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("annotation.xls",?) FROM ?',[mystyle,vm.entityList]);
}
function saveLinkedAnnotationAttribute() {
annotationService.selectedEntity.annotationAttributeList.push(annotationService.selectedEntity.annotationAttribute);
}
vm.saveLinkedAnnotationAttribute=saveLinkedAnnotationAttribute;
function downloadAnnotationAttributeList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("annotationAttribute.xls",?) FROM ?',[mystyle,vm.selectedEntity.annotationAttributeList]);
}
vm.downloadAnnotationAttributeList=downloadAnnotationAttributeList;
function downloadGenerationRunList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("generationRun.xls",?) FROM ?',[mystyle,vm.selectedEntity.generationRunList]);
}
vm.downloadGenerationRunList=downloadGenerationRunList;
function downloadEnumFieldList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("enumField.xls",?) FROM ?',[mystyle,vm.selectedEntity.enumFieldList]);
}
vm.downloadEnumFieldList=downloadEnumFieldList;
function downloadRelationshipList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("relationship.xls",?) FROM ?',[mystyle,vm.selectedEntity.relationshipList]);
}
vm.downloadRelationshipList=downloadRelationshipList;
vm.annotationGridOptions={};
UtilityService.cloneObject(annotationService.gridOptions,vm.annotationGridOptions);
vm.annotationGridOptions.data=annotationService.entityList;
vm.initChildrenList = function () { 
}
vm.annotationGridOptions.onRegisterApi = function(gridApi){
vm.annotationGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
annotationService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.annotation=true;
$rootScope.parentServices.push(annotationService);
annotationService.setSelectedEntity(response.data[0]);
});
angular.element('#annotationTabs li:eq(0) a').tab('show');
}
else 
annotationService.setSelectedEntity(null);
delete $rootScope.openNode.annotation;
UtilityService.removeObjectFromList($rootScope.parentServices,annotationService);
annotationService.selectedEntity.show = row.isSelected;
});
  };
vm.annotationAttributeGridOptions={};
UtilityService.cloneObject(annotationAttributeService.gridOptions,vm.annotationAttributeGridOptions);
vm.annotationAttributeGridOptions.data=vm.selectedEntity.annotationAttributeList;
vm.initChildrenList = function () { 
}
vm.annotationAttributeGridOptions.onRegisterApi = function(gridApi){
vm.annotationAttributeGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
annotationAttributeService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.annotationAttribute=true;
$rootScope.parentServices.push(annotationAttributeService);
annotationAttributeService.setSelectedEntity(response.data[0]);
});
angular.element('#annotationAttributeTabs li:eq(0) a').tab('show');
}
else 
annotationAttributeService.setSelectedEntity(null);
delete $rootScope.openNode.annotationAttribute;
UtilityService.removeObjectFromList($rootScope.parentServices,annotationAttributeService);
annotationAttributeService.selectedEntity.show = row.isSelected;
});
  };
vm.generationRunGridOptions={};
UtilityService.cloneObject(generationRunService.gridOptions,vm.generationRunGridOptions);
vm.generationRunGridOptions.data=vm.selectedEntity.generationRunList;
vm.initChildrenList = function () { 
}
vm.generationRunGridOptions.onRegisterApi = function(gridApi){
vm.generationRunGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
generationRunService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.generationRun=true;
$rootScope.parentServices.push(generationRunService);
generationRunService.setSelectedEntity(response.data[0]);
});
angular.element('#generationRunTabs li:eq(0) a').tab('show');
}
else 
generationRunService.setSelectedEntity(null);
delete $rootScope.openNode.generationRun;
UtilityService.removeObjectFromList($rootScope.parentServices,generationRunService);
generationRunService.selectedEntity.show = row.isSelected;
});
  };
vm.enumFieldGridOptions={};
UtilityService.cloneObject(enumFieldService.gridOptions,vm.enumFieldGridOptions);
vm.enumFieldGridOptions.data=vm.selectedEntity.enumFieldList;
vm.initChildrenList = function () { 
}
vm.enumFieldGridOptions.onRegisterApi = function(gridApi){
vm.enumFieldGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
enumFieldService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.enumField=true;
$rootScope.parentServices.push(enumFieldService);
enumFieldService.setSelectedEntity(response.data[0]);
});
angular.element('#enumFieldTabs li:eq(0) a').tab('show');
}
else 
enumFieldService.setSelectedEntity(null);
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
enumFieldService.selectedEntity.show = row.isSelected;
});
  };
vm.relationshipGridOptions={};
UtilityService.cloneObject(relationshipService.gridOptions,vm.relationshipGridOptions);
vm.relationshipGridOptions.data=vm.selectedEntity.relationshipList;
vm.initChildrenList = function () { 
}
vm.relationshipGridOptions.onRegisterApi = function(gridApi){
vm.relationshipGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
relationshipService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.relationship=true;
$rootScope.parentServices.push(relationshipService);
relationshipService.setSelectedEntity(response.data[0]);
});
angular.element('#relationshipTabs li:eq(0) a').tab('show');
}
else 
relationshipService.setSelectedEntity(null);
delete $rootScope.openNode.relationship;
UtilityService.removeObjectFromList($rootScope.parentServices,relationshipService);
relationshipService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
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
}
function closeEntityDetail(){ 
annotationService.setSelectedEntity(null);
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
UtilityService.removeObjectFromList($rootScope.parentServices,annotationService);
}
vm.reset=reset;
vm.addNew=addNew;
vm.insert=insert;
vm.update=update;
vm.search=search;
vm.remove=remove;
vm.del=del;
vm.loadFile=loadFile;
vm.downloadList=downloadList;
vm.closeEntityDetail=closeEntityDetail;
}
})();
