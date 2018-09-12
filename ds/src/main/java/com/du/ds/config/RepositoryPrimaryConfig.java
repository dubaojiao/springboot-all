package com.du.ds.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

@SuppressWarnings( "all" )
@Configuration
@EnableTransactionManagement //开启事务支持
@EnableJpaRepositories(//用于取代xml形式的配置文件
        entityManagerFactoryRef="entityManagerFactoryPrimary",
        transactionManagerRef="transactionManagerPrimary",
        basePackages= {"com.du.ds.mapper.one.rep"})//设置repository所在位置
public class RepositoryPrimaryConfig {
    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("primaryDS")
    private DataSource primaryDS;

    @Bean(name = "entityManagerPrimary")
    @Primary
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryPrimary( builder ).getObject( ).createEntityManager( );
    }

    @Bean(name = "entityManagerFactoryPrimary")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource( primaryDS )
                .properties(getVendorProperties())
                .packages( "com.du.ds.mapper.one.domain" ) //设置实体类所在位置
                .persistenceUnit( "primaryPersistenceUnit" )
                .build( );
    }

    private Map<String, Object> getVendorProperties() {
        return jpaProperties.getHibernateProperties( new HibernateSettings() );
    }

    @Bean(name = "transactionManagerPrimary")
    @Primary
    PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager( entityManagerFactoryPrimary( builder ).getObject( ) );
    }
}
