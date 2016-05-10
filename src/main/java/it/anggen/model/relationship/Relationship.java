
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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@javax.persistence.Entity
@Table(schema = "meta", name = "relationship")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
public class Relationship
    extends EntityAttribute
{

    public final static java.lang.Long staticEntityId = 7L;
    @javax.persistence.Column(name = "add_date")
    @CreationTimestamp
    private java.util.Date addDate;
    @javax.persistence.Column(name = "priority")
    private Integer priority;
    @javax.persistence.Column(name = "name")
    @it.anggen.utils.annotation.DescriptionField
    private String name;
    @javax.persistence.Column(name = "mod_date")
    @UpdateTimestamp
    private java.util.Date modDate;
    @javax.persistence.Column(name = "relationship_id")
    @it.anggen.utils.annotation.DescriptionField
    @Id
    @GeneratedValue
    private java.lang.Long relationshipId;
    @OneToMany(fetch = javax.persistence.FetchType.LAZY)
    @org.hibernate.annotations.Type(type = "it.anggen.model.field.Annotation")
    @javax.persistence.JoinColumn(name = "relationship_id_relationship")
    private List<Annotation> annotationList;
    @javax.persistence.OneToOne(fetch = javax.persistence.FetchType.LAZY)
    @org.hibernate.annotations.Type(type = "it.anggen.model.entity.Entity")
    @javax.persistence.JoinColumn(name = "entity_id_entity_target")
    private it.anggen.model.entity.Entity entityTarget;
    @javax.persistence.OneToOne(fetch = javax.persistence.FetchType.LAZY)
    @org.hibernate.annotations.Type(type = "it.anggen.model.entity.Entity")
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    private it.anggen.model.entity.Entity entity;
    @ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @javax.persistence.JoinColumn(name = "tab_id_tab")
    private it.anggen.model.entity.Tab tab;
    @javax.persistence.Column(name = "relationship_type")
    private RelationshipType relationshipType;

    public java.util.Date getAddDate() {
        return this.addDate;
    }

    public void setAddDate(java.util.Date addDate) {
        this.addDate=addDate;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority=priority;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public java.util.Date getModDate() {
        return this.modDate;
    }

    public void setModDate(java.util.Date modDate) {
        this.modDate=modDate;
    }

    public java.lang.Long getRelationshipId() {
        return this.relationshipId;
    }

    public void setRelationshipId(java.lang.Long relationshipId) {
        this.relationshipId=relationshipId;
    }

    public List<Annotation> getAnnotationList() {
        return this.annotationList;
    }

    public void setAnnotationList(List<Annotation> annotationList) {
        this.annotationList=annotationList;
    }

    public it.anggen.model.entity.Entity getEntityTarget() {
        return this.entityTarget;
    }

    public void setEntityTarget(it.anggen.model.entity.Entity entityTarget) {
        this.entityTarget=entityTarget;
    }

    public it.anggen.model.entity.Entity getEntity() {
        return this.entity;
    }

    public void setEntity(it.anggen.model.entity.Entity entity) {
        this.entity=entity;
    }

    public it.anggen.model.entity.Tab getTab() {
        return this.tab;
    }

    public void setTab(it.anggen.model.entity.Tab tab) {
        this.tab=tab;
    }

    public RelationshipType getRelationshipType() {
        return this.relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType=relationshipType;
    }

}
