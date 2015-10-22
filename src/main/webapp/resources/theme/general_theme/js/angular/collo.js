var colloApp=angular.module("colloApp",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])
.service("colloService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
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
}
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
var promise= $http.post("../collo/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
this.setSelectedEntity(null);
var promise= $http.get("../collo/"+entity.colloId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../collo/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../collo/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../collo/"+this.selectedEntity.colloId;
var promise= $http["delete"](url);
return promise; 
}
 this.initOrdineList= function()
{
var promise= $http
.post("../ordine/search",
{});
return promise;
};
})
.controller("colloController",function($scope,$http,colloService,ordineService,itemOrdineService,itemOrdineCodiceService)
{
$scope.searchBean=colloService.searchBean;
$scope.entityList=colloService.entityList;
$scope.selectedEntity=colloService.selectedEntity;
$scope.childrenList=colloService.childrenList; 
$scope.reset = function()
{
colloService.resetSearchBean();
$scope.searchBean=colloService.searchBean;colloService.setSelectedEntity(null);
colloService.selectedEntity.show=false;
colloService.setEntityList(null); 
ordineService.selectedEntity.show=false;itemOrdineService.selectedEntity.show=false;itemOrdineCodiceService.selectedEntity.show=false;}
$scope.addNew= function()
{
colloService.setSelectedEntity(null);
colloService.setEntityList(null);
colloService.selectedEntity.show=true;
ordineService.selectedEntity.show=false;itemOrdineService.selectedEntity.show=false;itemOrdineCodiceService.selectedEntity.show=false;};
		
$scope.search=function()
{
colloService.selectedEntity.show=false;
colloService.search().then(function successCallback(response) {
colloService.setEntityList(response.data);
},function errorCallback(response) { 
return; 
});
};
$scope.insert=function()
{
if (!$scope.colloDetailForm.$valid) return; 
colloService.insert().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
return; 
});
};
$scope.update=function()
{
if (!$scope.colloDetailForm.$valid) return; 
ordineService.selectedEntity.show=false;itemOrdineService.selectedEntity.show=false;itemOrdineCodiceService.selectedEntity.show=false;colloService.update().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
return; 
});
};
$scope.del=function()
{
nullService.selectedEntity.collo=null;
colloService.del().then(function successCallback(response) { 
$scope.search();
},function errorCallback(response) { 
return; 
});
};
$scope.trueFalseValues=[true,false];
$scope.showOrdineDetail= function(index)
{
if (index!=null)
{
ordineService.searchOne(colloService.selectedEntity.ordineList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
ordineService.setSelectedEntity(response.data[0]);
ordineService.selectedEntity.show=true;
  }, function errorCallback(response) {
return; 
  }	
);
}
else 
{
if (colloService.selectedEntity.ordine==null || colloService.selectedEntity.ordine==undefined)
{
ordineService.setSelectedEntity(null); 
ordineService.selectedEntity.show=true; 
}
else
ordineService.searchOne(colloService.selectedEntity.ordine).then(
function successCallback(response) {
ordineService.setSelectedEntity(response.data[0]);
ordineService.selectedEntity.show=true;
  }, function errorCallback(response) {
return; 
  }	
);
}
};
$scope.init=function()
{
colloService.initOrdineList().then(function successCallback(response) {
colloService.childrenList.ordineList=response.data;
},function errorCallback(response) { 
return; 
});
}; 
$scope.init();
$scope.colloGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'colloId'},
{ name: 'codice'},
{ name: 'cutoffRoadnet'},
{ name: 'ordinamentoReport'},
{ name: 'tsGenerazione', cellFilter: "date:'dd-MM-yyyy'"},
{ name: 'ordine.ordineId', displayName: 'ordine'} 
]
,data: colloService.entityList
 };
