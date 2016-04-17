(function() { 

angular
.module("serverTestApp")
.controller("AnnotationAttributeController",AnnotationAttributeController);
/** @ngInject */
function AnnotationAttributeController($scope,$http,$rootScope,$log,UtilityService ,annotationAttributeService, SecurityService, MainService ,annotationService)
{
var vm=this;
vm.searchBean=annotationAttributeService.searchBean;
vm.entityList=annotationAttributeService.entityList;
vm.selectedEntity=annotationAttributeService.selectedEntity;
vm.annotationPreparedData=annotationService.preparedData;
function reset()
{
annotationAttributeService.resetSearchBean();
vm.searchBean=annotationAttributeService.searchBean;
annotationAttributeService.setSelectedEntity(null);
annotationAttributeService.selectedEntity.show=false;
annotationAttributeService.setEntityList(null); 
if (annotationAttributeService.isParent()) 
{
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
}
}
function addNew()
{
$rootScope.openNode.annotationAttribute=true;
annotationAttributeService.setSelectedEntity(null);
annotationAttributeService.setEntityList(null);
annotationAttributeService.selectedEntity.show=true;
if (annotationAttributeService.isParent()) 
{
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
}
angular.element('#annotationAttributeTabs li:eq(0) a').tab('show');
}
		
function search()
{
annotationAttributeService.selectedEntity.show=false;
delete $rootScope.openNode.annotationAttribute;
annotationAttributeService.search().then(function successCallback(response) {
annotationAttributeService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
function insert()
{
if (!$scope.annotationAttributeDetailForm.$valid) return; 
if (annotationAttributeService.isParent()) 
{
annotationAttributeService.insert().then(function successCallback(response) { 
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
annotationAttributeService.selectedEntity.show=false;
annotationAttributeService.insert().then(function successCallBack(response) { 
$log.debug(response);
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
if (!$scope.annotationAttributeDetailForm.$valid) return; 
if (annotationAttributeService.isParent()) 
{
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
annotationAttributeService.update().then(function successCallback(response) { 
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
annotationAttributeService.selectedEntity.show=false;

annotationAttributeService.update().then(function successCallback(response){
annotationAttributeService.setSelectedEntity(response.data);
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
annotationAttributeService.selectedEntity.show=false;
delete $rootScope.openNode.annotationAttribute;
annotationAttributeService.setSelectedEntity(null);
}
function del()
{
annotationAttributeService.del().then(function successCallback(response) { 
$log.debug(response);
if (annotationAttributeService.isParent()) 
{
vm.search();
} else { 
annotationAttributeService.setSelectedEntity(null);
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
if ($scope.annotationGridApi!=undefined && $scope.annotationGridApi!=null)
 $scope.annotationGridApi.core.handleWindowResize(); 
}
vm.refreshTableDetail=refreshTableDetail;
function loadFile(file,field)
{
annotationAttributeService.loadFile(file,field).then(function successCallback(response) {
annotationAttributeService.setSelectedEntity(response.data);
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
annotationService.searchOne(annotationAttributeService.selectedEntity.annotationList[index]).then(
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
if (annotationAttributeService.selectedEntity.annotation==null || annotationAttributeService.selectedEntity.annotation==undefined)
{
annotationService.setSelectedEntity(null); 
annotationService.selectedEntity.show=true; 
$rootScope.openNode.annotation=true;
}
else
annotationService.searchOne(annotationAttributeService.selectedEntity.annotation).then(
function successCallback(response) {
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
$rootScope.openNode.annotation=true;
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#annotationTabs li:eq(0) a').tab('show');
}
vm.showAnnotationDetail=showAnnotationDetail;
function downloadList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("annotationAttribute.xls",?) FROM ?',[mystyle,vm.entityList]);
}
function downloadAnnotationList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("annotation.xls",?) FROM ?',[mystyle,vm.selectedEntity.annotationList]);
}
vm.downloadAnnotationList=downloadAnnotationList;
vm.annotationAttributeGridOptions={};
UtilityService.cloneObject(annotationAttributeService.gridOptions,vm.annotationAttributeGridOptions);
vm.annotationAttributeGridOptions.data=annotationAttributeService.entityList;
vm.initChildrenList = function () { 
}
vm.annotationAttributeGridOptions.onRegisterApi = function(gridApi){
vm.annotationAttributeGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
annotationAttributeService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.annotationAttribute=true;
annotationAttributeService.setSelectedEntity(response.data[0]);
});
angular.element('#annotationAttributeTabs li:eq(0) a').tab('show');
}
else 
annotationAttributeService.setSelectedEntity(null);
delete $rootScope.openNode.annotationAttribute;
annotationAttributeService.selectedEntity.show = row.isSelected;
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
annotationService.searchOne(row.entity).then(function(response) { 
$log.debug(response.data);
$rootScope.openNode.annotation=true;
annotationService.setSelectedEntity(response.data[0]);
});
angular.element('#annotationTabs li:eq(0) a').tab('show');
}
else 
annotationService.setSelectedEntity(null);
delete $rootScope.openNode.annotation;
annotationService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
annotationService.initAnnotationAttributeList().then(function(response) {
annotationAttributeService.preparedData.entityList=response.data;
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
annotationAttributeService.setSelectedEntity(null);
annotationAttributeService.selectedEntity.show=false;
delete $rootScope.openNode.annotationAttribute;
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
