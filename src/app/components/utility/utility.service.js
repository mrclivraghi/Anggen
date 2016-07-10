(function() { 
'use strict'; 

angular.module("serverTestApp").service("UtilityService", UtilityService);
/** @ngInject */
function UtilityService()
{
this.alasql=alasql;
this.AlertSuccess = (function() {
   "use strict";
   var elem,
       hideHandler,
       that = {};
    that.init = function(options) {
        elem = angular.element(options.selector);
    };
    that.show = function(text) {
        clearTimeout(hideHandler);
        elem.find("span").html(text);
        elem.delay(200).fadeIn().delay(2000).fadeOut();
   };
    return that;
}());
this.AlertError = (function() {
  "use strict";
  var elem,
     hideHandler,
     that = {};
  that.init = function(options) {
      elem = angular.element(options.selector);
  };
that.show = function(text) {
   clearTimeout(hideHandler);
     elem.find("span").html(text);
    elem.delay(200).fadeIn().delay(2000).fadeOut();
 };
  return that;
}());
this.cloneObject=function(sourceObject,targetObject)
{
var keyList = Object.keys(sourceObject);
if (keyList.length == 0)
	keyList = Object.keys(targetObject);
for (var i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
	&& (typeof sourceObject[val] == "object" || typeof targetObject[val]=="object")) {
if (sourceObject[val] != null
	&& sourceObject[val] != undefined) {
if (targetObject[val]!=undefined)
	while (targetObject[val].length > 0)
		targetObject[val].pop();
if (sourceObject[val] != null)
		for (var j = 0; j < sourceObject[val].length; j++)
				targetObject[val]
			.push(sourceObject[val][j]);
	} else 
			this.emptyList(targetObject[val]);
	} else {
		if (val.toLowerCase().indexOf("time") > -1
				&& typeof val == "string") {
			targetObject[val] = new Date(sourceObject[val]);
		} else {
			targetObject[val] = sourceObject[val];
		}
	}
}
}
}
this.emptyList=function(list)
{
if (list!=undefined) 
	while (list.length>0)
		list.pop();
}
this.removeObjectFromList=function(list,obj)
{
	for (var i=0; i<list.length; i++)
	{
	if (list[i]==obj) 
	list.splice(i,i+1); 
	}
}
}
})();
