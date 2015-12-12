
package it.anggen.service.security;

import java.util.List;
import it.anggen.searchbean.security.RestrictionEntityGroupSearchBean;

public interface RestrictionEntityGroupService {


    public List<it.anggen.model.security.RestrictionEntityGroup> findById(Long RestrictionEntityGroupId);

    public List<it.anggen.model.security.RestrictionEntityGroup> find(RestrictionEntityGroupSearchBean RestrictionEntityGroup);

    public void deleteById(Long RestrictionEntityGroupId);

    public it.anggen.model.security.RestrictionEntityGroup insert(it.anggen.model.security.RestrictionEntityGroup RestrictionEntityGroup);

    public it.anggen.model.security.RestrictionEntityGroup update(it.anggen.model.security.RestrictionEntityGroup RestrictionEntityGroup);

}
