(function() { 
'use strict'; 

angular
.module("serverTestApp").config(routerConfig);
/** @ngInject */
function routerConfig($stateProvider,$urlRouterProvider)
{
$stateProvider
.state('main',{
 url:'/app',
abstract:true,
templateUrl:'app/main/main.html',
 controller:'MainController', 
controllerAs: 'main' ,
name: 'main'
 })
.state('main.home',{
 url:'/home',
views:{
'search': {
templateUrl:'app/components/home/home.html',
 controller:'HomeController', 
controllerAs: 'vm' 
 }
}
})
.state('main.metrics',{
 url:'/metrics',
views:{
'pageContent': {
templateUrl:'app/components/metrics/metrics.html',
 controller:'MetricsController', 
controllerAs: 'vm' 
 }
}
})
.state('main.user',{
 url:'/user',
views:{
'pageContent': {
templateUrl:'app/controller/user/user-template.html',
 controller:'HomeController', 
controllerAs: 'vm' 
 }
},
resolve: {
}
})
.state('main.restrictionField',{
 url:'/restrictionField',
views:{
'pageContent': {
templateUrl:'app/controller/restrictionField/restrictionField-template.html',
 controller:'HomeController', 
controllerAs: 'vm' 
 }
},
resolve: {
}
})
.state('main.restrictionEntityGroup',{
 url:'/restrictionEntityGroup',
views:{
'pageContent': {
templateUrl:'app/controller/restrictionEntityGroup/restrictionEntityGroup-template.html',
 controller:'HomeController', 
controllerAs: 'vm' 
 }
},
resolve: {
}
})
.state('main.enumEntity',{
 url:'/enumEntity',
views:{
'pageContent': {
templateUrl:'app/controller/enumEntity/enumEntity-template.html',
 controller:'HomeController', 
controllerAs: 'vm' 
 }
},
resolve: {
}
})
.state('main.project',{
 url:'/project',
views:{
'pageContent': {
templateUrl:'app/controller/project/project-template.html',
 controller:'HomeController', 
controllerAs: 'vm' 
 }
},
resolve: {
}
})
.state('main.entity',{
 url:'/entity',
views:{
'pageContent': {
templateUrl:'app/controller/entity/entity-template.html',
 controller:'HomeController', 
controllerAs: 'vm' 
 }
},
resolve: {
}
})
.state('main.enumField',{
 url:'/enumField',
views:{
'pageContent': {
templateUrl:'app/controller/enumField/enumField-template.html',
 controller:'HomeController', 
controllerAs: 'vm' 
 }
},
resolve: {
}
})
.state('main.enumValue',{
 url:'/enumValue',
views:{
'pageContent': {
templateUrl:'app/controller/enumValue/enumValue-template.html',
 controller:'HomeController', 
controllerAs: 'vm' 
 }
},
resolve: {
}
})
.state('main.annotationAttribute',{
 url:'/annotationAttribute',
views:{
'pageContent': {
templateUrl:'app/controller/annotationAttribute/annotationAttribute-template.html',
 controller:'HomeController', 
controllerAs: 'vm' 
 }
},
resolve: {
}
})
.state('main.annotation',{
 url:'/annotation',
views:{
'pageContent': {
templateUrl:'app/controller/annotation/annotation-template.html',
 controller:'HomeController', 
controllerAs: 'vm' 
 }
},
resolve: {
}
})
.state('main.generationRun',{
 url:'/generationRun',
views:{
'pageContent': {
templateUrl:'app/controller/generationRun/generationRun-template.html',
 controller:'HomeController', 
controllerAs: 'vm' 
 }
},
resolve: {
}
})
.state('main.relationship',{
 url:'/relationship',
views:{
'pageContent': {
templateUrl:'app/controller/relationship/relationship-template.html',
 controller:'HomeController', 
controllerAs: 'vm' 
 }
},
resolve: {
}
})
;$urlRouterProvider.otherwise('/app/home');
}
})();
