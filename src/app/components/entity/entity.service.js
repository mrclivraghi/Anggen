(function() { 

angular
.module("serverTestApp")
.service("entityService", EntityService);
/** @ngInject */
function EntityService($http,MainService)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,restrictionEntityList: [],tabList: [],enumFieldList: [],fieldList: [],relationshipList: []};
this.hidden= { hiddenFields: []};
this.preparedData= {};

this.isParent=function()
{
return MainService.parentEntity=="Entity";
};
this.childrenList={}; 
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
var promise= $http.post("http://127.0.0.1:8080/ServerTestApp/entity/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("http://127.0.0.1:8080/ServerTestApp/entity/"+entity.entityId);
return promise; 
};
this.insert = function() {
var promise= $http.put("http://127.0.0.1:8080/ServerTestApp/entity/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("http://127.0.0.1:8080/ServerTestApp/entity/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="http://127.0.0.1:8080/ServerTestApp/entity/"+this.selectedEntity.entityId;
var promise= $http["delete"](url);
return promise; 
}
this.loadFile= function(file,field){
var formData = new FormData();
if (file!=null)
formData.append('file',file);
var promise= $http.post("http://127.0.0.1:8080/ServerTestApp/entity/"+this.selectedEntity.entityId+"/load"+field+"/",formData,{
 headers: {'Content-Type': undefined}
});
return promise; 
}
 this.initRestrictionEntityList= function()
{
var promise= $http
.post("http://127.0.0.1:8080/ServerTestApp/restrictionEntity/search",
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
 this.initEntityGroupList= function()
{
var promise= $http
.post("http://127.0.0.1:8080/ServerTestApp/entityGroup/search",
{});
return promise;
};
 this.initEnumFieldList= function()
{
var promise= $http
.post("http://127.0.0.1:8080/ServerTestApp/enumField/search",
{});
return promise;
};
 this.initFieldList= function()
{
var promise= $http
.post("http://127.0.0.1:8080/ServerTestApp/field/search",
{});
return promise;
};
 this.initRelationshipList= function()
{
var promise= $http
.post("http://127.0.0.1:8080/ServerTestApp/relationship/search",
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
{ name: 'entityId'},
{ name: 'enableRestrictionData'},
{ name: 'disableViewGeneration'},
{ name: 'cache'},
{ name: 'descendantMaxLevel'},
{ name: 'name'},
{ name: 'generateFrontEnd'},
{ name: 'entityGroup.entityGroupId', displayName: 'entityGroup'} 
]
 };
};
})();
