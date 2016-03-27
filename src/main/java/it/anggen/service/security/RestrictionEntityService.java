
package it.anggen.service.security;

import java.util.List;
import it.anggen.searchbean.security.RestrictionEntitySearchBean;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

public interface RestrictionEntityService {


    public List<it.anggen.model.security.RestrictionEntity> findById(Long RestrictionEntityId);

    public List<it.anggen.model.security.RestrictionEntity> find(RestrictionEntitySearchBean RestrictionEntity);

    public void deleteById(Long RestrictionEntityId);

    public it.anggen.model.security.RestrictionEntity insert(it.anggen.model.security.RestrictionEntity RestrictionEntity);

    public it.anggen.model.security.RestrictionEntity update(it.anggen.model.security.RestrictionEntity RestrictionEntity);

    public Page<it.anggen.model.security.RestrictionEntity> findByPage(
        @PathVariable
        Integer pageNumber);

}
