<!DOCTYPE html>
<html>
<head>
<title>test order</title>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-touch.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-animate.js"></script>
<script type="text/javascript"
	src="http://ui-grid.info/docs/grunt-scripts/csv.js"></script>
<script type="text/javascript"
	src="http://ui-grid.info/docs/grunt-scripts/pdfmake.js"></script>
<script type="text/javascript"
	src="http://ui-grid.info/docs/grunt-scripts/vfs_fonts.js"></script>
<script type="text/javascript"
	src="http://ui-grid.info/release/ui-grid.js"></script>
<script type="text/javascript"
	src="../resources/general_theme/js/angular/ordine.js"></script>
<script type="text/javascript"
	src="../resources/general_theme/js/date.js"></script>
<link rel="stylesheet" href="http://ui-grid.info/release/ui-grid.css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" />
<link rel="stylesheet" href="../resources/general_theme/css/main.css" />
<link rel="stylesheet"
	href="../resources/general_theme/css/jquery-ui.css" />
</head>
<body ng-app="ordineApp">
	<div ng-controller="ordineController">
		<form id="ordineSearchBean">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Search form ordine</div>
				<div class="panel-body">
					<div class="pull-left right-input">
						<label id="ordineId">ordineId</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="searchBean.ordineId" ng-readonly="false"
							name="ordineId" placeholder="ordineId" id="ordine-ordineId" />
					</div>
					<div class="pull-right right-input">
						<label id="billFile">billFile</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="searchBean.billFile" ng-readonly="false"
							name="billFile" placeholder="billFile" id="ordine-billFile" />
					</div>
					<div class="pull-left right-input">
						<label id="cittaSpedizione">cittaSpedizione</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.cittaSpedizione"
							ng-readonly="false" name="cittaSpedizione"
							placeholder="cittaSpedizione" id="ordine-cittaSpedizione" />
					</div>
					<div class="pull-right right-input">
						<label id="cityId">cityId</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="searchBean.cityId" ng-readonly="false" name="cityId"
							placeholder="cityId" id="ordine-cityId" />
					</div>
					<div class="pull-left right-input">
						<label id="cutoffTime">cutoffTime</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="searchBean.cutoffTime"
							ng-readonly="false" name="cutoffTime" placeholder="cutoffTime"
							id="ordine-cutoffTime" />
					</div>
					<div class="pull-right right-input">
						<label id="emailSpedizione">emailSpedizione</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.emailSpedizione"
							ng-readonly="false" name="emailSpedizione"
							placeholder="emailSpedizione" id="ordine-emailSpedizione" />
					</div>
					<div class="pull-left right-input">
						<label id="fattura">fattura</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="searchBean.fattura" ng-readonly="false" name="fattura"
							placeholder="fattura" id="ordine-fattura" />
					</div>
					<div class="pull-right right-input">
						<label id="infoFeedback">infoFeedback</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.infoFeedback"
							ng-readonly="false" name="infoFeedback"
							placeholder="infoFeedback" id="ordine-infoFeedback" />
					</div>
					<div class="pull-left right-input">
						<label id="inviatoCassa">inviatoCassa</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.inviatoCassa"
							ng-readonly="false" name="inviatoCassa"
							placeholder="inviatoCassa" id="ordine-inviatoCassa" />
					</div>
					<div class="pull-right right-input">
						<label id="nomeSpedizione">nomeSpedizione</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.nomeSpedizione"
							ng-readonly="false" name="nomeSpedizione"
							placeholder="nomeSpedizione" id="ordine-nomeSpedizione" />
					</div>
					<div class="pull-left right-input">
						<label id="note">note</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="searchBean.note" ng-readonly="false" name="note"
							placeholder="note" id="ordine-note" />
					</div>
					<div class="pull-right right-input">
						<label id="pickupTime">pickupTime</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="searchBean.pickupTime"
							ng-readonly="false" name="pickupTime" placeholder="pickupTime"
							id="ordine-pickupTime" />
					</div>
					<div class="pull-left right-input">
						<label id="riaperto">riaperto</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="searchBean.riaperto" ng-readonly="false"
							name="riaperto" placeholder="riaperto" id="ordine-riaperto" />
					</div>
					<div class="pull-right right-input">
						<label id="rimandatoAltraData">rimandatoAltraData</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.rimandatoAltraData"
							ng-readonly="false" name="rimandatoAltraData"
							placeholder="rimandatoAltraData" id="ordine-rimandatoAltraData" />
					</div>
					<div class="pull-left right-input">
						<label id="sentSucceeded">sentSucceeded</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.sentSucceeded"
							ng-readonly="false" name="sentSucceeded"
							placeholder="sentSucceeded" id="ordine-sentSucceeded" />
					</div>
					<div class="pull-right right-input">
						<label id="statusPreBill">statusPreBill</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.statusPreBill"
							ng-readonly="false" name="statusPreBill"
							placeholder="statusPreBill" id="ordine-statusPreBill" />
					</div>
					<div class="pull-left right-input">
						<label id="statusPreBillErr">statusPreBillErr</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.statusPreBillErr"
							ng-readonly="false" name="statusPreBillErr"
							placeholder="statusPreBillErr" id="ordine-statusPreBillErr" />
					</div>
					<div class="pull-right right-input">
						<label id="suborderStatusId">suborderStatusId</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.suborderStatusId"
							ng-readonly="false" name="suborderStatusId"
							placeholder="suborderStatusId" id="ordine-suborderStatusId" />
					</div>
					<div class="pull-left right-input">
						<label id="telSpedizione">telSpedizione</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.telSpedizione"
							ng-readonly="false" name="telSpedizione"
							placeholder="telSpedizione" id="ordine-telSpedizione" />
					</div>
					<div class="pull-right right-input">
						<label id="tigrosCard">tigrosCard</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.tigrosCard" ng-readonly="false"
							name="tigrosCard" placeholder="tigrosCard" id="ordine-tigrosCard" />
					</div>
					<div class="pull-left right-input">
						<label id="timeslotBegin">timeslotBegin</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="searchBean.timeslotBegin"
							ng-readonly="false" name="timeslotBegin"
							placeholder="timeslotBegin" id="ordine-timeslotBegin" />
					</div>
					<div class="pull-right right-input">
						<label id="timeslotDate">timeslotDate</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="searchBean.timeslotDate"
							ng-readonly="false" name="timeslotDate"
							placeholder="timeslotDate" id="ordine-timeslotDate" />
					</div>
					<div class="pull-left right-input">
						<label id="timeslotEnd">timeslotEnd</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="searchBean.timeslotEnd"
							ng-readonly="false" name="timeslotEnd" placeholder="timeslotEnd"
							id="ordine-timeslotEnd" />
					</div>
					<div class="pull-right right-input">
						<label id="tipoSacchetto">tipoSacchetto</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.tipoSacchetto"
							ng-readonly="false" name="tipoSacchetto"
							placeholder="tipoSacchetto" id="ordine-tipoSacchetto" />
					</div>
					<div class="pull-left right-input">
						<label id="totale">totale</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="searchBean.totale" ng-readonly="false" name="totale"
							placeholder="totale" id="ordine-totale" />
					</div>
					<div class="pull-right right-input">
						<label id="tsCaricamentoCamion">tsCaricamentoCamion</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy"
							ng-model="searchBean.tsCaricamentoCamion" ng-readonly="false"
							name="tsCaricamentoCamion" placeholder="tsCaricamentoCamion"
							id="ordine-tsCaricamentoCamion" />
					</div>
					<div class="pull-left right-input">
						<label id="tsFinePreparazione">tsFinePreparazione</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy"
							ng-model="searchBean.tsFinePreparazione" ng-readonly="false"
							name="tsFinePreparazione" placeholder="tsFinePreparazione"
							id="ordine-tsFinePreparazione" />
					</div>
					<div class="pull-right right-input">
						<label id="tsInizioPreparazione">tsInizioPreparazione</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy"
							ng-model="searchBean.tsInizioPreparazione" ng-readonly="false"
							name="tsInizioPreparazione" placeholder="tsInizioPreparazione"
							id="ordine-tsInizioPreparazione" />
					</div>
					<div class="pull-left right-input">
						<label id="tsInvioFeedback">tsInvioFeedback</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="searchBean.tsInvioFeedback"
							ng-readonly="false" name="tsInvioFeedback"
							placeholder="tsInvioFeedback" id="ordine-tsInvioFeedback" />
					</div>
					<div class="pull-right right-input">
						<label id="tsModifica">tsModifica</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="searchBean.tsModifica"
							ng-readonly="false" name="tsModifica" placeholder="tsModifica"
							id="ordine-tsModifica" />
					</div>
					<div class="pull-left right-input">
						<label id="tsRicevuto">tsRicevuto</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="searchBean.tsRicevuto"
							ng-readonly="false" name="tsRicevuto" placeholder="tsRicevuto"
							id="ordine-tsRicevuto" />
					</div>
					<div class="pull-right right-input">
						<label id="tsStartPayment">tsStartPayment</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="searchBean.tsStartPayment"
							ng-readonly="false" name="tsStartPayment"
							placeholder="tsStartPayment" id="ordine-tsStartPayment" />
					</div>
					<div class="pull-left right-input">
						<label id="vOrderId">vOrderId</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="searchBean.vOrderId" ng-readonly="false"
							name="vOrderId" placeholder="vOrderId" id="ordine-vOrderId" />
					</div>
					<div class="pull-right right-input">
						<label id="vSuborderId">vSuborderId</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.vSuborderId" ng-readonly="false"
							name="vSuborderId" placeholder="vSuborderId"
							id="ordine-vSuborderId" />
					</div>
					<div class="pull-left right-input">
						<label id="viaSpedizione">viaSpedizione</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.viaSpedizione"
							ng-readonly="false" name="viaSpedizione"
							placeholder="viaSpedizione" id="ordine-viaSpedizione" />
					</div>
					<div class="pull-right right-input">
						<label id="zoneGroupId">zoneGroupId</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.zoneGroupId" ng-readonly="false"
							name="zoneGroupId" placeholder="zoneGroupId"
							id="ordine-zoneGroupId" />
					</div>
					<div class="pull-left right-input">
						<label id="zoneId">zoneId</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="searchBean.zoneId" ng-readonly="false" name="zoneId"
							placeholder="zoneId" id="ordine-zoneId" />
					</div>
					<div class="pull-right right-input">
						<label id="addressName">addressName</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.addressName" ng-readonly="false"
							name="addressName" placeholder="addressName"
							id="ordine-addressName" />
					</div>
					<div class="pull-left right-input">
						<label id="doorBellName">doorBellName</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.doorBellName"
							ng-readonly="false" name="doorBellName"
							placeholder="doorBellName" id="ordine-doorBellName" />
					</div>
					<div class="pull-right right-input">
						<label id="deliveryCity">deliveryCity</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.deliveryCity"
							ng-readonly="false" name="deliveryCity"
							placeholder="deliveryCity" id="ordine-deliveryCity" />
					</div>
					<div class="pull-left right-input">
						<label id="address1">address1</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="searchBean.address1" ng-readonly="false"
							name="address1" placeholder="address1" id="ordine-address1" />
					</div>
					<div class="pull-right right-input">
						<label id="postalCode">postalCode</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.postalCode" ng-readonly="false"
							name="postalCode" placeholder="postalCode" id="ordine-postalCode" />
					</div>
					<div class="pull-left right-input">
						<label id="province">province</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="searchBean.province" ng-readonly="false"
							name="province" placeholder="province" id="ordine-province" />
					</div>
					<div class="pull-right right-input">
						<label id="doorBellNumber">doorBellNumber</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.doorBellNumber"
							ng-readonly="false" name="doorBellNumber"
							placeholder="doorBellNumber" id="ordine-doorBellNumber" />
					</div>
					<div class="pull-left right-input">
						<label id="address2">address2</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="searchBean.address2" ng-readonly="false"
							name="address2" placeholder="address2" id="ordine-address2" />
					</div>
					<div class="pull-right right-input">
						<label id="referencePhone">referencePhone</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.referencePhone"
							ng-readonly="false" name="referencePhone"
							placeholder="referencePhone" id="ordine-referencePhone" />
					</div>
					<div class="pull-left right-input">
						<label id="latitude">latitude</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="searchBean.latitude" ng-readonly="false"
							name="latitude" placeholder="latitude" id="ordine-latitude" />
					</div>
					<div class="pull-right right-input">
						<label id="longitude">longitude</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="searchBean.longitude" ng-readonly="false"
							name="longitude" placeholder="longitude" id="ordine-longitude" />
					</div>
					<div class="pull-left right-input">
						<label id="collo">collo</label><select class="form-control "
							aria-describedby="sizing-addon3"
							ng-model="searchBean.collo.colloId" id="collo"
							ng-options="collo.colloId as  collo.codice+&#39; &#39;+ collo.ordinamentoReport for collo in childrenList.colloList"
							enctype="UTF-8"></select>
					</div>
					<div class="pull-right right-input">
						<label id="itemOrdine">itemOrdine</label><select
							class="form-control " aria-describedby="sizing-addon3"
							ng-model="searchBean.itemOrdine.itemOrdineId" id="itemOrdine"
							ng-options="itemOrdine.itemOrdineId as  itemOrdine.barcode+&#39; &#39;+ itemOrdine.carfCanaleLocale+&#39; &#39;+ itemOrdine.format+&#39; &#39;+ itemOrdine.infoFeedback+&#39; &#39;+ itemOrdine.name+&#39; &#39;+ itemOrdine.note+&#39; &#39;+ itemOrdine.vOrderItemId+&#39; &#39;+ itemOrdine.vProductId for itemOrdine in childrenList.itemOrdineList"
							enctype="UTF-8"></select>
					</div>
				</div>
				<div class="panel-body">
					<div class="pull-left right-input">
						<button ng-click="addNew()" class="btn btn-default">Add
							new</button>
						<button ng-click="search()" class="btn btn-default">Find</button>
						<button ng-click="reset()" class="btn btn-default">Reset</button>
					</div>
				</div>
			</div>
		</form>
		<form id="ordineList" ng-if="entityList.length&gt;0" enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">List ordine</div>
				<div class="panel-body">
					<div ui-grid="ordineGridOptions" ui-grid-pagination=""
						ui-grid-selection=""></div>
				</div>
			</div>
		</form>
		<form id="ordineDetailForm" name="ordineDetailForm"
			ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail ordine {{
					selectedEntity.ordineId }}</div>
				<div class="panel-body">
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.ordineId.$valid, &#39;has-success&#39;: ordineDetailForm.ordineId.$valid}">
						<label for="ordineId">ordineId</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.ordineId" ng-readonly="false"
							name="ordineId" placeholder="ordineId" id="ordine-ordineId" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.billFile.$valid, &#39;has-success&#39;: ordineDetailForm.billFile.$valid}">
						<label for="billFile">billFile</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.billFile" ng-readonly="false"
							name="billFile" placeholder="billFile" id="ordine-billFile"
							ng-minlength="0" ng-maxlength="255" /><small class="help-block"
							ng-show="ordineDetailForm.billFile.$error.minlength">ordine:
							billFile min 0 caratteri</small><small class="help-block"
							ng-show="ordineDetailForm.billFile.$error.maxlength">ordine:
							billFile max 255 caratteri</small>
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.cittaSpedizione.$valid, &#39;has-success&#39;: ordineDetailForm.cittaSpedizione.$valid}">
						<label for="cittaSpedizione">cittaSpedizione</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.cittaSpedizione"
							ng-readonly="false" name="cittaSpedizione"
							placeholder="cittaSpedizione" id="ordine-cittaSpedizione" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.cityId.$valid, &#39;has-success&#39;: ordineDetailForm.cityId.$valid}">
						<label for="cityId">cityId</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.cityId" ng-readonly="false"
							name="cityId" placeholder="cityId" id="ordine-cityId" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.cutoffTime.$valid, &#39;has-success&#39;: ordineDetailForm.cutoffTime.$valid}">
						<label for="cutoffTime">cutoffTime</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="selectedEntity.cutoffTime"
							ng-readonly="false" name="cutoffTime" placeholder="cutoffTime"
							id="ordine-cutoffTime" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.emailSpedizione.$valid, &#39;has-success&#39;: ordineDetailForm.emailSpedizione.$valid}">
						<label for="emailSpedizione">emailSpedizione</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.emailSpedizione"
							ng-readonly="false" name="emailSpedizione"
							placeholder="emailSpedizione" id="ordine-emailSpedizione" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.fattura.$valid, &#39;has-success&#39;: ordineDetailForm.fattura.$valid}">
						<label for="fattura">fattura</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.fattura" ng-readonly="false"
							name="fattura" placeholder="fattura" id="ordine-fattura" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.infoFeedback.$valid, &#39;has-success&#39;: ordineDetailForm.infoFeedback.$valid}">
						<label for="infoFeedback">infoFeedback</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.infoFeedback"
							ng-readonly="false" name="infoFeedback"
							placeholder="infoFeedback" id="ordine-infoFeedback" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.inviatoCassa.$valid, &#39;has-success&#39;: ordineDetailForm.inviatoCassa.$valid}">
						<label for="inviatoCassa">inviatoCassa</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.inviatoCassa"
							ng-readonly="false" name="inviatoCassa"
							placeholder="inviatoCassa" id="ordine-inviatoCassa" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.nomeSpedizione.$valid, &#39;has-success&#39;: ordineDetailForm.nomeSpedizione.$valid}">
						<label for="nomeSpedizione">nomeSpedizione</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.nomeSpedizione"
							ng-readonly="false" name="nomeSpedizione"
							placeholder="nomeSpedizione" id="ordine-nomeSpedizione" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.note.$valid, &#39;has-success&#39;: ordineDetailForm.note.$valid}">
						<label for="note">note</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.note" ng-readonly="false" name="note"
							placeholder="note" id="ordine-note" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.pickupTime.$valid, &#39;has-success&#39;: ordineDetailForm.pickupTime.$valid}">
						<label for="pickupTime">pickupTime</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="selectedEntity.pickupTime"
							ng-readonly="false" name="pickupTime" placeholder="pickupTime"
							id="ordine-pickupTime" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.riaperto.$valid, &#39;has-success&#39;: ordineDetailForm.riaperto.$valid}">
						<label for="riaperto">riaperto</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.riaperto" ng-readonly="false"
							name="riaperto" placeholder="riaperto" id="ordine-riaperto" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.rimandatoAltraData.$valid, &#39;has-success&#39;: ordineDetailForm.rimandatoAltraData.$valid}">
						<label for="rimandatoAltraData">rimandatoAltraData</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.rimandatoAltraData"
							ng-readonly="false" name="rimandatoAltraData"
							placeholder="rimandatoAltraData" id="ordine-rimandatoAltraData" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.sentSucceeded.$valid, &#39;has-success&#39;: ordineDetailForm.sentSucceeded.$valid}">
						<label for="sentSucceeded">sentSucceeded</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.sentSucceeded"
							ng-readonly="false" name="sentSucceeded"
							placeholder="sentSucceeded" id="ordine-sentSucceeded" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.statusPreBill.$valid, &#39;has-success&#39;: ordineDetailForm.statusPreBill.$valid}">
						<label for="statusPreBill">statusPreBill</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.statusPreBill"
							ng-readonly="false" name="statusPreBill"
							placeholder="statusPreBill" id="ordine-statusPreBill" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.statusPreBillErr.$valid, &#39;has-success&#39;: ordineDetailForm.statusPreBillErr.$valid}">
						<label for="statusPreBillErr">statusPreBillErr</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.statusPreBillErr"
							ng-readonly="false" name="statusPreBillErr"
							placeholder="statusPreBillErr" id="ordine-statusPreBillErr" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.suborderStatusId.$valid, &#39;has-success&#39;: ordineDetailForm.suborderStatusId.$valid}">
						<label for="suborderStatusId">suborderStatusId</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.suborderStatusId"
							ng-readonly="false" name="suborderStatusId"
							placeholder="suborderStatusId" id="ordine-suborderStatusId" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.telSpedizione.$valid, &#39;has-success&#39;: ordineDetailForm.telSpedizione.$valid}">
						<label for="telSpedizione">telSpedizione</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.telSpedizione"
							ng-readonly="false" name="telSpedizione"
							placeholder="telSpedizione" id="ordine-telSpedizione" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.tigrosCard.$valid, &#39;has-success&#39;: ordineDetailForm.tigrosCard.$valid}">
						<label for="tigrosCard">tigrosCard</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.tigrosCard"
							ng-readonly="false" name="tigrosCard" placeholder="tigrosCard"
							id="ordine-tigrosCard" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.timeslotBegin.$valid, &#39;has-success&#39;: ordineDetailForm.timeslotBegin.$valid}">
						<label for="timeslotBegin">timeslotBegin</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="selectedEntity.timeslotBegin"
							ng-readonly="false" name="timeslotBegin"
							placeholder="timeslotBegin" id="ordine-timeslotBegin" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.timeslotDate.$valid, &#39;has-success&#39;: ordineDetailForm.timeslotDate.$valid}">
						<label for="timeslotDate">timeslotDate</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="selectedEntity.timeslotDate"
							ng-readonly="false" name="timeslotDate"
							placeholder="timeslotDate" id="ordine-timeslotDate" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.timeslotEnd.$valid, &#39;has-success&#39;: ordineDetailForm.timeslotEnd.$valid}">
						<label for="timeslotEnd">timeslotEnd</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="selectedEntity.timeslotEnd"
							ng-readonly="false" name="timeslotEnd" placeholder="timeslotEnd"
							id="ordine-timeslotEnd" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.tipoSacchetto.$valid, &#39;has-success&#39;: ordineDetailForm.tipoSacchetto.$valid}">
						<label for="tipoSacchetto">tipoSacchetto</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.tipoSacchetto"
							ng-readonly="false" name="tipoSacchetto"
							placeholder="tipoSacchetto" id="ordine-tipoSacchetto" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.totale.$valid, &#39;has-success&#39;: ordineDetailForm.totale.$valid}">
						<label for="totale">totale</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.totale" ng-readonly="false"
							name="totale" placeholder="totale" id="ordine-totale" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.tsCaricamentoCamion.$valid, &#39;has-success&#39;: ordineDetailForm.tsCaricamentoCamion.$valid}">
						<label for="tsCaricamentoCamion">tsCaricamentoCamion</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy"
							ng-model="selectedEntity.tsCaricamentoCamion" ng-readonly="false"
							name="tsCaricamentoCamion" placeholder="tsCaricamentoCamion"
							id="ordine-tsCaricamentoCamion" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.tsFinePreparazione.$valid, &#39;has-success&#39;: ordineDetailForm.tsFinePreparazione.$valid}">
						<label for="tsFinePreparazione">tsFinePreparazione</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy"
							ng-model="selectedEntity.tsFinePreparazione" ng-readonly="false"
							name="tsFinePreparazione" placeholder="tsFinePreparazione"
							id="ordine-tsFinePreparazione" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.tsInizioPreparazione.$valid, &#39;has-success&#39;: ordineDetailForm.tsInizioPreparazione.$valid}">
						<label for="tsInizioPreparazione">tsInizioPreparazione</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy"
							ng-model="selectedEntity.tsInizioPreparazione"
							ng-readonly="false" name="tsInizioPreparazione"
							placeholder="tsInizioPreparazione"
							id="ordine-tsInizioPreparazione" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.tsInvioFeedback.$valid, &#39;has-success&#39;: ordineDetailForm.tsInvioFeedback.$valid}">
						<label for="tsInvioFeedback">tsInvioFeedback</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy"
							ng-model="selectedEntity.tsInvioFeedback" ng-readonly="false"
							name="tsInvioFeedback" placeholder="tsInvioFeedback"
							id="ordine-tsInvioFeedback" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.tsModifica.$valid, &#39;has-success&#39;: ordineDetailForm.tsModifica.$valid}">
						<label for="tsModifica">tsModifica</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="selectedEntity.tsModifica"
							ng-readonly="false" name="tsModifica" placeholder="tsModifica"
							id="ordine-tsModifica" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.tsRicevuto.$valid, &#39;has-success&#39;: ordineDetailForm.tsRicevuto.$valid}">
						<label for="tsRicevuto">tsRicevuto</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="selectedEntity.tsRicevuto"
							ng-readonly="false" name="tsRicevuto" placeholder="tsRicevuto"
							id="ordine-tsRicevuto" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.tsStartPayment.$valid, &#39;has-success&#39;: ordineDetailForm.tsStartPayment.$valid}">
						<label for="tsStartPayment">tsStartPayment</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy"
							ng-model="selectedEntity.tsStartPayment" ng-readonly="false"
							name="tsStartPayment" placeholder="tsStartPayment"
							id="ordine-tsStartPayment" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.vOrderId.$valid, &#39;has-success&#39;: ordineDetailForm.vOrderId.$valid}">
						<label for="vOrderId">vOrderId</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.vOrderId" ng-readonly="false"
							name="vOrderId" placeholder="vOrderId" id="ordine-vOrderId" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.vSuborderId.$valid, &#39;has-success&#39;: ordineDetailForm.vSuborderId.$valid}">
						<label for="vSuborderId">vSuborderId</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.vSuborderId"
							ng-readonly="false" name="vSuborderId" placeholder="vSuborderId"
							id="ordine-vSuborderId" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.viaSpedizione.$valid, &#39;has-success&#39;: ordineDetailForm.viaSpedizione.$valid}">
						<label for="viaSpedizione">viaSpedizione</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.viaSpedizione"
							ng-readonly="false" name="viaSpedizione"
							placeholder="viaSpedizione" id="ordine-viaSpedizione" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.zoneGroupId.$valid, &#39;has-success&#39;: ordineDetailForm.zoneGroupId.$valid}">
						<label for="zoneGroupId">zoneGroupId</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.zoneGroupId"
							ng-readonly="false" name="zoneGroupId" placeholder="zoneGroupId"
							id="ordine-zoneGroupId" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.zoneId.$valid, &#39;has-success&#39;: ordineDetailForm.zoneId.$valid}">
						<label for="zoneId">zoneId</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.zoneId" ng-readonly="false"
							name="zoneId" placeholder="zoneId" id="ordine-zoneId" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.addressName.$valid, &#39;has-success&#39;: ordineDetailForm.addressName.$valid}">
						<label for="addressName">addressName</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.addressName"
							ng-readonly="false" name="addressName" placeholder="addressName"
							id="ordine-addressName" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.doorBellName.$valid, &#39;has-success&#39;: ordineDetailForm.doorBellName.$valid}">
						<label for="doorBellName">doorBellName</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.doorBellName"
							ng-readonly="false" name="doorBellName"
							placeholder="doorBellName" id="ordine-doorBellName" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.deliveryCity.$valid, &#39;has-success&#39;: ordineDetailForm.deliveryCity.$valid}">
						<label for="deliveryCity">deliveryCity</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.deliveryCity"
							ng-readonly="false" name="deliveryCity"
							placeholder="deliveryCity" id="ordine-deliveryCity" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.address1.$valid, &#39;has-success&#39;: ordineDetailForm.address1.$valid}">
						<label for="address1">address1</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.address1" ng-readonly="false"
							name="address1" placeholder="address1" id="ordine-address1" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.postalCode.$valid, &#39;has-success&#39;: ordineDetailForm.postalCode.$valid}">
						<label for="postalCode">postalCode</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.postalCode"
							ng-readonly="false" name="postalCode" placeholder="postalCode"
							id="ordine-postalCode" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.province.$valid, &#39;has-success&#39;: ordineDetailForm.province.$valid}">
						<label for="province">province</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.province" ng-readonly="false"
							name="province" placeholder="province" id="ordine-province" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.doorBellNumber.$valid, &#39;has-success&#39;: ordineDetailForm.doorBellNumber.$valid}">
						<label for="doorBellNumber">doorBellNumber</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.doorBellNumber"
							ng-readonly="false" name="doorBellNumber"
							placeholder="doorBellNumber" id="ordine-doorBellNumber" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.address2.$valid, &#39;has-success&#39;: ordineDetailForm.address2.$valid}">
						<label for="address2">address2</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.address2" ng-readonly="false"
							name="address2" placeholder="address2" id="ordine-address2" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.referencePhone.$valid, &#39;has-success&#39;: ordineDetailForm.referencePhone.$valid}">
						<label for="referencePhone">referencePhone</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.referencePhone"
							ng-readonly="false" name="referencePhone"
							placeholder="referencePhone" id="ordine-referencePhone" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.latitude.$valid, &#39;has-success&#39;: ordineDetailForm.latitude.$valid}">
						<label for="latitude">latitude</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.latitude" ng-readonly="false"
							name="latitude" placeholder="latitude" id="ordine-latitude" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !ordineDetailForm.longitude.$valid, &#39;has-success&#39;: ordineDetailForm.longitude.$valid}">
						<label for="longitude">longitude</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.longitude"
							ng-readonly="false" name="longitude" placeholder="longitude"
							id="ordine-longitude" />
					</div>
				</div>
				<div class="panel-body"
					ng-class="{&#39;has-error&#39;: !ordineDetailForm.collo.$valid, &#39;has-success&#39;: ordineDetailForm.collo.$valid}">
					<label id="collo">collo</label>
					<button ng-click="showColloDetail()" class="btn btn-default">Add
						new collo</button>
					<div id="collo" ng-if="selectedEntity.colloList.length&gt;0">
						<div style="top: 100px" ui-grid="colloListGridOptions"
							ui-grid-pagination="" ui-grid-selection=""></div>
					</div>
				</div>
				<div class="panel-body"></div>
				<div class="panel-body"
					ng-class="{&#39;has-error&#39;: !ordineDetailForm.itemOrdine.$valid, &#39;has-success&#39;: ordineDetailForm.itemOrdine.$valid}">
					<label id="itemOrdine">itemOrdine</label>
					<button ng-click="showItemOrdineDetail()" class="btn btn-default">Add
						new itemOrdine</button>
					<div id="itemOrdine"
						ng-if="selectedEntity.itemOrdineList.length&gt;0">
						<div style="top: 100px" ui-grid="itemOrdineListGridOptions"
							ui-grid-pagination="" ui-grid-selection=""></div>
					</div>
				</div>
				<div class="panel-body"></div>
				<div class="panel-body">
					<div class="pull-left">
						<form id="ordineActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default"
								ng-if="selectedEntity.ordineId==undefined">Insert</button>
							<button ng-click="update()" class="btn btn-default"
								ng-if="selectedEntity.ordineId&gt;0">Update</button>
							<button ng-click="del()" class="btn btn-default"
								ng-if="selectedEntity.ordineId&gt;0">Delete</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="colloController">
		<form id="colloList" ng-if="entityList.length&gt;0" enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">List collo</div>
				<div class="panel-body">
					<div ui-grid="colloGridOptions" ui-grid-pagination=""
						ui-grid-selection=""></div>
				</div>
			</div>
		</form>
		<form id="colloDetailForm" name="colloDetailForm"
			ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail collo {{
					selectedEntity.colloId }}</div>
				<div class="panel-body">
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !colloDetailForm.colloId.$valid, &#39;has-success&#39;: colloDetailForm.colloId.$valid}">
						<label for="colloId">colloId</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.colloId" ng-readonly="false"
							name="colloId" placeholder="colloId" id="collo-colloId" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !colloDetailForm.codice.$valid, &#39;has-success&#39;: colloDetailForm.codice.$valid}">
						<label for="codice">codice</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.codice" ng-readonly="false"
							name="codice" placeholder="codice" id="collo-codice" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !colloDetailForm.cutoffRoadnet.$valid, &#39;has-success&#39;: colloDetailForm.cutoffRoadnet.$valid}">
						<label for="cutoffRoadnet">cutoffRoadnet</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.cutoffRoadnet"
							ng-readonly="false" name="cutoffRoadnet"
							placeholder="cutoffRoadnet" id="collo-cutoffRoadnet" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !colloDetailForm.ordinamentoReport.$valid, &#39;has-success&#39;: colloDetailForm.ordinamentoReport.$valid}">
						<label for="ordinamentoReport">ordinamentoReport</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.ordinamentoReport"
							ng-readonly="false" name="ordinamentoReport"
							placeholder="ordinamentoReport" id="collo-ordinamentoReport" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !colloDetailForm.tsGenerazione.$valid, &#39;has-success&#39;: colloDetailForm.tsGenerazione.$valid}">
						<label for="tsGenerazione">tsGenerazione</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="selectedEntity.tsGenerazione"
							ng-readonly="false" name="tsGenerazione"
							placeholder="tsGenerazione" id="collo-tsGenerazione" />
					</div>
				</div>
				<div class="panel-body">
					<div class="pull-left">
						<form id="colloActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default"
								ng-if="selectedEntity.colloId==undefined">Insert</button>
							<button ng-click="update()" class="btn btn-default"
								ng-if="selectedEntity.colloId&gt;0">Update</button>
							<button ng-click="del()" class="btn btn-default"
								ng-if="selectedEntity.colloId&gt;0">Delete</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="itemOrdineController">
		<form id="itemOrdineList" ng-if="entityList.length&gt;0"
			enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">List itemOrdine</div>
				<div class="panel-body">
					<div ui-grid="itemOrdineGridOptions" ui-grid-pagination=""
						ui-grid-selection=""></div>
				</div>
			</div>
		</form>
		<form id="itemOrdineDetailForm" name="itemOrdineDetailForm"
			ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail itemOrdine {{
					selectedEntity.itemOrdineId }}</div>
				<div class="panel-body">
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.itemOrdineId.$valid, &#39;has-success&#39;: itemOrdineDetailForm.itemOrdineId.$valid}">
						<label for="itemOrdineId">itemOrdineId</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.itemOrdineId"
							ng-readonly="false" name="itemOrdineId"
							placeholder="itemOrdineId" id="itemOrdine-itemOrdineId" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.barcode.$valid, &#39;has-success&#39;: itemOrdineDetailForm.barcode.$valid}">
						<label for="barcode">barcode</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.barcode" ng-readonly="false"
							name="barcode" placeholder="barcode" id="itemOrdine-barcode" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.carfCanaleLocale.$valid, &#39;has-success&#39;: itemOrdineDetailForm.carfCanaleLocale.$valid}">
						<label for="carfCanaleLocale">carfCanaleLocale</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.carfCanaleLocale"
							ng-readonly="false" name="carfCanaleLocale"
							placeholder="carfCanaleLocale" id="itemOrdine-carfCanaleLocale"
							ng-required="true" /><small class="help-block"
							ng-show="itemOrdineDetailForm.carfCanaleLocale.$error.required">itemOrdine:
							carfCanaleLocale required</small>
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.daCancellare.$valid, &#39;has-success&#39;: itemOrdineDetailForm.daCancellare.$valid}">
						<label for="daCancellare">daCancellare</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.daCancellare"
							ng-readonly="false" name="daCancellare"
							placeholder="daCancellare" id="itemOrdine-daCancellare" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.format.$valid, &#39;has-success&#39;: itemOrdineDetailForm.format.$valid}">
						<label for="format">format</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.format" ng-readonly="false"
							name="format" placeholder="format" id="itemOrdine-format" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.idStatoFeedback.$valid, &#39;has-success&#39;: itemOrdineDetailForm.idStatoFeedback.$valid}">
						<label for="idStatoFeedback">idStatoFeedback</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.idStatoFeedback"
							ng-readonly="false" name="idStatoFeedback"
							placeholder="idStatoFeedback" id="itemOrdine-idStatoFeedback" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.idStatoItemOrdine.$valid, &#39;has-success&#39;: itemOrdineDetailForm.idStatoItemOrdine.$valid}">
						<label for="idStatoItemOrdine">idStatoItemOrdine</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.idStatoItemOrdine"
							ng-readonly="false" name="idStatoItemOrdine"
							placeholder="idStatoItemOrdine" id="itemOrdine-idStatoItemOrdine" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.idStatoPreparazione.$valid, &#39;has-success&#39;: itemOrdineDetailForm.idStatoPreparazione.$valid}">
						<label for="idStatoPreparazione">idStatoPreparazione</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.idStatoPreparazione"
							ng-readonly="false" name="idStatoPreparazione"
							placeholder="idStatoPreparazione"
							id="itemOrdine-idStatoPreparazione" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.infoFeedback.$valid, &#39;has-success&#39;: itemOrdineDetailForm.infoFeedback.$valid}">
						<label for="infoFeedback">infoFeedback</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.infoFeedback"
							ng-readonly="false" name="infoFeedback"
							placeholder="infoFeedback" id="itemOrdine-infoFeedback" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.name.$valid, &#39;has-success&#39;: itemOrdineDetailForm.name.$valid}">
						<label for="name">name</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.name" ng-readonly="false" name="name"
							placeholder="name" id="itemOrdine-name" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.note.$valid, &#39;has-success&#39;: itemOrdineDetailForm.note.$valid}">
						<label for="note">note</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.note" ng-readonly="false" name="note"
							placeholder="note" id="itemOrdine-note" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.priorita.$valid, &#39;has-success&#39;: itemOrdineDetailForm.priorita.$valid}">
						<label for="priorita">priorita</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.priorita" ng-readonly="false"
							name="priorita" placeholder="priorita" id="itemOrdine-priorita" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.productStatus.$valid, &#39;has-success&#39;: itemOrdineDetailForm.productStatus.$valid}">
						<label for="productStatus">productStatus</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.productStatus"
							ng-readonly="false" name="productStatus"
							placeholder="productStatus" id="itemOrdine-productStatus" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.promoPunti.$valid, &#39;has-success&#39;: itemOrdineDetailForm.promoPunti.$valid}">
						<label for="promoPunti">promoPunti</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.promoPunti"
							ng-readonly="false" name="promoPunti" placeholder="promoPunti"
							id="itemOrdine-promoPunti" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.quantityFinale.$valid, &#39;has-success&#39;: itemOrdineDetailForm.quantityFinale.$valid}">
						<label for="quantityFinale">quantityFinale</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.quantityFinale"
							ng-readonly="false" name="quantityFinale"
							placeholder="quantityFinale" id="itemOrdine-quantityFinale" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.quantityIniz.$valid, &#39;has-success&#39;: itemOrdineDetailForm.quantityIniz.$valid}">
						<label for="quantityIniz">quantityIniz</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.quantityIniz"
							ng-readonly="false" name="quantityIniz"
							placeholder="quantityIniz" id="itemOrdine-quantityIniz" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.singleUnitWeight.$valid, &#39;has-success&#39;: itemOrdineDetailForm.singleUnitWeight.$valid}">
						<label for="singleUnitWeight">singleUnitWeight</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.singleUnitWeight"
							ng-readonly="false" name="singleUnitWeight"
							placeholder="singleUnitWeight" id="itemOrdine-singleUnitWeight" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.sostituito.$valid, &#39;has-success&#39;: itemOrdineDetailForm.sostituito.$valid}">
						<label for="sostituito">sostituito</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.sostituito"
							ng-readonly="false" name="sostituito" placeholder="sostituito"
							id="itemOrdine-sostituito" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.tsModifica.$valid, &#39;has-success&#39;: itemOrdineDetailForm.tsModifica.$valid}">
						<label for="tsModifica">tsModifica</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="selectedEntity.tsModifica"
							ng-readonly="false" name="tsModifica" placeholder="tsModifica"
							id="itemOrdine-tsModifica" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.tsPreparazione.$valid, &#39;has-success&#39;: itemOrdineDetailForm.tsPreparazione.$valid}">
						<label for="tsPreparazione">tsPreparazione</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy"
							ng-model="selectedEntity.tsPreparazione" ng-readonly="false"
							name="tsPreparazione" placeholder="tsPreparazione"
							id="itemOrdine-tsPreparazione" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.vOrderItemId.$valid, &#39;has-success&#39;: itemOrdineDetailForm.vOrderItemId.$valid}">
						<label for="vOrderItemId">vOrderItemId</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.vOrderItemId"
							ng-readonly="false" name="vOrderItemId"
							placeholder="vOrderItemId" id="itemOrdine-vOrderItemId" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.vPrice.$valid, &#39;has-success&#39;: itemOrdineDetailForm.vPrice.$valid}">
						<label for="vPrice">vPrice</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.vPrice" ng-readonly="false"
							name="vPrice" placeholder="vPrice" id="itemOrdine-vPrice" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.vProductId.$valid, &#39;has-success&#39;: itemOrdineDetailForm.vProductId.$valid}">
						<label for="vProductId">vProductId</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.vProductId"
							ng-readonly="false" name="vProductId" placeholder="vProductId"
							id="itemOrdine-vProductId" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.weightFinale.$valid, &#39;has-success&#39;: itemOrdineDetailForm.weightFinale.$valid}">
						<label for="weightFinale">weightFinale</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.weightFinale"
							ng-readonly="false" name="weightFinale"
							placeholder="weightFinale" id="itemOrdine-weightFinale" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.weightIniz.$valid, &#39;has-success&#39;: itemOrdineDetailForm.weightIniz.$valid}">
						<label for="weightIniz">weightIniz</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.weightIniz"
							ng-readonly="false" name="weightIniz" placeholder="weightIniz"
							id="itemOrdine-weightIniz" />
					</div>
				</div>
				<div class="panel-body"
					ng-class="{&#39;has-error&#39;: !itemOrdineDetailForm.itemOrdineCodice.$valid, &#39;has-success&#39;: itemOrdineDetailForm.itemOrdineCodice.$valid}">
					<label id="itemOrdineCodice">itemOrdineCodice</label>
					<button ng-click="showItemOrdineCodiceDetail()"
						class="btn btn-default">Add new itemOrdineCodice</button>
					<div id="itemOrdineCodice"
						ng-if="selectedEntity.itemOrdineCodiceList.length&gt;0">
						<div style="top: 100px" ui-grid="itemOrdineCodiceListGridOptions"
							ui-grid-pagination="" ui-grid-selection=""></div>
					</div>
				</div>
				<div class="panel-body"></div>
				<div class="panel-body">
					<div class="pull-left">
						<form id="itemOrdineActionButton" ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default"
								ng-if="selectedEntity.itemOrdineId==undefined">Insert</button>
							<button ng-click="update()" class="btn btn-default"
								ng-if="selectedEntity.itemOrdineId&gt;0">Update</button>
							<button ng-click="del()" class="btn btn-default"
								ng-if="selectedEntity.itemOrdineId&gt;0">Delete</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div ng-controller="itemOrdineCodiceController">
		<form id="itemOrdineCodiceList" ng-if="entityList.length&gt;0"
			enctype="UTF-8">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">List itemOrdineCodice</div>
				<div class="panel-body">
					<div ui-grid="itemOrdineCodiceGridOptions" ui-grid-pagination=""
						ui-grid-selection=""></div>
				</div>
			</div>
		</form>
		<form id="itemOrdineCodiceDetailForm"
			name="itemOrdineCodiceDetailForm" ng-show="selectedEntity.show">
			<div class="panel panel-default default-panel">
				<div class="panel-heading">Detail itemOrdineCodice {{
					selectedEntity.itemOrdineCodiceId }}</div>
				<div class="panel-body">
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineCodiceDetailForm.itemOrdineCodiceId.$valid, &#39;has-success&#39;: itemOrdineCodiceDetailForm.itemOrdineCodiceId.$valid}">
						<label for="itemOrdineCodiceId">itemOrdineCodiceId</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.itemOrdineCodiceId"
							ng-readonly="false" name="itemOrdineCodiceId"
							placeholder="itemOrdineCodiceId"
							id="itemOrdineCodice-itemOrdineCodiceId" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineCodiceDetailForm.barcode.$valid, &#39;has-success&#39;: itemOrdineCodiceDetailForm.barcode.$valid}">
						<label for="barcode">barcode</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.barcode" ng-readonly="false"
							name="barcode" placeholder="barcode"
							id="itemOrdineCodice-barcode" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineCodiceDetailForm.barcodeRead.$valid, &#39;has-success&#39;: itemOrdineCodiceDetailForm.barcodeRead.$valid}">
						<label for="barcodeRead">barcodeRead</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.barcodeRead"
							ng-readonly="false" name="barcodeRead" placeholder="barcodeRead"
							id="itemOrdineCodice-barcodeRead" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineCodiceDetailForm.daCancellare.$valid, &#39;has-success&#39;: itemOrdineCodiceDetailForm.daCancellare.$valid}">
						<label for="daCancellare">daCancellare</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.daCancellare"
							ng-readonly="false" name="daCancellare"
							placeholder="daCancellare" id="itemOrdineCodice-daCancellare" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineCodiceDetailForm.idStato.$valid, &#39;has-success&#39;: itemOrdineCodiceDetailForm.idStato.$valid}">
						<label for="idStato">idStato</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.idStato" ng-readonly="false"
							name="idStato" placeholder="idStato"
							id="itemOrdineCodice-idStato" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineCodiceDetailForm.name.$valid, &#39;has-success&#39;: itemOrdineCodiceDetailForm.name.$valid}">
						<label for="name">name</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.name" ng-readonly="false" name="name"
							placeholder="name" id="itemOrdineCodice-name" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineCodiceDetailForm.nelCarrello.$valid, &#39;has-success&#39;: itemOrdineCodiceDetailForm.nelCarrello.$valid}">
						<label for="nelCarrello">nelCarrello</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.nelCarrello"
							ng-readonly="false" name="nelCarrello" placeholder="nelCarrello"
							id="itemOrdineCodice-nelCarrello" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineCodiceDetailForm.quantityFinale.$valid, &#39;has-success&#39;: itemOrdineCodiceDetailForm.quantityFinale.$valid}">
						<label for="quantityFinale">quantityFinale</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.quantityFinale"
							ng-readonly="false" name="quantityFinale"
							placeholder="quantityFinale" id="itemOrdineCodice-quantityFinale" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineCodiceDetailForm.tsModifica.$valid, &#39;has-success&#39;: itemOrdineCodiceDetailForm.tsModifica.$valid}">
						<label for="tsModifica">tsModifica</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ui-date="{ dateFormat: &#39;dd/mm/yy&#39; }"
							ui-date-format="dd/mm/yy" ng-model="selectedEntity.tsModifica"
							ng-readonly="false" name="tsModifica" placeholder="tsModifica"
							id="itemOrdineCodice-tsModifica" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineCodiceDetailForm.vPrice.$valid, &#39;has-success&#39;: itemOrdineCodiceDetailForm.vPrice.$valid}">
						<label for="vPrice">vPrice</label><input class="form-control "
							aria-describedby="sizing-addon3" type="text"
							ng-model="selectedEntity.vPrice" ng-readonly="false"
							name="vPrice" placeholder="vPrice" id="itemOrdineCodice-vPrice" />
					</div>
					<div class="pull-left right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineCodiceDetailForm.vProductId.$valid, &#39;has-success&#39;: itemOrdineCodiceDetailForm.vProductId.$valid}">
						<label for="vProductId">vProductId</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.vProductId"
							ng-readonly="false" name="vProductId" placeholder="vProductId"
							id="itemOrdineCodice-vProductId" />
					</div>
					<div class="pull-right right-input"
						ng-class="{&#39;has-error&#39;: !itemOrdineCodiceDetailForm.weightFinale.$valid, &#39;has-success&#39;: itemOrdineCodiceDetailForm.weightFinale.$valid}">
						<label for="weightFinale">weightFinale</label><input
							class="form-control " aria-describedby="sizing-addon3"
							type="text" ng-model="selectedEntity.weightFinale"
							ng-readonly="false" name="weightFinale"
							placeholder="weightFinale" id="itemOrdineCodice-weightFinale" />
					</div>
				</div>
				<div class="panel-body">
					<div class="pull-left">
						<form id="itemOrdineCodiceActionButton"
							ng-if="selectedEntity.show">
							<button ng-click="insert()" class="btn btn-default"
								ng-if="selectedEntity.itemOrdineCodiceId==undefined">Insert</button>
							<button ng-click="update()" class="btn btn-default"
								ng-if="selectedEntity.itemOrdineCodiceId&gt;0">Update</button>
							<button ng-click="del()" class="btn btn-default"
								ng-if="selectedEntity.itemOrdineCodiceId&gt;0">Delete</button>
						</form>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>