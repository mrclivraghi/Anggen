
package it.polimi.service;

import java.util.List;

import it.polimi.model.field.Annotation;
import it.polimi.searchbean.AnnotationSearchBean;

public interface AnnotationService {


    public List<Annotation> findById(Long annotationId);

    public List<Annotation> find(AnnotationSearchBean annotation);

    public void deleteById(Long annotationId);

    public Annotation insert(Annotation annotation);

    public Annotation update(Annotation annotation);

}
