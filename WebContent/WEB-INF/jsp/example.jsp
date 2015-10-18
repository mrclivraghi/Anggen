<!DOCTYPE html><html><head><title>example</title><script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.js"></script><script type="text/javascript" src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script><script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular.js"></script><script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-touch.js"></script><script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-animate.js"></script><script type="text/javascript" src="http://ui-grid.info/docs/grunt-scripts/csv.js"></script><script type="text/javascript" src="http://ui-grid.info/docs/grunt-scripts/pdfmake.js"></script><script type="text/javascript" src="http://ui-grid.info/docs/grunt-scripts/vfs_fonts.js"></script><script type="text/javascript" src="http://ui-grid.info/release/ui-grid.js"></script><script type="text/javascript" src="../resources/general_theme/js/angular/example.js"></script><script type="text/javascript" src="../resources/general_theme/js/date.js"></script><script type="text/javascript" src="../resources/general_theme/js/utility.js"></script><script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script><script type="text/javascript" src="http://cdn.jsdelivr.net/alasql/0.2/alasql.min.js"></script><link rel="stylesheet" href="http://ui-grid.info/release/ui-grid.css"/><link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/><link rel="stylesheet" href="../resources/general_theme/css/main.css"/><link rel="stylesheet" href="../resources/general_theme/css/jquery-ui.css"/><link rel="import" href="../resources/general_theme/static/menu.html"/></head><body ng-app="exampleApp"><div ng-controller="exampleController"><form id="exampleSearchBean"><div class="panel panel-default default-panel"><div class="panel-heading">Search form example</div><div class="panel-body"><div class="pull-left right-input" style="height: 59px;"><label id="exampleId">exampleId</label><input aria-describedby="sizing-addon3" class="form-control " type="text" id="example-exampleId" ng-model="searchBean.exampleId" ng-readonly="false" name="exampleId" placeholder="exampleId"/></div><div class="pull-right right-input" style="height: 59px;"><label id="eta">eta</label><input aria-describedby="sizing-addon3" class="form-control " type="text" id="example-eta" ng-model="searchBean.eta" ng-readonly="false" name="eta" placeholder="eta"/></div><div class="pull-left right-input" style="height: 59px;"><label id="male">male</label><div class="input-group"><select class="form-control" ng-model="searchBean.male" name="male" ng-options="value for value in trueFalseValues"></select><span class="input-group-btn"><button class="btn btn-default">male</button></span></div></div><div class="pull-right right-input" style="height: 59px;"><label id="birthDate">birthDate</label><input aria-describedby="sizing-addon3" class="form-control " type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }" ng-model="searchBean.birthDate" ng-readonly="false" name="birthDate" placeholder="birthDate"/></div><div class="pull-left right-input"><label id="sex">sex</label><select class="form-control " aria-describedby="sizing-addon3" ng-model="searchBean.sex" id="sex" ng-options="sex as sex for sex in childrenList.sexList" enctype="UTF-8"></select></div></div><div class="panel-body"><div class="pull-left right-input"><button ng-click="addNew()" class="btn btn-default ">Add new</button><button ng-click="search()" class="btn btn-default ">Find</button><button ng-click="reset()" class="btn btn-default ">Reset</button></div></div></div></form><form id="exampleList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List example<button ng-click="downloadEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body"><div ui-grid="exampleGridOptions" ui-grid-pagination="" ui-grid-selection="" ui-grid-exporter=""></div></div></div></form><form id="exampleDetailForm" name="exampleDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail example {{ selectedEntity.exampleId }}</div><div class="panel-body"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !exampleDetailForm.name.$valid, &#39;has-success&#39;: exampleDetailForm.name.$valid}" style="height: 59px;"><label for="name">name</label><input aria-describedby="sizing-addon3" class="form-control " type="text" id="example-name" ng-model="example.name" ng-readonly="false" name="name" placeholder="name"/><small class="help-block" ng-show="exampleDetailForm.name.$error.required">example: name required</small><small class="help-block" ng-show="exampleDetailForm.name.$error.minlength">example: name min 3 caratteri</small><small class="help-block" ng-show="exampleDetailForm.name.$error.maxlength">example: name max 20 caratteri</small></div><div class="pull-right right-input" ng-class="{&#39;has-error&#39;: !exampleDetailForm.eta.$valid, &#39;has-success&#39;: exampleDetailForm.eta.$valid}" style="height: 59px;"><label for="eta">eta</label><input aria-describedby="sizing-addon3" class="form-control " type="text" id="example-eta" ng-model="example.eta" ng-readonly="false" name="eta" placeholder="eta"/></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !exampleDetailForm.male.$valid, &#39;has-success&#39;: exampleDetailForm.male.$valid}" style="height: 59px;"><label for="male">male</label><div class="input-group"><select class="form-control" ng-model="example.male" name="male" ng-options="value for value in trueFalseValues"></select><span class="input-group-btn"><button class="btn btn-default">male</button></span></div></div><div class="pull-right right-input" ng-class="{&#39;has-error&#39;: !exampleDetailForm.birthDate.$valid, &#39;has-success&#39;: exampleDetailForm.birthDate.$valid}" style="height: 59px;"><label for="birthDate">birthDate</label><input aria-describedby="sizing-addon3" class="form-control " type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }" ng-model="example.birthDate" ng-readonly="false" name="birthDate" placeholder="birthDate"/></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !exampleDetailForm.birthTime.$valid, &#39;has-success&#39;: exampleDetailForm.birthTime.$valid}" style="height: 59px;"><label for="birthTime">birthTime</label><input aria-describedby="sizing-addon3" class="form-control " type="time" placeholder="HH:mm" ng-model="example.birthTime" ng-readonly="false" name="birthTime" placeholder="birthTime"/></div><div class="pull-right right-input" ng-class="{&#39;has-error&#39;: !exampleDetailForm.sex.$valid, &#39;has-success&#39;: exampleDetailForm.sex.$valid}"><label id="sex">sex</label><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.sex" id="sex" name="sex" ng-options="sex as sex for sex in childrenList.sexList" enctype="UTF-8"></select></div></div><div class="panel-body"><div class="pull-left"><form id="exampleActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default " ng-if="selectedEntity.exampleId==undefined">Insert</button><button ng-click="update()" class="btn btn-default " ng-if="selectedEntity.exampleId&gt;0">Update</button><button ng-click="del()" class="btn btn-default " ng-if="selectedEntity.exampleId&gt;0">Delete</button></form></div></div></div></form></div><script type="text/javascript">loadMenu();	activeMenu("example");</script></body></html>