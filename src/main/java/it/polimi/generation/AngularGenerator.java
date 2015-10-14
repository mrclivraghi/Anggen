package it.polimi.generation;

import it.polimi.model.Mountain;
import it.polimi.utils.ClassDetail;
import it.polimi.utils.Field;
import it.polimi.utils.ReflectionManager;
import it.polimi.utils.Utility;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.text.html.HTML;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.rendersnake.HtmlAttributes;
import org.rendersnake.HtmlCanvas;

public class AngularGenerator {
	private String entityName;

	private Boolean isParent;

	private List<Field> fieldList;

	private ReflectionManager reflectionManager;

	private List<Class> parentClass;

	private Class classClass;

	public AngularGenerator(Class myClass,Boolean isParent, List<Class> parentClass)
	{
		this.reflectionManager= new ReflectionManager(myClass);
		this.entityName=reflectionManager.parseName();
		this.isParent=isParent;
		this.fieldList= reflectionManager.getFieldList();
		this.parentClass=parentClass;
		if (this.parentClass!=null)
			this.parentClass.add(myClass);
		this.classClass=myClass;
	}



	public void generateEntityView(HtmlCanvas html) throws IOException {
		html.div((new HtmlAttributes()).add("ng-controller", entityName+"Controller"));
		//search bean
		if (isParent)
		{
			html.form((new HtmlAttributes()).add("id", entityName+"SearchBean"));
			renderSearchForm(html,"searchBean");
			html._form();
		}
		//list
		/*
		html.form((new HtmlAttributes()).add("id", entityName+"List").add("ng-if", "entityList.length>0").enctype("UTF-8"))
		.p().content("LISTA")
		.ul()
		.li((new HtmlAttributes()).add("ng-repeat", "entity in entityList").add("ng-click", "showEntityDetail($index)"))
		.p();
		renderItem(html,"entity");
		html._li()._ul()._form();
		 */
		html.form((new HtmlAttributes()).add("id", entityName+"List").add("ng-if", "entityList.length>0").enctype("UTF-8"))
		.div(CssGenerator.getPanel())
		.div(CssGenerator.getPanelHeader())
		.content("List "+entityName)
		.div(CssGenerator.getPanelBody())
		
		.div((new HtmlAttributes()).add("ui-grid", entityName+"GridOptions").add("ui-grid-pagination", "").add("ui-grid-selection",""))
		._div()
		
		._div()
		._div()
		._form();

		//detail
		html.form((new HtmlAttributes()).add("id", entityName+"DetailForm").add("name", entityName+"DetailForm").add("ng-show", "selectedEntity.show"));
		//.p().content("DETAIL");
		renderDetail(html);
		html._form();
	
		html._div();
		if (isParent)
		{
			ArrayList<Class> oldParentClassList = (ArrayList<Class>) ((ArrayList<Class>) parentClass).clone();
			List<ClassDetail> descendantClassList = ReflectionManager.getDescendantClassList(classClass, parentClass);
			parentClass=oldParentClassList;
			if (descendantClassList==null || descendantClassList.size()==0) return;
			for (ClassDetail theClass: descendantClassList)
			{
				AngularGenerator angularGenerator = new AngularGenerator(theClass.getClassClass(), false, parentClass);
				angularGenerator.generateEntityView(html);
			}
		}
	}

