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
      // Verify update?
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
      expect(scope.$broadcast).toHaveBeenCalledWith('onProjectUpdated', projects[1]);
    });

    it('should mark the project as active', function () {
      var projectId = 302;
      var project = service.get(projectId);
      expect(project.id).toBe(projectId);
      expect(project.active).toBe(false);
      // The test
      service.setActive(project, true);
      expect(project.active).toBe(true);
    });
  });
});
