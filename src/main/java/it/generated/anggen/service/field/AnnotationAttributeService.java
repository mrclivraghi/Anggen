
package it.generated.anggen.service.field;

import java.util.List;
import it.generated.anggen.searchbean.field.AnnotationAttributeSearchBean;

public interface AnnotationAttributeService {


    public List<it.generated.anggen.model.field.AnnotationAttribute> findById(Long AnnotationAttributeId);

    public List<it.generated.anggen.model.field.AnnotationAttribute> find(AnnotationAttributeSearchBean AnnotationAttribute);

    public void deleteById(Long AnnotationAttributeId);

    public it.generated.anggen.model.field.AnnotationAttribute insert(it.generated.anggen.model.field.AnnotationAttribute AnnotationAttribute);

    public it.generated.anggen.model.field.AnnotationAttribute update(it.generated.anggen.model.field.AnnotationAttribute AnnotationAttribute);

}
