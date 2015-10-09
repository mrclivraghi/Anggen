<!doctype html>
<html>
<head>
<title>test order</title>
<link rel="styleSheet" href="../resources/general_theme/css/ui-grid.css" />

 <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-touch.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-animate.js"></script>
    <script src="http://ui-grid.info/docs/grunt-scripts/csv.js"></script>
    <script src="http://ui-grid.info/docs/grunt-scripts/pdfmake.js"></script>
    <script src="http://ui-grid.info/docs/grunt-scripts/vfs_fonts.js"></script>
    <script src="../resources/general_theme/js/ui-grid.js"></script>
    <link rel="stylesheet" href="../resources/general_theme/css/ui-grid.css" type="text/css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="../resources/general_theme/js/ui-grid.js"></script>
<style type="text/css">
.myGrid {
	width: 700px;
	/*height: 180px;*/
}
</style>
<script>
	var app=angular
			.module("mountainApp", ['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection'])
			.service(
					"mountainService",
					function() {
						this.entityList = [];
						this.selectedEntity = {
							show : false
						};
						this.childrenList = [];
						this.addEntity = function(entity) {
							this.entityList.push(entity);
						};
						this.setEntityList = function(entityList) {
							while (this.entityList.length > 0)
								this.entityList.pop();
							if (entityList != null)
								for (i = 0; i < entityList.length; i++)
									this.entityList.push(entityList[i]);
						};
						this.searchBean = new Object();
						this.resetSearchBean = function() {
							this.searchBean.orderId = "";
							this.searchBean.name = "";
							this.searchBean.timeslotDate = "";
						};
						this.setSelectedEntity = function(entity) {
							if (entity == null) {
								entity = {};
								this.selectedEntity.show = false;
							} //else
							var keyList = Object.keys(entity);
							if (keyList.length == 0)
								keyList = Object.keys(this.selectedEntity);
							for (i = 0; i < keyList.length; i++) {
								var val = keyList[i];
								this.selectedEntity[val] = entity[val];
								if (val != undefined) {
									if (val.toLowerCase().indexOf("list") > -1
											&& (entity[val] == null || entity[val] == undefined)
											&& typeof entity[val] == "object")
										this.selectedEntity[val] = [];
									if (val.toLowerCase().indexOf("date") > -1
											&& typeof val == "string") {
										var date = new Date(entity[val]);
										this.selectedEntity[val] = new Date(
												date.getFullYear(), date
														.getMonth(), date
														.getDate());
									}
								}
							}
						};
					})
			.service(
					"seedQueryService",
					function() {
						this.entityList = [];
						this.selectedEntity = {
							show : false
						};
						this.childrenList = [];
						this.addEntity = function(entity) {
							this.entityList.push(entity);
						};
						this.setEntityList = function(entityList) {
							while (this.entityList.length > 0)
								this.entityList.pop();
							if (entityList != null)
								for (i = 0; i < entityList.length; i++)
									this.entityList.push(entityList[i]);
						};
						this.setSelectedEntity = function(entity) {
							if (entity == null) {
								entity = {};
								this.selectedEntity.show = false;
							} //else
							var keyList = Object.keys(entity);
							if (keyList.length == 0)
								keyList = Object.keys(this.selectedEntity);
							for (i = 0; i < keyList.length; i++) {
								var val = keyList[i];
								this.selectedEntity[val] = entity[val];
								if (val != undefined) {
									if (val.toLowerCase().indexOf("list") > -1
											&& (entity[val] == null || entity[val] == undefined)
											&& typeof entity[val] == "object")
										this.selectedEntity[val] = [];
									if (val.toLowerCase().indexOf("date") > -1
											&& typeof val == "string") {
										var date = new Date(entity[val]);
										this.selectedEntity[val] = new Date(
												date.getFullYear(), date
														.getMonth(), date
														.getDate());
									}
								}
							}
						};
					})
			.service(
					"photoService",
					function() {
						this.entityList = [];
						this.selectedEntity = {
							show : false
						};
						this.childrenList = [];
						this.addEntity = function(entity) {
							this.entityList.push(entity);
						};
						this.setEntityList = function(entityList) {
							while (this.entityList.length > 0)
								this.entityList.pop();
							if (entityList != null)
								for (i = 0; i < entityList.length; i++)
									this.entityList.push(entityList[i]);
						};
						this.setSelectedEntity = function(entity) {
							if (entity == null) {
								entity = {};
								this.selectedEntity.show = false;
							} //else
							var keyList = Object.keys(entity);
							if (keyList.length == 0)
								keyList = Object.keys(this.selectedEntity);
							for (i = 0; i < keyList.length; i++) {
								var val = keyList[i];
								this.selectedEntity[val] = entity[val];
								if (val != undefined) {
									if (val.toLowerCase().indexOf("list") > -1
											&& (entity[val] == null || entity[val] == undefined)
											&& typeof entity[val] == "object")
										this.selectedEntity[val] = [];
									if (val.toLowerCase().indexOf("date") > -1
											&& typeof val == "string") {
										var date = new Date(entity[val]);
										this.selectedEntity[val] = new Date(
												date.getFullYear(), date
														.getMonth(), date
														.getDate());
									}
								}
							}
						};
					})
			.controller(
					"mountainController",
					function($scope, $http, mountainService, seedQueryService,
							photoService,uiGridConstants) {
						$scope.searchBean = mountainService.searchBean;
						$scope.entityList = mountainService.entityList;
						$scope.selectedEntity = mountainService.selectedEntity;
						$scope.childrenList = mountainService.childrenList;
						$scope.reset = function() {
							mountainService.resetSearchBean();
							mountainService.setSelectedEntity(null);
							mountainService.selectedEntity.show = false;
							mountainService.setEntityList(null);
							seedQueryService.selectedEntity.show = false;
							photoService.selectedEntity.show = false;
						}
						$scope.showEntityDetail = function(index) {
							mountainService.indexSelected = index;
							mountainService
									.setSelectedEntity($scope.entityList[index]);
							mountainService.selectedEntity.show = true;
						};
						$scope.addNew = function() {
							mountainService.setSelectedEntity(null);
							mountainService.selectedEntity.show = true;
							seedQueryService.selectedEntity.show = false;
							photoService.selectedEntity.show = false;
						};

						$scope.search = function() {
							mountainService.selectedEntity.show = false;
							//alert(mountainService.searchBean.seedQuery);
							mountainService.searchBean.seedQueryList = [];
							mountainService.searchBean.seedQueryList
									.push(mountainService.searchBean.seedQuery);
							delete mountainService.searchBean.seedQuery;
							$http.post("../mountain/search",
									mountainService.searchBean).success(
									function(entityList) {
										console.log(entityList);
										mountainService
												.setEntityList(entityList);
									}).error(function() {
								alert("error");
							})
						};
						$scope.insert = function() {
							if (!$scope.mountainDetailForm.$valid)
								return;
							$http.put("../mountain/",
									mountainService.selectedEntity).success(
									function(data) {
										$scope.search();
									}).error(function() {
								alert("error");
							});
						};
						$scope.update = function() {
							if (!$scope.mountainDetailForm.$valid)
								return;
							seedQueryService.selectedEntity.show = false;
							photoService.selectedEntity.show = false;
							alert(mountainService.selectedEntity.seedQueryList[0].mountain+"--"+mountainService.selectedEntity.seedQueryList[0].seedQueryId+"--"+mountainService.selectedEntity.seedQueryList.length);
							$http.post("../mountain/",
									mountainService.selectedEntity).success(
									function(data) {
										$scope.search();
									}).error(function() {
								alert("error");
							});
						};
						$scope.del = function() {
							var url = "../mountain/"
									+ mountainService.selectedEntity.mountainId;
							$http["delete"](url).success(function(data) {
								mountainService.setSelectedEntity(null);
								$scope.search();
							}).error(function() {
								alert("error");
							});
						};
						$scope.showSeedQueryDetail = function(index) {
							if (index != null)
								seedQueryService
										.setSelectedEntity(mountainService.selectedEntity.seedQueryList[index]);
							else
								seedQueryService
										.setSelectedEntity(mountainService.selectedEntity.seedQuery);
							seedQueryService.selectedEntity.show = true;
						};
						$scope.init = function() {
							$http
									.post("../seedQuery/search", {})
									.success(
											function(entityList) {
												mountainService.childrenList.seedQueryList = entityList;
											}).error(function() {
										alert("error");
									});
						};
						$scope.init();
						
						/*pagination */
					$scope.gridOptions = {
    enablePaginationControls: true,
 
    paginationPageSizes: [2, 4, 6],
    paginationPageSize: 2,
    columnDefs: [
      { name: 'mountainId' },
      { name: 'name' },
      { name: 'height' }
    ],
    data: mountainService.entityList
    
  };
 
					})
			.controller(
					"seedQueryController",
					function($scope, $http, seedQueryService, mountainService,
							photoService) {
						$scope.searchBean = seedQueryService.searchBean;
						$scope.entityList = seedQueryService.entityList;
						$scope.selectedEntity = seedQueryService.selectedEntity;
						$scope.childrenList = seedQueryService.childrenList;
						$scope.reset = function() {
							seedQueryService.resetSearchBean();
							seedQueryService.setSelectedEntity(null);
							seedQueryService.selectedEntity.show = false;
							seedQueryService.setEntityList(null);
						}
						$scope.updateParent = function(toDo) {
							$http
									.post("../mountain/",
											mountainService.selectedEntity)
									.then(
											function(response) {
												if (response.status == 200) {
													mountainService
															.setSelectedEntity(response.data);
													if (toDo != null)
														toDo();
													if (!$scope.$$phase)
														$rootScope.$digest();
												}
											}, function(error) {
												alert("error");
											});
						};
						$scope.showEntityDetail = function(index) {
							seedQueryService.indexSelected = index;
							seedQueryService
									.setSelectedEntity($scope.entityList[index]);
							seedQueryService.selectedEntity.show = true;
						};
						$scope.addNew = function() {
							seedQueryService.setSelectedEntity(null);
							seedQueryService.selectedEntity.show = true;
						};

						$scope.search = function() {
							seedQueryService.selectedEntity.show = false;
							seedQueryService.searchBean.photoList = [];
							seedQueryService.searchBean.photoList
									.push(seedQueryService.searchBean.photo);
							delete seedQueryService.searchBean.photo;
							$http.post("../seedQuery/search",
									seedQueryService.searchBean).success(
									function(entityList) {
										seedQueryService
												.setEntityList(entityList);
									}).error(function() {
								alert("error");
							})
						};
						$scope.insert = function() {
							if (!$scope.seedQueryDetailForm.$valid)
								return;
							seedQueryService.selectedEntity.show = false;

							mountainService.selectedEntity.seedQueryList
									.push(seedQueryService.selectedEntity);

							$scope.updateParent();

						};
						$scope.update = function() {
							if (!$scope.seedQueryDetailForm.$valid)
								return;
							seedQueryService.selectedEntity.show = false;

							for (i = 0; i < mountainService.selectedEntity.seedQueryList.length; i++)

							{

								if (mountainService.selectedEntity.seedQueryList[i].seedQueryId == seedQueryService.selectedEntity.seedQueryId)

									mountainService.selectedEntity.seedQueryList
											.splice(i, 1);

							}
							//seedQueryService.selectedEntity.mountain={};
							//seedQueryService.selectedEntity.mountain.mountainId=mountainService.selectedEntity.mountainId;
							//alert(seedQueryService.selectedEntity.mountain.mountainId);
							mountainService.selectedEntity.seedQueryList
									.push(seedQueryService.selectedEntity);
							//alert(seedQueryService.selectedEntity.seedQueryId+"--"+mountainService.selectedEntity.seedQueryList.length+"--"+seedQueryService.selectedEntity.mountain);
							$scope.updateParent();
						};
						$scope.del = function() {
							seedQueryService.selectedEntity.show = false;
							for (i = 0; i < mountainService.selectedEntity.seedQueryList.length; i++) {
								if (mountainService.selectedEntity.seedQueryList[i].seedQueryId == seedQueryService.selectedEntity.seedQueryId)
									mountainService.selectedEntity.seedQueryList
											.splice(i, 1);
							}
							seedQueryService.setSelectedEntity(null);
							$scope.updateParent();
						};
						$scope.showMountainDetail = function(index) {
							if (index != null)
								mountainService
										.setSelectedEntity(seedQueryService.selectedEntity.mountainList[index]);
							else
								mountainService
										.setSelectedEntity(seedQueryService.selectedEntity.mountain);
							mountainService.selectedEntity.show = true;
						};
						$scope.showPhotoDetail = function(index) {
							if (index != null)
								photoService
										.setSelectedEntity(seedQueryService.selectedEntity.photoList[index]);
							else
								photoService
										.setSelectedEntity(seedQueryService.selectedEntity.photo);
							photoService.selectedEntity.show = true;
						};
						$scope.init = function() {
							$http
									.post("../mountain/search", {})
									.success(
											function(entityList) {
												seedQueryService.childrenList.mountainList = entityList;
											}).error(function() {
										alert("error");
									});
							$http
									.post("../photo/search", {})
									.success(
											function(entityList) {
												seedQueryService.childrenList.photoList = entityList;
											}).error(function() {
										alert("error");
									});
						};
						$scope.init();
					})
			.controller(
					"photoController",
					function($scope, $http, photoService, seedQueryService,
							mountainService) {
						$scope.searchBean = photoService.searchBean;
						$scope.entityList = photoService.entityList;
						$scope.selectedEntity = photoService.selectedEntity;
						$scope.childrenList = photoService.childrenList;
						$scope.reset = function() {
							photoService.resetSearchBean();
							photoService.setSelectedEntity(null);
							photoService.selectedEntity.show = false;
							photoService.setEntityList(null);
						}
						$scope.updateParent = function(toDo) {
							$http
									.post("../seedQuery/",
											seedQueryService.selectedEntity)
									.then(
											function(response) {
												if (response.status == 200) {
													seedQueryService
															.setSelectedEntity(response.data);
													if (toDo != null)
														toDo();
													if (!$scope.$$phase)
														$rootScope.$digest();
												}
											}, function(error) {
												alert("error");
											});
						};
						$scope.showEntityDetail = function(index) {
							photoService.indexSelected = index;
							photoService
									.setSelectedEntity($scope.entityList[index]);
							photoService.selectedEntity.show = true;
						};
						$scope.addNew = function() {
							photoService.setSelectedEntity(null);
							photoService.selectedEntity.show = true;
						};

						$scope.search = function() {
							photoService.selectedEntity.show = false;
							$http.post("../photo/search",
									photoService.searchBean).success(
									function(entityList) {
										photoService.setEntityList(entityList);
									}).error(function() {
								alert("error");
							})
						};
						$scope.insert = function() {
							if (!$scope.photoDetailForm.$valid)
								return;
							photoService.selectedEntity.show = false;

							seedQueryService.selectedEntity.photoList
									.push(photoService.selectedEntity);

							$scope.updateParent();

						};
						$scope.update = function() {
							if (!$scope.photoDetailForm.$valid)
								return;
							photoService.selectedEntity.show = false;

							for (i = 0; i < seedQueryService.selectedEntity.photoList.length; i++)

							{

								if (seedQueryService.selectedEntity.photoList[i].photoId == photoService.selectedEntity.photoId)

									seedQueryService.selectedEntity.photoList
											.splice(i, 1);

							}

							seedQueryService.selectedEntity.photoList
									.push(photoService.selectedEntity);

							$scope.updateParent();
						};
						$scope.del = function() {
							photoService.selectedEntity.show = false;
							for (i = 0; i < seedQueryService.selectedEntity.photoList.length; i++) {
								if (seedQueryService.selectedEntity.photoList[i].photoId == photoService.selectedEntity.photoId)
									seedQueryService.selectedEntity.photoList
											.splice(i, 1);
							}
							photoService.setSelectedEntity(null);
							$scope.updateParent();
						};
						$scope.showSeedQueryDetail = function(index) {
							if (index != null)
								seedQueryService
										.setSelectedEntity(photoService.selectedEntity.seedQueryList[index]);
							else
								seedQueryService
										.setSelectedEntity(photoService.selectedEntity.seedQuery);
							seedQueryService.selectedEntity.show = true;
						};
						$scope.init = function() {
							$http
									.post("../seedQuery/search", {})
									.success(
											function(entityList) {
												photoService.childrenList.seedQueryList = entityList;
											}).error(function() {
										alert("error");
									});
						};
						$scope.init();
					});
