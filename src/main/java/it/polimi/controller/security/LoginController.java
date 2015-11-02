package it.polimi.controller.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/login")
public class LoginController {
	@RequestMapping(method = RequestMethod.GET)
	public String success(ModelMap map) {
 		map.addAttribute("msg", "Successfully logged in");
		return "success";
 	}
}