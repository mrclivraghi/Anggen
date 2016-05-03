(function() {
'use strict';
 angular
 .module('serverTest')
 .directive('entityDetail', entityDetail);
/** @ngInject */
  function entityDetail(entityService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/entity/entity-detail.html',
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
