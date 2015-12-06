
package it.polimi.service.field;

import java.util.List;

import it.polimi.searchbean.field.AnnotationSearchBean;

public interface AnnotationService {


    public List<it.polimi.model.field.Annotation> findById(Long AnnotationId);

    public List<it.polimi.model.field.Annotation> find(AnnotationSearchBean Annotation);

    public void deleteById(Long AnnotationId);

    public it.polimi.model.field.Annotation insert(it.polimi.model.field.Annotation Annotation);

    public it.polimi.model.field.Annotation update(it.polimi.model.field.Annotation Annotation);

}
