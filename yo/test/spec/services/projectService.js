/*globals expect, afterEach, beforeEach, spyOn, describe, it, inject, _ */

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
    { id: 301, name: 'Project A' },
    { id: 302, name: 'Project B' }];
  var timeEntries = [
    { id: 201, startTime: 0, endTime: 2, project: projects[0] }];
  var persons = [
    { id: 1, username: 'User A', activeTimeEntry: null },
    { id: 2, username: 'User B' },
    { id: 3, username: 'User C', activeTimeEntry: timeEntries[0] }];

  // Inject the services
  beforeEach(inject(function (ProjectService, $httpBackend, $rootScope) {
    service = ProjectService;
    httpBackend = $httpBackend;
    scope = $rootScope;
  }));  

  afterEach(function () {
    httpBackend.verifyNoOutstandingExpectation();
    httpBackend.verifyNoOutstandingRequest();
  });

  describe('getAll - uninitialized', function() {

    it('should return empty list when project list has not been refreshed/initialized', function () {
      // Test setup
      spyOn(service, 'getAll').andCallThrough();

      // Test
      var result = service.getAll();

      // Make the requests go though
      scope.$digest();

      // Verifications
      expect(service.getAll).toHaveBeenCalled();
      expect(result).toBeDefined();
      expect(result.length).toBe(0);
    });
  });

  describe('getAll - initialized', function() {
    beforeEach(function () {
      httpBackend.whenGET('http://localhost:8080/api/api/project').respond(_.clone(projects));
      spyOn(service, 'getAll').andCallThrough();
      spyOn(service, 'refresh').andCallThrough();
      spyOn(scope, '$broadcast').andCallThrough();
      service.refresh();
      // Must let the service process the refresh
      scope.$digest();
      httpBackend.flush();
      console.log('init list');
    });

    it('should return project list when project list has been refreshed/initialized', function () {
      var result = service.getAll();

      // Verifications
      expect(service.refresh).toHaveBeenCalled();
      expect(service.getAll).toHaveBeenCalled();
      expect(result).toBeDefined();
      expect(result).not.toBeNull();
      expect(result.length).toBe(2);
      expect(scope.$broadcast).toHaveBeenCalledWith('onProjectsRefreshed', result);
    });

    it('should get the project with the provided id', function () {
      var result = service.get(302);
      expect(result).not.toBeNull();
      expect(result).toBeDefined();
      expect(result.id).toBe(302);
    });

    it('should remove the project with the provided id', function () {
      var projectId = 302;
      // Assure project exists before removing it
      var projectToBeRemoved = service.get(projectId);
      expect(projectToBeRemoved.id).toBe(projectId);
      // Remove project
      httpBackend.whenDELETE('http://localhost:8080/api/api/project/302').respond(201);
      service.remove(projectToBeRemoved);
      // Make the requests go though
      scope.$digest();
      httpBackend.flush();
      // Verify it is gone      
      var negative = service.get(projectId);
      expect(negative).toBeUndefined();
    });

    it('should update the provided project', function () {
      var projectId = 302;
      // First get the project
      var project = service.get(projectId);
      expect(project.id).toBe(projectId);
      // Update it
      httpBackend.whenPUT('http://localhost:8080/api/api/project/302').respond();
      service.update(project);
      // Make the requests go though
      scope.$digest();
      httpBackend.flush();
    });

    it('should create a new project when project has no id', function () {
      var project = { name: 'New project' };
      // Create the proejct
      httpBackend.whenPOST('http://localhost:8080/api/api/project').respond(projects[1]);
      service.update(project);
      // Make the requests go though
      scope.$digest();
      httpBackend.flush();
      // Verify it now was created      
    });
  });
});
