
package it.anggen.service.field;

import java.util.List;
import it.anggen.repository.field.EnumValueRepository;
import it.anggen.searchbean.field.EnumValueSearchBean;
import it.anggen.service.field.EnumValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnumValueServiceImpl
    implements EnumValueService
{

    @Autowired
    public EnumValueRepository enumValueRepository;

    @Override
    public List<it.anggen.model.field.EnumValue> findById(Long enumValueId) {
        return enumValueRepository.findByEnumValueId(enumValueId);
    }

    @Override
    public List<it.anggen.model.field.EnumValue> find(EnumValueSearchBean enumValue) {
        return enumValueRepository.findByEnumValueIdAndNameAndValueAndEnumEntity(enumValue.getEnumValueId(),enumValue.getName(),enumValue.getValue(),enumValue.getEnumEntity());
    }

    @Override
    public void deleteById(Long enumValueId) {
        enumValueRepository.delete(enumValueId);
        return;
    }

    @Override
    public it.anggen.model.field.EnumValue insert(it.anggen.model.field.EnumValue enumValue) {
        return enumValueRepository.save(enumValue);
    }

    @Override
    @Transactional
    public it.anggen.model.field.EnumValue update(it.anggen.model.field.EnumValue enumValue) {
        it.anggen.model.field.EnumValue returnedEnumValue=enumValueRepository.save(enumValue);
        if (enumValue.getEnumEntity()!=null)
        {
        List<it.anggen.model.field.EnumValue> enumValueList = enumValueRepository.findByEnumEntity( enumValue.getEnumEntity());
        if (!enumValueList.contains(returnedEnumValue))
        enumValueList.add(returnedEnumValue);
        returnedEnumValue.getEnumEntity().setEnumValueList(enumValueList);
        }
         return returnedEnumValue;
    }

}
