
package it.anggen.boot.config;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import it.anggen.utils.CustomDateSerializer;
import it.anggen.utils.CustomJsonDateDeserializer;
import it.anggen.utils.CustomJsonTimeDeserializer;
import it.anggen.utils.CustomTimeSerializer;
import it.anggen.utils.HibernateAwareObjectMapper;

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

	
	@Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        
        it.anggen.utils.HibernateAwareObjectMapper hibernateAwareObjectMapper = new it.anggen.utils.HibernateAwareObjectMapper();
        
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Date.class, new CustomJsonDateDeserializer());
        simpleModule.addDeserializer(Time.class, new CustomJsonTimeDeserializer());
        
        simpleModule.addSerializer(Date.class,new CustomDateSerializer());
        simpleModule.addSerializer(Time.class,new CustomTimeSerializer());
        hibernateAwareObjectMapper.registerModule(simpleModule);
        hibernateAwareObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonConverter.setObjectMapper(hibernateAwareObjectMapper);
        return jsonConverter;
    }
	
	
	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
    }


}
