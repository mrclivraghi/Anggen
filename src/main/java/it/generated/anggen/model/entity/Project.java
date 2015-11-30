
package it.generated.anggen.model.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.generated.anggen.model.entity.EntityGroup;
import it.polimi.utils.annotation.DescriptionField;
import org.hibernate.annotations.Type;

@Entity
@Table(schema = "mustle", name = "project")
public class Project {

    public final static java.lang.Long staticEntityId = 5294L;
    @javax.persistence.Column(name = "entity_id")
    private java.lang.Long entityId;
    @javax.persistence.Column(name = "project_id")
    @DescriptionField
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer projectId;
    @javax.persistence.Column(name = "name")
    private String name;
    @OneToMany(fetch = FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.entity.EntityGroup")
    @JoinColumn(name = "project_id_entity_group")
    private List<EntityGroup> entityGroupList;

    public java.lang.Long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(java.lang.Long entityId) {
        this.entityId=entityId;
    }

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

    public List<EntityGroup> getEntityGroupList() {
        return this.entityGroupList;
    }

    public void setEntityGroupList(List<EntityGroup> entityGroupList) {
        this.entityGroupList=entityGroupList;
    }

}
