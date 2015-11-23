<!DOCTYPE html>
<html>
<head>
<title>field</title>
<script type="text/javascript" src="../js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../js/jquery-ui.js"></script>
<script type="text/javascript" src="../js/angular.js"></script>
<script type="text/javascript" src="../js/angular-touch.js"></script>
<script type="text/javascript" src="../js/angular-animate.js"></script>
<script type="text/javascript" src="../js/csv.js"></script>
<script type="text/javascript" src="../js/pdfmake.js"></script>
<script type="text/javascript" src="../js/vfs_fonts.js"></script>
<script type="text/javascript" src="../js/ui-grid.js"></script>
<script type="text/javascript" src="../js/angular/field.js"></script>
<script type="text/javascript" src="../js/date.js"></script>
<script type="text/javascript" src="../js/utility.js"></script>
<script type="text/javascript" src="../js/jquery.easytree.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/alasql.min.js"></script>
<link rel="stylesheet" href="../css/ui-grid.css" />
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/main.css" />
<link rel="stylesheet" href="../css/jquery-ui.css" />
<link rel="stylesheet" href="../css/easytree/skin-win8/ui.easytree.css" />
<link rel="import" href="../menu.jsp" />
</head>
<body ng-app="fieldApp">
	<div ng-controller="fieldController">
		<form id="fieldSearchBean">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Search form field</div>
				<div class="panel-body">
					<div class="pull-left right-input">
						<div class="input-group">
							<span class="input-group-addon">fieldId</span><input
								class="form-control " aria-describedby="sizing-addon3"
								type="text" id="field-fieldId" ng-model="searchBean.fieldId"
								ng-readonly="false" name="fieldId" placeholder="fieldId" />
						</div>
					</div>
					<div class="pull-left right-input">
						<div class="input-group">
							<span class="input-group-addon">name</span><input
								class="form-control " aria-describedby="sizing-addon3"
								type="text" id="field-name" ng-model="searchBean.name"
								ng-readonly="false" name="name" placeholder="name" />
						</div>
					</div>
					<div class="pull-left right-input" style="height: 59px;">
						<div class="input-group">
							<span class="input-group-addon">entity</span><select
								class="form-control " aria-describedby="sizing-addon3"
								ng-model="searchBean.entity.entityId" id="entity"
								ng-options="entity.entityId as  entity.entityId+&#39; &#39;+ entity.name for entity in childrenList.entityList"
								enctype="UTF-8"></select>
						</div>
					</div>
					<div class="pull-left right-input">
						<div class="input-group">
							<span class="input-group-addon">fieldType</span><select
								class="form-control pull-left" aria-describedby="sizing-addon3"
								type="text" id="field-fieldType" ng-model="searchBean.fieldType"
								ng-readonly="false" name="fieldType" placeholder="fieldType"
								ng-options="fieldType as fieldType for fieldType in childrenList.fieldTypeList"
								enctype="UTF-8"></select>
						</div>
					</div>
					<div class="pull-left right-input" style="height: 59px;">
						<div class="input-group">
							<span class="input-group-addon">annotation</span><select
								class="form-control " aria-describedby="sizing-addon3"
								ng-model="searchBean.annotation.annotationId" id="annotation"
								ng-options="annotation.annotationId as annotation.annotationId for annotation in childrenList.annotationList"
								enctype="UTF-8"></select>
						</div>
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-body">
						<div class="pull-left right-input">
							<button ng-click="addNew()" class="btn btn-default ">Add
								new</button>
							<button ng-click="search()" class="btn btn-default ">Find</button>
							<button ng-click="reset()" class="btn btn-default ">Reset</button>
						</div>
					</div>
				</div>
			</div>
		</form>
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
								style="height: 59px">
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
								style="height: 59px">
								<div class="input-group">
									<span class="input-group-addon">name</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="field-name" ng-model="selectedEntity.name"
										ng-readonly="false" name="name" placeholder="name" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !fieldDetailForm.entity.$valid, &#39;has-success&#39;: fieldDetailForm.entity.$valid}"
								style="height: 59px">
								<div class="input-group">
									<span class="input-group-addon">entity</span><select
										class="form-control " aria-describedby="sizing-addon3"
										ng-model="selectedEntity.entity" id="entity" name="entity"
										ng-options="entity as  entity.entityId+&#39; &#39;+ entity.name for entity in childrenList.entityList track by entity.entityId"
										enctype="UTF-8"></select><span class="input-group-btn"><button
											ng-click="showEntityDetail()" class="btn btn-default "
											id="entity" ng-if="selectedEntity.entity==null">Add
											new entity</button>
										<button ng-click="showEntityDetail()" class="btn btn-default "
											id="entity" ng-if="selectedEntity.entity!=null">Show
											detail</button></span>
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !fieldDetailForm.fieldType.$valid, &#39;has-success&#39;: fieldDetailForm.fieldType.$valid}"
								style="height: 59px">
								<div class="input-group">
									<span class="input-group-addon">fieldType</span><select
										class="form-control pull-left"
										aria-describedby="sizing-addon3" type="text"
										id="field-fieldType" ng-model="selectedEntity.fieldType"
										ng-readonly="false" name="fieldType" placeholder="fieldType"
										ng-options="fieldType as fieldType for fieldType in childrenList.fieldTypeList"
										enctype="UTF-8"></select>
								</div>
							</div>
							<div id="field-annotation" class="modal fade" role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Modal header</h4>
										</div>
										<div class="modal-body">
											<p>some text</p>
											<div class="input-group">
												<span class="input-group-addon">annotation</span><select
													class="form-control " aria-describedby="sizing-addon3"
													ng-model="selectedEntity.annotation" id="annotation"
													name="annotation"
													ng-options="annotation as annotation.annotationId for annotation in childrenList.annotationList track by annotation.annotationId"
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
							<div class="pull-left" style="width: 100%">
								<div class="panel panel-default default-panel">
									<div class="panel-heading">
										annotation
										<button ng-click="showAnnotationDetail()"
											class="btn btn-default  pull-right" style="margin-top: -7px">Add
											new annotation</button>
										<button type="button" class="btn btn-default pull-right"
											style="margin-top: -7px" data-toggle="modal"
											data-target="#field-annotation">Link existing</button>
										<button ng-click="downloadAnnotationList()"
											class="btn btn-default pull-right" style="margin-top: -7px">
											<span class="glyphicon glyphicon-download-alt"
												aria-hidden="true"></span>
										</button>
									</div>
									<div class="panel-body"
										ng-class="{&#39;has-error&#39;: !fieldDetailForm.annotation.$valid, &#39;has-success&#39;: fieldDetailForm.annotation.$valid}">
										<label id="annotation">annotation</label>
										<div id="annotation"
											ng-if="selectedEntity.annotationList.length&gt;0">
											<div style="top: 100px" ui-grid="annotationListGridOptions"
												ui-grid-pagination="" ui-grid-selection=""></div>
										</div>
									</div>
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
								ng-if="selectedEntity.fieldId==undefined">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.fieldId&gt;0">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.fieldId&gt;0">Delete</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="entityController">
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
								style="height: 59px">
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
								style="height: 59px">
								<div class="input-group">
									<span class="input-group-addon">name</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="entity-name" ng-model="selectedEntity.name"
										ng-readonly="false" name="name" placeholder="name" />
								</div>
							</div>
							<div id="entity-relationship" class="modal fade" role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Modal header</h4>
										</div>
										<div class="modal-body">
											<p>some text</p>
											<div class="input-group">
												<span class="input-group-addon">relationship</span><select
													class="form-control " aria-describedby="sizing-addon3"
													ng-model="selectedEntity.relationship" id="relationship"
													name="relationship"
													ng-options="relationship as relationship.relationshipId for relationship in childrenList.relationshipList track by relationship.relationshipId"
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
							<div class="pull-left" style="width: 100%">
								<div class="panel panel-default default-panel">
									<div class="panel-heading">
										relationship
										<button ng-click="showRelationshipDetail()"
											class="btn btn-default  pull-right" style="margin-top: -7px">Add
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
											<h4 class="modal-title">Modal header</h4>
										</div>
										<div class="modal-body">
											<p>some text</p>
											<div class="input-group">
												<span class="input-group-addon">enumField</span><select
													class="form-control " aria-describedby="sizing-addon3"
													ng-model="selectedEntity.enumField" id="enumField"
													name="enumField"
													ng-options="enumField as enumField.enumFieldId for enumField in childrenList.enumFieldList track by enumField.enumFieldId"
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
							<div class="pull-left" style="width: 100%">
								<div class="panel panel-default default-panel">
									<div class="panel-heading">
										enumField
										<button ng-click="showEnumFieldDetail()"
											class="btn btn-default  pull-right" style="margin-top: -7px">Add
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
								ng-if="selectedEntity.entityId==undefined">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.entityId&gt;0">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.entityId&gt;0">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.entityId&gt;0">Remove</button>
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
								style="height: 59px">
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
								style="height: 59px">
								<div class="input-group">
									<span class="input-group-addon">annotationType</span><select
										class="form-control pull-left"
										aria-describedby="sizing-addon3" type="text"
										id="annotation-annotationType"
										ng-model="selectedEntity.annotationType" ng-readonly="false"
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
											<h4 class="modal-title">Modal header</h4>
										</div>
										<div class="modal-body">
											<p>some text</p>
											<div class="input-group">
												<span class="input-group-addon">annotationAttribute</span><select
													class="form-control " aria-describedby="sizing-addon3"
													ng-model="selectedEntity.annotationAttribute"
													id="annotationAttribute" name="annotationAttribute"
													ng-options="annotationAttribute as annotationAttribute.annotationAttributeId for annotationAttribute in childrenList.annotationAttributeList track by annotationAttribute.annotationAttributeId"
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
							<div class="pull-left" style="width: 100%">
								<div class="panel panel-default default-panel">
									<div class="panel-heading">
										annotationAttribute
										<button ng-click="showAnnotationAttributeDetail()"
											class="btn btn-default  pull-right" style="margin-top: -7px">Add
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
								ng-class="{&#39;has-error&#39;: !annotationDetailForm.relationship.$valid, &#39;has-success&#39;: annotationDetailForm.relationship.$valid}"
								style="height: 59px">
								<div class="input-group">
									<span class="input-group-addon">relationship</span><select
										class="form-control " aria-describedby="sizing-addon3"
										ng-model="selectedEntity.relationship" id="relationship"
										name="relationship"
										ng-options="relationship as relationship.relationshipId for relationship in childrenList.relationshipList track by relationship.relationshipId"
										enctype="UTF-8"></select><span class="input-group-btn"><button
											ng-click="showRelationshipDetail()" class="btn btn-default "
											id="relationship" ng-if="selectedEntity.relationship==null">Add
											new relationship</button>
										<button ng-click="showRelationshipDetail()"
											class="btn btn-default " id="relationship"
											ng-if="selectedEntity.relationship!=null">Show
											detail</button></span>
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
								ng-if="selectedEntity.annotationId==undefined">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.annotationId&gt;0">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.annotationId&gt;0">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.annotationId&gt;0">Remove</button>
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
								style="height: 59px">
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
								style="height: 59px">
								<div class="input-group">
									<span class="input-group-addon">name</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="relationship-name"
										ng-model="selectedEntity.name" ng-readonly="false" name="name"
										placeholder="name" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !relationshipDetailForm.relationshipType.$valid, &#39;has-success&#39;: relationshipDetailForm.relationshipType.$valid}"
								style="height: 59px">
								<div class="input-group">
									<span class="input-group-addon">relationshipType</span><select
										class="form-control pull-left"
										aria-describedby="sizing-addon3" type="text"
										id="relationship-relationshipType"
										ng-model="selectedEntity.relationshipType" ng-readonly="false"
										name="relationshipType" placeholder="relationshipType"
										ng-options="relationshipType as relationshipType for relationshipType in childrenList.relationshipTypeList"
										enctype="UTF-8"></select>
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
								ng-if="selectedEntity.relationshipId==undefined">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.relationshipId&gt;0">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.relationshipId&gt;0">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.relationshipId&gt;0">Remove</button>
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
						<script type="text/javascript">
							$('a[href="#enumField-Detail"]')
									.on(
											'shown.bs.tab',
											function(e) {
												var target = $(e.target).attr(
														"href"); // activated tab
												//console.log(target);
												if (angular.element(
														$('#enumFieldTabs'))
														.scope() != null
														&& angular
																.element(
																		$('#enumFieldTabs'))
																.scope() != undefined)
													angular
															.element(
																	$('#enumFieldTabs'))
															.scope()
															.refreshTableDetail();
											});
						</script>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade" id="enumField-Detail">
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !enumFieldDetailForm.enumFieldId.$valid, &#39;has-success&#39;: enumFieldDetailForm.enumFieldId.$valid}"
								style="height: 59px">
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
								style="height: 59px">
								<div class="input-group">
									<span class="input-group-addon">name</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="enumField-name" ng-model="selectedEntity.name"
										ng-readonly="false" name="name" placeholder="name" />
								</div>
							</div>
							<div id="enumField-enumValue" class="modal fade" role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Modal header</h4>
										</div>
										<div class="modal-body">
											<p>some text</p>
											<div class="input-group">
												<span class="input-group-addon">enumValue</span><select
													class="form-control " aria-describedby="sizing-addon3"
													ng-model="selectedEntity.enumValue" id="enumValue"
													name="enumValue"
													ng-options="enumValue as enumValue.enumValueId for enumValue in childrenList.enumValueList track by enumValue.enumValueId"
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
							<div class="pull-left" style="width: 100%">
								<div class="panel panel-default default-panel">
									<div class="panel-heading">
										enumValue
										<button ng-click="showEnumValueDetail()"
											class="btn btn-default  pull-right" style="margin-top: -7px">Add
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
				<script type="text/javascript">
					$('#enumFieldTabs a:first').tab('show');
				</script>
				<div class="panel-body">
					<div class="pull-left">
						<form id="enumFieldActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default "
								ng-if="selectedEntity.enumFieldId==undefined">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.enumFieldId&gt;0">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.enumFieldId&gt;0">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.enumFieldId&gt;0">Remove</button>
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
								style="height: 59px">
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
								style="height: 59px">
								<div class="input-group">
									<span class="input-group-addon">value</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="enumValue-value"
										ng-model="selectedEntity.value" ng-readonly="false"
										name="value" placeholder="value" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !enumValueDetailForm.name.$valid, &#39;has-success&#39;: enumValueDetailForm.name.$valid}"
								style="height: 59px">
								<div class="input-group">
									<span class="input-group-addon">name</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="enumValue-name" ng-model="selectedEntity.name"
										ng-readonly="false" name="name" placeholder="name" />
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
								ng-if="selectedEntity.enumValueId==undefined">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.enumValueId&gt;0">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.enumValueId&gt;0">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.enumValueId&gt;0">Remove</button>
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
						<script type="text/javascript">$('a[href="#annotationAttribute-Detail"]').on('shown.bs.tab', function (e) {
var target = $(e.target).attr("href"); // activated tab
//console.log(target);
if (angular.element($('#annotationAttributeTabs')).scope()!=null && angular.element($('#annotationAttributeTabs')).scope()!=undefined) 
angular.element($('#annotationAttributeTabs')).scope().refreshTableDetail();
});
</script>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade"
							id="annotationAttribute-Detail">
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !annotationAttributeDetailForm.annotationAttributeId.$valid, &#39;has-success&#39;: annotationAttributeDetailForm.annotationAttributeId.$valid}"
								style="height: 59px">
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
								style="height: 59px">
								<div class="input-group">
									<span class="input-group-addon">property</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="annotationAttribute-property"
										ng-model="selectedEntity.property" ng-readonly="false"
										name="property" placeholder="property" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !annotationAttributeDetailForm.value.$valid, &#39;has-success&#39;: annotationAttributeDetailForm.value.$valid}"
								style="height: 59px">
								<div class="input-group">
									<span class="input-group-addon">value</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="annotationAttribute-value"
										ng-model="selectedEntity.value" ng-readonly="false"
										name="value" placeholder="value" />
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">$('#annotationAttributeTabs a:first').tab('show');</script>
				<div class="panel-body">
					<div class="pull-left">
						<form id="annotationAttributeActionButton"
							ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default "
								ng-if="selectedEntity.annotationAttributeId==undefined">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.annotationAttributeId&gt;0">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.annotationAttributeId&gt;0">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.annotationAttributeId&gt;0">Remove</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">loadMenu();  activeMenu("field");</script>
</body>
</html>