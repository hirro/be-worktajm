[![Build Status](https://travis-ci.org/hirro/be-worktajm.png?branch=master)](https://travis-ci.org/hirro/be-worktajm)
[![Coverage Status](https://coveralls.io/repos/hirro/be-worktajm/badge.png)](https://coveralls.io/r/hirro/be-worktajm)
[![Codeship](https://www.codeship.io/projects/acf7fa20-b204-0131-9b79-66f8eb76dc49/status)

# WorkTajm
Hosted at: http://www.worktajm.com

***

## Work in progress
Version 1.0 is not yet scheduled.

## Description
Worktajm is a simple time reporting tool that helps you keep track of your work time.

The application is ideal for small contractors with features as automatic invoice generation.

The application is free of charge with the exception of the iPhone and Android clients.
If you like it send some money to charity.


## Description

This application is divided into two git projects, a backend and a frontend.

The backend server is written in Java and distributed as a war file.

The front end is a AngularJS single page application.

	
## Backend
Git: [be-worktajm](https://github.com/hirro/be-worktajm)

The backend is written mainly in Java using the bleeding edge components.

### System requirements
* Tomcat 7.42+, may be moved to 8 soon.
* JRE 7
* Mysql/MariaDB

### Stack
* Java 8, servlet 3.1.0
* Spring Framework 4.x
 * [Spring](www.spring.org)  Spring Data (JPA)
 * [Spring](www.spring.org)  Spring Security
* Jackson for the REST API ( [API version 1](https://github.com/hirro/be-worktajm/blob/master/api.md) )
* Liquibase for database change management.
* Lombok (no longer used)
* Maven 3

## Front end
Git: [yo-worktajm](https://github.com/hirro/yo-worktajm)

### System requirements

### Stack
* [Yeoman 1.0](http://yeoman.io/)
 * [Grunt.js](http://gruntjs.com/)
 * bower
 * angular generators
* [AngularJS 1.2.11](http://www.angularjs.org/) as foundation.
* [Bootstrap 3](http://getbootstrap.com/) as default stylesheet.
* [Restangular](https://github.com/mgonto/restangular) for communicating with backend using REST.
* [toastr](https://github.com/CodeSeven/toastr) to send the user notifications in Growl like fashion.
* [Jasmine](http://pivotal.github.com/jasmine/) for wrting unit tests
* [Karma Test Runner](http://karma-runner.github.io/0.8/index.html) (integrated with the Grunt.js build)

### Continious Integration

* [Travis-CI](https://travis-ci.org/) 






