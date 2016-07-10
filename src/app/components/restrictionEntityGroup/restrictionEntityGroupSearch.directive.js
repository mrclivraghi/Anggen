(function() {
'use strict';
 angular
 .module('serverTestApp')
 .directive('restrictionEntityGroupSearch', restrictionEntityGroupSearch);
/** @ngInject */
  function restrictionEntityGroupSearch(restrictionEntityGroupService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/restrictionEntityGroup/restrictionEntityGroup-search.html',
  scope: {
  fields: '='
 },
controller: 'RestrictionEntityGroupController',
controllerAs: 'vm',
bindToController: true,
 link: function(scope,element,attributes) {
if (attributes.fields)
restrictionEntityGroupService.hidden.hiddenFields=attributes.fields.split(";");
 }
};
return directive;
 }
})();
