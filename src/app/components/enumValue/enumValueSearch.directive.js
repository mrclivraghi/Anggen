(function() {
'use strict';
 angular
 .module('serverTestApp')
 .directive('enumValueSearch', enumValueSearch);
/** @ngInject */
  function enumValueSearch(enumValueService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/enumValue/enumValue-search.html',
  scope: {
  fields: '='
 },
controller: 'EnumValueController',
controllerAs: 'vm',
bindToController: true,
 link: function(scope,element,attributes) {
if (attributes.fields)
enumValueService.hidden.hiddenFields=attributes.fields.split(";");
 }
};
return directive;
 }
})();
