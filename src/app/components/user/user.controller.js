(function() { 

angular
.module("serverTest")
.controller("UserController",UserController);
/** @ngInject */
function UserController($scope,$http,$rootScope,$log,UtilityService ,userService, SecurityService, MainService ,roleService)
{
var vm=this;
vm.activeTab=1;
vm.searchBean=userService.searchBean;
vm.entityList=userService.entityList;
vm.selectedEntity=userService.selectedEntity;
vm.rolePreparedData=roleService.preparedData;
function reset()
{
userService.resetSearchBean();
vm.searchBean=userService.searchBean;
userService.setSelectedEntity(null);
userService.selectedEntity.show=false;
userService.setEntityList(null); 
if (userService.isParent()) 
{
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
UtilityService.removeObjectFromList($rootScope.parentServices,roleService);
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
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
UtilityService.removeObjectFromList($rootScope.parentServices,roleService);
}
angular.element('#userTabs li:eq(0) a').tab('show');
}
		
function search()
{
userService.selectedEntity.show=false;
delete $rootScope.openNode.user;
UtilityService.removeObjectFromList($rootScope.parentServices,userService);
userService.searchBean.roleList=[];
userService.searchBean.roleList.push(userService.searchBean.role);
delete userService.searchBean.role; 
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
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
UtilityService.removeObjectFromList($rootScope.parentServices,roleService);
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
if ($scope.roleGridApi!=undefined && $scope.roleGridApi!=null)
 $scope.roleGridApi.core.handleWindowResize(); 
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
 function showRoleDetail(index)
{
if (index!=null)
{
roleService.searchOne(userService.selectedEntity.roleList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
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
if (userService.selectedEntity.role==null || userService.selectedEntity.role==undefined)
{
roleService.setSelectedEntity(null); 
roleService.selectedEntity.show=true; 
$rootScope.openNode.role=true;
}
else
roleService.searchOne(userService.selectedEntity.role).then(
function successCallback(response) {
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
$rootScope.openNode.role=true;
$rootScope.parentServices.push(roleService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#roleTabs li:eq(0) a').tab('show');
}
vm.showRoleDetail=showRoleDetail;
function downloadList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("user.xls",?) FROM ?',[mystyle,vm.entityList]);
}
function saveLinkedRole() {
userService.selectedEntity.roleList.push(userService.selectedEntity.role);
}
vm.saveLinkedRole=saveLinkedRole;
function downloadRoleList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,vm.selectedEntity.roleList]);
}
vm.downloadRoleList=downloadRoleList;
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
vm.roleGridOptions={};
UtilityService.cloneObject(roleService.gridOptions,vm.roleGridOptions);
vm.roleGridOptions.data=vm.selectedEntity.roleList;
vm.initChildrenList = function () { 
}
vm.roleGridOptions.onRegisterApi = function(gridApi){
vm.roleGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
roleService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.role=true;
$rootScope.parentServices.push(roleService);
roleService.setSelectedEntity(response.data[0]);
});
angular.element('#roleTabs li:eq(0) a').tab('show');
}
else 
roleService.setSelectedEntity(null);
delete $rootScope.openNode.role;
UtilityService.removeObjectFromList($rootScope.parentServices,roleService);
roleService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
roleService.initUserList().then(function(response) {
userService.preparedData.entityList=response.data;
});

if (roleService.selectedEntity.roleId!=undefined) roleService.searchOne(roleService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
roleService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
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
