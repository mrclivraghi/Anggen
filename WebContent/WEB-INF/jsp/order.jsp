<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order</title>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.24/angular.min.js"></script>
<script type="text/javascript">
angular.module("orderApp",[])
.service("orderService", function()
{
	this.entityList =		null;
	this.selectedEntity= 	new Object();
	this.addEntity=function (entity)
	{
		this.entityList.push(entity);
	};
	this.setEntityList= function(entityList)
	{ 
		this.entityList=entityList;
	};
	
	//search just in case of the father
	this.searchBean = 		new Object();
	this.resetSearchBean= function()
	{
		this.searchBean.orderId="";
		this.searchBean.name="";
		this.searchBean.timeslotDate="";
	};
	
	this.setSelectedEntity= function (entity)
	{ ///TODO FIELD MGMT
		this.selectedEntity = new Object();
		this.selectedEntity.orderId=entity.orderId;
		this.selectedEntity.name=entity.name;
		var date= new Date(entity.timeslotDate);
		this.selectedEntity.timeslotDate= new Date(date.getFullYear(),date.getMonth(),date.getDate());
		this.selectedEntity.person=entity.person;
		if (entity.person!=undefined && entity.person!=null)
			{
			this.selectedEntity.person.personId=entity.person.personId;
			}
	};
	
})
.service("personService",function()
{
	this.entityList =		null;
	this.selectedEntity= 	null;
	this.addEntity=function (entity)
	{
		this.entityList.push(entity);
	};
	this.setEntityList= function(entityList)
	{ 
		this.entityList=entityList;
	};
	
	this.setSelectedEntity= function(entity)
	{ //TODO FIELD MGMT
		if (this.selectedEntity==null)
			this.selectedEntity = new Object();
		if (entity!=null)
		{
				this.selectedEntity.personId=entity.personId;
				this.selectedEntity.firstName=entity.firstName;
				this.selectedEntity.lastName=entity.lastName;
				var date= new Date(entity.birthDate);
				this.selectedEntity.birthDate= new Date(date.getFullYear(),date.getMonth(),date.getDate());
		}
	};
	

})
.service("placeService", function()
{
	this.entityList =		null;
	this.selectedEntity= 	null;
	this.addEntity=function (entity)
	{
		this.entityList.push(entity);
	};
	this.setEntityList= function(entityList)
	{ 
		this.entityList=entityList;
	};
	
})
.controller("orderController",function($scope,$http,orderService,personService,placeService)
{
	//search var
	$scope.searchBean=orderService.searchBean;
	
	$scope.showEntityList=false;
	$scope.entityList=orderService.entityList;
	$scope.selectedEntity=	null;
	
	
	//search function
	$scope.reset = function()
	{
		orderService.resetSearchBean();
	}
	
	$scope.showEntityDetail= function(index)
	{
		orderService.setSelectedEntity($scope.entityList[index]);
		$scope.selectedEntity=orderService.selectedEntity;
	};
	
	$scope.addNew= function()
	{
		$scope.selectedEntity= new Object();
	};
	
	//REST
	//search function
	$scope.search=function()
	{
		$scope.selectedEntity=null;
		$http.post("../order/search",orderService.searchBean)
					.success( function(entityList) {
							orderService.setEntityList(entityList);
							$scope.showEntityList=true;
							$scope.entityList=orderService.entityList;
					})
					.error(function() {
							alert("error");
					});
	};
	$scope.insert=function()
	{
		$http.put("../order/",$scope.selectedEntity)
				.success( function(data) 
				{
					$scope.search();
					
				})
				.error(function() 
				{ 
					alert("error");
				});
	};
	$scope.update=function()
	{
		$http.post("../order/",$scope.selectedEntity)
				.success( function(data) {
					$scope.search();
				})
				.error(function() { 
					alert("error");
				});
	};
	$scope.del=function()
	{
		var url="../order/"+$scope.selectedEntity.orderId;
		$http["delete"](url)
				.success( function(data) {
					$scope.reset();
					$scope.search();
				})
				.error(function() { 
					alert("error");
				});
	};
	//manage relationships
	$scope.showPersonDetail= function()
	{
		personService.setSelectedEntity($scope.selectedEntity.person);
	};
	$scope.checkPersonDetail = function()
	{
		alert("service:"+personService.selectedEntity.personId+"-controller:"+$scope.selectedEntity.personId);
	}
})
.controller("personController",function($scope,$http,orderService,personService)
{
	$scope.selectedEntity= personService.selectedEntity;
	
	//standard
	$scope.updateParent = function(toDo)
	{
		$http.post("../order/",orderService.selectedEntity)
		.success(
				function(data) {
					//alert(data.person.personId);
					 orderService.entityList[personService.parentIndex]=data;
					orderService.setSelectedEntity(data);
					personService.selectedEntity=null;
					if (toDo!=null)
						toDo(); 
					while ($scope.$$phase) {}
							$scope.$digest();
				}).error(function() {
					alert("error");
				
				}
			
		);
	};
	$scope.showEntity= function ()
	{
		$scope.selectedEntity=personService.selectedEntity;
		return $scope.selectedEntity!=null;
	};
	
	$scope.insert = function()
	{
		orderService.selectedEntity.person=personService.selectedEntity;
		$scope.updateParent();
	};
	
	$scope.update= function()
	{
		orderService.selectedEntity.person=personService.selectedEntity;
		$scope.updateParent();
	};
	
	$scope.del=function()
	{
		orderService.selectedEntity.person=null;
		$scope.updateParent();
	};

})
.controller("placeController",function($scope,$http,orderService)
{


});

