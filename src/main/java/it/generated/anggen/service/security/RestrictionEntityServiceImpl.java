
package it.generated.anggen.service.security;

import java.util.List;
import it.generated.anggen.repository.security.RestrictionEntityRepository;
import it.generated.anggen.searchbean.security.RestrictionEntitySearchBean;
import it.generated.anggen.service.security.RestrictionEntityService;
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
    public List<it.generated.anggen.model.security.RestrictionEntity> findById(Long restrictionEntityId) {
        return restrictionEntityRepository.findByRestrictionEntityId(restrictionEntityId);
    }

    @Override
    public List<it.generated.anggen.model.security.RestrictionEntity> find(RestrictionEntitySearchBean restrictionEntity) {
        return restrictionEntityRepository.findByRestrictionEntityIdAndCanCreateAndCanUpdateAndCanSearchAndCanDeleteAndRoleAndEntity(restrictionEntity.getRestrictionEntityId(),restrictionEntity.getCanCreate(),restrictionEntity.getCanUpdate(),restrictionEntity.getCanSearch(),restrictionEntity.getCanDelete(),restrictionEntity.getRole(),restrictionEntity.getEntity());
    }

    @Override
    public void deleteById(Long restrictionEntityId) {
        restrictionEntityRepository.delete(restrictionEntityId);
        return;
    }

    @Override
    public it.generated.anggen.model.security.RestrictionEntity insert(it.generated.anggen.model.security.RestrictionEntity restrictionEntity) {
        return restrictionEntityRepository.save(restrictionEntity);
    }

    @Override
    @Transactional
    public it.generated.anggen.model.security.RestrictionEntity update(it.generated.anggen.model.security.RestrictionEntity restrictionEntity) {
        it.generated.anggen.model.security.RestrictionEntity returnedRestrictionEntity=restrictionEntityRepository.save(restrictionEntity);
        if (restrictionEntity.getRole()!=null)
        {
        List<it.generated.anggen.model.security.RestrictionEntity> restrictionEntityList = restrictionEntityRepository.findByRole( restrictionEntity.getRole());
        if (!restrictionEntityList.contains(returnedRestrictionEntity))
        restrictionEntityList.add(returnedRestrictionEntity);
        returnedRestrictionEntity.getRole().setRestrictionEntityList(restrictionEntityList);
        }
        if (restrictionEntity.getEntity()!=null)
        {
        List<it.generated.anggen.model.security.RestrictionEntity> restrictionEntityList = restrictionEntityRepository.findByEntity( restrictionEntity.getEntity());
        if (!restrictionEntityList.contains(returnedRestrictionEntity))
        restrictionEntityList.add(returnedRestrictionEntity);
        returnedRestrictionEntity.getEntity().setRestrictionEntityList(restrictionEntityList);
        }
         return returnedRestrictionEntity;
    }

}
