(function() {
  'use strict'

  angular
.module('serverTestApp')
.directive('serverTestNavbar', serverTestNavbar);
/** @ngInject */
function serverTestNavbar() {
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
  function NavbarController($scope,$http,$log) {
    var vm = this;
  function doLogout()
  {
  $http.post("http://127.0.0.1:8080/ServerTestApp/auth/logout/").then(function(response)
{
$log.debug(response);
		$log.debug("logout");
  });
  }
  vm.doLogout=doLogout;
 }
 }
})();
