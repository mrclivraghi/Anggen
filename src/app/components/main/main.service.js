(function() { 
'use strict'; 

angular.module("serverTest").service("MainService", MainService);
/** @ngInject */
function MainService()
{
this.parentEntity="";
 this.parentService=null; 
}
})();
