
package it.polimi.service.field;

import java.util.List;

import it.polimi.repository.field.EnumValueRepository;
import it.polimi.searchbean.field.EnumValueSearchBean;
import it.polimi.service.field.EnumValueService;

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
    public List<it.polimi.model.field.EnumValue> findById(Long enumValueId) {
        return enumValueRepository.findByEnumValueId(enumValueId);
    }

    @Override
    public List<it.polimi.model.field.EnumValue> find(EnumValueSearchBean enumValue) {
        return enumValueRepository.findByEnumValueIdAndValueAndNameAndEnumField(enumValue.getEnumValueId(),enumValue.getValue(),enumValue.getName(),enumValue.getEnumField());
    }

    @Override
    public void deleteById(Long enumValueId) {
        enumValueRepository.delete(enumValueId);
        return;
    }

    @Override
    public it.polimi.model.field.EnumValue insert(it.polimi.model.field.EnumValue enumValue) {
        return enumValueRepository.save(enumValue);
    }

    @Override
    @Transactional
    public it.polimi.model.field.EnumValue update(it.polimi.model.field.EnumValue enumValue) {
        it.polimi.model.field.EnumValue returnedEnumValue=enumValueRepository.save(enumValue);
        if (enumValue.getEnumField()!=null)
        {
        List<it.polimi.model.field.EnumValue> enumValueList = enumValueRepository.findByEnumField( enumValue.getEnumField());
        if (!enumValueList.contains(returnedEnumValue))
        enumValueList.add(returnedEnumValue);
        returnedEnumValue.getEnumField().setEnumValueList(enumValueList);
        }
         return returnedEnumValue;
    }

}
