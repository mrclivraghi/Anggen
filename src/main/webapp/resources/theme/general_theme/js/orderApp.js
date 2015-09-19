		angular.module("orderApp",[])
		.service("orderService", function(){
			this.orderList=[];
			this.orderForm= new Object();//{orderTestId: -1, name: "nome", timeslotDate: Date.now()};
			this.setOrderList= function(data) {
				//TODO check
				//this.orderList= JSON.parse(data);
				 while (this.orderList.length>0)
					this.orderList.pop();
				for (i=0; i<data.length; i++)
				{
					var tempOrder= new Object();
					tempOrder.orderTestId=data[i].orderTestId;
					tempOrder.name=data[i].name;
					tempOrder.timeslotDate=data[i].timeslotDate;
					this.orderList[i]=tempOrder;
				}
			};
			this.resetForm = function (order) {
				this.orderForm.orderTestId= (order==null || order== undefined )? "" : order.orderTestId;
				this.orderForm.name=(order==null || order== undefined )? "" : order.name;
				this.orderForm.timeslotDate=(order==null || order== undefined )? "" : order.timeslotDate;
			};
			this.addOrder= function(order) {
					this.orderList.push(order);
					this.resetForm(order);
			};
			
		})
		.controller("orderController",
			function($scope,orderService) {
				$scope.orderForm=orderService.orderForm;
		}		
		)
		.controller("orderRetrieveController", function ($scope,$http,orderService) {
			$scope.reset = function ()
			{
				orderService.resetForm();
			};
			$scope.search = function() {
					$http.post("../orderTest/search",orderService.orderForm)
						.success( function(data) {
					orderService.setOrderList(data);
				})
				.error(function() { alert("error");});
			};
			$scope.insert = function() 
			{
				$http.put("../orderTest/",orderService.orderForm)
					.success( function(data) 
							{
								orderService.addOrder(data);
							})
					.error(function() 
							{ 
								alert("error");
							});
			};
		$scope.update = function() {
			$http.post("../orderTest/",orderService.orderForm)
				.success( function(data) {
					$scope.search();
		})
		.error(function() { alert("error");});
	};
	$scope.del = function() {
		var url="../orderTest/"+orderService.orderForm.orderTestId;
		$http["delete"](url)
			.success( function(data) {
				orderService.resetForm();
				$scope.search();
	})
	.error(function() { alert("error");});
};
		})
		.controller("orderListController", function($scope,orderService,dateFilter)
				{
					$scope.orderList=orderService.orderList;
					
					$scope.refreshForm = function (index) 
					{
						var date= new Date(orderService.orderList[index].timeslotDate);
							orderService.orderList[index].timeslotDate= new Date(date.getFullYear(),date.getMonth(),date.getDate());//dateFilter(date,"dd/MM/yyyy");
							orderService.resetForm(orderService.orderList[index]);
					};
				})
		;
		
		