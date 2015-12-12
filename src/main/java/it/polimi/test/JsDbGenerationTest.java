package it.polimi.test;

import java.io.File;
import java.util.ArrayList;

import it.polimi.AnggenApplication;
import it.polimi.generation.AngularGenerator;
import it.polimi.generation.EntityGenerator;
import it.polimi.generation.Generator;
import it.polimi.generation.HtmlGenerator;
import it.polimi.generation.JsGenerator;
import it.polimi.model.entity.Entity;
import it.polimi.model.entity.EntityGroup;
import it.polimi.model.entity.Project;
import it.polimi.model.field.EnumField;
import it.polimi.repository.entity.ProjectRepository;
import it.polimi.repository.field.EnumFieldRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


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
