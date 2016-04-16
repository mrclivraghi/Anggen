(function() { 

angular
.module("serverTestApp")
.service("relationshipService", RelationshipService);
/** @ngInject */
function RelationshipService($http,MainService)
{
this.entityList =		[];
this.preparedData={};
this.selectedEntity= 	{show: false 
,annotationList: []};
this.hidden= { hiddenFields: []};
this.isParent=function()
{
return MainService.parentEntity=="Relationship";
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
var promise= $http.post("http://127.0.0.1:8080/ServerTestApp/relationship/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("http://127.0.0.1:8080/ServerTestApp/relationship/"+entity.relationshipId);
return promise; 
};
this.insert = function() {
var promise= $http.put("http://127.0.0.1:8080/ServerTestApp/relationship/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("http://127.0.0.1:8080/ServerTestApp/relationship/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="http://127.0.0.1:8080/ServerTestApp/relationship/"+this.selectedEntity.relationshipId;
var promise= $http["delete"](url);
return promise; 
}
this.loadFile= function(file,field){
var formData = new FormData();
if (file!=null)
formData.append('file',file);
var promise= $http.post("http://127.0.0.1:8080/ServerTestApp/relationship/"+this.selectedEntity.relationshipId+"/load"+field+"/",formData,{
 headers: {'Content-Type': undefined}
});
return promise; 
}
 this.initEntityList= function()
{
var promise= $http
.post("http://127.0.0.1:8080/ServerTestApp/entity/search",
{});
return promise;
};
 this.initEntityList= function()
{
var promise= $http
.post("http://127.0.0.1:8080/ServerTestApp/entity/search",
{});
return promise;
};
 this.initTabList= function()
{
var promise= $http
.post("http://127.0.0.1:8080/ServerTestApp/tab/search",
{});
return promise;
};
 this.initAnnotationList= function()
{
var promise= $http
.post("http://127.0.0.1:8080/ServerTestApp/annotation/search",
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
{ name: 'entity.entityId', displayName: 'entity'},
{ name: 'entityTarget.entityTargetId', displayName: 'entityTarget'},
{ name: 'tab.tabId', displayName: 'tab'} 
]
 };
};
})();
