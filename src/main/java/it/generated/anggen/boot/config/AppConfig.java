package it.generated.anggen.boot.config;


import java.util.Properties;

import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableAutoConfiguration
@ComponentScan({ "it.generated.anggen.*" })
public class AppConfig {
	

	
	 @Value("${hibernate.format_sql}")
	 private String formatSql;
	
	 @Value("${hibernate.show_sql}")
	 private String showSql;
	 
	 @Value("${hibernate.dialect}")
	 private String dialect;
	 
	 @Value("${hibernate.hbm2ddl.auto}")
	 private String mode;
	 
	 @Value("${hibernate.naming-strategy}")
	 private String namingStrategy;

	 @Value("${datasource.driver.class.name}")
	 private String driverClassName;
	 
	 @Value("${datasource.jdbc}")
	 private String jdbcString;
	 
	 @Value("${datasource.url}")
	 private String dbUrl;
	 
	 @Value("${datasource.port}")
	 private String dbPort;
	 
	 @Value("${datasource.db.name}")
	 private String dbName;
	 
	 @Value("${datasource.username}")
	 private String dbUser;
	 
	 @Value("${datasource.password}")
	 private String dbPassword;


	 @Bean
	 public SessionFactory sessionFactory() {
		 LocalSessionFactoryBuilder builder = 
			new LocalSessionFactoryBuilder(dataSource());
                builder.scanPackages("it.generated.anggen")
                      .addProperties(getHibernateProperties());

              return builder.buildSessionFactory();
        }

	private Properties getHibernateProperties() {
                Properties prop = new Properties();
                prop.put("hibernate.format_sql", formatSql);
                prop.put("hibernate.show_sql", showSql);
                prop.put("hibernate.dialect", dialect);
                prop.put("hibernate.hbm2ddl.auto", mode);
                //spring.jpa.hibernate.naming-strategy=org.hibernate.cfg.UppercaseNamingStrategy
                prop.put("hibernate.naming-strategy",namingStrategy);
                return prop;
        }
	
	@Bean(name = "dataSource")
	public DataSource dataSource() {
		
		BasicDataSource ds = new BasicDataSource();
	        ds.setDriverClassName(driverClassName);
		ds.setUrl(jdbcString+"://"+dbUrl+":"+dbPort+"/"+dbName);
		ds.setUsername(dbUser);
		ds.setPassword(dbPassword);
		return ds;
	}
	
	//Create a transaction manager
/*	@Bean
        public HibernateTransactionManager txManager() {
                return new HibernateTransactionManager(sessionFactory());
        }*/
		
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver 
                             = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp/anggen/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	
}