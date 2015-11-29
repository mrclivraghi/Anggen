
package it.generated.anggen.service.field;

import java.util.List;
import it.generated.anggen.repository.field.AnnotationRepository;
import it.generated.anggen.repository.field.FieldRepository;
import it.generated.anggen.repository.security.RestrictionFieldRepository;
import it.generated.anggen.searchbean.field.FieldSearchBean;
import it.generated.anggen.service.field.FieldService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FieldServiceImpl
    implements FieldService
{

    @org.springframework.beans.factory.annotation.Autowired
    public FieldRepository fieldRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public AnnotationRepository annotationRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RestrictionFieldRepository restrictionFieldRepository;

    @Override
    public List<it.generated.anggen.model.field.Field> findById(Long fieldId) {
        return fieldRepository.findByFieldId(fieldId);
    }

    @Override
    public List<it.generated.anggen.model.field.Field> find(FieldSearchBean field) {
        return fieldRepository.findByFieldIdAndNameAndEntityAndAnnotationAndRestrictionFieldAndTabAndFieldType(field.getFieldId(),field.getName(),field.getEntity(),field.getAnnotationList()==null? null :field.getAnnotationList().get(0),field.getRestrictionFieldList()==null? null :field.getRestrictionFieldList().get(0),field.getTab(), (field.getFieldType()==null)? null : field.getFieldType().getValue());
    }

    @Override
    public void deleteById(Long fieldId) {
        fieldRepository.delete(fieldId);
        return;
    }

    @Override
    public it.generated.anggen.model.field.Field insert(it.generated.anggen.model.field.Field field) {
        return fieldRepository.save(field);
    }

    @Override
    @Transactional
    public it.generated.anggen.model.field.Field update(it.generated.anggen.model.field.Field field) {
        if (field.getAnnotationList()!=null)
        for (it.generated.anggen.model.field.Annotation annotation: field.getAnnotationList())
        {
        annotation.setField(field);
        }
        if (field.getRestrictionFieldList()!=null)
        for (it.generated.anggen.model.security.RestrictionField restrictionField: field.getRestrictionFieldList())
        {
        restrictionField.setField(field);
        }
        it.generated.anggen.model.field.Field returnedField=fieldRepository.save(field);
        if (field.getEntity()!=null)
        {
        List<it.generated.anggen.model.field.Field> fieldList = fieldRepository.findByEntity( field.getEntity());
        if (!fieldList.contains(returnedField))
        fieldList.add(returnedField);
        returnedField.getEntity().setFieldList(fieldList);
        }
        if (field.getTab()!=null)
        {
        List<it.generated.anggen.model.field.Field> fieldList = fieldRepository.findByTab( field.getTab());
        if (!fieldList.contains(returnedField))
        fieldList.add(returnedField);
        returnedField.getTab().setFieldList(fieldList);
        }
         return returnedField;
    }

}
