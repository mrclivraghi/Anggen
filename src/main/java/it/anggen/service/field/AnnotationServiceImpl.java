
package it.anggen.service.field;

import java.util.List;
import it.anggen.repository.field.AnnotationAttributeRepository;
import it.anggen.repository.field.AnnotationRepository;
import it.anggen.searchbean.field.AnnotationSearchBean;
import it.anggen.service.field.AnnotationService;
import org.springframework.data.domain.Page;
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
    private static Integer PAGE_SIZE = (5);

    @Override
    public List<it.anggen.model.field.Annotation> findById(Long annotationId) {
        return annotationRepository.findByAnnotationId(annotationId);
    }

    @Override
    public Page<it.anggen.model.field.Annotation> findByPage(Integer pageNumber) {
        org.springframework.data.domain.PageRequest pageRequest = new org.springframework.data.domain.PageRequest(pageNumber - 1, PAGE_SIZE, org.springframework.data.domain.Sort.Direction.DESC, "annotationId");
        return annotationRepository.findAll(pageRequest);
    }

    @Override
    public List<it.anggen.model.field.Annotation> find(AnnotationSearchBean annotation) {
        return annotationRepository.findByAnnotationIdAndAnnotationTypeAndRelationshipAndFieldAndEnumFieldAndAnnotationAttribute(annotation.getAnnotationId(), (annotation.getAnnotationType()==null)? null : annotation.getAnnotationType().getValue(),annotation.getRelationship(),annotation.getField(),annotation.getEnumField(),annotation.getAnnotationAttributeList()==null? null :annotation.getAnnotationAttributeList().get(0));
    }

    @Override
    public void deleteById(Long annotationId) {
        annotationRepository.delete(annotationId);
        return;
    }

    @Override
    public it.anggen.model.field.Annotation insert(it.anggen.model.field.Annotation annotation) {
        return annotationRepository.save(annotation);
    }

    @Override
    @Transactional
    public it.anggen.model.field.Annotation update(it.anggen.model.field.Annotation annotation) {
        if (annotation.getAnnotationAttributeList()!=null)
        for (it.anggen.model.field.AnnotationAttribute annotationAttribute: annotation.getAnnotationAttributeList())
        {
        annotationAttribute.setAnnotation(annotation);
        }
        it.anggen.model.field.Annotation returnedAnnotation=annotationRepository.save(annotation);
        if (annotation.getRelationship()!=null)
        {
        List<it.anggen.model.field.Annotation> annotationList = annotationRepository.findByRelationship( annotation.getRelationship());
        if (!annotationList.contains(returnedAnnotation))
        annotationList.add(returnedAnnotation);
        returnedAnnotation.getRelationship().setAnnotationList(annotationList);
        }
        if (annotation.getField()!=null)
        {
        List<it.anggen.model.field.Annotation> annotationList = annotationRepository.findByField( annotation.getField());
        if (!annotationList.contains(returnedAnnotation))
        annotationList.add(returnedAnnotation);
        returnedAnnotation.getField().setAnnotationList(annotationList);
        }
        if (annotation.getEnumField()!=null)
        {
        List<it.anggen.model.field.Annotation> annotationList = annotationRepository.findByEnumField( annotation.getEnumField());
        if (!annotationList.contains(returnedAnnotation))
        annotationList.add(returnedAnnotation);
        returnedAnnotation.getEnumField().setAnnotationList(annotationList);
        }
         return returnedAnnotation;
    }

}
