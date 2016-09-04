import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.CaseFormat;

import it.Application;
import it.anggen.generation.BeanToDBConverter;
import it.anggen.generation.Generator;
import it.anggen.generation.postgresmeta.service.PostgresMetaService;
import it.anggen.model.entity.Entity;
import it.anggen.model.relationship.Relationship;
import it.anggen.repository.relationship.RelationshipRepository;
import it.anggen.service.relationship.RelationshipService;
import it.anggen.utils.OracleNamingStrategy;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=Application.class)
@Rollback(false)
public class MetaPostgresTest {

	@Autowired
	PostgresMetaService postgresMetaService;
	
	@Autowired
	Generator generator;

	public MetaPostgresTest()
	{

	}

	
	@Test
	public void testOracleNaming(){
		OracleNamingStrategy s = new OracleNamingStrategy();
		System.out.println(s.tableName("nomeTabella"));
		System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "nome_tabella"));
	}
	
	
	@Test
	public void testAfter()
	{
		try {
			generator.init();
			postgresMetaService.generateFromSchema("sp");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

