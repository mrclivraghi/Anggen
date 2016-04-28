(function() {
    'use strict';

    angular
        .module('test')
        .factory('MetricsService', MetricsService);

    MetricsService.$inject = ['$rootScope', '$http'];

    function MetricsService ($rootScope, $http) {
        var service = {
            getMetrics: getMetrics,
            threadDump: threadDump
        };

        return service;

        function getMetrics () {
            return $http.get('http://127.0.0.1:8080/ServerTestApp/metrics/metrics').then(function (response) {
                return response.data;
            });
        }

        function threadDump () {
            return $http.get('dump').then(function (response) {
                return response.data;
            });
        }
    }
})();
