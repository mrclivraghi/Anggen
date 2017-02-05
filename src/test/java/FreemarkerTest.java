import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import freemarker.template.Version;
import it.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=Application.class)
public class FreemarkerTest {
	
	@Test
	public void createFromTemplate() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException
	{
		Configuration cfg = new Configuration(new Version("2.3.25-incubating"));

		cfg.setClassForTemplateLoading(FreemarkerTest.class, "/");
		cfg.setDefaultEncoding("UTF-8");

		Template template = cfg.getTemplate("template/test.ftl");

		Map<String, Object> templateData = new HashMap<>();
		templateData.put("msg", "Today is a beautiful day");

		try (StringWriter out = new StringWriter()) {

			template.process(templateData, out);
			System.out.println(out.getBuffer().toString());

			out.flush();
		}
	}
}
