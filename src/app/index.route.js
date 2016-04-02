(function() {
	'use strict';

	angular
	.module('serverTestApp')
	.config(routerConfig);

	/** @ngInject */
	function routerConfig($stateProvider/*, $urlRouterProvider*/) {
			$stateProvider
			.state('main',{
          url:'/app',
          abstract:true,
          templateUrl:'app/main/main.html',
          controller:'MainController',     
          controllerAs: 'main' ,
			name: 'main'
      })
      .state('main.home', {
        url: '/home',
          views:{
              'pageContent':{
                  templateUrl: 'app/components/home/home.html',
                  controller: 'HomeController',
                  controllerAs: 'vm'
              }
          }
      })
	   .state('main.entity', {
        url: '/entity',
          data:{
              authorization:true
          },
          views:{
              'pageContent':{
                  templateUrl: 'app/components/entity/entity.html',
                  controller: 'EntityController',
                  controllerAs: 'vm'
              }
          }
         
      })
	  
	  ;
			//$urlRouterProvider.otherwise('/app/home');
		}
		
})();


