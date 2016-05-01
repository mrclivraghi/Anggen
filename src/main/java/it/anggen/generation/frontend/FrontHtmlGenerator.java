package it.anggen.generation.frontend;

import it.anggen.reflection.EntityAttributeManager;
import it.anggen.reflection.EntityManager;
import it.anggen.reflection.EntityManagerImpl;
import it.anggen.utils.EntityAttribute;
import it.anggen.utils.ReflectionManager;
import it.anggen.utils.Utility;
import it.anggen.generation.CssGenerator;
import it.anggen.generation.Generator;
import it.anggen.generation.JsGenerator;
import it.anggen.model.FieldType;
import it.anggen.model.entity.Entity;
import it.anggen.model.entity.EntityGroup;
import it.anggen.model.entity.Tab;
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
			.macros().javascript("../../js/jquery-1.9.1.js")
			.macros().javascript("../../js/jquery-ui.js")
			.macros().javascript("../../js/angular.js")
			.macros().javascript("../../js/angular-touch.js")
			.macros().javascript("../../js/angular-animate.js")
			.macros().javascript("../../js/csv.js")
			.macros().javascript("../../js/pdfmake.js")
			.macros().javascript("../../js/vfs_fonts.js")
			.macros().javascript("../../js/sanitize.js")
			.macros().javascript("../../js/ui-grid.js");
			if (includeEntityFile)
				html.macros().javascript("../../js/angular/"+Generator.appName+"/"+entityName+"-front.js");
			html.macros().javascript("../../js/date.js")
			.macros().javascript("../../js/utility.js")
			.macros().javascript("../../js/bootstrap.min.js");
			} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void incluseCssFiles(HtmlCanvas html)
	{
		try {
			html.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "../../css/ui-grid.css"))
			.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "../../css/bootstrap.min.css"))
			.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "../../css/main.css"))
			.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "../../css/jquery-ui.css"))
			.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "../../css/easytree/skin-win8/ui.easytree.css"))
			.link((new HtmlAttributes()).add("rel","import").add("href", "../../"+Generator.appName+Generator.menuNameProperty));
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
			.body(htmlAttributes.add("ng-app", Utility.getFirstLower(entityName)+"FrontApp"));
			html.div((new HtmlAttributes()).add("id", "alertInfo").add("class","alert alert-success custom-alert").add("style","display: none")).span().content("")._div();
			html.div((new HtmlAttributes()).add("id", "alertError").add("class","alert alert-danger custom-alert").add("style","display: none")).span().content("")._div();
			
			renderBody(html);
			
			//TODO switch
			String loadMenuScript="loadMenu(); ";
			html.script((new HtmlAttributes()).add("type", "text/javascript")).content(loadMenuScript,false);
			if (Generator.easyTreeMenuProperty)
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
	
	private void renderBody(HtmlCanvas html)
	{
		/*
		 * <div ng-controller="entityFrontController">
		<div ng-repeat="entity in entityList" class="panel panel-default default-panel">
			{{entity.entityId}} <br>
		</div>
		<ul class="pagination">
			<li  ng-class="{disabled: currentPage<=1}" ><a ng-click="getPagination(currentPage-1)">&laquo;</a></li>
		<li ng-repeat="i in [].constructor(selectedEntity.totalPages) track by $index" ng-class="{active: $index+1==currentPage}"><a ng-click="getPagination($index+1)" >{{$index+1}}</a></li>
			<li ng-class="{disabled: currentPage>=selectedEntity.totalPages}"><a ng-click="getPagination(currentPage+1)" ng-class="{disabled: currentPage>=selectedEntity.totalPages}">&raquo;</a></li>
		</ul>
	</div>
		 */
		try {
			html.div((new HtmlAttributes()).add("ng-controller", entityName+"FrontController"));
			
			html.div((new HtmlAttributes()).add("ng-repeat", "entity in entityList"));
			renderElement(html);
			html._div();
			//ul for pagination
			html.div((new HtmlAttributes()).add("class", "default-panel").add("style", "margin-left:45%"));
			html.ul((new HtmlAttributes()).add("class", "pagination"))
			.li((new HtmlAttributes()).add("ng-class", "{disabled: currentPage<=1}")).a((new HtmlAttributes()).add("ng-click", "getPagination(currentPage-1)")).content("&laquo;",false)._li()
			.li((new HtmlAttributes()).add("ng-repeat", "i in [].constructor(selectedEntity.totalPages) track by $index").add("ng-class","{active: $index+1==currentPage  || (currentPage==undefined && $index==0)}")).a((new HtmlAttributes()).add("ng-click", "getPagination($index+1)")).content("{{$index+1}}")._li()
			.li((new HtmlAttributes()).add("ng-class", "{disabled: currentPage>=selectedEntity.totalPages}")).a((new HtmlAttributes()).add("ng-click", "getPagination(currentPage+1)")).content("&raquo;",false)._li()
			._ul();
			html._div();
			//close div
			html._div();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void renderElement(HtmlCanvas html) throws IOException
	{
		//

		html.div(CssGenerator.getPanel());
		html.div(CssGenerator.getPanelHeader());
		html.content(""+entityName+" {{ entity."+entityName+"Id }}");
		html.div(CssGenerator.getPanelBody());

		for (EntityAttribute entityAttribute: entityManager.getAllAttribute())
		{
				
			renderField(html, entityAttribute);
		}



		html._div();

		html._div();
	
	}

	private void renderField(HtmlCanvas html, EntityAttribute entityAttribute) throws IOException {


		if (EntityAttributeManager.getInstance(entityAttribute).isEnumField())
		{
			html.div((new HtmlAttributes()).add("ng-bind-html", "entity."+entityAttribute.getName()+""))
			._div();
		}
		else
		{
			if (EntityAttributeManager.getInstance(entityAttribute).isField())
			{
				String inputType=getInputType(entityAttribute);
				if (inputType.equals("embedded"))
				{
					html.div((new HtmlAttributes()).add("ng-bind-html", "entity."+entityAttribute.getName()+""))
					._div();
					
				}else
				{
					if (inputType.equals("checkbox"))
					{
						html.button(CssGenerator.getButton("","btn-s"));
						HtmlCanvas buttonContent= new HtmlCanvas();
						buttonContent.span((new HtmlAttributes()).add("class","glyphicon").add("ng-class", "{'glyphicon-ok': entity."+entityAttribute.getName()+",'glyphicon-remove ': !entity."+entityAttribute.getName()+"}").add("aria-hidden", "true"));
						html.content(buttonContent.toHtml()+""+entityAttribute.getName()+"",false);
					}else
					{
						if (inputType.equals("file"))
						{
							html.div().content("{{entity."+entityAttribute.getName()+"}}",false);
						}else
						{ //base attribute
							
							if (EntityAttributeManager.getInstance(entityAttribute).getFieldTypeName().equals("Date"))
							{
								html.div().content(entityAttribute.getName()+": {{entity."+entityAttribute.getName()+" | date: 'dd/MM/yyyy'}}");
							}
							else
							html.div().content(entityAttribute.getName()+": {{entity."+entityAttribute.getName()+"}}");
						}
							
					}
				}
			
			}
		}
	
	}
	
	
	private String getInputType(EntityAttribute entityAttribute)
	{
		if (EntityAttributeManager.getInstance(entityAttribute).asField()!=null && EntityAttributeManager.getInstance(entityAttribute).asField().getFieldType()==FieldType.BOOLEAN) return "checkbox";
		if (EntityAttributeManager.getInstance(entityAttribute).asField()!=null && EntityAttributeManager.getInstance(entityAttribute).asField().getFieldType()==FieldType.TIME) return "time";
		if (EntityAttributeManager.getInstance(entityAttribute).asField()!=null && EntityAttributeManager.getInstance(entityAttribute).getPassword()) 
			return "password";
		if (EntityAttributeManager.getInstance(entityAttribute).asField()!=null && EntityAttributeManager.getInstance(entityAttribute).isEmbedded()) 
			return "embedded";
		
		if (EntityAttributeManager.getInstance(entityAttribute).isField()&&EntityAttributeManager.getInstance(entityAttribute).asField().getFieldType()==FieldType.FILE) return "file";
		return "text";
	}
}
