
package it.polimi.service.security;

import java.util.List;

import it.polimi.searchbean.security.RoleSearchBean;

public interface RoleService {


    public List<it.polimi.model.security.Role> findById(Integer RoleId);

    public List<it.polimi.model.security.Role> find(RoleSearchBean Role);

    public void deleteById(Integer RoleId);

    public it.polimi.model.security.Role insert(it.polimi.model.security.Role Role);

    public it.polimi.model.security.Role update(it.polimi.model.security.Role Role);

}
