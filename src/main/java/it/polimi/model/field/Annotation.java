
package it.polimi.model.field;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.polimi.model.AnnotationType;
import it.polimi.model.field.AnnotationAttribute;
import it.polimi.utils.annotation.DescriptionField;

import org.hibernate.annotations.Type;

@Entity
@Table(schema = "meta", name = "annotation")
public class Annotation {

    public final static java.lang.Long staticEntityId = 7L;
    @javax.persistence.Column(name = "annotation_id")
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @DescriptionField
    private java.lang.Long annotationId;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.field.AnnotationAttribute")
    @javax.persistence.JoinColumn(name = "annotation_id_annotation")
    private List<AnnotationAttribute> annotationAttributeList;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "field_id_field")
    private it.polimi.model.field.Field field;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "relationship_id_relationship")
    private it.polimi.model.relationship.Relationship relationship;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "enum_field_id_enum_field")
    private it.polimi.model.field.EnumField enumField;
    @javax.persistence.Column(name = "annotation_type")
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

    public it.polimi.model.field.Field getField() {
        return this.field;
    }

    public void setField(it.polimi.model.field.Field field) {
        this.field=field;
    }

    public it.polimi.model.relationship.Relationship getRelationship() {
        return this.relationship;
    }

    public void setRelationship(it.polimi.model.relationship.Relationship relationship) {
        this.relationship=relationship;
    }

    public it.polimi.model.field.EnumField getEnumField() {
        return this.enumField;
    }

    public void setEnumField(it.polimi.model.field.EnumField enumField) {
        this.enumField=enumField;
    }

    public AnnotationType getAnnotationType() {
        return this.annotationType;
    }

    public void setAnnotationType(AnnotationType annotationType) {
        this.annotationType=annotationType;
    }

}
