(function() {
'use strict';
 angular
 .module('serverTestApp')
 .directive('roleSearch', roleSearch);
/** @ngInject */
  function roleSearch(roleService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/role/role-search.html',
  scope: {
  fields: '='
 },
controller: 'RoleController',
controllerAs: 'vm',
bindToController: true,
 link: function(scope,element,attributes) {
if (attributes.fields)
roleService.hidden.hiddenFields=attributes.fields.split(";");
 }
};
return directive;
 }
})();
