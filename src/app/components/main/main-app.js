angular.module("anggenApp",['ui.bootstrap','ngFileUpload','ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter']);

angular.module("serverTestApp").service("securityService",function($http)
{
this.restrictionList;
this.init= function() {
var promise= $http.get("./authentication/");
return promise; 
};
})
.run(function($rootScope,securityService){
securityService.init().then(function successCallback(response) {
securityService.restrictionList=response.data;
$rootScope.restrictionList=response.data;
});
});
angular.module("serverTestApp").service("mainService", function()
{
this.parentEntity="";
 this.parentService=null; 
});/*
angular.module("serverTestApp").controller("MainController",function($scope, $route, $routeParams, $location,mainService)
{
$scope.$route = $route;
$scope.$location = $location;
$scope.$routeParams = $routeParams;
});*/

