package it.polimi.utils;

import java.lang.annotation.Annotation;
import java.util.List;

import com.sun.codemodel.JClass;

public class Field{
	private String name;
	private Class fieldClass;
	private JClass compositeClass;
	private Class repositoryClass;
	private Annotation[] annotationList;
	
	public Field(String name, Class fieldClass, JClass compositeClass,Class repositoryClass)
	{
		this.name=name;
		this.fieldClass=fieldClass;
		this.setCompositeClass(compositeClass);
		this.setRepositoryClass(repositoryClass);
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
	public Class getRepositoryClass() {
		return repositoryClass;
	}
	public void setRepositoryClass(Class repositoryClass) {
		this.repositoryClass = repositoryClass;
	}

	public Annotation[] getAnnotationList() {
		return annotationList;
	}

	public void setAnnotationList(Annotation[] annotationList) {
		this.annotationList = annotationList;
	}
}
