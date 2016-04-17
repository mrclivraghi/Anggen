
package it.anggen.boot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ServerTestWebConfig
    extends WebMvcConfigurerAdapter
{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		super.addCorsMappings(registry);
	    registry.addMapping("/**")

        .allowedOrigins("http://localhost:3000");
	}




}
