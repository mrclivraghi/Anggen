
package it.polimi.searchbean.security;

import java.util.List;
import it.polimi.model.security.Role;

public class EntitySearchBean {

    public Long entityId;
    public String entityName;
    public List<Role> roleList;

    public Long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId=entityId;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName=entityName;
    }

    public List<Role> getRoleList() {
        return this.roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList=roleList;
    }

}
