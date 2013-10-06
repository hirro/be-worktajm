'use strict';

describe('Service: ProjectService', function () {

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
    { id: 201, startTime: 0, endTime: 2, project: projects[0] }];
  var persons = [
    { id: 1, username: 'User A', activeTimeEntry: null },
    { id: 2, username: 'User B' },
    { id: 3, username: 'User C', activeTimeEntry: timeEntries[0] }];

  // Inject the person service
  beforeEach(inject(function (ProjectService, $httpBackend, $rootScope) {
    service = ProjectService;
    httpBackend = $httpBackend;
    scope = $rootScope;
  }));  

  afterEach(function () {
    httpBackend.verifyNoOutstandingExpectation();
    httpBackend.verifyNoOutstandingRequest();
  });

  describe('getAll', function() {

    describe('should return all defined projects belonging to the user`s company', function () {
      var projects = service.getAll();
      expect(projects).toBe(null);
    });
    describe('should return all defined projects belonging to the user`s company', function () {
      var projects = service.update();
      expect(projects).toBe(null);
    });

  });
});
