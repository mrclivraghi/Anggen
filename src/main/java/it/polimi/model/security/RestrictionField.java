
package it.polimi.model.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import it.polimi.utils.annotation.DescriptionField;

@Entity
@Table(schema = "sso", name = "restriction_field")
public class RestrictionField {

    public final static java.lang.Long staticEntityId = 14L;
    @Column(name = "restriction_field_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @DescriptionField
    private java.lang.Long restrictionFieldId;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "field_id_field")
    private it.polimi.model.field.Field field;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "role_id_role")
    private it.polimi.model.security.Role role;

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
