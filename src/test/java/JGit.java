import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffConfig;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.FollowFilter;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.junit.Test;
import org.junit.runner.RunWith;
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

	private static AbstractTreeIterator prepareTreeParser(Repository repository, String objectId) throws IOException,
	MissingObjectException,
	IncorrectObjectTypeException {
		// from the commit we can build the tree which allows us to construct the TreeParser

		RevWalk walk = new RevWalk(repository);

		RevCommit commit = walk.parseCommit(ObjectId.fromString(objectId));
		RevTree tree = walk.parseTree(commit.getTree().getId());

		CanonicalTreeParser oldTreeParser = new CanonicalTreeParser();
		ObjectReader oldReader = repository.newObjectReader();
		oldTreeParser.reset(oldReader, tree.getId());

		walk.dispose();

		return oldTreeParser;
	}

	private static DiffEntry diffFile(Repository repo, String oldCommit,
			String newCommit, String path) throws IOException, GitAPIException {
		Config config = new Config();
		config.setBoolean("diff", null, "renames", true);
		DiffConfig diffConfig = config.get(DiffConfig.KEY);
		Git git = new Git(repo);
		List<DiffEntry> diffList = git.diff().
				setOldTree(prepareTreeParser(repo, oldCommit)).
				setNewTree(prepareTreeParser(repo, newCommit)).
				setPathFilter(FollowFilter.create(path, diffConfig)).
				call();
		if (diffList.size() == 0)
			return null;
		if (diffList.size() > 1)
			throw new RuntimeException("invalid diff");
		return diffList.get(0);
	}




	@Test
	@Transactional
	public void createDbEntities()
	{
		try {
			
			File test = new File("");
			
			Repository repo = new FileRepositoryBuilder()
					.setGitDir(new File(test.getAbsolutePath()+"/.git"))
					.build();

			
			// Get a reference
			Ref develop = repo.getRef("develop");

			// Get the object the reference points to
			ObjectId developTip = develop.getObjectId();

			// Rev-parse
			//ObjectId obj = repo.resolve("HEAD^{tree}");

			// Load raw object contents
			//ObjectLoader loader = repo.open(masterTip);
			//loader.copyTo(System.out);

			// Create a branch
			
			Ref testBranch= repo.getRef("refs/heads/feature/testGit");
			if (testBranch==null)
			{

				RefUpdate createBranch1 = repo.updateRef("refs/heads/feature/testJGit");
				createBranch1.setNewObjectId(developTip);
				createBranch1.update();
				testBranch= repo.getRef("refs/heads/feature/testGit");
			}
			Git git = new Git(repo);
			

			
			
			try {
				git.checkout().setName(testBranch.getName()).call();
			} catch (GitAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			String firstCommit=testBranch.getObjectId().getName();
			

			File newTest = new File("test.txt");
			PrintWriter writer = new PrintWriter(newTest);
			writer.println("CIAO");
			writer.println("HELLA");
			writer.println("BONJOUR"+new Date());
			writer.close();
			//repo.updateRef(createBranch1.getNewObjectId()).update();
			System.out.println("1----"+git.getRepository().getBranch());
			//git.getRepository().updateRef(createBranch1.getNewObjectId().getName());
			System.out.println("2----"+git.getRepository().getBranch());
			
			try {
				//add file
				git.add().addFilepattern("test.txt").call();
				
				RevCommit lastCommit = git.commit().setMessage("new test txt").call();
				
				git.checkout().setName("refs/heads/feature/JGit").call();
				git.merge().setCommit(false).include(lastCommit).call();
				

				//DiffEntry diffEntry=diffFile(repo, firstCommit, lastCommit.getId().getName(), "test.txt");
				
				
			} catch (GitAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Delete a branch
			//RefUpdate deleteBranch1 = repo.updateRef("refs/heads/feature/testJGit");
			//deleteBranch1.setForceUpdate(true);
			//deleteBranch1.delete();

			// Config
			//Config cfg = repo.getConfig();
			//String name = cfg.getString("user", null, "name");


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

