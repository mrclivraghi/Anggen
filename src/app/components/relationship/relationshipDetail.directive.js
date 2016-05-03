(function() {
'use strict';
 angular
 .module('serverTest')
 .directive('relationshipDetail', relationshipDetail);
/** @ngInject */
  function relationshipDetail(relationshipService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/relationship/relationship-detail.html',
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
