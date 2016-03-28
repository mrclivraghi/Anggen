package it.anggen.controller;

import it.anggen.reflection.EntityManager;
import it.anggen.reflection.EntityManagerImpl;
import it.anggen.security.RestrictionItem;
import it.anggen.security.UserManager;
import it.anggen.model.entity.Entity;
import it.anggen.model.field.Annotation;
import it.anggen.model.field.Field;
import it.anggen.model.security.RestrictionEntity;
import it.anggen.model.security.RestrictionEntityGroup;
import it.anggen.model.security.RestrictionField;
import it.anggen.model.security.User;
import it.anggen.searchbean.security.UserSearchBean;
import it.anggen.service.security.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codahale.metrics.annotation.Timed;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	UserService userService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	@Timed
	public String getHome(){
		return "home";
	}

}
