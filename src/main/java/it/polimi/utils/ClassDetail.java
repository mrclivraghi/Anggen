package it.polimi.utils;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JType;

public class ClassDetail {
	private JType classClass;
	
	private JType compositeClass;
	
	private String parentName;
	
	public JType getClassClass() {
		return classClass;
	}
	public void setClassClass(JType classClass) {
		this.classClass = classClass;
	}
	public JType getCompositeClass() {
		return compositeClass;
	}
	public void setCompositeClass(JType compositeClass) {
		this.compositeClass = compositeClass;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
}
