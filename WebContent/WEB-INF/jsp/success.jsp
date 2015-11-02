<html>
<body>
<table><tr><td>
 ${msg}
 </td></tr>
 <tr><td>
 <a href="j_spring_security_logout">logout </a>
 </td></tr>
 </table>
 <http auto-config='true'>
     <intercept-url pattern="/**" access="ROLE_USER" />
    <form-login login-page='/login.jsp' default-target-url="/loginSuccess.jsp" always-use-default-target='true' />
</http>
 </body>
 </html>