<!DOCTYPE html>
<html>
<head>
<title>place</title>
<script type="text/javascript" src="../js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../js/jquery-ui.js"></script>
<script type="text/javascript" src="../js/angular.js"></script>
<script type="text/javascript" src="../js/angular-touch.js"></script>
<script type="text/javascript" src="../js/angular-animate.js"></script>
<script type="text/javascript" src="../js/csv.js"></script>
<script type="text/javascript" src="../js/pdfmake.js"></script>
<script type="text/javascript" src="../js/vfs_fonts.js"></script>
<script type="text/javascript" src="../js/ui-grid.js"></script>
<script type="text/javascript" src="../js/angular/test/place.js"></script>
<script type="text/javascript" src="../js/date.js"></script>
<script type="text/javascript" src="../js/utility.js"></script>
<script type="text/javascript" src="../js/jquery.easytree.js"></script>
<script type="text/javascript" src="../js/jquery.cookie.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/alasql.min.js"></script>
<link rel="stylesheet" href="../css/ui-grid.css" />
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/main.css" />
<link rel="stylesheet" href="../css/jquery-ui.css" />
<link rel="stylesheet" href="../css/easytree/skin-win8/ui.easytree.css" />
<link rel="import" href="../testMenu.jsp" />
</head>
<body ng-app="placeApp">
	<div ng-controller="placeController">
		<form id="placeSearchBean">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Search form place</div>
				<div class="panel-body">
					<div class="pull-left right-input" ng-show="true ">
						<div class="input-group">
							<span class="input-group-addon">entityId</span><input
								class="form-control " aria-describedby="sizing-addon3"
								type="text" id="place-entityId" ng-model="searchBean.entityId"
								ng-readonly="false" name="entityId" placeholder="entityId" />
						</div>
					</div>
					<div class="pull-left right-input" ng-show="true ">
						<div class="input-group">
							<span class="input-group-addon">placeId</span><input
								class="form-control " aria-describedby="sizing-addon3"
								type="text" id="place-placeId" ng-model="searchBean.placeId"
								ng-readonly="false" name="placeId" placeholder="placeId" />
						</div>
					</div>
					<div class="pull-left right-input" ng-show="true ">
						<div class="input-group">
							<span class="input-group-addon">placeName</span><input
								class="form-control " aria-describedby="sizing-addon3"
								type="text" id="place-placeName" ng-model="searchBean.placeName"
								ng-readonly="false" name="placeName" placeholder="placeName" />
						</div>
					</div>
					<div class="pull-left right-input" style="height: 59px;"
						ng-show="true  && (restrictionList.example==undefined || restrictionList.example.canSearch==true)">
						<div class="input-group">
							<span class="input-group-addon">example</span><select
								class="form-control " aria-describedby="sizing-addon3"
								ng-model="searchBean.example.exampleId" id="example"
								ng-options="example.exampleId as  example.exampleId for example in childrenList.exampleList"
								enctype="UTF-8"></select>
						</div>
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-body">
						<div class="pull-left right-input">
							<button ng-click="addNew()" class="btn btn-default "
								ng-show="(restrictionList.place==undefined || restrictionList.place.canCreate==true)">Add
								new</button>
							<button ng-click="search()" class="btn btn-default ">Find</button>
							<button ng-click="reset()" class="btn btn-default ">Reset</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<form id="placeList" ng-if="entityList.length&gt;0" enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">
					List place
					<button ng-click="downloadEntityList()"
						class="btn btn-default pull-right" style="margin-top: -7px">
						<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
					</button>
				</div>
				<div class="panel-body">
					<div ui-grid="placeGridOptions" ui-grid-pagination=""
						ui-grid-selection="" ui-grid-exporter=""></div>
				</div>
			</div>
		</form>
		<form id="placeDetailForm" name="placeDetailForm"
			ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail place {{
					selectedEntity.placeId }}</div>
				<div class="panel-body">
					<ul class="nav nav-tabs" role="tablist" id="placeTabs">
						<li role="presentation"><a href="#place-Detail"
							aria-controls="Detail" role="tab" data-toggle="tab"
							ng-click="refreshTableDetail()">Detail</a></li>
						<script type="text/javascript">
							$('a[href="#place-Detail"]').on(
									'shown.bs.tab',
									function(e) {
										var target = $(e.target).attr("href"); // activated tab
										//console.log(target);
										if (angular.element($('#placeTabs'))
												.scope() != null
												&& angular.element(
														$('#placeTabs'))
														.scope() != undefined)
											angular.element($('#placeTabs'))
													.scope()
													.refreshTableDetail();
									});
						</script>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade" id="place-Detail">
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !placeDetailForm.entityId.$valid, &#39;has-success&#39;: placeDetailForm.entityId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">entityId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="place-entityId"
										ng-model="selectedEntity.entityId"
										ng-readonly="restrictionList.place.restrictionFieldMap.entityId"
										name="entityId" placeholder="entityId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !placeDetailForm.placeId.$valid, &#39;has-success&#39;: placeDetailForm.placeId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">placeId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="place-placeId"
										ng-model="selectedEntity.placeId" ng-readonly="true"
										name="placeId" placeholder="placeId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !placeDetailForm.placeName.$valid, &#39;has-success&#39;: placeDetailForm.placeName.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">placeName</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="place-placeName"
										ng-model="selectedEntity.placeName"
										ng-readonly="restrictionList.place.restrictionFieldMap.placeName"
										name="placeName" placeholder="placeName" ng-minlength="2"
										ng-maxlength="10" />
								</div>
								<small class="help-block"
									ng-show="placeDetailForm.placeName.$error.minlength">place:
									placeName min 2 caratteri</small><small class="help-block"
									ng-show="placeDetailForm.placeName.$error.maxlength">place:
									placeName max 10 caratteri</small>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !placeDetailForm.example.$valid, &#39;has-success&#39;: placeDetailForm.example.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">example</span><select
										class="form-control " aria-describedby="sizing-addon3"
										ng-model="selectedEntity.example" id="example" name="example"
										ng-options="example as  example.exampleId for example in childrenList.exampleList track by example.exampleId"
										enctype="UTF-8"></select><span class="input-group-btn"><button
											ng-click="showExampleDetail()" class="btn btn-default "
											id="example" ng-if="selectedEntity.example==null">Add
											new example</button>
										<button ng-click="showExampleDetail()"
											class="btn btn-default " id="example"
											ng-if="selectedEntity.example!=null">Show detail</button></span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">
					$('#placeTabs a:first').tab('show');
				</script>
				<div class="panel-body">
					<div class="pull-left">
						<form id="placeActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default "
								ng-if="selectedEntity.placeId==undefined"
								ng-show="(restrictionList.place==undefined || restrictionList.place.canCreate==true)">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.placeId&gt;0"
								ng-show="(restrictionList.place==undefined || restrictionList.place.canUpdate==true)">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.placeId&gt;0"
								ng-show="(restrictionList.place==undefined || restrictionList.place.canDelete==true)">Delete</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="exampleController">
		<form id="exampleList" ng-if="entityList.length&gt;0" enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">
					List example
					<button ng-click="downloadEntityList()"
						class="btn btn-default pull-right" style="margin-top: -7px">
						<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
					</button>
				</div>
				<div class="panel-body">
					<div ui-grid="exampleGridOptions" ui-grid-pagination=""
						ui-grid-selection="" ui-grid-exporter=""></div>
				</div>
			</div>
		</form>
		<form id="exampleDetailForm" name="exampleDetailForm"
			ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail example {{
					selectedEntity.exampleId }}</div>
				<div class="panel-body">
					<ul class="nav nav-tabs" role="tablist" id="exampleTabs">
						<li role="presentation"><a href="#example-Detail"
							aria-controls="Detail" role="tab" data-toggle="tab"
							ng-click="refreshTableDetail()">Detail</a></li>
						<script type="text/javascript">
							$('a[href="#example-Detail"]').on(
									'shown.bs.tab',
									function(e) {
										var target = $(e.target).attr("href"); // activated tab
										//console.log(target);
										if (angular.element($('#exampleTabs'))
												.scope() != null
												&& angular.element(
														$('#exampleTabs'))
														.scope() != undefined)
											angular.element($('#exampleTabs'))
													.scope()
													.refreshTableDetail();
									});
						</script>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade" id="example-Detail">
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !exampleDetailForm.entityId.$valid, &#39;has-success&#39;: exampleDetailForm.entityId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">entityId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="example-entityId"
										ng-model="selectedEntity.entityId"
										ng-readonly="restrictionList.example.restrictionFieldMap.entityId"
										name="entityId" placeholder="entityId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !exampleDetailForm.male.$valid, &#39;has-success&#39;: exampleDetailForm.male.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">male</span><select
										class="form-control " aria-describedby="sizing-addon3"
										type="checkbox" id="example-male"
										ng-model="selectedEntity.male"
										ng-readonly="restrictionList.example.restrictionFieldMap.male"
										name="male" placeholder="male"
										ng-options="value for value in trueFalseValues"></select>
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !exampleDetailForm.age.$valid, &#39;has-success&#39;: exampleDetailForm.age.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">age</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="example-age" ng-model="selectedEntity.age"
										ng-readonly="restrictionList.example.restrictionFieldMap.age"
										name="age" placeholder="age" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !exampleDetailForm.exampleDate.$valid, &#39;has-success&#39;: exampleDetailForm.exampleDate.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">exampleDate</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
										ng-model="selectedEntity.exampleDate"
										ng-readonly="restrictionList.example.restrictionFieldMap.exampleDate"
										name="exampleDate" placeholder="exampleDate" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !exampleDetailForm.exampleId.$valid, &#39;has-success&#39;: exampleDetailForm.exampleId.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">exampleId</span><input
										class="form-control " aria-describedby="sizing-addon3"
										type="text" id="example-exampleId"
										ng-model="selectedEntity.exampleId" ng-readonly="true"
										name="exampleId" placeholder="exampleId" />
								</div>
							</div>
							<div class="pull-left right-input"
								ng-class="{&#39;has-error&#39;: !exampleDetailForm.exampleType.$valid, &#39;has-success&#39;: exampleDetailForm.exampleType.$valid}"
								style="height: 59px" ng-show="true ">
								<div class="input-group">
									<span class="input-group-addon">exampleType</span><select
										class="form-control pull-left"
										aria-describedby="sizing-addon3" type="text"
										id="example-exampleType" ng-model="selectedEntity.exampleType"
										ng-readonly="restrictionList.example.restrictionFieldMap.exampleType"
										name="exampleType" placeholder="exampleType"
										ng-options="exampleType as exampleType for exampleType in childrenList.exampleTypeList"
										enctype="UTF-8"></select>
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">$('#exampleTabs a:first').tab('show');</script>
				<div class="panel-body">
					<div class="pull-left">
						<form id="exampleActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default "
								ng-if="selectedEntity.exampleId==undefined"
								ng-show="(restrictionList.example==undefined || restrictionList.example.canCreate==true)">Insert</button>
							<button ng-click="update()" class="btn btn-default "
								ng-if="selectedEntity.exampleId&gt;0"
								ng-show="(restrictionList.example==undefined || restrictionList.example.canUpdate==true)">Update</button>
							<button ng-click="del()" class="btn btn-default "
								ng-if="selectedEntity.exampleId&gt;0"
								ng-show="(restrictionList.example==undefined || restrictionList.example.canDelete==true)">Delete</button>
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.exampleId&gt;0"
								ng-show="(restrictionList.example==undefined || restrictionList.example.canDelete==true)">Remove</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">loadMenu(); </script>
</body>
</html>