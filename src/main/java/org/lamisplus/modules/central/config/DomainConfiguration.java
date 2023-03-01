package org.lamisplus.modules.central.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.sql.DataSource;

@RequiredArgsConstructor
@Slf4j
@Configuration
@EnableJpaRepositories(
        transactionManagerRef = "syncTransactionManger",
        basePackages = {"org.lamisplus.modules.central.repository"}
)
public class DomainConfiguration {

    private final DataSource dataSource;

    @PersistenceUnit
    private  final EntityManagerFactory entityManagerFactory;


    @Bean(name = "syncTransactionManger")
    @Primary
    public TransactionManager transactionManager(){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setDataSource(dataSource);
        jpaTransactionManager.setEntityManagerFactory (entityManagerFactory);
        return jpaTransactionManager;
    }
}
