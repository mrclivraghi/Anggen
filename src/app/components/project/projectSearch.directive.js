(function() {
'use strict';
 angular
 .module('serverTest')
 .directive('projectSearch', projectSearch);
/** @ngInject */
  function projectSearch(projectService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/project/project-search.html',
  scope: {
  fields: '='
 },
controller: 'ProjectController',
controllerAs: 'vm',
bindToController: true,
 link: function(scope,element,attributes) {
if (attributes.fields)
projectService.hidden.hiddenFields=attributes.fields.split(";");
 }
};
return directive;
 }
})();
