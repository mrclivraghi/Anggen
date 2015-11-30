
package it.generated.anggen.service.security;

import java.util.List;
import it.generated.anggen.repository.security.RoleRepository;
import it.generated.anggen.repository.security.UserRepository;
import it.generated.anggen.searchbean.security.UserSearchBean;
import it.generated.anggen.service.security.UserService;
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
    public List<it.generated.anggen.model.security.User> findById(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public List<it.generated.anggen.model.security.User> find(UserSearchBean user) {
        return userRepository.findByEnabledAndPasswordAndUsernameAndUserIdAndRole(user.getEnabled(),user.getPassword(),user.getUsername(),user.getUserId(),user.getRoleList()==null? null :user.getRoleList().get(0));
    }

    @Override
    public void deleteById(Long userId) {
        userRepository.delete(userId);
        return;
    }

    @Override
    public it.generated.anggen.model.security.User insert(it.generated.anggen.model.security.User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public it.generated.anggen.model.security.User update(it.generated.anggen.model.security.User user) {
        if (user.getRoleList()!=null)
        for (it.generated.anggen.model.security.Role role: user.getRoleList())
        {
        it.generated.anggen.model.security.Role savedRole = roleRepository.findOne(role.getRoleId());
        Boolean found=false; 
        for (it.generated.anggen.model.security.User tempUser : savedRole.getUserList())
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
        it.generated.anggen.model.security.User returnedUser=userRepository.save(user);
         return returnedUser;
    }

}
