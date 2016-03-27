
package it.anggen.service.security;

import java.util.List;
import it.anggen.repository.security.RestrictionEntityRepository;
import it.anggen.searchbean.security.RestrictionEntitySearchBean;
import it.anggen.service.security.RestrictionEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestrictionEntityServiceImpl
    implements RestrictionEntityService
{

    @Autowired
    public RestrictionEntityRepository restrictionEntityRepository;
    private static Integer PAGE_SIZE = (5);

    @Override
    public List<it.anggen.model.security.RestrictionEntity> findById(Long restrictionEntityId) {
        return restrictionEntityRepository.findByRestrictionEntityId(restrictionEntityId);
    }

    @Override
    public Page<it.anggen.model.security.RestrictionEntity> findByPage(Integer pageNumber) {
        org.springframework.data.domain.PageRequest pageRequest = new org.springframework.data.domain.PageRequest(pageNumber - 1, PAGE_SIZE, org.springframework.data.domain.Sort.Direction.DESC, "restrictionEntityId");
        return restrictionEntityRepository.findAll(pageRequest);
    }

    @Override
    public List<it.anggen.model.security.RestrictionEntity> find(RestrictionEntitySearchBean restrictionEntity) {
        return restrictionEntityRepository.findByRestrictionEntityIdAndCanCreateAndCanUpdateAndCanSearchAndCanDeleteAndEntityAndRole(restrictionEntity.getRestrictionEntityId(),restrictionEntity.getCanCreate(),restrictionEntity.getCanUpdate(),restrictionEntity.getCanSearch(),restrictionEntity.getCanDelete(),restrictionEntity.getEntity(),restrictionEntity.getRole());
    }

    @Override
    public void deleteById(Long restrictionEntityId) {
        restrictionEntityRepository.delete(restrictionEntityId);
        return;
    }

    @Override
    public it.anggen.model.security.RestrictionEntity insert(it.anggen.model.security.RestrictionEntity restrictionEntity) {
        return restrictionEntityRepository.save(restrictionEntity);
    }

    @Override
    @Transactional
    public it.anggen.model.security.RestrictionEntity update(it.anggen.model.security.RestrictionEntity restrictionEntity) {
        it.anggen.model.security.RestrictionEntity returnedRestrictionEntity=restrictionEntityRepository.save(restrictionEntity);
        if (restrictionEntity.getEntity()!=null)
        {
        List<it.anggen.model.security.RestrictionEntity> restrictionEntityList = restrictionEntityRepository.findByEntity( restrictionEntity.getEntity());
        if (!restrictionEntityList.contains(returnedRestrictionEntity))
        restrictionEntityList.add(returnedRestrictionEntity);
        returnedRestrictionEntity.getEntity().setRestrictionEntityList(restrictionEntityList);
        }
        if (restrictionEntity.getRole()!=null)
        {
        List<it.anggen.model.security.RestrictionEntity> restrictionEntityList = restrictionEntityRepository.findByRole( restrictionEntity.getRole());
        if (!restrictionEntityList.contains(returnedRestrictionEntity))
        restrictionEntityList.add(returnedRestrictionEntity);
        returnedRestrictionEntity.getRole().setRestrictionEntityList(restrictionEntityList);
        }
         return returnedRestrictionEntity;
    }

}
