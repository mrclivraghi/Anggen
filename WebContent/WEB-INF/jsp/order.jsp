<head>
<title>test order</title>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<script>
	angular
			.module("orderApp", [])
			.service(
					"orderService",
					function($http) { //MODIFICA
						this.entityList = [];
						this.childrenList={};
						
						this.selectedEntity = {
							show : false
						};
						this.addEntity = function(entity) {
							this.entityList.push(entity);
						};
						this.setEntityList = function(entityList) {
							while (this.entityList.length > 0)
								this.entityList.pop();
							//MODIFICA
							if (entityList!=null)
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
					"personService",
					function() {
						this.entityList = [];
						this.selectedEntity = {
							show : false
						};
						this.addEntity = function(entity) {
							this.entityList.push(entity);
						};
						this.setEntityList = function(entityList) {
							while (this.entityList.length > 0)
								this.entityList.pop();
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
					"placeService",
					function() {
						this.entityList = [];
						this.selectedEntity = {
							show : false
						};
						this.addEntity = function(entity) {
							this.entityList.push(entity);
						};
						this.setEntityList = function(entityList) {
							while (this.entityList.length > 0)
								this.entityList.pop();
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
					"orderController",
					function($scope, $http, orderService, personService,
							placeService) {
						$scope.searchBean = orderService.searchBean;
						$scope.entityList = orderService.entityList;
						$scope.selectedEntity = orderService.selectedEntity;
						$scope.childrenList = orderService.childrenList;
						$scope.reset = function() {
							orderService.resetSearchBean();
							orderService.selectedEntity.show = false;
							personService.selectedEntity.show = false;
							placeService.selectedEntity.show = false;
							//MODIFICA
							orderService.setEntityList(null);
						}
						$scope.showEntityDetail = function(index) {
							orderService.indexSelected = index;
							orderService
									.setSelectedEntity($scope.entityList[index]);
							orderService.selectedEntity.show = true;
						};
						$scope.addNew = function() {
							orderService.setSelectedEntity(null);
							orderService.selectedEntity.show = true;
							personService.selectedEntity.show = false;
							placeService.selectedEntity.show = false;
						};

						$scope.search = function() {
							orderService.selectedEntity.show = false;
							//MODIFICA
							orderService.searchBean.placeList=[];
							orderService.searchBean.placeList.push(orderService.searchBean.place);
							delete orderService.searchBean.place;
							$http.post("../order/search",
									orderService.searchBean).success(
									function(entityList) {
										orderService.setEntityList(entityList);
									}).error(function() {
								alert("error");
							})
						};
						$scope.insert = function() {
							$http.put("../order/", orderService.selectedEntity)
									.success(function(data) {
										$scope.search();
									}).error(function() {
										alert("error");
									});
						};
						$scope.update = function() {
							personService.selectedEntity.show = false;
							placeService.selectedEntity.show = false;
							$http
									.post("../order/",
											orderService.selectedEntity)
									.success(function(data) {
										$scope.search();
									}).error(function() {
										alert("error");
									});
						};
						$scope.del = function() {
							var url = "../order/"
									+ orderService.selectedEntity.orderId;
							$http["delete"](url).success(function(data) {
								orderService.setSelectedEntity(null);
								$scope.search();
							}).error(function() {
								alert("error");
							});
						};
						$scope.showPersonDetail = function(index) {
							if (index != null)
								personService
										.setSelectedEntity(orderService.selectedEntity.personList[index]);
							else
								personService
										.setSelectedEntity(orderService.selectedEntity.person);
							personService.selectedEntity.show = true;
						};
						$scope.showPlaceDetail = function(index) {
							if (index != null)
								placeService
										.setSelectedEntity(orderService.selectedEntity.placeList[index]);
							else
								placeService
										.setSelectedEntity(orderService.selectedEntity.place);
							placeService.selectedEntity.show = true;
						};
						
						
						//MODIFICA
						$scope.init=function()
						{
							$http
							.post("../person/search",
									{})
							.success(
									function(entityList) {
										orderService.childrenList.personList=entityList;
									}).error(function() {
								alert("error");
							});
							$http
							.post("../place/search",
									{})
							.success(
									function(entityList) {
										orderService.childrenList.placeList=entityList;
									}).error(function() {
								alert("error");
							});
							
							
						};
						
						//MODIFICA
						$scope.init();
					})
			.controller(
					"personController",
					function($scope, $http, orderService, personService) {
						$scope.searchBean = personService.searchBean;
						$scope.entityList = personService.entityList;
						$scope.selectedEntity = personService.selectedEntity;
						$scope.reset = function() {
							personService.resetSearchBean();
							personService.selectedEntity.show = false;
						}
						$scope.updateParent = function(toDo) {
							$http
									.post("../order/",
											orderService.selectedEntity)
									.then(
											function(response) {
												if (response.status == 200) {
													orderService
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
							personService.indexSelected = index;
							personService
									.setSelectedEntity($scope.entityList[index]);
							personService.selectedEntity.show = true;
						};
						$scope.addNew = function() {
							personService.setSelectedEntity(null);
							personService.selectedEntity.show = true;
						};

						$scope.search = function() {
							personService.selectedEntity.show = false;
							$http
									.post("../person/search",
											personService.searchBean)
									.success(
											function(entityList) {
												//MODIFICA
												orderService.searchBean.personList=entityList;
												//personService
													//	.setEntityList(entityList);
											}).error(function() {
										alert("error");
									})
						};
						$scope.insert = function() {
							personService.selectedEntity.show = false;

							orderService.selectedEntity.person = personService.selectedEntity;

							$scope.updateParent();

						};
						$scope.update = function() {
							personService.selectedEntity.show = false;

							orderService.selectedEntity.person = personService.selectedEntity;

							$scope.updateParent();
						};
						$scope.del = function() {
							personService.selectedEntity.show = false;
							orderService.selectedEntity.person = null;
							personService.setSelectedEntity(null);
							$scope.updateParent();
						};
					})
			.controller(
					"placeController",
					function($scope, $http, orderService, placeService) {
						$scope.searchBean = placeService.searchBean;
						$scope.entityList = placeService.entityList;
						$scope.selectedEntity = placeService.selectedEntity;
						$scope.reset = function() {
							placeService.resetSearchBean();
							placeService.selectedEntity.show = false;
						}
						$scope.updateParent = function(toDo) {
							$http
									.post("../order/",
											orderService.selectedEntity)
									.then(
											function(response) {
												if (response.status == 200) {
													orderService
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
							placeService.indexSelected = index;
							placeService
									.setSelectedEntity($scope.entityList[index]);
							placeService.selectedEntity.show = true;
						};
						$scope.addNew = function() {
							placeService.setSelectedEntity(null);
							placeService.selectedEntity.show = true;
						};

						$scope.search = function() {
							placeService.selectedEntity.show = false;
							$http.post("../place/search",
									placeService.searchBean).success(
									function(entityList) {
										placeService.setEntityList(entityList);
									}).error(function() {
								alert("error");
							})
						};
						$scope.insert = function() {
							placeService.selectedEntity.show = false;

							orderService.selectedEntity.placeList
									.push(placeService.selectedEntity);

							$scope.updateParent();

						};
						$scope.update = function() {
							placeService.selectedEntity.show = false;

							for (i = 0; i < orderService.selectedEntity.placeList.length; i++)

							{

								if (orderService.selectedEntity.placeList[i].placeId == placeService.selectedEntity.placeId)

									orderService.selectedEntity.placeList
											.splice(i, 1);

							}

							orderService.selectedEntity.placeList
									.push(placeService.selectedEntity);

							$scope.updateParent();
						};
						$scope.del = function() {
							placeService.selectedEntity.show = false;
							for (i = 0; i < orderService.selectedEntity.placeList.length; i++) {
								if (orderService.selectedEntity.placeList[i].placeId == placeService.selectedEntity.placeId)
									orderService.selectedEntity.placeList
											.splice(i, 1);
							}
							placeService.setSelectedEntity(null);
							$scope.updateParent();
						};
					});
//angular.element("#initController").scope().init();
</script>
</head>
<body ng-app="orderApp">
	<div ng-controller="orderController">
		<div id="orderSearchBean">
			<p>orderId</p>
			<input type="text" ng-model="searchBean.orderId" ng-readonly="false" />
			<p>name</p>
			<input type="text" ng-model="searchBean.name" ng-readonly="false" />
			<p>timeslotDate</p>
			<!-- TODO mgmt -->
			<p>person</p>
			<select ng-model="searchBean.person.personId"
					ng-options="person.personId as person.firstName+' '+person.lastName for person in childrenList.personList">
			</select>
			<p>place</p>
			<select ng-model="searchBean.place.placeId"
					ng-options="place.placeId as place.description for place in childrenList.placeList">
			</select>
			
			<input type="date" ng-model="searchBean.timeslotDate"
				ng-readonly="false" />
			<button ng-click="addNew()">Add new</button>
			<button ng-click="search()">Find</button>
			<button ng-click="reset()">Reset</button>
		</div>
		<div id="orderList" ng-if="entityList.length&gt;0" enctype="UTF-8">
			<p>LISTA</p>
			<ul>
				<li ng-repeat="entity in entityList"
					ng-click="showEntityDetail($index)"><p>{{$index}}
						{{entity.orderId}} {{entity.name}} {{entity.timeslotDate}}</p></li>
			</ul>
		</div>
		<div id="orderDetail" ng-if="selectedEntity.show">
			<p>DETAIL</p>
			<p>orderId</p>
			<input type="text" ng-model="selectedEntity.orderId"
				ng-readonly="true" />
			<p>name</p>
			<input type="text" ng-model="selectedEntity.name" ng-readonly="false" />
			<p>timeslotDate</p>
			<input type="text" ng-model="selectedEntity.timeslotDate"
				ng-readonly="false" />
			<p ng-click="showPersonDetail()" ng-if="selectedEntity.person==null">Add
				new person</p>
			<p ng-click="showPersonDetail()" ng-if="selectedEntity.person!=null">person:
				{{selectedEntity.person.personId}}</p>
			<p ng-click="showPlaceDetail()">Add new place</p>
			<div ng-if="selectedEntity.placeList.length&gt;0">
				<ul>
					<li ng-repeat="entity in selectedEntity.placeList"
						ng-click="showPlaceDetail($index)">{{$index}}--{{entity.placeId}}--{{entity.description}}</li>
				</ul>
			</div>
		</div>
		<div id="orderActionButton" ng-if="selectedEntity.show">
			<p>ACTION BUTTON</p>
			<button ng-click="insert()" ng-if="selectedEntity.orderId==undefined">Insert</button>
			<button ng-click="update()" ng-if="selectedEntity.orderId&gt;0">Update</button>
			<button ng-click="del()" ng-if="selectedEntity.orderId&gt;0">Delete</button>
		</div>
	</div>
	<div ng-controller="personController">
		<div id="personList" ng-if="entityList.length&gt;0" enctype="UTF-8">
			<p>LISTA</p>
			<ul>
				<li ng-repeat="entity in entityList"
					ng-click="showEntityDetail($index)"><p>{{$index}}
						{{entity.personId}} {{entity.firstName}} {{entity.lastName}}
						{{entity.birthDate}}</p></li>
			</ul>
		</div>
		<div id="personDetail" ng-if="selectedEntity.show">
			<p>DETAIL</p>
			<p>personId</p>
			<input type="text" ng-model="selectedEntity.personId"
				ng-readonly="true" />
			<p>firstName</p>
			<input type="text" ng-model="selectedEntity.firstName"
				ng-readonly="false" />
			<p>lastName</p>
			<input type="text" ng-model="selectedEntity.lastName"
				ng-readonly="false" />
			<p>birthDate</p>
			<input type="text" ng-model="selectedEntity.birthDate"
				ng-readonly="false" />
		</div>
		<div id="personActionButton" ng-if="selectedEntity.show">
			<p>ACTION BUTTON</p>
			<button ng-click="insert()"
				ng-if="selectedEntity.personId==undefined">Insert</button>
			<button ng-click="update()" ng-if="selectedEntity.personId&gt;0">Update</button>
			<button ng-click="del()" ng-if="selectedEntity.personId&gt;0">Delete</button>
		</div>
	</div>
	<div ng-controller="placeController">
		<div id="placeList" ng-if="entityList.length&gt;0" enctype="UTF-8">
			<p>LISTA</p>
			<ul>
				<li ng-repeat="entity in entityList"
					ng-click="showEntityDetail($index)"><p>{{$index}}
						{{entity.placeId}} {{entity.description}}</p></li>
			</ul>
		</div>
		<div id="placeDetail" ng-if="selectedEntity.show">
			<p>DETAIL</p>
			<p>placeId</p>
			<input type="text" ng-model="selectedEntity.placeId"
				ng-readonly="true" />
			<p>description</p>
			<input type="text" ng-model="selectedEntity.description"
				ng-readonly="false" />
		</div>
		<div id="placeActionButton" ng-if="selectedEntity.show">
			<p>ACTION BUTTON</p>
			<button ng-click="insert()" ng-if="selectedEntity.placeId==undefined">Insert</button>
			<button ng-click="update()" ng-if="selectedEntity.placeId&gt;0">Update</button>
			<button ng-click="del()" ng-if="selectedEntity.placeId&gt;0">Delete</button>
		</div>
	</div>
</body>