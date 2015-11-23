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
						test="${entity.entityName=='role' ||entity.entityName=='field' ||entity.entityName=='entity' ||entity.entityName=='relationship' ||entity.entityName=='enumValue' ||entity.entityName=='enumField' ||entity.entityName=='annotation' ||entity.entityName=='restriction' ||entity.entityName=='annotationAttribute' ||entity.entityName=='tab' ||entity.entityName=='user'}">
						<c:set var="fill" value="1" />
					</c:if>
				</c:forEach>
				<c:if test="${fill==1}">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">domain<span class="caret"></span></a>
					<ul class="dropdown-menu">
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='role'}">
									<li><a href="../role/">role</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='field'}">
									<li><a href="../field/">field</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='entity'}">
									<li><a href="../entity/">entity</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='relationship'}">
									<li><a href="../relationship/">relationship</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='enumValue'}">
									<li><a href="../enumValue/">enumValue</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='enumField'}">
									<li><a href="../enumField/">enumField</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='annotation'}">
									<li><a href="../annotation/">annotation</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='restriction'}">
									<li><a href="../restriction/">restriction</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='annotationAttribute'}">
									<li><a href="../annotationAttribute/">annotationAttribute</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='tab'}">
									<li><a href="../tab/">tab</a></li>
								</c:if>
							</c:forEach>
							<c:forEach var="entity" items="${entityList}">
								<c:if test="${entity.entityName=='user'}">
									<li><a href="../user/">user</a></li>
								</c:if>
							</c:forEach>
						</ul></li>
				</c:if>
			</ul>
		</div>
	</div>
</nav>