
package it;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableCaching
public class ServerTestApplication {


    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(ServerTestApplication.class, args);
    }

    @Bean
    public Docket newsApi() {
        return new Docket(springfox.documentation.spi.DocumentationType.SWAGGER_2) 
.apiInfo(apiInfo()) 
.select() 
.build(); 

    }

    private ApiInfo apiInfo() {
        return new springfox.documentation.builders.ApiInfoBuilder() 
.title("Swagger documentation") 
.description("Swagger documentation") 
.termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open") 
.contact("Marco Livraghi") 
.license("Apache License Version 2.0") 
.licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE") 
.version("2.0") 
.build(); 

    }

}
