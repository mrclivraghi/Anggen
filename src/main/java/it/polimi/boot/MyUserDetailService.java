package it.polimi.boot;


import it.polimi.model.domain.Role;
import it.polimi.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("userDetailsService")
public class MyUserDetailService implements UserDetailsService {

	//get user from the database, via Hibernate
	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(final String username) 
		throws UsernameNotFoundException {
	
		List<it.polimi.model.domain.User> userList = userRepository.findByUsername(username);
		if (userList==null || userList.size()==0)
			throw new UsernameNotFoundException("Username "+username+" not found");
		it.polimi.model.domain.User user = userList.get(0);
		List<GrantedAuthority> authorities = 
                                      buildUserAuthority(user.getRoleList());
		System.out.println("cerco user by "+username);
		return buildUserForAuthentication(user, authorities);
		
	}

	// Converts com.mkyong.users.model.User user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(it.polimi.model.domain.User user, 
		List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), 
			user.isEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(List<Role> roleList) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		for (Role role: roleList)
			setAuths.add(new SimpleGrantedAuthority(role.getRole()));

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

}