
package it.polimi.service.field;

import java.util.List;

import it.polimi.repository.field.AnnotationAttributeRepository;
import it.polimi.searchbean.field.AnnotationAttributeSearchBean;
import it.polimi.service.field.AnnotationAttributeService;

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
    public List<it.polimi.model.field.AnnotationAttribute> findById(Long annotationAttributeId) {
        return annotationAttributeRepository.findByAnnotationAttributeId(annotationAttributeId);
    }

    @Override
    public List<it.polimi.model.field.AnnotationAttribute> find(AnnotationAttributeSearchBean annotationAttribute) {
        return annotationAttributeRepository.findByAnnotationAttributeIdAndPropertyAndValueAndAnnotation(annotationAttribute.getAnnotationAttributeId(),annotationAttribute.getProperty(),annotationAttribute.getValue(),annotationAttribute.getAnnotation());
    }

    @Override
    public void deleteById(Long annotationAttributeId) {
        annotationAttributeRepository.delete(annotationAttributeId);
        return;
    }

    @Override
    public it.polimi.model.field.AnnotationAttribute insert(it.polimi.model.field.AnnotationAttribute annotationAttribute) {
        return annotationAttributeRepository.save(annotationAttribute);
    }

    @Override
    @Transactional
    public it.polimi.model.field.AnnotationAttribute update(it.polimi.model.field.AnnotationAttribute annotationAttribute) {
        it.polimi.model.field.AnnotationAttribute returnedAnnotationAttribute=annotationAttributeRepository.save(annotationAttribute);
        if (annotationAttribute.getAnnotation()!=null)
        {
        List<it.polimi.model.field.AnnotationAttribute> annotationAttributeList = annotationAttributeRepository.findByAnnotation( annotationAttribute.getAnnotation());
        if (!annotationAttributeList.contains(returnedAnnotationAttribute))
        annotationAttributeList.add(returnedAnnotationAttribute);
        returnedAnnotationAttribute.getAnnotation().setAnnotationAttributeList(annotationAttributeList);
        }
         return returnedAnnotationAttribute;
    }

}
