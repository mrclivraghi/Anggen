(function() {
'use strict';
 angular
 .module('serverTest')
 .directive('restrictionEntityGroupDetail', restrictionEntityGroupDetail);
/** @ngInject */
  function restrictionEntityGroupDetail(restrictionEntityGroupService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/restrictionEntityGroup/restrictionEntityGroup-detail.html',
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
