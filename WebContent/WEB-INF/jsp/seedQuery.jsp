<!DOCTYPE html>
<html>
<head>
<title>test order</title>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
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
	src="../resources/general_theme/js/angular/seedQuery.js"></script>
<script type="text/javascript"
	src="../resources/general_theme/js/date.js"></script>
<link rel="stylesheet" href="http://ui-grid.info/release/ui-grid.css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" />
<link rel="stylesheet" href="../resources/general_theme/css/main.css" />
<link rel="stylesheet"
	href="../resources/general_theme/css/jquery-ui.css" />
</head>
<body ng-app="seedQueryApp">
	<div ng-controller="seedQueryController">
		<form id="seedQuerySearchBean">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Search form seedQuery</div>
				<div class="panel-body">
					<div class="pull-left right-input">
						<label id="seedQueryId">seedQueryId</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.seedQueryId" ng-readonly="false"
							name="seedQueryId" placeholder="seedQueryId"
							id="seedQuery-seedQueryId" />
					</div>
					<div class="pull-right right-input">
						<label id="seedKeyword">seedKeyword</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.seedKeyword" ng-readonly="false"
							name="seedKeyword" placeholder="seedKeyword"
							id="seedQuery-seedKeyword" />
					</div>
					<div class="pull-left right-input">
						<label id="status">status</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="searchBean.status" ng-readonly="false" name="status"
							placeholder="status" id="seedQuery-status" />
					</div>
					<div class="pull-right right-input">
						<label id="mountain">mountain</label><select class="form-control "
							aria-describedby="sizing-addon3"
							ng-model="searchBean.mountain.mountainId" id="mountain"
							ng-options="mountain.mountainId as  mountain.name+&#39; &#39;+ mountain.height for mountain in childrenList.mountainList"
							enctype="UTF-8"></select>
					</div>
					<div class="pull-left right-input">
						<label id="photo">photo</label><select class="form-control "
							aria-describedby="sizing-addon3"
							ng-model="searchBean.photo.photoId" id="photo"
							ng-options="photo.photoId as  photo.url+&#39; &#39;+ photo.social+&#39; &#39;+ photo.socialId+&#39; &#39;+ photo.relatedPost for photo in childrenList.photoList"
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
							id="seedQuery-seedQueryId" />
					</div>
					<div class="pull-right right-input">
						<label for="seedKeyword">seedKeyword</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.seedKeyword"
							ng-readonly="false" name="seedKeyword" placeholder="seedKeyword"
							id="seedQuery-seedKeyword" />
					</div>
					<div class="pull-left right-input">
						<label for="status">status</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.status" ng-readonly="false"
							name="status" placeholder="status" id="seedQuery-status"
							ng-required="true" />
					</div>
					<div ng-show="seedQueryDetailForm.status.$error.required">seedQuery:
						status required</div>
					<div class="pull-right right-input">
						<button ng-click="showMountainDetail()()" class="btn btn-default"
							ng-if="selectedEntity.mountain==null">Add new mountain</button>
						<label for="mountain">mountain</label><select
							class="form-control " aria-describedby="sizing-addon3"
							ng-model="selectedEntity.mountain" id="mountain"
							ng-options="mountain as  mountain.name+&#39; &#39;+ mountain.height for mountain in childrenList.mountainList track by mountain.mountainId"
							enctype="UTF-8"></select>
						<button ng-click="showMountainDetail()" class="btn btn-default"
							id="mountain" ng-if="selectedEntity.mountain!=null">Show Detail</button>
					</div>
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
	<div ng-controller="mountainController">
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
							id="mountain-mountainId" />
					</div>
					<div class="pull-right right-input">
						<label for="name">name</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.name" ng-readonly="false" name="name"
							placeholder="name" id="mountain-name" ng-required="true"
							ng-minlength="2" ng-maxlength="14" />
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
							name="height" placeholder="height" id="mountain-height" />
					</div>
				</div>
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
							name="photoId" placeholder="photoId" id="photo-photoId" />
					</div>
					<div class="pull-right right-input">
						<label for="url">url</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.url" ng-readonly="false" name="url"
							placeholder="url" id="photo-url" />
					</div>
					<div class="pull-left right-input">
						<label for="social">social</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.social" ng-readonly="false"
							name="social" placeholder="social" id="photo-social" />
					</div>
					<div class="pull-right right-input">
						<label for="date">date</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="selectedEntity.date"
							ng-readonly="false" name="date" placeholder="date"
							id="photo-date" />
					</div>
					<div class="pull-left right-input">
						<label for="status">status</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.status" ng-readonly="false"
							name="status" placeholder="status" id="photo-status" />
					</div>
					<div class="pull-right right-input">
						<label for="socialId">socialId</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.socialId" ng-readonly="false"
							name="socialId" placeholder="socialId" id="photo-socialId" />
					</div>
					<div class="pull-left right-input">
						<label for="relatedPost">relatedPost</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.relatedPost"
							ng-readonly="false" name="relatedPost" placeholder="relatedPost"
							id="photo-relatedPost" />
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