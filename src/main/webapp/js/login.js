angular.module('login', [ ])
.service("loginService",function($http) {

	this.credentials={};
	
	this.login = function() {
			var promise=$http.post("../login",this.credentials);
			return promise;


	}
})
.controller("loginController",

		function($scope, $http,loginService) {

			$scope.credentials=loginService.credentials;

			$scope.login = function() {
				loginService.login().then(function (response) {console.log(response);})
				.catch(function(response){ console.log(response); });
			};

			$scope.logout = function() {
				$http.post('logout', {}).success(function() {
					$rootScope.authenticated = false;
					$location.path("/");
				}).error(function(data) {
					console.log("Logout failed")
					$rootScope.authenticated = false;
				});
			}

		});
