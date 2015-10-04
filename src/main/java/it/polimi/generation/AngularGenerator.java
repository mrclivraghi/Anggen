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
import java.util.Date;
import java.util.List;

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
			renderForm(html,"searchBean");
			html.button((new HtmlAttributes()).add("ng-click", "addNew()"))
				.content("Add new")
				.button((new HtmlAttributes()).add("ng-click", "search()"))
				.content("Find")
				.button((new HtmlAttributes()).add("ng-click", "reset()"))
				.content("Reset");
			html._form();
		}
		//list
		html.form((new HtmlAttributes()).add("id", entityName+"List").add("ng-if", "entityList.length>0").enctype("UTF-8"))
		.p().content("LISTA")
		.ul()
		.li((new HtmlAttributes()).add("ng-repeat", "entity in entityList").add("ng-click", "showEntityDetail($index)"))
		.p();
		renderItem(html,"entity");
		
		html._li()._ul()._form();
		//detail
		html.form((new HtmlAttributes()).add("id", entityName+"DetailForm").add("name", entityName+"DetailForm").add("ng-if", "selectedEntity.show"))
			.p().content("DETAIL");
		renderDetail(html);
		html._form();
		html.form((new HtmlAttributes()).add("id", entityName+"ActionButton").add("ng-if", "selectedEntity.show"))
		.p().content("ACTION BUTTON")
			.button((new HtmlAttributes()).add("ng-click", "insert()").add("ng-if", "selectedEntity."+entityName+"Id==undefined"))
				.content("Insert")
				.button((new HtmlAttributes()).add("ng-click", "update()").add("ng-if", "selectedEntity."+entityName+"Id>0"))
				.content("Update")
				.button((new HtmlAttributes()).add("ng-click", "del()").add("ng-if", "selectedEntity."+entityName+"Id>0"))
				.content("Delete");
		html._form();
		html._div();
		if (isParent)
		{

			List<ClassDetail> descendantClassList = ReflectionManager.getDescendantClassList(classClass, parentClass);
			if (descendantClassList==null || descendantClassList.size()==0) return;
			for (ClassDetail theClass: descendantClassList)
			{
				AngularGenerator angularGenerator = new AngularGenerator(theClass.getClassClass(), false, parentClass);
				angularGenerator.generateEntityView(html);
			}
		}
	}

	private void renderDetail(HtmlCanvas html) throws IOException {
		for (Field field: fieldList)
		{
			if (reflectionManager.isKnownClass(field.getFieldClass()))
			{
				String readOnly="false";
				if (field.getName().contains(entityName+"Id"))
					readOnly="true";
				
				String type= (field.getFieldClass()==Date.class ? "date" : "text");
				html.p()
				.content(field.getName())
				.input(getFieldHtmlAttributes(field,"selectedEntity",true));
			} else
				//TODO block after 2°level improve?
				if (field.getCompositeClass()!=null  && !(parentClass.contains(field.getFieldClass())))
				{ // entity or list!
					if (field.getCompositeClass().fullName().contains("java.util.List"))
					{ //list
						html.p((new HtmlAttributes()).add("ng-click", "show"+Utility.getFirstUpper(field.getName())+"Detail()"))
						.content("Add new "+field.getName());
						html.div((new HtmlAttributes()).add("ng-if", "selectedEntity."+field.getName()+"List.length>0"))
						.ul()
						.li((new HtmlAttributes()).add("ng-repeat", "entity in selectedEntity."+field.getName()+"List").add("ng-click", "show"+Utility.getFirstUpper(field.getName())+"Detail($index)"));
						AngularGenerator angularGenerator = new AngularGenerator(field.getFieldClass(), false, null);
						angularGenerator.renderItem(html, "entity");
						//html.content("{{$index}}--{{entity."+field.getName()+"Id}}--{{entity.description}}");
						
						html._ul()._div();
					}else
					{//entity
						html.p((new HtmlAttributes()).add("ng-click", "show"+Utility.getFirstUpper(field.getName())+"Detail()").add("ng-if", "selectedEntity."+field.getName()+"==null"))
						.content("Add new "+field.getName());
						html.p((new HtmlAttributes()).add("ng-click", "show"+Utility.getFirstUpper(field.getName())+"Detail()").add("ng-if", "selectedEntity."+field.getName()+"!=null"))
						.content(field.getName()+": {{selectedEntity."+field.getName()+"."+field.getName()+"Id}}");
						
						
					}
				}
			renderValidator(html,field);
		}
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
	
	private HtmlAttributes getFieldHtmlAttributes(Field field,String baseEntity, Boolean validation)
	{
		String readOnly="false";
		if (field.getName().contains(baseEntity+"Id"))
			readOnly="true";
		String type= (field.getFieldClass()==java.sql.Date.class || field.getFieldClass()==java.util.Date.class) ? "date" : "text";
		String fieldForm=baseEntity+"."+Utility.getFirstLower(field.getName());
		HtmlAttributes htmlAttributes = new HtmlAttributes();
		htmlAttributes.add("type", type).add("ng-model", fieldForm).add("ng-readonly",readOnly).add("name",field.getName());
		if (validation)
			renderValidatorAttributes(htmlAttributes,field);
		return htmlAttributes;
	}
	
	private void renderForm(HtmlCanvas html,String baseEntity)
	{ 
		for (Field field: fieldList)
		{
			if (field.getCompositeClass()==null)
			{
				try {
						html.p()
					.content(field.getName())
					.input(getFieldHtmlAttributes(field,baseEntity,false));
						
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else
			{
			/*	<!-- TODO mgmt -->
				<p>person</p>
				<select ng-model="searchBean.person.personId"
						ng-options="person.personId as person.firstName+' '+person.lastName for person in childrenList.personList">
				</select>
				<p>place</p>
				<select ng-model="searchBean.place.placeId"
						ng-options="place.placeId as place.description for place in childrenList.placeList">
				</select>*/
				try {
					html.p()
					.content(field.getName());
					html.select((new HtmlAttributes()).add("ng-model", "searchBean."+field.getName()+"."+field.getName()+"Id")
							.add("ng-options", field.getName()+"."+field.getName()+"Id as "+reflectionManager.getDescriptionField(field.getFieldClass())+" for "+field.getName()+" in childrenList."+field.getName()+"List").enctype("UTF-8"))
							._select();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}

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
