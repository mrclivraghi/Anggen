
package it.anggen.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.anggen.model.GenerationType;
import it.anggen.model.entity.EntityGroup;
import it.anggen.model.entity.EnumEntity;
import it.anggen.model.generation.GenerationRun;
import it.anggen.utils.annotation.MaxDescendantLevel;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(schema = "meta", name = "project")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
public class Project {

    public final static Long staticEntityId = 5L;
    @javax.persistence.Column(name = "project_id")
    @it.anggen.utils.annotation.DescriptionField
    @Id
    @GeneratedValue
    private Integer projectId;
    @javax.persistence.Column(name = "name")
    @it.anggen.utils.annotation.DescriptionField
    private String name;
    @javax.persistence.Column(name = "add_date")
    @CreationTimestamp
    private java.util.Date addDate;
    @javax.persistence.Column(name = "mod_date")
    @UpdateTimestamp
    private java.util.Date modDate;
    @OneToMany(fetch = FetchType.LAZY)
    @Type(type = "it.anggen.model.entity.EntityGroup")
    @JoinColumn(name = "project_id_project")
    private List<EntityGroup> entityGroupList;
    @OneToMany(fetch = FetchType.LAZY)
    @Type(type = "it.anggen.model.entity.EnumEntity")
    @JoinColumn(name = "project_id_project")
    private List<EnumEntity> enumEntityList;
    @OneToMany(fetch = FetchType.LAZY)
    @Type(type = "it.anggen.model.generation.GenerationRun")
    @JoinColumn(name = "project_id_project")
    private List<GenerationRun> generationRunList;
    
    @Column(name="generation_type")
    private GenerationType generationType;
    

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

    public java.util.Date getAddDate() {
        return this.addDate;
    }

    public void setAddDate(java.util.Date addDate) {
        this.addDate=addDate;
    }

    public java.util.Date getModDate() {
        return this.modDate;
    }

    public void setModDate(java.util.Date modDate) {
        this.modDate=modDate;
    }

    public List<EntityGroup> getEntityGroupList() {
        return this.entityGroupList;
    }

    public void setEntityGroupList(List<EntityGroup> entityGroupList) {
        this.entityGroupList=entityGroupList;
    }

    public List<EnumEntity> getEnumEntityList() {
        return this.enumEntityList;
    }

    public void setEnumEntityList(List<EnumEntity> enumEntityList) {
        this.enumEntityList=enumEntityList;
    }

    public List<GenerationRun> getGenerationRunList() {
        return this.generationRunList;
    }

    public void setGenerationRunList(List<GenerationRun> generationRunList) {
        this.generationRunList=generationRunList;
    }

	public GenerationType getGenerationType() {
		return generationType;
	}

	public void setGenerationType(GenerationType generationType) {
		this.generationType = generationType;
	}

}
