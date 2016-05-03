(function() {
'use strict';
 angular
 .module('serverTest')
 .directive('enumFieldSearch', enumFieldSearch);
/** @ngInject */
  function enumFieldSearch(enumFieldService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/enumField/enumField-search.html',
  scope: {
  fields: '='
 },
controller: 'EnumFieldController',
controllerAs: 'vm',
bindToController: true,
 link: function(scope,element,attributes) {
if (attributes.fields)
enumFieldService.hidden.hiddenFields=attributes.fields.split(";");
 }
};
return directive;
 }
})();
