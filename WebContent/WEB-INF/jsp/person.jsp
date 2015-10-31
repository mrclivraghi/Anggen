<!DOCTYPE html><html><head><title>person</title><script type="text/javascript" src="../resources/general_theme/js/jquery-1.9.1.js"></script><script type="text/javascript" src="../resources/general_theme/js/jquery-ui.js"></script><script type="text/javascript" src="../resources/general_theme/js/angular.js"></script><script type="text/javascript" src="../resources/general_theme/js/angular-touch.js"></script><script type="text/javascript" src="../resources/general_theme/js/angular-animate.js"></script><script type="text/javascript" src="../resources/general_theme/js/csv.js"></script><script type="text/javascript" src="../resources/general_theme/js/pdfmake.js"></script><script type="text/javascript" src="../resources/general_theme/js/vfs_fonts.js"></script><script type="text/javascript" src="../resources/general_theme/js/ui-grid.js"></script><script type="text/javascript" src="../resources/general_theme/js/angular/person.js"></script><script type="text/javascript" src="../resources/general_theme/js/date.js"></script><script type="text/javascript" src="../resources/general_theme/js/utility.js"></script><script type="text/javascript" src="../resources/general_theme/js/jquery.easytree.js"></script><script type="text/javascript" src="../resources/general_theme/js/bootstrap.min.js"></script><script type="text/javascript" src="../resources/general_theme/js/alasql.min.js"></script><link rel="stylesheet" href="../resources/general_theme/css/ui-grid.css"/><link rel="stylesheet" href="../resources/general_theme/css/bootstrap.min.css"/><link rel="stylesheet" href="../resources/general_theme/css/main.css"/><link rel="stylesheet" href="../resources/general_theme/css/jquery-ui.css"/><link rel="stylesheet" href="../resources/general_theme/css/easytree/skin-win8/ui.easytree.css"/><link rel="import" href="../resources/general_theme/static/menu.html"/></head><body ng-app="personApp"><div ng-controller="personController"><form id="personSearchBean"><div class="panel panel-default default-panel"><div class="panel-heading">Search form person</div><div class="panel-body"><div class="pull-left right-input"><div class="input-group"><span class="input-group-addon">personId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="person-personId" ng-model="searchBean.personId" ng-readonly="false" name="personId" placeholder="personId"/></div></div><div class="pull-left right-input"><div class="input-group"><span class="input-group-addon">firstName</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="person-firstName" ng-model="searchBean.firstName" ng-readonly="false" name="firstName" placeholder="firstName"/></div></div><div class="pull-left right-input"><div class="input-group"><span class="input-group-addon">lastName</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="person-lastName" ng-model="searchBean.lastName" ng-readonly="false" name="lastName" placeholder="lastName"/></div></div><div class="pull-left right-input"><div class="input-group"><span class="input-group-addon">birthDate</span><input class="form-control " aria-describedby="sizing-addon3" type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }" ng-model="searchBean.birthDate" ng-readonly="false" name="birthDate" placeholder="birthDate"/></div></div></div><div class="panel-body"><div class="panel-body"><div class="pull-left right-input"><button ng-click="addNew()" class="btn btn-default ">Add new</button><button ng-click="search()" class="btn btn-default ">Find</button><button ng-click="reset()" class="btn btn-default ">Reset</button></div></div></div></div></form><form id="personList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List person<button ng-click="downloadEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body"><div ui-grid="personGridOptions" ui-grid-pagination="" ui-grid-selection="" ui-grid-exporter=""></div></div></div></form><form id="personDetailForm" name="personDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail person {{ selectedEntity.personId }}</div><div class="panel-body"><ul class="nav nav-tabs" role="tablist" id="personTabs"><li role="presentation"><a href="#person-Detail" aria-controls="Detail" role="tab" data-toggle="tab" ng-click="">Detail</a></li></ul><div class="tab-content"><div role="tabpanel" class="tab-pane fade" id="person-Detail"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !personDetailForm.personId.$valid, &#39;has-success&#39;: personDetailForm.personId.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">personId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="person-personId" ng-model="selectedEntity.personId" ng-readonly="true" name="personId" placeholder="personId"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !personDetailForm.firstName.$valid, &#39;has-success&#39;: personDetailForm.firstName.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">firstName</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="person-firstName" ng-model="selectedEntity.firstName" ng-readonly="false" name="firstName" placeholder="firstName"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !personDetailForm.lastName.$valid, &#39;has-success&#39;: personDetailForm.lastName.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">lastName</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="person-lastName" ng-model="selectedEntity.lastName" ng-readonly="false" name="lastName" placeholder="lastName"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !personDetailForm.birthDate.$valid, &#39;has-success&#39;: personDetailForm.birthDate.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">birthDate</span><input class="form-control " aria-describedby="sizing-addon3" type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }" ng-model="selectedEntity.birthDate" ng-readonly="false" name="birthDate" placeholder="birthDate"/></div></div></div></div></div><script type="text/javascript">$('#personTabs a:first').tab('show');</script><div class="panel-body"><div class="pull-left"><form id="personActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default " ng-if="selectedEntity.personId==undefined">Insert</button><button ng-click="update()" class="btn btn-default " ng-if="selectedEntity.personId&gt;0">Update</button><button ng-click="del()" class="btn btn-default " ng-if="selectedEntity.personId&gt;0">Delete</button></form></div></div></div></form></div><script type="text/javascript">loadMenu();  activeMenu("person");</script></body></html>