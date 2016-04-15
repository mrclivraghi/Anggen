(function() {
  'use strict';

  angular
    .module('serverTestApp')
    .directive('entityDetail', entityDetail);

  /** @ngInject */
  function entityDetail(entityService) {
    var directive = {
      restrict: 'E',
      templateUrl: 'app/components/entity/entity-detail.html',
      scope: {
          fields: '='
      },
      controller: 'EntityController',
      controllerAs: 'vm',
      bindToController: true,
	  link: function(scope,element,attributes) {
	  
		console.log(attributes.fields);
		entityService.search();
	  }
    };

    return directive;

  }

})();
