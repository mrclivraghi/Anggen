
package it.polimi.service.domain;

import java.util.List;
import it.polimi.model.domain.AnnotationAttribute;
import it.polimi.searchbean.domain.AnnotationAttributeSearchBean;

public interface AnnotationAttributeService {


    public List<AnnotationAttribute> findById(Long annotationAttributeId);

    public List<AnnotationAttribute> find(AnnotationAttributeSearchBean annotationAttribute);

    public void deleteById(Long annotationAttributeId);

    public AnnotationAttribute insert(AnnotationAttribute annotationAttribute);

    public AnnotationAttribute update(AnnotationAttribute annotationAttribute);

}
