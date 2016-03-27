angular.module("anggenApp").service("relationshipService", relationshipService);
function relationshipService($http,mainService)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,annotationList: []};
this.isParent=function()
{
return mainService.parentEntity=="Relationship";
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
var promise= $http.post("relationship/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("relationship/"+entity.relationshipId);
return promise; 
};
this.insert = function() {
var promise= $http.put("relationship/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("relationship/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="relationship/"+this.selectedEntity.relationshipId;
var promise= $http["delete"](url);
return promise; 
}
this.loadFile= function(file,field){
var formData = new FormData();
if (file!=null)
formData.append('file',file);
var promise= $http.post("relationship/"+this.selectedEntity.relationshipId+"/load"+field+"/",formData,{
 headers: {'Content-Type': undefined}
});
return promise; 
}
 this.initAnnotationList= function()
{
var promise= $http
.post("annotation/search",
{});
return promise;
};
 this.initEntityList= function()
{
var promise= $http
.post("entity/search",
{});
return promise;
};
 this.initEntityList= function()
{
var promise= $http
.post("entity/search",
{});
return promise;
};
 this.initTabList= function()
{
var promise= $http
.post("tab/search",
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
{ name: 'relationshipId'},
{ name: 'priority'},
{ name: 'name'},
{ name: 'entityTarget.entityTargetId', displayName: 'entityTarget'},
{ name: 'entity.entityId', displayName: 'entity'},
{ name: 'tab.tabId', displayName: 'tab'} 
]
 };
};
