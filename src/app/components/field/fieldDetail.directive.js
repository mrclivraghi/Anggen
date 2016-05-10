(function() {
'use strict';
 angular
 .module('serverTest')
 .directive('fieldDetail', fieldDetail);
/** @ngInject */
  function fieldDetail(fieldService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/field/field-detail.html',
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
