/*
 * Copyright 2013 Arnell Consulting AB.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package com.arnellconsulting.tps.config;

import com.jolbox.bonecp.BoneCPDataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Properties;

import javax.annotation.Resource;

import javax.sql.DataSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author jiar
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {
        "com.arnellconsulting.tps.repository"
})
@EnableJpaRepositories(basePackages = "com.arnellconsulting.tps.repository")
public class PersistenceContext {
   protected static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
   protected static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
   protected static final String PROPERTY_NAME_DATABASE_URL = "db.url";
   protected static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
   private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
   private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
   private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
   private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
   private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
   private static final String PROPERTY_PACKAGES_TO_SCAN = "com.arnellconsulting.tps.model";
   private static final String DATABASE_DRIVER = "org.h2.Driver";
   private static final String DATABASE_URL = "jdbc:h2:mem:datajpa";
   private static final String DATABASE_USERNAME = "sa";
   private static final String DATABASE_PASSWORD = "";
   private static final String HIBERNATE_DIALECT = "org.hibernate.dialect.H2Dialect";
   private static final boolean HIBERNATE_FORMAT_SQL = true;
   private static final String HIBERNATE_HBM2DDL_AUTO = "create-drop";
   private static final String HIBERNATE_NAMING_STRATEGY = "org.hibernate.cfg.ImprovedNamingStrategy";
   private static final boolean HIBERNATE_SHOW_SQL = true;

   @Resource
   private Environment environment;

   //~--- methods -------------------------------------------------------------

   @Bean
   public DataSource dataSource() {
      BoneCPDataSource dataSource = new BoneCPDataSource();

      dataSource.setDriverClass(DATABASE_DRIVER);
      dataSource.setJdbcUrl(DATABASE_URL);
      dataSource.setUsername(DATABASE_USERNAME);
      dataSource.setPassword(DATABASE_PASSWORD);

      return dataSource;
   }

   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
      LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

      entityManagerFactoryBean.setDataSource(dataSource());
      entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      entityManagerFactoryBean.setPackagesToScan(PROPERTY_PACKAGES_TO_SCAN);

      Properties jpaProperties = new Properties();

      jpaProperties.put(PROPERTY_NAME_HIBERNATE_DIALECT, HIBERNATE_DIALECT);
      jpaProperties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, HIBERNATE_FORMAT_SQL);
      jpaProperties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, HIBERNATE_HBM2DDL_AUTO);
      jpaProperties.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY, HIBERNATE_NAMING_STRATEGY);
      jpaProperties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, HIBERNATE_SHOW_SQL);
      entityManagerFactoryBean.setJpaProperties(jpaProperties);

      return entityManagerFactoryBean;
   }

   @Bean
   public JpaTransactionManager transactionManager() {
      JpaTransactionManager transactionManager = new JpaTransactionManager();

      transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

      return transactionManager;
   }
}
