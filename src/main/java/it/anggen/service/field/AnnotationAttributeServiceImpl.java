
package it.anggen.service.field;

import java.util.List;
import it.anggen.repository.field.AnnotationAttributeRepository;
import it.anggen.searchbean.field.AnnotationAttributeSearchBean;
import it.anggen.service.field.AnnotationAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnnotationAttributeServiceImpl
    implements AnnotationAttributeService
{

    @Autowired
    public AnnotationAttributeRepository annotationAttributeRepository;
    private static Integer PAGE_SIZE = (5);

    @Override
    public List<it.anggen.model.field.AnnotationAttribute> findById(Long annotationAttributeId) {
        return annotationAttributeRepository.findByAnnotationAttributeId(annotationAttributeId);
    }

    @Override
    public Page<it.anggen.model.field.AnnotationAttribute> findByPage(Integer pageNumber) {
        org.springframework.data.domain.PageRequest pageRequest = new org.springframework.data.domain.PageRequest(pageNumber - 1, PAGE_SIZE, org.springframework.data.domain.Sort.Direction.DESC, "annotationAttributeId");
        return annotationAttributeRepository.findAll(pageRequest);
    }

    @Override
    public List<it.anggen.model.field.AnnotationAttribute> find(AnnotationAttributeSearchBean annotationAttribute) {
        return annotationAttributeRepository.findByAnnotationAttributeIdAndValueAndPropertyAndAnnotation(annotationAttribute.getAnnotationAttributeId(),annotationAttribute.getValue(),annotationAttribute.getProperty(),annotationAttribute.getAnnotation());
    }

    @Override
    public void deleteById(Long annotationAttributeId) {
        annotationAttributeRepository.delete(annotationAttributeId);
        return;
    }

    @Override
    public it.anggen.model.field.AnnotationAttribute insert(it.anggen.model.field.AnnotationAttribute annotationAttribute) {
        return annotationAttributeRepository.save(annotationAttribute);
    }

    @Override
    @Transactional
    public it.anggen.model.field.AnnotationAttribute update(it.anggen.model.field.AnnotationAttribute annotationAttribute) {
        it.anggen.model.field.AnnotationAttribute returnedAnnotationAttribute=annotationAttributeRepository.save(annotationAttribute);
        if (annotationAttribute.getAnnotation()!=null)
        {
        List<it.anggen.model.field.AnnotationAttribute> annotationAttributeList = annotationAttributeRepository.findByAnnotation( annotationAttribute.getAnnotation());
        if (!annotationAttributeList.contains(returnedAnnotationAttribute))
        annotationAttributeList.add(returnedAnnotationAttribute);
        returnedAnnotationAttribute.getAnnotation().setAnnotationAttributeList(annotationAttributeList);
        }
         return returnedAnnotationAttribute;
    }

}
