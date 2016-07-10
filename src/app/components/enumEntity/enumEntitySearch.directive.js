(function() {
'use strict';
 angular
 .module('serverTestApp')
 .directive('enumEntitySearch', enumEntitySearch);
/** @ngInject */
  function enumEntitySearch(enumEntityService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/enumEntity/enumEntity-search.html',
  scope: {
  fields: '='
 },
controller: 'EnumEntityController',
controllerAs: 'vm',
bindToController: true,
 link: function(scope,element,attributes) {
if (attributes.fields)
enumEntityService.hidden.hiddenFields=attributes.fields.split(";");
 }
};
return directive;
 }
})();
