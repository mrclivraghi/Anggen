
package it.polimi.service.security;

import java.util.List;

import it.polimi.repository.security.RestrictionEntityRepository;
import it.polimi.searchbean.security.RestrictionEntitySearchBean;
import it.polimi.service.security.RestrictionEntityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestrictionEntityServiceImpl
    implements RestrictionEntityService
{

    @Autowired
    public RestrictionEntityRepository restrictionEntityRepository;

    @Override
    public List<it.polimi.model.security.RestrictionEntity> findById(Long restrictionEntityId) {
        return restrictionEntityRepository.findByRestrictionEntityId(restrictionEntityId);
    }

    @Override
    public List<it.polimi.model.security.RestrictionEntity> find(RestrictionEntitySearchBean restrictionEntity) {
        return restrictionEntityRepository.findByRestrictionEntityIdAndCanCreateAndCanUpdateAndCanSearchAndCanDeleteAndRoleAndEntity(restrictionEntity.getRestrictionEntityId(),restrictionEntity.getCanCreate(),restrictionEntity.getCanUpdate(),restrictionEntity.getCanSearch(),restrictionEntity.getCanDelete(),restrictionEntity.getRole(),restrictionEntity.getEntity());
    }

    @Override
    public void deleteById(Long restrictionEntityId) {
        restrictionEntityRepository.delete(restrictionEntityId);
        return;
    }

    @Override
    public it.polimi.model.security.RestrictionEntity insert(it.polimi.model.security.RestrictionEntity restrictionEntity) {
        return restrictionEntityRepository.save(restrictionEntity);
    }

    @Override
    @Transactional
    public it.polimi.model.security.RestrictionEntity update(it.polimi.model.security.RestrictionEntity restrictionEntity) {
        it.polimi.model.security.RestrictionEntity returnedRestrictionEntity=restrictionEntityRepository.save(restrictionEntity);
        if (restrictionEntity.getRole()!=null)
        {
        List<it.polimi.model.security.RestrictionEntity> restrictionEntityList = restrictionEntityRepository.findByRole( restrictionEntity.getRole());
        if (!restrictionEntityList.contains(returnedRestrictionEntity))
        restrictionEntityList.add(returnedRestrictionEntity);
        returnedRestrictionEntity.getRole().setRestrictionEntityList(restrictionEntityList);
        }
        if (restrictionEntity.getEntity()!=null)
        {
        List<it.polimi.model.security.RestrictionEntity> restrictionEntityList = restrictionEntityRepository.findByEntity( restrictionEntity.getEntity());
        if (!restrictionEntityList.contains(returnedRestrictionEntity))
        restrictionEntityList.add(returnedRestrictionEntity);
        returnedRestrictionEntity.getEntity().setRestrictionEntityList(restrictionEntityList);
        }
         return returnedRestrictionEntity;
    }

}
