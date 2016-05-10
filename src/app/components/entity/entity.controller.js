(function() { 

angular
.module("serverTest")
.controller("EntityController",EntityController);
/** @ngInject */
function EntityController($scope,$http,$rootScope,$log,UtilityService ,entityService, SecurityService, MainService ,restrictionEntityService,fieldService,enumFieldService,entityGroupService,tabService,relationshipService)
{
var vm=this;
vm.activeTab=1;
vm.searchBean=entityService.searchBean;
vm.entityList=entityService.entityList;
vm.selectedEntity=entityService.selectedEntity;
vm.restrictionEntityPreparedData=restrictionEntityService.preparedData;
vm.fieldPreparedData=fieldService.preparedData;
vm.enumFieldPreparedData=enumFieldService.preparedData;
vm.entityGroupPreparedData=entityGroupService.preparedData;
vm.tabPreparedData=tabService.preparedData;
vm.relationshipPreparedData=relationshipService.preparedData;
vm.securityTypePreparedData={};
vm.securityTypePreparedData.entityList=["BLOCK_WITH_RESTRICTION","ACCESS_WITH_PERMISSION" ];
vm.generationTypePreparedData={};
vm.generationTypePreparedData.entityList=["SHOW_INCLUDE","HIDE_IGNORE" ];
function reset()
{
entityService.resetSearchBean();
vm.searchBean=entityService.searchBean;
entityService.setSelectedEntity(null);
entityService.selectedEntity.show=false;
entityService.setEntityList(null); 
if (entityService.isParent()) 
{
restrictionEntityService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityService);
fieldService.selectedEntity.show=false;
delete $rootScope.openNode.field;
UtilityService.removeObjectFromList($rootScope.parentServices,fieldService);
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
entityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.entityGroup;
UtilityService.removeObjectFromList($rootScope.parentServices,entityGroupService);
tabService.selectedEntity.show=false;
delete $rootScope.openNode.tab;
UtilityService.removeObjectFromList($rootScope.parentServices,tabService);
relationshipService.selectedEntity.show=false;
delete $rootScope.openNode.relationship;
UtilityService.removeObjectFromList($rootScope.parentServices,relationshipService);
}
}
function addNew()
{
$rootScope.openNode.entity=true;
entityService.setSelectedEntity(null);
entityService.setEntityList(null);
entityService.selectedEntity.show=true;
if (entityService.isParent()) 
{
restrictionEntityService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityService);
fieldService.selectedEntity.show=false;
delete $rootScope.openNode.field;
UtilityService.removeObjectFromList($rootScope.parentServices,fieldService);
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
entityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.entityGroup;
UtilityService.removeObjectFromList($rootScope.parentServices,entityGroupService);
tabService.selectedEntity.show=false;
delete $rootScope.openNode.tab;
UtilityService.removeObjectFromList($rootScope.parentServices,tabService);
relationshipService.selectedEntity.show=false;
delete $rootScope.openNode.relationship;
UtilityService.removeObjectFromList($rootScope.parentServices,relationshipService);
}
angular.element('#entityTabs li:eq(0) a').tab('show');
}
		
function search()
{
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
entityService.searchBean.restrictionEntityList=[];
entityService.searchBean.restrictionEntityList.push(entityService.searchBean.restrictionEntity);
delete entityService.searchBean.restrictionEntity; 
entityService.searchBean.fieldList=[];
entityService.searchBean.fieldList.push(entityService.searchBean.field);
delete entityService.searchBean.field; 
entityService.searchBean.enumFieldList=[];
entityService.searchBean.enumFieldList.push(entityService.searchBean.enumField);
delete entityService.searchBean.enumField; 
entityService.searchBean.tabList=[];
entityService.searchBean.tabList.push(entityService.searchBean.tab);
delete entityService.searchBean.tab; 
entityService.searchBean.relationshipList=[];
entityService.searchBean.relationshipList.push(entityService.searchBean.relationship);
delete entityService.searchBean.relationship; 
entityService.search().then(function successCallback(response) {
entityService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
function insert()
{
if (!$scope.entityDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
entityService.insert().then(function successCallback(response) { 
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
entityService.selectedEntity.show=false;
entityService.insert().then(function successCallBack(response) { 
$log.debug(response);
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeentity(entityService.selectedEntity);
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
if (!$scope.entityDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
restrictionEntityService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityService);
fieldService.selectedEntity.show=false;
delete $rootScope.openNode.field;
UtilityService.removeObjectFromList($rootScope.parentServices,fieldService);
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
entityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.entityGroup;
UtilityService.removeObjectFromList($rootScope.parentServices,entityGroupService);
tabService.selectedEntity.show=false;
delete $rootScope.openNode.tab;
UtilityService.removeObjectFromList($rootScope.parentServices,tabService);
relationshipService.selectedEntity.show=false;
delete $rootScope.openNode.relationship;
UtilityService.removeObjectFromList($rootScope.parentServices,relationshipService);
entityService.update().then(function successCallback(response) { 
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
entityService.selectedEntity.show=false;

entityService.update().then(function successCallback(response){
entityService.setSelectedEntity(response.data);
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
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeEntity(entityService.selectedEntity);
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
entityService.setSelectedEntity(null);
}
function del()
{
$rootScope.parentServices.pop();
entityService.del().then(function successCallback(response) { 
$log.debug(response);
entityService.setSelectedEntity(null);
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
if ($scope.restrictionEntityGridApi!=undefined && $scope.restrictionEntityGridApi!=null)
 $scope.restrictionEntityGridApi.core.handleWindowResize(); 
if ($scope.fieldGridApi!=undefined && $scope.fieldGridApi!=null)
 $scope.fieldGridApi.core.handleWindowResize(); 
if ($scope.enumFieldGridApi!=undefined && $scope.enumFieldGridApi!=null)
 $scope.enumFieldGridApi.core.handleWindowResize(); 
if ($scope.entityGroupGridApi!=undefined && $scope.entityGroupGridApi!=null)
 $scope.entityGroupGridApi.core.handleWindowResize(); 
if ($scope.tabGridApi!=undefined && $scope.tabGridApi!=null)
 $scope.tabGridApi.core.handleWindowResize(); 
if ($scope.relationshipGridApi!=undefined && $scope.relationshipGridApi!=null)
 $scope.relationshipGridApi.core.handleWindowResize(); 
}
vm.refreshTableDetail=refreshTableDetail;
function loadFile(file,field)
{
entityService.loadFile(file,field).then(function successCallback(response) {
entityService.setSelectedEntity(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
vm.trueFalseValues=['',true,false];
 function showRestrictionEntityDetail(index)
{
if (index!=null)
{
restrictionEntityService.searchOne(entityService.selectedEntity.restrictionEntityList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
restrictionEntityService.setSelectedEntity(response.data[0]);
restrictionEntityService.selectedEntity.show=true;
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
if (entityService.selectedEntity.restrictionEntity==null || entityService.selectedEntity.restrictionEntity==undefined)
{
restrictionEntityService.setSelectedEntity(null); 
restrictionEntityService.selectedEntity.show=true; 
$rootScope.openNode.restrictionEntity=true;
}
else
restrictionEntityService.searchOne(entityService.selectedEntity.restrictionEntity).then(
function successCallback(response) {
restrictionEntityService.setSelectedEntity(response.data[0]);
restrictionEntityService.selectedEntity.show=true;
$rootScope.openNode.restrictionEntity=true;
$rootScope.parentServices.push(restrictionEntityService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#restrictionEntityTabs li:eq(0) a').tab('show');
}
vm.showRestrictionEntityDetail=showRestrictionEntityDetail;
 function showFieldDetail(index)
{
if (index!=null)
{
fieldService.searchOne(entityService.selectedEntity.fieldList[index]).then(
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
if (entityService.selectedEntity.field==null || entityService.selectedEntity.field==undefined)
{
fieldService.setSelectedEntity(null); 
fieldService.selectedEntity.show=true; 
$rootScope.openNode.field=true;
}
else
fieldService.searchOne(entityService.selectedEntity.field).then(
function successCallback(response) {
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
$rootScope.openNode.field=true;
$rootScope.parentServices.push(fieldService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#fieldTabs li:eq(0) a').tab('show');
}
vm.showFieldDetail=showFieldDetail;
 function showEnumFieldDetail(index)
{
if (index!=null)
{
enumFieldService.searchOne(entityService.selectedEntity.enumFieldList[index]).then(
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
if (entityService.selectedEntity.enumField==null || entityService.selectedEntity.enumField==undefined)
{
enumFieldService.setSelectedEntity(null); 
enumFieldService.selectedEntity.show=true; 
$rootScope.openNode.enumField=true;
}
else
enumFieldService.searchOne(entityService.selectedEntity.enumField).then(
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
 function showEntityGroupDetail(index)
{
if (index!=null)
{
entityGroupService.searchOne(entityService.selectedEntity.entityGroupList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
entityGroupService.setSelectedEntity(response.data[0]);
entityGroupService.selectedEntity.show=true;
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
if (entityService.selectedEntity.entityGroup==null || entityService.selectedEntity.entityGroup==undefined)
{
entityGroupService.setSelectedEntity(null); 
entityGroupService.selectedEntity.show=true; 
$rootScope.openNode.entityGroup=true;
}
else
entityGroupService.searchOne(entityService.selectedEntity.entityGroup).then(
function successCallback(response) {
entityGroupService.setSelectedEntity(response.data[0]);
entityGroupService.selectedEntity.show=true;
$rootScope.openNode.entityGroup=true;
$rootScope.parentServices.push(entityGroupService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#entityGroupTabs li:eq(0) a').tab('show');
}
vm.showEntityGroupDetail=showEntityGroupDetail;
 function showTabDetail(index)
{
if (index!=null)
{
tabService.searchOne(entityService.selectedEntity.tabList[index]).then(
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
if (entityService.selectedEntity.tab==null || entityService.selectedEntity.tab==undefined)
{
tabService.setSelectedEntity(null); 
tabService.selectedEntity.show=true; 
$rootScope.openNode.tab=true;
}
else
tabService.searchOne(entityService.selectedEntity.tab).then(
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
 function showRelationshipDetail(index)
{
if (index!=null)
{
relationshipService.searchOne(entityService.selectedEntity.relationshipList[index]).then(
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
if (entityService.selectedEntity.relationship==null || entityService.selectedEntity.relationship==undefined)
{
relationshipService.setSelectedEntity(null); 
relationshipService.selectedEntity.show=true; 
$rootScope.openNode.relationship=true;
}
else
relationshipService.searchOne(entityService.selectedEntity.relationship).then(
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
UtilityService.alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,vm.entityList]);
}
function saveLinkedRestrictionEntity() {
entityService.selectedEntity.restrictionEntityList.push(entityService.selectedEntity.restrictionEntity);
}
vm.saveLinkedRestrictionEntity=saveLinkedRestrictionEntity;
function downloadRestrictionEntityList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("restrictionEntity.xls",?) FROM ?',[mystyle,vm.selectedEntity.restrictionEntityList]);
}
vm.downloadRestrictionEntityList=downloadRestrictionEntityList;
function saveLinkedField() {
entityService.selectedEntity.fieldList.push(entityService.selectedEntity.field);
}
vm.saveLinkedField=saveLinkedField;
function downloadFieldList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("field.xls",?) FROM ?',[mystyle,vm.selectedEntity.fieldList]);
}
vm.downloadFieldList=downloadFieldList;
function saveLinkedEnumField() {
entityService.selectedEntity.enumFieldList.push(entityService.selectedEntity.enumField);
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
function downloadEntityGroupList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("entityGroup.xls",?) FROM ?',[mystyle,vm.selectedEntity.entityGroupList]);
}
vm.downloadEntityGroupList=downloadEntityGroupList;
function saveLinkedTab() {
entityService.selectedEntity.tabList.push(entityService.selectedEntity.tab);
}
vm.saveLinkedTab=saveLinkedTab;
function downloadTabList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("tab.xls",?) FROM ?',[mystyle,vm.selectedEntity.tabList]);
}
vm.downloadTabList=downloadTabList;
function saveLinkedRelationship() {
entityService.selectedEntity.relationshipList.push(entityService.selectedEntity.relationship);
}
vm.saveLinkedRelationship=saveLinkedRelationship;
function downloadRelationshipList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("relationship.xls",?) FROM ?',[mystyle,vm.selectedEntity.relationshipList]);
}
vm.downloadRelationshipList=downloadRelationshipList;
vm.entityGridOptions={};
UtilityService.cloneObject(entityService.gridOptions,vm.entityGridOptions);
vm.entityGridOptions.data=entityService.entityList;
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
vm.restrictionEntityGridOptions={};
UtilityService.cloneObject(restrictionEntityService.gridOptions,vm.restrictionEntityGridOptions);
vm.restrictionEntityGridOptions.data=vm.selectedEntity.restrictionEntityList;
vm.initChildrenList = function () { 
}
vm.restrictionEntityGridOptions.onRegisterApi = function(gridApi){
vm.restrictionEntityGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionEntityService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.restrictionEntity=true;
$rootScope.parentServices.push(restrictionEntityService);
restrictionEntityService.setSelectedEntity(response.data[0]);
});
angular.element('#restrictionEntityTabs li:eq(0) a').tab('show');
}
else 
restrictionEntityService.setSelectedEntity(null);
delete $rootScope.openNode.restrictionEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityService);
restrictionEntityService.selectedEntity.show = row.isSelected;
});
  };
vm.fieldGridOptions={};
UtilityService.cloneObject(fieldService.gridOptions,vm.fieldGridOptions);
vm.fieldGridOptions.data=vm.selectedEntity.fieldList;
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
vm.entityGroupGridOptions={};
UtilityService.cloneObject(entityGroupService.gridOptions,vm.entityGroupGridOptions);
vm.entityGroupGridOptions.data=vm.selectedEntity.entityGroupList;
vm.initChildrenList = function () { 
}
vm.entityGroupGridOptions.onRegisterApi = function(gridApi){
vm.entityGroupGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
entityGroupService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.entityGroup=true;
$rootScope.parentServices.push(entityGroupService);
entityGroupService.setSelectedEntity(response.data[0]);
});
angular.element('#entityGroupTabs li:eq(0) a').tab('show');
}
else 
entityGroupService.setSelectedEntity(null);
delete $rootScope.openNode.entityGroup;
UtilityService.removeObjectFromList($rootScope.parentServices,entityGroupService);
entityGroupService.selectedEntity.show = row.isSelected;
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
tabService.initEntityList().then(function(response) {
entityService.preparedData.entityList=response.data;
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
restrictionEntityService.initEntityList().then(function(response) {
entityService.preparedData.entityList=response.data;
});

if (restrictionEntityService.selectedEntity.restrictionEntityId!=undefined) restrictionEntityService.searchOne(restrictionEntityService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
restrictionEntityService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
enumFieldService.initEntityList().then(function(response) {
entityService.preparedData.entityList=response.data;
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
entityGroupService.initEntityList().then(function(response) {
entityService.preparedData.entityList=response.data;
});

if (entityGroupService.selectedEntity.entityGroupId!=undefined) entityGroupService.searchOne(entityGroupService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
entityGroupService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
relationshipService.initEntityList().then(function(response) {
entityService.preparedData.entityList=response.data;
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
relationshipService.initEntityList().then(function(response) {
entityService.preparedData.entityList=response.data;
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
fieldService.initEntityList().then(function(response) {
entityService.preparedData.entityList=response.data;
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
}
function closeEntityDetail(){ 
entityService.setSelectedEntity(null);
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
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
