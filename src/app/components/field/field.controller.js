(function() { 

angular
.module("serverTest")
.controller("FieldController",FieldController);
/** @ngInject */
function FieldController($scope,$http,$rootScope,$log,UtilityService ,fieldService, SecurityService, MainService ,restrictionFieldService,entityService,tabService,annotationService)
{
var vm=this;
vm.activeTab=1;
vm.searchBean=fieldService.searchBean;
vm.entityList=fieldService.entityList;
vm.selectedEntity=fieldService.selectedEntity;
vm.restrictionFieldPreparedData=restrictionFieldService.preparedData;
vm.entityPreparedData=entityService.preparedData;
vm.tabPreparedData=tabService.preparedData;
vm.annotationPreparedData=annotationService.preparedData;
vm.fieldTypePreparedData={};
vm.fieldTypePreparedData.entityList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG","FILE" ];
function reset()
{
fieldService.resetSearchBean();
vm.searchBean=fieldService.searchBean;
fieldService.setSelectedEntity(null);
fieldService.selectedEntity.show=false;
fieldService.setEntityList(null); 
if (fieldService.isParent()) 
{
restrictionFieldService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionField;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionFieldService);
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
tabService.selectedEntity.show=false;
delete $rootScope.openNode.tab;
UtilityService.removeObjectFromList($rootScope.parentServices,tabService);
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
UtilityService.removeObjectFromList($rootScope.parentServices,annotationService);
}
}
function addNew()
{
$rootScope.openNode.field=true;
fieldService.setSelectedEntity(null);
fieldService.setEntityList(null);
fieldService.selectedEntity.show=true;
if (fieldService.isParent()) 
{
restrictionFieldService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionField;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionFieldService);
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
tabService.selectedEntity.show=false;
delete $rootScope.openNode.tab;
UtilityService.removeObjectFromList($rootScope.parentServices,tabService);
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
UtilityService.removeObjectFromList($rootScope.parentServices,annotationService);
}
angular.element('#fieldTabs li:eq(0) a').tab('show');
}
		
function search()
{
fieldService.selectedEntity.show=false;
delete $rootScope.openNode.field;
UtilityService.removeObjectFromList($rootScope.parentServices,fieldService);
fieldService.searchBean.restrictionFieldList=[];
fieldService.searchBean.restrictionFieldList.push(fieldService.searchBean.restrictionField);
delete fieldService.searchBean.restrictionField; 
fieldService.searchBean.annotationList=[];
fieldService.searchBean.annotationList.push(fieldService.searchBean.annotation);
delete fieldService.searchBean.annotation; 
fieldService.search().then(function successCallback(response) {
fieldService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
function insert()
{
if (!$scope.fieldDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
fieldService.insert().then(function successCallback(response) { 
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
fieldService.selectedEntity.show=false;
fieldService.insert().then(function successCallBack(response) { 
$log.debug(response);
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removefield(fieldService.selectedEntity);
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
if (!$scope.fieldDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
restrictionFieldService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionField;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionFieldService);
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
tabService.selectedEntity.show=false;
delete $rootScope.openNode.tab;
UtilityService.removeObjectFromList($rootScope.parentServices,tabService);
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
UtilityService.removeObjectFromList($rootScope.parentServices,annotationService);
fieldService.update().then(function successCallback(response) { 
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
fieldService.selectedEntity.show=false;

fieldService.update().then(function successCallback(response){
fieldService.setSelectedEntity(response.data);
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
fieldService.selectedEntity.show=false;
delete $rootScope.openNode.field;
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeField(fieldService.selectedEntity);
UtilityService.removeObjectFromList($rootScope.parentServices,fieldService);
fieldService.setSelectedEntity(null);
}
function del()
{
$rootScope.parentServices.pop();
fieldService.del().then(function successCallback(response) { 
$log.debug(response);
fieldService.setSelectedEntity(null);
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
if ($scope.restrictionFieldGridApi!=undefined && $scope.restrictionFieldGridApi!=null)
 $scope.restrictionFieldGridApi.core.handleWindowResize(); 
if ($scope.entityGridApi!=undefined && $scope.entityGridApi!=null)
 $scope.entityGridApi.core.handleWindowResize(); 
if ($scope.tabGridApi!=undefined && $scope.tabGridApi!=null)
 $scope.tabGridApi.core.handleWindowResize(); 
if ($scope.annotationGridApi!=undefined && $scope.annotationGridApi!=null)
 $scope.annotationGridApi.core.handleWindowResize(); 
}
vm.refreshTableDetail=refreshTableDetail;
function loadFile(file,field)
{
fieldService.loadFile(file,field).then(function successCallback(response) {
fieldService.setSelectedEntity(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
vm.trueFalseValues=['',true,false];
 function showRestrictionFieldDetail(index)
{
if (index!=null)
{
restrictionFieldService.searchOne(fieldService.selectedEntity.restrictionFieldList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
restrictionFieldService.setSelectedEntity(response.data[0]);
restrictionFieldService.selectedEntity.show=true;
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
$rootScope.parentServices.push(restrictionFieldService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#restrictionFieldTabs li:eq(0) a').tab('show');
}
vm.showRestrictionFieldDetail=showRestrictionFieldDetail;
 function showEntityDetail(index)
{
if (index!=null)
{
entityService.searchOne(fieldService.selectedEntity.entityList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
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
$rootScope.parentServices.push(entityService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#entityTabs li:eq(0) a').tab('show');
}
vm.showEntityDetail=showEntityDetail;
 function showTabDetail(index)
{
if (index!=null)
{
tabService.searchOne(fieldService.selectedEntity.tabList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
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
$rootScope.parentServices.push(tabService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#tabTabs li:eq(0) a').tab('show');
}
vm.showTabDetail=showTabDetail;
 function showAnnotationDetail(index)
{
if (index!=null)
{
annotationService.searchOne(fieldService.selectedEntity.annotationList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
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
$rootScope.parentServices.push(annotationService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#annotationTabs li:eq(0) a').tab('show');
}
vm.showAnnotationDetail=showAnnotationDetail;
function downloadList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("field.xls",?) FROM ?',[mystyle,vm.entityList]);
}
function saveLinkedRestrictionField() {
fieldService.selectedEntity.restrictionFieldList.push(fieldService.selectedEntity.restrictionField);
}
vm.saveLinkedRestrictionField=saveLinkedRestrictionField;
function downloadRestrictionFieldList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("restrictionField.xls",?) FROM ?',[mystyle,vm.selectedEntity.restrictionFieldList]);
}
vm.downloadRestrictionFieldList=downloadRestrictionFieldList;
function downloadEntityList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,vm.selectedEntity.entityList]);
}
vm.downloadEntityList=downloadEntityList;
function downloadTabList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("tab.xls",?) FROM ?',[mystyle,vm.selectedEntity.tabList]);
}
vm.downloadTabList=downloadTabList;
function saveLinkedAnnotation() {
fieldService.selectedEntity.annotationList.push(fieldService.selectedEntity.annotation);
}
vm.saveLinkedAnnotation=saveLinkedAnnotation;
function downloadAnnotationList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("annotation.xls",?) FROM ?',[mystyle,vm.selectedEntity.annotationList]);
}
vm.downloadAnnotationList=downloadAnnotationList;
vm.fieldGridOptions={};
UtilityService.cloneObject(fieldService.gridOptions,vm.fieldGridOptions);
vm.fieldGridOptions.data=fieldService.entityList;
vm.initChildrenList = function () { 
}
vm.fieldGridOptions.onRegisterApi = function(gridApi){
vm.fieldGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
fieldService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.field=true;
$rootScope.parentServices.push(fieldService);
fieldService.setSelectedEntity(response.data[0]);
});
angular.element('#fieldTabs li:eq(0) a').tab('show');
}
else 
fieldService.setSelectedEntity(null);
delete $rootScope.openNode.field;
UtilityService.removeObjectFromList($rootScope.parentServices,fieldService);
fieldService.selectedEntity.show = row.isSelected;
});
  };
vm.restrictionFieldGridOptions={};
UtilityService.cloneObject(restrictionFieldService.gridOptions,vm.restrictionFieldGridOptions);
vm.restrictionFieldGridOptions.data=vm.selectedEntity.restrictionFieldList;
vm.initChildrenList = function () { 
}
vm.restrictionFieldGridOptions.onRegisterApi = function(gridApi){
vm.restrictionFieldGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionFieldService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.restrictionField=true;
$rootScope.parentServices.push(restrictionFieldService);
restrictionFieldService.setSelectedEntity(response.data[0]);
});
angular.element('#restrictionFieldTabs li:eq(0) a').tab('show');
}
else 
restrictionFieldService.setSelectedEntity(null);
delete $rootScope.openNode.restrictionField;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionFieldService);
restrictionFieldService.selectedEntity.show = row.isSelected;
});
  };
vm.entityGridOptions={};
UtilityService.cloneObject(entityService.gridOptions,vm.entityGridOptions);
vm.entityGridOptions.data=vm.selectedEntity.entityList;
vm.initChildrenList = function () { 
}
vm.entityGridOptions.onRegisterApi = function(gridApi){
vm.entityGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
entityService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.entity=true;
$rootScope.parentServices.push(entityService);
entityService.setSelectedEntity(response.data[0]);
});
angular.element('#entityTabs li:eq(0) a').tab('show');
}
else 
entityService.setSelectedEntity(null);
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
entityService.selectedEntity.show = row.isSelected;
});
  };
vm.tabGridOptions={};
UtilityService.cloneObject(tabService.gridOptions,vm.tabGridOptions);
vm.tabGridOptions.data=vm.selectedEntity.tabList;
vm.initChildrenList = function () { 
}
vm.tabGridOptions.onRegisterApi = function(gridApi){
vm.tabGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
tabService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.tab=true;
$rootScope.parentServices.push(tabService);
tabService.setSelectedEntity(response.data[0]);
});
angular.element('#tabTabs li:eq(0) a').tab('show');
}
else 
tabService.setSelectedEntity(null);
delete $rootScope.openNode.tab;
UtilityService.removeObjectFromList($rootScope.parentServices,tabService);
tabService.selectedEntity.show = row.isSelected;
});
  };
vm.annotationGridOptions={};
UtilityService.cloneObject(annotationService.gridOptions,vm.annotationGridOptions);
vm.annotationGridOptions.data=vm.selectedEntity.annotationList;
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
function updateParentEntities() { 
tabService.initFieldList().then(function(response) {
fieldService.preparedData.entityList=response.data;
});

if (tabService.selectedEntity.tabId!=undefined) tabService.searchOne(tabService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
tabService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
restrictionFieldService.initFieldList().then(function(response) {
fieldService.preparedData.entityList=response.data;
});

if (restrictionFieldService.selectedEntity.restrictionFieldId!=undefined) restrictionFieldService.searchOne(restrictionFieldService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
restrictionFieldService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
entityService.initFieldList().then(function(response) {
fieldService.preparedData.entityList=response.data;
});

if (entityService.selectedEntity.entityId!=undefined) entityService.searchOne(entityService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
entityService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
annotationService.initFieldList().then(function(response) {
fieldService.preparedData.entityList=response.data;
});

if (annotationService.selectedEntity.annotationId!=undefined) annotationService.searchOne(annotationService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
annotationService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
function closeEntityDetail(){ 
fieldService.setSelectedEntity(null);
fieldService.selectedEntity.show=false;
delete $rootScope.openNode.field;
UtilityService.removeObjectFromList($rootScope.parentServices,fieldService);
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
