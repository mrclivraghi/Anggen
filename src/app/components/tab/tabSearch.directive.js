(function() {
'use strict';
 angular
 .module('serverTest')
 .directive('tabSearch', tabSearch);
/** @ngInject */
  function tabSearch(tabService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/tab/tab-search.html',
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
