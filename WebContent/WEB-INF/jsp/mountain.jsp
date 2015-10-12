<!DOCTYPE html>
<html>
<head>
<title>test order</title>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-touch.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-animate.js"></script>
<script type="text/javascript"
	src="http://ui-grid.info/docs/grunt-scripts/csv.js"></script>
<script type="text/javascript"
	src="http://ui-grid.info/docs/grunt-scripts/pdfmake.js"></script>
<script type="text/javascript"
	src="http://ui-grid.info/docs/grunt-scripts/vfs_fonts.js"></script>
<script type="text/javascript"
	src="http://ui-grid.info/release/ui-grid.js"></script>
<script type="text/javascript"
	src="../resources/general_theme/js/angular/mountain.js"></script>
<link rel="stylesheet" href="http://ui-grid.info/release/ui-grid.css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" />
<link rel="stylesheet" href="../resources/general_theme/css/main.css" />
</head>
<body ng-app="mountainApp">
	<div ng-controller="mountainController">
		<form id="mountainSearchBean">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Search form mountain</div>
				<div class="panel-body">
					<div class="pull-left right-input">
						<label id="mountainId">mountainId</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.mountainId" ng-readonly="false"
							name="mountainId" placeholder="mountainId" id="mountainId"
							id="mountainId" />
					</div>
					<div class="pull-right right-input">
						<label id="name">name</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="searchBean.name" ng-readonly="false" name="name"
							placeholder="name" id="name" id="name" />
					</div>
					<div class="pull-left right-input">
						<label id="height">height</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="searchBean.height" ng-readonly="false" name="height"
							placeholder="height" id="height" id="height" />
					</div>
					<div class="pull-right right-input">
						<label id="seedQuery">seedQuery</label><select
							class="form-control " aria-describedby="sizing-addon3"
							ng-model="searchBean.seedQuery.seedQueryId" id="seedQuery"
							ng-options="seedQuery.seedQueryId as  seedQuery.seedKeyword for seedQuery in childrenList.seedQueryList"
							enctype="UTF-8"></select>
					</div>
				</div>
				<div class="panel-body">
					<div class="pull-left right-input">
						<button ng-click="addNew()" class="btn btn-default">Add
							new</button>
						<button ng-click="search()" class="btn btn-default">Find</button>
						<button ng-click="reset()" class="btn btn-default">Reset</button>
					</div>
				</div>
			</div>
		</form>
		<form id="mountainList" ng-if="entityList.length&gt;0" enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">List mountain</div>
				<div class="panel-body">
					<div ui-grid="mountainGridOptions" ui-grid-pagination=""
						ui-grid-selection=""></div>
				</div>
			</div>
		</form>
		<form id="mountainDetailForm" name="mountainDetailForm"
			ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail mountain {{
					selectedEntity.mountainId }}</div>
				<div class="panel-body">
					<div class="pull-left right-input">
						<label for="mountainId">mountainId</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.mountainId"
							ng-readonly="false" name="mountainId" placeholder="mountainId"
							id="mountainId" />
					</div>
					<div class="pull-right right-input">
						<label for="name">name</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.name" ng-readonly="false" name="name"
							placeholder="name" id="name" ng-required="true" ng-minlength="2"
							ng-maxlength="14" />
					</div>
					<div ng-show="mountainDetailForm.name.$error.required">mountain:
						name required</div>
					<div ng-show="mountainDetailForm.name.$error.minlength">mountain:
						name min 2 caratteri</div>
					<div ng-show="mountainDetailForm.name.$error.maxlength">mountain:
						name max 14 caratteri</div>
					<div class="pull-left right-input">
						<label for="height">height</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.height" ng-readonly="false"
							name="height" placeholder="height" id="height" />
					</div>
				</div>
				<div class="panel-body">
					<label id="seedQuery">seedQuery</label>
					<button ng-click="showSeedQueryDetail()" class="btn btn-default">Add
						new seedQuery</button>
					<div id="seedQuery"
						ng-if="selectedEntity.seedQueryList.length&gt;0">
						<div style="top: 100px" ui-grid="seedQueryListGridOptions"
							ui-grid-pagination="" ui-grid-selection=""></div>
					</div>
				</div>
				<div class="panel-body"></div>
				<div class="panel-body">
					<div class="pull-left">
						<form id="mountainActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default"
								ng-if="selectedEntity.mountainId==undefined">Insert</button>
							<button ng-click="update()" class="btn btn-default"
								ng-if="selectedEntity.mountainId&gt;0">Update</button>
							<button ng-click="del()" class="btn btn-default"
								ng-if="selectedEntity.mountainId&gt;0">Delete</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="seedQueryController">
		<form id="seedQueryList" ng-if="entityList.length&gt;0"
			enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">List seedQuery</div>
				<div class="panel-body">
					<div ui-grid="seedQueryGridOptions" ui-grid-pagination=""
						ui-grid-selection=""></div>
				</div>
			</div>
		</form>
		<form id="seedQueryDetailForm" name="seedQueryDetailForm"
			ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail seedQuery {{
					selectedEntity.seedQueryId }}</div>
				<div class="panel-body">
					<div class="pull-left right-input">
						<label for="seedQueryId">seedQueryId</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.seedQueryId"
							ng-readonly="false" name="seedQueryId" placeholder="seedQueryId"
							id="seedQueryId" />
					</div>
					<div class="pull-right right-input">
						<label for="seedKeyword">seedKeyword</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.seedKeyword"
							ng-readonly="false" name="seedKeyword" placeholder="seedKeyword"
							id="seedKeyword" />
					</div>
					<div class="pull-left right-input">
						<label for="status">status</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.status" ng-readonly="false"
							name="status" placeholder="status" id="status" ng-required="true" />
					</div>
					<div ng-show="seedQueryDetailForm.status.$error.required">seedQuery:
						status required</div>
				</div>
				<div class="panel-body">
					<label id="photo">photo</label>
					<button ng-click="showPhotoDetail()" class="btn btn-default">Add
						new photo</button>
					<div id="photo" ng-if="selectedEntity.photoList.length&gt;0">
						<div style="top: 100px" ui-grid="photoListGridOptions"
							ui-grid-pagination="" ui-grid-selection=""></div>
					</div>
				</div>
				<div class="panel-body"></div>
				<div class="panel-body">
					<div class="pull-left">
						<form id="seedQueryActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default"
								ng-if="selectedEntity.seedQueryId==undefined">Insert</button>
							<button ng-click="update()" class="btn btn-default"
								ng-if="selectedEntity.seedQueryId&gt;0">Update</button>
							<button ng-click="del()" class="btn btn-default"
								ng-if="selectedEntity.seedQueryId&gt;0">Delete</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="photoController">
		<form id="photoList" ng-if="entityList.length&gt;0" enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">List photo</div>
				<div class="panel-body">
					<div ui-grid="photoGridOptions" ui-grid-pagination=""
						ui-grid-selection=""></div>
				</div>
			</div>
		</form>
		<form id="photoDetailForm" name="photoDetailForm"
			ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail photo {{
					selectedEntity.photoId }}</div>
				<div class="panel-body">
					<div class="pull-left right-input">
						<label for="photoId">photoId</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.photoId" ng-readonly="false"
							name="photoId" placeholder="photoId" id="photoId" />
					</div>
					<div class="pull-right right-input">
						<label for="url">url</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.url" ng-readonly="false" name="url"
							placeholder="url" id="url" />
					</div>
					<div class="pull-left right-input">
						<label for="social">social</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.social" ng-readonly="false"
							name="social" placeholder="social" id="social" />
					</div>
					<div class="pull-right right-input">
						<label for="date">date</label><input class="form-control "
							aria-describedby="sizing-addon3" type="date"
							ng-model="selectedEntity.date" ng-readonly="false" name="date"
							placeholder="date" id="date" />
					</div>
					<div class="pull-left right-input">
						<label for="status">status</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.status" ng-readonly="false"
							name="status" placeholder="status" id="status" />
					</div>
					<div class="pull-right right-input">
						<label for="socialId">socialId</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.socialId" ng-readonly="false"
							name="socialId" placeholder="socialId" id="socialId" />
					</div>
					<div class="pull-left right-input">
						<label for="relatedPost">relatedPost</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.relatedPost"
							ng-readonly="false" name="relatedPost" placeholder="relatedPost"
							id="relatedPost" />
					</div>
				</div>
				<div class="panel-body">
					<div class="pull-left">
						<form id="photoActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default"
								ng-if="selectedEntity.photoId==undefined">Insert</button>
							<button ng-click="update()" class="btn btn-default"
								ng-if="selectedEntity.photoId&gt;0">Update</button>
							<button ng-click="del()" class="btn btn-default"
								ng-if="selectedEntity.photoId&gt;0">Delete</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>