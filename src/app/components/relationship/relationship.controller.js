(function() { 

angular
.module("serverTestApp")
.controller("RelationshipController",RelationshipController);
/** @ngInject */
function RelationshipController($scope,$http,$rootScope ,relationshipService, SecurityService, MainService ,entityService,restrictionEntityService,roleService,restrictionFieldService,fieldService,tabService,enumFieldService,enumEntityService,projectService,entityGroupService,restrictionEntityGroupService,enumValueService,annotationService,annotationAttributeService,userService)
{
$scope.searchBean=relationshipService.searchBean;
$scope.entityList=relationshipService.entityList;
$scope.selectedEntity=relationshipService.selectedEntity;
$scope.hidden=relationshipService.hidden;
$scope.childrenList=relationshipService.childrenList; 
$scope.reset = function()
{
relationshipService.resetSearchBean();
$scope.searchBean=relationshipService.searchBean;relationshipService.setSelectedEntity(null);
relationshipService.selectedEntity.show=false;
relationshipService.setEntityList(null); 
if (relationshipService.isParent()) 
{
entityService.selectedEntity.show=false;
entityService.selectedEntity.show=false;
tabService.selectedEntity.show=false;
annotationService.selectedEntity.show=false;
}
}
$scope.addNew= function()
{
relationshipService.setSelectedEntity(null);
relationshipService.setEntityList(null);
relationshipService.selectedEntity.show=true;
if (relationshipService.isParent()) 
{
entityService.selectedEntity.show=false;
entityService.selectedEntity.show=false;
tabService.selectedEntity.show=false;
annotationService.selectedEntity.show=false;
}
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
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
};
$scope.insert=function()
{
if (!$scope.relationshipDetailForm.$valid) return; 
if (relationshipService.isParent()) 
{
relationshipService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
else 
{
relationshipService.selectedEntity.show=false;
relationshipService.insert().then(function successCallBack(response) { 
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
};
$scope.update=function()
{
if (!$scope.relationshipDetailForm.$valid) return; 
if (relationshipService.isParent()) 
{
entityService.selectedEntity.show=false;
entityService.selectedEntity.show=false;
tabService.selectedEntity.show=false;
annotationService.selectedEntity.show=false;
relationshipService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
else 
{
relationshipService.selectedEntity.show=false;

relationshipService.update().then(function successCallback(response){
relationshipService.setSelectedEntity(response.data);
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
relationshipService.selectedEntity.show=false;
relationshipService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
if (!relationshipService.isParent()) 
$scope.updateParent();
relationshipService.del().then(function successCallback(response) { 
if (relationshipService.isParent()) 
{
$scope.search();
} else { 
relationshipService.setSelectedEntity(null);
}
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
};
$scope.refreshTableDetail= function() 
{
if ($scope.entityGridApi!=undefined && $scope.entityGridApi!=null)
 $scope.entityGridApi.core.handleWindowResize(); 
if ($scope.entityGridApi!=undefined && $scope.entityGridApi!=null)
 $scope.entityGridApi.core.handleWindowResize(); 
if ($scope.tabGridApi!=undefined && $scope.tabGridApi!=null)
 $scope.tabGridApi.core.handleWindowResize(); 
if ($scope.annotationGridApi!=undefined && $scope.annotationGridApi!=null)
 $scope.annotationGridApi.core.handleWindowResize(); 
};
$scope.loadFile = function(file,field)
{
relationshipService.loadFile(file,field).then(function successCallback(response) {
relationshipService.setSelectedEntity(response.data);
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
return; 
});
}
$scope.trueFalseValues=[true,false];
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(relationshipService.selectedEntity.entityList[index]).then(
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
if (relationshipService.selectedEntity.entity==null || relationshipService.selectedEntity.entity==undefined)
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
entityService.searchOne(relationshipService.selectedEntity.entity).then(
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
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(relationshipService.selectedEntity.entityList[index]).then(
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
if (relationshipService.selectedEntity.entity==null || relationshipService.selectedEntity.entity==undefined)
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
entityService.searchOne(relationshipService.selectedEntity.entity).then(
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
tabService.searchOne(relationshipService.selectedEntity.tabList[index]).then(
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
if (relationshipService.selectedEntity.tab==null || relationshipService.selectedEntity.tab==undefined)
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
tabService.searchOne(relationshipService.selectedEntity.tab).then(
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
annotationService.searchOne(relationshipService.selectedEntity.annotationList[index]).then(
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
if (relationshipService.selectedEntity.annotation==null || relationshipService.selectedEntity.annotation==undefined)
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
annotationService.searchOne(relationshipService.selectedEntity.annotation).then(
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
$scope.relationshipGridOptions={};
cloneObject(relationshipService.gridOptions,$scope.relationshipGridOptions);
$scope.relationshipGridOptions.data=relationshipService.entityList;
$scope.initChildrenList = function () { 
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entity.canSearch)
relationshipService.initEntityList().then(function successCallback(response) {
relationshipService.childrenList.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.entity.canSearch)
relationshipService.initEntityList().then(function successCallback(response) {
relationshipService.childrenList.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.entity!=undefined && $rootScope.restrictionList.entity.restrictionItemMap.tab.canSearch)
relationshipService.initTabList().then(function successCallback(response) {
relationshipService.childrenList.tabList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
if ($rootScope.restrictionList.field!=undefined && $rootScope.restrictionList.field.restrictionItemMap.annotation.canSearch)
relationshipService.initAnnotationList().then(function successCallback(response) {
relationshipService.childrenList.annotationList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
relationshipService.childrenList.relationshipTypeList=["ONE_TO_ONE","ONE_TO_MANY","MANY_TO_ONE","MANY_TO_MANY","MANY_TO_MANY_BACK",];
}
$scope.relationshipGridOptions.onRegisterApi = function(gridApi){
$scope.relationshipGridApi = gridApi;
gridApi.selection.on.rowSelectionChanged($scope,function(row){
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
entityService.initRelationshipList().then(function(response) {
entityService.childrenList.relationshipList=response.data;
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
tabService.initRelationshipList().then(function(response) {
tabService.childrenList.relationshipList=response.data;
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
annotationService.initRelationshipList().then(function(response) {
annotationService.childrenList.relationshipList=response.data;
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
relationshipService.setSelectedEntity(null);
relationshipService.selectedEntity.show=false;
}
}
})();
