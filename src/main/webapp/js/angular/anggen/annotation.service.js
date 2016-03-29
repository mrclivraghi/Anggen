angular.module("anggenApp").service("annotationService", annotationService);
function annotationService($http,mainService)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,annotationAttributeList: []};
this.isParent=function()
{
return mainService.parentEntity=="Annotation";
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
var promise= $http.post("annotation/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("annotation/"+entity.annotationId);
return promise; 
};
this.insert = function() {
var promise= $http.put("annotation/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("annotation/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="annotation/"+this.selectedEntity.annotationId;
var promise= $http["delete"](url);
return promise; 
}
this.loadFile= function(file,field){
var formData = new FormData();
if (file!=null)
formData.append('file',file);
var promise= $http.post("annotation/"+this.selectedEntity.annotationId+"/load"+field+"/",formData,{
 headers: {'Content-Type': undefined}
});
return promise; 
}
 this.initEnumFieldList= function()
{
var promise= $http
.post("enumField/search",
{});
return promise;
};
 this.initFieldList= function()
{
var promise= $http
.post("field/search",
{});
return promise;
};
 this.initAnnotationAttributeList= function()
{
var promise= $http
.post("annotationAttribute/search",
{});
return promise;
};
 this.initRelationshipList= function()
{
var promise= $http
.post("relationship/search",
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
{ name: 'annotationId'},
{ name: 'enumField.enumFieldId', displayName: 'enumField'},
{ name: 'field.fieldId', displayName: 'field'},
{ name: 'relationship.relationshipId', displayName: 'relationship'} 
]
 };
};