	private void renderDetail(HtmlCanvas html) throws IOException {
		html.div(CssGenerator.getPanel());
		html.div(CssGenerator.getPanelHeader())
		.content("Detail "+entityName+" {{ selectedEntity."+entityName+"Id }}");
		html.div(CssGenerator.getPanelBody());
		String style="";
		for (Field field: fieldList)
		{
			style= style.equals("pull-left")? "pull-right": "pull-left";
			if (reflectionManager.isKnownClass(field.getFieldClass()))
			{
				String readOnly="false";
				if (field.getName().contains(entityName+"Id"))
					readOnly="true";

				String type= (field.getFieldClass()==Date.class ? "date" : "text");
				//html.p()
				//.content(field.getName())
				html.div((new HtmlAttributes()).add("class", style+" right-input"))
				.label((new HtmlAttributes()).add("for", field.getName()))
				.content(field.getName())
				.input(getFieldHtmlAttributes(field,"selectedEntity",true,""))
				._div();
			} else
				if (field.getCompositeClass()!=null  && !(parentClass.contains(field.getFieldClass())))
				{ // entity or list!
					if (field.getCompositeClass().fullName().contains("java.util.List"))
					{ //list
						//html.div((new HtmlAttributes()).add("class", style))
						//.label((new HtmlAttributes()).add("for", field.getName()))
						//.content(field.getName())
						html._div();
						html.div(CssGenerator.getPanelBody())
						.label((new HtmlAttributes()).add("id", field.getName())).content(field.getName())
						.button(CssGenerator.getButton("show"+Utility.getFirstUpper(field.getName())+"Detail"))
						.content("Add new "+field.getName());
						html.div((new HtmlAttributes()).add("id",field.getName()).add("ng-if", "selectedEntity."+field.getName()+"List.length>0"))

						.div((new HtmlAttributes()).add("style","top: 100px").add("ui-grid", field.getName()+"ListGridOptions").add("ui-grid-pagination", "").add("ui-grid-selection",""))
						._div();
						
						//html.content("{{$index}}--{{entity."+field.getName()+"Id}}--{{entity.description}}");
						html._div()._div();//._div();
						html.div(CssGenerator.getPanelBody());
					}else
					{//entity
						html.div((new HtmlAttributes()).add("class", style+" right-input"))
						.button(CssGenerator.getButton("show"+Utility.getFirstUpper(field.getName())+"Detail()").add("ng-if", "selectedEntity."+field.getName()+"==null"))
						.content("Add new "+field.getName());


						//html.p((new HtmlAttributes()).add("ng-click", "show"+Utility.getFirstUpper(field.getName())+"Detail()").add("ng-if", "selectedEntity."+field.getName()+"==null"))
						//.content("Add new "+field.getName());
						html
						.label((new HtmlAttributes()).add("for", field.getName()))
						.content(field.getName())
						.button(CssGenerator.getButton("show"+Utility.getFirstUpper(field.getName())+"Detail()").add("id",field.getName()).add("ng-if", "selectedEntity."+field.getName()+"!=null"))
						//.p((new HtmlAttributes()).add("ng-click", "show"+Utility.getFirstUpper(field.getName())+"Detail()").add("ng-if", "selectedEntity."+field.getName()+"!=null"))
						.content(field.getName()+": {{selectedEntity."+field.getName()+"."+field.getName()+"Id}}")
						._div();


					}
				}
			renderValidator(html,field);
		
		}
		html._div();
		html.div(CssGenerator.getPanelBody());
		html.div((new HtmlAttributes()).add("class", "pull-left"))
		.form((new HtmlAttributes()).add("id", entityName+"ActionButton").add("ng-if", "selectedEntity.show"))
		//.p().content("ACTION BUTTON")
		.button(CssGenerator.getButton("insert").add("ng-if", "selectedEntity."+entityName+"Id==undefined"))
		.content("Insert")
		.button(CssGenerator.getButton("update").add("ng-if", "selectedEntity."+entityName+"Id>0"))
		.content("Update")
		.button(CssGenerator.getButton("del").add("ng-if", "selectedEntity."+entityName+"Id>0"))
		.content("Delete");
		html._form()._div();
		html._div()._div();
		/*
		<p ng-click="showPlaceDetail()" >Add new place 
		</p>
		<div ng-if="selectedEntity.placeList.length>0">
			<ul>
				<li ng-repeat="entity in selectedEntity.placeList" ng-click="showPlaceDetail($index)">{{$index}}--{{entity.placeId}}--{{entity.description}}</li>
			</ul>
		</div>
		 */
	}

