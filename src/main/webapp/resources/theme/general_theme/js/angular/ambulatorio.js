var ambulatorioApp=angular.module("ambulatorioApp",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])
.service("ambulatorioService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,pazienteList: []};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
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
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
} else 
this.emptyList(this.selectedEntity[val]);
} else {
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
} else {
this.selectedEntity[val] = entity[val];
}
}
}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../ambulatorio/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
this.setSelectedEntity(null);
var promise= $http.get("../ambulatorio/"+entity.ambulatorioId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../ambulatorio/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../ambulatorio/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../ambulatorio/"+this.selectedEntity.ambulatorioId;
var promise= $http["delete"](url);
return promise; 
}
 this.initPazienteList= function()
{
var promise= $http
.post("../paziente/search",
{});
return promise;
};
})
.controller("ambulatorioController",function($scope,$http,ambulatorioService,pazienteService,fascicoloService)
{
//null
$scope.searchBean=ambulatorioService.searchBean;
$scope.entityList=ambulatorioService.entityList;
$scope.selectedEntity=ambulatorioService.selectedEntity;
$scope.childrenList=ambulatorioService.childrenList; 
$scope.reset = function()
{
ambulatorioService.resetSearchBean();
$scope.searchBean=ambulatorioService.searchBean;ambulatorioService.setSelectedEntity(null);
ambulatorioService.selectedEntity.show=false;
ambulatorioService.setEntityList(null); 
pazienteService.selectedEntity.show=false;fascicoloService.selectedEntity.show=false;}
$scope.addNew= function()
{
ambulatorioService.setSelectedEntity(null);
ambulatorioService.setEntityList(null);
ambulatorioService.selectedEntity.show=true;
pazienteService.selectedEntity.show=false;fascicoloService.selectedEntity.show=false;$('#ambulatorioTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
ambulatorioService.selectedEntity.show=false;
ambulatorioService.searchBean.pazienteList=[];
ambulatorioService.searchBean.pazienteList.push(ambulatorioService.searchBean.paziente);
delete ambulatorioService.searchBean.paziente; 
ambulatorioService.search().then(function successCallback(response) {
ambulatorioService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.ambulatorioDetailForm.$valid) return; 
ambulatorioService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.ambulatorioDetailForm.$valid) return; 
pazienteService.selectedEntity.show=false;fascicoloService.selectedEntity.show=false;ambulatorioService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.del=function()
{
nullService.selectedEntity.ambulatorio=null;
ambulatorioService.del().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
 $scope.pazienteGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showPazienteDetail= function(index)
{
if (index!=null)
{
pazienteService.searchOne(ambulatorioService.selectedEntity.pazienteList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
pazienteService.setSelectedEntity(response.data[0]);
pazienteService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (ambulatorioService.selectedEntity.paziente==null || ambulatorioService.selectedEntity.paziente==undefined)
{
pazienteService.setSelectedEntity(null); 
pazienteService.selectedEntity.show=true; 
}
else
pazienteService.searchOne(ambulatorioService.selectedEntity.paziente).then(
function successCallback(response) {
pazienteService.setSelectedEntity(response.data[0]);
pazienteService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#pazienteTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
ambulatorioService.initPazienteList().then(function successCallback(response) {
ambulatorioService.childrenList.pazienteList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
$scope.ambulatorioGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'ambulatorioId'},
{ name: 'nome'},
{ name: 'indirizzo'} 
]
,data: ambulatorioService.entityList
 };
$scope.ambulatorioGridOptions.onRegisterApi = function(gridApi){
$scope.ambulatorioGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
pazienteService.selectedEntity.show=false;fascicoloService.selectedEntity.show=false;if (row.isSelected)
{
ambulatorioService.setSelectedEntity(row.entity);
$('#ambulatorioTabs li:eq(0) a').tab('show');
}
else 
ambulatorioService.setSelectedEntity(null);
ambulatorioService.selectedEntity.show = row.isSelected;
});
  };
$scope.pazienteListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'pazienteId'},
{ name: 'nome'},
{ name: 'cognome'},
{ name: 'birthDate', cellFilter: "date:'dd-MM-yyyy'"} 
]
,data: $scope.selectedEntity.pazienteList
 };
$scope.pazienteListGridOptions.onRegisterApi = function(gridApi){
$scope.pazienteGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
pazienteService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
pazienteService.setSelectedEntity(response.data[0]);
});
$('#pazienteTabs li:eq(0) a').tab('show');
}
else 
pazienteService.setSelectedEntity(null);
pazienteService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("ambulatorio.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedPaziente= function() {
ambulatorioService.selectedEntity.pazienteList.push(ambulatorioService.selectedEntity.paziente);
}
$scope.downloadPazienteList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("paziente.xls",?) FROM ?',[mystyle,$scope.selectedEntity.pazienteList]);
};
})
.service("pazienteService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,fascicoloList: [],ambulatorioList: []};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
for (i=0; i<entityList.length; i++)
this.entityList.push(entityList[i]);
};
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
} else 
this.emptyList(this.selectedEntity[val]);
} else {
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
} else {
this.selectedEntity[val] = entity[val];
}
}
}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../paziente/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
this.setSelectedEntity(null);
var promise= $http.get("../paziente/"+entity.pazienteId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../paziente/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../paziente/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../paziente/"+this.selectedEntity.pazienteId;
var promise= $http["delete"](url);
return promise; 
}
 this.initFascicoloList= function()
{
var promise= $http
.post("../fascicolo/search",
{});
return promise;
};
 this.initAmbulatorioList= function()
{
var promise= $http
.post("../ambulatorio/search",
{});
return promise;
};
})
.controller("pazienteController",function($scope,$http,pazienteService,fascicoloService,ambulatorioService)
{
//ambulatorio
$scope.searchBean=pazienteService.searchBean;
$scope.entityList=pazienteService.entityList;
$scope.selectedEntity=pazienteService.selectedEntity;
$scope.childrenList=pazienteService.childrenList; 
$scope.reset = function()
{
pazienteService.resetSearchBean();
$scope.searchBean=pazienteService.searchBean;pazienteService.setSelectedEntity(null);
pazienteService.selectedEntity.show=false;
pazienteService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
ambulatorioService.update().then(function successCallback(response) {
ambulatorioService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
alert("error");
return; 
}
);
};
$scope.addNew= function()
{
pazienteService.setSelectedEntity(null);
pazienteService.setEntityList(null);
pazienteService.selectedEntity.show=true;
$('#pazienteTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
pazienteService.selectedEntity.show=false;
pazienteService.searchBean.fascicoloList=[];
pazienteService.searchBean.fascicoloList.push(pazienteService.searchBean.fascicolo);
delete pazienteService.searchBean.fascicolo; 
pazienteService.searchBean.ambulatorioList=[];
pazienteService.searchBean.ambulatorioList.push(pazienteService.searchBean.ambulatorio);
delete pazienteService.searchBean.ambulatorio; 
pazienteService.search().then(function successCallback(response) {
pazienteService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.pazienteDetailForm.$valid) return; 
pazienteService.selectedEntity.show=false;
pazienteService.selectedEntity.ambulatorioList.push(ambulatorioService.selectedEntity);
pazienteService.insert().then(function successCallBack(response) { 
ambulatorioService.selectedEntity.pazienteList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.pazienteDetailForm.$valid) return; 
pazienteService.selectedEntity.show=false;

for (i=0; i<ambulatorioService.selectedEntity.pazienteList.length; i++)

{

if (ambulatorioService.selectedEntity.pazienteList[i].pazienteId==pazienteService.selectedEntity.pazienteId)

ambulatorioService.selectedEntity.pazienteList.splice(i,1);

}

ambulatorioService.selectedEntity.pazienteList.push(pazienteService.selectedEntity);

pazienteService.update().then(function successCallback(response){
pazienteService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
pazienteService.selectedEntity.show=false;
for (i=0; i<ambulatorioService.selectedEntity.pazienteList.length; i++)
{
if (ambulatorioService.selectedEntity.pazienteList[i].pazienteId==pazienteService.selectedEntity.pazienteId)
ambulatorioService.selectedEntity.pazienteList.splice(i,1);
}
pazienteService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<ambulatorioService.selectedEntity.pazienteList.length; i++)
{
if (ambulatorioService.selectedEntity.pazienteList[i].pazienteId==pazienteService.selectedEntity.pazienteId)
ambulatorioService.selectedEntity.pazienteList.splice(i,1);
}
$scope.updateParent();
pazienteService.del().then(function successCallback(response) { 
pazienteService.setSelectedEntity(null);
ambulatorioService.initPazienteList().then(function(response) {
ambulatorioService.childrenList.pazienteList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDatianagrafici= function() 
{
};
$scope.refreshTableRel= function() 
{
 $scope.fascicoloGridApi.core.handleWindowResize(); 
 $scope.ambulatorioGridApi.core.handleWindowResize(); 
};
$scope.trueFalseValues=[true,false];
$scope.showFascicoloDetail= function(index)
{
if (index!=null)
{
fascicoloService.searchOne(pazienteService.selectedEntity.fascicoloList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
fascicoloService.setSelectedEntity(response.data[0]);
fascicoloService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (pazienteService.selectedEntity.fascicolo==null || pazienteService.selectedEntity.fascicolo==undefined)
{
fascicoloService.setSelectedEntity(null); 
fascicoloService.selectedEntity.show=true; 
}
else
fascicoloService.searchOne(pazienteService.selectedEntity.fascicolo).then(
function successCallback(response) {
fascicoloService.setSelectedEntity(response.data[0]);
fascicoloService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#fascicoloTabs li:eq(0) a').tab('show');
};
$scope.showAmbulatorioDetail= function(index)
{
if (index!=null)
{
ambulatorioService.searchOne(pazienteService.selectedEntity.ambulatorioList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
ambulatorioService.setSelectedEntity(response.data[0]);
ambulatorioService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (pazienteService.selectedEntity.ambulatorio==null || pazienteService.selectedEntity.ambulatorio==undefined)
{
ambulatorioService.setSelectedEntity(null); 
ambulatorioService.selectedEntity.show=true; 
}
else
ambulatorioService.searchOne(pazienteService.selectedEntity.ambulatorio).then(
function successCallback(response) {
ambulatorioService.setSelectedEntity(response.data[0]);
ambulatorioService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#ambulatorioTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
pazienteService.initFascicoloList().then(function successCallback(response) {
pazienteService.childrenList.fascicoloList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
pazienteService.initAmbulatorioList().then(function successCallback(response) {
pazienteService.childrenList.ambulatorioList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
$scope.fascicoloListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'fascicoloId'},
{ name: 'creationDate', cellFilter: "date:'dd-MM-yyyy'"},
{ name: 'tipoIntervento'} 
]
,data: $scope.selectedEntity.fascicoloList
 };
$scope.fascicoloListGridOptions.onRegisterApi = function(gridApi){
$scope.fascicoloGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
fascicoloService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
fascicoloService.setSelectedEntity(response.data[0]);
});
$('#fascicoloTabs li:eq(0) a').tab('show');
}
else 
fascicoloService.setSelectedEntity(null);
fascicoloService.selectedEntity.show = row.isSelected;
});
  };
$scope.ambulatorioListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'ambulatorioId'},
{ name: 'nome'},
{ name: 'indirizzo'} 
]
,data: $scope.selectedEntity.ambulatorioList
 };
$scope.ambulatorioListGridOptions.onRegisterApi = function(gridApi){
$scope.ambulatorioGridApi = gridApi;gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
ambulatorioService.searchOne(row.entity).then(function(response) { 
console.log(response.data);
ambulatorioService.setSelectedEntity(response.data[0]);
});
$('#ambulatorioTabs li:eq(0) a').tab('show');
}
else 
ambulatorioService.setSelectedEntity(null);
ambulatorioService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("paziente.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.saveLinkedFascicolo= function() {
pazienteService.selectedEntity.fascicoloList.push(pazienteService.selectedEntity.fascicolo);
}
$scope.downloadFascicoloList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("fascicolo.xls",?) FROM ?',[mystyle,$scope.selectedEntity.fascicoloList]);
};
$scope.saveLinkedAmbulatorio= function() {
pazienteService.selectedEntity.ambulatorioList.push(pazienteService.selectedEntity.ambulatorio);
}
$scope.downloadAmbulatorioList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("ambulatorio.xls",?) FROM ?',[mystyle,$scope.selectedEntity.ambulatorioList]);
};
})
.service("fascicoloService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
};
this.childrenList=[]; 
this.addEntity=function (entity)
{
this.entityList.push(entity);
};
this.emptyList= function(list)
{
while (list.length>0)
list.pop();
}
this.setEntityList= function(entityList)
{ 
while (this.entityList.length>0)
this.entityList.pop();
if (entityList!=null)
for (i=0; i<entityList.length; i++)
this.entityList.push(entityList[i]);
};
this.setSelectedEntity= function (entity)
{ 
if (entity == null) {
entity = {};
this.selectedEntity.show = false;
} //else
var keyList = Object.keys(entity);
if (keyList.length == 0)
keyList = Object.keys(this.selectedEntity);
for (i = 0; i < keyList.length; i++) {
var val = keyList[i];
if (val != undefined) {
if (val.toLowerCase().indexOf("list") > -1
&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
if (entity[val] != null
&& entity[val] != undefined) {
if (this.selectedEntity[val]!=undefined)
while (this.selectedEntity[val].length > 0)
this.selectedEntity[val].pop();
if (entity[val] != null)
for (j = 0; j < entity[val].length; j++)
this.selectedEntity[val]
.push(entity[val][j]);
} else 
this.emptyList(this.selectedEntity[val]);
} else {
if (val.toLowerCase().indexOf("time") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(entity[val]);
} else {
this.selectedEntity[val] = entity[val];
}
}
}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../fascicolo/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
this.setSelectedEntity(null);
var promise= $http.get("../fascicolo/"+entity.fascicoloId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../fascicolo/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../fascicolo/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../fascicolo/"+this.selectedEntity.fascicoloId;
var promise= $http["delete"](url);
return promise; 
}
 this.initPazienteList= function()
{
var promise= $http
.post("../paziente/search",
{});
return promise;
};
 this.initAmbulatorioList= function()
{
var promise= $http
.post("../ambulatorio/search",
{});
return promise;
};
})
.controller("fascicoloController",function($scope,$http,fascicoloService,pazienteService,ambulatorioService)
{
//paziente
$scope.searchBean=fascicoloService.searchBean;
$scope.entityList=fascicoloService.entityList;
$scope.selectedEntity=fascicoloService.selectedEntity;
$scope.childrenList=fascicoloService.childrenList; 
$scope.reset = function()
{
fascicoloService.resetSearchBean();
$scope.searchBean=fascicoloService.searchBean;fascicoloService.setSelectedEntity(null);
fascicoloService.selectedEntity.show=false;
fascicoloService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
pazienteService.update().then(function successCallback(response) {
pazienteService.setSelectedEntity(response);
if (toDo != null)
toDo();
},function errorCallback(response) {      
alert("error");
return; 
}
);
};
$scope.addNew= function()
{
fascicoloService.setSelectedEntity(null);
fascicoloService.setEntityList(null);
fascicoloService.selectedEntity.show=true;
$('#fascicoloTabs li:eq(0) a').tab('show');
};
		
$scope.search=function()
{
fascicoloService.selectedEntity.show=false;
fascicoloService.search().then(function successCallback(response) {
fascicoloService.setEntityList(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.insert=function()
{
if (!$scope.fascicoloDetailForm.$valid) return; 
fascicoloService.selectedEntity.show=false;
fascicoloService.selectedEntity.paziente={};
fascicoloService.selectedEntity.paziente.pazienteId=pazienteService.selectedEntity.pazienteId;
fascicoloService.insert().then(function successCallBack(response) { 
pazienteService.selectedEntity.fascicoloList.push(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.update=function()
{
if (!$scope.fascicoloDetailForm.$valid) return; 
fascicoloService.selectedEntity.show=false;

for (i=0; i<pazienteService.selectedEntity.fascicoloList.length; i++)

{

if (pazienteService.selectedEntity.fascicoloList[i].fascicoloId==fascicoloService.selectedEntity.fascicoloId)

pazienteService.selectedEntity.fascicoloList.splice(i,1);

}

pazienteService.selectedEntity.fascicoloList.push(fascicoloService.selectedEntity);

fascicoloService.update().then(function successCallback(response){
fascicoloService.setSelectedEntity(response.data);
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.remove= function()
{
fascicoloService.selectedEntity.show=false;
for (i=0; i<pazienteService.selectedEntity.fascicoloList.length; i++)
{
if (pazienteService.selectedEntity.fascicoloList[i].fascicoloId==fascicoloService.selectedEntity.fascicoloId)
pazienteService.selectedEntity.fascicoloList.splice(i,1);
}
fascicoloService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<pazienteService.selectedEntity.fascicoloList.length; i++)
{
if (pazienteService.selectedEntity.fascicoloList[i].fascicoloId==fascicoloService.selectedEntity.fascicoloId)
pazienteService.selectedEntity.fascicoloList.splice(i,1);
}
$scope.updateParent();
fascicoloService.del().then(function successCallback(response) { 
fascicoloService.setSelectedEntity(null);
pazienteService.initFascicoloList().then(function(response) {
pazienteService.childrenList.fascicoloList=response.data;
});
},function errorCallback(response) { 
alert("error");
return; 
});
};
$scope.refreshTableDetail= function() 
{
};
$scope.trueFalseValues=[true,false];
$scope.showPazienteDetail= function(index)
{
if (index!=null)
{
pazienteService.searchOne(fascicoloService.selectedEntity.pazienteList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
pazienteService.setSelectedEntity(response.data[0]);
pazienteService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (fascicoloService.selectedEntity.paziente==null || fascicoloService.selectedEntity.paziente==undefined)
{
pazienteService.setSelectedEntity(null); 
pazienteService.selectedEntity.show=true; 
}
else
pazienteService.searchOne(fascicoloService.selectedEntity.paziente).then(
function successCallback(response) {
pazienteService.setSelectedEntity(response.data[0]);
pazienteService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#pazienteTabs li:eq(0) a').tab('show');
};
$scope.showAmbulatorioDetail= function(index)
{
if (index!=null)
{
ambulatorioService.searchOne(fascicoloService.selectedEntity.ambulatorioList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
ambulatorioService.setSelectedEntity(response.data[0]);
ambulatorioService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
else 
{
if (fascicoloService.selectedEntity.ambulatorio==null || fascicoloService.selectedEntity.ambulatorio==undefined)
{
ambulatorioService.setSelectedEntity(null); 
ambulatorioService.selectedEntity.show=true; 
}
else
ambulatorioService.searchOne(fascicoloService.selectedEntity.ambulatorio).then(
function successCallback(response) {
ambulatorioService.setSelectedEntity(response.data[0]);
ambulatorioService.selectedEntity.show=true;
  }, function errorCallback(response) {
alert("error");
return; 
  }	
);
}
$('#ambulatorioTabs li:eq(0) a').tab('show');
};
$scope.init=function()
{
fascicoloService.initPazienteList().then(function successCallback(response) {
fascicoloService.childrenList.pazienteList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
fascicoloService.initAmbulatorioList().then(function successCallback(response) {
fascicoloService.childrenList.ambulatorioList=response.data;
},function errorCallback(response) { 
alert("error");
return; 
});
}; 
$scope.init();
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("fascicolo.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadPazienteList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("paziente.xls",?) FROM ?',[mystyle,$scope.selectedEntity.pazienteList]);
};
$scope.downloadAmbulatorioList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("ambulatorio.xls",?) FROM ?',[mystyle,$scope.selectedEntity.ambulatorioList]);
};
})
;