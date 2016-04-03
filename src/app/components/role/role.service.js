(function() { 

angular
.module("serverTestApp")
.service("roleService", RoleService);
/** @ngInject */
function RoleService($http,MainService)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,restrictionFieldList: [],enumFieldList: [],annotationList: [],relationshipList: []};
this.isParent=function()
{
return MainService.parentEntity=="Role";
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
cloneObject(entity,this.selectedEntity);
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("http://localhost:8080/ServerTestApp/role/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("http://localhost:8080/ServerTestApp/role/"+entity.roleId);
return promise; 
};
this.insert = function() {
var promise= $http.put("http://localhost:8080/ServerTestApp/role/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("http://localhost:8080/ServerTestApp/role/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="http://localhost:8080/ServerTestApp/role/"+this.selectedEntity.roleId;
var promise= $http["delete"](url);
return promise; 
}
this.loadFile= function(file,field){
var formData = new FormData();
if (file!=null)
formData.append('file',file);
var promise= $http.post("http://localhost:8080/ServerTestApp/role/"+this.selectedEntity.roleId+"/load"+field+"/",formData,{
 headers: {'Content-Type': undefined}
});
return promise; 
}
 this.initRestrictionFieldList= function()
{
var promise= $http
.post("http://localhost:8080/ServerTestApp/restrictionField/search",
{});
return promise;
};
 this.initEnumFieldList= function()
{
var promise= $http
.post("http://localhost:8080/ServerTestApp/enumField/search",
{});
return promise;
};
 this.initAnnotationList= function()
{
var promise= $http
.post("http://localhost:8080/ServerTestApp/annotation/search",
{});
return promise;
};
 this.initRelationshipList= function()
{
var promise= $http
.post("http://localhost:8080/ServerTestApp/relationship/search",
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
{ name: 'roleId'},
{ name: 'role'} 
]
 };
};
})();
