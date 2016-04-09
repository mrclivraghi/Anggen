(function() {
  'use strict';

  angular
    .module('serverTestApp')
    .directive('anggenNavbar', anggenNavbar);

  /** @ngInject */
  function anggenNavbar() {
    var directive = {
      restrict: 'E',
      templateUrl: 'app/components/navbar/navbar.html',
      scope: {
          creationDate: '='
      },
      controller: NavbarController,
      controllerAs: 'vm',
      bindToController: true
    };

    return directive;

    /** @ngInject */
    function NavbarController($scope,$http,$log,moment) {
      var vm = this;

      // "vm.creationDate" is available by directive option "bindToController: true"
      vm.relativeDate = moment(vm.creationDate).fromNow();
	  $scope.restrictionList={};
	  
	  function doLogout()
	  {
	  
	  $http.post("http://localhost:8080/ServerTestApp/auth/logout/").then(function(response)
	  {
			$log.debug("logout");
	  
	  });
	  
	  
	  }
	  
	  vm.doLogout=doLogout;
	  
	  
	  
    }
  }

})();
