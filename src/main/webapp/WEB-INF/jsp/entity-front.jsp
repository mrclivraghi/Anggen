<!DOCTYPE html>
<html>
<head>
<title>entity</title>
<script type="text/javascript" src="../js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../js/jquery-ui.js"></script>
<script type="text/javascript" src="../js/angular.js"></script>
<script type="text/javascript" src="../js/angular-touch.js"></script>
<script type="text/javascript" src="../js/angular-animate.js"></script>
<script type="text/javascript" src="../js/csv.js"></script>
<script type="text/javascript" src="../js/pdfmake.js"></script>
<script type="text/javascript" src="../js/vfs_fonts.js"></script>
<script type="text/javascript" src="../js/ui-grid.js"></script>
<script type="text/javascript" src="../js/angular/anggen/entity-front.js"></script>
<script type="text/javascript" src="../js/date.js"></script>
<script type="text/javascript" src="../js/utility.js"></script>
<script type="text/javascript" src="../js/jquery.cookie.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/alasql.min.js"></script>
<script type="text/javascript" src="../js/ng-file-upload-all.js"></script>
<link rel="stylesheet" href="../css/ui-grid.css" />
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/main.css" />
<link rel="stylesheet" href="../css/jquery-ui.css" />
<link rel="stylesheet" href="../css/easytree/skin-win8/ui.easytree.css" />
<link rel="import" href="../anggenMenu.jsp" />
</head>
<body ng-app="entityFrontApp">
	<div id="alertInfo" class="alert alert-success custom-alert"
		style="display: none">
		<span></span>
	</div>
	<div id="alertError" class="alert alert-danger custom-alert"
		style="display: none">
		<span></span>
	</div>
	<div ng-controller="entityFrontController">
		<div ng-repeat="entity in entityList" class="panel panel-default default-panel">
			{{entity.entityId}} <br>
		</div>

		<ul class="pagination">
			<li class="disabled"><a href="#">&laquo;</a></li>
			<li><a href="#">1</a></li>
			<li class="active"><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">5</a></li>
			<li><a href="#">&raquo;</a></li>
		</ul>
	</div>
	<script type="text/javascript">loadMenu(); </script>
</body>
</html>