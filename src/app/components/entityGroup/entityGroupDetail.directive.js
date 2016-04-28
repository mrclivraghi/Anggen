(function() {
'use strict';
 angular
 .module('serverTestApp')
 .directive('entityGroupDetail', entityGroupDetail);
/** @ngInject */
  function entityGroupDetail(entityGroupService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/entityGroup/entityGroup-detail.html',
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
