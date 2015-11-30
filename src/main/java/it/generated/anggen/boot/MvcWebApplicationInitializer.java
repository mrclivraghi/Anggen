
package it.generated.anggen.boot;

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
        return new Class[] {it.generated.anggen.boot.config.SecurityConfig.class};
    }

}
