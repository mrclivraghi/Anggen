
package it.anggen.model.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.anggen.model.entity.EntityGroup;
import it.anggen.model.entity.EnumEntity;
import it.anggen.utils.annotation.MaxDescendantLevel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

@Entity
@Table(schema = "meta", name = "project")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Project {

    public final static Long staticEntityId = 9L;
    @javax.persistence.Column(name = "project_id")
    @it.anggen.utils.annotation.Priority(1)
    @Id
    @GeneratedValue
    @it.anggen.utils.annotation.DescriptionField
    private Integer projectId;
    @javax.persistence.Column(name = "name")
    @it.anggen.utils.annotation.Priority(2)
    @it.anggen.utils.annotation.DescriptionField
    private String name;
    @OneToMany(fetch = FetchType.EAGER)
    @Type(type = "it.anggen.model.entity.EnumEntity")
    @JoinColumn(name = "project_id_project")
    @it.anggen.utils.annotation.Priority(4)
    private List<EnumEntity> enumEntityList;
    @OneToMany(fetch = FetchType.EAGER)
    @Type(type = "it.anggen.model.entity.EntityGroup")
    @JoinColumn(name = "project_id_project")
    @it.anggen.utils.annotation.Priority(4)
    private List<EntityGroup> entityGroupList;

    public Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId=projectId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public List<EnumEntity> getEnumEntityList() {
        return this.enumEntityList;
    }

    public void setEnumEntityList(List<EnumEntity> enumEntityList) {
        this.enumEntityList=enumEntityList;
    }

    public List<EntityGroup> getEntityGroupList() {
        return this.entityGroupList;
    }

    public void setEntityGroupList(List<EntityGroup> entityGroupList) {
        this.entityGroupList=entityGroupList;
    }

}
