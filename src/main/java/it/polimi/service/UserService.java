
package it.polimi.service;

import java.util.List;

import it.polimi.model.domain.User;
import it.polimi.searchbean.UserSearchBean;

public interface UserService {


    public List<User> findById(Long userId);

    public List<User> find(UserSearchBean user);

    public void deleteById(Long userId);

    public User insert(User user);

    public User update(User user);

}
