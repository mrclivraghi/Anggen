<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><nav class="navbar navbar-default"><div class="container-fluid"><div class="navbar-header"><button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false"><span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button><a class="navbar-brand" href="#">test</a></div><div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1"><ul class="nav navbar-nav"><li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">domain<span class="caret"></span></a><ul class="dropdown-menu"><li ng-if="restrictionList.place.canSearch || restrictionList.place==undefined"><a href="../place/">Place</a></li><li ng-if="restrictionList.example.canSearch || restrictionList.example==undefined"><a href="../example/">Example</a></li></ul></li></ul></div></div></nav>