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
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
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
} else 
this.emptyList(this.selectedEntity[val]);
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
var promise= $http.post("../place/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../place/"+entity.placeId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../place/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../place/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../place/"+this.selectedEntity.placeId;
var promise= $http["delete"](url);
return promise; 
}
 this.initOrderList= function()
{
var promise= $http
.post("../order/search",
{});
return promise;
};
})
.controller("placeController",function($scope,$http,placeService,orderService,personService)
{
//null
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
orderService.selectedEntity.show=false;personService.selectedEntity.show=false;$('#placeTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
placeService.selectedEntity.show=false;
placeService.search().then(function successCallback(response) {
placeService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.placeDetailForm.$valid) return; 
placeService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.placeDetailForm.$valid) return; 
orderService.selectedEntity.show=false;personService.selectedEntity.show=false;placeService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.del=function()
{
nullService.selectedEntity.place=null;
placeService.del().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
};
$scope.trueFalseValues=[true,false];
$scope.showOrderDetail= function(index)
{
if (index!=null)
{
orderService.searchOne(placeService.selectedEntity.orderList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
orderService.setSelectedEntity(response.data[0]);
orderService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (placeService.selectedEntity.order==null || placeService.selectedEntity.order==undefined)
{
orderService.setSelectedEntity(null); 
orderService.selectedEntity.show=true; 
}
else
orderService.searchOne(placeService.selectedEntity.order).then(
function successCallback(response) {
orderService.setSelectedEntity(response.data[0]);
orderService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#orderTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
placeService.initOrderList().then(function successCallback(response) {
placeService.childrenList.orderList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
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
$scope.placeGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
orderService.selectedEntity.show=false;personService.selectedEntity.show=false;if (row.isSelected)
{
placeService.setSelectedEntity(row.entity);
$('#placeTabs li:eq(0) a').tab('show');
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
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
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
} else 
this.emptyList(this.selectedEntity[val]);
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
var promise= $http.post("../order/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../order/"+entity.orderId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../order/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../order/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../order/"+this.selectedEntity.orderId;
var promise= $http["delete"](url);
return promise; 
}
 this.initPersonList= function()
{
var promise= $http
.post("../person/search",
{});
return promise;
};
 this.initPlaceList= function()
{
var promise= $http
.post("../place/search",
{});
return promise;
};
})
.controller("orderController",function($scope,$http,orderService,personService,placeService)
{
//place
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
placeService.update().then(function successCallback(response) {
placeService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
alert("error");
return; 
}
);
};
$scope.addNew= function()
{
orderService.setSelectedEntity(null);
orderService.setEntityList(null);
orderService.selectedEntity.show=true;
$('#orderTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
orderService.selectedEntity.show=false;
orderService.searchBean.placeList=[];
orderService.searchBean.placeList.push(orderService.searchBean.place);
delete orderService.searchBean.place; 
orderService.search().then(function successCallback(response) {
orderService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.orderDetailForm.$valid) return; 
orderService.selectedEntity.show=false;
orderService.selectedEntity.place={};
orderService.selectedEntity.place.placeId=placeService.selectedEntity.placeId;
orderService.insert().then(function successCallBack(response) { 
placeService.selectedEntity.order=response.data;
placeService.initOrderList().then(function(response) {
placeService.childrenList.orderList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.orderDetailForm.$valid) return; 
orderService.selectedEntity.show=false;

placeService.selectedEntity.order=orderService.selectedEntity;

orderService.update().then(function successCallback(response){
orderService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
orderService.selectedEntity.show=false;
placeService.selectedEntity.order=null;
orderService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
placeService.selectedEntity.order=null;
$scope.updateParent();
orderService.del().then(function successCallback(response) { 
orderService.setSelectedEntity(null);
placeService.initOrderList().then(function(response) {
placeService.childrenList.orderList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
 $scope.placeGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showPersonDetail= function(index)
{
if (index!=null)
{
personService.searchOne(orderService.selectedEntity.personList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
personService.setSelectedEntity(response.data[0]);
personService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (orderService.selectedEntity.person==null || orderService.selectedEntity.person==undefined)
{
personService.setSelectedEntity(null); 
personService.selectedEntity.show=true; 
}
else
personService.searchOne(orderService.selectedEntity.person).then(
function successCallback(response) {
personService.setSelectedEntity(response.data[0]);
personService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#personTabs li:eq(0) a').tab('show');
};
$scope.showPlaceDetail= function(index)
{
if (index!=null)
{
placeService.searchOne(orderService.selectedEntity.placeList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
placeService.setSelectedEntity(response.data[0]);
placeService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (orderService.selectedEntity.place==null || orderService.selectedEntity.place==undefined)
{
placeService.setSelectedEntity(null); 
placeService.selectedEntity.show=true; 
}
else
placeService.searchOne(orderService.selectedEntity.place).then(
function successCallback(response) {
placeService.setSelectedEntity(response.data[0]);
placeService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#placeTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
orderService.initPersonList().then(function successCallback(response) {
orderService.childrenList.personList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
orderService.initPlaceList().then(function successCallback(response) {
orderService.childrenList.placeList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
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
$scope.placeGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
placeService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
placeService.setSelectedEntity(response.data[0]);
});
$('#placeTabs li:eq(0) a').tab('show');
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
$scope.saveLinkedPlace= function() {
orderService.selectedEntity.placeList.push(orderService.selectedEntity.place);
}
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
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
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
} else 
this.emptyList(this.selectedEntity[val]);
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
var promise= $http.post("../person/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../person/"+entity.personId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../person/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../person/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../person/"+this.selectedEntity.personId;
var promise= $http["delete"](url);
return promise; 
}
})
.controller("personController",function($scope,$http,personService)
{
//order
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
orderService.update().then(function successCallback(response) {
orderService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
alert("error");
return; 
}
);
};
$scope.addNew= function()
{
personService.setSelectedEntity(null);
personService.setEntityList(null);
personService.selectedEntity.show=true;
$('#personTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
personService.selectedEntity.show=false;
personService.search().then(function successCallback(response) {
personService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.personDetailForm.$valid) return; 
personService.selectedEntity.show=false;
personService.selectedEntity.order={};
personService.selectedEntity.order.orderId=orderService.selectedEntity.orderId;
personService.insert().then(function successCallBack(response) { 
orderService.selectedEntity.person=response.data;
orderService.initPersonList().then(function(response) {
orderService.childrenList.personList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.personDetailForm.$valid) return; 
personService.selectedEntity.show=false;

orderService.selectedEntity.person=personService.selectedEntity;

personService.update().then(function successCallback(response){
personService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
personService.selectedEntity.show=false;
orderService.selectedEntity.person=null;
personService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
orderService.selectedEntity.person=null;
$scope.updateParent();
personService.del().then(function successCallback(response) { 
personService.setSelectedEntity(null);
orderService.initPersonList().then(function(response) {
orderService.childrenList.personList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
};
$scope.trueFalseValues=[true,false];
$scope.init=function()
{
}; 
$scope.init();
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