(function() {
'use strict';
 angular
 .module('serverTest')
 .directive('logEntryDetail', logEntryDetail);
/** @ngInject */
  function logEntryDetail(logEntryService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/logEntry/logEntry-detail.html',
  scope: {
  fields: '='
 },
controller: 'LogEntryController',
controllerAs: 'vm',
bindToController: true,
 link: function(scope,element,attributes) {
if (attributes.fields)
logEntryService.hidden.hiddenFields=attributes.fields.split(";");
 }
};
return directive;
 }
})();
