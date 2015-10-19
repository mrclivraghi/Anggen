package it.polimi.generation;

import it.polimi.utils.Field;

import org.rendersnake.HtmlAttributes;
import org.rendersnake.internal.CharactersWriteable;

public  class CssGenerator {
	
	

	public static HtmlAttributes getButton(String function)
	{
		return CssGenerator.getButton(function, "");
	}
	
	
	public static HtmlAttributes getButton(String function,String otherClasses)
	{
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		htmlAttributes.add("ng-click", function+"()")
		.add("class", "btn btn-default "+otherClasses);
		return htmlAttributes;
	}
	
	public static HtmlAttributes getInput(String style)
	{
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		htmlAttributes.add("class", "form-control "+style);
		htmlAttributes.add("aria-describedby", "sizing-addon3");
		return htmlAttributes;
	}
	
	public static HtmlAttributes getSelect(String style)
	{
		return CssGenerator.getInput(style);
	}
	
	public static HtmlAttributes getPanel()
	{
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		htmlAttributes.add("class", "panel panel-default default-panel");
		return htmlAttributes;
	}

	public static HtmlAttributes getPanelHeader() {
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		htmlAttributes.add("class", "panel-heading");
		return htmlAttributes;
	}
	
	public static HtmlAttributes getPanelBody() {
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		htmlAttributes.add("class", "panel-body");
		return htmlAttributes;
	}

	public static HtmlAttributes getNav() {
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		htmlAttributes.add("class", "navbar navbar-default");
		return htmlAttributes;
	}


	
	public static HtmlAttributes getInputGroup(Boolean search,String entityName,Field field)
	{
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		htmlAttributes.add("class", "input-group");
		/*if (!search)
		{
			htmlAttributes.add("ng-class","{'has-error': !"+entityName+"DetailForm."+field.getName()+".$valid, 'has-success': "+entityName+"DetailForm."+field.getName()+".$valid}");
			//htmlAttributes.add("style", "height:59px");
		}*/
		
		return htmlAttributes;
	}
	
	public static HtmlAttributes getExternalFieldPanel(String style,Boolean search, String entityName,Field field)
	{
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		htmlAttributes.add("class", style+" right-input");
		if (!search)
		{
			htmlAttributes.add("ng-class","{'has-error': !"+entityName+"DetailForm."+field.getName()+".$valid, 'has-success': "+entityName+"DetailForm."+field.getName()+".$valid}");
			htmlAttributes.add("style", "height:59px");
		}
		
		return htmlAttributes;
	}
	
	/* public static HtmlAttributes getInput(String style, boolean formControl) {
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		htmlAttributes.add("aria-describedby", "sizing-addon3");
		if (formControl)
			htmlAttributes.add("class", "form-control "+style );
		return htmlAttributes;
	}*/
	
	
}
