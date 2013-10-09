/* globals expect, it, afterEach, beforeEach, spyOn, describe, inject */

'use strict';

describe('Service: TimeEntryService', function () {

  // load the service's module
  beforeEach(module('tpsApp'));

  // API
  var httpBackend;
  var service;
  var scope;

  // Test constants
  var projects = [
    { id: 301, name: 'Project A', rate: null }];
  var timeEntries = [
    { id: 201, startTime: 0, endTime: 1381337488*1000, project: projects[0] },
    { id: 202, startTime: 0, endTime: 2 }];
  var persons = [
    { id: 1, username: 'User A', activeTimeEntry: null },
    { id: 2, username: 'User B' },
    { id: 3, username: 'User C', activeTimeEntry: timeEntries[0] }];

  // Inject the person service
  beforeEach(inject(function (TimeEntryService, $httpBackend, $rootScope) {
    service = TimeEntryService;
    httpBackend = $httpBackend;
    scope = $rootScope;
  }));  

  afterEach(function () {
    httpBackend.verifyNoOutstandingExpectation();
    httpBackend.verifyNoOutstandingRequest();
  });

  describe('getTimeEntries', function () {

    it('should get the currenly logged in person', function () {      

      // Test setup
      httpBackend.whenGET('http://localhost:8080/api/api/timeEntry').respond([
        { id: '1', startTime: '0', endTime: '1' }
      ]);
      spyOn(service, 'getTimeEntries').andCallThrough();

      // Test
      var timeEntries = null;
      service.getTimeEntries().then(function (result) {
        timeEntries = result;
      });

      // Make the requests go though
      scope.$digest();
      httpBackend.flush();
      expect(service.getTimeEntries).toHaveBeenCalled();
      expect(timeEntries).not.toBeNull();
      expect(timeEntries).toBeDefined();
      expect(timeEntries.length).toBe(1);
    });

    it('should set the current time', function () {
      var dateString = '1998-10-08';
      service.setSelectedDate(dateString);
      expect(service.getSelectedDate()).toBe(dateString);
    });
  });

  describe('getTimeEntryById', function() {
    it('should return the time entry with the provided id', function() {
      httpBackend.whenGET('http://localhost:8080/api/api/timeEntry').respond(timeEntries);      
      service.getTimeEntries().then(function () {
        console.log('Promise fulfilled, got time entry');
      });
      // Make the requests go though
      scope.$digest();
      httpBackend.flush();
      // The test
      var timeEntry = service.getTimeEntryById(201);
      expect(timeEntry).toBeDefined();
      expect(timeEntry.id).toBe(201);
    });
  });

  describe('getEndTime', function() {
    it('should return in progress if time is undefined/null', function () {
      var timeString = service.getEndTime({ id: 1});
      expect(timeString).toBe('In Progress');
    });
    it('should output the end time as end time if defined', function () {
      var timeString = service.getEndTime(timeEntries[0]);
      expect(timeString).toBe('2013-10-09');
    });
  });

  describe('removeTimeEntry', function () {
    it('should remove the provided time entry', function () {
      httpBackend.whenGET('http://localhost:8080/api/api/timeEntry').respond(timeEntries);      
      service.getTimeEntries().then(function () {
        console.log('Promise fulfilled, got time entry');
      });
      // Make the requests go though
      scope.$digest();
      httpBackend.flush();
      // Verify the entry exists
      var timeEntry = service.getTimeEntryById(201);
      expect(timeEntry).toBeDefined();
      expect(timeEntry.id).toBe(201);
      // Remove time entry
      service.removeTimeEntry(timeEntry);
      // Make the requests go though
      httpBackend.whenDELETE('http://localhost:8080/api/api/timeEntry/201').respond();
      scope.$digest();
      httpBackend.flush();
      // Verify its gone
      timeEntry = service.getTimeEntryById(201);
      expect(timeEntry).not.toBeDefined();
    });
  });

});
