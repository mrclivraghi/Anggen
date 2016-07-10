(function() { 
'use strict'; 

angular.module("serverTestApp").service("MainService", MainService);
/** @ngInject */
function MainService()
{
this.parentEntity="";
 this.parentService=null; 
}
})();
