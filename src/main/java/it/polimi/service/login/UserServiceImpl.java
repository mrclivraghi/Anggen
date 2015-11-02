
package it.polimi.service.login;

import java.util.List;
import it.polimi.model.login.User;
import it.polimi.repository.login.AuthorityRepository;
import it.polimi.repository.login.UserRepository;
import it.polimi.searchbean.login.UserSearchBean;
import it.polimi.service.login.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl
    implements UserService
{

    @org.springframework.beans.factory.annotation.Autowired
    public UserRepository userRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public AuthorityRepository authorityRepository;

    @Override
    public List<User> findById(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public List<User> find(UserSearchBean user) {
        return userRepository.findByUserIdAndUsernameAndPasswordAndRoleAndEnabledAndAuthority(user.getUserId(),user.getUsername(),user.getPassword(),user.getRole(),user.getEnabled(),user.getAuthorityList()==null? null :user.getAuthorityList().get(0));
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
        if (user.getAuthorityList()!=null)
        for (it.polimi.model.login.Authority authority: user.getAuthorityList())
        {
        it.polimi.model.login.Authority savedAuthority = authorityRepository.findOne(authority.getAuthorityId());
        Boolean found=false; 
        for (User tempUser : savedAuthority.getUserList())
        {
        if (tempUser.getUserId().equals(user.getUserId()))
        {
        found=true;
        break;
        }
        }
        if (!found)
        savedAuthority.getUserList().add(user);
        }
        User returnedUser=userRepository.save(user);
         return returnedUser;
    }

}
