
package it.generated.anggen.service.security;

import java.util.List;
import it.generated.anggen.searchbean.security.RestrictionEntityGroupSearchBean;

public interface RestrictionEntityGroupService {


    public List<it.generated.anggen.model.security.RestrictionEntityGroup> findById(Long RestrictionEntityGroupId);

    public List<it.generated.anggen.model.security.RestrictionEntityGroup> find(RestrictionEntityGroupSearchBean RestrictionEntityGroup);

    public void deleteById(Long RestrictionEntityGroupId);

    public it.generated.anggen.model.security.RestrictionEntityGroup insert(it.generated.anggen.model.security.RestrictionEntityGroup RestrictionEntityGroup);

    public it.generated.anggen.model.security.RestrictionEntityGroup update(it.generated.anggen.model.security.RestrictionEntityGroup RestrictionEntityGroup);

}
