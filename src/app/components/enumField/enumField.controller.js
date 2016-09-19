(function() { 

angular
.module("serverTestApp")
.controller("EnumFieldController",EnumFieldController);
/** @ngInject */
function EnumFieldController($scope,$http,$rootScope,$log,UtilityService ,enumFieldService, SecurityService, MainService ,annotationService,entityService,enumEntityService,tabService)
{
var vm=this;
vm.activeTab=1;
vm.searchBean=enumFieldService.searchBean;
vm.entityList=enumFieldService.entityList;
vm.selectedEntity=enumFieldService.selectedEntity;
vm.annotationPreparedData=annotationService.preparedData;
vm.entityPreparedData=entityService.preparedData;
vm.enumEntityPreparedData=enumEntityService.preparedData;
vm.tabPreparedData=tabService.preparedData;
function reset()
{
enumFieldService.resetSearchBean();
vm.searchBean=enumFieldService.searchBean;
enumFieldService.setSelectedEntity(null);
enumFieldService.selectedEntity.show=false;
enumFieldService.setEntityList(null); 
if (enumFieldService.isParent()) 
{
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
UtilityService.removeObjectFromList($rootScope.parentServices,annotationService);
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,enumEntityService);
tabService.selectedEntity.show=false;
delete $rootScope.openNode.tab;
UtilityService.removeObjectFromList($rootScope.parentServices,tabService);
}
}
function addNew()
{
$rootScope.openNode.enumField=true;
enumFieldService.setSelectedEntity(null);
enumFieldService.setEntityList(null);
enumFieldService.selectedEntity.show=true;
if (enumFieldService.isParent()) 
{
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
UtilityService.removeObjectFromList($rootScope.parentServices,annotationService);
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,enumEntityService);
tabService.selectedEntity.show=false;
delete $rootScope.openNode.tab;
UtilityService.removeObjectFromList($rootScope.parentServices,tabService);
}
//angular.element('#enumFieldTabs li:eq(0) a').tab('show');
}
		
function search()
{
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
enumFieldService.searchBean.annotationList=[];
enumFieldService.searchBean.annotationList.push(enumFieldService.searchBean.annotation);
delete enumFieldService.searchBean.annotation; 
enumFieldService.search().then(function successCallback(response) {
enumFieldService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
function insert()
{
if (!vm.enumFieldDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
enumFieldService.insert().then(function successCallback(response) { 
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
enumFieldService.selectedEntity.show=false;
enumFieldService.insert().then(function successCallBack(response) { 
$log.debug(response);
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeenumField(enumFieldService.selectedEntity);
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
if (!vm.enumFieldDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
UtilityService.removeObjectFromList($rootScope.parentServices,annotationService);
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,enumEntityService);
tabService.selectedEntity.show=false;
delete $rootScope.openNode.tab;
UtilityService.removeObjectFromList($rootScope.parentServices,tabService);
enumFieldService.update().then(function successCallback(response) { 
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
enumFieldService.selectedEntity.show=false;

enumFieldService.update().then(function successCallback(response){
enumFieldService.setSelectedEntity(response.data);
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
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeEnumField(enumFieldService.selectedEntity);
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
enumFieldService.setSelectedEntity(null);
}
function del()
{
$rootScope.parentServices.pop();
enumFieldService.del().then(function successCallback(response) { 
$log.debug(response);
enumFieldService.setSelectedEntity(null);
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
function loadFile(file,field)
{
enumFieldService.loadFile(file,field).then(function successCallback(response) {
enumFieldService.setSelectedEntity(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
vm.trueFalseValues=['',true,false];
 function showAnnotationDetail(index)
{
if (index!=null)
{
annotationService.searchOne(enumFieldService.selectedEntity.annotationList[index]).then(
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
$rootScope.parentServices.push(annotationService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
//angular.element('#annotationTabs li:eq(0) a').tab('show');
}
vm.showAnnotationDetail=showAnnotationDetail;
 function showEntityDetail(index)
{
if (index!=null)
{
entityService.searchOne(enumFieldService.selectedEntity.entityList[index]).then(
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
$rootScope.parentServices.push(entityService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
//angular.element('#entityTabs li:eq(0) a').tab('show');
}
vm.showEntityDetail=showEntityDetail;
 function showEnumEntityDetail(index)
{
if (index!=null)
{
enumEntityService.searchOne(enumFieldService.selectedEntity.enumEntityList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
enumEntityService.setSelectedEntity(response.data[0]);
enumEntityService.selectedEntity.show=true;
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
$rootScope.parentServices.push(enumEntityService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
//angular.element('#enumEntityTabs li:eq(0) a').tab('show');
}
vm.showEnumEntityDetail=showEnumEntityDetail;
 function showTabDetail(index)
{
if (index!=null)
{
tabService.searchOne(enumFieldService.selectedEntity.tabList[index]).then(
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
$rootScope.parentServices.push(tabService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
//angular.element('#tabTabs li:eq(0) a').tab('show');
}
vm.showTabDetail=showTabDetail;
function downloadList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("enumField.xls",?) FROM ?',[mystyle,vm.entityList]);
}
function saveLinkedAnnotation() {
enumFieldService.selectedEntity.annotationList.push(enumFieldService.selectedEntity.annotation);
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
function downloadEntityList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,vm.selectedEntity.entityList]);
}
vm.downloadEntityList=downloadEntityList;
function downloadEnumEntityList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("enumEntity.xls",?) FROM ?',[mystyle,vm.selectedEntity.enumEntityList]);
}
vm.downloadEnumEntityList=downloadEnumEntityList;
function downloadTabList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("tab.xls",?) FROM ?',[mystyle,vm.selectedEntity.tabList]);
}
vm.downloadTabList=downloadTabList;
vm.enumFieldGridOptions={};
UtilityService.cloneObject(enumFieldService.gridOptions,vm.enumFieldGridOptions);
vm.enumFieldGridOptions.data=enumFieldService.entityList;
vm.initChildrenList = function () { 
}
vm.enumFieldGridOptions.onRegisterApi = function(gridApi){
vm.enumFieldGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
vm.activeTab=1;
enumFieldService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.enumField=true;
$rootScope.parentServices.push(enumFieldService);
enumFieldService.setSelectedEntity(response.data[0]);
});
//angular.element('#enumFieldTabs li:eq(0) a').tab('show');
}
else 
enumFieldService.setSelectedEntity(null);
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
enumFieldService.selectedEntity.show = row.isSelected;
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
vm.activeTab=1;
annotationService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.annotation=true;
$rootScope.parentServices.push(annotationService);
annotationService.setSelectedEntity(response.data[0]);
});
//angular.element('#annotationTabs li:eq(0) a').tab('show');
}
else 
annotationService.setSelectedEntity(null);
delete $rootScope.openNode.annotation;
UtilityService.removeObjectFromList($rootScope.parentServices,annotationService);
annotationService.selectedEntity.show = row.isSelected;
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
vm.activeTab=1;
entityService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.entity=true;
$rootScope.parentServices.push(entityService);
entityService.setSelectedEntity(response.data[0]);
});
//angular.element('#entityTabs li:eq(0) a').tab('show');
}
else 
entityService.setSelectedEntity(null);
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
entityService.selectedEntity.show = row.isSelected;
});
  };
vm.enumEntityGridOptions={};
UtilityService.cloneObject(enumEntityService.gridOptions,vm.enumEntityGridOptions);
vm.enumEntityGridOptions.data=vm.selectedEntity.enumEntityList;
vm.initChildrenList = function () { 
}
vm.enumEntityGridOptions.onRegisterApi = function(gridApi){
vm.enumEntityGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
vm.activeTab=1;
enumEntityService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.enumEntity=true;
$rootScope.parentServices.push(enumEntityService);
enumEntityService.setSelectedEntity(response.data[0]);
});
//angular.element('#enumEntityTabs li:eq(0) a').tab('show');
}
else 
enumEntityService.setSelectedEntity(null);
delete $rootScope.openNode.enumEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,enumEntityService);
enumEntityService.selectedEntity.show = row.isSelected;
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
vm.activeTab=1;
tabService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.tab=true;
$rootScope.parentServices.push(tabService);
tabService.setSelectedEntity(response.data[0]);
});
//angular.element('#tabTabs li:eq(0) a').tab('show');
}
else 
tabService.setSelectedEntity(null);
delete $rootScope.openNode.tab;
UtilityService.removeObjectFromList($rootScope.parentServices,tabService);
tabService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
entityService.initEnumFieldList().then(function(response) {
enumFieldService.preparedData.entityList=response.data;
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
tabService.initEnumFieldList().then(function(response) {
enumFieldService.preparedData.entityList=response.data;
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
enumEntityService.initEnumFieldList().then(function(response) {
enumFieldService.preparedData.entityList=response.data;
});

if (enumEntityService.selectedEntity.enumEntityId!=undefined) enumEntityService.searchOne(enumEntityService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
enumEntityService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
annotationService.initEnumFieldList().then(function(response) {
enumFieldService.preparedData.entityList=response.data;
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
enumFieldService.setSelectedEntity(null);
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
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
