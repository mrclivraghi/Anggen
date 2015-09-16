<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>No Scope Controller</title>
		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
		<script type="text/javascript">
		angular.module("orderApp",[])
		.service("orderService", function(){
			this.orderList=[];
			this.orderForm= new Object();//{orderId: -1, name: "nome", timeslotDate: Date.now()};
			this.setOrderList= function(data) {
				//TODO check
				//this.orderList= JSON.parse(data);
				 while (this.orderList.length>0)
					this.orderList.pop();
				for (i=0; i<data.length; i++)
				{
					var tempOrder= new Object();
					tempOrder.orderId=data[i].orderId;
					tempOrder.name=data[i].name;
					tempOrder.timeslotDate=data[i].timeslotDate;
					this.orderList[i]=tempOrder;
				}
			};
		})
		.controller("orderController",
			function($scope,orderService) {
				$scope.orderForm=orderService.orderForm;
		}		
		)
		.controller("orderRetrieveController", function ($scope,$http,orderService) {
			 $scope.search = function() {
					$http.post("../order/search",orderService.orderForm)
						.success( function(data) {
					orderService.setOrderList(data);
				})
				.error(function() { alert("error");});
			};
			$scope.insert = function() {
				$http.put("../order/",orderService.orderForm)
					.success( function(data) {
				$scope.search();
			})
			.error(function() { alert("error");});
		};
		
		$scope.update = function() {
			$http.post("../order/",orderService.orderForm)
				.success( function(data) {
					$scope.search();
		})
		.error(function() { alert("error");});
	};
	
	$scope.del = function() {
		var url="../order/"+orderService.orderForm.orderId;
		$http.delete(url)
			.success( function(data) {
				$scope.search();
	})
	.error(function() { alert("error");});
};
	
	
		})
		.controller("orderListController", function($scope,orderService)
				{
					$scope.orderList=orderService.orderList;	
				})
		;
		
		
		</script>
</head>
<body>
<div ng-app="orderApp">
		<div ng-controller="orderController"> <!-- definisco il controller -->
			<p>OrderId: <input type="text" ng-model="orderForm.orderId"></p> <!-- definisco gli attr. del model -->
			<p>Name: <input type="text" ng-model="orderForm.name"></p>
			<p>Timeslot date: <input type="text" ng-model="orderForm.timeslotDate"></p>
		</div>
		<div ng-controller="orderRetrieveController">
		<button  ng-click="search()">Search</button>
		<button ng-click="insert()">Insert</button>
		<button ng-click="update()">Update</button>
		<button ng-click="del()">Delete</button>
		</div>
		<div ng-controller="orderListController">
			<ul>
				<li ng-repeat="order in orderList">{{index}} {{order.orderId}} {{order.name}} {{order.timeslotDate | date: 'dd-MM-yyyy'}}</li>
			</ul>
		</div>
	</div>
</body>
</html>