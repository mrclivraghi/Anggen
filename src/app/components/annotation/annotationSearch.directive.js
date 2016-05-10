(function() {
'use strict';
 angular
 .module('serverTest')
 .directive('annotationSearch', annotationSearch);
/** @ngInject */
  function annotationSearch(annotationService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/annotation/annotation-search.html',
  scope: {
  fields: '='
 },
controller: 'AnnotationController',
controllerAs: 'vm',
bindToController: true,
 link: function(scope,element,attributes) {
if (attributes.fields)
annotationService.hidden.hiddenFields=attributes.fields.split(";");
 }
};
return directive;
 }
})();
