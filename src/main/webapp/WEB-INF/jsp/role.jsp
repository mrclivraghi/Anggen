<!DOCTYPE html><html><head><title>role</title><script type="text/javascript" src="../js/jquery-1.9.1.js"></script><script type="text/javascript" src="../js/jquery-ui.js"></script><script type="text/javascript" src="../js/angular.js"></script><script type="text/javascript" src="../js/angular-touch.js"></script><script type="text/javascript" src="../js/angular-animate.js"></script><script type="text/javascript" src="../js/csv.js"></script><script type="text/javascript" src="../js/pdfmake.js"></script><script type="text/javascript" src="../js/vfs_fonts.js"></script><script type="text/javascript" src="../js/ui-grid.js"></script><script type="text/javascript" src="../js/angular/role.js"></script><script type="text/javascript" src="../js/date.js"></script><script type="text/javascript" src="../js/utility.js"></script><script type="text/javascript" src="../js/jquery.easytree.js"></script><script type="text/javascript" src="../js/bootstrap.min.js"></script><script type="text/javascript" src="../js/alasql.min.js"></script><link rel="stylesheet" href="../css/ui-grid.css"/><link rel="stylesheet" href="../css/bootstrap.min.css"/><link rel="stylesheet" href="../css/main.css"/><link rel="stylesheet" href="../css/jquery-ui.css"/><link rel="stylesheet" href="../css/easytree/skin-win8/ui.easytree.css"/><link rel="import" href="../menu.html"/></head><body ng-app="roleApp"><div ng-controller="roleController"><form id="roleSearchBean"><div class="panel panel-default default-panel"><div class="panel-heading">Search form role</div><div class="panel-body"><div class="pull-left right-input"><div class="input-group"><span class="input-group-addon">roleId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="role-roleId" ng-model="searchBean.roleId" ng-readonly="false" name="roleId" placeholder="roleId"/></div></div><div class="pull-left right-input"><div class="input-group"><span class="input-group-addon">role</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="role-role" ng-model="searchBean.role" ng-readonly="false" name="role" placeholder="role"/></div></div><div class="pull-left right-input" style="height: 59px;"><div class="input-group"><span class="input-group-addon">user</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="searchBean.user.userId" id="user" ng-options="user.userId as user.userId for user in childrenList.userList" enctype="UTF-8"></select></div></div><div class="pull-left right-input" style="height: 59px;"><div class="input-group"><span class="input-group-addon">entity</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="searchBean.entity.entityId" id="entity" ng-options="entity.entityId as entity.entityId for entity in childrenList.entityList" enctype="UTF-8"></select></div></div></div><div class="panel-body"><div class="panel-body"><div class="pull-left right-input"><button ng-click="addNew()" class="btn btn-default ">Add new</button><button ng-click="search()" class="btn btn-default ">Find</button><button ng-click="reset()" class="btn btn-default ">Reset</button></div></div></div></div></form><form id="roleList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List role<button ng-click="downloadEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body"><div ui-grid="roleGridOptions" ui-grid-pagination="" ui-grid-selection="" ui-grid-exporter=""></div></div></div></form><form id="roleDetailForm" name="roleDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail role {{ selectedEntity.roleId }}</div><div class="panel-body"><ul class="nav nav-tabs" role="tablist" id="roleTabs"><li role="presentation"><a href="#role-Detail" aria-controls="Detail" role="tab" data-toggle="tab" ng-click="refreshTableDetail()">Detail</a></li><script type="text/javascript">$('a[href="#role-Detail"]').on('shown.bs.tab', function (e) {
var target = $(e.target).attr("href"); // activated tab
//console.log(target);
if (angular.element($('#roleTabs')).scope()!=null && angular.element($('#roleTabs')).scope()!=undefined) 
angular.element($('#roleTabs')).scope().refreshTableDetail();
});
</script></ul><div class="tab-content"><div role="tabpanel" class="tab-pane fade" id="role-Detail"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !roleDetailForm.roleId.$valid, &#39;has-success&#39;: roleDetailForm.roleId.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">roleId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="role-roleId" ng-model="selectedEntity.roleId" ng-readonly="true" name="roleId" placeholder="roleId"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !roleDetailForm.role.$valid, &#39;has-success&#39;: roleDetailForm.role.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">role</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="role-role" ng-model="selectedEntity.role" ng-readonly="false" name="role" placeholder="role"/></div></div><div id="role-user" class="modal fade" role="dialog"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button><h4 class="modal-title">Modal header</h4></div><div class="modal-body"><p>some text</p><div class="input-group"><span class="input-group-addon">user</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.user" id="user" name="user" ng-options="user as user.userId for user in childrenList.userList track by user.userId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="saveLinkedUser()" class="btn btn-default " id="user">Save</button></span></div></div><div class="modal-footer"><button ng-click="close()" class="btn btn-default " data-dismiss="modal">close</button></div></div></div></div><br/><br/><div class="pull-left" style="width: 100%"><div class="panel panel-default default-panel"><div class="panel-heading">user<button ng-click="showUserDetail()" class="btn btn-default  pull-right" style="margin-top: -7px">Add new user</button><button type="button" class="btn btn-default pull-right" style="margin-top: -7px" data-toggle="modal" data-target="#role-user">Link existing</button><button ng-click="downloadUserList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body" ng-class="{&#39;has-error&#39;: !roleDetailForm.user.$valid, &#39;has-success&#39;: roleDetailForm.user.$valid}"><label id="user">user</label><div id="user" ng-if="selectedEntity.userList.length&gt;0"><div style="top: 100px" ui-grid="userListGridOptions" ui-grid-pagination="" ui-grid-selection=""></div></div></div></div></div><div id="role-entity" class="modal fade" role="dialog"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button><h4 class="modal-title">Modal header</h4></div><div class="modal-body"><p>some text</p><div class="input-group"><span class="input-group-addon">entity</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.entity" id="entity" name="entity" ng-options="entity as entity.entityId for entity in childrenList.entityList track by entity.entityId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="saveLinkedEntity()" class="btn btn-default " id="entity">Save</button></span></div></div><div class="modal-footer"><button ng-click="close()" class="btn btn-default " data-dismiss="modal">close</button></div></div></div></div><br/><br/><div class="pull-left" style="width: 100%"><div class="panel panel-default default-panel"><div class="panel-heading">entity<button ng-click="showEntityDetail()" class="btn btn-default  pull-right" style="margin-top: -7px">Add new entity</button><button type="button" class="btn btn-default pull-right" style="margin-top: -7px" data-toggle="modal" data-target="#role-entity">Link existing</button><button ng-click="downloadEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body" ng-class="{&#39;has-error&#39;: !roleDetailForm.entity.$valid, &#39;has-success&#39;: roleDetailForm.entity.$valid}"><label id="entity">entity</label><div id="entity" ng-if="selectedEntity.entityList.length&gt;0"><div style="top: 100px" ui-grid="entityListGridOptions" ui-grid-pagination="" ui-grid-selection=""></div></div></div></div></div></div></div></div><script type="text/javascript">$('#roleTabs a:first').tab('show');</script><div class="panel-body"><div class="pull-left"><form id="roleActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default " ng-if="selectedEntity.roleId==undefined">Insert</button><button ng-click="update()" class="btn btn-default " ng-if="selectedEntity.roleId&gt;0">Update</button><button ng-click="del()" class="btn btn-default " ng-if="selectedEntity.roleId&gt;0">Delete</button></form></div></div></div></form></div><div ng-controller="userController"><form id="userList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List user<button ng-click="downloadEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body"><div ui-grid="userGridOptions" ui-grid-pagination="" ui-grid-selection="" ui-grid-exporter=""></div></div></div></form><form id="userDetailForm" name="userDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail user {{ selectedEntity.userId }}</div><div class="panel-body"><ul class="nav nav-tabs" role="tablist" id="userTabs"><li role="presentation"><a href="#user-Detail" aria-controls="Detail" role="tab" data-toggle="tab" ng-click="refreshTableDetail()">Detail</a></li><script type="text/javascript">$('a[href="#user-Detail"]').on('shown.bs.tab', function (e) {
var target = $(e.target).attr("href"); // activated tab
//console.log(target);
if (angular.element($('#userTabs')).scope()!=null && angular.element($('#userTabs')).scope()!=undefined) 
angular.element($('#userTabs')).scope().refreshTableDetail();
});
</script></ul><div class="tab-content"><div role="tabpanel" class="tab-pane fade" id="user-Detail"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !userDetailForm.userId.$valid, &#39;has-success&#39;: userDetailForm.userId.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">userId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="user-userId" ng-model="selectedEntity.userId" ng-readonly="true" name="userId" placeholder="userId"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !userDetailForm.username.$valid, &#39;has-success&#39;: userDetailForm.username.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">username</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="user-username" ng-model="selectedEntity.username" ng-readonly="false" name="username" placeholder="username"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !userDetailForm.password.$valid, &#39;has-success&#39;: userDetailForm.password.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">password</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="user-password" ng-model="selectedEntity.password" ng-readonly="false" name="password" placeholder="password"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !userDetailForm.enabled.$valid, &#39;has-success&#39;: userDetailForm.enabled.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">enabled</span><select class="form-control " aria-describedby="sizing-addon3" type="checkbox" id="user-enabled" ng-model="selectedEntity.enabled" ng-readonly="false" name="enabled" placeholder="enabled" ng-options="value for value in trueFalseValues"></select></div></div></div></div></div><script type="text/javascript">$('#userTabs a:first').tab('show');</script><div class="panel-body"><div class="pull-left"><form id="userActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default " ng-if="selectedEntity.userId==undefined">Insert</button><button ng-click="update()" class="btn btn-default " ng-if="selectedEntity.userId&gt;0">Update</button><button ng-click="del()" class="btn btn-default " ng-if="selectedEntity.userId&gt;0">Delete</button><button ng-click="remove()" class="btn btn-default " ng-if="selectedEntity.userId&gt;0">Remove</button></form></div></div></div></form></div><div ng-controller="entityController"><form id="entityList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List entity<button ng-click="downloadEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body"><div ui-grid="entityGridOptions" ui-grid-pagination="" ui-grid-selection="" ui-grid-exporter=""></div></div></div></form><form id="entityDetailForm" name="entityDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail entity {{ selectedEntity.entityId }}</div><div class="panel-body"><ul class="nav nav-tabs" role="tablist" id="entityTabs"><li role="presentation"><a href="#entity-Detail" aria-controls="Detail" role="tab" data-toggle="tab" ng-click="refreshTableDetail()">Detail</a></li><script type="text/javascript">$('a[href="#entity-Detail"]').on('shown.bs.tab', function (e) {
var target = $(e.target).attr("href"); // activated tab
//console.log(target);
if (angular.element($('#entityTabs')).scope()!=null && angular.element($('#entityTabs')).scope()!=undefined) 
angular.element($('#entityTabs')).scope().refreshTableDetail();
});
</script></ul><div class="tab-content"><div role="tabpanel" class="tab-pane fade" id="entity-Detail"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !entityDetailForm.entityId.$valid, &#39;has-success&#39;: entityDetailForm.entityId.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">entityId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="entity-entityId" ng-model="selectedEntity.entityId" ng-readonly="true" name="entityId" placeholder="entityId"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !entityDetailForm.entityName.$valid, &#39;has-success&#39;: entityDetailForm.entityName.$valid}" style="height:59px"><div class="input-group"><span class="input-group-addon">entityName</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="entity-entityName" ng-model="selectedEntity.entityName" ng-readonly="false" name="entityName" placeholder="entityName"/></div></div></div></div></div><script type="text/javascript">$('#entityTabs a:first').tab('show');</script><div class="panel-body"><div class="pull-left"><form id="entityActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default " ng-if="selectedEntity.entityId==undefined">Insert</button><button ng-click="update()" class="btn btn-default " ng-if="selectedEntity.entityId&gt;0">Update</button><button ng-click="del()" class="btn btn-default " ng-if="selectedEntity.entityId&gt;0">Delete</button><button ng-click="remove()" class="btn btn-default " ng-if="selectedEntity.entityId&gt;0">Remove</button></form></div></div></div></form></div><script type="text/javascript">loadMenu();  activeMenu("role");</script></body></html>