$scope.colloGridOptions.onRegisterApi = function(gridApi){
gridApi.selection.on.rowSelectionChanged($scope,function(row){
ordineService.selectedEntity.show=false;itemOrdineService.selectedEntity.show=false;itemOrdineCodiceService.selectedEntity.show=false;if (row.isSelected)
{
colloService.setSelectedEntity(row.entity);
}
else 
colloService.setSelectedEntity(null);
colloService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("collo.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadOrdineList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("ordine.xls",?) FROM ?',[mystyle,$scope.selectedEntity.ordineList]);
};
})
.service("ordineService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,colloList: [],itemOrdineList: []};
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
}
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
var promise= $http.post("../ordine/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
this.setSelectedEntity(null);
var promise= $http.get("../ordine/"+entity.ordineId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../ordine/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../ordine/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../ordine/"+this.selectedEntity.ordineId;
var promise= $http["delete"](url);
return promise; 
}
 this.initColloList= function()
{
var promise= $http
.post("../collo/search",
{});
return promise;
};
 this.initItemOrdineList= function()
{
var promise= $http
.post("../itemOrdine/search",
{});
return promise;
};
})
.controller("ordineController",function($scope,$http,ordineService,colloService,itemOrdineService,itemOrdineCodiceService)
{
$scope.searchBean=ordineService.searchBean;
$scope.entityList=ordineService.entityList;
$scope.selectedEntity=ordineService.selectedEntity;
$scope.childrenList=ordineService.childrenList; 
$scope.reset = function()
{
ordineService.resetSearchBean();
$scope.searchBean=ordineService.searchBean;ordineService.setSelectedEntity(null);
ordineService.selectedEntity.show=false;
ordineService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
colloService.update().then(function successCallback(response) {
colloService.setSelectedEntity(data);
if (toDo != null)
toDo();
},function errorCallback(response) {      
return; 
}
);
};
$scope.addNew= function()
{
ordineService.setSelectedEntity(null);
ordineService.setEntityList(null);
ordineService.selectedEntity.show=true;
};
		
$scope.search=function()
{
ordineService.selectedEntity.show=false;
ordineService.searchBean.colloList=[];
ordineService.searchBean.colloList.push(ordineService.searchBean.collo);
delete ordineService.searchBean.collo; 
ordineService.searchBean.itemOrdineList=[];
ordineService.searchBean.itemOrdineList.push(ordineService.searchBean.itemOrdine);
delete ordineService.searchBean.itemOrdine; 
ordineService.search().then(function successCallback(response) {
ordineService.setEntityList(response.data);
},function errorCallback(response) { 
return; 
});
};
$scope.insert=function()
{
if (!$scope.ordineDetailForm.$valid) return; 
ordineService.selectedEntity.show=false;

ordineService.selectedEntity.show=false;
ordineService.selectedEntity.collo={};
ordineService.selectedEntity.collo.colloId=colloService.selectedEntity.colloId;
ordineService.insert().then(function successCallBack(response) { 
colloService.selectedEntity.ordine=response.data;
colloService.initOrdineList().then(function(response) {
colloService.childrenList.ordineList=response.data;
});
},function errorCallback(response) { 
return; 
});
};
$scope.update=function()
{
if (!$scope.ordineDetailForm.$valid) return; 
ordineService.selectedEntity.show=false;

colloService.selectedEntity.ordine=ordineService.selectedEntity;

ordineService.update().then(function successCallback(response){
ordineService.setSelectedEntity(response.data);
},function errorCallback(response) { 
return; 
});
};
$scope.remove= function()
{
ordineService.selectedEntity.show=false;
colloService.selectedEntity.ordine=null;
ordineService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
colloService.selectedEntity.ordine=null;
$scope.updateParent();
ordineService.del().then(function successCallback(response) { 
ordineService.setSelectedEntity(null);
colloService.initOrdineList().then(function(response) {
colloService.childrenList.ordineList=response.data;
});
},function errorCallback(response) { 
return; 
});
};
$scope.trueFalseValues=[true,false];
$scope.showColloDetail= function(index)
{
if (index!=null)
{
colloService.searchOne(ordineService.selectedEntity.colloList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
colloService.setSelectedEntity(response.data[0]);
colloService.selectedEntity.show=true;
  }, function errorCallback(response) {
return; 
  }	
);
}
else 
{
if (ordineService.selectedEntity.collo==null || ordineService.selectedEntity.collo==undefined)
{
colloService.setSelectedEntity(null); 
colloService.selectedEntity.show=true; 
}
else
colloService.searchOne(ordineService.selectedEntity.collo).then(
function successCallback(response) {
colloService.setSelectedEntity(response.data[0]);
colloService.selectedEntity.show=true;
  }, function errorCallback(response) {
return; 
  }	
);
}
};
$scope.showItemOrdineDetail= function(index)
{
if (index!=null)
{
itemOrdineService.searchOne(ordineService.selectedEntity.itemOrdineList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
itemOrdineService.setSelectedEntity(response.data[0]);
itemOrdineService.selectedEntity.show=true;
  }, function errorCallback(response) {
return; 
  }	
);
}
else 
{
if (ordineService.selectedEntity.itemOrdine==null || ordineService.selectedEntity.itemOrdine==undefined)
{
itemOrdineService.setSelectedEntity(null); 
itemOrdineService.selectedEntity.show=true; 
}
else
itemOrdineService.searchOne(ordineService.selectedEntity.itemOrdine).then(
function successCallback(response) {
itemOrdineService.setSelectedEntity(response.data[0]);
itemOrdineService.selectedEntity.show=true;
  }, function errorCallback(response) {
return; 
  }	
);
}
};
$scope.init=function()
{
ordineService.initColloList().then(function successCallback(response) {
ordineService.childrenList.colloList=response.data;
},function errorCallback(response) { 
return; 
});
ordineService.initItemOrdineList().then(function successCallback(response) {
ordineService.childrenList.itemOrdineList=response.data;
},function errorCallback(response) { 
return; 
});
}; 
$scope.init();
$scope.colloListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'colloId'},
{ name: 'codice'},
{ name: 'cutoffRoadnet'},
{ name: 'ordinamentoReport'},
{ name: 'tsGenerazione', cellFilter: "date:'dd-MM-yyyy'"} 
]
,data: $scope.selectedEntity.colloList
 };
