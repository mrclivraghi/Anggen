(function() {
'use strict';
 angular
 .module('serverTest')
 .directive('entitySearch', entitySearch);
/** @ngInject */
  function entitySearch(entityService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/entity/entity-search.html',
  scope: {
  fields: '='
 },
controller: 'EntityController',
controllerAs: 'vm',
bindToController: true,
 link: function(scope,element,attributes) {
if (attributes.fields)
entityService.hidden.hiddenFields=attributes.fields.split(";");
 }
};
return directive;
 }
})();
