angular.module("anggenApp",['ngRoute','ui.bootstrap','ngFileUpload','ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter']);angular.module("anggenApp").service("securityService",function($http)
{
this.restrictionList;
this.init= function() {
var promise= $http.get("./authentication/");
return promise; 
};
})
.run(function($rootScope,securityService){
securityService.init().then(function successCallback(response) {
securityService.restrictionList=response.data;
$rootScope.restrictionList=response.data;
});
});
angular.module("anggenApp").service("mainService", function()
{
this.parentEntity="";
 this.parentService=null; 
});
angular.module("anggenApp").controller("MainController",function($scope, $route, $routeParams, $location,mainService)
{
$scope.$route = $route;
$scope.$location = $location;
$scope.$routeParams = $routeParams;
});
angular.module("anggenApp").config(function($routeProvider, $locationProvider) 
{
$routeProvider
.when('/',{
templateUrl:'./home/'
})
.when('/home/',{
templateUrl:'./home/'
})
.when('/metrics/',{
 templateUrl: 'js/metrics/metrics.html',
controller: 'MetricsMonitoringController',
controllerAs: 'vm'
})
.when('/AnnotationAttribute/',{
templateUrl: './annotationAttribute/',
controller:'annotationAttributeController',
resolve: {
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
}
})
.when('/Annotation/',{
templateUrl: './annotation/',
controller:'annotationController',
resolve: {
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
}
})
.when('/Field/',{
templateUrl: './field/',
controller:'fieldController',
resolve: {
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
}
})
.when('/EnumValue/',{
templateUrl: './enumValue/',
controller:'enumValueController',
resolve: {
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
}
})
.when('/EnumField/',{
templateUrl: './enumField/',
controller:'enumFieldController',
resolve: {
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
}
})
.when('/Tab/',{
templateUrl: './tab/',
controller:'tabController',
resolve: {
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
mainService.parentService.initFieldList().then(function(response) {
mainService.parentService.childrenList.fieldList=response.data;
});
mainService.parentService.initEnumFieldList().then(function(response) {
mainService.parentService.childrenList.enumFieldList=response.data;
});
mainService.parentService.initRelationshipList().then(function(response) {
mainService.parentService.childrenList.relationshipList=response.data;
});
mainService.parentService.initEntityList().then(function(response) {
mainService.parentService.childrenList.entityList=response.data;
});
}
}
})
.when('/EntityGroup/',{
templateUrl: './entityGroup/',
controller:'entityGroupController',
resolve: {
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
mainService.parentService.initRestrictionEntityGroupList().then(function(response) {
mainService.parentService.childrenList.restrictionEntityGroupList=response.data;
});
mainService.parentService.initProjectList().then(function(response) {
mainService.parentService.childrenList.projectList=response.data;
});
mainService.parentService.initEntityList().then(function(response) {
mainService.parentService.childrenList.entityList=response.data;
});
}
}
})
.when('/Project/',{
templateUrl: './project/',
controller:'projectController',
resolve: {
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
}
})
.when('/EnumEntity/',{
templateUrl: './enumEntity/',
controller:'enumEntityController',
resolve: {
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
}
})
.when('/Entity/',{
templateUrl: './entity/',
controller:'entityController',
resolve: {
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
mainService.parentService.initEnumFieldList().then(function(response) {
mainService.parentService.childrenList.enumFieldList=response.data;
});
mainService.parentService.initFieldList().then(function(response) {
mainService.parentService.childrenList.fieldList=response.data;
});
mainService.parentService.initEntityGroupList().then(function(response) {
mainService.parentService.childrenList.entityGroupList=response.data;
});
mainService.parentService.initTabList().then(function(response) {
mainService.parentService.childrenList.tabList=response.data;
});
mainService.parentService.initRestrictionEntityList().then(function(response) {
mainService.parentService.childrenList.restrictionEntityList=response.data;
});
mainService.parentService.initRelationshipList().then(function(response) {
mainService.parentService.childrenList.relationshipList=response.data;
});
}
}
})
.when('/RestrictionEntityGroup/',{
templateUrl: './restrictionEntityGroup/',
controller:'restrictionEntityGroupController',
resolve: {
setParent: function(mainService,restrictionEntityGroupService){
if (mainService.parentService!=null)
{
mainService.parentService.resetSearchBean();
mainService.parentService.setSelectedEntity(null);
mainService.parentService.selectedEntity.show=false;
mainService.parentService.setEntityList(null);
}
mainService.parentEntity="RestrictionEntityGroup";
mainService.parentService=restrictionEntityGroupService;
mainService.parentService.initEntityGroupList().then(function(response) {
mainService.parentService.childrenList.entityGroupList=response.data;
});
mainService.parentService.initRoleList().then(function(response) {
mainService.parentService.childrenList.roleList=response.data;
});
}
}
})
.when('/User/',{
templateUrl: './user/',
controller:'userController',
resolve: {
setParent: function(mainService,userService){
if (mainService.parentService!=null)
{
mainService.parentService.resetSearchBean();
mainService.parentService.setSelectedEntity(null);
mainService.parentService.selectedEntity.show=false;
mainService.parentService.setEntityList(null);
}
mainService.parentEntity="User";
mainService.parentService=userService;
mainService.parentService.initRoleList().then(function(response) {
mainService.parentService.childrenList.roleList=response.data;
});
}
}
})
.when('/RestrictionField/',{
templateUrl: './restrictionField/',
controller:'restrictionFieldController',
resolve: {
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
}
})
.when('/RestrictionEntity/',{
templateUrl: './restrictionEntity/',
controller:'restrictionEntityController',
resolve: {
setParent: function(mainService,restrictionEntityService){
if (mainService.parentService!=null)
{
mainService.parentService.resetSearchBean();
mainService.parentService.setSelectedEntity(null);
mainService.parentService.selectedEntity.show=false;
mainService.parentService.setEntityList(null);
}
mainService.parentEntity="RestrictionEntity";
mainService.parentService=restrictionEntityService;
mainService.parentService.initEntityList().then(function(response) {
mainService.parentService.childrenList.entityList=response.data;
});
mainService.parentService.initRoleList().then(function(response) {
mainService.parentService.childrenList.roleList=response.data;
});
}
}
})
.when('/Role/',{
templateUrl: './role/',
controller:'roleController',
resolve: {
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
mainService.parentService.initRestrictionEntityList().then(function(response) {
mainService.parentService.childrenList.restrictionEntityList=response.data;
});
mainService.parentService.initRestrictionFieldList().then(function(response) {
mainService.parentService.childrenList.restrictionFieldList=response.data;
});
mainService.parentService.initUserList().then(function(response) {
mainService.parentService.childrenList.userList=response.data;
});
mainService.parentService.initRestrictionEntityGroupList().then(function(response) {
mainService.parentService.childrenList.restrictionEntityGroupList=response.data;
});
}
}
})
.when('/Relationship/',{
templateUrl: './relationship/',
controller:'relationshipController',
resolve: {
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
}
})
.when('/LogEntry/',{
templateUrl: './logEntry/',
controller:'logEntryController',
resolve: {
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
mainService.parentService.initEntityList().then(function(response) {
mainService.parentService.childrenList.entityList=response.data;
});
mainService.parentService.initUserList().then(function(response) {
mainService.parentService.childrenList.userList=response.data;
});
}
}
})
;$locationProvider.html5Mode(true);
});
