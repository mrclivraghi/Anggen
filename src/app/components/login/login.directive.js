(function() {
  'use strict';

  angular
    .module('serverTestApp')
    .directive('login', login);

  /** @ngInject */
  function login() {
    var directive = {
      restrict: 'EA',
      templateUrl:'app/components/login/login.html',
      controller: LoginController,
      controllerAs: 'vm'
    };

    return directive;

    /** @ngInject */
    function LoginController(SecurityService) {
      var vm = this;
      
      function doLogin(username,password){
        SecurityService.login(username,password).then(function(data){
                //cartController.cart=data.data;
        });
      }
      
      vm.onSubmit = doLogin;
      
    }

  }

})();
