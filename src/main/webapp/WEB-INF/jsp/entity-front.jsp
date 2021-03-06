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
<script type="text/javascript"
	src="../js/angular/anggen/entity-front.js"></script>
<script type="text/javascript" src="../js/date.js"></script>
<script type="text/javascript" src="../js/utility.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
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
		<div ng-repeat="entity in entityList">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">entity {{ entity.entityId }}</div>
				<div class="panel-body">
					<div>entityId: {{entity.entityId}}</div>
					<button class="btn btn-default btn-lg">
						<span class="glyphicon"
							ng-class="{&#39;glyphicon-ok&#39;: entity.enableRestrictionData,&#39;glyphicon-remove &#39;: !entity.enableRestrictionData}"
							aria-hidden="true">enableRestrictionData
					</button>
					<div>descendantMaxLevel: {{entity.descendantMaxLevel}}</div>
					<div>name: {{entity.name}}</div>
					<div>securityType: {{entity.securityType}}</div>
				</div>
			</div>
		</div>
		<div class="default-panel" style="margin-left: 45%">
			<ul class="pagination">
			currentPAge {{currentPage}}
				<li ng-class="{disabled: currentPage&lt;=1}"><a
					ng-click="getPagination(currentPage-1)">&laquo;</a></li>
				<li
					ng-repeat="i in [].constructor(selectedEntity.totalPages) track by $index"
					ng-class="{active: $index+1==currentPage || (currentPage==undefined && $index==0)}"><a
					ng-click="getPagination($index+1)">{{$index+1}}</a></li>
				<li ng-class="{disabled: currentPage&gt;=selectedEntity.totalPages}"><a
					ng-click="getPagination(currentPage+1)">&raquo;</a></li>
			</ul>
		</div>
	</div>
	<script type="text/javascript">loadMenu(); </script>
</body>
</html>