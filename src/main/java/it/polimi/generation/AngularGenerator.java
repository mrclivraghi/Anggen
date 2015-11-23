package it.polimi.generation;

import it.polimi.model.domain.Entity;
import it.polimi.model.domain.EntityAttribute;
import it.polimi.model.domain.FieldType;
import it.polimi.reflection.EntityManager;
import it.polimi.reflection.EntityManagerImpl;
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

/**
 * @author Marco
 * Creates an html file for rendering the entity with all its children.
 */
public class AngularGenerator {
	private String entityName;

	private Boolean isParent;

	private List<EntityAttribute> attributeList;

	private EntityManager entityManager;

	private List<Entity> parentEntity;

	private Entity entity;
	

	public AngularGenerator(Entity entity,Boolean isParent, List<Entity> parentEntity)
	{
		this.entityManager= new EntityManagerImpl(entity);
		this.entityName=entity.getName();
		this.isParent=isParent;
		this.attributeList= entityManager.getAttributeList();
		this.parentEntity=parentEntity;
		if (this.parentEntity!=null)
			this.parentEntity.add(entity);
		this.entity=entity;
		
	}


	/**
	 * Creates the html structure
	 * 
	 * @param html
	 * @throws IOException
	 */
	public void generateEntityView(HtmlCanvas html) throws IOException {
		HtmlAttributes mainControllerAttributes = new HtmlAttributes();
		mainControllerAttributes.add("ng-controller", entityName+"Controller");
		//TODO
		if (Generator.easyTreeMenu!=null)
		{
			mainControllerAttributes.add("style", "position: absolute; left: 250px; width:80%; top: 30px;");
		}
		html.div(mainControllerAttributes);
		//search bean
		if (isParent)
		{
			html.form((new HtmlAttributes()).add("id", entityName+"SearchBean"));
			renderTabForm(html, true);
			html._form();
		}
		HtmlCanvas downloadCanvas= new HtmlCanvas();
		downloadCanvas.button(CssGenerator.getButton("downloadEntityList","pull-right").add("style", "margin-top:-7px"))
		.span((new HtmlAttributes()).add("class", "glyphicon glyphicon-download-alt").add("aria-hidden", "true"))
		._span()
		._button();
		
		html.form((new HtmlAttributes()).add("id", entityName+"List").add("ng-if", "entityList.length>0").enctype("UTF-8"))
		.div(CssGenerator.getPanel())
		.div(CssGenerator.getPanelHeader())
		.content("List "+entityName+downloadCanvas.toHtml(),false)
		
		.div(CssGenerator.getPanelBody())
		
		.div((new HtmlAttributes()).add("ui-grid", entityName+"GridOptions").add("ui-grid-pagination", "").add("ui-grid-selection","").add("ui-grid-exporter", ""))
		._div()
		._div()
		._div()
		._form();

		//detail
		html.form((new HtmlAttributes()).add("id", entityName+"DetailForm").add("name", entityName+"DetailForm").add("ng-show", "selectedEntity.show"));
		renderTabForm(html, false);
		html._form();
	
		html._div();
		if (isParent)
		{
			ArrayList<Entity> oldParentClassList = (ArrayList<Entity>) ((ArrayList<Entity>) parentEntity).clone();
			List<Entity> descendantEntityList = entityManager.getDescendantEntities(entity, parentEntity);
			parentEntity=oldParentClassList;
			if (descendantEntityList==null || descendantEntityList.size()==0) return;
			for (Entity descendantEntity: descendantEntityList)
			{
				AngularGenerator angularGenerator = new AngularGenerator(descendantEntity, false, parentEntity);
				angularGenerator.generateEntityView(html);
			}
		}
	}

	/**
	 * Generate a html form that can be of two types: search or not.
	 * Search form render childrenList as a select, a non search form render them as a table.
	 * 
	 * @param html
	 * @param search if the form is a search form
	 * @throws IOException
	 */
	
