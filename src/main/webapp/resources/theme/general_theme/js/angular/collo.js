var colloApp=angular.module("colloApp",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date'])
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
if (val.toLowerCase().indexOf("date") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(
date.getFullYear(), date
.getMonth(), date
.getDate());
} else {
this.selectedEntity[val] = entity[val];
}
}
	}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../collo/search",this.searchBean)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
};
this.insert = function() {
var promise= $http.put("../collo/",this.selectedEntity)
.then( function(response) 
{
return response.data;
})
.catch(function() 
{ 
alert("error");
});
return promise; 
};
this.update = function() {
var promise= $http.post("../collo/",this.selectedEntity)
.then( function(response) {
return response.data;
})
.catch(function() { 
alert("error");
});
return promise; 
}
this.del = function() {
var url="../collo/selectedEntity.colloId";
var promise= $http["delete"](url)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
}
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
colloService.search().then(function(data) { 
colloService.setEntityList(data);
});
};
$scope.insert=function()
{
if (!$scope.colloDetailForm.$valid) return; 
colloService.insert().then(function(data) { 
$scope.search();
});
};
$scope.update=function()
{
if (!$scope.colloDetailForm.$valid) return; 
ordineService.selectedEntity.show=false;itemOrdineService.selectedEntity.show=false;itemOrdineCodiceService.selectedEntity.show=false;colloService.update().then(function(data) { 
$scope.search();
});
};
$scope.del=function()
{
colloService.del().then(function(data) { 
$scope.search();
});
};$scope.showOrdineDetail= function(index)
{
if (index!=null)
ordineService.setSelectedEntity(colloService.selectedEntity.ordineList[index]);
else 
ordineService.setSelectedEntity(colloService.selectedEntity.ordine); 
ordineService.selectedEntity.show=true;
};
$scope.init=function()
{
$http
.post("../ordine/search",
{})
.success(
function(entityList) {
colloService.childrenList.ordineList=entityList;
}).error(function() {
alert("error");
});
}; 
$scope.init();
$scope.colloGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
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
ordineService.selectedEntity.show=false;itemOrdineService.selectedEntity.show=false;itemOrdineCodiceService.selectedEntity.show=false;colloService
.setSelectedEntity(row.entity);
colloService.selectedEntity.show = true;
});
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
if (val.toLowerCase().indexOf("date") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(
date.getFullYear(), date
.getMonth(), date
.getDate());
} else {
this.selectedEntity[val] = entity[val];
}
}
	}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../ordine/search",this.searchBean)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
};
this.insert = function() {
var promise= $http.put("../ordine/",this.selectedEntity)
.then( function(response) 
{
return response.data;
})
.catch(function() 
{ 
alert("error");
});
return promise; 
};
this.update = function() {
var promise= $http.post("../ordine/",this.selectedEntity)
.then( function(response) {
return response.data;
})
.catch(function() { 
alert("error");
});
return promise; 
}
this.del = function() {
var url="../ordine/selectedEntity.ordineId";
var promise= $http["delete"](url)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
}
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
colloService.update().then(function(data) {
colloService.setSelectedEntity(data);
if (toDo != null)
toDo();
});
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
ordineService.search().then(function(data) { 
ordineService.setEntityList(data);
});
};
$scope.insert=function()
{
if (!$scope.ordineDetailForm.$valid) return; 
ordineService.selectedEntity.show=false;

colloService.selectedEntity.ordine=ordineService.selectedEntity;

$scope.updateParent();

};
$scope.update=function()
{
if (!$scope.ordineDetailForm.$valid) return; 
ordineService.selectedEntity.show=false;

colloService.selectedEntity.ordine=ordineService.selectedEntity;

$scope.updateParent();
};
$scope.del=function()
{
ordineService.selectedEntity.show=false;
colloService.selectedEntity.ordine=null;ordineService.setSelectedEntity(null);
$scope.updateParent();
};$scope.showColloDetail= function(index)
{
if (index!=null)
colloService.setSelectedEntity(ordineService.selectedEntity.colloList[index]);
else 
colloService.setSelectedEntity(ordineService.selectedEntity.collo); 
colloService.selectedEntity.show=true;
};
$scope.showItemOrdineDetail= function(index)
{
if (index!=null)
itemOrdineService.setSelectedEntity(ordineService.selectedEntity.itemOrdineList[index]);
else 
itemOrdineService.setSelectedEntity(ordineService.selectedEntity.itemOrdine); 
itemOrdineService.selectedEntity.show=true;
};
$scope.init=function()
{
$http
.post("../collo/search",
{})
.success(
function(entityList) {
ordineService.childrenList.colloList=entityList;
}).error(function() {
alert("error");
});
$http
.post("../itemOrdine/search",
{})
.success(
function(entityList) {
ordineService.childrenList.itemOrdineList=entityList;
}).error(function() {
alert("error");
});
}; 
$scope.colloListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
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
colloService
.setSelectedEntity(row.entity);
colloService.selectedEntity.show = true;
});
  };
