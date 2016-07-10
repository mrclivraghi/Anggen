(function() {
'use strict';
 angular
 .module('serverTestApp')
 .directive('entityGroupSearch', entityGroupSearch);
/** @ngInject */
  function entityGroupSearch(entityGroupService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/entityGroup/entityGroup-search.html',
  scope: {
  fields: '='
 },
controller: 'EntityGroupController',
controllerAs: 'vm',
bindToController: true,
 link: function(scope,element,attributes) {
if (attributes.fields)
entityGroupService.hidden.hiddenFields=attributes.fields.split(";");
 }
};
return directive;
 }
})();
