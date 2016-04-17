package it.anggen.generation;

import it.anggen.reflection.EntityAttributeManager;
import it.anggen.utils.EntityAttribute;
import it.anggen.utils.Field;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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
		if (!function.isEmpty())
			htmlAttributes.add("ng-click", function+"()");
		htmlAttributes.add("class", "btn btn-default "+otherClasses);
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
		if (EntityAttributeManager.getInstance(entityAttribute).asField()!=null && EntityAttributeManager.getInstance(entityAttribute).isEmbedded()) 
			htmlAttributes.add("class", style); // if embedded create a new row
		else
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
	
	public static void generateMain(String angularDirectory)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("@CHARSET \"ISO-8859-1\";\n");
		sb.append(".default-panel{\n");
		sb.append("	width: 90%;\n");
		sb.append("	margin-left: 5%;AAAAAAAAAAAAAAAAAAA\n");
		sb.append("}\n");
		sb.append(".right-input{\n");
		sb.append("	width:45%;\n");
		sb.append("	/*float: right;*/\n");
		sb.append("	margin-top:15px;\n");
		sb.append("	}\n");

		sb.append(".left-input{\n");
		sb.append("	width:45%;\n");
		sb.append("	float: left;\n");
		sb.append("}\n");

		sb.append(".custom-alert{\n");
		sb.append("	position:absolute;\n");
		sb.append("	width:200px;\n");
		sb.append("	height:80px;\n");
		sb.append("	right: 50px;\n");
		sb.append("	bottom: 20px;\n");
		sb.append("}\n");

		File file = new File("");
		String directory= file.getAbsolutePath()+angularDirectory+"customLib/";
		File dir = new File(directory);
		if (!dir.exists())
			dir.mkdirs();
		file = new File(directory+"main.css");
		PrintWriter writer;
		try {
			writer = new PrintWriter(file, "UTF-8");
			writer.write(sb.toString());
			writer.close();
			System.out.println("written css file "+file.getAbsolutePath());
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void generateLoginSCSS(String angularDirectory)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("@CHARSET \"ISO-8859-1\";\n");
		sb.append("/* sign in */\n");
		sb.append(".form-signin {\n");
		sb.append("  max-width: 330px;\n");
		sb.append("  padding: 15px;\n");
		sb.append("  margin: 0 auto;\n");
		sb.append("}\n");
		sb.append(".form-signin .form-signin-heading,\n");
		sb.append(".form-signin .checkbox {\n");
		sb.append("  margin-bottom: 10px;\n");
		sb.append("}\n");
		sb.append(".form-signin .checkbox {\n");
		sb.append("  font-weight: normal;\n");
		sb.append("}\n");
		sb.append(".form-signin .form-control {\n");
		sb.append("  position: relative;\n");
		sb.append(" height: auto;\n");
		sb.append("  -webkit-box-sizing: border-box;\n");
		sb.append("     -moz-box-sizing: border-box;\n");
		sb.append("          box-sizing: border-box;\n");
		sb.append("  padding: 10px;\n");
		sb.append("  font-size: 16px;\n");
		sb.append("}\n");
		sb.append(".form-signin .form-control:focus {\n");
		sb.append("  z-index: 2;\n");
		sb.append("}\n");
		sb.append(".form-signin input[type=\"text\"] {\n");
		sb.append("  margin-bottom: -1px;\n");
		sb.append("  border-bottom-right-radius: 0;\n");
		sb.append("  border-bottom-left-radius: 0;\n");
		sb.append("}\n");
		sb.append(".form-signin input[type=\"password\"] {\n");
		sb.append("  margin-bottom: 10px;\n");
		sb.append("  border-top-left-radius: 0;\n");
		sb.append("  border-top-right-radius: 0;\n");
		sb.append("}\n");

		File file = new File("");
		String directory= file.getAbsolutePath()+angularDirectory+"login/";
		File dir = new File(directory);
		if (!dir.exists())
			dir.mkdirs();
		file = new File(directory+"login.scss");
		PrintWriter writer;
		try {
			writer = new PrintWriter(file, "UTF-8");
			writer.write(sb.toString());
			writer.close();
			System.out.println("written css file "+file.getAbsolutePath());
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
