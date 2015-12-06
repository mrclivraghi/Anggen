
package it.polimi.searchbean.security;


public class RestrictionFieldSearchBean {

    public java.lang.Long restrictionFieldId;
    public it.polimi.model.field.Field field;
    public it.polimi.model.security.Role role;

    public java.lang.Long getRestrictionFieldId() {
        return this.restrictionFieldId;
    }

    public void setRestrictionFieldId(java.lang.Long restrictionFieldId) {
        this.restrictionFieldId=restrictionFieldId;
    }

    public it.polimi.model.field.Field getField() {
        return this.field;
    }

    public void setField(it.polimi.model.field.Field field) {
        this.field=field;
    }

    public it.polimi.model.security.Role getRole() {
        return this.role;
    }

    public void setRole(it.polimi.model.security.Role role) {
        this.role=role;
    }

}
