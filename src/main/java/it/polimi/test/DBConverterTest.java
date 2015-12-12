package it.polimi.test;

import java.util.ArrayList;
import java.util.List;

import it.polimi.AnggenApplication;
import it.polimi.generation.BeanToDBConverter;
import it.polimi.generation.Generator;
import it.polimi.model.entity.Entity;
import it.polimi.model.entity.EntityGroup;
import it.polimi.model.entity.Project;
import it.polimi.model.field.EnumField;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=AnggenApplication.class)
public class DBConverterTest {

	@Autowired
	BeanToDBConverter beanToDBConverter;
	
	public DBConverterTest() {
		// TODO Auto-generated constructor stub
	}

	@Test
	public void createDbEntities()
	{
		beanToDBConverter.convert();
	}
}