	private void renderForm(HtmlCanvas html,Boolean search) throws IOException {
		html.div(CssGenerator.getPanel());
		html.div(CssGenerator.getPanelHeader());
		String baseEntity;
		if (search)
		{
			baseEntity="searchBean";
			html.content("Search form "+entityName);
		}
		else
		{
			baseEntity="selectedEntity";
			html.content("Detail "+entityName+" {{ selectedEntity."+entityName+"Id }}");
		}
		html.div(CssGenerator.getPanelBody());
		String style="";
		for (EntityAttribute entityAttribute: entityManager.getAllAttribute())
		{
			if (search && entityAttribute.getIgnoreSearch()) continue;
			if (!search && entityAttribute.getIgnoreUpdate()) continue;
			
			style= style.equals("pull-left")? "pull-right": "pull-left";
			
			
			
			if (entityAttribute.isEnumField())
			{
				html.div(CssGenerator.getExternalFieldPanel(style, search, entityName, entityAttribute));
				
				html.div(CssGenerator.getInputGroup());
				html.span((new HtmlAttributes()).add("class","input-group-addon")).content(entityAttribute.getName());
				html.select(getFieldHtmlAttributes(entityAttribute, baseEntity, !search, style)
				.add("ng-options", entityAttribute.getName()+ " as "+entityAttribute.getName()+" for "+entityAttribute.getName()+" in childrenList."+entityAttribute.getName()+"List").enctype("UTF-8"));
				html._select();
				html._div();
				if (!search)
				renderValidator(html, entityAttribute);
				html._div();
			}
			else
			{
				if (entityAttribute.asField()!=null)
				{
					html.div(CssGenerator.getExternalFieldPanel(style, search, entityName, entityAttribute));
					html.div(CssGenerator.getInputGroup());
					html.span((new HtmlAttributes()).add("class","input-group-addon")).content(entityAttribute.getName());
					if (getInputType(entityAttribute).equals("checkbox"))
					{
						html.select(getFieldHtmlAttributes(entityAttribute, baseEntity, !search, "").add("ng-options", "value for value in trueFalseValues"))
						._select();
					}else
						html.input(getFieldHtmlAttributes(entityAttribute,baseEntity,!search,""));
					html._div();
					if (!search)
					renderValidator(html,entityAttribute);
					html._div();
				} else
					
					
					
					if (!(parentEntity.contains(entityAttribute.asRelationship().getEntityTarget())))
					{ // entity or list!

						
						EntityManager entityAttributeManager = new EntityManagerImpl(entityAttribute.asRelationship().getEntityTarget());
						
						if (search)
						{
							html.div((new HtmlAttributes()).add("class", style+" right-input").add("style","height: 59px;"));
							
							html.div((new HtmlAttributes()).add("class", "input-group"));
							html.span((new HtmlAttributes()).add("class","input-group-addon")).content(entityAttribute.getName());
						
							html.select(CssGenerator.getSelect("").add("ng-model", baseEntity+"."+entityAttribute.getName()+"."+entityAttribute.getName()+"Id")
									.add("id", entityAttribute.getName())
									.add("ng-options", entityAttribute.getName()+"."+entityAttribute.getName()+"Id as "+entityAttributeManager.getDescriptionField()+" for "+entityAttribute.getName()+" in childrenList."+entityAttribute.getName()+"List").enctype("UTF-8"))
									._select();
							html._div()._div();
						} else
						{


							if (entityAttribute.asRelationship().isList())
							{ //list
								
								HtmlCanvas downloadCanvas= new HtmlCanvas();
								downloadCanvas
								.button(CssGenerator.getButton("show"+Utility.getFirstUpper(entityAttribute.getName())+"Detail"," pull-right").add("style", "margin-top: -7px"))
								.content("Add new "+entityAttribute.getName())
								.button(CssGenerator.getButton("download"+Utility.getFirstUpper(entityAttribute.getName())+"List","pull-right").add("style", "margin-top:-7px"))
								.span((new HtmlAttributes()).add("class", "glyphicon glyphicon-download-alt").add("aria-hidden", "true"))
								._span()
								._button();
								
								html._div();
								html.div(CssGenerator.getPanel())
								.div(CssGenerator.getPanelHeader())
								.content(entityAttribute.getName()+downloadCanvas.toHtml(),false);
								html.div(CssGenerator.getPanelBody().add("ng-class","{'has-error': !"+entityName+"DetailForm."+entityAttribute.getName()+".$valid, 'has-success': "+entityName+"DetailForm."+entityAttribute.getName()+".$valid}"))
								.label((new HtmlAttributes()).add("id", entityAttribute.getName())).content(entityAttribute.getName());
								//.button(CssGenerator.getButton("show"+Utility.getFirstUpper(field.getName())+"Detail"))
								//.content("Add new "+field.getName());
								html.div((new HtmlAttributes()).add("id",entityAttribute.getName()).add("ng-if", "selectedEntity."+entityAttribute.getName()+"List.length>0"))
								.div((new HtmlAttributes()).add("style","top: 100px").add("ui-grid", entityAttribute.getName()+"ListGridOptions").add("ui-grid-pagination", "").add("ui-grid-selection",""))
								._div();
								renderValidator(html,entityAttribute);
								html._div()._div();
								
								html._div();
								html.div(CssGenerator.getPanelBody());
							}else
							{//entity
								html.div(CssGenerator.getExternalFieldPanel(style, search, entityName, entityAttribute));
								html.div((new HtmlAttributes()).add("class", "input-group"));
								html.span((new HtmlAttributes()).add("class", "input-group-addon")).content(entityAttribute.getName());
								html.select(CssGenerator.getSelect("").add("ng-model", "selectedEntity."+entityAttribute.getName())
										.add("id", entityAttribute.getName())
										.add("name", entityAttribute.getName())
										.add("ng-options", entityAttribute.getName()+" as "+entityAttributeManager.getDescriptionField()+" for "+entityAttribute.getName()+" in childrenList."+entityAttribute.getName()+"List track by "+entityAttribute.getName()+"."+entityAttribute.getName()+"Id").enctype("UTF-8"))
										._select();
								renderValidator(html,entityAttribute);
								html.span((new HtmlAttributes()).add("class", "input-group-btn"))
								.button(CssGenerator.getButton("show"+Utility.getFirstUpper(entityAttribute.getName())+"Detail").add("id",entityAttribute.getName()).add("ng-if", "selectedEntity."+entityAttribute.getName()+"==null"))
								.content("Add new "+entityAttribute.getName())
								.button(CssGenerator.getButton("show"+Utility.getFirstUpper(entityAttribute.getName())+"Detail").add("id",entityAttribute.getName()).add("ng-if", "selectedEntity."+entityAttribute.getName()+"!=null"))
								.content("Show detail")
								._span();
								html._div();
								html._div();


							}
						}
					}
			}
		
		}
		html._div();
		html.div(CssGenerator.getPanelBody());
		if (!search)
		{
			html.div((new HtmlAttributes()).add("class", "pull-left"))
			.form((new HtmlAttributes()).add("id", entityName+"ActionButton").add("ng-if", "selectedEntity.show"))
			.button(CssGenerator.getButton("insert").add("ng-if", "selectedEntity."+entityName+"Id==undefined"))
			.content("Insert")
			.button(CssGenerator.getButton("update").add("ng-if", "selectedEntity."+entityName+"Id>0"))
			.content("Update")
			.button(CssGenerator.getButton("del").add("ng-if", "selectedEntity."+entityName+"Id>0"))
			.content("Delete");
			if (!isParent)
				html.button(CssGenerator.getButton("remove").add("ng-if", "selectedEntity."+entityName+"Id>0"))
				.content("Remove");
			html._form()._div();
		} else
		{
			html.div(CssGenerator.getPanelBody());
			html.div((new HtmlAttributes()).add("class", "pull-left right-input"))
			.button(CssGenerator.getButton("addNew"))
			.content("Add new")
			.button(CssGenerator.getButton("search"))
			.content("Find")
			.button(CssGenerator.getButton("reset"))
			.content("Reset")
			._div()
			._div();
		}
		html._div()._div();
	}

	
	/**
	 * Generate the validator fields to show errors to the user.
	 * 
	 * @param html
	 * @param entityAttribute
	 * @throws IOException
	 */
	private void renderValidator(HtmlCanvas html,EntityAttribute entityAttribute) throws IOException
	{/*
		Annotation[] annotationList= entityAttribute.getAnnotationList();
		Boolean required = false;
		for (int i=0; i<annotationList.length; i++)
		{
			if ((annotationList[i].annotationType()==NotNull.class || annotationList[i].annotationType()==NotBlank.class) && !required)
			{
				html.small((new HtmlAttributes()).add("class", "help-block").add("ng-show", entityName+"DetailForm."+entityAttribute.getName()+".$error.required"))
				.content(entityName+": "+entityAttribute.getName()+" required");
				required=true;
			}else if (annotationList[i].annotationType()==Size.class)
			{
				for (Method method : annotationList[i].annotationType().getDeclaredMethods()) {
					if (method.getName().equals("min") || method.getName().equals("max"))
					{
						Object value;
						try {
							value = method.invoke(annotationList[i], (Object[])null);
							html.small((new HtmlAttributes()).add("class", "help-block").add("ng-show", entityName+"DetailForm."+entityAttribute.getName()+".$error."+method.getName()+"length"));
							html.content(entityName+": "+entityAttribute.getName()+" "+method.getName()+" "+value+" caratteri");
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}*/
	}

