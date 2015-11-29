
package it.polimi.searchbean;

import it.polimi.model.field.Annotation;

public class AnnotationAttributeSearchBean {

    public Long annotationAttributeId;
    public String property;
    public String value;
    public Annotation annotation;

    public Long getAnnotationAttributeId() {
        return this.annotationAttributeId;
    }

    public void setAnnotationAttributeId(Long annotationAttributeId) {
        this.annotationAttributeId=annotationAttributeId;
    }

    public String getProperty() {
        return this.property;
    }

    public void setProperty(String property) {
        this.property=property;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value=value;
    }

    public Annotation getAnnotation() {
        return this.annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation=annotation;
    }

}
