package it.anggen.generation;

import it.anggen.utils.ReflectionManager;
import it.anggen.utils.Utility;
import it.anggen.model.RestrictionType;
import it.anggen.model.SecurityType;
import it.anggen.repository.security.UserRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.xml.crypto.dsig.spec.HMACParameterSpec;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.rendersnake.HtmlCanvas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.util.WebUtils;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JAnnotationArrayMember;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

@Service
public class WebappGenerator {

	@Autowired
	private Generator generator;
	
	@Autowired
	private JsGenerator jsGenerator;
	
	@Autowired
	private HtmlGenerator htmlGenerator;
	
	private String applicationName;
	private String packageName;
	private String directory;
	private String modelPackage;
	
	public WebappGenerator()
	{
		
	}
	
	public void init() {
		applicationName=generator.applicationName;
		
		packageName=generator.mainPackage+applicationName.toLowerCase()+".";
		File file = new File(""); 
		this.directory = file.getAbsolutePath()+"\\src\\main\\java";
		this.modelPackage=generator.mainPackage+generator.applicationName+".model.";
		
		
	}
	
	public void generate()
	{
		init();
		generateAppConfig();
		generateCsrfHeaderFilter();
		generateMvcWebAppInitializer();
		//generateMyUserDetailService();
		generateSecurityConfig();
		generateSecurityWebappInitializer();
		generateSpringBootApplication();
		generateForbiddenJsp();
		generateMainAppController();
		htmlGenerator.setDirectory();
		htmlGenerator.generateTemplate();
		htmlGenerator.generateHomePage();
		jsGenerator.generateMainApp();
	}
	
