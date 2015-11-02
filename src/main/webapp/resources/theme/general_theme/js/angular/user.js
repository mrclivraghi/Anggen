var userApp=angular.module("userApp",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])
.service("userService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,authorityList: []};
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
var promise= $http.post("../user/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
this.setSelectedEntity(null);
var promise= $http.get("../user/"+entity.userId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../user/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../user/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../user/"+this.selectedEntity.userId;
var promise= $http["delete"](url);
return promise; 
}
 this.initAuthorityList= function()
{
var promise= $http
.post("../authority/search",
{});
return promise;
};
})
.controller("userController",function($scope,$http,userService,authorityService)
{
//null
$scope.searchBean=userService.searchBean;
$scope.entityList=userService.entityList;
$scope.selectedEntity=userService.selectedEntity;
$scope.childrenList=userService.childrenList; 
$scope.reset = function()
{
userService.resetSearchBean();
$scope.searchBean=userService.searchBean;userService.setSelectedEntity(null);
userService.selectedEntity.show=false;
userService.setEntityList(null); 
authorityService.selectedEntity.show=false;}
$scope.addNew= function()
{
userService.setSelectedEntity(null);
userService.setEntityList(null);
userService.selectedEntity.show=true;
authorityService.selectedEntity.show=false;$('#userTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
userService.selectedEntity.show=false;
userService.searchBean.authorityList=[];
userService.searchBean.authorityList.push(userService.searchBean.authority);
delete userService.searchBean.authority; 
userService.search().then(function successCallback(response) {
userService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.userDetailForm.$valid) return; 
userService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.userDetailForm.$valid) return; 
authorityService.selectedEntity.show=false;userService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.del=function()
{
nullService.selectedEntity.user=null;
userService.del().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
 $scope.authorityGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showAuthorityDetail= function(index)
{
if (index!=null)
{
authorityService.searchOne(userService.selectedEntity.authorityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
authorityService.setSelectedEntity(response.data[0]);
authorityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (userService.selectedEntity.authority==null || userService.selectedEntity.authority==undefined)
{
authorityService.setSelectedEntity(null); 
authorityService.selectedEntity.show=true; 
}
else
authorityService.searchOne(userService.selectedEntity.authority).then(
function successCallback(response) {
authorityService.setSelectedEntity(response.data[0]);
authorityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#authorityTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
userService.initAuthorityList().then(function successCallback(response) {
userService.childrenList.authorityList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
$scope.userGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'userId'},
{ name: 'username'},
{ name: 'password'},
{ name: 'role'},
{ name: 'enabled'} 
]
,data: userService.entityList
 };
$scope.userGridOptions.onRegisterApi = function(gridApi){
$scope.userGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
authorityService.selectedEntity.show=false;if (row.isSelected)
{
userService.setSelectedEntity(row.entity);
$('#userTabs li:eq(0) a').tab('show');
}
else 
userService.setSelectedEntity(null);
userService.selectedEntity.show = row.isSelected;
});
  };
$scope.authorityListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'authorityId'},
{ name: 'name'} 
]
,data: $scope.selectedEntity.authorityList
 };
$scope.authorityListGridOptions.onRegisterApi = function(gridApi){
$scope.authorityGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
authorityService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
authorityService.setSelectedEntity(response.data[0]);
});
$('#authorityTabs li:eq(0) a').tab('show');
}
else 
authorityService.setSelectedEntity(null);
authorityService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("user.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedAuthority= function() {
userService.selectedEntity.authorityList.push(userService.selectedEntity.authority);
}
$scope.downloadAuthorityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("authority.xls",?) FROM ?',[mystyle,$scope.selectedEntity.authorityList]);
};
})
.service("authorityService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,userList: []};
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
var promise= $http.post("../authority/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
this.setSelectedEntity(null);
var promise= $http.get("../authority/"+entity.authorityId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../authority/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../authority/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../authority/"+this.selectedEntity.authorityId;
var promise= $http["delete"](url);
return promise; 
}
 this.initUserList= function()
{
var promise= $http
.post("../user/search",
{});
return promise;
};
})
.controller("authorityController",function($scope,$http,authorityService,userService)
{
//user
$scope.searchBean=authorityService.searchBean;
$scope.entityList=authorityService.entityList;
$scope.selectedEntity=authorityService.selectedEntity;
$scope.childrenList=authorityService.childrenList; 
$scope.reset = function()
{
authorityService.resetSearchBean();
$scope.searchBean=authorityService.searchBean;authorityService.setSelectedEntity(null);
authorityService.selectedEntity.show=false;
authorityService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
userService.update().then(function successCallback(response) {
userService.setSelectedEntity(response);
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
authorityService.setSelectedEntity(null);
authorityService.setEntityList(null);
authorityService.selectedEntity.show=true;
$('#authorityTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
authorityService.selectedEntity.show=false;
authorityService.searchBean.userList=[];
authorityService.searchBean.userList.push(authorityService.searchBean.user);
delete authorityService.searchBean.user; 
authorityService.search().then(function successCallback(response) {
authorityService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.authorityDetailForm.$valid) return; 
authorityService.selectedEntity.show=false;
authorityService.selectedEntity.userList.push(userService.selectedEntity);
authorityService.insert().then(function successCallBack(response) { 
userService.selectedEntity.authorityList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.authorityDetailForm.$valid) return; 
authorityService.selectedEntity.show=false;

for (i=0; i<userService.selectedEntity.authorityList.length; i++)

{

if (userService.selectedEntity.authorityList[i].authorityId==authorityService.selectedEntity.authorityId)

userService.selectedEntity.authorityList.splice(i,1);

}

userService.selectedEntity.authorityList.push(authorityService.selectedEntity);

authorityService.update().then(function successCallback(response){
authorityService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
authorityService.selectedEntity.show=false;
for (i=0; i<userService.selectedEntity.authorityList.length; i++)
{
if (userService.selectedEntity.authorityList[i].authorityId==authorityService.selectedEntity.authorityId)
userService.selectedEntity.authorityList.splice(i,1);
}
authorityService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<userService.selectedEntity.authorityList.length; i++)
{
if (userService.selectedEntity.authorityList[i].authorityId==authorityService.selectedEntity.authorityId)
userService.selectedEntity.authorityList.splice(i,1);
}
$scope.updateParent();
authorityService.del().then(function successCallback(response) { 
authorityService.setSelectedEntity(null);
userService.initAuthorityList().then(function(response) {
userService.childrenList.authorityList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
 $scope.userGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showUserDetail= function(index)
{
if (index!=null)
{
userService.searchOne(authorityService.selectedEntity.userList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
userService.setSelectedEntity(response.data[0]);
userService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (authorityService.selectedEntity.user==null || authorityService.selectedEntity.user==undefined)
{
userService.setSelectedEntity(null); 
userService.selectedEntity.show=true; 
}
else
userService.searchOne(authorityService.selectedEntity.user).then(
function successCallback(response) {
userService.setSelectedEntity(response.data[0]);
userService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#userTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
authorityService.initUserList().then(function successCallback(response) {
authorityService.childrenList.userList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
$scope.userListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'userId'},
{ name: 'username'},
{ name: 'password'},
{ name: 'role'},
{ name: 'enabled'} 
]
,data: $scope.selectedEntity.userList
 };
$scope.userListGridOptions.onRegisterApi = function(gridApi){
$scope.userGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
userService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
userService.setSelectedEntity(response.data[0]);
});
$('#userTabs li:eq(0) a').tab('show');
}
else 
userService.setSelectedEntity(null);
userService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("authority.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedUser= function() {
authorityService.selectedEntity.userList.push(authorityService.selectedEntity.user);
}
$scope.downloadUserList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("user.xls",?) FROM ?',[mystyle,$scope.selectedEntity.userList]);
};
})
;