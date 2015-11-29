
package it.polimi.service;

import java.util.List;

import it.polimi.model.security.RestrictionEntity;
import it.polimi.repository.RestrictionEntityRepository;
import it.polimi.searchbean.RestrictionEntitySearchBean;
import it.polimi.service.RestrictionEntityService;

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
    public List<RestrictionEntity> findById(Long restrictionEntityId) {
        return restrictionEntityRepository.findByRestrictionEntityId(restrictionEntityId);
    }

    @Override
    public List<RestrictionEntity> find(RestrictionEntitySearchBean restrictionEntity) {
        return restrictionEntityRepository.findByRestrictionEntityIdAndCanCreateAndCanUpdateAndCanSearchAndCanDeleteAndRoleAndEntity(restrictionEntity.getRestrictionEntityId(),restrictionEntity.getCanCreate(),restrictionEntity.getCanUpdate(),restrictionEntity.getCanSearch(),restrictionEntity.getCanDelete(),restrictionEntity.getRole(),restrictionEntity.getEntity());
    }

    @Override
    public void deleteById(Long restrictionEntityId) {
        restrictionEntityRepository.delete(restrictionEntityId);
        return;
    }

    @Override
    public RestrictionEntity insert(RestrictionEntity restrictionEntity) {
        return restrictionEntityRepository.save(restrictionEntity);
    }

    @Override
    @Transactional
    public RestrictionEntity update(RestrictionEntity restrictionEntity) {
        RestrictionEntity returnedRestrictionEntity=restrictionEntityRepository.save(restrictionEntity);
        if (restrictionEntity.getRole()!=null)
        {
        List<RestrictionEntity> restrictionEntityList = restrictionEntityRepository.findByRole( restrictionEntity.getRole());
        if (!restrictionEntityList.contains(returnedRestrictionEntity))
        restrictionEntityList.add(returnedRestrictionEntity);
        returnedRestrictionEntity.getRole().setRestrictionEntityList(restrictionEntityList);
        }
        if (restrictionEntity.getEntity()!=null)
        {
        List<RestrictionEntity> restrictionEntityList = restrictionEntityRepository.findByEntity( restrictionEntity.getEntity());
        if (!restrictionEntityList.contains(returnedRestrictionEntity))
        restrictionEntityList.add(returnedRestrictionEntity);
        returnedRestrictionEntity.getEntity().setRestrictionEntityList(restrictionEntityList);
        }
         return returnedRestrictionEntity;
    }

}
