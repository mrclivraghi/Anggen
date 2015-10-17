package it.polimi.generation;

import org.rendersnake.HtmlAttributes;
import org.rendersnake.internal.CharactersWriteable;

public  class CssGenerator {
	
	public static HtmlAttributes getButton(String function)
	{
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		htmlAttributes.add("ng-click", function+"()")
		.add("class", "btn btn-default");
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
	
	
}
