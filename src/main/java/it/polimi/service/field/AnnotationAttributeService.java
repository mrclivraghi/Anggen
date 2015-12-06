
package it.polimi.service.field;

import java.util.List;

import it.polimi.searchbean.field.AnnotationAttributeSearchBean;

public interface AnnotationAttributeService {


    public List<it.polimi.model.field.AnnotationAttribute> findById(Long AnnotationAttributeId);

    public List<it.polimi.model.field.AnnotationAttribute> find(AnnotationAttributeSearchBean AnnotationAttribute);

    public void deleteById(Long AnnotationAttributeId);

    public it.polimi.model.field.AnnotationAttribute insert(it.polimi.model.field.AnnotationAttribute AnnotationAttribute);

    public it.polimi.model.field.AnnotationAttribute update(it.polimi.model.field.AnnotationAttribute AnnotationAttribute);

}
