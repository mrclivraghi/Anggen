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
				'pageContent': {
					templateUrl:'app/controller/entity/entity-template.html',
					controller:'HomeController', 
					controllerAs: 'vm' 
				}
			}
		})


		/*  MARCO MOD */
		.state('test',{
			url:'/test',
			abstract:true,
			templateUrl: 'app/controller/abstractEntity/abstractEntity.html',
			controller:'HomeController', 
			controllerAs: 'main' ,
			name: 'main'
		})
		.state('test.abstractEntity',{
			url: '/entity',
			
			/*views: {
			'search': {
					templateUrl:'app/components/entity/entity-search.html',
					controller:'EntityController', 
					controllerAs: 'vm' 
				},
				'entity': {
					templateUrl:'app/components/entity/entity-detail.html',
					controller:'EntityController', 
					controllerAs: 'vm' 
				},
				'entityGroup': {
					templateUrl:'app/components/entityGroup/entityGroup-detail.html',
					controller:'EntityGroupController', 
					controllerAs: 'vm' 
				},
				'relationship': {
					templateUrl:'app/components/relationship/relationship-detail.html',
					controller:'RelationshipController', 
					controllerAs: 'vm' 
				}
				
			}*/
			
			
		})
.state('entity.abstractRole',{
			url: '/role',
			
			views: {
				'entity': {
					templateUrl:'app/components/role/role.html',
					controller:'RoleController', 
					controllerAs: 'vm' 
				},
				'entityGroup': {
					templateUrl:'app/components/entityGroup/entityGroup.html',
					controller:'EntityGroupController', 
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
		.state('main.role',{
			url:'/role',
			views:{
				'pageContent': {
					templateUrl:'app/components/role/role.html',
					controller:'RoleController', 
					controllerAs: 'vm' 
				}
			},
			resolve: {
				restrictionEntityChildrenList: function(roleService) {
					roleService.initRestrictionEntityList().then(function(response) {
						roleService.childrenList.restrictionEntityList=response.data;
					});
				}, 
				restrictionFieldChildrenList: function(roleService) {
					roleService.initRestrictionFieldList().then(function(response) {
						roleService.childrenList.restrictionFieldList=response.data;
					});
				}, 
				userChildrenList: function(roleService) {
					roleService.initUserList().then(function(response) {
						roleService.childrenList.userList=response.data;
					});
				}, 
				restrictionEntityGroupChildrenList: function(roleService) {
					roleService.initRestrictionEntityGroupList().then(function(response) {
						roleService.childrenList.restrictionEntityGroupList=response.data;
					});
				}, 
			}
		})
		.state('main.restrictionEntity',{
			url:'/restrictionEntity',
			views:{
				'pageContent': {
					templateUrl:'app/components/restrictionEntity/restrictionEntity.html',
					controller:'RestrictionEntityController', 
					controllerAs: 'vm' 
				}
			},
			resolve: {
				roleChildrenList: function(restrictionEntityService) {
					restrictionEntityService.initRoleList().then(function(response) {
						restrictionEntityService.childrenList.roleList=response.data;
					});
				}, 
				entityChildrenList: function(restrictionEntityService) {
					restrictionEntityService.initEntityList().then(function(response) {
						restrictionEntityService.childrenList.entityList=response.data;
					});
				}, 
			}
		})
		.state('main.restrictionField',{
			url:'/restrictionField',
			views:{
				'pageContent': {
					templateUrl:'app/components/restrictionField/restrictionField.html',
					controller:'RestrictionFieldController', 
					controllerAs: 'vm' 
				}
			},
			resolve: {
				roleChildrenList: function(restrictionFieldService) {
					restrictionFieldService.initRoleList().then(function(response) {
						restrictionFieldService.childrenList.roleList=response.data;
					});
				}, 
				fieldChildrenList: function(restrictionFieldService) {
					restrictionFieldService.initFieldList().then(function(response) {
						restrictionFieldService.childrenList.fieldList=response.data;
					});
				}, 
			}
		})
		.state('main.user',{
			url:'/user',
			views:{
				'pageContent': {
					templateUrl:'app/components/user/user.html',
					controller:'UserController', 
					controllerAs: 'vm' 
				}
			},
			resolve: {
				roleChildrenList: function(userService) {
					userService.initRoleList().then(function(response) {
						userService.childrenList.roleList=response.data;
					});
				}, 
			}
		})
		.state('main.restrictionEntityGroup',{
			url:'/restrictionEntityGroup',
			views:{
				'pageContent': {
					templateUrl:'app/components/restrictionEntityGroup/restrictionEntityGroup.html',
					controller:'RestrictionEntityGroupController', 
					controllerAs: 'vm' 
				}
			},
			resolve: {
				roleChildrenList: function(restrictionEntityGroupService) {
					restrictionEntityGroupService.initRoleList().then(function(response) {
						restrictionEntityGroupService.childrenList.roleList=response.data;
					});
				}, 
				entityGroupChildrenList: function(restrictionEntityGroupService) {
					restrictionEntityGroupService.initEntityGroupList().then(function(response) {
						restrictionEntityGroupService.childrenList.entityGroupList=response.data;
					});
				}, 
			}
		})
		.state('main.logEntry',{
			url:'/logEntry',
			views:{
				'pageContent': {
					templateUrl:'app/components/logEntry/logEntry.html',
					controller:'LogEntryController', 
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
					templateUrl:'app/components/entity/entity.html',
					controller:'EntityController', 
					controllerAs: 'vm' 
				}
			},
			resolve: {
				restrictionEntityChildrenList: function(entityService) {
					entityService.initRestrictionEntityList().then(function(response) {
						entityService.childrenList.restrictionEntityList=response.data;
					});
				}, 
				tabChildrenList: function(entityService) {
					entityService.initTabList().then(function(response) {
						entityService.childrenList.tabList=response.data;
					});
				}, 
				entityGroupChildrenList: function(entityService) {
					entityService.initEntityGroupList().then(function(response) {
						entityService.childrenList.entityGroupList=response.data;
					});
				}, 
				enumFieldChildrenList: function(entityService) {
					entityService.initEnumFieldList().then(function(response) {
						entityService.childrenList.enumFieldList=response.data;
					});
				}, 
				fieldChildrenList: function(entityService) {
					entityService.initFieldList().then(function(response) {
						entityService.childrenList.fieldList=response.data;
					});
				}, 
				relationshipChildrenList: function(entityService) {
					entityService.initRelationshipList().then(function(response) {
						entityService.childrenList.relationshipList=response.data;
					});
				}, 
			}
		})
		.state('main.enumEntity',{
			url:'/enumEntity',
			views:{
				'pageContent': {
					templateUrl:'app/components/enumEntity/enumEntity.html',
					controller:'EnumEntityController', 
					controllerAs: 'vm' 
				}
			},
			resolve: {
				projectChildrenList: function(enumEntityService) {
					enumEntityService.initProjectList().then(function(response) {
						enumEntityService.childrenList.projectList=response.data;
					});
				}, 
				enumValueChildrenList: function(enumEntityService) {
					enumEntityService.initEnumValueList().then(function(response) {
						enumEntityService.childrenList.enumValueList=response.data;
					});
				}, 
			}
		})
		.state('main.entityGroup',{
			url:'/entityGroup',
			views:{
				'pageContent': {
					templateUrl:'app/components/entityGroup/entityGroup.html',
					controller:'EntityGroupController', 
					controllerAs: 'vm' 
				}
			},
			resolve: {
				restrictionEntityGroupChildrenList: function(entityGroupService) {
					entityGroupService.initRestrictionEntityGroupList().then(function(response) {
						entityGroupService.childrenList.restrictionEntityGroupList=response.data;
					});
				}, 
				entityChildrenList: function(entityGroupService) {
					entityGroupService.initEntityList().then(function(response) {
						entityGroupService.childrenList.entityList=response.data;
					});
				}, 
				projectChildrenList: function(entityGroupService) {
					entityGroupService.initProjectList().then(function(response) {
						entityGroupService.childrenList.projectList=response.data;
					});
				}, 
			}
		})
		.state('main.tab',{
			url:'/tab',
			views:{
				'pageContent': {
					templateUrl:'app/components/tab/tab.html',
					controller:'TabController', 
					controllerAs: 'vm' 
				}
			},
			resolve: {
				entityChildrenList: function(tabService) {
					tabService.initEntityList().then(function(response) {
						tabService.childrenList.entityList=response.data;
					});
				}, 
				fieldChildrenList: function(tabService) {
					tabService.initFieldList().then(function(response) {
						tabService.childrenList.fieldList=response.data;
					});
				}, 
				enumFieldChildrenList: function(tabService) {
					tabService.initEnumFieldList().then(function(response) {
						tabService.childrenList.enumFieldList=response.data;
					});
				}, 
				relationshipChildrenList: function(tabService) {
					tabService.initRelationshipList().then(function(response) {
						tabService.childrenList.relationshipList=response.data;
					});
				}, 
			}
		})
		.state('main.project',{
			url:'/project',
			views:{
				'pageContent': {
					templateUrl:'app/components/project/project.html',
					controller:'ProjectController', 
					controllerAs: 'vm' 
				}
			},
			resolve: {
				enumEntityChildrenList: function(projectService) {
					projectService.initEnumEntityList().then(function(response) {
						projectService.childrenList.enumEntityList=response.data;
					});
				}, 
				entityGroupChildrenList: function(projectService) {
					projectService.initEntityGroupList().then(function(response) {
						projectService.childrenList.entityGroupList=response.data;
					});
				}, 
			}
		})
		.state('main.annotation',{
			url:'/annotation',
			views:{
				'pageContent': {
					templateUrl:'app/components/annotation/annotation.html',
					controller:'AnnotationController', 
					controllerAs: 'vm' 
				}
			},
			resolve: {
				annotationAttributeChildrenList: function(annotationService) {
					annotationService.initAnnotationAttributeList().then(function(response) {
						annotationService.childrenList.annotationAttributeList=response.data;
					});
				}, 
				fieldChildrenList: function(annotationService) {
					annotationService.initFieldList().then(function(response) {
						annotationService.childrenList.fieldList=response.data;
					});
				}, 
				enumFieldChildrenList: function(annotationService) {
					annotationService.initEnumFieldList().then(function(response) {
						annotationService.childrenList.enumFieldList=response.data;
					});
				}, 
				relationshipChildrenList: function(annotationService) {
					annotationService.initRelationshipList().then(function(response) {
						annotationService.childrenList.relationshipList=response.data;
					});
				}, 
			}
		})
		.state('main.enumValue',{
			url:'/enumValue',
			views:{
				'pageContent': {
					templateUrl:'app/components/enumValue/enumValue.html',
					controller:'EnumValueController', 
					controllerAs: 'vm' 
				}
			},
			resolve: {
				enumEntityChildrenList: function(enumValueService) {
					enumValueService.initEnumEntityList().then(function(response) {
						enumValueService.childrenList.enumEntityList=response.data;
					});
				}, 
			}
		})
		.state('main.annotationAttribute',{
			url:'/annotationAttribute',
			views:{
				'pageContent': {
					templateUrl:'app/components/annotationAttribute/annotationAttribute.html',
					controller:'AnnotationAttributeController', 
					controllerAs: 'vm' 
				}
			},
			resolve: {
				annotationChildrenList: function(annotationAttributeService) {
					annotationAttributeService.initAnnotationList().then(function(response) {
						annotationAttributeService.childrenList.annotationList=response.data;
					});
				}, 
			}
		})
		.state('main.field',{
			url:'/field',
			views:{
				'pageContent': {
					templateUrl:'app/components/field/field.html',
					controller:'FieldController', 
					controllerAs: 'vm' 
				}
			},
			resolve: {
				restrictionFieldChildrenList: function(fieldService) {
					fieldService.initRestrictionFieldList().then(function(response) {
						fieldService.childrenList.restrictionFieldList=response.data;
					});
				}, 
				entityChildrenList: function(fieldService) {
					fieldService.initEntityList().then(function(response) {
						fieldService.childrenList.entityList=response.data;
					});
				}, 
				tabChildrenList: function(fieldService) {
					fieldService.initTabList().then(function(response) {
						fieldService.childrenList.tabList=response.data;
					});
				}, 
				annotationChildrenList: function(fieldService) {
					fieldService.initAnnotationList().then(function(response) {
						fieldService.childrenList.annotationList=response.data;
					});
				}, 
			}
		})
		.state('main.enumField',{
			url:'/enumField',
			views:{
				'pageContent': {
					templateUrl:'app/components/enumField/enumField.html',
					controller:'EnumFieldController', 
					controllerAs: 'vm' 
				}
			},
			resolve: {
				entityChildrenList: function(enumFieldService) {
					enumFieldService.initEntityList().then(function(response) {
						enumFieldService.childrenList.entityList=response.data;
					});
				}, 
				enumEntityChildrenList: function(enumFieldService) {
					enumFieldService.initEnumEntityList().then(function(response) {
						enumFieldService.childrenList.enumEntityList=response.data;
					});
				}, 
				tabChildrenList: function(enumFieldService) {
					enumFieldService.initTabList().then(function(response) {
						enumFieldService.childrenList.tabList=response.data;
					});
				}, 
				annotationChildrenList: function(enumFieldService) {
					enumFieldService.initAnnotationList().then(function(response) {
						enumFieldService.childrenList.annotationList=response.data;
					});
				}, 
			}
		})
		.state('main.relationship',{
			url:'/relationship',
			views:{
				'pageContent': {
					templateUrl:'app/components/relationship/relationship.html',
					controller:'RelationshipController', 
					controllerAs: 'vm' 
				}
			},
			resolve: {
				entityChildrenList: function(relationshipService) {
					relationshipService.initEntityList().then(function(response) {
						relationshipService.childrenList.entityList=response.data;
					});
				}, 
				entityTargetChildrenList: function(relationshipService) {
					relationshipService.initEntityList().then(function(response) {
						relationshipService.childrenList.entityTargetList=response.data;
					});
				}, 
				tabChildrenList: function(relationshipService) {
					relationshipService.initTabList().then(function(response) {
						relationshipService.childrenList.tabList=response.data;
					});
				}, 
				annotationChildrenList: function(relationshipService) {
					relationshipService.initAnnotationList().then(function(response) {
						relationshipService.childrenList.annotationList=response.data;
					});
				}, 
			}
		})
		;$urlRouterProvider.otherwise('/app/home');
	}
})();
