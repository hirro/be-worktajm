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

  describe('getTimeEntries', function () {

    it('should set the current time', function () {
      var dateString = '1998-10-08';
      timerService.setSelectedDate(dateString);
      expect(timerService.getSelectedDate()).toBe(dateString);
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
      timerService.reloadProject();
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

    xit('should start the timer', function () {
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

    xit('should stop the timer of the active user', function () {
      // Get the person
      var person;
      personService.getPerson().then(function (result) {
        person = result;
      });
      scope.$digest();
      expect(person).toBeDefined();
      expect(person).not.toBeNull();

      // Set the person as active
      var activePerson = person;
      activePerson.activeTimeEntry = timeEntries[0];
      httpBackend.whenPUT('http://localhost:8080/api/api/person/1').respond(activePerson);
      personService.setActiveTimeEntry(timeEntries[0]);
      scope.$digest();
      httpBackend.flush();
      expect(personService.getActiveProjectId()).toBe(projects[0].id);

      // Stop the active timer
      person.activeTimeEntry = timeEntries[0];
      timerService.stopTimer();
      scope.$digest();
      httpBackend.flush();
      expect(personService.getActiveProjectId()).toBe(-1);
    });
  });    
});
