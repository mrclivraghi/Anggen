
package it.anggen.service.security;

import java.util.List;
import it.anggen.repository.security.RoleRepository;
import it.anggen.repository.security.UserRepository;
import it.anggen.searchbean.security.UserSearchBean;
import it.anggen.service.security.UserService;
import org.springframework.data.domain.Page;
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
    private static Integer PAGE_SIZE = (5);

    @Override
    public List<it.anggen.model.security.User> findById(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public Page<it.anggen.model.security.User> findByPage(Integer pageNumber) {
        org.springframework.data.domain.PageRequest pageRequest = new org.springframework.data.domain.PageRequest(pageNumber - 1, PAGE_SIZE, org.springframework.data.domain.Sort.Direction.DESC, "userId");
        return userRepository.findAll(pageRequest);
    }

    @Override
    public List<it.anggen.model.security.User> find(UserSearchBean user) {
        return userRepository.findByUserIdAndPasswordAndUsernameAndEnabledAndRole(user.getUserId(),user.getPassword(),user.getUsername(),user.getEnabled(),user.getRoleList()==null? null :user.getRoleList().get(0));
    }

    @Override
    public void deleteById(Long userId) {
        userRepository.delete(userId);
        return;
    }

    @Override
    public it.anggen.model.security.User insert(it.anggen.model.security.User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public it.anggen.model.security.User update(it.anggen.model.security.User user) {
        if (user.getRoleList()!=null)
        for (it.anggen.model.security.Role role: user.getRoleList())
        {
        it.anggen.model.security.Role savedRole = roleRepository.findOne(role.getRoleId());
        Boolean found=false; 
        for (it.anggen.model.security.User tempUser : savedRole.getUserList())
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
        it.anggen.model.security.User returnedUser=userRepository.save(user);
         return returnedUser;
    }

}
