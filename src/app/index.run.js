(function() {
  'use strict'

  angular
    .module('serverTestApp')
    .run(runBlock);

  /** @ngInject */
  function runBlock($log,SecurityService,$rootScope,$uibModal,roleService,restrictionFieldService,userService,restrictionEntityGroupService,restrictionEntityService,logEntryService,entityService,enumEntityService,entityGroupService,tabService,projectService,annotationService,enumValueService,annotationAttributeService,fieldService,enumFieldService,relationshipService) { 
var deregistrationsCallbacks=[];
deregistrationsCallbacks[0] = $rootScope.$on('security:loginRequired', function(evt,args) {
showLogin();
 }); 
var loginWindow;
function showLogin(){
loginWindow = $uibModal.open({
size:'md',
animation: true,
 templateUrl:'app/components/login/login-modal.html',
 backdrop: 'static',
 keyboard: false
});
function close(){
initList();
SecurityService.init().then(function successCallback(response) {
$rootScope.restrictionList=response.data;
});
if(loginWindow){
loginWindow.dismiss();
loginWindow = null;
}
}           
deregistrationsCallbacks[1] = $rootScope.$on('security:loggedIn',close);
}
//deregistration of events on $destroy
deregistrationsCallbacks[2] = $rootScope.$on('$destroy', function(evt,args) {
 $log.debug("deregistering global events");
for(var i=0;i<deregistrationsCallbacks.length;i++){
  deregistrationsCallbacks[i]();
 }
 });
$log.debug("check login");
SecurityService.isLoggedIn().then(function successCallback(response) {
if (!response.data.authenticated)
{
$rootScope.$broadcast('security:loginRequired');
}
else
{
initList();
$log.debug("loggato come ");
$log.debug(response.data.message);
SecurityService.init().then(function successCallback(response) {
$rootScope.restrictionList=response.data;
});
}
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
$rootScope.openNode= {};
function initList() {
$log.debug("inizio init");
roleService.searchBean={};
roleService.search().then(function successCallback(response) {
roleService.preparedData.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
restrictionFieldService.searchBean={};
restrictionFieldService.search().then(function successCallback(response) {
restrictionFieldService.preparedData.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
userService.searchBean={};
userService.search().then(function successCallback(response) {
userService.preparedData.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
restrictionEntityGroupService.searchBean={};
restrictionEntityGroupService.search().then(function successCallback(response) {
restrictionEntityGroupService.preparedData.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
restrictionEntityService.searchBean={};
restrictionEntityService.search().then(function successCallback(response) {
restrictionEntityService.preparedData.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
logEntryService.searchBean={};
logEntryService.search().then(function successCallback(response) {
logEntryService.preparedData.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
entityService.searchBean={};
entityService.search().then(function successCallback(response) {
entityService.preparedData.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
enumEntityService.searchBean={};
enumEntityService.search().then(function successCallback(response) {
enumEntityService.preparedData.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
entityGroupService.searchBean={};
entityGroupService.search().then(function successCallback(response) {
entityGroupService.preparedData.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
tabService.searchBean={};
tabService.search().then(function successCallback(response) {
tabService.preparedData.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
projectService.searchBean={};
projectService.search().then(function successCallback(response) {
projectService.preparedData.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
annotationService.searchBean={};
annotationService.search().then(function successCallback(response) {
annotationService.preparedData.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
enumValueService.searchBean={};
enumValueService.search().then(function successCallback(response) {
enumValueService.preparedData.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
annotationAttributeService.searchBean={};
annotationAttributeService.search().then(function successCallback(response) {
annotationAttributeService.preparedData.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
fieldService.searchBean={};
fieldService.search().then(function successCallback(response) {
fieldService.preparedData.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
enumFieldService.searchBean={};
enumFieldService.search().then(function successCallback(response) {
enumFieldService.preparedData.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
relationshipService.searchBean={};
relationshipService.search().then(function successCallback(response) {
relationshipService.preparedData.entityList=response.data;
},function errorCallback(response) { 
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
$log.debug("fine init");
}
$log.debug('runBlock end');
}
})();
