(function() { 

angular
.module("serverTestApp")
.controller("RoleController",RoleController);
/** @ngInject */
function RoleController($scope,$http,$rootScope,$log,UtilityService ,roleService, SecurityService, MainService ,restrictionEntityService,restrictionFieldService,userService,restrictionEntityGroupService)
{
var vm=this;
vm.activeTab=1;
vm.searchBean=roleService.searchBean;
vm.entityList=roleService.entityList;
vm.selectedEntity=roleService.selectedEntity;
vm.restrictionEntityPreparedData=restrictionEntityService.preparedData;
vm.restrictionFieldPreparedData=restrictionFieldService.preparedData;
vm.userPreparedData=userService.preparedData;
vm.restrictionEntityGroupPreparedData=restrictionEntityGroupService.preparedData;
function reset()
{
roleService.resetSearchBean();
vm.searchBean=roleService.searchBean;
roleService.setSelectedEntity(null);
roleService.selectedEntity.show=false;
roleService.setEntityList(null); 
if (roleService.isParent()) 
{
restrictionEntityService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityService);
restrictionFieldService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionField;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionFieldService);
userService.selectedEntity.show=false;
delete $rootScope.openNode.user;
UtilityService.removeObjectFromList($rootScope.parentServices,userService);
restrictionEntityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntityGroup;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityGroupService);
}
}
function addNew()
{
$rootScope.openNode.role=true;
roleService.setSelectedEntity(null);
roleService.setEntityList(null);
roleService.selectedEntity.show=true;
if (roleService.isParent()) 
{
restrictionEntityService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityService);
restrictionFieldService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionField;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionFieldService);
userService.selectedEntity.show=false;
delete $rootScope.openNode.user;
UtilityService.removeObjectFromList($rootScope.parentServices,userService);
restrictionEntityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntityGroup;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityGroupService);
}
angular.element('#roleTabs li:eq(0) a').tab('show');
}
		
