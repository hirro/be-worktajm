/* globals expect, it, afterEach, beforeEach, spyOn, describe, inject */

'use strict';

describe('Service: PersonService', function () {

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
  beforeEach(inject(function (PersonService, $httpBackend, $rootScope) {
    service = PersonService;
    httpBackend = $httpBackend;
    scope = $rootScope;
  }));  

  afterEach(function () {
    httpBackend.verifyNoOutstandingExpectation();
    httpBackend.verifyNoOutstandingRequest();
  });

  describe('getPerson', function () {

    it('should get the currenly logged in person', function () {

      // Test setup
      httpBackend.whenGET('http://localhost:8080/api/api/person/1').respond(persons[0]);
      spyOn(service, 'getPerson').andCallThrough();

      // Test
      var person = null;
      service.getPerson().then(function (result) {
        person = result;
      });

      // Make the requests go though
      scope.$digest();
      httpBackend.flush();

      expect(service.getPerson).toHaveBeenCalled();
      expect(person).toBeDefined();
      expect(person.id).toBe(1);
      expect(person.username).toBeDefined();
      expect(person.username).toBe('User A');
    });

    it('should fail gracefully when token has expired or is invalid', function () {
        // Test setup
        httpBackend.whenGET('http://localhost:8080/api/api/person/1').respond(403);
        spyOn(service, 'getPerson').andCallThrough();

        // Test
        var person = null;
        service.getPerson().then(function (result) {
          person = result;
        }, function () {
          person = null;
        });

        // Make the requests go though
        scope.$digest();
        httpBackend.flush();

        expect(service.getPerson).toHaveBeenCalled();
        expect(person).toBe(null);
    });
  });

  describe('getActiveProjectId', function () {

    it('should return the -1 when no project is active', function () {

      // Test setup
      httpBackend.whenGET('http://localhost:8080/api/api/person/1').respond(persons[0]);
      var person = null;
      service.getPerson().then(function (result) {
        person = result;
      }, function () {
      });

      // Make the requests go though
      scope.$digest();
      httpBackend.flush();

      // Test
      var projectId = service.getActiveProjectId();

      expect(projectId).toBe(-1);
    });

    it('should return the project id when no user is logged in', function () {
      // Test setup
      httpBackend.whenGET('http://localhost:8080/api/api/person/1').respond(persons[2]);
      var person = null;
      service.getPerson().then(function (result) {
        person = result;
      }, function () {
      });

      // Make the requests go though
      scope.$digest();
      httpBackend.flush();

      var projectId = service.getActiveProjectId();
      expect(projectId).toBe(301);
    });
  });
});
