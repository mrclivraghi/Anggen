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
			<a class="navbar-brand" href="metrics">anggen</a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">restrictiondata<span class="caret"></span></a>
				<ul class="dropdown-menu"></ul></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">security<span class="caret"></span></a>
				<ul class="dropdown-menu">
						<li
							ng-if="restrictionList.restrictionEntityGroup.canSearch || restrictionList.restrictionEntityGroup==undefined"><a
							href="RestrictionEntityGroup">RestrictionEntityGroup</a></li>
						<li
							ng-if="restrictionList.user.canSearch || restrictionList.user==undefined"><a
							href="User">User</a></li>
						<li
							ng-if="restrictionList.role.canSearch || restrictionList.role==undefined"><a
							href="Role">Role</a></li>
						<li
							ng-if="restrictionList.restrictionField.canSearch || restrictionList.restrictionField==undefined"><a
							href="RestrictionField">RestrictionField</a></li>
						<li
							ng-if="restrictionList.restrictionEntity.canSearch || restrictionList.restrictionEntity==undefined"><a
							href="RestrictionEntity">RestrictionEntity</a></li>
					</ul></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">field<span class="caret"></span></a>
				<ul class="dropdown-menu">
						<li
							ng-if="restrictionList.field.canSearch || restrictionList.field==undefined"><a
							href="Field">Field</a></li>
						<li
							ng-if="restrictionList.enumField.canSearch || restrictionList.enumField==undefined"><a
							href="EnumField">EnumField</a></li>
						<li
							ng-if="restrictionList.annotationAttribute.canSearch || restrictionList.annotationAttribute==undefined"><a
							href="AnnotationAttribute">AnnotationAttribute</a></li>
						<li
							ng-if="restrictionList.annotation.canSearch || restrictionList.annotation==undefined"><a
							href="Annotation">Annotation</a></li>
						<li
							ng-if="restrictionList.enumValue.canSearch || restrictionList.enumValue==undefined"><a
							href="EnumValue">EnumValue</a></li>
					</ul></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">entity<span class="caret"></span></a>
				<ul class="dropdown-menu">
						<li
							ng-if="restrictionList.project.canSearch || restrictionList.project==undefined"><a
							href="Project">Project</a></li>
						<li
							ng-if="restrictionList.entity.canSearch || restrictionList.entity==undefined"><a
							href="Entity">Entity</a></li>
						<li
							ng-if="restrictionList.enumEntity.canSearch || restrictionList.enumEntity==undefined"><a
							href="EnumEntity">EnumEntity</a></li>
						<li
							ng-if="restrictionList.tab.canSearch || restrictionList.tab==undefined"><a
							href="Tab">Tab</a></li>
						<li
							ng-if="restrictionList.entityGroup.canSearch || restrictionList.entityGroup==undefined"><a
							href="EntityGroup">EntityGroup</a></li>
					</ul></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">relationship<span class="caret"></span></a>
				<ul class="dropdown-menu">
						<li
							ng-if="restrictionList.relationship.canSearch || restrictionList.relationship==undefined"><a
							href="Relationship">Relationship</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</nav>