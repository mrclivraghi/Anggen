
package it.polimi.service;

import java.util.List;

import it.polimi.model.field.EnumValue;
import it.polimi.repository.EnumValueRepository;
import it.polimi.searchbean.EnumValueSearchBean;
import it.polimi.service.EnumValueService;

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
    public List<EnumValue> findById(Long enumValueId) {
        return enumValueRepository.findByEnumValueId(enumValueId);
    }

    @Override
    public List<EnumValue> find(EnumValueSearchBean enumValue) {
        return enumValueRepository.findByEnumValueIdAndValueAndNameAndEnumField(enumValue.getEnumValueId(),enumValue.getValue(),enumValue.getName(),enumValue.getEnumField());
    }

    @Override
    public void deleteById(Long enumValueId) {
        enumValueRepository.delete(enumValueId);
        return;
    }

    @Override
    public EnumValue insert(EnumValue enumValue) {
        return enumValueRepository.save(enumValue);
    }

    @Override
    @Transactional
    public EnumValue update(EnumValue enumValue) {
        EnumValue returnedEnumValue=enumValueRepository.save(enumValue);
        if (enumValue.getEnumField()!=null)
        {
        List<EnumValue> enumValueList = enumValueRepository.findByEnumField( enumValue.getEnumField());
        if (!enumValueList.contains(returnedEnumValue))
        enumValueList.add(returnedEnumValue);
        returnedEnumValue.getEnumField().setEnumValueList(enumValueList);
        }
         return returnedEnumValue;
    }

}
