package it.anggen.generation;

import it.anggen.reflection.EntityAttributeManager;
import it.anggen.utils.EntityAttribute;
import it.anggen.utils.Field;

import org.rendersnake.HtmlAttributes;
import org.rendersnake.internal.CharactersWriteable;
/**
 * Class to render the base html attributes of the main components
 * 
 * @author Marco
 *
 */
public  class CssGenerator {
	
	

	/**
	 * Default button style
	 * @param function
	 * @return
	 */
	public static HtmlAttributes getButton(String function)
	{
		return CssGenerator.getButton(function, "");
	}
	
	/**
	 * Button element with custom additional classes
	 * 
	 * @param function
	 * @param otherClasses
	 * @return
	 */
	public static HtmlAttributes getButton(String function,String otherClasses)
	{
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		htmlAttributes.add("ng-click", function+"()")
		.add("class", "btn btn-default "+otherClasses);
		return htmlAttributes;
	}
	
	/**
	 * default input style
	 * @param style
	 * @return
	 */
	public static HtmlAttributes getInput(String style)
	{
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		htmlAttributes.add("class", "form-control "+style);
		htmlAttributes.add("aria-describedby", "sizing-addon3");
		return htmlAttributes;
	}
	/**
	 * Default select style
	 * @param style
	 * @return
	 */
	public static HtmlAttributes getSelect(String style)
	{
		return CssGenerator.getInput(style);
	}
	/**
	 * Default panel style
	 * @return
	 */
	public static HtmlAttributes getPanel()
	{
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		htmlAttributes.add("class", "panel panel-default default-panel");
		return htmlAttributes;
	}
	/**
	 * Default panel header style
	 * @return
	 */
	public static HtmlAttributes getPanelHeader() {
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		htmlAttributes.add("class", "panel-heading");
		return htmlAttributes;
	}
	/**
	 * Default panel body style
	 * @return
	 */
	public static HtmlAttributes getPanelBody() {
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		htmlAttributes.add("class", "panel-body");
		return htmlAttributes;
	}
	/**
	 * Default nav style
	 * @return
	 */
	public static HtmlAttributes getNav() {
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		htmlAttributes.add("class", "navbar navbar-default");
		return htmlAttributes;
	}

	/**
	 * Default input group style
	 * @return
	 */
	
	public static HtmlAttributes getInputGroup()
	{
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		htmlAttributes.add("class", "input-group");
		return htmlAttributes;
	}
	/**
	 * External field panel
	 * @param style
	 * @param search
	 * @param entityName
	 * @param entityAttribute
	 * @return
	 */
	public static HtmlAttributes getExternalFieldPanel(String style,Boolean search, String entityName,EntityAttribute entityAttribute)
	{
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		String entityAttributeName= (EntityAttributeManager.getInstance(entityAttribute).isRelationship()? EntityAttributeManager.getInstance(entityAttribute).asRelationship().getEntityTarget().getName(): entityAttribute.getName());
		htmlAttributes.add("class", style+" right-input");
		if (!search)
		{
			htmlAttributes.add("ng-class","{'has-error': !"+entityName+"DetailForm."+entityAttributeName+".$valid, 'has-success': "+entityName+"DetailForm."+entityAttributeName+".$valid}");
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
