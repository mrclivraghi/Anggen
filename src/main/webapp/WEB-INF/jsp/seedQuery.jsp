<!DOCTYPE html><html><head><title>seedQuery</title><script type="text/javascript" src="../js/jquery-1.9.1.js"></script><script type="text/javascript" src="../js/jquery-ui.js"></script><script type="text/javascript" src="../js/angular.js"></script><script type="text/javascript" src="../js/angular-touch.js"></script><script type="text/javascript" src="../js/angular-animate.js"></script><script type="text/javascript" src="../js/csv.js"></script><script type="text/javascript" src="../js/pdfmake.js"></script><script type="text/javascript" src="../js/vfs_fonts.js"></script><script type="text/javascript" src="../js/ui-grid.js"></script><script type="text/javascript" src="../js/angular/seedQuery.js"></script><script type="text/javascript" src="../js/date.js"></script><script type="text/javascript" src="../js/utility.js"></script><script type="text/javascript" src="../js/jquery.easytree.js"></script><script type="text/javascript" src="../js/bootstrap.min.js"></script><script type="text/javascript" src="../js/alasql.min.js"></script><link rel="stylesheet" href="../css/ui-grid.css"/><link rel="stylesheet" href="../css/bootstrap.min.css"/><link rel="stylesheet" href="../css/main.css"/><link rel="stylesheet" href="../css/jquery-ui.css"/><link rel="stylesheet" href="../css/easytree/skin-win8/ui.easytree.css"/><link rel="import" href="../menu.html"/></head><body ng-app="seedQueryApp"><div ng-controller="seedQueryController"><form id="seedQuerySearchBean"><div class="panel panel-default default-panel"><div class="panel-heading">Search form seedQuery</div><div class="panel-body"><div class="pull-left right-input"><div class="input-group"><span class="input-group-addon">seedQueryId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="seedQuery-seedQueryId" ng-model="searchBean.seedQueryId" ng-readonly="false" name="seedQueryId" placeholder="seedQueryId"/></div></div><div class="pull-left right-input"><div class="input-group"><span class="input-group-addon">seedKeyword</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="seedQuery-seedKeyword" ng-model="searchBean.seedKeyword" ng-readonly="false" name="seedKeyword" placeholder="seedKeyword"/></div></div><div class="pull-left right-input"><div class="input-group"><span class="input-group-addon">status</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="seedQuery-status" ng-model="searchBean.status" ng-readonly="false" name="status" placeholder="status"/></div></div><div class="pull-left right-input" style="height: 59px;"><div class="input-group"><span class="input-group-addon">mountain</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="searchBean.mountain.mountainId" id="mountain" ng-options="mountain.mountainId as  mountain.mountainId+&#39; &#39;+ mountain.name for mountain in childrenList.mountainList" enctype="UTF-8"></select></div></div><div class="pull-left right-input"><div class="input-group"><span class="input-group-addon">mountainHeight</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="seedQuery-mountainHeight" ng-model="searchBean.mountainHeight" ng-readonly="false" name="mountainHeight" placeholder="mountainHeight"/></div></div><div class="pull-left right-input" style="height: 59px;"><div class="input-group"><span class="input-group-addon">photo</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="searchBean.photo.photoId" id="photo" ng-options="photo.photoId as  photo.photoId+&#39; &#39;+ photo.url for photo in childrenList.photoList" enctype="UTF-8"></select></div></div></div><div class="panel-body"><div class="panel-body"><div class="pull-left right-input"><button ng-click="addNew()" class="btn btn-default ">Add new</button><button ng-click="search()" class="btn btn-default ">Find</button><button ng-click="reset()" class="btn btn-default ">Reset</button></div></div></div></div></form><form id="seedQueryList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List seedQuery<button ng-click="downloadEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body"><div ui-grid="seedQueryGridOptions" ui-grid-pagination="" ui-grid-selection="" ui-grid-exporter=""></div></div></div></form><form id="seedQueryDetailForm" name="seedQueryDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail seedQuery {{ selectedEntity.seedQueryId }}</div><div class="panel-body"><ul class="nav nav-tabs" role="tablist" id="seedQueryTabs"><li role="presentation"><a href="#seedQuery-Details" aria-controls="Details" role="tab" data-toggle="tab" ng-click="refreshTableDetails()">Details</a></li><script type="text/javascript">$('a[href="#seedQuery-Details"]').on('shown.bs.tab', function (e) {
var target = $(e.target).attr("href"); // activated tab
//console.log(target);
if (angular.element($('#seedQueryTabs')).scope()!=null && angular.element($('#seedQueryTabs')).scope()!=undefined) 
angular.element($('#seedQueryTabs')).scope().refreshTableDetails();
});
</script><li role="presentation"><a href="#seedQuery-relationships" aria-controls="relationships" role="tab" data-toggle="tab" ng-click="refreshTableRelationships()">relationships</a></li><script type="text/javascript">$('a[href="#seedQuery-relationships"]').on('shown.bs.tab', function (e) {
var target = $(e.target).attr("href"); // activated tab
//console.log(target);
if (angular.element($('#seedQueryTabs')).scope()!=null && angular.element($('#seedQueryTabs')).scope()!=undefined) 
angular.element($('#seedQueryTabs')).scope().refreshTableRelationships();
});
</script></ul><div class="tab-content"><div role="tabpanel" class="tab-pane fade" id="seedQuery-Details"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !seedQueryDetailForm.seedQueryId.$valid, &#39;has-success&#39;: seedQueryDetailForm.seedQueryId.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">seedQueryId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="seedQuery-seedQueryId" ng-model="selectedEntity.seedQueryId" ng-readonly="true" name="seedQueryId" placeholder="seedQueryId"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !seedQueryDetailForm.seedKeyword.$valid, &#39;has-success&#39;: seedQueryDetailForm.seedKeyword.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">seedKeyword</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="seedQuery-seedKeyword" ng-model="selectedEntity.seedKeyword" ng-readonly="false" name="seedKeyword" placeholder="seedKeyword"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !seedQueryDetailForm.status.$valid, &#39;has-success&#39;: seedQueryDetailForm.status.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">status</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="seedQuery-status" ng-model="selectedEntity.status" ng-readonly="false" name="status" placeholder="status" ng-required="true"/></div><small class="help-block" ng-show="seedQueryDetailForm.status.$error.required">seedQuery: status required</small></div></div><div role="tabpanel" class="tab-pane fade" id="seedQuery-relationships"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !seedQueryDetailForm.mountain.$valid, &#39;has-success&#39;: seedQueryDetailForm.mountain.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">mountain</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.mountain" id="mountain" name="mountain" ng-options="mountain as  mountain.mountainId+&#39; &#39;+ mountain.name for mountain in childrenList.mountainList track by mountain.mountainId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="showMountainDetail()" class="btn btn-default " id="mountain" ng-if="selectedEntity.mountain==null">Add new mountain</button><button ng-click="showMountainDetail()" class="btn btn-default " id="mountain" ng-if="selectedEntity.mountain!=null">Show detail</button></span></div></div><div id="seedQuery-photo" class="modal fade" role="dialog"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button><h4 class="modal-title">Modal header</h4></div><div class="modal-body"><p>some text</p><div class="input-group"><span class="input-group-addon">photo</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.photo" id="photo" name="photo" ng-options="photo as  photo.photoId+&#39; &#39;+ photo.url for photo in childrenList.photoList track by photo.photoId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="saveLinkedPhoto()" class="btn btn-default " id="photo">Save</button></span></div></div><div class="modal-footer"><button ng-click="close()" class="btn btn-default " data-dismiss="modal">close</button></div></div></div></div><br/><br/><div class="pull-left" style="width: 100%"><div class="panel panel-default default-panel"><div class="panel-heading">photo<button ng-click="showPhotoDetail()" class="btn btn-default  pull-right" style="margin-top: -7px">Add new photo</button><button type="button" class="btn btn-default pull-right" style="margin-top: -7px" data-toggle="modal" data-target="#seedQuery-photo">Link existing</button><button ng-click="downloadPhotoList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body" ng-class="{&#39;has-error&#39;: !seedQueryDetailForm.photo.$valid, &#39;has-success&#39;: seedQueryDetailForm.photo.$valid}"><label id="photo">photo</label><div id="photo" ng-if="selectedEntity.photoList.length&gt;0"><div style="top: 100px" ui-grid="photoListGridOptions" ui-grid-pagination="" ui-grid-selection=""></div></div></div></div></div></div></div></div><script type="text/javascript">$('#seedQueryTabs a:first').tab('show');</script><div class="panel-body"><div class="pull-left"><form id="seedQueryActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default " ng-if="selectedEntity.seedQueryId==undefined">Insert</button><button ng-click="update()" class="btn btn-default " ng-if="selectedEntity.seedQueryId&gt;0">Update</button><button ng-click="del()" class="btn btn-default " ng-if="selectedEntity.seedQueryId&gt;0">Delete</button></form></div></div></div></form></div><div ng-controller="mountainController"><form id="mountainList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List mountain<button ng-click="downloadEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body"><div ui-grid="mountainGridOptions" ui-grid-pagination="" ui-grid-selection="" ui-grid-exporter=""></div></div></div></form><form id="mountainDetailForm" name="mountainDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail mountain {{ selectedEntity.mountainId }}</div><div class="panel-body"><ul class="nav nav-tabs" role="tablist" id="mountainTabs"><li role="presentation"><a href="#mountain-tab1" aria-controls="tab1" role="tab" data-toggle="tab" ng-click="refreshTableTab1()">tab1</a></li><script type="text/javascript">$('a[href="#mountain-tab1"]').on('shown.bs.tab', function (e) {
var target = $(e.target).attr("href"); // activated tab
//console.log(target);
if (angular.element($('#mountainTabs')).scope()!=null && angular.element($('#mountainTabs')).scope()!=undefined) 
angular.element($('#mountainTabs')).scope().refreshTableTab1();
});
</script><li role="presentation"><a href="#mountain-tab2" aria-controls="tab2" role="tab" data-toggle="tab" ng-click="refreshTableTab2()">tab2</a></li><script type="text/javascript">$('a[href="#mountain-tab2"]').on('shown.bs.tab', function (e) {
var target = $(e.target).attr("href"); // activated tab
//console.log(target);
if (angular.element($('#mountainTabs')).scope()!=null && angular.element($('#mountainTabs')).scope()!=undefined) 
angular.element($('#mountainTabs')).scope().refreshTableTab2();
});
</script></ul><div class="tab-content"><div role="tabpanel" class="tab-pane fade" id="mountain-tab1"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !mountainDetailForm.mountainId.$valid, &#39;has-success&#39;: mountainDetailForm.mountainId.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">mountainId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="mountain-mountainId" ng-model="selectedEntity.mountainId" ng-readonly="true" name="mountainId" placeholder="mountainId"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !mountainDetailForm.name.$valid, &#39;has-success&#39;: mountainDetailForm.name.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">name</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="mountain-name" ng-model="selectedEntity.name" ng-readonly="false" name="name" placeholder="name" ng-required="true" ng-minlength="2" ng-maxlength="14"/></div><small class="help-block" ng-show="mountainDetailForm.name.$error.required">mountain: name required</small><small class="help-block" ng-show="mountainDetailForm.name.$error.minlength">mountain: name min 2 caratteri</small><small class="help-block" ng-show="mountainDetailForm.name.$error.maxlength">mountain: name max 14 caratteri</small></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !mountainDetailForm.height.$valid, &#39;has-success&#39;: mountainDetailForm.height.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">height</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="mountain-height" ng-model="selectedEntity.height" ng-readonly="false" name="height" placeholder="height"/></div></div></div><div role="tabpanel" class="tab-pane fade" id="mountain-tab2"></div></div></div><script type="text/javascript">$('#mountainTabs a:first').tab('show');</script><div class="panel-body"><div class="pull-left"><form id="mountainActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default " ng-if="selectedEntity.mountainId==undefined">Insert</button><button ng-click="update()" class="btn btn-default " ng-if="selectedEntity.mountainId&gt;0">Update</button><button ng-click="del()" class="btn btn-default " ng-if="selectedEntity.mountainId&gt;0">Delete</button><button ng-click="remove()" class="btn btn-default " ng-if="selectedEntity.mountainId&gt;0">Remove</button></form></div></div></div></form></div><div ng-controller="photoController"><form id="photoList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List photo<button ng-click="downloadEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body"><div ui-grid="photoGridOptions" ui-grid-pagination="" ui-grid-selection="" ui-grid-exporter=""></div></div></div></form><form id="photoDetailForm" name="photoDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail photo {{ selectedEntity.photoId }}</div><div class="panel-body"><ul class="nav nav-tabs" role="tablist" id="photoTabs"><li role="presentation"><a href="#photo-Detail" aria-controls="Detail" role="tab" data-toggle="tab" ng-click="refreshTableDetail()">Detail</a></li><script type="text/javascript">$('a[href="#photo-Detail"]').on('shown.bs.tab', function (e) {
var target = $(e.target).attr("href"); // activated tab
//console.log(target);
if (angular.element($('#photoTabs')).scope()!=null && angular.element($('#photoTabs')).scope()!=undefined) 
angular.element($('#photoTabs')).scope().refreshTableDetail();
});
</script></ul><div class="tab-content"><div role="tabpanel" class="tab-pane fade" id="photo-Detail"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !photoDetailForm.photoId.$valid, &#39;has-success&#39;: photoDetailForm.photoId.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">photoId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="photo-photoId" ng-model="selectedEntity.photoId" ng-readonly="true" name="photoId" placeholder="photoId"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !photoDetailForm.url.$valid, &#39;has-success&#39;: photoDetailForm.url.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">url</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="photo-url" ng-model="selectedEntity.url" ng-readonly="false" name="url" placeholder="url"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !photoDetailForm.social.$valid, &#39;has-success&#39;: photoDetailForm.social.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">social</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="photo-social" ng-model="selectedEntity.social" ng-readonly="false" name="social" placeholder="social"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !photoDetailForm.date.$valid, &#39;has-success&#39;: photoDetailForm.date.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">date</span><input class="form-control " aria-describedby="sizing-addon3" type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }" ng-model="selectedEntity.date" ng-readonly="false" name="date" placeholder="date"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !photoDetailForm.status.$valid, &#39;has-success&#39;: photoDetailForm.status.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">status</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="photo-status" ng-model="selectedEntity.status" ng-readonly="false" name="status" placeholder="status"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !photoDetailForm.socialId.$valid, &#39;has-success&#39;: photoDetailForm.socialId.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">socialId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="photo-socialId" ng-model="selectedEntity.socialId" ng-readonly="false" name="socialId" placeholder="socialId"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !photoDetailForm.relatedPost.$valid, &#39;has-success&#39;: photoDetailForm.relatedPost.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">relatedPost</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="photo-relatedPost" ng-model="selectedEntity.relatedPost" ng-readonly="false" name="relatedPost" placeholder="relatedPost"/></div></div></div></div></div><script type="text/javascript">$('#photoTabs a:first').tab('show');</script><div class="panel-body"><div class="pull-left"><form id="photoActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default " ng-if="selectedEntity.photoId==undefined">Insert</button><button ng-click="update()" class="btn btn-default " ng-if="selectedEntity.photoId&gt;0">Update</button><button ng-click="del()" class="btn btn-default " ng-if="selectedEntity.photoId&gt;0">Delete</button><button ng-click="remove()" class="btn btn-default " ng-if="selectedEntity.photoId&gt;0">Remove</button></form></div></div></div></form></div><script type="text/javascript">loadMenu();  activeMenu("seedQuery");</script></body></html>