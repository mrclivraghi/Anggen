package it.anggen.generation.frontend;

import it.anggen.reflection.EntityManager;
import it.anggen.reflection.EntityManagerImpl;
import it.anggen.utils.EntityAttribute;
import it.anggen.utils.ReflectionManager;
import it.anggen.utils.Utility;
import it.anggen.generation.Generator;
import it.anggen.model.entity.Entity;
import it.anggen.model.entity.EntityGroup;
import it.anggen.model.field.Field;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.rendersnake.DocType;
import org.rendersnake.HtmlAttributes;
import org.rendersnake.HtmlCanvas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
/**
 * Prepare the html template and fill it with angular code and html elements.
 * @author Marco
 *
 */
@Service
public class FrontHtmlGenerator {
	
	@Autowired
	Generator generator;
	
	private List<EntityAttribute> attributeList;
	
	private List<Entity> childrenEntity;
	
	private String entityName;

	private static String directoryViewPages;
	
	private static String directoryAngularFiles;
	
	private EntityManager entityManager;
	
	private Entity entity;
	
	public static  DocType docType= DocType.HTML5;
	
	
	@Autowired
	FrontJsGenerator frontJsGenerator;
	
	public FrontHtmlGenerator(){
		
	}
	
	public void init(Entity entity)
	{
		this.entity=entity;
		this.entityManager = new EntityManagerImpl(entity);
		this.entityName=Utility.getFirstLower(entity.getName());
		this.attributeList=entityManager.getAttributeList();
		this.childrenEntity=entityManager.getChildrenEntities();
		File file = new File(""); 
		directoryViewPages = file.getAbsolutePath()+generator.htmlDirectory+"/";
		directoryAngularFiles=file.getAbsolutePath()+generator.angularDirectory+generator.applicationName+"/";
	}
	
	
	
	/**
	 * Include the functional js scripts
	 * @param html
	 */
	public void includeJavascriptScripts(HtmlCanvas html,Boolean includeEntityFile)
	{
		
		try {
			//js
			html
			.macros().javascript("../js/jquery-1.9.1.js")
			.macros().javascript("../js/jquery-ui.js")
			.macros().javascript("../js/angular.js")
			.macros().javascript("../js/angular-touch.js")
			.macros().javascript("../js/angular-animate.js")
			.macros().javascript("../js/csv.js")
			.macros().javascript("../js/pdfmake.js")
			.macros().javascript("../js/vfs_fonts.js")
			.macros().javascript("../js/ui-grid.js");
			if (includeEntityFile)
				html.macros().javascript("../js/angular/"+generator.applicationName+"/"+entityName+"-front.js");
			html.macros().javascript("../js/date.js")
			.macros().javascript("../js/utility.js")
			.macros().javascript("../js/bootstrap.min.js");
			} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void incluseCssFiles(HtmlCanvas html)
	{
		try {
			html.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "../css/ui-grid.css"))
			.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "../css/bootstrap.min.css"))
			.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "../css/main.css"))
			.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "../css/jquery-ui.css"))
			.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "../css/easytree/skin-win8/ui.easytree.css"))
			.link((new HtmlAttributes()).add("rel","import").add("href", "../"+generator.applicationName+generator.menuName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/**
	 * Generate the main template of the page
	 * @throws IllegalAccessException
	 */
	public void generateJSP() throws IllegalAccessException
	{
		HtmlCanvas html = new HtmlCanvas();
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		frontJsGenerator.init(entity, true, null, null,entityManager.isLastLevel(entity),null);
		
		try {
			html.render(docType);
			html.
			html()
			.head()
			.title().content(entityName);
			includeJavascriptScripts(html,true);
			incluseCssFiles(html);
			frontJsGenerator.saveJsToFile(directoryAngularFiles);
			html._head()
			.body(htmlAttributes.add("ng-app", Utility.getFirstLower(entityName)+"App"));
			html.div((new HtmlAttributes()).add("id", "alertInfo").add("class","alert alert-success custom-alert").add("style","display: none")).span().content("")._div();
			html.div((new HtmlAttributes()).add("id", "alertError").add("class","alert alert-danger custom-alert").add("style","display: none")).span().content("")._div();
			
			//TODO switch
			String loadMenuScript="loadMenu(); ";
			html.script((new HtmlAttributes()).add("type", "text/javascript")).content(loadMenuScript,false);
			if (generator.easyTreeMenu)
				html.script().content("function stateChanged(nodes, nodesJson) {var t = nodes[0].text; $.cookie('menu', nodesJson); };  var easyTree = $('#menu').easytree({data: ($.cookie('menu')!=null? $.cookie('menu') : null), stateChanged: stateChanged});",false);
			html._body()._html();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		File dir = new File(directoryViewPages);
		if (!dir.exists())
			dir.mkdirs();
		
		File myJsp=new File(directoryViewPages+entityName+"-front.jsp");
		PrintWriter writer;
		try {
			System.out.println("Written "+myJsp.getAbsolutePath());
			writer = new PrintWriter(myJsp, "UTF-8");
			writer.write(html.toHtml());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
}
