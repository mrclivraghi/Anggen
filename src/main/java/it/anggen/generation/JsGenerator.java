package it.anggen.generation;

import it.anggen.reflection.EntityAttributeManager;
import it.anggen.reflection.EntityManager;
import it.anggen.reflection.EntityManagerImpl;
import it.anggen.utils.ClassDetail;
import it.anggen.utils.EntityAttribute;
import it.anggen.utils.ReflectionManager;
import it.anggen.utils.Utility;
import it.anggen.model.FieldType;
import it.anggen.model.GenerationType;
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
	}
	
	public void generateMainApp()
	{
		generateSecurityService();
		generateMainService();
		generateMainController();
		generateHomeController();
		generateSwaggerService();
		generateSwaggerController();
		
		generateIndexRoute();
		generateIndexRun();
		generateIndexConfig();
		generateIndexConstants();
		generateIndexModule();
		

	}
	
	public void generateControllerFile()
	{
		for (Entity entity: generator.getEntityList())
		{
			StringBuilder sb = new StringBuilder();
			sb.append("(function() { \n")
			//.append("'use strict'; \n")
			.append("\n");
			
			sb.append("angular\n.module(\""+Generator.appName+"\")\n");
			init(entity, null, null, null, null, null);
			sb.append(generateController());
			
			sb.append("})();\n");
		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+Generator.generateAngularDirectory+entity.getName()+"/";
		saveAsJsFile(directoryAngularFiles, entity.getName()+".controller", sb.toString());
		}
	}
	
	public void generateServiceFile()
	{
		for (Entity entity: generator.getEntityList())
		{
			StringBuilder sb = new StringBuilder();
			sb.append("(function() { \n")
			//.append("'use strict'; \n")
			.append("\n");
			
			sb.append("angular\n.module(\""+Generator.appName+"\")\n");
			init(entity, null, null, null, null, null);
			sb.append(generateService ());
			
			sb.append("})();\n");
		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+Generator.generateAngularDirectory+entity.getName()+"/";
		saveAsJsFile(directoryAngularFiles, entity.getName()+".service", sb.toString());
		}
	}
	
	public void generateDirectiveFile()
	{
		for (Entity entity: generator.getEntityList())
		{
			init(entity, null, null, null, null, null);
			generateDetailDirective();
			generateSearchDirective();
		}
	}
	

	
	/**
	 * Generate the service code for the entity
	 * @return
	 */
	private String generateService()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(".service(\""+entityName+"Service\", "+Utility.getFirstUpper(entityName)+"Service);\n");
		
		sb.append("/** @ngInject */\n");
		
		sb.append("function "+Utility.getFirstUpper(entityName)+"Service($http,MainService,UtilityService)\n")
		.append("{\n")
		.append("this.entityList =		[];\n")
		.append("this.preparedData={};\n")
		.append("this.selectedEntity= 	{show: false \n");
		for (Relationship relationship : relationshipList)
		{
			if (EntityAttributeManager.getInstance(relationship).isList())
				sb.append(","+relationship.getEntityTarget().getName()+"List: []");
		}
		sb.append("};\n")
		.append("this.hidden= { hiddenFields: []};\n")
		//check if is parent
		.append("this.isParent=function()\n")
		.append("{\n")
		.append("return MainService.parentEntity==\""+Utility.getFirstUpper(entityName)+"\";\n")
		.append("};\n")
		
		//.append("this.childrenList={}; \n")
		.append("this.addEntity=function (entity)\n")
		.append("{\n")
		.append("this.entityList.push(entity);\n")
		.append("};\n")

