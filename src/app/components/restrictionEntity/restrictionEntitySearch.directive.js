(function() {
'use strict';
 angular
 .module('serverTestApp')
 .directive('restrictionEntitySearch', restrictionEntitySearch);
/** @ngInject */
  function restrictionEntitySearch(restrictionEntityService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/restrictionEntity/restrictionEntity-search.html',
  scope: {
  fields: '='
 },
controller: 'RestrictionEntityController',
controllerAs: 'vm',
bindToController: true,
 link: function(scope,element,attributes) {
if (attributes.fields)
restrictionEntityService.hidden.hiddenFields=attributes.fields.split(";");
 }
};
return directive;
 }
})();
