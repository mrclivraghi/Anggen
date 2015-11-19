<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span
					class="icon-bar"></span><span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">AngGen</a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">test<span class="caret"></span></a>
				<ul class="dropdown-menu">
						<li><a href="../order/">order</a></li>
						<li><a href="../peron/">person</a></li>
						<li><a href="../place/">place</a></li>
					</ul></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">storepicking<span class="caret"></span></a>
				<ul class="dropdown-menu">
						<li><a href="../itemOrdine/">itemOrdine</a></li>
						<li><a href="../collo/">collo</a></li>
						<li><a href="../ordine/">ordine</a></li>
						<c:forEach var="entity" items="${entityList}">
							<c:if test="${entity.entityName=='itemOrdineCodice'}">
								<li><a href="../itemOrdineCodice/">itemOrdineCodice</a></li>
							</c:if>
						</c:forEach>
					</ul></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">mountain<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="../photo/">photo</a></li>
						<li><a href="../seedQuery/">seedQuery</a></li>
						
						<c:forEach var="entity" items="${entityList}">
							<c:if test="${entity.entityName=='mountain'}">
								<li><a href="../mountain/">mountain</a></li>
							</c:if>
						</c:forEach>
						


					</ul></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">security<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="../entity/">entity</a></li>
						<li><a href="../user/">user</a></li>
						<li><a href="../role/">role</a></li>
					</ul></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">ospedale<span class="caret"></span></a>
				<ul class="dropdown-menu">
						<li><a href="../paziente/">paziente</a></li>
						<li><a href="../ambulatorio/">ambulatorio</a></li>
						<li><a href="../fascicolo/">fascicolo</a></li>
					</ul></li>
				<li><a href="../example/">example</a></li>
			</ul>
		</div>
	</div>
</nav>