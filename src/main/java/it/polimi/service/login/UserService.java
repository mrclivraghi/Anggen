
package it.polimi.service.login;

import java.util.List;
import it.polimi.model.login.User;
import it.polimi.searchbean.login.UserSearchBean;

public interface UserService {


    public List<User> findById(Long userId);

    public List<User> find(UserSearchBean user);

    public void deleteById(Long userId);

    public User insert(User user);

    public User update(User user);

}
