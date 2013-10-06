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
    { id: 201, startTime: 0, endTime: 2, project: projects[0] },
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
  });

  describe('getTimeEntryById', function() {
    it('should return the time entry with the provided id', function() {
      // TBD
    });
  });

  describe('getEndTime', function() {
    // XXX: This should be a controller function...
  });
});
