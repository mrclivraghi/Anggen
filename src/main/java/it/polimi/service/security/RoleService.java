
package it.polimi.service.security;

import java.util.List;
import it.polimi.model.security.Role;
import it.polimi.searchbean.security.RoleSearchBean;

public interface RoleService {


    public List<Role> findById(Integer roleId);

    public List<Role> find(RoleSearchBean role);

    public void deleteById(Integer roleId);

    public Role insert(Role role);

    public Role update(Role role);

}
