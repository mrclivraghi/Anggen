package it.anggen.generation;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

import it.anggen.utils.OracleNamingStrategy;
import it.anggen.utils.Utility;
import it.anggen.model.entity.EnumEntity;
import it.anggen.model.field.EnumField;
import it.anggen.model.field.EnumValue;
import it.anggen.model.field.Field;
import it.anggen.model.relationship.Relationship;

@Service
public class EnumClassGenerator {

	private EnumEntity enumEntity;
	
	@Autowired
	private Generator generator;
	
	public EnumClassGenerator()
	{
		
	}
	
	public void init(EnumEntity enumEntity) {
		// TODO Auto-generated constructor stub
		this.enumEntity=enumEntity;
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
		String className = Utility.getFirstUpper(enumEntity.getName());
		String lowerClassName = Utility.getFirstLower(className);
		JDefinedClass myClass= null;
		try {
			//TODO fix
			myClass = codeModel._class(generator.mainPackage+generator.applicationName.replace("serverTest","anggen")+".model."+className, ClassType.ENUM);
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		OracleNamingStrategy namingStrategy = new OracleNamingStrategy();
		for (EnumValue enumValue : enumEntity.getEnumValueList())
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