	/*
	 * <div ng-show="mountainDetailForm.name.$error.maxlength">Il nome
				utente non può superare i 14 caratteri</div>
			<div ng-show="mountainDetailForm.name.$error.minlength">Il nome
				utente deve essere di almeno 2 caratteri</div>
			<div ng-show="mountainDetailForm.name.$error.required && (mountainDetailForm.$dirty)">Il nome utente
				è obbligatorio</div>
	 */
	private void renderValidator(HtmlCanvas html,Field field) throws IOException
	{
		Annotation[] annotationList= field.getAnnotationList();
		Boolean required = false;
		for (int i=0; i<annotationList.length; i++)
		{
			if ((annotationList[i].annotationType()==NotNull.class || annotationList[i].annotationType()==NotBlank.class) && !required)
			{
				html.div((new HtmlAttributes()).add("ng-show", entityName+"DetailForm."+field.getName()+".$error.required"))
				.content(entityName+": "+field.getName()+" required");
				required=true;
			}else if (annotationList[i].annotationType()==Size.class)
			{
				for (Method method : annotationList[i].annotationType().getDeclaredMethods()) {
					if (method.getName().equals("min") || method.getName().equals("max"))
					{
						Object value;
						try {
							value = method.invoke(annotationList[i], (Object[])null);
							html.div((new HtmlAttributes()).add("ng-show", entityName+"DetailForm."+field.getName()+".$error."+method.getName()+"length"));
							html.content(entityName+": "+field.getName()+" "+method.getName()+" "+value+" caratteri");
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	private void renderValidatorAttributes(HtmlAttributes htmlAttributes, Field field)
	{
		Annotation[] annotationList= field.getAnnotationList();
		Boolean required = false;
		for (int i=0; i<annotationList.length; i++)
		{
			if ((annotationList[i].annotationType()==NotNull.class || annotationList[i].annotationType()==NotBlank.class) && !required)
			{
				htmlAttributes.add("ng-required", "true");
				required=true;
			}else if (annotationList[i].annotationType()==Size.class)
			{
				for (Method method : annotationList[i].annotationType().getDeclaredMethods()) {
					if (method.getName().equals("min") || method.getName().equals("max"))
					{
						Object value;
						try {
							value = method.invoke(annotationList[i], (Object[])null);
							htmlAttributes.add("ng-"+method.getName()+"length", value.toString());
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	private HtmlAttributes getFieldHtmlAttributes(Field field,String baseEntity, Boolean validation, String style)
	{
		String readOnly="false";
		if (field.getName().contains(baseEntity+"Id"))
			readOnly="true";
		String fieldForm=baseEntity+"."+Utility.getFirstLower(field.getName());
		HtmlAttributes htmlAttributes = CssGenerator.getInput(style);
		htmlAttributes.add("type", "text");
		if (field.getFieldClass()==java.sql.Date.class || field.getFieldClass()==java.util.Date.class)
		{
			htmlAttributes.add("ui-date", "{ dateFormat: 'dd/mm/yy' }");
			htmlAttributes.add("ui-date-format", "dd/mm/yy");

			
		}
		htmlAttributes.add("ng-model", fieldForm).add("ng-readonly",readOnly).add("name",field.getName());
		htmlAttributes.add("placeholder", field.getName());
		htmlAttributes.add("id", entityName+"-"+field.getName());
		if (validation)
			renderValidatorAttributes(htmlAttributes,field);
		return htmlAttributes;
	}

	private void renderSearchForm(HtmlCanvas html,String baseEntity)
	{ 
		try {
			html.div(CssGenerator.getPanel());
			html.div(CssGenerator.getPanelHeader())
			.content("Search form "+entityName);
			html.div(CssGenerator.getPanelBody());
			String style="";
			for (Field field: fieldList)
			{
				style= style.equals("pull-left")? "pull-right": "pull-left";
				if (field.getCompositeClass()==null)
				{

					//.p()
					//.content(field.getName())
					html.div((new HtmlAttributes()).add("class", style+" right-input"))
					.label((new HtmlAttributes()).add("id", field.getName())).content(field.getName())
					.input(getFieldHtmlAttributes(field,baseEntity,false,""))._div();


				} else
				{
					html.div((new HtmlAttributes()).add("class", style+" right-input"))
					.label((new HtmlAttributes()).add("id", field.getName())).content(field.getName());
					html.select(CssGenerator.getSelect("").add("ng-model", "searchBean."+field.getName()+"."+field.getName()+"Id")
							.add("id", field.getName())
							.add("ng-options", field.getName()+"."+field.getName()+"Id as "+reflectionManager.getDescriptionField(field.getFieldClass())+" for "+field.getName()+" in childrenList."+field.getName()+"List").enctype("UTF-8"))
							._select()._div();

				}
			}
			html._div();
			html.div(CssGenerator.getPanelBody());
			html.div((new HtmlAttributes()).add("class", "pull-left right-input"))
			.button(CssGenerator.getButton("addNew"))
			.content("Add new")
			.button(CssGenerator.getButton("search"))
			.content("Find")
			.button(CssGenerator.getButton("reset"))
			.content("Reset")
			._div();
			html._div()._div();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//useless
	private void renderItem(HtmlCanvas html,String baseEntity)
	{
		StringBuilder itemContent= new StringBuilder();
		itemContent.append("{{$index}} \n");
		for (Field field : fieldList)
		{
			if (field.getCompositeClass()==null)
			{
				itemContent.append("{{"+baseEntity+"."+field.getName()+"");
				if (field.getFieldClass()==Date.class)
				{ // set filter for each class type
					itemContent.append(" | date: 'dd-MM-yyyy'");
				}
				itemContent.append("}}\n");

			}
		}
		try {
			html.content(itemContent.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
