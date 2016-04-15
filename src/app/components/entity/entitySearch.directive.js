(function() {
  'use strict';

  angular
    .module('serverTestApp')
    .directive('entitySearch', entitySearch);

  /** @ngInject */
  function entitySearch(entityService) {
    var directive = {
      restrict: 'E',
      templateUrl: 'app/components/entity/entity-search.html',
      scope: {
          creationDate: '='
      },
      controller: 'EntityController',
      controllerAs: 'vm',
      bindToController: true
    };

    return directive;

  }

})();
