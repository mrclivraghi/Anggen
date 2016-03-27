angular.module("anggenApp").controller("enumEntityController",enumEntityController);
function enumEntityController($scope,$http ,enumEntityService, securityService, mainService ,projectService,enumValueService)
{
$scope.searchBean=enumEntityService.searchBean;
$scope.entityList=enumEntityService.entityList;
$scope.selectedEntity=enumEntityService.selectedEntity;
$scope.childrenList=enumEntityService.childrenList; 
$scope.reset = function()
{
enumEntityService.resetSearchBean();
$scope.searchBean=enumEntityService.searchBean;enumEntityService.setSelectedEntity(null);
enumEntityService.selectedEntity.show=false;
enumEntityService.setEntityList(null); 
if (enumEntityService.isParent()) 
{
projectService.selectedEntity.show=false;
enumValueService.selectedEntity.show=false;
}
}
$scope.addNew= function()
{
enumEntityService.setSelectedEntity(null);
enumEntityService.setEntityList(null);
enumEntityService.selectedEntity.show=true;
if (enumEntityService.isParent()) 
{
projectService.selectedEntity.show=false;
enumValueService.selectedEntity.show=false;
}
$('#enumEntityTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
enumEntityService.selectedEntity.show=false;
enumEntityService.searchBean.enumValueList=[];
enumEntityService.searchBean.enumValueList.push(enumEntityService.searchBean.enumValue);
delete enumEntityService.searchBean.enumValue; 
enumEntityService.search().then(function successCallback(response) {
enumEntityService.setEntityList(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.insert=function()
{
if (!$scope.enumEntityDetailForm.$valid) return; 
if (enumEntityService.isParent()) 
{
enumEntityService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
}
else 
{
enumEntityService.selectedEntity.show=false;
enumEntityService.insert().then(function successCallBack(response) { 
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
}
};
$scope.update=function()
{
if (!$scope.enumEntityDetailForm.$valid) return; 
if (enumEntityService.isParent()) 
{
projectService.selectedEntity.show=false;
enumValueService.selectedEntity.show=false;
enumEntityService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
}
else 
{
enumEntityService.selectedEntity.show=false;

enumEntityService.update().then(function successCallback(response){
enumEntityService.setSelectedEntity(response.data);
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
enumEntityService.selectedEntity.show=false;
enumEntityService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!enumEntityService.isParent()) 
$scope.updateParent();
enumEntityService.del().then(function successCallback(response) { 
if (enumEntityService.isParent()) 
{
$scope.search();
} else { 
enumEntityService.setSelectedEntity(null);
}
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.projectGridApi!=undefined && $scope.projectGridApi!=null)
 $scope.projectGridApi.core.handleWindowResize(); 
if ($scope.enumValueGridApi!=undefined && $scope.enumValueGridApi!=null)
 $scope.enumValueGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
enumEntityService.loadFile(file,field).then(function successCallback(response) {
enumEntityService.setSelectedEntity(response.data);
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
return; 
});
}
$scope.trueFalseValues=[true,false];
$scope.showProjectDetail= function(index)
{
if (index!=null)
{
projectService.searchOne(enumEntityService.selectedEntity.projectList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
projectService.initEnumEntityList().then(function successCallback(response) {
projectService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
projectService.initEntityGroupList().then(function successCallback(response) {
projectService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
projectService.setSelectedEntity(response.data[0]);
projectService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (enumEntityService.selectedEntity.project==null || enumEntityService.selectedEntity.project==undefined)
{
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
projectService.initEnumEntityList().then(function successCallback(response) {
projectService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
projectService.initEntityGroupList().then(function successCallback(response) {
projectService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
projectService.setSelectedEntity(null); 
projectService.selectedEntity.show=true; 
}
else
projectService.searchOne(enumEntityService.selectedEntity.project).then(
function successCallback(response) {
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
projectService.initEnumEntityList().then(function successCallback(response) {
projectService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
projectService.initEntityGroupList().then(function successCallback(response) {
projectService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
projectService.setSelectedEntity(response.data[0]);
projectService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#projectTabs li:eq(0) a').tab('show');
};
$scope.showEnumValueDetail= function(index)
{
if (index!=null)
{
enumValueService.searchOne(enumEntityService.selectedEntity.enumValueList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
enumValueService.initEnumEntityList().then(function successCallback(response) {
enumValueService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
enumValueService.setSelectedEntity(response.data[0]);
enumValueService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
else 
{
if (enumEntityService.selectedEntity.enumValue==null || enumEntityService.selectedEntity.enumValue==undefined)
{
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
enumValueService.initEnumEntityList().then(function successCallback(response) {
enumValueService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
enumValueService.setSelectedEntity(null); 
enumValueService.selectedEntity.show=true; 
}
else
enumValueService.searchOne(enumEntityService.selectedEntity.enumValue).then(
function successCallback(response) {
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
enumValueService.initEnumEntityList().then(function successCallback(response) {
enumValueService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
enumValueService.setSelectedEntity(response.data[0]);
enumValueService.selectedEntity.show=true;
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
$('#enumValueTabs li:eq(0) a').tab('show');
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumEntity.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadProjectList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("project.xls",?) FROM ?',[mystyle,$scope.selectedEntity.projectList]);
};
$scope.saveLinkedEnumValue= function() {
enumEntityService.selectedEntity.enumValueList.push(enumEntityService.selectedEntity.enumValue);
}
$scope.downloadEnumValueList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumValue.xls",?) FROM ?',[mystyle,$scope.selectedEntity.enumValueList]);
};
$scope.enumEntityGridOptions={};
cloneObject(enumEntityService.gridOptions,$scope.enumEntityGridOptions);
$scope.enumEntityGridOptions.data=enumEntityService.entityList;
$scope.enumEntityGridOptions.onRegisterApi = function(gridApi){
$scope.enumEntityGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
if (securityService.restrictionList.project==undefined || securityService.restrictionList.project.canSearch)
enumEntityService.initProjectList().then(function successCallback(response) {
enumEntityService.childrenList.projectList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.enumValue==undefined || securityService.restrictionList.enumValue.canSearch)
enumEntityService.initEnumValueList().then(function successCallback(response) {
enumEntityService.childrenList.enumValueList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
enumEntityService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
enumEntityService.setSelectedEntity(response.data[0]);
});
$('#enumEntityTabs li:eq(0) a').tab('show');
}
else 
enumEntityService.setSelectedEntity(null);
enumEntityService.selectedEntity.show = row.isSelected;
});
  };
$scope.projectGridOptions={};
cloneObject(projectService.gridOptions,$scope.projectGridOptions);
$scope.projectGridOptions.data=$scope.selectedEntity.projectList;
$scope.projectGridOptions.onRegisterApi = function(gridApi){
$scope.projectGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
projectService.initEnumEntityList().then(function successCallback(response) {
projectService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
if (securityService.restrictionList.entityGroup==undefined || securityService.restrictionList.entityGroup.canSearch)
projectService.initEntityGroupList().then(function successCallback(response) {
projectService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
projectService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
projectService.setSelectedEntity(response.data[0]);
});
$('#projectTabs li:eq(0) a').tab('show');
}
else 
projectService.setSelectedEntity(null);
projectService.selectedEntity.show = row.isSelected;
});
  };
$scope.enumValueGridOptions={};
cloneObject(enumValueService.gridOptions,$scope.enumValueGridOptions);
$scope.enumValueGridOptions.data=$scope.selectedEntity.enumValueList;
$scope.enumValueGridOptions.onRegisterApi = function(gridApi){
$scope.enumValueGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
if (securityService.restrictionList.enumEntity==undefined || securityService.restrictionList.enumEntity.canSearch)
enumValueService.initEnumEntityList().then(function successCallback(response) {
enumValueService.childrenList.enumEntityList=response.data;
},function errorCallback(response) { 
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
});
enumValueService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
enumValueService.setSelectedEntity(response.data[0]);
});
$('#enumValueTabs li:eq(0) a').tab('show');
}
else 
enumValueService.setSelectedEntity(null);
enumValueService.selectedEntity.show = row.isSelected;
});
  };
function updateParentEntities() { 
enumFieldService.initEnumEntityList().then(function(response) {
enumFieldService.childrenList.enumEntityList=response.data;
});

if (enumFieldService.selectedEntity.enumFieldId!=undefined) enumFieldService.searchOne(enumFieldService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
enumFieldService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
enumValueService.initEnumEntityList().then(function(response) {
enumValueService.childrenList.enumEntityList=response.data;
});

if (enumValueService.selectedEntity.enumValueId!=undefined) enumValueService.searchOne(enumValueService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
enumValueService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
projectService.initEnumEntityList().then(function(response) {
projectService.childrenList.enumEntityList=response.data;
});

if (projectService.selectedEntity.projectId!=undefined) projectService.searchOne(projectService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
projectService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
AlertError.init({selector: "#alertError"});
AlertError.show("Si è verificato un errore");
return; 
  }	
);
}
};
