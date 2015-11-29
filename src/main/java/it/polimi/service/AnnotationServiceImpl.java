
package it.polimi.service;

import java.util.List;

import it.polimi.model.field.Annotation;
import it.polimi.repository.AnnotationAttributeRepository;
import it.polimi.repository.AnnotationRepository;
import it.polimi.searchbean.AnnotationSearchBean;
import it.polimi.service.AnnotationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnnotationServiceImpl
    implements AnnotationService
{

    @org.springframework.beans.factory.annotation.Autowired
    public AnnotationRepository annotationRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public AnnotationAttributeRepository annotationAttributeRepository;

    @Override
    public List<Annotation> findById(Long annotationId) {
        return annotationRepository.findByAnnotationId(annotationId);
    }

    @Override
    public List<Annotation> find(AnnotationSearchBean annotation) {
        return annotationRepository.findByAnnotationIdAndAnnotationTypeAndAnnotationAttributeAndFieldAndRelationshipAndEnumField(annotation.getAnnotationId(), (annotation.getAnnotationType()==null)? null : annotation.getAnnotationType().getValue(),annotation.getAnnotationAttributeList()==null? null :annotation.getAnnotationAttributeList().get(0),annotation.getField(),annotation.getRelationship(),annotation.getEnumField());
    }

    @Override
    public void deleteById(Long annotationId) {
        annotationRepository.delete(annotationId);
        return;
    }

    @Override
    public Annotation insert(Annotation annotation) {
        return annotationRepository.save(annotation);
    }

    @Override
    @Transactional
    public Annotation update(Annotation annotation) {
        if (annotation.getAnnotationAttributeList()!=null)
        for (it.polimi.model.field.AnnotationAttribute annotationAttribute: annotation.getAnnotationAttributeList())
        {
        annotationAttribute.setAnnotation(annotation);
        }
        Annotation returnedAnnotation=annotationRepository.save(annotation);
        if (annotation.getField()!=null)
        {
        List<Annotation> annotationList = annotationRepository.findByField( annotation.getField());
        if (!annotationList.contains(returnedAnnotation))
        annotationList.add(returnedAnnotation);
        returnedAnnotation.getField().setAnnotationList(annotationList);
        }
        if (annotation.getRelationship()!=null)
        {
        List<Annotation> annotationList = annotationRepository.findByRelationship( annotation.getRelationship());
        if (!annotationList.contains(returnedAnnotation))
        annotationList.add(returnedAnnotation);
        returnedAnnotation.getRelationship().setAnnotationList(annotationList);
        }
        if (annotation.getEnumField()!=null)
        {
        List<Annotation> annotationList = annotationRepository.findByEnumField( annotation.getEnumField());
        if (!annotationList.contains(returnedAnnotation))
        annotationList.add(returnedAnnotation);
        returnedAnnotation.getEnumField().setAnnotationList(annotationList);
        }
         return returnedAnnotation;
    }

}
