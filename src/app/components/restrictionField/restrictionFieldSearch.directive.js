(function() {
'use strict';
 angular
 .module('serverTestApp')
 .directive('restrictionFieldSearch', restrictionFieldSearch);
/** @ngInject */
  function restrictionFieldSearch(restrictionFieldService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/restrictionField/restrictionField-search.html',
  scope: {
  fields: '='
 },
controller: 'RestrictionFieldController',
controllerAs: 'vm',
bindToController: true,
 link: function(scope,element,attributes) {
if (attributes.fields)
restrictionFieldService.hidden.hiddenFields=attributes.fields.split(";");
 }
};
return directive;
 }
})();
