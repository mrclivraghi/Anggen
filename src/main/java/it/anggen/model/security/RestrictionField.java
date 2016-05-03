
package it.anggen.model.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import it.anggen.utils.annotation.DescriptionField;
import it.anggen.utils.annotation.MaxDescendantLevel;

@Entity
@Table(schema = "sso", name = "restriction_field")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
public class RestrictionField {

    public final static java.lang.Long staticEntityId = 10L;
    @Column(name = "restriction_field_id")
    @Id
    @GeneratedValue
    @DescriptionField
    private java.lang.Long restrictionFieldId;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @javax.persistence.JoinColumn(name = "role_id_role")
    private it.anggen.model.security.Role role;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @javax.persistence.JoinColumn(name = "field_id_field")
    private it.anggen.model.field.Field field;

    public java.lang.Long getRestrictionFieldId() {
        return this.restrictionFieldId;
    }

    public void setRestrictionFieldId(java.lang.Long restrictionFieldId) {
        this.restrictionFieldId=restrictionFieldId;
    }

    public it.anggen.model.security.Role getRole() {
        return this.role;
    }

    public void setRole(it.anggen.model.security.Role role) {
        this.role=role;
    }

    public it.anggen.model.field.Field getField() {
        return this.field;
    }

    public void setField(it.anggen.model.field.Field field) {
        this.field=field;
    }

}
