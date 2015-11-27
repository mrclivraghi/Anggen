
package it.polimi.service;

import java.util.List;

import it.polimi.model.domain.Restriction;
import it.polimi.searchbean.RestrictionSearchBean;

public interface RestrictionService {


    public List<Restriction> findById(Long restrictionId);

    public List<Restriction> find(RestrictionSearchBean restriction);

    public void deleteById(Long restrictionId);

    public Restriction insert(Restriction restriction);

    public Restriction update(Restriction restriction);

}
