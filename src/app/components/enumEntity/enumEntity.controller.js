(function() { 

angular
.module("serverTestApp")
.controller("EnumEntityController",EnumEntityController);
/** @ngInject */
function EnumEntityController($scope,$http,$rootScope,$log,UtilityService ,enumEntityService, SecurityService, MainService ,projectService,enumValueService,enumFieldService)
{
var vm=this;
vm.activeTab=1;
vm.searchBean=enumEntityService.searchBean;
vm.entityList=enumEntityService.entityList;
vm.selectedEntity=enumEntityService.selectedEntity;
vm.projectPreparedData=projectService.preparedData;
vm.enumValuePreparedData=enumValueService.preparedData;
vm.enumFieldPreparedData=enumFieldService.preparedData;
function reset()
{
enumEntityService.resetSearchBean();
vm.searchBean=enumEntityService.searchBean;
enumEntityService.setSelectedEntity(null);
enumEntityService.selectedEntity.show=false;
enumEntityService.setEntityList(null); 
if (enumEntityService.isParent()) 
{
projectService.selectedEntity.show=false;
delete $rootScope.openNode.project;
UtilityService.removeObjectFromList($rootScope.parentServices,projectService);
enumValueService.selectedEntity.show=false;
delete $rootScope.openNode.enumValue;
UtilityService.removeObjectFromList($rootScope.parentServices,enumValueService);
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
}
}
function addNew()
{
$rootScope.openNode.enumEntity=true;
enumEntityService.setSelectedEntity(null);
enumEntityService.setEntityList(null);
enumEntityService.selectedEntity.show=true;
if (enumEntityService.isParent()) 
{
projectService.selectedEntity.show=false;
delete $rootScope.openNode.project;
UtilityService.removeObjectFromList($rootScope.parentServices,projectService);
enumValueService.selectedEntity.show=false;
delete $rootScope.openNode.enumValue;
UtilityService.removeObjectFromList($rootScope.parentServices,enumValueService);
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
}
//angular.element('#enumEntityTabs li:eq(0) a').tab('show');
}
		
function search()
{
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,enumEntityService);
enumEntityService.searchBean.enumValueList=[];
enumEntityService.searchBean.enumValueList.push(enumEntityService.searchBean.enumValue);
delete enumEntityService.searchBean.enumValue; 
enumEntityService.searchBean.enumFieldList=[];
enumEntityService.searchBean.enumFieldList.push(enumEntityService.searchBean.enumField);
delete enumEntityService.searchBean.enumField; 
enumEntityService.search().then(function successCallback(response) {
enumEntityService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
function insert()
{
if (!vm.enumEntityDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
enumEntityService.insert().then(function successCallback(response) { 
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
enumEntityService.selectedEntity.show=false;
enumEntityService.insert().then(function successCallBack(response) { 
$log.debug(response);
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeenumEntity(enumEntityService.selectedEntity);
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
if (!vm.enumEntityDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
projectService.selectedEntity.show=false;
delete $rootScope.openNode.project;
UtilityService.removeObjectFromList($rootScope.parentServices,projectService);
enumValueService.selectedEntity.show=false;
delete $rootScope.openNode.enumValue;
UtilityService.removeObjectFromList($rootScope.parentServices,enumValueService);
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
enumEntityService.update().then(function successCallback(response) { 
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
enumEntityService.selectedEntity.show=false;

enumEntityService.update().then(function successCallback(response){
enumEntityService.setSelectedEntity(response.data);
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
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeEnumEntity(enumEntityService.selectedEntity);
UtilityService.removeObjectFromList($rootScope.parentServices,enumEntityService);
enumEntityService.setSelectedEntity(null);
}
function del()
{
$rootScope.parentServices.pop();
enumEntityService.del().then(function successCallback(response) { 
$log.debug(response);
enumEntityService.setSelectedEntity(null);
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
enumEntityService.loadFile(file,field).then(function successCallback(response) {
enumEntityService.setSelectedEntity(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
vm.trueFalseValues=['',true,false];
 function showProjectDetail(index)
{
if (index!=null)
{
projectService.searchOne(enumEntityService.selectedEntity.projectList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
projectService.setSelectedEntity(response.data[0]);
projectService.selectedEntity.show=true;
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
$rootScope.parentServices.push(projectService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
//angular.element('#projectTabs li:eq(0) a').tab('show');
}
vm.showProjectDetail=showProjectDetail;
 function showEnumValueDetail(index)
{
if (index!=null)
{
enumValueService.searchOne(enumEntityService.selectedEntity.enumValueList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
enumValueService.setSelectedEntity(response.data[0]);
enumValueService.selectedEntity.show=true;
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
$rootScope.parentServices.push(enumValueService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
//angular.element('#enumValueTabs li:eq(0) a').tab('show');
}
vm.showEnumValueDetail=showEnumValueDetail;
 function showEnumFieldDetail(index)
{
if (index!=null)
{
enumFieldService.searchOne(enumEntityService.selectedEntity.enumFieldList[index]).then(
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
if (enumEntityService.selectedEntity.enumField==null || enumEntityService.selectedEntity.enumField==undefined)
{
enumFieldService.setSelectedEntity(null); 
enumFieldService.selectedEntity.show=true; 
$rootScope.openNode.enumField=true;
}
else
enumFieldService.searchOne(enumEntityService.selectedEntity.enumField).then(
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
//angular.element('#enumFieldTabs li:eq(0) a').tab('show');
}
vm.showEnumFieldDetail=showEnumFieldDetail;
function downloadList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("enumEntity.xls",?) FROM ?',[mystyle,vm.entityList]);
}
function downloadProjectList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("project.xls",?) FROM ?',[mystyle,vm.selectedEntity.projectList]);
}
vm.downloadProjectList=downloadProjectList;
function saveLinkedEnumValue() {
enumEntityService.selectedEntity.enumValueList.push(enumEntityService.selectedEntity.enumValue);
}
vm.saveLinkedEnumValue=saveLinkedEnumValue;
function downloadEnumValueList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("enumValue.xls",?) FROM ?',[mystyle,vm.selectedEntity.enumValueList]);
}
vm.downloadEnumValueList=downloadEnumValueList;
function saveLinkedEnumField() {
enumEntityService.selectedEntity.enumFieldList.push(enumEntityService.selectedEntity.enumField);
}
vm.saveLinkedEnumField=saveLinkedEnumField;
function downloadEnumFieldList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("enumField.xls",?) FROM ?',[mystyle,vm.selectedEntity.enumFieldList]);
}
vm.downloadEnumFieldList=downloadEnumFieldList;
vm.enumEntityGridOptions={};
UtilityService.cloneObject(enumEntityService.gridOptions,vm.enumEntityGridOptions);
vm.enumEntityGridOptions.data=enumEntityService.entityList;
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
vm.projectGridOptions={};
UtilityService.cloneObject(projectService.gridOptions,vm.projectGridOptions);
vm.projectGridOptions.data=vm.selectedEntity.projectList;
vm.initChildrenList = function () { 
}
vm.projectGridOptions.onRegisterApi = function(gridApi){
vm.projectGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
vm.activeTab=1;
projectService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.project=true;
$rootScope.parentServices.push(projectService);
projectService.setSelectedEntity(response.data[0]);
});
//angular.element('#projectTabs li:eq(0) a').tab('show');
}
else 
projectService.setSelectedEntity(null);
delete $rootScope.openNode.project;
UtilityService.removeObjectFromList($rootScope.parentServices,projectService);
projectService.selectedEntity.show = row.isSelected;
});
  };
vm.enumValueGridOptions={};
UtilityService.cloneObject(enumValueService.gridOptions,vm.enumValueGridOptions);
vm.enumValueGridOptions.data=vm.selectedEntity.enumValueList;
vm.initChildrenList = function () { 
}
vm.enumValueGridOptions.onRegisterApi = function(gridApi){
vm.enumValueGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
vm.activeTab=1;
enumValueService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.enumValue=true;
$rootScope.parentServices.push(enumValueService);
enumValueService.setSelectedEntity(response.data[0]);
});
//angular.element('#enumValueTabs li:eq(0) a').tab('show');
}
else 
enumValueService.setSelectedEntity(null);
delete $rootScope.openNode.enumValue;
UtilityService.removeObjectFromList($rootScope.parentServices,enumValueService);
enumValueService.selectedEntity.show = row.isSelected;
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
function updateParentEntities() { 
enumValueService.initEnumEntityList().then(function(response) {
enumEntityService.preparedData.entityList=response.data;
});

if (enumValueService.selectedEntity.enumValueId!=undefined) enumValueService.searchOne(enumValueService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
enumValueService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
projectService.initEnumEntityList().then(function(response) {
enumEntityService.preparedData.entityList=response.data;
});

if (projectService.selectedEntity.projectId!=undefined) projectService.searchOne(projectService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
projectService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
enumFieldService.initEnumEntityList().then(function(response) {
enumEntityService.preparedData.entityList=response.data;
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
}
function closeEntityDetail(){ 
enumEntityService.setSelectedEntity(null);
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,enumEntityService);
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
