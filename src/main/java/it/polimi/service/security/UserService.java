
package it.polimi.service.security;

import java.util.List;

import it.polimi.searchbean.security.UserSearchBean;

public interface UserService {


    public List<it.polimi.model.security.User> findById(Long UserId);

    public List<it.polimi.model.security.User> find(UserSearchBean User);

    public void deleteById(Long UserId);

    public it.polimi.model.security.User insert(it.polimi.model.security.User User);

    public it.polimi.model.security.User update(it.polimi.model.security.User User);

}
