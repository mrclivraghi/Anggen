<!-- 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Login Page</title>
<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

#login-box {
	width: 300px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}
</style>
</head>
<body onload='document.loginForm.username.focus();'>

	<h1>Spring Security Custom Login Form (Annotation)</h1>

	<div id="login-box">

		<h2>Login with Username and Password</h2>

		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>

		<form name='loginForm'
		    action="<c:url value='../login' />" method='post'>

		    <table>
			<tr>
				<td>User:</td>
				<td><input type='text' name='user' value=''></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='pass' /></td>
			</tr>
			<tr>
			        <td colspan='2'>
                                <input name="submit" type="submit" value="Login" />
                                </td>
			</tr>
		   </table>

		   <input type="hidden" 
                     name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
	</div>

</body>
</html>


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
<body >
	<div ng-view class="container">
	<div class="alert alert-danger" ng-show="error">
	There was a problem logging in. Please try again.
</div>
<form action="../login" method="get">
	<div class="form-group">
		<label for="username">Username:</label> <input type="text"
			class="form-control" id="username" name="username"/>
	</div>
	<div class="form-group">
		<label for="password">Password:</label> <input type="password"
			class="form-control" id="password" name="password"/>
	</div>
	 <input type="hidden" 
                     name="${_csrf.parameterName}" value="${_csrf.token}" />
	<button type="submit" class="btn btn-primary" name="submit" value="Login">Submit</button>
</form>
	
	</div>
</body>
</html> -->


<html><head><title>Login Page</title></head><body onload='document.f.username.focus();'>
<h3>custom Login with Username and Password</h3><form name='f' action='/ServerTestApp/login' method='POST'>
 <table>
    <tr><td>User:</td><td><input type='text' name='username'></td></tr>
    <tr><td>Password:</td><td><input type='password' name='password'/></td></tr>
    <tr><td colspan='2'><input name="submit" type="submit" value="Login"/></td></tr>
    <input type="hidden" 
                     name="${_csrf.parameterName}" value="${_csrf.token}" />
  </table>
</form></body></html>