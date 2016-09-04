package it.anggen.generation;

import it.anggen.reflection.EntityManager;
import it.anggen.reflection.EntityManagerImpl;
import it.anggen.utils.EntityAttribute;
import it.anggen.utils.ReflectionManager;
import it.anggen.utils.Utility;
import it.anggen.model.GenerationType;
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
import org.rendersnake.tools.PrettyWriter;
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
		directoryViewPages = file.getAbsolutePath()+Generator.htmlDirectoryProperty+"/";
		directoryAngularFiles=file.getAbsolutePath()+Generator.generateAngularDirectory+Generator.appName+"/";
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
			.macros().javascript("js/ui-grid.js")
			.macros().javascript("js/ui-bootstrap-tpls-1.2.5.min.js");
			if (includeEntityFile)
			{
				html.macros().javascript("js/angular/"+Generator.appName+"/main-app.js");
				html.macros().javascript("js/metrics/metrics.controller.js");
				html.macros().javascript("js/metrics/metrics.service.js");
				//html.macros().javascript("js/angular/"+Generator.appName+"/"+Generator.appName+"-service.js");
				//html.macros().javascript("js/angular/"+Generator.appName+"/"+Generator.appName+"-controller.js");
				for (Entity entity: generator.getEntityList())
				{
					html.macros().javascript("js/angular/"+Generator.appName+"/"+entity.getName()+".service.js");
					html.macros().javascript("js/angular/"+Generator.appName+"/"+entity.getName()+".controller.js");
					
				}
			}
			
			html.macros().javascript("js/date.js")
			.macros().javascript("js/utility.js");
			
			if (Generator.easyTreeMenuProperty)
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
			.link((new HtmlAttributes()).add("rel","import").add("href", ""+Generator.appName+Generator.menuNameProperty));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/**
	 * Generate the main template of the page
	 * @throws IllegalAccessException
	 */
	public void generateSearchView() throws IllegalAccessException
	{
		HtmlCanvas html = new HtmlCanvas(new PrettyWriter());
		angularGenerator.init(entity, true,new ArrayList<Entity>(),entityManager.isLastLevel(entity));
		try {
			angularGenerator.generateSearchView(html);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File dir = new File(directoryViewPages);
		if (!dir.exists())
			dir.mkdirs();
		
		File myJsp=new File(directoryViewPages+entityName+"/"+entityName+"-search.html");
		PrintWriter writer;
		try {
			System.out.println("Written "+myJsp.getAbsolutePath());
			writer = new PrintWriter(myJsp, "UTF-8");
			//PrettyWriter w = new PrettyWriter(writer);
			writer.write(html.toHtml());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public void generateDetailView() throws IllegalAccessException
	{
		HtmlCanvas html = new HtmlCanvas(new PrettyWriter());
		angularGenerator.init(entity, true,new ArrayList<Entity>(),entityManager.isLastLevel(entity));
		try {
			angularGenerator.generateDetailHtml(html);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File dir = new File(directoryViewPages);
		if (!dir.exists())
			dir.mkdirs();
		
		File myJsp=new File(directoryViewPages+entityName+"/"+entityName+"-detail.html");
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
	
	
	public void generatePageContent() throws IllegalAccessException
	{
		HtmlCanvas html = new HtmlCanvas(new PrettyWriter());
		angularGenerator.init(entity, true,new ArrayList<Entity>(),entityManager.isLastLevel(entity));
		try {
			angularGenerator.generateTemplate(html);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File dir = new File(directoryViewPages+"../controller/"+entityName+"/");
		if (!dir.exists())
			dir.mkdirs();
		
		File myJsp=new File(directoryViewPages+"../controller/"+entityName+"/"+entityName+"-template.html");
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
	
	
	public void generateMain()
	{
		HtmlCanvas html = new HtmlCanvas(new PrettyWriter());
		try {

			html.div((new HtmlAttributes()).add("id", "canvas"))
			.div().content("<"+Utility.camelCaseToMinus(Generator.appName)+"-navbar></"+Utility.camelCaseToMinus(Generator.appName)+"-navbar> ",false)
			.div((new HtmlAttributes()).add("id", "alertInfo").add("class","alert alert-success custom-alert").add("style","display: none")).span().content("")._div()
			.div((new HtmlAttributes()).add("id", "alertError").add("class","alert alert-danger custom-alert").add("style","display: none")).span().content("")._div()
			
			.div((new HtmlAttributes()).add("style", ""))
			.div((new HtmlAttributes()).add("ui-view", "pageContent"))
			._div()
			._div();
			
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		File f = new File("");
		
		File dir = new File(f.getAbsolutePath()+Generator.generateAngularDirectory+"../main/");
		if (!dir.exists())
			dir.mkdirs();
		
		File myJsp=new File(f.getAbsolutePath()+Generator.generateAngularDirectory+"../main/main.html");
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
		HtmlCanvas html = new HtmlCanvas(new PrettyWriter());
		try {
			html.div((new HtmlAttributes()).add("id", "menu").add("style", "width: 250px;"))
			.ul();
			
			for (EntityGroup entityGroup: entityGroupList)
			{
				html.li((new HtmlAttributes()).add("class", "isFolder"));
				HtmlCanvas folderHtml = new HtmlCanvas(new PrettyWriter());
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
		String directoryViewPages = file.getAbsolutePath()+Generator.menuDirectoryProperty;
		File menuFile=new File(directoryViewPages+Generator.menuNameProperty);
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
		HtmlCanvas html = new HtmlCanvas(new PrettyWriter());
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
			.a((new HtmlAttributes()).add("class", "navbar-brand").add("href", "home"))
			.content(Generator.appName)
			._div()//end header
			.div((new HtmlAttributes()).add("class", "collapse navbar-collapse").add("id", "bs-example-navbar-collapse-1")) //start real nav menu
			.ul((new HtmlAttributes()).add("class", "nav navbar-nav"));
			StringBuilder sb = new StringBuilder();
			for (EntityGroup entityGroup: entityGroupList)
			{
				HtmlCanvas ulHtml= new HtmlCanvas(new PrettyWriter());
				ulHtml.li((new HtmlAttributes()).add("uib-dropdown", "").add("on-toggle", "toggled(\"open\")"))
				.a((new HtmlAttributes()).add("href", "#").add("class", "dropdown-toggle").add("data-toggle", "dropdown").add("role", "button").add("aria-haspopup", "true").add("aria-expanded", "false").add("uib-dropdown-toggle", ""));
				HtmlCanvas caretHtml = new HtmlCanvas(new PrettyWriter());
				
				caretHtml.span((new HtmlAttributes()).add("class", "caret"))
				._span();
				ulHtml.content(entityGroup.getName()+caretHtml.toHtml(),false);
				ulHtml.ul((new HtmlAttributes()).add("uib-dropdown-menu", "").add("aria-labelledby", "simple-dropdown"));
				
				String ulContent="";
				for (Entity entity: entityGroup.getEntityList())
				if (!entity.getDisableViewGeneration())
				if (!entity.getIgnoreMenu())
				{
					ulContent=ulContent+"<li ng-if=\"$root.restrictionList."+entityGroup.getName()+".restrictionItemMap."+entity.getName()+".canSearch\">\n<a href=\"#/app/"+Utility.getFirstLower(entity.getName())+"\" role=\"menuitem\">"+Utility.getFirstUpper(entity.getName())+"</a>\n</li>\n";
				}
				
				
				
				ulHtml.content(ulContent,false);
				ulHtml._li();
				sb.append(ulHtml.toHtml());
			}
			
			//administration menù
			
			HtmlCanvas ulHtml= new HtmlCanvas(new PrettyWriter());
			ulHtml.li((new HtmlAttributes()).add("uib-dropdown", "").add("on-toggle", "toggled(\"open\")"))
			.a((new HtmlAttributes()).add("href", "#").add("class", "dropdown-toggle").add("data-toggle", "dropdown").add("role", "button").add("aria-haspopup", "true").add("aria-expanded", "false").add("uib-dropdown-toggle", ""));
			HtmlCanvas caretHtml = new HtmlCanvas(new PrettyWriter());
			
			caretHtml.span((new HtmlAttributes()).add("class", "caret"))
			._span();
			ulHtml.content("Administration"+caretHtml.toHtml(),false);
			ulHtml.ul((new HtmlAttributes()).add("uib-dropdown-menu", "").add("aria-labelledby", "simple-dropdown"));
			
			String ulContent="";
			
			ulContent=ulContent+"<li>\n<a href=\"#/app/metrics\" role=\"menuitem\">Metrics</a>\n</li>\n";
			
			
			
			
			ulHtml.content(ulContent,false);
			ulHtml._li();
			sb.append(ulHtml.toHtml());
			
			
			
			/*HtmlCanvas ulHtml= new HtmlCanvas(new PrettyWriter());
			ulHtml.li((new HtmlAttributes()).add("class", "dropdown"))
			.a((new HtmlAttributes()).add("href", "#").add("class", "dropdown-toggle").add("data-toggle", "dropdown").add("role", "button").add("aria-haspopup", "true").add("aria-expanded", "false"));
			HtmlCanvas caretHtml = new HtmlCanvas(new PrettyWriter());
			
			caretHtml.span((new HtmlAttributes()).add("class", "caret"))
			._span();
			ulHtml.content("Administration"+caretHtml.toHtml(),false);
			ulHtml.ul((new HtmlAttributes()).add("class", "dropdown-menu"));
			
			String ulContent="";
			{
				ulContent=ulContent+"<li><a href=\"#/app/metrics\">Metrics</a></li>";
			}
			
			ulHtml.content(ulContent,false);
			ulHtml._li();
			sb.append(ulHtml.toHtml());*/
			html.content(sb.toString(),false);
			html._div() //end real nav menu
			._div()
			._nav();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		File file = new File(""); 
		String directoryViewPages = file.getAbsolutePath()+Generator.menuDirectoryProperty;
		File menuFile=new File(directoryViewPages+Generator.menuNameProperty);
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
	
	public void generateHomePage(){
		HtmlCanvas html = new HtmlCanvas(new PrettyWriter());
		try {
			html.h1().center().content("HOME PAGE")._h1();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File dir = new File(directoryViewPages+"home/");
		if (!dir.exists())
			dir.mkdirs();
		
		File myFile=new File(directoryViewPages+"home/home.html");
		PrintWriter writer;
		try {
			System.out.println("Written "+myFile.getAbsolutePath());
			writer = new PrintWriter(myFile, "UTF-8");
			writer.write(html.toHtml());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void generateLogin(){
		HtmlCanvas html = new HtmlCanvas(new PrettyWriter());
		try {
			html.form((new HtmlAttributes()).add("class", "form-signin").add("ng-submit", "vm.onSubmit(vm.username,vm.password)"))
			.h2((new HtmlAttributes()).add("class", "form-signin-heading")).content("Please sign in")
			.label((new HtmlAttributes())
					.add("for", "inputEmail").add("class", "sr-only")
					).content("Username")
			.input((new HtmlAttributes())
					.add("type", "text")
					.add("id", "inputEmail")
					.add("class", "form-control")
					.add("placeholder", "Username")
					.add("required", "")
					.add("autofocus", "")
					.add("ng-model", "vm.username")
					)
			.label((new HtmlAttributes()).add("for", "inputPassword").add("class", "sr-only"))
				.content("Password")
			.input((new HtmlAttributes())
					.add("type", "password")
					.add("id", "inputPassword")
					.add("class", "form-control")
					.add("placeholder", "Password")
					.add("required", "")
					.add("autofocus", "")
					.add("ng-model", "vm.password")
					)
			.div((new HtmlAttributes()).add("class", "checkbox"))
				.label()
					.input((new HtmlAttributes()).add("type", "checkbox").add("value", "remember-me"))
					.content("Rembember me")
				//._label()
			._div()
			.button((new HtmlAttributes()).add("class", "btn btn-lg btn-primary btn-block").add("type", "submit")).content("Sign in")
			
			._form();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File dir = new File(directoryViewPages+"login/");
		if (!dir.exists())
			dir.mkdirs();
		
		File myFile=new File(directoryViewPages+"login/login.html");
		PrintWriter writer;
		try {
			System.out.println("Written "+myFile.getAbsolutePath());
			writer = new PrintWriter(myFile, "UTF-8");
			writer.write(html.toHtml());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		generateLoginModal();
	}
	
	private void generateLoginModal()
	{
		File myFile=new File(directoryViewPages+"login/login-modal.html");
		PrintWriter writer;
		try {
			System.out.println("Written "+myFile.getAbsolutePath());
			writer = new PrintWriter(myFile, "UTF-8");
			writer.write("<login>\n</login>");
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	


	
}
