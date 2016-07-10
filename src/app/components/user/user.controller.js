(function() { 

angular
.module("serverTestApp")
.controller("UserController",UserController);
/** @ngInject */
function UserController($scope,$http,$rootScope,$log,UtilityService ,userService, SecurityService, MainService ,enumFieldService)
{
var vm=this;
vm.activeTab=1;
vm.searchBean=userService.searchBean;
vm.entityList=userService.entityList;
vm.selectedEntity=userService.selectedEntity;
vm.enumFieldPreparedData=enumFieldService.preparedData;
function reset()
{
userService.resetSearchBean();
vm.searchBean=userService.searchBean;
userService.setSelectedEntity(null);
userService.selectedEntity.show=false;
userService.setEntityList(null); 
if (userService.isParent()) 
{
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
}
}
function addNew()
{
$rootScope.openNode.user=true;
userService.setSelectedEntity(null);
userService.setEntityList(null);
userService.selectedEntity.show=true;
if (userService.isParent()) 
{
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
}
angular.element('#userTabs li:eq(0) a').tab('show');
}
		
function search()
{
userService.selectedEntity.show=false;
delete $rootScope.openNode.user;
UtilityService.removeObjectFromList($rootScope.parentServices,userService);
userService.searchBean.enumFieldList=[];
userService.searchBean.enumFieldList.push(userService.searchBean.enumField);
delete userService.searchBean.enumField; 
userService.search().then(function successCallback(response) {
userService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
function insert()
{
if (!$scope.userDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
userService.insert().then(function successCallback(response) { 
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
userService.selectedEntity.show=false;
userService.insert().then(function successCallBack(response) { 
$log.debug(response);
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeuser(userService.selectedEntity);
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
if (!$scope.userDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
enumFieldService.selectedEntity.show=false;
delete $rootScope.openNode.enumField;
UtilityService.removeObjectFromList($rootScope.parentServices,enumFieldService);
userService.update().then(function successCallback(response) { 
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
userService.selectedEntity.show=false;

userService.update().then(function successCallback(response){
userService.setSelectedEntity(response.data);
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
userService.selectedEntity.show=false;
delete $rootScope.openNode.user;
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeUser(userService.selectedEntity);
UtilityService.removeObjectFromList($rootScope.parentServices,userService);
userService.setSelectedEntity(null);
}
function del()
{
$rootScope.parentServices.pop();
userService.del().then(function successCallback(response) { 
$log.debug(response);
userService.setSelectedEntity(null);
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
}
vm.refreshTableDetail=refreshTableDetail;
function loadFile(file,field)
{
userService.loadFile(file,field).then(function successCallback(response) {
userService.setSelectedEntity(response.data);
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
enumFieldService.searchOne(userService.selectedEntity.enumFieldList[index]).then(
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
if (userService.selectedEntity.enumField==null || userService.selectedEntity.enumField==undefined)
{
enumFieldService.setSelectedEntity(null); 
enumFieldService.selectedEntity.show=true; 
$rootScope.openNode.enumField=true;
}
else
enumFieldService.searchOne(userService.selectedEntity.enumField).then(
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
function downloadList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("user.xls",?) FROM ?',[mystyle,vm.entityList]);
}
function saveLinkedEnumField() {
userService.selectedEntity.enumFieldList.push(userService.selectedEntity.enumField);
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
vm.userGridOptions={};
UtilityService.cloneObject(userService.gridOptions,vm.userGridOptions);
vm.userGridOptions.data=userService.entityList;
vm.initChildrenList = function () { 
}
vm.userGridOptions.onRegisterApi = function(gridApi){
vm.userGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
userService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.user=true;
$rootScope.parentServices.push(userService);
userService.setSelectedEntity(response.data[0]);
});
angular.element('#userTabs li:eq(0) a').tab('show');
}
else 
userService.setSelectedEntity(null);
delete $rootScope.openNode.user;
UtilityService.removeObjectFromList($rootScope.parentServices,userService);
userService.selectedEntity.show = row.isSelected;
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
function updateParentEntities() { 
}
function closeEntityDetail(){ 
userService.setSelectedEntity(null);
userService.selectedEntity.show=false;
delete $rootScope.openNode.user;
UtilityService.removeObjectFromList($rootScope.parentServices,userService);
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
