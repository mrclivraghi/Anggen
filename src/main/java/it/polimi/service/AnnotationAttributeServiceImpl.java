
package it.polimi.service;

import java.util.List;

import it.polimi.model.field.AnnotationAttribute;
import it.polimi.repository.AnnotationAttributeRepository;
import it.polimi.searchbean.AnnotationAttributeSearchBean;
import it.polimi.service.AnnotationAttributeService;

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
    public List<AnnotationAttribute> findById(Long annotationAttributeId) {
        return annotationAttributeRepository.findByAnnotationAttributeId(annotationAttributeId);
    }

    @Override
    public List<AnnotationAttribute> find(AnnotationAttributeSearchBean annotationAttribute) {
        return annotationAttributeRepository.findByAnnotationAttributeIdAndPropertyAndValueAndAnnotation(annotationAttribute.getAnnotationAttributeId(),annotationAttribute.getProperty(),annotationAttribute.getValue(),annotationAttribute.getAnnotation());
    }

    @Override
    public void deleteById(Long annotationAttributeId) {
        annotationAttributeRepository.delete(annotationAttributeId);
        return;
    }

    @Override
    public AnnotationAttribute insert(AnnotationAttribute annotationAttribute) {
        return annotationAttributeRepository.save(annotationAttribute);
    }

    @Override
    @Transactional
    public AnnotationAttribute update(AnnotationAttribute annotationAttribute) {
        AnnotationAttribute returnedAnnotationAttribute=annotationAttributeRepository.save(annotationAttribute);
        if (annotationAttribute.getAnnotation()!=null)
        {
        List<AnnotationAttribute> annotationAttributeList = annotationAttributeRepository.findByAnnotation( annotationAttribute.getAnnotation());
        if (!annotationAttributeList.contains(returnedAnnotationAttribute))
        annotationAttributeList.add(returnedAnnotationAttribute);
        returnedAnnotationAttribute.getAnnotation().setAnnotationAttributeList(annotationAttributeList);
        }
         return returnedAnnotationAttribute;
    }

}
