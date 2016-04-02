(function() { 
'use strict'; 

angular
.module("serverTestApp").config(routerConfig);
/** @ngInject */
function routerConfig($stateProvider,$urlRouterProvider)
{
$stateProvider
.state('main',{
 url:'/app',
abstract:true,
templateUrl:'app/main/main.html',
 controller:'MainController', 
controllerAs: 'main' ,
name: 'main'
 })
.state('main.home',{
 url:'/home',
views:{
'pageContent': {
templateUrl:'app/components/home/home.html',
 controller:'HomeController', 
controllerAs: 'vm' 
 }
}
})
.state('main.role',{
 url:'/role',
views:{
'pageContent': {
templateUrl:'app/components/role/role.html',
 controller:'RoleController', 
controllerAs: 'vm' 
 }
/*resolve: {
setParent: function(mainService,roleService){
if (mainService.parentService!=null)
{
mainService.parentService.resetSearchBean();
mainService.parentService.setSelectedEntity(null);
mainService.parentService.selectedEntity.show=false;
mainService.parentService.setEntityList(null);
}
mainService.parentEntity="Role";
mainService.parentService=roleService;
mainService.parentService.initRestrictionFieldList().then(function(response) {
mainService.parentService.childrenList.restrictionFieldList=response.data;
});
mainService.parentService.initEnumFieldList().then(function(response) {
mainService.parentService.childrenList.enumFieldList=response.data;
});
mainService.parentService.initAnnotationList().then(function(response) {
mainService.parentService.childrenList.annotationList=response.data;
});
mainService.parentService.initRelationshipList().then(function(response) {
mainService.parentService.childrenList.relationshipList=response.data;
});
}
*/}
})
.state('main.restrictionField',{
 url:'/restrictionField',
views:{
'pageContent': {
templateUrl:'app/components/restrictionField/restrictionField.html',
 controller:'RestrictionFieldController', 
controllerAs: 'vm' 
 }
/*resolve: {
setParent: function(mainService,restrictionFieldService){
if (mainService.parentService!=null)
{
mainService.parentService.resetSearchBean();
mainService.parentService.setSelectedEntity(null);
mainService.parentService.selectedEntity.show=false;
mainService.parentService.setEntityList(null);
}
mainService.parentEntity="RestrictionField";
mainService.parentService=restrictionFieldService;
mainService.parentService.initRoleList().then(function(response) {
mainService.parentService.childrenList.roleList=response.data;
});
mainService.parentService.initFieldList().then(function(response) {
mainService.parentService.childrenList.fieldList=response.data;
});
}
*/}
})
.state('main.project',{
 url:'/project',
views:{
'pageContent': {
templateUrl:'app/components/project/project.html',
 controller:'ProjectController', 
controllerAs: 'vm' 
 }
/*resolve: {
setParent: function(mainService,projectService){
if (mainService.parentService!=null)
{
mainService.parentService.resetSearchBean();
mainService.parentService.setSelectedEntity(null);
mainService.parentService.selectedEntity.show=false;
mainService.parentService.setEntityList(null);
}
mainService.parentEntity="Project";
mainService.parentService=projectService;
mainService.parentService.initEnumEntityList().then(function(response) {
mainService.parentService.childrenList.enumEntityList=response.data;
});
mainService.parentService.initEntityGroupList().then(function(response) {
mainService.parentService.childrenList.entityGroupList=response.data;
});
}
*/}
})
.state('main.tab',{
 url:'/tab',
views:{
'pageContent': {
templateUrl:'app/components/tab/tab.html',
 controller:'TabController', 
controllerAs: 'vm' 
 }
/*resolve: {
setParent: function(mainService,tabService){
if (mainService.parentService!=null)
{
mainService.parentService.resetSearchBean();
mainService.parentService.setSelectedEntity(null);
mainService.parentService.selectedEntity.show=false;
mainService.parentService.setEntityList(null);
}
mainService.parentEntity="Tab";
mainService.parentService=tabService;
mainService.parentService.initEntityList().then(function(response) {
mainService.parentService.childrenList.entityList=response.data;
});
mainService.parentService.initFieldList().then(function(response) {
mainService.parentService.childrenList.fieldList=response.data;
});
mainService.parentService.initEnumFieldList().then(function(response) {
mainService.parentService.childrenList.enumFieldList=response.data;
});
mainService.parentService.initRelationshipList().then(function(response) {
mainService.parentService.childrenList.relationshipList=response.data;
});
}
*/}
})
.state('main.entity',{
 url:'/entity',
views:{
'pageContent': {
templateUrl:'app/components/entity/entity.html',
 controller:'EntityController', 
controllerAs: 'vm' 
 }
/*resolve: {
setParent: function(mainService,entityService){
if (mainService.parentService!=null)
{
mainService.parentService.resetSearchBean();
mainService.parentService.setSelectedEntity(null);
mainService.parentService.selectedEntity.show=false;
mainService.parentService.setEntityList(null);
}
mainService.parentEntity="Entity";
mainService.parentService=entityService;
mainService.parentService.initTabList().then(function(response) {
mainService.parentService.childrenList.tabList=response.data;
});
mainService.parentService.initEntityGroupList().then(function(response) {
mainService.parentService.childrenList.entityGroupList=response.data;
});
mainService.parentService.initEnumFieldList().then(function(response) {
mainService.parentService.childrenList.enumFieldList=response.data;
});
mainService.parentService.initFieldList().then(function(response) {
mainService.parentService.childrenList.fieldList=response.data;
});
mainService.parentService.initEnumFieldList().then(function(response) {
mainService.parentService.childrenList.enumFieldList=response.data;
});
mainService.parentService.initRelationshipList().then(function(response) {
mainService.parentService.childrenList.relationshipList=response.data;
});
}
*/}
})
.state('main.enumEntity',{
 url:'/enumEntity',
views:{
'pageContent': {
templateUrl:'app/components/enumEntity/enumEntity.html',
 controller:'EnumEntityController', 
controllerAs: 'vm' 
 }
/*resolve: {
setParent: function(mainService,enumEntityService){
if (mainService.parentService!=null)
{
mainService.parentService.resetSearchBean();
mainService.parentService.setSelectedEntity(null);
mainService.parentService.selectedEntity.show=false;
mainService.parentService.setEntityList(null);
}
mainService.parentEntity="EnumEntity";
mainService.parentService=enumEntityService;
mainService.parentService.initProjectList().then(function(response) {
mainService.parentService.childrenList.projectList=response.data;
});
mainService.parentService.initEnumValueList().then(function(response) {
mainService.parentService.childrenList.enumValueList=response.data;
});
}
*/}
})
.state('main.entityGroup',{
 url:'/entityGroup',
views:{
'pageContent': {
templateUrl:'app/components/entityGroup/entityGroup.html',
 controller:'EntityGroupController', 
controllerAs: 'vm' 
 }
/*resolve: {
setParent: function(mainService,entityGroupService){
if (mainService.parentService!=null)
{
mainService.parentService.resetSearchBean();
mainService.parentService.setSelectedEntity(null);
mainService.parentService.selectedEntity.show=false;
mainService.parentService.setEntityList(null);
}
mainService.parentEntity="EntityGroup";
mainService.parentService=entityGroupService;
mainService.parentService.initRelationshipList().then(function(response) {
mainService.parentService.childrenList.relationshipList=response.data;
});
mainService.parentService.initProjectList().then(function(response) {
mainService.parentService.childrenList.projectList=response.data;
});
mainService.parentService.initEntityList().then(function(response) {
mainService.parentService.childrenList.entityList=response.data;
});
}
*/}
})
.state('main.annotation',{
 url:'/annotation',
views:{
'pageContent': {
templateUrl:'app/components/annotation/annotation.html',
 controller:'AnnotationController', 
controllerAs: 'vm' 
 }
/*resolve: {
setParent: function(mainService,annotationService){
if (mainService.parentService!=null)
{
mainService.parentService.resetSearchBean();
mainService.parentService.setSelectedEntity(null);
mainService.parentService.selectedEntity.show=false;
mainService.parentService.setEntityList(null);
}
mainService.parentEntity="Annotation";
mainService.parentService=annotationService;
mainService.parentService.initAnnotationAttributeList().then(function(response) {
mainService.parentService.childrenList.annotationAttributeList=response.data;
});
mainService.parentService.initFieldList().then(function(response) {
mainService.parentService.childrenList.fieldList=response.data;
});
mainService.parentService.initEnumFieldList().then(function(response) {
mainService.parentService.childrenList.enumFieldList=response.data;
});
mainService.parentService.initRelationshipList().then(function(response) {
mainService.parentService.childrenList.relationshipList=response.data;
});
}
*/}
})
.state('main.enumField',{
 url:'/enumField',
views:{
'pageContent': {
templateUrl:'app/components/enumField/enumField.html',
 controller:'EnumFieldController', 
controllerAs: 'vm' 
 }
/*resolve: {
setParent: function(mainService,enumFieldService){
if (mainService.parentService!=null)
{
mainService.parentService.resetSearchBean();
mainService.parentService.setSelectedEntity(null);
mainService.parentService.selectedEntity.show=false;
mainService.parentService.setEntityList(null);
}
mainService.parentEntity="EnumField";
mainService.parentService=enumFieldService;
mainService.parentService.initAnnotationList().then(function(response) {
mainService.parentService.childrenList.annotationList=response.data;
});
mainService.parentService.initTabList().then(function(response) {
mainService.parentService.childrenList.tabList=response.data;
});
mainService.parentService.initEnumEntityList().then(function(response) {
mainService.parentService.childrenList.enumEntityList=response.data;
});
mainService.parentService.initEntityList().then(function(response) {
mainService.parentService.childrenList.entityList=response.data;
});
}
*/}
})
.state('main.enumValue',{
 url:'/enumValue',
views:{
'pageContent': {
templateUrl:'app/components/enumValue/enumValue.html',
 controller:'EnumValueController', 
controllerAs: 'vm' 
 }
/*resolve: {
setParent: function(mainService,enumValueService){
if (mainService.parentService!=null)
{
mainService.parentService.resetSearchBean();
mainService.parentService.setSelectedEntity(null);
mainService.parentService.selectedEntity.show=false;
mainService.parentService.setEntityList(null);
}
mainService.parentEntity="EnumValue";
mainService.parentService=enumValueService;
mainService.parentService.initEnumEntityList().then(function(response) {
mainService.parentService.childrenList.enumEntityList=response.data;
});
}
*/}
})
.state('main.field',{
 url:'/field',
views:{
'pageContent': {
templateUrl:'app/components/field/field.html',
 controller:'FieldController', 
controllerAs: 'vm' 
 }
/*resolve: {
setParent: function(mainService,fieldService){
if (mainService.parentService!=null)
{
mainService.parentService.resetSearchBean();
mainService.parentService.setSelectedEntity(null);
mainService.parentService.selectedEntity.show=false;
mainService.parentService.setEntityList(null);
}
mainService.parentEntity="Field";
mainService.parentService=fieldService;
mainService.parentService.initAnnotationList().then(function(response) {
mainService.parentService.childrenList.annotationList=response.data;
});
mainService.parentService.initRestrictionFieldList().then(function(response) {
mainService.parentService.childrenList.restrictionFieldList=response.data;
});
mainService.parentService.initTabList().then(function(response) {
mainService.parentService.childrenList.tabList=response.data;
});
mainService.parentService.initEntityList().then(function(response) {
mainService.parentService.childrenList.entityList=response.data;
});
}
*/}
})
.state('main.annotationAttribute',{
 url:'/annotationAttribute',
views:{
'pageContent': {
templateUrl:'app/components/annotationAttribute/annotationAttribute.html',
 controller:'AnnotationAttributeController', 
controllerAs: 'vm' 
 }
/*resolve: {
setParent: function(mainService,annotationAttributeService){
if (mainService.parentService!=null)
{
mainService.parentService.resetSearchBean();
mainService.parentService.setSelectedEntity(null);
mainService.parentService.selectedEntity.show=false;
mainService.parentService.setEntityList(null);
}
mainService.parentEntity="AnnotationAttribute";
mainService.parentService=annotationAttributeService;
mainService.parentService.initAnnotationList().then(function(response) {
mainService.parentService.childrenList.annotationList=response.data;
});
}
*/}
})
.state('main.relationship',{
 url:'/relationship',
views:{
'pageContent': {
templateUrl:'app/components/relationship/relationship.html',
 controller:'RelationshipController', 
controllerAs: 'vm' 
 }
/*resolve: {
setParent: function(mainService,relationshipService){
if (mainService.parentService!=null)
{
mainService.parentService.resetSearchBean();
mainService.parentService.setSelectedEntity(null);
mainService.parentService.selectedEntity.show=false;
mainService.parentService.setEntityList(null);
}
mainService.parentEntity="Relationship";
mainService.parentService=relationshipService;
mainService.parentService.initEntityList().then(function(response) {
mainService.parentService.childrenList.entityList=response.data;
});
mainService.parentService.initEntityList().then(function(response) {
mainService.parentService.childrenList.entityList=response.data;
});
mainService.parentService.initTabList().then(function(response) {
mainService.parentService.childrenList.tabList=response.data;
});
mainService.parentService.initAnnotationList().then(function(response) {
mainService.parentService.childrenList.annotationList=response.data;
});
}
*/}
})
.state('main.logEntry',{
 url:'/logEntry',
views:{
'pageContent': {
templateUrl:'app/components/logEntry/logEntry.html',
 controller:'LogEntryController', 
controllerAs: 'vm' 
 }
/*resolve: {
setParent: function(mainService,logEntryService){
if (mainService.parentService!=null)
{
mainService.parentService.resetSearchBean();
mainService.parentService.setSelectedEntity(null);
mainService.parentService.selectedEntity.show=false;
mainService.parentService.setEntityList(null);
}
mainService.parentEntity="LogEntry";
mainService.parentService=logEntryService;
}
*/}
})
;$urlRouterProvider.otherwise('/app/home');
}
})();
