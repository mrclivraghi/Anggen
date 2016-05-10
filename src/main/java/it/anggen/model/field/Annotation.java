
package it.anggen.model.field;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.anggen.model.AnnotationType;
import it.anggen.model.field.AnnotationAttribute;
import it.anggen.utils.annotation.MaxDescendantLevel;
import org.hibernate.annotations.Type;

@Entity
@Table(schema = "meta", name = "annotation")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
public class Annotation {

    public final static java.lang.Long staticEntityId = 17L;
    @javax.persistence.Column(name = "annotation_id")
    @Id
    @GeneratedValue
    @it.anggen.utils.annotation.DescriptionField
    private java.lang.Long annotationId;
    @OneToMany(fetch = javax.persistence.FetchType.LAZY)
    @Type(type = "it.anggen.model.field.AnnotationAttribute")
    @javax.persistence.JoinColumn(name = "annotation_id_annotation")
    private List<AnnotationAttribute> annotationAttributeList;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @javax.persistence.JoinColumn(name = "field_id_field")
    private it.anggen.model.field.Field field;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @javax.persistence.JoinColumn(name = "enum_field_id_enum_field")
    private it.anggen.model.field.EnumField enumField;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @javax.persistence.JoinColumn(name = "relationship_id_relationship")
    private it.anggen.model.relationship.Relationship relationship;
    @javax.persistence.Column(name = "annotation_type")
    @it.anggen.utils.annotation.DescriptionField
    private AnnotationType annotationType;

    public java.lang.Long getAnnotationId() {
        return this.annotationId;
    }

    public void setAnnotationId(java.lang.Long annotationId) {
        this.annotationId=annotationId;
    }

    public List<AnnotationAttribute> getAnnotationAttributeList() {
        return this.annotationAttributeList;
    }

    public void setAnnotationAttributeList(List<AnnotationAttribute> annotationAttributeList) {
        this.annotationAttributeList=annotationAttributeList;
    }

    public it.anggen.model.field.Field getField() {
        return this.field;
    }

    public void setField(it.anggen.model.field.Field field) {
        this.field=field;
    }

    public it.anggen.model.field.EnumField getEnumField() {
        return this.enumField;
    }

    public void setEnumField(it.anggen.model.field.EnumField enumField) {
        this.enumField=enumField;
    }

    public it.anggen.model.relationship.Relationship getRelationship() {
        return this.relationship;
    }

    public void setRelationship(it.anggen.model.relationship.Relationship relationship) {
        this.relationship=relationship;
    }

    public AnnotationType getAnnotationType() {
        return this.annotationType;
    }

    public void setAnnotationType(AnnotationType annotationType) {
        this.annotationType=annotationType;
    }

}
