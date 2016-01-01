
package it.anggen.service.security;

import java.util.List;
import it.anggen.repository.security.RestrictionEntityGroupRepository;
import it.anggen.searchbean.security.RestrictionEntityGroupSearchBean;
import it.anggen.service.security.RestrictionEntityGroupService;
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
    public List<it.anggen.model.security.RestrictionEntityGroup> findById(Long restrictionEntityGroupId) {
        return restrictionEntityGroupRepository.findByRestrictionEntityGroupId(restrictionEntityGroupId);
    }

    @Override
    public List<it.anggen.model.security.RestrictionEntityGroup> find(RestrictionEntityGroupSearchBean restrictionEntityGroup) {
        return restrictionEntityGroupRepository.findByRestrictionEntityGroupIdAndCanCreateAndCanSearchAndCanDeleteAndCanUpdateAndEntityGroupAndRole(restrictionEntityGroup.getRestrictionEntityGroupId(),restrictionEntityGroup.getCanCreate(),restrictionEntityGroup.getCanSearch(),restrictionEntityGroup.getCanDelete(),restrictionEntityGroup.getCanUpdate(),restrictionEntityGroup.getEntityGroup(),restrictionEntityGroup.getRole());
    }

    @Override
    public void deleteById(Long restrictionEntityGroupId) {
        restrictionEntityGroupRepository.delete(restrictionEntityGroupId);
        return;
    }

    @Override
    public it.anggen.model.security.RestrictionEntityGroup insert(it.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup) {
        return restrictionEntityGroupRepository.save(restrictionEntityGroup);
    }

    @Override
    @Transactional
    public it.anggen.model.security.RestrictionEntityGroup update(it.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup) {
        it.anggen.model.security.RestrictionEntityGroup returnedRestrictionEntityGroup=restrictionEntityGroupRepository.save(restrictionEntityGroup);
        if (restrictionEntityGroup.getEntityGroup()!=null)
        {
        List<it.anggen.model.security.RestrictionEntityGroup> restrictionEntityGroupList = restrictionEntityGroupRepository.findByEntityGroup( restrictionEntityGroup.getEntityGroup());
        if (!restrictionEntityGroupList.contains(returnedRestrictionEntityGroup))
        restrictionEntityGroupList.add(returnedRestrictionEntityGroup);
        returnedRestrictionEntityGroup.getEntityGroup().setRestrictionEntityGroupList(restrictionEntityGroupList);
        }
        if (restrictionEntityGroup.getRole()!=null)
        {
        List<it.anggen.model.security.RestrictionEntityGroup> restrictionEntityGroupList = restrictionEntityGroupRepository.findByRole( restrictionEntityGroup.getRole());
        if (!restrictionEntityGroupList.contains(returnedRestrictionEntityGroup))
        restrictionEntityGroupList.add(returnedRestrictionEntityGroup);
        returnedRestrictionEntityGroup.getRole().setRestrictionEntityGroupList(restrictionEntityGroupList);
        }
         return returnedRestrictionEntityGroup;
    }

}
