
package it.anggen.controller.entity;

import java.util.List;
import it.anggen.searchbean.entity.EntitySearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.entity.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/entity-front")
public class EntityFrontController {

    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.entity.Entity.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
    	return "entity-front";
    }

   

}
