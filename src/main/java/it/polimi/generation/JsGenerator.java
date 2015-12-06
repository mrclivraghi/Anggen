package it.polimi.generation;

import it.polimi.model.EntityAttribute;
import it.polimi.model.FieldType;
import it.polimi.model.entity.Entity;
import it.polimi.model.entity.Tab;
import it.polimi.model.field.EnumField;
import it.polimi.model.field.EnumValue;
import it.polimi.model.field.Field;
import it.polimi.model.relationship.Relationship;
import it.polimi.reflection.EntityManager;
import it.polimi.reflection.EntityManagerImpl;
import it.polimi.utils.ClassDetail;
import it.polimi.utils.ReflectionManager;
import it.polimi.utils.Utility;

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

import com.sun.codemodel.JClass;


/**
 * Generate the JS source files based on angularJS
 * @author Marco
 *
 */
public class JsGenerator {

	private Entity entity;

	
	private EntityManager entityManager;
	
	private String parentEntityName;

	private Boolean isParent;

	private String entityName;
	
	private List<Field> fieldList;
	
	private List<Relationship> relationshipList;
	
	private List<Entity> descendantEntityList;
	
	private Boolean entityList;
	
	/**
	 * Constructor
	 * @param classClass
	 * @param isParent
	 * @param compositeClass
	 * @param parentEntityName
	 */
	public JsGenerator(Entity entity,Boolean isParent,String parentEntityName,Boolean entityList)
	{
		this.entity=entity;
		this.parentEntityName=parentEntityName;
		this.isParent=isParent;
		this.entityList=entityList;
		entityManager = new EntityManagerImpl(entity);
		
		this.entityName=Utility.getFirstLower(entity.getName());
		
		this.fieldList=entity.getFieldList();
		
		this.relationshipList=entity.getRelationshipList();
		
		this.descendantEntityList=entityManager.getDescendantEntities();
		
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
			if (relationship.isList())
				sb.append(","+relationship.getEntityTarget().getName()+"List: []");
		}
		sb.append("};\n")
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
		//search
		sb.append("this.search = function() {\n");
		sb.append("this.setSelectedEntity(null);\n");
		sb.append("var promise= $http.post(\"../"+entityName+"/search\",this.searchBean);\n");
		sb.append("return promise; \n");
		sb.append("};\n");
		//searchOne

		sb.append("this.searchOne=function(entity) {\n");
		//sb.append("this.setSelectedEntity(null);\n");
		sb.append("var promise= $http.get(\"../"+entityName+"/\"+entity."+entityName+"Id);\n");
		sb.append("return promise; \n");
		sb.append("};\n");


		//insert
		sb.append("this.insert = function() {\n");
		sb.append("var promise= $http.put(\"../"+entityName+"/\",this.selectedEntity);\n");
		sb.append("return promise; \n");
		sb.append("};\n");
		//update
		sb.append("this.update = function() {\n");
		sb.append("var promise= $http.post(\"../"+entityName+"/\",this.selectedEntity);\n");
		sb.append("return promise; \n");
		sb.append("}\n");
		//delete
		sb.append("this.del = function() {\n");
		sb.append("var url=\"../"+entityName+"/\"+this.selectedEntity."+entityName+"Id;\n");
		sb.append("var promise= $http[\"delete\"](url);\n");
		sb.append("return promise; \n");
		sb.append("}\n");

		//load file
		sb.append("this.loadFile= function(file,field){\n");
		sb.append("var formData = new FormData();\n");
		sb.append("if (file!=null)\n");
		sb.append("formData.append('file',file);\n");
		sb.append("var promise= $http.post(\"../"+entityName+"/\"+this.selectedEntity."+entityName+"Id+\"/load\"+field+\"/\",formData,{\n");
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
				sb.append(".post(\"../"+Utility.getEntityCallName(relationship.getEntityTarget().getName())+"/search\",\n");
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

		sb.append(".controller(\""+entityName+"Controller\",function($scope,$http"+getServices()+")\n");
		sb.append("{\n");
		sb.append("//"+parentEntityName+"\n");
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
		if (isParent)
			changeChildrenVisibility(sb, false);
		sb.append("}\n");


