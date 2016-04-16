(function() {
'use strict';
 angular
 .module('serverTestApp')
 .directive('roleDetail', roleDetail);
/** @ngInject */
  function roleDetail(roleService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/role/role-detail.html',
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
