
package it.anggen.model.generation;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import it.anggen.utils.annotation.DescriptionField;
import it.anggen.utils.annotation.MaxDescendantLevel;

@Entity
@Table(schema = "meta", name = "generation_run")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(1)
public class GenerationRun {

    public final static Long staticEntityId = 17L;
    @javax.persistence.Column(name = "generation_run_id")
    @Id
    @GeneratedValue
    @DescriptionField
    private java.lang.Integer generationRunId;
    @javax.persistence.Column(name = "end_date")
    private java.util.Date endDate;
    @javax.persistence.Column(name = "status")
    private java.lang.Integer status;
    @javax.persistence.Column(name = "start_date")
    private java.util.Date startDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id_project")
    private it.anggen.model.entity.Project project;

    public java.lang.Integer getGenerationRunId() {
        return this.generationRunId;
    }

    public void setGenerationRunId(java.lang.Integer generationRunId) {
        this.generationRunId=generationRunId;
    }

    public java.util.Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(java.util.Date endDate) {
        this.endDate=endDate;
    }

    public java.lang.Integer getStatus() {
        return this.status;
    }

    public void setStatus(java.lang.Integer status) {
        this.status=status;
    }

    public java.util.Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(java.util.Date startDate) {
        this.startDate=startDate;
    }

    public it.anggen.model.entity.Project getProject() {
        return this.project;
    }

    public void setProject(it.anggen.model.entity.Project project) {
        this.project=project;
    }

}
