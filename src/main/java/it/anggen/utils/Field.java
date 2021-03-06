package it.anggen.utils;

import java.lang.annotation.Annotation;
import java.util.List;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JDefinedClass;

public class Field{
	private String name;
	private Class fieldClass;
	private JClass compositeClass;
	private JDefinedClass repositoryClass;
	private Annotation[] annotationList;
	private Boolean isEnum;
	private List<String> enumValuesList;
	private List<Field> childrenFilterList;
	private Class ownerClass;
	
	public Field(String name, Class fieldClass, JClass compositeClass,JDefinedClass repositoryClass2)
	{
		this.name=name;
		this.fieldClass=fieldClass;
		this.setCompositeClass(compositeClass);
		this.setRepositoryClass(repositoryClass2);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Class getFieldClass() {
		return fieldClass;
	}
	public void setFieldClass(Class fieldClass) {
		this.fieldClass = fieldClass;
	}
	public JClass getCompositeClass() {
		return compositeClass;
	}
	public void setCompositeClass(JClass compositeClass) {
		this.compositeClass = compositeClass;
	}
	public JDefinedClass getRepositoryClass() {
		return repositoryClass;
	}
	public void setRepositoryClass(JDefinedClass repositoryClass2) {
		this.repositoryClass = repositoryClass2;
	}

	public Annotation[] getAnnotationList() {
		return annotationList;
	}

	public void setAnnotationList(Annotation[] annotationList) {
		this.annotationList = annotationList;
	}

	/**
	 * @return the isEnum
	 */
	public Boolean getIsEnum() {
		return isEnum;
	}

	/**
	 * @param isEnum the isEnum to set
	 */
	public void setIsEnum(Boolean isEnum) {
		this.isEnum = isEnum;
	}

	/**
	 * @return the enumValuesList
	 */
	public List<String> getEnumValuesList() {
		return enumValuesList;
	}

	/**
	 * @param enumValuesList the enumValuesList to set
	 */
	public void setEnumValuesList(List<String> enumValuesList) {
		this.enumValuesList = enumValuesList;
	}

	/**
	 * @return the childrenFilterList
	 */
	public List<Field> getChildrenFilterList() {
		return childrenFilterList;
	}

	/**
	 * @param childrenFilterList the childrenFilterList to set
	 */
	public void setChildrenFilterList(List<Field> childrenFilterList) {
		this.childrenFilterList = childrenFilterList;
	}

	/**
	 * @return the ownerClass
	 */
	public Class getOwnerClass() {
		return ownerClass;
	}

	/**
	 * @param ownerClass the ownerClass to set
	 */
	public void setOwnerClass(Class ownerClass) {
		this.ownerClass = ownerClass;
	}

}
