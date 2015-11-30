package it.polimi.generation;

import it.generated.anggen.GeneratedApplication;
import it.polimi.boot.config.SecurityConfig;
import it.polimi.utils.Utility;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
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
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;

public class WebappGenerator {

	private String applicationName;
	private String directory;
	
	public WebappGenerator() {
		applicationName=Generator.applicationName;
		directory=Generator.mainPackage+applicationName.toLowerCase()+".";
	}
	
	public void generate()
	{
		
	}
	
	private void generateSpringBootApplication()
	{
		JCodeModel codeModel = new JCodeModel();
		JDefinedClass genApp=null;
		try {
			genApp = codeModel._class(JMod.PUBLIC, directory+Utility.getFirstUpper(applicationName)+"Application", ClassType.CLASS);
		} catch (JClassAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		genApp.annotate(Configuration.class);
		genApp.annotate(EnableAutoConfiguration.class);
		genApp.annotate(SpringBootApplication.class);
		
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
			appConfig = codeModel._class(JMod.PUBLIC, directory+"boot.config.AppConfig", ClassType.CLASS);
		} catch (JClassAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		appConfig.annotate(Configuration.class);
		appConfig.annotate(EnableAutoConfiguration.class);
		JAnnotationUse componentScanAnnotation = appConfig.annotate(ComponentScan.class);
		JAnnotationArrayMember member = componentScanAnnotation.paramArray("value");
		member.param(directory+".*");
		declareVar(appConfig,"formatSql",String.class,"hibernate.format_sql");
		declareVar(appConfig,"showSql",String.class,"hibernate.show_sql");
		declareVar(appConfig,"dialect",String.class,"hibernate.dialect");
		declareVar(appConfig,"mode",String.class,"hibernate.hbm2ddl.auto");
		declareVar(appConfig,"namingStrategy",String.class,"hibernate.naming-strategy");
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
		sessionFactoryBlock.directStatement(" builder.scanPackages(\""+directory+"\")");
		sessionFactoryBlock.directStatement(".addProperties(getHibernateProperties());");
		sessionFactoryBlock.directStatement("return builder.buildSessionFactory();");
		JMethod hibProperties = appConfig.method(JMod.PRIVATE, Properties.class,"getHibernateProperties");
		JBlock hibPropertiesBlock= hibProperties.body();
		hibPropertiesBlock.directStatement(Properties.class.getName()+"  prop = new "+Properties.class.getName()+"();");
		hibPropertiesBlock.directStatement(" prop.put(\"hibernate.format_sql\", formatSql);");
		hibPropertiesBlock.directStatement(" prop.put(\"hibernate.show_sql\", showSql);");
		hibPropertiesBlock.directStatement(" prop.put(\"hibernate.dialect\", dialect);");
		hibPropertiesBlock.directStatement("prop.put(\"hibernate.hbm2ddl.auto\", mode);");
		hibPropertiesBlock.directStatement(" prop.put(\"hibernate.naming-strategy\",namingStrategy);");
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
		viewResolverBlock.directStatement("viewResolver.setPrefix(\"/WEB-INF/jsp/"+applicationName.toLowerCase()+"/\");");
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
				securityConfig = codeModel._class(JMod.PUBLIC, directory+"boot.config.SecurityConfig", ClassType.CLASS);
				securityConfig._extends(WebSecurityConfigurerAdapter.class);
			} catch (JClassAlreadyExistsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			securityConfig.annotate(Configuration.class);
			securityConfig.annotate(EnableWebSecurity.class);
			JVar userDetailsService = securityConfig.field(JMod.PRIVATE, UserDetailsService.class, "userDetailsService");
			userDetailsService.annotate(Autowired.class);
			JAnnotationUse qualifierAnnotation= userDetailsService.annotate(Qualifier.class);
			qualifierAnnotation.param("value", "userDetailsService");
			
			JMethod configure = securityConfig.method(JMod.PROTECTED, void.class, "configure");
			configure.param(HttpSecurity.class, "http");
			configure.annotate(Override.class);
			
			JBlock configureBlock=configure.body();
			configureBlock.directStatement("http");
			configureBlock.directStatement(".authorizeRequests()");
			configureBlock.directStatement(".antMatchers(\"/css/**\",\"/img/**\",\"/js/**\",\"/auth/**\",\"/login/**\").permitAll()");
			configureBlock.directStatement(".and()");
			configureBlock.directStatement(".authorizeRequests().anyRequest().fullyAuthenticated().and()");
			configureBlock.directStatement(".formLogin().and().csrf()");
			configureBlock.directStatement(".csrfTokenRepository(csrfTokenRepository()).and()");
			configureBlock.directStatement(".addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);");
			
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
			
	}
	private void generateCsrfHeaderFilter()
	{
		
		JCodeModel codeModel = new JCodeModel();
			JDefinedClass csrfFilter=null;
			try {
				csrfFilter = codeModel._class(JMod.PUBLIC, directory+"boot.CsrfHeaderFilter", ClassType.CLASS);
				csrfFilter._extends(OncePerRequestFilter.class);
			} catch (JClassAlreadyExistsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			csrfFilter.annotate(Configuration.class);
			JAnnotationUse orderAnnotation=csrfFilter.annotate(Order.class);
			orderAnnotation.param("value", SecurityProperties.ACCESS_OVERRIDE_ORDER);
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
			waInit = codeModel._class(JMod.PUBLIC, directory+"boot.MvcWebApplicationInitializer", ClassType.CLASS);
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
		rootConfigBlock.directStatement("return new Class[] {"+directory+"boot.config.SecurityConfig.class};");
	
		
		
		saveFile(codeModel);
	}
	private void generateMyUserDetailService()
	{
		
	}
	private void generateSecurityWebappInitializer()
	{
		JCodeModel codeModel = new JCodeModel();
		JDefinedClass securityWebAppInit=null;
		try {
			securityWebAppInit = codeModel._class(JMod.PUBLIC, directory+"boot.SecurityWebApplicationInitializer", ClassType.CLASS);
			securityWebAppInit._extends(AbstractSecurityWebApplicationInitializer.class);
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		securityWebAppInit.annotate(Configuration.class);
		securityWebAppInit.constructor(JMod.PUBLIC);
		
		saveFile(codeModel);
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
