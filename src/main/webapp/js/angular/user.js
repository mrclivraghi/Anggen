var userApp=angular.module("userApp",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])
.service("userService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,roleList: []};
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
 this.initRoleList= function()
{
var promise= $http
.post("../role/search",
{});
return promise;
};
})
.controller("userController",function($scope,$http,userService,roleService,entityService)
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
roleService.selectedEntity.show=false;entityService.selectedEntity.show=false;}
$scope.addNew= function()
{
userService.setSelectedEntity(null);
userService.setEntityList(null);
userService.selectedEntity.show=true;
roleService.selectedEntity.show=false;entityService.selectedEntity.show=false;$('#userTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
userService.selectedEntity.show=false;
userService.searchBean.roleList=[];
userService.searchBean.roleList.push(userService.searchBean.role);
delete userService.searchBean.role; 
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
roleService.selectedEntity.show=false;entityService.selectedEntity.show=false;userService.update().then(function successCallback(response) { 
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
 $scope.roleGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showRoleDetail= function(index)
{
if (index!=null)
{
roleService.searchOne(userService.selectedEntity.roleList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (userService.selectedEntity.role==null || userService.selectedEntity.role==undefined)
{
roleService.setSelectedEntity(null); 
roleService.selectedEntity.show=true; 
}
else
roleService.searchOne(userService.selectedEntity.role).then(
function successCallback(response) {
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#roleTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
userService.initRoleList().then(function successCallback(response) {
userService.childrenList.roleList=response.data;
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
{ name: 'enabled'} 
]
,data: userService.entityList
 };
$scope.userGridOptions.onRegisterApi = function(gridApi){
$scope.userGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
roleService.selectedEntity.show=false;entityService.selectedEntity.show=false;if (row.isSelected)
{
userService.setSelectedEntity(row.entity);
$('#userTabs li:eq(0) a').tab('show');
}
else 
userService.setSelectedEntity(null);
userService.selectedEntity.show = row.isSelected;
});
  };
$scope.roleListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'roleId'},
{ name: 'role'} 
]
,data: $scope.selectedEntity.roleList
 };
$scope.roleListGridOptions.onRegisterApi = function(gridApi){
$scope.roleGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
roleService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
roleService.setSelectedEntity(response.data[0]);
});
$('#roleTabs li:eq(0) a').tab('show');
}
else 
roleService.setSelectedEntity(null);
roleService.selectedEntity.show = row.isSelected;
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
$scope.saveLinkedRole= function() {
userService.selectedEntity.roleList.push(userService.selectedEntity.role);
}
$scope.downloadRoleList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,$scope.selectedEntity.roleList]);
};
})
.service("roleService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,userList: [],entityList: []};
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
var promise= $http.post("../role/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../role/"+entity.roleId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../role/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../role/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../role/"+this.selectedEntity.roleId;
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
 this.initEntityList= function()
{
var promise= $http
.post("../entity/search",
{});
return promise;
};
})
.controller("roleController",function($scope,$http,roleService,userService,entityService)
{
//user
$scope.searchBean=roleService.searchBean;
$scope.entityList=roleService.entityList;
$scope.selectedEntity=roleService.selectedEntity;
$scope.childrenList=roleService.childrenList; 
$scope.reset = function()
{
roleService.resetSearchBean();
$scope.searchBean=roleService.searchBean;roleService.setSelectedEntity(null);
roleService.selectedEntity.show=false;
roleService.setEntityList(null); 
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
roleService.setSelectedEntity(null);
roleService.setEntityList(null);
roleService.selectedEntity.show=true;
$('#roleTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
roleService.selectedEntity.show=false;
roleService.searchBean.userList=[];
roleService.searchBean.userList.push(roleService.searchBean.user);
delete roleService.searchBean.user; 
roleService.searchBean.entityList=[];
roleService.searchBean.entityList.push(roleService.searchBean.entity);
delete roleService.searchBean.entity; 
roleService.search().then(function successCallback(response) {
roleService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.roleDetailForm.$valid) return; 
roleService.selectedEntity.show=false;
roleService.selectedEntity.userList.push(userService.selectedEntity);
roleService.insert().then(function successCallBack(response) { 
userService.selectedEntity.roleList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.roleDetailForm.$valid) return; 
roleService.selectedEntity.show=false;

for (i=0; i<userService.selectedEntity.roleList.length; i++)

{

if (userService.selectedEntity.roleList[i].roleId==roleService.selectedEntity.roleId)

userService.selectedEntity.roleList.splice(i,1);

}

userService.selectedEntity.roleList.push(roleService.selectedEntity);

roleService.update().then(function successCallback(response){
roleService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
roleService.selectedEntity.show=false;
for (i=0; i<userService.selectedEntity.roleList.length; i++)
{
if (userService.selectedEntity.roleList[i].roleId==roleService.selectedEntity.roleId)
userService.selectedEntity.roleList.splice(i,1);
}
roleService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<userService.selectedEntity.roleList.length; i++)
{
if (userService.selectedEntity.roleList[i].roleId==roleService.selectedEntity.roleId)
userService.selectedEntity.roleList.splice(i,1);
}
$scope.updateParent();
roleService.del().then(function successCallback(response) { 
roleService.setSelectedEntity(null);
userService.initRoleList().then(function(response) {
userService.childrenList.roleList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
 $scope.userGridApi.core.handleWindowResize(); 
 $scope.entityGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showUserDetail= function(index)
{
if (index!=null)
{
userService.searchOne(roleService.selectedEntity.userList[index]).then(
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
if (roleService.selectedEntity.user==null || roleService.selectedEntity.user==undefined)
{
userService.setSelectedEntity(null); 
userService.selectedEntity.show=true; 
}
else
userService.searchOne(roleService.selectedEntity.user).then(
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
$scope.showEntityDetail= function(index)
{
if (index!=null)
{
entityService.searchOne(roleService.selectedEntity.entityList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (roleService.selectedEntity.entity==null || roleService.selectedEntity.entity==undefined)
{
entityService.setSelectedEntity(null); 
entityService.selectedEntity.show=true; 
}
else
entityService.searchOne(roleService.selectedEntity.entity).then(
function successCallback(response) {
entityService.setSelectedEntity(response.data[0]);
entityService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#entityTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
roleService.initUserList().then(function successCallback(response) {
roleService.childrenList.userList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
roleService.initEntityList().then(function successCallback(response) {
roleService.childrenList.entityList=response.data;
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
$scope.entityListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'entityId'},
{ name: 'entityName'} 
]
,data: $scope.selectedEntity.entityList
 };
$scope.entityListGridOptions.onRegisterApi = function(gridApi){
$scope.entityGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
entityService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
entityService.setSelectedEntity(response.data[0]);
});
$('#entityTabs li:eq(0) a').tab('show');
}
else 
entityService.setSelectedEntity(null);
entityService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedUser= function() {
roleService.selectedEntity.userList.push(roleService.selectedEntity.user);
}
$scope.downloadUserList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("user.xls",?) FROM ?',[mystyle,$scope.selectedEntity.userList]);
};
$scope.saveLinkedEntity= function() {
roleService.selectedEntity.entityList.push(roleService.selectedEntity.entity);
}
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityList]);
};
})
.service("entityService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,roleList: []};
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
var promise= $http.post("../entity/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("../entity/"+entity.entityId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../entity/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../entity/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../entity/"+this.selectedEntity.entityId;
var promise= $http["delete"](url);
return promise; 
}
 this.initRoleList= function()
{
var promise= $http
.post("../role/search",
{});
return promise;
};
})
.controller("entityController",function($scope,$http,entityService,roleService,userService)
{
//role
$scope.searchBean=entityService.searchBean;
$scope.entityList=entityService.entityList;
$scope.selectedEntity=entityService.selectedEntity;
$scope.childrenList=entityService.childrenList; 
$scope.reset = function()
{
entityService.resetSearchBean();
$scope.searchBean=entityService.searchBean;entityService.setSelectedEntity(null);
entityService.selectedEntity.show=false;
entityService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
roleService.update().then(function successCallback(response) {
roleService.setSelectedEntity(response);
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
entityService.setSelectedEntity(null);
entityService.setEntityList(null);
entityService.selectedEntity.show=true;
$('#entityTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
entityService.selectedEntity.show=false;
entityService.searchBean.roleList=[];
entityService.searchBean.roleList.push(entityService.searchBean.role);
delete entityService.searchBean.role; 
entityService.search().then(function successCallback(response) {
entityService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.entityDetailForm.$valid) return; 
entityService.selectedEntity.show=false;
entityService.selectedEntity.roleList.push(roleService.selectedEntity);
entityService.insert().then(function successCallBack(response) { 
roleService.selectedEntity.entityList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.entityDetailForm.$valid) return; 
entityService.selectedEntity.show=false;

for (i=0; i<roleService.selectedEntity.entityList.length; i++)

{

if (roleService.selectedEntity.entityList[i].entityId==entityService.selectedEntity.entityId)

roleService.selectedEntity.entityList.splice(i,1);

}

roleService.selectedEntity.entityList.push(entityService.selectedEntity);

entityService.update().then(function successCallback(response){
entityService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
entityService.selectedEntity.show=false;
for (i=0; i<roleService.selectedEntity.entityList.length; i++)
{
if (roleService.selectedEntity.entityList[i].entityId==entityService.selectedEntity.entityId)
roleService.selectedEntity.entityList.splice(i,1);
}
entityService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<roleService.selectedEntity.entityList.length; i++)
{
if (roleService.selectedEntity.entityList[i].entityId==entityService.selectedEntity.entityId)
roleService.selectedEntity.entityList.splice(i,1);
}
$scope.updateParent();
entityService.del().then(function successCallback(response) { 
entityService.setSelectedEntity(null);
roleService.initEntityList().then(function(response) {
roleService.childrenList.entityList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
 $scope.roleGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showRoleDetail= function(index)
{
if (index!=null)
{
roleService.searchOne(entityService.selectedEntity.roleList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (entityService.selectedEntity.role==null || entityService.selectedEntity.role==undefined)
{
roleService.setSelectedEntity(null); 
roleService.selectedEntity.show=true; 
}
else
roleService.searchOne(entityService.selectedEntity.role).then(
function successCallback(response) {
roleService.setSelectedEntity(response.data[0]);
roleService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#roleTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
entityService.initRoleList().then(function successCallback(response) {
entityService.childrenList.roleList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
$scope.roleListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'roleId'},
{ name: 'role'} 
]
,data: $scope.selectedEntity.roleList
 };
$scope.roleListGridOptions.onRegisterApi = function(gridApi){
$scope.roleGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
roleService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
roleService.setSelectedEntity(response.data[0]);
});
$('#roleTabs li:eq(0) a').tab('show');
}
else 
roleService.setSelectedEntity(null);
roleService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedRole= function() {
entityService.selectedEntity.roleList.push(entityService.selectedEntity.role);
}
$scope.downloadRoleList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("role.xls",?) FROM ?',[mystyle,$scope.selectedEntity.roleList]);
};
})
;