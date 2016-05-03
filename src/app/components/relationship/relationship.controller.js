(function() { 

angular
.module("serverTest")
.controller("RelationshipController",RelationshipController);
/** @ngInject */
function RelationshipController($scope,$http,$rootScope,$log,UtilityService ,relationshipService, SecurityService, MainService ,annotationService,entityService,entityService,tabService)
{
var vm=this;
vm.activeTab=1;
vm.searchBean=relationshipService.searchBean;
vm.entityList=relationshipService.entityList;
vm.selectedEntity=relationshipService.selectedEntity;
vm.annotationPreparedData=annotationService.preparedData;
vm.entityPreparedData=entityService.preparedData;
vm.entityPreparedData=entityService.preparedData;
vm.tabPreparedData=tabService.preparedData;
vm.relationshipTypePreparedData={};
vm.relationshipTypePreparedData.entityList=["ONE_TO_ONE","ONE_TO_MANY","MANY_TO_ONE","MANY_TO_MANY","MANY_TO_MANY_BACK" ];
function reset()
{
relationshipService.resetSearchBean();
vm.searchBean=relationshipService.searchBean;
relationshipService.setSelectedEntity(null);
relationshipService.selectedEntity.show=false;
relationshipService.setEntityList(null); 
if (relationshipService.isParent()) 
{
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
UtilityService.removeObjectFromList($rootScope.parentServices,annotationService);
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
tabService.selectedEntity.show=false;
delete $rootScope.openNode.tab;
UtilityService.removeObjectFromList($rootScope.parentServices,tabService);
}
}
function addNew()
{
$rootScope.openNode.relationship=true;
relationshipService.setSelectedEntity(null);
relationshipService.setEntityList(null);
relationshipService.selectedEntity.show=true;
if (relationshipService.isParent()) 
{
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
UtilityService.removeObjectFromList($rootScope.parentServices,annotationService);
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
tabService.selectedEntity.show=false;
delete $rootScope.openNode.tab;
UtilityService.removeObjectFromList($rootScope.parentServices,tabService);
}
angular.element('#relationshipTabs li:eq(0) a').tab('show');
}
		
function search()
{
relationshipService.selectedEntity.show=false;
delete $rootScope.openNode.relationship;
UtilityService.removeObjectFromList($rootScope.parentServices,relationshipService);
relationshipService.searchBean.annotationList=[];
relationshipService.searchBean.annotationList.push(relationshipService.searchBean.annotation);
delete relationshipService.searchBean.annotation; 
relationshipService.search().then(function successCallback(response) {
relationshipService.setEntityList(response.data);
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
function insert()
{
if (!$scope.relationshipDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
relationshipService.insert().then(function successCallback(response) { 
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
relationshipService.selectedEntity.show=false;
relationshipService.insert().then(function successCallBack(response) { 
$log.debug(response);
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removerelationship(relationshipService.selectedEntity);
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
if (!$scope.relationshipDetailForm.$valid) return; 
$rootScope.parentServices.pop();
if ($rootScope.parentServices.length==0) 
{
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
UtilityService.removeObjectFromList($rootScope.parentServices,annotationService);
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
entityService.selectedEntity.show=false;
delete $rootScope.openNode.entity;
UtilityService.removeObjectFromList($rootScope.parentServices,entityService);
tabService.selectedEntity.show=false;
delete $rootScope.openNode.tab;
UtilityService.removeObjectFromList($rootScope.parentServices,tabService);
relationshipService.update().then(function successCallback(response) { 
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
relationshipService.selectedEntity.show=false;

relationshipService.update().then(function successCallback(response){
relationshipService.setSelectedEntity(response.data);
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
relationshipService.selectedEntity.show=false;
delete $rootScope.openNode.relationship;
$rootScope.parentServices.pop();
var parentService=$rootScope.parentServices.pop();
parentService.removeRelationship(relationshipService.selectedEntity);
UtilityService.removeObjectFromList($rootScope.parentServices,relationshipService);
relationshipService.setSelectedEntity(null);
}
function del()
{
$rootScope.parentServices.pop();
relationshipService.del().then(function successCallback(response) { 
$log.debug(response);
relationshipService.setSelectedEntity(null);
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
if ($scope.annotationGridApi!=undefined && $scope.annotationGridApi!=null)
 $scope.annotationGridApi.core.handleWindowResize(); 
if ($scope.entityGridApi!=undefined && $scope.entityGridApi!=null)
 $scope.entityGridApi.core.handleWindowResize(); 
if ($scope.entityGridApi!=undefined && $scope.entityGridApi!=null)
 $scope.entityGridApi.core.handleWindowResize(); 
if ($scope.tabGridApi!=undefined && $scope.tabGridApi!=null)
 $scope.tabGridApi.core.handleWindowResize(); 
}
vm.refreshTableDetail=refreshTableDetail;
function loadFile(file,field)
{
relationshipService.loadFile(file,field).then(function successCallback(response) {
relationshipService.setSelectedEntity(response.data);
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
annotationService.searchOne(relationshipService.selectedEntity.annotationList[index]).then(
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
if (relationshipService.selectedEntity.annotation==null || relationshipService.selectedEntity.annotation==undefined)
{
annotationService.setSelectedEntity(null); 
annotationService.selectedEntity.show=true; 
$rootScope.openNode.annotation=true;
}
else
annotationService.searchOne(relationshipService.selectedEntity.annotation).then(
function successCallback(response) {
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
$rootScope.openNode.annotation=true;
$rootScope.parentServices.push(annotationService);
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
 function showEntityDetail(index)
{
if (index!=null)
{
entityService.searchOne(relationshipService.selectedEntity.entityList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
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
if (relationshipService.selectedEntity.entity==null || relationshipService.selectedEntity.entity==undefined)
{
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
$rootScope.openNode.entity=true;
}
else
entityService.searchOne(relationshipService.selectedEntity.entity).then(
function successCallback(response) {
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
$rootScope.openNode.entity=true;
$rootScope.parentServices.push(entityService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#entityTabs li:eq(0) a').tab('show');
}
vm.showEntityDetail=showEntityDetail;
 function showEntityDetail(index)
{
if (index!=null)
{
entityService.searchOne(relationshipService.selectedEntity.entityList[index]).then(
function successCallback(response) {
$log.debug("INDEX!=NULLLLLLLLLLLL");
$log.debug(response);
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
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
if (relationshipService.selectedEntity.entity==null || relationshipService.selectedEntity.entity==undefined)
{
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
$rootScope.openNode.entity=true;
}
else
entityService.searchOne(relationshipService.selectedEntity.entity).then(
function successCallback(response) {
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
$rootScope.openNode.entity=true;
$rootScope.parentServices.push(entityService);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
angular.element('#entityTabs li:eq(0) a').tab('show');
}
vm.showEntityDetail=showEntityDetail;
 function showTabDetail(index)
{
if (index!=null)
{
tabService.searchOne(relationshipService.selectedEntity.tabList[index]).then(
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
if (relationshipService.selectedEntity.tab==null || relationshipService.selectedEntity.tab==undefined)
{
tabService.setSelectedEntity(null); 
tabService.selectedEntity.show=true; 
$rootScope.openNode.tab=true;
}
else
tabService.searchOne(relationshipService.selectedEntity.tab).then(
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
function downloadList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("relationship.xls",?) FROM ?',[mystyle,vm.entityList]);
}
function saveLinkedAnnotation() {
relationshipService.selectedEntity.annotationList.push(relationshipService.selectedEntity.annotation);
}
vm.saveLinkedAnnotation=saveLinkedAnnotation;
function downloadAnnotationList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("annotation.xls",?) FROM ?',[mystyle,vm.selectedEntity.annotationList]);
}
vm.downloadAnnotationList=downloadAnnotationList;
function downloadEntityList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,vm.selectedEntity.entityList]);
}
vm.downloadEntityList=downloadEntityList;
function downloadEntityList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,vm.selectedEntity.entityList]);
}
vm.downloadEntityList=downloadEntityList;
function downloadTabList()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
}
UtilityService.alasql('SELECT * INTO XLSXML("tab.xls",?) FROM ?',[mystyle,vm.selectedEntity.tabList]);
}
vm.downloadTabList=downloadTabList;
vm.relationshipGridOptions={};
UtilityService.cloneObject(relationshipService.gridOptions,vm.relationshipGridOptions);
vm.relationshipGridOptions.data=relationshipService.entityList;
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
$rootScope.parentServices.push(annotationService);
annotationService.setSelectedEntity(response.data[0]);
});
angular.element('#annotationTabs li:eq(0) a').tab('show');
}
else 
annotationService.setSelectedEntity(null);
delete $rootScope.openNode.annotation;
UtilityService.removeObjectFromList($rootScope.parentServices,annotationService);
annotationService.selectedEntity.show = row.isSelected;
});
  };
vm.entityGridOptions={};
UtilityService.cloneObject(entityService.gridOptions,vm.entityGridOptions);
vm.entityGridOptions.data=vm.selectedEntity.entityList;
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
vm.entityGridOptions={};
UtilityService.cloneObject(entityService.gridOptions,vm.entityGridOptions);
vm.entityGridOptions.data=vm.selectedEntity.entityList;
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
function updateParentEntities() { 
tabService.initRelationshipList().then(function(response) {
relationshipService.preparedData.entityList=response.data;
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
annotationService.initRelationshipList().then(function(response) {
relationshipService.preparedData.entityList=response.data;
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
entityService.initRelationshipList().then(function(response) {
relationshipService.preparedData.entityList=response.data;
});

if (entityService.selectedEntity.entityId!=undefined) entityService.searchOne(entityService.selectedEntity).then(
function successCallback(response) {
$log.debug("response-ok");
$log.debug(response);
entityService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
  }	
);
}
function closeEntityDetail(){ 
relationshipService.setSelectedEntity(null);
relationshipService.selectedEntity.show=false;
delete $rootScope.openNode.relationship;
UtilityService.removeObjectFromList($rootScope.parentServices,relationshipService);
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
