
package it.polimi.searchbean.domain;

import java.util.List;
import it.polimi.model.domain.Field;
import it.polimi.model.domain.Relationship;

public class EntitySearchBean {

    public Long entityId;
    public String name;
    public List<Field> fieldList;
    public List<Relationship> relationshipList;

    public Long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId=entityId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public List<Field> getFieldList() {
        return this.fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList=fieldList;
    }

    public List<Relationship> getRelationshipList() {
        return this.relationshipList;
    }

    public void setRelationshipList(List<Relationship> relationshipList) {
        this.relationshipList=relationshipList;
    }

}
