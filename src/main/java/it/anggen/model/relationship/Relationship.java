
package it.anggen.model.relationship;

import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.anggen.model.RelationshipType;
import it.anggen.model.field.Annotation;
import it.anggen.utils.EntityAttribute;
import it.anggen.utils.annotation.MaxDescendantLevel;

@javax.persistence.Entity
@Table(schema = "meta", name = "relationship")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
public class Relationship extends EntityAttribute{

    public final static java.lang.Long staticEntityId = 11L;
    @javax.persistence.Column(name = "name")
    @it.anggen.utils.annotation.DescriptionField
    @it.anggen.utils.annotation.Priority(2)
    private String name;
    @javax.persistence.Column(name = "relationship_id")
    @Id
    @GeneratedValue
    @it.anggen.utils.annotation.DescriptionField
    @it.anggen.utils.annotation.Priority(1)
    private java.lang.Long relationshipId;
    @javax.persistence.Column(name = "priority")
    @it.anggen.utils.annotation.Priority(2)
    private Integer priority;
    @javax.persistence.OneToOne(fetch = javax.persistence.FetchType.EAGER)
    @org.hibernate.annotations.Type(type = "it.anggen.model.entity.Entity")
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    @it.anggen.utils.annotation.Priority(4)
    private it.anggen.model.entity.Entity entity;
    @javax.persistence.OneToOne(fetch = javax.persistence.FetchType.EAGER)
    @org.hibernate.annotations.Type(type = "it.anggen.model.entity.Entity")
    @javax.persistence.JoinColumn(name = "entity_id_entity_target")
    @it.anggen.utils.annotation.Priority(4)
    private it.anggen.model.entity.Entity entityTarget;
    @ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "tab_id_tab")
    @it.anggen.utils.annotation.Priority(4)
    private it.anggen.model.entity.Tab tab;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @org.hibernate.annotations.Type(type = "it.anggen.model.field.Annotation")
    @javax.persistence.JoinColumn(name = "relationship_id_relationship")
    @it.anggen.utils.annotation.Priority(4)
    private List<Annotation> annotationList;
    @javax.persistence.Column(name = "relationship_type")
    @it.anggen.utils.annotation.Priority(3)
    private RelationshipType relationshipType;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public java.lang.Long getRelationshipId() {
        return this.relationshipId;
    }

    public void setRelationshipId(java.lang.Long relationshipId) {
        this.relationshipId=relationshipId;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority=priority;
    }

    public it.anggen.model.entity.Entity getEntity() {
        return this.entity;
    }

    public void setEntity(it.anggen.model.entity.Entity entity) {
        this.entity=entity;
    }

    public it.anggen.model.entity.Entity getEntityTarget() {
        return this.entityTarget;
    }

    public void setEntityTarget(it.anggen.model.entity.Entity entityTarget) {
        this.entityTarget=entityTarget;
    }

    public it.anggen.model.entity.Tab getTab() {
        return this.tab;
    }

    public void setTab(it.anggen.model.entity.Tab tab) {
        this.tab=tab;
    }

    public List<Annotation> getAnnotationList() {
        return this.annotationList;
    }

    public void setAnnotationList(List<Annotation> annotationList) {
        this.annotationList=annotationList;
    }

    public RelationshipType getRelationshipType() {
        return this.relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType=relationshipType;
    }

}
