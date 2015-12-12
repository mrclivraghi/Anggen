
package it.anggen.service.field;

import java.util.List;
import it.anggen.searchbean.field.AnnotationSearchBean;

public interface AnnotationService {


    public List<it.anggen.model.field.Annotation> findById(Long AnnotationId);

    public List<it.anggen.model.field.Annotation> find(AnnotationSearchBean Annotation);

    public void deleteById(Long AnnotationId);

    public it.anggen.model.field.Annotation insert(it.anggen.model.field.Annotation Annotation);

    public it.anggen.model.field.Annotation update(it.anggen.model.field.Annotation Annotation);

}