	/**
	 * Given a field generated the angular html attributes corrected to manage the validation
	 * 
	 * @param htmlAttributes
	 * @param entityAttribute
	 */
	private void renderValidatorAttributes(HtmlAttributes htmlAttributes, EntityAttribute entityAttribute)
	{
	/*Annotation[] annotationList= entityAttribute.getAnnotationList();
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
							e.printStackTrace();
						}
					}
				}
			}
		}*/
	}

	/**
	 * Return the type of the html input based on the field.
	 * 
	 * @param entityAttribute
	 * @return
	 */
	private String getInputType(EntityAttribute entityAttribute)
	{
		if (entityAttribute.asField()!=null && entityAttribute.asField().getFieldType()==FieldType.BOOLEAN) return "checkbox";
		if (entityAttribute.asField()!=null && entityAttribute.asField().getFieldType()==FieldType.TIME) return "time";
		
		return "text";
	}
	
	
	/**
	 * Creates the htmlAttributes for a field, specifying the baseEntity (searchBean or selectedEntity), 
	 * validation and additional style
	 * @param entityAttribute
	 * @param baseEntity
	 * @param validation
	 * @param style
	 * @return
	 */
	private HtmlAttributes getFieldHtmlAttributes(EntityAttribute entityAttribute,String baseEntity, Boolean validation, String style)
	{
		String readOnly="false";
		String entityAttributeName= (entityAttribute.asField()!=null? entityAttribute.asField().getName() : entityAttribute.asRelationship().getEntityTarget().getName());
		
		
		if (entityAttributeName.equals(entityName+"Id")&&validation)
			readOnly="true";
		String fieldForm=baseEntity+"."+Utility.getFirstLower(entityAttributeName);
		String type= getInputType(entityAttribute);
		HtmlAttributes htmlAttributes = CssGenerator.getInput(style);
		htmlAttributes.add("type", type);
		if (entityAttribute.asField()!=null && entityAttribute.asField().getFieldType()==FieldType.TIME)
		{
			htmlAttributes.add("placeholder", "HH:mm");
		} else
		{
			if (entityAttribute.asField()!=null && entityAttribute.asField().getFieldType()==FieldType.DATE)
			{
				htmlAttributes.add("ui-date", "{ dateFormat: 'dd/mm/yy' }");
			} else
			{
				htmlAttributes.add("id", entityName+"-"+entityAttributeName);
			}
		}
		htmlAttributes.add("ng-model", fieldForm).add("ng-readonly",readOnly).add("name",entityAttributeName);
		htmlAttributes.add("placeholder", entityAttributeName);
		if (validation)
			renderValidatorAttributes(htmlAttributes,entityAttribute);
		return htmlAttributes;
	}


