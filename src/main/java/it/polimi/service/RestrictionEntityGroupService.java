
package it.polimi.service;

import java.util.List;

import it.polimi.model.security.RestrictionEntityGroup;
import it.polimi.searchbean.RestrictionEntityGroupSearchBean;

public interface RestrictionEntityGroupService {


    public List<RestrictionEntityGroup> findById(Long restrictionEntityGroupId);

    public List<RestrictionEntityGroup> find(RestrictionEntityGroupSearchBean restrictionEntityGroup);

    public void deleteById(Long restrictionEntityGroupId);

    public RestrictionEntityGroup insert(RestrictionEntityGroup restrictionEntityGroup);

    public RestrictionEntityGroup update(RestrictionEntityGroup restrictionEntityGroup);

}
