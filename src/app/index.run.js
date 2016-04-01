(function() {
  'use strict';

  angular
    .module('serverTestApp')
    .run(runBlock);

  /** @ngInject */
  function runBlock($log) {

    $log.debug('runBlock end');
  }

})();
