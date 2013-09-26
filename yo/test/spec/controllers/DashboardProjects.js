/*globals describe, beforeEach, $injector, inject, expect, it */
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

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, $injector) {
    scope = $rootScope.$new();
    DashboardProjectsCtrl = $controller('DashboardProjectsCtrl', {
      $scope: scope
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
    expect(DashboardProjectsCtrl.getById(2)).toEqual(projects[1]);
  });
});
