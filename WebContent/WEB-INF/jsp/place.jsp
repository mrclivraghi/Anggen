<head><title>test order</title><script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script><script>angular.module("placeApp",[])
.service("placeService", function()
{
this.entityList =		[];
this.selectedEntity= 	{show: false};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
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
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
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
.service("personService", function()
{
this.entityList =		[];
this.selectedEntity= 	{show: false};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
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
.controller("placeController",function($scope,$http,placeService,orderService,personService)
{
$scope.searchBean=placeService.searchBean;
$scope.entityList=placeService.entityList;
$scope.selectedEntity=placeService.selectedEntity;
$scope.childrenList=placeService.childrenList; 
$scope.reset = function()
{
placeService.resetSearchBean();
placeService.setSelectedEntity(null);
placeService.selectedEntity.show=false;
placeService.setEntityList(null); 
orderService.selectedEntity.show=false;personService.selectedEntity.show=false;}
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
orderService.selectedEntity.show=false;personService.selectedEntity.show=false;};
		
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
if (!$scope.placeDetailForm.$valid) return; 
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
if (!$scope.placeDetailForm.$valid) return; 
orderService.selectedEntity.show=false;personService.selectedEntity.show=false;$http.post("../place/",placeService.selectedEntity)
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
$scope.init=function()
{
$http.post("../order/search",{}).success(function(entityList) {placeService.childrenList.orderList=entityList;}).error(function() {alert("error");});}; 
$scope.init();
})
.controller("orderController",function($scope,$http,orderService,personService,placeService)
{
$scope.searchBean=orderService.searchBean;
$scope.entityList=orderService.entityList;
$scope.selectedEntity=orderService.selectedEntity;
$scope.childrenList=orderService.childrenList; 
$scope.reset = function()
{
orderService.resetSearchBean();
orderService.setSelectedEntity(null);
orderService.selectedEntity.show=false;
orderService.setEntityList(null); 
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
orderService.searchBean.placeList=[];
orderService.searchBean.placeList.push(orderService.searchBean.place);
delete orderService.searchBean.place; 
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
if (!$scope.orderDetailForm.$valid) return; 
orderService.selectedEntity.show=false;

placeService.selectedEntity.order=orderService.selectedEntity;

$scope.updateParent();

};
$scope.update=function()
{
if (!$scope.orderDetailForm.$valid) return; 
orderService.selectedEntity.show=false;

placeService.selectedEntity.order=orderService.selectedEntity;

$scope.updateParent();
};
$scope.del=function()
{
orderService.selectedEntity.show=false;
placeService.selectedEntity.order=null;orderService.setSelectedEntity(null);
$scope.updateParent();
};$scope.showPersonDetail= function(index)
{
if (index!=null)
personService.setSelectedEntity(orderService.selectedEntity.personList[index]);
else 
personService.setSelectedEntity(orderService.selectedEntity.person); 
personService.selectedEntity.show=true;
};
$scope.showPlaceDetail= function(index)
{
if (index!=null)
placeService.setSelectedEntity(orderService.selectedEntity.placeList[index]);
else 
placeService.setSelectedEntity(orderService.selectedEntity.place); 
placeService.selectedEntity.show=true;
};
$scope.init=function()
{
$http.post("../person/search",{}).success(function(entityList) {orderService.childrenList.personList=entityList;}).error(function() {alert("error");});$http.post("../place/search",{}).success(function(entityList) {orderService.childrenList.placeList=entityList;}).error(function() {alert("error");});}; 
$scope.init();
})
.controller("personController",function($scope,$http,personService)
{
$scope.searchBean=personService.searchBean;
$scope.entityList=personService.entityList;
$scope.selectedEntity=personService.selectedEntity;
$scope.childrenList=personService.childrenList; 
$scope.reset = function()
{
personService.resetSearchBean();
personService.setSelectedEntity(null);
personService.selectedEntity.show=false;
personService.setEntityList(null); 
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
personService.indexSelected=index;
personService.setSelectedEntity($scope.entityList[index]);
personService.selectedEntity.show=true;
};
$scope.addNew= function()
{
personService.setSelectedEntity(null);
personService.selectedEntity.show=true;
};
		
$scope.search=function()
{
personService.selectedEntity.show=false;
$http.post("../person/search",personService.searchBean)
.success( function(entityList) {
personService.setEntityList(entityList);
})
.error(function() {
alert("error");
})
};
$scope.insert=function()
{
if (!$scope.personDetailForm.$valid) return; 
personService.selectedEntity.show=false;

placeService.selectedEntity.person=personService.selectedEntity;

$scope.updateParent();

};
$scope.update=function()
{
if (!$scope.personDetailForm.$valid) return; 
personService.selectedEntity.show=false;

placeService.selectedEntity.person=personService.selectedEntity;

$scope.updateParent();
};
$scope.del=function()
{
personService.selectedEntity.show=false;
placeService.selectedEntity.person=null;personService.setSelectedEntity(null);
$scope.updateParent();
};$scope.init=function()
{
}; 
$scope.init();
})
;</script></head><body ng-app="placeApp"><div ng-controller="placeController"><form id="placeSearchBean"><p>placeId</p><input type="text" ng-model="searchBean.placeId" ng-readonly="false" name="placeId"/><p>description</p><input type="text" ng-model="searchBean.description" ng-readonly="false" name="description"/><p>order</p><select ng-model="searchBean.order.orderId" ng-options="order.orderId as  order.name for order in childrenList.orderList" enctype="UTF-8"></select><button ng-click="addNew()">Add new</button><button ng-click="search()">Find</button><button ng-click="reset()">Reset</button></form><form id="placeList" ng-if="entityList.length&gt;0" enctype="UTF-8"><p>LISTA</p><ul><li ng-repeat="entity in entityList" ng-click="showEntityDetail($index)"><p>{{$index}} 
{{entity.placeId}}
{{entity.description}}
</p></li></ul></form><form id="placeDetailForm" name="placeDetailForm" ng-show="selectedEntity.show"><p>DETAIL</p><p>placeId</p><input type="text" ng-model="selectedEntity.placeId" ng-readonly="false" name="placeId"/><p>description</p><input type="text" ng-model="selectedEntity.description" ng-readonly="false" name="description"/><p ng-click="showOrderDetail()" ng-if="selectedEntity.order==null">Add new order</p><p ng-click="showOrderDetail()" ng-if="selectedEntity.order!=null">order: {{selectedEntity.order.orderId}}</p></form><form id="placeActionButton" ng-if="selectedEntity.show"><p>ACTION BUTTON</p><button ng-click="insert()" ng-if="selectedEntity.placeId==undefined">Insert</button><button ng-click="update()" ng-if="selectedEntity.placeId&gt;0">Update</button><button ng-click="del()" ng-if="selectedEntity.placeId&gt;0">Delete</button></form></div><div ng-controller="orderController"><form id="orderList" ng-if="entityList.length&gt;0" enctype="UTF-8"><p>LISTA</p><ul><li ng-repeat="entity in entityList" ng-click="showEntityDetail($index)"><p>{{$index}} 
{{entity.orderId}}
{{entity.name}}
{{entity.timeslotDate}}
</p></li></ul></form><form id="orderDetailForm" name="orderDetailForm" ng-show="selectedEntity.show"><p>DETAIL</p><p>orderId</p><input type="text" ng-model="selectedEntity.orderId" ng-readonly="false" name="orderId"/><p>name</p><input type="text" ng-model="selectedEntity.name" ng-readonly="false" name="name"/><p>timeslotDate</p><input type="date" ng-model="selectedEntity.timeslotDate" ng-readonly="false" name="timeslotDate"/></form><form id="orderActionButton" ng-if="selectedEntity.show"><p>ACTION BUTTON</p><button ng-click="insert()" ng-if="selectedEntity.orderId==undefined">Insert</button><button ng-click="update()" ng-if="selectedEntity.orderId&gt;0">Update</button><button ng-click="del()" ng-if="selectedEntity.orderId&gt;0">Delete</button></form></div><div ng-controller="personController"><form id="personList" ng-if="entityList.length&gt;0" enctype="UTF-8"><p>LISTA</p><ul><li ng-repeat="entity in entityList" ng-click="showEntityDetail($index)"><p>{{$index}} 
{{entity.personId}}
{{entity.firstName}}
{{entity.lastName}}
{{entity.birthDate}}
</p></li></ul></form><form id="personDetailForm" name="personDetailForm" ng-show="selectedEntity.show"><p>DETAIL</p><p>personId</p><input type="text" ng-model="selectedEntity.personId" ng-readonly="false" name="personId"/><p>firstName</p><input type="text" ng-model="selectedEntity.firstName" ng-readonly="false" name="firstName"/><p>lastName</p><input type="text" ng-model="selectedEntity.lastName" ng-readonly="false" name="lastName"/><p>birthDate</p><input type="date" ng-model="selectedEntity.birthDate" ng-readonly="false" name="birthDate"/></form><form id="personActionButton" ng-if="selectedEntity.show"><p>ACTION BUTTON</p><button ng-click="insert()" ng-if="selectedEntity.personId==undefined">Insert</button><button ng-click="update()" ng-if="selectedEntity.personId&gt;0">Update</button><button ng-click="del()" ng-if="selectedEntity.personId&gt;0">Delete</button></form></div></body>