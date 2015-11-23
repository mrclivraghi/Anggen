
package it.polimi.service.domain;

import java.util.List;
import it.polimi.model.domain.EnumField;
import it.polimi.repository.domain.EnumFieldRepository;
import it.polimi.repository.domain.EnumValueRepository;
import it.polimi.searchbean.domain.EnumFieldSearchBean;
import it.polimi.service.domain.EnumFieldService;
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
        return enumFieldRepository.findByEnumFieldIdAndNameAndEnumValueAndEntityAndAnnotationAndTab(enumField.getEnumFieldId(),enumField.getName(),enumField.getEnumValueList()==null? null :enumField.getEnumValueList().get(0),enumField.getEntity(),enumField.getAnnotationList()==null? null :enumField.getAnnotationList().get(0),enumField.getTab());
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
        for (it.polimi.model.domain.EnumValue enumValue: enumField.getEnumValueList())
        {
        enumValue.setEnumField(enumField);
        }
        if (enumField.getAnnotationList()!=null)
        for (it.polimi.model.domain.Annotation annotation: enumField.getAnnotationList())
        {
        annotation.setEnumField(enumField);
        }
        EnumField returnedEnumField=enumFieldRepository.save(enumField);
        if (enumField.getEntity()!=null)
        {
        List<EnumField> enumFieldList = enumFieldRepository.findByEntity( enumField.getEntity());
        if (!enumFieldList.contains(returnedEnumField))
        enumFieldList.add(returnedEnumField);
        returnedEnumField.getEntity().setEnumFieldList(enumFieldList);
        }
        if (enumField.getTab()!=null)
        {
        List<EnumField> enumFieldList = enumFieldRepository.findByTab( enumField.getTab());
        if (!enumFieldList.contains(returnedEnumField))
        enumFieldList.add(returnedEnumField);
        returnedEnumField.getTab().setEnumFieldList(enumFieldList);
        }
         return returnedEnumField;
    }

}
