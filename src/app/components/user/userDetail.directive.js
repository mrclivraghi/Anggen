(function() {
'use strict';
 angular
 .module('serverTestApp')
 .directive('userDetail', userDetail);
/** @ngInject */
  function userDetail(userService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/user/user-detail.html',
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
