package it.polimi.generation;

import it.polimi.model.domain.Entity;
import it.polimi.model.domain.EntityAttribute;
import it.polimi.model.domain.Field;
import it.polimi.reflection.EntityManager;
import it.polimi.reflection.EntityManagerImpl;
import it.polimi.utils.ReflectionManager;
import it.polimi.utils.Utility;

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
import org.springframework.beans.factory.annotation.Value;
/**
 * Prepare the html template and fill it with angular code and html elements.
 * @author Marco
 *
 */
public class HtmlGenerator {
	private List<EntityAttribute> attributeList;
	
	private List<Entity> childrenEntity;
	
	private String entityName;

	private static String directoryViewPages;
	
	private static String directoryAngularFiles;
	
	private EntityManager entityManager;
	
	private Entity entity;
	
	private DocType docType= DocType.HTML5;
	
	
	public HtmlGenerator(Entity entity)
	{
		this.entity=entity;
		this.entityManager = new EntityManagerImpl(entity);
		this.entityName=Utility.getFirstLower(entity.getName());
		this.attributeList=entityManager.getAttributeList();
		this.childrenEntity=entityManager.getChildrenEntities();
		File file = new File(""); 
		directoryViewPages = file.getAbsolutePath()+Generator.htmlDirectory;
		directoryAngularFiles=file.getAbsolutePath()+Generator.angularDirectory;
	}
	
	
	
	/**
	 * Include the functional js scripts
	 * @param html
	 */
	private void includeJavascriptScripts(HtmlCanvas html)
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
			.macros().javascript("../js/ui-grid.js")
			.macros().javascript("../js/angular/"+entityName+".js")
			.macros().javascript("../js/date.js")
			.macros().javascript("../js/utility.js")
			.macros().javascript("../js/jquery.easytree.js")
			.macros().javascript("../js/bootstrap.min.js")
			.macros().javascript("../js/alasql.min.js");
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
			.link((new HtmlAttributes()).add("rel","import").add("href", "../"+Generator.menuName+".html"));
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
		JsGenerator jsGenerator = new JsGenerator(entity, true, null, false);
		
