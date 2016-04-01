angular.module("anggenApp").service("restrictionEntityGroupService", restrictionEntityGroupService);
function restrictionEntityGroupService($http,mainService)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
};
this.isParent=function()
{
return mainService.parentEntity=="RestrictionEntityGroup";
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
var promise= $http.post("restrictionEntityGroup/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("restrictionEntityGroup/"+entity.restrictionEntityGroupId);
return promise; 
};
this.insert = function() {
var promise= $http.put("restrictionEntityGroup/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("restrictionEntityGroup/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="restrictionEntityGroup/"+this.selectedEntity.restrictionEntityGroupId;
var promise= $http["delete"](url);
return promise; 
}
this.loadFile= function(file,field){
var formData = new FormData();
if (file!=null)
formData.append('file',file);
var promise= $http.post("restrictionEntityGroup/"+this.selectedEntity.restrictionEntityGroupId+"/load"+field+"/",formData,{
 headers: {'Content-Type': undefined}
});
return promise; 
}
 this.initEntityGroupList= function()
{
var promise= $http
.post("entityGroup/search",
{});
return promise;
};
 this.initRoleList= function()
{
var promise= $http
.post("role/search",
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
{ name: 'restrictionEntityGroupId'},
{ name: 'canUpdate'},
{ name: 'canCreate'},
{ name: 'canDelete'},
{ name: 'canSearch'},
{ name: 'entityGroup.entityGroupId', displayName: 'entityGroup'},
{ name: 'role.roleId', displayName: 'role'} 
]
 };
};
