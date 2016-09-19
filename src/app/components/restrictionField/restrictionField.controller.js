(function() { 

angular
.module("serverTestApp")
.controller("RestrictionFieldController",RestrictionFieldController);
/** @ngInject */
function RestrictionFieldController($scope,$http,$rootScope,$log,UtilityService ,restrictionFieldService, SecurityService, MainService ,roleService,fieldService)
{
var vm=this;
vm.activeTab=1;
vm.searchBean=restrictionFieldService.searchBean;
vm.entityList=restrictionFieldService.entityList;
vm.selectedEntity=restrictionFieldService.selectedEntity;
vm.rolePreparedData=roleService.preparedData;
vm.fieldPreparedData=fieldService.preparedData;
function reset()
{
restrictionFieldService.resetSearchBean();
vm.searchBean=restrictionFieldService.searchBean;
restrictionFieldService.setSelectedEntity(null);
restrictionFieldService.selectedEntity.show=false;
restrictionFieldService.setEntityList(null); 
if (restrictionFieldService.isParent()) 
{
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
UtilityService.removeObjectFromList($rootScope.parentServices,roleService);
fieldService.selectedEntity.show=false;
delete $rootScope.openNode.field;
UtilityService.removeObjectFromList($rootScope.parentServices,fieldService);
}
}
function addNew()
{
$rootScope.openNode.restrictionField=true;
restrictionFieldService.setSelectedEntity(null);
restrictionFieldService.setEntityList(null);
restrictionFieldService.selectedEntity.show=true;
if (restrictionFieldService.isParent()) 
{
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
UtilityService.removeObjectFromList($rootScope.parentServices,roleService);
fieldService.selectedEntity.show=false;
delete $rootScope.openNode.field;
UtilityService.removeObjectFromList($rootScope.parentServices,fieldService);
}
//angular.element('#restrictionFieldTabs li:eq(0) a').tab('show');
}
		
function search()
{
restrictionFieldService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionField;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionFieldService);
restrictionFieldService.search().then(function successCallback(response) {
restrictionFieldService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
function insert()
{
if (!vm.restrictionFieldDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
restrictionFieldService.insert().then(function successCallback(response) { 
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
restrictionFieldService.selectedEntity.show=false;
restrictionFieldService.insert().then(function successCallBack(response) { 
$log.debug(response);
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removerestrictionField(restrictionFieldService.selectedEntity);
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
if (!vm.restrictionFieldDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
roleService.selectedEntity.show=false;
delete $rootScope.openNode.role;
UtilityService.removeObjectFromList($rootScope.parentServices,roleService);
fieldService.selectedEntity.show=false;
delete $rootScope.openNode.field;
UtilityService.removeObjectFromList($rootScope.parentServices,fieldService);
restrictionFieldService.update().then(function successCallback(response) { 
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
restrictionFieldService.selectedEntity.show=false;

restrictionFieldService.update().then(function successCallback(response){
restrictionFieldService.setSelectedEntity(response.data);
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
restrictionFieldService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionField;
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeRestrictionField(restrictionFieldService.selectedEntity);
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionFieldService);
restrictionFieldService.setSelectedEntity(null);
}
function del()
{
$rootScope.parentServices.pop();
restrictionFieldService.del().then(function successCallback(response) { 
$log.debug(response);
restrictionFieldService.setSelectedEntity(null);
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
restrictionFieldService.loadFile(file,field).then(function successCallback(response) {
restrictionFieldService.setSelectedEntity(response.data);
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
roleService.searchOne(restrictionFieldService.selectedEntity.roleList[index]).then(
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
if (restrictionFieldService.selectedEntity.role==null || restrictionFieldService.selectedEntity.role==undefined)
{
roleService.setSelectedEntity(null); 
roleService.selectedEntity.show=true; 
$rootScope.openNode.role=true;
}
else
roleService.searchOne(restrictionFieldService.selectedEntity.role).then(
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
//angular.element('#roleTabs li:eq(0) a').tab('show');
}
vm.showRoleDetail=showRoleDetail;
 function showFieldDetail(index)
{
if (index!=null)
{
fieldService.searchOne(restrictionFieldService.selectedEntity.fieldList[index]).then(
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
if (restrictionFieldService.selectedEntity.field==null || restrictionFieldService.selectedEntity.field==undefined)
{
fieldService.setSelectedEntity(null); 
fieldService.selectedEntity.show=true; 
$rootScope.openNode.field=true;
}
else
fieldService.searchOne(restrictionFieldService.selectedEntity.field).then(
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
//angular.element('#fieldTabs li:eq(0) a').tab('show');
}
vm.showFieldDetail=showFieldDetail;
function downloadList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("restrictionField.xls",?) FROM ?',[mystyle,vm.entityList]);
}
function downloadRoleList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,vm.selectedEntity.roleList]);
}
vm.downloadRoleList=downloadRoleList;
function downloadFieldList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("field.xls",?) FROM ?',[mystyle,vm.selectedEntity.fieldList]);
}
vm.downloadFieldList=downloadFieldList;
vm.restrictionFieldGridOptions={};
UtilityService.cloneObject(restrictionFieldService.gridOptions,vm.restrictionFieldGridOptions);
vm.restrictionFieldGridOptions.data=restrictionFieldService.entityList;
vm.initChildrenList = function () { 
}
vm.restrictionFieldGridOptions.onRegisterApi = function(gridApi){
vm.restrictionFieldGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
vm.activeTab=1;
restrictionFieldService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.restrictionField=true;
$rootScope.parentServices.push(restrictionFieldService);
restrictionFieldService.setSelectedEntity(response.data[0]);
});
//angular.element('#restrictionFieldTabs li:eq(0) a').tab('show');
}
else 
restrictionFieldService.setSelectedEntity(null);
delete $rootScope.openNode.restrictionField;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionFieldService);
restrictionFieldService.selectedEntity.show = row.isSelected;
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
vm.activeTab=1;
roleService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.role=true;
$rootScope.parentServices.push(roleService);
roleService.setSelectedEntity(response.data[0]);
});
//angular.element('#roleTabs li:eq(0) a').tab('show');
}
else 
roleService.setSelectedEntity(null);
delete $rootScope.openNode.role;
UtilityService.removeObjectFromList($rootScope.parentServices,roleService);
roleService.selectedEntity.show = row.isSelected;
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
vm.activeTab=1;
fieldService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.field=true;
$rootScope.parentServices.push(fieldService);
fieldService.setSelectedEntity(response.data[0]);
});
//angular.element('#fieldTabs li:eq(0) a').tab('show');
}
else 
fieldService.setSelectedEntity(null);
delete $rootScope.openNode.field;
UtilityService.removeObjectFromList($rootScope.parentServices,fieldService);
fieldService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
fieldService.initRestrictionFieldList().then(function(response) {
restrictionFieldService.preparedData.entityList=response.data;
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
roleService.initRestrictionFieldList().then(function(response) {
restrictionFieldService.preparedData.entityList=response.data;
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
restrictionFieldService.setSelectedEntity(null);
restrictionFieldService.selectedEntity.show=false;
delete $rootScope.openNode.restrictionField;
UtilityService.removeObjectFromList($rootScope.parentServices,restrictionFieldService);
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
