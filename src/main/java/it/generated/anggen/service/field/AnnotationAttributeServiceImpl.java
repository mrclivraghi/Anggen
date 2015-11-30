
package it.generated.anggen.service.field;

import java.util.List;
import it.generated.anggen.repository.field.AnnotationAttributeRepository;
import it.generated.anggen.searchbean.field.AnnotationAttributeSearchBean;
import it.generated.anggen.service.field.AnnotationAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnnotationAttributeServiceImpl
    implements AnnotationAttributeService
{

    @Autowired
    public AnnotationAttributeRepository annotationAttributeRepository;

    @Override
    public List<it.generated.anggen.model.field.AnnotationAttribute> findById(Long annotationAttributeId) {
        return annotationAttributeRepository.findByAnnotationAttributeId(annotationAttributeId);
    }

    @Override
    public List<it.generated.anggen.model.field.AnnotationAttribute> find(AnnotationAttributeSearchBean annotationAttribute) {
        return annotationAttributeRepository.findByValueAndPropertyAndAnnotationAttributeIdAndAnnotation(annotationAttribute.getValue(),annotationAttribute.getProperty(),annotationAttribute.getAnnotationAttributeId(),annotationAttribute.getAnnotation());
    }

    @Override
    public void deleteById(Long annotationAttributeId) {
        annotationAttributeRepository.delete(annotationAttributeId);
        return;
    }

    @Override
    public it.generated.anggen.model.field.AnnotationAttribute insert(it.generated.anggen.model.field.AnnotationAttribute annotationAttribute) {
        return annotationAttributeRepository.save(annotationAttribute);
    }

    @Override
    @Transactional
    public it.generated.anggen.model.field.AnnotationAttribute update(it.generated.anggen.model.field.AnnotationAttribute annotationAttribute) {
        it.generated.anggen.model.field.AnnotationAttribute returnedAnnotationAttribute=annotationAttributeRepository.save(annotationAttribute);
        if (annotationAttribute.getAnnotation()!=null)
        {
        List<it.generated.anggen.model.field.AnnotationAttribute> annotationAttributeList = annotationAttributeRepository.findByAnnotation( annotationAttribute.getAnnotation());
        if (!annotationAttributeList.contains(returnedAnnotationAttribute))
        annotationAttributeList.add(returnedAnnotationAttribute);
        returnedAnnotationAttribute.getAnnotation().setAnnotationAttributeList(annotationAttributeList);
        }
         return returnedAnnotationAttribute;
    }

}
