package it.anggen.test;

import it.anggen.generation.Generator;
import it.AnggenApplication;
import it.anggen.repository.entity.ProjectRepository;
import it.anggen.repository.field.EnumFieldRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=AnggenApplication.class)
public class JsDbGenerationTest {

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	EnumFieldRepository enumFieldRepository;
	
	@Autowired
	Generator generator;
	
	
	public JsDbGenerationTest() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void createJs()
	{
		try {
			generator.generate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
