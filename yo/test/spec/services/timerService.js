/*
  @licstart The following is the entire license notice for the 
            JavaScript code in this page.
  @source TBD

  Copyright (C) 2013 Jim Arnell.

  The JavaScript code in this page is free software: you can
  redistribute it and/or modify it under the terms of the GNU
  General Public License (GNU GPL) as published by the Free Software
  Foundation, either version 3 of the License, or (at your option)
  any later version.  The code is distributed WITHOUT ANY WARRANTY;
  without even the implied warranty of MERCHANTABILITY or FITNESS
  FOR A PARTICULAR PURPOSE.  See the GNU GPL for more details.

  As additional permission under GNU GPL version 3 section 7, you
  may distribute non-source (e.g., minimized or compacted) forms of
  that code without the copy of the GNU GPL normally required by
  section 4, provided you include this license notice and a URL
  through which recipients can access the Corresponding Source.

  @licend The above is the entire license notice
          for the JavaScript code in this page.  
*/


/*globals expect, afterEach, beforeEach, spyOn, describe, it, inject, _ */

'use strict';

describe('Service: TimerService', function () {

  // load the module
  beforeEach(module('tpsApp'));

  // API
  var httpBackend;
  var timerService, personService;
  var scope;

  // Test constants
  var projects = [
    { id: 301, name: 'Project A' },
    { id: 302, name: 'Project B' }];
  var timeEntries = [
    { id: 201, startTime: 0, endTime: 1381337488*1000, project: projects[0] },
    { id: 202, startTime: 0, endTime: 2 }];
  var persons = [
    { id: 1, username: 'User A', activeTimeEntry: null },
    { id: 2, username: 'User B' },
    { id: 3, username: 'User C', activeTimeEntry: timeEntries[0] }];

  // Inject the required services
  beforeEach(inject(function (TimerService, PersonService, $httpBackend, $rootScope) {
    timerService = TimerService;
    personService = PersonService;    
    httpBackend = $httpBackend;
    scope = $rootScope;
  }));  

  afterEach(function () {
    httpBackend.verifyNoOutstandingExpectation();
    httpBackend.verifyNoOutstandingRequest();
  });

  describe('getProjects - uninitialized', function() {

    it('should return empty list when project list has not been refreshProjected/initialized', function () {
      // Test setup
      spyOn(timerService, 'getProjects').andCallThrough();

      // Test
      var result = timerService.getProjects();

      // Make the requests go though
      scope.$digest();

      // Verifications
      expect(timerService.getProjects).toHaveBeenCalled();
      expect(result).toBeDefined();
      expect(result.length).toBe(0);
    });
  });

  describe('getProjects - initialized', function() {
    beforeEach(function () {
      httpBackend.whenGET('http://localhost:8080/api/api/project').respond(_.clone(projects));
      spyOn(timerService, 'getProjects').andCallThrough();
      spyOn(timerService, 'refreshProject').andCallThrough();
      spyOn(scope, '$broadcast').andCallThrough();
      timerService.refreshProject();
      // Must let the timerService process the refreshProject
      scope.$digest();
      httpBackend.flush();
      console.log('init list');
    });

    it('should return project list when project list has been refreshProjected/initialized', function () {
      var result = timerService.getProjects();

      // Verifications
      expect(timerService.refreshProject).toHaveBeenCalled();
      expect(timerService.getProjects).toHaveBeenCalled();
      expect(result).toBeDefined();
      expect(result).not.toBeNull();
      expect(result.length).toBe(2);
      expect(scope.$broadcast).toHaveBeenCalledWith('onProjectsRefreshed', result);
    });

    it('should get the project with the provided id', function () {
      var result = timerService.getProject(302);
      expect(result).not.toBeNull();
      expect(result).toBeDefined();
      expect(result.id).toBe(302);
    });

    it('should remove the project with the provided id', function () {
      var projectId = 302;
      // Assure project exists before removing it
      var projectToBeRemoved = timerService.getProject(projectId);
      expect(projectToBeRemoved.id).toBe(projectId);
      // Remove project
      httpBackend.whenDELETE('http://localhost:8080/api/api/project/302').respond(201);
      timerService.removeProject(projectToBeRemoved);
      // Make the requests go though
      scope.$digest();
      httpBackend.flush();
      // Verify it is gone      
      var negative = timerService.getProject(projectId);
      expect(negative).toBeUndefined();
    });

    it('should update the provided project', function () {
      var projectId = 302;
      // First get the project
      var project = timerService.getProject(projectId);
      expect(project.id).toBe(projectId);
      // Update it
      httpBackend.whenPUT('http://localhost:8080/api/api/project/302').respond();
      timerService.updateProject(project);
      // Make the requests go though
      scope.$digest();
      httpBackend.flush();
      // Verify update?
    });

    it('should create a new project when project has no id', function () {
      var project = { name: 'New project' };
      // Create the proejct
      httpBackend.whenPOST('http://localhost:8080/api/api/project').respond(projects[1]);
      timerService.updateProject(project);
      // Make the requests go though
      scope.$digest();
      httpBackend.flush();
      // Verify it now was created  
      expect(scope.$broadcast).toHaveBeenCalledWith('onProjectUpdated', projects[1]);
    });

    it('should mark the project as active', function () {
      var projectId = 302;
      var project = timerService.getProject(projectId);
      expect(project.id).toBe(projectId);
      expect(project.active).toBe(false);
      // The test
      timerService.setActive(project, true);
      expect(project.active).toBe(true);
    });
  });

  describe('getTimeEntries', function () {

    it('should get the currenly logged in person', function () {      

      // Test setup
      httpBackend.whenGET('http://localhost:8080/api/api/timeEntry').respond([
        { id: '1', startTime: '0', endTime: '1' }
      ]);
      spyOn(timerService, 'getTimeEntries').andCallThrough();

      // Test
      var timeEntries = null;
      timerService.getTimeEntries().then(function (result) {
        timeEntries = result;
      });

      // Make the requests go though
      scope.$digest();
      httpBackend.flush();
      expect(timerService.getTimeEntries).toHaveBeenCalled();
      expect(timeEntries).not.toBeNull();
      expect(timeEntries).toBeDefined();
      expect(timeEntries.length).toBe(1);
    });

    it('should set the current time', function () {
      var dateString = '1998-10-08';
      timerService.setSelectedDate(dateString);
      expect(timerService.getSelectedDate()).toBe(dateString);
    });
  });

  describe('getTimeEntryById', function() {
    it('should return the time entry with the provided id', function() {
      httpBackend.whenGET('http://localhost:8080/api/api/timeEntry').respond(timeEntries);      
      timerService.getTimeEntries().then(function () {
        console.log('Promise fulfilled, got time entry');
      });
      // Make the requests go though
      scope.$digest();
      httpBackend.flush();
      // The test
      var timeEntry = timerService.getTimeEntryById(201);
      expect(timeEntry).toBeDefined();
      expect(timeEntry.id).toBe(201);
    });
  });

  describe('getEndTime', function() {
    it('should return in progress if time is undefined/null', function () {
      var timeString = timerService.getEndTime({ id: 1});
      expect(timeString).toBe('In Progress');
    });
    it('should output the end time as end time if defined', function () {
      var timeString = timerService.getEndTime(timeEntries[0]);
      expect(timeString).toBe('2013-10-09');
    });
  });

  describe('removeTimeEntry', function () {
    it('should remove the provided time entry', function () {
      httpBackend.whenGET('http://localhost:8080/api/api/timeEntry').respond(timeEntries);      
      timerService.getTimeEntries().then(function () {
        console.log('Promise fulfilled, got time entry');
      });
      // Make the requests go though
      scope.$digest();
      httpBackend.flush();
      // Verify the entry exists
      var timeEntry = timerService.getTimeEntryById(201);
      expect(timeEntry).toBeDefined();
      expect(timeEntry.id).toBe(201);
      // Remove time entry
      timerService.removeTimeEntry(timeEntry);
      // Make the requests go though
      httpBackend.whenDELETE('http://localhost:8080/api/api/timeEntry/201').respond();
      scope.$digest();
      httpBackend.flush();
      // Verify its gone
      timeEntry = timerService.getTimeEntryById(201);
      expect(timeEntry).not.toBeDefined();
    });
  });

  describe('Timer tests', function () {
    beforeEach(function () {
      // Prereq 1 = Person must be loaded
      var person = null;
      httpBackend.whenGET('http://localhost:8080/api/api/person/1').respond(persons[0]);
      personService.getPerson().then(function (result) {
        person = result;
      });
      scope.$digest();
      httpBackend.flush();
      expect(person.username).toBe('User A');

      // Prereq 2 = Projects must be loaded
      httpBackend.whenGET('http://localhost:8080/api/api/project').respond(_.clone(projects));
      spyOn(scope, '$broadcast').andCallThrough();
      timerService.refreshProject();
      scope.$digest();
      httpBackend.flush();

       // Prereq 3 = TimeEntries must be loaded
      var timeEntries = null;
      timerService.getTimeEntries().then(function (result) {
        timeEntries = result;
      });
      httpBackend.whenGET('http://localhost:8080/api/api/timeEntry').respond([
        { id: '1', startTime: '0', endTime: '1' }
      ]);
      scope.$digest();
      httpBackend.flush();
      expect(timeEntries.length).toBe(1);
    });

    it('should start the timer', function () {
      // Start the timer
      var person = personService.getPerson();
      timerService.startTimer(projects[0]);

      // Make the requests go though and validate
      httpBackend.whenPOST('http://localhost:8080/api/api/timeEntry').respond(timeEntries[0]);
      httpBackend.whenPUT('http://localhost:8080/api/api/person/1').respond(person[0]);
      scope.$digest();
      httpBackend.flush();
      expect(scope.$broadcast).toHaveBeenCalledWith('onTimeEntryUpdated', timeEntries[0]);
      expect(scope.$broadcast).toHaveBeenCalledWith('onProjectUpdated', projects[0]);

      // Stop the timer
      //timerService.stopTimer(projects[0]);
    });

    it('should not stop the timer when there are no active projects', function () {
      timerService.stopTimer(projects[0], persons[0]);
    });

    it('should handle null value for person gracefully', function () {
      timerService.stopTimer(projects[0], null);
    });

    it('should handle null value for project gracefully', function () {
      timerService.stopTimer(null, persons[0]);
    });

    it('should stop the timer of the active user', function () {
      var person = personService.getPerson();
      expect(person).toBeDefined();
      expect(person).not.toBeNull();
      person.activeTimeEntry = timeEntries[0];
      timerService.stopTimer(projects[0]);
      scope.$digest();
      httpBackend.flush();
    });

  });    
});
