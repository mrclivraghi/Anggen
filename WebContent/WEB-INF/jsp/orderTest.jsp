<head><title>test order</title><script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script><script>
angular.module("orderTestApp",[])
.service("orderTestService", function(){
this.orderTestList=[];
this.orderTestForm= new Object();
this.setOrderTestList= function(data) {
while (this.orderTestList.length>0)
this.orderTestList.pop();
for (i=0; i<data.length; i++)
{
var tempOrderTest= new Object();
tempOrderTest.orderTestId=data[i].orderTestId;
tempOrderTest.name=data[i].name;
tempOrderTest.timeslotDate=data[i].timeslotDate;
this.orderTestList[i]=tempOrderTest;
}
};
this.resetForm = function (orderTest) {
this.orderTestForm.orderTestId= (orderTest==null || orderTest== undefined )? "" : orderTest.orderTestId;
this.orderTestForm.name= (orderTest==null || orderTest== undefined )? "" : orderTest.name;
this.orderTestForm.timeslotDate= (orderTest==null || orderTest== undefined )? "" : orderTest.timeslotDate;
};
this.addOrderTest= function(orderTest) {
this.orderTestList.push(orderTest);
this.resetForm(orderTest);
};
})
.controller("orderTestController",
function($scope,orderTestService) {
$scope.orderTestForm=orderTestService.orderTestForm;
}
)
.controller("orderTestRetrieveController", function ($scope,$http,orderTestService) {
$scope.reset = function ()
{
orderTestService.resetForm();
};
$scope.search = function() {
$http.post("../orderTest/search",orderTestService.orderTestForm)
.success( function(data) {
orderTestService.setOrderTestList(data);
})
.error(function() { alert("error");});
};
$scope.insert = function() 
{
$http.put("../orderTest/",orderTestService.orderTestForm)
.success( function(data) 
{
orderTestService.addOrderTest(data);
})
.error(function() 
{ 
alert("error");
});
};
$scope.update = function() {
$http.post("../orderTest/",orderTestService.orderTestForm)
.success( function(data) {
$scope.search();
	})
.error(function() { alert("error");});
};
$scope.del = function() {
var url="../orderTest/"+orderTestService.orderTestForm.orderTestId;
$http["delete"](url)
.success( function(data) {
orderTestService.resetForm();
$scope.search();
})
.error(function() { alert("error");});
};
})
.controller("orderTestListController", function($scope,orderTestService,dateFilter)
{
$scope.orderTestList=orderTestService.orderTestList;
$scope.refreshForm = function (index) 
{
date= new Date(orderTestService.orderTestList[index].timeslotDate);
orderTestService.orderTestList[index].timeslotDate= new Date(date.getFullYear(),date.getMonth(),date.getDate());
orderTestService.resetForm(orderTestService.orderTestList[index]);
};
});
</script></head><body><div ng-app="orderTestApp"><div ng-controller="orderTestController"><p><input type="text" ng-model="orderTestForm.orderTestId"/></p><p><input type="text" ng-model="orderTestForm.name"/></p><p><input type="date" ng-model="orderTestForm.timeslotDate"/></p></div><div ng-controller="orderTestRetrieveController"><button ng-click="search()">search</button></div><div ng-controller="orderTestRetrieveController"><button ng-click="insert()">insert</button></div><div ng-controller="orderTestRetrieveController"><button ng-click="update()">update</button></div><div ng-controller="orderTestRetrieveController"><button ng-click="del()">del</button></div><div ng-controller="orderTestRetrieveController"><button ng-click="reset()">reset</button></div><div ng-controller="orderTestListController"><ul><li ng-repeat="orderTest in orderTestList"><p ng-click="refreshForm($index)">{{$index}} 
{{orderTest.orderTestId}}
{{orderTest.name}}
{{orderTest.timeslotDate | date: 'dd-MM-yyyy'}}
</p></li></ul></div></div></body>