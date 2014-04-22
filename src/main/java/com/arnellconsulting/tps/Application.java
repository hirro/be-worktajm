/*
 * Copyright 2014 Pivotal Software, Inc..
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



package com.arnellconsulting.tps;

import com.arnellconsulting.tps.config.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import java.io.IOException;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import javax.inject.Inject;

@EnableAutoConfiguration
@ComponentScan
public class Application {
   private final Logger log = LoggerFactory.getLogger(Application.class);
   @Inject
   private Environment env;

   /**
    * Initializes worktajm.
    * <p/>
    * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
    * <p/>
     * @throws java.io.IOException
    */
   @PostConstruct
   public void initApplication() throws IOException {
      if (env.getActiveProfiles().length == 0) {
         log.warn("No Spring profile configured, running with default configuration");
      } else {
         log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
      }
   }

   /**
    * Main method, used to run the application.
    * @param args
    */
   public static void main(String[] args) {
      SpringApplication app = new SpringApplication(Application.class);

      app.setShowBanner(false);

      SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);

      // Check if the selected profile has been set as argument.
      // if not the development profile will be added
      addDefaultProfile(app, source);

      // Fallback to set the list of liquibase package list
      addLiquibaseScanPackages();
      app.run(args);
   }

   /**
    * Set a default profile if it has not been set
    */
   private static void addDefaultProfile(SpringApplication app, SimpleCommandLinePropertySource source) {
      if (!source.containsProperty("spring.profiles.active")) {
         app.setAdditionalProfiles(Constants.SPRING_PROFILE_DEVELOPMENT);
      }
   }

   /**
    * Set the liquibases.scan.packages to avoid an exception from ServiceLocator
    * <p/>
    * See the following JIRA issue https://liquibase.jira.com/browse/CORE-677
    */
   private static void addLiquibaseScanPackages() {
      System.setProperty("liquibase.scan.packages",
                         "liquibase.change" + "," + "liquibase.database" + "," + "liquibase.parser" + ","
                         + "liquibase.precondition" + "," + "liquibase.datatype" + "," + "liquibase.serializer" + ","
                         + "liquibase.sqlgenerator" + "," + "liquibase.executor" + "," + "liquibase.snapshot" + ","
                         + "liquibase.logging" + "," + "liquibase.diff" + "," + "liquibase.structure" + ","
                         + "liquibase.structurecompare" + "," + "liquibase.lockservice" + "," + "liquibase.ext" + ","
                         + "liquibase.changelog");
   }
}