	private void generateMainAppController() {
		JCodeModel codeModel = new JCodeModel();
		JDefinedClass mainAppController=null;
		try {
			mainAppController = codeModel._class(""+modelPackage.replace(".model.", ".controller.")+"MainAppController", ClassType.CLASS);
			} catch (JClassAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainAppController.annotate(Controller.class);
		//appConfig.annotate(EnableAutoConfiguration.class);
		JAnnotationUse requestMapping = mainAppController.annotate(RequestMapping.class);
		requestMapping.param("value", "//");
		JMethod manage = mainAppController.method(JMod.PUBLIC, String.class, "manage");
		JAnnotationUse requestMappingManage = manage.annotate(RequestMapping.class);
		requestMappingManage.param("method", RequestMethod.GET);
		JBlock manageBlock = manage.body();
		manageBlock.directStatement("return \"template\";");
		
		JMethod home = mainAppController.method(JMod.PUBLIC, String.class, "home");
		JAnnotationUse requestMappingHome = home.annotate(RequestMapping.class);
		requestMappingHome.param("value", "/home");
		requestMappingHome.param("method", RequestMethod.GET);
		JBlock homeBlock = home.body();
		homeBlock.directStatement("return \"home\";");
		
		
		
		saveFile(codeModel);
		
	}

	private void generateSpringBootApplication()
	{
		JCodeModel codeModel = new JCodeModel();
		JDefinedClass genApp=null;
		try {
			genApp = codeModel._class(JMod.PUBLIC, "it."+Utility.getFirstUpper(applicationName)+"Application", ClassType.CLASS);
		} catch (JClassAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		genApp.annotate(Configuration.class);
		genApp.annotate(EnableAutoConfiguration.class);
		genApp.annotate(SpringBootApplication.class);
		genApp.annotate(EnableCaching.class);
		
		JMethod mainMethod = genApp.method(JMod.PUBLIC+JMod.STATIC, void.class, "main");
		mainMethod.param(String[].class, "args");
		JBlock mainBlock = mainMethod.body();
		mainBlock.directStatement(SpringApplication.class.getName()+".run("+Utility.getFirstUpper(applicationName)+"Application.class, args);");
		saveFile(codeModel);
		
	}
	private void generateAppConfig()
	{
		JCodeModel codeModel = new JCodeModel();
		JDefinedClass appConfig=null;
		try {
			appConfig = codeModel._class(JMod.PUBLIC, packageName+"boot.config."+Utility.getFirstUpper(applicationName)+"Config", ClassType.CLASS);
		} catch (JClassAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		appConfig.annotate(Configuration.class);
		//appConfig.annotate(EnableAutoConfiguration.class);
		JAnnotationUse componentScanAnnotation = appConfig.annotate(ComponentScan.class);
		JAnnotationArrayMember member = componentScanAnnotation.paramArray("value");
		member.param(packageName+"*");
		declareVar(appConfig,"formatSql",String.class,"hibernate.format_sql");
		declareVar(appConfig,"showSql",String.class,"hibernate.show_sql");
		declareVar(appConfig,"cacheRegion",String.class,"hibernate.cache.region.factory_class");
	       
		//declareVar(appConfig,"dialect",String.class,"hibernate.dialect");
		//declareVar(appConfig,"mode",String.class,"hibernate.hbm2ddl.auto");
		//declareVar(appConfig,"namingStrategy",String.class,"hibernate.naming-strategy");
		declareVar(appConfig,"driverClassName",String.class,"datasource.driver.class.name");
		declareVar(appConfig,"jdbcString",String.class,"datasource.jdbc");
		declareVar(appConfig,"dbUrl",String.class,"datasource.url");
		declareVar(appConfig,"dbPort",String.class,"datasource.port");
		declareVar(appConfig,"dbName",String.class,"datasource.db.name");
		declareVar(appConfig,"dbUser",String.class,"datasource.username");
		declareVar(appConfig,"dbPassword",String.class,"datasource.password");
		
		JMethod sessionFactory= appConfig.method(JMod.PUBLIC, SessionFactory.class, "sessionFactory");
		sessionFactory.annotate(Bean.class);
		JBlock sessionFactoryBlock=sessionFactory.body();
		sessionFactoryBlock.directStatement(LocalSessionFactoryBuilder.class.getName()+" builder = ");
		sessionFactoryBlock.directStatement("new "+LocalSessionFactoryBuilder.class.getName()+"(dataSource());");
		sessionFactoryBlock.directStatement(" builder.scanPackages(\""+packageName.substring(0, packageName.length()-1)+"\")");
		sessionFactoryBlock.directStatement(".addProperties(getHibernateProperties());");
		sessionFactoryBlock.directStatement("return builder.buildSessionFactory();");
		JMethod hibProperties = appConfig.method(JMod.PRIVATE, Properties.class,"getHibernateProperties");
		JBlock hibPropertiesBlock= hibProperties.body();
		hibPropertiesBlock.directStatement(Properties.class.getName()+"  prop = new "+Properties.class.getName()+"();");
		hibPropertiesBlock.directStatement(" prop.put(\"hibernate.format_sql\", formatSql);");
		hibPropertiesBlock.directStatement(" prop.put(\"hibernate.show_sql\", showSql);");
		hibPropertiesBlock.directStatement(" prop.put(\"hibernate.cache.region.factory_class\", cacheRegion);");
		//hibPropertiesBlock.directStatement(" prop.put(\"hibernate.dialect\", dialect);");
		//hibPropertiesBlock.directStatement("prop.put(\"hibernate.hbm2ddl.auto\", mode);");
		//hibPropertiesBlock.directStatement(" prop.put(\"hibernate.naming-strategy\",namingStrategy);");
		hibPropertiesBlock.directStatement(" return prop;");

		JMethod dataSource = appConfig.method(JMod.PUBLIC, DataSource.class, "dataSource");
		JAnnotationUse beanAnnotation=dataSource.annotate(Bean.class);
		beanAnnotation.param("name", "dataSource");
		JBlock dataSourceBlock = dataSource.body();
		dataSourceBlock.directStatement(BasicDataSource.class.getName()+" ds = new "+BasicDataSource.class.getName()+"();");
		dataSourceBlock.directStatement("ds.setDriverClassName(driverClassName);");
		dataSourceBlock.directStatement("ds.setUrl(jdbcString+\"://\"+dbUrl+\":\"+dbPort+\"/\"+dbName);");
		dataSourceBlock.directStatement("ds.setUsername(dbUser);");
		dataSourceBlock.directStatement("ds.setPassword(dbPassword);");
		dataSourceBlock.directStatement("return ds;");
		
		JMethod viewResolver = appConfig.method(JMod.PUBLIC, InternalResourceViewResolver.class, "viewResolver");
		viewResolver.annotate(Bean.class);
		
		JBlock viewResolverBlock = viewResolver.body();
		viewResolverBlock.directStatement(InternalResourceViewResolver.class.getName()+" viewResolver= new "+InternalResourceViewResolver.class.getName()+"();");
		viewResolverBlock.directStatement("viewResolver.setViewClass("+JstlView.class.getName()+".class);");
		viewResolverBlock.directStatement("viewResolver.setPrefix(\"/WEB-INF/jsp/\");");
		viewResolverBlock.directStatement("viewResolver.setSuffix(\".jsp\");");
		viewResolverBlock.directStatement("return viewResolver;");
		
		
		saveFile(codeModel);
	}
	private void declareVar(JDefinedClass theClass, String varName,Class varClass, String value) {
		JVar myVar=theClass.field(JMod.PRIVATE, varClass, varName);
		JAnnotationUse valueAnnotation=myVar.annotate(Value.class);
		valueAnnotation.param("value", "${"+value+"}");
		
	}

	private void generateSecurityConfig()
	{
			JCodeModel codeModel = new JCodeModel();
			JDefinedClass securityConfig=null;
			try {
				securityConfig = codeModel._class(JMod.PUBLIC, packageName+"boot.config.SecurityConfig", ClassType.CLASS);
				securityConfig._extends(WebSecurityConfigurerAdapter.class);
			} catch (JClassAlreadyExistsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			securityConfig.annotate(Configuration.class);
			securityConfig.annotate(EnableWebSecurity.class);
			JAnnotationUse orderAnnotation = securityConfig.annotate(Order.class);
			orderAnnotation.param("value", 99);
			JVar userDetailsService = securityConfig.field(JMod.PRIVATE, UserDetailsService.class, "userDetailsService");
			userDetailsService.annotate(Autowired.class);
			JAnnotationUse qualifierAnnotation= userDetailsService.annotate(Qualifier.class);
			qualifierAnnotation.param("value", "userDetailsService");
			
			JMethod configure = securityConfig.method(JMod.PROTECTED, void.class, "configure");
			configure._throws(Exception.class);
			configure.param(HttpSecurity.class, "http");
			configure.annotate(Override.class);
			
			JBlock configureBlock=configure.body();
			configureBlock.directStatement("http");
			configureBlock.directStatement(".authorizeRequests()");
			String enableAll="";
			if (!generator.security)
				enableAll=",\"/**\"";
			configureBlock.directStatement(".antMatchers(\"/css/**\",\"/img/**\",\"/js/**\",\"/auth/**\",\"/login/**\""+enableAll+").permitAll()");
			configureBlock.directStatement(".and()");
			configureBlock.directStatement(".authorizeRequests().anyRequest().fullyAuthenticated().and()");
			configureBlock.directStatement(".formLogin().and().csrf()");
			configureBlock.directStatement(".csrfTokenRepository(csrfTokenRepository()).and()");
			configureBlock.directStatement(".addFilterAfter(new "+ReflectionManager.getJDefinedCustomClass(packageName+"boot.CsrfHeaderFilter").fullName()+"(), "+CsrfFilter.class.getName()+".class);");
			
			JMethod tokenRepository = securityConfig.method(JMod.PRIVATE, CsrfTokenRepository.class, "csrfTokenRepository");
			JBlock tokenRepositoryBody=tokenRepository.body();
			tokenRepositoryBody.directStatement(HttpSessionCsrfTokenRepository.class.getName()+" repository = new "+HttpSessionCsrfTokenRepository.class.getName()+"();");
			tokenRepositoryBody.directStatement(" repository.setHeaderName(\"X-XSRF-TOKEN\");");
			tokenRepositoryBody.directStatement("return repository;");
			
			JMethod configureGlobal = securityConfig.method(JMod.PUBLIC, void.class, "configureGlobal");
			configureGlobal.param(AuthenticationManagerBuilder.class, "auth");
			configureGlobal._throws(Exception.class);
			configureGlobal.annotate(Autowired.class);
			JBlock configureGlobalBlock = configureGlobal.body();
			configureGlobalBlock.directStatement("auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());");
			
			JMethod passwordEncoder = securityConfig.method(JMod.PUBLIC, PasswordEncoder.class, "passwordEncoder");
			passwordEncoder.annotate(Bean.class);
			JBlock passwordEncoderBlock = passwordEncoder.body();
			passwordEncoderBlock.directStatement("PasswordEncoder encoder = new "+BCryptPasswordEncoder.class.getName()+"();");
			passwordEncoderBlock.directStatement("return encoder;");
			
			saveFile(codeModel);
	}
	private void generateCsrfHeaderFilter()
	{
		
		JCodeModel codeModel = new JCodeModel();
			JDefinedClass csrfFilter=null;
			try {
				csrfFilter = codeModel._class(JMod.PUBLIC, packageName+"boot.CsrfHeaderFilter", ClassType.CLASS);
				csrfFilter._extends(OncePerRequestFilter.class);
			} catch (JClassAlreadyExistsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JAnnotationUse configAnnotation =csrfFilter.annotate(Configuration.class);
			configAnnotation.param("value", generator.applicationName+"CsrfFilter");
			JAnnotationUse orderAnnotation=csrfFilter.annotate(Order.class);
			orderAnnotation.param("value", SecurityProperties.ACCESS_OVERRIDE_ORDER);//(, );
			JMethod doFilter = csrfFilter.method(JMod.PROTECTED, void.class, "doFilterInternal");
			doFilter.annotate(Override.class);
			doFilter.param(HttpServletRequest.class, "request");
			doFilter.param(HttpServletResponse.class, "response");
			doFilter.param(FilterChain.class, "filterChain");
			doFilter._throws(ServletException.class);
			doFilter._throws(IOException.class);
			JBlock doFilterBlock = doFilter.body();
			doFilterBlock.directStatement(CsrfToken.class.getName()+" csrf = ("+CsrfToken.class.getName()+") request.getAttribute("+CsrfToken.class.getName()+".class.getName());");
			doFilterBlock.directStatement("if (csrf != null) {");
			doFilterBlock.directStatement(Cookie.class.getName()+" cookie = "+WebUtils.class.getName()+".getCookie(request, \"XSRF-TOKEN\");");
			doFilterBlock.directStatement("String token = csrf.getToken();");
			doFilterBlock.directStatement("if (cookie == null || token != null	&& !token.equals(cookie.getValue())) {");
			doFilterBlock.directStatement("cookie = new "+Cookie.class.getName()+"(\"XSRF-TOKEN\", token);");
			doFilterBlock.directStatement("cookie.setPath(\"/ServerTestApp\");");
			doFilterBlock.directStatement("response.addCookie(cookie);");
			doFilterBlock.directStatement("}");
			doFilterBlock.directStatement("}");
			doFilterBlock.directStatement("filterChain.doFilter(request, response);");
			saveFile(codeModel);
			
	}
	private void generateMvcWebAppInitializer()
	{
		JCodeModel codeModel = new JCodeModel();
		JDefinedClass waInit=null;
		try {
			waInit = codeModel._class(JMod.PUBLIC, packageName+"boot.MvcWebApplicationInitializer", ClassType.CLASS);
			waInit._extends(AbstractAnnotationConfigDispatcherServletInitializer.class);
		} catch (JClassAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JMethod getServletConfig = waInit.method(JMod.PROTECTED, Class[].class, "getServletConfigClasses");
		getServletConfig.annotate(Override.class);
		JBlock servletConfigBlock = getServletConfig.body();
		servletConfigBlock.directStatement("return null;");
		
		
		JMethod getServletMappings = waInit.method(JMod.PROTECTED, String[].class, "getServletMappings");
		getServletMappings.annotate(Override.class);
		JBlock servletMappingsBlock = getServletMappings.body();
		servletMappingsBlock.directStatement("return null;");
	
		JMethod getRootConfig = waInit.method(JMod.PROTECTED, Class[].class, "getRootConfigClasses");
		getRootConfig.annotate(Override.class);
		JBlock rootConfigBlock = getRootConfig.body();
		rootConfigBlock.directStatement("return new Class[] {"+packageName+"boot.config.SecurityConfig.class};");
	
		
		
		saveFile(codeModel);
	}
	private void generateMyUserDetailService()
	{
		JCodeModel codeModel = new JCodeModel();
		JDefinedClass userDetailService=null;
		try {
			userDetailService = codeModel._class(JMod.PUBLIC, packageName+"boot.MyUserDetailService", ClassType.CLASS);
			userDetailService._implements(UserDetailsService.class);
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		
		JType userClass=codeModel.ref(it.anggen.model.security.User.class);//Generator.getJDefinedCustomClass(packageName+"model.security.User");
		JType roleClass=codeModel.ref(it.anggen.model.security.Role.class);//Generator.getJDefinedCustomClass(packageName+"model.security.Role");
		JType userRepositoryClass= codeModel.ref(UserRepository.class);//Generator.getJDefinedCustomClass(packageName+"repository.security.UserRepository")
		
		JAnnotationUse serviceAnnotation=userDetailService.annotate(Service.class);
		serviceAnnotation.param("value", "userDetailsService");
		
		JVar userRepository = userDetailService.field(JMod.PRIVATE, userRepositoryClass, "userRepository");
		userRepository.annotate(Autowired.class);
		
		
		JMethod loadUserByUsername = userDetailService.method(JMod.PUBLIC, UserDetails.class, "loadUserByUsername");
		loadUserByUsername.annotate(Override.class);
		JAnnotationUse transactionalAnnotation = loadUserByUsername.annotate(Transactional.class);
		transactionalAnnotation.param("readOnly", true);
		loadUserByUsername.param(JMod.FINAL, String.class, "username");
		loadUserByUsername._throws(UsernameNotFoundException.class);
		JClass userListClass = codeModel.ref(List.class).narrow(userClass);
		
		//JVar userList = loadUserByUsername.
		JBlock loadUserBlock = loadUserByUsername.body();
		JVar userList=loadUserBlock.decl(userListClass, "userList");
		userList.init(JExpr.direct("userRepository.findByUsername(username)"));
		loadUserBlock.directStatement("if (userList==null || userList.size()==0)");
		loadUserBlock.directStatement("throw new UsernameNotFoundException(\"Username \"+username+\" not found\");");
		loadUserBlock.directStatement(userClass.fullName()+" user = userList.get(0);");
		loadUserBlock.directStatement("List<"+GrantedAuthority.class.getName()+"> authorities = buildUserAuthority(user.getRoleList());");
		loadUserBlock.directStatement("return buildUserForAuthentication(user, authorities);");
		
		JMethod buildUserAuth = userDetailService.method(JMod.PRIVATE, User.class, "buildUserForAuthentication");
		buildUserAuth.param(userClass, "user");
		JClass authList=codeModel.ref(List.class).narrow(GrantedAuthority.class);
		buildUserAuth.param(authList, "authorities");
		JBlock buildUserAuthBlock= buildUserAuth.body();
		buildUserAuthBlock.directStatement("return new "+User.class.getName()+"(user.getUsername(), user.getPassword(),user.getEnabled(), true, true, true, authorities);");

		JMethod buildUserAuthority = userDetailService.method(JMod.PRIVATE, authList, "buildUserAuthority");
		
		JClass roleList=codeModel.ref(List.class).narrow(roleClass);
		
		buildUserAuthority.param(roleList, "roleList");
		JBlock buildUserAuthorityBlock = buildUserAuthority.body();
		JClass grantedSet=codeModel.ref(Set.class).narrow(GrantedAuthority.class);
		JVar grantedSetVar=buildUserAuthorityBlock.decl(grantedSet, "setAuths");
		grantedSetVar.init(JExpr.direct("new "+HashSet.class.getName()+"<"+GrantedAuthority.class.getName()+">()"));
		buildUserAuthorityBlock.directStatement("for ("+roleClass.fullName()+" role: roleList)");
		buildUserAuthorityBlock.directStatement("setAuths.add(new "+SimpleGrantedAuthority.class.getName()+"(role.getRole()));");
		buildUserAuthorityBlock.directStatement("List<"+GrantedAuthority.class.getName()+"> result = new "+ArrayList.class.getName()+"<"+GrantedAuthority.class.getName()+">(setAuths);");
		buildUserAuthorityBlock.directStatement("return result;");
		
		saveFile(codeModel);
	}
	private void generateSecurityWebappInitializer()
	{
		JCodeModel codeModel = new JCodeModel();
		JDefinedClass securityWebAppInit=null;
		try {
			securityWebAppInit = codeModel._class(JMod.PUBLIC, packageName+"boot.SecurityWebApplicationInitializer"+Utility.getFirstUpper(applicationName), ClassType.CLASS);
			securityWebAppInit._extends(AbstractSecurityWebApplicationInitializer.class);
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		securityWebAppInit.annotate(Configuration.class);
		securityWebAppInit.constructor(JMod.PUBLIC);
		
		saveFile(codeModel);
	}
	
	private void generateForbiddenJsp()
	{
		HtmlCanvas html = new HtmlCanvas();
		try {
			html.render(HtmlGenerator.docType);
			html.
			html()
			.head()
			.title().content("Access forbidden");
			htmlGenerator.incluseCssFiles(html);
			htmlGenerator.includeJavascriptScripts(html, false);
			html._head()
			.body()
			.content("Access forbidden!!!")
			._html();

			File file= new File("");
			String directoryViewPages = file.getAbsolutePath()+generator.htmlDirectory+"/";
			
			File dir = new File(directoryViewPages);
			if (!dir.exists())
				dir.mkdirs();
			
			File myJsp=new File(directoryViewPages+"forbidden.jsp");
			PrintWriter writer;
			try {
				System.out.println("Written "+myJsp.getAbsolutePath());
				writer = new PrintWriter(myJsp, "UTF-8");
				writer.write(html.toHtml());
				writer.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * Save codeModel to file
	 * @param codeModel
	 */
	private void saveFile(JCodeModel codeModel)
	{
		try {
			   codeModel.build(new File (directory));
			} catch (Exception ex) {
			   ex.printStackTrace();
			}
		
	}
	
}
