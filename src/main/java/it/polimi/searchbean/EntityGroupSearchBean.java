
package it.polimi.searchbean;

import java.util.List;
import it.polimi.model.domain.Entity;
import it.polimi.model.domain.RestrictionEntityGroup;

public class EntityGroupSearchBean {

    public Long entityGroupId;
    public String name;
    public List<Entity> entityList;
    public List<RestrictionEntityGroup> restrictionEntityGroupList;

    public Long getEntityGroupId() {
        return this.entityGroupId;
    }

    public void setEntityGroupId(Long entityGroupId) {
        this.entityGroupId=entityGroupId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public List<Entity> getEntityList() {
        return this.entityList;
    }

    public void setEntityList(List<Entity> entityList) {
        this.entityList=entityList;
    }

    public List<RestrictionEntityGroup> getRestrictionEntityGroupList() {
        return this.restrictionEntityGroupList;
    }

    public void setRestrictionEntityGroupList(List<RestrictionEntityGroup> restrictionEntityGroupList) {
        this.restrictionEntityGroupList=restrictionEntityGroupList;
    }

}
