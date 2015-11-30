
package it.generated.anggen.service.field;

import java.util.List;
import it.generated.anggen.repository.field.AnnotationAttributeRepository;
import it.generated.anggen.repository.field.AnnotationRepository;
import it.generated.anggen.searchbean.field.AnnotationSearchBean;
import it.generated.anggen.service.field.AnnotationService;
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
    public List<it.generated.anggen.model.field.Annotation> findById(Long annotationId) {
        return annotationRepository.findByAnnotationId(annotationId);
    }

    @Override
    public List<it.generated.anggen.model.field.Annotation> find(AnnotationSearchBean annotation) {
        return annotationRepository.findByAnnotationIdAndEnumFieldAndRelationshipAndFieldAndAnnotationAttributeAndAnnotationType(annotation.getAnnotationId(),annotation.getEnumField(),annotation.getRelationship(),annotation.getField(),annotation.getAnnotationAttributeList()==null? null :annotation.getAnnotationAttributeList().get(0), (annotation.getAnnotationType()==null)? null : annotation.getAnnotationType().getValue());
    }

    @Override
    public void deleteById(Long annotationId) {
        annotationRepository.delete(annotationId);
        return;
    }

    @Override
    public it.generated.anggen.model.field.Annotation insert(it.generated.anggen.model.field.Annotation annotation) {
        return annotationRepository.save(annotation);
    }

    @Override
    @Transactional
    public it.generated.anggen.model.field.Annotation update(it.generated.anggen.model.field.Annotation annotation) {
        if (annotation.getAnnotationAttributeList()!=null)
        for (it.generated.anggen.model.field.AnnotationAttribute annotationAttribute: annotation.getAnnotationAttributeList())
        {
        annotationAttribute.setAnnotation(annotation);
        }
        it.generated.anggen.model.field.Annotation returnedAnnotation=annotationRepository.save(annotation);
        if (annotation.getEnumField()!=null)
        {
        List<it.generated.anggen.model.field.Annotation> annotationList = annotationRepository.findByEnumField( annotation.getEnumField());
        if (!annotationList.contains(returnedAnnotation))
        annotationList.add(returnedAnnotation);
        returnedAnnotation.getEnumField().setAnnotationList(annotationList);
        }
        if (annotation.getRelationship()!=null)
        {
        List<it.generated.anggen.model.field.Annotation> annotationList = annotationRepository.findByRelationship( annotation.getRelationship());
        if (!annotationList.contains(returnedAnnotation))
        annotationList.add(returnedAnnotation);
        returnedAnnotation.getRelationship().setAnnotationList(annotationList);
        }
        if (annotation.getField()!=null)
        {
        List<it.generated.anggen.model.field.Annotation> annotationList = annotationRepository.findByField( annotation.getField());
        if (!annotationList.contains(returnedAnnotation))
        annotationList.add(returnedAnnotation);
        returnedAnnotation.getField().setAnnotationList(annotationList);
        }
         return returnedAnnotation;
    }

}
