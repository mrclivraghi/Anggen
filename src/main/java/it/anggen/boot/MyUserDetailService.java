
package it.anggen.boot;

import it.anggen.model.LogType;
import it.anggen.model.OperationType;
import it.anggen.model.security.Role;
import it.anggen.model.security.User;
import it.anggen.repository.security.UserRepository;
import it.anggen.service.log.LogEntryService;

import java.util.List;
import java.util.Set;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class MyUserDetailService
    implements UserDetailsService
{

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private LogEntryService logEntryService;
    

    @Override
  //  @Transactional(readOnly=false)
    public UserDetails loadUserByUsername(final String username)
        throws UsernameNotFoundException
    {
        List<it.anggen.model.security.User> userList = (userRepository.findByUsername(username));
        if (userList==null || userList.size()==0)
        {
        	logEntryService.addLogEntry(username + " not found ", LogType.INFO, OperationType.LOGIN_FAILED, User.staticEntityId, null, null);
        	throw new UsernameNotFoundException("Username "+username+" not found");
        }
        it.anggen.model.security.User user = userList.get(0);
        logEntryService.addLogEntry(username + " found ", LogType.INFO, OperationType.LOGIN_SUCCESS, User.staticEntityId, user, null);
        List<org.springframework.security.core.GrantedAuthority> authorities = buildUserAuthority(user.getRoleList());
        return buildUserForAuthentication(user, authorities);
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(it.anggen.model.security.User user, List<GrantedAuthority> authorities) {
    	org.springframework.security.core.userdetails.User springUser= new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),user.getEnabled(), true, true, true, authorities);
    	return springUser;
    }

    private List<GrantedAuthority> buildUserAuthority(List<Role> roleList) {
        Set<GrantedAuthority> setAuths = (new java.util.HashSet<org.springframework.security.core.GrantedAuthority>());
        for (it.anggen.model.security.Role role: roleList)
        setAuths.add(new org.springframework.security.core.authority.SimpleGrantedAuthority(role.getRole()));
        List<org.springframework.security.core.GrantedAuthority> result = new java.util.ArrayList<org.springframework.security.core.GrantedAuthority>(setAuths);
        return result;
    }

}
