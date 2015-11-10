angular.module('login', [ ]).controller('loginController',

		  function($rootScope, $scope, $http, $location) {

		  var authenticate = function(credentials, callback) {

		    var headers = credentials ? {authorization : "Basic "
		        + btoa(credentials.username + ":" + credentials.password)
		    } : {};

		    $http.post('../login', credentials).success(function(data) {
		      if (data.name) {
		        $rootScope.authenticated = true;
		      } else {
		        $rootScope.authenticated = false;
		      }
		      callback && callback();
		    }).error(function() {
		      $rootScope.authenticated = false;
		      callback && callback();
		    });

		  }

		  authenticate();
		  $scope.credentials = {};
		  $scope.login = function() {
		      authenticate($scope.credentials, function() {
		        if ($rootScope.authenticated) {
		          $location.path("/");
		          console.log("login ok");
		          $scope.error = false;
		        } else {
		          $location.path("/login");
		          $scope.error = true;
		          console.log("login error");
		        }
		      });
		  };
		});
