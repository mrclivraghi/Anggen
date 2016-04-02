(function() { 
'use strict'; 

angular.module("serverTestApp").service("SecurityService",SecurityService);
/** @ngInject */
function SecurityService($http)
{
this.restrictionList;
}
})();