</script>
</head>
<body ng-app="mountainApp">
	<div ng-controller="mountainController">
		<form id="mountainSearchBean">
			<p>mountainId</p>
			<input type="text" ng-model="searchBean.mountainId"
				ng-readonly="false" name="mountainId" />
			<p>name</p>
			<input type="text" ng-model="searchBean.name" ng-readonly="false"
				name="name" />
			<p>height</p>
			<input type="text" ng-model="searchBean.height" ng-readonly="false"
				name="height" />
			<p>seedQuery</p>
			<select ng-model="searchBean.seedQuery.seedQueryId"
				ng-options="seedQuery.seedQueryId as  seedQuery.seedKeyword for seedQuery in childrenList.seedQueryList"
				enctype="UTF-8"></select>
			<button ng-click="addNew()">Add new</button>
			<button ng-click="search()">Find</button>
			<button ng-click="reset()">Reset</button>
		</form>
		<!-- <form id="mountainList" ng-if="entityList.length&gt;0" enctype="UTF-8"> -->
			<p>LISTA</p>
			
			<div ui-grid="gridOptions" ui-grid-pagination ui-grid-selection class="grid"></div>
			
			<!-- <ul>
				<li ng-repeat="entity in entityList"
					ng-click="showEntityDetail($index)"><p>{{$index}}
						{{entity.mountainId}} {{entity.name}} {{entity.height}}</p></li>
			</ul> -->
		<!-- </form> -->
		<form id="mountainDetailForm" name="mountainDetailForm"
			ng-show="selectedEntity.show">
			<p>DETAIL</p>
			<p>mountainId</p>
			<input type="text" ng-model="selectedEntity.mountainId"
				ng-readonly="false" name="mountainId" />
			<p>name</p>
			<input type="text" ng-model="selectedEntity.name" ng-readonly="false"
				name="name" ng-required="true" ng-minlength="2" ng-maxlength="14" />
			<div ng-show="mountainDetailForm.name.$error.required">mountain:
				name required</div>
			<div ng-show="mountainDetailForm.name.$error.minlength">mountain:
				name min 2 caratteri</div>
			<div ng-show="mountainDetailForm.name.$error.maxlength">mountain:
				name max 14 caratteri</div>
			<p>height</p>
			<input type="text" ng-model="selectedEntity.height"
				ng-readonly="false" name="height" />
			<p ng-click="showSeedQueryDetail()">Add new seedQuery</p>
			<div ng-if="selectedEntity.seedQueryList.length&gt;0">
				<ul>
					<li ng-repeat="entity in selectedEntity.seedQueryList"
						ng-click="showSeedQueryDetail($index)">{{$index}}
						{{entity.seedQueryId}} {{entity.seedKeyword}} {{entity.status}}</li>
				</ul>
			</div>
		</form>
		<form id="mountainActionButton" ng-if="selectedEntity.show">
			<p>ACTION BUTTON</p>
			<button ng-click="insert()"
				ng-if="selectedEntity.mountainId==undefined">Insert</button>
			<button ng-click="update()" ng-if="selectedEntity.mountainId&gt;0">Update</button>
			<button ng-click="del()" ng-if="selectedEntity.mountainId&gt;0">Delete</button>
		</form>
	</div>
	<div ng-controller="seedQueryController">
		<form id="seedQueryList" ng-if="entityList.length&gt;0"
			enctype="UTF-8">
			<p>LISTA</p>
			<ul>
				<li ng-repeat="entity in entityList"
					ng-click="showEntityDetail($index)"><p>{{$index}}
						{{entity.seedQueryId}} {{entity.seedKeyword}} {{entity.status}}</p></li>
			</ul>
		</form>
		<form id="seedQueryDetailForm" name="seedQueryDetailForm"
			ng-show="selectedEntity.show">
			<p>DETAIL</p>
			<p>seedQueryId</p>
			<input type="text" ng-model="selectedEntity.seedQueryId"
				ng-readonly="false" name="seedQueryId" />
			<p>seedKeyword</p>
			<input type="text" ng-model="selectedEntity.seedKeyword"
				ng-readonly="false" name="seedKeyword" />
			<p>status</p>
			<input type="text" ng-model="selectedEntity.status"
				ng-readonly="false" name="status" ng-required="true" />
			<div ng-show="seedQueryDetailForm.status.$error.required">seedQuery:
				status required</div>
			<p ng-click="showPhotoDetail()">Add new photo</p>
			<div ng-if="selectedEntity.photoList.length&gt;0">
				<ul>
					<li ng-repeat="entity in selectedEntity.photoList"
						ng-click="showPhotoDetail($index)">{{$index}}
						{{entity.photoId}} {{entity.url}} {{entity.social}} {{entity.date
						| date: 'dd-MM-yyyy'}} {{entity.status}} {{entity.socialId}}
						{{entity.relatedPost}}</li>
				</ul>
			</div>
		</form>
		<form id="seedQueryActionButton" ng-if="selectedEntity.show">
			<p>ACTION BUTTON</p>
			<button ng-click="insert()"
				ng-if="selectedEntity.seedQueryId==undefined">Insert</button>
			<button ng-click="update()" ng-if="selectedEntity.seedQueryId&gt;0">Update</button>
			<button ng-click="del()" ng-if="selectedEntity.seedQueryId&gt;0">Delete</button>
		</form>
	</div>
	<div ng-controller="photoController">
		<form id="photoList" ng-if="entityList.length&gt;0" enctype="UTF-8">
			<p>LISTA</p>
			<ul>
				<li ng-repeat="entity in entityList"
					ng-click="showEntityDetail($index)"><p>{{$index}}
						{{entity.photoId}} {{entity.url}} {{entity.social}} {{entity.date
						| date: 'dd-MM-yyyy'}} {{entity.status}} {{entity.socialId}}
						{{entity.relatedPost}}</p></li>
			</ul>
		</form>
		<form id="photoDetailForm" name="photoDetailForm"
			ng-show="selectedEntity.show">
			<p>DETAIL</p>
			<p>photoId</p>
			<input type="text" ng-model="selectedEntity.photoId"
				ng-readonly="false" name="photoId" />
			<p>url</p>
			<input type="text" ng-model="selectedEntity.url" ng-readonly="false"
				name="url" />
			<p>social</p>
			<input type="text" ng-model="selectedEntity.social"
				ng-readonly="false" name="social" />
			<p>date</p>
			<input type="date" ng-model="selectedEntity.date" ng-readonly="false"
				name="date" />
			<p>status</p>
			<input type="text" ng-model="selectedEntity.status"
				ng-readonly="false" name="status" />
			<p>socialId</p>
			<input type="text" ng-model="selectedEntity.socialId"
				ng-readonly="false" name="socialId" />
			<p>relatedPost</p>
			<input type="text" ng-model="selectedEntity.relatedPost"
				ng-readonly="false" name="relatedPost" />
		</form>
		<form id="photoActionButton" ng-if="selectedEntity.show">
			<p>ACTION BUTTON</p>
			<button ng-click="insert()" ng-if="selectedEntity.photoId==undefined">Insert</button>
			<button ng-click="update()" ng-if="selectedEntity.photoId&gt;0">Update</button>
			<button ng-click="del()" ng-if="selectedEntity.photoId&gt;0">Delete</button>
		</form>
	</div>
	
</body>
</html>