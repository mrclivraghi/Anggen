<!DOCTYPE html><html><head><title>project</title><script type="text/javascript" src="../js/jquery-1.9.1.js"></script><script type="text/javascript" src="../js/jquery-ui.js"></script><script type="text/javascript" src="../js/angular.js"></script><script type="text/javascript" src="../js/angular-touch.js"></script><script type="text/javascript" src="../js/angular-animate.js"></script><script type="text/javascript" src="../js/csv.js"></script><script type="text/javascript" src="../js/pdfmake.js"></script><script type="text/javascript" src="../js/vfs_fonts.js"></script><script type="text/javascript" src="../js/ui-grid.js"></script><script type="text/javascript" src="../js/angular/project.js"></script><script type="text/javascript" src="../js/date.js"></script><script type="text/javascript" src="../js/utility.js"></script><script type="text/javascript" src="../js/jquery.easytree.js"></script><script type="text/javascript" src="../js/jquery.cookie.js"></script><script type="text/javascript" src="../js/bootstrap.min.js"></script><script type="text/javascript" src="../js/alasql.min.js"></script><link rel="stylesheet" href="../css/ui-grid.css"/><link rel="stylesheet" href="../css/bootstrap.min.css"/><link rel="stylesheet" href="../css/main.css"/><link rel="stylesheet" href="../css/jquery-ui.css"/><link rel="stylesheet" href="../css/easytree/skin-win8/ui.easytree.css"/><link rel="import" href="../generatedMenu.jsp"/></head><body ng-app="projectApp"><div ng-controller="projectController"><form id="projectSearchBean"><div class="panel panel-default default-panel"><div class="panel-heading">Search form project</div><div class="panel-body"><div class="pull-left right-input" ng-show="true "><div class="input-group"><span class="input-group-addon">name</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="project-name" ng-model="searchBean.name" ng-readonly="false" name="name" placeholder="name"/></div></div><div class="pull-left right-input" ng-show="true "><div class="input-group"><span class="input-group-addon">projectId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="project-projectId" ng-model="searchBean.projectId" ng-readonly="false" name="projectId" placeholder="projectId"/></div></div><div class="pull-left right-input" style="height: 59px;" ng-show="true  && (restrictionList.EntityGroupTest==undefined || restrictionList.EntityGroupTest.canSearch==true)"><div class="input-group"><span class="input-group-addon">EntityGroupTest</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="searchBean.EntityGroupTest.EntityGroupTestId" id="project_entity_group" ng-options="EntityGroupTest.EntityGroupTestId as EntityGroupTest.EntityGroupTestId for EntityGroupTest in childrenList.EntityGroupTestList" enctype="UTF-8"></select></div></div></div><div class="panel-body"><div class="panel-body"><div class="pull-left right-input"><button ng-click="addNew()" class="btn btn-default " ng-show="(restrictionList.project==undefined || restrictionList.project.canCreate==true)">Add new</button><button ng-click="search()" class="btn btn-default ">Find</button><button ng-click="reset()" class="btn btn-default ">Reset</button></div></div></div></div></form><form id="projectList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List project<button ng-click="downloadEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body"><div ui-grid="projectGridOptions" ui-grid-pagination="" ui-grid-selection="" ui-grid-exporter=""></div></div></div></form><form id="projectDetailForm" name="projectDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail project {{ selectedEntity.projectId }}</div><div class="panel-body"><ul class="nav nav-tabs" role="tablist" id="projectTabs"><li role="presentation"><a href="#project-Searchdetails" aria-controls="Searchdetails" role="tab" data-toggle="tab" ng-click="refreshTableSearchdetails()">Searchdetails</a></li><script type="text/javascript">$('a[href="#project-Searchdetails"]').on('shown.bs.tab', function (e) {
var target = $(e.target).attr("href"); // activated tab
//console.log(target);
if (angular.element($('#projectTabs')).scope()!=null && angular.element($('#projectTabs')).scope()!=undefined) 
angular.element($('#projectTabs')).scope().refreshTableSearchdetails();
});
</script></ul><div class="tab-content"><div role="tabpanel" class="tab-pane fade" id="project-Searchdetails"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !projectDetailForm.name.$valid, &#39;has-success&#39;: projectDetailForm.name.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">name</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="project-name" ng-model="selectedEntity.name" ng-readonly="restrictionList.project.restrictionFieldMap.name" name="name" placeholder="name"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !projectDetailForm.projectId.$valid, &#39;has-success&#39;: projectDetailForm.projectId.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">projectId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="project-projectId" ng-model="selectedEntity.projectId" ng-readonly="true" name="projectId" placeholder="projectId"/></div></div><div id="project-EntityGroupTest" class="modal fade" role="dialog"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button><h4 class="modal-title">Link existing EntityGroupTest</h4></div><div class="modal-body"><p></p><div class="input-group"><span class="input-group-addon">EntityGroupTest</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.EntityGroupTest" id="EntityGroupTest" name="EntityGroupTest" ng-options="EntityGroupTest as EntityGroupTest.EntityGroupTestId for EntityGroupTest in childrenList.EntityGroupTestList track by EntityGroupTest.EntityGroupTestId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="saveLinkedEntityGroupTest()" class="btn btn-default " id="EntityGroupTest">Save</button></span></div></div><div class="modal-footer"><button ng-click="close()" class="btn btn-default " data-dismiss="modal">close</button></div></div></div></div><br/><br/><div class="pull-left" style="width: 100%" ng-show="true  && (restrictionList.EntityGroupTest==undefined || restrictionList.EntityGroupTest.canSearch==true)"><div class="panel panel-default default-panel"><div class="panel-heading">EntityGroupTest<button ng-click="showEntityGroupTestDetail()" class="btn btn-default  pull-right" style="margin-top: -7px" ng-show="(restrictionList.EntityGroupTest==undefined || restrictionList.EntityGroupTest.canCreate==true)">Add new EntityGroupTest</button><button type="button" class="btn btn-default pull-right" style="margin-top: -7px" data-toggle="modal" data-target="#project-EntityGroupTest">Link existing</button><button ng-click="downloadEntityGroupTestList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body" ng-class="{&#39;has-error&#39;: !projectDetailForm.project_entity_group.$valid, &#39;has-success&#39;: projectDetailForm.EntityGroupTest.$valid}"><label id="EntityGroupTest">project_entity_group</label><div id="EntityGroupTest" ng-if="selectedEntity.EntityGroupTestList.length&gt;0"><div style="top: 100px" ui-grid="EntityGroupTestListGridOptions" ui-grid-pagination="" ui-grid-selection=""></div></div></div></div></div></div></div></div><script type="text/javascript">$('#projectTabs a:first').tab('show');</script><div class="panel-body"><div class="pull-left"><form id="projectActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default " ng-if="selectedEntity.projectId==undefined" ng-show="(restrictionList.project==undefined || restrictionList.project.canCreate==true)">Insert</button><button ng-click="update()" class="btn btn-default " ng-if="selectedEntity.projectId&gt;0" ng-show="(restrictionList.project==undefined || restrictionList.project.canUpdate==true)">Update</button><button ng-click="del()" class="btn btn-default " ng-if="selectedEntity.projectId&gt;0" ng-show="(restrictionList.project==undefined || restrictionList.project.canDelete==true)">Delete</button></form></div></div></div></form></div><div ng-controller="EntityGroupTestController"><form id="EntityGroupTestList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List EntityGroupTest<button ng-click="downloadEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body"><div ui-grid="EntityGroupTestGridOptions" ui-grid-pagination="" ui-grid-selection="" ui-grid-exporter=""></div></div></div></form><form id="EntityGroupTestDetailForm" name="EntityGroupTestDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail EntityGroupTest {{ selectedEntity.EntityGroupTestId }}</div><div class="panel-body"><ul class="nav nav-tabs" role="tablist" id="EntityGroupTestTabs"><li role="presentation"><a href="#EntityGroupTest-Searchdetails" aria-controls="Searchdetails" role="tab" data-toggle="tab" ng-click="refreshTableSearchdetails()">Searchdetails</a></li><script type="text/javascript">$('a[href="#EntityGroupTest-Searchdetails"]').on('shown.bs.tab', function (e) {
var target = $(e.target).attr("href"); // activated tab
//console.log(target);
if (angular.element($('#EntityGroupTestTabs')).scope()!=null && angular.element($('#EntityGroupTestTabs')).scope()!=undefined) 
angular.element($('#EntityGroupTestTabs')).scope().refreshTableSearchdetails();
});
</script></ul><div class="tab-content"><div role="tabpanel" class="tab-pane fade" id="EntityGroupTest-Searchdetails"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !EntityGroupTestDetailForm.entityGroupTestId.$valid, &#39;has-success&#39;: EntityGroupTestDetailForm.entityGroupTestId.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">entityGroupTestId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="EntityGroupTest-entityGroupTestId" ng-model="selectedEntity.entityGroupTestId" ng-readonly="restrictionList.EntityGroupTest.restrictionFieldMap.entityGroupTestId" name="entityGroupTestId" placeholder="entityGroupTestId"/></div></div></div></div></div><script type="text/javascript">$('#EntityGroupTestTabs a:first').tab('show');</script><div class="panel-body"><div class="pull-left"><form id="EntityGroupTestActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default " ng-if="selectedEntity.EntityGroupTestId==undefined" ng-show="(restrictionList.EntityGroupTest==undefined || restrictionList.EntityGroupTest.canCreate==true)">Insert</button><button ng-click="update()" class="btn btn-default " ng-if="selectedEntity.EntityGroupTestId&gt;0" ng-show="(restrictionList.EntityGroupTest==undefined || restrictionList.EntityGroupTest.canUpdate==true)">Update</button><button ng-click="del()" class="btn btn-default " ng-if="selectedEntity.EntityGroupTestId&gt;0" ng-show="(restrictionList.EntityGroupTest==undefined || restrictionList.EntityGroupTest.canDelete==true)">Delete</button><button ng-click="remove()" class="btn btn-default " ng-if="selectedEntity.EntityGroupTestId&gt;0" ng-show="(restrictionList.EntityGroupTest==undefined || restrictionList.EntityGroupTest.canDelete==true)">Remove</button></form></div></div></div></form></div><script type="text/javascript">loadMenu(); </script></body></html>