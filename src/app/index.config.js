(function() {
  'use strict';

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
  $httpProvider.defaults.headers.common = {};
  $httpProvider.defaults.headers.post = {};
  $httpProvider.defaults.headers.put = {};
  $httpProvider.defaults.headers.patch = {};
   $httpProvider.defaults.headers.get = {};
  $httpProvider.defaults.useXDomain = true;
delete $httpProvider.defaults.headers.common['X-Requested-With'];
  $httpProvider.defaults.headers.common.Accept = 'application/json';
       // $httpProvider.defaults.headers.common['X-Ebsn-Client']="site";
       // $httpProvider.defaults.headers.common['X-Ebsn-Client-Version'] = '0.0.1';
        $httpProvider.defaults.cache=true;
        $httpProvider.defaults.withCredentials = true;
        $httpProvider.defaults.headers.post['Content-Type'] = 'application/json; charset=UTF-8';
		//$httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
  
  
/* $httpProvider.defaults.headers.common = {};
   $httpProvider.defaults.headers.common.Accept = 'application/json';
 // $httpProvider.defaults.headers.post = {};
   $httpProvider.defaults.headers.common["Content-Type"] = 'application/json;charset=UTF-8';
   $httpProvider.defaults.useXDomain = true;
delete $httpProvider.defaults.headers.common['X-Requested-With'];
  */ 
  //$httpProvider.defaults.headers.put = {};
  //$httpProvider.defaults.headers.patch = {};
}
  
  

})();
