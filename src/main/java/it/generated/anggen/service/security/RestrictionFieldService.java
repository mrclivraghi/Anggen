
package it.generated.anggen.service.security;

import java.util.List;
import it.generated.anggen.searchbean.security.RestrictionFieldSearchBean;

public interface RestrictionFieldService {


    public List<it.generated.anggen.model.security.RestrictionField> findById(Long RestrictionFieldId);

    public List<it.generated.anggen.model.security.RestrictionField> find(RestrictionFieldSearchBean RestrictionField);

    public void deleteById(Long RestrictionFieldId);

    public it.generated.anggen.model.security.RestrictionField insert(it.generated.anggen.model.security.RestrictionField RestrictionField);

    public it.generated.anggen.model.security.RestrictionField update(it.generated.anggen.model.security.RestrictionField RestrictionField);

}
