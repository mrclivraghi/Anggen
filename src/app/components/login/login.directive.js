(function() { 
'use strict'; 

angular.module("serverTest").directive("login",login);

/** @ngInject */
function login(){
  var directive = {
    restrict: 'EA',
   templateUrl:'app/components/login/login.html',
   controller: LoginController,
  controllerAs: 'vm'
};
 return directive;
  /** @ngInject */
  function LoginController($scope,SecurityService,UtilityService,$rootScope,$log) {
   var vm = this;
   function doLogin(username,password){
     SecurityService.login(username,password).then(function successCallback(response) {
				if (response.data.authenticated)
				{
					$log.debug("login ok, chiudo");
					$rootScope.$broadcast('security:loggedIn');
				} 
			},function errorCallback(response) { 
				$log.debug("errore callback");
				$log.debug(response);
			});
}
function checkUsername()
{
	SecurityService.isLoggedIn(vm).then(function successCallback(response) {
	if (!response.data.authenticated)
	{
		$rootScope.$broadcast('security:loginRequired');
		}
else
{
$rootScope.$broadcast('security:loggedIn');
	}
},function errorCallback(response) { 
UtilityService.AlertError.init({selector: "#alertError"});
UtilityService.AlertError.show("Si è verificato un errore");
$log.debug(response);
return; 
});
}
 vm.onSubmit = doLogin;
vm.checkUsername=checkUsername;
 }
}
})();
