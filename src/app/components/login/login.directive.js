(function() { 
'use strict'; 

angular.module("serverTestApp").directive("login",login);

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
  function LoginController($scope,SecurityService,$rootScope,$log) {
   var vm = this;
   function doLogin(username,password){
     SecurityService.login(username,password).then(function successCallback(response) {
				if (response.data.authenticated)
				{
					$log.debug("login ok, chiudo");
					$rootScope.$broadcast('security:loggedIn');
				} 
			},function errorCallback(response) { 
				console.log("errore callback");
				console.log(response);
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
//AlertError.init({selector: "#alertError"});
//AlertError.show("Si è verificato un errore");
//return; 
});
}
 vm.onSubmit = doLogin;
vm.checkUsername=checkUsername;
 }
};
})();
