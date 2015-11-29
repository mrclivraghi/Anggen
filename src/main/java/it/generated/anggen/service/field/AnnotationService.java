
package it.generated.anggen.service.field;

import java.util.List;
import it.generated.anggen.searchbean.field.AnnotationSearchBean;

public interface AnnotationService {


    public List<it.generated.anggen.model.field.Annotation> findById(Long AnnotationId);

    public List<it.generated.anggen.model.field.Annotation> find(AnnotationSearchBean Annotation);

    public void deleteById(Long AnnotationId);

    public it.generated.anggen.model.field.Annotation insert(it.generated.anggen.model.field.Annotation Annotation);

    public it.generated.anggen.model.field.Annotation update(it.generated.anggen.model.field.Annotation Annotation);

}
