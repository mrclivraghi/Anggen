package it.anggen.generation;

import it.anggen.reflection.EntityManager;
import it.anggen.reflection.EntityManagerImpl;
import it.anggen.utils.EntityAttribute;
import it.anggen.utils.ReflectionManager;
import it.anggen.utils.Utility;
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
public class HtmlGenerator {
	
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
	AngularGenerator angularGenerator;
	
	@Autowired
	JsGenerator jsGenerator;
	
	public HtmlGenerator(){
		
	}
	
	public void init(Entity entity)
	{
		this.entity=entity;
		this.entityManager = new EntityManagerImpl(entity);
		this.entityName=Utility.getFirstLower(entity.getName());
		this.attributeList=entityManager.getAttributeList();
		this.childrenEntity=entityManager.getChildrenEntities();
		setDirectory();
		}
	
	
	public void setDirectory()
	{
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
			.macros().javascript("js/jquery-1.9.1.js")
			.macros().javascript("js/jquery-ui.js")
			.macros().javascript("js/angular.js")
			.macros().javascript("js/angular-touch.js")
			.macros().javascript("js/angular-animate.js")
			.macros().javascript("js/csv.js")
			.macros().javascript("js/pdfmake.js")
			.macros().javascript("js/vfs_fonts.js")
			.macros().javascript("js/angular-route.js")
			.macros().javascript("js/ui-grid.js");
			if (includeEntityFile)
			{
				html.macros().javascript("js/angular/"+generator.applicationName+"/main-app.js");
				html.macros().javascript("js/angular/"+generator.applicationName+"/"+generator.applicationName+"-services.js");
				html.macros().javascript("js/angular/"+generator.applicationName+"/"+generator.applicationName+"-controller.js");
			}
			
			html.macros().javascript("js/date.js")
			.macros().javascript("js/utility.js");
			
			if (generator.easyTreeMenu)
				html.macros().javascript("js/jquery.easytree.js");
			
			html.macros().javascript("js/jquery.cookie.js")
			.macros().javascript("js/bootstrap.min.js")
			.macros().javascript("js/alasql.min.js")
			.macros().javascript("js/ng-file-upload-all.js");
			
			html.script((new HtmlAttributes()).add("type", "text/javascript"))
			.content(" angular.element(document.getElementsByTagName('head')).append(angular.element('<base href=\"' + window.location.pathname + '\" />'));",false);
			
			} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void incluseCssFiles(HtmlCanvas html)
	{
		try {
			html.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "css/ui-grid.css"))
			.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "css/bootstrap.min.css"))
			.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "css/main.css"))
			.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "css/jquery-ui.css"))
			.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "css/easytree/skin-win8/ui.easytree.css"))
			.link((new HtmlAttributes()).add("rel","import").add("href", ""+generator.applicationName+generator.menuName));
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
		angularGenerator.init(entity, true,new ArrayList<Entity>(),entityManager.isLastLevel(entity));
		try {
			angularGenerator.generateEntityView(html);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File dir = new File(directoryViewPages);
		if (!dir.exists())
			dir.mkdirs();
		
		File myJsp=new File(directoryViewPages+entityName+".jsp");
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
	
	public void GenerateEasyTreeMenu(List<EntityGroup> entityGroupList)
	{
		HtmlCanvas html= new HtmlCanvas();
		try {
			html.div((new HtmlAttributes()).add("id", "menu").add("style", "width: 250px;"))
			.ul();
			
			for (EntityGroup entityGroup: entityGroupList)
			{
				html.li((new HtmlAttributes()).add("class", "isFolder"));
				HtmlCanvas folderHtml = new HtmlCanvas();
				folderHtml.ul();
				for (Entity entity: entityGroup.getEntityList())
				{
						folderHtml.li().a((new HtmlAttributes()).add("href", "../"+Utility.getFirstLower(entity.getName())+"/")).content(Utility.getFirstUpper(entity.getName()))._li();
				}
				folderHtml._ul();
				html.content(entityGroup.getName()+folderHtml.toHtml(),false);
			}
			html._ul()._div();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File file = new File(""); 
		String directoryViewPages = file.getAbsolutePath()+generator.menuDirectory;
		File menuFile=new File(directoryViewPages+generator.menuName);
		PrintWriter writer;
		try {
			System.out.println("Written "+menuFile.getAbsolutePath());
			writer = new PrintWriter(menuFile, "UTF-8");
			writer.write(html.toHtml());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Generate the html menu
	 */
	public void GenerateMenu(List<EntityGroup> entityGroupList)
	{
		HtmlCanvas html = new HtmlCanvas();
		try {
			
			html.nav(CssGenerator.getNav())
			.div((new HtmlAttributes()).add("class", "container-fluid"))
			.div((new HtmlAttributes()).add("class", "navbar-header")) // start nav header
			.button((new HtmlAttributes()).add("type", "button").add("class", "navbar-toggle collapsed").add("data-toggle", "collapse").add("data-target", "#bs-example-navbar-collapse-1").add("aria-expanded", "false"))
			.span((new HtmlAttributes()).add("class", "sr-only"))
			.content("Toggle navigation")
			.span((new HtmlAttributes()).add("class", "icon-bar"))._span()
			.span((new HtmlAttributes()).add("class", "icon-bar"))._span()
			.span((new HtmlAttributes()).add("class", "icon-bar"))._span()
			._button()
			.a((new HtmlAttributes()).add("class", "navbar-brand").add("href", "#"))
			.content(generator.applicationName)
			._div()//end header
			.div((new HtmlAttributes()).add("class", "collapse navbar-collapse").add("id", "bs-example-navbar-collapse-1")) //start real nav menu
			.ul((new HtmlAttributes()).add("class", "nav navbar-nav"));
			StringBuilder sb = new StringBuilder();
			for (EntityGroup entityGroup: entityGroupList)
			{
				HtmlCanvas ulHtml= new HtmlCanvas();
				ulHtml.li((new HtmlAttributes()).add("class", "dropdown"))
				.a((new HtmlAttributes()).add("href", "#").add("class", "dropdown-toggle").add("data-toggle", "dropdown").add("role", "button").add("aria-haspopup", "true").add("aria-expanded", "false"));
				HtmlCanvas caretHtml = new HtmlCanvas();
				
				caretHtml.span((new HtmlAttributes()).add("class", "caret"))
				._span();
				ulHtml.content(entityGroup.getName()+caretHtml.toHtml(),false);
				ulHtml.ul((new HtmlAttributes()).add("class", "dropdown-menu"));
				
				String ulContent="";
				for (Entity entity: entityGroup.getEntityList())
				{
					ulContent=ulContent+"<li ng-if=\"restrictionList."+entity.getName()+".canSearch || restrictionList."+entity.getName()+"==undefined\"><a href=\""+Utility.getFirstUpper(entity.getName())+"\">"+Utility.getFirstUpper(entity.getName())+"</a></li>";
				}
				
				ulHtml.content(ulContent,false);
				ulHtml._li();
				sb.append(ulHtml.toHtml());
			}
			html.content(sb.toString(),false);
			html._div() //end real nav menu
			._div()
			._nav();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		File file = new File(""); 
		String directoryViewPages = file.getAbsolutePath()+generator.menuDirectory;
		File menuFile=new File(directoryViewPages+generator.applicationName+generator.menuName);
		PrintWriter writer;
		try {
			System.out.println("Written "+menuFile.getAbsolutePath());
			writer = new PrintWriter(menuFile, "UTF-8");
			writer.write("<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\" %>");
			writer.write(html.toHtml());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void generateTemplate() {
		HtmlCanvas html = new HtmlCanvas();
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		try {
			html.render(docType);
			html.
			html()
			.head()
			.title().content(generator.applicationName);
			includeJavascriptScripts(html,true);
			incluseCssFiles(html);
			html._head()
			.body(htmlAttributes.add("ng-app", generator.applicationName+"App").add("ng-controller", "MainController"));
			html.div((new HtmlAttributes()).add("id", "alertInfo").add("class","alert alert-success custom-alert").add("style","display: none")).span().content("")._div();
			html.div((new HtmlAttributes()).add("id", "alertError").add("class","alert alert-danger custom-alert").add("style","display: none")).span().content("")._div();
			//TODO switch
			html.div((new HtmlAttributes()).add("ng-view", ""))
			._div();
			
			
			String loadMenuScript="loadMenu(); ";
			/*if (Generator.bootstrapMenu)
				loadMenuScript=loadMenuScript+" activeMenu(\""+entityName+"\");";
			else
				loadMenuScript=loadMenuScript+" $('#menu').easytree(easyTreeOption);";
			*/
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
		
		File myJsp=new File(directoryViewPages+"template.jsp");
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
