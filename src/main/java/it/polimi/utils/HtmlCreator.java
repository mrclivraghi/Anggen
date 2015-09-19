package it.polimi.utils;

import it.polimi.utils.Generator.Field;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.rendersnake.HtmlAttributes;
import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;
import org.rendersnake.StringResource;

public class HtmlCreator {

	public static void renderItem(HtmlCanvas html,String entityName,List<Field> fields)
	{
		StringBuilder itemContent= new StringBuilder();
		itemContent.append("{{$index}} \n");
		for (Field field : fields)
		{
			itemContent.append("{{"+Generator.getFirstLower(entityName)+"."+Generator.getFirstLower(field.getName())+"");
			if (field.getFieldClass()==Date.class)
			{ // set filter for each class type
				itemContent.append(" | date: 'dd-MM-yyyy'");
			}
			itemContent.append("}}\n");
		}
		try {
			html.content(itemContent.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void renderForm(HtmlCanvas html,String entityName,List<Field> fields)
	{ // TODO mgmt readonly on p key
		for (Field field: fields)
		{
			try {
				String type= field.getFieldClass()==Date.class ? "date" : "text";
				String fieldForm=Generator.getFirstLower(entityName)+"Form."+Generator.getFirstLower(field.getName());
				html.p()
					.content(field.getName())
					.input((new HtmlAttributes()).add("type", type).add("ng-model", fieldForm));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String setTempEntityForm(String entityName,List<Field> fields)
	{
		StringBuilder sb = new StringBuilder();
		for (Field field: fields)
		{
			sb.append("temp"+Generator.getFirstUpper(entityName)+"."+Generator.getFirstLower(field.getName())+"=data[i]."+Generator.getFirstLower(field.getName())+";\n");
		}
		
		return sb.toString();
	}
	
	public static String resetEntityForm(String entityName,List<Field> fields)
	{
		StringBuilder sb = new StringBuilder();
		for (Field field: fields)
		{
			sb.append("this."+Generator.getFirstLower(entityName)+"Form."+Generator.getFirstLower(field.getName())+"= ("+Generator.getFirstLower(entityName)+"==null || "+Generator.getFirstLower(entityName)+"== undefined )? \"\" : "+Generator.getFirstLower(entityName)+"."+Generator.getFirstLower(field.getName())+";\n");
		}
		
		return sb.toString();
	}
	
	
	public static String refreshEntityForm(String entityName,List<Field> fields)
	{
		StringBuilder sb = new StringBuilder();
		for (Field field: fields)
		{
			if (field.getFieldClass()==Date.class)
			{
				sb.append("date= new Date("+Generator.getFirstLower(entityName)+"Service."+Generator.getFirstLower(entityName)+"List[index]."+Generator.getFirstLower(field.getName())+");\n");
				sb.append(""+Generator.getFirstLower(entityName)+"Service."+Generator.getFirstLower(entityName)+"List[index]."+Generator.getFirstLower(field.getName())+"= new Date(date.getFullYear(),date.getMonth(),date.getDate());\n");
			}
		}
		sb.append(""+Generator.getFirstLower(entityName)+"Service.resetForm("+Generator.getFirstLower(entityName)+"Service."+Generator.getFirstLower(entityName)+"List[index]);\n");
		return sb.toString();
	}
	
	public static String generateJS(String entityName, List<Field> fields)
	{
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
		.append(setTempEntityForm(entityName, fields))
		.append("this."+Generator.getFirstLower(entityName)+"List[i]=temp"+Generator.getFirstUpper(entityName)+";\n")
		.append("}\n")
		.append("};\n")
		.append("this.resetForm = function ("+Generator.getFirstLower(entityName)+") {\n")
		//manage fields
		.append(resetEntityForm(entityName, fields))
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
		.append(refreshEntityForm(entityName, fields))
		.append("};\n")
		.append("});\n");
		return sb.toString();
	}
	
	
	
	public static void generateJSP(String entityName,List<Field> fields)
	{
		HtmlCanvas html = new HtmlCanvas();
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		try {
			html
					.head()
						.title().content("test order")
						.macros().javascript("https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js")
						.script().content(generateJS(entityName,fields),false)
					._head()
					.body()
					.div(htmlAttributes.add("ng-app", Generator.getFirstLower(entityName)+"App"))
					//ordercontroller
					.div((new HtmlAttributes()).add("ng-controller", Generator.getFirstLower(entityName)+"Controller"));
					renderForm(html, entityName, fields);
					html._div()
					//orderRetrieveController
					.div((new HtmlAttributes()).add("ng-controller", Generator.getFirstLower(entityName)+"RetrieveController"))
						.button((new HtmlAttributes()).add("ng-click", "search()"))
						.content("search")
					._div()
					.div((new HtmlAttributes()).add("ng-controller", Generator.getFirstLower(entityName)+"RetrieveController"))
						.button((new HtmlAttributes()).add("ng-click", "insert()"))
						.content("insert")
						//._button()
					._div()
					.div((new HtmlAttributes()).add("ng-controller", Generator.getFirstLower(entityName)+"RetrieveController"))
						.button((new HtmlAttributes()).add("ng-click", "update()"))
						.content("update")
						//._button()
					._div()
					.div((new HtmlAttributes()).add("ng-controller", Generator.getFirstLower(entityName)+"RetrieveController"))
						.button((new HtmlAttributes()).add("ng-click", "del()"))
						.content("del")
						//._button()
					._div()
					.div((new HtmlAttributes()).add("ng-controller", Generator.getFirstLower(entityName)+"RetrieveController"))
						.button((new HtmlAttributes()).add("ng-click", "reset()"))
						.content("reset")
						//._button()
					._div()
					//orderListcontroller
					.div((new HtmlAttributes()).add("ng-controller", Generator.getFirstLower(entityName)+"ListController"))
					.ul()
						.li((new HtmlAttributes()).add("ng-repeat", Generator.getFirstLower(entityName)+" in "+Generator.getFirstLower(entityName)+"List"))
							.p((new HtmlAttributes()).add("ng-click", "refreshForm($index)"));
					
					renderItem(html,entityName,fields);
					
							//._p()
						html._li()
					._ul()
					
					._div()
					._div()
					._body()
					;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println(html.toHtml());
		
	}

	public static void main(String[] args)
	{
		/*List<Field> fields = new ArrayList<Field>();
		fields.add(new Field("orderTestId",Long.class));
		fields.add(new Field("name",String.class));
		fields.add(new Field("timeslotDate",Date.class));
		String nameEntity="OrderTest";
	*/	
		List<Field> fields = new ArrayList<Field>();
		fields.add(new Field("personId",Long.class));
		fields.add(new Field("firstName",String.class));
		fields.add(new Field("lastName",String.class));
		fields.add(new Field("birthDate",Date.class));
		String nameEntity="Person";
		generateJSP(nameEntity,fields);
		//System.out.println(StringResource.get("orderApp.js"));
	}


}
