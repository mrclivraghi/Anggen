
package it.polimi.service;

import java.util.List;

import it.polimi.model.domain.RestrictionEntity;
import it.polimi.searchbean.RestrictionEntitySearchBean;

public interface RestrictionEntityService {


    public List<RestrictionEntity> findById(Long restrictionEntityId);

    public List<RestrictionEntity> find(RestrictionEntitySearchBean restrictionEntity);

    public void deleteById(Long restrictionEntityId);

    public RestrictionEntity insert(RestrictionEntity restrictionEntity);

    public RestrictionEntity update(RestrictionEntity restrictionEntity);

}
