(function() {
'use strict';
 angular
 .module('serverTest')
 .directive('restrictionEntityDetail', restrictionEntityDetail);
/** @ngInject */
  function restrictionEntityDetail(restrictionEntityService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/restrictionEntity/restrictionEntity-detail.html',
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
