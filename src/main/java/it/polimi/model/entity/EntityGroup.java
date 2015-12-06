
package it.polimi.model.entity;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.polimi.model.security.RestrictionEntityGroup;
import it.polimi.utils.annotation.DescriptionField;

import org.hibernate.annotations.Type;

@javax.persistence.Entity
@Table(schema = "meta", name = "entity_group")
public class EntityGroup {

    public final static java.lang.Long staticEntityId = 8L;
    @javax.persistence.Column(name = "name")
    private String name;
    @javax.persistence.Column(name = "entity_id")
    private java.lang.Long entityId;
    @javax.persistence.Column(name = "entity_group_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @DescriptionField
    private java.lang.Long entityGroupId;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.entity.Entity")
    @javax.persistence.JoinColumn(name = "entity_group_id_entity_group")
    private List<it.polimi.model.entity.Entity> entityList;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.security.RestrictionEntityGroup")
    @javax.persistence.JoinColumn(name = "entity_group_id_entity_group")
    private List<RestrictionEntityGroup> restrictionEntityGroupList;
    @ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "project_id_project")
    private it.polimi.model.entity.Project project;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public java.lang.Long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(java.lang.Long entityId) {
        this.entityId=entityId;
    }

    public java.lang.Long getEntityGroupId() {
        return this.entityGroupId;
    }

    public void setEntityGroupId(java.lang.Long entityGroupId) {
        this.entityGroupId=entityGroupId;
    }

    public List<it.polimi.model.entity.Entity> getEntityList() {
        return this.entityList;
    }

    public void setEntityList(List<it.polimi.model.entity.Entity> entityList) {
        this.entityList=entityList;
    }

    public List<RestrictionEntityGroup> getRestrictionEntityGroupList() {
        return this.restrictionEntityGroupList;
    }

    public void setRestrictionEntityGroupList(List<RestrictionEntityGroup> restrictionEntityGroupList) {
        this.restrictionEntityGroupList=restrictionEntityGroupList;
    }

    public it.polimi.model.entity.Project getProject() {
        return this.project;
    }

    public void setProject(it.polimi.model.entity.Project project) {
        this.project=project;
    }

}
