(function() {
'use strict';
 angular
 .module('serverTest')
 .directive('generationRunDetail', generationRunDetail);
/** @ngInject */
  function generationRunDetail(generationRunService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/generationRun/generationRun-detail.html',
  scope: {
  fields: '='
 },
controller: 'GenerationRunController',
controllerAs: 'vm',
bindToController: true,
 link: function(scope,element,attributes) {
if (attributes.fields)
generationRunService.hidden.hiddenFields=attributes.fields.split(";");
 }
};
return directive;
 }
})();
