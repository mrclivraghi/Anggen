(function() {
  'use strict'

  angular
    .module('serverTestApp')
    .config(config)
    .config(setHttpProvider);

  /** @ngInject */
function config($logProvider, toastrConfig) {
// Enable log
 $logProvider.debugEnabled(true);
 // Set options third-party lib
 toastrConfig.allowHtml = true;
 toastrConfig.timeOut = 3000;
toastrConfig.positionClass = 'toast-top-right';
 toastrConfig.preventDuplicates = true;
toastrConfig.progressBar = true;
}

/** @ngInject */
function setHttpProvider($httpProvider) {
$httpProvider.defaults.headers.common.Accept = 'application/json';
 $httpProvider.defaults.withCredentials = true;
}
})();
