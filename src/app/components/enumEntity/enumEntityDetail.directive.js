(function() {
'use strict';
 angular
 .module('serverTest')
 .directive('enumEntityDetail', enumEntityDetail);
/** @ngInject */
  function enumEntityDetail(enumEntityService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/enumEntity/enumEntity-detail.html',
  scope: {
  fields: '='
 },
controller: 'EnumEntityController',
controllerAs: 'vm',
bindToController: true,
 link: function(scope,element,attributes) {
if (attributes.fields)
enumEntityService.hidden.hiddenFields=attributes.fields.split(";");
 }
};
return directive;
 }
})();
