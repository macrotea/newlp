package com.lesso.newlp.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

/**
 * UserDTO: Sean
 * Date: 7/2/13
 * Time: 11:11 AM
 */
@Configuration
@EnableJpaRepositories("com.lesso.newlp.*.repository")
@EnableTransactionManagement
public class PersistenceConfig implements TransactionManagementConfigurer {

    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    @Value("${jpa.database}")
    private Database database;
    @Value("${jpa.databasePlatform}")
    private String databasePlatform;
    @Value("${jpa.generateDdl}")
    private boolean generateDdl;
    @Value("${jpa.showSql}")
    private boolean showSql;


    @Value("${hibernate.dialect}")
    private String dialect;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;
    @Value("${hibernate.show_sql}")
    private String show_sql;
    @Value("${hibernate.format_sql}")
    private String format_sql;

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        try {
            ds.setDriverClass(driver);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        ds.setJdbcUrl(url);
        ds.setUser(username);
        ds.setPassword(password);
        ds.setIdleConnectionTestPeriod(60);
        ds.setInitialPoolSize(10);
        ds.setMinPoolSize(10);
        ds.setMaxPoolSize(100);
        ds.setMaxStatements(50);

//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(driver);
//        dataSource.setUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
        return ds;
    }

    @Bean
    public TransactionAwareDataSourceProxy dataSourceProxy(){
        return new TransactionAwareDataSourceProxy(dataSource());
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(database);
        jpaVendorAdapter.setDatabasePlatform(databasePlatform);
        jpaVendorAdapter.setGenerateDdl(generateDdl);
        jpaVendorAdapter.setShowSql(showSql);

//        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
//        validatorFactoryBean.setProviderClass(org.hibernate.validator.HibernateValidator.class);

        Properties jpaProperties = new Properties();
//        jpaProperties.put(AvailableSettings.VALIDATION_FACTORY, validatorFactoryBean);
        jpaProperties.put(Environment.DIALECT, dialect);
        jpaProperties.put(Environment.HBM2DDL_AUTO, hbm2ddlAuto);
        jpaProperties.put(Environment.SHOW_SQL, show_sql);
        jpaProperties.put(Environment.FORMAT_SQL, format_sql);


        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("com.lesso.newlp");
        factoryBean.setMappingResources("META-INF/orm.xml");
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setJpaProperties(jpaProperties);
//        factoryBean.setPersistenceUnitManager(new MergingPersistenceUnitManager());
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(){
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        return transactionManager;
    }

    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager();
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator(){
        return new HibernateExceptionTranslator();
    }
}
