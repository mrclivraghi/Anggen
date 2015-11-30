
package it.generated.anggen.model.field;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.generated.anggen.model.AnnotationType;
import it.generated.anggen.model.field.AnnotationAttribute;
import it.polimi.utils.annotation.DescriptionField;
import org.hibernate.annotations.Type;

@Entity
@Table(schema = "mustle", name = "annotation")
public class Annotation {

    public final static java.lang.Long staticEntityId = 5282L;
    @javax.persistence.Column(name = "annotation_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @DescriptionField
    private java.lang.Long annotationId;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "enum_field_id_enum_field")
    private it.generated.anggen.model.field.EnumField enumField;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "relationship_id_relationship")
    private it.generated.anggen.model.relationship.Relationship relationship;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "field_id_field")
    private it.generated.anggen.model.field.Field field;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.field.AnnotationAttribute")
    @javax.persistence.JoinColumn(name = "annotation_id_annotation_attribute")
    private List<AnnotationAttribute> annotationAttributeList;
    @javax.persistence.Column(name = "annotation_type")
    private AnnotationType annotationType;

    public java.lang.Long getAnnotationId() {
        return this.annotationId;
    }

    public void setAnnotationId(java.lang.Long annotationId) {
        this.annotationId=annotationId;
    }

    public it.generated.anggen.model.field.EnumField getEnumField() {
        return this.enumField;
    }

    public void setEnumField(it.generated.anggen.model.field.EnumField enumField) {
        this.enumField=enumField;
    }

    public it.generated.anggen.model.relationship.Relationship getRelationship() {
        return this.relationship;
    }

    public void setRelationship(it.generated.anggen.model.relationship.Relationship relationship) {
        this.relationship=relationship;
    }

    public it.generated.anggen.model.field.Field getField() {
        return this.field;
    }

    public void setField(it.generated.anggen.model.field.Field field) {
        this.field=field;
    }

    public List<AnnotationAttribute> getAnnotationAttributeList() {
        return this.annotationAttributeList;
    }

    public void setAnnotationAttributeList(List<AnnotationAttribute> annotationAttributeList) {
        this.annotationAttributeList=annotationAttributeList;
    }

    public AnnotationType getAnnotationType() {
        return this.annotationType;
    }

    public void setAnnotationType(AnnotationType annotationType) {
        this.annotationType=annotationType;
    }

}