		/* UPDATE PARENT  */
		if (!isParent)
		{
			sb.append("$scope.updateParent = function(toDo)\n");
			sb.append("{\n");

			sb.append(Utility.getEntityCallName(parentEntityName)+"Service.update().then(function successCallback(response) {\n");
			sb.append(Utility.getEntityCallName(parentEntityName)+"Service.setSelectedEntity(response);\n");
			sb.append("if (toDo != null)\n");
			sb.append("toDo();\n");
			sb.append("},function errorCallback(response) {      \n");
			manageRestError(sb);
			sb.append("}\n");
			sb.append(");\n");
			sb.append("};\n");
		}
		sb.append("$scope.addNew= function()\n");
		sb.append("{\n");
		sb.append(""+Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(null);\n");
		sb.append(""+Utility.getEntityCallName(entityName)+"Service.setEntityList(null);\n");
		sb.append(""+Utility.getEntityCallName(entityName)+"Service.selectedEntity.show=true;\n");

		if (isParent)
		{
			changeChildrenVisibility(sb, false);
		}
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
				if (relationship.isList())
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
		if (isParent)
		{
			sb.append(Utility.getEntityCallName(entityName)+"Service.insert().then(function successCallback(response) { \n");
			sb.append("$scope.search();\n");
			sb.append("},function errorCallback(response) { \n");
			manageRestError(sb);
			sb.append("});\n");

		}else
		{
			//sb.append(entityName+"Service.selectedEntity.show=false;\n\n");
			/*sb.append(entityName+"Service.insert().then(function(data) { });\n");*/
			sb.append(Utility.getEntityCallName(entityName)+"Service.selectedEntity.show=false;\n");
			//TODO 
			if (entityList)
			{
				sb.append(Utility.getEntityCallName(entityName)+"Service.selectedEntity."+parentEntityName+"List.push("+parentEntityName+"Service.selectedEntity);\n");
			}else
			{
				sb.append(Utility.getEntityCallName(entityName)+"Service.selectedEntity."+parentEntityName+"={};\n");
				sb.append(Utility.getEntityCallName(entityName)+"Service.selectedEntity."+parentEntityName+"."+parentEntityName+"Id="+parentEntityName+"Service.selectedEntity."+parentEntityName+"Id;\n");
			
			}
			
			
			sb.append(Utility.getEntityCallName(entityName)+"Service.insert().then(function successCallBack(response) { \n");
			if (entityList)
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
			sb.append("},function errorCallback(response) { \n");
			manageRestError(sb);
			sb.append("});\n");
			//sb.append("$scope.updateParent();\n\n");

		}
		sb.append("};\n");
		//UPDATE
		sb.append("$scope.update=function()\n");
		sb.append("{\n");
		sb.append("if (!$scope."+entityName+"DetailForm.$valid) return; \n");
		if (isParent)
		{
			changeChildrenVisibility(sb, false);
			sb.append(Utility.getEntityCallName(entityName)+"Service.update().then(function successCallback(response) { \n");
			sb.append("$scope.search();\n");
			sb.append("},function errorCallback(response) { \n");
			manageRestError(sb);
			sb.append("});\n");
		}else
		{
			sb.append(Utility.getEntityCallName(entityName)+"Service.selectedEntity.show=false;\n\n");
			if (entityList)
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
			}

			//sb.append("$scope.updateParent();\n");
			sb.append(Utility.getEntityCallName(entityName)+"Service.update().then(function successCallback(response){\n");
			//console.log(data);
			sb.append(Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(response.data);\n");
			sb.append("},function errorCallback(response) { \n");
			manageRestError(sb);
			sb.append("});\n");
		}
		sb.append("};\n");
		//REMOVE
		if (!isParent)
		{
			sb.append("$scope.remove= function()\n");
			sb.append("{\n");
			sb.append(Utility.getEntityCallName(entityName)+"Service.selectedEntity.show=false;\n");
			if (entityList)
			{
				sb.append("for (i=0; i<"+parentEntityName+"Service.selectedEntity."+entityName+"List.length; i++)\n");
				sb.append("{\n");
				sb.append("if ("+parentEntityName+"Service.selectedEntity."+entityName+"List[i]."+entityName+"Id=="+entityName+"Service.selectedEntity."+entityName+"Id)\n");
				sb.append(""+parentEntityName+"Service.selectedEntity."+entityName+"List.splice(i,1);\n");
				sb.append("}\n");

			}else
			{
				sb.append(parentEntityName+"Service.selectedEntity."+entityName+"=null;\n");
			}

			sb.append(Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(null);\n");
			sb.append("$scope.updateParent();\n");
			
			sb.append("};\n");
		}

		//DELETE
		sb.append("$scope.del=function()\n");
		sb.append("{\n");
		//update entity in $scope
		if (entityList)
		{
			sb.append("for (i=0; i<"+parentEntityName+"Service.selectedEntity."+entityName+"List.length; i++)\n");
			sb.append("{\n");
			sb.append("if ("+parentEntityName+"Service.selectedEntity."+entityName+"List[i]."+entityName+"Id=="+entityName+"Service.selectedEntity."+entityName+"Id)\n");
			sb.append(""+parentEntityName+"Service.selectedEntity."+entityName+"List.splice(i,1);\n");
			sb.append("}\n");

		}else
			if (!isParent)
				sb.append(parentEntityName+"Service.selectedEntity."+entityName+"=null;\n");

		if (!isParent)
			sb.append("$scope.updateParent();\n");
		
		sb.append(Utility.getEntityCallName(entityName)+"Service.del().then(function successCallback(response) { \n");
		if (isParent)
		{
			sb.append("$scope.search();\n");
		}else
		{
			sb.append(Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(null);\n");
			sb.append(parentEntityName+"Service.init"+Utility.getFirstUpper(entityName)+"List().then(function(response) {\n");
			sb.append(parentEntityName+"Service.childrenList."+Utility.getFirstLower(entityName)+"List=response.data;\n");
			sb.append("});\n");
		}
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
		if (isParent)
			sb.append(getPagination());
		for (Relationship relationship: relationshipList)
		{
			JsGenerator jsGenerator = new JsGenerator(relationship.getEntityTarget(), false, entityName,true);
			sb.append(jsGenerator.getPagination());
		}
		sb.append("$scope.downloadEntityList=function()\n");
		sb.append("{\n");
		sb.append("var mystyle = {\n");
		sb.append(" headers:true, \n");
		sb.append("column: {style:{Font:{Bold:\"1\"}}}\n");
		sb.append("};\n");
		
		String exportFields="";
		for (Field field: fieldList)
		{
			if (field.getExcelExport())
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
			if (relationship.isList())
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
				if (field.getExcelExport())
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
	private String getPagination()
	{
		StringBuilder sb = new StringBuilder();
		//pagination options
		sb.append("$scope."+entityName+ (entityList? "List":"")+"GridOptions = {\n");
		sb.append("enablePaginationControls: true,\n");
		sb.append("multiSelect: false,\n");
		sb.append("enableSelectAll: false,\n");
		sb.append("paginationPageSizes: [2, 4, 6],\n");
		sb.append("paginationPageSize: 2,\n");
		sb.append("enableGridMenu: true,\n");
		//generate dynamically
		sb.append("columnDefs: [\n");
		//TODO add enum fields?
		for (EntityAttribute entityAttribute: entityManager.getAttributeList())
		{
			if ((entityAttribute.asRelationship()!=null && entityAttribute.getIgnoreTableList()) || (entityAttribute.asField()!=null && entityAttribute.asField().getIgnoreTableList())) continue;
			
			if (entityAttribute.asField()!= null )
			{
				if (entityAttribute.getPassword())
				{
					continue;
				}
				if (entityAttribute.asField().getFieldType()==FieldType.TIME)
				{
					sb.append("{ name: '"+entityAttribute.getName()+"', cellFilter: \"date:\'HH:mm\'\"},\n");
				}else
				{
					if (entityAttribute.asField().getFieldType()==FieldType.DATE)
						sb.append("{ name: '"+entityAttribute.getName()+"', cellFilter: \"date:\'dd-MM-yyyy\'\"},\n");
					else
						sb.append("{ name: '"+entityAttribute.getName()+"'},\n");
				}
			}
			else if (!entityAttribute.asRelationship().isList() && isParent)
			{
				sb.append("{ name: '"+entityAttribute.getName()+"."+entityAttribute.getName()+"Id', displayName: '"+entityAttribute.getName()+"'},\n");
			}
		}
		sb.setCharAt(sb.length()-2, ' ');
		sb.append("]\n");

		if (isParent)
			sb.append(",data: "+Utility.getEntityCallName(entityName)+"Service.entityList\n");
		else
			sb.append(",data: $scope.selectedEntity."+entityName+"List\n");
		sb.append(" };\n");

		//on row selection
		sb.append("$scope."+entityName+ (entityList? "List":"")+"GridOptions.onRegisterApi = function(gridApi){\n");
		sb.append("$scope."+entityName+"GridApi = gridApi;");
		sb.append("gridApi.selection.on.rowSelectionChanged($scope,function(row){\n");
		if (isParent)
			changeChildrenVisibility(sb, false);

		sb.append("if (row.isSelected)\n");
		sb.append("{\n");
		if (isParent)
		{
			sb.append(Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(row.entity);\n");
			
		} else
		{
			sb.append(Utility.getEntityCallName(entityName)+"Service.searchOne(row.entity).then(function(response) { \n");
			sb.append("console.log(response.data);\n");
			sb.append(Utility.getEntityCallName(entityName)+"Service.setSelectedEntity(response.data[0]);\n");
			sb.append("});\n");
		}
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
		services=services+","+entityName+"Service, securityService ";
		for (Entity descendantEntity : descendantEntityList)
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
				for (EnumValue enumValue: enumField.getEnumValueList())
				{
					sb.append("\""+enumValue.getName()+"\",");
				}
				sb.append("];\n");

			}
	}
	
	
	private String getSecurity()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(".service(\"securityService\",function($http)\n");
		sb.append("{\n");
		sb.append("this.restrictionList;\n");
		sb.append("this.init= function() {\n");
		sb.append("var promise= $http.get(\"../authentication/\");\n");
		sb.append("return promise; \n");

		sb.append("};\n");

		sb.append("})\n");
		String services = getServices();
		
		sb.append(".run(function($rootScope,securityService"+services+"){\n");

		sb.append("securityService.init().then(function successCallback(response) {\n");
		sb.append("securityService.restrictionList=response.data;\n");
		sb.append("$rootScope.restrictionList=response.data;\n");
		sb.append("console.log($rootScope.restrictionList);\n");
		String[] serviceArray=services.split(",");
		//for (int i=0; i<serviceArray.length; i++)
		//	if (!serviceArray[i].equals("") && !serviceArray[i].trim().equals("securityService"))
		{
			//sb.append("//"+serviceArray[i].trim()+".init();\n");
			
			initChildrenList(sb, entity);
				
		}
		
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
		buildJS.append("var "+entityName+"App=angular.module(\""+entityName+"App\",['ngFileUpload','ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection','ui.date', 'ui.grid.exporter'])\n");
		//JsGenerator jsGenerator = new JsGenerator(entity, true,null,null);
		buildJS.append(getSecurity());
		buildJS.append(generateService());
		buildJS.append(generateController());
		List<Entity> descendantEntityList = entityManager.getDescendantEntities();
		Set<Entity> descendantEntitySet = new HashSet<Entity>();
		descendantEntitySet.addAll(descendantEntityList);
		for (Entity descendantEntity : descendantEntitySet)
		{
			JsGenerator jsGenerator = new JsGenerator(descendantEntity,false,entity.getName(),true);
			buildJS.append(jsGenerator.generateService());
			buildJS.append(jsGenerator.generateController());
			
			
		}
		buildJS.append(";");
		return buildJS.toString();
	}



	private void manageRestError(StringBuilder sb)
	{
		sb.append("alert(\"error\");\n");
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
