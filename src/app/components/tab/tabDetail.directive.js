(function() {
'use strict';
 angular
 .module('serverTestApp')
 .directive('tabDetail', tabDetail);
/** @ngInject */
  function tabDetail(tabService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/tab/tab-detail.html',
  scope: {
  fields: '='
 },
controller: 'TabController',
controllerAs: 'vm',
bindToController: true,
 link: function(scope,element,attributes) {
if (attributes.fields)
tabService.hidden.hiddenFields=attributes.fields.split(";");
 }
};
return directive;
 }
})();
