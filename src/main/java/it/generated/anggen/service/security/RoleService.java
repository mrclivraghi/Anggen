
package it.generated.anggen.service.security;

import java.util.List;
import it.generated.anggen.searchbean.security.RoleSearchBean;

public interface RoleService {


    public List<it.generated.anggen.model.security.Role> findById(Integer RoleId);

    public List<it.generated.anggen.model.security.Role> find(RoleSearchBean Role);

    public void deleteById(Integer RoleId);

    public it.generated.anggen.model.security.Role insert(it.generated.anggen.model.security.Role Role);

    public it.generated.anggen.model.security.Role update(it.generated.anggen.model.security.Role Role);

}