		try {
			html.render(docType);
			html.
			html()
			.head()
			.title().content(entityName);
			includeJavascriptScripts(html);
			incluseCssFiles(html);
			jsGenerator.saveJsToFile(directoryAngularFiles);
			html._head()
			.body(htmlAttributes.add("ng-app", Utility.getFirstLower(entityName)+"App"));
			AngularGenerator angularGenerator= new AngularGenerator(entity, true,new ArrayList<Entity>());
			angularGenerator.generateEntityView(html);
			
			//TODO switch
			String loadMenuScript="loadMenu(); ";
			/*if (Generator.bootstrapMenu)
				loadMenuScript=loadMenuScript+" activeMenu(\""+entityName+"\");";
			else
				loadMenuScript=loadMenuScript+" $('#menu').easytree(easyTreeOption);";
			*/
			html.script((new HtmlAttributes()).add("type", "text/javascript")).content(loadMenuScript,false);
			html._body()._html();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
	
	public static void GenerateEasyTreeMenu(List<Entity> entityList)
	{
		HtmlCanvas html= new HtmlCanvas();
		try {
			html.div((new HtmlAttributes()).add("id", "menu").add("style", "width: 250px;"))
			.ul();
			List<String> packageList= ReflectionManager.getSubPackages(Generator.modelPackage);
			
			//TODO manage drop down
			
			/*for (String myPackage: packageList)
			{
				html.li((new HtmlAttributes()).add("class", "isFolder"));
				Set<Class<?>> packageClassList = ReflectionManager.getClassInPackage(myPackage);
				HtmlCanvas folderHtml = new HtmlCanvas();
				folderHtml.ul();
				for (Class myClass: packageClassList)
				{
					folderHtml.li().a((new HtmlAttributes()).add("href", "../"+reflectionManager.parseName(myClass.getName())+"/")).content(reflectionManager.parseName(myClass.getName()))._li();
				}
				folderHtml._ul();
				html.content(reflectionManager.parseName(myPackage)+folderHtml.toHtml(),false);
				
			}*/
			for (Entity entity: entityList)
			{
				//if (theClass.getPackage().getName().equals(Generator.modelPackage))
				//{
					html.li().a((new HtmlAttributes()).add("href", "../"+Utility.getFirstLower(entity.getName())+"/")).content(Utility.getFirstUpper(entity.getName()))._li();
				//}
			}
			
			html._ul()._div();

			html.script().content("function stateChanged(nodes, nodesJson) {var t = nodes[0].text; $.cookie('menu', nodesJson); }; var easyTreeOption={data: $.cookie('menu'), stateChanged: stateChanged};",false);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File file = new File(""); 
		String directoryViewPages = file.getAbsolutePath()+Generator.menuDirectory;
		File menuFile=new File(directoryViewPages+Generator.menuName+".jsp");
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
	public static void GenerateMenu(List<Entity> entityList)
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
			.content(Generator.applicationName)
			._div()//end header
			.div((new HtmlAttributes()).add("class", "collapse navbar-collapse").add("id", "bs-example-navbar-collapse-1")) //start real nav menu
			.ul((new HtmlAttributes()).add("class", "nav navbar-nav"));
			List<String> packageList= ReflectionManager.getSubPackages(Generator.modelPackage);
			StringBuilder sb = new StringBuilder();
			/*for (String myPackage: packageList)
			{
				
				HtmlCanvas ulHtml= new HtmlCanvas();
				ulHtml.li((new HtmlAttributes()).add("class", "dropdown"))
				.a((new HtmlAttributes()).add("href", "#").add("class", "dropdown-toggle").add("data-toggle", "dropdown").add("role", "button").add("aria-haspopup", "true").add("aria-expanded", "false"));
				HtmlCanvas caretHtml = new HtmlCanvas();
				
				caretHtml.span((new HtmlAttributes()).add("class", "caret"))
				._span();
				ulHtml.content(reflectionManager.parseName(myPackage)+caretHtml.toHtml(),false);
				Set<Class<?>> packageClassList = ReflectionManager.getClassInPackage(myPackage);
				ulHtml.ul((new HtmlAttributes()).add("class", "dropdown-menu"));
				String ulContent="";
				for (Class myClass: packageClassList)
				{
					//ulContent=ulContent+"<c:forEach var=\"entity\" items=\"${entityList}\">";
					//ulContent=ulContent+"<c:if test=\"${entity.entityName=='"+reflectionManager.parseName(myClass.getName())+"'}\">";
					ulContent=ulContent+"<li><a href=\"../"+reflectionManager.parseName(myClass.getName())+"/\">"+reflectionManager.parseName(myClass.getName())+"</a></li>";
					//ulContent=ulContent+"</c:if>";
					//ulContent=ulContent+"</c:forEach>";
				}
				ulHtml.content(ulContent,false);
				//html._ul();
				ulHtml._li();
				//ulContent="<c:set var=\"fill\" value=\"0\"/>";
				//ulContent=ulContent+"<c:forEach var=\"entity\" items=\"${entityList}\">";
				String condition="";
				for (Class myClass: packageClassList)
				{
					condition=condition+"entity.entityName=='"+reflectionManager.parseName(myClass.getName())+"' ||";
				}
				condition=condition.substring(0, condition.length()-3);
				//ulContent=ulContent+"<c:if test=\"${"+condition+"}\">";
				//ulContent=ulContent+"<c:set var=\"fill\" value=\"1\"/>";
				//ulContent=ulContent+"</c:if>";
				//ulContent=ulContent+"</c:forEach>";
				//ulContent=ulContent+"<c:if test=\"${fill==1}\">";
				ulContent=ulContent+ulHtml.toHtml();
				//ulContent=ulContent+"</c:if>";
				sb.append(ulContent);
				
			}*/
			for (Entity entity: entityList)
			{
				//if (entity.getPackage().getName().equals(Generator.modelPackage))
				//{
					String ulContent="";
					
					//ulContent=ulContent+"<c:forEach var=\"entity\" items=\"${entityList}\">";
					//ulContent=ulContent+"<c:if test=\"${entity.entityName=='"+reflectionManager.parseName(theClass.getName())+"'}\">";
					ulContent=ulContent+"<li><a href=\"../"+Utility.getFirstLower(entity.getName())+"/\">"+Utility.getFirstUpper(entity.getName())+"</a></li>";
					//ulContent=ulContent+"</c:if>";
					//ulContent=ulContent+"</c:forEach>";
					sb.append(ulContent);
				//}
			}
			html.content(sb.toString(),false);
//			html._ul()
			html._div() //end real nav menu
			._div()
			._nav();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		File file = new File(""); 
		String directoryViewPages = file.getAbsolutePath()+Generator.menuDirectory;
		File menuFile=new File(directoryViewPages+Generator.menuName+".jsp");
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
	
}
