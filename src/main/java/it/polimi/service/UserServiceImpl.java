
package it.polimi.service;

import java.util.List;

import it.polimi.model.domain.User;
import it.polimi.repository.RoleRepository;
import it.polimi.repository.UserRepository;
import it.polimi.searchbean.UserSearchBean;
import it.polimi.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl
    implements UserService
{

    @org.springframework.beans.factory.annotation.Autowired
    public UserRepository userRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RoleRepository roleRepository;

    @Override
    public List<User> findById(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public List<User> find(UserSearchBean user) {
        return userRepository.findByUserIdAndUsernameAndPasswordAndEnabledAndRole(user.getUserId(),user.getUsername(),user.getPassword(),user.getEnabled(),user.getRoleList()==null? null :user.getRoleList().get(0));
    }

    @Override
    public void deleteById(Long userId) {
        userRepository.delete(userId);
        return;
    }

    @Override
    public User insert(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        if (user.getRoleList()!=null)
        for (it.polimi.model.domain.Role role: user.getRoleList())
        {
        it.polimi.model.domain.Role savedRole = roleRepository.findOne(role.getRoleId());
        Boolean found=false; 
        for (User tempUser : savedRole.getUserList())
        {
        if (tempUser.getUserId().equals(user.getUserId()))
        {
        found=true;
        break;
        }
        }
        if (!found)
        savedRole.getUserList().add(user);
        }
        User returnedUser=userRepository.save(user);
         return returnedUser;
    }

}
