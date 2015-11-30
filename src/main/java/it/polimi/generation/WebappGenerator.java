package it.polimi.generation;

import it.generated.anggen.GeneratedApplication;
import it.polimi.boot.config.SecurityConfig;
import it.polimi.utils.Utility;

import java.io.File;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.util.WebUtils;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;

public class WebappGenerator {

	private String applicationName;
	private String directory;
	
	public WebappGenerator() {
		applicationName=Generator.applicationName;
		directory=Generator.mainPackage+applicationName+".";
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
		
	}
	private void generateSecurityConfig()
	{
		
	}
	private void generateCsrfHeaderFilter()
	{
		/*

		CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
				.getName());
		if (csrf != null) {
			Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
			String token = csrf.getToken();
			if (cookie == null || token != null
					&& !token.equals(cookie.getValue())) {
				cookie = new Cookie("XSRF-TOKEN", token);
				System.out.println(token);
				cookie.setPath("/ServerTestApp");
				response.addCookie(cookie);
			}
		}
		filterChain.doFilter(request, response);
	}
		 * 
		 */
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
