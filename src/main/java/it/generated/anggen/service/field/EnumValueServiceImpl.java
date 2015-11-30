
package it.generated.anggen.service.field;

import java.util.List;
import it.generated.anggen.repository.field.EnumValueRepository;
import it.generated.anggen.searchbean.field.EnumValueSearchBean;
import it.generated.anggen.service.field.EnumValueService;
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
    public List<it.generated.anggen.model.field.EnumValue> findById(Long enumValueId) {
        return enumValueRepository.findByEnumValueId(enumValueId);
    }

    @Override
    public List<it.generated.anggen.model.field.EnumValue> find(EnumValueSearchBean enumValue) {
        return enumValueRepository.findByNameAndValueAndEnumValueIdAndEnumField(enumValue.getName(),enumValue.getValue(),enumValue.getEnumValueId(),enumValue.getEnumField());
    }

    @Override
    public void deleteById(Long enumValueId) {
        enumValueRepository.delete(enumValueId);
        return;
    }

    @Override
    public it.generated.anggen.model.field.EnumValue insert(it.generated.anggen.model.field.EnumValue enumValue) {
        return enumValueRepository.save(enumValue);
    }

    @Override
    @Transactional
    public it.generated.anggen.model.field.EnumValue update(it.generated.anggen.model.field.EnumValue enumValue) {
        it.generated.anggen.model.field.EnumValue returnedEnumValue=enumValueRepository.save(enumValue);
        if (enumValue.getEnumField()!=null)
        {
        List<it.generated.anggen.model.field.EnumValue> enumValueList = enumValueRepository.findByEnumField( enumValue.getEnumField());
        if (!enumValueList.contains(returnedEnumValue))
        enumValueList.add(returnedEnumValue);
        returnedEnumValue.getEnumField().setEnumValueList(enumValueList);
        }
         return returnedEnumValue;
    }

}
