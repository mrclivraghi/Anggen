
package it.anggen.boot;

import it.anggen.model.security.Role;
import it.anggen.repository.security.UserRepository;

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

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username)
        throws UsernameNotFoundException
    {
        List<it.anggen.model.security.User> userList = (userRepository.findByUsername(username));
        if (userList==null || userList.size()==0)
        throw new UsernameNotFoundException("Username "+username+" not found");
        it.anggen.model.security.User user = userList.get(0);
        List<org.springframework.security.core.GrantedAuthority> authorities = buildUserAuthority(user.getRoleList());
        return buildUserForAuthentication(user, authorities);
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(it.anggen.model.security.User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),user.getEnabled(), true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(List<Role> roleList) {
        Set<GrantedAuthority> setAuths = (new java.util.HashSet<org.springframework.security.core.GrantedAuthority>());
        for (it.anggen.model.security.Role role: roleList)
        setAuths.add(new org.springframework.security.core.authority.SimpleGrantedAuthority(role.getRole()));
        List<org.springframework.security.core.GrantedAuthority> result = new java.util.ArrayList<org.springframework.security.core.GrantedAuthority>(setAuths);
        return result;
    }

}
