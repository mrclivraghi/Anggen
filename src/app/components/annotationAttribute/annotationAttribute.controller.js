(function() { 

angular
.module("serverTestApp")
.controller("AnnotationAttributeController",AnnotationAttributeController);
/** @ngInject */
function AnnotationAttributeController($scope,$http,$rootScope ,annotationAttributeService, SecurityService, MainService ,annotationService,fieldService,entityService,restrictionEntityService,roleService,restrictionFieldService,userService,restrictionEntityGroupService,entityGroupService,projectService,enumEntityService,enumValueService,tabService,enumFieldService,relationshipService)
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
$('#annotationAttributeTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
annotationAttributeService.selectedEntity.show=false;
delete $rootScope.openNode.annotationAttribute;
annotationAttributeService.search().then(function successCallback(response) {
annotationAttributeService.setEntityList(response.data);
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
};
$scope.insert=function()
{
if (!$scope.annotationAttributeDetailForm.$valid) return; 
if (annotationAttributeService.isParent()) 
{
annotationAttributeService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
else 
{
annotationAttributeService.selectedEntity.show=false;
annotationAttributeService.insert().then(function successCallBack(response) { 
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
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
$scope.search();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
else 
{
annotationAttributeService.selectedEntity.show=false;

annotationAttributeService.update().then(function successCallback(response){
annotationAttributeService.setSelectedEntity(response.data);
updateParentEntities();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
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
if (annotationAttributeService.isParent()) 
{
$scope.search();
} else { 
annotationAttributeService.setSelectedEntity(null);
}
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
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
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
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
console.log("INDEX!=NULLLLLLLLLLLL");
console.log(response);
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
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
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
$('#annotationTabs li:eq(0) a').tab('show');
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("annotationAttribute.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadAnnotationList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("annotation.xls",?) FROM ?',[mystyle,$scope.selectedEntity.annotationList]);
};
$scope.annotationAttributeGridOptions={};
cloneObject(annotationAttributeService.gridOptions,$scope.annotationAttributeGridOptions);
$scope.annotationAttributeGridOptions.data=annotationAttributeService.entityList;
$scope.initChildrenList = function () { 
}
$scope.annotationAttributeGridOptions.onRegisterApi = function(gridApi){
$scope.annotationAttributeGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
annotationAttributeService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
$rootScope.openNode.annotationAttribute=true;
annotationAttributeService.setSelectedEntity(response.data[0]);
});
$('#annotationAttributeTabs li:eq(0) a').tab('show');
}
else 
annotationAttributeService.setSelectedEntity(null);
delete $rootScope.openNode.annotationAttribute;
annotationAttributeService.selectedEntity.show = row.isSelected;
});
  };
$scope.annotationGridOptions={};
cloneObject(annotationService.gridOptions,$scope.annotationGridOptions);
$scope.annotationGridOptions.data=$scope.selectedEntity.annotationList;
$scope.initChildrenList = function () { 
}
$scope.annotationGridOptions.onRegisterApi = function(gridApi){
$scope.annotationGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
annotationService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
$rootScope.openNode.annotation=true;
annotationService.setSelectedEntity(response.data[0]);
});
$('#annotationTabs li:eq(0) a').tab('show');
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
console.log("response-ok");
console.log(response);
annotationService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
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