/*		.append("this.emptyList= function(list)\n")
		.append("{\n")
		.append("while (list.length>0)\n")
		.append("list.pop();\n")
		.append("}\n")*/

		.append("this.setEntityList= function(entityList)\n")
		.append("{ \n")
		.append("while (this.entityList.length>0)\n")
		.append("this.entityList.pop();\n")
		.append("if (entityList!=null)\n")
		.append("for (var i=0; i<entityList.length; i++)\n")
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
		.append("UtilityService.cloneObject(entity,this.selectedEntity);\n");
		
		sb.append("};\n");
		//search
		sb.append("this.search = function() {\n");
		sb.append("this.setSelectedEntity(null);\n");
		sb.append("var promise= $http.post(\""+Generator.restUrlProperty+entityName+"/search\",this.searchBean);\n");
		sb.append("return promise; \n");
		sb.append("};\n");
		//searchOne

		sb.append("this.searchOne=function(entity) {\n");
		//sb.append("this.setSelectedEntity(null);\n");
		sb.append("var promise= $http.get(\""+Generator.restUrlProperty+entityName+"/\"+entity."+entityName+"Id);\n");
		sb.append("return promise; \n");
		sb.append("};\n");


		//insert
		sb.append("this.insert = function() {\n");
		sb.append("var promise= $http.put(\""+Generator.restUrlProperty+entityName+"/\",this.selectedEntity);\n");
		sb.append("return promise; \n");
		sb.append("};\n");
		//update
		sb.append("this.update = function() {\n");
		sb.append("var promise= $http.post(\""+Generator.restUrlProperty+entityName+"/\",this.selectedEntity);\n");
		sb.append("return promise; \n");
		sb.append("}\n");
		//delete
		sb.append("this.del = function() {\n");
		sb.append("var url=\""+Generator.restUrlProperty+entityName+"/\"+this.selectedEntity."+entityName+"Id;\n");
		sb.append("var promise= $http[\"delete\"](url);\n");
		sb.append("return promise; \n");
		sb.append("}\n");

		//load file
		sb.append("this.loadFile= function(file,field){\n");
		sb.append("var formData = new FormData();\n");
		sb.append("if (file!=null)\n");
		sb.append("formData.append('file',file);\n");
		sb.append("var promise= $http.post(\""+Generator.restUrlProperty+entityName+"/\"+this.selectedEntity."+entityName+"Id+\"/load\"+field+\"/\",formData,{\n");
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
				sb.append(".post(\""+Generator.restUrlProperty+Utility.getEntityCallName(relationship.getEntityTarget().getName())+"/search\",\n");
				sb.append("{});\n");
				sb.append("return promise;\n");
				sb.append("};\n");
			}
		
		sb.append(generateGridOptions());
		
		sb.append("}\n");
		
		
		
		//sb.append("}\n");	
	/*	Entity mainEntity=entity;
		Boolean mainIsParent= false; //isParent;
		String mainParentName = entityName;//parentEntityName;
		EntityManager mainEntityManager= new EntityManagerImpl(mainEntity);
		String MainServiceList = serviceList;
		for (Relationship relationship: relationshipList)
		{
			init(relationship.getEntityTarget(), false, entityName,relationship.getRelationshipType(),mainEntityManager.isLastLevel(relationship.getEntityTarget()),MainServiceList);
			sb.append(getPagination(mainParentName));
		}
		init(mainEntity,mainIsParent,mainParentName,relationshipType,entityManager.isLastLevel(mainEntity),serviceList);
		
		
		*/
		return sb.toString();
	}
	/**
	 * Change the visibility of the children entities view
	 * @param stringBuilder
	 * @param show
	 */
	private void changeChildrenVisibility(StringBuilder stringBuilder,Boolean show)
	{
		//if (lastLevel)
		//	return;
		//if (isParent)
		{
			/*if (descendantEntityList!=null && descendantEntityList.size()>0)
				for (Entity descendantEntity: descendantEntityList)
				{
					stringBuilder.append(descendantEntity.getName()+"Service.selectedEntity.show="+show.toString()+";");
				}*/
		}
		//else
		{
			if (relationshipList!=null && relationshipList.size()>0)
				for (Relationship relationship: relationshipList)
				{
					stringBuilder.append(relationship.getEntityTarget().getName()+"Service.selectedEntity.show="+show.toString()+";\n");
					stringBuilder.append("delete $rootScope.openNode."+relationship.getEntityTarget().getName()+";\n");
					stringBuilder.append("UtilityService.removeObjectFromList($rootScope.parentServices,"+relationship.getEntityTarget().getName()+"Service);\n");
				}

		}

	}
	
	private  void generateSearchDirective(){
		StringBuilder sb = new StringBuilder();
		sb.append("(function() {\n")
		.append("'use strict';\n")

		.append(" angular\n")
		.append(" .module('"+Generator.appName+"')\n")
		.append(" .directive('"+entityName+"Search', "+entityName+"Search);\n")

		.append("/** @ngInject */\n")
		.append("  function "+entityName+"Search("+entityName+"Service) {\n")
		.append("  var directive = {\n")
		.append("  restrict: 'E',\n")
		.append(" templateUrl: 'app/components/"+entityName+"/"+entityName+"-search.html',\n")
		.append("  scope: {\n")
		.append("  fields: '='\n")
		.append(" },\n")
		.append("controller: '"+Utility.getFirstUpper(entityName)+"Controller',\n")
		.append("controllerAs: 'vm',\n")
		.append("bindToController: true,\n")
		.append(" link: function(scope,element,attributes) {\n")
		.append("if (attributes.fields)\n")
		.append(""+entityName+"Service.hidden.hiddenFields=attributes.fields.split(\";\");\n")
		.append(" }\n")
		.append("};\n")

		.append("return directive;\n")

		.append(" }\n")

		.append("})();\n");
		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+Generator.generateAngularDirectory+entity.getName()+"/";
		saveAsJsFile(directoryAngularFiles, entity.getName()+"Search.directive", sb.toString());

	}
	
	
	private  void generateDetailDirective(){
		StringBuilder sb = new StringBuilder();
		sb.append("(function() {\n")
		.append("'use strict';\n")

		.append(" angular\n")
		.append(" .module('"+Generator.appName+"')\n")
		.append(" .directive('"+entityName+"Detail', "+entityName+"Detail);\n")

		.append("/** @ngInject */\n")
		.append("  function "+entityName+"Detail("+entityName+"Service) {\n")
		.append("  var directive = {\n")
		.append("  restrict: 'E',\n")
		.append(" templateUrl: 'app/components/"+entityName+"/"+entityName+"-detail.html',\n")
		.append("  scope: {\n")
		.append("  fields: '='\n")
		.append(" },\n")
		.append("controller: '"+Utility.getFirstUpper(entityName)+"Controller',\n")
		.append("controllerAs: 'vm',\n")
		.append("bindToController: true,\n")
		.append(" link: function(scope,element,attributes) {\n")
		.append("if (attributes.fields)\n")
		.append(""+entityName+"Service.hidden.hiddenFields=attributes.fields.split(\";\");\n")
		.append(" }\n")
		.append("};\n")

		.append("return directive;\n")

		.append(" }\n")

		.append("})();\n");
		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+Generator.generateAngularDirectory+entity.getName()+"/";
		saveAsJsFile(directoryAngularFiles, entity.getName()+"Detail.directive", sb.toString());

	}
	
	
	/**
	 * Generate the angularJS controller
	 * @return
	 */
	private String generateController()
	{
		StringBuilder sb = new StringBuilder();

		sb.append(".controller(\""+Utility.getFirstUpper(entityName)+"Controller\","+Utility.getFirstUpper(entityName)+"Controller);\n");
		sb.append("/** @ngInject */\n");
		sb.append("function "+Utility.getFirstUpper(entityName)+"Controller($scope,$http,$rootScope,$log,UtilityService "+getServices()+")\n");
		sb.append("{\n");
		//search var
		sb.append("var vm=this;\n");
		sb.append("vm.activeTab=1;\n");
		sb.append("vm.searchBean="+Utility.getEntityCallName(entityName)+"Service.searchBean;\n");
		sb.append("vm.entityList="+Utility.getEntityCallName(entityName)+"Service.entityList;\n");
		sb.append("vm.selectedEntity="+Utility.getEntityCallName(entityName)+"Service.selectedEntity;\n");
		//sb.append("$scope.hidden="+entityName+"Service.hidden;\n");
		//sb.append("$scope.childrenList="+Utility.getEntityCallName(entityName)+"Service.childrenList; \n");
		bindChildrenList(sb, entity);

		//reset function
		sb.append("function reset()\n");
		sb.append("{\n");
		sb.append(""+Utility.getEntityCallName(entityName)+"Service.resetSearchBean();\n");
		sb.append("vm.searchBean="+entityName+"Service.searchBean;\n");
		sb.append(""+Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(null);\n");
		sb.append(""+Utility.getEntityCallName(entityName)+"Service.selectedEntity.show=false;\n");
		sb.append(""+Utility.getEntityCallName(entityName)+"Service.setEntityList(null); \n");
		sb.append("if ("+Utility.getEntityCallName(entityName)+"Service.isParent()) \n");
		sb.append("{\n");
		changeChildrenVisibility(sb, false);
		sb.append("}\n");

		sb.append("}\n");

		// add new function
		sb.append("function addNew()\n");
		sb.append("{\n");
		sb.append("$rootScope.openNode."+Utility.getEntityCallName(entityName)+"=true;\n");
		sb.append(""+Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(null);\n");
		sb.append(""+Utility.getEntityCallName(entityName)+"Service.setEntityList(null);\n");
		sb.append(""+Utility.getEntityCallName(entityName)+"Service.selectedEntity.show=true;\n");

		sb.append("if ("+Utility.getEntityCallName(entityName)+"Service.isParent()) \n");
		sb.append("{\n");
		changeChildrenVisibility(sb, false);
		sb.append("}\n");
		sb.append("//angular.element('#"+entityName+"Tabs li:eq(0) a').tab('show');\n");
		sb.append("}\n");
		sb.append("		\n");


		//search function
		sb.append("function search()\n");
		sb.append("{\n");
		sb.append(""+Utility.getEntityCallName(entityName)+"Service.selectedEntity.show=false;\n");
		sb.append("delete $rootScope.openNode."+entityName+";\n");
		sb.append("UtilityService.removeObjectFromList($rootScope.parentServices,"+entityName+"Service);\n");
		if (relationshipList!=null)
			for (Relationship relationship: relationshipList)
			{
				if (EntityAttributeManager.getInstance(relationship).isList() &&relationship.getEntityTarget().getEntityGroup()!=null)
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

		sb.append("}\n");

		//INSERT
		sb.append("function insert()\n");
		sb.append("{\n");
		sb.append("if (!$scope."+entityName+"DetailForm.$valid) return; \n");
		
		sb.append("$rootScope.parentServices.pop();\n");
		sb.append("if ($rootScope.parentServices.length==0) \n");
		sb.append("{\n");

		sb.append(Utility.getEntityCallName(entityName)+"Service.insert().then(function successCallback(response) { \n");
		sb.append("$log.debug(response);\n");
		sb.append("vm.search();\n");
		sb.append("},function errorCallback(response) { \n");
		manageRestError(sb);
		sb.append("});\n");
		sb.append("}\n");
		sb.append("else \n");
		sb.append("{\n");
		sb.append(Utility.getEntityCallName(entityName)+"Service.selectedEntity.show=false;\n");

		sb.append(Utility.getEntityCallName(entityName)+"Service.insert().then(function successCallBack(response) { \n");
		sb.append("$log.debug(response);\n");
		
		sb.append("$rootScope.parentServices.pop();\n");
		sb.append("var parentService=$rootScope.parentServices.pop();\n");
		sb.append("parentService.remove"+Utility.getEntityCallName(entityName)+"("+entityName+"Service.selectedEntity);\n");
		
		
		
		sb.append("},function errorCallback(response) { \n");
		manageRestError(sb);
		sb.append("});\n");
		sb.append("}\n");
		
		sb.append("}\n");
		
		
		
		
		//UPDATE
		sb.append("function update()\n");
		sb.append("{\n");
		sb.append("if (!$scope."+entityName+"DetailForm.$valid) return; \n");
		
		sb.append("$rootScope.parentServices.pop();\n");
		sb.append("if ($rootScope.parentServices.length==0) \n");
		sb.append("{\n");
		changeChildrenVisibility(sb, false);
		sb.append(Utility.getEntityCallName(entityName)+"Service.update().then(function successCallback(response) { \n");
		sb.append("$log.debug(response);\n");
		sb.append("vm.search();\n");
		sb.append("},function errorCallback(response) { \n");
		manageRestError(sb);
		sb.append("});\n");
		sb.append("}\n");
		sb.append("else \n");
		sb.append("{\n");
		sb.append(Utility.getEntityCallName(entityName)+"Service.selectedEntity.show=false;\n\n");
		sb.append(Utility.getEntityCallName(entityName)+"Service.update().then(function successCallback(response){\n");
		sb.append(Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(response.data);\n");
		sb.append("updateParentEntities();\n");
		sb.append("},function errorCallback(response) { \n");
		manageRestError(sb);
		sb.append("});\n");
		sb.append("}\n");

		sb.append("}\n");


		//REMOVE function
		sb.append("function remove()\n");
		sb.append("{\n");
		sb.append(Utility.getEntityCallName(entityName)+"Service.selectedEntity.show=false;\n");
		sb.append("delete $rootScope.openNode."+entityName+";\n");

		
		sb.append("$rootScope.parentServices.pop();\n");
		sb.append("var parentService=$rootScope.parentServices.pop();\n");
		sb.append("parentService.remove"+Utility.getFirstUpper(Utility.getEntityCallName(entityName))+"("+entityName+"Service.selectedEntity);\n");
		
		
		sb.append("UtilityService.removeObjectFromList($rootScope.parentServices,"+entityName+"Service);\n");
		sb.append(Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(null);\n");

		sb.append("}\n");


		//DELETE
		sb.append("function del()\n");
		sb.append("{\n");
		//sb.append("if (!"+Utility.getEntityCallName(entityName)+"Service.isParent()) \n");
		//sb.append("$scope.updateParent();\n");
		sb.append("$rootScope.parentServices.pop();\n");
		
		
		sb.append(Utility.getEntityCallName(entityName)+"Service.del().then(function successCallback(response) { \n");
		sb.append("$log.debug(response);\n");
		sb.append(Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(null);\n");
		sb.append("if ($rootScope.parentServices.length==0) \n");
		sb.append("{\n");

		sb.append("vm.search();\n");
		sb.append("} else { \n");
		sb.append("}\n");
		sb.append("},function errorCallback(response) { \n");
		manageRestError(sb);
		sb.append("});\n");
		sb.append("}\n");
		
		
		
		for (Tab tab: entityManager.getTabList())
		{
			sb.append("function refreshTable"+Utility.getFirstUpper(tab.getName().replaceAll(" ", ""))+"() \n");
			sb.append("{\n");
			sb.append(resetTableTab(tab.getName(),entity));
			sb.append("}\n");
			
			sb.append("vm.refreshTable"+Utility.getFirstUpper(tab.getName().replaceAll(" ", ""))+"=refreshTable"+Utility.getFirstUpper(tab.getName().replaceAll(" ", ""))+";\n");
		}


		//loadFile
		sb.append("function loadFile(file,field)\n");
		sb.append("{\n");
		sb.append(entityName+"Service.loadFile(file,field).then(function successCallback(response) {\n");
		sb.append(entityName+"Service.setSelectedEntity(response.data);\n");
		sb.append("},function errorCallback(response) { \n");
		manageRestError(sb);
		sb.append("});\n");
		sb.append("}\n");

		
		
		//true false values
		sb.append("vm.trueFalseValues=['',true,false];\n");
		
		
			for (Relationship relationship: relationshipList)
				if (relationship.getEntityTarget().getEntityGroup()!=null)
				{



					sb.append(" function show"+Utility.getFirstUpper(relationship.getEntityTarget().getName())+"Detail(index)\n");
					sb.append("{\n");
					sb.append("if (index!=null)\n");
					//sb.append(field.getName()+"Service.setSelectedEntity("+entityName+"Service.selectedEntity."+field.getName()+"List[index]);\n");
					sb.append("{\n");
					sb.append(relationship.getEntityTarget().getName()+"Service.searchOne("+entityName+"Service.selectedEntity."+relationship.getEntityTarget().getName()+"List[index]).then(\n");
					sb.append("function successCallback(response) {\n");
					sb.append("$log.debug(\"INDEX!=NULLLLLLLLLLLL\");\n");
					sb.append("$log.debug(response);\n");
					//initChildrenList(sb, relationship.getEntityTarget());
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
					//initChildrenList(sb, relationship.getEntityTarget());
					sb.append(Utility.getEntityCallName(relationship.getEntityTarget().getName())+"Service.setSelectedEntity(null); \n");
					sb.append(Utility.getEntityCallName(relationship.getEntityTarget().getName())+"Service.selectedEntity.show=true; \n");
					sb.append("$rootScope.openNode."+Utility.getEntityCallName(relationship.getEntityTarget().getName())+"=true;\n");
					//TODO set owner, list or entity?
					sb.append("}\n");
					sb.append("else\n");
					sb.append(Utility.getEntityCallName(relationship.getEntityTarget().getName())+"Service.searchOne("+entityName+"Service.selectedEntity."+relationship.getEntityTarget().getName()+").then(\n");

					sb.append("function successCallback(response) {\n");
					//sb.append("console.log(\"response-ok\");\n");
					//sb.append("console.log(response);\n");
					//initChildrenList(sb, relationship.getEntityTarget());
					sb.append(Utility.getEntityCallName(relationship.getEntityTarget().getName())+"Service.setSelectedEntity(response.data[0]);\n");
					sb.append(Utility.getEntityCallName(relationship.getEntityTarget().getName())+"Service.selectedEntity.show=true;\n");
					sb.append("$rootScope.openNode."+Utility.getEntityCallName(relationship.getEntityTarget().getName())+"=true;\n");
					sb.append("$rootScope.parentServices.push("+Utility.getEntityCallName(relationship.getEntityTarget().getName())+"Service);\n");
					sb.append("  }, function errorCallback(response) {\n");
					manageRestError(sb);
					sb.append("  }	\n");

					sb.append(");\n");


					sb.append("}\n");
					sb.append("//angular.element('#"+relationship.getEntityTarget().getName()+"Tabs li:eq(0) a').tab('show');\n");
					sb.append("}\n");

					
					sb.append("vm.show"+Utility.getFirstUpper(relationship.getEntityTarget().getName())+"Detail=show"+Utility.getFirstUpper(relationship.getEntityTarget().getName())+"Detail;\n");
					
				}


		//download entity list
		sb.append("function downloadList()\n");
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
		sb.append("UtilityService.alasql('SELECT "+exportFields+" INTO XLSXML(\""+entityName+".xls\",?) FROM ?',[mystyle,vm.entityList]);\n");
		sb.append("}\n");


		for (Relationship relationship: relationshipList)
		{
			if (EntityAttributeManager.getInstance(relationship).isList())
			{
				sb.append("function saveLinked"+Utility.getFirstUpper(relationship.getEntityTarget().getName())+"() {\n");
				sb.append(Utility.getEntityCallName(entityName)+"Service.selectedEntity."+relationship.getEntityTarget().getName()+"List.push("+Utility.getEntityCallName(entityName)+"Service.selectedEntity."+relationship.getEntityTarget().getName()+");\n");
				sb.append("}\n");
				
				sb.append("vm.saveLinked"+Utility.getFirstUpper(relationship.getEntityTarget().getName())+"=saveLinked"+Utility.getFirstUpper(relationship.getEntityTarget().getName())+";\n");
			}

			//downlaod children
			sb.append("function download"+Utility.getFirstUpper(relationship.getEntityTarget().getName())+"List()\n");
			sb.append("{\n");
			sb.append("var mystyle = {\n");
			sb.append(" headers:true, \n");
			sb.append("column: {style:{Font:{Bold:\"1\"}}}\n");
			sb.append("}\n");

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
			sb.append("UtilityService.alasql('SELECT "+exportFields+" INTO XLSXML(\""+relationship.getEntityTarget().getName()+".xls\",?) FROM ?',[mystyle,vm.selectedEntity."+relationship.getEntityTarget().getName()+"List]);\n");
			sb.append("}\n");
			
			sb.append("vm.download"+Utility.getFirstUpper(relationship.getEntityTarget().getName())+"List=download"+Utility.getFirstUpper(relationship.getEntityTarget().getName())+"List;\n");
		}

		//grid options
		sb.append("vm."+entityName+"GridOptions={};\n")
		.append("UtilityService.cloneObject("+entityName+"Service.gridOptions,vm."+entityName+"GridOptions);\n");
		sb.append("vm."+entityName+"GridOptions.data="+Utility.getEntityCallName(entityName)+"Service.entityList;\n");

		sb.append(getGridApi(null));//sb.append("}\n");	
		Entity mainEntity=entity;
		Boolean mainIsParent= false; //isParent;
		String mainParentName = entityName;//parentEntityName;
		EntityManager mainEntityManager= new EntityManagerImpl(mainEntity);
		String MainServiceList = serviceList;

		for (Relationship relationship: relationshipList)
			if (relationship.getEntityTarget().getEntityGroup()!=null)
			{
				init(relationship.getEntityTarget(), false, entityName,relationship.getRelationshipType(),mainEntityManager.isLastLevel(relationship.getEntityTarget()),MainServiceList);
				
				//children grid options
				sb.append("vm."+entityName+"GridOptions={};\n");
				sb.append("UtilityService.cloneObject("+entityName+"Service.gridOptions,vm."+entityName+"GridOptions);\n");
				sb.append("vm."+entityName+"GridOptions.data=vm.selectedEntity."+entityName+"List;\n");
				sb.append(getGridApi(mainParentName));
			}
		init(mainEntity,mainIsParent,mainParentName,relationshipType,entityManager.isLastLevel(mainEntity),serviceList);


		updateParentEntities(sb);

		//close entity detail
		sb.append("function closeEntityDetail(){ \n")
		.append(""+Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(null);\n")
		.append(""+Utility.getEntityCallName(entityName)+"Service.selectedEntity.show=false;\n")
		.append("delete $rootScope.openNode."+entityName+";\n")
		.append("UtilityService.removeObjectFromList($rootScope.parentServices,"+entityName+"Service);\n")
		.append("}\n");

		
		// public functions
		sb.append("vm.reset=reset;\n");
		sb.append("vm.addNew=addNew;\n");
		sb.append("vm.insert=insert;\n");
		sb.append("vm.update=update;\n");
		sb.append("vm.search=search;\n");
		sb.append("vm.remove=remove;\n");
		sb.append("vm.del=del;\n");
		sb.append("vm.loadFile=loadFile;\n");
		sb.append("vm.downloadList=downloadList;\n");
		sb.append("vm.closeEntityDetail=closeEntityDetail;\n");

		sb.append("}\n");
		return sb.toString();
	}
	/**
	 * Create the pagination option for ui-grid
	 * @return
	 */
	private String generateGridOptions()
	{
		StringBuilder sb = new StringBuilder();
		//pagination options
		sb.append("this.gridOptions = {\n");
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
			if ((EntityAttributeManager.getInstance(entityAttribute).getParent().getGenerationType()==GenerationType.HIDE_IGNORE && (EntityAttributeManager.getInstance(entityAttribute).getIgnoreTableList()) )) 
				continue;
			
			if ((EntityAttributeManager.getInstance(entityAttribute).getParent().getGenerationType()==GenerationType.SHOW_INCLUDE && !EntityAttributeManager.getInstance(entityAttribute).getIncludeTableList()  && !EntityAttributeManager.getInstance(entityAttribute).getIncludeSearch() ) ) 
				continue;
			
			
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

		/*if (parentEntityName==null)
			sb.append(",data: "+Utility.getEntityCallName(entityName)+"Service.entityList\n");
		else
			sb.append(",data: $scope.selectedEntity."+entityName+"List\n");
		*/
		
		sb.append(" };\n");

		return sb.toString();
	}
	
	
	private String getGridApi(String parentEntityName)
	{
		StringBuilder sb = new StringBuilder();
				//if (parentEntityName!=null && parentEntityName.equals("entity"))
		//	System.out.println("**tento di generare** "+entityName+" - parent "+parentEntityName+" - lastLevel"+lastLevel+"- isParent "+isParent);
			
	//	if (lastLevel ) 
	//		return sb.toString();
		//if (parentEntityName!=null && parentEntityName.equals("entity"))
		//System.out.println("**GENERO** "+entityName+" - parent "+parentEntityName+" - lastLevel"+lastLevel+"- isParent "+isParent);
		//on row selection
		
		sb.append("vm.initChildrenList = function () { \n");
		//initChildrenList(sb, entity);
		sb.append("}\n");
		
		sb.append("vm."+entityName+"GridOptions.onRegisterApi = function(gridApi){\n");
		sb.append("vm."+entityName+"GridApi = gridApi;\n");
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
		//sb.append("$scope.initChildrenList();");
		//initChildrenList(sb, entity);
if (entity.getEntityGroup()!=null)
{
			sb.append(Utility.getEntityCallName(entityName)+"Service.searchOne(row.entity).then(function(response) { \n");
			sb.append("$log.debug(response.data);\n");
			sb.append("$rootScope.openNode."+entityName+"=true;\n");
			sb.append("$rootScope.parentServices.push("+Utility.getEntityCallName(entityName)+"Service);\n");
			sb.append(Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(response.data[0]);\n");
			sb.append("});\n");
}
			//}
			sb.append("//angular.element('#"+entityName+"Tabs li:eq(0) a').tab('show');\n");
			sb.append("}\n");
			sb.append("else \n");
			sb.append(Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(null);\n");
			sb.append("delete $rootScope.openNode."+entityName+";\n");
			sb.append("UtilityService.removeObjectFromList($rootScope.parentServices,"+entityName+"Service);\n");
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
		services=services+","+entityName+"Service, SecurityService, MainService ";
		if (descendantEntityList!=null)
		for (Entity descendantEntity : descendantEntityList)
			if (descendantEntity.getEntityGroup()!=null)
		{
			//services=services+","+descendantEntity.getName()+"Service";

		}
		if (entity.getRelationshipList()!=null)
		for (Relationship relationship : entity.getRelationshipList())
			if (relationship.getEntityTarget().getEntityGroup()!=null)
		{
			services=services+","+relationship.getEntityTarget().getName()+"Service";
		}
		return services;
	}
	
	private String getallServices(){
		String str="";
		for (Entity entity: generator.getEntityList())
		{
			str=str+","+Utility.getFirstLower(entity.getName())+"Service";
		}
		return str;
	}
	
	private String checkSecurity(Entity entity,String action)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("if ($rootScope.restrictionList."+entity.getEntityGroup().getName()+"!=undefined && $rootScope.restrictionList."+entity.getEntityGroup().getName()+".restrictionItemMap."+entity.getName()+".can"+Utility.getFirstUpper(action)+")\n");
		return sb.toString();
	}
	
	
	private void bindChildrenList(StringBuilder sb, Entity entity)
	{
		if (entity.getRelationshipList()!=null)
			for (Relationship relationship: entity.getRelationshipList())
			if (relationship.getEntityTarget().getEntityGroup()!=null)
			{
				sb.append("vm."+relationship.getEntityTarget().getName()+"PreparedData="+relationship.getEntityTarget().getName()+"Service.preparedData;\n");
			}
		
		if (entity.getEnumFieldList()!=null)
			for (EnumField enumField: entity.getEnumFieldList())
			{
				sb.append("vm."+enumField.getName()+"PreparedData={};\n");
				sb.append("vm."+enumField.getName()+"PreparedData.entityList=[");
				for (EnumValue enumValue: enumField.getEnumEntity().getEnumValueList())
				{
					sb.append("\""+enumValue.getName()+"\",");
				}
				sb.setCharAt(sb.length()-1, ' ');
				sb.append("];\n");

			}
	}
	
	
	private void initChildrenList(StringBuilder sb,Entity entity,Boolean test)
	{
		if (entity.getRelationshipList()!=null)
			for (Relationship relationship: entity.getRelationshipList())
			{

				sb.append(checkSecurity(relationship.getEntityTarget(), "search"));
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
	
	private void generateMainService()
	{

		StringBuilder sb = new StringBuilder();	
		
		sb.append("(function() { \n")
		.append("'use strict'; \n")
		.append("\n");
		
		
		sb.append("angular.module(\""+Generator.appName+"\").service(\"MainService\", MainService);\n");
		
		sb.append("/** @ngInject */\n");
		sb.append("function MainService()\n");
		sb.append("{\n");
		sb.append("this.parentEntity=\"\";\n");
		sb.append(" this.parentService=null; \n");
		sb.append("}\n");
		
		sb.append("})();\n");
		
		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+Generator.generateAngularDirectory+"main/";
		saveAsJsFile(directoryAngularFiles, "main.service", sb.toString());

	
	}
	
	private void generateMainController()
	{
		StringBuilder sb = new StringBuilder();	
		
		sb.append("(function() { \n")
		.append("'use strict'; \n")
		.append("\n");
		
		sb.append("angular.module(\""+Generator.appName+"\").controller(\"MainController\",MainController);\n\n");
		
		
		sb.append("/** @ngInject */\n");
		sb.append("function MainController(){\n");
		sb.append("}\n");
		sb.append("})();\n");
		
		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+Generator.generateAngularDirectory+"main/";
		saveAsJsFile(directoryAngularFiles, "main.controller", sb.toString());

	}
	
	
	
	private void generateSecurityService()
	{
		StringBuilder sb = new StringBuilder();	

		sb.append("(function() { \n")
		.append("'use strict'; \n")
		.append("\n");


		sb.append("angular.module(\""+Generator.appName+"\").service(\"SecurityService\",SecurityService);\n");

		sb.append("/** @ngInject */\n");
		sb.append("function SecurityService($http,$log)\n");
		sb.append("{\n");
		sb.append("this.restrictionList={};\n");

		if (Generator.enableSecurity)
		{
			sb.append("this.init= function() {\n");
			sb.append("var promise= $http.get(\""+Generator.restUrlProperty+"authentication/\");\n");
			sb.append("return promise; \n");
			sb.append("};\n");
		}

		sb.append("this.isLoggedIn = function()\n")
		.append("{\n")
		.append("var promise= $http.post(\""+Generator.restUrlProperty+"authentication/username/\");\n")
		.append("return promise;\n")
		.append("}\n")

		.append("function serializeObj(obj) {\n")
		.append("var result = [];\n")

		.append("for (var property in obj)\n")
		.append("result.push(encodeURIComponent(property) + \"=\" + encodeURIComponent(obj[property]));\n")

		.append("return result.join(\"&\");\n")
		.append("}\n")

		.append("this.login= function(username,password)\n")
		.append("{\n")
		.append("var credentials={};\n")
		.append("credentials.username=username;\n")
		.append("credentials.password=password;\n")
		.append("credentials=serializeObj(credentials);\n")
		.append("$log.debug(credentials);\n")
		.append("var promise=$http.post(\""+Generator.restUrlProperty+"auth/authenticate/\",credentials,{ headers: {'Content-Type': 'application/x-www-form-urlencoded' }});\n")
		.append("return promise;\n")
		.append("}\n");




		sb.append("}\n");
		sb.append("})();\n");




		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+Generator.generateAngularDirectory+"security/";
		saveAsJsFile(directoryAngularFiles, "security.service", sb.toString());
	}
	
	
	private void generateIndexConfig()
	{

		StringBuilder sb = new StringBuilder();
		sb.append("(function() {\n")
		.append("  'use strict'\n")
		.append("\n")
		.append("  angular\n")
		.append("    .module('"+Generator.appName+"')\n")
		.append("    .config(config)\n")
		.append("    .config(setHttpProvider);\n")
		.append("\n")
		.append("  /** @ngInject */\n");
		sb.append("function config($logProvider, toastrConfig) {\n")
		.append("// Enable log\n")
		.append(" $logProvider.debugEnabled(true);\n")
		.append(" // Set options third-party lib\n")
		.append(" toastrConfig.allowHtml = true;\n")
		.append(" toastrConfig.timeOut = 3000;\n")
		.append("toastrConfig.positionClass = 'toast-top-right';\n")
		.append(" toastrConfig.preventDuplicates = true;\n")
		.append("toastrConfig.progressBar = true;\n")
		.append("}\n")
		.append("\n")
		.append("/** @ngInject */\n")
		.append("function setHttpProvider($httpProvider) {\n")
		  
		.append("$httpProvider.defaults.headers.common.Accept = 'application/json';\n")
		.append(" $httpProvider.defaults.withCredentials = true;\n")
		.append("}\n");
		
		sb.append("})();\n");
		
		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+Generator.generateAngularDirectory+"../";
		saveAsJsFile(directoryAngularFiles, "index.config", sb.toString());
		
	
	}
	
	
	private void generateIndexConstants()
	{

		StringBuilder sb = new StringBuilder();
		sb.append("(function() {\n")
		.append("  'use strict'\n")
		.append("\n")
		.append("  angular\n")
		.append("    .module('"+Generator.appName+"');\n")
		.append("\n");
		
		
		sb.append("})();\n");
		
		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+Generator.generateAngularDirectory+"../";
		saveAsJsFile(directoryAngularFiles, "index.constants", sb.toString());
		
	
	}
	
	
	private void generateIndexModule()
	{

		StringBuilder sb = new StringBuilder();
		sb.append("(function() {\n")
		.append("  'use strict'\n")
		.append("\n")
		.append("  angular\n")
		.append("    .module('"+Generator.appName+"',['ngAnimate', 'ngCookies', 'ngTouch', 'ngSanitize',"
				+ " 'ngMessages', 'ngAria', 'ngResource','ui.router',"
				+ "'ui.bootstrap', 'toastr','ui.grid', 'ui.grid.pagination','ui.grid.selection',"
				+ "'ui.date', 'ui.grid.exporter','ngFileUpload','swaggerUi']);\n")
		.append("\n");
		
		sb.append("})();\n");
		
		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+Generator.generateAngularDirectory+"../";
		saveAsJsFile(directoryAngularFiles, "index.module", sb.toString());
		
	
	}
	
	private void generateIndexRoute()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("(function() { \n")
		.append("'use strict'; \n")
		.append("\n");
		
		
		sb.append("angular\n.module(\""+Generator.appName+"\").config(routerConfig);\n");
		
		sb.append("/** @ngInject */\n");
		sb.append("function routerConfig($stateProvider,$urlRouterProvider)\n");
		
		sb.append("{\n");
		sb.append("$stateProvider\n")
		.append(".state('main',{\n")
		
		.append(" url:'/app',\n")
		.append("abstract:true,\n")
		.append("templateUrl:'app/main/main.html',\n")
		
		.append(" controller:'MainController', \n")
		.append("controllerAs: 'main' ,\n")
		.append("name: 'main'\n") 
		.append(" })\n")
		
		
		.append(".state('main.home',{\n")
		.append(" url:'/home',\n")
		.append("views:{\n")
		.append("'search': {\n")
		.append("templateUrl:'app/components/home/home.html',\n")
		.append(" controller:'HomeController', \n")
		.append("controllerAs: 'vm' \n")
		.append(" }\n")
		.append("}\n")
		.append("})\n")

		
		.append(".state('main.metrics',{\n")
		.append(" url:'/metrics',\n")
		.append("views:{\n")
		.append("'pageContent': {\n")
		.append("templateUrl:'app/components/metrics/metrics.html',\n")
		.append(" controller:'MetricsController', \n")
		.append("controllerAs: 'vm' \n")
		.append(" }\n")
		.append("}\n")
		.append("})\n")
		
		.append(".state('main.swagger',{\n")
		
		.append(" url:'/swagger',\n")
		.append("views:{\n")
		.append("'pageContent': {\n")
		.append("templateUrl:'app/components/swagger/swagger.html',\n")
		.append(" controller:'SwaggerController', \n")
		.append("controllerAs: 'vm' \n")
		.append(" }\n")
		.append("}\n")
		.append("})\n");
		
		for (Entity entity: generator.getEntityList())
		{
			sb.append(".state('main."+entity.getName()+"',{\n")
			
			.append(" url:'/"+entity.getName()+"',\n")
			.append("views:{\n")
			.append("'pageContent': {\n")
			.append("templateUrl:'app/controller/"+entity.getName()+"/"+entity.getName()+"-template.html',\n")
			.append(" controller:'HomeController', \n")
			.append("controllerAs: 'vm' \n")
			.append(" }\n")
			.append("},\n")
			.append("resolve: {\n");
			/*.append("setParent: function(MainService,"+Utility.getFirstLower(entity.getName())+"Service){\n")

			.append("if (MainService.parentService!=null)\n")
			.append("{\n")

			.append("MainService.parentService.resetSearchBean();\n")
			.append("MainService.parentService.setSelectedEntity(null);\n")
			.append("MainService.parentService.selectedEntity.show=false;\n")
			.append("MainService.parentService.setEntityList(null);\n") 
			.append("}\n")

			.append("MainService.parentEntity=\""+Utility.getFirstUpper(entity.getName())+"\";\n")

			.append("MainService.parentService="+Utility.getFirstLower(entity.getName())+"Service;\n");
			*/
			//todo get descendant e init children
			for (Relationship relationship : entity.getRelationshipList())
			{

				/*sb.append(relationship.getName()+"ChildrenList: function("+Utility.getFirstLower(entity.getName())+"Service) {\n");
				sb.append(Utility.getFirstLower(entity.getName())+"Service.init"+Utility.getFirstUpper(relationship.getEntityTarget().getName())+"List().then(function(response) {\n")
				.append(Utility.getFirstLower(entity.getName())+"Service.childrenList."+Utility.getFirstLower(relationship.getName())+"List=response.data;\n")
				.append("});\n")
				
				.append("}, \n ");*/
			}
			
			
			
			
			sb.append("}\n") // end resolve
			
			
			.append("})\n");
		}
		
		sb.append(";");
		sb.append("$urlRouterProvider.otherwise('/app/home');\n");
		
		sb.append("}\n");
		sb.append("})();\n");
		
		
		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+Generator.generateAngularDirectory+"../";
		saveAsJsFile(directoryAngularFiles, "index.route", sb.toString());
	}
	
	
	private void generateIndexRun()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("(function() {\n")
		.append("  'use strict'\n")
		.append("\n")
		.append("  angular\n")
		.append("    .module('"+Generator.appName+"')\n")
		.append("    .run(runBlock);\n")
		.append("\n")
		.append("  /** @ngInject */\n")
		.append("  function runBlock($log,SecurityService,UtilityService,$rootScope,$uibModal"+getallServices()+") { \n");
		
		String services = serviceList;
		if (services==null)
			services="";
		//sb.append(".run(function($rootScope,SecurityService"+services+"){\n");

		if (Generator.enableSecurity)
		{
			
			//sb.append("$rootScope.restrictionList=response.data;\n");
		} else
		{
			//sb.append("SecurityService.restrictionList={};\n");
			//sb.append("$rootScope.restrictionList={};\n");
		}


		
		sb.append("var deregistrationsCallbacks=[];\n")



		.append("deregistrationsCallbacks[0] = $rootScope.$on('security:loginRequired', function() {\n")
		.append("showLogin();\n")
		.append(" }); \n")

		.append("var loginWindow;\n")
		.append("function showLogin(){\n")
		.append("loginWindow = $uibModal.open({\n")
		.append("size:'md',\n")
		.append("animation: true,\n")
		.append(" templateUrl:'app/components/login/login-modal.html',\n")
		.append(" backdrop: 'static',\n")
		.append(" keyboard: false\n")
		.append("});\n")
		.append("function close(){\n")
		.append("initList();\n")
		.append("SecurityService.init().then(function successCallback(response) {\n")
		.append("$rootScope.restrictionList=response.data;\n")
		.append("});\n")
		
		.append("if(loginWindow){\n")
		.append("loginWindow.dismiss();\n")
		.append("loginWindow = null;\n")
		.append("}\n")
		.append("}           \n")
		.append("deregistrationsCallbacks[1] = $rootScope.$on('security:loggedIn',close);\n")

		            
		.append("}\n")

		.append("//deregistration of events on $destroy\n")
		.append("deregistrationsCallbacks[2] = $rootScope.$on('$destroy', function() {\n")
		.append(" $log.debug(\"deregistering global events\");\n")
		.append("for(var i=0;i<deregistrationsCallbacks.length;i++){\n")
		.append("  deregistrationsCallbacks[i]();\n")
		.append(" }\n")
		.append(" });\n")

		.append("$log.debug(\"check login\");\n")
		.append("SecurityService.isLoggedIn().then(function successCallback(response) {\n")
		.append("if (!response.data.authenticated)\n")
		.append("{\n")
		.append("$rootScope.$broadcast('security:loginRequired');\n")
		.append("}\n")
		.append("else\n")
		.append("{\n")
		.append("initList();\n")
		.append("$log.debug(\"loggato come \");\n")
		.append("$log.debug(response.data.message);\n")

		.append("SecurityService.init().then(function successCallback(response) {\n")
		.append("$rootScope.restrictionList=response.data;\n")
		.append("});\n")


		.append("}\n")

		.append("},function errorCallback(response) { \n");
		manageRestError(sb);
			sb.append("});\n");
		
		

		sb.append("$rootScope.openNode= {};\n");
		sb.append("$rootScope.parentServices=[];\n");

		
		/*  init all the list - heavy? */
		sb.append("function initList() {\n");
		sb.append("$log.debug(\"inizio init\");\n");
		for (Entity entity: generator.getEntityList())
		{
			sb.append(""+Utility.getFirstLower(entity.getName())+"Service.searchBean={};\n");
			sb.append(""+Utility.getFirstLower(entity.getName())+"Service.search().then(function successCallback(response) {\n");
			sb.append(""+Utility.getFirstLower(entity.getName())+"Service.preparedData.entityList=response.data;\n");
			sb.append("},function errorCallback(response) { \n");
				manageRestError(sb);
			sb.append("});\n");
		}
		sb.append("$log.debug(\"fine init\");\n");
		sb.append("}\n");

		sb.append("$log.debug('runBlock end');\n");

		sb.append("}\n"); 
		
		sb.append("})();\n");
		
		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+Generator.generateAngularDirectory+"../";
		saveAsJsFile(directoryAngularFiles, "index.run", sb.toString());
		
	}
	
	
	private void generateHomeController()
	{

		StringBuilder sb = new StringBuilder();
		sb.append("(function() {\n")
		.append("  'use strict'\n")
		.append("\n")
		.append("  angular\n")

		.append(" .module('"+Generator.appName+"')\n")
		.append(" .controller('HomeController', HomeController);\n")

		.append("  /** @ngInject */\n")
		.append(" function HomeController($element,$timeout,SecurityService,$log,$rootScope,$resource) {\n")
		.append(" var vm = this;\n")

		.append(" function checkUsername()\n")
		.append(" {\n")
		.append("var Username= $resource(\""+Generator.restUrlProperty+"authentication/username/\");\n")
		.append("Username.save({},function(data){\n")
		.append("         $log.debug(data);\n")
		.append("     });\n")
		.append(" }\n")


		.append(" vm.checkUsername=checkUsername;\n")
		.append("  }\n");


		sb.append("})();\n");

		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+Generator.generateAngularDirectory+"home/";
		saveAsJsFile(directoryAngularFiles, "home.controller", sb.toString());


	}
	
	
	public void generateUtilityService()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("(function() { \n")
		.append("'use strict'; \n")
		.append("\n");
		
		
		sb.append("angular.module(\""+Generator.appName+"\").service(\"UtilityService\", UtilityService);\n");
		
		sb.append("/** @ngInject */\n");
		sb.append("function UtilityService()\n");
		sb.append("{\n");
		
	/*	sb.append("function loadMenu()\n");
		sb.append("{\n");
		sb.append("var content = document.querySelector('link[rel=\"import\"]').import; \n");
		sb.append(" $(\"body\").prepend(content.documentElement.getElementsByTagName(\"body\")[0]);\n");
		sb.append("}\n");
		sb.append("function activeMenu(path)\n");
		sb.append("{\n");
		sb.append("	$(\"a\").parent(\"li\").removeClass(\"active\");\n");
		sb.append("	$(\"a[href='../\"+path+\"/']\").parent(\"li\").addClass(\"active\");\n");
		sb.append("	if ($(\"a[href='../\"+path+\"/']\").parent(\"li\").parent(\"ul\").parent(\"li\")[0]!=undefined)\n");
		sb.append("		$(\"a[href='../\"+path+\"/']\").parent(\"li\").parent(\"ul\").parent(\"li\").addClass(\"active\");\n");


		sb.append("}\n");*/

		
		sb.append("this.alasql=alasql;\n");
		
		//alert success
		sb.append("this.AlertSuccess = (function() {\n");
		sb.append("   \"use strict\";\n");

		sb.append("   var elem,\n");
		sb.append("       hideHandler,\n");
		sb.append("       that = {};\n");

		sb.append("    that.init = function(options) {\n");
		sb.append("        elem = angular.element(options.selector);\n");
		sb.append("    };\n");

		sb.append("    that.show = function(text) {\n");
		sb.append("        clearTimeout(hideHandler);\n");

		sb.append("        elem.find(\"span\").html(text);\n");
		sb.append("        elem.delay(200).fadeIn().delay(2000).fadeOut();\n");
		sb.append("   };\n");

		sb.append("    return that;\n");
		sb.append("}());\n");

		//Alert error
		sb.append("this.AlertError = (function() {\n");
		sb.append("  \"use strict\";\n");

		sb.append("  var elem,\n");
		sb.append("     hideHandler,\n");
		sb.append("     that = {};\n");

		sb.append("  that.init = function(options) {\n");
		sb.append("      elem = angular.element(options.selector);\n");
		sb.append("  };\n");

		sb.append("that.show = function(text) {\n");
		sb.append("   clearTimeout(hideHandler);\n");

		sb.append("     elem.find(\"span\").html(text);\n");
		sb.append("    elem.delay(200).fadeIn().delay(2000).fadeOut();\n");
		sb.append(" };\n");

		sb.append("  return that;\n");
		sb.append("}());\n");

		//clone object
		sb.append("this.cloneObject=function(sourceObject,targetObject)\n");
		sb.append("{\n");

		sb.append("var keyList = Object.keys(sourceObject);\n");
		sb.append("if (keyList.length == 0)\n");
		sb.append("	keyList = Object.keys(targetObject);\n");
		sb.append("for (var i = 0; i < keyList.length; i++) {\n");
		sb.append("var val = keyList[i];\n");
		sb.append("if (val != undefined) {\n");
		sb.append("if (val.toLowerCase().indexOf(\"list\") > -1\n");
		sb.append("	&& (typeof sourceObject[val] == \"object\" || typeof targetObject[val]==\"object\")) {\n");
		sb.append("if (sourceObject[val] != null\n");
		sb.append("	&& sourceObject[val] != undefined) {\n");
		sb.append("if (targetObject[val]!=undefined)\n");
		sb.append("	while (targetObject[val].length > 0)\n");
		sb.append("		targetObject[val].pop();\n");
		sb.append("if (sourceObject[val] != null)\n");
		sb.append("		for (var j = 0; j < sourceObject[val].length; j++)\n");
		sb.append("				targetObject[val]\n");
		sb.append("			.push(sourceObject[val][j]);\n");
		sb.append("	} else \n");
		sb.append("			this.emptyList(targetObject[val]);\n");
		sb.append("	} else {\n");
		sb.append("		if (val.toLowerCase().indexOf(\"time\") > -1\n");
		sb.append("				&& typeof val == \"string\") {\n");
		//sb.append("			var date = new Date(sourceObject[val]);\n");
		sb.append("			targetObject[val] = new Date(sourceObject[val]);\n");
		sb.append("		} else {\n");
		sb.append("			targetObject[val] = sourceObject[val];\n");
		sb.append("		}\n");
		sb.append("	}\n");
		sb.append("}\n");
		sb.append("}\n");

		sb.append("}\n");
		//empty list
		sb.append("this.emptyList=function(list)\n");
		sb.append("{\n");
		sb.append("if (list!=undefined) \n");
		sb.append("	while (list.length>0)\n");
		sb.append("		list.pop();\n");
		sb.append("}\n");

		//remove object from list
		sb.append("this.removeObjectFromList=function(list,obj)\n");
		sb.append("{\n");
		sb.append("	for (var i=0; i<list.length; i++)\n");
		sb.append("	{\n");
		sb.append("	if (list[i]==obj) \n");
		sb.append("	list.splice(i,i+1); \n");
		sb.append("	}\n");
		sb.append("}\n");



		sb.append("}\n");
		
		sb.append("})();\n"); // end of service
		
		File file = new File("");
		String directory= file.getAbsolutePath()+Generator.generateAngularDirectory+"utility/";
		saveAsJsFile(directory, "utility.service", sb.toString());
	}
	
	
	public void generateNavbarDirective()
	{

		StringBuilder sb = new StringBuilder();
		sb.append("(function() {\n")
		.append("  'use strict'\n")
		.append("\n")
		.append("  angular\n")
		.append(".module('"+Generator.appName+"')\n")
		.append(".directive('"+Generator.appName+"Navbar', "+Generator.appName+"Navbar);\n")

		.append("/** @ngInject */\n")
		.append("function "+Generator.appName+"Navbar() {\n")
		.append(" var directive = {\n")
		.append("    restrict: 'E',\n")
		.append("    templateUrl: 'app/components/navbar/navbar.html',\n")
		.append("    scope: {\n")
		.append("         creationDate: '='\n")
		.append("    },\n")
		.append("     controller: NavbarController,\n")
		.append("     controllerAs: 'vm',\n")
		.append("     bindToController: true\n")
		.append("   };\n")

		.append("   return directive;\n")

		.append("  /** @ngInject */\n")
		.append("  function NavbarController($scope,$http,$log) {\n")
		.append("    var vm = this;\n")
		.append("  function doLogout()\n")
		.append("  {\n")
		.append("  $http.post(\""+Generator.restUrlProperty+"auth/logout/\").then(function(response)\n")
		.append("{\n")
		.append("$log.debug(response);\n")
		.append("		$log.debug(\"logout\");\n")
		.append("  });\n")


		.append("  }\n")
		.append("  vm.doLogout=doLogout;\n")



		.append(" }\n")
		.append(" }\n")

		.append("})();\n");




		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+Generator.generateAngularDirectory+"navbar/";
		saveAsJsFile(directoryAngularFiles, "navbar.directive", sb.toString());


	}
	
	
	
	public void generateLoginDirective()
	{
		StringBuilder sb = new StringBuilder();	

		sb.append("(function() { \n")
		.append("'use strict'; \n")
		.append("\n");

		sb.append("angular.module(\""+Generator.appName+"\").directive(\"login\",login);\n\n");


		sb.append("/** @ngInject */\n");
		sb.append("function login(){\n");


		sb.append("  var directive = {\n");
		sb.append("    restrict: 'EA',\n");
		sb.append("   templateUrl:'app/components/login/login.html',\n");
		sb.append("   controller: LoginController,\n");
		sb.append("  controllerAs: 'vm'\n");
		sb.append("};\n");

		sb.append(" return directive;\n");

		sb.append("  /** @ngInject */\n");
		sb.append("  function LoginController($scope,SecurityService,UtilityService,$rootScope,$log) {\n");
		sb.append("   var vm = this;\n");

		sb.append("   function doLogin(username,password){\n");
		sb.append("     SecurityService.login(username,password).then(function successCallback(response) {\n");
		sb.append("				if (response.data.authenticated)\n");
		sb.append("				{\n");
		sb.append("					$log.debug(\"login ok, chiudo\");\n");
		sb.append("					$rootScope.$broadcast('security:loggedIn');\n");
		sb.append("				} \n");
		sb.append("			},function errorCallback(response) { \n");
		sb.append("				$log.debug(\"errore callback\");\n");
		sb.append("				$log.debug(response);\n");
		sb.append("			});\n");
		sb.append("}\n");

		sb.append("function checkUsername()\n");
		sb.append("{\n");

		sb.append("	SecurityService.isLoggedIn(vm).then(function successCallback(response) {\n");
		sb.append("	if (!response.data.authenticated)\n");
		sb.append("	{\n");
		sb.append("		$rootScope.$broadcast('security:loginRequired');\n");
		sb.append("		}\n");
		sb.append("else\n");
		sb.append("{\n");
		sb.append("$rootScope.$broadcast('security:loggedIn');\n");
		sb.append("	}\n");

		sb.append("},function errorCallback(response) { \n");
		manageRestError(sb);
		sb.append("});\n");

		sb.append("}\n");
		sb.append(" vm.onSubmit = doLogin;\n");
		sb.append("vm.checkUsername=checkUsername;\n");

		sb.append(" }\n");




		sb.append("}\n");
		sb.append("})();\n");

		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+Generator.htmlDirectoryProperty+"login/";
		saveAsJsFile(directoryAngularFiles, "login.directive", sb.toString());

	}
	
	
	
	/**
	 * Create the JS string
	 * @return
	 */
	private String buildJS()
	{
		StringBuilder buildJS= new StringBuilder();
		buildJS.append("angular.module(\""+entityName+"\",['ngFileUpload','ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])\n");
		//JsGenerator jsGenerator = new JsGenerator(entity, true,null,null);
		generateSecurityService();
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
		String MainServiceList = serviceList;
		for (Relationship descendantRelationship : descendantRelationshipSet)
		{
			init(descendantRelationship.getEntityTarget(),false,entityName,descendantRelationship.getRelationshipType(),mainEntityManager.isLastLevel(descendantRelationship.getEntityTarget()),MainServiceList);
			buildJS.append(generateService());
			buildJS.append(generateController());
			
			
		}
		buildJS.append(";");
		return buildJS.toString();
	}



	private void manageRestError(StringBuilder sb)
	{
		sb.append("UtilityService.AlertError.init({selector: \"#alertError\"});\n");
		sb.append("UtilityService.AlertError.show(\"Si è verificato un errore\");\n");
		sb.append("$log.debug(response);\n");
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
	
	private void updateParentEntities(StringBuilder sb)
	{
		
		sb.append("function updateParentEntities() { \n");
		
		for (Relationship relationship : generator.getRelationshipList())
		if (relationship.getEntityTarget().getEntityId()==entity.getEntityId())
			if (relationship.getEntity()!=null)
		{
			String entitySource = Utility.getFirstLower(relationship.getEntity().getName());
			String entityTarget = Utility.getFirstLower(relationship.getEntityTarget().getName());
			
			//update childrenEntityList
			/*
			 * MainService.parentService.initRoleList().then(function(response) {
MainService.parentService.childrenList.roleList=response.data;
});
			 */
			sb.append(entitySource+"Service.init"+Utility.getFirstUpper(entityTarget)+"List().then(function(response) {\n");
			sb.append(Utility.getFirstLower(entityTarget)+"Service.preparedData.entityList=response.data;\n");
			sb.append("});\n");
			
			sb.append("\n");
			
			
			sb.append("if ("+entitySource+"Service.selectedEntity."+entitySource+"Id!=undefined) ");
			sb.append(entitySource+"Service.searchOne("+entitySource+"Service.selectedEntity).then(\n");
			sb.append("function successCallback(response) {\n");
			sb.append("$log.debug(\"response-ok\");\n");
			sb.append("$log.debug(response);\n");
			//initChildrenList(sb, relationship.getEntityTarget());
			sb.append(entitySource+"Service.setSelectedEntity(response.data[0]);\n");
			//sb.append(Utility.getEntityCallName(relationship.getEntityTarget().getName())+"Service.selectedEntity.show=true;\n");

			sb.append("  }, function errorCallback(response) {\n");
			// called asynchronously if an error occurs
			// or server returns response with an error status.
			//sb.append(" console.log(\"response-error-controller\");\n");
			//sb.append("console.log(response);\n");
			manageRestError(sb);
			sb.append("  }	\n");

			sb.append(");\n");
			
			
			if (relationship.getRelationshipType()==RelationshipType.MANY_TO_ONE || relationship.getRelationshipType()==RelationshipType.ONE_TO_ONE)
			{ //update single entity
				//sb.append(entitySource+"Service.selectedEntity="+entityTarget+"Service.selectedEntity;\n");
			} else
			{//update list
				
			}
		}
		
		sb.append("}\n");
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

		sb.append("angular.element('a[href=\"#"+entityName+"-"+tabName+"\"]').on('shown.bs.tab', function (e) {\n");
		sb.append("var target = angular.element(e.target).attr(\"href\"); // activated tab\n");
		sb.append("//$log.debug(target);\n");
		sb.append("if (angular.element('#"+entityName+"Tabs').scope()!=null && angular.element(('#"+entityName+"Tabs')).scope()!=undefined) \n");
		//TODO FIX
		sb.append("{ /* angular.element(('#"+entityName+"Tabs')).scope().refreshTable"+Utility.getFirstUpper(tabName.replaceAll(" ",""))+"(); */ }\n");
		sb.append("});\n");

		return sb.toString();
	}
	
	public void generateBowerFile()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		sb.append(" \"name\": \""+Generator.appName+"\",\n");
		sb.append("\"version\": \"0.0.0\",\n");
		sb.append("\"dependencies\": {\n");
		sb.append("\"angular-animate\": \"~1.5.3\",\n");
		sb.append("\"angular-cookies\": \"~1.5.3\",\n");
		sb.append("\"angular-touch\": \"~1.5.3\",\n");
		sb.append("\"angular-sanitize\": \"~1.5.3\",\n");
		sb.append("\"angular-messages\": \"~1.5.3\",\n");
		sb.append("\"angular-aria\": \"~1.5.3\",\n");
		sb.append(" \"jquery\": \"~2.1.4\",\n");
		sb.append("\"angular-resource\": \"~1.5.3\",\n");
		sb.append("\"angular-ui-router\": \"~0.2.15\",\n");
		sb.append("\"bootstrap-sass\": \"~3.3.5\",\n");
		sb.append("\"angular-bootstrap\": \"~1.2.5\",\n");
		sb.append(" \"angular-toastr\": \"~1.5.0\",\n");
		sb.append("\"moment\": \"~2.10.6\",\n");
		sb.append("\"animate.css\": \"~3.4.0\",\n");
		sb.append(" \"angular\": \"~1.5.3\",\n");
		sb.append(" \"ng-file-upload\": \"^12.0.4\",\n");
		sb.append(" \"angular-ui-grid\": \"^3.1.1\",\n");
		sb.append("  \"angular-ui-date\": \"^1.0.0\",\n");
		sb.append("  \"bootstrap\": \"^3.3.6\",\n");
		sb.append("  \"main\": \"./src/app/components/customLib/main.css\",\n");
		sb.append("\"alasql\": \"^0.2.5\",\n");
		sb.append("\"angular-swagger-ui\": \"^0.3.1\"\n");
		sb.append(" },\n");
		sb.append("  \"devDependencies\": {\n");
		sb.append(" \"angular-mocks\": \"~1.5.3\"\n");
		sb.append(" },\n");
		sb.append("\"overrides\": {\n");
		sb.append("\"bootstrap-sass\": {\n");
		sb.append(" \"main\": [\n");
		sb.append("  \"assets/stylesheets/_bootstrap.scss\",\n");
		sb.append(" \"assets/fonts/bootstrap/glyphicons-halflings-regular.eot\",\n");
		sb.append("\"assets/fonts/bootstrap/glyphicons-halflings-regular.svg\",\n");
		sb.append("\"assets/fonts/bootstrap/glyphicons-halflings-regular.ttf\",\n");
		sb.append("\"assets/fonts/bootstrap/glyphicons-halflings-regular.woff\",\n");
		sb.append(" \"assets/fonts/bootstrap/glyphicons-halflings-regular.woff2\"\n");
		sb.append(" ]\n");
		sb.append("}\n");
		sb.append("},\n");
		sb.append("\"resolutions\": {\n");
		sb.append("\"jquery\": \"~2.1.4\",\n");
		sb.append(" \"angular\": \"~1.5.3\"\n");
		sb.append(" }\n");
		sb.append("}\n");


		File file= new File("");
		File myJsp=new File(file.getAbsolutePath()+"/bower.json");
		PrintWriter writer;
		try {
			System.out.println("Written "+myJsp.getAbsolutePath());
			writer = new PrintWriter(myJsp, "UTF-8");
			writer.write(sb.toString());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void generateSwaggerService()
	{

		StringBuilder sb = new StringBuilder();	
		
		sb.append("(function() { \n")
		.append("'use strict'; \n")
		.append("\n");
		
		
		sb.append("angular.module(\""+Generator.appName+"\").service(\"SwaggerService\", SwaggerService);\n");
		
		sb.append("/** @ngInject */\n");
		sb.append("function SwaggerService($http,$log)\n");
		sb.append("{\n");
		sb.append("this.swaggerObject={};\n");
		sb.append(" this.getSwaggerJson = function() \n");
		sb.append(" { \n");
		sb.append(" var promise= $http.get(\""+Generator.restUrlProperty+"v2/api-docs/\"); \n");
		sb.append(" return promise; \n");
		sb.append(" } \n");
		sb.append("}\n");
		
		sb.append("})();\n");
		
		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+Generator.generateAngularDirectory+"components/swagger/";
		saveAsJsFile(directoryAngularFiles, "swagger.service", sb.toString());

	
	}
	
	public void generateSwaggerController()
	{
		StringBuilder sb = new StringBuilder();	
		
		sb.append("(function() { \n")
		.append("'use strict'; \n")
		.append("\n");
		
		sb.append("angular.module(\""+Generator.appName+"\").controller(\"SwaggerController\",SwaggerController);\n\n");
		
		
		sb.append("/** @ngInject */\n");
		sb.append("function SwaggerController($scope,$http,$rootScope,$log,UtilityService,SwaggerService){\n");
		
		sb.append("var vm = this;\n");
		sb.append("vm.swaggerObject=SwaggerService.swaggerObject;\n");
		sb.append("vm.url=\""+Generator.restUrlProperty+"v2/api-docs/\";\n");
		sb.append("function getJson()\n");
		sb.append("{\n");
		
		sb.append("SwaggerService.getSwaggerJson().then(function successCallback(response) {\n");
		sb.append("UtilityService.cloneObject(response.data,SwaggerService.swaggerObject);\n");
		sb.append("},function errorCallback(response) { \n");
		sb.append("UtilityService.AlertError.init({selector: \"#alertError\"});\n");
		sb.append("UtilityService.AlertError.show(\"Si è verificato un errore\");\n");
		sb.append("return; \n");
		sb.append("});\n");
		
		
		sb.append("}\n");
		sb.append("getJson();\n");
		sb.append("vm.getJson=getJson;\n");
		
		
		sb.append("}\n");
		sb.append("})();\n");
		
		File file = new File("");
		String directoryAngularFiles=file.getAbsolutePath()+Generator.generateAngularDirectory+"main/";
		saveAsJsFile(directoryAngularFiles, "main.controller", sb.toString());

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
