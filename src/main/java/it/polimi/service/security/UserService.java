
package it.polimi.service.security;

import java.util.List;
import it.polimi.model.security.User;
import it.polimi.searchbean.security.UserSearchBean;

public interface UserService {


    public List<User> findById(Long userId);

    public List<User> find(UserSearchBean user);

    public void deleteById(Long userId);

    public User insert(User user);

    public User update(User user);

}
