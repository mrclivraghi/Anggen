(function() {
  'use strict';
/*
  angular
    .module('serverTestApp')
	.factory('sessionInjector', ['SessionService', function(SessionService) {  
    var sessionInjector = {
        request: function(config) {
            if (!SessionService.isAnonymus) {
                config.headers['x-session-token'] = SessionService.token;
            }
            return config;
        }
    };
    return sessionInjector;
}]);*/
angular.
module('serverTestApp')
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
        //$httpProvider.defaults.headers.common['X-Ebsn-Client']="site";
        //$httpProvider.defaults.headers.common['X-Ebsn-Client-Version'] = '0.0.1';
        //$httpProvider.defaults.cache=true;
        $httpProvider.defaults.withCredentials = true;
        //$httpProvider.defaults.headers.post['Content-Type'] = 'application/json; charset=UTF-8';
		//$httpProvider.defaults.useXDomain = true;
        //delete $httpProvider.defaults.headers.common['X-Requested-With'];
       /* $httpProvider.defaults.transformRequest = function(data) {
            if (angular.isUndefined(data)) {
                return data;
            }
            var str = [];
            for(var p in data)
            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(data[p]));
            return str.join("&");
        };
        
		
		/*$httpProvider.interceptors.push(function($q,$rootScope,$log,$injector,$window) {
        
            return {
              request: function(config) {
                  return config;
              },
              response: function(response) {
                return response;
              },
              responseError: function(response) {
                  
                }
            }
          });*/
}
  
  

})();