</script>
</head>
<body ng-app="orderApp">
	<!-- ORDER	 -->
	<div ng-controller="orderController">
		<div id="orderSearchBean">
			<p>Name <input type="text" ng-model="searchBean.name"></p>
			<p>TimeslotDate <input type="date" ng-model="searchBean.timeslotDate"></p>
			<button ng-click="addNew()">Add new</button><button ng-click="search()">Find</button><button ng-click="reset()">Reset</button>
		</div>
		<div id="orderList" ng-if="entityList!=null">LISTA
			<ul>
				<li ng-repeat="entity in entityList"  ng-click="showEntityDetail($index)">
					{{$index}}-{{entity.orderId}}--{{entity.name}}--{{entity.timeslotDate | date: 'dd-MM-yyyy'}}
				</li>
			</ul>
		</div>
		<div id="orderDetail" ng-if="selectedEntity!=null">DETAIL
			<p>OrderId <input type="text" ng-model="selectedEntity.orderId"></p>
			<p>Name <input type="text" ng-model="selectedEntity.name"></p>
			<p>TimeslotDate <input type="date" ng-model="selectedEntity.timeslotDate"></p>
			<p ng-click="showPersonDetail()" ng-if="selectedEntity.person!=null">Person {{selectedEntity.person.personId}}</p>
			<p ng-click="showPersonDetail()" ng-if="selectedEntity.person==null">Add new person {{selectedEntity.person}}
			<button ng-click="checkPersonDetail()">check</button>
			</p>
			
			
		</div>
		<div id="orderActionButton" ng-if="selectedEntity!=null">ACTION BUTTON
			<button ng-click="insert()" ng-if="selectedEntity.orderId==undefined">Insert</button>
			<button ng-click="update()" ng-if="selectedEntity.orderId>0">Update</button>
			<button ng-click="del()" ng-if="selectedEntity.orderId>0">Delete</button>
		</div>
	</div>
	
	<!-- PERSON -->
	<div ng-controller="personController">
		<div id="personSearchBean" ng-if="false"> <!--  TODO DYNAMIC -->
			<p>Name <input type="text" ng-model="searchBean.name"></p>
			<p>TimeslotDate <input type="date" ng-model="searchBean.timeslotDate"></p>
			<button ng-click="addNew()">Add new</button><button ng-click="search()">Find</button><button ng-click="reset()">Reset</button>
		</div>
		<div id="personList" ng-if="entityList!=null">LISTA
			<ul>
				<li ng-repeat="entity in entityList"  ng-click="showEntityDetail($index)">
					{{$index}}-{{entity.orderId}}--{{entity.name}}--{{entity.timeslotDate | date: 'dd-MM-yyyy'}}
				</li>
			</ul>
		</div>
		<div id="personDetail" ng-if="showEntity()">DETAIL PERSON
			<p>PersonId <input type="text" ng-model="selectedEntity.personId"></p>
			<p>FirstName <input type="text" ng-model="selectedEntity.firstName"></p>
			<p>LastName <input type="text" ng-model="selectedEntity.lastName"></p>
			<p>Birth Date <input type="date" ng-model="selectedEntity.birthDate"></p>
		</div>
		<div id="personActionButton" ng-if="showEntity()">ACTION BUTTON
			<button ng-click="insert()" ng-if="selectedEntity.personId==undefined">Insert</button>
			<button ng-click="update()" ng-if="selectedEntity.personId>0">Update</button>
			<button ng-click="del()" ng-if="selectedEntity.personId>0">Delete</button>
		</div>
		
	</div>
</body>
</html>