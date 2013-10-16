
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

/*globals describe, beforeEach, inject, expect, it, spyOn */
'use strict';

describe('Controller: DashboardProjectsCtrl', function () {

  // load the controller's module
  beforeEach(module('tpsApp'));

  var DashboardProjectsCtrl, scope;
  var projects = [
    {'id': 1, 'name': 'Project A', 'description': null, 'rate': null, 'new': false},
    {'id': 2, 'name': 'Project B', 'description': null, 'rate': null, 'new': false},
    {'id': 3, 'name': 'Project C', 'description': null, 'rate': null, 'new': false}
  ];
  var timeEntries = [
    {id: 1, startTime: 2, endTime: 3},
    {id: 2, startTime: 2, endTime: 3}
  ];

  // Initialize the TimerServiceMock
  var TimerServiceMock = {
    remove: function (project) {
      console.log('TimerServiceMock:remove called');
    },
    refresh: function () {
      console.log('TimerServiceMock:refresh called');
    },
    update: function () {
      console.log('TimerServiceMock:update called');
    }
  };

  // Initialize the TimeEntryServiceMock
  var TimeEntryServiceMock = {
    startTimer: function () {
      console.log('TimeEntryServiceMock:startTimer called');
    },
    stopTimer: function () {
      console.log('TimeEntryServiceMock:stopTimer called');
    }
  };

  // Initialize the PersonServiceMock
  var activeProjectId = -1;
  var PersonServiceMock = {
    getActiveProjectId: function () {
      return activeProjectId;
    }
  };

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, $injector) {
    scope = $rootScope.$new();
    DashboardProjectsCtrl = $controller('DashboardProjectsCtrl', {
      $scope: scope,
      TimerService: TimerServiceMock,
      PersonService: PersonServiceMock,
      TimeEntryService: TimeEntryServiceMock
    });
    var project = [];
    DashboardProjectsCtrl.$inject = ['$scope',  '$route', 'ProjectServic', 'PersonService', 'TimeEntryService'];
  }));

  it('should initialize with empty project list, etc.', function () {
    expect(scope.projects).toBeDefined();
    expect(scope.projects.length).not.toBeDefined();
  });

  it('should use projects provided from service when receiving "onProjectsRefreshed" event', function () {
    scope.$broadcast('onProjectsRefreshed', projects);
    expect(scope.projects).toBeDefined();
    expect(scope.projects.length).toBe(3);
  });

  it('should return the reference of the project with id', function () {
    scope.projects = projects;
    expect(scope.getById(scope.projects, 2)).toEqual(projects[1]);
  });

  it('should create a new project using the TimerService', function () {
    var project = projects[0];
    scope.project.name = 'New Project';
    scope.project.rate = 530;
    scope.project.comment = 'Hej';
    scope.createProject();
  });

  it('should call remove project in TimerService', function () {
    // Register spyes
    spyOn(TimerServiceMock, 'remove').andCallThrough();

    // Test code
    var project = projects[1];
    scope.removeProject(project);

    // Check spyes
    expect(TimerServiceMock.remove).toHaveBeenCalled();
  });

  it('should should call update in TimerService', function () {
    var project = projects[2];
    spyOn(TimerServiceMock, 'update').andCallThrough();
    // expect(TimerServiceMock.update).toHaveBeenCalled();
  });

  it('should just create a new timer task when no project is active', function () {
    // Register spyes
    spyOn(TimeEntryServiceMock, 'startTimer').andCallThrough();
    spyOn(TimeEntryServiceMock, 'stopTimer').andCallThrough();

    // Preconditions
    // var activeProjectId = -1;
    // expect(PersonServiceMock.getActiveProjectId()).not.toBeGreaterThan(0);

    // Test code
    var projectToStart = projects[0];
    scope.startProjectTimer(projectToStart);
    activeProjectId = 2;

    // Verifications
    // Timer must be started
    expect(TimeEntryServiceMock.startTimer).toHaveBeenCalled();
    expect(TimeEntryServiceMock.stopTimer).not.toHaveBeenCalled();
    expect(PersonServiceMock.getActiveProjectId()).toBeGreaterThan(0);
  });

  it('should stop active timer when project is active', function () {
    // Register spyes
    spyOn(TimeEntryServiceMock, 'startTimer').andCallThrough();

    // Test code
    var projectToStart = projects[0];
    activeProjectId = projectToStart.id;
    scope.startProjectTimer(projectToStart);

    // Project should no longer be active
    //expect(PersonServiceMock.getActiveProjectId()).toBe(-1);

  });


  it('should create a new timer task and stop the running one when project is active', function () {
    // Setup test

    // Test code

    // Verifications
  });
});
