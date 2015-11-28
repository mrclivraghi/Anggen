package it.polimi.test;

import java.io.File;
import java.util.ArrayList;

import it.polimi.Application;
import it.polimi.generation.AngularGenerator;
import it.polimi.generation.EntityGenerator;
import it.polimi.generation.Generator;
import it.polimi.generation.HtmlGenerator;
import it.polimi.generation.JsGenerator;
import it.polimi.model.domain.Entity;
import it.polimi.model.domain.EntityGroup;
import it.polimi.model.domain.EnumField;
import it.polimi.repository.EntityGroupRepository;
import it.polimi.repository.EntityRepository;
import it.polimi.repository.EnumFieldRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=Application.class)
public class JsDbGenerationTest {

	@Autowired
	EntityRepository entityRepository;
	
	@Autowired
	EnumFieldRepository enumFieldRepository;
	
	@Autowired
	EntityGroupRepository entityGroupRepository;
	
	public JsDbGenerationTest() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void createJs()
	{
		List<Entity> entityList = entityRepository.findByEntityIdAndNameAndFieldAndRelationshipAndEnumFieldAndTabAndRestrictionEntityAndEntityGroup(null, null, null, null, null, null, null, null);
		List<EnumField> enumFieldList = enumFieldRepository.findByEnumFieldIdAndNameAndEnumValueAndEntityAndAnnotationAndTab(null, null, null, null, null, null);
		List<EntityGroup> entityGroupList=entityGroupRepository.findByEntityGroupIdAndNameAndEntityAndRestrictionEntityGroup(null, null, null, null);
		Generator generator = new Generator(entityGroupList,enumFieldList);
		generator.generate();
		
	}

}
