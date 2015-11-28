package it.polimi.test;

import java.util.List;

import it.polimi.Application;
import it.polimi.generation.BeanToDBConverter;
import it.polimi.generation.Generator;
import it.polimi.model.domain.Entity;
import it.polimi.model.domain.EntityGroup;
import it.polimi.model.domain.EnumField;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=Application.class)
public class DBConverterTest {

	@Autowired
	BeanToDBConverter beanToDBConverter;
	
	public DBConverterTest() {
		// TODO Auto-generated constructor stub
	}

	@Test
	public void createDbEntities()
	{
		beanToDBConverter.convert(Generator.modelPackage);
	}
}
