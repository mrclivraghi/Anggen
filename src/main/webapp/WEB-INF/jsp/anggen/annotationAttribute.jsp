<!DOCTYPE html><html><head><title>annotationAttribute</title><script type="text/javascript" src="../js/jquery-1.9.1.js"></script><script type="text/javascript" src="../js/jquery-ui.js"></script><script type="text/javascript" src="../js/angular.js"></script><script type="text/javascript" src="../js/angular-touch.js"></script><script type="text/javascript" src="../js/angular-animate.js"></script><script type="text/javascript" src="../js/csv.js"></script><script type="text/javascript" src="../js/pdfmake.js"></script><script type="text/javascript" src="../js/vfs_fonts.js"></script><script type="text/javascript" src="../js/ui-grid.js"></script><script type="text/javascript" src="../js/angular/anggen/annotationAttribute.js"></script><script type="text/javascript" src="../js/date.js"></script><script type="text/javascript" src="../js/utility.js"></script><script type="text/javascript" src="../js/jquery.easytree.js"></script><script type="text/javascript" src="../js/jquery.cookie.js"></script><script type="text/javascript" src="../js/bootstrap.min.js"></script><script type="text/javascript" src="../js/alasql.min.js"></script><link rel="stylesheet" href="../css/ui-grid.css"/><link rel="stylesheet" href="../css/bootstrap.min.css"/><link rel="stylesheet" href="../css/main.css"/><link rel="stylesheet" href="../css/jquery-ui.css"/><link rel="stylesheet" href="../css/easytree/skin-win8/ui.easytree.css"/><link rel="import" href="../anggenMenu.jsp"/></head><body ng-app="annotationAttributeApp"><div ng-controller="annotationAttributeController"><form id="annotationAttributeSearchBean"><div class="panel panel-default default-panel"><div class="panel-heading">Search form annotationAttribute</div><div class="panel-body"><div class="pull-left right-input" ng-show="true "><div class="input-group"><span class="input-group-addon">value</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="annotationAttribute-value" ng-model="searchBean.value" ng-readonly="false" name="value" placeholder="value"/></div></div><div class="pull-left right-input" ng-show="true "><div class="input-group"><span class="input-group-addon">property</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="annotationAttribute-property" ng-model="searchBean.property" ng-readonly="false" name="property" placeholder="property"/></div></div><div class="pull-left right-input" ng-show="true "><div class="input-group"><span class="input-group-addon">annotationAttributeId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="annotationAttribute-annotationAttributeId" ng-model="searchBean.annotationAttributeId" ng-readonly="false" name="annotationAttributeId" placeholder="annotationAttributeId"/></div></div><div class="pull-left right-input" style="height: 59px;" ng-show="true  && (restrictionList.annotation==undefined || restrictionList.annotation.canSearch==true)"><div class="input-group"><span class="input-group-addon">annotation</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="searchBean.annotation.annotationId" id="annotation" ng-options="annotation.annotationId as annotation.annotationId for annotation in childrenList.annotationList" enctype="UTF-8"></select></div></div></div><div class="panel-body"><div class="panel-body"><div class="pull-left right-input"><button ng-click="addNew()" class="btn btn-default " ng-show="(restrictionList.annotationAttribute==undefined || restrictionList.annotationAttribute.canCreate==true)">Add new</button><button ng-click="search()" class="btn btn-default ">Find</button><button ng-click="reset()" class="btn btn-default ">Reset</button></div></div></div></div></form><form id="annotationAttributeList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List annotationAttribute<button ng-click="downloadEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body"><div ui-grid="annotationAttributeGridOptions" ui-grid-pagination="" ui-grid-selection="" ui-grid-exporter=""></div></div></div></form><form id="annotationAttributeDetailForm" name="annotationAttributeDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail annotationAttribute {{ selectedEntity.annotationAttributeId }}</div><div class="panel-body"><ul class="nav nav-tabs" role="tablist" id="annotationAttributeTabs"><li role="presentation"><a href="#annotationAttribute-Detail" aria-controls="Detail" role="tab" data-toggle="tab" ng-click="refreshTableDetail()">Detail</a></li><script type="text/javascript">$('a[href="#annotationAttribute-Detail"]').on('shown.bs.tab', function (e) {
var target = $(e.target).attr("href"); // activated tab
//console.log(target);
if (angular.element($('#annotationAttributeTabs')).scope()!=null && angular.element($('#annotationAttributeTabs')).scope()!=undefined) 
angular.element($('#annotationAttributeTabs')).scope().refreshTableDetail();
});
</script></ul><div class="tab-content"><div role="tabpanel" class="tab-pane fade" id="annotationAttribute-Detail"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !annotationAttributeDetailForm.value.$valid, &#39;has-success&#39;: annotationAttributeDetailForm.value.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">value</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="annotationAttribute-value" ng-model="selectedEntity.value" ng-readonly="restrictionList.annotationAttribute.restrictionFieldMap.value" name="value" placeholder="value"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !annotationAttributeDetailForm.property.$valid, &#39;has-success&#39;: annotationAttributeDetailForm.property.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">property</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="annotationAttribute-property" ng-model="selectedEntity.property" ng-readonly="restrictionList.annotationAttribute.restrictionFieldMap.property" name="property" placeholder="property"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !annotationAttributeDetailForm.annotationAttributeId.$valid, &#39;has-success&#39;: annotationAttributeDetailForm.annotationAttributeId.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">annotationAttributeId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="annotationAttribute-annotationAttributeId" ng-model="selectedEntity.annotationAttributeId" ng-readonly="true" name="annotationAttributeId" placeholder="annotationAttributeId"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !annotationAttributeDetailForm.annotation.$valid, &#39;has-success&#39;: annotationAttributeDetailForm.annotation.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">annotation</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.annotation" id="annotation" name="annotation" ng-options="annotation as annotation.annotationId for annotation in childrenList.annotationList track by annotation.annotationId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="showAnnotationDetail()" class="btn btn-default " id="annotation" ng-if="selectedEntity.annotation==null">Add new annotation</button><button ng-click="showAnnotationDetail()" class="btn btn-default " id="annotation" ng-if="selectedEntity.annotation!=null">Show detail</button></span></div></div></div></div></div><script type="text/javascript">$('#annotationAttributeTabs a:first').tab('show');</script><div class="panel-body"><div class="pull-left"><form id="annotationAttributeActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default " ng-if="selectedEntity.annotationAttributeId==undefined" ng-show="(restrictionList.annotationAttribute==undefined || restrictionList.annotationAttribute.canCreate==true)">Insert</button><button ng-click="update()" class="btn btn-default " ng-if="selectedEntity.annotationAttributeId&gt;0" ng-show="(restrictionList.annotationAttribute==undefined || restrictionList.annotationAttribute.canUpdate==true)">Update</button><button ng-click="del()" class="btn btn-default " ng-if="selectedEntity.annotationAttributeId&gt;0" ng-show="(restrictionList.annotationAttribute==undefined || restrictionList.annotationAttribute.canDelete==true)">Delete</button></form></div></div></div></form></div><div ng-controller="annotationController"><form id="annotationList" ng-if="entityList.length&gt;0" enctype="UTF-8"><div class="panel panel-default default-panel"><div class="panel-heading">List annotation<button ng-click="downloadEntityList()" class="btn btn-default pull-right" style="margin-top:-7px"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span></button></div><div class="panel-body"><div ui-grid="annotationGridOptions" ui-grid-pagination="" ui-grid-selection="" ui-grid-exporter=""></div></div></div></form><form id="annotationDetailForm" name="annotationDetailForm" ng-show="selectedEntity.show"><div class="panel panel-default default-panel"><div class="panel-heading">Detail annotation {{ selectedEntity.annotationId }}</div><div class="panel-body"><ul class="nav nav-tabs" role="tablist" id="annotationTabs"><li role="presentation"><a href="#annotation-Detail" aria-controls="Detail" role="tab" data-toggle="tab" ng-click="refreshTableDetail()">Detail</a></li><script type="text/javascript">$('a[href="#annotation-Detail"]').on('shown.bs.tab', function (e) {
var target = $(e.target).attr("href"); // activated tab
//console.log(target);
if (angular.element($('#annotationTabs')).scope()!=null && angular.element($('#annotationTabs')).scope()!=undefined) 
angular.element($('#annotationTabs')).scope().refreshTableDetail();
});
</script></ul><div class="tab-content"><div role="tabpanel" class="tab-pane fade" id="annotation-Detail"><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !annotationDetailForm.annotationId.$valid, &#39;has-success&#39;: annotationDetailForm.annotationId.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">annotationId</span><input class="form-control " aria-describedby="sizing-addon3" type="text" id="annotation-annotationId" ng-model="selectedEntity.annotationId" ng-readonly="true" name="annotationId" placeholder="annotationId"/></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !annotationDetailForm.enumField.$valid, &#39;has-success&#39;: annotationDetailForm.enumField.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">enumField</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.enumField" id="enumField" name="enumField" ng-options="enumField as enumField.enumFieldId for enumField in childrenList.enumFieldList track by enumField.enumFieldId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="showEnumFieldDetail()" class="btn btn-default " id="enumField" ng-if="selectedEntity.enumField==null">Add new enumField</button><button ng-click="showEnumFieldDetail()" class="btn btn-default " id="enumField" ng-if="selectedEntity.enumField!=null">Show detail</button></span></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !annotationDetailForm.relationship.$valid, &#39;has-success&#39;: annotationDetailForm.relationship.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">relationship</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.relationship" id="relationship" name="relationship" ng-options="relationship as relationship.relationshipId for relationship in childrenList.relationshipList track by relationship.relationshipId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="showRelationshipDetail()" class="btn btn-default " id="relationship" ng-if="selectedEntity.relationship==null">Add new relationship</button><button ng-click="showRelationshipDetail()" class="btn btn-default " id="relationship" ng-if="selectedEntity.relationship!=null">Show detail</button></span></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !annotationDetailForm.field.$valid, &#39;has-success&#39;: annotationDetailForm.field.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">field</span><select class="form-control " aria-describedby="sizing-addon3" ng-model="selectedEntity.field" id="field" name="field" ng-options="field as field.fieldId for field in childrenList.fieldList track by field.fieldId" enctype="UTF-8"></select><span class="input-group-btn"><button ng-click="showFieldDetail()" class="btn btn-default " id="field" ng-if="selectedEntity.field==null">Add new field</button><button ng-click="showFieldDetail()" class="btn btn-default " id="field" ng-if="selectedEntity.field!=null">Show detail</button></span></div></div><div class="pull-left right-input" ng-class="{&#39;has-error&#39;: !annotationDetailForm.annotationType.$valid, &#39;has-success&#39;: annotationDetailForm.annotationType.$valid}" style="height:59px" ng-show="true "><div class="input-group"><span class="input-group-addon">annotationType</span><select class="form-control pull-left" aria-describedby="sizing-addon3" type="text" id="annotation-annotationType" ng-model="selectedEntity.annotationType" ng-readonly="restrictionList.annotation.restrictionFieldMap.annotationType" name="annotationType" placeholder="annotationType" ng-options="annotationType as annotationType for annotationType in childrenList.annotationTypeList" enctype="UTF-8"></select></div></div></div></div></div><script type="text/javascript">$('#annotationTabs a:first').tab('show');</script><div class="panel-body"><div class="pull-left"><form id="annotationActionButton" ng-if="selectedEntity.show"><button ng-click="insert()" class="btn btn-default " ng-if="selectedEntity.annotationId==undefined" ng-show="(restrictionList.annotation==undefined || restrictionList.annotation.canCreate==true)">Insert</button><button ng-click="update()" class="btn btn-default " ng-if="selectedEntity.annotationId&gt;0" ng-show="(restrictionList.annotation==undefined || restrictionList.annotation.canUpdate==true)">Update</button><button ng-click="del()" class="btn btn-default " ng-if="selectedEntity.annotationId&gt;0" ng-show="(restrictionList.annotation==undefined || restrictionList.annotation.canDelete==true)">Delete</button><button ng-click="remove()" class="btn btn-default " ng-if="selectedEntity.annotationId&gt;0" ng-show="(restrictionList.annotation==undefined || restrictionList.annotation.canDelete==true)">Remove</button></form></div></div></div></form></div><script type="text/javascript">loadMenu(); </script></body></html>