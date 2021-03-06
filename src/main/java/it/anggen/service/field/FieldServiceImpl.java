
package it.anggen.service.field;

import java.util.List;
import it.anggen.repository.field.AnnotationRepository;
import it.anggen.repository.field.FieldRepository;
import it.anggen.repository.security.RestrictionFieldRepository;
import it.anggen.searchbean.field.FieldSearchBean;
import it.anggen.service.field.FieldService;
import org.springframework.data.domain.Page;
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
    @org.springframework.beans.factory.annotation.Autowired
    public AnnotationRepository annotationRepository;
    private static Integer PAGE_SIZE = (5);

    @Override
    public List<it.anggen.model.field.Field> findById(Long fieldId) {
        return fieldRepository.findByFieldId(fieldId);
    }

    @Override
    public Page<it.anggen.model.field.Field> findByPage(Integer pageNumber) {
        org.springframework.data.domain.PageRequest pageRequest = new org.springframework.data.domain.PageRequest(pageNumber - 1, PAGE_SIZE, org.springframework.data.domain.Sort.Direction.DESC, "fieldId");
        return fieldRepository.findAll(pageRequest);
    }

    @Override
    public List<it.anggen.model.field.Field> find(FieldSearchBean field) {
        return fieldRepository.findByFieldIdAndPriorityAndNameAndFieldTypeAndRestrictionFieldAndEntityAndTabAndAnnotation(field.getFieldId(),field.getPriority(),field.getName(), (field.getFieldType()==null)? null : field.getFieldType().getValue(),field.getRestrictionFieldList()==null? null :field.getRestrictionFieldList().get(0),field.getEntity(),field.getTab(),field.getAnnotationList()==null? null :field.getAnnotationList().get(0));
    }

    @Override
    public void deleteById(Long fieldId) {
        fieldRepository.delete(fieldId);
        return;
    }

    @Override
    public it.anggen.model.field.Field insert(it.anggen.model.field.Field field) {
        return fieldRepository.save(field);
    }

    @Override
    @Transactional
    public it.anggen.model.field.Field update(it.anggen.model.field.Field field) {
        if (field.getRestrictionFieldList()!=null)
        for (it.anggen.model.security.RestrictionField restrictionField: field.getRestrictionFieldList())
        {
        restrictionField.setField(field);
        }
        if (field.getAnnotationList()!=null)
        for (it.anggen.model.field.Annotation annotation: field.getAnnotationList())
        {
        annotation.setField(field);
        }
        it.anggen.model.field.Field returnedField=fieldRepository.save(field);
        if (field.getEntity()!=null)
        {
        List<it.anggen.model.field.Field> fieldList = fieldRepository.findByEntity( field.getEntity());
        if (!fieldList.contains(returnedField))
        fieldList.add(returnedField);
        returnedField.getEntity().setFieldList(fieldList);
        }
        if (field.getTab()!=null)
        {
        List<it.anggen.model.field.Field> fieldList = fieldRepository.findByTab( field.getTab());
        if (!fieldList.contains(returnedField))
        fieldList.add(returnedField);
        returnedField.getTab().setFieldList(fieldList);
        }
         return returnedField;
    }

}
