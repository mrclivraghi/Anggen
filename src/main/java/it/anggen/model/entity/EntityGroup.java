
package it.anggen.model.entity;

import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.anggen.model.security.RestrictionEntityGroup;
import it.anggen.utils.annotation.MaxDescendantLevel;
import org.hibernate.annotations.Type;

@javax.persistence.Entity
@Table(schema = "meta", name = "entity_group")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
public class EntityGroup {

    public final static java.lang.Long staticEntityId = 19L;
    @javax.persistence.Column(name = "name")
    @it.anggen.utils.annotation.DescriptionField
    private String name;
    @javax.persistence.Column(name = "entity_group_id")
    @Id
    @GeneratedValue
    @it.anggen.utils.annotation.DescriptionField
    private java.lang.Long entityGroupId;
    @ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @javax.persistence.JoinColumn(name = "project_id_project")
    private it.anggen.model.entity.Project project;
    @OneToMany(fetch = javax.persistence.FetchType.LAZY)
    @Type(type = "it.anggen.model.security.RestrictionEntityGroup")
    @javax.persistence.JoinColumn(name = "entity_group_id_entity_group")
    private List<RestrictionEntityGroup> restrictionEntityGroupList;
    @OneToMany(fetch = javax.persistence.FetchType.LAZY)
    @Type(type = "it.anggen.model.entity.Entity")
    @javax.persistence.JoinColumn(name = "entity_group_id_entity_group")
    private List<it.anggen.model.entity.Entity> entityList;
    @javax.persistence.Column(name = "security_type")
    private it.anggen.model.SecurityType securityType;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public java.lang.Long getEntityGroupId() {
        return this.entityGroupId;
    }

    public void setEntityGroupId(java.lang.Long entityGroupId) {
        this.entityGroupId=entityGroupId;
    }

    public it.anggen.model.entity.Project getProject() {
        return this.project;
    }

    public void setProject(it.anggen.model.entity.Project project) {
        this.project=project;
    }

    public List<RestrictionEntityGroup> getRestrictionEntityGroupList() {
        return this.restrictionEntityGroupList;
    }

    public void setRestrictionEntityGroupList(List<RestrictionEntityGroup> restrictionEntityGroupList) {
        this.restrictionEntityGroupList=restrictionEntityGroupList;
    }

    public List<it.anggen.model.entity.Entity> getEntityList() {
        return this.entityList;
    }

    public void setEntityList(List<it.anggen.model.entity.Entity> entityList) {
        this.entityList=entityList;
    }

    public it.anggen.model.SecurityType getSecurityType() {
        return this.securityType;
    }

    public void setSecurityType(it.anggen.model.SecurityType securityType) {
        this.securityType=securityType;
    }

}
