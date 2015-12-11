
package it.polimi;

import it.polimi.boot.config.AnGenAppConfig;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AnGenAppConfig.class})
public class AnggenApplication {


    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(AnggenApplication.class, args);
    }

}
