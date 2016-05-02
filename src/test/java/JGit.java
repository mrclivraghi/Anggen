import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
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
public class JGit {
	
	
	
	public JGit()
	{
		
	}
	
	@Test
	@Transactional
	public void createDbEntities()
	{
		try {
			Repository repo = new FileRepositoryBuilder()
								.setGitDir(new File(".git"))
								.build();
			
			// Get a reference
			Ref master = repo.getRef("master");

			// Get the object the reference points to
			ObjectId masterTip = master.getObjectId();

			// Rev-parse
			ObjectId obj = repo.resolve("HEAD^{tree}");

			// Load raw object contents
			ObjectLoader loader = repo.open(masterTip);
			loader.copyTo(System.out);

			// Create a branch
			RefUpdate createBranch1 = repo.updateRef("refs/heads/branch1");
			createBranch1.setNewObjectId(masterTip);
			createBranch1.update();

			// Delete a branch
			RefUpdate deleteBranch1 = repo.updateRef("refs/heads/branch1");
			deleteBranch1.setForceUpdate(true);
			deleteBranch1.delete();

			// Config
			Config cfg = repo.getConfig();
			String name = cfg.getString("user", null, "name");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

