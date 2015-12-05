<!DOCTYPE html>
<html>
<head>
<title>example</title>
<script type="text/javascript" src="../js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../js/jquery-ui.js"></script>
<script type="text/javascript" src="../js/angular.js"></script>
<script type="text/javascript" src="../js/angular-touch.js"></script>
<script type="text/javascript" src="../js/angular-animate.js"></script>
<script type="text/javascript" src="../js/csv.js"></script>
<script type="text/javascript" src="../js/pdfmake.js"></script>
<script type="text/javascript" src="../js/vfs_fonts.js"></script>
<script type="text/javascript" src="../js/ui-grid.js"></script>
<script type="text/javascript" src="../js/angular/test/example.js"></script>
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
<body ng-app="exampleApp">
	<div ng-controller="exampleController">
		<form id="exampleSearchBean">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Search form example</div>
				<div class="panel-body">
					<div class="pull-left right-input" ng-show="true ">
						<div class="input-group">
							<span class="input-group-addon">entityId</span><input
								class="form-control " aria-describedby="sizing-addon3"
								type="text" id="example-entityId" ng-model="searchBean.entityId"
								ng-readonly="false" name="entityId" placeholder="entityId" />
						</div>
					</div>
					<div class="pull-left right-input" ng-show="true ">
						<div class="input-group">
							<span class="input-group-addon">male</span><select
								class="form-control " aria-describedby="sizing-addon3"
								type="checkbox" id="example-male" ng-model="searchBean.male"
								ng-readonly="false" name="male" placeholder="male"
								ng-options="value for value in trueFalseValues"></select>
						</div>
					</div>
					<div class="pull-left right-input" ng-show="true ">
						<div class="input-group">
							<span class="input-group-addon">age</span><input
								class="form-control " aria-describedby="sizing-addon3"
								type="text" id="example-age" ng-model="searchBean.age"
								ng-readonly="false" name="age" placeholder="age" />
						</div>
					</div>
					<div class="pull-left right-input" ng-show="true ">
						<div class="input-group">
							<span class="input-group-addon">exampleDate</span><input
								class="form-control " aria-describedby="sizing-addon3"
								type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
								ng-model="searchBean.exampleDate" ng-readonly="false"
								name="exampleDate" placeholder="exampleDate" />
						</div>
					</div>
					<div class="pull-left right-input" ng-show="true ">
						<div class="input-group">
							<span class="input-group-addon">exampleId</span><input
								class="form-control " aria-describedby="sizing-addon3"
								type="text" id="example-exampleId"
								ng-model="searchBean.exampleId" ng-readonly="false"
								name="exampleId" placeholder="exampleId" />
						</div>
					</div>
					<div class="pull-left right-input" style="height: 59px;"
						ng-show="true  && (restrictionList.place==undefined || restrictionList.place.canSearch==true)">
						<div class="input-group">
							<span class="input-group-addon">place</span><select
								class="form-control " aria-describedby="sizing-addon3"
								ng-model="searchBean.place.placeId" id="place"
								ng-options="place.placeId as  place.placeId for place in childrenList.placeList"
								enctype="UTF-8"></select>
						</div>
					</div>
					<div class="pull-left right-input"
						ng-show="(restrictionList.place==undefined || restrictionList.place.canSearch==true)">
						<div class="input-group">
							<span class="input-group-addon">placePlaceName</span><input
								class="form-control " aria-describedby="sizing-addon3"
								type="text" id="example-placePlaceName"
								ng-model="searchBean.placePlaceName" ng-readonly="false"
								name="placePlaceName" placeholder="placePlaceName" />
						</div>
					</div>
					<div class="pull-left right-input" ng-show="true ">
						<div class="input-group">
							<span class="input-group-addon">exampleType</span><select
								class="form-control pull-left" aria-describedby="sizing-addon3"
								type="text" id="example-exampleType"
								ng-model="searchBean.exampleType" ng-readonly="false"
								name="exampleType" placeholder="exampleType"
								ng-options="exampleType as exampleType for exampleType in childrenList.exampleTypeList"
								enctype="UTF-8"></select>
						</div>
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-body">
						<div class="pull-left right-input">
							<button ng-click="addNew()" class="btn btn-default "
								ng-show="(restrictionList.example==undefined || restrictionList.example.canCreate==true)">Add
								new</button>
							<button ng-click="search()" class="btn btn-default ">Find</button>
							<button ng-click="reset()" class="btn btn-default ">Reset</button>
						</div>
					</div>
				</div>
			</div>
		</form>
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

<br><br><br>
<input type="file" id="test-file"  ng-if="selectedEntity.filePath==undefined"/> 
<script>
$("#test-file").fileinput();
</script>

							<div class="pull-left right-input" 
								 ng-if="selectedEntity.exampleId&gt;0 "
								ng-class="{'has-success' : true }"
								>
								<script src="../js/fileinput.js" type="text/javascript"></script>
								<div class="input-group">
									<span class="input-group-addon">testFile</span>

