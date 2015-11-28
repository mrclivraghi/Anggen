var fieldApp=angular.module("fieldApp",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])
.service("fieldService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,annotationList: [],restrictionFieldList: []};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
for (i=0; i<entityList.length; i++)
this.entityList.push(entityList[i]);
};
this.searchBean = 		new Object();
this.resetSearchBean= function()
{
this.searchBean={};
};
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
} else 
this.emptyList(this.selectedEntity[val]);
} else {
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
} else {
this.selectedEntity[val] = entity[val];
}
}
}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../field/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../field/"+entity.fieldId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../field/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../field/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../field/"+this.selectedEntity.fieldId;
var promise= $http["delete"](url);
return promise; 
}
 this.initEntityList= function()
{
var promise= $http
.post("../entity/search",
{});
return promise;
};
 this.initAnnotationList= function()
{
var promise= $http
.post("../annotation/search",
{});
return promise;
};
 this.initRestrictionFieldList= function()
{
var promise= $http
.post("../restrictionField/search",
{});
return promise;
};
 this.initTabList= function()
{
var promise= $http
.post("../tab/search",
{});
return promise;
};
})
.controller("fieldController",function($scope,$http,fieldService,entityService,annotationService,restrictionFieldService,tabService,relationshipService,enumFieldService,restrictionEntityService,entityGroupService,enumValueService,roleService,userService,restrictionEntityGroupService,annotationAttributeService)
{
//null
$scope.searchBean=fieldService.searchBean;
$scope.entityList=fieldService.entityList;
$scope.selectedEntity=fieldService.selectedEntity;
$scope.childrenList=fieldService.childrenList; 
$scope.reset = function()
{
fieldService.resetSearchBean();
$scope.searchBean=fieldService.searchBean;fieldService.setSelectedEntity(null);
fieldService.selectedEntity.show=false;
fieldService.setEntityList(null); 
entityService.selectedEntity.show=false;annotationService.selectedEntity.show=false;restrictionFieldService.selectedEntity.show=false;tabService.selectedEntity.show=false;relationshipService.selectedEntity.show=false;enumFieldService.selectedEntity.show=false;restrictionEntityService.selectedEntity.show=false;entityGroupService.selectedEntity.show=false;enumValueService.selectedEntity.show=false;roleService.selectedEntity.show=false;userService.selectedEntity.show=false;restrictionEntityGroupService.selectedEntity.show=false;annotationAttributeService.selectedEntity.show=false;}
$scope.addNew= function()
{
fieldService.setSelectedEntity(null);
fieldService.setEntityList(null);
fieldService.selectedEntity.show=true;
entityService.selectedEntity.show=false;annotationService.selectedEntity.show=false;restrictionFieldService.selectedEntity.show=false;tabService.selectedEntity.show=false;relationshipService.selectedEntity.show=false;enumFieldService.selectedEntity.show=false;restrictionEntityService.selectedEntity.show=false;entityGroupService.selectedEntity.show=false;enumValueService.selectedEntity.show=false;roleService.selectedEntity.show=false;userService.selectedEntity.show=false;restrictionEntityGroupService.selectedEntity.show=false;annotationAttributeService.selectedEntity.show=false;$('#fieldTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
fieldService.selectedEntity.show=false;
fieldService.searchBean.annotationList=[];
fieldService.searchBean.annotationList.push(fieldService.searchBean.annotation);
delete fieldService.searchBean.annotation; 
fieldService.searchBean.restrictionFieldList=[];
fieldService.searchBean.restrictionFieldList.push(fieldService.searchBean.restrictionField);
delete fieldService.searchBean.restrictionField; 
fieldService.search().then(function successCallback(response) {
fieldService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.fieldDetailForm.$valid) return; 
fieldService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.fieldDetailForm.$valid) return; 
entityService.selectedEntity.show=false;annotationService.selectedEntity.show=false;restrictionFieldService.selectedEntity.show=false;tabService.selectedEntity.show=false;relationshipService.selectedEntity.show=false;enumFieldService.selectedEntity.show=false;restrictionEntityService.selectedEntity.show=false;entityGroupService.selectedEntity.show=false;enumValueService.selectedEntity.show=false;roleService.selectedEntity.show=false;userService.selectedEntity.show=false;restrictionEntityGroupService.selectedEntity.show=false;annotationAttributeService.selectedEntity.show=false;fieldService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.del=function()
{
fieldService.del().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
 $scope.annotationGridApi.core.handleWindowResize(); 
 $scope.restrictionFieldGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(fieldService.selectedEntity.entityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (fieldService.selectedEntity.entity==null || fieldService.selectedEntity.entity==undefined)
{
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
}
else
entityService.searchOne(fieldService.selectedEntity.entity).then(
function successCallback(response) {
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#entityTabs li:eq(0) a').tab('show');
};
$scope.showAnnotationDetail= function(index)
{
if (index!=null)
{
annotationService.searchOne(fieldService.selectedEntity.annotationList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (fieldService.selectedEntity.annotation==null || fieldService.selectedEntity.annotation==undefined)
{
annotationService.setSelectedEntity(null); 
annotationService.selectedEntity.show=true; 
}
else
annotationService.searchOne(fieldService.selectedEntity.annotation).then(
function successCallback(response) {
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#annotationTabs li:eq(0) a').tab('show');
};
$scope.showRestrictionFieldDetail= function(index)
{
if (index!=null)
{
restrictionFieldService.searchOne(fieldService.selectedEntity.restrictionFieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
restrictionFieldService.setSelectedEntity(response.data[0]);
restrictionFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (fieldService.selectedEntity.restrictionField==null || fieldService.selectedEntity.restrictionField==undefined)
{
restrictionFieldService.setSelectedEntity(null); 
restrictionFieldService.selectedEntity.show=true; 
}
else
restrictionFieldService.searchOne(fieldService.selectedEntity.restrictionField).then(
function successCallback(response) {
restrictionFieldService.setSelectedEntity(response.data[0]);
restrictionFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#restrictionFieldTabs li:eq(0) a').tab('show');
};
$scope.showTabDetail= function(index)
{
if (index!=null)
{
tabService.searchOne(fieldService.selectedEntity.tabList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (fieldService.selectedEntity.tab==null || fieldService.selectedEntity.tab==undefined)
{
tabService.setSelectedEntity(null); 
tabService.selectedEntity.show=true; 
}
else
tabService.searchOne(fieldService.selectedEntity.tab).then(
function successCallback(response) {
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#tabTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
fieldService.initEntityList().then(function successCallback(response) {
fieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
fieldService.initAnnotationList().then(function successCallback(response) {
fieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
fieldService.initRestrictionFieldList().then(function successCallback(response) {
fieldService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
fieldService.initTabList().then(function successCallback(response) {
fieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","ENUM",];
}; 
$scope.init();
$scope.fieldGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'fieldId'},
{ name: 'name'},
{ name: 'entity.entityId', displayName: 'entity'},
{ name: 'fieldType'},
{ name: 'tab.tabId', displayName: 'tab'} 
]
,data: fieldService.entityList
 };
$scope.fieldGridOptions.onRegisterApi = function(gridApi){
$scope.fieldGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
entityService.selectedEntity.show=false;annotationService.selectedEntity.show=false;restrictionFieldService.selectedEntity.show=false;tabService.selectedEntity.show=false;relationshipService.selectedEntity.show=false;enumFieldService.selectedEntity.show=false;restrictionEntityService.selectedEntity.show=false;entityGroupService.selectedEntity.show=false;enumValueService.selectedEntity.show=false;roleService.selectedEntity.show=false;userService.selectedEntity.show=false;restrictionEntityGroupService.selectedEntity.show=false;annotationAttributeService.selectedEntity.show=false;if (row.isSelected)
{
fieldService.setSelectedEntity(row.entity);
$('#fieldTabs li:eq(0) a').tab('show');
}
else 
fieldService.setSelectedEntity(null);
fieldService.selectedEntity.show = row.isSelected;
});
  };
$scope.annotationListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'annotationId'},
{ name: 'annotationType'} 
]
,data: $scope.selectedEntity.annotationList
 };
$scope.annotationListGridOptions.onRegisterApi = function(gridApi){
$scope.annotationGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
annotationService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
annotationService.setSelectedEntity(response.data[0]);
});
$('#annotationTabs li:eq(0) a').tab('show');
}
else 
annotationService.setSelectedEntity(null);
annotationService.selectedEntity.show = row.isSelected;
});
  };
$scope.restrictionFieldListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'restrictionFieldId'} 
]
,data: $scope.selectedEntity.restrictionFieldList
 };
$scope.restrictionFieldListGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionFieldGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
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
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("field.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityList]);
};
$scope.saveLinkedAnnotation= function() {
fieldService.selectedEntity.annotationList.push(fieldService.selectedEntity.annotation);
}
$scope.downloadAnnotationList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("annotation.xls",?) FROM ?',[mystyle,$scope.selectedEntity.annotationList]);
};
$scope.saveLinkedRestrictionField= function() {
fieldService.selectedEntity.restrictionFieldList.push(fieldService.selectedEntity.restrictionField);
}
$scope.downloadRestrictionFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionField.xls",?) FROM ?',[mystyle,$scope.selectedEntity.restrictionFieldList]);
};
$scope.downloadTabList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("tab.xls",?) FROM ?',[mystyle,$scope.selectedEntity.tabList]);
};
})
.service("entityService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,fieldList: [],relationshipList: [],enumFieldList: [],tabList: [],restrictionEntityList: []};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
for (i=0; i<entityList.length; i++)
this.entityList.push(entityList[i]);
};
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
} else 
this.emptyList(this.selectedEntity[val]);
} else {
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
} else {
this.selectedEntity[val] = entity[val];
}
}
}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../entity/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../entity/"+entity.entityId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../entity/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../entity/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../entity/"+this.selectedEntity.entityId;
var promise= $http["delete"](url);
return promise; 
}
 this.initFieldList= function()
{
var promise= $http
.post("../field/search",
{});
return promise;
};
 this.initRelationshipList= function()
{
var promise= $http
.post("../relationship/search",
{});
return promise;
};
 this.initEnumFieldList= function()
{
var promise= $http
.post("../enumField/search",
{});
return promise;
};
 this.initTabList= function()
{
var promise= $http
.post("../tab/search",
{});
return promise;
};
 this.initRestrictionEntityList= function()
{
var promise= $http
.post("../restrictionEntity/search",
{});
return promise;
};
 this.initEntityGroupList= function()
{
var promise= $http
.post("../entityGroup/search",
{});
return promise;
};
})
.controller("entityController",function($scope,$http,entityService,fieldService,relationshipService,enumFieldService,tabService,restrictionEntityService,entityGroupService,annotationService,restrictionFieldService,annotationAttributeService,roleService,userService,restrictionEntityGroupService,enumValueService)
{
//field
$scope.searchBean=entityService.searchBean;
$scope.entityList=entityService.entityList;
$scope.selectedEntity=entityService.selectedEntity;
$scope.childrenList=entityService.childrenList; 
$scope.reset = function()
{
entityService.resetSearchBean();
$scope.searchBean=entityService.searchBean;entityService.setSelectedEntity(null);
entityService.selectedEntity.show=false;
entityService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
fieldService.update().then(function successCallback(response) {
fieldService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
alert("error");
return; 
}
);
};
$scope.addNew= function()
{
entityService.setSelectedEntity(null);
entityService.setEntityList(null);
entityService.selectedEntity.show=true;
$('#entityTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
entityService.selectedEntity.show=false;
entityService.searchBean.fieldList=[];
entityService.searchBean.fieldList.push(entityService.searchBean.field);
delete entityService.searchBean.field; 
entityService.searchBean.relationshipList=[];
entityService.searchBean.relationshipList.push(entityService.searchBean.relationship);
delete entityService.searchBean.relationship; 
entityService.searchBean.enumFieldList=[];
entityService.searchBean.enumFieldList.push(entityService.searchBean.enumField);
delete entityService.searchBean.enumField; 
entityService.searchBean.tabList=[];
entityService.searchBean.tabList.push(entityService.searchBean.tab);
delete entityService.searchBean.tab; 
entityService.searchBean.restrictionEntityList=[];
entityService.searchBean.restrictionEntityList.push(entityService.searchBean.restrictionEntity);
delete entityService.searchBean.restrictionEntity; 
entityService.search().then(function successCallback(response) {
entityService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.entityDetailForm.$valid) return; 
entityService.selectedEntity.show=false;
entityService.selectedEntity.field={};
entityService.selectedEntity.field.fieldId=fieldService.selectedEntity.fieldId;
entityService.insert().then(function successCallBack(response) { 
fieldService.selectedEntity.entity=response.data;
fieldService.initEntityList().then(function(response) {
fieldService.childrenList.entityList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.entityDetailForm.$valid) return; 
entityService.selectedEntity.show=false;

fieldService.selectedEntity.entity=entityService.selectedEntity;

entityService.update().then(function successCallback(response){
entityService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
entityService.selectedEntity.show=false;
fieldService.selectedEntity.entity=null;
entityService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
fieldService.selectedEntity.entity=null;
$scope.updateParent();
entityService.del().then(function successCallback(response) { 
entityService.setSelectedEntity(null);
fieldService.initEntityList().then(function(response) {
fieldService.childrenList.entityList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
 $scope.fieldGridApi.core.handleWindowResize(); 
 $scope.relationshipGridApi.core.handleWindowResize(); 
 $scope.enumFieldGridApi.core.handleWindowResize(); 
 $scope.tabGridApi.core.handleWindowResize(); 
 $scope.restrictionEntityGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showFieldDetail= function(index)
{
if (index!=null)
{
fieldService.searchOne(entityService.selectedEntity.fieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.field==null || entityService.selectedEntity.field==undefined)
{
fieldService.setSelectedEntity(null); 
fieldService.selectedEntity.show=true; 
}
else
fieldService.searchOne(entityService.selectedEntity.field).then(
function successCallback(response) {
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#fieldTabs li:eq(0) a').tab('show');
};
$scope.showRelationshipDetail= function(index)
{
if (index!=null)
{
relationshipService.searchOne(entityService.selectedEntity.relationshipList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
relationshipService.setSelectedEntity(response.data[0]);
relationshipService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.relationship==null || entityService.selectedEntity.relationship==undefined)
{
relationshipService.setSelectedEntity(null); 
relationshipService.selectedEntity.show=true; 
}
else
relationshipService.searchOne(entityService.selectedEntity.relationship).then(
function successCallback(response) {
relationshipService.setSelectedEntity(response.data[0]);
relationshipService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#relationshipTabs li:eq(0) a').tab('show');
};
$scope.showEnumFieldDetail= function(index)
{
if (index!=null)
{
enumFieldService.searchOne(entityService.selectedEntity.enumFieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
enumFieldService.setSelectedEntity(response.data[0]);
enumFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.enumField==null || entityService.selectedEntity.enumField==undefined)
{
enumFieldService.setSelectedEntity(null); 
enumFieldService.selectedEntity.show=true; 
}
else
enumFieldService.searchOne(entityService.selectedEntity.enumField).then(
function successCallback(response) {
enumFieldService.setSelectedEntity(response.data[0]);
enumFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#enumFieldTabs li:eq(0) a').tab('show');
};
$scope.showTabDetail= function(index)
{
if (index!=null)
{
tabService.searchOne(entityService.selectedEntity.tabList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.tab==null || entityService.selectedEntity.tab==undefined)
{
tabService.setSelectedEntity(null); 
tabService.selectedEntity.show=true; 
}
else
tabService.searchOne(entityService.selectedEntity.tab).then(
function successCallback(response) {
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#tabTabs li:eq(0) a').tab('show');
};
$scope.showRestrictionEntityDetail= function(index)
{
if (index!=null)
{
restrictionEntityService.searchOne(entityService.selectedEntity.restrictionEntityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
restrictionEntityService.setSelectedEntity(response.data[0]);
restrictionEntityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.restrictionEntity==null || entityService.selectedEntity.restrictionEntity==undefined)
{
restrictionEntityService.setSelectedEntity(null); 
restrictionEntityService.selectedEntity.show=true; 
}
else
restrictionEntityService.searchOne(entityService.selectedEntity.restrictionEntity).then(
function successCallback(response) {
restrictionEntityService.setSelectedEntity(response.data[0]);
restrictionEntityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#restrictionEntityTabs li:eq(0) a').tab('show');
};
$scope.showEntityGroupDetail= function(index)
{
if (index!=null)
{
entityGroupService.searchOne(entityService.selectedEntity.entityGroupList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
entityGroupService.setSelectedEntity(response.data[0]);
entityGroupService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.entityGroup==null || entityService.selectedEntity.entityGroup==undefined)
{
entityGroupService.setSelectedEntity(null); 
entityGroupService.selectedEntity.show=true; 
}
else
entityGroupService.searchOne(entityService.selectedEntity.entityGroup).then(
function successCallback(response) {
entityGroupService.setSelectedEntity(response.data[0]);
entityGroupService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#entityGroupTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
$scope.fieldListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'fieldId'},
{ name: 'name'},
{ name: 'fieldType'} 
]
,data: $scope.selectedEntity.fieldList
 };
$scope.fieldListGridOptions.onRegisterApi = function(gridApi){
$scope.fieldGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
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
$scope.relationshipListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'relationshipId'},
{ name: 'name'},
{ name: 'relationshipType'} 
]
,data: $scope.selectedEntity.relationshipList
 };
$scope.relationshipListGridOptions.onRegisterApi = function(gridApi){
$scope.relationshipGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
relationshipService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
relationshipService.setSelectedEntity(response.data[0]);
});
$('#relationshipTabs li:eq(0) a').tab('show');
}
else 
relationshipService.setSelectedEntity(null);
relationshipService.selectedEntity.show = row.isSelected;
});
  };
$scope.enumFieldListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'enumFieldId'},
{ name: 'name'} 
]
,data: $scope.selectedEntity.enumFieldList
 };
$scope.enumFieldListGridOptions.onRegisterApi = function(gridApi){
$scope.enumFieldGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
enumFieldService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
enumFieldService.setSelectedEntity(response.data[0]);
});
$('#enumFieldTabs li:eq(0) a').tab('show');
}
else 
enumFieldService.setSelectedEntity(null);
enumFieldService.selectedEntity.show = row.isSelected;
});
  };
$scope.tabListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'tabId'},
{ name: 'name'} 
]
,data: $scope.selectedEntity.tabList
 };
$scope.tabListGridOptions.onRegisterApi = function(gridApi){
$scope.tabGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
tabService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
tabService.setSelectedEntity(response.data[0]);
});
$('#tabTabs li:eq(0) a').tab('show');
}
else 
tabService.setSelectedEntity(null);
tabService.selectedEntity.show = row.isSelected;
});
  };
$scope.restrictionEntityListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'restrictionEntityId'},
{ name: 'canCreate'},
{ name: 'canUpdate'},
{ name: 'canSearch'},
{ name: 'canDelete'} 
]
,data: $scope.selectedEntity.restrictionEntityList
 };
$scope.restrictionEntityListGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionEntityGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionEntityService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
restrictionEntityService.setSelectedEntity(response.data[0]);
});
$('#restrictionEntityTabs li:eq(0) a').tab('show');
}
else 
restrictionEntityService.setSelectedEntity(null);
restrictionEntityService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedField= function() {
entityService.selectedEntity.fieldList.push(entityService.selectedEntity.field);
}
$scope.downloadFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("field.xls",?) FROM ?',[mystyle,$scope.selectedEntity.fieldList]);
};
$scope.saveLinkedRelationship= function() {
entityService.selectedEntity.relationshipList.push(entityService.selectedEntity.relationship);
}
$scope.downloadRelationshipList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("relationship.xls",?) FROM ?',[mystyle,$scope.selectedEntity.relationshipList]);
};
$scope.saveLinkedEnumField= function() {
entityService.selectedEntity.enumFieldList.push(entityService.selectedEntity.enumField);
}
$scope.downloadEnumFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumField.xls",?) FROM ?',[mystyle,$scope.selectedEntity.enumFieldList]);
};
$scope.saveLinkedTab= function() {
entityService.selectedEntity.tabList.push(entityService.selectedEntity.tab);
}
$scope.downloadTabList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("tab.xls",?) FROM ?',[mystyle,$scope.selectedEntity.tabList]);
};
$scope.saveLinkedRestrictionEntity= function() {
entityService.selectedEntity.restrictionEntityList.push(entityService.selectedEntity.restrictionEntity);
}
$scope.downloadRestrictionEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionEntity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.restrictionEntityList]);
};
$scope.downloadEntityGroupList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entityGroup.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityGroupList]);
};
})
.service("annotationService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,annotationAttributeList: []};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
for (i=0; i<entityList.length; i++)
this.entityList.push(entityList[i]);
};
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
} else 
this.emptyList(this.selectedEntity[val]);
} else {
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
} else {
this.selectedEntity[val] = entity[val];
}
}
}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../annotation/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../annotation/"+entity.annotationId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../annotation/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../annotation/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../annotation/"+this.selectedEntity.annotationId;
var promise= $http["delete"](url);
return promise; 
}
 this.initAnnotationAttributeList= function()
{
var promise= $http
.post("../annotationAttribute/search",
{});
return promise;
};
 this.initFieldList= function()
{
var promise= $http
.post("../field/search",
{});
return promise;
};
 this.initRelationshipList= function()
{
var promise= $http
.post("../relationship/search",
{});
return promise;
};
 this.initEnumFieldList= function()
{
var promise= $http
.post("../enumField/search",
{});
return promise;
};
})
.controller("annotationController",function($scope,$http,annotationService,annotationAttributeService,fieldService,relationshipService,enumFieldService,entityService,restrictionFieldService,tabService,restrictionEntityService,entityGroupService,roleService,userService,restrictionEntityGroupService,enumValueService)
{
//field
$scope.searchBean=annotationService.searchBean;
$scope.entityList=annotationService.entityList;
$scope.selectedEntity=annotationService.selectedEntity;
$scope.childrenList=annotationService.childrenList; 
$scope.reset = function()
{
annotationService.resetSearchBean();
$scope.searchBean=annotationService.searchBean;annotationService.setSelectedEntity(null);
annotationService.selectedEntity.show=false;
annotationService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
fieldService.update().then(function successCallback(response) {
fieldService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
alert("error");
return; 
}
);
};
$scope.addNew= function()
{
annotationService.setSelectedEntity(null);
annotationService.setEntityList(null);
annotationService.selectedEntity.show=true;
$('#annotationTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
annotationService.selectedEntity.show=false;
annotationService.searchBean.annotationAttributeList=[];
annotationService.searchBean.annotationAttributeList.push(annotationService.searchBean.annotationAttribute);
delete annotationService.searchBean.annotationAttribute; 
annotationService.search().then(function successCallback(response) {
annotationService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.annotationDetailForm.$valid) return; 
annotationService.selectedEntity.show=false;
annotationService.selectedEntity.field={};
annotationService.selectedEntity.field.fieldId=fieldService.selectedEntity.fieldId;
annotationService.insert().then(function successCallBack(response) { 
fieldService.selectedEntity.annotationList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.annotationDetailForm.$valid) return; 
annotationService.selectedEntity.show=false;

for (i=0; i<fieldService.selectedEntity.annotationList.length; i++)

{

if (fieldService.selectedEntity.annotationList[i].annotationId==annotationService.selectedEntity.annotationId)

fieldService.selectedEntity.annotationList.splice(i,1);

}

fieldService.selectedEntity.annotationList.push(annotationService.selectedEntity);

annotationService.update().then(function successCallback(response){
annotationService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
annotationService.selectedEntity.show=false;
for (i=0; i<fieldService.selectedEntity.annotationList.length; i++)
{
if (fieldService.selectedEntity.annotationList[i].annotationId==annotationService.selectedEntity.annotationId)
fieldService.selectedEntity.annotationList.splice(i,1);
}
annotationService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<fieldService.selectedEntity.annotationList.length; i++)
{
if (fieldService.selectedEntity.annotationList[i].annotationId==annotationService.selectedEntity.annotationId)
fieldService.selectedEntity.annotationList.splice(i,1);
}
$scope.updateParent();
annotationService.del().then(function successCallback(response) { 
annotationService.setSelectedEntity(null);
fieldService.initAnnotationList().then(function(response) {
fieldService.childrenList.annotationList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
 $scope.annotationAttributeGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showAnnotationAttributeDetail= function(index)
{
if (index!=null)
{
annotationAttributeService.searchOne(annotationService.selectedEntity.annotationAttributeList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
annotationAttributeService.setSelectedEntity(response.data[0]);
annotationAttributeService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (annotationService.selectedEntity.annotationAttribute==null || annotationService.selectedEntity.annotationAttribute==undefined)
{
annotationAttributeService.setSelectedEntity(null); 
annotationAttributeService.selectedEntity.show=true; 
}
else
annotationAttributeService.searchOne(annotationService.selectedEntity.annotationAttribute).then(
function successCallback(response) {
annotationAttributeService.setSelectedEntity(response.data[0]);
annotationAttributeService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#annotationAttributeTabs li:eq(0) a').tab('show');
};
$scope.showFieldDetail= function(index)
{
if (index!=null)
{
fieldService.searchOne(annotationService.selectedEntity.fieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (annotationService.selectedEntity.field==null || annotationService.selectedEntity.field==undefined)
{
fieldService.setSelectedEntity(null); 
fieldService.selectedEntity.show=true; 
}
else
fieldService.searchOne(annotationService.selectedEntity.field).then(
function successCallback(response) {
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#fieldTabs li:eq(0) a').tab('show');
};
$scope.showRelationshipDetail= function(index)
{
if (index!=null)
{
relationshipService.searchOne(annotationService.selectedEntity.relationshipList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
relationshipService.setSelectedEntity(response.data[0]);
relationshipService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (annotationService.selectedEntity.relationship==null || annotationService.selectedEntity.relationship==undefined)
{
relationshipService.setSelectedEntity(null); 
relationshipService.selectedEntity.show=true; 
}
else
relationshipService.searchOne(annotationService.selectedEntity.relationship).then(
function successCallback(response) {
relationshipService.setSelectedEntity(response.data[0]);
relationshipService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#relationshipTabs li:eq(0) a').tab('show');
};
$scope.showEnumFieldDetail= function(index)
{
if (index!=null)
{
enumFieldService.searchOne(annotationService.selectedEntity.enumFieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
enumFieldService.setSelectedEntity(response.data[0]);
enumFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (annotationService.selectedEntity.enumField==null || annotationService.selectedEntity.enumField==undefined)
{
enumFieldService.setSelectedEntity(null); 
enumFieldService.selectedEntity.show=true; 
}
else
enumFieldService.searchOne(annotationService.selectedEntity.enumField).then(
function successCallback(response) {
enumFieldService.setSelectedEntity(response.data[0]);
enumFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#enumFieldTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
annotationService.initAnnotationAttributeList().then(function successCallback(response) {
annotationService.childrenList.annotationAttributeList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
annotationService.initFieldList().then(function successCallback(response) {
annotationService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
annotationService.initRelationshipList().then(function successCallback(response) {
annotationService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
annotationService.initEnumFieldList().then(function successCallback(response) {
annotationService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
annotationService.childrenList.annotationTypeList=["PRIMARY_KEY","NOT_NULL","NOT_BLANK","DESCRIPTION_FIELD","BETWEEN_FILTER","EXCEL_EXPORT","FILTER_FIELD","IGNORE_SEARCH","IGNORE_UPDATE","IGNORE_TABLE_LIST","SIZE",];
}; 
$scope.init();
$scope.annotationAttributeListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'annotationAttributeId'},
{ name: 'property'},
{ name: 'value'} 
]
,data: $scope.selectedEntity.annotationAttributeList
 };
$scope.annotationAttributeListGridOptions.onRegisterApi = function(gridApi){
$scope.annotationAttributeGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
annotationAttributeService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
annotationAttributeService.setSelectedEntity(response.data[0]);
});
$('#annotationAttributeTabs li:eq(0) a').tab('show');
}
else 
annotationAttributeService.setSelectedEntity(null);
annotationAttributeService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("annotation.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedAnnotationAttribute= function() {
annotationService.selectedEntity.annotationAttributeList.push(annotationService.selectedEntity.annotationAttribute);
}
$scope.downloadAnnotationAttributeList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("annotationAttribute.xls",?) FROM ?',[mystyle,$scope.selectedEntity.annotationAttributeList]);
};
$scope.downloadFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("field.xls",?) FROM ?',[mystyle,$scope.selectedEntity.fieldList]);
};
$scope.downloadRelationshipList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("relationship.xls",?) FROM ?',[mystyle,$scope.selectedEntity.relationshipList]);
};
$scope.downloadEnumFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumField.xls",?) FROM ?',[mystyle,$scope.selectedEntity.enumFieldList]);
};
})
.service("restrictionFieldService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
for (i=0; i<entityList.length; i++)
this.entityList.push(entityList[i]);
};
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
} else 
this.emptyList(this.selectedEntity[val]);
} else {
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
} else {
this.selectedEntity[val] = entity[val];
}
}
}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../restrictionField/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../restrictionField/"+entity.restrictionFieldId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../restrictionField/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../restrictionField/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../restrictionField/"+this.selectedEntity.restrictionFieldId;
var promise= $http["delete"](url);
return promise; 
}
 this.initFieldList= function()
{
var promise= $http
.post("../field/search",
{});
return promise;
};
 this.initRoleList= function()
{
var promise= $http
.post("../role/search",
{});
return promise;
};
})
.controller("restrictionFieldController",function($scope,$http,restrictionFieldService,fieldService,roleService,entityService,annotationService,tabService,relationshipService,enumFieldService,restrictionEntityService,entityGroupService,enumValueService,restrictionEntityGroupService,annotationAttributeService,userService)
{
//field
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
}
$scope.updateParent = function(toDo)
{
fieldService.update().then(function successCallback(response) {
fieldService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
alert("error");
return; 
}
);
};
$scope.addNew= function()
{
restrictionFieldService.setSelectedEntity(null);
restrictionFieldService.setEntityList(null);
restrictionFieldService.selectedEntity.show=true;
$('#restrictionFieldTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
restrictionFieldService.selectedEntity.show=false;
restrictionFieldService.search().then(function successCallback(response) {
restrictionFieldService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.restrictionFieldDetailForm.$valid) return; 
restrictionFieldService.selectedEntity.show=false;
restrictionFieldService.selectedEntity.field={};
restrictionFieldService.selectedEntity.field.fieldId=fieldService.selectedEntity.fieldId;
restrictionFieldService.insert().then(function successCallBack(response) { 
fieldService.selectedEntity.restrictionFieldList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.restrictionFieldDetailForm.$valid) return; 
restrictionFieldService.selectedEntity.show=false;

for (i=0; i<fieldService.selectedEntity.restrictionFieldList.length; i++)

{

if (fieldService.selectedEntity.restrictionFieldList[i].restrictionFieldId==restrictionFieldService.selectedEntity.restrictionFieldId)

fieldService.selectedEntity.restrictionFieldList.splice(i,1);

}

fieldService.selectedEntity.restrictionFieldList.push(restrictionFieldService.selectedEntity);

restrictionFieldService.update().then(function successCallback(response){
restrictionFieldService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
restrictionFieldService.selectedEntity.show=false;
for (i=0; i<fieldService.selectedEntity.restrictionFieldList.length; i++)
{
if (fieldService.selectedEntity.restrictionFieldList[i].restrictionFieldId==restrictionFieldService.selectedEntity.restrictionFieldId)
fieldService.selectedEntity.restrictionFieldList.splice(i,1);
}
restrictionFieldService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<fieldService.selectedEntity.restrictionFieldList.length; i++)
{
if (fieldService.selectedEntity.restrictionFieldList[i].restrictionFieldId==restrictionFieldService.selectedEntity.restrictionFieldId)
fieldService.selectedEntity.restrictionFieldList.splice(i,1);
}
$scope.updateParent();
restrictionFieldService.del().then(function successCallback(response) { 
restrictionFieldService.setSelectedEntity(null);
fieldService.initRestrictionFieldList().then(function(response) {
fieldService.childrenList.restrictionFieldList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
};
$scope.trueFalseValues=[true,false];
$scope.showFieldDetail= function(index)
{
if (index!=null)
{
fieldService.searchOne(restrictionFieldService.selectedEntity.fieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
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
}
else
fieldService.searchOne(restrictionFieldService.selectedEntity.field).then(
function successCallback(response) {
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#fieldTabs li:eq(0) a').tab('show');
};
$scope.showRoleDetail= function(index)
{
if (index!=null)
{
roleService.searchOne(restrictionFieldService.selectedEntity.roleList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
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
}
else
roleService.searchOne(restrictionFieldService.selectedEntity.role).then(
function successCallback(response) {
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#roleTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
restrictionFieldService.initFieldList().then(function successCallback(response) {
restrictionFieldService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionFieldService.initRoleList().then(function successCallback(response) {
restrictionFieldService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionField.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("field.xls",?) FROM ?',[mystyle,$scope.selectedEntity.fieldList]);
};
$scope.downloadRoleList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,$scope.selectedEntity.roleList]);
};
})
.service("tabService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,fieldList: [],relationshipList: [],enumFieldList: []};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
for (i=0; i<entityList.length; i++)
this.entityList.push(entityList[i]);
};
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
} else 
this.emptyList(this.selectedEntity[val]);
} else {
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
} else {
this.selectedEntity[val] = entity[val];
}
}
}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../tab/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../tab/"+entity.tabId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../tab/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../tab/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../tab/"+this.selectedEntity.tabId;
var promise= $http["delete"](url);
return promise; 
}
 this.initEntityList= function()
{
var promise= $http
.post("../entity/search",
{});
return promise;
};
 this.initFieldList= function()
{
var promise= $http
.post("../field/search",
{});
return promise;
};
 this.initRelationshipList= function()
{
var promise= $http
.post("../relationship/search",
{});
return promise;
};
 this.initEnumFieldList= function()
{
var promise= $http
.post("../enumField/search",
{});
return promise;
};
})
.controller("tabController",function($scope,$http,tabService,entityService,fieldService,relationshipService,enumFieldService,restrictionEntityService,entityGroupService,roleService,userService,restrictionFieldService,restrictionEntityGroupService,annotationService,annotationAttributeService,enumValueService)
{
//field
$scope.searchBean=tabService.searchBean;
$scope.entityList=tabService.entityList;
$scope.selectedEntity=tabService.selectedEntity;
$scope.childrenList=tabService.childrenList; 
$scope.reset = function()
{
tabService.resetSearchBean();
$scope.searchBean=tabService.searchBean;tabService.setSelectedEntity(null);
tabService.selectedEntity.show=false;
tabService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
fieldService.update().then(function successCallback(response) {
fieldService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
alert("error");
return; 
}
);
};
$scope.addNew= function()
{
tabService.setSelectedEntity(null);
tabService.setEntityList(null);
tabService.selectedEntity.show=true;
$('#tabTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
tabService.selectedEntity.show=false;
tabService.searchBean.fieldList=[];
tabService.searchBean.fieldList.push(tabService.searchBean.field);
delete tabService.searchBean.field; 
tabService.searchBean.relationshipList=[];
tabService.searchBean.relationshipList.push(tabService.searchBean.relationship);
delete tabService.searchBean.relationship; 
tabService.searchBean.enumFieldList=[];
tabService.searchBean.enumFieldList.push(tabService.searchBean.enumField);
delete tabService.searchBean.enumField; 
tabService.search().then(function successCallback(response) {
tabService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.tabDetailForm.$valid) return; 
tabService.selectedEntity.show=false;
tabService.selectedEntity.field={};
tabService.selectedEntity.field.fieldId=fieldService.selectedEntity.fieldId;
tabService.insert().then(function successCallBack(response) { 
fieldService.selectedEntity.tab=response.data;
fieldService.initTabList().then(function(response) {
fieldService.childrenList.tabList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.tabDetailForm.$valid) return; 
tabService.selectedEntity.show=false;

fieldService.selectedEntity.tab=tabService.selectedEntity;

tabService.update().then(function successCallback(response){
tabService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
tabService.selectedEntity.show=false;
fieldService.selectedEntity.tab=null;
tabService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
fieldService.selectedEntity.tab=null;
$scope.updateParent();
tabService.del().then(function successCallback(response) { 
tabService.setSelectedEntity(null);
fieldService.initTabList().then(function(response) {
fieldService.childrenList.tabList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
 $scope.fieldGridApi.core.handleWindowResize(); 
 $scope.relationshipGridApi.core.handleWindowResize(); 
 $scope.enumFieldGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(tabService.selectedEntity.entityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (tabService.selectedEntity.entity==null || tabService.selectedEntity.entity==undefined)
{
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
}
else
entityService.searchOne(tabService.selectedEntity.entity).then(
function successCallback(response) {
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#entityTabs li:eq(0) a').tab('show');
};
$scope.showFieldDetail= function(index)
{
if (index!=null)
{
fieldService.searchOne(tabService.selectedEntity.fieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (tabService.selectedEntity.field==null || tabService.selectedEntity.field==undefined)
{
fieldService.setSelectedEntity(null); 
fieldService.selectedEntity.show=true; 
}
else
fieldService.searchOne(tabService.selectedEntity.field).then(
function successCallback(response) {
fieldService.setSelectedEntity(response.data[0]);
fieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#fieldTabs li:eq(0) a').tab('show');
};
$scope.showRelationshipDetail= function(index)
{
if (index!=null)
{
relationshipService.searchOne(tabService.selectedEntity.relationshipList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
relationshipService.setSelectedEntity(response.data[0]);
relationshipService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (tabService.selectedEntity.relationship==null || tabService.selectedEntity.relationship==undefined)
{
relationshipService.setSelectedEntity(null); 
relationshipService.selectedEntity.show=true; 
}
else
relationshipService.searchOne(tabService.selectedEntity.relationship).then(
function successCallback(response) {
relationshipService.setSelectedEntity(response.data[0]);
relationshipService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#relationshipTabs li:eq(0) a').tab('show');
};
$scope.showEnumFieldDetail= function(index)
{
if (index!=null)
{
enumFieldService.searchOne(tabService.selectedEntity.enumFieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
enumFieldService.setSelectedEntity(response.data[0]);
enumFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (tabService.selectedEntity.enumField==null || tabService.selectedEntity.enumField==undefined)
{
enumFieldService.setSelectedEntity(null); 
enumFieldService.selectedEntity.show=true; 
}
else
enumFieldService.searchOne(tabService.selectedEntity.enumField).then(
function successCallback(response) {
enumFieldService.setSelectedEntity(response.data[0]);
enumFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#enumFieldTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
$scope.fieldListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'fieldId'},
{ name: 'name'},
{ name: 'fieldType'} 
]
,data: $scope.selectedEntity.fieldList
 };
$scope.fieldListGridOptions.onRegisterApi = function(gridApi){
$scope.fieldGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
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
$scope.relationshipListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'relationshipId'},
{ name: 'name'},
{ name: 'relationshipType'} 
]
,data: $scope.selectedEntity.relationshipList
 };
$scope.relationshipListGridOptions.onRegisterApi = function(gridApi){
$scope.relationshipGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
relationshipService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
relationshipService.setSelectedEntity(response.data[0]);
});
$('#relationshipTabs li:eq(0) a').tab('show');
}
else 
relationshipService.setSelectedEntity(null);
relationshipService.selectedEntity.show = row.isSelected;
});
  };
$scope.enumFieldListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'enumFieldId'},
{ name: 'name'} 
]
,data: $scope.selectedEntity.enumFieldList
 };
$scope.enumFieldListGridOptions.onRegisterApi = function(gridApi){
$scope.enumFieldGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
enumFieldService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
enumFieldService.setSelectedEntity(response.data[0]);
});
$('#enumFieldTabs li:eq(0) a').tab('show');
}
else 
enumFieldService.setSelectedEntity(null);
enumFieldService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("tab.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityList]);
};
$scope.saveLinkedField= function() {
tabService.selectedEntity.fieldList.push(tabService.selectedEntity.field);
}
$scope.downloadFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("field.xls",?) FROM ?',[mystyle,$scope.selectedEntity.fieldList]);
};
$scope.saveLinkedRelationship= function() {
tabService.selectedEntity.relationshipList.push(tabService.selectedEntity.relationship);
}
$scope.downloadRelationshipList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("relationship.xls",?) FROM ?',[mystyle,$scope.selectedEntity.relationshipList]);
};
$scope.saveLinkedEnumField= function() {
tabService.selectedEntity.enumFieldList.push(tabService.selectedEntity.enumField);
}
$scope.downloadEnumFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumField.xls",?) FROM ?',[mystyle,$scope.selectedEntity.enumFieldList]);
};
})
.service("relationshipService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,annotationList: []};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
for (i=0; i<entityList.length; i++)
this.entityList.push(entityList[i]);
};
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
} else 
this.emptyList(this.selectedEntity[val]);
} else {
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
} else {
this.selectedEntity[val] = entity[val];
}
}
}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../relationship/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../relationship/"+entity.relationshipId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../relationship/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../relationship/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../relationship/"+this.selectedEntity.relationshipId;
var promise= $http["delete"](url);
return promise; 
}
 this.initEntityList= function()
{
var promise= $http
.post("../entity/search",
{});
return promise;
};
 this.initEntityTargetList= function()
{
var promise= $http
.post("../entity/search",
{});
return promise;
};
 this.initAnnotationList= function()
{
var promise= $http
.post("../annotation/search",
{});
return promise;
};
 this.initTabList= function()
{
var promise= $http
.post("../tab/search",
{});
return promise;
};
})
.controller("relationshipController",function($scope,$http,relationshipService,entityService,annotationService,tabService,fieldService,enumFieldService,restrictionEntityService,entityGroupService,restrictionFieldService,roleService,userService,restrictionEntityGroupService,enumValueService,annotationAttributeService)
{
//entity
$scope.searchBean=relationshipService.searchBean;
$scope.entityList=relationshipService.entityList;
$scope.selectedEntity=relationshipService.selectedEntity;
$scope.childrenList=relationshipService.childrenList; 
$scope.reset = function()
{
relationshipService.resetSearchBean();
$scope.searchBean=relationshipService.searchBean;relationshipService.setSelectedEntity(null);
relationshipService.selectedEntity.show=false;
relationshipService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
entityService.update().then(function successCallback(response) {
entityService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
alert("error");
return; 
}
);
};
$scope.addNew= function()
{
relationshipService.setSelectedEntity(null);
relationshipService.setEntityList(null);
relationshipService.selectedEntity.show=true;
$('#relationshipTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
relationshipService.selectedEntity.show=false;
relationshipService.searchBean.annotationList=[];
relationshipService.searchBean.annotationList.push(relationshipService.searchBean.annotation);
delete relationshipService.searchBean.annotation; 
relationshipService.search().then(function successCallback(response) {
relationshipService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.relationshipDetailForm.$valid) return; 
relationshipService.selectedEntity.show=false;
relationshipService.selectedEntity.entity={};
relationshipService.selectedEntity.entity.entityId=entityService.selectedEntity.entityId;
relationshipService.insert().then(function successCallBack(response) { 
entityService.selectedEntity.relationshipList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.relationshipDetailForm.$valid) return; 
relationshipService.selectedEntity.show=false;

for (i=0; i<entityService.selectedEntity.relationshipList.length; i++)

{

if (entityService.selectedEntity.relationshipList[i].relationshipId==relationshipService.selectedEntity.relationshipId)

entityService.selectedEntity.relationshipList.splice(i,1);

}

entityService.selectedEntity.relationshipList.push(relationshipService.selectedEntity);

relationshipService.update().then(function successCallback(response){
relationshipService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
relationshipService.selectedEntity.show=false;
for (i=0; i<entityService.selectedEntity.relationshipList.length; i++)
{
if (entityService.selectedEntity.relationshipList[i].relationshipId==relationshipService.selectedEntity.relationshipId)
entityService.selectedEntity.relationshipList.splice(i,1);
}
relationshipService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<entityService.selectedEntity.relationshipList.length; i++)
{
if (entityService.selectedEntity.relationshipList[i].relationshipId==relationshipService.selectedEntity.relationshipId)
entityService.selectedEntity.relationshipList.splice(i,1);
}
$scope.updateParent();
relationshipService.del().then(function successCallback(response) { 
relationshipService.setSelectedEntity(null);
entityService.initRelationshipList().then(function(response) {
entityService.childrenList.relationshipList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
 $scope.annotationGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(relationshipService.selectedEntity.entityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
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
}
else
entityService.searchOne(relationshipService.selectedEntity.entity).then(
function successCallback(response) {
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#entityTabs li:eq(0) a').tab('show');
};
$scope.showEntityTargetDetail= function(index)
{
if (index!=null)
{
entityTargetService.searchOne(relationshipService.selectedEntity.entityTargetList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
entityTargetService.setSelectedEntity(response.data[0]);
entityTargetService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (relationshipService.selectedEntity.entityTarget==null || relationshipService.selectedEntity.entityTarget==undefined)
{
entityTargetService.setSelectedEntity(null); 
entityTargetService.selectedEntity.show=true; 
}
else
entityTargetService.searchOne(relationshipService.selectedEntity.entityTarget).then(
function successCallback(response) {
entityTargetService.setSelectedEntity(response.data[0]);
entityTargetService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#entityTargetTabs li:eq(0) a').tab('show');
};
$scope.showAnnotationDetail= function(index)
{
if (index!=null)
{
annotationService.searchOne(relationshipService.selectedEntity.annotationList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
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
}
else
annotationService.searchOne(relationshipService.selectedEntity.annotation).then(
function successCallback(response) {
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#annotationTabs li:eq(0) a').tab('show');
};
$scope.showTabDetail= function(index)
{
if (index!=null)
{
tabService.searchOne(relationshipService.selectedEntity.tabList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
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
}
else
tabService.searchOne(relationshipService.selectedEntity.tab).then(
function successCallback(response) {
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#tabTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
relationshipService.initEntityList().then(function successCallback(response) {
relationshipService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
relationshipService.initEntityTargetList().then(function successCallback(response) {
relationshipService.childrenList.entityTargetList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
relationshipService.initAnnotationList().then(function successCallback(response) {
relationshipService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
relationshipService.initTabList().then(function successCallback(response) {
relationshipService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
relationshipService.childrenList.relationshipTypeList=["ONE_TO_ONE","ONE_TO_MANY","MANY_TO_ONE","MANY_TO_MANY","MANY_TO_MANY_BACK",];
}; 
$scope.init();
$scope.annotationListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'annotationId'},
{ name: 'annotationType'} 
]
,data: $scope.selectedEntity.annotationList
 };
$scope.annotationListGridOptions.onRegisterApi = function(gridApi){
$scope.annotationGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
annotationService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
annotationService.setSelectedEntity(response.data[0]);
});
$('#annotationTabs li:eq(0) a').tab('show');
}
else 
annotationService.setSelectedEntity(null);
annotationService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("relationship.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityList]);
};
$scope.downloadEntityTargetList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entityTarget.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityTargetList]);
};
$scope.saveLinkedAnnotation= function() {
relationshipService.selectedEntity.annotationList.push(relationshipService.selectedEntity.annotation);
}
$scope.downloadAnnotationList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("annotation.xls",?) FROM ?',[mystyle,$scope.selectedEntity.annotationList]);
};
$scope.downloadTabList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("tab.xls",?) FROM ?',[mystyle,$scope.selectedEntity.tabList]);
};
})
.service("enumFieldService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,enumValueList: [],annotationList: []};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
for (i=0; i<entityList.length; i++)
this.entityList.push(entityList[i]);
};
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
} else 
this.emptyList(this.selectedEntity[val]);
} else {
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
} else {
this.selectedEntity[val] = entity[val];
}
}
}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../enumField/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../enumField/"+entity.enumFieldId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../enumField/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../enumField/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../enumField/"+this.selectedEntity.enumFieldId;
var promise= $http["delete"](url);
return promise; 
}
 this.initEnumValueList= function()
{
var promise= $http
.post("../enumValue/search",
{});
return promise;
};
 this.initEntityList= function()
{
var promise= $http
.post("../entity/search",
{});
return promise;
};
 this.initAnnotationList= function()
{
var promise= $http
.post("../annotation/search",
{});
return promise;
};
 this.initTabList= function()
{
var promise= $http
.post("../tab/search",
{});
return promise;
};
})
.controller("enumFieldController",function($scope,$http,enumFieldService,enumValueService,entityService,annotationService,tabService,fieldService,relationshipService,restrictionEntityService,entityGroupService,restrictionFieldService,roleService,userService,restrictionEntityGroupService,annotationAttributeService)
{
//entity
$scope.searchBean=enumFieldService.searchBean;
$scope.entityList=enumFieldService.entityList;
$scope.selectedEntity=enumFieldService.selectedEntity;
$scope.childrenList=enumFieldService.childrenList; 
$scope.reset = function()
{
enumFieldService.resetSearchBean();
$scope.searchBean=enumFieldService.searchBean;enumFieldService.setSelectedEntity(null);
enumFieldService.selectedEntity.show=false;
enumFieldService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
entityService.update().then(function successCallback(response) {
entityService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
alert("error");
return; 
}
);
};
$scope.addNew= function()
{
enumFieldService.setSelectedEntity(null);
enumFieldService.setEntityList(null);
enumFieldService.selectedEntity.show=true;
$('#enumFieldTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
enumFieldService.selectedEntity.show=false;
enumFieldService.searchBean.enumValueList=[];
enumFieldService.searchBean.enumValueList.push(enumFieldService.searchBean.enumValue);
delete enumFieldService.searchBean.enumValue; 
enumFieldService.searchBean.annotationList=[];
enumFieldService.searchBean.annotationList.push(enumFieldService.searchBean.annotation);
delete enumFieldService.searchBean.annotation; 
enumFieldService.search().then(function successCallback(response) {
enumFieldService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.enumFieldDetailForm.$valid) return; 
enumFieldService.selectedEntity.show=false;
enumFieldService.selectedEntity.entity={};
enumFieldService.selectedEntity.entity.entityId=entityService.selectedEntity.entityId;
enumFieldService.insert().then(function successCallBack(response) { 
entityService.selectedEntity.enumFieldList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.enumFieldDetailForm.$valid) return; 
enumFieldService.selectedEntity.show=false;

for (i=0; i<entityService.selectedEntity.enumFieldList.length; i++)

{

if (entityService.selectedEntity.enumFieldList[i].enumFieldId==enumFieldService.selectedEntity.enumFieldId)

entityService.selectedEntity.enumFieldList.splice(i,1);

}

entityService.selectedEntity.enumFieldList.push(enumFieldService.selectedEntity);

enumFieldService.update().then(function successCallback(response){
enumFieldService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
enumFieldService.selectedEntity.show=false;
for (i=0; i<entityService.selectedEntity.enumFieldList.length; i++)
{
if (entityService.selectedEntity.enumFieldList[i].enumFieldId==enumFieldService.selectedEntity.enumFieldId)
entityService.selectedEntity.enumFieldList.splice(i,1);
}
enumFieldService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<entityService.selectedEntity.enumFieldList.length; i++)
{
if (entityService.selectedEntity.enumFieldList[i].enumFieldId==enumFieldService.selectedEntity.enumFieldId)
entityService.selectedEntity.enumFieldList.splice(i,1);
}
$scope.updateParent();
enumFieldService.del().then(function successCallback(response) { 
enumFieldService.setSelectedEntity(null);
entityService.initEnumFieldList().then(function(response) {
entityService.childrenList.enumFieldList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
 $scope.enumValueGridApi.core.handleWindowResize(); 
 $scope.annotationGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showEnumValueDetail= function(index)
{
if (index!=null)
{
enumValueService.searchOne(enumFieldService.selectedEntity.enumValueList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
enumValueService.setSelectedEntity(response.data[0]);
enumValueService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (enumFieldService.selectedEntity.enumValue==null || enumFieldService.selectedEntity.enumValue==undefined)
{
enumValueService.setSelectedEntity(null); 
enumValueService.selectedEntity.show=true; 
}
else
enumValueService.searchOne(enumFieldService.selectedEntity.enumValue).then(
function successCallback(response) {
enumValueService.setSelectedEntity(response.data[0]);
enumValueService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#enumValueTabs li:eq(0) a').tab('show');
};
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(enumFieldService.selectedEntity.entityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (enumFieldService.selectedEntity.entity==null || enumFieldService.selectedEntity.entity==undefined)
{
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
}
else
entityService.searchOne(enumFieldService.selectedEntity.entity).then(
function successCallback(response) {
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#entityTabs li:eq(0) a').tab('show');
};
$scope.showAnnotationDetail= function(index)
{
if (index!=null)
{
annotationService.searchOne(enumFieldService.selectedEntity.annotationList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (enumFieldService.selectedEntity.annotation==null || enumFieldService.selectedEntity.annotation==undefined)
{
annotationService.setSelectedEntity(null); 
annotationService.selectedEntity.show=true; 
}
else
annotationService.searchOne(enumFieldService.selectedEntity.annotation).then(
function successCallback(response) {
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#annotationTabs li:eq(0) a').tab('show');
};
$scope.showTabDetail= function(index)
{
if (index!=null)
{
tabService.searchOne(enumFieldService.selectedEntity.tabList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (enumFieldService.selectedEntity.tab==null || enumFieldService.selectedEntity.tab==undefined)
{
tabService.setSelectedEntity(null); 
tabService.selectedEntity.show=true; 
}
else
tabService.searchOne(enumFieldService.selectedEntity.tab).then(
function successCallback(response) {
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#tabTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
enumFieldService.initEnumValueList().then(function successCallback(response) {
enumFieldService.childrenList.enumValueList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
enumFieldService.initEntityList().then(function successCallback(response) {
enumFieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
enumFieldService.initAnnotationList().then(function successCallback(response) {
enumFieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
enumFieldService.initTabList().then(function successCallback(response) {
enumFieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
$scope.enumValueListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'enumValueId'},
{ name: 'value'},
{ name: 'name'} 
]
,data: $scope.selectedEntity.enumValueList
 };
$scope.enumValueListGridOptions.onRegisterApi = function(gridApi){
$scope.enumValueGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
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
$scope.annotationListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'annotationId'},
{ name: 'annotationType'} 
]
,data: $scope.selectedEntity.annotationList
 };
$scope.annotationListGridOptions.onRegisterApi = function(gridApi){
$scope.annotationGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
annotationService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
annotationService.setSelectedEntity(response.data[0]);
});
$('#annotationTabs li:eq(0) a').tab('show');
}
else 
annotationService.setSelectedEntity(null);
annotationService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumField.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedEnumValue= function() {
enumFieldService.selectedEntity.enumValueList.push(enumFieldService.selectedEntity.enumValue);
}
$scope.downloadEnumValueList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumValue.xls",?) FROM ?',[mystyle,$scope.selectedEntity.enumValueList]);
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityList]);
};
$scope.saveLinkedAnnotation= function() {
enumFieldService.selectedEntity.annotationList.push(enumFieldService.selectedEntity.annotation);
}
$scope.downloadAnnotationList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("annotation.xls",?) FROM ?',[mystyle,$scope.selectedEntity.annotationList]);
};
$scope.downloadTabList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("tab.xls",?) FROM ?',[mystyle,$scope.selectedEntity.tabList]);
};
})
.service("restrictionEntityService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
for (i=0; i<entityList.length; i++)
this.entityList.push(entityList[i]);
};
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
} else 
this.emptyList(this.selectedEntity[val]);
} else {
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
} else {
this.selectedEntity[val] = entity[val];
}
}
}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../restrictionEntity/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../restrictionEntity/"+entity.restrictionEntityId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../restrictionEntity/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../restrictionEntity/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../restrictionEntity/"+this.selectedEntity.restrictionEntityId;
var promise= $http["delete"](url);
return promise; 
}
 this.initRoleList= function()
{
var promise= $http
.post("../role/search",
{});
return promise;
};
 this.initEntityList= function()
{
var promise= $http
.post("../entity/search",
{});
return promise;
};
})
.controller("restrictionEntityController",function($scope,$http,restrictionEntityService,roleService,entityService,userService,restrictionFieldService,restrictionEntityGroupService,fieldService,annotationService,tabService,annotationAttributeService,relationshipService,enumFieldService,enumValueService,entityGroupService)
{
//entity
$scope.searchBean=restrictionEntityService.searchBean;
$scope.entityList=restrictionEntityService.entityList;
$scope.selectedEntity=restrictionEntityService.selectedEntity;
$scope.childrenList=restrictionEntityService.childrenList; 
$scope.reset = function()
{
restrictionEntityService.resetSearchBean();
$scope.searchBean=restrictionEntityService.searchBean;restrictionEntityService.setSelectedEntity(null);
restrictionEntityService.selectedEntity.show=false;
restrictionEntityService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
entityService.update().then(function successCallback(response) {
entityService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
alert("error");
return; 
}
);
};
$scope.addNew= function()
{
restrictionEntityService.setSelectedEntity(null);
restrictionEntityService.setEntityList(null);
restrictionEntityService.selectedEntity.show=true;
$('#restrictionEntityTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
restrictionEntityService.selectedEntity.show=false;
restrictionEntityService.search().then(function successCallback(response) {
restrictionEntityService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.restrictionEntityDetailForm.$valid) return; 
restrictionEntityService.selectedEntity.show=false;
restrictionEntityService.selectedEntity.entity={};
restrictionEntityService.selectedEntity.entity.entityId=entityService.selectedEntity.entityId;
restrictionEntityService.insert().then(function successCallBack(response) { 
entityService.selectedEntity.restrictionEntityList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.restrictionEntityDetailForm.$valid) return; 
restrictionEntityService.selectedEntity.show=false;

for (i=0; i<entityService.selectedEntity.restrictionEntityList.length; i++)

{

if (entityService.selectedEntity.restrictionEntityList[i].restrictionEntityId==restrictionEntityService.selectedEntity.restrictionEntityId)

entityService.selectedEntity.restrictionEntityList.splice(i,1);

}

entityService.selectedEntity.restrictionEntityList.push(restrictionEntityService.selectedEntity);

restrictionEntityService.update().then(function successCallback(response){
restrictionEntityService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
restrictionEntityService.selectedEntity.show=false;
for (i=0; i<entityService.selectedEntity.restrictionEntityList.length; i++)
{
if (entityService.selectedEntity.restrictionEntityList[i].restrictionEntityId==restrictionEntityService.selectedEntity.restrictionEntityId)
entityService.selectedEntity.restrictionEntityList.splice(i,1);
}
restrictionEntityService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<entityService.selectedEntity.restrictionEntityList.length; i++)
{
if (entityService.selectedEntity.restrictionEntityList[i].restrictionEntityId==restrictionEntityService.selectedEntity.restrictionEntityId)
entityService.selectedEntity.restrictionEntityList.splice(i,1);
}
$scope.updateParent();
restrictionEntityService.del().then(function successCallback(response) { 
restrictionEntityService.setSelectedEntity(null);
entityService.initRestrictionEntityList().then(function(response) {
entityService.childrenList.restrictionEntityList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
};
$scope.trueFalseValues=[true,false];
$scope.showRoleDetail= function(index)
{
if (index!=null)
{
roleService.searchOne(restrictionEntityService.selectedEntity.roleList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (restrictionEntityService.selectedEntity.role==null || restrictionEntityService.selectedEntity.role==undefined)
{
roleService.setSelectedEntity(null); 
roleService.selectedEntity.show=true; 
}
else
roleService.searchOne(restrictionEntityService.selectedEntity.role).then(
function successCallback(response) {
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#roleTabs li:eq(0) a').tab('show');
};
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(restrictionEntityService.selectedEntity.entityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (restrictionEntityService.selectedEntity.entity==null || restrictionEntityService.selectedEntity.entity==undefined)
{
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
}
else
entityService.searchOne(restrictionEntityService.selectedEntity.entity).then(
function successCallback(response) {
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#entityTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
restrictionEntityService.initRoleList().then(function successCallback(response) {
restrictionEntityService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionEntityService.initEntityList().then(function successCallback(response) {
restrictionEntityService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionEntity.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadRoleList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,$scope.selectedEntity.roleList]);
};
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityList]);
};
})
.service("entityGroupService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,entityList: [],restrictionEntityGroupList: []};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
for (i=0; i<entityList.length; i++)
this.entityList.push(entityList[i]);
};
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
} else 
this.emptyList(this.selectedEntity[val]);
} else {
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
} else {
this.selectedEntity[val] = entity[val];
}
}
}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../entityGroup/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../entityGroup/"+entity.entityGroupId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../entityGroup/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../entityGroup/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../entityGroup/"+this.selectedEntity.entityGroupId;
var promise= $http["delete"](url);
return promise; 
}
 this.initEntityList= function()
{
var promise= $http
.post("../entity/search",
{});
return promise;
};
 this.initRestrictionEntityGroupList= function()
{
var promise= $http
.post("../restrictionEntityGroup/search",
{});
return promise;
};
})
.controller("entityGroupController",function($scope,$http,entityGroupService,entityService,restrictionEntityGroupService,fieldService,relationshipService,enumFieldService,tabService,restrictionEntityService,annotationService,restrictionFieldService,annotationAttributeService,roleService,userService,enumValueService)
{
//entity
$scope.searchBean=entityGroupService.searchBean;
$scope.entityList=entityGroupService.entityList;
$scope.selectedEntity=entityGroupService.selectedEntity;
$scope.childrenList=entityGroupService.childrenList; 
$scope.reset = function()
{
entityGroupService.resetSearchBean();
$scope.searchBean=entityGroupService.searchBean;entityGroupService.setSelectedEntity(null);
entityGroupService.selectedEntity.show=false;
entityGroupService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
entityService.update().then(function successCallback(response) {
entityService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
alert("error");
return; 
}
);
};
$scope.addNew= function()
{
entityGroupService.setSelectedEntity(null);
entityGroupService.setEntityList(null);
entityGroupService.selectedEntity.show=true;
$('#entityGroupTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
entityGroupService.selectedEntity.show=false;
entityGroupService.searchBean.entityList=[];
entityGroupService.searchBean.entityList.push(entityGroupService.searchBean.entity);
delete entityGroupService.searchBean.entity; 
entityGroupService.searchBean.restrictionEntityGroupList=[];
entityGroupService.searchBean.restrictionEntityGroupList.push(entityGroupService.searchBean.restrictionEntityGroup);
delete entityGroupService.searchBean.restrictionEntityGroup; 
entityGroupService.search().then(function successCallback(response) {
entityGroupService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.entityGroupDetailForm.$valid) return; 
entityGroupService.selectedEntity.show=false;
entityGroupService.selectedEntity.entity={};
entityGroupService.selectedEntity.entity.entityId=entityService.selectedEntity.entityId;
entityGroupService.insert().then(function successCallBack(response) { 
entityService.selectedEntity.entityGroup=response.data;
entityService.initEntityGroupList().then(function(response) {
entityService.childrenList.entityGroupList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.entityGroupDetailForm.$valid) return; 
entityGroupService.selectedEntity.show=false;

entityService.selectedEntity.entityGroup=entityGroupService.selectedEntity;

entityGroupService.update().then(function successCallback(response){
entityGroupService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
entityGroupService.selectedEntity.show=false;
entityService.selectedEntity.entityGroup=null;
entityGroupService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
entityService.selectedEntity.entityGroup=null;
$scope.updateParent();
entityGroupService.del().then(function successCallback(response) { 
entityGroupService.setSelectedEntity(null);
entityService.initEntityGroupList().then(function(response) {
entityService.childrenList.entityGroupList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
 $scope.entityGridApi.core.handleWindowResize(); 
 $scope.restrictionEntityGroupGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(entityGroupService.selectedEntity.entityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (entityGroupService.selectedEntity.entity==null || entityGroupService.selectedEntity.entity==undefined)
{
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
}
else
entityService.searchOne(entityGroupService.selectedEntity.entity).then(
function successCallback(response) {
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#entityTabs li:eq(0) a').tab('show');
};
$scope.showRestrictionEntityGroupDetail= function(index)
{
if (index!=null)
{
restrictionEntityGroupService.searchOne(entityGroupService.selectedEntity.restrictionEntityGroupList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
restrictionEntityGroupService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (entityGroupService.selectedEntity.restrictionEntityGroup==null || entityGroupService.selectedEntity.restrictionEntityGroup==undefined)
{
restrictionEntityGroupService.setSelectedEntity(null); 
restrictionEntityGroupService.selectedEntity.show=true; 
}
else
restrictionEntityGroupService.searchOne(entityGroupService.selectedEntity.restrictionEntityGroup).then(
function successCallback(response) {
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
restrictionEntityGroupService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
entityGroupService.initEntityList().then(function successCallback(response) {
entityGroupService.childrenList.entityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
entityGroupService.initRestrictionEntityGroupList().then(function successCallback(response) {
entityGroupService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
$scope.entityListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'entityId'},
{ name: 'name'} 
]
,data: $scope.selectedEntity.entityList
 };
$scope.entityListGridOptions.onRegisterApi = function(gridApi){
$scope.entityGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
entityService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
entityService.setSelectedEntity(response.data[0]);
});
$('#entityTabs li:eq(0) a').tab('show');
}
else 
entityService.setSelectedEntity(null);
entityService.selectedEntity.show = row.isSelected;
});
  };
$scope.restrictionEntityGroupListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'restrictionEntityGroupId'},
{ name: 'canCreate'},
{ name: 'canUpdate'},
{ name: 'canSearch'},
{ name: 'canDelete'} 
]
,data: $scope.selectedEntity.restrictionEntityGroupList
 };
$scope.restrictionEntityGroupListGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionEntityGroupGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionEntityGroupService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
});
$('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
}
else 
restrictionEntityGroupService.setSelectedEntity(null);
restrictionEntityGroupService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entityGroup.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedEntity= function() {
entityGroupService.selectedEntity.entityList.push(entityGroupService.selectedEntity.entity);
}
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityList]);
};
$scope.saveLinkedRestrictionEntityGroup= function() {
entityGroupService.selectedEntity.restrictionEntityGroupList.push(entityGroupService.selectedEntity.restrictionEntityGroup);
}
$scope.downloadRestrictionEntityGroupList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionEntityGroup.xls",?) FROM ?',[mystyle,$scope.selectedEntity.restrictionEntityGroupList]);
};
})
.service("enumValueService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
for (i=0; i<entityList.length; i++)
this.entityList.push(entityList[i]);
};
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
} else 
this.emptyList(this.selectedEntity[val]);
} else {
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
} else {
this.selectedEntity[val] = entity[val];
}
}
}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../enumValue/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../enumValue/"+entity.enumValueId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../enumValue/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../enumValue/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../enumValue/"+this.selectedEntity.enumValueId;
var promise= $http["delete"](url);
return promise; 
}
 this.initEnumFieldList= function()
{
var promise= $http
.post("../enumField/search",
{});
return promise;
};
})
.controller("enumValueController",function($scope,$http,enumValueService,enumFieldService,entityService,annotationService,tabService,fieldService,relationshipService,restrictionEntityService,entityGroupService,restrictionFieldService,roleService,userService,restrictionEntityGroupService,annotationAttributeService)
{
//enumField
$scope.searchBean=enumValueService.searchBean;
$scope.entityList=enumValueService.entityList;
$scope.selectedEntity=enumValueService.selectedEntity;
$scope.childrenList=enumValueService.childrenList; 
$scope.reset = function()
{
enumValueService.resetSearchBean();
$scope.searchBean=enumValueService.searchBean;enumValueService.setSelectedEntity(null);
enumValueService.selectedEntity.show=false;
enumValueService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
enumFieldService.update().then(function successCallback(response) {
enumFieldService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
alert("error");
return; 
}
);
};
$scope.addNew= function()
{
enumValueService.setSelectedEntity(null);
enumValueService.setEntityList(null);
enumValueService.selectedEntity.show=true;
$('#enumValueTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
enumValueService.selectedEntity.show=false;
enumValueService.search().then(function successCallback(response) {
enumValueService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.enumValueDetailForm.$valid) return; 
enumValueService.selectedEntity.show=false;
enumValueService.selectedEntity.enumField={};
enumValueService.selectedEntity.enumField.enumFieldId=enumFieldService.selectedEntity.enumFieldId;
enumValueService.insert().then(function successCallBack(response) { 
enumFieldService.selectedEntity.enumValueList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.enumValueDetailForm.$valid) return; 
enumValueService.selectedEntity.show=false;

for (i=0; i<enumFieldService.selectedEntity.enumValueList.length; i++)

{

if (enumFieldService.selectedEntity.enumValueList[i].enumValueId==enumValueService.selectedEntity.enumValueId)

enumFieldService.selectedEntity.enumValueList.splice(i,1);

}

enumFieldService.selectedEntity.enumValueList.push(enumValueService.selectedEntity);

enumValueService.update().then(function successCallback(response){
enumValueService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
enumValueService.selectedEntity.show=false;
for (i=0; i<enumFieldService.selectedEntity.enumValueList.length; i++)
{
if (enumFieldService.selectedEntity.enumValueList[i].enumValueId==enumValueService.selectedEntity.enumValueId)
enumFieldService.selectedEntity.enumValueList.splice(i,1);
}
enumValueService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<enumFieldService.selectedEntity.enumValueList.length; i++)
{
if (enumFieldService.selectedEntity.enumValueList[i].enumValueId==enumValueService.selectedEntity.enumValueId)
enumFieldService.selectedEntity.enumValueList.splice(i,1);
}
$scope.updateParent();
enumValueService.del().then(function successCallback(response) { 
enumValueService.setSelectedEntity(null);
enumFieldService.initEnumValueList().then(function(response) {
enumFieldService.childrenList.enumValueList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
};
$scope.trueFalseValues=[true,false];
$scope.showEnumFieldDetail= function(index)
{
if (index!=null)
{
enumFieldService.searchOne(enumValueService.selectedEntity.enumFieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
enumFieldService.setSelectedEntity(response.data[0]);
enumFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (enumValueService.selectedEntity.enumField==null || enumValueService.selectedEntity.enumField==undefined)
{
enumFieldService.setSelectedEntity(null); 
enumFieldService.selectedEntity.show=true; 
}
else
enumFieldService.searchOne(enumValueService.selectedEntity.enumField).then(
function successCallback(response) {
enumFieldService.setSelectedEntity(response.data[0]);
enumFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#enumFieldTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
enumValueService.initEnumFieldList().then(function successCallback(response) {
enumValueService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumValue.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadEnumFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("enumField.xls",?) FROM ?',[mystyle,$scope.selectedEntity.enumFieldList]);
};
})
.service("roleService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,userList: [],restrictionEntityList: [],restrictionFieldList: [],restrictionEntityGroupList: []};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
for (i=0; i<entityList.length; i++)
this.entityList.push(entityList[i]);
};
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
} else 
this.emptyList(this.selectedEntity[val]);
} else {
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
} else {
this.selectedEntity[val] = entity[val];
}
}
}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../role/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../role/"+entity.roleId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../role/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../role/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../role/"+this.selectedEntity.roleId;
var promise= $http["delete"](url);
return promise; 
}
 this.initUserList= function()
{
var promise= $http
.post("../user/search",
{});
return promise;
};
 this.initRestrictionEntityList= function()
{
var promise= $http
.post("../restrictionEntity/search",
{});
return promise;
};
 this.initRestrictionFieldList= function()
{
var promise= $http
.post("../restrictionField/search",
{});
return promise;
};
 this.initRestrictionEntityGroupList= function()
{
var promise= $http
.post("../restrictionEntityGroup/search",
{});
return promise;
};
})
.controller("roleController",function($scope,$http,roleService,userService,restrictionEntityService,restrictionFieldService,restrictionEntityGroupService,entityService,fieldService,relationshipService,enumFieldService,tabService,entityGroupService,annotationService,annotationAttributeService,enumValueService)
{
//restrictionEntity
$scope.searchBean=roleService.searchBean;
$scope.entityList=roleService.entityList;
$scope.selectedEntity=roleService.selectedEntity;
$scope.childrenList=roleService.childrenList; 
$scope.reset = function()
{
roleService.resetSearchBean();
$scope.searchBean=roleService.searchBean;roleService.setSelectedEntity(null);
roleService.selectedEntity.show=false;
roleService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
restrictionEntityService.update().then(function successCallback(response) {
restrictionEntityService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
alert("error");
return; 
}
);
};
$scope.addNew= function()
{
roleService.setSelectedEntity(null);
roleService.setEntityList(null);
roleService.selectedEntity.show=true;
$('#roleTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
roleService.selectedEntity.show=false;
roleService.searchBean.userList=[];
roleService.searchBean.userList.push(roleService.searchBean.user);
delete roleService.searchBean.user; 
roleService.searchBean.restrictionEntityList=[];
roleService.searchBean.restrictionEntityList.push(roleService.searchBean.restrictionEntity);
delete roleService.searchBean.restrictionEntity; 
roleService.searchBean.restrictionFieldList=[];
roleService.searchBean.restrictionFieldList.push(roleService.searchBean.restrictionField);
delete roleService.searchBean.restrictionField; 
roleService.searchBean.restrictionEntityGroupList=[];
roleService.searchBean.restrictionEntityGroupList.push(roleService.searchBean.restrictionEntityGroup);
delete roleService.searchBean.restrictionEntityGroup; 
roleService.search().then(function successCallback(response) {
roleService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.roleDetailForm.$valid) return; 
roleService.selectedEntity.show=false;
roleService.selectedEntity.restrictionEntity={};
roleService.selectedEntity.restrictionEntity.restrictionEntityId=restrictionEntityService.selectedEntity.restrictionEntityId;
roleService.insert().then(function successCallBack(response) { 
restrictionEntityService.selectedEntity.role=response.data;
restrictionEntityService.initRoleList().then(function(response) {
restrictionEntityService.childrenList.roleList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.roleDetailForm.$valid) return; 
roleService.selectedEntity.show=false;

restrictionEntityService.selectedEntity.role=roleService.selectedEntity;

roleService.update().then(function successCallback(response){
roleService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
roleService.selectedEntity.show=false;
restrictionEntityService.selectedEntity.role=null;
roleService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
restrictionEntityService.selectedEntity.role=null;
$scope.updateParent();
roleService.del().then(function successCallback(response) { 
roleService.setSelectedEntity(null);
restrictionEntityService.initRoleList().then(function(response) {
restrictionEntityService.childrenList.roleList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
 $scope.userGridApi.core.handleWindowResize(); 
 $scope.restrictionEntityGridApi.core.handleWindowResize(); 
 $scope.restrictionFieldGridApi.core.handleWindowResize(); 
 $scope.restrictionEntityGroupGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showUserDetail= function(index)
{
if (index!=null)
{
userService.searchOne(roleService.selectedEntity.userList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
userService.setSelectedEntity(response.data[0]);
userService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
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
}
else
userService.searchOne(roleService.selectedEntity.user).then(
function successCallback(response) {
userService.setSelectedEntity(response.data[0]);
userService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#userTabs li:eq(0) a').tab('show');
};
$scope.showRestrictionEntityDetail= function(index)
{
if (index!=null)
{
restrictionEntityService.searchOne(roleService.selectedEntity.restrictionEntityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
restrictionEntityService.setSelectedEntity(response.data[0]);
restrictionEntityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
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
}
else
restrictionEntityService.searchOne(roleService.selectedEntity.restrictionEntity).then(
function successCallback(response) {
restrictionEntityService.setSelectedEntity(response.data[0]);
restrictionEntityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#restrictionEntityTabs li:eq(0) a').tab('show');
};
$scope.showRestrictionFieldDetail= function(index)
{
if (index!=null)
{
restrictionFieldService.searchOne(roleService.selectedEntity.restrictionFieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
restrictionFieldService.setSelectedEntity(response.data[0]);
restrictionFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
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
}
else
restrictionFieldService.searchOne(roleService.selectedEntity.restrictionField).then(
function successCallback(response) {
restrictionFieldService.setSelectedEntity(response.data[0]);
restrictionFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#restrictionFieldTabs li:eq(0) a').tab('show');
};
$scope.showRestrictionEntityGroupDetail= function(index)
{
if (index!=null)
{
restrictionEntityGroupService.searchOne(roleService.selectedEntity.restrictionEntityGroupList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
restrictionEntityGroupService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
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
}
else
restrictionEntityGroupService.searchOne(roleService.selectedEntity.restrictionEntityGroup).then(
function successCallback(response) {
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
restrictionEntityGroupService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
roleService.initRestrictionEntityList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
roleService.initRestrictionFieldList().then(function successCallback(response) {
roleService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
roleService.initRestrictionEntityGroupList().then(function successCallback(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
$scope.userListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'userId'},
{ name: 'username'},
{ name: 'password'},
{ name: 'enabled'} 
]
,data: $scope.selectedEntity.userList
 };
$scope.userListGridOptions.onRegisterApi = function(gridApi){
$scope.userGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
userService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
userService.setSelectedEntity(response.data[0]);
});
$('#userTabs li:eq(0) a').tab('show');
}
else 
userService.setSelectedEntity(null);
userService.selectedEntity.show = row.isSelected;
});
  };
$scope.restrictionEntityListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'restrictionEntityId'},
{ name: 'canCreate'},
{ name: 'canUpdate'},
{ name: 'canSearch'},
{ name: 'canDelete'} 
]
,data: $scope.selectedEntity.restrictionEntityList
 };
$scope.restrictionEntityListGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionEntityGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionEntityService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
restrictionEntityService.setSelectedEntity(response.data[0]);
});
$('#restrictionEntityTabs li:eq(0) a').tab('show');
}
else 
restrictionEntityService.setSelectedEntity(null);
restrictionEntityService.selectedEntity.show = row.isSelected;
});
  };
$scope.restrictionFieldListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'restrictionFieldId'} 
]
,data: $scope.selectedEntity.restrictionFieldList
 };
$scope.restrictionFieldListGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionFieldGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
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
$scope.restrictionEntityGroupListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'restrictionEntityGroupId'},
{ name: 'canCreate'},
{ name: 'canUpdate'},
{ name: 'canSearch'},
{ name: 'canDelete'} 
]
,data: $scope.selectedEntity.restrictionEntityGroupList
 };
$scope.restrictionEntityGroupListGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionEntityGroupGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
restrictionEntityGroupService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
restrictionEntityGroupService.setSelectedEntity(response.data[0]);
});
$('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
}
else 
restrictionEntityGroupService.setSelectedEntity(null);
restrictionEntityGroupService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedUser= function() {
roleService.selectedEntity.userList.push(roleService.selectedEntity.user);
}
$scope.downloadUserList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("user.xls",?) FROM ?',[mystyle,$scope.selectedEntity.userList]);
};
$scope.saveLinkedRestrictionEntity= function() {
roleService.selectedEntity.restrictionEntityList.push(roleService.selectedEntity.restrictionEntity);
}
$scope.downloadRestrictionEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionEntity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.restrictionEntityList]);
};
$scope.saveLinkedRestrictionField= function() {
roleService.selectedEntity.restrictionFieldList.push(roleService.selectedEntity.restrictionField);
}
$scope.downloadRestrictionFieldList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionField.xls",?) FROM ?',[mystyle,$scope.selectedEntity.restrictionFieldList]);
};
$scope.saveLinkedRestrictionEntityGroup= function() {
roleService.selectedEntity.restrictionEntityGroupList.push(roleService.selectedEntity.restrictionEntityGroup);
}
$scope.downloadRestrictionEntityGroupList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionEntityGroup.xls",?) FROM ?',[mystyle,$scope.selectedEntity.restrictionEntityGroupList]);
};
})
.service("userService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,roleList: []};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
for (i=0; i<entityList.length; i++)
this.entityList.push(entityList[i]);
};
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
} else 
this.emptyList(this.selectedEntity[val]);
} else {
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
} else {
this.selectedEntity[val] = entity[val];
}
}
}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../user/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../user/"+entity.userId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../user/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../user/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../user/"+this.selectedEntity.userId;
var promise= $http["delete"](url);
return promise; 
}
 this.initRoleList= function()
{
var promise= $http
.post("../role/search",
{});
return promise;
};
})
.controller("userController",function($scope,$http,userService,roleService,restrictionEntityService,restrictionFieldService,restrictionEntityGroupService,entityService,fieldService,relationshipService,enumFieldService,tabService,entityGroupService,annotationService,annotationAttributeService,enumValueService)
{
//role
$scope.searchBean=userService.searchBean;
$scope.entityList=userService.entityList;
$scope.selectedEntity=userService.selectedEntity;
$scope.childrenList=userService.childrenList; 
$scope.reset = function()
{
userService.resetSearchBean();
$scope.searchBean=userService.searchBean;userService.setSelectedEntity(null);
userService.selectedEntity.show=false;
userService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
roleService.update().then(function successCallback(response) {
roleService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
alert("error");
return; 
}
);
};
$scope.addNew= function()
{
userService.setSelectedEntity(null);
userService.setEntityList(null);
userService.selectedEntity.show=true;
$('#userTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
userService.selectedEntity.show=false;
userService.searchBean.roleList=[];
userService.searchBean.roleList.push(userService.searchBean.role);
delete userService.searchBean.role; 
userService.search().then(function successCallback(response) {
userService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.userDetailForm.$valid) return; 
userService.selectedEntity.show=false;
userService.selectedEntity.roleList.push(roleService.selectedEntity);
userService.insert().then(function successCallBack(response) { 
roleService.selectedEntity.userList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.userDetailForm.$valid) return; 
userService.selectedEntity.show=false;

for (i=0; i<roleService.selectedEntity.userList.length; i++)

{

if (roleService.selectedEntity.userList[i].userId==userService.selectedEntity.userId)

roleService.selectedEntity.userList.splice(i,1);

}

roleService.selectedEntity.userList.push(userService.selectedEntity);

userService.update().then(function successCallback(response){
userService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
userService.selectedEntity.show=false;
for (i=0; i<roleService.selectedEntity.userList.length; i++)
{
if (roleService.selectedEntity.userList[i].userId==userService.selectedEntity.userId)
roleService.selectedEntity.userList.splice(i,1);
}
userService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<roleService.selectedEntity.userList.length; i++)
{
if (roleService.selectedEntity.userList[i].userId==userService.selectedEntity.userId)
roleService.selectedEntity.userList.splice(i,1);
}
$scope.updateParent();
userService.del().then(function successCallback(response) { 
userService.setSelectedEntity(null);
roleService.initUserList().then(function(response) {
roleService.childrenList.userList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
 $scope.roleGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showRoleDetail= function(index)
{
if (index!=null)
{
roleService.searchOne(userService.selectedEntity.roleList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
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
}
else
roleService.searchOne(userService.selectedEntity.role).then(
function successCallback(response) {
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#roleTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
userService.initRoleList().then(function successCallback(response) {
userService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
$scope.roleListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'roleId'},
{ name: 'role'} 
]
,data: $scope.selectedEntity.roleList
 };
$scope.roleListGridOptions.onRegisterApi = function(gridApi){
$scope.roleGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
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
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("user.xls",?) FROM ?',[mystyle,$scope.entityList]);
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
alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,$scope.selectedEntity.roleList]);
};
})
.service("restrictionEntityGroupService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
for (i=0; i<entityList.length; i++)
this.entityList.push(entityList[i]);
};
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
} else 
this.emptyList(this.selectedEntity[val]);
} else {
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
} else {
this.selectedEntity[val] = entity[val];
}
}
}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../restrictionEntityGroup/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../restrictionEntityGroup/"+entity.restrictionEntityGroupId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../restrictionEntityGroup/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../restrictionEntityGroup/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../restrictionEntityGroup/"+this.selectedEntity.restrictionEntityGroupId;
var promise= $http["delete"](url);
return promise; 
}
 this.initEntityGroupList= function()
{
var promise= $http
.post("../entityGroup/search",
{});
return promise;
};
 this.initRoleList= function()
{
var promise= $http
.post("../role/search",
{});
return promise;
};
})
.controller("restrictionEntityGroupController",function($scope,$http,restrictionEntityGroupService,entityGroupService,roleService,entityService,fieldService,relationshipService,enumFieldService,tabService,restrictionEntityService,annotationService,restrictionFieldService,annotationAttributeService,enumValueService,userService)
{
//role
$scope.searchBean=restrictionEntityGroupService.searchBean;
$scope.entityList=restrictionEntityGroupService.entityList;
$scope.selectedEntity=restrictionEntityGroupService.selectedEntity;
$scope.childrenList=restrictionEntityGroupService.childrenList; 
$scope.reset = function()
{
restrictionEntityGroupService.resetSearchBean();
$scope.searchBean=restrictionEntityGroupService.searchBean;restrictionEntityGroupService.setSelectedEntity(null);
restrictionEntityGroupService.selectedEntity.show=false;
restrictionEntityGroupService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
roleService.update().then(function successCallback(response) {
roleService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
alert("error");
return; 
}
);
};
$scope.addNew= function()
{
restrictionEntityGroupService.setSelectedEntity(null);
restrictionEntityGroupService.setEntityList(null);
restrictionEntityGroupService.selectedEntity.show=true;
$('#restrictionEntityGroupTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
restrictionEntityGroupService.selectedEntity.show=false;
restrictionEntityGroupService.search().then(function successCallback(response) {
restrictionEntityGroupService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.restrictionEntityGroupDetailForm.$valid) return; 
restrictionEntityGroupService.selectedEntity.show=false;
restrictionEntityGroupService.selectedEntity.role={};
restrictionEntityGroupService.selectedEntity.role.roleId=roleService.selectedEntity.roleId;
restrictionEntityGroupService.insert().then(function successCallBack(response) { 
roleService.selectedEntity.restrictionEntityGroupList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.restrictionEntityGroupDetailForm.$valid) return; 
restrictionEntityGroupService.selectedEntity.show=false;

for (i=0; i<roleService.selectedEntity.restrictionEntityGroupList.length; i++)

{

if (roleService.selectedEntity.restrictionEntityGroupList[i].restrictionEntityGroupId==restrictionEntityGroupService.selectedEntity.restrictionEntityGroupId)

roleService.selectedEntity.restrictionEntityGroupList.splice(i,1);

}

roleService.selectedEntity.restrictionEntityGroupList.push(restrictionEntityGroupService.selectedEntity);

restrictionEntityGroupService.update().then(function successCallback(response){
restrictionEntityGroupService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
restrictionEntityGroupService.selectedEntity.show=false;
for (i=0; i<roleService.selectedEntity.restrictionEntityGroupList.length; i++)
{
if (roleService.selectedEntity.restrictionEntityGroupList[i].restrictionEntityGroupId==restrictionEntityGroupService.selectedEntity.restrictionEntityGroupId)
roleService.selectedEntity.restrictionEntityGroupList.splice(i,1);
}
restrictionEntityGroupService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<roleService.selectedEntity.restrictionEntityGroupList.length; i++)
{
if (roleService.selectedEntity.restrictionEntityGroupList[i].restrictionEntityGroupId==restrictionEntityGroupService.selectedEntity.restrictionEntityGroupId)
roleService.selectedEntity.restrictionEntityGroupList.splice(i,1);
}
$scope.updateParent();
restrictionEntityGroupService.del().then(function successCallback(response) { 
restrictionEntityGroupService.setSelectedEntity(null);
roleService.initRestrictionEntityGroupList().then(function(response) {
roleService.childrenList.restrictionEntityGroupList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
};
$scope.trueFalseValues=[true,false];
$scope.showEntityGroupDetail= function(index)
{
if (index!=null)
{
entityGroupService.searchOne(restrictionEntityGroupService.selectedEntity.entityGroupList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
entityGroupService.setSelectedEntity(response.data[0]);
entityGroupService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (restrictionEntityGroupService.selectedEntity.entityGroup==null || restrictionEntityGroupService.selectedEntity.entityGroup==undefined)
{
entityGroupService.setSelectedEntity(null); 
entityGroupService.selectedEntity.show=true; 
}
else
entityGroupService.searchOne(restrictionEntityGroupService.selectedEntity.entityGroup).then(
function successCallback(response) {
entityGroupService.setSelectedEntity(response.data[0]);
entityGroupService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#entityGroupTabs li:eq(0) a').tab('show');
};
$scope.showRoleDetail= function(index)
{
if (index!=null)
{
roleService.searchOne(restrictionEntityGroupService.selectedEntity.roleList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (restrictionEntityGroupService.selectedEntity.role==null || restrictionEntityGroupService.selectedEntity.role==undefined)
{
roleService.setSelectedEntity(null); 
roleService.selectedEntity.show=true; 
}
else
roleService.searchOne(restrictionEntityGroupService.selectedEntity.role).then(
function successCallback(response) {
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#roleTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
restrictionEntityGroupService.initEntityGroupList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
restrictionEntityGroupService.initRoleList().then(function successCallback(response) {
restrictionEntityGroupService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("restrictionEntityGroup.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadEntityGroupList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entityGroup.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityGroupList]);
};
$scope.downloadRoleList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,$scope.selectedEntity.roleList]);
};
})
.service("annotationAttributeService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
for (i=0; i<entityList.length; i++)
this.entityList.push(entityList[i]);
};
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
} else 
this.emptyList(this.selectedEntity[val]);
} else {
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
} else {
this.selectedEntity[val] = entity[val];
}
}
}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../annotationAttribute/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../annotationAttribute/"+entity.annotationAttributeId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../annotationAttribute/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../annotationAttribute/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../annotationAttribute/"+this.selectedEntity.annotationAttributeId;
var promise= $http["delete"](url);
return promise; 
}
 this.initAnnotationList= function()
{
var promise= $http
.post("../annotation/search",
{});
return promise;
};
})
.controller("annotationAttributeController",function($scope,$http,annotationAttributeService,annotationService,fieldService,relationshipService,enumFieldService,entityService,restrictionFieldService,tabService,restrictionEntityService,entityGroupService,roleService,userService,restrictionEntityGroupService,enumValueService)
{
//annotation
$scope.searchBean=annotationAttributeService.searchBean;
$scope.entityList=annotationAttributeService.entityList;
$scope.selectedEntity=annotationAttributeService.selectedEntity;
$scope.childrenList=annotationAttributeService.childrenList; 
$scope.reset = function()
{
annotationAttributeService.resetSearchBean();
$scope.searchBean=annotationAttributeService.searchBean;annotationAttributeService.setSelectedEntity(null);
annotationAttributeService.selectedEntity.show=false;
annotationAttributeService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
annotationService.update().then(function successCallback(response) {
annotationService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
alert("error");
return; 
}
);
};
$scope.addNew= function()
{
annotationAttributeService.setSelectedEntity(null);
annotationAttributeService.setEntityList(null);
annotationAttributeService.selectedEntity.show=true;
$('#annotationAttributeTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
annotationAttributeService.selectedEntity.show=false;
annotationAttributeService.search().then(function successCallback(response) {
annotationAttributeService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.annotationAttributeDetailForm.$valid) return; 
annotationAttributeService.selectedEntity.show=false;
annotationAttributeService.selectedEntity.annotation={};
annotationAttributeService.selectedEntity.annotation.annotationId=annotationService.selectedEntity.annotationId;
annotationAttributeService.insert().then(function successCallBack(response) { 
annotationService.selectedEntity.annotationAttributeList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.annotationAttributeDetailForm.$valid) return; 
annotationAttributeService.selectedEntity.show=false;

for (i=0; i<annotationService.selectedEntity.annotationAttributeList.length; i++)

{

if (annotationService.selectedEntity.annotationAttributeList[i].annotationAttributeId==annotationAttributeService.selectedEntity.annotationAttributeId)

annotationService.selectedEntity.annotationAttributeList.splice(i,1);

}

annotationService.selectedEntity.annotationAttributeList.push(annotationAttributeService.selectedEntity);

annotationAttributeService.update().then(function successCallback(response){
annotationAttributeService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
annotationAttributeService.selectedEntity.show=false;
for (i=0; i<annotationService.selectedEntity.annotationAttributeList.length; i++)
{
if (annotationService.selectedEntity.annotationAttributeList[i].annotationAttributeId==annotationAttributeService.selectedEntity.annotationAttributeId)
annotationService.selectedEntity.annotationAttributeList.splice(i,1);
}
annotationAttributeService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<annotationService.selectedEntity.annotationAttributeList.length; i++)
{
if (annotationService.selectedEntity.annotationAttributeList[i].annotationAttributeId==annotationAttributeService.selectedEntity.annotationAttributeId)
annotationService.selectedEntity.annotationAttributeList.splice(i,1);
}
$scope.updateParent();
annotationAttributeService.del().then(function successCallback(response) { 
annotationAttributeService.setSelectedEntity(null);
annotationService.initAnnotationAttributeList().then(function(response) {
annotationService.childrenList.annotationAttributeList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
};
$scope.trueFalseValues=[true,false];
$scope.showAnnotationDetail= function(index)
{
if (index!=null)
{
annotationService.searchOne(annotationAttributeService.selectedEntity.annotationList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
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
}
else
annotationService.searchOne(annotationAttributeService.selectedEntity.annotation).then(
function successCallback(response) {
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#annotationTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
annotationAttributeService.initAnnotationList().then(function successCallback(response) {
annotationAttributeService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
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
})
;