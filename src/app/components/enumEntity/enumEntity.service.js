(function() { 

angular
.module("serverTestApp")
.service("enumEntityService", EnumEntityService);
/** @ngInject */
function EnumEntityService($http,MainService)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,enumValueList: []};
this.isParent=function()
{
return MainService.parentEntity=="EnumEntity";
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
var promise= $http.post("http://127.0.0.1:8080/ServerTestApp/enumEntity/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("http://127.0.0.1:8080/ServerTestApp/enumEntity/"+entity.enumEntityId);
return promise; 
};
this.insert = function() {
var promise= $http.put("http://127.0.0.1:8080/ServerTestApp/enumEntity/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("http://127.0.0.1:8080/ServerTestApp/enumEntity/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="http://127.0.0.1:8080/ServerTestApp/enumEntity/"+this.selectedEntity.enumEntityId;
var promise= $http["delete"](url);
return promise; 
}
this.loadFile= function(file,field){
var formData = new FormData();
if (file!=null)
formData.append('file',file);
var promise= $http.post("http://127.0.0.1:8080/ServerTestApp/enumEntity/"+this.selectedEntity.enumEntityId+"/load"+field+"/",formData,{
 headers: {'Content-Type': undefined}
});
return promise; 
}
 this.initProjectList= function()
{
var promise= $http
.post("http://127.0.0.1:8080/ServerTestApp/project/search",
{});
return promise;
};
 this.initEnumValueList= function()
{
var promise= $http
.post("http://127.0.0.1:8080/ServerTestApp/enumValue/search",
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
{ name: 'enumEntityId'},
{ name: 'name'},
{ name: 'project.projectId', displayName: 'project'} 
]
 };
};
})();
