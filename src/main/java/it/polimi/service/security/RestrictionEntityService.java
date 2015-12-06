
package it.polimi.service.security;

import java.util.List;

import it.polimi.searchbean.security.RestrictionEntitySearchBean;

public interface RestrictionEntityService {


    public List<it.polimi.model.security.RestrictionEntity> findById(Long RestrictionEntityId);

    public List<it.polimi.model.security.RestrictionEntity> find(RestrictionEntitySearchBean RestrictionEntity);

    public void deleteById(Long RestrictionEntityId);

    public it.polimi.model.security.RestrictionEntity insert(it.polimi.model.security.RestrictionEntity RestrictionEntity);

    public it.polimi.model.security.RestrictionEntity update(it.polimi.model.security.RestrictionEntity RestrictionEntity);

}
