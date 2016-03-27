
package it.anggen.service.entity;

import java.util.List;
import it.anggen.repository.entity.EnumEntityRepository;
import it.anggen.repository.field.EnumValueRepository;
import it.anggen.searchbean.entity.EnumEntitySearchBean;
import it.anggen.service.entity.EnumEntityService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnumEntityServiceImpl
    implements EnumEntityService
{

    @org.springframework.beans.factory.annotation.Autowired
    public EnumEntityRepository enumEntityRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public EnumValueRepository enumValueRepository;
    private static Integer PAGE_SIZE = (5);

    @Override
    public List<it.anggen.model.entity.EnumEntity> findById(Long enumEntityId) {
        return enumEntityRepository.findByEnumEntityId(enumEntityId);
    }

    @Override
    public Page<it.anggen.model.entity.EnumEntity> findByPage(Integer pageNumber) {
        org.springframework.data.domain.PageRequest pageRequest = new org.springframework.data.domain.PageRequest(pageNumber - 1, PAGE_SIZE, org.springframework.data.domain.Sort.Direction.DESC, "enumEntityId");
        return enumEntityRepository.findAll(pageRequest);
    }

    @Override
    public List<it.anggen.model.entity.EnumEntity> find(EnumEntitySearchBean enumEntity) {
        return enumEntityRepository.findByEnumEntityIdAndNameAndProjectAndEnumValue(enumEntity.getEnumEntityId(),enumEntity.getName(),enumEntity.getProject(),enumEntity.getEnumValueList()==null? null :enumEntity.getEnumValueList().get(0));
    }

    @Override
    public void deleteById(Long enumEntityId) {
        enumEntityRepository.delete(enumEntityId);
        return;
    }

    @Override
    public it.anggen.model.entity.EnumEntity insert(it.anggen.model.entity.EnumEntity enumEntity) {
        return enumEntityRepository.save(enumEntity);
    }

    @Override
    @Transactional
    public it.anggen.model.entity.EnumEntity update(it.anggen.model.entity.EnumEntity enumEntity) {
        if (enumEntity.getEnumValueList()!=null)
        for (it.anggen.model.field.EnumValue enumValue: enumEntity.getEnumValueList())
        {
        enumValue.setEnumEntity(enumEntity);
        }
        it.anggen.model.entity.EnumEntity returnedEnumEntity=enumEntityRepository.save(enumEntity);
        if (enumEntity.getProject()!=null)
        {
        List<it.anggen.model.entity.EnumEntity> enumEntityList = enumEntityRepository.findByProject( enumEntity.getProject());
        if (!enumEntityList.contains(returnedEnumEntity))
        enumEntityList.add(returnedEnumEntity);
        returnedEnumEntity.getProject().setEnumEntityList(enumEntityList);
        }
         return returnedEnumEntity;
    }

}
