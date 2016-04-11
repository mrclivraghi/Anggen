<!DOCTYPE html>
<html>
<head>
<title>serverTest</title>
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
<script type="text/javascript" src="js/angular/serverTest/main-app.js"></script>
<script type="text/javascript" src="js/metrics/metrics.controller.js"></script>
<script type="text/javascript" src="js/metrics/metrics.service.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/role.service.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/role.controller.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/restrictionEntity.service.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/restrictionEntity.controller.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/restrictionField.service.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/restrictionField.controller.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/user.service.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/user.controller.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/restrictionEntityGroup.service.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/restrictionEntityGroup.controller.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/logEntry.service.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/logEntry.controller.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/entity.service.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/entity.controller.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/enumEntity.service.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/enumEntity.controller.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/entityGroup.service.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/entityGroup.controller.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/tab.service.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/tab.controller.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/project.service.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/project.controller.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/annotation.service.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/annotation.controller.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/enumValue.service.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/enumValue.controller.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/annotationAttribute.service.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/annotationAttribute.controller.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/field.service.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/field.controller.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/enumField.service.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/enumField.controller.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/relationship.service.js"></script>
<script type="text/javascript"
	src="js/angular/serverTest/relationship.controller.js"></script>
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
<link rel="import" href="serverTestnavbar.html" />
</head>
<body ng-app="serverTestApp" ng-controller="MainController">
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