	/**
	 * Generate a html form that can be of two types: search or not.
	 * Search form render childrenList as a select, a non search form render them as a table.
	 * 
	 * @param html
	 * @param search if the form is a search form
	 * @throws IOException
	 */
	
	private void renderTabForm(HtmlCanvas html,Boolean search) throws IOException {
		html.div(CssGenerator.getPanel());
		html.div(CssGenerator.getPanelHeader());
		String baseEntity;
		if (search)
		{
			baseEntity="searchBean";
			html.content("Search form "+entityName);
		}
		else
		{
			baseEntity="selectedEntity";
			html.content("Detail "+entityName+" {{ selectedEntity."+entityName+"Id }}");
		}
		html.div(CssGenerator.getPanelBody());
		
		/*
		 * print ul
		 *<ul class="nav nav-tabs" role="tablist">
    <li role="presentation" class="active"><a href="#tab0" aria-controls="tab0" role="tab" data-toggle="tab">Tab0</a></li>
    <li role="presentation"><a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">Tab1</a></li>
  </ul> 
		 */
		List<String> tabNameList;
		if (search)
		{
			tabNameList=new ArrayList<String>();
			tabNameList.add("Detail");
		} else
			tabNameList=entityManager.getTabsName();
		
		if (!search)
		{
			html.ul((new HtmlAttributes()).add("class", "nav nav-tabs").add("role", "tablist").add("id", entityName+"Tabs"));
			for (String tabName: tabNameList)
			{
				html.li((new HtmlAttributes()).add("role", "presentation"))
				.a((new HtmlAttributes()).add("href", "#"+entityName+"-"+tabName.replace(' ','-' )).add("aria-controls", tabName.replace(' ','-' )).add("role", "tab").add("data-toggle", "tab").add("ng-click","refreshTable"+Utility.getFirstUpper(tabName.replaceAll(" ", ""))+"()"))
				.content(tabName);
				html._li();
				html.script((new HtmlAttributes()).add("type", "text/javascript")).content(JsGenerator.scriptResizeTableTab(tabName, entityName),false);
			}
			html._ul();
			html.div((new HtmlAttributes()).add("class", "tab-content"));
		}
		
		
		for (String tabName: tabNameList)
		{
			 //<div role="tabpanel" class="tab-pane fade in active" id="home">
			if (!search)
				html.div((new HtmlAttributes()).add("role", "tabpanel").add("class", "tab-pane fade").add("id", entityName+"-"+tabName.replace(' ','-' )));
			
			String style="";
			if (entityName.equals("mountain"))
				System.out.println("");
			for (EntityAttribute entityAttribute: entityManager.getFieldByTab())
			{

				if (entityAttribute.getBetweenFilter() && (search))
				{
					entityAttribute.setName(entityAttribute.getName()+"From");
					renderField(html, entityAttribute, search, style, baseEntity);
					entityAttribute.setName(entityAttribute.getName().replace("From","To"));
					renderField(html, entityAttribute, search, style, baseEntity);
				}else
					renderField(html, entityAttribute, search, style, baseEntity);
				
				if (search)
				{
					
						for (EntityAttribute filterField: entityAttribute.getFilterField())
						{
							String filterFieldName=filterField.getParent().getName()+Utility.getFirstUpper(filterField.getName());
							filterField.setName(filterFieldName);
							renderField(html, filterField, search, style, baseEntity);
							
						}
				}
				
			}
			
			
			if (!search)
				html._div();
		}
		if (!search)
			html._div(); //close tab content
		

		html._div();
		
		if (!search)
		{
			html.script((new HtmlAttributes()).add("type", "text/javascript")).content("$('#"+entityName+"Tabs a:first').tab('show');");
		}
		html.div(CssGenerator.getPanelBody());
		if (!search)
		{
			html.div((new HtmlAttributes()).add("class", "pull-left"))
			.form((new HtmlAttributes()).add("id", entityName+"ActionButton").add("ng-if", "selectedEntity.show"))
			.button(CssGenerator.getButton("insert").add("ng-if", "selectedEntity."+entityName+"Id==undefined"))
			.content("Insert")
			.button(CssGenerator.getButton("update").add("ng-if", "selectedEntity."+entityName+"Id>0"))
			.content("Update")
			.button(CssGenerator.getButton("del").add("ng-if", "selectedEntity."+entityName+"Id>0"))
			.content("Delete");
			if (!isParent)
				html.button(CssGenerator.getButton("remove").add("ng-if", "selectedEntity."+entityName+"Id>0"))
				.content("Remove");
			html._form()._div();
		} else
		{
			html.div(CssGenerator.getPanelBody());
			html.div((new HtmlAttributes()).add("class", "pull-left right-input"))
			.button(CssGenerator.getButton("addNew"))
			.content("Add new")
			.button(CssGenerator.getButton("search"))
			.content("Find")
			.button(CssGenerator.getButton("reset"))
			.content("Reset")
			._div()
			._div();
		}
		html._div()._div();
	}

	
	private void renderField(HtmlCanvas html,EntityAttribute entityAttribute, Boolean search,String style,String baseEntity) throws IOException
	{

		if (search && entityAttribute.getIgnoreSearch()) return;
		if (!search && entityAttribute.getIgnoreUpdate()) return;
		
		style= style.equals("pull-left")? "pull-right": "pull-left";
		if (entityAttribute.isEnumField())
		{
			html.div(CssGenerator.getExternalFieldPanel(style, search, entityName, entityAttribute));
			
			html.div(CssGenerator.getInputGroup());
			html.span((new HtmlAttributes()).add("class","input-group-addon")).content(entityAttribute.getName());
			html.select(getFieldHtmlAttributes(entityAttribute, baseEntity, !search, style)
			.add("ng-options", entityAttribute.getName()+ " as "+entityAttribute.getName()+" for "+entityAttribute.getName()+" in childrenList."+entityAttribute.getName()+"List").enctype("UTF-8"));
			html._select();
			html._div();
			if (!search)
			renderValidator(html, entityAttribute);
			html._div();
		}
		else
		{
			if (entityAttribute.asField()!=null)
			{
				
				
				
				html.div(CssGenerator.getExternalFieldPanel(style, search, entityName, entityAttribute));
				html.div(CssGenerator.getInputGroup());
				html.span((new HtmlAttributes()).add("class","input-group-addon")).content(entityAttribute.getName());
				if (getInputType(entityAttribute).equals("checkbox"))
				{
					html.select(getFieldHtmlAttributes(entityAttribute, baseEntity, !search, "").add("ng-options", "value for value in trueFalseValues"))
					._select();
				}else
						html.input(getFieldHtmlAttributes(entityAttribute,baseEntity,!search,""));
				html._div();
				if (!search)
				renderValidator(html,entityAttribute);
				html._div();
				
			} else
				if ( !(parentEntity.contains(entityAttribute.asRelationship().getEntityTarget())))
				{ // entity or list!

					EntityManager entityAttributeManager = new EntityManagerImpl(entityAttribute.asRelationship().getEntityTarget());
					if (search)
					{
						html.div((new HtmlAttributes()).add("class", style+" right-input").add("style","height: 59px;"));
						
						html.div((new HtmlAttributes()).add("class", "input-group"));
						html.span((new HtmlAttributes()).add("class","input-group-addon")).content(entityAttribute.getName());
					
						html.select(CssGenerator.getSelect("").add("ng-model", baseEntity+"."+entityAttribute.getName()+"."+entityAttribute.getName()+"Id")
								.add("id", entityAttribute.getName())
								.add("ng-options", entityAttribute.getName()+"."+entityAttribute.getName()+"Id as "+entityAttributeManager.getDescriptionField()+" for "+entityAttribute.getName()+" in childrenList."+entityAttribute.getName()+"List").enctype("UTF-8"))
								._select();
						html._div()._div();
					} else
					{


						if (entityAttribute.asRelationship().isList())
						{ //list
							
							HtmlCanvas downloadCanvas= new HtmlCanvas();
							downloadCanvas
							.button(CssGenerator.getButton("show"+Utility.getFirstUpper(entityAttribute.getName())+"Detail"," pull-right").add("style", "margin-top: -7px"))
							.content("Add new "+entityAttribute.getName())
							//<button type="button" class="btn btn-info btn-lg" data-toggle="modal" 
							//data-target="#myModal">Open Modal</button>
							.button((new HtmlAttributes()).add("type", "button").add("class", "btn btn-default pull-right").add("style", "margin-top: -7px").add("data-toggle", "modal").add("data-target", "#"+entityName+"-"+entityAttribute.getName()))
							.content("Link existing")
							.button(CssGenerator.getButton("download"+Utility.getFirstUpper(entityAttribute.getName())+"List","pull-right").add("style", "margin-top:-7px"))
							.span((new HtmlAttributes()).add("class", "glyphicon glyphicon-download-alt").add("aria-hidden", "true"))
							._span()
							._button();
							style="pull-left";
							renderModalInsertExistingPanel(html,entityAttribute);
							html.br().br();
							html.div((new HtmlAttributes()).add("class", style).add("style", "width: 100%"));
							//html._div();
							html.div(CssGenerator.getPanel())
							.div(CssGenerator.getPanelHeader())
							.content(entityAttribute.getName()+downloadCanvas.toHtml(),false);
							html.div(CssGenerator.getPanelBody().add("ng-class","{'has-error': !"+entityName+"DetailForm."+entityAttribute.getName()+".$valid, 'has-success': "+entityName+"DetailForm."+entityAttribute.getName()+".$valid}"))
							.label((new HtmlAttributes()).add("id", entityAttribute.getName())).content(entityAttribute.getName());
							//.button(CssGenerator.getButton("show"+Utility.getFirstUpper(field.getName())+"Detail"))
							//.content("Add new "+field.getName());
							html.div((new HtmlAttributes()).add("id",entityAttribute.getName()).add("ng-if", "selectedEntity."+entityAttribute.getName()+"List.length>0"))
							.div((new HtmlAttributes()).add("style","top: 100px").add("ui-grid", entityAttribute.getName()+"ListGridOptions").add("ui-grid-pagination", "").add("ui-grid-selection",""))
							._div();
							renderValidator(html,entityAttribute);
							html._div()._div();
							
							html._div();
							
							
							html._div();
							//html.div(CssGenerator.getPanelBody());
						}else
						{//entity
							html.div(CssGenerator.getExternalFieldPanel(style, search, entityName, entityAttribute));
							html.div((new HtmlAttributes()).add("class", "input-group"));
							html.span((new HtmlAttributes()).add("class", "input-group-addon")).content(entityAttribute.getName());
							html.select(CssGenerator.getSelect("").add("ng-model", "selectedEntity."+entityAttribute.getName())
									.add("id", entityAttribute.getName())
									.add("name", entityAttribute.getName())
									.add("ng-options", entityAttribute.getName()+" as "+entityAttributeManager.getDescriptionField()+" for "+entityAttribute.getName()+" in childrenList."+entityAttribute.getName()+"List track by "+entityAttribute.getName()+"."+Utility.getEntityCallName(entityAttribute.getName())+"Id").enctype("UTF-8"))
									._select();
							renderValidator(html,entityAttribute);
							html.span((new HtmlAttributes()).add("class", "input-group-btn"))
							.button(CssGenerator.getButton("show"+Utility.getFirstUpper(entityAttribute.getName())+"Detail").add("id",entityAttribute.getName()).add("ng-if", "selectedEntity."+entityAttribute.getName()+"==null"))
							.content("Add new "+entityAttribute.getName())
							.button(CssGenerator.getButton("show"+Utility.getFirstUpper(entityAttribute.getName())+"Detail").add("id",entityAttribute.getName()).add("ng-if", "selectedEntity."+entityAttribute.getName()+"!=null"))
							.content("Show detail")
							._span();
							html._div();
							html._div();


						}
					}
				}
		}
	
	
	}
	
	
	
