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
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">test<span class="caret"></span></a>
				<ul class="dropdown-menu">
						<li
							ng-if="restrictionList.project.canSearch || restrictionList.project==undefined"><a
							href="../project/">Project</a></li>
						<li
							ng-if="restrictionList.EntityGroupTest.canSearch || restrictionList.EntityGroupTest==undefined"><a
							href="../entityGroupTest/">EntityGroupTest</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</nav>