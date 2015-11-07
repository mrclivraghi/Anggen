package it.polimi.boot.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan({ "it.polimi.*" })
@Import({ SecurityConfig.class })
public class AppConfig {

        @Bean
        public SessionFactory sessionFactory() {
                LocalSessionFactoryBuilder builder = 
			new LocalSessionFactoryBuilder(dataSource());
                builder.scanPackages("it.polimi")
                      .addProperties(getHibernateProperties());

              return builder.buildSessionFactory();
        }

	private Properties getHibernateProperties() {
                Properties prop = new Properties();
                prop.put("hibernate.format_sql", "true");
                prop.put("hibernate.show_sql", "true");
                prop.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                prop.put("hibernate.hbm2ddl.auto", "update");
                //spring.jpa.hibernate.naming-strategy=org.hibernate.cfg.UppercaseNamingStrategy
                //prop.put("hibernate.naming-strategy","org.hibernate.cfg.UppercaseNamingStrategy");
                return prop;
        }
	
	@Bean(name = "dataSource")
	public DataSource dataSource() {
		
		BasicDataSource ds = new BasicDataSource();
	        ds.setDriverClassName("org.postgresql.Driver");
		ds.setUrl("jdbc:postgresql://localhost:5432/test_db");
		ds.setUsername("postgres");
		ds.setPassword("postgres");
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
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
}