
package it.anggen.service.security;

import java.util.List;
import it.anggen.searchbean.security.RestrictionFieldSearchBean;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

public interface RestrictionFieldService {


    public List<it.anggen.model.security.RestrictionField> findById(Long RestrictionFieldId);

    public List<it.anggen.model.security.RestrictionField> find(RestrictionFieldSearchBean RestrictionField);

    public void deleteById(Long RestrictionFieldId);

    public it.anggen.model.security.RestrictionField insert(it.anggen.model.security.RestrictionField RestrictionField);

    public it.anggen.model.security.RestrictionField update(it.anggen.model.security.RestrictionField RestrictionField);

    public Page<it.anggen.model.security.RestrictionField> findByPage(
        @PathVariable
        Integer pageNumber);

}
