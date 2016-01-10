package it.anggen.generation.frontend;

import it.anggen.reflection.EntityAttributeManager;
import it.anggen.reflection.EntityManager;
import it.anggen.reflection.EntityManagerImpl;
import it.anggen.utils.ClassDetail;
import it.anggen.utils.EntityAttribute;
import it.anggen.utils.ReflectionManager;
import it.anggen.utils.Utility;
import it.anggen.generation.Generator;
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
public class FrontJsGenerator {

	private Entity entity;

	
	private EntityManager entityManager;
	
	private String parentEntityName;

	private Boolean isParent;

	private String entityName;
	
	private List<Field> fieldList;
	
	private List<Relationship> relationshipList;
	
	private List<Entity> descendantEntityList;
	
	private RelationshipType relationshipType;
	
	private Boolean lastLevel;
	
	private String serviceList;
	
	@Autowired
	private Generator generator;

	public FrontJsGenerator()
	{
		
	}

	
	public void init(Entity entity,Boolean isParent,String parentEntityName,RelationshipType relationshipType, Boolean lastLevel,String serviceList)
	{
		this.entity=entity;
		this.parentEntityName=parentEntityName;
		this.isParent=isParent;
		this.relationshipType=relationshipType;
		entityManager = new EntityManagerImpl(entity);
		
		this.entityName=Utility.getFirstLower(entity.getName());
		
		this.fieldList=entity.getFieldList();
		
		this.relationshipList=entity.getRelationshipList();
		this.lastLevel=lastLevel;
		this.descendantEntityList=entityManager.getDescendantEntities();
		if (isParent)
			this.serviceList=getServices();
		else
			this.serviceList=serviceList;
		/*List<Class> parentClassList = new ArrayList<Class>();
		parentClassList.add(classClass);
		this.descendantClassList=reflectionManager.getDescendantClassList(classClass, parentClassList);
		if (compositeClass!=null && compositeClass.fullName().contains("java.util.List"))
			entityList=true;
		else
			entityList=false;*/
	}
	/**
	 * Generate the service code for the entity
	 * @return
	 */
	private String generateService()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(".service(\""+entityName+"Service\", function($http)\n")
		.append("{\n")
		.append("this.entityList =		[];\n")
		.append("this.selectedEntity= 	{show: false \n");
		
		for (Relationship relationship : relationshipList)
		{
			if (EntityAttributeManager.getInstance(relationship).isList())
				sb.append(","+relationship.getEntityTarget().getName()+"List: []");
		}
		sb.append("};\n")
		.append("this.currentPage=1;\n")
		.append("this.maxPage=0;\n")
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
		if (isParent)
		{
			sb.append("this.searchBean = 		new Object();\n")
			.append("this.resetSearchBean= function()\n")
			.append("{\n")
			.append("this.searchBean={};\n")
			.append("};\n");
		}
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


		sb.append("this.searchPage = function() {\n");
		sb.append("var promise= $http.get(\"../../"+entityName+"/pages/\"+this.currentPage);\n");
		sb.append("return promise; \n");
		sb.append("};\n");


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
		if (isParent)
		{
			if (descendantEntityList!=null && descendantEntityList.size()>0)
				for (Entity descendantEntity: descendantEntityList)
				{
					stringBuilder.append(descendantEntity.getName()+"Service.selectedEntity.show="+show.toString()+";");
				}
		}
		else
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

		sb.append(".controller(\""+entityName+"FrontController\",function($scope,$http"+serviceList+")\n");
		sb.append("{\n");
		sb.append("//"+parentEntityName+"\n");
		//search var
		sb.append("$scope.searchBean="+Utility.getEntityCallName(entityName)+"Service.searchBean;\n");
		sb.append("$scope.entityList="+Utility.getEntityCallName(entityName)+"Service.entityList;\n");
		sb.append("$scope.selectedEntity="+Utility.getEntityCallName(entityName)+"Service.selectedEntity;\n");

		sb.append("$scope.childrenList="+Utility.getEntityCallName(entityName)+"Service.childrenList; \n");
		sb.append("$scope.getPagination= function(pageNumber) \n");
		sb.append("{ \n");
		sb.append("if (pageNumber<=0 || pageNumber>"+entityName+"Service.maxPage) \n");
		sb.append("return;\n ");
		sb.append(entityName+"Service.currentPage=pageNumber; \n");
		sb.append("$scope.currentPage=pageNumber; \n");
		sb.append(entityName+"Service.searchPage().then(function successCallback(response) { \n");
		sb.append(entityName+"Service.setEntityList(response.data.content); \n");
		sb.append(entityName+"Service.maxPage=response.data.totalPages; \n");
		sb.append("},function errorCallback(response) {  \n");
		sb.append("AlertError.init({selector: \"#alertError\"}); \n");
		sb.append("AlertError.show(\"Si è verificato un errore\"); \n");
		sb.append("return;  \n");
		sb.append("}); \n");

		sb.append("} \n");
		sb.append("})\n");
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
		services=services+","+entityName+"Service";
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
	
	
	private String getSecurity()
	{
		StringBuilder sb = new StringBuilder();	

		String services = serviceList;

		sb.append(".run(function($rootScope"+services+"){\n");
		sb.append(entityName+"Service.maxPage=1;\n");
		sb.append(entityName+"Service.searchPage().then(function successCallback(response) {\n");
		sb.append(entityName+"Service.setEntityList(response.data.content);\n");
		sb.append(entityName+"Service.setSelectedEntity(response.data);\n");
		sb.append(entityName+"Service.maxPage=response.data.totalPages;\n");
		sb.append("},function errorCallback(response) { \n");
		sb.append("AlertError.init({selector: \"#alertError\"});\n");
		sb.append("AlertError.show(\"Si è verificato un errore\");\n");
		sb.append("return; \n");
		sb.append("});\n");



		sb.append("})\n");


		return sb.toString();
	}
	
	/**
	 * Create the JS string
	 * @return
	 */
	private String buildJS()
	{
		StringBuilder buildJS= new StringBuilder();
		buildJS.append("var "+entityName+"FrontApp=angular.module(\""+entityName+"FrontApp\",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])\n");
		//JsGenerator jsGenerator = new JsGenerator(entity, true,null,null);
		buildJS.append(getSecurity());
		buildJS.append(generateService());
		if (entityName.equals("entity"))
			System.out.println("");
		buildJS.append(generateController());
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
	/**
	 * Save the generated file
	 * @param directory
	 */
	public void saveJsToFile(String directory)
	{
		File dir = new File(directory);
		if (!dir.exists())
			dir.mkdirs();
		File file = new File(directory+Utility.getFirstLower(entityName)+"-front.js");
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
		sb.append("angular.element($('#"+entityName+"Tabs')).scope().refreshTable"+Utility.getFirstUpper(tabName.replaceAll(" ",""))+"();\n");
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
