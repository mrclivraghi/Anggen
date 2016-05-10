(function() {
'use strict';
 angular
 .module('serverTest')
 .directive('relationshipSearch', relationshipSearch);
/** @ngInject */
  function relationshipSearch(relationshipService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/relationship/relationship-search.html',
  scope: {
  fields: '='
 },
controller: 'RelationshipController',
controllerAs: 'vm',
bindToController: true,
 link: function(scope,element,attributes) {
if (attributes.fields)
relationshipService.hidden.hiddenFields=attributes.fields.split(";");
 }
};
return directive;
 }
})();
