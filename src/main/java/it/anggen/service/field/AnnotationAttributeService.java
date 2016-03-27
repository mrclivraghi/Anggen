
package it.anggen.service.field;

import java.util.List;
import it.anggen.searchbean.field.AnnotationAttributeSearchBean;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

public interface AnnotationAttributeService {


    public List<it.anggen.model.field.AnnotationAttribute> findById(Long AnnotationAttributeId);

    public List<it.anggen.model.field.AnnotationAttribute> find(AnnotationAttributeSearchBean AnnotationAttribute);

    public void deleteById(Long AnnotationAttributeId);

    public it.anggen.model.field.AnnotationAttribute insert(it.anggen.model.field.AnnotationAttribute AnnotationAttribute);

    public it.anggen.model.field.AnnotationAttribute update(it.anggen.model.field.AnnotationAttribute AnnotationAttribute);

    public Page<it.anggen.model.field.AnnotationAttribute> findByPage(
        @PathVariable
        Integer pageNumber);

}
