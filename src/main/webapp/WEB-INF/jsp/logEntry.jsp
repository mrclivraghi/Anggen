<div ng-controller="logEntryController"><form id="logEntrySearchBean"><div class="panel panel-default default-panel"><div class="panel-heading">Search form logEntry</div><div class="panel-body"><div class="pull-left right-input" ng-show="true "><div class="input-group"><span class="input-group-addon">logEntryId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="logEntry-logEntryId" ng-model="searchBean.logEntryId" ng-readonly="false" name="logEntryId" placeholder="logEntryId"/></div></div><div class="pull-left right-input" ng-show="true "><div class="input-group"><span class="input-group-addon">info</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="logEntry-info" ng-model="searchBean.info" ng-readonly="false" name="info" placeholder="info"/></div></div><div class="pull-left right-input" ng-show="true "><div class="input-group"><span class="input-group-addon">hostName</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="logEntry-hostName" ng-model="searchBean.hostName" ng-readonly="false" name="hostName" placeholder="hostName"/></div></div><div class="pull-left right-input" ng-show="true "><div class="input-group"><span class="input-group-addon">ipAddress</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="logEntry-ipAddress" ng-model="searchBean.ipAddress" ng-readonly="false" name="ipAddress" placeholder="ipAddress"/></div></div><div class="pull-left right-input" ng-show="true "><div class="input-group"><span class="input-group-addon">logDate</span><input class="form-control " aria-describedby="sizing-addon3" type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }" ng-model="searchBean.logDate" ng-readonly="false" name="logDate" placeholder="logDate"/></div></div><div class="pull-left right-input" ng-show="true "><div class="input-group"><span class="input-group-addon">logType</span><select class="form-control pull-left" aria-describedby="sizing-addon3" type="text" id="logEntry-logType" ng-model="searchBean.logType" ng-readonly="false" name="logType" placeholder="logType" ng-options="logType as logType for logType in childrenList.logTypeList" enctype="UTF-8"></select></div></div><div class="pull-left right-input" ng-show="true "><div class="input-group"><span class="input-group-addon">operationType</span><select class="form-control pull-left" aria-describedby="sizing-addon3" type="text" id="logEntry-operationType" ng-model="searchBean.operationType" ng-readonly="false" name="operationType" placeholder="operationType" ng-options="operationType as operationType for operationType in childrenList.operationTypeList" enctype="UTF-8"></select></div></div><div class="pull-left right-input" style="height: 59px;" ng-show="true  && (restrictionList.user==undefined || restrictionList.user.canSearch==true)"><div class="input-group"><span class="input-group-addon">user</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="searchBean.user.userId" id="user" ng-options="user.userId as  user.username+&#39; &#39;+ user.userId for user in childrenList.userList" enctype="UTF-8"></select></div></div><div class="pull-left right-input" style="height: 59px;" ng-show="true  && (restrictionList.entity==undefined || restrictionList.entity.canSearch==true)"><div class="input-group"><span class="input-group-addon">entity</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="searchBean.entity.entityId" id="entity" ng-options="entity.entityId as  entity.entityId+&#39; &#39;+ entity.name for entity in childrenList.entityList" enctype="UTF-8"></select></div></div></div><div class="panel-body"><div class="panel-body"><div class="pull-left right-input"><button ng-click="addNew()" class="btn btn-default " ng-show="(restrictionList.logEntry==undefined || restrictionList.logEntry.canCreate==true)">Add new</button><button ng-click="search()" class="btn btn-default ">Find</button><button ng-click="reset()" class="btn btn-default ">Reset</button></div></div></div></div></form><form id="logEntryList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List logEntry<button ng-click="downloadEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body"><div ui-grid="logEntryGridOptions" ui-grid-pagination="" ui-grid-selection="" ui-grid-exporter=""></div></div></div></form><form id="logEntryDetailForm" name="logEntryDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail logEntry {{ selectedEntity.logEntryId }}<button type="button" class="close" aria-label="Close" ng-click="closeEntityDetail()"><span aria-hidden="true">&times;</span></button></div><div class="panel-body"><ul class="nav nav-tabs" role="tablist" id="logEntryTabs"><li role="presentation"><a href="#logEntry-Detail" aria-controls="Detail" role="tab" data-toggle="tab" ng-click="refreshTableDetail()">Detail</a></li><script type="text/javascript">$('a[href="#logEntry-Detail"]').on('shown.bs.tab', function (e) {
var target = $(e.target).attr("href"); // activated tab
//console.log(target);
if (angular.element($('#logEntryTabs')).scope()!=null && angular.element($('#logEntryTabs')).scope()!=undefined) 
{ /* angular.element($('#logEntryTabs')).scope().refreshTableDetail(); */ }
});
</script></ul><div class="tab-content"><div role="tabpanel" class="tab-pane fade" id="logEntry-Detail"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !logEntryDetailForm.logEntryId.$valid, &#39;has-success&#39;: logEntryDetailForm.logEntryId.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">logEntryId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="logEntry-logEntryId" ng-model="selectedEntity.logEntryId" ng-readonly="true" name="logEntryId" placeholder="logEntryId"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !logEntryDetailForm.logDate.$valid, &#39;has-success&#39;: logEntryDetailForm.logDate.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">logDate</span><input class="form-control " aria-describedby="sizing-addon3" type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }" ng-model="selectedEntity.logDate" ng-readonly="restrictionList.logEntry.restrictionFieldMap.logDate" name="logDate" placeholder="logDate"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !logEntryDetailForm.ipAddress.$valid, &#39;has-success&#39;: logEntryDetailForm.ipAddress.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">ipAddress</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="logEntry-ipAddress" ng-model="selectedEntity.ipAddress" ng-readonly="restrictionList.logEntry.restrictionFieldMap.ipAddress" name="ipAddress" placeholder="ipAddress"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !logEntryDetailForm.hostName.$valid, &#39;has-success&#39;: logEntryDetailForm.hostName.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">hostName</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="logEntry-hostName" ng-model="selectedEntity.hostName" ng-readonly="restrictionList.logEntry.restrictionFieldMap.hostName" name="hostName" placeholder="hostName"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !logEntryDetailForm.info.$valid, &#39;has-success&#39;: logEntryDetailForm.info.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">info</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="logEntry-info" ng-model="selectedEntity.info" ng-readonly="restrictionList.logEntry.restrictionFieldMap.info" name="info" placeholder="info"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !logEntryDetailForm.logType.$valid, &#39;has-success&#39;: logEntryDetailForm.logType.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">logType</span><select class="form-control pull-left" aria-describedby="sizing-addon3" type="text" id="logEntry-logType" ng-model="selectedEntity.logType" ng-readonly="restrictionList.logEntry.restrictionFieldMap.logType" name="logType" placeholder="logType" ng-options="logType as logType for logType in childrenList.logTypeList" enctype="UTF-8"></select></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !logEntryDetailForm.operationType.$valid, &#39;has-success&#39;: logEntryDetailForm.operationType.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">operationType</span><select class="form-control pull-left" aria-describedby="sizing-addon3" type="text" id="logEntry-operationType" ng-model="selectedEntity.operationType" ng-readonly="restrictionList.logEntry.restrictionFieldMap.operationType" name="operationType" placeholder="operationType" ng-options="operationType as operationType for operationType in childrenList.operationTypeList" enctype="UTF-8"></select></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !logEntryDetailForm.entity.$valid, &#39;has-success&#39;: logEntryDetailForm.entity.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">entity</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.entity" id="entity" name="entity" ng-options="entity as  entity.entityId+&#39; &#39;+ entity.name for entity in childrenList.entityList track by entity.entityId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="showEntityDetail()" class="btn btn-default " id="entity" ng-if="selectedEntity.entity==null">Add new entity</button><button ng-click="showEntityDetail()" class="btn btn-default " id="entity" ng-if="selectedEntity.entity!=null">Show detail</button></span></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !logEntryDetailForm.user.$valid, &#39;has-success&#39;: logEntryDetailForm.user.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">user</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.user" id="user" name="user" ng-options="user as  user.username+&#39; &#39;+ user.userId for user in childrenList.userList track by user.userId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="showUserDetail()" class="btn btn-default " id="user" ng-if="selectedEntity.user==null">Add new user</button><button ng-click="showUserDetail()" class="btn btn-default " id="user" ng-if="selectedEntity.user!=null">Show detail</button></span></div></div></div></div></div><script type="text/javascript">$('#logEntryTabs a:first').tab('show');</script><div class="panel-body"><div class="pull-left"><form id="logEntryActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default " ng-if="selectedEntity.logEntryId==undefined" ng-show="(restrictionList.logEntry==undefined || restrictionList.logEntry.canCreate==true)">Insert</button><button ng-click="update()" class="btn btn-default " ng-if="selectedEntity.logEntryId&gt;0" ng-show="(restrictionList.logEntry==undefined || restrictionList.logEntry.canUpdate==true)">Update</button><button ng-click="del()" class="btn btn-default " ng-if="selectedEntity.logEntryId&gt;0" ng-show="(restrictionList.logEntry==undefined || restrictionList.logEntry.canDelete==true)">Delete</button></form></div></div></div></form></div><div ng-controller="userController"><form id="userList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List user<button ng-click="downloadEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body"><div ui-grid="userGridOptions" ui-grid-pagination="" ui-grid-selection="" ui-grid-exporter=""></div></div></div></form><form id="userDetailForm" name="userDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail user {{ selectedEntity.userId }}<button type="button" class="close" aria-label="Close" ng-click="closeEntityDetail()"><span aria-hidden="true">&times;</span></button></div><div class="panel-body"><ul class="nav nav-tabs" role="tablist" id="userTabs"><li role="presentation"><a href="#user-Detail" aria-controls="Detail" role="tab" data-toggle="tab" ng-click="refreshTableDetail()">Detail</a></li><script type="text/javascript">$('a[href="#user-Detail"]').on('shown.bs.tab', function (e) {
var target = $(e.target).attr("href"); // activated tab
//console.log(target);
if (angular.element($('#userTabs')).scope()!=null && angular.element($('#userTabs')).scope()!=undefined) 
{ /* angular.element($('#userTabs')).scope().refreshTableDetail(); */ }
});
</script></ul><div class="tab-content"><div role="tabpanel" class="tab-pane fade" id="user-Detail"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !userDetailForm.userId.$valid, &#39;has-success&#39;: userDetailForm.userId.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">userId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="user-userId" ng-model="selectedEntity.userId" ng-readonly="true" name="userId" placeholder="userId"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !userDetailForm.username.$valid, &#39;has-success&#39;: userDetailForm.username.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">username</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="user-username" ng-model="selectedEntity.username" ng-readonly="restrictionList.user.restrictionFieldMap.username" name="username" placeholder="username"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !userDetailForm.enabled.$valid, &#39;has-success&#39;: userDetailForm.enabled.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">enabled</span><select class="form-control " aria-describedby="sizing-addon3" type="checkbox" id="user-enabled" ng-model="selectedEntity.enabled" ng-readonly="restrictionList.user.restrictionFieldMap.enabled" name="enabled" placeholder="enabled" ng-options="value for value in trueFalseValues"></select></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !userDetailForm.password.$valid, &#39;has-success&#39;: userDetailForm.password.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">password</span><input class="form-control " aria-describedby="sizing-addon3" type="password" id="user-password" ng-model="selectedEntity.password" ng-readonly="restrictionList.user.restrictionFieldMap.password" name="password" placeholder="password"/></div></div><div id="user-role" class="modal fade" role="dialog"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button><h4 class="modal-title">Link existing role</h4></div><div class="modal-body"><p></p><div class="input-group"><span class="input-group-addon">role</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.role" id="role" name="role" ng-options="role as  role.roleId+&#39; &#39;+ role.role for role in childrenList.roleList track by role.roleId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="saveLinkedRole()" class="btn btn-default " id="role">Save</button></span></div></div><div class="modal-footer"><button ng-click="close()" class="btn btn-default " data-dismiss="modal">close</button></div></div></div></div><br/><br/><div class="pull-left" style="width: 100%" ng-show="true  && (restrictionList.role==undefined || restrictionList.role.canSearch==true)"><div class="panel panel-default default-panel"><div class="panel-heading">role<button ng-click="showRoleDetail()" class="btn btn-default  pull-right" style="margin-top: -7px" ng-show="(restrictionList.role==undefined || restrictionList.role.canCreate==true)">Add new role</button><button type="button" class="btn btn-default pull-right" style="margin-top: -7px" data-toggle="modal" data-target="#user-role">Link existing</button><button ng-click="downloadRoleList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body" ng-class="{&#39;has-error&#39;: !userDetailForm.role.$valid, &#39;has-success&#39;: userDetailForm.role.$valid}"><label id="role">role</label><div id="role" ng-if="selectedEntity.roleList.length&gt;0"><div style="top: 100px" ui-grid="roleGridOptions" ui-grid-pagination="" ui-grid-selection=""></div></div></div></div></div></div></div></div><script type="text/javascript">$('#userTabs a:first').tab('show');</script><div class="panel-body"><div class="pull-left"><form id="userActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default " ng-if="selectedEntity.userId==undefined" ng-show="(restrictionList.user==undefined || restrictionList.user.canCreate==true)">Insert</button><button ng-click="update()" class="btn btn-default " ng-if="selectedEntity.userId&gt;0" ng-show="(restrictionList.user==undefined || restrictionList.user.canUpdate==true)">Update</button><button ng-click="del()" class="btn btn-default " ng-if="selectedEntity.userId&gt;0" ng-show="(restrictionList.user==undefined || restrictionList.user.canDelete==true)">Delete</button><button ng-click="remove()" class="btn btn-default " ng-if="selectedEntity.userId&gt;0" ng-show="(restrictionList.user==undefined || restrictionList.user.canDelete==true)">Remove</button></form></div></div></div></form></div><div ng-controller="entityController"><form id="entityList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List entity<button ng-click="downloadEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body"><div ui-grid="entityGridOptions" ui-grid-pagination="" ui-grid-selection="" ui-grid-exporter=""></div></div></div></form><form id="entityDetailForm" name="entityDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail entity {{ selectedEntity.entityId }}<button type="button" class="close" aria-label="Close" ng-click="closeEntityDetail()"><span aria-hidden="true">&times;</span></button></div><div class="panel-body"><ul class="nav nav-tabs" role="tablist" id="entityTabs"><li role="presentation"><a href="#entity-Detail" aria-controls="Detail" role="tab" data-toggle="tab" ng-click="refreshTableDetail()">Detail</a></li><script type="text/javascript">$('a[href="#entity-Detail"]').on('shown.bs.tab', function (e) {
var target = $(e.target).attr("href"); // activated tab
//console.log(target);
if (angular.element($('#entityTabs')).scope()!=null && angular.element($('#entityTabs')).scope()!=undefined) 
{ /* angular.element($('#entityTabs')).scope().refreshTableDetail(); */ }
});
</script></ul><div class="tab-content"><div role="tabpanel" class="tab-pane fade" id="entity-Detail"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !entityDetailForm.entityId.$valid, &#39;has-success&#39;: entityDetailForm.entityId.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">entityId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="entity-entityId" ng-model="selectedEntity.entityId" ng-readonly="true" name="entityId" placeholder="entityId"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !entityDetailForm.disableViewGeneration.$valid, &#39;has-success&#39;: entityDetailForm.disableViewGeneration.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">disableViewGeneration</span><select class="form-control " aria-describedby="sizing-addon3" type="checkbox" id="entity-disableViewGeneration" ng-model="selectedEntity.disableViewGeneration" ng-readonly="restrictionList.entity.restrictionFieldMap.disableViewGeneration" name="disableViewGeneration" placeholder="disableViewGeneration" ng-options="value for value in trueFalseValues"></select></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !entityDetailForm.enableRestrictionData.$valid, &#39;has-success&#39;: entityDetailForm.enableRestrictionData.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">enableRestrictionData</span><select class="form-control " aria-describedby="sizing-addon3" type="checkbox" id="entity-enableRestrictionData" ng-model="selectedEntity.enableRestrictionData" ng-readonly="restrictionList.entity.restrictionFieldMap.enableRestrictionData" name="enableRestrictionData" placeholder="enableRestrictionData" ng-options="value for value in trueFalseValues"></select></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !entityDetailForm.descendantMaxLevel.$valid, &#39;has-success&#39;: entityDetailForm.descendantMaxLevel.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">descendantMaxLevel</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="entity-descendantMaxLevel" ng-model="selectedEntity.descendantMaxLevel" ng-readonly="restrictionList.entity.restrictionFieldMap.descendantMaxLevel" name="descendantMaxLevel" placeholder="descendantMaxLevel"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !entityDetailForm.generateFrontEnd.$valid, &#39;has-success&#39;: entityDetailForm.generateFrontEnd.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">generateFrontEnd</span><select class="form-control " aria-describedby="sizing-addon3" type="checkbox" id="entity-generateFrontEnd" ng-model="selectedEntity.generateFrontEnd" ng-readonly="restrictionList.entity.restrictionFieldMap.generateFrontEnd" name="generateFrontEnd" placeholder="generateFrontEnd" ng-options="value for value in trueFalseValues"></select></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !entityDetailForm.cache.$valid, &#39;has-success&#39;: entityDetailForm.cache.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">cache</span><select class="form-control " aria-describedby="sizing-addon3" type="checkbox" id="entity-cache" ng-model="selectedEntity.cache" ng-readonly="restrictionList.entity.restrictionFieldMap.cache" name="cache" placeholder="cache" ng-options="value for value in trueFalseValues"></select></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !entityDetailForm.name.$valid, &#39;has-success&#39;: entityDetailForm.name.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">name</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="entity-name" ng-model="selectedEntity.name" ng-readonly="restrictionList.entity.restrictionFieldMap.name" name="name" placeholder="name"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !entityDetailForm.securityType.$valid, &#39;has-success&#39;: entityDetailForm.securityType.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">securityType</span><select class="form-control pull-left" aria-describedby="sizing-addon3" type="text" id="entity-securityType" ng-model="selectedEntity.securityType" ng-readonly="restrictionList.entity.restrictionFieldMap.securityType" name="securityType" placeholder="securityType" ng-options="securityType as securityType for securityType in childrenList.securityTypeList" enctype="UTF-8"></select></div></div><div id="entity-field" class="modal fade" role="dialog"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button><h4 class="modal-title">Link existing field</h4></div><div class="modal-body"><p></p><div class="input-group"><span class="input-group-addon">field</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.field" id="field" name="field" ng-options="field as  field.fieldId+&#39; &#39;+ field.name for field in childrenList.fieldList track by field.fieldId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="saveLinkedField()" class="btn btn-default " id="field">Save</button></span></div></div><div class="modal-footer"><button ng-click="close()" class="btn btn-default " data-dismiss="modal">close</button></div></div></div></div><br/><br/><div class="pull-left" style="width: 100%" ng-show="true  && (restrictionList.field==undefined || restrictionList.field.canSearch==true)"><div class="panel panel-default default-panel"><div class="panel-heading">field<button ng-click="showFieldDetail()" class="btn btn-default  pull-right" style="margin-top: -7px" ng-show="(restrictionList.field==undefined || restrictionList.field.canCreate==true)">Add new field</button><button type="button" class="btn btn-default pull-right" style="margin-top: -7px" data-toggle="modal" data-target="#entity-field">Link existing</button><button ng-click="downloadFieldList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body" ng-class="{&#39;has-error&#39;: !entityDetailForm.field.$valid, &#39;has-success&#39;: entityDetailForm.field.$valid}"><label id="field">field</label><div id="field" ng-if="selectedEntity.fieldList.length&gt;0"><div style="top: 100px" ui-grid="fieldGridOptions" ui-grid-pagination="" ui-grid-selection=""></div></div></div></div></div><div id="entity-enumField" class="modal fade" role="dialog"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button><h4 class="modal-title">Link existing enumField</h4></div><div class="modal-body"><p></p><div class="input-group"><span class="input-group-addon">enumField</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.enumField" id="enumField" name="enumField" ng-options="enumField as  enumField.name+&#39; &#39;+ enumField.enumFieldId for enumField in childrenList.enumFieldList track by enumField.enumFieldId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="saveLinkedEnumField()" class="btn btn-default " id="enumField">Save</button></span></div></div><div class="modal-footer"><button ng-click="close()" class="btn btn-default " data-dismiss="modal">close</button></div></div></div></div><br/><br/><div class="pull-left" style="width: 100%" ng-show="true  && (restrictionList.enumField==undefined || restrictionList.enumField.canSearch==true)"><div class="panel panel-default default-panel"><div class="panel-heading">enumField<button ng-click="showEnumFieldDetail()" class="btn btn-default  pull-right" style="margin-top: -7px" ng-show="(restrictionList.enumField==undefined || restrictionList.enumField.canCreate==true)">Add new enumField</button><button type="button" class="btn btn-default pull-right" style="margin-top: -7px" data-toggle="modal" data-target="#entity-enumField">Link existing</button><button ng-click="downloadEnumFieldList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body" ng-class="{&#39;has-error&#39;: !entityDetailForm.enumField.$valid, &#39;has-success&#39;: entityDetailForm.enumField.$valid}"><label id="enumField">enumField</label><div id="enumField" ng-if="selectedEntity.enumFieldList.length&gt;0"><div style="top: 100px" ui-grid="enumFieldGridOptions" ui-grid-pagination="" ui-grid-selection=""></div></div></div></div></div><div id="entity-tab" class="modal fade" role="dialog"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button><h4 class="modal-title">Link existing tab</h4></div><div class="modal-body"><p></p><div class="input-group"><span class="input-group-addon">tab</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.tab" id="tab" name="tab" ng-options="tab as  tab.name+&#39; &#39;+ tab.tabId for tab in childrenList.tabList track by tab.tabId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="saveLinkedTab()" class="btn btn-default " id="tab">Save</button></span></div></div><div class="modal-footer"><button ng-click="close()" class="btn btn-default " data-dismiss="modal">close</button></div></div></div></div><br/><br/><div class="pull-left" style="width: 100%" ng-show="true  && (restrictionList.tab==undefined || restrictionList.tab.canSearch==true)"><div class="panel panel-default default-panel"><div class="panel-heading">tab<button ng-click="showTabDetail()" class="btn btn-default  pull-right" style="margin-top: -7px" ng-show="(restrictionList.tab==undefined || restrictionList.tab.canCreate==true)">Add new tab</button><button type="button" class="btn btn-default pull-right" style="margin-top: -7px" data-toggle="modal" data-target="#entity-tab">Link existing</button><button ng-click="downloadTabList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body" ng-class="{&#39;has-error&#39;: !entityDetailForm.tab.$valid, &#39;has-success&#39;: entityDetailForm.tab.$valid}"><label id="tab">tab</label><div id="tab" ng-if="selectedEntity.tabList.length&gt;0"><div style="top: 100px" ui-grid="tabGridOptions" ui-grid-pagination="" ui-grid-selection=""></div></div></div></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !entityDetailForm.entityGroup.$valid, &#39;has-success&#39;: entityDetailForm.entityGroup.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">entityGroup</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.entityGroup" id="entityGroup" name="entityGroup" ng-options="entityGroup as  entityGroup.name+&#39; &#39;+ entityGroup.entityGroupId for entityGroup in childrenList.entityGroupList track by entityGroup.entityGroupId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="showEntityGroupDetail()" class="btn btn-default " id="entityGroup" ng-if="selectedEntity.entityGroup==null">Add new entityGroup</button><button ng-click="showEntityGroupDetail()" class="btn btn-default " id="entityGroup" ng-if="selectedEntity.entityGroup!=null">Show detail</button></span></div></div><div id="entity-restrictionEntity" class="modal fade" role="dialog"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button><h4 class="modal-title">Link existing restrictionEntity</h4></div><div class="modal-body"><p></p><div class="input-group"><span class="input-group-addon">restrictionEntity</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.restrictionEntity" id="restrictionEntity" name="restrictionEntity" ng-options="restrictionEntity as  restrictionEntity.restrictionEntityId+&#39; &#39;+ restrictionEntity.entity.EntityId for restrictionEntity in childrenList.restrictionEntityList track by restrictionEntity.restrictionEntityId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="saveLinkedRestrictionEntity()" class="btn btn-default " id="restrictionEntity">Save</button></span></div></div><div class="modal-footer"><button ng-click="close()" class="btn btn-default " data-dismiss="modal">close</button></div></div></div></div><br/><br/><div class="pull-left" style="width: 100%" ng-show="true  && (restrictionList.restrictionEntity==undefined || restrictionList.restrictionEntity.canSearch==true)"><div class="panel panel-default default-panel"><div class="panel-heading">restrictionEntity<button ng-click="showRestrictionEntityDetail()" class="btn btn-default  pull-right" style="margin-top: -7px" ng-show="(restrictionList.restrictionEntity==undefined || restrictionList.restrictionEntity.canCreate==true)">Add new restrictionEntity</button><button type="button" class="btn btn-default pull-right" style="margin-top: -7px" data-toggle="modal" data-target="#entity-restrictionEntity">Link existing</button><button ng-click="downloadRestrictionEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body" ng-class="{&#39;has-error&#39;: !entityDetailForm.restrictionEntity.$valid, &#39;has-success&#39;: entityDetailForm.restrictionEntity.$valid}"><label id="restrictionEntity">restrictionEntity</label><div id="restrictionEntity" ng-if="selectedEntity.restrictionEntityList.length&gt;0"><div style="top: 100px" ui-grid="restrictionEntityGridOptions" ui-grid-pagination="" ui-grid-selection=""></div></div></div></div></div><div id="entity-relationship" class="modal fade" role="dialog"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button><h4 class="modal-title">Link existing relationship</h4></div><div class="modal-body"><p></p><div class="input-group"><span class="input-group-addon">relationship</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.relationship" id="relationship" name="relationship" ng-options="relationship as  relationship.name+&#39; &#39;+ relationship.relationshipId for relationship in childrenList.relationshipList track by relationship.relationshipId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="saveLinkedRelationship()" class="btn btn-default " id="relationship">Save</button></span></div></div><div class="modal-footer"><button ng-click="close()" class="btn btn-default " data-dismiss="modal">close</button></div></div></div></div><br/><br/><div class="pull-left" style="width: 100%" ng-show="true  && (restrictionList.relationship==undefined || restrictionList.relationship.canSearch==true)"><div class="panel panel-default default-panel"><div class="panel-heading">relationship<button ng-click="showRelationshipDetail()" class="btn btn-default  pull-right" style="margin-top: -7px" ng-show="(restrictionList.relationship==undefined || restrictionList.relationship.canCreate==true)">Add new relationship</button><button type="button" class="btn btn-default pull-right" style="margin-top: -7px" data-toggle="modal" data-target="#entity-relationship">Link existing</button><button ng-click="downloadRelationshipList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body" ng-class="{&#39;has-error&#39;: !entityDetailForm.relationship.$valid, &#39;has-success&#39;: entityDetailForm.relationship.$valid}"><label id="relationship">relationship</label><div id="relationship" ng-if="selectedEntity.relationshipList.length&gt;0"><div style="top: 100px" ui-grid="relationshipGridOptions" ui-grid-pagination="" ui-grid-selection=""></div></div></div></div></div></div></div></div><script type="text/javascript">$('#entityTabs a:first').tab('show');</script><div class="panel-body"><div class="pull-left"><form id="entityActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default " ng-if="selectedEntity.entityId==undefined" ng-show="(restrictionList.entity==undefined || restrictionList.entity.canCreate==true)">Insert</button><button ng-click="update()" class="btn btn-default " ng-if="selectedEntity.entityId&gt;0" ng-show="(restrictionList.entity==undefined || restrictionList.entity.canUpdate==true)">Update</button><button ng-click="del()" class="btn btn-default " ng-if="selectedEntity.entityId&gt;0" ng-show="(restrictionList.entity==undefined || restrictionList.entity.canDelete==true)">Delete</button><button ng-click="remove()" class="btn btn-default " ng-if="selectedEntity.entityId&gt;0" ng-show="(restrictionList.entity==undefined || restrictionList.entity.canDelete==true)">Remove</button></form></div></div></div></form></div>