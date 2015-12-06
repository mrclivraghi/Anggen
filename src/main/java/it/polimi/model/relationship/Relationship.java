
package it.polimi.model.relationship;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import it.polimi.model.EntityAttribute;
import it.polimi.model.RelationshipType;
import it.polimi.model.field.Annotation;
import it.polimi.utils.annotation.DescriptionField;

@javax.persistence.Entity
@Table(schema = "meta", name = "relationship")
public class Relationship extends EntityAttribute{

    public final static java.lang.Long staticEntityId = 10L;
    @javax.persistence.Column(name = "name")
    private String name;
    @javax.persistence.Column(name = "relationship_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @DescriptionField
    private java.lang.Long relationshipId;
    @javax.persistence.OneToOne(fetch = javax.persistence.FetchType.EAGER)
    @org.hibernate.annotations.Type(type = "it.generated.anggen.model.entity.Entity")
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    private it.polimi.model.entity.Entity entity;
    @javax.persistence.OneToOne(fetch = javax.persistence.FetchType.EAGER)
    @org.hibernate.annotations.Type(type = "it.generated.anggen.model.entity.Entity")
    @javax.persistence.JoinColumn(name = "entity_id_entity_target")
    private it.polimi.model.entity.Entity entityTarget;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @org.hibernate.annotations.Type(type = "it.generated.anggen.model.field.Annotation")
    @javax.persistence.JoinColumn(name = "relationship_id_relationship")
    private List<Annotation> annotationList;
    @ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "tab_id_tab")
    private it.polimi.model.entity.Tab tab;
    @javax.persistence.Column(name = "relationship_type")
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

    public it.polimi.model.entity.Entity getEntity() {
        return this.entity;
    }

    public void setEntity(it.polimi.model.entity.Entity entity) {
        this.entity=entity;
    }

    public it.polimi.model.entity.Entity getEntityTarget() {
        return this.entityTarget;
    }

    public void setEntityTarget(it.polimi.model.entity.Entity entityTarget) {
        this.entityTarget=entityTarget;
    }

    public List<Annotation> getAnnotationList() {
        return this.annotationList;
    }

    public void setAnnotationList(List<Annotation> annotationList) {
        this.annotationList=annotationList;
    }

    public it.polimi.model.entity.Tab getTab() {
        return this.tab;
    }

    public void setTab(it.polimi.model.entity.Tab tab) {
        this.tab=tab;
    }

    public RelationshipType getRelationshipType() {
        return this.relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType=relationshipType;
    }
    
    @JsonIgnore
    public Boolean isList()
	{
		return !(relationshipType==RelationshipType.ONE_TO_ONE || relationshipType==RelationshipType.MANY_TO_ONE);
	}

}
