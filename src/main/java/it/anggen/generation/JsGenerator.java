package it.anggen.generation;

import it.anggen.reflection.EntityAttributeManager;
import it.anggen.reflection.EntityManager;
import it.anggen.reflection.EntityManagerImpl;
import it.anggen.utils.ClassDetail;
import it.anggen.utils.EntityAttribute;
import it.anggen.utils.ReflectionManager;
import it.anggen.utils.Utility;
import it.anggen.model.FieldType;
import it.anggen.model.RelationshipType;
import it.anggen.model.entity.Entity;
import it.anggen.model.entity.Tab;
import it.anggen.model.field.EnumField;
import it.anggen.model.field.EnumValue;
import it.anggen.model.field.Field;
import it.anggen.model.relationship.Relationship;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.ObjectInputStream.GetField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.dbcp.BasicDataSource;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.codemodel.JClass;


/**
 * Generate the JS source files based on angularJS
 * @author Marco
 *
 */
@Service
public class JsGenerator {

	private Entity entity;
	
	private EntityManager entityManager;
	
	private String entityName;
	
	private List<Field> fieldList;
	
	private List<Relationship> relationshipList;
	
	private List<Entity> descendantEntityList;
	
	private RelationshipType relationshipType;
	
	private Boolean lastLevel;
	
	private String serviceList;
	
	
	
	
	@Autowired
	private Generator generator;

	public JsGenerator()
	{
		
	}

	public void init(Entity entity,Boolean isParent,String parentEntityName,RelationshipType relationshipType, Boolean lastLevel,String serviceList)
	{
		this.entity=entity;
		this.relationshipType=relationshipType;
		entityManager = new EntityManagerImpl(entity);
		
		this.entityName=Utility.getFirstLower(entity.getName());
		
		this.fieldList=entity.getFieldList();
		
		this.relationshipList=entity.getRelationshipList();
		this.lastLevel=lastLevel==null? true : lastLevel;
		this.descendantEntityList=entityManager.getDescendantEntities();
			this.serviceList=serviceList;
		/*List<Class> parentClassList = new ArrayList<Class>();
		parentClassList.add(classClass);
		this.descendantClassList=reflectionManager.getDescendantClassList(classClass, parentClassList);
		if (compositeClass!=null && compositeClass.fullName().contains("java.util.List"))
			entityList=true;
		else
			entityList=false;*/
	}
	
