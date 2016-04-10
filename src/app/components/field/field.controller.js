(function() { 

angular
.module("serverTestApp")
.controller("FieldController",FieldController);
/** @ngInject */
function FieldController($scope,$http,$rootScope ,fieldService, SecurityService, MainService ,restrictionFieldService,roleService,restrictionEntityService,entityService,tabService,enumFieldService,enumEntityService,projectService,entityGroupService,restrictionEntityGroupService,enumValueService,annotationService,annotationAttributeService,relationshipService,userService)
{
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
if (fieldService.isParent()) 
{
restrictionFieldService.selectedEntity.show=false;
entityService.selectedEntity.show=false;
tabService.selectedEntity.show=false;
annotationService.selectedEntity.show=false;
}
}
$scope.addNew= function()
{
fieldService.setSelectedEntity(null);
fieldService.setEntityList(null);
fieldService.selectedEntity.show=true;
if (fieldService.isParent()) 
{
restrictionFieldService.selectedEntity.show=false;
entityService.selectedEntity.show=false;
tabService.selectedEntity.show=false;
annotationService.selectedEntity.show=false;
}
$('#fieldTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
fieldService.selectedEntity.show=false;
fieldService.searchBean.restrictionFieldList=[];
fieldService.searchBean.restrictionFieldList.push(fieldService.searchBean.restrictionField);
delete fieldService.searchBean.restrictionField; 
fieldService.searchBean.annotationList=[];
fieldService.searchBean.annotationList.push(fieldService.searchBean.annotation);
delete fieldService.searchBean.annotation; 
fieldService.search().then(function successCallback(response) {
fieldService.setEntityList(response.data);
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
};
$scope.insert=function()
{
if (!$scope.fieldDetailForm.$valid) return; 
if (fieldService.isParent()) 
{
fieldService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
else 
{
fieldService.selectedEntity.show=false;
fieldService.insert().then(function successCallBack(response) { 
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
};
$scope.update=function()
{
if (!$scope.fieldDetailForm.$valid) return; 
if (fieldService.isParent()) 
{
restrictionFieldService.selectedEntity.show=false;
entityService.selectedEntity.show=false;
tabService.selectedEntity.show=false;
annotationService.selectedEntity.show=false;
fieldService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
else 
{
fieldService.selectedEntity.show=false;

fieldService.update().then(function successCallback(response){
fieldService.setSelectedEntity(response.data);
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
fieldService.selectedEntity.show=false;
fieldService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!fieldService.isParent()) 
$scope.updateParent();
fieldService.del().then(function successCallback(response) { 
if (fieldService.isParent()) 
{
$scope.search();
} else { 
fieldService.setSelectedEntity(null);
}
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.restrictionFieldGridApi!=undefined && $scope.restrictionFieldGridApi!=null)
 $scope.restrictionFieldGridApi.core.handleWindowResize(); 
if ($scope.entityGridApi!=undefined && $scope.entityGridApi!=null)
 $scope.entityGridApi.core.handleWindowResize(); 
if ($scope.tabGridApi!=undefined && $scope.tabGridApi!=null)
 $scope.tabGridApi.core.handleWindowResize(); 
if ($scope.annotationGridApi!=undefined && $scope.annotationGridApi!=null)
 $scope.annotationGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
fieldService.loadFile(file,field).then(function successCallback(response) {
fieldService.setSelectedEntity(response.data);
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
return; 
});
}
$scope.trueFalseValues=[true,false];
$scope.showRestrictionFieldDetail= function(index)
{
if (index!=null)
{
restrictionFieldService.searchOne(fieldService.selectedEntity.restrictionFieldList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
restrictionFieldService.initRoleList().then(function successCallback(response) {
restrictionFieldService.childrenList.roleList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
restrictionFieldService.initFieldList().then(function successCallback(response) {
restrictionFieldService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
restrictionFieldService.setSelectedEntity(response.data[0]);
restrictionFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
else 
{
if (fieldService.selectedEntity.restrictionField==null || fieldService.selectedEntity.restrictionField==undefined)
{
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
restrictionFieldService.initRoleList().then(function successCallback(response) {
restrictionFieldService.childrenList.roleList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
restrictionFieldService.initFieldList().then(function successCallback(response) {
restrictionFieldService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
restrictionFieldService.setSelectedEntity(null); 
restrictionFieldService.selectedEntity.show=true; 
}
else
restrictionFieldService.searchOne(fieldService.selectedEntity.restrictionField).then(
function successCallback(response) {
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
restrictionFieldService.initRoleList().then(function successCallback(response) {
restrictionFieldService.childrenList.roleList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
restrictionFieldService.initFieldList().then(function successCallback(response) {
restrictionFieldService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
restrictionFieldService.setSelectedEntity(response.data[0]);
restrictionFieldService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
$('#restrictionFieldTabs li:eq(0) a').tab('show');
};
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(fieldService.selectedEntity.entityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.relationship!=undefined && $rootScope.restrictionList.relationship.restrictionItemMap.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
entityService.childrenList.securityTypeList=["BLOCK_WITH_RESTRICTION","ACCESS_WITH_PERMISSION",];
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
else 
{
if (fieldService.selectedEntity.entity==null || fieldService.selectedEntity.entity==undefined)
{
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.relationship!=undefined && $rootScope.restrictionList.relationship.restrictionItemMap.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
entityService.childrenList.securityTypeList=["BLOCK_WITH_RESTRICTION","ACCESS_WITH_PERMISSION",];
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
}
else
entityService.searchOne(fieldService.selectedEntity.entity).then(
function successCallback(response) {
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.relationship!=undefined && $rootScope.restrictionList.relationship.restrictionItemMap.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
entityService.childrenList.securityTypeList=["BLOCK_WITH_RESTRICTION","ACCESS_WITH_PERMISSION",];
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
$('#entityTabs li:eq(0) a').tab('show');
};
$scope.showTabDetail= function(index)
{
if (index!=null)
{
tabService.searchOne(fieldService.selectedEntity.tabList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entity.canSearch)
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.enumField.canSearch)
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.relationship!=undefined && $rootScope.restrictionList.relationship.restrictionItemMap.relationship.canSearch)
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
else 
{
if (fieldService.selectedEntity.tab==null || fieldService.selectedEntity.tab==undefined)
{
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entity.canSearch)
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.enumField.canSearch)
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.relationship!=undefined && $rootScope.restrictionList.relationship.restrictionItemMap.relationship.canSearch)
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
tabService.setSelectedEntity(null); 
tabService.selectedEntity.show=true; 
}
else
tabService.searchOne(fieldService.selectedEntity.tab).then(
function successCallback(response) {
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entity.canSearch)
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.enumField.canSearch)
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.relationship!=undefined && $rootScope.restrictionList.relationship.restrictionItemMap.relationship.canSearch)
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
tabService.setSelectedEntity(response.data[0]);
tabService.selectedEntity.show=true;
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
}
$('#tabTabs li:eq(0) a').tab('show');
};
$scope.showAnnotationDetail= function(index)
{
if (index!=null)
{
annotationService.searchOne(fieldService.selectedEntity.annotationList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.annotationAttribute.canSearch)
annotationService.initAnnotationAttributeList().then(function successCallback(response) {
annotationService.childrenList.annotationAttributeList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
annotationService.initFieldList().then(function successCallback(response) {
annotationService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.enumField.canSearch)
annotationService.initEnumFieldList().then(function successCallback(response) {
annotationService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.relationship!=undefined && $rootScope.restrictionList.relationship.restrictionItemMap.relationship.canSearch)
annotationService.initRelationshipList().then(function successCallback(response) {
annotationService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
annotationService.childrenList.annotationTypeList=["PRIMARY_KEY","NOT_NULL","NOT_BLANK","DESCRIPTION_FIELD","BETWEEN_FILTER","EXCEL_EXPORT","FILTER_FIELD","IGNORE_SEARCH","IGNORE_UPDATE","IGNORE_TABLE_LIST","SIZE","PASSWORD","PRIORITY","EMBEDDED","CACHE",];
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
if (fieldService.selectedEntity.annotation==null || fieldService.selectedEntity.annotation==undefined)
{
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.annotationAttribute.canSearch)
annotationService.initAnnotationAttributeList().then(function successCallback(response) {
annotationService.childrenList.annotationAttributeList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
annotationService.initFieldList().then(function successCallback(response) {
annotationService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.enumField.canSearch)
annotationService.initEnumFieldList().then(function successCallback(response) {
annotationService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.relationship!=undefined && $rootScope.restrictionList.relationship.restrictionItemMap.relationship.canSearch)
annotationService.initRelationshipList().then(function successCallback(response) {
annotationService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
annotationService.childrenList.annotationTypeList=["PRIMARY_KEY","NOT_NULL","NOT_BLANK","DESCRIPTION_FIELD","BETWEEN_FILTER","EXCEL_EXPORT","FILTER_FIELD","IGNORE_SEARCH","IGNORE_UPDATE","IGNORE_TABLE_LIST","SIZE","PASSWORD","PRIORITY","EMBEDDED","CACHE",];
annotationService.setSelectedEntity(null); 
annotationService.selectedEntity.show=true; 
}
else
annotationService.searchOne(fieldService.selectedEntity.annotation).then(
function successCallback(response) {
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.annotationAttribute.canSearch)
annotationService.initAnnotationAttributeList().then(function successCallback(response) {
annotationService.childrenList.annotationAttributeList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
annotationService.initFieldList().then(function successCallback(response) {
annotationService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.enumField.canSearch)
annotationService.initEnumFieldList().then(function successCallback(response) {
annotationService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.relationship!=undefined && $rootScope.restrictionList.relationship.restrictionItemMap.relationship.canSearch)
annotationService.initRelationshipList().then(function successCallback(response) {
annotationService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
annotationService.childrenList.annotationTypeList=["PRIMARY_KEY","NOT_NULL","NOT_BLANK","DESCRIPTION_FIELD","BETWEEN_FILTER","EXCEL_EXPORT","FILTER_FIELD","IGNORE_SEARCH","IGNORE_UPDATE","IGNORE_TABLE_LIST","SIZE","PASSWORD","PRIORITY","EMBEDDED","CACHE",];
annotationService.setSelectedEntity(response.data[0]);
annotationService.selectedEntity.show=true;
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
alasql('SELECT * INTO XLSXML("field.xls",?) FROM ?',[mystyle,$scope.entityList]);
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
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityList]);
};
$scope.downloadTabList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("tab.xls",?) FROM ?',[mystyle,$scope.selectedEntity.tabList]);
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
$scope.fieldGridOptions={};
cloneObject(fieldService.gridOptions,$scope.fieldGridOptions);
$scope.fieldGridOptions.data=fieldService.entityList;
$scope.initChildrenList = function () { 
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionField.canSearch)
fieldService.initRestrictionFieldList().then(function successCallback(response) {
fieldService.childrenList.restrictionFieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entity.canSearch)
fieldService.initEntityList().then(function successCallback(response) {
fieldService.childrenList.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.tab.canSearch)
fieldService.initTabList().then(function successCallback(response) {
fieldService.childrenList.tabList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.annotation.canSearch)
fieldService.initAnnotationList().then(function successCallback(response) {
fieldService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG","FILE",];
}
$scope.fieldGridOptions.onRegisterApi = function(gridApi){
$scope.fieldGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
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
$scope.restrictionFieldGridOptions={};
cloneObject(restrictionFieldService.gridOptions,$scope.restrictionFieldGridOptions);
$scope.restrictionFieldGridOptions.data=$scope.selectedEntity.restrictionFieldList;
$scope.initChildrenList = function () { 
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.role.canSearch)
restrictionFieldService.initRoleList().then(function successCallback(response) {
restrictionFieldService.childrenList.roleList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
restrictionFieldService.initFieldList().then(function successCallback(response) {
restrictionFieldService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
$scope.restrictionFieldGridOptions.onRegisterApi = function(gridApi){
$scope.restrictionFieldGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
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
$scope.entityGridOptions={};
cloneObject(entityService.gridOptions,$scope.entityGridOptions);
$scope.entityGridOptions.data=$scope.selectedEntity.entityList;
$scope.initChildrenList = function () { 
if ($rootScope.restrictionList.security!=undefined && $rootScope.restrictionList.security.restrictionItemMap.restrictionEntity.canSearch)
entityService.initRestrictionEntityList().then(function successCallback(response) {
entityService.childrenList.restrictionEntityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.tab.canSearch)
entityService.initTabList().then(function successCallback(response) {
entityService.childrenList.tabList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entityGroup.canSearch)
entityService.initEntityGroupList().then(function successCallback(response) {
entityService.childrenList.entityGroupList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.enumField.canSearch)
entityService.initEnumFieldList().then(function successCallback(response) {
entityService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
entityService.initFieldList().then(function successCallback(response) {
entityService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.relationship!=undefined && $rootScope.restrictionList.relationship.restrictionItemMap.relationship.canSearch)
entityService.initRelationshipList().then(function successCallback(response) {
entityService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
entityService.childrenList.securityTypeList=["BLOCK_WITH_RESTRICTION","ACCESS_WITH_PERMISSION",];
}
$scope.entityGridOptions.onRegisterApi = function(gridApi){
$scope.entityGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
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
$scope.tabGridOptions={};
cloneObject(tabService.gridOptions,$scope.tabGridOptions);
$scope.tabGridOptions.data=$scope.selectedEntity.tabList;
$scope.initChildrenList = function () { 
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entity.canSearch)
tabService.initEntityList().then(function successCallback(response) {
tabService.childrenList.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
tabService.initFieldList().then(function successCallback(response) {
tabService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.enumField.canSearch)
tabService.initEnumFieldList().then(function successCallback(response) {
tabService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.relationship!=undefined && $rootScope.restrictionList.relationship.restrictionItemMap.relationship.canSearch)
tabService.initRelationshipList().then(function successCallback(response) {
tabService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
$scope.tabGridOptions.onRegisterApi = function(gridApi){
$scope.tabGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
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
$scope.annotationGridOptions={};
cloneObject(annotationService.gridOptions,$scope.annotationGridOptions);
$scope.annotationGridOptions.data=$scope.selectedEntity.annotationList;
$scope.initChildrenList = function () { 
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.annotationAttribute.canSearch)
annotationService.initAnnotationAttributeList().then(function successCallback(response) {
annotationService.childrenList.annotationAttributeList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.field.canSearch)
annotationService.initFieldList().then(function successCallback(response) {
annotationService.childrenList.fieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.enumField.canSearch)
annotationService.initEnumFieldList().then(function successCallback(response) {
annotationService.childrenList.enumFieldList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.relationship!=undefined && $rootScope.restrictionList.relationship.restrictionItemMap.relationship.canSearch)
annotationService.initRelationshipList().then(function successCallback(response) {
annotationService.childrenList.relationshipList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
annotationService.childrenList.annotationTypeList=["PRIMARY_KEY","NOT_NULL","NOT_BLANK","DESCRIPTION_FIELD","BETWEEN_FILTER","EXCEL_EXPORT","FILTER_FIELD","IGNORE_SEARCH","IGNORE_UPDATE","IGNORE_TABLE_LIST","SIZE","PASSWORD","PRIORITY","EMBEDDED","CACHE",];
}
$scope.annotationGridOptions.onRegisterApi = function(gridApi){
$scope.annotationGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
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
function updateParentEntities() { 
restrictionFieldService.initFieldList().then(function(response) {
restrictionFieldService.childrenList.fieldList=response.data;
});

if (restrictionFieldService.selectedEntity.restrictionFieldId!=undefined) restrictionFieldService.searchOne(restrictionFieldService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
restrictionFieldService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
entityService.initFieldList().then(function(response) {
entityService.childrenList.fieldList=response.data;
});

if (entityService.selectedEntity.entityId!=undefined) entityService.searchOne(entityService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
entityService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
tabService.initFieldList().then(function(response) {
tabService.childrenList.fieldList=response.data;
});

if (tabService.selectedEntity.tabId!=undefined) tabService.searchOne(tabService.selectedEntity).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
tabService.setSelectedEntity(response.data[0]);
  }, function errorCallback(response) {
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
  }	
);
annotationService.initFieldList().then(function(response) {
annotationService.childrenList.fieldList=response.data;
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
fieldService.setSelectedEntity(null);
fieldService.selectedEntity.show=false;
}
}
})();
