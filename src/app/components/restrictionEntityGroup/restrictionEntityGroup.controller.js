(function() { 

angular
.module("serverTestApp")
.controller("RestrictionEntityGroupController",RestrictionEntityGroupController);
/** @ngInject */
function RestrictionEntityGroupController($scope,$http,$rootScope,$log,UtilityService ,restrictionEntityGroupService, SecurityService, MainService ,enumFieldService,relationshipService)
{
var vm=this;
vm.activeTab=1;
vm.searchBean=restrictionEntityGroupService.searchBean;
vm.entityList=restrictionEntityGroupService.entityList;
vm.selectedEntity=restrictionEntityGroupService.selectedEntity;
vm.enumFieldPreparedData=enumFieldService.preparedData;
vm.relationshipPreparedData=relationshipService.preparedData;
function reset()
{
restrictionEntityGroupService.resetSearchBean();
vm.searchBean=restrictionEntityGroupService.searchBean;
restrictionEntityGroupService.setSelectedEntity(null);
restrictionEntityGroupService.selectedEntity.show=false;
restrictionEntityGroupService.setEntityList(null); 
if (restrictionEntityGroupService.isParent()) 
{
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
$rootScope.openNode.restrictionEntityGroup=true;
restrictionEntityGroupService.setSelectedEntity(null);
restrictionEntityGroupService.setEntityList(null);
restrictionEntityGroupService.selectedEntity.show=true;
if (restrictionEntityGroupService.isParent()) 
{
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
relationshipService.selectedEntity.show=false;
delete $rootScope.openNode.relationship;
UtilityService.removeObjectFromList($rootScope.parentServices,relationshipService);
}
angular.element('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
}
		
function search()
{
restrictionEntityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntityGroup;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityGroupService);
restrictionEntityGroupService.search().then(function successCallback(response) {
restrictionEntityGroupService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
function insert()
{
if (!$scope.restrictionEntityGroupDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
restrictionEntityGroupService.insert().then(function successCallback(response) { 
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
restrictionEntityGroupService.selectedEntity.show=false;
restrictionEntityGroupService.insert().then(function successCallBack(response) { 
$log.debug(response);
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removerestrictionEntityGroup(restrictionEntityGroupService.selectedEntity);
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
if (!$scope.restrictionEntityGroupDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
relationshipService.selectedEntity.show=false;
delete $rootScope.openNode.relationship;
UtilityService.removeObjectFromList($rootScope.parentServices,relationshipService);
restrictionEntityGroupService.update().then(function successCallback(response) { 
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
restrictionEntityGroupService.selectedEntity.show=false;

restrictionEntityGroupService.update().then(function successCallback(response){
restrictionEntityGroupService.setSelectedEntity(response.data);
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
restrictionEntityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntityGroup;
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeRestrictionEntityGroup(restrictionEntityGroupService.selectedEntity);
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityGroupService);
restrictionEntityGroupService.setSelectedEntity(null);
}
function del()
{
$rootScope.parentServices.pop();
restrictionEntityGroupService.del().then(function successCallback(response) { 
$log.debug(response);
restrictionEntityGroupService.setSelectedEntity(null);
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
if ($scope.enumFieldGridApi!=undefined && $scope.enumFieldGridApi!=null)
 $scope.enumFieldGridApi.core.handleWindowResize(); 
if ($scope.relationshipGridApi!=undefined && $scope.relationshipGridApi!=null)
 $scope.relationshipGridApi.core.handleWindowResize(); 
}
vm.refreshTableDetail=refreshTableDetail;
function loadFile(file,field)
{
restrictionEntityGroupService.loadFile(file,field).then(function successCallback(response) {
restrictionEntityGroupService.setSelectedEntity(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
vm.trueFalseValues=['',true,false];
 function showEnumFieldDetail(index)
{
if (index!=null)
{
enumFieldService.searchOne(restrictionEntityGroupService.selectedEntity.enumFieldList[index]).then(
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
if (restrictionEntityGroupService.selectedEntity.enumField==null || restrictionEntityGroupService.selectedEntity.enumField==undefined)
{
enumFieldService.setSelectedEntity(null); 
enumFieldService.selectedEntity.show=true; 
$rootScope.openNode.enumField=true;
}
else
enumFieldService.searchOne(restrictionEntityGroupService.selectedEntity.enumField).then(
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
relationshipService.searchOne(restrictionEntityGroupService.selectedEntity.relationshipList[index]).then(
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
if (restrictionEntityGroupService.selectedEntity.relationship==null || restrictionEntityGroupService.selectedEntity.relationship==undefined)
{
relationshipService.setSelectedEntity(null); 
relationshipService.selectedEntity.show=true; 
$rootScope.openNode.relationship=true;
}
else
relationshipService.searchOne(restrictionEntityGroupService.selectedEntity.relationship).then(
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
UtilityService.alasql('SELECT * INTO XLSXML("restrictionEntityGroup.xls",?) FROM ?',[mystyle,vm.entityList]);
}
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
vm.restrictionEntityGroupGridOptions={};
UtilityService.cloneObject(restrictionEntityGroupService.gridOptions,vm.restrictionEntityGroupGridOptions);
vm.restrictionEntityGroupGridOptions.data=restrictionEntityGroupService.entityList;
vm.initChildrenList = function () { 
}
vm.restrictionEntityGroupGridOptions.onRegisterApi = function(gridApi){
vm.restrictionEntityGroupGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionEntityGroupService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.restrictionEntityGroup=true;
$rootScope.parentServices.push(restrictionEntityGroupService);
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
});
angular.element('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
}
else 
restrictionEntityGroupService.setSelectedEntity(null);
delete $rootScope.openNode.restrictionEntityGroup;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityGroupService);
restrictionEntityGroupService.selectedEntity.show = row.isSelected;
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
}
function closeEntityDetail(){ 
restrictionEntityGroupService.setSelectedEntity(null);
restrictionEntityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntityGroup;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityGroupService);
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
