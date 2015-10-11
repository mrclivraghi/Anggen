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
		.append("this.selectedEntity= 	{show: false \n");
		for (Field field: fieldList)
		{
			if (field.getCompositeClass()!=null && field.getCompositeClass().fullName().contains("java.util.List"))
				sb.append(","+field.getName()+"List: []");
		}
		sb.append("};\n")
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

		.append("if (entity == null) {\n")
		.append("entity = {};\n")
		.append("this.selectedEntity.show = false;\n")
		.append("} //else\n")
		.append("var keyList = Object.keys(entity);\n")
		.append("if (keyList.length == 0)\n")
		.append("keyList = Object.keys(this.selectedEntity);\n")
		.append("for (i = 0; i < keyList.length; i++) {\n")
		.append("var val = keyList[i];\n")
		//TODO MODIFICA
		.append("if (val != undefined) {\n")
		.append("if (val.toLowerCase().indexOf(\"list\") > -1\n")
		.append("&& typeof entity[val] == \"object\") {\n")

		.append("if (entity[val] != null\n")
		.append("&& entity[val] != undefined) {\n")
		.append("while (this.selectedEntity[val].length > 0)\n")
		.append("this.selectedEntity[val].pop();\n")
		.append("if (entity[val] != null)\n")
		.append("for (j = 0; j < entity[val].length; j++)\n")
		.append("this.selectedEntity[val]\n")
		.append(".push(entity[val][j]);\n")

		.append("}\n")
		.append("} else {\n")

		.append("if (val.toLowerCase().indexOf(\"date\") > -1\n")
		.append("&& typeof val == \"string\") {\n")
		.append("var date = new Date(entity[val]);\n")
		.append("this.selectedEntity[val] = new Date(\n")
		.append("date.getFullYear(), date\n")
		.append(".getMonth(), date\n")
		.append(".getDate());\n")
		.append("} else {\n")
		.append("this.selectedEntity[val] = entity[val];\n")
		.append("}\n")

		.append("}\n")
		.append("	}\n")

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

					sb.append("$http\n");
					sb.append(".post(\"../"+field.getName()+"/search\",\n");
					sb.append("{})\n");
					sb.append(".success(\n");
					sb.append("function(entityList) {\n");
					sb.append(""+entityName+"Service.childrenList."+field.getName()+"List=entityList;\n");
					sb.append("}).error(function() {\n");
					sb.append("alert(\"error\");\n");
					sb.append("});\n");
				}

			sb.append("}; \n");
			sb.append("$scope.init();\n");


		}
		//pagination
		if (isParent)
			sb.append(getPagination());
		for (Field field: childrenList)
		{
			if (field.getCompositeClass().fullName().contains("java.util.List"))
			{
				JsGenerator jsGenerator = new JsGenerator(field.getFieldClass(), false, field.getCompositeClass(), entityName);
				sb.append(jsGenerator.getPagination());
			}
		}
		sb.append("})\n");
		return sb.toString();
	}

	public String getPagination()
	{
		StringBuilder sb = new StringBuilder();
		//pagination options
		sb.append("$scope."+entityName+ (entityList? "List":"")+"GridOptions = {\n");
		sb.append("enablePaginationControls: true,\n");
		sb.append("multiSelect: false,\n");
		sb.append("enableSelectAll: false,\n");
		sb.append("paginationPageSizes: [2, 4, 6],\n");
		sb.append("paginationPageSize: 2,\n");
		//generate dynamically
		sb.append("columnDefs: [\n");
		for (Field field: fieldList)
		{
			if (field.getCompositeClass()== null )
				sb.append("{ name: '"+field.getName()+"'},\n");
			else if (!field.getCompositeClass().fullName().contains("java.util.List") && isParent)
			{
				sb.append("{ name: '"+field.getName()+"."+field.getName()+"Id', displayName: '"+field.getName()+"'},\n");
			}
		}
		sb.setCharAt(sb.length()-2, ' ');
		sb.append("]\n");

		//if (isParent)
		sb.append(",data: "+entityName+"Service.entityList\n");
		sb.append(" };\n");

		//on row selection
		sb.append("$scope."+entityName+ (entityList? "List":"")+"GridOptions.onRegisterApi = function(gridApi){\n");
		sb.append("gridApi.selection.on.rowSelectionChanged($scope,function(row){\n");
		changeChildrenVisibility(sb, false);
		sb.append(entityName+"Service\n");
		sb.append(".setSelectedEntity(row.entity);\n");

		for (Field field: fieldList)
		{
			if (field.getCompositeClass()!=null && field.getCompositeClass().fullName().contains("java.util.List"))
				sb.append("$scope."+field.getName()+"ListGridOptions.data="+entityName+"Service.selectedEntity."+field.getName()+"List; \n");
		}

		sb.append(entityName+"Service.selectedEntity.show = true;\n");
		sb.append("});\n");
		sb.append("  };\n");


		return sb.toString();
	}

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
		buildJS.append("angular.module(\""+entityName+"App\",['ngTouch', 'ui.grid', 'ui.grid.pagination','ui.grid.selection'])\n");
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
