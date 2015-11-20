package it.polimi.generation;

import java.util.List;

import it.polimi.model.domain.Entity;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;

public class TestClasses {

	
	
	
	public static void main(String[] args) {
		Class classclass = null;
		JClass jClass;
		JDefinedClass jDefinedClass = null;
		JType jType;
		JCodeModel codeModel = new JCodeModel();
		//jDefinedClass = String.class;
		jClass= jDefinedClass;
		//jClass=classclass;
		//jType=classclass;
		jType = jDefinedClass;
		jClass = codeModel.ref(List.class).narrow(Entity.class);
		System.out.println(jClass.name()+"-"+jClass.fullName());
		jClass = codeModel.ref(String.class);
		System.out.println(jClass.name()+"-"+jClass.fullName());
		jClass = codeModel.ref(Entity.class);
		System.out.println(jClass.name()+"-"+jClass.fullName());
		try {
			jClass= codeModel._class("Test", ClassType.CLASS);
			System.out.println(jClass.name()+"-"+jClass.fullName());
		} catch (JClassAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		

	}

}
