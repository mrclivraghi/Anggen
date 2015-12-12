
package it.anggen.service.security;

import java.util.List;
import it.anggen.searchbean.security.RoleSearchBean;

public interface RoleService {


    public List<it.anggen.model.security.Role> findById(Integer RoleId);

    public List<it.anggen.model.security.Role> find(RoleSearchBean Role);

    public void deleteById(Integer RoleId);

    public it.anggen.model.security.Role insert(it.anggen.model.security.Role Role);

    public it.anggen.model.security.Role update(it.anggen.model.security.Role Role);

}
