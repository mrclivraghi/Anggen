import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import it.Application;
import it.anggen.generation.BeanToDBConverter;
import it.anggen.model.entity.Entity;
import it.anggen.model.field.Field;
import it.anggen.model.relationship.Relationship;
import it.anggen.repository.relationship.RelationshipRepository;
import it.anggen.service.entity.EntityService;
import it.anggen.service.relationship.RelationshipService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=Application.class)
//@TransactionConfiguration(defaultRollback=false)
@Rollback(false)
public class TestCreateUpdateTimestamp {
	
	
	@Autowired
	EntityService entityService;
	
	public TestCreateUpdateTimestamp()
	{
		
	}
	
	@Test
	@Transactional
	public void createDbEntities()
	{
		List<Entity> entityList=entityService.findById(2L);
		Entity test = entityList.get(0);
		Field field = test.getFieldList().get(0);
		field.setEntity(null);
		test.setCache(true);
		test=entityService.update(test);
		test= new Entity();
		test.setName("test");
		entityService.insert(test);
	}
	
	@Test
	public void testAfter()
	{
		Date now = new Date();
		if (now.after(null))
			System.out.println("after");
	}
}

