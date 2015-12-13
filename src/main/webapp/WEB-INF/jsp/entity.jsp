<!DOCTYPE html>
<html>
<head>
<title>entity</title>
<script type="text/javascript" src="../js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../js/jquery-ui.js"></script>
<script type="text/javascript" src="../js/angular.js"></script>
<script type="text/javascript" src="../js/angular-touch.js"></script>
<script type="text/javascript" src="../js/angular-animate.js"></script>
<script type="text/javascript" src="../js/csv.js"></script>
<script type="text/javascript" src="../js/pdfmake.js"></script>
<script type="text/javascript" src="../js/vfs_fonts.js"></script>
<script type="text/javascript" src="../js/ui-grid.js"></script>
<script type="text/javascript" src="../js/angular/anggen/entity.js"></script>
<script type="text/javascript" src="../js/date.js"></script>
<script type="text/javascript" src="../js/utility.js"></script>
<script type="text/javascript" src="../js/jquery.easytree.js"></script>
<script type="text/javascript" src="../js/jquery.cookie.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/alasql.min.js"></script>
<script type="text/javascript" src="../js/ng-file-upload-all.js"></script>
<link rel="stylesheet" href="../css/ui-grid.css" />
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/main.css" />
<link rel="stylesheet" href="../css/jquery-ui.css" />
<link rel="stylesheet" href="../css/easytree/skin-win8/ui.easytree.css" />
<link rel="import" href="../anggenMenu.jsp" />
</head>
<body ng-app="entityApp">
<div id="alertInfo" class="alert alert-success custom-alert" style="display: none;">
        <span>Hello world callback</span>
    </div>
    <div id="alertError" class="alert alert-danger custom-alert" style="display: none;">
        <span>Hello world callback</span>
    </div>
	<div ng-controller="entityController">
		<form id="entitySearchBean">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Search form entity</div>
				<div class="panel-body">
					<div class="pull-left right-input" ng-show="true ">
						<div class="input-group">
							<span class="input-group-addon">entityId</span><input
								class="form-control " aria-describedby="sizing-addon3"
								type="text" id="entity-entityId" ng-model="searchBean.entityId"
								ng-readonly="false" name="entityId" placeholder="entityId" />
						</div>
					</div>
					<div class="pull-left right-input" ng-show="true ">
						<div class="input-group">
							<span class="input-group-addon">descendantMaxLevel</span><input
								class="form-control " aria-describedby="sizing-addon3"
								type="text" id="entity-descendantMaxLevel"
								ng-model="searchBean.descendantMaxLevel" ng-readonly="false"
								name="descendantMaxLevel" placeholder="descendantMaxLevel" />
						</div>
					</div>
					<div class="pull-left right-input" ng-show="true ">
						<div class="input-group">
							<span class="input-group-addon">name</span><input
								class="form-control " aria-describedby="sizing-addon3"
								type="text" id="entity-name" ng-model="searchBean.name"
								ng-readonly="false" name="name" placeholder="name" />
						</div>
					</div>
					<div class="pull-left right-input" ng-show="true ">
						<div class="input-group">
							<span class="input-group-addon">securityType</span><select
								class="form-control pull-left" aria-describedby="sizing-addon3"
								type="text" id="entity-securityType"
								ng-model="searchBean.securityType" ng-readonly="false"
								name="securityType" placeholder="securityType"
								ng-options="securityType as securityType for securityType in childrenList.securityTypeList"
								enctype="UTF-8"></select>
						</div>
					</div>
					<div class="pull-left right-input" style="height: 59px;"
						ng-show="true  && (restrictionList.relationship==undefined || restrictionList.relationship.canSearch==true)">
						<div class="input-group">
							<span class="input-group-addon">relationship</span><select
								class="form-control " aria-describedby="sizing-addon3"
								ng-model="searchBean.relationship.relationshipId"
								id="relationship"
								ng-options="relationship.relationshipId as  relationship.relationshipId+&#39; &#39;+ relationship.name for relationship in childrenList.relationshipList"
								enctype="UTF-8"></select>
						</div>
					</div>
					<div class="pull-left right-input" style="height: 59px;"
						ng-show="true  && (restrictionList.enumField==undefined || restrictionList.enumField.canSearch==true)">
						<div class="input-group">
							<span class="input-group-addon">enumField</span><select
								class="form-control " aria-describedby="sizing-addon3"
								ng-model="searchBean.enumField.enumFieldId" id="enumField"
								ng-options="enumField.enumFieldId as  enumField.name+&#39; &#39;+ enumField.enumFieldId for enumField in childrenList.enumFieldList"
								enctype="UTF-8"></select>
						</div>
					</div>
					<div class="pull-left right-input" style="height: 59px;"
						ng-show="true  && (restrictionList.tab==undefined || restrictionList.tab.canSearch==true)">
						<div class="input-group">
							<span class="input-group-addon">tab</span><select
								class="form-control " aria-describedby="sizing-addon3"
								ng-model="searchBean.tab.tabId" id="tab"
								ng-options="tab.tabId as  tab.tabId+&#39; &#39;+ tab.name for tab in childrenList.tabList"
								enctype="UTF-8"></select>
						</div>
					</div>
					<div class="pull-left right-input" style="height: 59px;"
						ng-show="true  && (restrictionList.restrictionEntity==undefined || restrictionList.restrictionEntity.canSearch==true)">
						<div class="input-group">
							<span class="input-group-addon">restrictionEntity</span><select
								class="form-control " aria-describedby="sizing-addon3"
								ng-model="searchBean.restrictionEntity.restrictionEntityId"
								id="restrictionEntity"
								ng-options="restrictionEntity.restrictionEntityId as  restrictionEntity.restrictionEntityId+&#39; &#39;+ restrictionEntity.entity for restrictionEntity in childrenList.restrictionEntityList"
								enctype="UTF-8"></select>
						</div>
					</div>
					<div class="pull-left right-input" style="height: 59px;"
						ng-show="true  && (restrictionList.entityGroup==undefined || restrictionList.entityGroup.canSearch==true)">
						<div class="input-group">
							<span class="input-group-addon">entityGroup</span><select
								class="form-control " aria-describedby="sizing-addon3"
								ng-model="searchBean.entityGroup.entityGroupId" id="entityGroup"
								ng-options="entityGroup.entityGroupId as  entityGroup.entityGroupId+&#39; &#39;+ entityGroup.name for entityGroup in childrenList.entityGroupList"
								enctype="UTF-8"></select>
						</div>
					</div>
					<div class="pull-left right-input" style="height: 59px;"
						ng-show="true  && (restrictionList.field==undefined || restrictionList.field.canSearch==true)">
						<div class="input-group">
							<span class="input-group-addon">field</span><select
								class="form-control " aria-describedby="sizing-addon3"
								ng-model="searchBean.field.fieldId" id="field"
								ng-options="field.fieldId as  field.name+&#39; &#39;+ field.fieldId for field in childrenList.fieldList"
								enctype="UTF-8"></select>
						</div>
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-body">
						<div class="pull-left right-input">
							<button ng-click="addNew()" class="btn btn-default "
								ng-show="(restrictionList.entity==undefined || restrictionList.entity.canCreate==true)">Add
								new</button>
							<button ng-click="search()" class="btn btn-default ">Find</button>
							<button ng-click="reset()" class="btn btn-default ">Reset</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<form id="entityList" ng-if="entityList.length&gt;0" enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">
					List entity
					<button ng-click="downloadEntityList()"
						class="btn btn-default pull-right" style="margin-top: -7px">
						<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
					</button>
				</div>
				<div class="panel-body">
					<div ui-grid="entityGridOptions" ui-grid-pagination=""
						ui-grid-selection="" ui-grid-exporter=""></div>
				</div>
			</div>
		</form>
		<form id="entityDetailForm" name="entityDetailForm"
			ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail entity {{
					selectedEntity.entityId }}</div>
				<div class="panel-body">
					<ul class="nav nav-tabs" role="tablist" id="entityTabs">
						<li role="presentation"><a href="#entity-Detail"
							aria-controls="Detail" role="tab" data-toggle="tab"
							ng-click="refreshTableDetail()">Detail</a></li>
						<script type="text/javascript">
							$('a[href="#entity-Detail"]').on(
									'shown.bs.tab',
									function(e) {
										var target = $(e.target).attr("href"); // activated tab
										//console.log(target);
										if (angular.element($('#entityTabs'))
												.scope() != null
												&& angular.element(
														$('#entityTabs'))
														.scope() != undefined)
											angular.element($('#entityTabs'))
													.scope()
													.refreshTableDetail();
									});
						</script>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade" id="entity-Detail">
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !entityDetailForm.entityId.$valid, &#39;has-success&#39;: entityDetailForm.entityId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">entityId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="entity-entityId"
										ng-model="selectedEntity.entityId" ng-readonly="true"
										name="entityId" placeholder="entityId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !entityDetailForm.name.$valid, &#39;has-success&#39;: entityDetailForm.name.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">name</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="entity-name" ng-model="selectedEntity.name"
										ng-readonly="restrictionList.entity.restrictionFieldMap.name"
										name="name" placeholder="name" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !entityDetailForm.descendantMaxLevel.$valid, &#39;has-success&#39;: entityDetailForm.descendantMaxLevel.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">descendantMaxLevel</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="entity-descendantMaxLevel"
										ng-model="selectedEntity.descendantMaxLevel"
										ng-readonly="restrictionList.entity.restrictionFieldMap.descendantMaxLevel"
										name="descendantMaxLevel" placeholder="descendantMaxLevel" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !entityDetailForm.securityType.$valid, &#39;has-success&#39;: entityDetailForm.securityType.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">securityType</span><select
										class="form-control pull-left"
										aria-describedby="sizing-addon3" type="text"
										id="entity-securityType"
										ng-model="selectedEntity.securityType"
										ng-readonly="restrictionList.entity.restrictionFieldMap.securityType"
										name="securityType" placeholder="securityType"
										ng-options="securityType as securityType for securityType in childrenList.securityTypeList"
										enctype="UTF-8"></select>
								</div>
							</div>
							<div id="entity-relationship" class="modal fade" role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Link existing relationship</h4>
										</div>
										<div class="modal-body">
											<p></p>
											<div class="input-group">
												<span class="input-group-addon">relationship</span><select
													class="form-control " aria-describedby="sizing-addon3"
													ng-model="selectedEntity.relationship" id="relationship"
													name="relationship"
													ng-options="relationship as  relationship.relationshipId+&#39; &#39;+ relationship.name for relationship in childrenList.relationshipList track by relationship.relationshipId"
													enctype="UTF-8"></select><span class="input-group-btn"><button
														ng-click="saveLinkedRelationship()"
														class="btn btn-default " id="relationship">Save</button></span>
											</div>
										</div>
										<div class="modal-footer">
											<button ng-click="close()" class="btn btn-default "
												data-dismiss="modal">close</button>
										</div>
									</div>
								</div>
							</div>
							<br />
							<br />
							<div class="pull-left" style="width: 100%"
								ng-show="true  && (restrictionList.relationship==undefined || restrictionList.relationship.canSearch==true)">
								<div class="panel panel-default default-panel">
									<div class="panel-heading">
										relationship
										<button ng-click="showRelationshipDetail()"
											class="btn btn-default  pull-right" style="margin-top: -7px"
											ng-show="(restrictionList.relationship==undefined || restrictionList.relationship.canCreate==true)">Add
											new relationship</button>
										<button type="button" class="btn btn-default pull-right"
											style="margin-top: -7px" data-toggle="modal"
											data-target="#entity-relationship">Link existing</button>
										<button ng-click="downloadRelationshipList()"
											class="btn btn-default pull-right" style="margin-top: -7px">
											<span class="glyphicon glyphicon-download-alt"
												aria-hidden="true"></span>
										</button>
									</div>
									<div class="panel-body"
										ng-class="{&#39;has-error&#39;: !entityDetailForm.relationship.$valid, &#39;has-success&#39;: entityDetailForm.relationship.$valid}">
										<label id="relationship">relationship</label>
										<div id="relationship"
											ng-if="selectedEntity.relationshipList.length&gt;0">
											<div style="top: 100px" ui-grid="relationshipListGridOptions"
												ui-grid-pagination="" ui-grid-selection=""></div>
										</div>
									</div>
								</div>
							</div>
							<div id="entity-enumField" class="modal fade" role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Link existing enumField</h4>
										</div>
										<div class="modal-body">
											<p></p>
											<div class="input-group">
												<span class="input-group-addon">enumField</span><select
													class="form-control " aria-describedby="sizing-addon3"
													ng-model="selectedEntity.enumField" id="enumField"
													name="enumField"
													ng-options="enumField as  enumField.name+&#39; &#39;+ enumField.enumFieldId for enumField in childrenList.enumFieldList track by enumField.enumFieldId"
													enctype="UTF-8"></select><span class="input-group-btn"><button
														ng-click="saveLinkedEnumField()" class="btn btn-default "
														id="enumField">Save</button></span>
											</div>
										</div>
										<div class="modal-footer">
											<button ng-click="close()" class="btn btn-default "
												data-dismiss="modal">close</button>
										</div>
									</div>
								</div>
							</div>
							<br />
							<br />
							<div class="pull-left" style="width: 100%"
								ng-show="true  && (restrictionList.enumField==undefined || restrictionList.enumField.canSearch==true)">
								<div class="panel panel-default default-panel">
									<div class="panel-heading">
										enumField
										<button ng-click="showEnumFieldDetail()"
											class="btn btn-default  pull-right" style="margin-top: -7px"
											ng-show="(restrictionList.enumField==undefined || restrictionList.enumField.canCreate==true)">Add
											new enumField</button>
										<button type="button" class="btn btn-default pull-right"
											style="margin-top: -7px" data-toggle="modal"
											data-target="#entity-enumField">Link existing</button>
										<button ng-click="downloadEnumFieldList()"
											class="btn btn-default pull-right" style="margin-top: -7px">
											<span class="glyphicon glyphicon-download-alt"
												aria-hidden="true"></span>
										</button>
									</div>
									<div class="panel-body"
										ng-class="{&#39;has-error&#39;: !entityDetailForm.enumField.$valid, &#39;has-success&#39;: entityDetailForm.enumField.$valid}">
										<label id="enumField">enumField</label>
										<div id="enumField"
											ng-if="selectedEntity.enumFieldList.length&gt;0">
											<div style="top: 100px" ui-grid="enumFieldListGridOptions"
												ui-grid-pagination="" ui-grid-selection=""></div>
										</div>
									</div>
								</div>
							</div>
							<div id="entity-tab" class="modal fade" role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Link existing tab</h4>
										</div>
										<div class="modal-body">
											<p></p>
											<div class="input-group">
												<span class="input-group-addon">tab</span><select
													class="form-control " aria-describedby="sizing-addon3"
													ng-model="selectedEntity.tab" id="tab" name="tab"
													ng-options="tab as  tab.tabId+&#39; &#39;+ tab.name for tab in childrenList.tabList track by tab.tabId"
													enctype="UTF-8"></select><span class="input-group-btn"><button
														ng-click="saveLinkedTab()" class="btn btn-default "
														id="tab">Save</button></span>
											</div>
										</div>
										<div class="modal-footer">
											<button ng-click="close()" class="btn btn-default "
												data-dismiss="modal">close</button>
										</div>
									</div>
								</div>
							</div>
							<br />
							<br />
							<div class="pull-left" style="width: 100%"
								ng-show="true  && (restrictionList.tab==undefined || restrictionList.tab.canSearch==true)">
								<div class="panel panel-default default-panel">
									<div class="panel-heading">
										tab
										<button ng-click="showTabDetail()"
											class="btn btn-default  pull-right" style="margin-top: -7px"
											ng-show="(restrictionList.tab==undefined || restrictionList.tab.canCreate==true)">Add
											new tab</button>
										<button type="button" class="btn btn-default pull-right"
											style="margin-top: -7px" data-toggle="modal"
											data-target="#entity-tab">Link existing</button>
										<button ng-click="downloadTabList()"
											class="btn btn-default pull-right" style="margin-top: -7px">
											<span class="glyphicon glyphicon-download-alt"
												aria-hidden="true"></span>
										</button>
									</div>
									<div class="panel-body"
										ng-class="{&#39;has-error&#39;: !entityDetailForm.tab.$valid, &#39;has-success&#39;: entityDetailForm.tab.$valid}">
										<label id="tab">tab</label>
										<div id="tab" ng-if="selectedEntity.tabList.length&gt;0">
											<div style="top: 100px" ui-grid="tabListGridOptions"
												ui-grid-pagination="" ui-grid-selection=""></div>
										</div>
									</div>
								</div>
							</div>
							<div id="entity-restrictionEntity" class="modal fade"
								role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Link existing restrictionEntity</h4>
										</div>
										<div class="modal-body">
											<p></p>
											<div class="input-group">
												<span class="input-group-addon">restrictionEntity</span><select
													class="form-control " aria-describedby="sizing-addon3"
													ng-model="selectedEntity.restrictionEntity"
													id="restrictionEntity" name="restrictionEntity"
													ng-options="restrictionEntity as  restrictionEntity.restrictionEntityId+&#39; &#39;+ restrictionEntity.entity for restrictionEntity in childrenList.restrictionEntityList track by restrictionEntity.restrictionEntityId"
													enctype="UTF-8"></select><span class="input-group-btn"><button
														ng-click="saveLinkedRestrictionEntity()"
														class="btn btn-default " id="restrictionEntity">Save</button></span>
											</div>
										</div>
										<div class="modal-footer">
											<button ng-click="close()" class="btn btn-default "
												data-dismiss="modal">close</button>
										</div>
									</div>
								</div>
							</div>
							<br />
							<br />
							<div class="pull-left" style="width: 100%"
								ng-show="true  && (restrictionList.restrictionEntity==undefined || restrictionList.restrictionEntity.canSearch==true)">
								<div class="panel panel-default default-panel">
									<div class="panel-heading">
										restrictionEntity
										<button ng-click="showRestrictionEntityDetail()"
											class="btn btn-default  pull-right" style="margin-top: -7px"
											ng-show="(restrictionList.restrictionEntity==undefined || restrictionList.restrictionEntity.canCreate==true)">Add
											new restrictionEntity</button>
										<button type="button" class="btn btn-default pull-right"
											style="margin-top: -7px" data-toggle="modal"
											data-target="#entity-restrictionEntity">Link
											existing</button>
										<button ng-click="downloadRestrictionEntityList()"
											class="btn btn-default pull-right" style="margin-top: -7px">
											<span class="glyphicon glyphicon-download-alt"
												aria-hidden="true"></span>
										</button>
									</div>
									<div class="panel-body"
										ng-class="{&#39;has-error&#39;: !entityDetailForm.restrictionEntity.$valid, &#39;has-success&#39;: entityDetailForm.restrictionEntity.$valid}">
										<label id="restrictionEntity">restrictionEntity</label>
										<div id="restrictionEntity"
											ng-if="selectedEntity.restrictionEntityList.length&gt;0">
											<div style="top: 100px"
												ui-grid="restrictionEntityListGridOptions"
												ui-grid-pagination="" ui-grid-selection=""></div>
										</div>
									</div>
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !entityDetailForm.entityGroup.$valid, &#39;has-success&#39;: entityDetailForm.entityGroup.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">entityGroup</span><select
										class="form-control " aria-describedby="sizing-addon3"
										ng-model="selectedEntity.entityGroup" id="entityGroup"
										name="entityGroup"
										ng-options="entityGroup as  entityGroup.entityGroupId+&#39; &#39;+ entityGroup.name for entityGroup in childrenList.entityGroupList track by entityGroup.entityGroupId"
										enctype="UTF-8"></select><span class="input-group-btn"><button
											ng-click="showEntityGroupDetail()" class="btn btn-default "
											id="entityGroup" ng-if="selectedEntity.entityGroup==null">Add
											new entityGroup</button>
										<button ng-click="showEntityGroupDetail()"
											class="btn btn-default " id="entityGroup"
											ng-if="selectedEntity.entityGroup!=null">Show detail</button></span>
								</div>
							</div>
							<div id="entity-field" class="modal fade" role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Link existing field</h4>
										</div>
										<div class="modal-body">
											<p></p>
											<div class="input-group">
												<span class="input-group-addon">field</span><select
													class="form-control " aria-describedby="sizing-addon3"
													ng-model="selectedEntity.field" id="field" name="field"
													ng-options="field as  field.name+&#39; &#39;+ field.fieldId for field in childrenList.fieldList track by field.fieldId"
													enctype="UTF-8"></select><span class="input-group-btn"><button
														ng-click="saveLinkedField()" class="btn btn-default "
														id="field">Save</button></span>
											</div>
										</div>
										<div class="modal-footer">
											<button ng-click="close()" class="btn btn-default "
												data-dismiss="modal">close</button>
										</div>
									</div>
								</div>
							</div>
							<br />
							<br />
							<div class="pull-left" style="width: 100%"
								ng-show="true  && (restrictionList.field==undefined || restrictionList.field.canSearch==true)">
								<div class="panel panel-default default-panel">
									<div class="panel-heading">
										field
										<button ng-click="showFieldDetail()"
											class="btn btn-default  pull-right" style="margin-top: -7px"
											ng-show="(restrictionList.field==undefined || restrictionList.field.canCreate==true)">Add
											new field</button>
										<button type="button" class="btn btn-default pull-right"
											style="margin-top: -7px" data-toggle="modal"
											data-target="#entity-field">Link existing</button>
										<button ng-click="downloadFieldList()"
											class="btn btn-default pull-right" style="margin-top: -7px">
											<span class="glyphicon glyphicon-download-alt"
												aria-hidden="true"></span>
										</button>
									</div>
									<div class="panel-body"
										ng-class="{&#39;has-error&#39;: !entityDetailForm.field.$valid, &#39;has-success&#39;: entityDetailForm.field.$valid}">
										<label id="field">field</label>
										<div id="field" ng-if="selectedEntity.fieldList.length&gt;0">
											<div style="top: 100px" ui-grid="fieldListGridOptions"
												ui-grid-pagination="" ui-grid-selection=""></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">
					$('#entityTabs a:first').tab('show');
				</script>
				<div class="panel-body">
					<div class="pull-left">
						<form id="entityActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default "
								ng-if="selectedEntity.entityId==undefined"
								ng-show="(restrictionList.entity==undefined || restrictionList.entity.canCreate==true)">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.entityId&gt;0"
								ng-show="(restrictionList.entity==undefined || restrictionList.entity.canUpdate==true)">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.entityId&gt;0"
								ng-show="(restrictionList.entity==undefined || restrictionList.entity.canDelete==true)">Delete</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="relationshipController">
		<form id="relationshipList" ng-if="entityList.length&gt;0"
			enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">
					List relationship
					<button ng-click="downloadEntityList()"
						class="btn btn-default pull-right" style="margin-top: -7px">
						<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
					</button>
				</div>
				<div class="panel-body">
					<div ui-grid="relationshipGridOptions" ui-grid-pagination=""
						ui-grid-selection="" ui-grid-exporter=""></div>
				</div>
			</div>
		</form>
		<form id="relationshipDetailForm" name="relationshipDetailForm"
			ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail relationship {{
					selectedEntity.relationshipId }}</div>
				<div class="panel-body">
					<ul class="nav nav-tabs" role="tablist" id="relationshipTabs">
						<li role="presentation"><a href="#relationship-Detail"
							aria-controls="Detail" role="tab" data-toggle="tab"
							ng-click="refreshTableDetail()">Detail</a></li>
						<script type="text/javascript">
							$('a[href="#relationship-Detail"]')
									.on(
											'shown.bs.tab',
											function(e) {
												var target = $(e.target).attr(
														"href"); // activated tab
												//console.log(target);
												if (angular.element(
														$('#relationshipTabs'))
														.scope() != null
														&& angular
																.element(
																		$('#relationshipTabs'))
																.scope() != undefined)
													angular
															.element(
																	$('#relationshipTabs'))
															.scope()
															.refreshTableDetail();
											});
						</script>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade"
							id="relationship-Detail">
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !relationshipDetailForm.relationshipId.$valid, &#39;has-success&#39;: relationshipDetailForm.relationshipId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">relationshipId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="relationship-relationshipId"
										ng-model="selectedEntity.relationshipId" ng-readonly="true"
										name="relationshipId" placeholder="relationshipId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !relationshipDetailForm.name.$valid, &#39;has-success&#39;: relationshipDetailForm.name.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">name</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="relationship-name"
										ng-model="selectedEntity.name"
										ng-readonly="restrictionList.relationship.restrictionFieldMap.name"
										name="name" placeholder="name" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !relationshipDetailForm.priority.$valid, &#39;has-success&#39;: relationshipDetailForm.priority.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">priority</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="relationship-priority"
										ng-model="selectedEntity.priority"
										ng-readonly="restrictionList.relationship.restrictionFieldMap.priority"
										name="priority" placeholder="priority" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !relationshipDetailForm.relationshipType.$valid, &#39;has-success&#39;: relationshipDetailForm.relationshipType.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">relationshipType</span><select
										class="form-control pull-left"
										aria-describedby="sizing-addon3" type="text"
										id="relationship-relationshipType"
										ng-model="selectedEntity.relationshipType"
										ng-readonly="restrictionList.relationship.restrictionFieldMap.relationshipType"
										name="relationshipType" placeholder="relationshipType"
										ng-options="relationshipType as relationshipType for relationshipType in childrenList.relationshipTypeList"
										enctype="UTF-8"></select>
								</div>
							</div>
							<div id="relationship-annotation" class="modal fade"
								role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Link existing annotation</h4>
										</div>
										<div class="modal-body">
											<p></p>
											<div class="input-group">
												<span class="input-group-addon">annotation</span><select
													class="form-control " aria-describedby="sizing-addon3"
													ng-model="selectedEntity.annotation" id="annotation"
													name="annotation"
													ng-options="annotation as  annotation.annotationId for annotation in childrenList.annotationList track by annotation.annotationId"
													enctype="UTF-8"></select><span class="input-group-btn"><button
														ng-click="saveLinkedAnnotation()" class="btn btn-default "
														id="annotation">Save</button></span>
											</div>
										</div>
										<div class="modal-footer">
											<button ng-click="close()" class="btn btn-default "
												data-dismiss="modal">close</button>
										</div>
									</div>
								</div>
							</div>
							<br />
							<br />
							<div class="pull-left" style="width: 100%"
								ng-show="true  && (restrictionList.annotation==undefined || restrictionList.annotation.canSearch==true)">
								<div class="panel panel-default default-panel">
									<div class="panel-heading">
										annotation
										<button ng-click="showAnnotationDetail()"
											class="btn btn-default  pull-right" style="margin-top: -7px"
											ng-show="(restrictionList.annotation==undefined || restrictionList.annotation.canCreate==true)">Add
											new annotation</button>
										<button type="button" class="btn btn-default pull-right"
											style="margin-top: -7px" data-toggle="modal"
											data-target="#relationship-annotation">Link existing</button>
										<button ng-click="downloadAnnotationList()"
											class="btn btn-default pull-right" style="margin-top: -7px">
											<span class="glyphicon glyphicon-download-alt"
												aria-hidden="true"></span>
										</button>
									</div>
									<div class="panel-body"
										ng-class="{&#39;has-error&#39;: !relationshipDetailForm.annotation.$valid, &#39;has-success&#39;: relationshipDetailForm.annotation.$valid}">
										<label id="annotation">annotation</label>
										<div id="annotation"
											ng-if="selectedEntity.annotationList.length&gt;0">
											<div style="top: 100px" ui-grid="annotationListGridOptions"
												ui-grid-pagination="" ui-grid-selection=""></div>
										</div>
									</div>
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !relationshipDetailForm.tab.$valid, &#39;has-success&#39;: relationshipDetailForm.tab.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">tab</span><select
										class="form-control " aria-describedby="sizing-addon3"
										ng-model="selectedEntity.tab" id="tab" name="tab"
										ng-options="tab as  tab.tabId+&#39; &#39;+ tab.name for tab in childrenList.tabList track by tab.tabId"
										enctype="UTF-8"></select><span class="input-group-btn"><button
											ng-click="showTabDetail()" class="btn btn-default " id="tab"
											ng-if="selectedEntity.tab==null">Add new tab</button>
										<button ng-click="showTabDetail()" class="btn btn-default "
											id="tab" ng-if="selectedEntity.tab!=null">Show
											detail</button></span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">
					$('#relationshipTabs a:first').tab('show');
				</script>
				<div class="panel-body">
					<div class="pull-left">
						<form id="relationshipActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default "
								ng-if="selectedEntity.relationshipId==undefined"
								ng-show="(restrictionList.relationship==undefined || restrictionList.relationship.canCreate==true)">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.relationshipId&gt;0"
								ng-show="(restrictionList.relationship==undefined || restrictionList.relationship.canUpdate==true)">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.relationshipId&gt;0"
								ng-show="(restrictionList.relationship==undefined || restrictionList.relationship.canDelete==true)">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.relationshipId&gt;0"
								ng-show="(restrictionList.relationship==undefined || restrictionList.relationship.canDelete==true)">Remove</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="annotationController">
		<form id="annotationList" ng-if="entityList.length&gt;0"
			enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">
					List annotation
					<button ng-click="downloadEntityList()"
						class="btn btn-default pull-right" style="margin-top: -7px">
						<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
					</button>
				</div>
				<div class="panel-body">
					<div ui-grid="annotationGridOptions" ui-grid-pagination=""
						ui-grid-selection="" ui-grid-exporter=""></div>
				</div>
			</div>
		</form>
		<form id="annotationDetailForm" name="annotationDetailForm"
			ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail annotation {{
					selectedEntity.annotationId }}</div>
				<div class="panel-body">
					<ul class="nav nav-tabs" role="tablist" id="annotationTabs">
						<li role="presentation"><a href="#annotation-Detail"
							aria-controls="Detail" role="tab" data-toggle="tab"
							ng-click="refreshTableDetail()">Detail</a></li>
						<script type="text/javascript">
							$('a[href="#annotation-Detail"]')
									.on(
											'shown.bs.tab',
											function(e) {
												var target = $(e.target).attr(
														"href"); // activated tab
												//console.log(target);
												if (angular.element(
														$('#annotationTabs'))
														.scope() != null
														&& angular
																.element(
																		$('#annotationTabs'))
																.scope() != undefined)
													angular
															.element(
																	$('#annotationTabs'))
															.scope()
															.refreshTableDetail();
											});
						</script>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade" id="annotation-Detail">
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !annotationDetailForm.annotationId.$valid, &#39;has-success&#39;: annotationDetailForm.annotationId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">annotationId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="annotation-annotationId"
										ng-model="selectedEntity.annotationId" ng-readonly="true"
										name="annotationId" placeholder="annotationId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !annotationDetailForm.annotationType.$valid, &#39;has-success&#39;: annotationDetailForm.annotationType.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">annotationType</span><select
										class="form-control pull-left"
										aria-describedby="sizing-addon3" type="text"
										id="annotation-annotationType"
										ng-model="selectedEntity.annotationType"
										ng-readonly="restrictionList.annotation.restrictionFieldMap.annotationType"
										name="annotationType" placeholder="annotationType"
										ng-options="annotationType as annotationType for annotationType in childrenList.annotationTypeList"
										enctype="UTF-8"></select>
								</div>
							</div>
							<div id="annotation-annotationAttribute" class="modal fade"
								role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Link existing
												annotationAttribute</h4>
										</div>
										<div class="modal-body">
											<p></p>
											<div class="input-group">
												<span class="input-group-addon">annotationAttribute</span><select
													class="form-control " aria-describedby="sizing-addon3"
													ng-model="selectedEntity.annotationAttribute"
													id="annotationAttribute" name="annotationAttribute"
													ng-options="annotationAttribute as  annotationAttribute.annotationAttributeId+&#39; &#39;+ annotationAttribute.property for annotationAttribute in childrenList.annotationAttributeList track by annotationAttribute.annotationAttributeId"
													enctype="UTF-8"></select><span class="input-group-btn"><button
														ng-click="saveLinkedAnnotationAttribute()"
														class="btn btn-default " id="annotationAttribute">Save</button></span>
											</div>
										</div>
										<div class="modal-footer">
											<button ng-click="close()" class="btn btn-default "
												data-dismiss="modal">close</button>
										</div>
									</div>
								</div>
							</div>
							<br />
							<br />
							<div class="pull-left" style="width: 100%"
								ng-show="true  && (restrictionList.annotationAttribute==undefined || restrictionList.annotationAttribute.canSearch==true)">
								<div class="panel panel-default default-panel">
									<div class="panel-heading">
										annotationAttribute
										<button ng-click="showAnnotationAttributeDetail()"
											class="btn btn-default  pull-right" style="margin-top: -7px"
											ng-show="(restrictionList.annotationAttribute==undefined || restrictionList.annotationAttribute.canCreate==true)">Add
											new annotationAttribute</button>
										<button type="button" class="btn btn-default pull-right"
											style="margin-top: -7px" data-toggle="modal"
											data-target="#annotation-annotationAttribute">Link
											existing</button>
										<button ng-click="downloadAnnotationAttributeList()"
											class="btn btn-default pull-right" style="margin-top: -7px">
											<span class="glyphicon glyphicon-download-alt"
												aria-hidden="true"></span>
										</button>
									</div>
									<div class="panel-body"
										ng-class="{&#39;has-error&#39;: !annotationDetailForm.annotationAttribute.$valid, &#39;has-success&#39;: annotationDetailForm.annotationAttribute.$valid}">
										<label id="annotationAttribute">annotationAttribute</label>
										<div id="annotationAttribute"
											ng-if="selectedEntity.annotationAttributeList.length&gt;0">
											<div style="top: 100px"
												ui-grid="annotationAttributeListGridOptions"
												ui-grid-pagination="" ui-grid-selection=""></div>
										</div>
									</div>
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !annotationDetailForm.field.$valid, &#39;has-success&#39;: annotationDetailForm.field.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">field</span><select
										class="form-control " aria-describedby="sizing-addon3"
										ng-model="selectedEntity.field" id="field" name="field"
										ng-options="field as  field.name+&#39; &#39;+ field.fieldId for field in childrenList.fieldList track by field.fieldId"
										enctype="UTF-8"></select><span class="input-group-btn"><button
											ng-click="showFieldDetail()" class="btn btn-default "
											id="field" ng-if="selectedEntity.field==null">Add
											new field</button>
										<button ng-click="showFieldDetail()" class="btn btn-default "
											id="field" ng-if="selectedEntity.field!=null">Show
											detail</button></span>
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !annotationDetailForm.enumField.$valid, &#39;has-success&#39;: annotationDetailForm.enumField.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">enumField</span><select
										class="form-control " aria-describedby="sizing-addon3"
										ng-model="selectedEntity.enumField" id="enumField"
										name="enumField"
										ng-options="enumField as  enumField.name+&#39; &#39;+ enumField.enumFieldId for enumField in childrenList.enumFieldList track by enumField.enumFieldId"
										enctype="UTF-8"></select><span class="input-group-btn"><button
											ng-click="showEnumFieldDetail()" class="btn btn-default "
											id="enumField" ng-if="selectedEntity.enumField==null">Add
											new enumField</button>
										<button ng-click="showEnumFieldDetail()"
											class="btn btn-default " id="enumField"
											ng-if="selectedEntity.enumField!=null">Show detail</button></span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">
					$('#annotationTabs a:first').tab('show');
				</script>
				<div class="panel-body">
					<div class="pull-left">
						<form id="annotationActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default "
								ng-if="selectedEntity.annotationId==undefined"
								ng-show="(restrictionList.annotation==undefined || restrictionList.annotation.canCreate==true)">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.annotationId&gt;0"
								ng-show="(restrictionList.annotation==undefined || restrictionList.annotation.canUpdate==true)">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.annotationId&gt;0"
								ng-show="(restrictionList.annotation==undefined || restrictionList.annotation.canDelete==true)">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.annotationId&gt;0"
								ng-show="(restrictionList.annotation==undefined || restrictionList.annotation.canDelete==true)">Remove</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="annotationAttributeController">
		<form id="annotationAttributeList" ng-if="entityList.length&gt;0"
			enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">
					List annotationAttribute
					<button ng-click="downloadEntityList()"
						class="btn btn-default pull-right" style="margin-top: -7px">
						<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
					</button>
				</div>
				<div class="panel-body">
					<div ui-grid="annotationAttributeGridOptions" ui-grid-pagination=""
						ui-grid-selection="" ui-grid-exporter=""></div>
				</div>
			</div>
		</form>
		<form id="annotationAttributeDetailForm"
			name="annotationAttributeDetailForm" ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail annotationAttribute {{
					selectedEntity.annotationAttributeId }}</div>
				<div class="panel-body">
					<ul class="nav nav-tabs" role="tablist"
						id="annotationAttributeTabs">
						<li role="presentation"><a href="#annotationAttribute-Detail"
							aria-controls="Detail" role="tab" data-toggle="tab"
							ng-click="refreshTableDetail()">Detail</a></li>
						<script type="text/javascript">
							$('a[href="#annotationAttribute-Detail"]')
									.on(
											'shown.bs.tab',
											function(e) {
												var target = $(e.target).attr(
														"href"); // activated tab
												//console.log(target);
												if (angular
														.element(
																$('#annotationAttributeTabs'))
														.scope() != null
														&& angular
																.element(
																		$('#annotationAttributeTabs'))
																.scope() != undefined)
													angular
															.element(
																	$('#annotationAttributeTabs'))
															.scope()
															.refreshTableDetail();
											});
						</script>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade"
							id="annotationAttribute-Detail">
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !annotationAttributeDetailForm.annotationAttributeId.$valid, &#39;has-success&#39;: annotationAttributeDetailForm.annotationAttributeId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">annotationAttributeId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="annotationAttribute-annotationAttributeId"
										ng-model="selectedEntity.annotationAttributeId"
										ng-readonly="true" name="annotationAttributeId"
										placeholder="annotationAttributeId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !annotationAttributeDetailForm.property.$valid, &#39;has-success&#39;: annotationAttributeDetailForm.property.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">property</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="annotationAttribute-property"
										ng-model="selectedEntity.property"
										ng-readonly="restrictionList.annotationAttribute.restrictionFieldMap.property"
										name="property" placeholder="property" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !annotationAttributeDetailForm.value.$valid, &#39;has-success&#39;: annotationAttributeDetailForm.value.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">value</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="annotationAttribute-value"
										ng-model="selectedEntity.value"
										ng-readonly="restrictionList.annotationAttribute.restrictionFieldMap.value"
										name="value" placeholder="value" />
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">
					$('#annotationAttributeTabs a:first').tab('show');
				</script>
				<div class="panel-body">
					<div class="pull-left">
						<form id="annotationAttributeActionButton"
							ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default "
								ng-if="selectedEntity.annotationAttributeId==undefined"
								ng-show="(restrictionList.annotationAttribute==undefined || restrictionList.annotationAttribute.canCreate==true)">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.annotationAttributeId&gt;0"
								ng-show="(restrictionList.annotationAttribute==undefined || restrictionList.annotationAttribute.canUpdate==true)">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.annotationAttributeId&gt;0"
								ng-show="(restrictionList.annotationAttribute==undefined || restrictionList.annotationAttribute.canDelete==true)">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.annotationAttributeId&gt;0"
								ng-show="(restrictionList.annotationAttribute==undefined || restrictionList.annotationAttribute.canDelete==true)">Remove</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="fieldController">
		<form id="fieldList" ng-if="entityList.length&gt;0" enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">
					List field
					<button ng-click="downloadEntityList()"
						class="btn btn-default pull-right" style="margin-top: -7px">
						<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
					</button>
				</div>
				<div class="panel-body">
					<div ui-grid="fieldGridOptions" ui-grid-pagination=""
						ui-grid-selection="" ui-grid-exporter=""></div>
				</div>
			</div>
		</form>
		<form id="fieldDetailForm" name="fieldDetailForm"
			ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail field {{
					selectedEntity.fieldId }}</div>
				<div class="panel-body">
					<ul class="nav nav-tabs" role="tablist" id="fieldTabs">
						<li role="presentation"><a href="#field-Detail"
							aria-controls="Detail" role="tab" data-toggle="tab"
							ng-click="refreshTableDetail()">Detail</a></li>
						<script type="text/javascript">
							$('a[href="#field-Detail"]').on(
									'shown.bs.tab',
									function(e) {
										var target = $(e.target).attr("href"); // activated tab
										//console.log(target);
										if (angular.element($('#fieldTabs'))
												.scope() != null
												&& angular.element(
														$('#fieldTabs'))
														.scope() != undefined)
											angular.element($('#fieldTabs'))
													.scope()
													.refreshTableDetail();
									});
						</script>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade" id="field-Detail">
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !fieldDetailForm.fieldId.$valid, &#39;has-success&#39;: fieldDetailForm.fieldId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">fieldId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="field-fieldId"
										ng-model="selectedEntity.fieldId" ng-readonly="true"
										name="fieldId" placeholder="fieldId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !fieldDetailForm.name.$valid, &#39;has-success&#39;: fieldDetailForm.name.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">name</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="field-name" ng-model="selectedEntity.name"
										ng-readonly="restrictionList.field.restrictionFieldMap.name"
										name="name" placeholder="name" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !fieldDetailForm.priority.$valid, &#39;has-success&#39;: fieldDetailForm.priority.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">priority</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="field-priority"
										ng-model="selectedEntity.priority"
										ng-readonly="restrictionList.field.restrictionFieldMap.priority"
										name="priority" placeholder="priority" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !fieldDetailForm.fieldType.$valid, &#39;has-success&#39;: fieldDetailForm.fieldType.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">fieldType</span><select
										class="form-control pull-left"
										aria-describedby="sizing-addon3" type="text"
										id="field-fieldType" ng-model="selectedEntity.fieldType"
										ng-readonly="restrictionList.field.restrictionFieldMap.fieldType"
										name="fieldType" placeholder="fieldType"
										ng-options="fieldType as fieldType for fieldType in childrenList.fieldTypeList"
										enctype="UTF-8"></select>
								</div>
							</div>
							<div id="field-restrictionField" class="modal fade" role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Link existing restrictionField</h4>
										</div>
										<div class="modal-body">
											<p></p>
											<div class="input-group">
												<span class="input-group-addon">restrictionField</span><select
													class="form-control " aria-describedby="sizing-addon3"
													ng-model="selectedEntity.restrictionField"
													id="restrictionField" name="restrictionField"
													ng-options="restrictionField as  restrictionField.restrictionFieldId for restrictionField in childrenList.restrictionFieldList track by restrictionField.restrictionFieldId"
													enctype="UTF-8"></select><span class="input-group-btn"><button
														ng-click="saveLinkedRestrictionField()"
														class="btn btn-default " id="restrictionField">Save</button></span>
											</div>
										</div>
										<div class="modal-footer">
											<button ng-click="close()" class="btn btn-default "
												data-dismiss="modal">close</button>
										</div>
									</div>
								</div>
							</div>
							<br />
							<br />
							<div class="pull-left" style="width: 100%"
								ng-show="true  && (restrictionList.restrictionField==undefined || restrictionList.restrictionField.canSearch==true)">
								<div class="panel panel-default default-panel">
									<div class="panel-heading">
										restrictionField
										<button ng-click="showRestrictionFieldDetail()"
											class="btn btn-default  pull-right" style="margin-top: -7px"
											ng-show="(restrictionList.restrictionField==undefined || restrictionList.restrictionField.canCreate==true)">Add
											new restrictionField</button>
										<button type="button" class="btn btn-default pull-right"
											style="margin-top: -7px" data-toggle="modal"
											data-target="#field-restrictionField">Link existing</button>
										<button ng-click="downloadRestrictionFieldList()"
											class="btn btn-default pull-right" style="margin-top: -7px">
											<span class="glyphicon glyphicon-download-alt"
												aria-hidden="true"></span>
										</button>
									</div>
									<div class="panel-body"
										ng-class="{&#39;has-error&#39;: !fieldDetailForm.restrictionField.$valid, &#39;has-success&#39;: fieldDetailForm.restrictionField.$valid}">
										<label id="restrictionField">restrictionField</label>
										<div id="restrictionField"
											ng-if="selectedEntity.restrictionFieldList.length&gt;0">
											<div style="top: 100px"
												ui-grid="restrictionFieldListGridOptions"
												ui-grid-pagination="" ui-grid-selection=""></div>
										</div>
									</div>
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !fieldDetailForm.tab.$valid, &#39;has-success&#39;: fieldDetailForm.tab.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">tab</span><select
										class="form-control " aria-describedby="sizing-addon3"
										ng-model="selectedEntity.tab" id="tab" name="tab"
										ng-options="tab as  tab.tabId+&#39; &#39;+ tab.name for tab in childrenList.tabList track by tab.tabId"
										enctype="UTF-8"></select><span class="input-group-btn"><button
											ng-click="showTabDetail()" class="btn btn-default " id="tab"
											ng-if="selectedEntity.tab==null">Add new tab</button>
										<button ng-click="showTabDetail()" class="btn btn-default "
											id="tab" ng-if="selectedEntity.tab!=null">Show
											detail</button></span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">
					$('#fieldTabs a:first').tab('show');
				</script>
				<div class="panel-body">
					<div class="pull-left">
						<form id="fieldActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default "
								ng-if="selectedEntity.fieldId==undefined"
								ng-show="(restrictionList.field==undefined || restrictionList.field.canCreate==true)">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.fieldId&gt;0"
								ng-show="(restrictionList.field==undefined || restrictionList.field.canUpdate==true)">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.fieldId&gt;0"
								ng-show="(restrictionList.field==undefined || restrictionList.field.canDelete==true)">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.fieldId&gt;0"
								ng-show="(restrictionList.field==undefined || restrictionList.field.canDelete==true)">Remove</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="restrictionFieldController">
		<form id="restrictionFieldList" ng-if="entityList.length&gt;0"
			enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">
					List restrictionField
					<button ng-click="downloadEntityList()"
						class="btn btn-default pull-right" style="margin-top: -7px">
						<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
					</button>
				</div>
				<div class="panel-body">
					<div ui-grid="restrictionFieldGridOptions" ui-grid-pagination=""
						ui-grid-selection="" ui-grid-exporter=""></div>
				</div>
			</div>
		</form>
		<form id="restrictionFieldDetailForm"
			name="restrictionFieldDetailForm" ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail restrictionField {{
					selectedEntity.restrictionFieldId }}</div>
				<div class="panel-body">
					<ul class="nav nav-tabs" role="tablist" id="restrictionFieldTabs">
						<li role="presentation"><a href="#restrictionField-Detail"
							aria-controls="Detail" role="tab" data-toggle="tab"
							ng-click="refreshTableDetail()">Detail</a></li>
						<script type="text/javascript">
							$('a[href="#restrictionField-Detail"]')
									.on(
											'shown.bs.tab',
											function(e) {
												var target = $(e.target).attr(
														"href"); // activated tab
												//console.log(target);
												if (angular
														.element(
																$('#restrictionFieldTabs'))
														.scope() != null
														&& angular
																.element(
																		$('#restrictionFieldTabs'))
																.scope() != undefined)
													angular
															.element(
																	$('#restrictionFieldTabs'))
															.scope()
															.refreshTableDetail();
											});
						</script>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade"
							id="restrictionField-Detail">
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !restrictionFieldDetailForm.restrictionFieldId.$valid, &#39;has-success&#39;: restrictionFieldDetailForm.restrictionFieldId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">restrictionFieldId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="restrictionField-restrictionFieldId"
										ng-model="selectedEntity.restrictionFieldId"
										ng-readonly="true" name="restrictionFieldId"
										placeholder="restrictionFieldId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !restrictionFieldDetailForm.role.$valid, &#39;has-success&#39;: restrictionFieldDetailForm.role.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">role</span><select
										class="form-control " aria-describedby="sizing-addon3"
										ng-model="selectedEntity.role" id="role" name="role"
										ng-options="role as  role.roleId+&#39; &#39;+ role.role for role in childrenList.roleList track by role.roleId"
										enctype="UTF-8"></select><span class="input-group-btn"><button
											ng-click="showRoleDetail()" class="btn btn-default "
											id="role" ng-if="selectedEntity.role==null">Add new
											role</button>
										<button ng-click="showRoleDetail()" class="btn btn-default "
											id="role" ng-if="selectedEntity.role!=null">Show
											detail</button></span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">
					$('#restrictionFieldTabs a:first').tab('show');
				</script>
				<div class="panel-body">
					<div class="pull-left">
						<form id="restrictionFieldActionButton"
							ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default "
								ng-if="selectedEntity.restrictionFieldId==undefined"
								ng-show="(restrictionList.restrictionField==undefined || restrictionList.restrictionField.canCreate==true)">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.restrictionFieldId&gt;0"
								ng-show="(restrictionList.restrictionField==undefined || restrictionList.restrictionField.canUpdate==true)">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.restrictionFieldId&gt;0"
								ng-show="(restrictionList.restrictionField==undefined || restrictionList.restrictionField.canDelete==true)">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.restrictionFieldId&gt;0"
								ng-show="(restrictionList.restrictionField==undefined || restrictionList.restrictionField.canDelete==true)">Remove</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="roleController">
		<form id="roleList" ng-if="entityList.length&gt;0" enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">
					List role
					<button ng-click="downloadEntityList()"
						class="btn btn-default pull-right" style="margin-top: -7px">
						<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
					</button>
				</div>
				<div class="panel-body">
					<div ui-grid="roleGridOptions" ui-grid-pagination=""
						ui-grid-selection="" ui-grid-exporter=""></div>
				</div>
			</div>
		</form>
		<form id="roleDetailForm" name="roleDetailForm"
			ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail role {{
					selectedEntity.roleId }}</div>
				<div class="panel-body">
					<ul class="nav nav-tabs" role="tablist" id="roleTabs">
						<li role="presentation"><a href="#role-Detail"
							aria-controls="Detail" role="tab" data-toggle="tab"
							ng-click="refreshTableDetail()">Detail</a></li>
						<script type="text/javascript">
							$('a[href="#role-Detail"]')
									.on(
											'shown.bs.tab',
											function(e) {
												var target = $(e.target).attr(
														"href"); // activated tab
												//console.log(target);
												if (angular.element(
														$('#roleTabs')).scope() != null
														&& angular.element(
																$('#roleTabs'))
																.scope() != undefined)
													angular
															.element(
																	$('#roleTabs'))
															.scope()
															.refreshTableDetail();
											});
						</script>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade" id="role-Detail">
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !roleDetailForm.roleId.$valid, &#39;has-success&#39;: roleDetailForm.roleId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">roleId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="role-roleId" ng-model="selectedEntity.roleId"
										ng-readonly="true" name="roleId" placeholder="roleId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !roleDetailForm.role.$valid, &#39;has-success&#39;: roleDetailForm.role.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">role</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="role-role" ng-model="selectedEntity.role"
										ng-readonly="restrictionList.role.restrictionFieldMap.role"
										name="role" placeholder="role" />
								</div>
							</div>
							<div id="role-user" class="modal fade" role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Link existing user</h4>
										</div>
										<div class="modal-body">
											<p></p>
											<div class="input-group">
												<span class="input-group-addon">user</span><select
													class="form-control " aria-describedby="sizing-addon3"
													ng-model="selectedEntity.user" id="user" name="user"
													ng-options="user as  user.userId+&#39; &#39;+ user.username for user in childrenList.userList track by user.userId"
													enctype="UTF-8"></select><span class="input-group-btn"><button
														ng-click="saveLinkedUser()" class="btn btn-default "
														id="user">Save</button></span>
											</div>
										</div>
										<div class="modal-footer">
											<button ng-click="close()" class="btn btn-default "
												data-dismiss="modal">close</button>
										</div>
									</div>
								</div>
							</div>
							<br />
							<br />
							<div class="pull-left" style="width: 100%"
								ng-show="true  && (restrictionList.user==undefined || restrictionList.user.canSearch==true)">
								<div class="panel panel-default default-panel">
									<div class="panel-heading">
										user
										<button ng-click="showUserDetail()"
											class="btn btn-default  pull-right" style="margin-top: -7px"
											ng-show="(restrictionList.user==undefined || restrictionList.user.canCreate==true)">Add
											new user</button>
										<button type="button" class="btn btn-default pull-right"
											style="margin-top: -7px" data-toggle="modal"
											data-target="#role-user">Link existing</button>
										<button ng-click="downloadUserList()"
											class="btn btn-default pull-right" style="margin-top: -7px">
											<span class="glyphicon glyphicon-download-alt"
												aria-hidden="true"></span>
										</button>
									</div>
									<div class="panel-body"
										ng-class="{&#39;has-error&#39;: !roleDetailForm.user.$valid, &#39;has-success&#39;: roleDetailForm.user.$valid}">
										<label id="user">user</label>
										<div id="user" ng-if="selectedEntity.userList.length&gt;0">
											<div style="top: 100px" ui-grid="userListGridOptions"
												ui-grid-pagination="" ui-grid-selection=""></div>
										</div>
									</div>
								</div>
							</div>
							<div id="role-restrictionEntity" class="modal fade" role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Link existing restrictionEntity</h4>
										</div>
										<div class="modal-body">
											<p></p>
											<div class="input-group">
												<span class="input-group-addon">restrictionEntity</span><select
													class="form-control " aria-describedby="sizing-addon3"
													ng-model="selectedEntity.restrictionEntity"
													id="restrictionEntity" name="restrictionEntity"
													ng-options="restrictionEntity as  restrictionEntity.restrictionEntityId+&#39; &#39;+ restrictionEntity.entity for restrictionEntity in childrenList.restrictionEntityList track by restrictionEntity.restrictionEntityId"
													enctype="UTF-8"></select><span class="input-group-btn"><button
														ng-click="saveLinkedRestrictionEntity()"
														class="btn btn-default " id="restrictionEntity">Save</button></span>
											</div>
										</div>
										<div class="modal-footer">
											<button ng-click="close()" class="btn btn-default "
												data-dismiss="modal">close</button>
										</div>
									</div>
								</div>
							</div>
							<br />
							<br />
							<div class="pull-left" style="width: 100%"
								ng-show="true  && (restrictionList.restrictionEntity==undefined || restrictionList.restrictionEntity.canSearch==true)">
								<div class="panel panel-default default-panel">
									<div class="panel-heading">
										restrictionEntity
										<button ng-click="showRestrictionEntityDetail()"
											class="btn btn-default  pull-right" style="margin-top: -7px"
											ng-show="(restrictionList.restrictionEntity==undefined || restrictionList.restrictionEntity.canCreate==true)">Add
											new restrictionEntity</button>
										<button type="button" class="btn btn-default pull-right"
											style="margin-top: -7px" data-toggle="modal"
											data-target="#role-restrictionEntity">Link existing</button>
										<button ng-click="downloadRestrictionEntityList()"
											class="btn btn-default pull-right" style="margin-top: -7px">
											<span class="glyphicon glyphicon-download-alt"
												aria-hidden="true"></span>
										</button>
									</div>
									<div class="panel-body"
										ng-class="{&#39;has-error&#39;: !roleDetailForm.restrictionEntity.$valid, &#39;has-success&#39;: roleDetailForm.restrictionEntity.$valid}">
										<label id="restrictionEntity">restrictionEntity</label>
										<div id="restrictionEntity"
											ng-if="selectedEntity.restrictionEntityList.length&gt;0">
											<div style="top: 100px"
												ui-grid="restrictionEntityListGridOptions"
												ui-grid-pagination="" ui-grid-selection=""></div>
										</div>
									</div>
								</div>
							</div>
							<div id="role-restrictionEntityGroup" class="modal fade"
								role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Link existing
												restrictionEntityGroup</h4>
										</div>
										<div class="modal-body">
											<p></p>
											<div class="input-group">
												<span class="input-group-addon">restrictionEntityGroup</span><select
													class="form-control " aria-describedby="sizing-addon3"
													ng-model="selectedEntity.restrictionEntityGroup"
													id="restrictionEntityGroup" name="restrictionEntityGroup"
													ng-options="restrictionEntityGroup as  restrictionEntityGroup.restrictionEntityGroupId for restrictionEntityGroup in childrenList.restrictionEntityGroupList track by restrictionEntityGroup.restrictionEntityGroupId"
													enctype="UTF-8"></select><span class="input-group-btn"><button
														ng-click="saveLinkedRestrictionEntityGroup()"
														class="btn btn-default " id="restrictionEntityGroup">Save</button></span>
											</div>
										</div>
										<div class="modal-footer">
											<button ng-click="close()" class="btn btn-default "
												data-dismiss="modal">close</button>
										</div>
									</div>
								</div>
							</div>
							<br />
							<br />
							<div class="pull-left" style="width: 100%"
								ng-show="true  && (restrictionList.restrictionEntityGroup==undefined || restrictionList.restrictionEntityGroup.canSearch==true)">
								<div class="panel panel-default default-panel">
									<div class="panel-heading">
										restrictionEntityGroup
										<button ng-click="showRestrictionEntityGroupDetail()"
											class="btn btn-default  pull-right" style="margin-top: -7px"
											ng-show="(restrictionList.restrictionEntityGroup==undefined || restrictionList.restrictionEntityGroup.canCreate==true)">Add
											new restrictionEntityGroup</button>
										<button type="button" class="btn btn-default pull-right"
											style="margin-top: -7px" data-toggle="modal"
											data-target="#role-restrictionEntityGroup">Link
											existing</button>
										<button ng-click="downloadRestrictionEntityGroupList()"
											class="btn btn-default pull-right" style="margin-top: -7px">
											<span class="glyphicon glyphicon-download-alt"
												aria-hidden="true"></span>
										</button>
									</div>
									<div class="panel-body"
										ng-class="{&#39;has-error&#39;: !roleDetailForm.restrictionEntityGroup.$valid, &#39;has-success&#39;: roleDetailForm.restrictionEntityGroup.$valid}">
										<label id="restrictionEntityGroup">restrictionEntityGroup</label>
										<div id="restrictionEntityGroup"
											ng-if="selectedEntity.restrictionEntityGroupList.length&gt;0">
											<div style="top: 100px"
												ui-grid="restrictionEntityGroupListGridOptions"
												ui-grid-pagination="" ui-grid-selection=""></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">
					$('#roleTabs a:first').tab('show');
				</script>
				<div class="panel-body">
					<div class="pull-left">
						<form id="roleActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default "
								ng-if="selectedEntity.roleId==undefined"
								ng-show="(restrictionList.role==undefined || restrictionList.role.canCreate==true)">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.roleId&gt;0"
								ng-show="(restrictionList.role==undefined || restrictionList.role.canUpdate==true)">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.roleId&gt;0"
								ng-show="(restrictionList.role==undefined || restrictionList.role.canDelete==true)">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.roleId&gt;0"
								ng-show="(restrictionList.role==undefined || restrictionList.role.canDelete==true)">Remove</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="userController">
		<form id="userList" ng-if="entityList.length&gt;0" enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">
					List user
					<button ng-click="downloadEntityList()"
						class="btn btn-default pull-right" style="margin-top: -7px">
						<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
					</button>
				</div>
				<div class="panel-body">
					<div ui-grid="userGridOptions" ui-grid-pagination=""
						ui-grid-selection="" ui-grid-exporter=""></div>
				</div>
			</div>
		</form>
		<form id="userDetailForm" name="userDetailForm"
			ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail user {{
					selectedEntity.userId }}</div>
				<div class="panel-body">
					<ul class="nav nav-tabs" role="tablist" id="userTabs">
						<li role="presentation"><a href="#user-Detail"
							aria-controls="Detail" role="tab" data-toggle="tab"
							ng-click="refreshTableDetail()">Detail</a></li>
						<script type="text/javascript">
							$('a[href="#user-Detail"]')
									.on(
											'shown.bs.tab',
											function(e) {
												var target = $(e.target).attr(
														"href"); // activated tab
												//console.log(target);
												if (angular.element(
														$('#userTabs')).scope() != null
														&& angular.element(
																$('#userTabs'))
																.scope() != undefined)
													angular
															.element(
																	$('#userTabs'))
															.scope()
															.refreshTableDetail();
											});
						</script>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade" id="user-Detail">
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !userDetailForm.userId.$valid, &#39;has-success&#39;: userDetailForm.userId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">userId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="user-userId" ng-model="selectedEntity.userId"
										ng-readonly="true" name="userId" placeholder="userId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !userDetailForm.username.$valid, &#39;has-success&#39;: userDetailForm.username.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">username</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="user-username"
										ng-model="selectedEntity.username"
										ng-readonly="restrictionList.user.restrictionFieldMap.username"
										name="username" placeholder="username" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !userDetailForm.enabled.$valid, &#39;has-success&#39;: userDetailForm.enabled.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">enabled</span><select
										class="form-control " aria-describedby="sizing-addon3"
										type="checkbox" id="user-enabled"
										ng-model="selectedEntity.enabled"
										ng-readonly="restrictionList.user.restrictionFieldMap.enabled"
										name="enabled" placeholder="enabled"
										ng-options="value for value in trueFalseValues"></select>
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !userDetailForm.password.$valid, &#39;has-success&#39;: userDetailForm.password.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">password</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="password" id="user-password"
										ng-model="selectedEntity.password"
										ng-readonly="restrictionList.user.restrictionFieldMap.password"
										name="password" placeholder="password" />
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">
					$('#userTabs a:first').tab('show');
				</script>
				<div class="panel-body">
					<div class="pull-left">
						<form id="userActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default "
								ng-if="selectedEntity.userId==undefined"
								ng-show="(restrictionList.user==undefined || restrictionList.user.canCreate==true)">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.userId&gt;0"
								ng-show="(restrictionList.user==undefined || restrictionList.user.canUpdate==true)">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.userId&gt;0"
								ng-show="(restrictionList.user==undefined || restrictionList.user.canDelete==true)">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.userId&gt;0"
								ng-show="(restrictionList.user==undefined || restrictionList.user.canDelete==true)">Remove</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="restrictionEntityController">
		<form id="restrictionEntityList" ng-if="entityList.length&gt;0"
			enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">
					List restrictionEntity
					<button ng-click="downloadEntityList()"
						class="btn btn-default pull-right" style="margin-top: -7px">
						<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
					</button>
				</div>
				<div class="panel-body">
					<div ui-grid="restrictionEntityGridOptions" ui-grid-pagination=""
						ui-grid-selection="" ui-grid-exporter=""></div>
				</div>
			</div>
		</form>
		<form id="restrictionEntityDetailForm"
			name="restrictionEntityDetailForm" ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail restrictionEntity {{
					selectedEntity.restrictionEntityId }}</div>
				<div class="panel-body">
					<ul class="nav nav-tabs" role="tablist" id="restrictionEntityTabs">
						<li role="presentation"><a href="#restrictionEntity-Detail"
							aria-controls="Detail" role="tab" data-toggle="tab"
							ng-click="refreshTableDetail()">Detail</a></li>
						<script type="text/javascript">
							$('a[href="#restrictionEntity-Detail"]')
									.on(
											'shown.bs.tab',
											function(e) {
												var target = $(e.target).attr(
														"href"); // activated tab
												//console.log(target);
												if (angular
														.element(
																$('#restrictionEntityTabs'))
														.scope() != null
														&& angular
																.element(
																		$('#restrictionEntityTabs'))
																.scope() != undefined)
													angular
															.element(
																	$('#restrictionEntityTabs'))
															.scope()
															.refreshTableDetail();
											});
						</script>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade"
							id="restrictionEntity-Detail">
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !restrictionEntityDetailForm.restrictionEntityId.$valid, &#39;has-success&#39;: restrictionEntityDetailForm.restrictionEntityId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">restrictionEntityId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="restrictionEntity-restrictionEntityId"
										ng-model="selectedEntity.restrictionEntityId"
										ng-readonly="true" name="restrictionEntityId"
										placeholder="restrictionEntityId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !restrictionEntityDetailForm.canCreate.$valid, &#39;has-success&#39;: restrictionEntityDetailForm.canCreate.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">canCreate</span><select
										class="form-control " aria-describedby="sizing-addon3"
										type="checkbox" id="restrictionEntity-canCreate"
										ng-model="selectedEntity.canCreate"
										ng-readonly="restrictionList.restrictionEntity.restrictionFieldMap.canCreate"
										name="canCreate" placeholder="canCreate"
										ng-options="value for value in trueFalseValues"></select>
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !restrictionEntityDetailForm.canDelete.$valid, &#39;has-success&#39;: restrictionEntityDetailForm.canDelete.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">canDelete</span><select
										class="form-control " aria-describedby="sizing-addon3"
										type="checkbox" id="restrictionEntity-canDelete"
										ng-model="selectedEntity.canDelete"
										ng-readonly="restrictionList.restrictionEntity.restrictionFieldMap.canDelete"
										name="canDelete" placeholder="canDelete"
										ng-options="value for value in trueFalseValues"></select>
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !restrictionEntityDetailForm.canUpdate.$valid, &#39;has-success&#39;: restrictionEntityDetailForm.canUpdate.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">canUpdate</span><select
										class="form-control " aria-describedby="sizing-addon3"
										type="checkbox" id="restrictionEntity-canUpdate"
										ng-model="selectedEntity.canUpdate"
										ng-readonly="restrictionList.restrictionEntity.restrictionFieldMap.canUpdate"
										name="canUpdate" placeholder="canUpdate"
										ng-options="value for value in trueFalseValues"></select>
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !restrictionEntityDetailForm.canSearch.$valid, &#39;has-success&#39;: restrictionEntityDetailForm.canSearch.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">canSearch</span><select
										class="form-control " aria-describedby="sizing-addon3"
										type="checkbox" id="restrictionEntity-canSearch"
										ng-model="selectedEntity.canSearch"
										ng-readonly="restrictionList.restrictionEntity.restrictionFieldMap.canSearch"
										name="canSearch" placeholder="canSearch"
										ng-options="value for value in trueFalseValues"></select>
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">
					$('#restrictionEntityTabs a:first').tab('show');
				</script>
				<div class="panel-body">
					<div class="pull-left">
						<form id="restrictionEntityActionButton"
							ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default "
								ng-if="selectedEntity.restrictionEntityId==undefined"
								ng-show="(restrictionList.restrictionEntity==undefined || restrictionList.restrictionEntity.canCreate==true)">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.restrictionEntityId&gt;0"
								ng-show="(restrictionList.restrictionEntity==undefined || restrictionList.restrictionEntity.canUpdate==true)">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.restrictionEntityId&gt;0"
								ng-show="(restrictionList.restrictionEntity==undefined || restrictionList.restrictionEntity.canDelete==true)">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.restrictionEntityId&gt;0"
								ng-show="(restrictionList.restrictionEntity==undefined || restrictionList.restrictionEntity.canDelete==true)">Remove</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="restrictionEntityGroupController">
		<form id="restrictionEntityGroupList" ng-if="entityList.length&gt;0"
			enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">
					List restrictionEntityGroup
					<button ng-click="downloadEntityList()"
						class="btn btn-default pull-right" style="margin-top: -7px">
						<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
					</button>
				</div>
				<div class="panel-body">
					<div ui-grid="restrictionEntityGroupGridOptions"
						ui-grid-pagination="" ui-grid-selection="" ui-grid-exporter=""></div>
				</div>
			</div>
		</form>
		<form id="restrictionEntityGroupDetailForm"
			name="restrictionEntityGroupDetailForm" ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail restrictionEntityGroup {{
					selectedEntity.restrictionEntityGroupId }}</div>
				<div class="panel-body">
					<ul class="nav nav-tabs" role="tablist"
						id="restrictionEntityGroupTabs">
						<li role="presentation"><a
							href="#restrictionEntityGroup-Detail" aria-controls="Detail"
							role="tab" data-toggle="tab" ng-click="refreshTableDetail()">Detail</a></li>
						<script type="text/javascript">
							$('a[href="#restrictionEntityGroup-Detail"]')
									.on(
											'shown.bs.tab',
											function(e) {
												var target = $(e.target).attr(
														"href"); // activated tab
												//console.log(target);
												if (angular
														.element(
																$('#restrictionEntityGroupTabs'))
														.scope() != null
														&& angular
																.element(
																		$('#restrictionEntityGroupTabs'))
																.scope() != undefined)
													angular
															.element(
																	$('#restrictionEntityGroupTabs'))
															.scope()
															.refreshTableDetail();
											});
						</script>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade"
							id="restrictionEntityGroup-Detail">
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !restrictionEntityGroupDetailForm.restrictionEntityGroupId.$valid, &#39;has-success&#39;: restrictionEntityGroupDetailForm.restrictionEntityGroupId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">restrictionEntityGroupId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text"
										id="restrictionEntityGroup-restrictionEntityGroupId"
										ng-model="selectedEntity.restrictionEntityGroupId"
										ng-readonly="true" name="restrictionEntityGroupId"
										placeholder="restrictionEntityGroupId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !restrictionEntityGroupDetailForm.canCreate.$valid, &#39;has-success&#39;: restrictionEntityGroupDetailForm.canCreate.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">canCreate</span><select
										class="form-control " aria-describedby="sizing-addon3"
										type="checkbox" id="restrictionEntityGroup-canCreate"
										ng-model="selectedEntity.canCreate"
										ng-readonly="restrictionList.restrictionEntityGroup.restrictionFieldMap.canCreate"
										name="canCreate" placeholder="canCreate"
										ng-options="value for value in trueFalseValues"></select>
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !restrictionEntityGroupDetailForm.canUpdate.$valid, &#39;has-success&#39;: restrictionEntityGroupDetailForm.canUpdate.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">canUpdate</span><select
										class="form-control " aria-describedby="sizing-addon3"
										type="checkbox" id="restrictionEntityGroup-canUpdate"
										ng-model="selectedEntity.canUpdate"
										ng-readonly="restrictionList.restrictionEntityGroup.restrictionFieldMap.canUpdate"
										name="canUpdate" placeholder="canUpdate"
										ng-options="value for value in trueFalseValues"></select>
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !restrictionEntityGroupDetailForm.canSearch.$valid, &#39;has-success&#39;: restrictionEntityGroupDetailForm.canSearch.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">canSearch</span><select
										class="form-control " aria-describedby="sizing-addon3"
										type="checkbox" id="restrictionEntityGroup-canSearch"
										ng-model="selectedEntity.canSearch"
										ng-readonly="restrictionList.restrictionEntityGroup.restrictionFieldMap.canSearch"
										name="canSearch" placeholder="canSearch"
										ng-options="value for value in trueFalseValues"></select>
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !restrictionEntityGroupDetailForm.canDelete.$valid, &#39;has-success&#39;: restrictionEntityGroupDetailForm.canDelete.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">canDelete</span><select
										class="form-control " aria-describedby="sizing-addon3"
										type="checkbox" id="restrictionEntityGroup-canDelete"
										ng-model="selectedEntity.canDelete"
										ng-readonly="restrictionList.restrictionEntityGroup.restrictionFieldMap.canDelete"
										name="canDelete" placeholder="canDelete"
										ng-options="value for value in trueFalseValues"></select>
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !restrictionEntityGroupDetailForm.entityGroup.$valid, &#39;has-success&#39;: restrictionEntityGroupDetailForm.entityGroup.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">entityGroup</span><select
										class="form-control " aria-describedby="sizing-addon3"
										ng-model="selectedEntity.entityGroup" id="entityGroup"
										name="entityGroup"
										ng-options="entityGroup as  entityGroup.entityGroupId+&#39; &#39;+ entityGroup.name for entityGroup in childrenList.entityGroupList track by entityGroup.entityGroupId"
										enctype="UTF-8"></select><span class="input-group-btn"><button
											ng-click="showEntityGroupDetail()" class="btn btn-default "
											id="entityGroup" ng-if="selectedEntity.entityGroup==null">Add
											new entityGroup</button>
										<button ng-click="showEntityGroupDetail()"
											class="btn btn-default " id="entityGroup"
											ng-if="selectedEntity.entityGroup!=null">Show detail</button></span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">
					$('#restrictionEntityGroupTabs a:first').tab('show');
				</script>
				<div class="panel-body">
					<div class="pull-left">
						<form id="restrictionEntityGroupActionButton"
							ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default "
								ng-if="selectedEntity.restrictionEntityGroupId==undefined"
								ng-show="(restrictionList.restrictionEntityGroup==undefined || restrictionList.restrictionEntityGroup.canCreate==true)">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.restrictionEntityGroupId&gt;0"
								ng-show="(restrictionList.restrictionEntityGroup==undefined || restrictionList.restrictionEntityGroup.canUpdate==true)">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.restrictionEntityGroupId&gt;0"
								ng-show="(restrictionList.restrictionEntityGroup==undefined || restrictionList.restrictionEntityGroup.canDelete==true)">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.restrictionEntityGroupId&gt;0"
								ng-show="(restrictionList.restrictionEntityGroup==undefined || restrictionList.restrictionEntityGroup.canDelete==true)">Remove</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="entityGroupController">
		<form id="entityGroupList" ng-if="entityList.length&gt;0"
			enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">
					List entityGroup
					<button ng-click="downloadEntityList()"
						class="btn btn-default pull-right" style="margin-top: -7px">
						<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
					</button>
				</div>
				<div class="panel-body">
					<div ui-grid="entityGroupGridOptions" ui-grid-pagination=""
						ui-grid-selection="" ui-grid-exporter=""></div>
				</div>
			</div>
		</form>
		<form id="entityGroupDetailForm" name="entityGroupDetailForm"
			ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail entityGroup {{
					selectedEntity.entityGroupId }}</div>
				<div class="panel-body">
					<ul class="nav nav-tabs" role="tablist" id="entityGroupTabs">
						<li role="presentation"><a href="#entityGroup-Detail"
							aria-controls="Detail" role="tab" data-toggle="tab"
							ng-click="refreshTableDetail()">Detail</a></li>
						<script type="text/javascript">
							$('a[href="#entityGroup-Detail"]')
									.on(
											'shown.bs.tab',
											function(e) {
												var target = $(e.target).attr(
														"href"); // activated tab
												//console.log(target);
												if (angular.element(
														$('#entityGroupTabs'))
														.scope() != null
														&& angular
																.element(
																		$('#entityGroupTabs'))
																.scope() != undefined)
													angular
															.element(
																	$('#entityGroupTabs'))
															.scope()
															.refreshTableDetail();
											});
						</script>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade" id="entityGroup-Detail">
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !entityGroupDetailForm.entityGroupId.$valid, &#39;has-success&#39;: entityGroupDetailForm.entityGroupId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">entityGroupId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="entityGroup-entityGroupId"
										ng-model="selectedEntity.entityGroupId" ng-readonly="true"
										name="entityGroupId" placeholder="entityGroupId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !entityGroupDetailForm.name.$valid, &#39;has-success&#39;: entityGroupDetailForm.name.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">name</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="entityGroup-name"
										ng-model="selectedEntity.name"
										ng-readonly="restrictionList.entityGroup.restrictionFieldMap.name"
										name="name" placeholder="name" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !entityGroupDetailForm.entityId.$valid, &#39;has-success&#39;: entityGroupDetailForm.entityId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">entityId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="entityGroup-entityId"
										ng-model="selectedEntity.entityId"
										ng-readonly="restrictionList.entityGroup.restrictionFieldMap.entityId"
										name="entityId" placeholder="entityId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !entityGroupDetailForm.project.$valid, &#39;has-success&#39;: entityGroupDetailForm.project.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">project</span><select
										class="form-control " aria-describedby="sizing-addon3"
										ng-model="selectedEntity.project" id="project" name="project"
										ng-options="project as  project.name+&#39; &#39;+ project.projectId for project in childrenList.projectList track by project.projectId"
										enctype="UTF-8"></select><span class="input-group-btn"><button
											ng-click="showProjectDetail()" class="btn btn-default "
											id="project" ng-if="selectedEntity.project==null">Add
											new project</button>
										<button ng-click="showProjectDetail()"
											class="btn btn-default " id="project"
											ng-if="selectedEntity.project!=null">Show detail</button></span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">
					$('#entityGroupTabs a:first').tab('show');
				</script>
				<div class="panel-body">
					<div class="pull-left">
						<form id="entityGroupActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default "
								ng-if="selectedEntity.entityGroupId==undefined"
								ng-show="(restrictionList.entityGroup==undefined || restrictionList.entityGroup.canCreate==true)">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.entityGroupId&gt;0"
								ng-show="(restrictionList.entityGroup==undefined || restrictionList.entityGroup.canUpdate==true)">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.entityGroupId&gt;0"
								ng-show="(restrictionList.entityGroup==undefined || restrictionList.entityGroup.canDelete==true)">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.entityGroupId&gt;0"
								ng-show="(restrictionList.entityGroup==undefined || restrictionList.entityGroup.canDelete==true)">Remove</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="projectController">
		<form id="projectList" ng-if="entityList.length&gt;0" enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">
					List project
					<button ng-click="downloadEntityList()"
						class="btn btn-default pull-right" style="margin-top: -7px">
						<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
					</button>
				</div>
				<div class="panel-body">
					<div ui-grid="projectGridOptions" ui-grid-pagination=""
						ui-grid-selection="" ui-grid-exporter=""></div>
				</div>
			</div>
		</form>
		<form id="projectDetailForm" name="projectDetailForm"
			ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail project {{
					selectedEntity.projectId }}</div>
				<div class="panel-body">
					<ul class="nav nav-tabs" role="tablist" id="projectTabs">
						<li role="presentation"><a href="#project-Detail"
							aria-controls="Detail" role="tab" data-toggle="tab"
							ng-click="refreshTableDetail()">Detail</a></li>
						<script type="text/javascript">
							$('a[href="#project-Detail"]').on(
									'shown.bs.tab',
									function(e) {
										var target = $(e.target).attr("href"); // activated tab
										//console.log(target);
										if (angular.element($('#projectTabs'))
												.scope() != null
												&& angular.element(
														$('#projectTabs'))
														.scope() != undefined)
											angular.element($('#projectTabs'))
													.scope()
													.refreshTableDetail();
									});
						</script>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade" id="project-Detail">
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !projectDetailForm.projectId.$valid, &#39;has-success&#39;: projectDetailForm.projectId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">projectId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="project-projectId"
										ng-model="selectedEntity.projectId" ng-readonly="true"
										name="projectId" placeholder="projectId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !projectDetailForm.name.$valid, &#39;has-success&#39;: projectDetailForm.name.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">name</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="project-name" ng-model="selectedEntity.name"
										ng-readonly="restrictionList.project.restrictionFieldMap.name"
										name="name" placeholder="name" />
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">$('#projectTabs a:first').tab('show');</script>
				<div class="panel-body">
					<div class="pull-left">
						<form id="projectActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default "
								ng-if="selectedEntity.projectId==undefined"
								ng-show="(restrictionList.project==undefined || restrictionList.project.canCreate==true)">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.projectId&gt;0"
								ng-show="(restrictionList.project==undefined || restrictionList.project.canUpdate==true)">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.projectId&gt;0"
								ng-show="(restrictionList.project==undefined || restrictionList.project.canDelete==true)">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.projectId&gt;0"
								ng-show="(restrictionList.project==undefined || restrictionList.project.canDelete==true)">Remove</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="tabController">
		<form id="tabList" ng-if="entityList.length&gt;0" enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">
					List tab
					<button ng-click="downloadEntityList()"
						class="btn btn-default pull-right" style="margin-top: -7px">
						<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
					</button>
				</div>
				<div class="panel-body">
					<div ui-grid="tabGridOptions" ui-grid-pagination=""
						ui-grid-selection="" ui-grid-exporter=""></div>
				</div>
			</div>
		</form>
		<form id="tabDetailForm" name="tabDetailForm"
			ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail tab {{ selectedEntity.tabId
					}}</div>
				<div class="panel-body">
					<ul class="nav nav-tabs" role="tablist" id="tabTabs">
						<li role="presentation"><a href="#tab-Detail"
							aria-controls="Detail" role="tab" data-toggle="tab"
							ng-click="refreshTableDetail()">Detail</a></li>
						<script type="text/javascript">$('a[href="#tab-Detail"]').on('shown.bs.tab', function (e) {
var target = $(e.target).attr("href"); // activated tab
//console.log(target);
if (angular.element($('#tabTabs')).scope()!=null && angular.element($('#tabTabs')).scope()!=undefined) 
angular.element($('#tabTabs')).scope().refreshTableDetail();
});
</script>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade" id="tab-Detail">
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !tabDetailForm.tabId.$valid, &#39;has-success&#39;: tabDetailForm.tabId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">tabId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="tab-tabId" ng-model="selectedEntity.tabId"
										ng-readonly="true" name="tabId" placeholder="tabId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !tabDetailForm.name.$valid, &#39;has-success&#39;: tabDetailForm.name.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">name</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="tab-name" ng-model="selectedEntity.name"
										ng-readonly="restrictionList.tab.restrictionFieldMap.name"
										name="name" placeholder="name" />
								</div>
							</div>
							<div id="tab-enumField" class="modal fade" role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Link existing enumField</h4>
										</div>
										<div class="modal-body">
											<p></p>
											<div class="input-group">
												<span class="input-group-addon">enumField</span><select
													class="form-control " aria-describedby="sizing-addon3"
													ng-model="selectedEntity.enumField" id="enumField"
													name="enumField"
													ng-options="enumField as  enumField.name+&#39; &#39;+ enumField.enumFieldId for enumField in childrenList.enumFieldList track by enumField.enumFieldId"
													enctype="UTF-8"></select><span class="input-group-btn"><button
														ng-click="saveLinkedEnumField()" class="btn btn-default "
														id="enumField">Save</button></span>
											</div>
										</div>
										<div class="modal-footer">
											<button ng-click="close()" class="btn btn-default "
												data-dismiss="modal">close</button>
										</div>
									</div>
								</div>
							</div>
							<br />
							<br />
							<div class="pull-left" style="width: 100%"
								ng-show="true  && (restrictionList.enumField==undefined || restrictionList.enumField.canSearch==true)">
								<div class="panel panel-default default-panel">
									<div class="panel-heading">
										enumField
										<button ng-click="showEnumFieldDetail()"
											class="btn btn-default  pull-right" style="margin-top: -7px"
											ng-show="(restrictionList.enumField==undefined || restrictionList.enumField.canCreate==true)">Add
											new enumField</button>
										<button type="button" class="btn btn-default pull-right"
											style="margin-top: -7px" data-toggle="modal"
											data-target="#tab-enumField">Link existing</button>
										<button ng-click="downloadEnumFieldList()"
											class="btn btn-default pull-right" style="margin-top: -7px">
											<span class="glyphicon glyphicon-download-alt"
												aria-hidden="true"></span>
										</button>
									</div>
									<div class="panel-body"
										ng-class="{&#39;has-error&#39;: !tabDetailForm.enumField.$valid, &#39;has-success&#39;: tabDetailForm.enumField.$valid}">
										<label id="enumField">enumField</label>
										<div id="enumField"
											ng-if="selectedEntity.enumFieldList.length&gt;0">
											<div style="top: 100px" ui-grid="enumFieldListGridOptions"
												ui-grid-pagination="" ui-grid-selection=""></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">$('#tabTabs a:first').tab('show');</script>
				<div class="panel-body">
					<div class="pull-left">
						<form id="tabActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default "
								ng-if="selectedEntity.tabId==undefined"
								ng-show="(restrictionList.tab==undefined || restrictionList.tab.canCreate==true)">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.tabId&gt;0"
								ng-show="(restrictionList.tab==undefined || restrictionList.tab.canUpdate==true)">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.tabId&gt;0"
								ng-show="(restrictionList.tab==undefined || restrictionList.tab.canDelete==true)">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.tabId&gt;0"
								ng-show="(restrictionList.tab==undefined || restrictionList.tab.canDelete==true)">Remove</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="enumFieldController">
		<form id="enumFieldList" ng-if="entityList.length&gt;0"
			enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">
					List enumField
					<button ng-click="downloadEntityList()"
						class="btn btn-default pull-right" style="margin-top: -7px">
						<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
					</button>
				</div>
				<div class="panel-body">
					<div ui-grid="enumFieldGridOptions" ui-grid-pagination=""
						ui-grid-selection="" ui-grid-exporter=""></div>
				</div>
			</div>
		</form>
		<form id="enumFieldDetailForm" name="enumFieldDetailForm"
			ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail enumField {{
					selectedEntity.enumFieldId }}</div>
				<div class="panel-body">
					<ul class="nav nav-tabs" role="tablist" id="enumFieldTabs">
						<li role="presentation"><a href="#enumField-Detail"
							aria-controls="Detail" role="tab" data-toggle="tab"
							ng-click="refreshTableDetail()">Detail</a></li>
						<script type="text/javascript">$('a[href="#enumField-Detail"]').on('shown.bs.tab', function (e) {
var target = $(e.target).attr("href"); // activated tab
//console.log(target);
if (angular.element($('#enumFieldTabs')).scope()!=null && angular.element($('#enumFieldTabs')).scope()!=undefined) 
angular.element($('#enumFieldTabs')).scope().refreshTableDetail();
});
</script>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade" id="enumField-Detail">
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !enumFieldDetailForm.enumFieldId.$valid, &#39;has-success&#39;: enumFieldDetailForm.enumFieldId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">enumFieldId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="enumField-enumFieldId"
										ng-model="selectedEntity.enumFieldId" ng-readonly="true"
										name="enumFieldId" placeholder="enumFieldId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !enumFieldDetailForm.name.$valid, &#39;has-success&#39;: enumFieldDetailForm.name.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">name</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="enumField-name" ng-model="selectedEntity.name"
										ng-readonly="restrictionList.enumField.restrictionFieldMap.name"
										name="name" placeholder="name" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !enumFieldDetailForm.priority.$valid, &#39;has-success&#39;: enumFieldDetailForm.priority.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">priority</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="enumField-priority"
										ng-model="selectedEntity.priority"
										ng-readonly="restrictionList.enumField.restrictionFieldMap.priority"
										name="priority" placeholder="priority" />
								</div>
							</div>
							<div id="enumField-enumValue" class="modal fade" role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Link existing enumValue</h4>
										</div>
										<div class="modal-body">
											<p></p>
											<div class="input-group">
												<span class="input-group-addon">enumValue</span><select
													class="form-control " aria-describedby="sizing-addon3"
													ng-model="selectedEntity.enumValue" id="enumValue"
													name="enumValue"
													ng-options="enumValue as  enumValue.name+&#39; &#39;+ enumValue.enumValueId for enumValue in childrenList.enumValueList track by enumValue.enumValueId"
													enctype="UTF-8"></select><span class="input-group-btn"><button
														ng-click="saveLinkedEnumValue()" class="btn btn-default "
														id="enumValue">Save</button></span>
											</div>
										</div>
										<div class="modal-footer">
											<button ng-click="close()" class="btn btn-default "
												data-dismiss="modal">close</button>
										</div>
									</div>
								</div>
							</div>
							<br />
							<br />
							<div class="pull-left" style="width: 100%"
								ng-show="true  && (restrictionList.enumValue==undefined || restrictionList.enumValue.canSearch==true)">
								<div class="panel panel-default default-panel">
									<div class="panel-heading">
										enumValue
										<button ng-click="showEnumValueDetail()"
											class="btn btn-default  pull-right" style="margin-top: -7px"
											ng-show="(restrictionList.enumValue==undefined || restrictionList.enumValue.canCreate==true)">Add
											new enumValue</button>
										<button type="button" class="btn btn-default pull-right"
											style="margin-top: -7px" data-toggle="modal"
											data-target="#enumField-enumValue">Link existing</button>
										<button ng-click="downloadEnumValueList()"
											class="btn btn-default pull-right" style="margin-top: -7px">
											<span class="glyphicon glyphicon-download-alt"
												aria-hidden="true"></span>
										</button>
									</div>
									<div class="panel-body"
										ng-class="{&#39;has-error&#39;: !enumFieldDetailForm.enumValue.$valid, &#39;has-success&#39;: enumFieldDetailForm.enumValue.$valid}">
										<label id="enumValue">enumValue</label>
										<div id="enumValue"
											ng-if="selectedEntity.enumValueList.length&gt;0">
											<div style="top: 100px" ui-grid="enumValueListGridOptions"
												ui-grid-pagination="" ui-grid-selection=""></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">$('#enumFieldTabs a:first').tab('show');</script>
				<div class="panel-body">
					<div class="pull-left">
						<form id="enumFieldActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default "
								ng-if="selectedEntity.enumFieldId==undefined"
								ng-show="(restrictionList.enumField==undefined || restrictionList.enumField.canCreate==true)">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.enumFieldId&gt;0"
								ng-show="(restrictionList.enumField==undefined || restrictionList.enumField.canUpdate==true)">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.enumFieldId&gt;0"
								ng-show="(restrictionList.enumField==undefined || restrictionList.enumField.canDelete==true)">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.enumFieldId&gt;0"
								ng-show="(restrictionList.enumField==undefined || restrictionList.enumField.canDelete==true)">Remove</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="enumValueController">
		<form id="enumValueList" ng-if="entityList.length&gt;0"
			enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">
					List enumValue
					<button ng-click="downloadEntityList()"
						class="btn btn-default pull-right" style="margin-top: -7px">
						<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
					</button>
				</div>
				<div class="panel-body">
					<div ui-grid="enumValueGridOptions" ui-grid-pagination=""
						ui-grid-selection="" ui-grid-exporter=""></div>
				</div>
			</div>
		</form>
		<form id="enumValueDetailForm" name="enumValueDetailForm"
			ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail enumValue {{
					selectedEntity.enumValueId }}</div>
				<div class="panel-body">
					<ul class="nav nav-tabs" role="tablist" id="enumValueTabs">
						<li role="presentation"><a href="#enumValue-Detail"
							aria-controls="Detail" role="tab" data-toggle="tab"
							ng-click="refreshTableDetail()">Detail</a></li>
						<script type="text/javascript">$('a[href="#enumValue-Detail"]').on('shown.bs.tab', function (e) {
var target = $(e.target).attr("href"); // activated tab
//console.log(target);
if (angular.element($('#enumValueTabs')).scope()!=null && angular.element($('#enumValueTabs')).scope()!=undefined) 
angular.element($('#enumValueTabs')).scope().refreshTableDetail();
});
</script>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade" id="enumValue-Detail">
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !enumValueDetailForm.enumValueId.$valid, &#39;has-success&#39;: enumValueDetailForm.enumValueId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">enumValueId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="enumValue-enumValueId"
										ng-model="selectedEntity.enumValueId" ng-readonly="true"
										name="enumValueId" placeholder="enumValueId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !enumValueDetailForm.value.$valid, &#39;has-success&#39;: enumValueDetailForm.value.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">value</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="enumValue-value"
										ng-model="selectedEntity.value"
										ng-readonly="restrictionList.enumValue.restrictionFieldMap.value"
										name="value" placeholder="value" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !enumValueDetailForm.name.$valid, &#39;has-success&#39;: enumValueDetailForm.name.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">name</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="enumValue-name" ng-model="selectedEntity.name"
										ng-readonly="restrictionList.enumValue.restrictionFieldMap.name"
										name="name" placeholder="name" />
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">$('#enumValueTabs a:first').tab('show');</script>
				<div class="panel-body">
					<div class="pull-left">
						<form id="enumValueActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default "
								ng-if="selectedEntity.enumValueId==undefined"
								ng-show="(restrictionList.enumValue==undefined || restrictionList.enumValue.canCreate==true)">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.enumValueId&gt;0"
								ng-show="(restrictionList.enumValue==undefined || restrictionList.enumValue.canUpdate==true)">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.enumValueId&gt;0"
								ng-show="(restrictionList.enumValue==undefined || restrictionList.enumValue.canDelete==true)">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.enumValueId&gt;0"
								ng-show="(restrictionList.enumValue==undefined || restrictionList.enumValue.canDelete==true)">Remove</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">loadMenu(); </script>
</body>
</html>