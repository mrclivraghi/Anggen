<!DOCTYPE html><html><head><title>paziente</title><script type="text/javascript" src="../resources/general_theme/js/jquery-1.9.1.js"></script><script type="text/javascript" src="../resources/general_theme/js/jquery-ui.js"></script><script type="text/javascript" src="../resources/general_theme/js/angular.js"></script><script type="text/javascript" src="../resources/general_theme/js/angular-touch.js"></script><script type="text/javascript" src="../resources/general_theme/js/angular-animate.js"></script><script type="text/javascript" src="../resources/general_theme/js/csv.js"></script><script type="text/javascript" src="../resources/general_theme/js/pdfmake.js"></script><script type="text/javascript" src="../resources/general_theme/js/vfs_fonts.js"></script><script type="text/javascript" src="../resources/general_theme/js/ui-grid.js"></script><script type="text/javascript" src="../resources/general_theme/js/angular/paziente.js"></script><script type="text/javascript" src="../resources/general_theme/js/date.js"></script><script type="text/javascript" src="../resources/general_theme/js/utility.js"></script><script type="text/javascript" src="../resources/general_theme/js/jquery.easytree.js"></script><script type="text/javascript" src="../resources/general_theme/js/bootstrap.min.js"></script><script type="text/javascript" src="../resources/general_theme/js/alasql.min.js"></script><link rel="stylesheet" href="../resources/general_theme/css/ui-grid.css"/><link rel="stylesheet" href="../resources/general_theme/css/bootstrap.min.css"/><link rel="stylesheet" href="../resources/general_theme/css/main.css"/><link rel="stylesheet" href="../resources/general_theme/css/jquery-ui.css"/><link rel="stylesheet" href="../resources/general_theme/css/easytree/skin-win8/ui.easytree.css"/><link rel="import" href="../resources/general_theme/static/menu.html"/></head><body ng-app="pazienteApp"><div ng-controller="pazienteController"><form id="pazienteSearchBean"><div class="panel panel-default default-panel"><div class="panel-heading">Search form paziente</div><div class="panel-body"><div class="pull-left right-input"><div class="input-group"><span class="input-group-addon">pazienteId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="paziente-pazienteId" ng-model="searchBean.pazienteId" ng-readonly="false" name="pazienteId" placeholder="pazienteId"/></div></div><div class="pull-right right-input"><div class="input-group"><span class="input-group-addon">nome</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="paziente-nome" ng-model="searchBean.nome" ng-readonly="false" name="nome" placeholder="nome"/></div></div><div class="pull-left right-input"><div class="input-group"><span class="input-group-addon">cognome</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="paziente-cognome" ng-model="searchBean.cognome" ng-readonly="false" name="cognome" placeholder="cognome"/></div></div><div class="pull-right right-input" style="height: 59px;"><div class="input-group"><span class="input-group-addon">fascicolo</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="searchBean.fascicolo.fascicoloId" id="fascicolo" ng-options="fascicolo.fascicoloId as  fascicolo.fascicoloId for fascicolo in childrenList.fascicoloList" enctype="UTF-8"></select></div></div><div class="pull-left right-input" style="height: 59px;"><div class="input-group"><span class="input-group-addon">ambulatorio</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="searchBean.ambulatorio.ambulatorioId" id="ambulatorio" ng-options="ambulatorio.ambulatorioId as  ambulatorio.ambulatorioId for ambulatorio in childrenList.ambulatorioList" enctype="UTF-8"></select></div></div></div><div class="panel-body"><div class="panel-body"><div class="pull-left right-input"><button ng-click="addNew()" class="btn btn-default ">Add new</button><button ng-click="search()" class="btn btn-default ">Find</button><button ng-click="reset()" class="btn btn-default ">Reset</button></div></div></div></div></form><form id="pazienteList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List paziente<button ng-click="downloadEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body"><div ui-grid="pazienteGridOptions" ui-grid-pagination="" ui-grid-selection="" ui-grid-exporter=""></div></div></div></form><form id="pazienteDetailForm" name="pazienteDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail paziente {{ selectedEntity.pazienteId }}</div><div class="panel-body"><ul class="nav nav-tabs" role="tablist" id="pazienteTabs"><li role="presentation"><a href="#paziente-Detail" aria-controls="Detail" role="tab" data-toggle="tab" ng-click=" $scope.fascicoloGridApi.core.handleWindowResize();  $scope.ambulatorioGridApi.core.handleWindowResize(); ">Detail</a></li></ul><div class="tab-content"><div role="tabpanel" class="tab-pane fade" id="paziente-Detail"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !pazienteDetailForm.pazienteId.$valid, &#39;has-success&#39;: pazienteDetailForm.pazienteId.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">pazienteId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="paziente-pazienteId" ng-model="selectedEntity.pazienteId" ng-readonly="true" name="pazienteId" placeholder="pazienteId"/></div></div><div class="pull-right right-input" ng-class="{&#39;has-error&#39;: !pazienteDetailForm.nome.$valid, &#39;has-success&#39;: pazienteDetailForm.nome.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">nome</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="paziente-nome" ng-model="selectedEntity.nome" ng-readonly="false" name="nome" placeholder="nome"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !pazienteDetailForm.cognome.$valid, &#39;has-success&#39;: pazienteDetailForm.cognome.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">cognome</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="paziente-cognome" ng-model="selectedEntity.cognome" ng-readonly="false" name="cognome" placeholder="cognome"/></div></div><div class="pull-right right-input" ng-class="{&#39;has-error&#39;: !pazienteDetailForm.birthDate.$valid, &#39;has-success&#39;: pazienteDetailForm.birthDate.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">birthDate</span><input class="form-control " aria-describedby="sizing-addon3" type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }" ng-model="selectedEntity.birthDate" ng-readonly="false" name="birthDate" placeholder="birthDate"/></div></div><div id="paziente-fascicolo" class="modal fade" role="dialog"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button><h4 class="modal-title">Modal header</h4></div><div class="modal-body"><p>some text</p><div class="input-group"><span class="input-group-addon">fascicolo</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.fascicolo" id="fascicolo" name="fascicolo" ng-options="fascicolo as  fascicolo.fascicoloId for fascicolo in childrenList.fascicoloList track by fascicolo.fascicoloId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="saveLinkedFascicolo()" class="btn btn-default " id="fascicolo">Save</button></span></div></div><div class="modal-footer"><button ng-click="close()" class="btn btn-default " data-dismiss="modal">close</button></div></div></div></div><br/><br/><div class="pull-left" style="width: 100%"><div class="panel panel-default default-panel"><div class="panel-heading">fascicolo<button ng-click="showFascicoloDetail()" class="btn btn-default  pull-right" style="margin-top: -7px">Add new fascicolo</button><button type="button" class="btn btn-default pull-right" style="margin-top: -7px" data-toggle="modal" data-target="#paziente-fascicolo">Link existing</button><button ng-click="downloadFascicoloList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body" ng-class="{&#39;has-error&#39;: !pazienteDetailForm.fascicolo.$valid, &#39;has-success&#39;: pazienteDetailForm.fascicolo.$valid}"><label id="fascicolo">fascicolo</label><div id="fascicolo" ng-if="selectedEntity.fascicoloList.length&gt;0"><div style="top: 100px" ui-grid="fascicoloListGridOptions" ui-grid-pagination="" ui-grid-selection=""></div></div></div></div></div><div id="paziente-ambulatorio" class="modal fade" role="dialog"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button><h4 class="modal-title">Modal header</h4></div><div class="modal-body"><p>some text</p><div class="input-group"><span class="input-group-addon">ambulatorio</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.ambulatorio" id="ambulatorio" name="ambulatorio" ng-options="ambulatorio as  ambulatorio.ambulatorioId for ambulatorio in childrenList.ambulatorioList track by ambulatorio.ambulatorioId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="saveLinkedAmbulatorio()" class="btn btn-default " id="ambulatorio">Save</button></span></div></div><div class="modal-footer"><button ng-click="close()" class="btn btn-default " data-dismiss="modal">close</button></div></div></div></div><br/><br/><div class="pull-left" style="width: 100%"><div class="panel panel-default default-panel"><div class="panel-heading">ambulatorio<button ng-click="showAmbulatorioDetail()" class="btn btn-default  pull-right" style="margin-top: -7px">Add new ambulatorio</button><button type="button" class="btn btn-default pull-right" style="margin-top: -7px" data-toggle="modal" data-target="#paziente-ambulatorio">Link existing</button><button ng-click="downloadAmbulatorioList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body" ng-class="{&#39;has-error&#39;: !pazienteDetailForm.ambulatorio.$valid, &#39;has-success&#39;: pazienteDetailForm.ambulatorio.$valid}"><label id="ambulatorio">ambulatorio</label><div id="ambulatorio" ng-if="selectedEntity.ambulatorioList.length&gt;0"><div style="top: 100px" ui-grid="ambulatorioListGridOptions" ui-grid-pagination="" ui-grid-selection=""></div></div></div></div></div></div></div></div><script type="text/javascript">$('#pazienteTabs a:first').tab('show');</script><div class="panel-body"><div class="pull-left"><form id="pazienteActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default " ng-if="selectedEntity.pazienteId==undefined">Insert</button><button ng-click="update()" class="btn btn-default " ng-if="selectedEntity.pazienteId&gt;0">Update</button><button ng-click="del()" class="btn btn-default " ng-if="selectedEntity.pazienteId&gt;0">Delete</button></form></div></div></div></form></div><div ng-controller="fascicoloController"><form id="fascicoloList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List fascicolo<button ng-click="downloadEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body"><div ui-grid="fascicoloGridOptions" ui-grid-pagination="" ui-grid-selection="" ui-grid-exporter=""></div></div></div></form><form id="fascicoloDetailForm" name="fascicoloDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail fascicolo {{ selectedEntity.fascicoloId }}</div><div class="panel-body"><ul class="nav nav-tabs" role="tablist" id="fascicoloTabs"><li role="presentation"><a href="#fascicolo-Detail" aria-controls="Detail" role="tab" data-toggle="tab" ng-click="">Detail</a></li></ul><div class="tab-content"><div role="tabpanel" class="tab-pane fade" id="fascicolo-Detail"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !fascicoloDetailForm.fascicoloId.$valid, &#39;has-success&#39;: fascicoloDetailForm.fascicoloId.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">fascicoloId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="fascicolo-fascicoloId" ng-model="selectedEntity.fascicoloId" ng-readonly="true" name="fascicoloId" placeholder="fascicoloId"/></div></div><div class="pull-right right-input" ng-class="{&#39;has-error&#39;: !fascicoloDetailForm.creationDate.$valid, &#39;has-success&#39;: fascicoloDetailForm.creationDate.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">creationDate</span><input class="form-control " aria-describedby="sizing-addon3" type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }" ng-model="selectedEntity.creationDate" ng-readonly="false" name="creationDate" placeholder="creationDate"/></div></div><div class="pull-right right-input" ng-class="{&#39;has-error&#39;: !fascicoloDetailForm.ambulatorio.$valid, &#39;has-success&#39;: fascicoloDetailForm.ambulatorio.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">ambulatorio</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.ambulatorio" id="ambulatorio" name="ambulatorio" ng-options="ambulatorio as  ambulatorio.ambulatorioId for ambulatorio in childrenList.ambulatorioList track by ambulatorio.ambulatorioId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="showAmbulatorioDetail()" class="btn btn-default " id="ambulatorio" ng-if="selectedEntity.ambulatorio==null">Add new ambulatorio</button><button ng-click="showAmbulatorioDetail()" class="btn btn-default " id="ambulatorio" ng-if="selectedEntity.ambulatorio!=null">Show detail</button></span></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !fascicoloDetailForm.tipoIntervento.$valid, &#39;has-success&#39;: fascicoloDetailForm.tipoIntervento.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">tipoIntervento</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="fascicolo-tipoIntervento" ng-model="selectedEntity.tipoIntervento" ng-readonly="false" name="tipoIntervento" placeholder="tipoIntervento"/></div></div></div></div></div><script type="text/javascript">$('#fascicoloTabs a:first').tab('show');</script><div class="panel-body"><div class="pull-left"><form id="fascicoloActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default " ng-if="selectedEntity.fascicoloId==undefined">Insert</button><button ng-click="update()" class="btn btn-default " ng-if="selectedEntity.fascicoloId&gt;0">Update</button><button ng-click="del()" class="btn btn-default " ng-if="selectedEntity.fascicoloId&gt;0">Delete</button><button ng-click="remove()" class="btn btn-default " ng-if="selectedEntity.fascicoloId&gt;0">Remove</button></form></div></div></div></form></div><div ng-controller="ambulatorioController"><form id="ambulatorioList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List ambulatorio<button ng-click="downloadEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body"><div ui-grid="ambulatorioGridOptions" ui-grid-pagination="" ui-grid-selection="" ui-grid-exporter=""></div></div></div></form><form id="ambulatorioDetailForm" name="ambulatorioDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail ambulatorio {{ selectedEntity.ambulatorioId }}</div><div class="panel-body"><ul class="nav nav-tabs" role="tablist" id="ambulatorioTabs"><li role="presentation"><a href="#ambulatorio-Detail" aria-controls="Detail" role="tab" data-toggle="tab" ng-click=" $scope.pazienteGridApi.core.handleWindowResize(); ">Detail</a></li></ul><div class="tab-content"><div role="tabpanel" class="tab-pane fade" id="ambulatorio-Detail"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !ambulatorioDetailForm.ambulatorioId.$valid, &#39;has-success&#39;: ambulatorioDetailForm.ambulatorioId.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">ambulatorioId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="ambulatorio-ambulatorioId" ng-model="selectedEntity.ambulatorioId" ng-readonly="true" name="ambulatorioId" placeholder="ambulatorioId"/></div></div><div class="pull-right right-input" ng-class="{&#39;has-error&#39;: !ambulatorioDetailForm.nome.$valid, &#39;has-success&#39;: ambulatorioDetailForm.nome.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">nome</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="ambulatorio-nome" ng-model="selectedEntity.nome" ng-readonly="false" name="nome" placeholder="nome"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !ambulatorioDetailForm.indirizzo.$valid, &#39;has-success&#39;: ambulatorioDetailForm.indirizzo.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">indirizzo</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="ambulatorio-indirizzo" ng-model="selectedEntity.indirizzo" ng-readonly="false" name="indirizzo" placeholder="indirizzo"/></div></div></div></div></div><script type="text/javascript">$('#ambulatorioTabs a:first').tab('show');</script><div class="panel-body"><div class="pull-left"><form id="ambulatorioActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default " ng-if="selectedEntity.ambulatorioId==undefined">Insert</button><button ng-click="update()" class="btn btn-default " ng-if="selectedEntity.ambulatorioId&gt;0">Update</button><button ng-click="del()" class="btn btn-default " ng-if="selectedEntity.ambulatorioId&gt;0">Delete</button><button ng-click="remove()" class="btn btn-default " ng-if="selectedEntity.ambulatorioId&gt;0">Remove</button></form></div></div></div></form></div><script type="text/javascript">loadMenu();  activeMenu("paziente");</script></body></html>