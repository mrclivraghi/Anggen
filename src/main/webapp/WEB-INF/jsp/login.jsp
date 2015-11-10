<!DOCTYPE html>
<html>
<head>
<title>itemOrdineCodice</title>
<link
	href="../css/bootstrap.min.css"
	rel="stylesheet">
<style type="text/css">
[ng\:cloak], [ng-cloak], .ng-cloak {
	display: none !important;
}
</style>
</head>
<body ng-app="hello" ng-cloak class="ng-cloak">
	<div ng-view class="container">
	<div class="alert alert-danger" ng-show="error">
	There was a problem logging in. Please try again.
</div>
<form role="form" ng-submit="login()">
	<div class="form-group">
		<label for="username">Username:</label> <input type="text"
			class="form-control" id="username" name="username" ng-model="credentials.username"/>
	</div>
	<div class="form-group">
		<label for="password">Password:</label> <input type="password"
			class="form-control" id="password" name="password" ng-model="credentials.password"/>
	</div>
	<button type="submit" class="btn btn-primary">Submit</button>
</form>
	
	</div>
	<script src="../js/angular.js" type="text/javascript"></script>
	<script src="../js/hello.js"></script>
</body>>
</html>