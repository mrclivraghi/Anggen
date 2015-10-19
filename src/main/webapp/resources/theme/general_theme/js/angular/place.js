var placeApp=angular.module("placeApp",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])
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
this.searchBean = 		new Object();
this.resetSearchBean= function()
{
this.searchBean={};
};
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
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
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
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
this.searchOne=function(entity) {
this.setSelectedEntity(null);
var promise= $http.post("../place/search",entity)
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
var url="../place/"+this.selectedEntity.placeId;
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
$scope.searchBean=placeService.searchBean;placeService.setSelectedEntity(null);
placeService.selectedEntity.show=false;
placeService.setEntityList(null); 
orderService.selectedEntity.show=false;personService.selectedEntity.show=false;}
$scope.addNew= function()
{
placeService.setSelectedEntity(null);
placeService.setEntityList(null);
placeService.selectedEntity.show=true;
orderService.selectedEntity.show=false;personService.selectedEntity.show=false;};
		
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
placeService.insert().then(function(data) { 
$scope.search();
});
};
$scope.update=function()
{
if (!$scope.placeDetailForm.$valid) return; 
orderService.selectedEntity.show=false;personService.selectedEntity.show=false;placeService.update().then(function(data) { 
$scope.search();
});
};
$scope.del=function()
{
placeService.del().then(function(data) { 
$scope.search();
});
};$scope.trueFalseValues=[true,false];$scope.showOrderDetail= function(index)
{
if (index!=null)
{
orderService.searchOne(placeService.selectedEntity.orderList[index]).then(function(data) { 
console.log(data[0]);
orderService.setSelectedEntity(data[0]);
orderService.selectedEntity.show=true;
});
}
else 
{
orderService.setSelectedEntity(placeService.selectedEntity.order); 
orderService.searchOne(placeService.selectedEntity.order).then(function(data) { 
console.log(data[0]);
orderService.setSelectedEntity(data[0]);
orderService.selectedEntity.show=true;
});
}
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
$scope.init();
$scope.placeGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'placeId'},
{ name: 'description'},
{ name: 'order.orderId', displayName: 'order'} 
]
,data: placeService.entityList
 };
$scope.placeGridOptions.onRegisterApi = function(gridApi){
gridApi.selection.on.rowSelectionChanged($scope,function(row){
orderService.selectedEntity.show=false;personService.selectedEntity.show=false;if (row.isSelected)
{
placeService.setSelectedEntity(row.entity);
}
else 
placeService.setSelectedEntity(null);
placeService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("place.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadOrderList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("order.xls",?) FROM ?',[mystyle,$scope.selectedEntity.orderList]);
};
})
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
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
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
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
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
this.searchOne=function(entity) {
this.setSelectedEntity(null);
var promise= $http.post("../order/search",entity)
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
var url="../order/"+this.selectedEntity.orderId;
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
$scope.searchBean=orderService.searchBean;orderService.setSelectedEntity(null);
orderService.selectedEntity.show=false;
orderService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
placeService.update().then(function(data) {
placeService.setSelectedEntity(data);
if (toDo != null)
toDo();
});
};
$scope.addNew= function()
{
orderService.setSelectedEntity(null);
orderService.setEntityList(null);
orderService.selectedEntity.show=true;
};
		
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
orderService.selectedEntity.show=false;

placeService.selectedEntity.order=orderService.selectedEntity;

$scope.updateParent();

};
$scope.update=function()
{
if (!$scope.orderDetailForm.$valid) return; 
orderService.selectedEntity.show=false;

placeService.selectedEntity.order=orderService.selectedEntity;

orderService.update().then(function(data){
orderService.setSelectedEntity(data);
});
};
$scope.del=function()
{
orderService.selectedEntity.show=false;
placeService.selectedEntity.order=null;orderService.setSelectedEntity(null);
$scope.updateParent();
};$scope.trueFalseValues=[true,false];$scope.showPersonDetail= function(index)
{
if (index!=null)
{
personService.searchOne(orderService.selectedEntity.personList[index]).then(function(data) { 
console.log(data[0]);
personService.setSelectedEntity(data[0]);
personService.selectedEntity.show=true;
});
}
else 
{
personService.setSelectedEntity(orderService.selectedEntity.person); 
personService.searchOne(orderService.selectedEntity.person).then(function(data) { 
console.log(data[0]);
personService.setSelectedEntity(data[0]);
personService.selectedEntity.show=true;
});
}
};
$scope.showPlaceDetail= function(index)
{
if (index!=null)
{
placeService.searchOne(orderService.selectedEntity.placeList[index]).then(function(data) { 
console.log(data[0]);
placeService.setSelectedEntity(data[0]);
placeService.selectedEntity.show=true;
});
}
else 
{
placeService.setSelectedEntity(orderService.selectedEntity.place); 
placeService.searchOne(orderService.selectedEntity.place).then(function(data) { 
console.log(data[0]);
placeService.setSelectedEntity(data[0]);
placeService.selectedEntity.show=true;
});
}
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
$scope.placeListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'placeId'},
{ name: 'description'} 
]
,data: $scope.selectedEntity.placeList
 };
$scope.placeListGridOptions.onRegisterApi = function(gridApi){
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
placeService.searchOne(row.entity).then(function(data) { 
console.log(data);
placeService.setSelectedEntity(data[0]);
});
}
else 
placeService.setSelectedEntity(null);
placeService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("order.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadPersonList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("person.xls",?) FROM ?',[mystyle,$scope.selectedEntity.personList]);
};
$scope.downloadPlaceList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("place.xls",?) FROM ?',[mystyle,$scope.selectedEntity.placeList]);
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
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
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
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
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
this.searchOne=function(entity) {
this.setSelectedEntity(null);
var promise= $http.post("../person/search",entity)
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
var url="../person/"+this.selectedEntity.personId;
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
$scope.searchBean=personService.searchBean;personService.setSelectedEntity(null);
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
personService.setEntityList(null);
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

personService.update().then(function(data){
personService.setSelectedEntity(data);
});
};
$scope.del=function()
{
personService.selectedEntity.show=false;
orderService.selectedEntity.person=null;personService.setSelectedEntity(null);
$scope.updateParent();
};$scope.trueFalseValues=[true,false];$scope.init=function()
{
}; 
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("person.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
})
;