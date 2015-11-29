
package it.polimi.model.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.polimi.domain.EntityGroupTest;
import it.polimi.utils.annotation.DescriptionField;
import org.hibernate.annotations.Type;

@Entity
@Table(schema = "mustle", name = "project")
public class Project {

    public final static Long entityId = 2510L;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @javax.persistence.Column(name = "project_id")
    @DescriptionField
    private Integer projectId;
    
    @javax.persistence.Column(name = "name")
    private String name;
    
    @OneToMany(fetch = FetchType.EAGER)
    @Type(type = "it.polimi.model.domain.EntityGroup")
    @JoinColumn(name = "project_id_project")
    private List<EntityGroup> entityGroupList;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId=projectId;
    }

	public List<EntityGroup> getEntityGroupList() {
		return entityGroupList;
	}

	public void setEntityGroupList(List<EntityGroup> entityGroupList) {
		this.entityGroupList = entityGroupList;
	}


}