$scope.colloListGridOptions.onRegisterApi = function(gridApi){
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
colloService.searchOne(row.entity).then(function(data) { 
console.log(data);
colloService.setSelectedEntity(data[0]);
});
}
else 
colloService.setSelectedEntity(null);
colloService.selectedEntity.show = row.isSelected;
});
  };
$scope.itemOrdineListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'itemOrdineId'},
{ name: 'barcode'},
{ name: 'carfCanaleLocale'},
{ name: 'daCancellare'},
{ name: 'format'},
{ name: 'idStatoFeedback'},
{ name: 'idStatoItemOrdine'},
{ name: 'idStatoPreparazione'},
{ name: 'infoFeedback'},
{ name: 'name'},
{ name: 'note'},
{ name: 'priorita'},
{ name: 'productStatus'},
{ name: 'promoPunti'},
{ name: 'quantityFinale'},
{ name: 'quantityIniz'},
{ name: 'singleUnitWeight'},
{ name: 'sostituito'},
{ name: 'tsModifica', cellFilter: "date:'dd-MM-yyyy'"},
{ name: 'tsPreparazione', cellFilter: "date:'dd-MM-yyyy'"},
{ name: 'vOrderItemId'},
{ name: 'vPrice'},
{ name: 'vProductId'},
{ name: 'weightFinale'},
{ name: 'weightIniz'} 
]
,data: $scope.selectedEntity.itemOrdineList
 };
