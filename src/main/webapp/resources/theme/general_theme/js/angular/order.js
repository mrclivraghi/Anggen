var orderApp=angular.module("orderApp",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date'])
.service("orderService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,placeList: []};
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
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& typeof entity[val] == "object") {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
}
} else {
if (val.toLowerCase().indexOf("date") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(
date.getFullYear(), date
.getMonth(), date
.getDate());
} else {
this.selectedEntity[val] = entity[val];
}
}
	}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../order/search",this.searchBean)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
};
this.insert = function() {
var promise= $http.put("../order/",this.selectedEntity)
.then( function(response) 
{
return response.data;
})
.catch(function() 
{ 
alert("error");
});
return promise; 
};
this.update = function() {
var promise= $http.post("../order/",this.selectedEntity)
.then( function(response) {
return response.data;
})
.catch(function() { 
alert("error");
});
return promise; 
}
this.del = function() {
var url="../order/selectedEntity.orderId";
var promise= $http["delete"](url)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
}
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
personService.selectedEntity.show=false;placeService.selectedEntity.show=false;}
$scope.addNew= function()
{
orderService.setSelectedEntity(null);
orderService.selectedEntity.show=true;
personService.selectedEntity.show=false;placeService.selectedEntity.show=false;};
		
$scope.search=function()
{
orderService.selectedEntity.show=false;
orderService.searchBean.placeList=[];
orderService.searchBean.placeList.push(orderService.searchBean.place);
delete orderService.searchBean.place; 
orderService.search().then(function(data) { 
orderService.setEntityList(data);
});
};
$scope.insert=function()
{
if (!$scope.orderDetailForm.$valid) return; 
orderService.insert().then(function(data) { 
$scope.search();
});
};
$scope.update=function()
{
if (!$scope.orderDetailForm.$valid) return; 
personService.selectedEntity.show=false;placeService.selectedEntity.show=false;orderService.update().then(function(data) { 
$scope.search();
});
};
$scope.del=function()
{
orderService.del().then(function(data) { 
$scope.search();
});
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
$scope.init();
$scope.orderGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
columnDefs: [
{ name: 'orderId'},
{ name: 'name'},
{ name: 'timeslotDate', cellFilter: "date:'dd-MM-yyyy'"},
{ name: 'person.personId', displayName: 'person'} 
]
,data: orderService.entityList
 };
$scope.orderGridOptions.onRegisterApi = function(gridApi){
gridApi.selection.on.rowSelectionChanged($scope,function(row){
personService.selectedEntity.show=false;placeService.selectedEntity.show=false;orderService
.setSelectedEntity(row.entity);
orderService.selectedEntity.show = true;
});
  };
$scope.placeListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
columnDefs: [
{ name: 'placeId'},
{ name: 'description'} 
]
,data: $scope.selectedEntity.placeList
 };
$scope.placeListGridOptions.onRegisterApi = function(gridApi){
gridApi.selection.on.rowSelectionChanged($scope,function(row){
placeService
.setSelectedEntity(row.entity);
placeService.selectedEntity.show = true;
});
  };
})
.service("personService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
};
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
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& typeof entity[val] == "object") {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
}
} else {
if (val.toLowerCase().indexOf("date") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(
date.getFullYear(), date
.getMonth(), date
.getDate());
} else {
this.selectedEntity[val] = entity[val];
}
}
	}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../person/search",this.searchBean)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
};
this.insert = function() {
var promise= $http.put("../person/",this.selectedEntity)
.then( function(response) 
{
return response.data;
})
.catch(function() 
{ 
alert("error");
});
return promise; 
};
this.update = function() {
var promise= $http.post("../person/",this.selectedEntity)
.then( function(response) {
return response.data;
})
.catch(function() { 
alert("error");
});
return promise; 
}
this.del = function() {
var url="../person/selectedEntity.personId";
var promise= $http["delete"](url)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
}
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
orderService.update().then(function(data) {
orderService.setSelectedEntity(data);
if (toDo != null)
toDo();
});
};
$scope.addNew= function()
{
personService.setSelectedEntity(null);
personService.selectedEntity.show=true;
};
		
$scope.search=function()
{
personService.selectedEntity.show=false;
personService.search().then(function(data) { 
personService.setEntityList(data);
});
};
$scope.insert=function()
{
if (!$scope.personDetailForm.$valid) return; 
personService.selectedEntity.show=false;

orderService.selectedEntity.person=personService.selectedEntity;

$scope.updateParent();

};
$scope.update=function()
{
if (!$scope.personDetailForm.$valid) return; 
personService.selectedEntity.show=false;

orderService.selectedEntity.person=personService.selectedEntity;

$scope.updateParent();
};
$scope.del=function()
{
personService.selectedEntity.show=false;
orderService.selectedEntity.person=null;personService.setSelectedEntity(null);
$scope.updateParent();
};$scope.init=function()
{
}; 
})
.service("placeService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
};
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
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& typeof entity[val] == "object") {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
}
} else {
if (val.toLowerCase().indexOf("date") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(
date.getFullYear(), date
.getMonth(), date
.getDate());
} else {
this.selectedEntity[val] = entity[val];
}
}
	}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../place/search",this.searchBean)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
};
this.insert = function() {
var promise= $http.put("../place/",this.selectedEntity)
.then( function(response) 
{
return response.data;
})
.catch(function() 
{ 
alert("error");
});
return promise; 
};
this.update = function() {
var promise= $http.post("../place/",this.selectedEntity)
.then( function(response) {
return response.data;
})
.catch(function() { 
alert("error");
});
return promise; 
}
this.del = function() {
var url="../place/selectedEntity.placeId";
var promise= $http["delete"](url)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
}
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
}
$scope.updateParent = function(toDo)
{
orderService.update().then(function(data) {
orderService.setSelectedEntity(data);
if (toDo != null)
toDo();
});
};
$scope.addNew= function()
{
placeService.setSelectedEntity(null);
placeService.selectedEntity.show=true;
};
		
$scope.search=function()
{
placeService.selectedEntity.show=false;
placeService.search().then(function(data) { 
placeService.setEntityList(data);
});
};
$scope.insert=function()
{
if (!$scope.placeDetailForm.$valid) return; 
placeService.selectedEntity.show=false;

orderService.selectedEntity.placeList.push(placeService.selectedEntity);

$scope.updateParent();

};
$scope.update=function()
{
if (!$scope.placeDetailForm.$valid) return; 
placeService.selectedEntity.show=false;

for (i=0; i<orderService.selectedEntity.placeList.length; i++)

{

if (orderService.selectedEntity.placeList[i].placeId==placeService.selectedEntity.placeId)

orderService.selectedEntity.placeList.splice(i,1);

}

orderService.selectedEntity.placeList.push(placeService.selectedEntity);

$scope.updateParent();
};
$scope.del=function()
{
placeService.selectedEntity.show=false;
for (i=0; i<orderService.selectedEntity.placeList.length; i++)
{
if (orderService.selectedEntity.placeList[i].placeId==placeService.selectedEntity.placeId)
orderService.selectedEntity.placeList.splice(i,1);
}
placeService.setSelectedEntity(null);
$scope.updateParent();
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
$http
.post("../order/search",
{})
.success(
function(entityList) {
placeService.childrenList.orderList=entityList;
}).error(function() {
alert("error");
});
}; 
})
;