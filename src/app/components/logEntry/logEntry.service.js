angular.module("anggenApp").service("logEntryService", logEntryService);
function logEntryService($http,mainService)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
};
this.isParent=function()
{
return mainService.parentEntity=="LogEntry";
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
var promise= $http.post("logEntry/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
var promise= $http.get("logEntry/"+entity.logEntryId);
return promise; 
};
this.insert = function() {
var promise= $http.put("logEntry/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("logEntry/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="logEntry/"+this.selectedEntity.logEntryId;
var promise= $http["delete"](url);
return promise; 
}
this.loadFile= function(file,field){
var formData = new FormData();
if (file!=null)
formData.append('file',file);
var promise= $http.post("logEntry/"+this.selectedEntity.logEntryId+"/load"+field+"/",formData,{
 headers: {'Content-Type': undefined}
});
return promise; 
}
 this.initEntityList= function()
{
var promise= $http
.post("entity/search",
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
this.gridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [10, 20, 30],
paginationPageSize: 10,
enableGridMenu: true,
columnDefs: [    
{ name: 'logEntryId'},
{ name: 'info'},
{ name: 'hostName'},
{ name: 'ipAddress'},
{ name: 'logDate', cellFilter: "date:'dd-MM-yyyy'"},
{ name: 'entity.entityId', displayName: 'entity'},
{ name: 'user.userId', displayName: 'user'} 
]
 };
};