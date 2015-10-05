package it.polimi.generation;

import it.polimi.utils.ClassDetail;
import it.polimi.utils.Field;
import it.polimi.utils.ReflectionManager;
import it.polimi.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import com.sun.codemodel.JClass;

public class JsGenerator {
	
	private Class classClass;
	
	private String entityName;
	
	private String parentEntityName;
	
	private Boolean isParent;
	
	private List<Field> childrenList;
	
	private Boolean entityList;
	
	private List<Field> fieldList;
	
	private List<ClassDetail> descendantClassList;
	
	
	public JsGenerator(Class classClass,Boolean isParent,JClass compositeClass,String parentEntityName)
	{
		this.classClass=classClass;
		ReflectionManager reflectionManager = new ReflectionManager(classClass);
		this.entityName=reflectionManager.parseName();
		this.parentEntityName=parentEntityName;
		this.isParent=isParent;
		this.fieldList=reflectionManager.getFieldList();
		this.childrenList=reflectionManager.getChildrenFieldList();
		List<Class> parentClassList = new ArrayList<Class>();
		parentClassList.add(classClass);
		this.descendantClassList=reflectionManager.getDescendantClassList(classClass, parentClassList);
		if (compositeClass!=null && compositeClass.fullName().contains("java.util.List"))
			entityList=true;
		else
			entityList=false;
	}
	