	private void renderModalInsertExistingPanel(HtmlCanvas html, EntityAttribute entityAttribute)
	{
		/*<!-- Modal -->
      <div class="modal-body">
        <p>Some text in the modal.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>*/
		try {
			EntityManager entityAttributeManager = new EntityManagerImpl(entityAttribute.asRelationship().getEntityTarget());
			html.div((new HtmlAttributes()).add("id", entityName+"-"+entityAttribute.getName()).add("class", "modal fade").add("role", "dialog"))
			.div((new HtmlAttributes()).add("class", "modal-dialog"))
			.div((new HtmlAttributes()).add("class", "modal-content"))
			.div((new HtmlAttributes()).add("class", "modal-header"))
			.button((new HtmlAttributes()).add("type", "button").add("class", "close").add("data-dismiss", "modal")).content("&times;",false)
			.h4((new HtmlAttributes()).add("class", "modal-title")).content("Modal header")
			._div()
			.div((new HtmlAttributes()).add("class", "modal-body"))
			.p().content("some text");
			
			html.div((new HtmlAttributes()).add("class", "input-group"));
			html.span((new HtmlAttributes()).add("class", "input-group-addon")).content(entityAttribute.getName());
			html.select(CssGenerator.getSelect("").add("ng-model", "selectedEntity."+entityAttribute.getName())
					.add("id", entityAttribute.getName())
					.add("name", entityAttribute.getName())
					.add("ng-options", entityAttribute.getName()+" as "+entityAttributeManager.getDescriptionField()+" for "+entityAttribute.getName()+" in childrenList."+entityAttribute.getName()+"List track by "+entityAttribute.getName()+"."+entityAttribute.getName()+"Id").enctype("UTF-8"))
					._select();
			renderValidator(html,entityAttribute);
			html.span((new HtmlAttributes()).add("class", "input-group-btn"))
			.button(CssGenerator.getButton("saveLinked"+Utility.getFirstUpper(entityAttribute.getName())+"").add("id",entityAttribute.getName()))
			.content("Save")
			._span();
			html._div();
			
			html._div()
			.div((new HtmlAttributes()).add("class", "modal-footer"))
			.button(CssGenerator.getButton("close").add("data-dismiss", "modal")).content("close")
			._div()
			._div()
			._div()
			._div();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*private void renderSearchForm(HtmlCanvas html,String baseEntity)
	{ 
		try {
			html.div(CssGenerator.getPanel());
			html.div(CssGenerator.getPanelHeader())
			.content("Search form "+entityName);
			html.div(CssGenerator.getPanelBody());
			String style="";
			for (Field field: fieldList)
			{
				if (ReflectionManager.hasIgnoreSearch(field)) 
					continue;
				style= style.equals("pull-left")? "pull-right": "pull-left";
				html.div((new HtmlAttributes()).add("class", style+" right-input").add("style","height: 59px;"));
				
				html.div((new HtmlAttributes()).add("class", "input-group"));
				html.span((new HtmlAttributes()).add("class","input-group-addon")).content(field.getName());
				
				if (field.getIsEnum())
				{
					//.label((new HtmlAttributes()).add("id", field.getName())).content(field.getName());
					html.select(CssGenerator.getSelect("").add("ng-model", baseEntity+"."+field.getName())
							.add("id", field.getName())
							.add("ng-options", field.getName()+" as "+field.getName()+" for "+field.getName()+" in childrenList."+field.getName()+"List").enctype("UTF-8"));
							html._select();

				}
				else
				{
					if (field.getCompositeClass()==null)
					{

						//.p()
						//.content(field.getName())
						
						//.label((new HtmlAttributes()).add("id", field.getName())).content(field.getName());
						if (getInputType(field).equals("checkbox"))
						{
							//html.div((new HtmlAttributes()).add("class", "input-group"))
							html.select((new HtmlAttributes()).add("class", "form-control").add("ng-model", baseEntity+"."+field.getName()).add("name", field.getName()).add("ng-options", "value for value in trueFalseValues"))
							._select();
						}else
						html.input(getFieldHtmlAttributes(field,baseEntity,false,""));
						
						//html._div();

					} else
					{
						//.label((new HtmlAttributes()).add("id", field.getName())).content(field.getName());
						html.select(CssGenerator.getSelect("").add("ng-model", baseEntity+"."+field.getName()+"."+field.getName()+"Id")
								.add("id", field.getName())
								.add("ng-options", field.getName()+"."+field.getName()+"Id as "+reflectionManager.getDescriptionField(field.getFieldClass())+" for "+field.getName()+" in childrenList."+field.getName()+"List").enctype("UTF-8"))
								._select();

					}
				}
				
				html._div()._div();
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
*/
	//useless
/*	private void renderItem(HtmlCanvas html,String baseEntity)
	{
		StringBuilder itemContent= new StringBuilder();
		itemContent.append("{{$index}} \n");
		for (Field field : fieldList)
		{
			if (field.getCompositeClass()==null)
			{
				itemContent.append("{{"+baseEntity+"."+field.getName()+"");
				if (reflectionManager.isDateField(field))
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
	}*/
	/*private void renderDetail(HtmlCanvas html) throws IOException {
		html.div(CssGenerator.getPanel());
		html.div(CssGenerator.getPanelHeader())
		.content("Detail "+entityName+" {{ selectedEntity."+entityName+"Id }}");
		html.div(CssGenerator.getPanelBody());
		String style="";
		for (Field field: fieldList)
		{
			if (ReflectionManager.hasIgnoreUpdate(field)) continue;
			style= style.equals("pull-left")? "pull-right": "pull-left";
			if (field.getIsEnum())
			{
				html.div((new HtmlAttributes()).add("class", style+" right-input"));
				html.div((new HtmlAttributes()).add("class", "input-group").add("ng-class","{'has-error': !"+entityName+"DetailForm."+field.getName()+".$valid, 'has-success': "+entityName+"DetailForm."+field.getName()+".$valid}"));
				html.span((new HtmlAttributes()).add("class","input-group-addon")).content(field.getName());
				//.label((new HtmlAttributes()).add("id", field.getName())).content(field.getName());
				html.select(getFieldHtmlAttributes(field, "selectedEntity", true, style)
				.add("ng-options", field.getName()+ " as "+field.getName()+" for "+field.getName()+" in childrenList."+field.getName()+"List").enctype("UTF-8"));

				html._select();
				html._div();
				renderValidator(html, field);
				html._div();
			}
			else
			{
				if (reflectionManager.isKnownClass(field.getFieldClass()))
				{

					//html.p()
					//.content(field.getName())
					html.div((new HtmlAttributes()).add("class", style+" right-input").add("style","height: 59px;").add("ng-class","{'has-error': !"+entityName+"DetailForm."+field.getName()+".$valid, 'has-success': "+entityName+"DetailForm."+field.getName()+".$valid}"));
					html.div((new HtmlAttributes()).add("class", "input-group"));
				html.span((new HtmlAttributes()).add("class","input-group-addon")).content(field.getName());
				//.input(getFieldHtmlAttributes(field,"selectedEntity",true,""));
					if (getInputType(field).equals("checkbox"))
					{
						//html.div((new HtmlAttributes()).add("class", "input-group"))
						html.select(getFieldHtmlAttributes(field, "selectedEntity", true, "").add("ng-options", "value for value in trueFalseValues"))
						._select();
						//.span((new HtmlAttributes()).add("class", "input-group-btn"))
						//.button((new HtmlAttributes()).add("class", "btn btn-default")).content(field.getName())
						//._span()
						//._div();
					}else
					html.input(getFieldHtmlAttributes(field,"selectedEntity",true,""));
					

					html._div();
					renderValidator(html,field);
					html._div();
				} else
					if (field.getCompositeClass()!=null  && !(parentClass.contains(field.getFieldClass())))
					{ // entity or list!
						if (field.getCompositeClass().fullName().contains("java.util.List"))
						{ //list
							//html.div((new HtmlAttributes()).add("class", style))
							//.label((new HtmlAttributes()).add("for", field.getName()))
							//.content(field.getName())
							html._div();
							html.div(CssGenerator.getPanelBody().add("ng-class","{'has-error': !"+entityName+"DetailForm."+field.getName()+".$valid, 'has-success': "+entityName+"DetailForm."+field.getName()+".$valid}"))
							.label((new HtmlAttributes()).add("id", field.getName())).content(field.getName())
									
							
							
							.button(CssGenerator.getButton("show"+Utility.getFirstUpper(field.getName())+"Detail"))
							.content("Add new "+field.getName());
							html.div((new HtmlAttributes()).add("id",field.getName()).add("ng-if", "selectedEntity."+field.getName()+"List.length>0"))

							.div((new HtmlAttributes()).add("style","top: 100px").add("ui-grid", field.getName()+"ListGridOptions").add("ui-grid-pagination", "").add("ui-grid-selection",""))
							._div();
							renderValidator(html,field);
							//html.content("{{$index}}--{{entity."+field.getName()+"Id}}--{{entity.description}}");
							html._div()._div();//._div();
							html.div(CssGenerator.getPanelBody());
						}else
						{//entity
							html.div((new HtmlAttributes()).add("class", style+" right-input").add("ng-class","{'has-error': !"+entityName+"DetailForm."+field.getName()+".$valid, 'has-success': "+entityName+"DetailForm."+field.getName()+".$valid}"));
							
							html.div((new HtmlAttributes()).add("class", "input-group"));
				//html.span((new HtmlAttributes()).add("class","input-group-addon")).content(field.getName());
				
							html.select(CssGenerator.getSelect("").add("ng-model", "selectedEntity."+field.getName())
									.add("id", field.getName())
									.add("name", field.getName())
									.add("ng-options", field.getName()+" as "+reflectionManager.getDescriptionField(field.getFieldClass())+" for "+field.getName()+" in childrenList."+field.getName()+"List track by "+field.getName()+"."+field.getName()+"Id").enctype("UTF-8"))
									._select();
									
							renderValidator(html,field);
							

							html.span((new HtmlAttributes()).add("class", "input-group-btn"))
							.button(CssGenerator.getButton("show"+Utility.getFirstUpper(field.getName())+"Detail").add("id",field.getName()).add("ng-if", "selectedEntity."+field.getName()+"==null"))
							.content("Add new "+field.getName())
							.button(CssGenerator.getButton("show"+Utility.getFirstUpper(field.getName())+"Detail").add("id",field.getName()).add("ng-if", "selectedEntity."+field.getName()+"!=null"))
									//.p((new HtmlAttributes()).add("ng-click", "show"+Utility.getFirstUpper(field.getName())+"Detail()").add("ng-if", "selectedEntity."+field.getName()+"!=null"))
									.content("Show detail")
							._span();

							html._div();
							html._div();


						}
					}
			}
		
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
		
	//	<p ng-click="showPlaceDetail()" >Add new place 
	//	</p>
	//	<div ng-if="selectedEntity.placeList.length>0">
	//		<ul>
	//			<li ng-repeat="entity in selectedEntity.placeList" ng-click="showPlaceDetail($index)">{{$index}}--{{entity.placeId}}--{{entity.description}}</li>
	//		</ul>
	//	</div>
		 
	}*/
}
