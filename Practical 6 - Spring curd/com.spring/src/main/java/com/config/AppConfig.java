package com.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@ComponentScan(basePackages = "com")   // replaces <ctx:component-scan base-package="com"/>
@EnableTransactionManagement          // replaces <tx:annotation-driven/>
public class AppConfig {

    // Equivalent of <bean id="myDataSource" ...>
    @Bean(destroyMethod = "close")
    public DataSource myDataSource() throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/parul");
        dataSource.setUser("root");
        dataSource.setPassword("password");
        return dataSource;
    }

    // Equivalent of <bean id="sessionFactory" ...>
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        try {
            // Corrected method call
            sessionFactory.setDataSource(myDataSource());
        } catch (Exception e) {
            e.printStackTrace();
        }
        sessionFactory.setPackagesToScan("com.pojo");
        
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "false");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create");
        
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    // Equivalent of <bean id="myTransactionManager" ...>
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }
}


/**
 * @Configuration → replaces <beans> root.
 * 
 * @ComponentScan("com") → replaces  <ctx:component-scan base-package="com"/>.
 * 
 * @EnableTransactionManagement → replaces <tx:annotation-driven/>.
 * 
 * @Bean methods → replace <bean> tags.
 * 
 * Properties for Hibernate are added via Properties object instead of <props> in XML.
 * 
 * The datasource lifecycle (destroy-method="close") is preserved with @Bean(destroyMethod="close").
 */
