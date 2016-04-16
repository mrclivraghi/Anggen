(function() {
'use strict';
 angular
 .module('serverTestApp')
 .directive('fieldSearch', fieldSearch);
/** @ngInject */
  function fieldSearch(fieldService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/field/field-search.html',
  scope: {
  fields: '='
 },
controller: 'FieldController',
controllerAs: 'vm',
bindToController: true,
 link: function(scope,element,attributes) {
if (attributes.fields)
fieldService.hidden.hiddenFields=attributes.fields.split(";");
 }
};
return directive;
 }
})();
