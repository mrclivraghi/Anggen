(function() { 
'use strict'; 

angular.module("serverTestApp").service("SecurityService",SecurityService);
/** @ngInject */
function SecurityService($http)
{
	this.restrictionList={};
	
	this.isLoggedIn = function()
	{
		var promise= $http.get("http://localhost:8080/ServerTestApp/authentication/username/",this.searchBean);
		return promise;
	}
	
	this.login= function(username,password)
	{
		var promise=$http.post("http://localhost:8080/ServerTestApp/authentication/username/",{username,password});
		return promise;
	}
	
}
})();
