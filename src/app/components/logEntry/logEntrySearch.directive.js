(function() {
'use strict';
 angular
 .module('serverTestApp')
 .directive('logEntrySearch', logEntrySearch);
/** @ngInject */
  function logEntrySearch(logEntryService) {
  var directive = {
  restrict: 'E',
 templateUrl: 'app/components/logEntry/logEntry-search.html',
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