function search()
{
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
UtilityService.removeObjectFromList($rootScope.parentServices,roleService);
roleService.searchBean.restrictionEntityList=[];
roleService.searchBean.restrictionEntityList.push(roleService.searchBean.restrictionEntity);
delete roleService.searchBean.restrictionEntity; 
roleService.searchBean.restrictionFieldList=[];
roleService.searchBean.restrictionFieldList.push(roleService.searchBean.restrictionField);
delete roleService.searchBean.restrictionField; 
roleService.searchBean.userList=[];
roleService.searchBean.userList.push(roleService.searchBean.user);
delete roleService.searchBean.user; 
roleService.searchBean.restrictionEntityGroupList=[];
roleService.searchBean.restrictionEntityGroupList.push(roleService.searchBean.restrictionEntityGroup);
delete roleService.searchBean.restrictionEntityGroup; 
roleService.search().then(function successCallback(response) {
roleService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
function insert()
{
if (!$scope.roleDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
roleService.insert().then(function successCallback(response) { 
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
roleService.selectedEntity.show=false;
roleService.insert().then(function successCallBack(response) { 
$log.debug(response);
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removerole(roleService.selectedEntity);
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
if (!$scope.roleDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
restrictionEntityService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntity;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityService);
restrictionFieldService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionField;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionFieldService);
userService.selectedEntity.show=false;
delete $rootScope.openNode.user;
UtilityService.removeObjectFromList($rootScope.parentServices,userService);
restrictionEntityGroupService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionEntityGroup;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionEntityGroupService);
roleService.update().then(function successCallback(response) { 
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
roleService.selectedEntity.show=false;

roleService.update().then(function successCallback(response){
roleService.setSelectedEntity(response.data);
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
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeRole(roleService.selectedEntity);
UtilityService.removeObjectFromList($rootScope.parentServices,roleService);
roleService.setSelectedEntity(null);
}
function del()
{
$rootScope.parentServices.pop();
roleService.del().then(function successCallback(response) { 
$log.debug(response);
roleService.setSelectedEntity(null);
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
if ($scope.restrictionFieldGridApi!=undefined && $scope.restrictionFieldGridApi!=null)
 $scope.restrictionFieldGridApi.core.handleWindowResize(); 
if ($scope.userGridApi!=undefined && $scope.userGridApi!=null)
 $scope.userGridApi.core.handleWindowResize(); 
if ($scope.restrictionEntityGroupGridApi!=undefined && $scope.restrictionEntityGroupGridApi!=null)
 $scope.restrictionEntityGroupGridApi.core.handleWindowResize(); 
}
vm.refreshTableDetail=refreshTableDetail;
function loadFile(file,field)
{
roleService.loadFile(file,field).then(function successCallback(response) {
roleService.setSelectedEntity(response.data);
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
restrictionEntityService.searchOne(roleService.selectedEntity.restrictionEntityList[index]).then(
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
if (roleService.selectedEntity.restrictionEntity==null || roleService.selectedEntity.restrictionEntity==undefined)
{
restrictionEntityService.setSelectedEntity(null); 
restrictionEntityService.selectedEntity.show=true; 
$rootScope.openNode.restrictionEntity=true;
}
else
restrictionEntityService.searchOne(roleService.selectedEntity.restrictionEntity).then(
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
 function showRestrictionFieldDetail(index)
{
if (index!=null)
{
restrictionFieldService.searchOne(roleService.selectedEntity.restrictionFieldList[index]).then(
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
if (roleService.selectedEntity.restrictionField==null || roleService.selectedEntity.restrictionField==undefined)
{
restrictionFieldService.setSelectedEntity(null); 
restrictionFieldService.selectedEntity.show=true; 
$rootScope.openNode.restrictionField=true;
}
else
restrictionFieldService.searchOne(roleService.selectedEntity.restrictionField).then(
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
 function showUserDetail(index)
{
if (index!=null)
{
userService.searchOne(roleService.selectedEntity.userList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
userService.setSelectedEntity(response.data[0]);
userService.selectedEntity.show=true;
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
if (roleService.selectedEntity.user==null || roleService.selectedEntity.user==undefined)
{
userService.setSelectedEntity(null); 
userService.selectedEntity.show=true; 
$rootScope.openNode.user=true;
}
else
userService.searchOne(roleService.selectedEntity.user).then(
function successCallback(response) {
userService.setSelectedEntity(response.data[0]);
userService.selectedEntity.show=true;
$rootScope.openNode.user=true;
$rootScope.parentServices.push(userService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#userTabs li:eq(0) a').tab('show');
}
vm.showUserDetail=showUserDetail;
 function showRestrictionEntityGroupDetail(index)
{
if (index!=null)
{
restrictionEntityGroupService.searchOne(roleService.selectedEntity.restrictionEntityGroupList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
restrictionEntityGroupService.selectedEntity.show=true;
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
if (roleService.selectedEntity.restrictionEntityGroup==null || roleService.selectedEntity.restrictionEntityGroup==undefined)
{
restrictionEntityGroupService.setSelectedEntity(null); 
restrictionEntityGroupService.selectedEntity.show=true; 
$rootScope.openNode.restrictionEntityGroup=true;
}
else
restrictionEntityGroupService.searchOne(roleService.selectedEntity.restrictionEntityGroup).then(
function successCallback(response) {
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
restrictionEntityGroupService.selectedEntity.show=true;
$rootScope.openNode.restrictionEntityGroup=true;
$rootScope.parentServices.push(restrictionEntityGroupService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
}
vm.showRestrictionEntityGroupDetail=showRestrictionEntityGroupDetail;
function downloadList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,vm.entityList]);
}
function saveLinkedRestrictionEntity() {
roleService.selectedEntity.restrictionEntityList.push(roleService.selectedEntity.restrictionEntity);
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
function saveLinkedRestrictionField() {
roleService.selectedEntity.restrictionFieldList.push(roleService.selectedEntity.restrictionField);
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
function saveLinkedUser() {
roleService.selectedEntity.userList.push(roleService.selectedEntity.user);
}
vm.saveLinkedUser=saveLinkedUser;
function downloadUserList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("user.xls",?) FROM ?',[mystyle,vm.selectedEntity.userList]);
}
vm.downloadUserList=downloadUserList;
function saveLinkedRestrictionEntityGroup() {
roleService.selectedEntity.restrictionEntityGroupList.push(roleService.selectedEntity.restrictionEntityGroup);
}
vm.saveLinkedRestrictionEntityGroup=saveLinkedRestrictionEntityGroup;
function downloadRestrictionEntityGroupList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("restrictionEntityGroup.xls",?) FROM ?',[mystyle,vm.selectedEntity.restrictionEntityGroupList]);
}
vm.downloadRestrictionEntityGroupList=downloadRestrictionEntityGroupList;
vm.roleGridOptions={};
UtilityService.cloneObject(roleService.gridOptions,vm.roleGridOptions);
vm.roleGridOptions.data=roleService.entityList;
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
vm.userGridOptions={};
UtilityService.cloneObject(userService.gridOptions,vm.userGridOptions);
vm.userGridOptions.data=vm.selectedEntity.userList;
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
vm.restrictionEntityGroupGridOptions={};
UtilityService.cloneObject(restrictionEntityGroupService.gridOptions,vm.restrictionEntityGroupGridOptions);
vm.restrictionEntityGroupGridOptions.data=vm.selectedEntity.restrictionEntityGroupList;
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
function updateParentEntities() { 
restrictionEntityGroupService.initRoleList().then(function(response) {
roleService.preparedData.entityList=response.data;
});

if (restrictionEntityGroupService.selectedEntity.restrictionEntityGroupId!=undefined) restrictionEntityGroupService.searchOne(restrictionEntityGroupService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
userService.initRoleList().then(function(response) {
roleService.preparedData.entityList=response.data;
});

if (userService.selectedEntity.userId!=undefined) userService.searchOne(userService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
userService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
restrictionFieldService.initRoleList().then(function(response) {
roleService.preparedData.entityList=response.data;
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
restrictionEntityService.initRoleList().then(function(response) {
roleService.preparedData.entityList=response.data;
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
}
function closeEntityDetail(){ 
roleService.setSelectedEntity(null);
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
UtilityService.removeObjectFromList($rootScope.parentServices,roleService);
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
