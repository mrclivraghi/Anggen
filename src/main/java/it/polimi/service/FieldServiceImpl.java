
package it.polimi.service;

import java.util.List;

import it.polimi.model.field.Field;
import it.polimi.repository.FieldRepository;
import it.polimi.repository.RestrictionFieldRepository;
import it.polimi.searchbean.FieldSearchBean;
import it.polimi.service.FieldService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FieldServiceImpl
    implements FieldService
{

    @org.springframework.beans.factory.annotation.Autowired
    public FieldRepository fieldRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RestrictionFieldRepository restrictionFieldRepository;

    @Override
    public List<Field> findById(Long fieldId) {
        return fieldRepository.findByFieldId(fieldId);
    }

    @Override
    public List<Field> find(FieldSearchBean field) {
        return fieldRepository.findByFieldIdAndNameAndEntityAndFieldTypeAndAnnotationAndRestrictionFieldAndTab(field.getFieldId(),field.getName(),field.getEntity(), (field.getFieldType()==null)? null : field.getFieldType().getValue(),field.getAnnotationList()==null? null :field.getAnnotationList().get(0),field.getRestrictionFieldList()==null? null :field.getRestrictionFieldList().get(0),field.getTab());
    }

    @Override
    public void deleteById(Long fieldId) {
        fieldRepository.delete(fieldId);
        return;
    }

    @Override
    public Field insert(Field field) {
        return fieldRepository.save(field);
    }

    @Override
    @Transactional
    public Field update(Field field) {
        if (field.getAnnotationList()!=null)
        for (it.polimi.model.field.Annotation annotation: field.getAnnotationList())
        {
        annotation.setField(field);
        }
        if (field.getRestrictionFieldList()!=null)
        for (it.polimi.model.security.RestrictionField restrictionField: field.getRestrictionFieldList())
        {
        restrictionField.setField(field);
        }
        Field returnedField=fieldRepository.save(field);
        if (field.getEntity()!=null)
        {
        List<Field> fieldList = fieldRepository.findByEntity( field.getEntity());
        if (!fieldList.contains(returnedField))
        fieldList.add(returnedField);
        returnedField.getEntity().setFieldList(fieldList);
        }
        if (field.getTab()!=null)
        {
        List<Field> fieldList = fieldRepository.findByTab( field.getTab());
        if (!fieldList.contains(returnedField))
        fieldList.add(returnedField);
        returnedField.getTab().setFieldList(fieldList);
        }
         return returnedField;
    }

}
