<!DOCTYPE html><html><head><title>test order</title><script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.js"></script><script type="text/javascript" src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script><script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular.js"></script><script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-touch.js"></script><script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-animate.js"></script><script type="text/javascript" src="http://ui-grid.info/docs/grunt-scripts/csv.js"></script><script type="text/javascript" src="http://ui-grid.info/docs/grunt-scripts/pdfmake.js"></script><script type="text/javascript" src="http://ui-grid.info/docs/grunt-scripts/vfs_fonts.js"></script><script type="text/javascript" src="http://ui-grid.info/release/ui-grid.js"></script><script type="text/javascript" src="../resources/general_theme/js/angular/order.js"></script><script type="text/javascript" src="../resources/general_theme/js/date.js"></script><link rel="stylesheet" href="http://ui-grid.info/release/ui-grid.css"/><link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/><link rel="stylesheet" href="../resources/general_theme/css/main.css"/><link rel="stylesheet" href="../resources/general_theme/css/jquery-ui.css"/></head><body ng-app="orderApp"><div ng-controller="orderController"><form id="orderSearchBean"><div class="panel panel-default default-panel"><div class="panel-heading">Search form order</div><div class="panel-body"><div class="pull-left right-input"><label id="orderId">orderId</label><input class="form-control " aria-describedby="sizing-addon3" type="text" ng-model="searchBean.orderId" ng-readonly="false" name="orderId" placeholder="orderId" id="order-orderId"/></div><div class="pull-right right-input"><label id="name">name</label><input class="form-control " aria-describedby="sizing-addon3" type="text" ng-model="searchBean.name" ng-readonly="false" name="name" placeholder="name" id="order-name"/></div><div class="pull-left right-input"><label id="timeslotDate">timeslotDate</label><input class="form-control " aria-describedby="sizing-addon3" type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }" ui-date-format="dd/mm/yy" ng-model="searchBean.timeslotDate" ng-readonly="false" name="timeslotDate" placeholder="timeslotDate" id="order-timeslotDate"/></div><div class="pull-right right-input"><label id="person">person</label><select class="form-control " aria-describedby="sizing-addon3" ng-model="searchBean.person.personId" id="person" ng-options="person.personId as  person.firstName+&#39; &#39;+ person.lastName for person in childrenList.personList" enctype="UTF-8"></select></div><div class="pull-left right-input"><label id="place">place</label><select class="form-control " aria-describedby="sizing-addon3" ng-model="searchBean.place.placeId" id="place" ng-options="place.placeId as  place.description for place in childrenList.placeList" enctype="UTF-8"></select></div></div><div class="panel-body"><div class="pull-left right-input"><button ng-click="addNew()" class="btn btn-default">Add new</button><button ng-click="search()" class="btn btn-default">Find</button><button ng-click="reset()" class="btn btn-default">Reset</button></div></div></div></form><form id="orderList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List order</div><div class="panel-body"><div ui-grid="orderGridOptions" ui-grid-pagination="" ui-grid-selection=""></div></div></div></form><form id="orderDetailForm" name="orderDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail order {{ selectedEntity.orderId }}</div><div class="panel-body"><div class="pull-left right-input"><label for="orderId">orderId</label><input class="form-control " aria-describedby="sizing-addon3" type="text" ng-model="selectedEntity.orderId" ng-readonly="false" name="orderId" placeholder="orderId" id="order-orderId"/></div><div class="pull-right right-input"><label for="name">name</label><input class="form-control " aria-describedby="sizing-addon3" type="text" ng-model="selectedEntity.name" ng-readonly="false" name="name" placeholder="name" id="order-name"/></div><div class="pull-left right-input"><label for="timeslotDate">timeslotDate</label><input class="form-control " aria-describedby="sizing-addon3" type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }" ui-date-format="dd/mm/yy" ng-model="selectedEntity.timeslotDate" ng-readonly="false" name="timeslotDate" placeholder="timeslotDate" id="order-timeslotDate"/></div><div class="pull-right right-input"><label id="person">person</label><button ng-click="showPersonDetail()()" class="btn btn-default" ng-if="selectedEntity.person==null">Add new person</button><label for="person">person</label><p ng-click="showPersonDetail()" ng-if="selectedEntity.person!=null">person: {{selectedEntity.person.personId}}</p></div></div><div class="panel-body"><label id="place">place</label><button ng-click="showPlaceDetail()" class="btn btn-default">Add new place</button><div id="place" ng-if="selectedEntity.placeList.length&gt;0"><div style="top: 100px" ui-grid="placeListGridOptions" ui-grid-pagination="" ui-grid-selection=""></div></div></div><div class="panel-body"></div><div class="panel-body"><div class="pull-left"><form id="orderActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default" ng-if="selectedEntity.orderId==undefined">Insert</button><button ng-click="update()" class="btn btn-default" ng-if="selectedEntity.orderId&gt;0">Update</button><button ng-click="del()" class="btn btn-default" ng-if="selectedEntity.orderId&gt;0">Delete</button></form></div></div></div></form></div><div ng-controller="personController"><form id="personList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List person</div><div class="panel-body"><div ui-grid="personGridOptions" ui-grid-pagination="" ui-grid-selection=""></div></div></div></form><form id="personDetailForm" name="personDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail person {{ selectedEntity.personId }}</div><div class="panel-body"><div class="pull-left right-input"><label for="personId">personId</label><input class="form-control " aria-describedby="sizing-addon3" type="text" ng-model="selectedEntity.personId" ng-readonly="false" name="personId" placeholder="personId" id="person-personId"/></div><div class="pull-right right-input"><label for="firstName">firstName</label><input class="form-control " aria-describedby="sizing-addon3" type="text" ng-model="selectedEntity.firstName" ng-readonly="false" name="firstName" placeholder="firstName" id="person-firstName"/></div><div class="pull-left right-input"><label for="lastName">lastName</label><input class="form-control " aria-describedby="sizing-addon3" type="text" ng-model="selectedEntity.lastName" ng-readonly="false" name="lastName" placeholder="lastName" id="person-lastName"/></div><div class="pull-right right-input"><label for="birthDate">birthDate</label><input class="form-control " aria-describedby="sizing-addon3" type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }" ui-date-format="dd/mm/yy" ng-model="selectedEntity.birthDate" ng-readonly="false" name="birthDate" placeholder="birthDate" id="person-birthDate"/></div></div><div class="panel-body"><div class="pull-left"><form id="personActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default" ng-if="selectedEntity.personId==undefined">Insert</button><button ng-click="update()" class="btn btn-default" ng-if="selectedEntity.personId&gt;0">Update</button><button ng-click="del()" class="btn btn-default" ng-if="selectedEntity.personId&gt;0">Delete</button></form></div></div></div></form></div><div ng-controller="placeController"><form id="placeList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List place</div><div class="panel-body"><div ui-grid="placeGridOptions" ui-grid-pagination="" ui-grid-selection=""></div></div></div></form><form id="placeDetailForm" name="placeDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail place {{ selectedEntity.placeId }}</div><div class="panel-body"><div class="pull-left right-input"><label for="placeId">placeId</label><input class="form-control " aria-describedby="sizing-addon3" type="text" ng-model="selectedEntity.placeId" ng-readonly="false" name="placeId" placeholder="placeId" id="place-placeId"/></div><div class="pull-right right-input"><label for="description">description</label><input class="form-control " aria-describedby="sizing-addon3" type="text" ng-model="selectedEntity.description" ng-readonly="false" name="description" placeholder="description" id="place-description"/></div></div><div class="panel-body"><div class="pull-left"><form id="placeActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default" ng-if="selectedEntity.placeId==undefined">Insert</button><button ng-click="update()" class="btn btn-default" ng-if="selectedEntity.placeId&gt;0">Update</button><button ng-click="del()" class="btn btn-default" ng-if="selectedEntity.placeId&gt;0">Delete</button></form></div></div></div></form></div></body></html>