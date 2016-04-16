(function() {
'use strict';
 angular
 .module('serverTestApp')
 .directive('annotationAttributeDetail', annotationAttributeDetail);
/** @ngInject */
  function annotationAttributeDetail(annotationAttributeService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/annotationAttribute/annotationAttribute-detail.html',
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
