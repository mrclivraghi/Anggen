
package it.anggen.service.security;

import java.util.List;
import it.anggen.searchbean.security.UserSearchBean;

public interface UserService {


    public List<it.anggen.model.security.User> findById(Long UserId);

    public List<it.anggen.model.security.User> find(UserSearchBean User);

    public void deleteById(Long UserId);

    public it.anggen.model.security.User insert(it.anggen.model.security.User User);

    public it.anggen.model.security.User update(it.anggen.model.security.User User);

}
