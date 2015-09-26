package it.polimi.utils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.rendersnake.HtmlAttributes;
import org.rendersnake.HtmlCanvas;

public class HtmlGenerator {
	String entityName;
	
	Boolean isParent;
	
	List<Field> fieldList;
	
	public HtmlGenerator(Class myClass,Boolean isParent)
	{
		this.entityName=ReflectionManager.parseName(myClass.getName());
		this.isParent=isParent;
		try {
			fieldList= ReflectionManager.generateField(myClass.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void generateEntityView(HtmlCanvas html) throws IOException {
		html.div((new HtmlAttributes()).add("ng-controller", entityName+"Controller"));
		//search bean
		if (isParent)
		{
			html.div((new HtmlAttributes()).add("id", entityName+"SearchBean"));
			renderForm(html,"searchBean");
			html.button((new HtmlAttributes()).add("ng-click", "addNew()"))
				.content("Add new")
				.button((new HtmlAttributes()).add("ng-click", "search()"))
				.content("Find")
				.button((new HtmlAttributes()).add("ng-click", "reset()"))
				.content("Reset");
			html._div();
		}
		//list
		html.div((new HtmlAttributes()).add("id", entityName+"List").add("ng-if", "entityList.length>0").enctype("UTF-8"))
		.p().content("LISTA")
		.ul()
		.li((new HtmlAttributes()).add("ng-repeat", "entity in entityList").add("ng-click", "showEntityDetail($index)"))
		.p();
		renderItem(html,"entity");
		
		html._li()._ul()._div();
		//detail
		html.div((new HtmlAttributes()).add("id", entityName+"Detail").add("ng-if", "selectedEntity.show"))
			.p().content("DETAIL");
		renderDetail(html);
		html._div();
		html.div((new HtmlAttributes()).add("id", entityName+"ActionButton").add("ng-if", "selectedEntity.show"))
		.p().content("ACTION BUTTON")
			.button((new HtmlAttributes()).add("ng-click", "insert()").add("ng-if", "selectedEntity."+entityName+"Id==undefined"))
				.content("Insert")
				.button((new HtmlAttributes()).add("ng-click", "update()").add("ng-if", "selectedEntity."+entityName+"Id>0"))
				.content("Update")
				.button((new HtmlAttributes()).add("ng-click", "del()").add("ng-if", "selectedEntity."+entityName+"Id>0"))
				.content("Delete");
		html._div();
		html._div();
	}

	private void renderDetail(HtmlCanvas html) throws IOException {
		for (Field field: fieldList)
		{
			if (ReflectionManager.isKnownClass(field.getFieldClass()))
			{
				String type= (field.getFieldClass()==Date.class ? "date" : "text");
				html.p()
				.content(field.getName())
				.input((new HtmlAttributes()).add("type", type).add("ng-model", "selectedEntity."+field.getName()));
			} else
				//TODO block after 2°level improve?
				if (field.getCompositeClass()!=null  && isParent)
				{ // entity or list!
					if (field.getCompositeClass().fullName().contains("java.util.List"))
					{ //list
						html.p((new HtmlAttributes()).add("ng-click", "show"+Generator.getFirstUpper(field.getName())+"Detail()"))
						.content("Add new "+field.getName());
						html.div((new HtmlAttributes()).add("ng-if", "selectedEntity."+field.getName()+"List.length>0"))
						.ul()
						.li((new HtmlAttributes()).add("ng-repeat", "entity in selectedEntity."+field.getName()+"List").add("ng-click", "show"+Generator.getFirstUpper(field.getName())+"Detail($index)"));
						html.content("{{$index}}--{{entity."+field.getName()+"Id}}--{{entity.description}}");
						html._ul();
					}else
					{//entity
						html.p((new HtmlAttributes()).add("ng-click", "show"+Generator.getFirstUpper(field.getName())+"Detail()").add("ng-if", "selectedEntity."+field.getName()+"==null"))
						.content("Add new "+field.getName());
						html.p((new HtmlAttributes()).add("ng-click", "show"+Generator.getFirstUpper(field.getName())+"Detail()").add("ng-if", "selectedEntity."+field.getName()+"!=null"))
						.content(field.getName()+": {{selectedEntity."+field.getName()+"."+field.getName()+"Id}}");
						
						
					}
				}
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

	private void renderForm(HtmlCanvas html,String baseEntity)
	{ // TODO mgmt readonly on p key
		for (Field field: fieldList)
		{
			if (field.getCompositeClass()==null)
			{
				try {
					String type= field.getFieldClass()==java.sql.Date.class ? "date" : "text";
					String fieldForm=baseEntity+"."+Generator.getFirstLower(field.getName());
					html.p()
					.content(field.getName())
					.input((new HtmlAttributes()).add("type", type).add("ng-model", fieldForm));
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
