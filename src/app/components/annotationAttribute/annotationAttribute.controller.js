(function() { 

angular
.module("serverTestApp")
.controller("AnnotationAttributeController",AnnotationAttributeController);
/** @ngInject */
function AnnotationAttributeController($scope,$http,$rootScope,$log,UtilityService ,annotationAttributeService, SecurityService, MainService ,annotationService)
{
$scope.searchBean=annotationAttributeService.searchBean;
$scope.entityList=annotationAttributeService.entityList;
$scope.selectedEntity=annotationAttributeService.selectedEntity;
$scope.hidden=annotationAttributeService.hidden;
$scope.annotationPreparedData=annotationService.preparedData;
$scope.reset = function()
{
annotationAttributeService.resetSearchBean();
$scope.searchBean=annotationAttributeService.searchBean;annotationAttributeService.setSelectedEntity(null);
annotationAttributeService.selectedEntity.show=false;
annotationAttributeService.setEntityList(null); 
if (annotationAttributeService.isParent()) 
{
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
}
}
$scope.addNew= function()
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
};
		
$scope.search=function()
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
};
$scope.insert=function()
{
if (!$scope.annotationAttributeDetailForm.$valid) return; 
if (annotationAttributeService.isParent()) 
{
annotationAttributeService.insert().then(function successCallback(response) { 
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
};
$scope.update=function()
{
if (!$scope.annotationAttributeDetailForm.$valid) return; 
if (annotationAttributeService.isParent()) 
{
annotationService.selectedEntity.show=false;
delete $rootScope.openNode.annotation;
annotationAttributeService.update().then(function successCallback(response) { 
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
};
$scope.remove= function()
{
annotationAttributeService.selectedEntity.show=false;
delete $rootScope.openNode.annotationAttribute;
annotationAttributeService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!annotationAttributeService.isParent()) 
$scope.updateParent();
annotationAttributeService.del().then(function successCallback(response) { 
$log.debug(response);
if (annotationAttributeService.isParent()) 
{
$scope.search();
} else { 
annotationAttributeService.setSelectedEntity(null);
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
if ($scope.annotationGridApi!=undefined && $scope.annotationGridApi!=null)
 $scope.annotationGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
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
$scope.trueFalseValues=['',true,false];
$scope.showAnnotationDetail= function(index)
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
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("annotationAttribute.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadAnnotationList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
UtilityService.alasql('SELECT * INTO XLSXML("annotation.xls",?) FROM ?',[mystyle,$scope.selectedEntity.annotationList]);
};
$scope.annotationAttributeGridOptions={};
UtilityService.cloneObject(annotationAttributeService.gridOptions,$scope.annotationAttributeGridOptions);
$scope.annotationAttributeGridOptions.data=annotationAttributeService.entityList;
$scope.initChildrenList = function () { 
}
$scope.annotationAttributeGridOptions.onRegisterApi = function(gridApi){
$scope.annotationAttributeGridApi = gridApi;
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
$scope.annotationGridOptions={};
UtilityService.cloneObject(annotationService.gridOptions,$scope.annotationGridOptions);
$scope.annotationGridOptions.data=$scope.selectedEntity.annotationList;
$scope.initChildrenList = function () { 
}
$scope.annotationGridOptions.onRegisterApi = function(gridApi){
$scope.annotationGridApi = gridApi;
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
$scope.closeEntityDetail = function(){ 
annotationAttributeService.setSelectedEntity(null);
annotationAttributeService.selectedEntity.show=false;
delete $rootScope.openNode.annotationAttribute;
}
}
})();
