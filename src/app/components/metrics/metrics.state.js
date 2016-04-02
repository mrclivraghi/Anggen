(function() {
    'use strict';

    angular.module("serverTestApp")
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
                    templateUrl: 'app/components/metrics/metrics.html',
                    controller: 'MetricsMonitoringController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
