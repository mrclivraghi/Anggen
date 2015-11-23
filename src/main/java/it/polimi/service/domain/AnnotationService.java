
package it.polimi.service.domain;

import java.util.List;
import it.polimi.model.domain.Annotation;
import it.polimi.searchbean.domain.AnnotationSearchBean;

public interface AnnotationService {


    public List<Annotation> findById(Long annotationId);

    public List<Annotation> find(AnnotationSearchBean annotation);

    public void deleteById(Long annotationId);

    public Annotation insert(Annotation annotation);

    public Annotation update(Annotation annotation);

}
