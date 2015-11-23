package it.polimi.controller;

import java.io.File;
import java.util.List;

import it.polimi.generation.Generator;
import it.polimi.model.domain.Entity;
import it.polimi.model.domain.EnumField;
import it.polimi.model.domain.Relationship;
import it.polimi.repository.EntityRepository;
import it.polimi.repository.EnumFieldRepository;
import it.polimi.service.RelationshipService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/generation")
public class GenerationController {
	@Autowired
	EntityRepository entityRepository;
	
	@Autowired
	EnumFieldRepository enumFieldRepository;
	
    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
    	//Entity mountain = entityRepository.findByEntityId((long)80).get(0);
		//System.out.println(mountain.getName());
		//JsGenerator jsGenerator = new JsGenerator(entity, true, null, false);
		//File file = new File("");
		//jsGenerator.saveJsToFile(file.getAbsolutePath()+"/src/main/webapp/js/angular/");
		//AngularGenerator angularGenerator = new AngularGenerator(entity, true, new ArrayList<Entity>());
    	List<Entity> entityList = entityRepository.findByEntityIdAndNameAndFieldAndRelationshipAndEnumField(null, null, null, null, null);
		List<EnumField> enumFieldList = enumFieldRepository.findByEnumFieldIdAndNameAndEnumValueAndEntity(null, null, null, null);
		
		Generator generator = new Generator(entityList,enumFieldList);
		generator.generate();
		return "generation";
    }

}
