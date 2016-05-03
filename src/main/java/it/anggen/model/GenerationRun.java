package it.anggen.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import it.anggen.model.entity.Project;

@Entity
public class GenerationRun {

	private Integer generationRunId;
	
	@ManyToOne
	private Project project;
	
	private Date startDate;
	private Date endDate;
	
	private Integer status;
	
	public Integer getGenerationRunId() {
		return generationRunId;
	}
	public void setGenerationRunId(Integer generationRunId) {
		this.generationRunId = generationRunId;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
