<!DOCTYPE html><html><head><title>test order</title><script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.js"></script><script type="text/javascript" src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script><script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular.js"></script><script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-touch.js"></script><script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-animate.js"></script><script type="text/javascript" src="http://ui-grid.info/docs/grunt-scripts/csv.js"></script><script type="text/javascript" src="http://ui-grid.info/docs/grunt-scripts/pdfmake.js"></script><script type="text/javascript" src="http://ui-grid.info/docs/grunt-scripts/vfs_fonts.js"></script><script type="text/javascript" src="http://ui-grid.info/release/ui-grid.js"></script><script type="text/javascript" src="../resources/general_theme/js/angular/person.js"></script><script type="text/javascript" src="../resources/general_theme/js/date.js"></script><script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script><link rel="stylesheet" href="http://ui-grid.info/release/ui-grid.css"/><link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/><link rel="stylesheet" href="../resources/general_theme/css/main.css"/><link rel="stylesheet" href="../resources/general_theme/css/jquery-ui.css"/></head><body ng-app="personApp"><div ng-controller="personController"><form id="personSearchBean"><div class="panel panel-default default-panel"><div class="panel-heading">Search form person</div><div class="panel-body"><div class="pull-left right-input" style="height: 59px;"><label id="personId">personId</label><input class="form-control " aria-describedby="sizing-addon3" type="text" id="person-personId" ng-model="searchBean.personId" ng-readonly="false" name="personId" placeholder="personId"/></div><div class="pull-right right-input" style="height: 59px;"><label id="firstName">firstName</label><input class="form-control " aria-describedby="sizing-addon3" type="text" id="person-firstName" ng-model="searchBean.firstName" ng-readonly="false" name="firstName" placeholder="firstName"/></div><div class="pull-left right-input" style="height: 59px;"><label id="lastName">lastName</label><input class="form-control " aria-describedby="sizing-addon3" type="text" id="person-lastName" ng-model="searchBean.lastName" ng-readonly="false" name="lastName" placeholder="lastName"/></div><div class="pull-right right-input" style="height: 59px;"><label id="birthDate">birthDate</label><input class="form-control " aria-describedby="sizing-addon3" type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }" ng-model="searchBean.birthDate" ng-readonly="false" name="birthDate" placeholder="birthDate"/></div></div><div class="panel-body"><div class="pull-left right-input"><button ng-click="addNew()" class="btn btn-default">Add new</button><button ng-click="search()" class="btn btn-default">Find</button><button ng-click="reset()" class="btn btn-default">Reset</button></div></div></div></form><form id="personList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List person</div><div class="panel-body"><div ui-grid="personGridOptions" ui-grid-pagination="" ui-grid-selection=""></div></div></div></form><form id="personDetailForm" name="personDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail person {{ selectedEntity.personId }}</div><div class="panel-body"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !personDetailForm.personId.$valid, &#39;has-success&#39;: personDetailForm.personId.$valid}" style="height: 59px;"><label for="personId">personId</label><input class="form-control " aria-describedby="sizing-addon3" type="text" id="person-personId" ng-model="selectedEntity.personId" ng-readonly="true" name="personId" placeholder="personId"/></div><div class="pull-right right-input" ng-class="{&#39;has-error&#39;: !personDetailForm.firstName.$valid, &#39;has-success&#39;: personDetailForm.firstName.$valid}" style="height: 59px;"><label for="firstName">firstName</label><input class="form-control " aria-describedby="sizing-addon3" type="text" id="person-firstName" ng-model="selectedEntity.firstName" ng-readonly="false" name="firstName" placeholder="firstName"/></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !personDetailForm.lastName.$valid, &#39;has-success&#39;: personDetailForm.lastName.$valid}" style="height: 59px;"><label for="lastName">lastName</label><input class="form-control " aria-describedby="sizing-addon3" type="text" id="person-lastName" ng-model="selectedEntity.lastName" ng-readonly="false" name="lastName" placeholder="lastName"/></div><div class="pull-right right-input" ng-class="{&#39;has-error&#39;: !personDetailForm.birthDate.$valid, &#39;has-success&#39;: personDetailForm.birthDate.$valid}" style="height: 59px;"><label for="birthDate">birthDate</label><input class="form-control " aria-describedby="sizing-addon3" type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }" ng-model="selectedEntity.birthDate" ng-readonly="false" name="birthDate" placeholder="birthDate"/></div></div><div class="panel-body"><div class="pull-left"><form id="personActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default" ng-if="selectedEntity.personId==undefined">Insert</button><button ng-click="update()" class="btn btn-default" ng-if="selectedEntity.personId&gt;0">Update</button><button ng-click="del()" class="btn btn-default" ng-if="selectedEntity.personId&gt;0">Delete</button></form></div></div></div></form></div></body></html>