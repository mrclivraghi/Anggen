angular.module("anggenApp").service("enumValueService", enumValueService);
function enumValueService($http,mainService)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
};
this.isParent=function()
{
return mainService.parentEntity=="EnumValue";
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
var promise= $http.post("enumValue/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("enumValue/"+entity.enumValueId);
return promise; 
};
this.insert = function() {
var promise= $http.put("enumValue/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("enumValue/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="enumValue/"+this.selectedEntity.enumValueId;
var promise= $http["delete"](url);
return promise; 
}
this.loadFile= function(file,field){
var formData = new FormData();
if (file!=null)
formData.append('file',file);
var promise= $http.post("enumValue/"+this.selectedEntity.enumValueId+"/load"+field+"/",formData,{
 headers: {'Content-Type': undefined}
});
return promise; 
}
 this.initEnumEntityList= function()
{
var promise= $http
.post("enumEntity/search",
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
{ name: 'enumValueId'},
{ name: 'name'},
{ name: 'value'},
{ name: 'enumEntity.enumEntityId', displayName: 'enumEntity'} 
]
 };
};
