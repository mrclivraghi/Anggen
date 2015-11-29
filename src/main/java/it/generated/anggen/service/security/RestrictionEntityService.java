
package it.generated.anggen.service.security;

import java.util.List;
import it.generated.anggen.searchbean.security.RestrictionEntitySearchBean;

public interface RestrictionEntityService {


    public List<it.generated.anggen.model.security.RestrictionEntity> findById(Long RestrictionEntityId);

    public List<it.generated.anggen.model.security.RestrictionEntity> find(RestrictionEntitySearchBean RestrictionEntity);

    public void deleteById(Long RestrictionEntityId);

    public it.generated.anggen.model.security.RestrictionEntity insert(it.generated.anggen.model.security.RestrictionEntity RestrictionEntity);

    public it.generated.anggen.model.security.RestrictionEntity update(it.generated.anggen.model.security.RestrictionEntity RestrictionEntity);

}