	public String generateService()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(".service(\""+entityName+"Service\", function()\n")
		.append("{\n")
		.append("this.entityList =		[];\n")
		.append("this.selectedEntity= 	{show: false};\n")
		.append("this.childrenList=[]; \n")
		.append("this.addEntity=function (entity)\n")
		.append("{\n")
		.append("this.entityList.push(entity);\n")
		.append("};\n")
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
			.append("this.searchBean.orderId=\"\";\n")
			.append("this.searchBean.name=\"\";\n")
			.append("this.searchBean.timeslotDate=\"\";\n")
			.append("};\n");
		}
		sb.append("this.setSelectedEntity= function (entity)\n")
		.append("{ \n")
		.append("if (entity==null)\n") 
		.append("{\n")
		.append("entity={};\n")
		.append("this.selectedEntity.show=false;\n")
		.append("} //else\n")
		.append("var keyList=Object.keys(entity);\n")
		.append("if (keyList.length==0)\n")
		.append("keyList=Object.keys(this.selectedEntity);\n")
		.append("for (i=0; i<keyList.length; i++)\n")
		.append("{\n")
		.append("var val=keyList[i];\n")
		.append("this.selectedEntity[val]=entity[val];\n")
		.append("if (val!=undefined)\n")
		.append("{\n")
		//check list\n")
		.append("if (val.toLowerCase().indexOf(\"list\")>-1 && (entity[val]==null || entity[val]==undefined) && typeof entity[val] == \"object\")\n")
		.append("this.selectedEntity[val]=[];\n")
		//check date\n")
		.append("if (val.toLowerCase().indexOf(\"date\")>-1 && typeof val == \"string\")\n")
		.append("{\n")
		.append("var date= new Date(entity[val]);\n")
		.append("this.selectedEntity[val]= new Date(date.getFullYear(),date.getMonth(),date.getDate());\n")
		.append("}\n")
		.append("}\n")
		.append("}\n")
		.append("};\n")
		.append("})\n");

		return sb.toString();
	}
	
	private void changeChildrenVisibility(StringBuilder stringBuilder,Boolean show)
	{
		if (isParent)
		{
			if (descendantClassList!=null && descendantClassList.size()>0)
			for (ClassDetail childrenClass: descendantClassList)
			{
				ReflectionManager reflectionManager = new ReflectionManager(childrenClass.getClassClass());
				stringBuilder.append(reflectionManager.parseName()+"Service.selectedEntity.show="+show.toString()+";");
			}
		}
		else
		{
			if (childrenList!=null && childrenList.size()>0)
				for (Field field: childrenList)
				{
					ReflectionManager reflectionManager = new ReflectionManager(field.getFieldClass());
					stringBuilder.append(reflectionManager.parseName()+"Service.selectedEntity.show="+show.toString()+";");
				}

		}
		
	}
	
	public String generateController()
	{
		StringBuilder sb = new StringBuilder();

		sb.append(".controller(\""+entityName+"Controller\",function($scope,$http"+getServices()+")\n");
		sb.append("{\n");
		//search var
		sb.append("$scope.searchBean="+entityName+"Service.searchBean;\n");
		sb.append("$scope.entityList="+entityName+"Service.entityList;\n");
		sb.append("$scope.selectedEntity="+entityName+"Service.selectedEntity;\n");

		sb.append("$scope.childrenList="+entityName+"Service.childrenList; \n");
		//search function
		sb.append("$scope.reset = function()\n");
		sb.append("{\n");
		sb.append(""+entityName+"Service.resetSearchBean();\n");
		sb.append(""+entityName+"Service.setSelectedEntity(null);\n");
		sb.append(""+entityName+"Service.selectedEntity.show=false;\n");
		sb.append(""+entityName+"Service.setEntityList(null); \n");
		if (isParent)
			changeChildrenVisibility(sb, false);
		sb.append("}\n");

		
		/* UPDATE PARENT  */
		if (!isParent)
		{
			sb.append("$scope.updateParent = function(toDo)\n");
			sb.append("{\n");
			sb.append("$http.post(\"../"+parentEntityName+"/\","+parentEntityName+"Service.selectedEntity)\n");
			sb.append(".then(\n");
			sb.append("function(response) {\n");
			sb.append("if (response.status==200)\n");
			sb.append("	{\n");

			sb.append(""+parentEntityName+"Service.setSelectedEntity(response.data);\n");
			sb.append("if (toDo!=null)\n");
			sb.append("toDo(); \n");
			sb.append("if (!$scope.$$phase)\n");
			sb.append("$rootScope.$digest();\n");

			sb.append("}\n");
			sb.append("}\n");
			sb.append(",function(error) {\n");
			sb.append("alert(\"error\");\n");
			sb.append("});\n");
			sb.append("};\n");
		}
		sb.append("$scope.showEntityDetail= function(index)\n");
		sb.append("{\n");
		sb.append(""+entityName+"Service.indexSelected=index;\n");
		sb.append(""+entityName+"Service.setSelectedEntity($scope.entityList[index]);\n");
		sb.append(""+entityName+"Service.selectedEntity.show=true;\n");
		sb.append("};\n");

		sb.append("$scope.addNew= function()\n");
		sb.append("{\n");
		sb.append(""+entityName+"Service.setSelectedEntity(null);\n");
		sb.append(""+entityName+"Service.selectedEntity.show=true;\n");
		
		if (isParent)
		{
			changeChildrenVisibility(sb, false);
		}
		sb.append("};\n");
		sb.append("		\n");			
		//search function
		sb.append("$scope.search=function()\n");
		sb.append("{\n");
		sb.append(""+entityName+"Service.selectedEntity.show=false;\n");
		if (childrenList!=null)
		for (Field field: childrenList)
		{
			if (field.getCompositeClass()!=null && field.getCompositeClass().fullName().contains("java.util.List"))
			{
				sb.append(""+entityName+"Service.searchBean."+field.getName()+"List=[];\n");
				sb.append(""+entityName+"Service.searchBean."+field.getName()+"List.push("+entityName+"Service.searchBean."+field.getName()+");\n");
				sb.append("delete "+entityName+"Service.searchBean."+field.getName()+"; \n");
			}
		}
		sb.append("$http.post(\"../"+entityName+"/search\","+entityName+"Service.searchBean)\n");
		sb.append(".success( function(entityList) {\n");
		sb.append(""+entityName+"Service.setEntityList(entityList);\n");
		sb.append("})\n");
		sb.append(".error(function() {\n");

		sb.append("alert(\"error\");\n");
		sb.append("})\n");
		sb.append("};\n");
		
		//INSERT
		sb.append("$scope.insert=function()\n");
		sb.append("{\n");
		sb.append("if (!$scope."+entityName+"DetailForm.$valid) return; \n");
		if (isParent)
		{
			sb.append("$http.put(\"../"+entityName+"/\","+entityName+"Service.selectedEntity)\n");
			sb.append(".success( function(data) \n");
			sb.append("{\n");
			sb.append("$scope.search();})\n");
			sb.append(".error(function() \n");
			sb.append("{ \n");
			sb.append("alert(\"error\");\n");
			sb.append("});\n");
		}else
		{
			sb.append(entityName+"Service.selectedEntity.show=false;\n\n");
			if (entityList)
			{
				sb.append(parentEntityName+"Service.selectedEntity."+entityName+"List.push("+entityName+"Service.selectedEntity);\n\n");
				
			}else
			{
				sb.append(parentEntityName+"Service.selectedEntity."+entityName+"="+entityName+"Service.selectedEntity;\n\n");
			}
			
			sb.append("$scope.updateParent();\n\n");
			
		}
		sb.append("};\n");
		//UPDATE
		sb.append("$scope.update=function()\n");
		sb.append("{\n");
		sb.append("if (!$scope."+entityName+"DetailForm.$valid) return; \n");
		if (isParent)
		{
			changeChildrenVisibility(sb, false);
			sb.append("$http.post(\"../"+entityName+"/\","+entityName+"Service.selectedEntity)\n");
			sb.append(".success( function(data) {\n");
			sb.append("$scope.search();\n");
			sb.append("})\n");
			sb.append(".error(function() { \n");
			sb.append("alert(\"error\");\n");
			sb.append("});\n");
		}else
		{
			sb.append(entityName+"Service.selectedEntity.show=false;\n\n");
			if (entityList)
			{
				sb.append("for (i=0; i<"+parentEntityName+"Service.selectedEntity."+entityName+"List.length; i++)\n\n");
				sb.append("{\n\n");
				sb.append("if ("+parentEntityName+"Service.selectedEntity."+entityName+"List[i]."+entityName+"Id=="+entityName+"Service.selectedEntity."+entityName+"Id)\n\n");
				sb.append(""+parentEntityName+"Service.selectedEntity."+entityName+"List.splice(i,1);\n\n");
				sb.append("}\n\n");

				sb.append(parentEntityName+"Service.selectedEntity."+entityName+"List.push("+entityName+"Service.selectedEntity);\n\n");
			}else
			{
				sb.append(parentEntityName+"Service.selectedEntity."+entityName+"="+entityName+"Service.selectedEntity;\n\n");
			}
			
			sb.append("$scope.updateParent();\n");
		}
		sb.append("};\n");
		//DELETE
		sb.append("$scope.del=function()\n");
		sb.append("{\n");
		if (isParent)
		{
			sb.append("var url=\"../"+entityName+"/\"+"+entityName+"Service.selectedEntity."+entityName+"Id;\n");
			sb.append("$http[\"delete\"](url)\n");
			sb.append(".success( function(data) {\n");
			sb.append(""+entityName+"Service.setSelectedEntity(null);\n");
			sb.append("$scope.search();\n");
			sb.append("})\n");
			sb.append(".error(function() {\n"); 
			sb.append("alert(\"error\");\n");
			sb.append("});\n");
		}else
		{
			sb.append(entityName+"Service.selectedEntity.show=false;\n");
			if (entityList)
			{
				sb.append("for (i=0; i<"+parentEntityName+"Service.selectedEntity."+entityName+"List.length; i++)\n");
				sb.append("{\n");
				sb.append("if ("+parentEntityName+"Service.selectedEntity."+entityName+"List[i]."+entityName+"Id=="+entityName+"Service.selectedEntity."+entityName+"Id)\n");
				sb.append(""+parentEntityName+"Service.selectedEntity."+entityName+"List.splice(i,1);\n");
				sb.append("}\n");

			}else
			{
				sb.append(parentEntityName+"Service.selectedEntity."+entityName+"=null;");
			}
			
			sb.append(entityName+"Service.setSelectedEntity(null);\n");
			sb.append("$scope.updateParent();\n");
		}
		sb.append("};");
		//if (isParent)
		{
			for (Field field: childrenList)
			{
				sb.append("$scope.show"+Utility.getFirstUpper(field.getName())+"Detail= function(index)\n");
				sb.append("{\n");
				sb.append("if (index!=null)\n");
				sb.append(field.getName()+"Service.setSelectedEntity("+entityName+"Service.selectedEntity."+field.getName()+"List[index]);\n");
				sb.append("else \n");
				sb.append(field.getName()+"Service.setSelectedEntity("+entityName+"Service.selectedEntity."+field.getName()+"); \n");
				sb.append(field.getName()+"Service.selectedEntity.show=true;\n");
				sb.append("};\n");
				
			}
			//INIT CHILDREN LIST
			sb.append("$scope.init=function()\n")
			.append("{\n");
			if (childrenList!=null)
			for (Field field: childrenList)
			{

				sb.append("$http");
				sb.append(".post(\"../"+field.getName()+"/search\",");
				sb.append("{})");
				sb.append(".success(");
				sb.append("function(entityList) {");
				sb.append(""+entityName+"Service.childrenList."+field.getName()+"List=entityList;");
				sb.append("}).error(function() {");
				sb.append("alert(\"error\");");
				sb.append("});");
			}
			
			sb.append("}; \n");
			sb.append("$scope.init();\n");
			
			
		}
		
		
		sb.append("})\n");
		return sb.toString();
	}
	/*
	 * 
		StringBuilder sb = new StringBuilder();
		//service
		sb.append("\nangular.module(\""+Generator.getFirstLower(entityName)+"App\",[])\n")
		.append(".service(\""+Generator.getFirstLower(entityName)+"Service\", function(){\n")
		.append("this."+Generator.getFirstLower(entityName)+"List=[];\n")
		.append("this."+Generator.getFirstLower(entityName)+"Form= new Object();\n")
		.append("this.set"+Generator.getFirstUpper(entityName)+"List= function(data) {\n")
		.append("while (this."+Generator.getFirstLower(entityName)+"List.length>0)\n")
		.append("this."+Generator.getFirstLower(entityName)+"List.pop();\n")
		.append("for (i=0; i<data.length; i++)\n")
		.append("{\n")
		.append("var temp"+Generator.getFirstUpper(entityName)+"= new Object();\n")
		//manage fields
		.append(setTempEntityForm())
		.append("this."+Generator.getFirstLower(entityName)+"List[i]=temp"+Generator.getFirstUpper(entityName)+";\n")
		.append("}\n")
		.append("};\n")
		.append("this.resetForm = function ("+Generator.getFirstLower(entityName)+") {\n")
		//manage fields
		.append(resetEntityForm())
		.append("};\n")
		.append("this.add"+Generator.getFirstUpper(entityName)+"= function("+Generator.getFirstLower(entityName)+") {\n")
		.append("this."+Generator.getFirstLower(entityName)+"List.push("+Generator.getFirstLower(entityName)+");\n")
		.append("this.resetForm("+Generator.getFirstLower(entityName)+");\n")
		.append("};\n")
		.append("})\n")
		//ordercontroller
		.append(".controller(\""+Generator.getFirstLower(entityName)+"Controller\",\n")
		.append("function($scope,"+Generator.getFirstLower(entityName)+"Service) {\n")
		.append("$scope."+Generator.getFirstLower(entityName)+"Form="+Generator.getFirstLower(entityName)+"Service."+Generator.getFirstLower(entityName)+"Form;\n")
		.append("}\n")
		.append(")\n")
		// order retrieve controller
		.append(".controller(\""+Generator.getFirstLower(entityName)+"RetrieveController\", function ($scope,$http,"+Generator.getFirstLower(entityName)+"Service) {\n")
		.append("$scope.reset = function ()\n")
		.append("{\n")
		.append(""+Generator.getFirstLower(entityName)+"Service.resetForm();\n")
		.append("};\n")
		.append("$scope.search = function() {\n")
		.append("$http.post(\"../"+Generator.getFirstLower(entityName)+"/search\","+Generator.getFirstLower(entityName)+"Service."+Generator.getFirstLower(entityName)+"Form)\n")
		.append(".success( function(data) {\n")
		.append(""+Generator.getFirstLower(entityName)+"Service.set"+Generator.getFirstUpper(entityName)+"List(data);\n")
		.append("})\n")
		.append(".error(function() { alert(\"error\");});\n")
		.append("};\n")
		.append("$scope.insert = function() \n")
		.append("{\n")
		.append("$http.put(\"../"+Generator.getFirstLower(entityName)+"/\","+Generator.getFirstLower(entityName)+"Service."+Generator.getFirstLower(entityName)+"Form)\n")
		.append(".success( function(data) \n")
		.append("{\n")
		.append(""+Generator.getFirstLower(entityName)+"Service.add"+Generator.getFirstUpper(entityName)+"(data);\n")
		.append("})\n")
		.append(".error(function() \n")
		.append("{ \n")
		.append("alert(\"error\");\n")
		.append("});\n")
		.append("};\n")
		.append("$scope.update = function() {\n")
		.append("$http.post(\"../"+Generator.getFirstLower(entityName)+"/\","+Generator.getFirstLower(entityName)+"Service."+Generator.getFirstLower(entityName)+"Form)\n")
		.append(".success( function(data) {\n")
		.append("$scope.search();\n")
		.append("	})\n")
		.append(".error(function() { alert(\"error\");});\n")
		.append("};\n")
		.append("$scope.del = function() {\n")
		.append("var url=\"../"+Generator.getFirstLower(entityName)+"/\"+"+Generator.getFirstLower(entityName)+"Service."+Generator.getFirstLower(entityName)+"Form."+Generator.getFirstLower(entityName)+"Id;\n")
		.append("$http[\"delete\"](url)\n")
		.append(".success( function(data) {\n")
		.append(""+Generator.getFirstLower(entityName)+"Service.resetForm();\n")
		.append("$scope.search();\n")
		.append("})\n")
		.append(".error(function() { alert(\"error\");});\n")
		.append("};\n")
		.append("})\n")
		//order list controller
		.append(".controller(\""+Generator.getFirstLower(entityName)+"ListController\", function($scope,"+Generator.getFirstLower(entityName)+"Service,dateFilter)\n")
		.append("{\n")
		.append("$scope."+Generator.getFirstLower(entityName)+"List="+Generator.getFirstLower(entityName)+"Service."+Generator.getFirstLower(entityName)+"List;\n")
		.append("$scope.refreshForm = function (index) \n")
		.append("{\n")
		//manage fields
		.append(refreshEntityForm())
		.append("};\n")
		.append("});\n");
		return sb.toString();
	
	 */

	private String getServices() {
		List<Class> parentClassList= new ArrayList<Class>();
		parentClassList.add(classClass);
		String services="";
		services=services+","+entityName+"Service";
		for (ClassDetail classDetail : descendantClassList)
		{
			ReflectionManager reflectionManager = new ReflectionManager(classDetail.getClassClass());
			services=services+","+reflectionManager.parseName()+"Service";
			
		}
		return services;
	}
	
	public String buildJS()
	{
		StringBuilder buildJS= new StringBuilder();
		buildJS.append("angular.module(\""+entityName+"App\",[])\n");
		List<JsGenerator> jsGeneratorList= new ArrayList<JsGenerator>();
		jsGeneratorList.add(new JsGenerator(classClass, true,null,null));
		buildJS.append(jsGeneratorList.get(0).generateService());
		List<Class> parentClass= new ArrayList<Class>();
		parentClass.add(classClass);
		
		List<ClassDetail> descendantClassList = ReflectionManager.getDescendantClassList(classClass, parentClass);
		for (ClassDetail theClass : descendantClassList)
		{
			jsGeneratorList.add(new JsGenerator(theClass.getClassClass(),false,theClass.getCompositeClass(),theClass.getParentName()));
			buildJS.append(jsGeneratorList.get(jsGeneratorList.size()-1).generateService());
		}
		 
		//generateChildrenJs(buildJS,jsGeneratorList,parentClass);
		/*for (Field field: childrenField)
		{
			jsGeneratorList.add(new JsGenerator(field.getName(), false,null,field.getCompositeClass(),entityName));
			buildJS.append(jsGeneratorList.get(jsGeneratorList.size()-1).generateService());
		}*/
		
		for (JsGenerator jsGenerator: jsGeneratorList)
		{
			buildJS.append(jsGenerator.generateController());
		}
		buildJS.append(";");
		return buildJS.toString();
	}
	
	
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
	}
	
	

	private String setTempEntityForm()
	{
		StringBuilder sb = new StringBuilder();
		for (Field field: fieldList)
		{
			sb.append("temp"+Utility.getFirstUpper(entityName)+"."+Utility.getFirstLower(field.getName())+"=data[i]."+Utility.getFirstLower(field.getName())+";\n");
		}
		
		return sb.toString();
	}
	

	
}
