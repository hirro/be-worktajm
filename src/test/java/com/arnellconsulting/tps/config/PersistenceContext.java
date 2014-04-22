/*
 * Copyright 2013 Jim Arnell
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.arnellconsulting.tps.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Petri Kainulainen
 * @author Jim Arnell
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {
   "com.arnellconsulting.tps.repository"
})
@EnableJpaRepositories(basePackages = "com.arnellconsulting.tps.repository")
public class PersistenceContext {

   private static final String[] PROPERTY_PACKAGES_TO_SCAN = {
      "com.arnellconsulting.tps.model"
   };

   protected static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
   protected static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
   protected static final String PROPERTY_NAME_DATABASE_URL = "db.url";
   protected static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

   private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
   private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
   private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
   private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
   private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";

   @Resource
   private Environment env;

   //~--- methods -------------------------------------------------------------
   @Bean
   public DataSource dataSource() {
      HikariDataSource dataSource = new HikariDataSource();

      return dataSource;
   }

   @Bean
   public JpaTransactionManager transactionManager() {
      JpaTransactionManager transactionManager = new JpaTransactionManager();

      transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

      return transactionManager;
   }

   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
      LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

      entityManagerFactoryBean.setDataSource(dataSource());
      entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      entityManagerFactoryBean.setPackagesToScan(PROPERTY_PACKAGES_TO_SCAN);

      Properties jpaProperties = new Properties();
      jpaProperties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
      jpaProperties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
      jpaProperties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
      jpaProperties.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY));
      jpaProperties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));

      entityManagerFactoryBean.setJpaProperties(jpaProperties);

      return entityManagerFactoryBean;
   }

}
