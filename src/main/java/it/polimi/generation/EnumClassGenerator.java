package it.polimi.generation;

import java.io.File;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JEnumConstant;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;

import it.polimi.boot.OracleNamingStrategy;
import it.polimi.model.field.EnumField;
import it.polimi.model.field.EnumValue;
import it.polimi.model.field.Field;
import it.polimi.model.relationship.Relationship;
import it.polimi.model.relationship.RelationshipType;
import it.polimi.utils.Utility;

public class EnumClassGenerator {

	private EnumField enumField;
	
	public EnumClassGenerator(EnumField enumField) {
		// TODO Auto-generated constructor stub
		this.enumField=enumField;
	}
	private void saveFile(JCodeModel codeModel)
	{
		try {
				File file = new File(""); 
				String directory = file.getAbsolutePath()+"\\src\\main\\java";
				System.out.println(directory);
			   codeModel.build(new File (directory));
			} catch (Exception ex) {
			   ex.printStackTrace();
			}
		
	}

	public JDefinedClass getModelClass()
	{
		JCodeModel	codeModel = new JCodeModel();
		String className = Utility.getFirstUpper(enumField.getName());
		String lowerClassName = Utility.getFirstLower(className);
		JDefinedClass myClass= null;
		try {
			//TODO fix
			myClass = codeModel._class("it.polimi.domain."+className, ClassType.ENUM);
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		OracleNamingStrategy namingStrategy = new OracleNamingStrategy();
		for (EnumValue enumValue : enumField.getEnumValueList())
		{
			JEnumConstant enumConstant =myClass.enumConstant(enumValue.getName());
			enumConstant.arg(JExpr.lit(enumValue.getValue()));
		}
		/* get and set value*/
		JVar valueField = myClass.field(JMod.PRIVATE+JMod.FINAL, int.class, "value");
		JMethod constructor = myClass.constructor(JMod.PRIVATE);
		constructor.param(int.class, "value");
		JBlock constructorBlock=constructor.body();
		constructorBlock.directStatement("this.value=value; \n");
		JMethod getter = myClass.method(JMod.PUBLIC, int.class, "getValue");
		JBlock getterBlock = getter.body();
		getterBlock.directStatement("return this.value; \n");
		saveFile(codeModel);
		return myClass;
	}


}
