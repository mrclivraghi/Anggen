
package it.anggen.security;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import it.anggen.boot.AngGenAuthenticationProvider;
import it.anggen.utils.MessageResponse;


@Controller
@RequestMapping("/auth")
public class LoginController {


	@Autowired
	private UserDetailsService userService;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;
	
	@Autowired
	AngGenAuthenticationProvider customAuthProvider;
	
	
	@RequestMapping(value="authenticate",method=RequestMethod.POST,produces="application/json")
	@ResponseBody
	public ResponseEntity authenticate(@RequestParam("username") String username, @RequestParam("password") String password)
	{
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication=null;
		SecurityContextHolder.getContext().setAuthentication(null);
		try
		{
			authentication=customAuthProvider.authenticate(authenticationToken);
		} catch (AuthenticationException e)
		{
			return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),false));
		} 
		System.out.println(RequestContextHolder.currentRequestAttributes().getSessionId());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		((CredentialsContainer) authentication).eraseCredentials();
		return ResponseEntity.ok().body(authentication);
	}
	
	
	@RequestMapping(value="logout",method=RequestMethod.POST,produces="application/json")
	@ResponseBody
	public ResponseEntity logout()
	{
		Authentication authentication=null;
		SecurityContextHolder.getContext().setAuthentication(null);
		System.out.println(RequestContextHolder.currentRequestAttributes().getSessionId());
		return ResponseEntity.ok().body(new MessageResponse("loggedOut", false));
	}
	
	
	

    

}
