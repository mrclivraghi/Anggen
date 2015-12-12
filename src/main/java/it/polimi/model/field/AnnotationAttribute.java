
package it.polimi.model.field;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.polimi.utils.annotation.DescriptionField;

@Entity
@Table(schema = "meta", name = "annotation_attribute")
public class AnnotationAttribute {

    public final static java.lang.Long staticEntityId = 1L;
    @javax.persistence.Column(name = "property")
    @DescriptionField
    private java.lang.String property;
    @javax.persistence.Column(name = "value")
    private java.lang.String value;
    @javax.persistence.Column(name = "annotation_attribute_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @DescriptionField
    private java.lang.Long annotationAttributeId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "annotation_id_annotation")
    private it.polimi.model.field.Annotation annotation;

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

    public java.lang.Long getAnnotationAttributeId() {
        return this.annotationAttributeId;
    }

    public void setAnnotationAttributeId(java.lang.Long annotationAttributeId) {
        this.annotationAttributeId=annotationAttributeId;
    }

    public it.polimi.model.field.Annotation getAnnotation() {
        return this.annotation;
    }

    public void setAnnotation(it.polimi.model.field.Annotation annotation) {
        this.annotation=annotation;
    }

}
