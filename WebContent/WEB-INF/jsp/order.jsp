<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order</title>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<script type="text/javascript">
var test;

angular.module("orderApp",[])
.service("orderService", function()
{
	this.entityList =		[];
	this.selectedEntity= 	{show: false};
	this.addEntity=function (entity)
	{
		this.entityList.push(entity);
	};
	this.setEntityList= function(entityList)
	{ 
		while (this.entityList.length>0)
			this.entityList.pop();
		for (i=0; i<entityList.length; i++)
			this.entityList.push(entityList[i]);
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
	{ // to do field mgmt
		if (entity==null) 
		{
			entity={};
			this.selectedEntity.show=false;
		} //else
		
			var keyList=Object.keys(entity);
		for (i=0; i<keyList.length; i++)
		{
			var val=keyList[i];
			this.selectedEntity[val]=entity[val];
			if (val!=undefined)
				{
			  //check list
			  if (val.toLowerCase().indexOf("list")>-1 && (entity[val]==null || entity[val]==undefined) && typeof entity[val] == "object")
				  this.selectedEntity[val]=[];
			  //check date
			  if (val.toLowerCase().indexOf("date")>-1 && typeof val == "string")
			  {
				  	var date= new Date(entity[val]);
				  	this.selectedEntity[val]= new Date(date.getFullYear(),date.getMonth(),date.getDate());
			  }
				}
			  
		}/*
		this.selectedEntity.orderId=entity.orderId;
		this.selectedEntity.name=entity.name;
		var date= new Date(entity.timeslotDate);
		this.selectedEntity.timeslotDate= new Date(date.getFullYear(),date.getMonth(),date.getDate());
		this.selectedEntity.person=entity.person;
		if (entity.person!=undefined && entity.person!=null)
		{	
			this.selectedEntity.person.personId=entity.person.personId;
		} // else	this.selectedEntity.person={};
		this.selectedEntity.placeList=entity.placeList;
		if (entity.placeList!=undefined && entity.placeList!=null)
		{	//fill the list
			this.selectedEntity.placeList=entity.placeList;
		} else
			this.selectedEntity.placeList=[]; */
	};
	
})
.service("personService",function()
{
	this.entityList =		[];
	this.selectedEntity= 	{show: false};
	this.addEntity=function (entity)
	{
		this.entityList.push(entity);
	};
	this.setEntityList= function(entityList)
	{ 
		while (this.entityList.length>0)
			this.entityList.pop();
		for (entity in entityList)
			this.entityList.push(entity);
	};
	
	this.setSelectedEntity= function(entity)
	{ //TODO FIELD MGMT
		if (entity==null) 
		{
			entity={};
			this.selectedEntity.show=false;
		}
		this.selectedEntity.personId=entity.personId;
		this.selectedEntity.firstName=entity.firstName;
		this.selectedEntity.lastName=entity.lastName;
		var date= new Date(entity.birthDate);
		this.selectedEntity.birthDate= new Date(date.getFullYear(),date.getMonth(),date.getDate());
	};
})
.service("placeService", function()
{
	this.entityList =		[];
	this.selectedEntity= 	{show: false};
	this.addEntity=function (entity)
	{
		this.entityList.push(entity);
	};
	this.setEntityList= function(entityList)
	{ 
		while (this.entityList.length>0)
			this.entityList.pop();
		for (entity in entityList)
			this.entityList.push(entity);
	};
	this.setSelectedEntity= function(entity)
	{ //TODO FIELD MGMT
		if (entity==null) 
		{
			entity={};
			this.selectedEntity.show=false;
		}
		this.selectedEntity.placeId=entity.placeId;
		this.selectedEntity.description=entity.description;
	};
	
})
.controller("orderController",function($scope,$http,orderService,personService,placeService)
{
	//search var
	$scope.searchBean		=	orderService.searchBean;
	
	$scope.entityList		=	orderService.entityList;
	$scope.selectedEntity	=	orderService.selectedEntity;
	
	
	//search function
	$scope.reset = function()
	{
		orderService.resetSearchBean();
		orderService.selectedEntity.show=false;
		personService.selectedEntity.show=false;
		placeService.selectedEntity.show=false;
	}
	
	$scope.showEntityDetail= function(index)
	{
		orderService.indexSelected=index;
		orderService.setSelectedEntity($scope.entityList[index]);
		orderService.selectedEntity.show=true;
	};
	
	$scope.addNew= function()
	{
		orderService.setSelectedEntity(null);
		orderService.selectedEntity.show=true;
		personService.selectedEntity.show=false;
		placeService.selectedEntity.show=false;
		
	};
	
	//REST
	//search function
	$scope.search=function()
	{
		orderService.selectedEntity.show=false;
		
		$http.post("../order/search",orderService.searchBean)
					.success( function(entityList) {
							orderService.setEntityList(entityList);
					})
					.error(function() {
							alert("error");
					});
	};
	$scope.insert=function()
	{
		$http.put("../order/",orderService.selectedEntity)
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
		placeService.selectedEntity.show=false;
		personService.selectedEntity.show=false;
		$http.post("../order/",orderService.selectedEntity)
				.success( function(data) {
					$scope.search();
				})
				.error(function() { 
					alert("error");
				});
	};
	$scope.del=function()
	{
		var url="../order/"+orderService.selectedEntity.orderId;
		$http["delete"](url)
				.success( function(data) {
					orderService.setSelectedEntity(null);
					$scope.search();
				})
				.error(function() { 
					alert("error");
				});
	};
	//relationships
	$scope.showPersonDetail= function()
	{
		personService.setSelectedEntity(orderService.selectedEntity.person);
		personService.selectedEntity.show=true;
	};
	$scope.showPlaceDetail=function(index)
	{
		placeService.selectedEntity.show=true;
		if (index!=null)
				placeService.setSelectedEntity(orderService.selectedEntity.placeList[index]);
	}
	
})
.controller("personController",function($scope,$http,orderService,personService)
{
	$scope.selectedEntity	= 	personService.selectedEntity;
	$scope.entityList		= 	personService.entityList;
	
	//standard
	$scope.updateParent = function(toDo)
	{
		$http.post("../order/",orderService.selectedEntity)
			.then(
				function(response) {
					if (response.status==200)
						{
						
					orderService.setSelectedEntity(response.data);
					if (toDo!=null)
						toDo(); 
					if (!$scope.$$phase)
						$rootScope.$digest();
						
						}
				}
			,function(error) {
					alert("error");
				
				});
					
			
		
	};
	
	$scope.insert = function()
	{
		personService.selectedEntity.show=false;
		orderService.selectedEntity.person=personService.selectedEntity;
		$scope.updateParent();
	};
	
	$scope.update= function()
	{
		
		personService.selectedEntity.show=false;
		orderService.selectedEntity.person=personService.selectedEntity;
		$scope.updateParent();
	};
	
	$scope.del=function()
	{
		
		personService.setSelectedEntity(null);
		orderService.selectedEntity.person=null;
		$scope.updateParent();
	};

})
.controller("placeController",function($scope,$http,orderService,placeService)
{
	$scope.selectedEntity=placeService.selectedEntity;
	$scope.entityList=placeService.entityList;

	
	//standard
	$scope.updateParent = function(toDo)
	{
		$http.post("../order/",orderService.selectedEntity)
			.then(
				function(response) {
					if (response.status==200)
						{
						
					orderService.setSelectedEntity(response.data);
					placeService.setSelectedEntity(null);
					if (toDo!=null)
						toDo(); 
					if (!$scope.$$phase)
						$rootScope.$digest();
						
						}
				}
			,function(error) {
					alert("error");
				
				});
	};
	
	$scope.insert = function()
	{
		placeService.selectedEntity.show=false;
		orderService.selectedEntity.placeList.push(placeService.selectedEntity);
		$scope.updateParent();
	};
	$scope.update= function()
	{
		for (i=0; i<orderService.selectedEntity.placeList.length; i++)
			{
				if (orderService.selectedEntity.placeList[i].placeId==placeService.selectedEntity.placeId)
					orderService.selectedEntity.placeList.splice(i,1);
			}
		placeService.selectedEntity.show=false;
		orderService.selectedEntity.placeList.push(placeService.selectedEntity);
		$scope.updateParent();
	};
	
	$scope.del=function()
	{
		for (i=0; i<orderService.selectedEntity.placeList.length; i++)
			{
				if (orderService.selectedEntity.placeList[i].placeId==placeService.selectedEntity.placeId)
					orderService.selectedEntity.placeList.splice(i,1);
			}
		placeService.setSelectedEntity(null);
		$scope.updateParent();
	};

	
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
		<div id="orderList" ng-if="entityList.length>0">LISTA
			<ul>
				<li ng-repeat="entity in entityList"  ng-click="showEntityDetail($index)">
					{{$index}}-{{entity.orderId}}--{{entity.name}}--{{entity.timeslotDate | date: 'dd-MM-yyyy'}}
				</li>
			</ul>
		</div>
		<div id="orderDetail" ng-if="selectedEntity.show">DETAIL
			<p>OrderId <input type="text" ng-model="selectedEntity.orderId"></p>
			<p>Name <input type="text" ng-model="selectedEntity.name"></p>
			<p>TimeslotDate <input type="date" ng-model="selectedEntity.timeslotDate"></p>
			<p ng-click="showPersonDetail()" ng-if="selectedEntity.person!=null">Person {{selectedEntity.person.personId}}</p>
			<p ng-click="showPersonDetail()" ng-if="selectedEntity.person==null">Add new person 
			</p>
			<p ng-click="showPlaceDetail()" >Add new place 
			</p>
			<div ng-if="selectedEntity.placeList.length>0">
				<ul>
					<li ng-repeat="entity in selectedEntity.placeList" ng-click="showPlaceDetail($index)">{{$index}}--{{entity.placeId}}--{{entity.description}}</li>
				</ul>
			</div>
		</div>
		<div id="orderActionButton" ng-if="selectedEntity.show">ACTION BUTTON
			<button ng-click="insert()" ng-if="selectedEntity.orderId==undefined">Insert</button>
			<button ng-click="update()" ng-if="selectedEntity.orderId>0">Update</button>
			<button ng-click="del()" ng-if="selectedEntity.orderId>0">Delete</button>
		</div>
	</div>
	
	<!-- PERSON -->
	<div ng-controller="personController">
		<div id="personSearchBean" ng-if="false"> 
			<p>Name <input type="text" ng-model="searchBean.name"></p>
			<p>TimeslotDate <input type="date" ng-model="searchBean.timeslotDate"></p>
			<button ng-click="addNew()">Add new</button><button ng-click="search()">Find</button><button ng-click="reset()">Reset</button>
		</div>
		<div id="personList" ng-if="entityList.length>0">LISTA
			<ul>
				<li ng-repeat="entity in entityList"  ng-click="showEntityDetail($index)">
					{{$index}}-{{entity.orderId}}--{{entity.name}}--{{entity.timeslotDate | date: 'dd-MM-yyyy'}}
				</li>
			</ul>
		</div>
		<div id="personDetail" ng-if="selectedEntity.show">DETAIL PERSON
			<p>PersonId <input type="text" ng-model="selectedEntity.personId"></p>
			<p>FirstName <input type="text" ng-model="selectedEntity.firstName"></p>
			<p>LastName <input type="text" ng-model="selectedEntity.lastName"></p>
			<p>Birth Date <input type="date" ng-model="selectedEntity.birthDate"></p>
		</div>
		<div id="personActionButton" ng-if="selectedEntity.show">ACTION BUTTON
			<button ng-click="insert()" ng-if="selectedEntity.personId==undefined">Insert</button>
			<button ng-click="update()" ng-if="selectedEntity.personId>0">Update</button>
			<button ng-click="del()" ng-if="selectedEntity.personId>0">Delete</button>
		</div>
	</div>
	
	<!-- PLACE -->
	<div ng-controller="placeController">
		<div id="placeSearchBean" ng-if="false">
			<p>Name <input type="text" ng-model="searchBean.name"></p>
			<p>TimeslotDate <input type="date" ng-model="searchBean.timeslotDate"></p>
			<button ng-click="addNew()">Add new</button><button ng-click="search()">Find</button><button ng-click="reset()">Reset</button>
		</div>
		<div id="placeList" ng-if="entityList.length>0">LISTA
			<ul>
				<li ng-repeat="entity in entityList"  ng-click="showEntityDetail($index)">
					{{$index}}-{{entity.orderId}}--{{entity.name}}--{{entity.timeslotDate | date: 'dd-MM-yyyy'}}
				</li>
			</ul>
		</div>
		<div id="placeDetail" ng-if="selectedEntity.show">DETAIL PERSON
			<p>placeId <input type="text" ng-model="selectedEntity.placeId"></p>
			<p>Description <input type="text" ng-model="selectedEntity.description"></p>
		</div>
		<div id="placeActionButton" ng-if="selectedEntity.show">ACTION BUTTON
			<button ng-click="insert()" ng-if="selectedEntity.placeId==undefined">Insert</button>
			<button ng-click="update()" ng-if="selectedEntity.placeId>0">Update</button>
			<button ng-click="del()" ng-if="selectedEntity.placeId>0">Delete</button>
		</div>
	</div>	
</body>
</html>