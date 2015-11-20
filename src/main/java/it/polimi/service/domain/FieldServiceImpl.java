
package it.polimi.service.domain;

import java.util.List;
import it.polimi.model.domain.Field;
import it.polimi.repository.domain.FieldRepository;
import it.polimi.searchbean.domain.FieldSearchBean;
import it.polimi.service.domain.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FieldServiceImpl
    implements FieldService
{

    @Autowired
    public FieldRepository fieldRepository;

    @Override
    public List<Field> findById(Long fieldId) {
        return fieldRepository.findByFieldId(fieldId);
    }

    @Override
    public List<Field> find(FieldSearchBean field) {
        return fieldRepository.findByFieldIdAndNameAndEntityAndFieldTypeAndList(field.getFieldId(),field.getName(),field.getEntity(), (field.getFieldType()==null)? null : field.getFieldType().getValue(),field.getList());
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
        Field returnedField=fieldRepository.save(field);
        if (field.getEntity()!=null)
        {
        List<Field> fieldList = fieldRepository.findByEntity( field.getEntity());
        if (!fieldList.contains(returnedField))
        fieldList.add(returnedField);
        returnedField.getEntity().setFieldList(fieldList);
        }
         return returnedField;
    }

}
