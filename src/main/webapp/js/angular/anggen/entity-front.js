var entityApp=angular.module("entityFrontApp",['ngFileUpload','ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection'])
		.run(function($rootScope,entityService){

			
			
			entityService.searchPage().then(function successCallback(response) {
				entityService.setEntityList(response.data.content);
				entityService.setSelectedEntity(response.data);
			},function errorCallback(response) { 
				AlertError.init({selector: "#alertError"});
				AlertError.show("Si è verificato un errore");
				return; 
			});
		
		})
		.service("entityService", function($http)
				{
			this.entityList =		[];
			this.currentPage=1;
			this.maxPage=0;
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

			this.searchPage = function() {
				var promise= $http.get("../entity/pages/"+this.currentPage);
				return promise; 
			};
				})
				.controller("entityFrontController",function($scope,$http,entityService)
						{
//					null
					$scope.currentPage=entityService.currentPage;
					$scope.searchBean=entityService.searchBean;
					$scope.entityList=entityService.entityList;
					$scope.selectedEntity=entityService.selectedEntity;
					$scope.childrenList=entityService.childrenList; 
					$scope.getPagination= function(pageNumber)
					{
						entityService.currentPage=pageNumber;
						$scope.currentPage=pageNumber;
						entityService.searchPage().then(function successCallback(response) {
							entityService.setEntityList(response.data.content);
							console.log(response.data);
							console.log(entityService.selectedEntity);
						},function errorCallback(response) { 
							AlertError.init({selector: "#alertError"});
							AlertError.show("Si è verificato un errore");
							return; 
						});

					}

						});
																																																																		;