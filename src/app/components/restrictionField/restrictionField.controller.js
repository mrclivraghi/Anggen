(function() { 

angular
.module("serverTestApp")
.controller("RestrictionFieldController",RestrictionFieldController);
/** @ngInject */
function RestrictionFieldController($scope,$http ,restrictionFieldService, SecurityService, MainService ,roleService,enumFieldService,annotationService,annotationAttributeService,fieldService,tabService,entityService,entityGroupService,relationshipService,projectService,enumEntityService,enumValueService)
{
$scope.searchBean=restrictionFieldService.searchBean;
$scope.entityList=restrictionFieldService.entityList;
$scope.selectedEntity=restrictionFieldService.selectedEntity;
$scope.childrenList=restrictionFieldService.childrenList; 
$scope.reset = function()
{
restrictionFieldService.resetSearchBean();
$scope.searchBean=restrictionFieldService.searchBean;restrictionFieldService.setSelectedEntity(null);
restrictionFieldService.selectedEntity.show=false;
restrictionFieldService.setEntityList(null); 
if (restrictionFieldService.isParent()) 
{
roleService.selectedEntity.show=false;
fieldService.selectedEntity.show=false;
}
}
$scope.addNew= function()
{
restrictionFieldService.setSelectedEntity(null);
restrictionFieldService.setEntityList(null);
restrictionFieldService.selectedEntity.show=true;
if (restrictionFieldService.isParent()) 
{
roleService.selectedEntity.show=false;
fieldService.selectedEntity.show=false;
}
$('#restrictionFieldTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
restrictionFieldService.selectedEntity.show=false;
restrictionFieldService.search().then(function successCallback(response) {
restrictionFieldService.setEntityList(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.insert=function()
{
if (!$scope.restrictionFieldDetailForm.$valid) return; 
if (restrictionFieldService.isParent()) 
{
restrictionFieldService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
}
else 
{
restrictionFieldService.selectedEntity.show=false;
restrictionFieldService.insert().then(function successCallBack(response) { 
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
}
};
$scope.update=function()
{
if (!$scope.restrictionFieldDetailForm.$valid) return; 
if (restrictionFieldService.isParent()) 
{
roleService.selectedEntity.show=false;
fieldService.selectedEntity.show=false;
restrictionFieldService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
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
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
}
};
$scope.remove= function()
{
restrictionFieldService.selectedEntity.show=false;
restrictionFieldService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!restrictionFieldService.isParent()) 
$scope.updateParent();
restrictionFieldService.del().then(function successCallback(response) { 
if (restrictionFieldService.isParent()) 
{
$scope.search();
} else { 
restrictionFieldService.setSelectedEntity(null);
}
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.roleGridApi!=undefined && $scope.roleGridApi!=null)
 $scope.roleGridApi.core.handleWindowResize(); 
if ($scope.fieldGridApi!=undefined && $scope.fieldGridApi!=null)
 $scope.fieldGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
restrictionFieldService.loadFile(file,field).then(function successCallback(response) {
restrictionFieldService.setSelectedEntity(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
return; 
});
}
$scope.trueFalseValues=[true,false];
$scope.showRoleDetail= function(index)
{
if (index!=null)
{
roleService.searchOne(restrictionFieldService.selectedEntity.roleList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (SecurityService.restrictionList.restrictionField==undefined || SecurityService.restrictionList.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.enumField==undefined || SecurityService.restrictionList.enumField.canSearch)
roleService.initEnumFieldList().then(function successCallback(response) {
roleService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
roleService.initAnnotationList().then(function successCallback(response) {
roleService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.relationship==undefined || SecurityService.restrictionList.relationship.canSearch)
roleService.initRelationshipList().then(function successCallback(response) {
roleService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (restrictionFieldService.selectedEntity.role==null || restrictionFieldService.selectedEntity.role==undefined)
{
if (SecurityService.restrictionList.restrictionField==undefined || SecurityService.restrictionList.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.enumField==undefined || SecurityService.restrictionList.enumField.canSearch)
roleService.initEnumFieldList().then(function successCallback(response) {
roleService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
roleService.initAnnotationList().then(function successCallback(response) {
roleService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.relationship==undefined || SecurityService.restrictionList.relationship.canSearch)
roleService.initRelationshipList().then(function successCallback(response) {
roleService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
roleService.setSelectedEntity(null); 
roleService.selectedEntity.show=true; 
}
else
roleService.searchOne(restrictionFieldService.selectedEntity.role).then(
function successCallback(response) {
if (SecurityService.restrictionList.restrictionField==undefined || SecurityService.restrictionList.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.enumField==undefined || SecurityService.restrictionList.enumField.canSearch)
roleService.initEnumFieldList().then(function successCallback(response) {
roleService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
roleService.initAnnotationList().then(function successCallback(response) {
roleService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.relationship==undefined || SecurityService.restrictionList.relationship.canSearch)
roleService.initRelationshipList().then(function successCallback(response) {
roleService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#roleTabs li:eq(0) a').tab('show');
};
$scope.showFieldDetail= function(index)
{
if (index!=null)
{
fieldService.searchOne(restrictionFieldService.selectedEntity.fieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
fieldService.initAnnotationList().then(function successCallback(response) {
fieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.restrictionField==undefined || SecurityService.restrictionList.restrictionField.canSearch)
fieldService.initRestrictionFieldList().then(function successCallback(response) {
fieldService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
fieldService.initTabList().then(function successCallback(response) {
fieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
fieldService.initEntityList().then(function successCallback(response) {
fieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG","FILE",];
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (restrictionFieldService.selectedEntity.field==null || restrictionFieldService.selectedEntity.field==undefined)
{
if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
fieldService.initAnnotationList().then(function successCallback(response) {
fieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.restrictionField==undefined || SecurityService.restrictionList.restrictionField.canSearch)
fieldService.initRestrictionFieldList().then(function successCallback(response) {
fieldService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
fieldService.initTabList().then(function successCallback(response) {
fieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
fieldService.initEntityList().then(function successCallback(response) {
fieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG","FILE",];
fieldService.setSelectedEntity(null); 
fieldService.selectedEntity.show=true; 
}
else
fieldService.searchOne(restrictionFieldService.selectedEntity.field).then(
function successCallback(response) {
if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
fieldService.initAnnotationList().then(function successCallback(response) {
fieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.restrictionField==undefined || SecurityService.restrictionList.restrictionField.canSearch)
fieldService.initRestrictionFieldList().then(function successCallback(response) {
fieldService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
fieldService.initTabList().then(function successCallback(response) {
fieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
fieldService.initEntityList().then(function successCallback(response) {
fieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG","FILE",];
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#fieldTabs li:eq(0) a').tab('show');
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionField.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadRoleList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,$scope.selectedEntity.roleList]);
};
$scope.downloadFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("field.xls",?) FROM ?',[mystyle,$scope.selectedEntity.fieldList]);
};
$scope.restrictionFieldGridOptions={};
cloneObject(restrictionFieldService.gridOptions,$scope.restrictionFieldGridOptions);
$scope.restrictionFieldGridOptions.data=restrictionFieldService.entityList;
$scope.restrictionFieldGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionFieldGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
if (SecurityService.restrictionList.role==undefined || SecurityService.restrictionList.role.canSearch)
restrictionFieldService.initRoleList().then(function successCallback(response) {
restrictionFieldService.childrenList.roleList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.field==undefined || SecurityService.restrictionList.field.canSearch)
restrictionFieldService.initFieldList().then(function successCallback(response) {
restrictionFieldService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
restrictionFieldService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
restrictionFieldService.setSelectedEntity(response.data[0]);
});
$('#restrictionFieldTabs li:eq(0) a').tab('show');
}
else 
restrictionFieldService.setSelectedEntity(null);
restrictionFieldService.selectedEntity.show = row.isSelected;
});
  };
$scope.roleGridOptions={};
cloneObject(roleService.gridOptions,$scope.roleGridOptions);
$scope.roleGridOptions.data=$scope.selectedEntity.roleList;
$scope.roleGridOptions.onRegisterApi = function(gridApi){
$scope.roleGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
if (SecurityService.restrictionList.restrictionField==undefined || SecurityService.restrictionList.restrictionField.canSearch)
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.enumField==undefined || SecurityService.restrictionList.enumField.canSearch)
roleService.initEnumFieldList().then(function successCallback(response) {
roleService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
roleService.initAnnotationList().then(function successCallback(response) {
roleService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.relationship==undefined || SecurityService.restrictionList.relationship.canSearch)
roleService.initRelationshipList().then(function successCallback(response) {
roleService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
roleService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
roleService.setSelectedEntity(response.data[0]);
});
$('#roleTabs li:eq(0) a').tab('show');
}
else 
roleService.setSelectedEntity(null);
roleService.selectedEntity.show = row.isSelected;
});
  };
$scope.fieldGridOptions={};
cloneObject(fieldService.gridOptions,$scope.fieldGridOptions);
$scope.fieldGridOptions.data=$scope.selectedEntity.fieldList;
$scope.fieldGridOptions.onRegisterApi = function(gridApi){
$scope.fieldGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
fieldService.initAnnotationList().then(function successCallback(response) {
fieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.restrictionField==undefined || SecurityService.restrictionList.restrictionField.canSearch)
fieldService.initRestrictionFieldList().then(function successCallback(response) {
fieldService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
fieldService.initTabList().then(function successCallback(response) {
fieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
fieldService.initEntityList().then(function successCallback(response) {
fieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG","FILE",];
fieldService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
fieldService.setSelectedEntity(response.data[0]);
});
$('#fieldTabs li:eq(0) a').tab('show');
}
else 
fieldService.setSelectedEntity(null);
fieldService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
roleService.initRestrictionFieldList().then(function(response) {
roleService.childrenList.restrictionFieldList=response.data;
});

if (roleService.selectedEntity.roleId!=undefined) roleService.searchOne(roleService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
roleService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
fieldService.initRestrictionFieldList().then(function(response) {
fieldService.childrenList.restrictionFieldList=response.data;
});

if (fieldService.selectedEntity.fieldId!=undefined) fieldService.searchOne(fieldService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
fieldService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$scope.closeEntityDetail = function(){ 
restrictionFieldService.setSelectedEntity(null);
restrictionFieldService.selectedEntity.show=false;
}}
})();