$scope.itemOrdineListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
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
itemOrdineService
.setSelectedEntity(row.entity);
itemOrdineService.selectedEntity.show = true;
});
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
if (val.toLowerCase().indexOf("date") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(
date.getFullYear(), date
.getMonth(), date
.getDate());
} else {
this.selectedEntity[val] = entity[val];
}
}
	}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../itemOrdine/search",this.searchBean)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
};
this.insert = function() {
var promise= $http.put("../itemOrdine/",this.selectedEntity)
.then( function(response) 
{
return response.data;
})
.catch(function() 
{ 
alert("error");
});
return promise; 
};
this.update = function() {
var promise= $http.post("../itemOrdine/",this.selectedEntity)
.then( function(response) {
return response.data;
})
.catch(function() { 
alert("error");
});
return promise; 
}
this.del = function() {
var url="../itemOrdine/selectedEntity.itemOrdineId";
var promise= $http["delete"](url)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
}
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
ordineService.update().then(function(data) {
ordineService.setSelectedEntity(data);
if (toDo != null)
toDo();
});
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
itemOrdineService.search().then(function(data) { 
itemOrdineService.setEntityList(data);
});
};
$scope.insert=function()
{
if (!$scope.itemOrdineDetailForm.$valid) return; 
itemOrdineService.selectedEntity.show=false;

ordineService.selectedEntity.itemOrdineList.push(itemOrdineService.selectedEntity);

$scope.updateParent();

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

$scope.updateParent();
};
$scope.del=function()
{
itemOrdineService.selectedEntity.show=false;
for (i=0; i<ordineService.selectedEntity.itemOrdineList.length; i++)
{
if (ordineService.selectedEntity.itemOrdineList[i].itemOrdineId==itemOrdineService.selectedEntity.itemOrdineId)
ordineService.selectedEntity.itemOrdineList.splice(i,1);
}
itemOrdineService.setSelectedEntity(null);
$scope.updateParent();
};$scope.showOrdineDetail= function(index)
{
if (index!=null)
ordineService.setSelectedEntity(itemOrdineService.selectedEntity.ordineList[index]);
else 
ordineService.setSelectedEntity(itemOrdineService.selectedEntity.ordine); 
ordineService.selectedEntity.show=true;
};
$scope.showItemOrdineCodiceDetail= function(index)
{
if (index!=null)
itemOrdineCodiceService.setSelectedEntity(itemOrdineService.selectedEntity.itemOrdineCodiceList[index]);
else 
itemOrdineCodiceService.setSelectedEntity(itemOrdineService.selectedEntity.itemOrdineCodice); 
itemOrdineCodiceService.selectedEntity.show=true;
};
$scope.init=function()
{
$http
.post("../ordine/search",
{})
.success(
function(entityList) {
itemOrdineService.childrenList.ordineList=entityList;
}).error(function() {
alert("error");
});
$http
.post("../itemOrdineCodice/search",
{})
.success(
function(entityList) {
itemOrdineService.childrenList.itemOrdineCodiceList=entityList;
}).error(function() {
alert("error");
});
}; 
$scope.itemOrdineCodiceListGridOptions = {
enablePaginationControls: true,
multiSelect: false,
enableSelectAll: false,
paginationPageSizes: [2, 4, 6],
paginationPageSize: 2,
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
itemOrdineCodiceService
.setSelectedEntity(row.entity);
itemOrdineCodiceService.selectedEntity.show = true;
});
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
if (val.toLowerCase().indexOf("date") > -1
&& typeof val == "string") {
var date = new Date(entity[val]);
this.selectedEntity[val] = new Date(
date.getFullYear(), date
.getMonth(), date
.getDate());
} else {
this.selectedEntity[val] = entity[val];
}
}
	}
};
};
this.search = function() {
this.setSelectedEntity(null);
var promise= $http.post("../itemOrdineCodice/search",this.searchBean)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
};
this.insert = function() {
var promise= $http.put("../itemOrdineCodice/",this.selectedEntity)
.then( function(response) 
{
return response.data;
})
.catch(function() 
{ 
alert("error");
});
return promise; 
};
this.update = function() {
var promise= $http.post("../itemOrdineCodice/",this.selectedEntity)
.then( function(response) {
return response.data;
})
.catch(function() { 
alert("error");
});
return promise; 
}
this.del = function() {
var url="../itemOrdineCodice/selectedEntity.itemOrdineCodiceId";
var promise= $http["delete"](url)
.then( function(response) {
return response.data;
})
.catch(function() {
alert("error");
});
return promise; 
}
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
itemOrdineService.update().then(function(data) {
itemOrdineService.setSelectedEntity(data);
if (toDo != null)
toDo();
});
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
itemOrdineCodiceService.search().then(function(data) { 
itemOrdineCodiceService.setEntityList(data);
});
};
$scope.insert=function()
{
if (!$scope.itemOrdineCodiceDetailForm.$valid) return; 
itemOrdineCodiceService.selectedEntity.show=false;

itemOrdineService.selectedEntity.itemOrdineCodiceList.push(itemOrdineCodiceService.selectedEntity);

$scope.updateParent();

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

$scope.updateParent();
};
$scope.del=function()
{
itemOrdineCodiceService.selectedEntity.show=false;
for (i=0; i<itemOrdineService.selectedEntity.itemOrdineCodiceList.length; i++)
{
if (itemOrdineService.selectedEntity.itemOrdineCodiceList[i].itemOrdineCodiceId==itemOrdineCodiceService.selectedEntity.itemOrdineCodiceId)
itemOrdineService.selectedEntity.itemOrdineCodiceList.splice(i,1);
}
itemOrdineCodiceService.setSelectedEntity(null);
$scope.updateParent();
};$scope.showItemOrdineDetail= function(index)
{
if (index!=null)
itemOrdineService.setSelectedEntity(itemOrdineCodiceService.selectedEntity.itemOrdineList[index]);
else 
itemOrdineService.setSelectedEntity(itemOrdineCodiceService.selectedEntity.itemOrdine); 
itemOrdineService.selectedEntity.show=true;
};
$scope.init=function()
{
$http
.post("../itemOrdine/search",
{})
.success(
function(entityList) {
itemOrdineCodiceService.childrenList.itemOrdineList=entityList;
}).error(function() {
alert("error");
});
}; 
})
;