(function() {
'use strict';
 angular
 .module('serverTestApp')
 .directive('annotationDetail', annotationDetail);
/** @ngInject */
  function annotationDetail(annotationService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/annotation/annotation-detail.html',
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
