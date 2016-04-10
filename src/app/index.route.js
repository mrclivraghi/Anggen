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
.state('main.metrics',{
 url:'/metrics',
views:{
'pageContent': {
templateUrl:'app/components/metrics/metrics.html',
 controller:'MetricsController', 
controllerAs: 'vm' 
 }
}
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
setParent: function(MainService,enumValueService){
if (MainService.parentService!=null)
{
MainService.parentService.resetSearchBean();
MainService.parentService.setSelectedEntity(null);
MainService.parentService.selectedEntity.show=false;
MainService.parentService.setEntityList(null);
}
MainService.parentEntity="EnumValue";
MainService.parentService=enumValueService;
MainService.parentService.initEnumEntityList().then(function(response) {
MainService.parentService.childrenList.enumEntityList=response.data;
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
setParent: function(MainService,annotationAttributeService){
if (MainService.parentService!=null)
{
MainService.parentService.resetSearchBean();
MainService.parentService.setSelectedEntity(null);
MainService.parentService.selectedEntity.show=false;
MainService.parentService.setEntityList(null);
}
MainService.parentEntity="AnnotationAttribute";
MainService.parentService=annotationAttributeService;
MainService.parentService.initAnnotationList().then(function(response) {
MainService.parentService.childrenList.annotationList=response.data;
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
setParent: function(MainService,annotationService){
if (MainService.parentService!=null)
{
MainService.parentService.resetSearchBean();
MainService.parentService.setSelectedEntity(null);
MainService.parentService.selectedEntity.show=false;
MainService.parentService.setEntityList(null);
}
MainService.parentEntity="Annotation";
MainService.parentService=annotationService;
MainService.parentService.initAnnotationAttributeList().then(function(response) {
MainService.parentService.childrenList.annotationAttributeList=response.data;
});
MainService.parentService.initFieldList().then(function(response) {
MainService.parentService.childrenList.fieldList=response.data;
});
MainService.parentService.initEnumFieldList().then(function(response) {
MainService.parentService.childrenList.enumFieldList=response.data;
});
MainService.parentService.initRelationshipList().then(function(response) {
MainService.parentService.childrenList.relationshipList=response.data;
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
setParent: function(MainService,enumFieldService){
if (MainService.parentService!=null)
{
MainService.parentService.resetSearchBean();
MainService.parentService.setSelectedEntity(null);
MainService.parentService.selectedEntity.show=false;
MainService.parentService.setEntityList(null);
}
MainService.parentEntity="EnumField";
MainService.parentService=enumFieldService;
MainService.parentService.initAnnotationList().then(function(response) {
MainService.parentService.childrenList.annotationList=response.data;
});
MainService.parentService.initTabList().then(function(response) {
MainService.parentService.childrenList.tabList=response.data;
});
MainService.parentService.initEnumEntityList().then(function(response) {
MainService.parentService.childrenList.enumEntityList=response.data;
});
MainService.parentService.initEntityList().then(function(response) {
MainService.parentService.childrenList.entityList=response.data;
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
setParent: function(MainService,fieldService){
if (MainService.parentService!=null)
{
MainService.parentService.resetSearchBean();
MainService.parentService.setSelectedEntity(null);
MainService.parentService.selectedEntity.show=false;
MainService.parentService.setEntityList(null);
}
MainService.parentEntity="Field";
MainService.parentService=fieldService;
MainService.parentService.initAnnotationList().then(function(response) {
MainService.parentService.childrenList.annotationList=response.data;
});
MainService.parentService.initRestrictionFieldList().then(function(response) {
MainService.parentService.childrenList.restrictionFieldList=response.data;
});
MainService.parentService.initTabList().then(function(response) {
MainService.parentService.childrenList.tabList=response.data;
});
MainService.parentService.initEntityList().then(function(response) {
MainService.parentService.childrenList.entityList=response.data;
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
setParent: function(MainService,projectService){
if (MainService.parentService!=null)
{
MainService.parentService.resetSearchBean();
MainService.parentService.setSelectedEntity(null);
MainService.parentService.selectedEntity.show=false;
MainService.parentService.setEntityList(null);
}
MainService.parentEntity="Project";
MainService.parentService=projectService;
MainService.parentService.initEnumEntityList().then(function(response) {
MainService.parentService.childrenList.enumEntityList=response.data;
});
MainService.parentService.initEntityGroupList().then(function(response) {
MainService.parentService.childrenList.entityGroupList=response.data;
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
setParent: function(MainService,tabService){
if (MainService.parentService!=null)
{
MainService.parentService.resetSearchBean();
MainService.parentService.setSelectedEntity(null);
MainService.parentService.selectedEntity.show=false;
MainService.parentService.setEntityList(null);
}
MainService.parentEntity="Tab";
MainService.parentService=tabService;
MainService.parentService.initFieldList().then(function(response) {
MainService.parentService.childrenList.fieldList=response.data;
});
MainService.parentService.initEnumFieldList().then(function(response) {
MainService.parentService.childrenList.enumFieldList=response.data;
});
MainService.parentService.initRelationshipList().then(function(response) {
MainService.parentService.childrenList.relationshipList=response.data;
});
MainService.parentService.initEntityList().then(function(response) {
MainService.parentService.childrenList.entityList=response.data;
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
setParent: function(MainService,enumEntityService){
if (MainService.parentService!=null)
{
MainService.parentService.resetSearchBean();
MainService.parentService.setSelectedEntity(null);
MainService.parentService.selectedEntity.show=false;
MainService.parentService.setEntityList(null);
}
MainService.parentEntity="EnumEntity";
MainService.parentService=enumEntityService;
MainService.parentService.initProjectList().then(function(response) {
MainService.parentService.childrenList.projectList=response.data;
});
MainService.parentService.initEnumValueList().then(function(response) {
MainService.parentService.childrenList.enumValueList=response.data;
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
setParent: function(MainService,entityGroupService){
if (MainService.parentService!=null)
{
MainService.parentService.resetSearchBean();
MainService.parentService.setSelectedEntity(null);
MainService.parentService.selectedEntity.show=false;
MainService.parentService.setEntityList(null);
}
MainService.parentEntity="EntityGroup";
MainService.parentService=entityGroupService;
MainService.parentService.initRestrictionEntityGroupList().then(function(response) {
MainService.parentService.childrenList.restrictionEntityGroupList=response.data;
});
MainService.parentService.initProjectList().then(function(response) {
MainService.parentService.childrenList.projectList=response.data;
});
MainService.parentService.initEntityList().then(function(response) {
MainService.parentService.childrenList.entityList=response.data;
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
setParent: function(MainService,entityService){
if (MainService.parentService!=null)
{
MainService.parentService.resetSearchBean();
MainService.parentService.setSelectedEntity(null);
MainService.parentService.selectedEntity.show=false;
MainService.parentService.setEntityList(null);
}
MainService.parentEntity="Entity";
MainService.parentService=entityService;
MainService.parentService.initFieldList().then(function(response) {
MainService.parentService.childrenList.fieldList=response.data;
});
MainService.parentService.initEnumFieldList().then(function(response) {
MainService.parentService.childrenList.enumFieldList=response.data;
});
MainService.parentService.initTabList().then(function(response) {
MainService.parentService.childrenList.tabList=response.data;
});
MainService.parentService.initEntityGroupList().then(function(response) {
MainService.parentService.childrenList.entityGroupList=response.data;
});
MainService.parentService.initRestrictionEntityList().then(function(response) {
MainService.parentService.childrenList.restrictionEntityList=response.data;
});
MainService.parentService.initRelationshipList().then(function(response) {
MainService.parentService.childrenList.relationshipList=response.data;
});
}
*/}
})
.state('main.restrictionEntityGroup',{
 url:'/restrictionEntityGroup',
views:{
'pageContent': {
templateUrl:'app/components/restrictionEntityGroup/restrictionEntityGroup.html',
 controller:'RestrictionEntityGroupController', 
controllerAs: 'vm' 
 }
/*resolve: {
setParent: function(MainService,restrictionEntityGroupService){
if (MainService.parentService!=null)
{
MainService.parentService.resetSearchBean();
MainService.parentService.setSelectedEntity(null);
MainService.parentService.selectedEntity.show=false;
MainService.parentService.setEntityList(null);
}
MainService.parentEntity="RestrictionEntityGroup";
MainService.parentService=restrictionEntityGroupService;
MainService.parentService.initEntityGroupList().then(function(response) {
MainService.parentService.childrenList.entityGroupList=response.data;
});
MainService.parentService.initRoleList().then(function(response) {
MainService.parentService.childrenList.roleList=response.data;
});
}
*/}
})
.state('main.user',{
 url:'/user',
views:{
'pageContent': {
templateUrl:'app/components/user/user.html',
 controller:'UserController', 
controllerAs: 'vm' 
 }
/*resolve: {
setParent: function(MainService,userService){
if (MainService.parentService!=null)
{
MainService.parentService.resetSearchBean();
MainService.parentService.setSelectedEntity(null);
MainService.parentService.selectedEntity.show=false;
MainService.parentService.setEntityList(null);
}
MainService.parentEntity="User";
MainService.parentService=userService;
MainService.parentService.initRoleList().then(function(response) {
MainService.parentService.childrenList.roleList=response.data;
});
}
*/}
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
setParent: function(MainService,roleService){
if (MainService.parentService!=null)
{
MainService.parentService.resetSearchBean();
MainService.parentService.setSelectedEntity(null);
MainService.parentService.selectedEntity.show=false;
MainService.parentService.setEntityList(null);
}
MainService.parentEntity="Role";
MainService.parentService=roleService;
MainService.parentService.initRestrictionEntityList().then(function(response) {
MainService.parentService.childrenList.restrictionEntityList=response.data;
});
MainService.parentService.initRestrictionFieldList().then(function(response) {
MainService.parentService.childrenList.restrictionFieldList=response.data;
});
MainService.parentService.initUserList().then(function(response) {
MainService.parentService.childrenList.userList=response.data;
});
MainService.parentService.initRestrictionEntityGroupList().then(function(response) {
MainService.parentService.childrenList.restrictionEntityGroupList=response.data;
});
}
*/}
})
.state('main.restrictionEntity',{
 url:'/restrictionEntity',
views:{
'pageContent': {
templateUrl:'app/components/restrictionEntity/restrictionEntity.html',
 controller:'RestrictionEntityController', 
controllerAs: 'vm' 
 }
/*resolve: {
setParent: function(MainService,restrictionEntityService){
if (MainService.parentService!=null)
{
MainService.parentService.resetSearchBean();
MainService.parentService.setSelectedEntity(null);
MainService.parentService.selectedEntity.show=false;
MainService.parentService.setEntityList(null);
}
MainService.parentEntity="RestrictionEntity";
MainService.parentService=restrictionEntityService;
MainService.parentService.initEntityList().then(function(response) {
MainService.parentService.childrenList.entityList=response.data;
});
MainService.parentService.initRoleList().then(function(response) {
MainService.parentService.childrenList.roleList=response.data;
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
setParent: function(MainService,restrictionFieldService){
if (MainService.parentService!=null)
{
MainService.parentService.resetSearchBean();
MainService.parentService.setSelectedEntity(null);
MainService.parentService.selectedEntity.show=false;
MainService.parentService.setEntityList(null);
}
MainService.parentEntity="RestrictionField";
MainService.parentService=restrictionFieldService;
MainService.parentService.initRoleList().then(function(response) {
MainService.parentService.childrenList.roleList=response.data;
});
MainService.parentService.initFieldList().then(function(response) {
MainService.parentService.childrenList.fieldList=response.data;
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
setParent: function(MainService,logEntryService){
if (MainService.parentService!=null)
{
MainService.parentService.resetSearchBean();
MainService.parentService.setSelectedEntity(null);
MainService.parentService.selectedEntity.show=false;
MainService.parentService.setEntityList(null);
}
MainService.parentEntity="LogEntry";
MainService.parentService=logEntryService;
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
setParent: function(MainService,relationshipService){
if (MainService.parentService!=null)
{
MainService.parentService.resetSearchBean();
MainService.parentService.setSelectedEntity(null);
MainService.parentService.selectedEntity.show=false;
MainService.parentService.setEntityList(null);
}
MainService.parentEntity="Relationship";
MainService.parentService=relationshipService;
MainService.parentService.initEntityList().then(function(response) {
MainService.parentService.childrenList.entityList=response.data;
});
MainService.parentService.initEntityList().then(function(response) {
MainService.parentService.childrenList.entityList=response.data;
});
MainService.parentService.initTabList().then(function(response) {
MainService.parentService.childrenList.tabList=response.data;
});
MainService.parentService.initAnnotationList().then(function(response) {
MainService.parentService.childrenList.annotationList=response.data;
});
}
*/}
})
;$urlRouterProvider.otherwise('/app/home');
}
})();
