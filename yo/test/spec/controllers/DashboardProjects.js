/*globals describe, beforeEach, $injector, inject, expect, it, spyOn */
'use strict';

describe('Controller: DashboardProjectsCtrl', function () {

  // load the controller's module
  beforeEach(module('tpsApp'));

  var ProjectServiceMock, TimeEntryServiceMock, PersonServiceMock;
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

  // Initialize the ProjectServiceMock
  beforeEach(function () {
    ProjectServiceMock = {
      remove: function (project) {
        //
      },
      refresh: function () {
        //
      },
      update: function () {
        //
      }
    };
  });

  // Initialize the TimeEntryServiceMock
  beforeEach(function () {
    TimeEntryServiceMock = {
      // TBD
    };
  });

  // Initialize the PersonServiceMock
  beforeEach(function () {
    PersonServiceMock = {
      getActiveProjectId: function () {
        return projects[2];
      }
    };
  });

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, $injector) {
    scope = $rootScope.$new();
    DashboardProjectsCtrl = $controller('DashboardProjectsCtrl', {
      $scope: scope,
      ProjectService: ProjectServiceMock,
      PersonService: PersonServiceMock,
      TimeEntryService: TimeEntryServiceMock
    });
    var project = [];
    DashboardProjectsCtrl.$inject = ['$scope',  '$route'];
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

  it('should create a new project using the ProjectService', function () {
    var project = projects[0];
    scope.project.name = 'New Project';
    scope.project.rate = 530;
    scope.project.comment = 'Hej';
    scope.createProject();
  });

  it('should call remove project in ProjectService', function () {
    var project = projects[1];
    scope.removProject(project);
    spyOn(ProjectServiceMock, 'remove').andCallThrough();
    // expect(ProjectServiceMock.remove).toHaveBeenCalled();
  });

  it('should should call update in ProjectService', function () {
    var project = projects[2];
    spyOn(ProjectServiceMock, 'update').andCallThrough();
    // expect(ProjectServiceMock.update).toHaveBeenCalled();
  });

  it('should just create a new timer task when no project is active', function () {
    var projectToStart = projects[0];

    // Clear active project
    scope.startProjectTimer(projectToStart);
    spyOn(TimeEntryServiceMock, 'startTimer').andCallThrough();
    expect(TimeEntryServiceMock.startTimer).toHaveBeenCalled();
  });
});
