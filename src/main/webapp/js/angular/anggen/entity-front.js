var entityApp=angular.module("entityFrontApp",['ngFileUpload','ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection'])
.service("securityService",function($http)
		{
	this.restrictionList;
	this.init= function() {
		var promise= $http.get("../authentication/");
		return promise; 
	};
		})
		.run(function($rootScope,entityService){

			entityService.search().then(function successCallback(response) {
				entityService.setEntityList(response.data);
			},function errorCallback(response) { 
				AlertError.init({selector: "#alertError"});
				AlertError.show("Si è verificato un errore");
				return; 
			});
		
		})
		.service("entityService", function($http)
				{
			this.entityList =		[];
			this.selectedEntity= 	{show: false 
					,relationshipList: [],tabList: [],fieldList: [],enumFieldList: [],restrictionEntityList: []};
			this.childrenList=[]; 
			this.addEntity=function (entity)
			{
				this.entityList.push(entity);
			};
			this.emptyList= function(list)
			{
				while (list.length>0)
					list.pop();
			}
			this.setEntityList= function(entityList)
			{ 
				while (this.entityList.length>0)
					this.entityList.pop();
				if (entityList!=null)
					for (i=0; i<entityList.length; i++)
						this.entityList.push(entityList[i]);
			};
			this.searchBean = 		new Object();
			this.resetSearchBean= function()
			{
				this.searchBean={};
			};
			this.setSelectedEntity= function (entity)
			{ 
				if (entity == null) {
					entity = {};
					this.selectedEntity.show = false;
				} //else
				var keyList = Object.keys(entity);
				if (keyList.length == 0)
					keyList = Object.keys(this.selectedEntity);
				for (i = 0; i < keyList.length; i++) {
					var val = keyList[i];
					if (val != undefined) {
						if (val.toLowerCase().indexOf("list") > -1
								&& (typeof entity[val] == "object" || typeof this.selectedEntity[val]=="object")) {
							if (entity[val] != null
									&& entity[val] != undefined) {
								if (this.selectedEntity[val]!=undefined)
									while (this.selectedEntity[val].length > 0)
										this.selectedEntity[val].pop();
								if (entity[val] != null)
									for (j = 0; j < entity[val].length; j++)
										this.selectedEntity[val]
								.push(entity[val][j]);
							} else 
								this.emptyList(this.selectedEntity[val]);
						} else {
							if (val.toLowerCase().indexOf("time") > -1
									&& typeof val == "string") {
								var date = new Date(entity[val]);
								this.selectedEntity[val] = new Date(entity[val]);
							} else {
								this.selectedEntity[val] = entity[val];
							}
						}
					}
				};
			};
			this.search = function() {
				this.setSelectedEntity(null);
				var promise= $http.post("../entity/search",this.searchBean);
				return promise; 
			};
			this.searchOne=function(entity) {};
			this.insert = function() {};
			this.update = function() {}
			this.del = function() {}
			this.loadFile= function(file,field){}
			this.initRelationshipList= function()
			{};
			this.initEntityGroupList= function()
			{};
			this.initTabList= function()
			{};
			this.initFieldList= function()
			{};
			this.initEnumFieldList= function()
			{};
			this.initRestrictionEntityList= function()
			{};
				})
				.controller("entityFrontController",function($scope,$http,entityService)
						{
//					null
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
						relationshipService.selectedEntity.show=false;tabService.selectedEntity.show=false;fieldService.selectedEntity.show=false;annotationService.selectedEntity.show=false;enumFieldService.selectedEntity.show=false;enumEntityService.selectedEntity.show=false;projectService.selectedEntity.show=false;entityGroupService.selectedEntity.show=false;restrictionEntityGroupService.selectedEntity.show=false;roleService.selectedEntity.show=false;restrictionEntityService.selectedEntity.show=false;restrictionFieldService.selectedEntity.show=false;userService.selectedEntity.show=false;enumValueService.selectedEntity.show=false;annotationAttributeService.selectedEntity.show=false;}
					$scope.search=function()
					{
						entityService.search().then(function successCallback(response) {
							entityService.setEntityList(response.data);
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});
					};
						});
																																																																		;