package it.polimi.utils;

import com.sun.codemodel.JClass;

public class ClassDetail {
	private Class classClass;
	
	private JClass compositeClass;
	
	private String parentName;
	
	public Class getClassClass() {
		return classClass;
	}
	public void setClassClass(Class classClass) {
		this.classClass = classClass;
	}
	public JClass getCompositeClass() {
		return compositeClass;
	}
	public void setCompositeClass(JClass compositeClass) {
		this.compositeClass = compositeClass;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
}
