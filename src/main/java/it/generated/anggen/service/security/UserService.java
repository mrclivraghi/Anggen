
package it.generated.anggen.service.security;

import java.util.List;
import it.generated.anggen.searchbean.security.UserSearchBean;

public interface UserService {


    public List<it.generated.anggen.model.security.User> findById(Long UserId);

    public List<it.generated.anggen.model.security.User> find(UserSearchBean User);

    public void deleteById(Long UserId);

    public it.generated.anggen.model.security.User insert(it.generated.anggen.model.security.User User);

    public it.generated.anggen.model.security.User update(it.generated.anggen.model.security.User User);

}
