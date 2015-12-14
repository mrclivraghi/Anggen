
package it.anggen.model.field;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import it.anggen.utils.annotation.MaxDescendantLevel;

@Entity
@Table(schema = "meta", name = "annotation_attribute")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
public class AnnotationAttribute {

    public final static java.lang.Long staticEntityId = 11L;
    @javax.persistence.Column(name = "annotation_attribute_id")
    @Id
    @GeneratedValue
    @it.anggen.utils.annotation.DescriptionField
    @it.anggen.utils.annotation.Priority(1)
    private java.lang.Long annotationAttributeId;
    @javax.persistence.Column(name = "property")
    @it.anggen.utils.annotation.DescriptionField
    @it.anggen.utils.annotation.Priority(2)
    private java.lang.String property;
    @javax.persistence.Column(name = "value")
    @it.anggen.utils.annotation.Priority(2)
    private java.lang.String value;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "annotation_id_annotation")
    @it.anggen.utils.annotation.Priority(4)
    private it.anggen.model.field.Annotation annotation;

    public java.lang.Long getAnnotationAttributeId() {
        return this.annotationAttributeId;
    }

    public void setAnnotationAttributeId(java.lang.Long annotationAttributeId) {
        this.annotationAttributeId=annotationAttributeId;
    }

    public java.lang.String getProperty() {
        return this.property;
    }

    public void setProperty(java.lang.String property) {
        this.property=property;
    }

    public java.lang.String getValue() {
        return this.value;
    }

    public void setValue(java.lang.String value) {
        this.value=value;
    }

    public it.anggen.model.field.Annotation getAnnotation() {
        return this.annotation;
    }

    public void setAnnotation(it.anggen.model.field.Annotation annotation) {
        this.annotation=annotation;
    }

}
