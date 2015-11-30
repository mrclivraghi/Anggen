
package it.generated.anggen.service.field;

import java.util.List;
import it.generated.anggen.repository.field.AnnotationRepository;
import it.generated.anggen.repository.field.EnumFieldRepository;
import it.generated.anggen.repository.field.EnumValueRepository;
import it.generated.anggen.searchbean.field.EnumFieldSearchBean;
import it.generated.anggen.service.field.EnumFieldService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnumFieldServiceImpl
    implements EnumFieldService
{

    @org.springframework.beans.factory.annotation.Autowired
    public EnumFieldRepository enumFieldRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public AnnotationRepository annotationRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public EnumValueRepository enumValueRepository;

    @Override
    public List<it.generated.anggen.model.field.EnumField> findById(Long enumFieldId) {
        return enumFieldRepository.findByEnumFieldId(enumFieldId);
    }

    @Override
    public List<it.generated.anggen.model.field.EnumField> find(EnumFieldSearchBean enumField) {
        return enumFieldRepository.findByNameAndEnumFieldIdAndTabAndAnnotationAndEntityAndEnumValue(enumField.getName(),enumField.getEnumFieldId(),enumField.getTab(),enumField.getAnnotationList()==null? null :enumField.getAnnotationList().get(0),enumField.getEntity(),enumField.getEnumValueList()==null? null :enumField.getEnumValueList().get(0));
    }

    @Override
    public void deleteById(Long enumFieldId) {
        enumFieldRepository.delete(enumFieldId);
        return;
    }

    @Override
    public it.generated.anggen.model.field.EnumField insert(it.generated.anggen.model.field.EnumField enumField) {
        return enumFieldRepository.save(enumField);
    }

    @Override
    @Transactional
    public it.generated.anggen.model.field.EnumField update(it.generated.anggen.model.field.EnumField enumField) {
        if (enumField.getAnnotationList()!=null)
        for (it.generated.anggen.model.field.Annotation annotation: enumField.getAnnotationList())
        {
        annotation.setEnumField(enumField);
        }
        if (enumField.getEnumValueList()!=null)
        for (it.generated.anggen.model.field.EnumValue enumValue: enumField.getEnumValueList())
        {
        enumValue.setEnumField(enumField);
        }
        it.generated.anggen.model.field.EnumField returnedEnumField=enumFieldRepository.save(enumField);
        if (enumField.getTab()!=null)
        {
        List<it.generated.anggen.model.field.EnumField> enumFieldList = enumFieldRepository.findByTab( enumField.getTab());
        if (!enumFieldList.contains(returnedEnumField))
        enumFieldList.add(returnedEnumField);
        returnedEnumField.getTab().setEnumFieldList(enumFieldList);
        }
        if (enumField.getEntity()!=null)
        {
        List<it.generated.anggen.model.field.EnumField> enumFieldList = enumFieldRepository.findByEntity( enumField.getEntity());
        if (!enumFieldList.contains(returnedEnumField))
        enumFieldList.add(returnedEnumField);
        returnedEnumField.getEntity().setEnumFieldList(enumFieldList);
        }
         return returnedEnumField;
    }

}
