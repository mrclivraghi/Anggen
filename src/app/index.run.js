(function() {
  'use strict'

  angular
    .module('serverTestApp')
    .run(runBlock);

  /** @ngInject */
  function runBlock($log,SecurityService,$rootScope,$uibModal) { 
$rootScope.restrictionList={};
var deregistrationsCallbacks=[];



deregistrationsCallbacks[0] = $rootScope.$on('security:loginRequired', function(evt,args) {
        showLogin();
    }); 

 var loginWindow;
    function showLogin(){
            loginWindow = $uibModal.open({
              size:'md',
              animation: true,
              templateUrl:'app/components/login/login-modal.html'
            });
            function close(){
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
	
		if (response.data==undefined || response.data=="")
		{

			$rootScope.$broadcast('security:loginRequired');
			}
			else
			$log.debug("loggato come "+response.data);
		
},function errorCallback(response) { 
	AlertError.init({selector: "#alertError"});
	AlertError.show("Si è verificato un errore");
	return; 
});
	
	
$log.debug('runBlock end');
}
})();
