
package it.anggen.searchbean.generation;


public class GenerationRunSearchBean {

    public java.lang.Integer generationRunId;
    public java.util.Date startDate;
    public java.util.Date endDate;
    public java.lang.Integer status;
    public it.anggen.model.entity.Project project;

    public java.lang.Integer getGenerationRunId() {
        return this.generationRunId;
    }

    public void setGenerationRunId(java.lang.Integer generationRunId) {
        this.generationRunId=generationRunId;
    }

    public java.util.Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(java.util.Date startDate) {
        this.startDate=startDate;
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

    public it.anggen.model.entity.Project getProject() {
        return this.project;
    }

    public void setProject(it.anggen.model.entity.Project project) {
        this.project=project;
    }

}
