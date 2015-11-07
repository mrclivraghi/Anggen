package it.polimi.boot;


import it.polimi.boot.config.SecurityConfig;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class MvcWebApplicationInitializer extends
AbstractAnnotationConfigDispatcherServletInitializer {

@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return null;
	}

@Override
protected Class<?>[] getRootConfigClasses() {
return new Class[] { SecurityConfig.class };
}

// ... other overrides ...
}