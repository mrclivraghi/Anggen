
package it.polimi.service;

import java.util.List;

import it.polimi.model.security.RestrictionField;
import it.polimi.searchbean.RestrictionFieldSearchBean;

public interface RestrictionFieldService {


    public List<RestrictionField> findById(Long restrictionFieldId);

    public List<RestrictionField> find(RestrictionFieldSearchBean restrictionField);

    public void deleteById(Long restrictionFieldId);

    public RestrictionField insert(RestrictionField restrictionField);

    public RestrictionField update(RestrictionField restrictionField);

}
