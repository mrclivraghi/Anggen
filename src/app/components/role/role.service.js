angular.module("anggenApp").service("roleService", roleService);
function roleService($http,mainService)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,restrictionEntityList: [],restrictionFieldList: [],userList: [],restrictionEntityGroupList: []};
this.isParent=function()
{
return mainService.parentEntity=="Role";
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
var promise= $http.post("role/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("role/"+entity.roleId);
return promise; 
};
this.insert = function() {
var promise= $http.put("role/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("role/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="role/"+this.selectedEntity.roleId;
var promise= $http["delete"](url);
return promise; 
}
this.loadFile= function(file,field){
var formData = new FormData();
if (file!=null)
formData.append('file',file);
var promise= $http.post("role/"+this.selectedEntity.roleId+"/load"+field+"/",formData,{
 headers: {'Content-Type': undefined}
});
return promise; 
}
 this.initRestrictionEntityList= function()
{
var promise= $http
.post("restrictionEntity/search",
{});
return promise;
};
 this.initRestrictionFieldList= function()
{
var promise= $http
.post("restrictionField/search",
{});
return promise;
};
 this.initUserList= function()
{
var promise= $http
.post("user/search",
{});
return promise;
};
 this.initRestrictionEntityGroupList= function()
{
var promise= $http
.post("restrictionEntityGroup/search",
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
