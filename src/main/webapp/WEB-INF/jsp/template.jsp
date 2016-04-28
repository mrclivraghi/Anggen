<!DOCTYPE html>
<html>
<head>
<title>anggen</title>
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/angular.js"></script>
<script type="text/javascript" src="js/angular-touch.js"></script>
<script type="text/javascript" src="js/angular-animate.js"></script>
<script type="text/javascript" src="js/csv.js"></script>
<script type="text/javascript" src="js/pdfmake.js"></script>
<script type="text/javascript" src="js/vfs_fonts.js"></script>
<script type="text/javascript" src="js/angular-route.js"></script>
<script type="text/javascript" src="js/ui-grid.js"></script>
<script type="text/javascript" src="js/ui-bootstrap-tpls-1.2.5.min.js"></script>
<script type="text/javascript" src="js/angular/anggen/main-app.js"></script>
<script type="text/javascript" src="js/metrics/metrics.controller.js"></script>
<script type="text/javascript" src="js/metrics/metrics.service.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/annotationAttribute.service.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/annotationAttribute.controller.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/annotation.service.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/annotation.controller.js"></script>
<script type="text/javascript" src="js/angular/anggen/field.service.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/field.controller.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/enumValue.service.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/enumValue.controller.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/enumField.service.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/enumField.controller.js"></script>
<script type="text/javascript" src="js/angular/anggen/tab.service.js"></script>
<script type="text/javascript" src="js/angular/anggen/tab.controller.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/entityGroup.service.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/entityGroup.controller.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/project.service.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/project.controller.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/enumEntity.service.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/enumEntity.controller.js"></script>
<script type="text/javascript" src="js/angular/anggen/entity.service.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/entity.controller.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/restrictionEntityGroup.service.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/restrictionEntityGroup.controller.js"></script>
<script type="text/javascript" src="js/angular/anggen/user.service.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/user.controller.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/restrictionField.service.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/restrictionField.controller.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/restrictionEntity.service.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/restrictionEntity.controller.js"></script>
<script type="text/javascript" src="js/angular/anggen/role.service.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/role.controller.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/relationship.service.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/relationship.controller.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/logEntry.service.js"></script>
<script type="text/javascript"
	src="js/angular/anggen/logEntry.controller.js"></script>
<script type="text/javascript" src="js/date.js"></script>
<script type="text/javascript" src="js/utility.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/alasql.min.js"></script>
<script type="text/javascript" src="js/ng-file-upload-all.js"></script>
<script type="text/javascript">
	angular
			.element(document.getElementsByTagName('head'))
			.append(
					angular
							.element('<base href="' + window.location.pathname + '" />'));
</script>
<link rel="stylesheet" href="css/ui-grid.css" />
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/easytree/skin-win8/ui.easytree.css" />
<link rel="import" href="anggenMenu.jsp" />
</head>
<body ng-app="anggenApp" ng-controller="MainController">
	<div id="alertInfo" class="alert alert-success custom-alert"
		style="display: none">
		<span></span>
	</div>
	<div id="alertError" class="alert alert-danger custom-alert"
		style="display: none">
		<span></span>
	</div>
	<div ng-view=""></div>
	<script type="text/javascript">loadMenu(); </script>
</body>
</html>