<head><title>test order</title><script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script><script>angular.module("placeApp",[])
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
for (i=0; i<entityList.length; i++)
this.entityList.push(entityList[i]);
};
this.searchBean = 		new Object();
this.resetSearchBean= function()
{
this.searchBean.orderId="";
this.searchBean.name="";
this.searchBean.timeslotDate="";
};
this.setSelectedEntity= function (entity)
{ 
if (entity==null)
{
entity={};
this.selectedEntity.show=false;
} //else
var keyList=Object.keys(entity);
if (keyList.length==0)
keyList=Object.keys(this.selectedEntity);
for (i=0; i<keyList.length; i++)
{
var val=keyList[i];
this.selectedEntity[val]=entity[val];
if (val!=undefined)
{
if (val.toLowerCase().indexOf("list")>-1 && (entity[val]==null || entity[val]==undefined) && typeof entity[val] == "object")
this.selectedEntity[val]=[];
if (val.toLowerCase().indexOf("date")>-1 && typeof val == "string")
{
var date= new Date(entity[val]);
this.selectedEntity[val]= new Date(date.getFullYear(),date.getMonth(),date.getDate());
}
}
}
};
})
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
this.setSelectedEntity= function (entity)
{ 
if (entity==null)
{
entity={};
this.selectedEntity.show=false;
} //else
var keyList=Object.keys(entity);
if (keyList.length==0)
keyList=Object.keys(this.selectedEntity);
for (i=0; i<keyList.length; i++)
{
var val=keyList[i];
this.selectedEntity[val]=entity[val];
if (val!=undefined)
{
if (val.toLowerCase().indexOf("list")>-1 && (entity[val]==null || entity[val]==undefined) && typeof entity[val] == "object")
this.selectedEntity[val]=[];
if (val.toLowerCase().indexOf("date")>-1 && typeof val == "string")
{
var date= new Date(entity[val]);
this.selectedEntity[val]= new Date(date.getFullYear(),date.getMonth(),date.getDate());
}
}
}
};
})
.controller("placeController",function($scope,$http,placeService,orderService)
{
$scope.searchBean=placeService.searchBean;
$scope.entityList=placeService.entityList;
$scope.selectedEntity=placeService.selectedEntity;
$scope.reset = function()
{
placeService.resetSearchBean();
placeService.selectedEntity.show=false;
orderService.selectedEntity.show=false;}
$scope.showEntityDetail= function(index)
{
placeService.indexSelected=index;
placeService.setSelectedEntity($scope.entityList[index]);
placeService.selectedEntity.show=true;
};
$scope.addNew= function()
{
placeService.setSelectedEntity(null);
placeService.selectedEntity.show=true;
orderService.selectedEntity.show=false;};
		
$scope.search=function()
{
placeService.selectedEntity.show=false;
$http.post("../place/search",placeService.searchBean)
.success( function(entityList) {
placeService.setEntityList(entityList);
})
.error(function() {
alert("error");
})
};
$scope.insert=function()
{
$http.put("../place/",placeService.selectedEntity)
.success( function(data) 
{
$scope.search();})
.error(function() 
{ 
alert("error");
});
};
$scope.update=function()
{
orderService.selectedEntity.show=false;$http.post("../place/",placeService.selectedEntity)
.success( function(data) {
$scope.search();
})
.error(function() { 
alert("error");
});
};
$scope.del=function()
{
var url="../place/"+placeService.selectedEntity.placeId;
$http["delete"](url)
.success( function(data) {
placeService.setSelectedEntity(null);
$scope.search();
})
.error(function() {
alert("error");
});
};$scope.showOrderDetail= function(index)
{
if (index!=null)
orderService.setSelectedEntity(placeService.selectedEntity.orderList[index]);
else 
orderService.setSelectedEntity(placeService.selectedEntity.order); 
orderService.selectedEntity.show=true;
};
})
.controller("orderController",function($scope,$http,placeService,orderService)
{
$scope.searchBean=orderService.searchBean;
$scope.entityList=orderService.entityList;
$scope.selectedEntity=orderService.selectedEntity;
$scope.reset = function()
{
orderService.resetSearchBean();
orderService.selectedEntity.show=false;
}
$scope.updateParent = function(toDo)
{
$http.post("../place/",placeService.selectedEntity)
.then(
function(response) {
if (response.status==200)
	{
placeService.setSelectedEntity(response.data);
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
};
		
$scope.search=function()
{
orderService.selectedEntity.show=false;
$http.post("../order/search",orderService.searchBean)
.success( function(entityList) {
orderService.setEntityList(entityList);
})
.error(function() {
alert("error");
})
};
$scope.insert=function()
{
orderService.selectedEntity.show=false;

placeService.selectedEntity.order=orderService.selectedEntity;

$scope.updateParent();

};
$scope.update=function()
{
orderService.selectedEntity.show=false;

placeService.selectedEntity.order=orderService.selectedEntity;

$scope.updateParent();
};
$scope.del=function()
{
orderService.selectedEntity.show=false;
placeService.selectedEntity.order=null;orderService.setSelectedEntity(null);
$scope.updateParent();
};})
;</script></head><body ng-app="placeApp"><div ng-controller="placeController"><div id="placeSearchBean"><p>placeId</p><input type="text" ng-model="searchBean.placeId" ng-readonly="false"/><p>description</p><input type="text" ng-model="searchBean.description" ng-readonly="false"/><button ng-click="addNew()">Add new</button><button ng-click="search()">Find</button><button ng-click="reset()">Reset</button></div><div id="placeList" ng-if="entityList.length&gt;0" enctype="UTF-8"><p>LISTA</p><ul><li ng-repeat="entity in entityList" ng-click="showEntityDetail($index)"><p>{{$index}} 
{{entity.placeId}}
{{entity.description}}
</p></li></ul></div><div id="placeDetail" ng-if="selectedEntity.show"><p>DETAIL</p><p>placeId</p><input type="text" ng-model="selectedEntity.placeId" ng-readonly="true"/><p>description</p><input type="text" ng-model="selectedEntity.description" ng-readonly="false"/><p ng-click="showOrderDetail()" ng-if="selectedEntity.order==null">Add new order</p><p ng-click="showOrderDetail()" ng-if="selectedEntity.order!=null">order: {{selectedEntity.order.orderId}}</p></div><div id="placeActionButton" ng-if="selectedEntity.show"><p>ACTION BUTTON</p><button ng-click="insert()" ng-if="selectedEntity.placeId==undefined">Insert</button><button ng-click="update()" ng-if="selectedEntity.placeId&gt;0">Update</button><button ng-click="del()" ng-if="selectedEntity.placeId&gt;0">Delete</button></div></div><div ng-controller="orderController"><div id="orderList" ng-if="entityList.length&gt;0" enctype="UTF-8"><p>LISTA</p><ul><li ng-repeat="entity in entityList" ng-click="showEntityDetail($index)"><p>{{$index}} 
{{entity.orderId}}
{{entity.name}}
{{entity.timeslotDate}}
</p></li></ul></div><div id="orderDetail" ng-if="selectedEntity.show"><p>DETAIL</p><p>orderId</p><input type="text" ng-model="selectedEntity.orderId" ng-readonly="true"/><p>name</p><input type="text" ng-model="selectedEntity.name" ng-readonly="false"/><p>timeslotDate</p><input type="text" ng-model="selectedEntity.timeslotDate" ng-readonly="false"/></div><div id="orderActionButton" ng-if="selectedEntity.show"><p>ACTION BUTTON</p><button ng-click="insert()" ng-if="selectedEntity.orderId==undefined">Insert</button><button ng-click="update()" ng-if="selectedEntity.orderId&gt;0">Update</button><button ng-click="del()" ng-if="selectedEntity.orderId&gt;0">Delete</button></div></div></body>