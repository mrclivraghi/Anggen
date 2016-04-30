import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import it.Application;
import it.anggen.generation.BeanToDBConverter;
import it.anggen.model.entity.Entity;
import it.anggen.model.relationship.Relationship;
import it.anggen.repository.relationship.RelationshipRepository;
import it.anggen.service.relationship.RelationshipService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=Application.class)
public class TestLazy {
	
	
	@Autowired
	RelationshipService relationshipRepository;
	
	public TestLazy()
	{
		
	}
	
	@Test
	@Transactional
	public void createDbEntities()
	{
		Date before = new Date();
		List<Relationship> relList = relationshipRepository.findById(10152L);
		//relList= relationshipRepository.findByRelationshipId(10152L);
		Entity target=relList.get(0).getEntityTarget();
		System.out.println(relList.get(0).getName()+"-"+target.getName());
		Date after = new Date();
		System.out.println("Passarono "+(after.getTime()-before.getTime())+"ms");
	}
}

