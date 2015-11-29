
package it.polimi.service;

import java.util.List;

import it.polimi.model.security.RestrictionEntityGroup;
import it.polimi.repository.RestrictionEntityGroupRepository;
import it.polimi.searchbean.RestrictionEntityGroupSearchBean;
import it.polimi.service.RestrictionEntityGroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestrictionEntityGroupServiceImpl
    implements RestrictionEntityGroupService
{

    @Autowired
    public RestrictionEntityGroupRepository restrictionEntityGroupRepository;

    @Override
    public List<RestrictionEntityGroup> findById(Long restrictionEntityGroupId) {
        return restrictionEntityGroupRepository.findByRestrictionEntityGroupId(restrictionEntityGroupId);
    }

    @Override
    public List<RestrictionEntityGroup> find(RestrictionEntityGroupSearchBean restrictionEntityGroup) {
        return restrictionEntityGroupRepository.findByRestrictionEntityGroupIdAndCanCreateAndCanUpdateAndCanSearchAndCanDeleteAndEntityGroupAndRole(restrictionEntityGroup.getRestrictionEntityGroupId(),restrictionEntityGroup.getCanCreate(),restrictionEntityGroup.getCanUpdate(),restrictionEntityGroup.getCanSearch(),restrictionEntityGroup.getCanDelete(),restrictionEntityGroup.getEntityGroup(),restrictionEntityGroup.getRole());
    }

    @Override
    public void deleteById(Long restrictionEntityGroupId) {
        restrictionEntityGroupRepository.delete(restrictionEntityGroupId);
        return;
    }

    @Override
    public RestrictionEntityGroup insert(RestrictionEntityGroup restrictionEntityGroup) {
        return restrictionEntityGroupRepository.save(restrictionEntityGroup);
    }

    @Override
    @Transactional
    public RestrictionEntityGroup update(RestrictionEntityGroup restrictionEntityGroup) {
        RestrictionEntityGroup returnedRestrictionEntityGroup=restrictionEntityGroupRepository.save(restrictionEntityGroup);
        if (restrictionEntityGroup.getEntityGroup()!=null)
        {
        List<RestrictionEntityGroup> restrictionEntityGroupList = restrictionEntityGroupRepository.findByEntityGroup( restrictionEntityGroup.getEntityGroup());
        if (!restrictionEntityGroupList.contains(returnedRestrictionEntityGroup))
        restrictionEntityGroupList.add(returnedRestrictionEntityGroup);
        returnedRestrictionEntityGroup.getEntityGroup().setRestrictionEntityGroupList(restrictionEntityGroupList);
        }
        if (restrictionEntityGroup.getRole()!=null)
        {
        List<RestrictionEntityGroup> restrictionEntityGroupList = restrictionEntityGroupRepository.findByRole( restrictionEntityGroup.getRole());
        if (!restrictionEntityGroupList.contains(returnedRestrictionEntityGroup))
        restrictionEntityGroupList.add(returnedRestrictionEntityGroup);
        returnedRestrictionEntityGroup.getRole().setRestrictionEntityGroupList(restrictionEntityGroupList);
        }
         return returnedRestrictionEntityGroup;
    }

}
