
package it.polimi.service.security;

import java.util.List;
import it.polimi.model.security.User;
import it.polimi.repository.security.UserRepository;
import it.polimi.searchbean.security.UserSearchBean;
import it.polimi.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl
    implements UserService
{

    @Autowired
    public UserRepository userRepository;

    @Override
    public List<User> findById(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public List<User> find(UserSearchBean user) {
        return userRepository.findByUserIdAndUsernameAndPasswordAndEnabledAndRole(user.getUserId(),user.getUsername(),user.getPassword(),user.getEnabled(),user.getRole());
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
        User returnedUser=userRepository.save(user);
        if (user.getRole()!=null)
        {
        List<User> userList = userRepository.findByRole( user.getRole());
        if (!userList.contains(returnedUser))
        userList.add(returnedUser);
        returnedUser.getRole().setUserList(userList);
        }
         return returnedUser;
    }

}
