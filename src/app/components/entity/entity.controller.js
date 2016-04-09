(function() { 

	angular
	.module("serverTestApp")
	.controller("EntityController",EntityController);
	/** @ngInject */
	function EntityController($scope,$http ,$cookies,$log,$resource,entityService, SecurityService, MainService ,tabService,fieldService,annotationService,annotationAttributeService,enumFieldService,enumEntityService,projectService,entityGroupService,relationshipService,enumValueService,restrictionFieldService,roleService)
	{
		$scope.searchBean=entityService.searchBean;
		$scope.entityList=entityService.entityList;
		$scope.selectedEntity=entityService.selectedEntity;
		$scope.childrenList=entityService.childrenList; 
		$scope.reset = function()
		{
			entityService.resetSearchBean();
			$scope.searchBean=entityService.searchBean;entityService.setSelectedEntity(null);
			entityService.selectedEntity.show=false;
			entityService.setEntityList(null); 
			if (entityService.isParent()) 
			{
				tabService.selectedEntity.show=false;
				entityGroupService.selectedEntity.show=false;
				enumFieldService.selectedEntity.show=false;
				fieldService.selectedEntity.show=false;
				enumFieldService.selectedEntity.show=false;
				relationshipService.selectedEntity.show=false;
			}
		}
		$scope.addNew= function()
		{
			entityService.setSelectedEntity(null);
			entityService.setEntityList(null);
			entityService.selectedEntity.show=true;
			if (entityService.isParent()) 
			{
				tabService.selectedEntity.show=false;
				entityGroupService.selectedEntity.show=false;
				enumFieldService.selectedEntity.show=false;
				fieldService.selectedEntity.show=false;
				enumFieldService.selectedEntity.show=false;
				relationshipService.selectedEntity.show=false;
			}
			$('#entityTabs li:eq(0) a').tab('show');
		};

		$scope.search=function()
		{
			entityService.selectedEntity.show=false;
			entityService.searchBean.tabList=[];
			entityService.searchBean.tabList.push(entityService.searchBean.tab);
			delete entityService.searchBean.tab; 
			entityService.searchBean.enumFieldList=[];
			entityService.searchBean.enumFieldList.push(entityService.searchBean.enumField);
			delete entityService.searchBean.enumField; 
			entityService.searchBean.fieldList=[];
			entityService.searchBean.fieldList.push(entityService.searchBean.field);
			delete entityService.searchBean.field; 
			entityService.searchBean.enumFieldList=[];
			entityService.searchBean.enumFieldList.push(entityService.searchBean.enumField);
			delete entityService.searchBean.enumField; 
			entityService.searchBean.relationshipList=[];
			entityService.searchBean.relationshipList.push(entityService.searchBean.relationship);
			delete entityService.searchBean.relationship; 
			$log.debug($cookies.getAll());
			for (var obj in $cookies.getAll())
				$cookies.remove(obj);
			$log.debug($cookies.getAll());
			
			entityService.search().then(function successCallback(response) {
			//	$log.debug(response);
				entityService.setEntityList(response.data);
			},function errorCallback(response) { 
					AlertError.init({selector: "#alertError"});
					AlertError.show("Si è verificato un errore");
					return; 
				});
			
			
			

			/*var Search= $resource("http://127.0.0.1:8080/ServerTestApp/entity/search/");
			$log.debug("intanziato Search");
			var result= Search.save({},function(data){
                $log.debug(data);
            });*/

		};

		$scope.checkUsername=function()
		{
			var Username= $resource("http://127.0.0.1:8080/ServerTestApp/authentication/username/");
			$log.debug("intanziato Username");
			var result= Username.save({},function(data){
				$log.debug(data);
			});
		}


		$scope.insert=function()
		{
			if (!$scope.entityDetailForm.$valid) return; 
			if (entityService.isParent()) 
			{
				entityService.insert().then(function successCallback(response) { 
					$scope.search();
				},function errorCallback(response) { 
					AlertError.init({selector: "#alertError"});
					AlertError.show("Si è verificato un errore");
					return; 
				});
			}
			else 
			{
				entityService.selectedEntity.show=false;
				entityService.insert().then(function successCallBack(response) { 
				},function errorCallback(response) { 
					AlertError.init({selector: "#alertError"});
					AlertError.show("Si è verificato un errore");
					return; 
				});
			}
		};
		$scope.update=function()
		{
			if (!$scope.entityDetailForm.$valid) return; 
			if (entityService.isParent()) 
			{
				tabService.selectedEntity.show=false;
				entityGroupService.selectedEntity.show=false;
				enumFieldService.selectedEntity.show=false;
				fieldService.selectedEntity.show=false;
				enumFieldService.selectedEntity.show=false;
				relationshipService.selectedEntity.show=false;
				entityService.update().then(function successCallback(response) { 
					$scope.search();
				},function errorCallback(response) { 
					AlertError.init({selector: "#alertError"});
					AlertError.show("Si è verificato un errore");
					return; 
				});
			}
			else 
			{
				entityService.selectedEntity.show=false;

				entityService.update().then(function successCallback(response){
					entityService.setSelectedEntity(response.data);
					updateParentEntities();
				},function errorCallback(response) { 
					AlertError.init({selector: "#alertError"});
					AlertError.show("Si è verificato un errore");
					return; 
				});
			}
		};
		$scope.remove= function()
		{
			entityService.selectedEntity.show=false;
			entityService.setSelectedEntity(null);
			$scope.updateParent();
		};
		$scope.del=function()
		{
			if (!entityService.isParent()) 
				$scope.updateParent();
			entityService.del().then(function successCallback(response) { 
				if (entityService.isParent()) 
				{
					$scope.search();
				} else { 
					entityService.setSelectedEntity(null);
				}
			},function errorCallback(response) { 
				AlertError.init({selector: "#alertError"});
				AlertError.show("Si è verificato un errore");
				return; 
			});
		};
		$scope.refreshTableDetail= function() 
		{
			if ($scope.tabGridApi!=undefined && $scope.tabGridApi!=null)
				$scope.tabGridApi.core.handleWindowResize(); 
			if ($scope.entityGroupGridApi!=undefined && $scope.entityGroupGridApi!=null)
				$scope.entityGroupGridApi.core.handleWindowResize(); 
			if ($scope.enumFieldGridApi!=undefined && $scope.enumFieldGridApi!=null)
				$scope.enumFieldGridApi.core.handleWindowResize(); 
			if ($scope.fieldGridApi!=undefined && $scope.fieldGridApi!=null)
				$scope.fieldGridApi.core.handleWindowResize(); 
			if ($scope.enumFieldGridApi!=undefined && $scope.enumFieldGridApi!=null)
				$scope.enumFieldGridApi.core.handleWindowResize(); 
			if ($scope.relationshipGridApi!=undefined && $scope.relationshipGridApi!=null)
				$scope.relationshipGridApi.core.handleWindowResize(); 
		};
		$scope.loadFile = function(file,field)
		{
			entityService.loadFile(file,field).then(function successCallback(response) {
				entityService.setSelectedEntity(response.data);
			},function errorCallback(response) { 
				AlertError.init({selector: "#alertError"});
				AlertError.show("Si è verificato un errore");
				return; 
				return; 
			});
		}
		$scope.trueFalseValues=[true,false];
		$scope.showTabDetail= function(index)
		{
			if (index!=null)
			{
				tabService.searchOne(entityService.selectedEntity.tabList[index]).then(
						function successCallback(response) {
							console.log("response-ok");
							console.log(response);
							if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
								tabService.initEntityList().then(function successCallback(response) {
									tabService.childrenList.entityList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							if (SecurityService.restrictionList.field==undefined || SecurityService.restrictionList.field.canSearch)
								tabService.initFieldList().then(function successCallback(response) {
									tabService.childrenList.fieldList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							if (SecurityService.restrictionList.enumField==undefined || SecurityService.restrictionList.enumField.canSearch)
								tabService.initEnumFieldList().then(function successCallback(response) {
									tabService.childrenList.enumFieldList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							if (SecurityService.restrictionList.relationship==undefined || SecurityService.restrictionList.relationship.canSearch)
								tabService.initRelationshipList().then(function successCallback(response) {
									tabService.childrenList.relationshipList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							tabService.setSelectedEntity(response.data[0]);
							tabService.selectedEntity.show=true;
						}, function errorCallback(response) {
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						}	
				);
			}
			else 
			{
				if (entityService.selectedEntity.tab==null || entityService.selectedEntity.tab==undefined)
				{
					if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
						tabService.initEntityList().then(function successCallback(response) {
							tabService.childrenList.entityList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.field==undefined || SecurityService.restrictionList.field.canSearch)
						tabService.initFieldList().then(function successCallback(response) {
							tabService.childrenList.fieldList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.enumField==undefined || SecurityService.restrictionList.enumField.canSearch)
						tabService.initEnumFieldList().then(function successCallback(response) {
							tabService.childrenList.enumFieldList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.relationship==undefined || SecurityService.restrictionList.relationship.canSearch)
						tabService.initRelationshipList().then(function successCallback(response) {
							tabService.childrenList.relationshipList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					tabService.setSelectedEntity(null); 
					tabService.selectedEntity.show=true; 
				}
				else
					tabService.searchOne(entityService.selectedEntity.tab).then(
							function successCallback(response) {
								if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
									tabService.initEntityList().then(function successCallback(response) {
										tabService.childrenList.entityList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								if (SecurityService.restrictionList.field==undefined || SecurityService.restrictionList.field.canSearch)
									tabService.initFieldList().then(function successCallback(response) {
										tabService.childrenList.fieldList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								if (SecurityService.restrictionList.enumField==undefined || SecurityService.restrictionList.enumField.canSearch)
									tabService.initEnumFieldList().then(function successCallback(response) {
										tabService.childrenList.enumFieldList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								if (SecurityService.restrictionList.relationship==undefined || SecurityService.restrictionList.relationship.canSearch)
									tabService.initRelationshipList().then(function successCallback(response) {
										tabService.childrenList.relationshipList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								tabService.setSelectedEntity(response.data[0]);
								tabService.selectedEntity.show=true;
							}, function errorCallback(response) {
								AlertError.init({selector: "#alertError"});
								AlertError.show("Si è verificato un errore");
								return; 
							}	
					);
			}
			$('#tabTabs li:eq(0) a').tab('show');
		};
		$scope.showEntityGroupDetail= function(index)
		{
			if (index!=null)
			{
				entityGroupService.searchOne(entityService.selectedEntity.entityGroupList[index]).then(
						function successCallback(response) {
							console.log("response-ok");
							console.log(response);
							if (SecurityService.restrictionList.relationship==undefined || SecurityService.restrictionList.relationship.canSearch)
								entityGroupService.initRelationshipList().then(function successCallback(response) {
									entityGroupService.childrenList.relationshipList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							if (SecurityService.restrictionList.project==undefined || SecurityService.restrictionList.project.canSearch)
								entityGroupService.initProjectList().then(function successCallback(response) {
									entityGroupService.childrenList.projectList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
								entityGroupService.initEntityList().then(function successCallback(response) {
									entityGroupService.childrenList.entityList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							entityGroupService.setSelectedEntity(response.data[0]);
							entityGroupService.selectedEntity.show=true;
						}, function errorCallback(response) {
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						}	
				);
			}
			else 
			{
				if (entityService.selectedEntity.entityGroup==null || entityService.selectedEntity.entityGroup==undefined)
				{
					if (SecurityService.restrictionList.relationship==undefined || SecurityService.restrictionList.relationship.canSearch)
						entityGroupService.initRelationshipList().then(function successCallback(response) {
							entityGroupService.childrenList.relationshipList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.project==undefined || SecurityService.restrictionList.project.canSearch)
						entityGroupService.initProjectList().then(function successCallback(response) {
							entityGroupService.childrenList.projectList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
						entityGroupService.initEntityList().then(function successCallback(response) {
							entityGroupService.childrenList.entityList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					entityGroupService.setSelectedEntity(null); 
					entityGroupService.selectedEntity.show=true; 
				}
				else
					entityGroupService.searchOne(entityService.selectedEntity.entityGroup).then(
							function successCallback(response) {
								if (SecurityService.restrictionList.relationship==undefined || SecurityService.restrictionList.relationship.canSearch)
									entityGroupService.initRelationshipList().then(function successCallback(response) {
										entityGroupService.childrenList.relationshipList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								if (SecurityService.restrictionList.project==undefined || SecurityService.restrictionList.project.canSearch)
									entityGroupService.initProjectList().then(function successCallback(response) {
										entityGroupService.childrenList.projectList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
									entityGroupService.initEntityList().then(function successCallback(response) {
										entityGroupService.childrenList.entityList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								entityGroupService.setSelectedEntity(response.data[0]);
								entityGroupService.selectedEntity.show=true;
							}, function errorCallback(response) {
								AlertError.init({selector: "#alertError"});
								AlertError.show("Si è verificato un errore");
								return; 
							}	
					);
			}
			$('#entityGroupTabs li:eq(0) a').tab('show');
		};
		$scope.showEnumFieldDetail= function(index)
		{
			if (index!=null)
			{
				enumFieldService.searchOne(entityService.selectedEntity.enumFieldList[index]).then(
						function successCallback(response) {
							console.log("response-ok");
							console.log(response);
							if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
								enumFieldService.initAnnotationList().then(function successCallback(response) {
									enumFieldService.childrenList.annotationList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
								enumFieldService.initTabList().then(function successCallback(response) {
									enumFieldService.childrenList.tabList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							if (SecurityService.restrictionList.enumEntity==undefined || SecurityService.restrictionList.enumEntity.canSearch)
								enumFieldService.initEnumEntityList().then(function successCallback(response) {
									enumFieldService.childrenList.enumEntityList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
								enumFieldService.initEntityList().then(function successCallback(response) {
									enumFieldService.childrenList.entityList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							enumFieldService.setSelectedEntity(response.data[0]);
							enumFieldService.selectedEntity.show=true;
						}, function errorCallback(response) {
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						}	
				);
			}
			else 
			{
				if (entityService.selectedEntity.enumField==null || entityService.selectedEntity.enumField==undefined)
				{
					if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
						enumFieldService.initAnnotationList().then(function successCallback(response) {
							enumFieldService.childrenList.annotationList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
						enumFieldService.initTabList().then(function successCallback(response) {
							enumFieldService.childrenList.tabList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.enumEntity==undefined || SecurityService.restrictionList.enumEntity.canSearch)
						enumFieldService.initEnumEntityList().then(function successCallback(response) {
							enumFieldService.childrenList.enumEntityList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
						enumFieldService.initEntityList().then(function successCallback(response) {
							enumFieldService.childrenList.entityList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					enumFieldService.setSelectedEntity(null); 
					enumFieldService.selectedEntity.show=true; 
				}
				else
					enumFieldService.searchOne(entityService.selectedEntity.enumField).then(
							function successCallback(response) {
								if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
									enumFieldService.initAnnotationList().then(function successCallback(response) {
										enumFieldService.childrenList.annotationList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
									enumFieldService.initTabList().then(function successCallback(response) {
										enumFieldService.childrenList.tabList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								if (SecurityService.restrictionList.enumEntity==undefined || SecurityService.restrictionList.enumEntity.canSearch)
									enumFieldService.initEnumEntityList().then(function successCallback(response) {
										enumFieldService.childrenList.enumEntityList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
									enumFieldService.initEntityList().then(function successCallback(response) {
										enumFieldService.childrenList.entityList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								enumFieldService.setSelectedEntity(response.data[0]);
								enumFieldService.selectedEntity.show=true;
							}, function errorCallback(response) {
								AlertError.init({selector: "#alertError"});
								AlertError.show("Si è verificato un errore");
								return; 
							}	
					);
			}
			$('#enumFieldTabs li:eq(0) a').tab('show');
		};
		$scope.showFieldDetail= function(index)
		{
			if (index!=null)
			{
				fieldService.searchOne(entityService.selectedEntity.fieldList[index]).then(
						function successCallback(response) {
							console.log("response-ok");
							console.log(response);
							if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
								fieldService.initAnnotationList().then(function successCallback(response) {
									fieldService.childrenList.annotationList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							if (SecurityService.restrictionList.restrictionField==undefined || SecurityService.restrictionList.restrictionField.canSearch)
								fieldService.initRestrictionFieldList().then(function successCallback(response) {
									fieldService.childrenList.restrictionFieldList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
								fieldService.initTabList().then(function successCallback(response) {
									fieldService.childrenList.tabList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
								fieldService.initEntityList().then(function successCallback(response) {
									fieldService.childrenList.entityList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG","FILE",];
							fieldService.setSelectedEntity(response.data[0]);
							fieldService.selectedEntity.show=true;
						}, function errorCallback(response) {
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						}	
				);
			}
			else 
			{
				if (entityService.selectedEntity.field==null || entityService.selectedEntity.field==undefined)
				{
					if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
						fieldService.initAnnotationList().then(function successCallback(response) {
							fieldService.childrenList.annotationList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.restrictionField==undefined || SecurityService.restrictionList.restrictionField.canSearch)
						fieldService.initRestrictionFieldList().then(function successCallback(response) {
							fieldService.childrenList.restrictionFieldList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
						fieldService.initTabList().then(function successCallback(response) {
							fieldService.childrenList.tabList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
						fieldService.initEntityList().then(function successCallback(response) {
							fieldService.childrenList.entityList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG","FILE",];
					fieldService.setSelectedEntity(null); 
					fieldService.selectedEntity.show=true; 
				}
				else
					fieldService.searchOne(entityService.selectedEntity.field).then(
							function successCallback(response) {
								if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
									fieldService.initAnnotationList().then(function successCallback(response) {
										fieldService.childrenList.annotationList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								if (SecurityService.restrictionList.restrictionField==undefined || SecurityService.restrictionList.restrictionField.canSearch)
									fieldService.initRestrictionFieldList().then(function successCallback(response) {
										fieldService.childrenList.restrictionFieldList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
									fieldService.initTabList().then(function successCallback(response) {
										fieldService.childrenList.tabList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
									fieldService.initEntityList().then(function successCallback(response) {
										fieldService.childrenList.entityList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG","FILE",];
								fieldService.setSelectedEntity(response.data[0]);
								fieldService.selectedEntity.show=true;
							}, function errorCallback(response) {
								AlertError.init({selector: "#alertError"});
								AlertError.show("Si è verificato un errore");
								return; 
							}	
					);
			}
			$('#fieldTabs li:eq(0) a').tab('show');
		};
		$scope.showEnumFieldDetail= function(index)
		{
			if (index!=null)
			{
				enumFieldService.searchOne(entityService.selectedEntity.enumFieldList[index]).then(
						function successCallback(response) {
							console.log("response-ok");
							console.log(response);
							if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
								enumFieldService.initAnnotationList().then(function successCallback(response) {
									enumFieldService.childrenList.annotationList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
								enumFieldService.initTabList().then(function successCallback(response) {
									enumFieldService.childrenList.tabList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							if (SecurityService.restrictionList.enumEntity==undefined || SecurityService.restrictionList.enumEntity.canSearch)
								enumFieldService.initEnumEntityList().then(function successCallback(response) {
									enumFieldService.childrenList.enumEntityList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
								enumFieldService.initEntityList().then(function successCallback(response) {
									enumFieldService.childrenList.entityList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							enumFieldService.setSelectedEntity(response.data[0]);
							enumFieldService.selectedEntity.show=true;
						}, function errorCallback(response) {
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						}	
				);
			}
			else 
			{
				if (entityService.selectedEntity.enumField==null || entityService.selectedEntity.enumField==undefined)
				{
					if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
						enumFieldService.initAnnotationList().then(function successCallback(response) {
							enumFieldService.childrenList.annotationList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
						enumFieldService.initTabList().then(function successCallback(response) {
							enumFieldService.childrenList.tabList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.enumEntity==undefined || SecurityService.restrictionList.enumEntity.canSearch)
						enumFieldService.initEnumEntityList().then(function successCallback(response) {
							enumFieldService.childrenList.enumEntityList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
						enumFieldService.initEntityList().then(function successCallback(response) {
							enumFieldService.childrenList.entityList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					enumFieldService.setSelectedEntity(null); 
					enumFieldService.selectedEntity.show=true; 
				}
				else
					enumFieldService.searchOne(entityService.selectedEntity.enumField).then(
							function successCallback(response) {
								if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
									enumFieldService.initAnnotationList().then(function successCallback(response) {
										enumFieldService.childrenList.annotationList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
									enumFieldService.initTabList().then(function successCallback(response) {
										enumFieldService.childrenList.tabList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								if (SecurityService.restrictionList.enumEntity==undefined || SecurityService.restrictionList.enumEntity.canSearch)
									enumFieldService.initEnumEntityList().then(function successCallback(response) {
										enumFieldService.childrenList.enumEntityList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
									enumFieldService.initEntityList().then(function successCallback(response) {
										enumFieldService.childrenList.entityList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								enumFieldService.setSelectedEntity(response.data[0]);
								enumFieldService.selectedEntity.show=true;
							}, function errorCallback(response) {
								AlertError.init({selector: "#alertError"});
								AlertError.show("Si è verificato un errore");
								return; 
							}	
					);
			}
			$('#enumFieldTabs li:eq(0) a').tab('show');
		};
		$scope.showRelationshipDetail= function(index)
		{
			if (index!=null)
			{
				relationshipService.searchOne(entityService.selectedEntity.relationshipList[index]).then(
						function successCallback(response) {
							console.log("response-ok");
							console.log(response);
							if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
								relationshipService.initEntityList().then(function successCallback(response) {
									relationshipService.childrenList.entityList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
								relationshipService.initEntityList().then(function successCallback(response) {
									relationshipService.childrenList.entityList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
								relationshipService.initTabList().then(function successCallback(response) {
									relationshipService.childrenList.tabList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
								relationshipService.initAnnotationList().then(function successCallback(response) {
									relationshipService.childrenList.annotationList=response.data;
								},function errorCallback(response) { 
									AlertError.init({selector: "#alertError"});
									AlertError.show("Si è verificato un errore");
									return; 
								});
							relationshipService.childrenList.relationshipTypeList=["ONE_TO_ONE","ONE_TO_MANY","MANY_TO_ONE","MANY_TO_MANY","MANY_TO_MANY_BACK",];
							relationshipService.setSelectedEntity(response.data[0]);
							relationshipService.selectedEntity.show=true;
						}, function errorCallback(response) {
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						}	
				);
			}
			else 
			{
				if (entityService.selectedEntity.relationship==null || entityService.selectedEntity.relationship==undefined)
				{
					if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
						relationshipService.initEntityList().then(function successCallback(response) {
							relationshipService.childrenList.entityList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
						relationshipService.initEntityList().then(function successCallback(response) {
							relationshipService.childrenList.entityList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
						relationshipService.initTabList().then(function successCallback(response) {
							relationshipService.childrenList.tabList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
						relationshipService.initAnnotationList().then(function successCallback(response) {
							relationshipService.childrenList.annotationList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					relationshipService.childrenList.relationshipTypeList=["ONE_TO_ONE","ONE_TO_MANY","MANY_TO_ONE","MANY_TO_MANY","MANY_TO_MANY_BACK",];
					relationshipService.setSelectedEntity(null); 
					relationshipService.selectedEntity.show=true; 
				}
				else
					relationshipService.searchOne(entityService.selectedEntity.relationship).then(
							function successCallback(response) {
								if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
									relationshipService.initEntityList().then(function successCallback(response) {
										relationshipService.childrenList.entityList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
									relationshipService.initEntityList().then(function successCallback(response) {
										relationshipService.childrenList.entityList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
									relationshipService.initTabList().then(function successCallback(response) {
										relationshipService.childrenList.tabList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
									relationshipService.initAnnotationList().then(function successCallback(response) {
										relationshipService.childrenList.annotationList=response.data;
									},function errorCallback(response) { 
										AlertError.init({selector: "#alertError"});
										AlertError.show("Si è verificato un errore");
										return; 
									});
								relationshipService.childrenList.relationshipTypeList=["ONE_TO_ONE","ONE_TO_MANY","MANY_TO_ONE","MANY_TO_MANY","MANY_TO_MANY_BACK",];
								relationshipService.setSelectedEntity(response.data[0]);
								relationshipService.selectedEntity.show=true;
							}, function errorCallback(response) {
								AlertError.init({selector: "#alertError"});
								AlertError.show("Si è verificato un errore");
								return; 
							}	
					);
			}
			$('#relationshipTabs li:eq(0) a').tab('show');
		};
		$scope.downloadEntityList=function()
		{
			var mystyle = {
					headers:true, 
					column: {style:{Font:{Bold:"1"}}}
			};
			alasql('SELECT * INTO XLSXML("entity.xls",?) FROM ?',[mystyle,$scope.entityList]);
		};
		$scope.saveLinkedTab= function() {
			entityService.selectedEntity.tabList.push(entityService.selectedEntity.tab);
		}
		$scope.downloadTabList=function()
		{
			var mystyle = {
					headers:true, 
					column: {style:{Font:{Bold:"1"}}}
			};
			alasql('SELECT * INTO XLSXML("tab.xls",?) FROM ?',[mystyle,$scope.selectedEntity.tabList]);
		};
		$scope.downloadEntityGroupList=function()
		{
			var mystyle = {
					headers:true, 
					column: {style:{Font:{Bold:"1"}}}
			};
			alasql('SELECT * INTO XLSXML("entityGroup.xls",?) FROM ?',[mystyle,$scope.selectedEntity.entityGroupList]);
		};
		$scope.saveLinkedEnumField= function() {
			entityService.selectedEntity.enumFieldList.push(entityService.selectedEntity.enumField);
		}
		$scope.downloadEnumFieldList=function()
		{
			var mystyle = {
					headers:true, 
					column: {style:{Font:{Bold:"1"}}}
			};
			alasql('SELECT * INTO XLSXML("enumField.xls",?) FROM ?',[mystyle,$scope.selectedEntity.enumFieldList]);
		};
		$scope.saveLinkedField= function() {
			entityService.selectedEntity.fieldList.push(entityService.selectedEntity.field);
		}
		$scope.downloadFieldList=function()
		{
			var mystyle = {
					headers:true, 
					column: {style:{Font:{Bold:"1"}}}
			};
			alasql('SELECT * INTO XLSXML("field.xls",?) FROM ?',[mystyle,$scope.selectedEntity.fieldList]);
		};
		$scope.saveLinkedEnumField= function() {
			entityService.selectedEntity.enumFieldList.push(entityService.selectedEntity.enumField);
		}
		$scope.downloadEnumFieldList=function()
		{
			var mystyle = {
					headers:true, 
					column: {style:{Font:{Bold:"1"}}}
			};
			alasql('SELECT * INTO XLSXML("enumField.xls",?) FROM ?',[mystyle,$scope.selectedEntity.enumFieldList]);
		};
		$scope.saveLinkedRelationship= function() {
			entityService.selectedEntity.relationshipList.push(entityService.selectedEntity.relationship);
		}
		$scope.downloadRelationshipList=function()
		{
			var mystyle = {
					headers:true, 
					column: {style:{Font:{Bold:"1"}}}
			};
			alasql('SELECT * INTO XLSXML("relationship.xls",?) FROM ?',[mystyle,$scope.selectedEntity.relationshipList]);
		};
		$scope.entityGridOptions={};
		cloneObject(entityService.gridOptions,$scope.entityGridOptions);
		$scope.entityGridOptions.data=entityService.entityList;
		$scope.entityGridOptions.onRegisterApi = function(gridApi){
			$scope.entityGridApi = gridApi;
			gridApi.selection.on.rowSelectionChanged($scope,function(row){
				if (row.isSelected)
				{
					if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
						entityService.initTabList().then(function successCallback(response) {
							entityService.childrenList.tabList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.entityGroup==undefined || SecurityService.restrictionList.entityGroup.canSearch)
						entityService.initEntityGroupList().then(function successCallback(response) {
							entityService.childrenList.entityGroupList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.enumField==undefined || SecurityService.restrictionList.enumField.canSearch)
						entityService.initEnumFieldList().then(function successCallback(response) {
							entityService.childrenList.enumFieldList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.field==undefined || SecurityService.restrictionList.field.canSearch)
						entityService.initFieldList().then(function successCallback(response) {
							entityService.childrenList.fieldList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.enumField==undefined || SecurityService.restrictionList.enumField.canSearch)
						entityService.initEnumFieldList().then(function successCallback(response) {
							entityService.childrenList.enumFieldList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.relationship==undefined || SecurityService.restrictionList.relationship.canSearch)
						entityService.initRelationshipList().then(function successCallback(response) {
							entityService.childrenList.relationshipList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					entityService.childrenList.securityTypeList=["BLOCK_WITH_RESTRICTION","ACCESS_WITH_PERMISSION",];
					entityService.searchOne(row.entity).then(function(response) { 
						console.log(response.data);
						entityService.setSelectedEntity(response.data[0]);
					});
					$('#entityTabs li:eq(0) a').tab('show');
				}
				else 
					entityService.setSelectedEntity(null);
				entityService.selectedEntity.show = row.isSelected;
			});
		};
		$scope.tabGridOptions={};
		cloneObject(tabService.gridOptions,$scope.tabGridOptions);
		$scope.tabGridOptions.data=$scope.selectedEntity.tabList;
		$scope.tabGridOptions.onRegisterApi = function(gridApi){
			$scope.tabGridApi = gridApi;
			gridApi.selection.on.rowSelectionChanged($scope,function(row){
				if (row.isSelected)
				{
					if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
						tabService.initEntityList().then(function successCallback(response) {
							tabService.childrenList.entityList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.field==undefined || SecurityService.restrictionList.field.canSearch)
						tabService.initFieldList().then(function successCallback(response) {
							tabService.childrenList.fieldList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.enumField==undefined || SecurityService.restrictionList.enumField.canSearch)
						tabService.initEnumFieldList().then(function successCallback(response) {
							tabService.childrenList.enumFieldList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.relationship==undefined || SecurityService.restrictionList.relationship.canSearch)
						tabService.initRelationshipList().then(function successCallback(response) {
							tabService.childrenList.relationshipList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					tabService.searchOne(row.entity).then(function(response) { 
						console.log(response.data);
						tabService.setSelectedEntity(response.data[0]);
					});
					$('#tabTabs li:eq(0) a').tab('show');
				}
				else 
					tabService.setSelectedEntity(null);
				tabService.selectedEntity.show = row.isSelected;
			});
		};
		$scope.entityGroupGridOptions={};
		cloneObject(entityGroupService.gridOptions,$scope.entityGroupGridOptions);
		$scope.entityGroupGridOptions.data=$scope.selectedEntity.entityGroupList;
		$scope.entityGroupGridOptions.onRegisterApi = function(gridApi){
			$scope.entityGroupGridApi = gridApi;
			gridApi.selection.on.rowSelectionChanged($scope,function(row){
				if (row.isSelected)
				{
					if (SecurityService.restrictionList.relationship==undefined || SecurityService.restrictionList.relationship.canSearch)
						entityGroupService.initRelationshipList().then(function successCallback(response) {
							entityGroupService.childrenList.relationshipList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.project==undefined || SecurityService.restrictionList.project.canSearch)
						entityGroupService.initProjectList().then(function successCallback(response) {
							entityGroupService.childrenList.projectList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
						entityGroupService.initEntityList().then(function successCallback(response) {
							entityGroupService.childrenList.entityList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					entityGroupService.searchOne(row.entity).then(function(response) { 
						console.log(response.data);
						entityGroupService.setSelectedEntity(response.data[0]);
					});
					$('#entityGroupTabs li:eq(0) a').tab('show');
				}
				else 
					entityGroupService.setSelectedEntity(null);
				entityGroupService.selectedEntity.show = row.isSelected;
			});
		};
		$scope.enumFieldGridOptions={};
		cloneObject(enumFieldService.gridOptions,$scope.enumFieldGridOptions);
		$scope.enumFieldGridOptions.data=$scope.selectedEntity.enumFieldList;
		$scope.enumFieldGridOptions.onRegisterApi = function(gridApi){
			$scope.enumFieldGridApi = gridApi;
			gridApi.selection.on.rowSelectionChanged($scope,function(row){
				if (row.isSelected)
				{
					if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
						enumFieldService.initAnnotationList().then(function successCallback(response) {
							enumFieldService.childrenList.annotationList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
						enumFieldService.initTabList().then(function successCallback(response) {
							enumFieldService.childrenList.tabList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.enumEntity==undefined || SecurityService.restrictionList.enumEntity.canSearch)
						enumFieldService.initEnumEntityList().then(function successCallback(response) {
							enumFieldService.childrenList.enumEntityList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
						enumFieldService.initEntityList().then(function successCallback(response) {
							enumFieldService.childrenList.entityList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					enumFieldService.searchOne(row.entity).then(function(response) { 
						console.log(response.data);
						enumFieldService.setSelectedEntity(response.data[0]);
					});
					$('#enumFieldTabs li:eq(0) a').tab('show');
				}
				else 
					enumFieldService.setSelectedEntity(null);
				enumFieldService.selectedEntity.show = row.isSelected;
			});
		};
		$scope.fieldGridOptions={};
		cloneObject(fieldService.gridOptions,$scope.fieldGridOptions);
		$scope.fieldGridOptions.data=$scope.selectedEntity.fieldList;
		$scope.fieldGridOptions.onRegisterApi = function(gridApi){
			$scope.fieldGridApi = gridApi;
			gridApi.selection.on.rowSelectionChanged($scope,function(row){
				if (row.isSelected)
				{
					if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
						fieldService.initAnnotationList().then(function successCallback(response) {
							fieldService.childrenList.annotationList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.restrictionField==undefined || SecurityService.restrictionList.restrictionField.canSearch)
						fieldService.initRestrictionFieldList().then(function successCallback(response) {
							fieldService.childrenList.restrictionFieldList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
						fieldService.initTabList().then(function successCallback(response) {
							fieldService.childrenList.tabList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
						fieldService.initEntityList().then(function successCallback(response) {
							fieldService.childrenList.entityList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					fieldService.childrenList.fieldTypeList=["STRING","INTEGER","DATE","DOUBLE","TIME","BOOLEAN","LONG","FILE",];
					fieldService.searchOne(row.entity).then(function(response) { 
						console.log(response.data);
						fieldService.setSelectedEntity(response.data[0]);
					});
					$('#fieldTabs li:eq(0) a').tab('show');
				}
				else 
					fieldService.setSelectedEntity(null);
				fieldService.selectedEntity.show = row.isSelected;
			});
		};
		$scope.enumFieldGridOptions={};
		cloneObject(enumFieldService.gridOptions,$scope.enumFieldGridOptions);
		$scope.enumFieldGridOptions.data=$scope.selectedEntity.enumFieldList;
		$scope.enumFieldGridOptions.onRegisterApi = function(gridApi){
			$scope.enumFieldGridApi = gridApi;
			gridApi.selection.on.rowSelectionChanged($scope,function(row){
				if (row.isSelected)
				{
					if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
						enumFieldService.initAnnotationList().then(function successCallback(response) {
							enumFieldService.childrenList.annotationList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
						enumFieldService.initTabList().then(function successCallback(response) {
							enumFieldService.childrenList.tabList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.enumEntity==undefined || SecurityService.restrictionList.enumEntity.canSearch)
						enumFieldService.initEnumEntityList().then(function successCallback(response) {
							enumFieldService.childrenList.enumEntityList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
						enumFieldService.initEntityList().then(function successCallback(response) {
							enumFieldService.childrenList.entityList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					enumFieldService.searchOne(row.entity).then(function(response) { 
						console.log(response.data);
						enumFieldService.setSelectedEntity(response.data[0]);
					});
					$('#enumFieldTabs li:eq(0) a').tab('show');
				}
				else 
					enumFieldService.setSelectedEntity(null);
				enumFieldService.selectedEntity.show = row.isSelected;
			});
		};
		$scope.relationshipGridOptions={};
		cloneObject(relationshipService.gridOptions,$scope.relationshipGridOptions);
		$scope.relationshipGridOptions.data=$scope.selectedEntity.relationshipList;
		$scope.relationshipGridOptions.onRegisterApi = function(gridApi){
			$scope.relationshipGridApi = gridApi;
			gridApi.selection.on.rowSelectionChanged($scope,function(row){
				if (row.isSelected)
				{
					if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
						relationshipService.initEntityList().then(function successCallback(response) {
							relationshipService.childrenList.entityList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.entity==undefined || SecurityService.restrictionList.entity.canSearch)
						relationshipService.initEntityList().then(function successCallback(response) {
							relationshipService.childrenList.entityList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.tab==undefined || SecurityService.restrictionList.tab.canSearch)
						relationshipService.initTabList().then(function successCallback(response) {
							relationshipService.childrenList.tabList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					if (SecurityService.restrictionList.annotation==undefined || SecurityService.restrictionList.annotation.canSearch)
						relationshipService.initAnnotationList().then(function successCallback(response) {
							relationshipService.childrenList.annotationList=response.data;
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					relationshipService.childrenList.relationshipTypeList=["ONE_TO_ONE","ONE_TO_MANY","MANY_TO_ONE","MANY_TO_MANY","MANY_TO_MANY_BACK",];
					relationshipService.searchOne(row.entity).then(function(response) { 
						console.log(response.data);
						relationshipService.setSelectedEntity(response.data[0]);
					});
					$('#relationshipTabs li:eq(0) a').tab('show');
				}
				else 
					relationshipService.setSelectedEntity(null);
				relationshipService.selectedEntity.show = row.isSelected;
			});
		};
		function updateParentEntities() { 
			tabService.initEntityList().then(function(response) {
				tabService.childrenList.entityList=response.data;
			});

			if (tabService.selectedEntity.tabId!=undefined) tabService.searchOne(tabService.selectedEntity).then(
					function successCallback(response) {
						console.log("response-ok");
						console.log(response);
						tabService.setSelectedEntity(response.data[0]);
					}, function errorCallback(response) {
						AlertError.init({selector: "#alertError"});
						AlertError.show("Si è verificato un errore");
						return; 
					}	
			);
			entityGroupService.initEntityList().then(function(response) {
				entityGroupService.childrenList.entityList=response.data;
			});

			if (entityGroupService.selectedEntity.entityGroupId!=undefined) entityGroupService.searchOne(entityGroupService.selectedEntity).then(
					function successCallback(response) {
						console.log("response-ok");
						console.log(response);
						entityGroupService.setSelectedEntity(response.data[0]);
					}, function errorCallback(response) {
						AlertError.init({selector: "#alertError"});
						AlertError.show("Si è verificato un errore");
						return; 
					}	
			);
			enumFieldService.initEntityList().then(function(response) {
				enumFieldService.childrenList.entityList=response.data;
			});

			if (enumFieldService.selectedEntity.enumFieldId!=undefined) enumFieldService.searchOne(enumFieldService.selectedEntity).then(
					function successCallback(response) {
						console.log("response-ok");
						console.log(response);
						enumFieldService.setSelectedEntity(response.data[0]);
					}, function errorCallback(response) {
						AlertError.init({selector: "#alertError"});
						AlertError.show("Si è verificato un errore");
						return; 
					}	
			);
			fieldService.initEntityList().then(function(response) {
				fieldService.childrenList.entityList=response.data;
			});

			if (fieldService.selectedEntity.fieldId!=undefined) fieldService.searchOne(fieldService.selectedEntity).then(
					function successCallback(response) {
						console.log("response-ok");
						console.log(response);
						fieldService.setSelectedEntity(response.data[0]);
					}, function errorCallback(response) {
						AlertError.init({selector: "#alertError"});
						AlertError.show("Si è verificato un errore");
						return; 
					}	
			);
			relationshipService.initEntityList().then(function(response) {
				relationshipService.childrenList.entityList=response.data;
			});

			if (relationshipService.selectedEntity.relationshipId!=undefined) relationshipService.searchOne(relationshipService.selectedEntity).then(
					function successCallback(response) {
						console.log("response-ok");
						console.log(response);
						relationshipService.setSelectedEntity(response.data[0]);
					}, function errorCallback(response) {
						AlertError.init({selector: "#alertError"});
						AlertError.show("Si è verificato un errore");
						return; 
					}	
			);
			relationshipService.initEntityList().then(function(response) {
				relationshipService.childrenList.entityList=response.data;
			});

			if (relationshipService.selectedEntity.relationshipId!=undefined) relationshipService.searchOne(relationshipService.selectedEntity).then(
					function successCallback(response) {
						console.log("response-ok");
						console.log(response);
						relationshipService.setSelectedEntity(response.data[0]);
					}, function errorCallback(response) {
						AlertError.init({selector: "#alertError"});
						AlertError.show("Si è verificato un errore");
						return; 
					}	
			);
		}
		$scope.closeEntityDetail = function(){ 
			entityService.setSelectedEntity(null);
			entityService.selectedEntity.show=false;
		}}
})();
