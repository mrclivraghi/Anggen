
package it.anggen.service.field;

import java.util.List;
import it.anggen.repository.field.AnnotationRepository;
import it.anggen.repository.field.EnumFieldRepository;
import it.anggen.repository.field.EnumValueRepository;
import it.anggen.searchbean.field.EnumFieldSearchBean;
import it.anggen.service.field.EnumFieldService;
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
    @org.springframework.beans.factory.annotation.Autowired
    public AnnotationRepository annotationRepository;

    @Override
    public List<it.anggen.model.field.EnumField> findById(Long enumFieldId) {
        return enumFieldRepository.findByEnumFieldId(enumFieldId);
    }

    @Override
    public List<it.anggen.model.field.EnumField> find(EnumFieldSearchBean enumField) {
        return enumFieldRepository.findByEnumFieldIdAndPriorityAndNameAndEnumValueAndEntityAndAnnotationAndTab(enumField.getEnumFieldId(),enumField.getPriority(),enumField.getName(),enumField.getEnumValueList()==null? null :enumField.getEnumValueList().get(0),enumField.getEntity(),enumField.getAnnotationList()==null? null :enumField.getAnnotationList().get(0),enumField.getTab());
    }

    @Override
    public void deleteById(Long enumFieldId) {
        enumFieldRepository.delete(enumFieldId);
        return;
    }

    @Override
    public it.anggen.model.field.EnumField insert(it.anggen.model.field.EnumField enumField) {
        return enumFieldRepository.save(enumField);
    }

    @Override
    @Transactional
    public it.anggen.model.field.EnumField update(it.anggen.model.field.EnumField enumField) {
        if (enumField.getAnnotationList()!=null)
        for (it.anggen.model.field.Annotation annotation: enumField.getAnnotationList())
        {
        annotation.setEnumField(enumField);
        }
        it.anggen.model.field.EnumField returnedEnumField=enumFieldRepository.save(enumField);
        if (enumField.getEntity()!=null)
        {
        List<it.anggen.model.field.EnumField> enumFieldList = enumFieldRepository.findByEntity( enumField.getEntity());
        if (!enumFieldList.contains(returnedEnumField))
        enumFieldList.add(returnedEnumField);
        returnedEnumField.getEntity().setEnumFieldList(enumFieldList);
        }
        if (enumField.getTab()!=null)
        {
        List<it.anggen.model.field.EnumField> enumFieldList = enumFieldRepository.findByTab( enumField.getTab());
        if (!enumFieldList.contains(returnedEnumField))
        enumFieldList.add(returnedEnumField);
        returnedEnumField.getTab().setEnumFieldList(enumFieldList);
        }
         return returnedEnumField;
    }

}
