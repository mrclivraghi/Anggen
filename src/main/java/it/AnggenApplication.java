
package it;

import java.security.Principal;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableCaching
@RestController
public class AnggenApplication {


    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(AnggenApplication.class, args);
    }
    
    @RequestMapping("/user")
    public Principal user(Principal user) {
      return user;
    }

}
