
package it.polimi.service.field;

import java.util.List;

import it.polimi.repository.field.AnnotationAttributeRepository;
import it.polimi.repository.field.AnnotationRepository;
import it.polimi.searchbean.field.AnnotationSearchBean;
import it.polimi.service.field.AnnotationService;

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
    public List<it.polimi.model.field.Annotation> findById(Long annotationId) {
        return annotationRepository.findByAnnotationId(annotationId);
    }

    @Override
    public List<it.polimi.model.field.Annotation> find(AnnotationSearchBean annotation) {
        return annotationRepository.findByAnnotationIdAndAnnotationAttributeAndFieldAndRelationshipAndEnumFieldAndAnnotationType(annotation.getAnnotationId(),annotation.getAnnotationAttributeList()==null? null :annotation.getAnnotationAttributeList().get(0),annotation.getField(),annotation.getRelationship(),annotation.getEnumField(), (annotation.getAnnotationType()==null)? null : annotation.getAnnotationType().getValue());
    }

    @Override
    public void deleteById(Long annotationId) {
        annotationRepository.delete(annotationId);
        return;
    }

    @Override
    public it.polimi.model.field.Annotation insert(it.polimi.model.field.Annotation annotation) {
        return annotationRepository.save(annotation);
    }

    @Override
    @Transactional
    public it.polimi.model.field.Annotation update(it.polimi.model.field.Annotation annotation) {
        if (annotation.getAnnotationAttributeList()!=null)
        for (it.polimi.model.field.AnnotationAttribute annotationAttribute: annotation.getAnnotationAttributeList())
        {
        annotationAttribute.setAnnotation(annotation);
        }
        it.polimi.model.field.Annotation returnedAnnotation=annotationRepository.save(annotation);
        if (annotation.getField()!=null)
        {
        List<it.polimi.model.field.Annotation> annotationList = annotationRepository.findByField( annotation.getField());
        if (!annotationList.contains(returnedAnnotation))
        annotationList.add(returnedAnnotation);
        returnedAnnotation.getField().setAnnotationList(annotationList);
        }
        if (annotation.getRelationship()!=null)
        {
        List<it.polimi.model.field.Annotation> annotationList = annotationRepository.findByRelationship( annotation.getRelationship());
        if (!annotationList.contains(returnedAnnotation))
        annotationList.add(returnedAnnotation);
        returnedAnnotation.getRelationship().setAnnotationList(annotationList);
        }
        if (annotation.getEnumField()!=null)
        {
        List<it.polimi.model.field.Annotation> annotationList = annotationRepository.findByEnumField( annotation.getEnumField());
        if (!annotationList.contains(returnedAnnotation))
        annotationList.add(returnedAnnotation);
        returnedAnnotation.getEnumField().setAnnotationList(annotationList);
        }
         return returnedAnnotation;
    }

}
