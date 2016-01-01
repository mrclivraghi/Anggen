
package it.anggen.searchbean.security;


public class RestrictionFieldSearchBean {

    public java.lang.Long restrictionFieldId;
    public it.anggen.model.field.Field field;
    public it.anggen.model.security.Role role;

    public java.lang.Long getRestrictionFieldId() {
        return this.restrictionFieldId;
    }

    public void setRestrictionFieldId(java.lang.Long restrictionFieldId) {
        this.restrictionFieldId=restrictionFieldId;
    }

    public it.anggen.model.field.Field getField() {
        return this.field;
    }

    public void setField(it.anggen.model.field.Field field) {
        this.field=field;
    }

    public it.anggen.model.security.Role getRole() {
        return this.role;
    }

    public void setRole(it.anggen.model.security.Role role) {
        this.role=role;
    }

}
