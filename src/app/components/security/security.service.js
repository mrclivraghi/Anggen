(function() { 
'use strict'; 

angular.module("serverTestApp").service("SecurityService",SecurityService);
/** @ngInject */
function SecurityService($http,$log)
{
this.restrictionList={};
this.init= function() {
var promise= $http.get("http://127.0.0.1:8080/ServerTestApp/authentication/");
return promise; 
};
this.isLoggedIn = function()
{
var promise= $http.post("http://127.0.0.1:8080/ServerTestApp/authentication/username/");
return promise;
}
function serializeObj(obj) {
var result = [];
for (var property in obj)
result.push(encodeURIComponent(property) + "=" + encodeURIComponent(obj[property]));
return result.join("&");
}
this.login= function(username,password)
{
var credentials={};
credentials.username=username;
credentials.password=password;
credentials=serializeObj(credentials);
$log.debug(credentials);
var promise=$http.post("http://127.0.0.1:8080/ServerTestApp/auth/authenticate/",credentials,{ headers: {'Content-Type': 'application/x-www-form-urlencoded' }});
return promise;
}
}
})();
