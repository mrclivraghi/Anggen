
package it.polimi;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
public class AnggenApplication {


    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(AnggenApplication.class, args);
    }

}
