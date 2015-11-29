
package it.polimi.searchbean;

import it.polimi.model.field.Field;
import it.polimi.model.security.Role;

public class RestrictionFieldSearchBean {

    public Long restrictionFieldId;
    public Field field;
    public Role role;

    public Long getRestrictionFieldId() {
        return this.restrictionFieldId;
    }

    public void setRestrictionFieldId(Long restrictionFieldId) {
        this.restrictionFieldId=restrictionFieldId;
    }

    public Field getField() {
        return this.field;
    }

    public void setField(Field field) {
        this.field=field;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role=role;
    }

}
