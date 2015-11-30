
package it;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
public class TestApplication {


    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(TestApplication.class, args);
    }

}
