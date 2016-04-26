(function() { 

angular
.module("serverTestApp")
.service("SwaggerService",SwaggerService);
/** @ngInject */
function SwaggerService($http,$log)
{
	this.swaggerObject={};
	
	this.getSwaggerJson = function()
	{
		var promise= $http.get("http://127.0.0.1:8080/ServerTestApp/v2/api-docs/");
		return promise; 
	}
	
	
}
})();
