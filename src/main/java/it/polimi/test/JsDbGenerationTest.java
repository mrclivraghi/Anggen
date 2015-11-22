package it.polimi.test;

import java.io.File;
import java.util.ArrayList;

import it.polimi.Application;
import it.polimi.generation.AngularGenerator;
import it.polimi.generation.Generator;
import it.polimi.generation.HtmlGenerator;
import it.polimi.generation.JsGenerator;
import it.polimi.model.domain.Entity;
import it.polimi.repository.domain.EntityRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import antlr.collections.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=Application.class)
public class JsDbGenerationTest {

	@Autowired
	EntityRepository entityRepository;
	
	public JsDbGenerationTest() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void createJs()
	{
		Entity mountain = entityRepository.findByEntityId((long)10).get(0);
		System.out.println(mountain.getName());
		//JsGenerator jsGenerator = new JsGenerator(entity, true, null, false);
		File file = new File("");
		//jsGenerator.saveJsToFile(file.getAbsolutePath()+"/src/main/webapp/js/angular/");
		//AngularGenerator angularGenerator = new AngularGenerator(entity, true, new ArrayList<Entity>());
		java.util.List<Entity> entityList = entityRepository.findByEntityIdAndNameAndFieldAndRelationship(null, null, null, null);
		entityList.add(mountain);
		Generator generator = new Generator(entityList);
		generator.generate();
		
	}

}
