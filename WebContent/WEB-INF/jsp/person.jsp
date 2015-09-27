<head><title>test order</title><script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script><script>angular.module("personApp",[])
.service("personService", function()
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
.controller("personController",function($scope,$http,personService)
{
$scope.searchBean=personService.searchBean;
$scope.entityList=personService.entityList;
$scope.selectedEntity=personService.selectedEntity;
$scope.reset = function()
{
personService.resetSearchBean();
personService.selectedEntity.show=false;
}
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
$http.put("../person/",personService.selectedEntity)
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
$http.post("../person/",personService.selectedEntity)
.success( function(data) {
$scope.search();
})
.error(function() { 
alert("error");
});
};
$scope.del=function()
{
var url="../person/"+personService.selectedEntity.personId;
$http["delete"](url)
.success( function(data) {
personService.setSelectedEntity(null);
$scope.search();
})
.error(function() {
alert("error");
});
};})
;</script></head><body ng-app="personApp"><div ng-controller="personController"><div id="personSearchBean"><p>personId</p><input type="text" ng-model="searchBean.personId" ng-readonly="false"/><p>firstName</p><input type="text" ng-model="searchBean.firstName" ng-readonly="false"/><p>lastName</p><input type="text" ng-model="searchBean.lastName" ng-readonly="false"/><p>birthDate</p><input type="date" ng-model="searchBean.birthDate" ng-readonly="false"/><button ng-click="addNew()">Add new</button><button ng-click="search()">Find</button><button ng-click="reset()">Reset</button></div><div id="personList" ng-if="entityList.length&gt;0" enctype="UTF-8"><p>LISTA</p><ul><li ng-repeat="entity in entityList" ng-click="showEntityDetail($index)"><p>{{$index}} 
{{entity.personId}}
{{entity.firstName}}
{{entity.lastName}}
{{entity.birthDate}}
</p></li></ul></div><div id="personDetail" ng-if="selectedEntity.show"><p>DETAIL</p><p>personId</p><input type="text" ng-model="selectedEntity.personId" ng-readonly="true"/><p>firstName</p><input type="text" ng-model="selectedEntity.firstName" ng-readonly="false"/><p>lastName</p><input type="text" ng-model="selectedEntity.lastName" ng-readonly="false"/><p>birthDate</p><input type="text" ng-model="selectedEntity.birthDate" ng-readonly="false"/></div><div id="personActionButton" ng-if="selectedEntity.show"><p>ACTION BUTTON</p><button ng-click="insert()" ng-if="selectedEntity.personId==undefined">Insert</button><button ng-click="update()" ng-if="selectedEntity.personId&gt;0">Update</button><button ng-click="del()" ng-if="selectedEntity.personId&gt;0">Delete</button></div></div></body>