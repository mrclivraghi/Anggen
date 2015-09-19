<head><title>test order</title><script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script><script>
angular.module("personApp",[])
.service("personService", function(){
this.personList=[];
this.personForm= new Object();
this.setPersonList= function(data) {
while (this.personList.length>0)
this.personList.pop();
for (i=0; i<data.length; i++)
{
var tempPerson= new Object();
tempPerson.personId=data[i].personId;
tempPerson.firstName=data[i].firstName;
tempPerson.lastName=data[i].lastName;
tempPerson.birthDate=data[i].birthDate;
this.personList[i]=tempPerson;
}
};
this.resetForm = function (person) {
this.personForm.personId= (person==null || person== undefined )? "" : person.personId;
this.personForm.firstName= (person==null || person== undefined )? "" : person.firstName;
this.personForm.lastName= (person==null || person== undefined )? "" : person.lastName;
this.personForm.birthDate= (person==null || person== undefined )? "" : person.birthDate;
};
this.addPerson= function(person) {
this.personList.push(person);
this.resetForm(person);
};
})
.controller("personController",
function($scope,personService) {
$scope.personForm=personService.personForm;
}
)
.controller("personRetrieveController", function ($scope,$http,personService) {
$scope.reset = function ()
{
personService.resetForm();
};
$scope.search = function() {
$http.post("../person/search",personService.personForm)
.success( function(data) {
personService.setPersonList(data);
})
.error(function() { alert("error");});
};
$scope.insert = function() 
{
$http.put("../person/",personService.personForm)
.success( function(data) 
{
personService.addPerson(data);
})
.error(function() 
{ 
alert("error");
});
};
$scope.update = function() {
$http.post("../person/",personService.personForm)
.success( function(data) {
$scope.search();
	})
.error(function() { alert("error");});
};
$scope.del = function() {
var url="../person/"+personService.personForm.personId;
$http["delete"](url)
.success( function(data) {
personService.resetForm();
$scope.search();
})
.error(function() { alert("error");});
};
})
.controller("personListController", function($scope,personService,dateFilter)
{
$scope.personList=personService.personList;
$scope.refreshForm = function (index) 
{
date= new Date(personService.personList[index].birthDate);
personService.personList[index].birthDate= new Date(date.getFullYear(),date.getMonth(),date.getDate());
personService.resetForm(personService.personList[index]);
};
});
</script></head><body><div ng-app="personApp"><div ng-controller="personController"><p>personId</p><input type="text" ng-model="personForm.personId"/><p>firstName</p><input type="text" ng-model="personForm.firstName"/><p>lastName</p><input type="text" ng-model="personForm.lastName"/><p>birthDate</p><input type="date" ng-model="personForm.birthDate"/></div><div ng-controller="personRetrieveController"><button ng-click="search()">search</button></div><div ng-controller="personRetrieveController"><button ng-click="insert()">insert</button></div><div ng-controller="personRetrieveController"><button ng-click="update()">update</button></div><div ng-controller="personRetrieveController"><button ng-click="del()">del</button></div><div ng-controller="personRetrieveController"><button ng-click="reset()">reset</button></div><div ng-controller="personListController"><ul><li ng-repeat="person in personList"><p ng-click="refreshForm($index)">{{$index}} 
{{person.personId}}
{{person.firstName}}
{{person.lastName}}
{{person.birthDate | date: 'dd-MM-yyyy'}}
</p></li></ul></div></div></body>
