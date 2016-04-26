(function() { 

angular
.module("serverTestApp")
.controller("SwaggerController",SwaggerController);
/** @ngInject */
function SwaggerController($scope,$http,$rootScope,$log,UtilityService,SwaggerService)
{
	var vm = this;
	vm.swaggerObject=SwaggerService.swaggerObject;
	vm.url="http://127.0.0.1:8080/ServerTestApp/v2/api-docs/";
	function getJson()
	{
		SwaggerService.getSwaggerJson().then(function successCallback(response) {
			UtilityService.cloneObject(response.data,SwaggerService.swaggerObject);
			$log.debug(response.data);
			},function errorCallback(response) { 
					UtilityService.AlertError.init({selector: "#alertError"});
					UtilityService.AlertError.show("Si è verificato un errore");
					$log.debug(response);
					return; 
			});
	}
	
	getJson();
	
	vm.getJson=getJson;
	}
	
})();
