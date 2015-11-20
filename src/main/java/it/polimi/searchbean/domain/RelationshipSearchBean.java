
package it.polimi.searchbean.domain;

import it.polimi.model.domain.Entity;
import it.polimi.model.domain.RelationshipType;

public class RelationshipSearchBean {

    public Long relationshipId;
    public String name;
    public Entity entity;
    public Entity entityTarget;
    public RelationshipType relationshipType;

    public Long getRelationshipId() {
        return this.relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId=relationshipId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public void setEntity(Entity entity) {
        this.entity=entity;
    }

    public Entity getEntityTarget() {
        return this.entityTarget;
    }

    public void setEntityTarget(Entity entityTarget) {
        this.entityTarget=entityTarget;
    }

    public RelationshipType getRelationshipType() {
        return this.relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType=relationshipType;
    }

}
