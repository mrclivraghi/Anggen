(function() {
'use strict';
 angular
 .module('serverTestApp')
 .directive('enumFieldDetail', enumFieldDetail);
/** @ngInject */
  function enumFieldDetail(enumFieldService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/enumField/enumField-detail.html',
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
