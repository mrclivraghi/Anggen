(function() {
'use strict';
 angular
 .module('serverTest')
 .directive('annotationAttributeSearch', annotationAttributeSearch);
/** @ngInject */
  function annotationAttributeSearch(annotationAttributeService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/annotationAttribute/annotationAttribute-search.html',
  scope: {
  fields: '='
 },
controller: 'AnnotationAttributeController',
controllerAs: 'vm',
bindToController: true,
 link: function(scope,element,attributes) {
if (attributes.fields)
annotationAttributeService.hidden.hiddenFields=attributes.fields.split(";");
 }
};
return directive;
 }
})();
