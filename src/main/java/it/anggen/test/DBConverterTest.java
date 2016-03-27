package it.anggen.test;

import it.anggen.generation.BeanToDBConverter;
import it.AnggenApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
