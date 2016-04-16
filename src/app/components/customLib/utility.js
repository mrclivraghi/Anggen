/**
 *  utility functionality
*/
function loadMenu()
{
var content = document.querySelector('link[rel="import"]').import; 
 $("body").prepend(content.documentElement.getElementsByTagName("body")[0]);
}
function activeMenu(path)
{
	$("a").parent("li").removeClass("active");
	$("a[href='../"+path+"/']").parent("li").addClass("active");
	if ($("a[href='../"+path+"/']").parent("li").parent("ul").parent("li")[0]!=undefined)
		$("a[href='../"+path+"/']").parent("li").parent("ul").parent("li").addClass("active");
}
var AlertSuccess = (function() {
   "use strict";
   var elem,
       hideHandler,
       that = {};
    that.init = function(options) {
        elem = $(options.selector);
    };
    that.show = function(text) {
        clearTimeout(hideHandler);
        elem.find("span").html(text);
        elem.delay(200).fadeIn().delay(2000).fadeOut();
   };
    return that;
}());
var AlertError = (function() {
  "use strict";
  var elem,
     hideHandler,
     that = {};
  that.init = function(options) {
      elem = $(options.selector);
  };
that.show = function(text) {
   clearTimeout(hideHandler);
     elem.find("span").html(text);
    elem.delay(200).fadeIn().delay(2000).fadeOut();
 };
  return that;
}());
function cloneObject(sourceObject,targetObject)
{
var keyList = Object.keys(sourceObject);
if (keyList.length == 0)
	keyList = Object.keys(targetObject);
for (i = 0; i < keyList.length; i++) {
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
		for (j = 0; j < sourceObject[val].length; j++)
				targetObject[val]
			.push(sourceObject[val][j]);
	} else 
			emptyList(targetObject[val]);
	} else {
		if (val.toLowerCase().indexOf("time") > -1
				&& typeof val == "string") {
			var date = new Date(sourceObject[val]);
			targetObject[val] = new Date(sourceObject[val]);
		} else {
			targetObject[val] = sourceObject[val];
		}
	}
}
};
}
function emptyList(list)
{
	while (list.length>0)
		list.pop();
}
