#ANGGEN

## Intro
The main goal of this project is to enable the generation of a crud schema based on a model composed of POJO Java class.
The rest api are generated on Spring, using Spring Data and Spring Security.
The client application is based on AngularJS, using Bower and Gulp.

##How to

###First step: Java model to meta info.
Create a JUnit test class based on ServerTestApplication autowiring BeanToDBConverter and call the method convert.

###Second step: Generate the code
In a Junit test class autowire the Generator class and run the generate method.

##Main properties
application.model.package -> package where you have the starter model
application.package.main -> start package where you generate 
application.rest.url -> endpoint of your rest api
application.cors.origin -> cors origin



