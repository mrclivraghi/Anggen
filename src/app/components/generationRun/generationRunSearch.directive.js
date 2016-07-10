(function() {
'use strict';
 angular
 .module('serverTestApp')
 .directive('generationRunSearch', generationRunSearch);
/** @ngInject */
  function generationRunSearch(generationRunService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/generationRun/generationRun-search.html',
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
