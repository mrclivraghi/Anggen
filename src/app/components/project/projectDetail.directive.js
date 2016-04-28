(function() {
'use strict';
 angular
 .module('serverTestApp')
 .directive('projectDetail', projectDetail);
/** @ngInject */
  function projectDetail(projectService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/project/project-detail.html',
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
