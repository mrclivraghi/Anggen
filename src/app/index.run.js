(function() {
  'use strict'

  angular
    .module('serverTestApp')
    .run(runBlock);

  /** @ngInject */
  function runBlock($log,SecurityService,$rootScope) { 
SecurityService.restrictionList={};
$rootScope.restrictionList={};
$log.debug('runBlock end');
}
})();
