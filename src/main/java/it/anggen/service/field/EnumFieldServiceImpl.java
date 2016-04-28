
package it.anggen.service.field;

import java.util.List;
import it.anggen.repository.field.AnnotationRepository;
import it.anggen.repository.field.EnumFieldRepository;
import it.anggen.searchbean.field.EnumFieldSearchBean;
import it.anggen.service.field.EnumFieldService;
import org.springframework.data.domain.Page;
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
    private static Integer PAGE_SIZE = (5);

    @Override
    public List<it.anggen.model.field.EnumField> findById(Long enumFieldId) {
        return enumFieldRepository.findByEnumFieldId(enumFieldId);
    }

    @Override
    public Page<it.anggen.model.field.EnumField> findByPage(Integer pageNumber) {
        org.springframework.data.domain.PageRequest pageRequest = new org.springframework.data.domain.PageRequest(pageNumber - 1, PAGE_SIZE, org.springframework.data.domain.Sort.Direction.DESC, "enumFieldId");
        return enumFieldRepository.findAll(pageRequest);
    }

    @Override
    public List<it.anggen.model.field.EnumField> find(EnumFieldSearchBean enumField) {
        return enumFieldRepository.findByEnumFieldIdAndPriorityAndNameAndEntityAndEnumEntityAndTabAndAnnotation(enumField.getEnumFieldId(),enumField.getPriority(),enumField.getName(),enumField.getEntity(),enumField.getEnumEntity(),enumField.getTab(),enumField.getAnnotationList()==null? null :enumField.getAnnotationList().get(0));
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
        if (enumField.getEnumEntity()!=null)
        {
        List<it.anggen.model.field.EnumField> enumFieldList = enumFieldRepository.findByEnumEntity( enumField.getEnumEntity());
        if (!enumFieldList.contains(returnedEnumField))
        enumFieldList.add(returnedEnumField);
        returnedEnumField.getEnumEntity().setEnumFieldList(enumFieldList);
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
