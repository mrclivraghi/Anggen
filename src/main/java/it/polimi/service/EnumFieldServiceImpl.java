
package it.polimi.service;

import java.util.List;

import it.polimi.model.domain.EnumField;
import it.polimi.model.domain.EnumValue;
import it.polimi.repository.EnumFieldRepository;
import it.polimi.repository.EnumValueRepository;
import it.polimi.searchbean.EnumFieldSearchBean;
import it.polimi.service.EnumFieldService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnumFieldServiceImpl
    implements EnumFieldService
{

    @org.springframework.beans.factory.annotation.Autowired
    public EnumFieldRepository enumFieldRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public EnumValueRepository enumValueRepository;

    @Override
    public List<EnumField> findById(Long enumFieldId) {
        return enumFieldRepository.findByEnumFieldId(enumFieldId);
    }

    @Override
    public List<EnumField> find(EnumFieldSearchBean enumField) {
        return enumFieldRepository.findByEnumFieldIdAndNameAndEnumValueAndEntity(enumField.getEnumFieldId(),enumField.getName(),enumField.getEnumValueList()==null? null :enumField.getEnumValueList().get(0),enumField.getEntity());
    }

    @Override
    public void deleteById(Long enumFieldId) {
        enumFieldRepository.delete(enumFieldId);
        return;
    }

    @Override
    public EnumField insert(EnumField enumField) {
        return enumFieldRepository.save(enumField);
    }

    @Override
    @Transactional
    public EnumField update(EnumField enumField) {
        if (enumField.getEnumValueList()!=null)
        for (EnumValue enumValue: enumField.getEnumValueList())
        {
        enumValue.setEnumField(enumField);
        }
        EnumField returnedEnumField=enumFieldRepository.save(enumField);
        if (enumField.getEntity()!=null)
        {
        List<EnumField> enumFieldList = enumFieldRepository.findByEntity( enumField.getEntity());
        if (!enumFieldList.contains(returnedEnumField))
        enumFieldList.add(returnedEnumField);
        returnedEnumField.getEntity().setEnumFieldList(enumFieldList);
        }
         return returnedEnumField;
    }

}
