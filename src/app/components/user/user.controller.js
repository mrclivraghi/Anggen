(function() { 

angular
.module("serverTestApp")
.controller("UserController",UserController);
/** @ngInject */
function UserController($scope,$http,$rootScope,$log,UtilityService ,userService, SecurityService, MainService ,roleService)
{
$scope.searchBean=userService.searchBean;
$scope.entityList=userService.entityList;
$scope.selectedEntity=userService.selectedEntity;
$scope.hidden=userService.hidden;
$scope.rolePreparedData=roleService.preparedData;
$scope.reset = function()
{
userService.resetSearchBean();
$scope.searchBean=userService.searchBean;userService.setSelectedEntity(null);
userService.selectedEntity.show=false;
userService.setEntityList(null); 
if (userService.isParent()) 
{
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
}
}
$scope.addNew= function()
{
$rootScope.openNode.user=true;
userService.setSelectedEntity(null);
userService.setEntityList(null);
userService.selectedEntity.show=true;
if (userService.isParent()) 
{
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
}
angular.element('#userTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
userService.selectedEntity.show=false;
delete $rootScope.openNode.user;
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
};
$scope.insert=function()
{
if (!$scope.userDetailForm.$valid) return; 
if (userService.isParent()) 
{
userService.insert().then(function successCallback(response) { 
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
userService.selectedEntity.show=false;
userService.insert().then(function successCallBack(response) { 
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
if (!$scope.userDetailForm.$valid) return; 
if (userService.isParent()) 
{
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
userService.update().then(function successCallback(response) { 
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
};
$scope.remove= function()
{
userService.selectedEntity.show=false;
delete $rootScope.openNode.user;
userService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!userService.isParent()) 
$scope.updateParent();
userService.del().then(function successCallback(response) { 
$log.debug(response);
if (userService.isParent()) 
{
$scope.search();
} else { 
userService.setSelectedEntity(null);
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
if ($scope.roleGridApi!=undefined && $scope.roleGridApi!=null)
 $scope.roleGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
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
$scope.trueFalseValues=['',true,false];
$scope.showRoleDetail= function(index)
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
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#roleTabs li:eq(0) a').tab('show');
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("user.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedRole= function() {
userService.selectedEntity.roleList.push(userService.selectedEntity.role);
}
$scope.downloadRoleList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,$scope.selectedEntity.roleList]);
};
$scope.userGridOptions={};
UtilityService.cloneObject(userService.gridOptions,$scope.userGridOptions);
$scope.userGridOptions.data=userService.entityList;
$scope.initChildrenList = function () { 
}
$scope.userGridOptions.onRegisterApi = function(gridApi){
$scope.userGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
userService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.user=true;
userService.setSelectedEntity(response.data[0]);
});
angular.element('#userTabs li:eq(0) a').tab('show');
}
else 
userService.setSelectedEntity(null);
delete $rootScope.openNode.user;
userService.selectedEntity.show = row.isSelected;
});
  };
$scope.roleGridOptions={};
UtilityService.cloneObject(roleService.gridOptions,$scope.roleGridOptions);
$scope.roleGridOptions.data=$scope.selectedEntity.roleList;
$scope.initChildrenList = function () { 
}
$scope.roleGridOptions.onRegisterApi = function(gridApi){
$scope.roleGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
roleService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.role=true;
roleService.setSelectedEntity(response.data[0]);
});
angular.element('#roleTabs li:eq(0) a').tab('show');
}
else 
roleService.setSelectedEntity(null);
delete $rootScope.openNode.role;
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
$scope.closeEntityDetail = function(){ 
userService.setSelectedEntity(null);
userService.selectedEntity.show=false;
delete $rootScope.openNode.user;
}
}
})();
