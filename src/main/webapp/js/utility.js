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

