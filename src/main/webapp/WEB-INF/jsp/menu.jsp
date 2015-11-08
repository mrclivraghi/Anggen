<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><nav
	class="navbar navbar-default">
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
				<c:set var="fill" value="0" />
				<c:forEach var="entity" items="${entityList}">
					<c:if
						test="${entity.entityName=='order' ||entity.entityName=='person' ||entity.entityName=='place'}">
						<c:set var="fill" value="1" />
					</c:if>
				</c:forEach>
				<c:if test="${fill==1}">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">test<span class="caret"></span></a>
					<ul class="dropdown-menu">
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='order'}">
									<li><a href="../order/">order</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='person'}">
									<li><a href="../person/">person</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='place'}">
									<li><a href="../place/">place</a></li>
								</c:if>
							</c:forEach>
						</ul></li>
				</c:if>
				<c:set var="fill" value="0" />
				<c:forEach var="entity" items="${entityList}">
					<c:if
						test="${entity.entityName=='itemOrdine' ||entity.entityName=='collo' ||entity.entityName=='ordine' ||entity.entityName=='itemOrdineCodice'}">
						<c:set var="fill" value="1" />
					</c:if>
				</c:forEach>
				<c:if test="${fill==1}">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">storepicking<span class="caret"></span></a>
					<ul class="dropdown-menu">
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='itemOrdine'}">
									<li><a href="../itemOrdine/">itemOrdine</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='collo'}">
									<li><a href="../collo/">collo</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='ordine'}">
									<li><a href="../ordine/">ordine</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='itemOrdineCodice'}">
									<li><a href="../itemOrdineCodice/">itemOrdineCodice</a></li>
								</c:if>
							</c:forEach>
						</ul></li>
				</c:if>
				<c:set var="fill" value="0" />
				<c:forEach var="entity" items="${entityList}">
					<c:if
						test="${entity.entityName=='photo' ||entity.entityName=='seedQuery' ||entity.entityName=='mountain'}">
						<c:set var="fill" value="1" />
					</c:if>
				</c:forEach>
				<c:if test="${fill==1}">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">mountain<span class="caret"></span></a>
					<ul class="dropdown-menu">
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='photo'}">
									<li><a href="../photo/">photo</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='seedQuery'}">
									<li><a href="../seedQuery/">seedQuery</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='mountain'}">
									<li><a href="../mountain/">mountain</a></li>
								</c:if>
							</c:forEach>
						</ul></li>
				</c:if>
				<c:set var="fill" value="0" />
				<c:forEach var="entity" items="${entityList}">
					<c:if
						test="${entity.entityName=='entity' ||entity.entityName=='user' ||entity.entityName=='role'}">
						<c:set var="fill" value="1" />
					</c:if>
				</c:forEach>
				<c:if test="${fill==1}">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">security<span class="caret"></span></a>
					<ul class="dropdown-menu">
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='entity'}">
									<li><a href="../entity/">entity</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='user'}">
									<li><a href="../user/">user</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='role'}">
									<li><a href="../role/">role</a></li>
								</c:if>
							</c:forEach>
						</ul></li>
				</c:if>
				<c:set var="fill" value="0" />
				<c:forEach var="entity" items="${entityList}">
					<c:if
						test="${entity.entityName=='paziente' ||entity.entityName=='ambulatorio' ||entity.entityName=='fascicolo'}">
						<c:set var="fill" value="1" />
					</c:if>
				</c:forEach>
				<c:if test="${fill==1}">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">ospedale<span class="caret"></span></a>
					<ul class="dropdown-menu">
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='paziente'}">
									<li><a href="../paziente/">paziente</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='ambulatorio'}">
									<li><a href="../ambulatorio/">ambulatorio</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='fascicolo'}">
									<li><a href="../fascicolo/">fascicolo</a></li>
								</c:if>
							</c:forEach>
						</ul></li>
				</c:if>
				<c:forEach var="entity" items="${entityList}">
					<c:if test="${entity.entityName=='example'}">
						<li><a href="../example/">example</a></li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
	</div>
</nav>