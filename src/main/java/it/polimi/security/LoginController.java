
package it.polimi.security;

import java.security.Principal;
import java.util.List;

import it.polimi.model.Example;
import it.polimi.searchbean.ExampleSearchBean;
import it.polimi.service.ExampleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    public ExampleService exampleService;
    private final static Logger log = LoggerFactory.getLogger(Example.class);

    /*@RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "auth";
    }*/
    
  //Spring Security see this :
  	@RequestMapping( method = RequestMethod.GET)
  	public ModelAndView login(
  		@RequestParam(value = "error", required = false) String error,
  		@RequestParam(value = "logout", required = false) String logout) {

  		ModelAndView model = new ModelAndView();
  		if (error != null) {
  			model.addObject("error", "Invalid username and password!");
  		}

  		if (logout != null) {
  			model.addObject("msg", "You've been logged out successfully.");
  		}
  		model.setViewName("auth");

  		return model;

  	}


    

}
