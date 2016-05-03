(function() {
'use strict';
 angular
 .module('serverTest')
 .directive('userSearch', userSearch);
/** @ngInject */
  function userSearch(userService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/user/user-search.html',
  scope: {
  fields: '='
 },
controller: 'UserController',
controllerAs: 'vm',
bindToController: true,
 link: function(scope,element,attributes) {
if (attributes.fields)
userService.hidden.hiddenFields=attributes.fields.split(";");
 }
};
return directive;
 }
})();