	public void generateMainApp()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("var "+generator.applicationName+"App=angular.module(\""+generator.applicationName+"App\",['ngRoute','ngFileUpload','ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter']);");
		sb.append(getSecurityService());
		sb.append(getMainController());
		sb.append(getNavigation());
		
		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+generator.angularDirectory+generator.applicationName+"/";
		saveAsJsFile(directoryAngularFiles, "main-app", sb.toString());
	}
	
	public void generateControllerFile()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(generator.applicationName+"App");
		for (Entity entity: generator.getEntityList())
		{
			init(entity, null, null, null, null, null);
			sb.append(generateController());
		}
		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+generator.angularDirectory+generator.applicationName+"/";
		saveAsJsFile(directoryAngularFiles, generator.applicationName+"-controller", sb.toString());
	}
	
	public void generateServiceFile()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(generator.applicationName+"App");
		for (Entity entity: generator.getEntityList())
		{
			init(entity, null, null, null, null, null);
			sb.append(generateService ());
		}
		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+generator.angularDirectory+generator.applicationName+"/";
		saveAsJsFile(directoryAngularFiles, generator.applicationName+"-service", sb.toString());
	}
	
	private String getNavigation()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(generator.applicationName+"App.config(function($routeProvider, $locationProvider) \n");
		sb.append("{\n");
		sb.append("$routeProvider\n")
		.append(".when('/',{\n")
		.append("templateUrl:'./home/'\n")
		.append("})\n")
		.append(".when('/home/',{\n")
		.append("templateUrl:'./home/'\n")
		.append("})\n");
		for (Entity entity: generator.getEntityList())
		{
			sb.append(".when('/"+Utility.getFirstUpper(entity.getName())+"/',{\n")
			.append("templateUrl: './"+Utility.getFirstLower(entity.getName())+"/',\n")
			.append("controller:'"+Utility.getFirstLower(entity.getName())+"Controller',\n")

			.append("resolve: {\n")
			.append("setParent: function(mainService,"+Utility.getFirstLower(entity.getName())+"Service){\n")

			.append("if (mainService.parentService!=null)\n")
			.append("{\n")

			.append("mainService.parentService.resetSearchBean();\n")
			.append("mainService.parentService.setSelectedEntity(null);\n")
			.append("mainService.parentService.selectedEntity.show=false;\n")
			.append("mainService.parentService.setEntityList(null);\n") 
			.append("}\n")

			.append("mainService.parentEntity=\""+Utility.getFirstUpper(entity.getName())+"\";\n")

			.append("mainService.parentService="+Utility.getFirstLower(entity.getName())+"Service;\n");
			
			//todo get descendant e init children
			for (Relationship relationship : entity.getRelationshipList())
			{

				sb.append("mainService.parentService.init"+Utility.getFirstUpper(relationship.getEntityTarget().getName())+"List().then(function(response) {\n")
				.append("mainService.parentService.childrenList."+Utility.getFirstLower(relationship.getEntityTarget().getName())+"List=response.data;\n")
				.append("});\n");
			}
			
			sb.append("}\n")
			.append("}\n")
			.append("})\n");
		}
		
		sb.append(";");
		sb.append("$locationProvider.html5Mode(true);\n");
		sb.append("});\n");
		return sb.toString();
	}
	
	/**
	 * Generate the service code for the entity
	 * @return
	 */
	private String generateService()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(".service(\""+entityName+"Service\", function($http,mainService)\n")
		.append("{\n")
		.append("this.entityList =		[];\n")
		.append("this.selectedEntity= 	{show: false \n");
		for (Relationship relationship : relationshipList)
		{
			if (EntityAttributeManager.getInstance(relationship).isList())
				sb.append(","+relationship.getEntityTarget().getName()+"List: []");
		}
		sb.append("};\n")
		
		//check if is parent
		.append("this.isParent=function()\n")
		.append("{\n")
		.append("return mainService.parentEntity==\""+Utility.getFirstUpper(entityName)+"\";\n")
		.append("};\n")
		
		.append("this.childrenList=[]; \n")
		.append("this.addEntity=function (entity)\n")
		.append("{\n")
		.append("this.entityList.push(entity);\n")
		.append("};\n")

		.append("this.emptyList= function(list)\n")
		.append("{\n")
		.append("while (list.length>0)\n")
		.append("list.pop();\n")
		.append("}\n")

		.append("this.setEntityList= function(entityList)\n")
		.append("{ \n")
		.append("while (this.entityList.length>0)\n")
		.append("this.entityList.pop();\n")
		.append("if (entityList!=null)\n")
		.append("for (i=0; i<entityList.length; i++)\n")
		.append("this.entityList.push(entityList[i]);\n")
		.append("};\n");
			sb.append("this.searchBean = 		new Object();\n")
			.append("this.resetSearchBean= function()\n")
			.append("{\n")
			.append("this.searchBean={};\n")
			.append("};\n");
		sb.append("this.setSelectedEntity= function (entity)\n")
		.append("{ \n")
		.append("if (entity == null) {\n")
		.append("entity = {};\n")
		.append("this.selectedEntity.show = false;\n")
		.append("} //else\n")
		.append("var keyList = Object.keys(entity);\n")
		.append("if (keyList.length == 0)\n")
		.append("keyList = Object.keys(this.selectedEntity);\n")
		.append("for (i = 0; i < keyList.length; i++) {\n")
		.append("var val = keyList[i];\n")
		.append("if (val != undefined) {\n")
		.append("if (val.toLowerCase().indexOf(\"list\") > -1\n")
		.append("&& (typeof entity[val] == \"object\" || typeof this.selectedEntity[val]==\"object\")) {\n")
		.append("if (entity[val] != null\n")
		.append("&& entity[val] != undefined) {\n")
		.append("if (this.selectedEntity[val]!=undefined)\n")
		.append("while (this.selectedEntity[val].length > 0)\n")
		.append("this.selectedEntity[val].pop();\n")
		.append("if (entity[val] != null)\n")
		.append("for (j = 0; j < entity[val].length; j++)\n")
		.append("this.selectedEntity[val]\n")
		.append(".push(entity[val][j]);\n")
		.append("} else \n")
		.append("this.emptyList(this.selectedEntity[val]);\n")
		.append("} else {\n")
		.append("if (val.toLowerCase().indexOf(\"time\") > -1\n")
		.append("&& typeof val == \"string\") {\n")
		.append("var date = new Date(entity[val]);\n")
		.append("this.selectedEntity[val] = new Date(entity[val]);\n")
		.append("} else {\n")
		.append("this.selectedEntity[val] = entity[val];\n")
		.append("}\n")
		.append("}\n")
		.append("}\n")
		.append("};\n");
		sb.append("};\n");
		//search
		sb.append("this.search = function() {\n");
		sb.append("this.setSelectedEntity(null);\n");
		sb.append("var promise= $http.post(\""+entityName+"/search\",this.searchBean);\n");
		sb.append("return promise; \n");
		sb.append("};\n");
		//searchOne

		sb.append("this.searchOne=function(entity) {\n");
		//sb.append("this.setSelectedEntity(null);\n");
		sb.append("var promise= $http.get(\""+entityName+"/\"+entity."+entityName+"Id);\n");
		sb.append("return promise; \n");
		sb.append("};\n");


		//insert
		sb.append("this.insert = function() {\n");
		sb.append("var promise= $http.put(\""+entityName+"/\",this.selectedEntity);\n");
		sb.append("return promise; \n");
		sb.append("};\n");
		//update
		sb.append("this.update = function() {\n");
		sb.append("var promise= $http.post(\""+entityName+"/\",this.selectedEntity);\n");
		sb.append("return promise; \n");
		sb.append("}\n");
		//delete
		sb.append("this.del = function() {\n");
		sb.append("var url=\""+entityName+"/\"+this.selectedEntity."+entityName+"Id;\n");
		sb.append("var promise= $http[\"delete\"](url);\n");
		sb.append("return promise; \n");
		sb.append("}\n");

		//load file
		sb.append("this.loadFile= function(file,field){\n");
		sb.append("var formData = new FormData();\n");
		sb.append("if (file!=null)\n");
		sb.append("formData.append('file',file);\n");
		sb.append("var promise= $http.post(\""+entityName+"/\"+this.selectedEntity."+entityName+"Id+\"/load\"+field+\"/\",formData,{\n");
		sb.append(" headers: {'Content-Type': undefined}\n");
		sb.append("});\n");
		sb.append("return promise; \n");
		sb.append("}\n");
		
		if (relationshipList!=null)
			for (Relationship relationship: relationshipList)
			{

				sb.append(" this.init"+Utility.getFirstUpper(relationship.getEntityTarget().getName())+"List= function()\n");
				sb.append("{\n");
				sb.append("var promise= $http\n");
				sb.append(".post(\""+Utility.getEntityCallName(relationship.getEntityTarget().getName())+"/search\",\n");
				sb.append("{});\n");
				sb.append("return promise;\n");
				sb.append("};\n");
			}
		
		
		sb.append("})\n");
		
		return sb.toString();
	}
	/**
	 * Change the visibility of the children entities view
	 * @param stringBuilder
	 * @param show
	 */
	private void changeChildrenVisibility(StringBuilder stringBuilder,Boolean show)
	{
		if (lastLevel)
			return;
		//if (isParent)
		{
			if (descendantEntityList!=null && descendantEntityList.size()>0)
				for (Entity descendantEntity: descendantEntityList)
				{
					stringBuilder.append(descendantEntity.getName()+"Service.selectedEntity.show="+show.toString()+";");
				}
		}
		//else
		{
			if (relationshipList!=null && relationshipList.size()>0)
				for (Relationship relationship: relationshipList)
				{
					stringBuilder.append(relationship.getEntityTarget().getName()+"Service.selectedEntity.show="+show.toString()+";");
				}

		}

	}
	/**
	 * Generate the angularJS controller
	 * @return
	 */
	private String generateController()
	{
		StringBuilder sb = new StringBuilder();

		sb.append(".controller(\""+entityName+"Controller\",function($scope,$http "+getServices()+")\n");
		sb.append("{\n");
		//search var
		sb.append("$scope.searchBean="+Utility.getEntityCallName(entityName)+"Service.searchBean;\n");
		sb.append("$scope.entityList="+Utility.getEntityCallName(entityName)+"Service.entityList;\n");
		sb.append("$scope.selectedEntity="+Utility.getEntityCallName(entityName)+"Service.selectedEntity;\n");

		sb.append("$scope.childrenList="+Utility.getEntityCallName(entityName)+"Service.childrenList; \n");
		//search function
		sb.append("$scope.reset = function()\n");
		sb.append("{\n");
		sb.append(""+Utility.getEntityCallName(entityName)+"Service.resetSearchBean();\n");
		sb.append("$scope.searchBean="+entityName+"Service.searchBean;");
		sb.append(""+Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(null);\n");
		sb.append(""+Utility.getEntityCallName(entityName)+"Service.selectedEntity.show=false;\n");
		sb.append(""+Utility.getEntityCallName(entityName)+"Service.setEntityList(null); \n");
		//if (isParent)
		sb.append("if ("+Utility.getEntityCallName(entityName)+"Service.isParent()) \n");
		sb.append("{\n");
		changeChildrenVisibility(sb, false);
		sb.append("}\n");
			
		sb.append("}\n");


		/* UPDATE PARENT  */
		//if (!isParent)
	//	{
		/*	sb.append("$scope.updateParent = function(toDo)\n");
			sb.append("{\n");

			sb.append(Utility.getEntityCallName(parentEntityName)+"Service.update().then(function successCallback(response) {\n");
			sb.append(Utility.getEntityCallName(parentEntityName)+"Service.setSelectedEntity(response);\n");
			sb.append("if (toDo != null)\n");
			sb.append("toDo();\n");
			sb.append("},function errorCallback(response) {      \n");
			manageRestError(sb);
			sb.append("}\n");
			sb.append(");\n");
			sb.append("};\n");*/
	//	}
		sb.append("$scope.addNew= function()\n");
		sb.append("{\n");
		sb.append(""+Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(null);\n");
		sb.append(""+Utility.getEntityCallName(entityName)+"Service.setEntityList(null);\n");
		sb.append(""+Utility.getEntityCallName(entityName)+"Service.selectedEntity.show=true;\n");

		sb.append("if ("+Utility.getEntityCallName(entityName)+"Service.isParent()) \n");
		sb.append("{\n");
			changeChildrenVisibility(sb, false);
		sb.append("}\n");
		sb.append("$('#"+entityName+"Tabs li:eq(0) a').tab('show');\n");
		sb.append("};\n");
		sb.append("		\n");			
		//search function
		sb.append("$scope.search=function()\n");
		sb.append("{\n");
		sb.append(""+Utility.getEntityCallName(entityName)+"Service.selectedEntity.show=false;\n");
		if (relationshipList!=null)
			for (Relationship relationship: relationshipList)
			{
				if (EntityAttributeManager.getInstance(relationship).isList())
				{
					sb.append(""+Utility.getEntityCallName(entityName)+"Service.searchBean."+relationship.getEntityTarget().getName()+"List=[];\n");
					sb.append(""+Utility.getEntityCallName(entityName)+"Service.searchBean."+relationship.getEntityTarget().getName()+"List.push("+entityName+"Service.searchBean."+relationship.getEntityTarget().getName()+");\n");
					sb.append("delete "+Utility.getEntityCallName(entityName)+"Service.searchBean."+relationship.getEntityTarget().getName()+"; \n");
				}
			}

		sb.append(Utility.getEntityCallName(entityName)+"Service.search().then(function successCallback(response) {\n");
		sb.append(Utility.getEntityCallName(entityName)+"Service.setEntityList(response.data);\n");
		sb.append("},function errorCallback(response) { \n");
		manageRestError(sb);
		sb.append("});\n");

		sb.append("};\n");

		//INSERT
		sb.append("$scope.insert=function()\n");
		sb.append("{\n");
		sb.append("if (!$scope."+entityName+"DetailForm.$valid) return; \n");
		sb.append("if ("+Utility.getEntityCallName(entityName)+"Service.isParent()) \n");
		sb.append("{\n");
		
			sb.append(Utility.getEntityCallName(entityName)+"Service.insert().then(function successCallback(response) { \n");
			sb.append("$scope.search();\n");
			sb.append("},function errorCallback(response) { \n");
			manageRestError(sb);
			sb.append("});\n");
			sb.append("}\n");
			sb.append("else \n");
			sb.append("{\n");
			//sb.append(entityName+"Service.selectedEntity.show=false;\n\n");
			/*sb.append(entityName+"Service.insert().then(function(data) { });\n");*/
			sb.append(Utility.getEntityCallName(entityName)+"Service.selectedEntity.show=false;\n");
			//TODO 
		
			/*if (relationshipType==RelationshipType.MANY_TO_MANY || relationshipType==RelationshipType.MANY_TO_ONE || relationshipType==RelationshipType.MANY_TO_MANY_BACK)
			{
				sb.append(Utility.getEntityCallName(entityName)+"Service.selectedEntity."+parentEntityName+"List.push("+parentEntityName+"Service.selectedEntity);\n");
			}else
			{
				sb.append(Utility.getEntityCallName(entityName)+"Service.selectedEntity."+parentEntityName+"={};\n");
				sb.append(Utility.getEntityCallName(entityName)+"Service.selectedEntity."+parentEntityName+"."+parentEntityName+"Id="+parentEntityName+"Service.selectedEntity."+parentEntityName+"Id;\n");
			
			}*/
			
			
			sb.append(Utility.getEntityCallName(entityName)+"Service.insert().then(function successCallBack(response) { \n");
		/*	if (relationshipType==RelationshipType.MANY_TO_MANY || relationshipType==RelationshipType.ONE_TO_MANY || relationshipType==RelationshipType.MANY_TO_MANY_BACK)
			{
				sb.append(""+Utility.getEntityCallName(parentEntityName)+"Service.selectedEntity."+entityName+"List.push(response.data);\n");
//				sb.append(parentEntityName+"Service.selectedEntity."+entityName+"List.push("+entityName+"Service.selectedEntity);\n\n");

			}else
			{
				sb.append(""+Utility.getEntityCallName(parentEntityName)+"Service.selectedEntity."+entityName+"=response.data;\n");
				sb.append(Utility.getEntityCallName(parentEntityName)+"Service.init"+Utility.getFirstUpper(entityName)+"List().then(function(response) {\n");
				sb.append(Utility.getEntityCallName(parentEntityName)+"Service.childrenList."+Utility.getFirstLower(entityName)+"List=response.data;\n");
				sb.append("});\n");
				//sb.append(parentEntityName+"Service.selectedEntity."+entityName+"="+entityName+"Service.selectedEntity;\n\n");
			}
			*/sb.append("},function errorCallback(response) { \n");
			manageRestError(sb);
			sb.append("});\n");
			//sb.append("$scope.updateParent();\n\n");

		
			sb.append("}\n");
			
		sb.append("};\n");
		//UPDATE
		sb.append("$scope.update=function()\n");
		sb.append("{\n");
		sb.append("if (!$scope."+entityName+"DetailForm.$valid) return; \n");

		sb.append("if ("+Utility.getEntityCallName(entityName)+"Service.isParent()) \n");
		sb.append("{\n");
		
		
			changeChildrenVisibility(sb, false);
			sb.append(Utility.getEntityCallName(entityName)+"Service.update().then(function successCallback(response) { \n");
			sb.append("$scope.search();\n");
			sb.append("},function errorCallback(response) { \n");
			manageRestError(sb);
			sb.append("});\n");
			
			sb.append("}\n");
			sb.append("else \n");
			sb.append("{\n");
			
			sb.append(Utility.getEntityCallName(entityName)+"Service.selectedEntity.show=false;\n\n");
		
			/*if (relationshipType==RelationshipType.MANY_TO_MANY || relationshipType==RelationshipType.ONE_TO_MANY || relationshipType==RelationshipType.MANY_TO_MANY_BACK)
			{
				sb.append("for (i=0; i<"+Utility.getEntityCallName(parentEntityName)+"Service.selectedEntity."+entityName+"List.length; i++)\n\n");
				sb.append("{\n\n");
				sb.append("if ("+parentEntityName+"Service.selectedEntity."+entityName+"List[i]."+entityName+"Id=="+entityName+"Service.selectedEntity."+entityName+"Id)\n\n");
				sb.append(""+parentEntityName+"Service.selectedEntity."+entityName+"List.splice(i,1);\n\n");
				sb.append("}\n\n");

				sb.append(parentEntityName+"Service.selectedEntity."+entityName+"List.push("+entityName+"Service.selectedEntity);\n\n");
			}else
			{
				sb.append(parentEntityName+"Service.selectedEntity."+entityName+"="+entityName+"Service.selectedEntity;\n\n");
			}*/

			//sb.append("$scope.updateParent();\n");
			sb.append(Utility.getEntityCallName(entityName)+"Service.update().then(function successCallback(response){\n");
			//console.log(data);
			sb.append(Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(response.data);\n");
			sb.append("},function errorCallback(response) { \n");
			manageRestError(sb);
			sb.append("});\n");
			sb.append("}\n");
		sb.append("};\n");


			sb.append("$scope.remove= function()\n");
			sb.append("{\n");
			sb.append(Utility.getEntityCallName(entityName)+"Service.selectedEntity.show=false;\n");
			
			
			/*if (relationshipType==RelationshipType.MANY_TO_MANY || relationshipType==RelationshipType.ONE_TO_MANY || relationshipType==RelationshipType.MANY_TO_MANY_BACK)
			{
				sb.append("for (i=0; i<"+parentEntityName+"Service.selectedEntity."+entityName+"List.length; i++)\n");
				sb.append("{\n");
				sb.append("if ("+parentEntityName+"Service.selectedEntity."+entityName+"List[i]."+entityName+"Id=="+entityName+"Service.selectedEntity."+entityName+"Id)\n");
				sb.append(""+parentEntityName+"Service.selectedEntity."+entityName+"List.splice(i,1);\n");
				sb.append("}\n");

			}else
			{
				sb.append(parentEntityName+"Service.selectedEntity."+entityName+"=null;\n");
			}*/

			sb.append(Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(null);\n");
			sb.append("$scope.updateParent();\n");
			
			sb.append("};\n");
		

		//DELETE
		sb.append("$scope.del=function()\n");
		sb.append("{\n");
		//update entity in $scope
		
		
		/*if (relationshipType==RelationshipType.MANY_TO_MANY || relationshipType==RelationshipType.ONE_TO_MANY || relationshipType==RelationshipType.MANY_TO_MANY_BACK)
		{
			sb.append("for (i=0; i<"+parentEntityName+"Service.selectedEntity."+entityName+"List.length; i++)\n");
			sb.append("{\n");
			sb.append("if ("+parentEntityName+"Service.selectedEntity."+entityName+"List[i]."+entityName+"Id=="+entityName+"Service.selectedEntity."+entityName+"Id)\n");
			sb.append(""+parentEntityName+"Service.selectedEntity."+entityName+"List.splice(i,1);\n");
			sb.append("}\n");

		}else
		{
			sb.append("if (!"+Utility.getEntityCallName(entityName)+"Service.isParent()) \n");
				sb.append(parentEntityName+"Service.selectedEntity."+entityName+"=null;\n");
		}*/
		sb.append("if (!"+Utility.getEntityCallName(entityName)+"Service.isParent()) \n");
			sb.append("$scope.updateParent();\n");
		
		sb.append(Utility.getEntityCallName(entityName)+"Service.del().then(function successCallback(response) { \n");
		sb.append("if ("+Utility.getEntityCallName(entityName)+"Service.isParent()) \n");
		sb.append("{\n");

			sb.append("$scope.search();\n");
			sb.append("} else { \n");
			
			sb.append(Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(null);\n");
			//sb.append(parentEntityName+"Service.init"+Utility.getFirstUpper(entityName)+"List().then(function(response) {\n");
			//sb.append(parentEntityName+"Service.childrenList."+Utility.getFirstLower(entityName)+"List=response.data;\n");
			
			//sb.append("}\n");
			//sb.append("});\n"); //close ajax call
			
		sb.append("}\n");
		sb.append("},function errorCallback(response) { \n");
		manageRestError(sb);
		sb.append("});\n");
		sb.append("};\n");
		for (Tab tab: entityManager.getTabList())
		{
			sb.append("$scope.refreshTable"+Utility.getFirstUpper(tab.getName().replaceAll(" ", ""))+"= function() \n");
			sb.append("{\n");
			sb.append(resetTableTab(tab.getName(),entity));
		sb.append("};\n");
		}


		//loadFile
		sb.append("$scope.loadFile = function(file,field)\n");
		sb.append("{\n");
		sb.append(entityName+"Service.loadFile(file,field).then(function successCallback(response) {\n");
		sb.append(entityName+"Service.setSelectedEntity(response.data);\n");
		sb.append("},function errorCallback(response) { \n");
		manageRestError(sb);
		sb.append("return; \n");
		sb.append("});\n");
		sb.append("}\n");

		sb.append("$scope.trueFalseValues=[true,false];\n");
		//if (isParent)
		{
			for (Relationship relationship: relationshipList)
				if (relationship.getEntityTarget().getEntityGroup()!=null)
			{
				
				
				
				sb.append("$scope.show"+Utility.getFirstUpper(relationship.getEntityTarget().getName())+"Detail= function(index)\n");
				sb.append("{\n");
				sb.append("if (index!=null)\n");
				//sb.append(field.getName()+"Service.setSelectedEntity("+entityName+"Service.selectedEntity."+field.getName()+"List[index]);\n");
				sb.append("{\n");
				sb.append(relationship.getEntityTarget().getName()+"Service.searchOne("+entityName+"Service.selectedEntity."+relationship.getEntityTarget().getName()+"List[index]).then(\n");
				sb.append("function successCallback(response) {\n");
				sb.append("console.log(\"response-ok\");\n");
				sb.append("console.log(response);\n");
				initChildrenList(sb, relationship.getEntityTarget());
				sb.append(Utility.getEntityCallName(relationship.getEntityTarget().getName())+"Service.setSelectedEntity(response.data[0]);\n");
				sb.append(Utility.getEntityCallName(relationship.getEntityTarget().getName())+"Service.selectedEntity.show=true;\n");

				sb.append("  }, function errorCallback(response) {\n");
				// called asynchronously if an error occurs
				// or server returns response with an error status.
				//sb.append(" console.log(\"response-error-controller\");\n");
				//sb.append("console.log(response);\n");
				manageRestError(sb);
				sb.append("  }	\n");

				sb.append(");\n");
				sb.append("}\n");
				sb.append("else \n");
				sb.append("{\n");
				sb.append("if ("+Utility.getEntityCallName(entityName)+"Service.selectedEntity."+relationship.getEntityTarget().getName()+"==null || "+entityName+"Service.selectedEntity."+relationship.getEntityTarget().getName()+"==undefined)\n");
				sb.append("{\n");
				initChildrenList(sb, relationship.getEntityTarget());
				sb.append(Utility.getEntityCallName(relationship.getEntityTarget().getName())+"Service.setSelectedEntity(null); \n");
				sb.append(Utility.getEntityCallName(relationship.getEntityTarget().getName())+"Service.selectedEntity.show=true; \n");
				//TODO set owner, list or entity?
				sb.append("}\n");
				sb.append("else\n");
				sb.append(Utility.getEntityCallName(relationship.getEntityTarget().getName())+"Service.searchOne("+entityName+"Service.selectedEntity."+relationship.getEntityTarget().getName()+").then(\n");
				
				sb.append("function successCallback(response) {\n");
				//sb.append("console.log(\"response-ok\");\n");
				//sb.append("console.log(response);\n");
				initChildrenList(sb, relationship.getEntityTarget());
				sb.append(Utility.getEntityCallName(relationship.getEntityTarget().getName())+"Service.setSelectedEntity(response.data[0]);\n");
				sb.append(Utility.getEntityCallName(relationship.getEntityTarget().getName())+"Service.selectedEntity.show=true;\n");

				sb.append("  }, function errorCallback(response) {\n");
				manageRestError(sb);
				sb.append("  }	\n");
				
				sb.append(");\n");


				sb.append("}\n");
				sb.append("$('#"+relationship.getEntityTarget().getName()+"Tabs li:eq(0) a').tab('show');\n");
				sb.append("};\n");

			}

		}
		//pagination
		//sb.append("if ("+Utility.getEntityCallName(entityName)+"Service.isParent()) \n");
		//sb.append("{\n");

			sb.append(getPagination(null));
		//sb.append("}\n");	
		Entity mainEntity=entity;
		Boolean mainIsParent= false; //isParent;
		String mainParentName = entityName;//parentEntityName;
		RelationshipType mainEntityList = relationshipType;
		EntityManager mainEntityManager= new EntityManagerImpl(mainEntity);
		String mainServiceList = serviceList;
		for (Relationship relationship: relationshipList)
		{
			init(relationship.getEntityTarget(), false, entityName,relationship.getRelationshipType(),mainEntityManager.isLastLevel(relationship.getEntityTarget()),mainServiceList);
			sb.append(getPagination(mainParentName));
		}
		init(mainEntity,mainIsParent,mainParentName,relationshipType,entityManager.isLastLevel(mainEntity),serviceList);
		sb.append("$scope.downloadEntityList=function()\n");
		sb.append("{\n");
		sb.append("var mystyle = {\n");
		sb.append(" headers:true, \n");
		sb.append("column: {style:{Font:{Bold:\"1\"}}}\n");
		sb.append("};\n");
		
		String exportFields="";
		for (Field field: fieldList)
		{
			if (EntityAttributeManager.getInstance(field).getExcelExport())
				exportFields=exportFields+field.getName()+",";
		}
		
		if (exportFields.length()>0)
		{
			exportFields=exportFields.substring(0, exportFields.length()-1);
		} else
			exportFields="*";
		sb.append("alasql('SELECT "+exportFields+" INTO XLSXML(\""+entityName+".xls\",?) FROM ?',[mystyle,$scope.entityList]);\n");
		sb.append("};\n");
		
		
		for (Relationship relationship: relationshipList)
		{
			if (EntityAttributeManager.getInstance(relationship).isList())
			{
				sb.append("$scope.saveLinked"+Utility.getFirstUpper(relationship.getEntityTarget().getName())+"= function() {\n");
				sb.append(Utility.getEntityCallName(entityName)+"Service.selectedEntity."+relationship.getEntityTarget().getName()+"List.push("+Utility.getEntityCallName(entityName)+"Service.selectedEntity."+relationship.getEntityTarget().getName()+");\n");
				sb.append("}\n");
			}
			
			sb.append("$scope.download"+Utility.getFirstUpper(relationship.getEntityTarget().getName())+"List=function()\n");
			sb.append("{\n");
			sb.append("var mystyle = {\n");
			sb.append(" headers:true, \n");
			sb.append("column: {style:{Font:{Bold:\"1\"}}}\n");
			sb.append("};\n");
			
			exportFields="";
			for (Field field : fieldList)
			{
				if (EntityAttributeManager.getInstance(field).getExcelExport())
					exportFields=exportFields+relationship.getEntityTarget().getName()+",";
			}
			
			if (exportFields.length()>0)
			{
				exportFields=exportFields.substring(0, exportFields.length()-1);
			} else
				exportFields="*";
			sb.append("alasql('SELECT "+exportFields+" INTO XLSXML(\""+relationship.getEntityTarget().getName()+".xls\",?) FROM ?',[mystyle,$scope.selectedEntity."+relationship.getEntityTarget().getName()+"List]);\n");
			sb.append("};\n");
		}

		sb.append("})\n");
		return sb.toString();
	}
	/**
	 * Create the pagination option for ui-grid
	 * @return
	 */
	private String getPagination(String parentEntityName)
	{
		StringBuilder sb = new StringBuilder();
		//pagination options
		sb.append("$scope."+entityName+ (relationshipType==RelationshipType.MANY_TO_MANY || relationshipType==RelationshipType.ONE_TO_MANY || relationshipType==RelationshipType.MANY_TO_MANY_BACK? "List":"")+"GridOptions = {\n");
		sb.append("enablePaginationControls: true,\n");
		sb.append("multiSelect: false,\n");
		sb.append("enableSelectAll: false,\n");
		sb.append("paginationPageSizes: [10, 20, 30],\n");
		sb.append("paginationPageSize: 10,\n");
		sb.append("enableGridMenu: true,\n");
		//generate dynamically
		sb.append("columnDefs: [    \n");
		List<EntityAttribute> entityAttributeList = entityManager.getAttributeList();
		Utility.orderByPriority(entityAttributeList);
		for (EntityAttribute entityAttribute: entityAttributeList)
		{
			if ((EntityAttributeManager.getInstance(entityAttribute).asRelationship()!=null && EntityAttributeManager.getInstance(entityAttribute).getIgnoreTableList()) || (EntityAttributeManager.getInstance(entityAttribute).asField()!=null && EntityAttributeManager.getInstance(entityAttribute).getIgnoreTableList())) continue;
			
			if (EntityAttributeManager.getInstance(entityAttribute).asField()!= null )
			{
				if (EntityAttributeManager.getInstance(entityAttribute).getPassword())
				{
					continue;
				}
				if (EntityAttributeManager.getInstance(entityAttribute).asField().getFieldType()==FieldType.TIME)
				{
					sb.append("{ name: '"+entityAttribute.getName()+"', cellFilter: \"date:\'HH:mm\'\"},\n");
				}else
				{
					if (EntityAttributeManager.getInstance(entityAttribute).asField().getFieldType()==FieldType.DATE)
						sb.append("{ name: '"+entityAttribute.getName()+"', cellFilter: \"date:\'dd-MM-yyyy\'\"},\n");
					else
						sb.append("{ name: '"+entityAttribute.getName()+"'},\n");
				}
			}
			else if (!EntityAttributeManager.getInstance(entityAttribute).isList() )//&& isParent)
			{
				sb.append("{ name: '"+entityAttribute.getName()+"."+entityAttribute.getName()+"Id', displayName: '"+entityAttribute.getName()+"'},\n");
			}
		}
		sb.setCharAt(sb.length()-2, ' ');
		sb.append("]\n");

		if (parentEntityName==null)
			sb.append(",data: "+Utility.getEntityCallName(entityName)+"Service.entityList\n");
		else
			sb.append(",data: $scope.selectedEntity."+entityName+"List\n");
		sb.append(" };\n");
		//if (parentEntityName!=null && parentEntityName.equals("entity"))
		//	System.out.println("**tento di generare** "+entityName+" - parent "+parentEntityName+" - lastLevel"+lastLevel+"- isParent "+isParent);
			
	//	if (lastLevel ) 
	//		return sb.toString();
		//if (parentEntityName!=null && parentEntityName.equals("entity"))
		//System.out.println("**GENERO** "+entityName+" - parent "+parentEntityName+" - lastLevel"+lastLevel+"- isParent "+isParent);
		//on row selection
		sb.append("$scope."+entityName+ (relationshipType==RelationshipType.MANY_TO_MANY || relationshipType==RelationshipType.ONE_TO_MANY || relationshipType==RelationshipType.MANY_TO_MANY_BACK? "List":"")+"GridOptions.onRegisterApi = function(gridApi){\n");
		sb.append("$scope."+entityName+"GridApi = gridApi;");
		sb.append("gridApi.selection.on.rowSelectionChanged($scope,function(row){\n");
		//if (isParent)
		//	changeChildrenVisibility(sb, false);
		
		sb.append("if (row.isSelected)\n");
		sb.append("{\n");
		//if (isParent)
		//{
			//sb.append(Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(row.entity);\n");
			
		//} else
		//{

if (entity.getEntityGroup()!=null)
{
			sb.append(Utility.getEntityCallName(entityName)+"Service.searchOne(row.entity).then(function(response) { \n");
			sb.append("console.log(response.data);\n");
			sb.append(Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(response.data[0]);\n");
			sb.append("});\n");
}
			//}
			sb.append("$('#"+entityName+"Tabs li:eq(0) a').tab('show');\n");
			sb.append("}\n");
			sb.append("else \n");
			sb.append(Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(null);\n");
			sb.append(Utility.getEntityCallName(entityName)+"Service.selectedEntity.show = row.isSelected;\n");
			sb.append("});\n");
		sb.append("  };\n");

		return sb.toString();
	}

	/**
	 * Return the useful services for the entity controller
	 * @return
	 */
	private String getServices() {
		List<Entity> parentClassList= new ArrayList<Entity>();
		parentClassList.add(entity);
		String services="";
		services=services+","+entityName+"Service, securityService, mainService ";
		if (descendantEntityList!=null)
		for (Entity descendantEntity : descendantEntityList)
			if (descendantEntity.getEntityGroup()!=null)
		{
			services=services+","+descendantEntity.getName()+"Service";

		}
		return services;
	}
	
	private String checkSecurity(String entity,String action)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("if (securityService.restrictionList."+entity+"==undefined || securityService.restrictionList."+entity+".can"+Utility.getFirstUpper(action)+")\n");
		return sb.toString();
	}
	
	private void initChildrenList(StringBuilder sb,Entity entity)
	{
		if (entity.getRelationshipList()!=null)
			for (Relationship relationship: entity.getRelationshipList())
			{

				sb.append(checkSecurity(relationship.getEntityTarget().getName(), "search"));
				sb.append(entity.getName()+"Service.init"+Utility.getFirstUpper(relationship.getEntityTarget().getName())+"List().then(function successCallback(response) {\n");
				sb.append(entity.getName()+"Service.childrenList."+Utility.getFirstLower(relationship.getEntityTarget().getName())+"List=response.data;\n");
				sb.append("},function errorCallback(response) { \n");
				manageRestError(sb);
				sb.append("});\n");
			}
		if (entity.getEnumFieldList()!=null)
			for (EnumField enumField: entity.getEnumFieldList())
			{
				sb.append(entity.getName()+"Service.childrenList."+enumField.getName()+"List=[");
				for (EnumValue enumValue: enumField.getEnumEntity().getEnumValueList())
				{
					sb.append("\""+enumValue.getName()+"\",");
				}
				sb.append("];\n");

			}
	}
	
	
	private String getMainController()
	{
		StringBuilder sb = new StringBuilder();	
		sb.append("testApp.service(\"mainService\", function()\n");
		sb.append("{\n");
		sb.append("this.parentEntity=\"\";\n");
		sb.append(" this.parentService=null; \n");
		sb.append("});\n");
		sb.append(generator.applicationName+"App.controller(\"MainController\",function($scope, $route, $routeParams, $location,mainService)\n");
		sb.append("{\n");
		sb.append("$scope.$route = $route;\n");
		sb.append("$scope.$location = $location;\n");
		sb.append("$scope.$routeParams = $routeParams;\n");


		sb.append("});\n");
		return sb.toString();

	}
	
	
	
	private String getSecurityService()
	{
		StringBuilder sb = new StringBuilder();	
			sb.append(generator.applicationName+"App.service(\"securityService\",function($http)\n");
			sb.append("{\n");
			sb.append("this.restrictionList;\n");
			if (generator.security)
			{
				sb.append("this.init= function() {\n");
				sb.append("var promise= $http.get(\"../authentication/\");\n");
				sb.append("return promise; \n");
				sb.append("};\n");
			}
			sb.append("})\n");
		String services = serviceList;
		if (services==null)
			services="";
		sb.append(".run(function($rootScope,securityService"+services+"){\n");

		if (generator.security)
		{
			sb.append("securityService.init().then(function successCallback(response) {\n");
			sb.append("securityService.restrictionList=response.data;\n");
			sb.append("$rootScope.restrictionList=response.data;\n");
		} else
		{
			sb.append("securityService.restrictionList={};\n");
			sb.append("$rootScope.restrictionList={};\n");
		}
			//initChildrenList(sb);
				
		if (generator.security)
			sb.append("});\n");

		
		
		sb.append("});\n");


		return sb.toString();
	}
	
	/**
	 * Create the JS string
	 * @return
	 */
	private String buildJS()
	{
		StringBuilder buildJS= new StringBuilder();
		buildJS.append("var "+entityName+"App=angular.module(\""+entityName+"App\",['ngFileUpload','ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])\n");
		//JsGenerator jsGenerator = new JsGenerator(entity, true,null,null);
		buildJS.append(getSecurityService());
		buildJS.append(generateService());
		if (entityName.equals("entity"))
			System.out.println("");
		buildJS.append(generateController());
		List<Relationship> descendantRelationshipList = entityManager.getDescendantRelationship();
		Set<Relationship> descendantRelationshipSet = new HashSet<Relationship>();
		for (Relationship relationshipToInsert : descendantRelationshipList)
		{
			Boolean found = false;
			for  (Relationship insertedRelationship: descendantRelationshipSet)
			{
				if (insertedRelationship.getEntityTarget().getEntityId().equals(relationshipToInsert.getEntityTarget().getEntityId()))
				{	
					found= true;
					break;
				}
			}
			if (!found)
				descendantRelationshipSet.add(relationshipToInsert);
		}
		
		String entityName=entity.getName();
		EntityManager mainEntityManager = new EntityManagerImpl(entity);
		String mainServiceList = serviceList;
		for (Relationship descendantRelationship : descendantRelationshipSet)
		{
			init(descendantRelationship.getEntityTarget(),false,entityName,descendantRelationship.getRelationshipType(),mainEntityManager.isLastLevel(descendantRelationship.getEntityTarget()),mainServiceList);
			buildJS.append(generateService());
			buildJS.append(generateController());
			
			
		}
		buildJS.append(";");
		return buildJS.toString();
	}



	private void manageRestError(StringBuilder sb)
	{
		sb.append("AlertError.init({selector: \"#alertError\"});\n");
		sb.append("AlertError.show(\"Si è verificato un errore\");\n");
		sb.append("return; \n");
	}
	

	/**
	 * Initialize a form
	 * @return
	 */
	private String setTempEntityForm()
	{
		StringBuilder sb = new StringBuilder();
		for (Field field: fieldList)
		{
			sb.append("temp"+Utility.getFirstUpper(entityName)+"."+Utility.getFirstLower(field.getName())+"=data[i]."+Utility.getFirstLower(field.getName())+";\n");
		}

		return sb.toString();
	}
	
	private void saveAsJsFile(String directory, String fileName, String content)
	{
		File dir = new File(directory);
		if (!dir.exists())
			dir.mkdirs();
		File file = new File(directory+fileName+".js");
		PrintWriter writer;
		try {
			writer = new PrintWriter(file, "UTF-8");
			writer.write(content);
			writer.close();
			System.out.println("written js file "+file.getAbsolutePath());
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Save the generated file
	 * @param directory
	 */
	public void saveJsToFile(String directory)
	{
		File dir = new File(directory);
		if (!dir.exists())
			dir.mkdirs();
		File file = new File(directory+Utility.getFirstLower(entityName)+".js");
		PrintWriter writer;
		try {
			writer = new PrintWriter(file, "UTF-8");
			writer.write(buildJS());
			writer.close();
			System.out.println("written js file "+file.getAbsolutePath());
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String resetTableTab(String tabName,Entity entity) {
		String resetTableTabString="";
		for (Relationship relationship: entity.getRelationshipList())
		{
			resetTableTabString=resetTableTabString+"if ($scope."+relationship.getEntityTarget().getName()+"GridApi!=undefined && $scope."+relationship.getEntityTarget().getName()+"GridApi!=null)\n";
			resetTableTabString=resetTableTabString+" $scope."+relationship.getEntityTarget().getName()+"GridApi.core.handleWindowResize(); \n";
				//$scope.seedQueryGridApi.core.handleWindowResize();
		}
		//resetTableTabString = resetTableTabString+" alert('done');";
		return resetTableTabString;
	}
	public static String scriptResizeTableTab(String tabName,String entityName)
	{
		StringBuilder sb= new StringBuilder();

		sb.append("$('a[href=\"#"+entityName+"-"+tabName+"\"]').on('shown.bs.tab', function (e) {\n");
		sb.append("var target = $(e.target).attr(\"href\"); // activated tab\n");
		sb.append("//console.log(target);\n");
		sb.append("if (angular.element($('#"+entityName+"Tabs')).scope()!=null && angular.element($('#"+entityName+"Tabs')).scope()!=undefined) \n");
		//TODO FIX
		sb.append("{ /* angular.element($('#"+entityName+"Tabs')).scope().refreshTable"+Utility.getFirstUpper(tabName.replaceAll(" ",""))+"(); */ }\n");
		sb.append("});\n");

		return sb.toString();
	}
	
	
	
	
	
	/*
	private void generateChildrenJs(StringBuilder sb,List<JsGenerator> jsGeneratorList, List<Class> parentClassList)
	{
		if (fieldList==null) return;
		for (Field field: fieldList)
		{
			if (field.getCompositeClass()!=null && !parentClassList.contains(field.getFieldClass()))
			{
				ReflectionManager reflectionManager = new ReflectionManager(field.getFieldClass());
				List<Field> childrenList= reflectionManager.getChildrenFieldList();
				JsGenerator jsGenerator = new JsGenerator(field.getFieldClass(), false, field.getCompositeClass(), entityName);
				sb.append(jsGenerator.generateService());
				jsGeneratorList.add(jsGenerator);
			}
		}
		//vado in ricorsione
		for (Field field: fieldList)
		{
			if (field.getCompositeClass()!=null && !parentClassList.contains(field.getFieldClass()))
			{
				//HtmlCreator htmlCreator = new HtmlCreator(field.getFieldClass());
				JsGenerator jsGenerator = new JsGenerator(field.getFieldClass(), false, field.getCompositeClass(), entityName);
				parentClassList.add(field.getFieldClass());
				jsGenerator.generateChildrenJs(sb,jsGeneratorList, parentClassList);
			}
		}
	}*/



}
