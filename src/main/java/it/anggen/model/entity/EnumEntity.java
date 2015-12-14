package it.anggen.model.entity;

import it.anggen.model.field.EnumField;
import it.anggen.model.field.EnumValue;
import it.anggen.utils.annotation.DescriptionField;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


@Entity
@Table(schema="meta",name="enum_entity")
public class EnumEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long enumEntityId;
	
	@DescriptionField
	private String name;
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="enumEntity")
	@Type(type = "it.anggen.model.field.EnumValue")
	private List<EnumValue> enumValueList;

	/*@OneToMany(fetch=FetchType.EAGER,mappedBy="enumEntity")
	@Type(type = "it.anggen.model.field.EnumField")
	private List<EnumField> enumFieldList;
*/
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="project_id_project")
	private Project project;
	
	public EnumEntity() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the enumEntityId
	 */
	public Long getEnumEntityId() {
		return enumEntityId;
	}

	/**
	 * @param enumEntityId the enumEntityId to set
	 */
	public void setEnumEntityId(Long enumEntityId) {
		this.enumEntityId = enumEntityId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the enumValueList
	 */
	public List<EnumValue> getEnumValueList() {
		return enumValueList;
	}

	/**
	 * @param enumValueList the enumValueList to set
	 */
	public void setEnumValueList(List<EnumValue> enumValueList) {
		this.enumValueList = enumValueList;
	}

	/**
	 * @return the enumFieldList
	 */
/*	public List<EnumField> getEnumFieldList() {
		return enumFieldList;
	}

	/**
	 * @param enumFieldList the enumFieldList to set
	 */
	/*public void setEnumFieldList(List<EnumField> enumFieldList) {
		this.enumFieldList = enumFieldList;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

}