$scope.itemOrdineListGridOptions.onRegisterApi = function(gridApi){
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
itemOrdineService.searchOne(row.entity).then(function(data) { 
console.log(data);
itemOrdineService.setSelectedEntity(data[0]);
});
}
else 
itemOrdineService.setSelectedEntity(null);
itemOrdineService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("ordine.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadColloList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("collo.xls",?) FROM ?',[mystyle,$scope.selectedEntity.colloList]);
};
$scope.downloadItemOrdineList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("itemOrdine.xls",?) FROM ?',[mystyle,$scope.selectedEntity.itemOrdineList]);
};
})
.service("itemOrdineService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
,itemOrdineCodiceList: []};
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
}
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
var promise= $http.post("../itemOrdine/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
this.setSelectedEntity(null);
var promise= $http.get("../itemOrdine/"+entity.itemOrdineId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../itemOrdine/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../itemOrdine/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../itemOrdine/"+this.selectedEntity.itemOrdineId;
var promise= $http["delete"](url);
return promise; 
}
 this.initOrdineList= function()
{
var promise= $http
.post("../ordine/search",
{});
return promise;
};
 this.initItemOrdineCodiceList= function()
{
var promise= $http
.post("../itemOrdineCodice/search",
{});
return promise;
};
})
.controller("itemOrdineController",function($scope,$http,itemOrdineService,ordineService,colloService,itemOrdineCodiceService)
{
$scope.searchBean=itemOrdineService.searchBean;
$scope.entityList=itemOrdineService.entityList;
$scope.selectedEntity=itemOrdineService.selectedEntity;
$scope.childrenList=itemOrdineService.childrenList; 
$scope.reset = function()
{
itemOrdineService.resetSearchBean();
$scope.searchBean=itemOrdineService.searchBean;itemOrdineService.setSelectedEntity(null);
itemOrdineService.selectedEntity.show=false;
itemOrdineService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
ordineService.update().then(function successCallback(response) {
ordineService.setSelectedEntity(data);
if (toDo != null)
toDo();
},function errorCallback(response) {      
return; 
}
);
};
$scope.addNew= function()
{
itemOrdineService.setSelectedEntity(null);
itemOrdineService.setEntityList(null);
itemOrdineService.selectedEntity.show=true;
};
		
$scope.search=function()
{
itemOrdineService.selectedEntity.show=false;
itemOrdineService.searchBean.itemOrdineCodiceList=[];
itemOrdineService.searchBean.itemOrdineCodiceList.push(itemOrdineService.searchBean.itemOrdineCodice);
delete itemOrdineService.searchBean.itemOrdineCodice; 
itemOrdineService.search().then(function successCallback(response) {
itemOrdineService.setEntityList(response.data);
},function errorCallback(response) { 
return; 
});
};
$scope.insert=function()
{
if (!$scope.itemOrdineDetailForm.$valid) return; 
itemOrdineService.selectedEntity.show=false;

itemOrdineService.selectedEntity.show=false;
itemOrdineService.selectedEntity.ordine={};
itemOrdineService.selectedEntity.ordine.ordineId=ordineService.selectedEntity.ordineId;
itemOrdineService.insert().then(function successCallBack(response) { 
ordineService.selectedEntity.itemOrdineList.push(response.data);
},function errorCallback(response) { 
return; 
});
};
$scope.update=function()
{
if (!$scope.itemOrdineDetailForm.$valid) return; 
itemOrdineService.selectedEntity.show=false;

for (i=0; i<ordineService.selectedEntity.itemOrdineList.length; i++)

{

if (ordineService.selectedEntity.itemOrdineList[i].itemOrdineId==itemOrdineService.selectedEntity.itemOrdineId)

ordineService.selectedEntity.itemOrdineList.splice(i,1);

}

ordineService.selectedEntity.itemOrdineList.push(itemOrdineService.selectedEntity);

itemOrdineService.update().then(function successCallback(response){
itemOrdineService.setSelectedEntity(response.data);
},function errorCallback(response) { 
return; 
});
};
$scope.remove= function()
{
itemOrdineService.selectedEntity.show=false;
for (i=0; i<ordineService.selectedEntity.itemOrdineList.length; i++)
{
if (ordineService.selectedEntity.itemOrdineList[i].itemOrdineId==itemOrdineService.selectedEntity.itemOrdineId)
ordineService.selectedEntity.itemOrdineList.splice(i,1);
}
itemOrdineService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<ordineService.selectedEntity.itemOrdineList.length; i++)
{
if (ordineService.selectedEntity.itemOrdineList[i].itemOrdineId==itemOrdineService.selectedEntity.itemOrdineId)
ordineService.selectedEntity.itemOrdineList.splice(i,1);
}
$scope.updateParent();
itemOrdineService.del().then(function successCallback(response) { 
itemOrdineService.setSelectedEntity(null);
ordineService.initItemOrdineList().then(function(response) {
ordineService.childrenList.itemOrdineList=response.data;
});
},function errorCallback(response) { 
return; 
});
};
$scope.trueFalseValues=[true,false];
$scope.showOrdineDetail= function(index)
{
if (index!=null)
{
ordineService.searchOne(itemOrdineService.selectedEntity.ordineList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
ordineService.setSelectedEntity(response.data[0]);
ordineService.selectedEntity.show=true;
  }, function errorCallback(response) {
return; 
  }	
);
}
else 
{
if (itemOrdineService.selectedEntity.ordine==null || itemOrdineService.selectedEntity.ordine==undefined)
{
ordineService.setSelectedEntity(null); 
ordineService.selectedEntity.show=true; 
}
else
ordineService.searchOne(itemOrdineService.selectedEntity.ordine).then(
function successCallback(response) {
ordineService.setSelectedEntity(response.data[0]);
ordineService.selectedEntity.show=true;
  }, function errorCallback(response) {
return; 
  }	
);
}
};
$scope.showItemOrdineCodiceDetail= function(index)
{
if (index!=null)
{
itemOrdineCodiceService.searchOne(itemOrdineService.selectedEntity.itemOrdineCodiceList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
itemOrdineCodiceService.setSelectedEntity(response.data[0]);
itemOrdineCodiceService.selectedEntity.show=true;
  }, function errorCallback(response) {
return; 
  }	
);
}
else 
{
if (itemOrdineService.selectedEntity.itemOrdineCodice==null || itemOrdineService.selectedEntity.itemOrdineCodice==undefined)
{
itemOrdineCodiceService.setSelectedEntity(null); 
itemOrdineCodiceService.selectedEntity.show=true; 
}
else
itemOrdineCodiceService.searchOne(itemOrdineService.selectedEntity.itemOrdineCodice).then(
function successCallback(response) {
itemOrdineCodiceService.setSelectedEntity(response.data[0]);
itemOrdineCodiceService.selectedEntity.show=true;
  }, function errorCallback(response) {
return; 
  }	
);
}
};
$scope.init=function()
{
itemOrdineService.initOrdineList().then(function successCallback(response) {
itemOrdineService.childrenList.ordineList=response.data;
},function errorCallback(response) { 
return; 
});
itemOrdineService.initItemOrdineCodiceList().then(function successCallback(response) {
itemOrdineService.childrenList.itemOrdineCodiceList=response.data;
},function errorCallback(response) { 
return; 
});
}; 
$scope.init();
$scope.itemOrdineCodiceListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
enableGridMenu: true,
columnDefs: [
{ name: 'itemOrdineCodiceId'},
{ name: 'barcode'},
{ name: 'barcodeRead'},
{ name: 'daCancellare'},
{ name: 'idStato'},
{ name: 'name'},
{ name: 'nelCarrello'},
{ name: 'quantityFinale'},
{ name: 'tsModifica', cellFilter: "date:'dd-MM-yyyy'"},
{ name: 'vPrice'},
{ name: 'vProductId'},
{ name: 'weightFinale'} 
]
,data: $scope.selectedEntity.itemOrdineCodiceList
 };
