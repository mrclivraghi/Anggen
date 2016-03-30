package it.anggen.boot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import it.anggen.model.LogType;
import it.anggen.model.OperationType;
import it.anggen.model.security.Role;
import it.anggen.model.security.User;
import it.anggen.repository.security.UserRepository;
import it.anggen.service.log.LogEntryService;

@Component
public class AngGenAuthenticationProvider implements AuthenticationProvider {

	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
    private LogEntryService logEntryService;
	
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        PasswordEncoder encoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
        List<User> userList = userRepository.findByUserIdAndPasswordAndEnabledAndUsernameAndRole(null, null, true, username, null);
        if (userList!=null && userList.size()==1 && encoder.matches(password, userList.get(0).getPassword())) {
            List<GrantedAuthority> grantedAuths = buildUserAuthority(userList.get(0).getRoleList());
            logEntryService.addLogEntry(userList.get(0).getUserId() +"has logged in ", LogType.INFO, OperationType.LOGIN_SUCCESS, User.staticEntityId, userList.get(0), null);
            
            return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
        } else {
        	 logEntryService.addLogEntry(username +"-"+password+ " are bad ", LogType.INFO, OperationType.LOGIN_FAILED, User.staticEntityId, null, null);
            throw new BadCredentialsException("Your credentials are not right");
        }
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	
	private List<GrantedAuthority> buildUserAuthority(List<Role> roleList) {
	        Set<GrantedAuthority> setAuths = (new java.util.HashSet<org.springframework.security.core.GrantedAuthority>());
	        for (it.anggen.model.security.Role role: roleList)
	        	setAuths.add(new org.springframework.security.core.authority.SimpleGrantedAuthority(role.getRole()));
	        List<org.springframework.security.core.GrantedAuthority> result = new java.util.ArrayList<org.springframework.security.core.GrantedAuthority>(setAuths);
	        return result;
	    }

}
