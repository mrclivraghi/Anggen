
package it.polimi.service;

import java.util.List;

import it.polimi.model.domain.Restriction;
import it.polimi.repository.RestrictionRepository;
import it.polimi.searchbean.RestrictionSearchBean;
import it.polimi.service.RestrictionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestrictionServiceImpl
    implements RestrictionService
{

    @Autowired
    public RestrictionRepository restrictionRepository;

    @Override
    public List<Restriction> findById(Long restrictionId) {
        return restrictionRepository.findByRestrictionId(restrictionId);
    }

    @Override
    public List<Restriction> find(RestrictionSearchBean restriction) {
        return restrictionRepository.findByRestrictionIdAndCreateAndUpdateAndSearchAndDeleteAndRoleAndEntity(restriction.getRestrictionId(),restriction.getCreate(),restriction.getUpdate(),restriction.getSearch(),restriction.getDelete(),restriction.getRole(),restriction.getEntity());
    }

    @Override
    public void deleteById(Long restrictionId) {
        restrictionRepository.delete(restrictionId);
        return;
    }

    @Override
    public Restriction insert(Restriction restriction) {
        return restrictionRepository.save(restriction);
    }

    @Override
    @Transactional
    public Restriction update(Restriction restriction) {
        Restriction returnedRestriction=restrictionRepository.save(restriction);
        if (restriction.getRole()!=null)
        {
        List<Restriction> restrictionList = restrictionRepository.findByRole( restriction.getRole());
        if (!restrictionList.contains(returnedRestriction))
        restrictionList.add(returnedRestriction);
        returnedRestriction.getRole().setRestrictionList(restrictionList);
        }
        if (restriction.getEntity()!=null)
        {
        List<Restriction> restrictionList = restrictionRepository.findByEntity( restriction.getEntity());
        if (!restrictionList.contains(returnedRestriction))
        restrictionList.add(returnedRestriction);
        returnedRestriction.getEntity().setRestrictionList(restrictionList);
        }
         return returnedRestriction;
    }

}
