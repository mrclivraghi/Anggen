(function() {
    'use strict';

    angular.module("anggenApp")
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('jhi-metrics', {
            url: '/metrics',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'Application Metrics'
            },
            views: {
                'content@': {
                    templateUrl: 'js/metrics/metrics.html',
                    controller: 'MetricsMonitoringController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
