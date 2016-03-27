
package it.anggen.boot;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MvcWebApplicationInitializer
    extends AbstractAnnotationConfigDispatcherServletInitializer
{


    @Override
    protected Class[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return null;
    }

    @Override
    protected Class[] getRootConfigClasses() {
        return new Class[] {it.anggen.boot.config.SecurityConfig.class};
    }

}
