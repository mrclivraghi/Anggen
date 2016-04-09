(function() {
  'use strict';

  angular
    .module('serverTestApp')
    .controller('HomeController', HomeController);

  /** @ngInject */
  function HomeController($element,$timeout,SecurityService,$log,$rootScope,$resource) {
    var vm = this;

	function checkUsername()
	{
			var Username= $resource("http://127.0.0.1:8080/ServerTestApp/authentication/username/");
			var result= Username.save({},function(data){
                $log.debug(data);
            });
	}
	
	
	vm.checkUsername=checkUsername;
  }
})();