<input type="file"  file-model="selectedEntity.testFile" ng-if="selectedEntity.filePath==undefined"/> 
<a href="{{selectedEntity.filePath}}" ng-if="selectedEntity.filePath!=undefined">file</a>

<span class="input-group-btn"><button
											ng-click="loadFile()" class="btn btn-default "
											id="example" ng-if="selectedEntity.filePath==undefined">Load</button>
										<button ng-click=""
											class="btn btn-default " id="example"
											ng-if="selectedEntity.example!=null">Open/button></span>
								</div>
								
								
								
								


								<!--  begin file gmmt -->


								<!-- 						
<div flow-init

      
 style="width: 500px">
 <!--  <div class="drop" flow-drop="" ng-class="dropClass">
    <span class="btn btn-default" flow-btn="">Upload File<input ng-model="selectedEntity.testFile" type="file" multiple="multiple" style="visibility: hidden; position: absolute;"></span>
  </div>-->

								<!-- 
  <input type="file" flow-btn/>

  <br>
<!--
  <div class="well">
    <a class="btn btn-small btn-success" ng-click="$flow.resume()">Resume all</a>
    <a class="btn btn-small btn-danger" ng-click="$flow.pause()">Pause all</a>
    <a class="btn btn-small btn-info" ng-click="$flow.cancel()">Cancel all</a>
    <span class="label label-info ng-binding">Total Size: 192832288bytes</span>
  </div>-->
								<!-- 
  <div>

   <div ng-repeat="file in $flow.files" class="transfer-box ng-scope ng-binding">
	{{file.name}} ({{file.size}}bytes)
      <div class="progress progress-striped" ng-class="{active: file.isUploading()}">
        <div class="progress-bar" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" ng-style="{width: (file.progress() * 100) + '%'}" style="width: 100%;">
          <span class="sr-only ng-binding">1% Complete</span>
        </div>
      </div>
      <div class="btn-group">
        <a class="btn btn-xs btn-warning ng-hide" ng-click="file.pause()" ng-show="!file.paused &amp;&amp; file.isUploading()">
          Pause
        </a>
        <a class="btn btn-xs btn-warning ng-hide" ng-click="file.resume()" ng-show="file.paused">
          Resume
        </a>
        <a class="btn btn-xs btn-danger" ng-click="file.cancel()">
          Cancel
        </a>
        <a class="btn btn-xs btn-info ng-hide" ng-click="file.retry()" ng-show="file.error">
          Retry
        </a>
      </div>
    </div>
  </div>
</div>

<!-- end file mgmt -->


							</div>



							<div id="example-place" class="modal fade" role="dialog">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Link existing place</h4>
										</div>
										<div class="modal-body">
											<p></p>
											<div class="input-group">
												<span class="input-group-addon">place</span><select
													class="form-control " aria-describedby="sizing-addon3"
													ng-model="selectedEntity.place" id="place" name="place"
													ng-options="place as  place.placeId for place in childrenList.placeList track by place.placeId"
													enctype="UTF-8"></select><span class="input-group-btn"><button
														ng-click="saveLinkedPlace()" class="btn btn-default "
														id="place">Save</button></span>
											</div>
										</div>
										<div class="modal-footer">
											<button ng-click="close()" class="btn btn-default "
												data-dismiss="modal">close</button>
										</div>
									</div>
								</div>
							</div>
							<br /> <br />
							<div class="pull-left" style="width: 100%"
								ng-show="true  && (restrictionList.place==undefined || restrictionList.place.canSearch==true)">
								<div class="panel panel-default default-panel">
									<div class="panel-heading">
										place
										<button ng-click="showPlaceDetail()"
											class="btn btn-default  pull-right" style="margin-top: -7px"
											ng-show="(restrictionList.place==undefined || restrictionList.place.canCreate==true)">Add
											new place</button>
										<button type="button" class="btn btn-default pull-right"
											style="margin-top: -7px" data-toggle="modal"
											data-target="#example-place">Link existing</button>
										<button ng-click="downloadPlaceList()"
											class="btn btn-default pull-right" style="margin-top: -7px">
											<span class="glyphicon glyphicon-download-alt"
												aria-hidden="true"></span>
										</button>
									</div>
									<div class="panel-body"
										ng-class="{&#39;has-error&#39;: !exampleDetailForm.place.$valid, &#39;has-success&#39;: exampleDetailForm.place.$valid}">
										<label id="place">place</label>
										<div id="place" ng-if="selectedEntity.placeList.length&gt;0">
											<div style="top: 100px" ui-grid="placeListGridOptions"
												ui-grid-pagination="" ui-grid-selection=""></div>
										</div>
									</div>
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
				<script type="text/javascript">
					$('#exampleTabs a:first').tab('show');
				</script>
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
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="placeController">
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
							<button ng-click="remove()" class="btn btn-default "
								ng-if="selectedEntity.placeId&gt;0"
								ng-show="(restrictionList.place==undefined || restrictionList.place.canDelete==true)">Remove</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		loadMenu();
	</script>
</body>
</html>