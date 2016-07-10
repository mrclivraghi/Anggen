(function() { 

angular
.module("serverTestApp")
.controller("EnumValueController",EnumValueController);
/** @ngInject */
function EnumValueController($scope,$http,$rootScope,$log,UtilityService ,enumValueService, SecurityService, MainService ,enumEntityService)
{
var vm=this;
vm.activeTab=1;
vm.searchBean=enumValueService.searchBean;
vm.entityList=enumValueService.entityList;
vm.selectedEntity=enumValueService.selectedEntity;
vm.enumEntityPreparedData=enumEntityService.preparedData;
function reset()
{
enumValueService.resetSearchBean();
vm.searchBean=enumValueService.searchBean;
enumValueService.setSelectedEntity(null);
enumValueService.selectedEntity.show=false;
enumValueService.setEntityList(null); 
if (enumValueService.isParent()) 
{
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,enumEntityService);
}
}
function addNew()
{
$rootScope.openNode.enumValue=true;
enumValueService.setSelectedEntity(null);
enumValueService.setEntityList(null);
enumValueService.selectedEntity.show=true;
if (enumValueService.isParent()) 
{
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,enumEntityService);
}
angular.element('#enumValueTabs li:eq(0) a').tab('show');
}
		
function search()
{
enumValueService.selectedEntity.show=false;
delete $rootScope.openNode.enumValue;
UtilityService.removeObjectFromList($rootScope.parentServices,enumValueService);
enumValueService.search().then(function successCallback(response) {
enumValueService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
function insert()
{
if (!$scope.enumValueDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
enumValueService.insert().then(function successCallback(response) { 
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
enumValueService.selectedEntity.show=false;
enumValueService.insert().then(function successCallBack(response) { 
$log.debug(response);
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeenumValue(enumValueService.selectedEntity);
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
if (!$scope.enumValueDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
enumEntityService.selectedEntity.show=false;
delete $rootScope.openNode.enumEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,enumEntityService);
enumValueService.update().then(function successCallback(response) { 
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
enumValueService.selectedEntity.show=false;

enumValueService.update().then(function successCallback(response){
enumValueService.setSelectedEntity(response.data);
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
enumValueService.selectedEntity.show=false;
delete $rootScope.openNode.enumValue;
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeEnumValue(enumValueService.selectedEntity);
UtilityService.removeObjectFromList($rootScope.parentServices,enumValueService);
enumValueService.setSelectedEntity(null);
}
function del()
{
$rootScope.parentServices.pop();
enumValueService.del().then(function successCallback(response) { 
$log.debug(response);
enumValueService.setSelectedEntity(null);
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
if ($scope.enumEntityGridApi!=undefined && $scope.enumEntityGridApi!=null)
 $scope.enumEntityGridApi.core.handleWindowResize(); 
}
vm.refreshTableDetail=refreshTableDetail;
function loadFile(file,field)
{
enumValueService.loadFile(file,field).then(function successCallback(response) {
enumValueService.setSelectedEntity(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
vm.trueFalseValues=['',true,false];
 function showEnumEntityDetail(index)
{
if (index!=null)
{
enumEntityService.searchOne(enumValueService.selectedEntity.enumEntityList[index]).then(
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
if (enumValueService.selectedEntity.enumEntity==null || enumValueService.selectedEntity.enumEntity==undefined)
{
enumEntityService.setSelectedEntity(null); 
enumEntityService.selectedEntity.show=true; 
$rootScope.openNode.enumEntity=true;
}
else
enumEntityService.searchOne(enumValueService.selectedEntity.enumEntity).then(
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
angular.element('#enumEntityTabs li:eq(0) a').tab('show');
}
vm.showEnumEntityDetail=showEnumEntityDetail;
function downloadList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("enumValue.xls",?) FROM ?',[mystyle,vm.entityList]);
}
function downloadEnumEntityList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("enumEntity.xls",?) FROM ?',[mystyle,vm.selectedEntity.enumEntityList]);
}
vm.downloadEnumEntityList=downloadEnumEntityList;
vm.enumValueGridOptions={};
UtilityService.cloneObject(enumValueService.gridOptions,vm.enumValueGridOptions);
vm.enumValueGridOptions.data=enumValueService.entityList;
vm.initChildrenList = function () { 
}
vm.enumValueGridOptions.onRegisterApi = function(gridApi){
vm.enumValueGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
enumValueService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.enumValue=true;
$rootScope.parentServices.push(enumValueService);
enumValueService.setSelectedEntity(response.data[0]);
});
angular.element('#enumValueTabs li:eq(0) a').tab('show');
}
else 
enumValueService.setSelectedEntity(null);
delete $rootScope.openNode.enumValue;
UtilityService.removeObjectFromList($rootScope.parentServices,enumValueService);
enumValueService.selectedEntity.show = row.isSelected;
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
enumEntityService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.enumEntity=true;
$rootScope.parentServices.push(enumEntityService);
enumEntityService.setSelectedEntity(response.data[0]);
});
angular.element('#enumEntityTabs li:eq(0) a').tab('show');
}
else 
enumEntityService.setSelectedEntity(null);
delete $rootScope.openNode.enumEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,enumEntityService);
enumEntityService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
enumEntityService.initEnumValueList().then(function(response) {
enumValueService.preparedData.entityList=response.data;
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
}
function closeEntityDetail(){ 
enumValueService.setSelectedEntity(null);
enumValueService.selectedEntity.show=false;
delete $rootScope.openNode.enumValue;
UtilityService.removeObjectFromList($rootScope.parentServices,enumValueService);
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
