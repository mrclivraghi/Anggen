(function() { 

angular
.module("serverTestApp")
.service("userService", UserService);
/** @ngInject */
function UserService($http,MainService,UtilityService)
{
this.entityList =		[];
this.preparedData={};
this.selectedEntity= 	{show: false 
,roleList: []};
this.hidden= { hiddenFields: []};
this.isParent=function()
{
return MainService.parentEntity=="User";
};
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
for (var i=0; i<entityList.length; i++)
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
UtilityService.cloneObject(entity,this.selectedEntity);
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("http://127.0.0.1:8080/ServerTestApp/user/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("http://127.0.0.1:8080/ServerTestApp/user/"+entity.userId);
return promise; 
};
this.insert = function() {
var promise= $http.put("http://127.0.0.1:8080/ServerTestApp/user/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("http://127.0.0.1:8080/ServerTestApp/user/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="http://127.0.0.1:8080/ServerTestApp/user/"+this.selectedEntity.userId;
var promise= $http["delete"](url);
return promise; 
}
this.loadFile= function(file,field){
var formData = new FormData();
if (file!=null)
formData.append('file',file);
var promise= $http.post("http://127.0.0.1:8080/ServerTestApp/user/"+this.selectedEntity.userId+"/load"+field+"/",formData,{
 headers: {'Content-Type': undefined}
});
return promise; 
}
 this.initRoleList= function()
{
var promise= $http
.post("http://127.0.0.1:8080/ServerTestApp/role/search",
{});
return promise;
};
this.gridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'userId'},
{ name: 'enabled'},
{ name: 'username'} 
]
 };
}
})();
