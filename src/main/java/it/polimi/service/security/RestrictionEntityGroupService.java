
package it.polimi.service.security;

import java.util.List;

import it.polimi.searchbean.security.RestrictionEntityGroupSearchBean;

public interface RestrictionEntityGroupService {


    public List<it.polimi.model.security.RestrictionEntityGroup> findById(Long RestrictionEntityGroupId);

    public List<it.polimi.model.security.RestrictionEntityGroup> find(RestrictionEntityGroupSearchBean RestrictionEntityGroup);

    public void deleteById(Long RestrictionEntityGroupId);

    public it.polimi.model.security.RestrictionEntityGroup insert(it.polimi.model.security.RestrictionEntityGroup RestrictionEntityGroup);

    public it.polimi.model.security.RestrictionEntityGroup update(it.polimi.model.security.RestrictionEntityGroup RestrictionEntityGroup);

}
