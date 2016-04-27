#ANGGEN

## Intro
The main goal of this project is to enable the generation of a crud schema based on a model composed of POJO Java class.
The rest api are generated on Spring, using Spring Data and Spring Security.
The client application is based on AngularJS, using Bower and Gulp.

##How to

###First step: Java model to meta info.
Create a JUnit test class based on it.anggen.Application autowiring BeanToDBConverter and call the method convert.

###Second step: Generate the code
In a Junit test class autowire the Generator class and run the generate method.

##Main properties
application.model.package -> package where you have the starter model
application.package.main -> start package where you generate 
application.rest.url -> endpoint of your rest api
application.cors.origin -> cors origin

##Annotation available
Entity
@Cache -> the entity will be cached
@DisableViewGeneration 
@EnableRestrictionData 
@MaxDescendantLevel -> max level for children in a tree
@SecurityType -> access_with_permission or block_with_restriction

@GenerateFrontEnd -> beta

Field
@Between -> you will be able to search through this field between two values
@DescriptionField -> field used as a description label
@embedded -> the field will be of type embedded
@ExcelExport -> field included in the excel export
@Filter -> this field will be used in the search form of the parent entities
@IgnoreSearch
@IgnoreTableList
@IgnoreUpdate
@Password -> the field will be a password and so stored using BCrypt
@Priority -> to define the order in the search/detail forms
@Tab -> field will be part of the mentioned tab