$scope.itemOrdineCodiceListGridOptions.onRegisterApi = function(gridApi){
gridApi.selection.on.rowSelectionChanged($scope,function(row){
if (row.isSelected)
{
itemOrdineCodiceService.searchOne(row.entity).then(function(data) { 
console.log(data);
itemOrdineCodiceService.setSelectedEntity(data[0]);
});
}
else 
itemOrdineCodiceService.setSelectedEntity(null);
itemOrdineCodiceService.selectedEntity.show = row.isSelected;
});
  };
$scope.downloadEntityList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("itemOrdine.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadOrdineList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("ordine.xls",?) FROM ?',[mystyle,$scope.selectedEntity.ordineList]);
};
$scope.downloadItemOrdineCodiceList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("itemOrdineCodice.xls",?) FROM ?',[mystyle,$scope.selectedEntity.itemOrdineCodiceList]);
};
})
.service("itemOrdineCodiceService", function($http)
{
this.entityList =		[];
this.selectedEntity= 	{show: false 
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
}
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
var promise= $http.post("../itemOrdineCodice/search",this.searchBean);
return promise; 
};
this.searchOne=function(entity) {
this.setSelectedEntity(null);
var promise= $http.get("../itemOrdineCodice/"+entity.itemOrdineCodiceId);
return promise; 
};
this.insert = function() {
var promise= $http.put("../itemOrdineCodice/",this.selectedEntity);
return promise; 
};
this.update = function() {
var promise= $http.post("../itemOrdineCodice/",this.selectedEntity);
return promise; 
}
this.del = function() {
var url="../itemOrdineCodice/"+this.selectedEntity.itemOrdineCodiceId;
var promise= $http["delete"](url);
return promise; 
}
 this.initItemOrdineList= function()
{
var promise= $http
.post("../itemOrdine/search",
{});
return promise;
};
})
.controller("itemOrdineCodiceController",function($scope,$http,itemOrdineCodiceService,itemOrdineService,ordineService,colloService)
{
$scope.searchBean=itemOrdineCodiceService.searchBean;
$scope.entityList=itemOrdineCodiceService.entityList;
$scope.selectedEntity=itemOrdineCodiceService.selectedEntity;
$scope.childrenList=itemOrdineCodiceService.childrenList; 
$scope.reset = function()
{
itemOrdineCodiceService.resetSearchBean();
$scope.searchBean=itemOrdineCodiceService.searchBean;itemOrdineCodiceService.setSelectedEntity(null);
itemOrdineCodiceService.selectedEntity.show=false;
itemOrdineCodiceService.setEntityList(null); 
}
$scope.updateParent = function(toDo)
{
itemOrdineService.update().then(function successCallback(response) {
itemOrdineService.setSelectedEntity(data);
if (toDo != null)
toDo();
},function errorCallback(response) {      
return; 
}
);
};
$scope.addNew= function()
{
itemOrdineCodiceService.setSelectedEntity(null);
itemOrdineCodiceService.setEntityList(null);
itemOrdineCodiceService.selectedEntity.show=true;
};
		
$scope.search=function()
{
itemOrdineCodiceService.selectedEntity.show=false;
itemOrdineCodiceService.search().then(function successCallback(response) {
itemOrdineCodiceService.setEntityList(response.data);
},function errorCallback(response) { 
return; 
});
};
$scope.insert=function()
{
if (!$scope.itemOrdineCodiceDetailForm.$valid) return; 
itemOrdineCodiceService.selectedEntity.show=false;

itemOrdineCodiceService.selectedEntity.show=false;
itemOrdineCodiceService.selectedEntity.itemOrdine={};
itemOrdineCodiceService.selectedEntity.itemOrdine.itemOrdineId=itemOrdineService.selectedEntity.itemOrdineId;
itemOrdineCodiceService.insert().then(function successCallBack(response) { 
itemOrdineService.selectedEntity.itemOrdineCodiceList.push(response.data);
},function errorCallback(response) { 
return; 
});
};
$scope.update=function()
{
if (!$scope.itemOrdineCodiceDetailForm.$valid) return; 
itemOrdineCodiceService.selectedEntity.show=false;

for (i=0; i<itemOrdineService.selectedEntity.itemOrdineCodiceList.length; i++)

{

if (itemOrdineService.selectedEntity.itemOrdineCodiceList[i].itemOrdineCodiceId==itemOrdineCodiceService.selectedEntity.itemOrdineCodiceId)

itemOrdineService.selectedEntity.itemOrdineCodiceList.splice(i,1);

}

itemOrdineService.selectedEntity.itemOrdineCodiceList.push(itemOrdineCodiceService.selectedEntity);

itemOrdineCodiceService.update().then(function successCallback(response){
itemOrdineCodiceService.setSelectedEntity(response.data);
},function errorCallback(response) { 
return; 
});
};
$scope.remove= function()
{
itemOrdineCodiceService.selectedEntity.show=false;
for (i=0; i<itemOrdineService.selectedEntity.itemOrdineCodiceList.length; i++)
{
if (itemOrdineService.selectedEntity.itemOrdineCodiceList[i].itemOrdineCodiceId==itemOrdineCodiceService.selectedEntity.itemOrdineCodiceId)
itemOrdineService.selectedEntity.itemOrdineCodiceList.splice(i,1);
}
itemOrdineCodiceService.setSelectedEntity(null);
$scope.updateParent();
};
$scope.del=function()
{
for (i=0; i<itemOrdineService.selectedEntity.itemOrdineCodiceList.length; i++)
{
if (itemOrdineService.selectedEntity.itemOrdineCodiceList[i].itemOrdineCodiceId==itemOrdineCodiceService.selectedEntity.itemOrdineCodiceId)
itemOrdineService.selectedEntity.itemOrdineCodiceList.splice(i,1);
}
$scope.updateParent();
itemOrdineCodiceService.del().then(function successCallback(response) { 
itemOrdineCodiceService.setSelectedEntity(null);
itemOrdineService.initItemOrdineCodiceList().then(function(response) {
itemOrdineService.childrenList.itemOrdineCodiceList=response.data;
});
},function errorCallback(response) { 
return; 
});
};
$scope.trueFalseValues=[true,false];
$scope.showItemOrdineDetail= function(index)
{
if (index!=null)
{
itemOrdineService.searchOne(itemOrdineCodiceService.selectedEntity.itemOrdineList[index]).then(
function successCallback(response) {
console.log("response-ok");
console.log(response);
itemOrdineService.setSelectedEntity(response.data[0]);
itemOrdineService.selectedEntity.show=true;
  }, function errorCallback(response) {
return; 
  }	
);
}
else 
{
if (itemOrdineCodiceService.selectedEntity.itemOrdine==null || itemOrdineCodiceService.selectedEntity.itemOrdine==undefined)
{
itemOrdineService.setSelectedEntity(null); 
itemOrdineService.selectedEntity.show=true; 
}
else
itemOrdineService.searchOne(itemOrdineCodiceService.selectedEntity.itemOrdine).then(
function successCallback(response) {
itemOrdineService.setSelectedEntity(response.data[0]);
itemOrdineService.selectedEntity.show=true;
  }, function errorCallback(response) {
return; 
  }	
);
}
};
$scope.init=function()
{
itemOrdineCodiceService.initItemOrdineList().then(function successCallback(response) {
itemOrdineCodiceService.childrenList.itemOrdineList=response.data;
},function errorCallback(response) { 
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
alasql('SELECT * INTO XLSXML("itemOrdineCodice.xls",?) FROM ?',[mystyle,$scope.entityList]);
};
$scope.downloadItemOrdineList=function()
{
var mystyle = {
 headers:true, 
column: {style:{Font:{Bold:"1"}}}
};
alasql('SELECT * INTO XLSXML("itemOrdine.xls",?) FROM ?',[mystyle,$scope.selectedEntity.itemOrdineList]);
};
})
;