
package it.polimi.service.security;

import java.util.List;

import it.polimi.searchbean.security.RestrictionFieldSearchBean;

public interface RestrictionFieldService {


    public List<it.polimi.model.security.RestrictionField> findById(Long RestrictionFieldId);

    public List<it.polimi.model.security.RestrictionField> find(RestrictionFieldSearchBean RestrictionField);

    public void deleteById(Long RestrictionFieldId);

    public it.polimi.model.security.RestrictionField insert(it.polimi.model.security.RestrictionField RestrictionField);

    public it.polimi.model.security.RestrictionField update(it.polimi.model.security.RestrictionField RestrictionField);

}
